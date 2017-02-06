package pl.setblack.pongi.scores.repo;

import javaslang.collection.HashMap;
import javaslang.collection.List;
import javaslang.collection.PriorityQueue;
import javaslang.control.Option;
import pl.setblack.pongi.scores.ScoreRecord;
import pl.setblack.pongi.scores.UserScore;

import java.util.Comparator;

public class ScoresRepositoryInMem implements ScoresRepository{
    private volatile PriorityQueue<UserScore> bestScores;
    private volatile HashMap<String, UserScore> userScores;


    public ScoresRepositoryInMem() {
        this.bestScores = PriorityQueue.empty(new ScoreComparator());
        this.userScores = HashMap.empty();
    }


    @Override
    public void registerScore(List<ScoreRecord> rec) {
        rec.forEach(this::registerSingleScore);

    }

    private void registerSingleScore(final ScoreRecord rec) {
        final Option<UserScore> current = this.userScores.get(rec.userId);
        current.orElse( Option.some(
                UserScore.emptyFor(rec.userId)))
                .map ( score -> add( score, rec))
                .forEach( score -> storeUserScore( score));

    }

    private void storeUserScore(final UserScore score) {
        this.userScores = this.userScores.put(score.userId, score);
        this.bestScores = this.bestScores
                .removeAll(s -> s.userId.equals(score.userId))
                .enqueue(score);
    }

    private UserScore add(UserScore score, ScoreRecord rec) {
        return score.add(rec);
    }

    @Override
    public Option<UserScore> getUserScore(String userId) {
        return this.userScores.get(userId);
    }

    @Override
    public List<UserScore> getTopScores(int limit) {
        return this.bestScores.iterator().take(limit).toList();
    }

    private static final class ScoreComparator
            implements Comparator<UserScore> {
        @Override
        public int compare(UserScore o1, UserScore o2) {
            return o2.totalScore - o1.totalScore;
        }
    }
}
