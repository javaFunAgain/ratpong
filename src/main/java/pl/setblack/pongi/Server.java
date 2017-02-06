package pl.setblack.pongi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import javaslang.jackson.datatype.JavaslangModule;
import pl.setblack.badass.Politician;
import pl.setblack.pongi.games.GamesService;
import pl.setblack.pongi.scores.ScoresService;
import pl.setblack.pongi.users.UsersService;
import ratpack.func.Action;
import ratpack.handling.Chain;
import ratpack.server.RatpackServer;
import ratpack.server.RatpackServerSpec;
import ratpack.server.ServerConfig;

import java.net.URI;

/**
 * Created by jarek on 1/29/17.
 */
public class Server {

    private final UsersService usersService;
    private final GamesService gamesService;
    private final ScoresService scoresService;

    public Server(UsersService usersService, GamesService gamesService, ScoresService scoresService) {
        this.usersService = usersService;
        this.gamesService = gamesService;
        this.scoresService = scoresService;
        Politician.beatAroundTheBush(this::init);
    }

    private void init() throws Exception {
        createServer(defineApi()).start();

    }

    public static RatpackServer createServer(Action<Chain> handlers)
            {
                try {
                    return RatpackServer.of(server -> createEmptyServer(server)
                            .handlers(chain ->
                                    chain.prefix("api", handlers)
                            )
                    );
                } catch (Exception e) {
                    throw new IllegalStateException(e);
                }
    }

    private Action<Chain> defineApi() {
        return apiChain -> apiChain
                .prefix("users", usersService.users())
                .prefix("games", gamesService.define())
                .prefix("score", scoresService.scores());
    }

    private static RatpackServerSpec createEmptyServer(RatpackServerSpec initial)
            throws Exception {
        return initial
                .serverConfig(
                        ServerConfig
                                .embedded()
                                .publicAddress(new URI("http://0.0.0.0"))
                                .port(9000)
                                .threads(4)
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
