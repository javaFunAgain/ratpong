package pl.setblack.pongi;

import pl.setblack.pongi.games.GamesModule;
import pl.setblack.pongi.scores.ScoresModule;
import pl.setblack.pongi.users.UsersModule;

import java.time.Clock;


public class Main {


    public static void main(final String ... args ) throws  Exception{
        final Clock clock = Clock.systemUTC();

        final UsersModule usersModule = new UsersModule(clock);
        final ScoresModule scoresModule = new ScoresModule();
        final GamesModule gamesModule = new GamesModule(
                clock,  usersModule.getSessionsRepo() , scoresModule.getScoresRepository() );


        Server server = new Server(
                usersModule.createService(),
                gamesModule.createService(),
                scoresModule.createService());
        server.start();

    }

}
