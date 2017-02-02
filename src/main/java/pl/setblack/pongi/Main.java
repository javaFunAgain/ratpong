package pl.setblack.pongi;

import pl.setblack.pongi.games.GamesService;
import pl.setblack.pongi.games.repo.GamesRepository;
import pl.setblack.pongi.games.repo.GamesRepositoryInMemory;
import pl.setblack.pongi.users.UsersService;
import pl.setblack.pongi.users.repo.SessionsRepo;
import pl.setblack.pongi.users.repo.UsersRepository;
import pl.setblack.pongi.users.repo.UsersRepositoryInMemory;

import java.time.Clock;

/**
 * Created by jarek on 1/29/17.
 */
public class Main {


    public static void main(final String ... args ) throws  Exception{

        final SessionsRepo  sessionRepo = new SessionsRepo(Clock.systemUTC());
        final UsersRepository usersRepo = new UsersRepositoryInMemory();
        final UsersService usersService = new UsersService(usersRepo, sessionRepo);
        final GamesRepository gamesRepo = new GamesRepositoryInMemory();
        final GamesService gamesService = new GamesService(gamesRepo, sessionRepo);
        Server server = new Server(usersService, gamesService);

    }




}
