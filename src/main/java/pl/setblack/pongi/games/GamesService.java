package pl.setblack.pongi.games;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.reactivex.Flowable;
import javaslang.collection.List;
import javaslang.control.Option;
import pl.setblack.pongi.JsonMapping;
import pl.setblack.pongi.games.api.GamePhase;
import pl.setblack.pongi.games.api.GameState;
import pl.setblack.pongi.games.api.Player;
import pl.setblack.pongi.games.repo.GamesRepository;
import pl.setblack.pongi.games.repo.GamesRepositoryProcessor;
import pl.setblack.pongi.scores.GameResult;
import pl.setblack.pongi.scores.ScoreRecord;
import pl.setblack.pongi.scores.repo.ScoresRepositoryProcessor;
import pl.setblack.pongi.users.api.Session;
import pl.setblack.pongi.users.repo.SessionsRepo;
import ratpack.func.Action;
import ratpack.func.Block;
import ratpack.handling.Chain;
import ratpack.handling.Context;
import ratpack.handling.Handler;
import ratpack.websocket.WebSockets;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class GamesService {

    private final GamesRepositoryProcessor gamesRepo;

    private final SessionsRepo sessionsRepo;

    private final ScoresRepositoryProcessor scoresRepo;

    private final ConcurrentHashMap<String, Flowable<GameState>> gamesFlow = new ConcurrentHashMap<>();

    GamesService(
            final GamesRepository gamesRepo,
            final SessionsRepo sessionsRepo,
            final ScoresRepositoryProcessor scoresRepo) {
        this.gamesRepo = new GamesRepositoryProcessor(gamesRepo);
        this.sessionsRepo = sessionsRepo;
        this.scoresRepo = scoresRepo;

    }

    public Action<Chain> gamesApi() {
        return apiChain -> apiChain
                .prefix("games", define());
    }

    private Action<Chain> define() {
        return chain -> chain
                .prefix("game", games ->
                        games.post(":id", joinGame())
                )
                .prefix("games", games ->
                        games.all(
                                noGameId ->
                                        noGameId.
                                                byMethod(m -> m
                                                        .get(listGames(noGameId))
                                                        .post(createGame(noGameId)))
                        )
                )
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
                renderSecure(ctx, session -> gamesRepo.movePaddle(gameId, session.userId, targetY));
            });
        };
    }

    private Handler streamGame(Chain chain) {
        return ctx -> {
            final String gameId = ctx.getPathTokens().get("id");
            final Option<Flowable<GameState>> gsOpt = Option.of(this.gamesFlow.get(gameId));
            gsOpt.forEach(gsFlow -> WebSockets.websocketBroadcast(ctx, gsFlow
                    .map(val -> chain.getRegistry()
                            .get(ObjectMapper.class)
                            .writeValueAsString(val))));
        };
    }

    private Block createGame(Context ctx) {
        return () ->
                ctx.getRequest().getBody().then(gameName -> {
                    final UUID uuid = UUID.randomUUID();
                    renderSecure(
                            ctx,
                            sess -> gamesRepo
                                    .createGame(uuid.toString(), gameName.getText(), sess.userId));
                });

    }

    private Handler joinGame() {
        return ctx -> {
            final String gameUUID = ctx.getPathTokens().get("id");
            renderSecure(
                    ctx,
                    sess -> getSessionCompletionStageFunction(sess, gameUUID));
        };
    }

    private CompletionStage<Option<GameState>> getSessionCompletionStageFunction(final Session sess, final String gameUUID) {

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

    }

    private Flowable<GameState> createFlow(String gameUUID) {
        return Flowable.interval(1000, 50, TimeUnit.MILLISECONDS)
                .flatMap(whatever -> {
                    final CompletionStage<Option<GameState>> future = this.gamesRepo.push(gameUUID);
                    future.thenAccept(o -> o.forEach(gs -> {
                        if (gs.phase == GamePhase.ENDED) {
                            endGame(gameUUID, gs);
                        }
                    }));
                    return Flowable.fromFuture(future.toCompletableFuture());
                })
                .filter(Option::isDefined)
                .map(Option::get);
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
        return () -> renderSecure(ctx, (any) -> gamesRepo.listGames());
    }

    private <T> void renderSecure(Context ctx,
                                  Function<Session, CompletionStage<T>> async
    ) {
        final Option<String> bearer = Option.of(ctx.getRequest().getHeaders().get("Authorization"));
        final Option<String> sessionId = bearer.map(b -> b.replace("Bearer ", ""));
        final Option<Session> session = sessionId.flatMap(sessionsRepo::getSession);
        ctx.render(JsonMapping.toJsonPromise(session.map(
                sess -> (CompletionStage<Object>) async.apply(sess)
        )
                .getOrElse(
                        CompletableFuture.completedFuture("unauthorized")
                )));
    }
}
