package pl.setblack.pongi.games;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.reactivex.Flowable;
import javaslang.collection.List;
import javaslang.control.Option;
import pl.setblack.pongi.games.api.GamePhase;
import pl.setblack.pongi.games.api.GameState;
import pl.setblack.pongi.games.api.Player;
import pl.setblack.pongi.games.repo.GamesRepository;
import pl.setblack.pongi.games.repo.GamesRepositoryNonBlocking;
import pl.setblack.pongi.scores.GameResult;
import pl.setblack.pongi.scores.ScoreRecord;
import pl.setblack.pongi.scores.repo.ScoresRepositoryNonBlocking;
import pl.setblack.pongi.users.api.Session;
import pl.setblack.pongi.users.repo.SessionsRepo;
import ratpack.exec.Promise;
import ratpack.func.Action;
import ratpack.func.Block;
import ratpack.handling.Chain;
import ratpack.handling.Context;
import ratpack.handling.Handler;
import ratpack.jackson.Jackson;
import ratpack.jackson.JsonRender;
import ratpack.websocket.WebSockets;

import java.time.Clock;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class GamesService {

    private final GamesRepositoryNonBlocking gamesRepo;

    private final SessionsRepo sessionsRepo;

    private final ScoresRepositoryNonBlocking scoresRepo;


    private final ConcurrentHashMap<String, Flowable<GameState>> gamesFlow = new ConcurrentHashMap<>();

    public GamesService(
            final GamesRepository gamesRepo,
            final SessionsRepo sessionsRepo,
            final ScoresRepositoryNonBlocking scoresRepo) {
        this.gamesRepo = new GamesRepositoryNonBlocking(gamesRepo);
        this.sessionsRepo = sessionsRepo;
        this.scoresRepo = scoresRepo;

    }

    public Action<Chain> define() {
        return chain -> chain
                .prefix("games", games ->
                        games.post(":id", joinGame())
                                .all(
                                        noGameId ->
                                                noGameId.
                                                        byMethod(m -> m
                                                                .get(listGames(noGameId))
                                                                .post(createGame(noGameId)))
                                )

                )
                //  .prefix("join", joinGame())
                .prefix("players", moves ->
                        moves.post(":id", movePaddle()))
                .prefix("stream", stream ->
                        stream.get(":id", streamGame(stream)));
    }

    private Handler movePaddle() {
        return ctx -> {
            final String gameId = ctx.getPathTokens().get("id");
            ctx.getRequest().getBody().then(body -> {
                final float targetY = Float.parseFloat(body.getText());
                renderAsync(ctx, session -> gamesRepo.movePaddle(gameId, session.userId, targetY));
            });
        };
    }

    private Handler streamGame(Chain chain) {
        return ctx -> {
            final String gameId = ctx.getPathTokens().get("id");
            final Option<Flowable<GameState>> gsOpt = Option.of(this.gamesFlow.get(gameId));
            gsOpt.forEach(gsFlow -> {
                final Flowable<String> stringFlow = gsFlow

                        .map(val -> {
                            final String result =
                                    chain.getRegistry()
                                            .get(ObjectMapper.class)
                                            .writeValueAsString(val);

                            return result;
                        });

                WebSockets.websocketBroadcast(ctx, stringFlow);
            });
        };

    }

    private Block createGame(Context ctx) {
        return () ->
                ctx.getRequest().getBody().then(gameName -> {
                    final UUID uuid = UUID.randomUUID();
                    renderAsync(
                            ctx,
                            sess -> gamesRepo
                                    .createGame(uuid.toString(), gameName.getText(), sess.userId));
                });

    }

    private Handler joinGame() {
        return ctx -> {
            final String gameUUID = ctx.getPathTokens().get("id");
            renderAsync(
                    ctx,
                    sess -> {
                        final CompletionStage<Option<GameState>> state = gamesRepo
                                .joinGame(gameUUID, sess.userId);
                        return state.thenApply(gameOption -> {
                            gameOption.forEach(game -> {
                                if (game.phase == GamePhase.STARTED) {
                                    this.gamesFlow.computeIfAbsent(gameUUID, this::createFlow);
                                }
                            });
                            return gameOption;
                        });
                    });
        };
    }

    private Flowable<GameState> createFlow(String gameUUID) {
        final Flowable<GameState> stateFlow = Flowable.interval(1000, 50, TimeUnit.MILLISECONDS)
                .flatMap(whatever -> {
                    final CompletionStage<Option<GameState>> future = this.gamesRepo.push(gameUUID);
                    future.thenAccept(o -> o.forEach(gs -> {
                        if (gs.phase == GamePhase.ENDED) {
                            endGame(gameUUID, gs);
                        }
                    }));
                    return Flowable.fromFuture(future.toCompletableFuture());
                })
                .filter(s -> s.isDefined())
                .map(s -> s.get());

        return stateFlow;
    }

    private void endGame(String gameUUID, GameState gs) {
        if (this.gamesFlow.remove(gameUUID) != null) {
            final ScoreRecord player1 =
                    createScore(gs.players._1, gs.players._2, gameUUID);
            final ScoreRecord player2 =
                    createScore(gs.players._2, gs.players._1, gameUUID);
            this.scoresRepo.registerScore(List.of(player1, player2));
            this.gamesRepo.removeGame(gameUUID);
        }
    }

    private ScoreRecord createScore(final Player player, final Player opponent, final String gameId) {
        return new ScoreRecord(
                player.name,
                calcResult(player, opponent),
                player.score,
                opponent.score,
                gameId
        );
    }

    private GameResult calcResult(final Player player, final Player opponent) {
        if (player.score > opponent.score) {
            return GameResult.WON;
        }
        if (player.score < opponent.score) {
            return GameResult.LOST;
        }
        throw new IllegalStateException();
    }

    private Block listGames(Context ctx) {
        return () -> renderAsync(ctx, (any) -> gamesRepo.listGames());
    }

    private <T> void renderAsync(Context ctx,
                                 Function<Session, CompletionStage<T>> async
    ) {
        String bearer = ctx.getRequest().getHeaders().get("Authorization");
        final String sessionId = bearer.replace("Bearer ", "");
        final Option<Session> session = sessionsRepo.getSession(sessionId);

        final Promise<JsonRender> result = Promise.async(d ->
                d.accept(session.map(
                        sess -> {
                            final CompletionStage<JsonRender> future = async.apply(sess).thenApply(Jackson::json);
                            return future;
                        })
                        .getOrElse(
                                CompletableFuture.completedFuture("unauthorized")
                                        .thenApply(Jackson::json)))
        );
        ctx.render(result);
    }
}
