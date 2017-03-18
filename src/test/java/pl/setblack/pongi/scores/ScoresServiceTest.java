package pl.setblack.pongi.scores;

import com.fasterxml.jackson.core.type.TypeReference;
import javaslang.Tuple;
import javaslang.Tuple3;
import javaslang.collection.List;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import pl.setblack.pongi.JSONMapping;
import pl.setblack.pongi.Server;
import pl.setblack.pongi.scores.repo.ScoresRepository;
import pl.setblack.pongi.scores.repo.ScoresRepositoryInMem;
import pl.setblack.pongi.scores.repo.ScoresRepositoryProcessor;

import ratpack.http.client.ReceivedResponse;
import ratpack.test.embed.EmbeddedApp;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.CoreMatchers.equalTo;

/**
 * Created by jarek on 2/6/17.
 */
class ScoresServiceTest {

    public static final ScoreRecord WIN = new ScoreRecord("a", GameResult.WON, 15, 5, "id");
    public static final ScoreRecord LOST = new ScoreRecord("a", GameResult.LOST, 5, 15, "id");
    private Tuple3<String, Integer, List<ScoreRecord>> emptyCase =
            Tuple.of("no scores", 0, List.empty());
    private Tuple3<String, Integer, List<ScoreRecord>> singleRecord =
            Tuple.of("single win record", 5,
                    List.of(WIN));
    private Tuple3<String, Integer, List<ScoreRecord>> winLost =
            Tuple.of(" win lost record", 5,
                    List.of(WIN,LOST));
    private Tuple3<String, Integer, List<ScoreRecord>> wonTwice =
            Tuple.of(" win win record", 10,
                    List.of(WIN,WIN));

    private Tuple3<String, Integer, List<ScoreRecord>> lostTwice =
            Tuple.of(" lost lost record", 0,
                    List.of(LOST,LOST));


    @TestFactory
    public Iterable<DynamicTest> testScores() {
        return List.of(
                emptyCase,
                singleRecord,
                winLost,
                wonTwice,
                lostTwice
                    )
                .map(
                        testData ->
                                DynamicTest.dynamicTest(testData._1,
                                        () -> testGivenCase(testData)
                                ));

    }


    private void testGivenCase(Tuple3<String, Integer, List<ScoreRecord>> testCase) {
        try {
            final ScoresRepository repository = new ScoresRepositoryInMem();
            ScoresRepositoryProcessor nonBlockingWrapper = new ScoresRepositoryProcessor(repository);
            final ScoresService testedService = new ScoresService(nonBlockingWrapper);
            repository.registerScore(testCase._3);
            EmbeddedApp.fromServer(
                    Server.createUnconfiguredServer(testedService.scores())
            )
                    .test(testHttpClient -> {
                        final ReceivedResponse resp = testHttpClient.get("api/scores");
                        assertThat(
                                parseScores(resp.getBody().getText()).headOption()
                                        .map( score->score.totalScore).getOrElse(0),
                                is(equalTo(testCase._2)));
                    });
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }


    private static List<UserScore> parseScores(String jsonString)
            throws IOException {
        return JSONMapping.getJsonMapping().readValue(jsonString, new TypeReference<List<UserScore>>() {
        });
    }

}