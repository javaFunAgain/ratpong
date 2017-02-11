package pl.setblack.pongi.scores.repo;

import javaslang.collection.HashMap;
import javaslang.collection.List;
import javaslang.collection.PriorityQueue;
import javaslang.control.Option;
import pl.setblack.pongi.scores.ScoreRecord;
import pl.setblack.pongi.scores.UserScore;

import java.util.Comparator;

public class ScoresRepositoryInMem implements ScoresRepository{


    public ScoresRepositoryInMem() {

    }


    @Override
    public void registerScore(List<ScoreRecord> rec) {
        throw new UnsupportedOperationException();
    }


    @Override
    public Option<UserScore> getUserScore(String userId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<UserScore> getTopScores(int limit) {
        throw new UnsupportedOperationException();
    }


}
