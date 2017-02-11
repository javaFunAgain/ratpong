package pl.setblack.pongi.scores.repo;

import javaslang.collection.List;
import javaslang.control.Option;
import pl.setblack.pongi.scores.ScoreRecord;
import pl.setblack.pongi.scores.UserScore;
import pl.setblack.pongi.users.api.RegUserStatus;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by jarek on 2/5/17.
 */
public class ScoresRepositoryNonBlocking {

    private final Executor writesExecutor = Executors.newSingleThreadExecutor();

    private final  ScoresRepository repository;

    public ScoresRepositoryNonBlocking(ScoresRepository repository) {
        this.repository = repository;
    }

    public void registerScore(List<ScoreRecord> rec){
        writesExecutor.execute( ()-> repository.registerScore(rec) );
    }

    public CompletionStage<Option<UserScore>> getUserScore(String userId) {
       throw new UnsupportedOperationException();
    }

    public CompletionStage<List<UserScore>> getTopScores(final int limit) {
        final CompletableFuture<List<UserScore>> result = new CompletableFuture<>();
        writesExecutor.execute(() -> {
            result.complete(this.repository.getTopScores(limit));
        });
        return result;
    }
}
