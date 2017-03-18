package pl.setblack.pongi.games;

import pl.setblack.pongi.games.repo.GamesRepoES;
import pl.setblack.pongi.games.repo.GamesRepository;
import pl.setblack.pongi.scores.repo.ScoresRepositoryProcessor;
import pl.setblack.pongi.users.repo.SessionsRepo;

import java.time.Clock;

public class GamesModule {
    private final GamesRepository gamesRepo;

    private final SessionsRepo sessionsRepo;

    private final ScoresRepositoryProcessor scoresRepo;


    public GamesModule(GamesRepository gamesRepo,
                       SessionsRepo sessionsRepo,
                       ScoresRepositoryProcessor scoresRepo) {
        this.gamesRepo = gamesRepo;
        this.sessionsRepo = sessionsRepo;
        this.scoresRepo = scoresRepo;
    }

    public GamesModule(
            final Clock clock,
            final SessionsRepo sessionsRepo,
            final ScoresRepositoryProcessor scoresRepo) {
        this (
                new GamesRepoES(clock),
                sessionsRepo,
                scoresRepo);
    }


    public GamesService createService() {
        return new GamesService(gamesRepo,sessionsRepo, scoresRepo);
    }

}
