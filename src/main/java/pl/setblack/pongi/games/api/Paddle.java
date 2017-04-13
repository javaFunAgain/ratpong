package pl.setblack.pongi.games.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.annotation.concurrent.Immutable;

/**
 * Created by jarek on 1/23/17.
 */
@Immutable
@JsonDeserialize
public class Paddle extends GameObject {
    private static final long serialVersionUID = 1L;
    public final float targetY;
    @JsonCreator
    public Paddle(float x, float y, float targetY) {
        super(x, y);
        this.targetY = targetY;
    }

    Paddle(float x, float y) {
        this(x,y,y);
    }

    Paddle paddleMove(long timeDiff) {
        final float distanceToTarget = targetY - y;
        final float direction = Math.signum(distanceToTarget);
        final float maxMove = timeDiff/ 5000.0f;
        final float realMove = Math.min(Math.abs(distanceToTarget),maxMove);
        final float newY = this.y + (direction)*realMove;
        return new Paddle(this.x, newY, targetY);
    }

    Paddle movingTo(float targetY) {
        return new Paddle(this.x,this.y,targetY);
    }

    static Paddle createPaddleForPlayer(int playerNr) {
        final float x = (float)playerNr -1;
        return  new Paddle(x, 0.5f);
    }
}
