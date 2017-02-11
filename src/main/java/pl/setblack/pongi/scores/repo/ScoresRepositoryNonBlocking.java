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
public class ScoresRepositoryNonBlocking {



    public ScoresRepositoryNonBlocking(ScoresRepository repository) {

    }

    public void registerScore(List<ScoreRecord> rec){

    }

    public CompletionStage<Option<UserScore>> getUserScore(String userId) {
       throw new UnsupportedOperationException();
    }

    public CompletionStage<List<UserScore>> getTopScores(final int limit) {
        throw new UnsupportedOperationException();
    }
}
