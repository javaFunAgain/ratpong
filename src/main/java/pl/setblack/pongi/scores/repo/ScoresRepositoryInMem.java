package pl.setblack.pongi.scores.repo;

import javaslang.collection.HashMap;
import javaslang.collection.List;
import javaslang.collection.PriorityQueue;
import javaslang.control.Option;
import pl.setblack.pongi.scores.ScoreRecord;
import pl.setblack.pongi.scores.UserScore;

import java.io.Serializable;
import java.util.Comparator;

public class ScoresRepositoryInMem implements ScoresRepository, Serializable{

    private volatile HashMap<String, UserScore> scores = HashMap.empty();


    public ScoresRepositoryInMem() {
    }


    @Override
    public void registerScore(List<ScoreRecord> rec) {
        rec.forEach( singleRecord -> {
            scores
                    .get(singleRecord.userId)
                    .orElse( Option.some(UserScore.emptyFor(singleRecord.userId)))
                    .forEach( oldRecord ->
                            scores = scores.put(singleRecord.userId, oldRecord.add(singleRecord)) );
            }
        );

    }


    @Override
    public Option<UserScore> getUserScore(String userId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<UserScore> getTopScores(int limit) {
         return this.scores.values()
                 .sortBy( score -> score.totalScore )
                 .reverse()
                 .take(limit)
                 .toList();
    }



}
