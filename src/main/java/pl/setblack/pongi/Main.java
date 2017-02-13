package pl.setblack.pongi;

import pl.setblack.pongi.games.GamesModule;
import pl.setblack.pongi.games.GamesService;
import pl.setblack.pongi.games.repo.GamesRepoES;
import pl.setblack.pongi.games.repo.GamesRepository;
import pl.setblack.pongi.scores.ScoresService;
import pl.setblack.pongi.scores.repo.ScoresRepositoryInMem;
import pl.setblack.pongi.scores.repo.ScoresRepositoryNonBlocking;
import pl.setblack.pongi.users.UsersModule;
import pl.setblack.pongi.users.UsersService;
import pl.setblack.pongi.users.repo.SessionsRepo;
import pl.setblack.pongi.users.repo.UsersRepoES;

import java.time.Clock;

/**
 * Created by jarek on 1/29/17.
 */
public class Main {


    public static void main(final String ... args ) throws  Exception{
        final Clock clock = Clock.systemUTC();

        final UsersModule usersModule = new UsersModule(clock);
        final UsersService usersService = usersModule.createService();

        final ScoresRepositoryNonBlocking scoresRepo = new ScoresRepositoryNonBlocking(new ScoresRepositoryInMem());

        final GamesModule gamesModule = new GamesModule(
                clock,  usersModule.getSessionsRepo() , scoresRepo );

        final GamesService gamesService = gamesModule.createService();

        final ScoresService scoresService = new ScoresService(scoresRepo);
        Server server = new Server(usersService, gamesService, scoresService);
        server.start();

    }




}
