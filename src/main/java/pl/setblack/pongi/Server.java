package pl.setblack.pongi;

import javaslang.Function1;
import javaslang.control.Try;
import pl.setblack.pongi.games.GamesService;
import pl.setblack.pongi.scores.ScoresService;
import pl.setblack.pongi.users.UsersService;
import ratpack.func.Action;
import ratpack.handling.Chain;
import ratpack.handling.RequestLogger;
import ratpack.server.RatpackServer;
import ratpack.server.RatpackServerSpec;

import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Server {

    private final UsersService usersService;
    private final GamesService gamesService;
    private final ScoresService scoresService;

    private final RatpackServer ratpackServer;

    public Server(UsersService usersService, GamesService gamesService, ScoresService scoresService) {
        this.usersService = usersService;
        this.gamesService = gamesService;
        this.scoresService = scoresService;
        ratpackServer =
                Try.of(() -> createDefaultServer(
                        defineApi()))
                        .onFailure(this::handleError).get();
    }

    public void start() {
        Try.run(() -> this.ratpackServer.start()).onFailure(System.out::println);

    }

    public void stop() {
        Try.run(() -> this.ratpackServer.stop());
    }



    public static RatpackServer createUnconfiguredServer(Action<Chain> handlers) {
        return createDefaultServer(makeApi(handlers), x->x);
    }

    private static RatpackServer createDefaultServer(Action<Chain> handlers ) {
        return createDefaultServer(makeApi(handlers).append(serveFiles()), Server::configuration);
    }


    private static Action<Chain> makeApi( Action<Chain> handlers) {
        return chain -> chain.prefix("api", handlers);
    }

    private static Action<Chain> serveFiles() {
        return chain->chain
                .files(fileHandlerSpec -> fileHandlerSpec
                .dir("src/main/webapp")
                .indexFiles("index.html")
        );
    }

    private static RatpackServer createDefaultServer(Action<Chain> handlers,
                                                    Function1<RatpackServerSpec, RatpackServerSpec> configuration ) {
        try {
            return RatpackServer.of(server -> configuration.apply(createEmptyServer(server))
                    .handlers(chain ->
                            handlers.execute(chain.all(RequestLogger.ncsa()))
                    )
            );
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }



    private Action<Chain> defineApi() {
        return apiChain -> apiChain
                .insert(usersService.usersApi())
                .prefix("games", gamesService.define())
                .prefix("score", scoresService.scores());
    }

    private static RatpackServerSpec createEmptyServer(RatpackServerSpec initial)
            throws Exception {
        return initial
                .registryOf(r -> r.add(JSONMapping.getJsonMapping()));
    }

    private static RatpackServerSpec configuration(RatpackServerSpec server) {
        final Path currentRelativePath = Paths.get("").toAbsolutePath();
        try {
            return server.serverConfig(
                    scb ->
                            scb
                            .baseDir(currentRelativePath)
                            .publicAddress(new URI("http://0.0.0.0"))
                            .port(9000)
                            .threads(4)
            );
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }


    private void handleError(final Throwable t) {
        System.err.println(t);
    }

}
