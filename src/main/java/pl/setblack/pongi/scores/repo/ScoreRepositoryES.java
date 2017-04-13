package pl.setblack.pongi.scores.repo;

import javaslang.collection.List;
import javaslang.control.Option;
import pl.setblack.airomem.core.Persistent;
import pl.setblack.airomem.core.VoidCommand;
import pl.setblack.pongi.scores.ScoreRecord;
import pl.setblack.pongi.scores.UserScore;
import pl.setblack.pongi.users.repo.UsersRepositoryInMemory;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by jarek on 2/11/17.
 */
public class ScoreRepositoryES implements ScoresRepository {

    private final Persistent<ScoresRepositoryInMem> perstenceController;

    public ScoreRepositoryES(Path where) {
        this.perstenceController = Persistent.loadOptional(
                where, ()-> new ScoresRepositoryInMem());
    }

    @Override
    public List<UserScore> getTopScores(int limit) {
        return this.perstenceController.query( scoreRepo -> scoreRepo.getTopScores(limit));
    }

    @Override
    public void registerScore(List<ScoreRecord> rec) {

            this.perstenceController.execute( scoreRepo -> scoreRepo.registerScore(rec) );
    }

    @Override
    public Option<UserScore> getUserScore(String userId) {
         return this.perstenceController.query( scoreRepo -> scoreRepo.getUserScore(userId));
    }

    public void close() {
        this.perstenceController.close();
    }

}
