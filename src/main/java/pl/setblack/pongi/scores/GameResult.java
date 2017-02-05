package pl.setblack.pongi.scores;

/**
 * Created by jarek on 2/5/17.
 */
public enum GameResult {
    WON(ScoreRules.SCORE_FOR_WIN),
    LOST(ScoreRules.SCORE_FOR_LOSS),
    FILE_NOT_FOUND(ScoreRules.SCORE_FOR_LOSS) // could not resist it - see http://thedailywtf.com/articles/What_Is_Truth_0x3f_
    ;
    public final int score;

    GameResult(int score) {
        this.score = score;
    }
}
