package pl.setblack.pongi.games.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.annotation.concurrent.Immutable;
import java.io.Serializable;

@Immutable
@JsonDeserialize
public class Player implements Serializable{
    private static final long serialVersionUID = 1L;
    public final int score;
    public final String name;
    public final Paddle paddle;

    @JsonCreator
    public Player(int score, String name, Paddle paddle) {
        this.score = score;
        this.name = name;
        this.paddle = paddle;
    }

    Player movePaddle(long timeDiff) {
        return new Player(this.score, this.name, this.paddle.paddleMove(timeDiff));
    }

     Player makeMoving(String userId, float targetY) {
        return (this.name.equals(userId))
                ? new Player(this.score, this.name, this.paddle.movingTo(targetY))
                : this;
    }

    Player score() {
        return new Player(this.score+1, name, paddle);
    }
}
