package pl.setblack.pongi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import javaslang.jackson.datatype.JavaslangModule;
import pl.setblack.badass.Politician;
import pl.setblack.pongi.games.GamesService;
import pl.setblack.pongi.users.UsersService;
import ratpack.server.RatpackServer;
import ratpack.server.ServerConfig;

import java.net.URI;

/**
 * Created by jarek on 1/29/17.
 */
public class Server {

    private final UsersService usersService;
    private final GamesService gamesService;

    public Server(UsersService usersService, GamesService gamesService) {
        this.usersService = usersService;
        this.gamesService = gamesService;
        Politician.beatAroundTheBush(this::init);
    }

    private void init() throws Exception {
        RatpackServer.start(server -> server
                .serverConfig(
                        ServerConfig
                                .embedded()
                                .publicAddress(new URI("http://0.0.0.0"))
                                .port(9000)
                                .threads(4)
                ).registryOf(r -> r.add(
                        new ObjectMapper()
                                .registerModule(new ParameterNamesModule())
                                .registerModule(new Jdk8Module())
                                .registerModule(new JavaslangModule())))
                .handlers(chain ->
                        chain.prefix("api",
                                apiChain -> apiChain
                                        .prefix("users", usersService.users())
                                        .prefix("games", gamesService.define()))
                ));

    }
}
