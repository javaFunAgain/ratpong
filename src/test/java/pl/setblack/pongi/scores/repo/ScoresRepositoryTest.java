package pl.setblack.pongi.scores.repo;

import javaslang.collection.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.setblack.pongi.scores.GameResult;
import pl.setblack.pongi.scores.ScoreRecord;
import pl.setblack.pongi.scores.ScoreRules;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by jarek on 2/5/17.
 */
class ScoresRepositoryTest {


    private ScoresRepository testee;

    @BeforeEach
    protected void createRepo () {
        testee =   new ScoresRepositoryInMem();
    }

    @Test
    public void shouldCollectSingleWinUserScore() {
        final ScoreRecord singleGameResult = singleWin("aa");
        testee.registerScore(List.of(singleGameResult));

        assertThat( testee.getUserScore("aa").get().gamesWon, is (equalTo(1)) );
    }
    @Test
    public void shouldCalculateScoreForWin() {
        final ScoreRecord singleGameResult = singleWin("aa");
        testee.registerScore(List.of(singleGameResult));

        assertThat( testee.getUserScore("aa").get().totalScore, is (equalTo(ScoreRules.SCORE_FOR_WIN)) );
    }

    @Test
    public void shouldCalculatePointsForLosses() {
        final ScoreRecord singleGameResult = singleLoss("aa");
        final ScoreRecord secondGameResult = singleLoss("aa");
        testee.registerScore(List.of(singleGameResult));
        testee.registerScore(List.of(secondGameResult));

        assertThat( testee.getUserScore("aa").get().pointsScored, is (equalTo(10)) );
    }

    @Test
    public void shouldSelectWinnerAsFirst() {
        final List<ScoreRecord> game = makeGameScore("jacek", "placek");
        testee.registerScore(game);

        assertThat( testee.getTopScores(1).head().userId, is (equalTo("jacek")) );
    }

    @Test
    public void shouldSelectTop5() {
        recordTestGames();
        assertThat( testee.getTopScores(5).map( score -> score.userId),
                is (equalTo(List.of("jacek0", "jacek1", "jacek2", "jacek3", "jacek4")) ));
    }

    @Test
    public void shouldReturnOnly5Elements() {
        recordTestGames();
        assertThat( testee.getTopScores(5).size(),
                is (equalTo(5) ));
    }

    private void recordTestGames() {
        for ( int j =0 ; j < 120 ; j++) {
            for (int i = 0; i < j; i++) {
                final List<ScoreRecord> game = makeGameScore("jacek" + i, "placek" + i);
                testee.registerScore(game);
            }
        }
    }

    private List<ScoreRecord> makeGameScore(final String winner, final String loser) {
        return List.of( singleWin(winner), singleLoss(loser));
    }

    private ScoreRecord singleWin(final String userId) {
        return new ScoreRecord(userId,
                GameResult.WON,
                15,
                5,
                "game1"
        );
    }

    private ScoreRecord singleLoss(final String userId) {
        return new ScoreRecord(userId,
                GameResult.LOST,
                5,
                15,
                "game1"
        );
    }


}