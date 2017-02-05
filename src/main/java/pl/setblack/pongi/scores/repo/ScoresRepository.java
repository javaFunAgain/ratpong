package pl.setblack.pongi.scores.repo;

import javaslang.collection.List;
import javaslang.control.Option;
import pl.setblack.pongi.scores.ScoreRecord;
import pl.setblack.pongi.scores.UserScore;

/**
 * Created by jarek on 2/5/17.
 */
public interface ScoresRepository {

        void registerScore(List<ScoreRecord> rec);

        Option<UserScore> getUserScore(String userId);

        List<UserScore> getTopScores(final int limit);
}
