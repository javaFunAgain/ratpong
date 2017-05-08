package pl.setblack.pongi.scores.repo;

import javaslang.collection.List;
import javaslang.control.Option;
import pl.setblack.pongi.scores.ScoreRecord;
import pl.setblack.pongi.scores.UserScore;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by jarek on 2/5/17.
 */
public class ScoresRepositoryProcessor {
    private final Executor writesExecutor = Executors.newSingleThreadExecutor();

    private final ScoresRepository repository;

    public ScoresRepositoryProcessor(ScoresRepository repository) {
        this.repository = repository;
    }

    public void registerScore(List<ScoreRecord> rec) {
        this.writesExecutor.execute(() -> repository.registerScore(rec));
    }

    public CompletionStage<Option<UserScore>> getUserScore(String userId) {
        return CompletableFuture.completedFuture(repository.getUserScore(userId));
    }

    public CompletionStage<List<UserScore>> getTopScores(final int limit) {
        return CompletableFuture.completedFuture(repository.getTopScores(limit));
    }
}
