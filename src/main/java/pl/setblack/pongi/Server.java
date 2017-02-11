package pl.setblack.pongi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import javaslang.control.Try;
import javaslang.jackson.datatype.JavaslangModule;
import pl.setblack.badass.Politician;
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

/**
 * Created by jarek on 1/29/17.
 */
public class Server {

    private final UsersService usersService;
    private final GamesService gamesService;
    private final ScoresService scoresService;

    private final RatpackServer ratpackServer;

    public Server(UsersService usersService, GamesService gamesService, ScoresService scoresService) {
        this.usersService = usersService;
        this.gamesService = gamesService;
        this.scoresService = scoresService;
        ratpackServer = Try.of(()->createServer(defineApi())).get();
    }

    public void start() {
        Try.run(()->this.ratpackServer.start());

    }

    public void stop() {
        Try.run(()->this.ratpackServer.stop());
    }

    public static RatpackServer createServer(Action<Chain> handlers)
            {
                try {
                    return RatpackServer.of(server -> createEmptyServer(server)
                            .handlers(chain ->
                                    chain.all(RequestLogger.ncsa())
                                    .prefix("api", handlers)

                                            .files(fileHandlerSpec -> fileHandlerSpec.dir("src/main/webapp"))

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
                .prefix("score", score->score.get("scores", ctx->ctx.render("[]")));
    }

    private static RatpackServerSpec createEmptyServer(RatpackServerSpec initial)
            throws Exception {
        Path currentRelativePath = Paths.get("").toAbsolutePath();
        System.out.println("path is:" + currentRelativePath);
        return initial
                .serverConfig(
                        ServerConfig
                                .builder()
                               .baseDir(currentRelativePath)
                                .publicAddress(new URI("http://0.0.0.0"))
                                .port(9000)
                                .threads(4)
                                //
                ).registryOf(r -> r.add(configureJacksonMapping()));
    }

    public static final ObjectMapper configureJacksonMapping() {
        return  new ObjectMapper()
                .registerModule(new ParameterNamesModule())
                .registerModule(new Jdk8Module())
                .registerModule(new JavaTimeModule())
                .registerModule(new JavaslangModule());
    }
}
