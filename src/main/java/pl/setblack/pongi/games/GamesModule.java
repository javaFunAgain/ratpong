package pl.setblack.pongi.games;

import pl.setblack.pongi.games.repo.GamesRepoES;
import pl.setblack.pongi.games.repo.GamesRepository;
import pl.setblack.pongi.scores.repo.ScoresRepositoryNonBlocking;
import pl.setblack.pongi.users.repo.SessionsRepo;

import java.time.Clock;

public class GamesModule {
    private final GamesRepository gamesRepo;

    private final SessionsRepo sessionsRepo;

    private final ScoresRepositoryNonBlocking scoresRepo;


    public GamesModule(GamesRepository gamesRepo,
                       SessionsRepo sessionsRepo,
                       ScoresRepositoryNonBlocking scoresRepo) {
        this.gamesRepo = gamesRepo;
        this.sessionsRepo = sessionsRepo;
        this.scoresRepo = scoresRepo;
    }

    public GamesModule(
            final Clock clock,
            final SessionsRepo sessionsRepo,
            final ScoresRepositoryNonBlocking scoresRepo) {
        this (
                new GamesRepoES(clock),
                sessionsRepo,
                scoresRepo);
    }


    public GamesService createService() {
        return new GamesService(gamesRepo,sessionsRepo, scoresRepo);
    }

}
