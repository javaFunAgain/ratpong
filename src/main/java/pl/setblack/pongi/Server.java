package pl.setblack.pongi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import javaslang.Function0;
import javaslang.Function1;
import javaslang.control.Try;
import javaslang.jackson.datatype.JavaslangModule;
import pl.setblack.pongi.games.GamesService;
import pl.setblack.pongi.scores.ScoresService;
import pl.setblack.pongi.users.UsersService;
import ratpack.func.Action;
import ratpack.handling.Chain;
import ratpack.handling.RequestLogger;
import ratpack.server.RatpackServer;
import ratpack.server.RatpackServerSpec;
import ratpack.server.ServerConfig;

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

    private static RatpackServer createDefaultServer(Action<Chain> handlers ) {
        return createDefaultServer(handlers, Server::configured);
    }

    public static RatpackServer createUnconfiguredServer(Action<Chain> handlers) {
        return createDefaultServer(handlers, x->x);
    }


    private static RatpackServer createDefaultServer(Action<Chain> handlers,
                                                    Function1<RatpackServerSpec, RatpackServerSpec> configuration) {
        try {
            return RatpackServer.of(server -> configuration.apply(createEmptyServer(server))
                    .handlers(chain ->
                            chain.all(RequestLogger.ncsa())
                                    .prefix("api", handlers)
                                    .files(fileHandlerSpec -> fileHandlerSpec
                                            .dir("src/main/webapp")
                                            .indexFiles("index.html")
                                    )

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
                .registryOf(r -> r.add(configureJacksonMapping()));
    }

    private static RatpackServerSpec configured(RatpackServerSpec server) {
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

    public static final ObjectMapper configureJacksonMapping() {
        return new ObjectMapper()
                .registerModule(new ParameterNamesModule())
                .registerModule(new Jdk8Module())
                .registerModule(new JavaTimeModule())
                .registerModule(new JavaslangModule());
    }


    private void handleError(final Throwable t) {
        System.err.println(t);
    }

}
