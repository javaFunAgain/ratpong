package pl.setblack.pongi.scores;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.annotation.concurrent.Immutable;
import java.io.Serializable;

/**
 * Created by jarek on 2/5/17.
 */
@JsonDeserialize
@Immutable
public class UserScore implements Serializable{
    private static final long serialVersionUID = 1L;
    public final String userId;
    public final int totalScore;
    public final int gamesWon;
    public final int gamesLost;
    public final int gamesPlayed;
    public final int pointsScored;
    public final int pointsLost;

    @JsonCreator
    public UserScore(String userId, int totalScore, int gamesWon, int gamesLost, int gamesPlayed, int pointsScored, int pointsLost) {
        this.userId = userId;
        this.totalScore = totalScore;
        this.gamesWon = gamesWon;
        this.gamesLost = gamesLost;
        this.gamesPlayed = gamesPlayed;
        this.pointsScored = pointsScored;
        this.pointsLost = pointsLost;
    }

    public static UserScore emptyFor(String userId) {
        return new UserScore(
                userId,
                0,
                0,
                0,
                0,
                0,
                0);
    }

    public UserScore add(ScoreRecord rec) {
        final int newScore = this.totalScore + rec.result.score;
        final int newWon = this.gamesWon +
                (rec.result == GameResult.WON ?  1: 0);
        final int newLost = this.gamesLost +
                (rec.result == GameResult.LOST ?  1: 0);
        final int newPlayed = this.gamesPlayed + 1;
        final int newPointsScored = this.pointsScored + rec.playerScored;
        final int newPointsLost = this.pointsLost + rec.opponentScore;
        return new UserScore(
                this.userId,
                newScore,
                newWon,
                newLost,
                newPlayed,
                newPointsScored,
                newPointsLost);
    }
}
