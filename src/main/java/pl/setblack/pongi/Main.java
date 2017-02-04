package pl.setblack.pongi;

import pl.setblack.pongi.games.GamesService;
import pl.setblack.pongi.games.repo.GamesRepository;
import pl.setblack.pongi.games.repo.GamesRepositoryInMemory;
import pl.setblack.pongi.users.UsersService;
import pl.setblack.pongi.users.repo.SessionsRepo;
import pl.setblack.pongi.users.repo.UsersRepoES;
import pl.setblack.pongi.users.repo.UsersRepository;
import pl.setblack.pongi.users.repo.UsersRepositoryInMemory;

import java.time.Clock;

/**
 * Created by jarek on 1/29/17.
 */
public class Main {


    public static void main(final String ... args ) throws  Exception{
        final Clock clock = Clock.systemUTC();
        final SessionsRepo  sessionRepo = new SessionsRepo(clock);
        final UsersRepoES persistentUsersRepo = new UsersRepoES();
        final UsersService usersService = new UsersService(persistentUsersRepo, sessionRepo);
        final GamesRepository gamesRepo = new GamesRepositoryInMemory();
        final GamesService gamesService = new GamesService(gamesRepo, sessionRepo, clock);
        Server server = new Server(usersService, gamesService);

    }




}
