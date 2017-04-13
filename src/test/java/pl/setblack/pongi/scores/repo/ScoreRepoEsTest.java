package pl.setblack.pongi.scores.repo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import pl.setblack.pongi.AiromemHelper;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by jarek on 4/12/17.
 */
public class ScoreRepoEsTest extends ScoresRepositoryBase {
    private final Path testRepoPath = Paths.get("target/airomem/test");
    private ScoreRepositoryES persistentRepo;
    @BeforeEach
    protected void createRepo () {
        this.persistentRepo =   new ScoreRepositoryES(testRepoPath);
        this.testee =  this.persistentRepo;

    }

    @AfterEach
    public void deleteRepo() throws IOException {
        this.persistentRepo.close();
        AiromemHelper.clearFolder(testRepoPath);
    }
}
