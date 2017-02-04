package pl.setblack.pongi.games;

import javaslang.control.Option;
import pl.setblack.pongi.games.repo.GamesRepository;
import pl.setblack.pongi.games.repo.GamesRepositoryNonBlocking;
import pl.setblack.pongi.users.api.Session;
import pl.setblack.pongi.users.repo.SessionsRepo;
import ratpack.exec.Promise;
import ratpack.func.Action;
import ratpack.handling.Chain;
import ratpack.handling.Context;
import ratpack.jackson.Jackson;
import ratpack.jackson.JsonRender;

import java.time.Clock;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;

/**
 * Created by jarek on 2/1/17.
 */
public class GamesService {

    private final GamesRepositoryNonBlocking gamesRepo;

    private final SessionsRepo sessionsRepo;

    private final Clock clock;


    public GamesService(final GamesRepository gamesRepo, SessionsRepo sessionsRepo, Clock clock) {
        this.gamesRepo = new GamesRepositoryNonBlocking(gamesRepo);

        this.sessionsRepo = sessionsRepo;
        this.clock = clock;
    }

    public Action<Chain> define() {
        return chain -> chain
                .prefix("games", listGames())
                .prefix("create", createGame())
                .prefix("join", joinGame());
    }

    private Action<? super Chain> createGame() {
        return chain -> chain.post( ctx -> {
            ctx.getRequest().getBody().then(gameName -> {
                final UUID uuid = UUID.randomUUID();
                renderAsync(
                        ctx,
                        sess -> gamesRepo
                                .createGame(uuid.toString(), gameName.getText(), sess.userId));
            });
        });
    }
    private Action<? super Chain> joinGame() {
        return chain -> chain.post( ctx -> {
            ctx.getRequest().getBody().then(gameUUID -> {
                renderAsync(
                        ctx,
                        sess -> gamesRepo
                                .joinGame(gameUUID.toString(), sess.userId, this.clock.millis()));
            });
        });
    }



    private Action<? super Chain> listGames() {
        return chain -> chain.get(
                ctx -> renderAsync(ctx, (any) -> gamesRepo.listGames()));
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
