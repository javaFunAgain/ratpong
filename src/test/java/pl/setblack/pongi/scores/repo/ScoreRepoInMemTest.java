package pl.setblack.pongi.scores.repo;

import org.junit.jupiter.api.BeforeEach;

/**
 * Created by jarek on 4/12/17.
 */
public class ScoreRepoInMemTest extends ScoresRepositoryBase {
    @BeforeEach
    protected void createRepo () {
        testee =   new ScoresRepositoryInMem();
    }
}
