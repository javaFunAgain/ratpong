package pl.setblack.pongi.scores;

/**
 * Created by jarek on 2/5/17.
 */
public enum GameResult {
    WON(ScoreRules.SCORE_FOR_WIN),
    LOST(ScoreRules.SCORE_FOR_LOSS);

    public final int score;

    GameResult(int score) {
        this.score = score;
    }
}
