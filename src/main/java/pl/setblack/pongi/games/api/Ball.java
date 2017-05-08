package pl.setblack.pongi.games.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import javaslang.Tuple;
import javaslang.Tuple2;

import javax.annotation.concurrent.Immutable;
import java.util.Random;

/**
 * Created by jarek on 1/23/17.
 */
@Immutable
@JsonDeserialize
public class Ball extends GameObject {
    private static final long serialVersionUID = 1L;
    public final Vector2D speed;

    @JsonCreator
    public Ball(float x, float y, Vector2D speed) {
        super(x, y);
        this.speed = speed;
    }

     Ball(float x, float y) {
        this(x,y, new Vector2D(0f,0f));
    }


    static Ball randomDirection(final Random rnd) {
        final double randomAngleA = rnd.nextDouble()*Math.PI/2.0;
        final double randomAngleB = randomAngleA + Math.PI/4.0;
        final double randomAngle = (rnd.nextDouble() <0.5 )? randomAngleB : (randomAngleB + Math.PI);
        final Vector2D speed = Vector2D.fromAngle(randomAngle, 0.001);

        return new Ball(0.5f, 0.5f,speed);
    }

    Ball move(float scale) {
        return new Ball( this.x + speed.x*scale, this.y + speed.y*scale, this.speed);
    }

    //A piece of smart code in Players should reduce both methods code duplication
    private Tuple2<Ball, Players> bouncePlayer1(final Players players, final Random rnd) {
        if ( this.x < 0 && speed.x < 0) {
            if (isTouchingPaddle(players.player1.paddle, this.y)){
                return Tuple.of(new Ball(0f, this.y, this.speed.bounceX()), players);
            } else {
                return Tuple.of(Ball.randomDirection(rnd), players.mapPlayer(2, pl2->pl2.score()));
            }
        }
        return Tuple.of(this, players);
    }

    private Tuple2<Ball, Players> bouncePlayer2(final Players players, final Random rnd) {
        if ( this.x > 1.0f && speed.x > 0) {
            if (isTouchingPaddle(players.player2.paddle, this.y)){
                return Tuple.of( new Ball(1f, this.y, this.speed.bounceX()),players);
            } else {
                return Tuple.of(Ball.randomDirection(rnd), players.mapPlayer(1, pl1->pl1.score()));
            }
        }
        return Tuple.of(this, players);
    }

    private Tuple2<Ball, Players> bounceX(Players players, final Random rnd) {
        final Tuple2<Ball, Players> afterPlayer1 = bouncePlayer1(players, rnd);
        return afterPlayer1._1.bouncePlayer2(afterPlayer1._2,rnd);
    }

    private boolean isTouchingPaddle(Paddle paddle, float y) {
        return Math.abs(paddle.y - y) < 0.05f;
    }

    private Ball bounceY() {
        if ( this.y < 0 && speed.y < 0) {
            return new Ball(this.x, 0f, this.speed.bounceY());
        }
        if ( this.y > 1.0f && speed.y > 0) {
            return new Ball(this.x, 1.0f, this.speed.bounceY());
        }
        return this;
    }


    Tuple2<Ball,Players> bounce(Players players, final Random rnd) {
        return this.bounceY().bounceX(players, rnd);
    }
}

