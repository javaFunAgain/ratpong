package pl.setblack.pongi.games.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import javaslang.Tuple2;

import javax.annotation.concurrent.Immutable;

/**
 * Created by jarek on 1/23/17.
 */
@Immutable
@JsonDeserialize
public class Ball extends GameObject {
    public final Vector2D speed;

    @JsonCreator
    public Ball(float x, float y, Vector2D speed) {
        super(x, y);
        this.speed = speed;
    }

    public Ball(float x, float y) {
        this(x,y, new Vector2D(0f,0f));
    }

    public Ball withSpeed( Vector2D newSpeed) {
        return new Ball(this.x,this.y, newSpeed);
    }

    public static Ball random() {
        final double randomAngleA = Math.random()*Math.PI/2.0;
        final double randomAngleB = randomAngleA + Math.PI/4.0;
        final double randomAngle = (Math.random() <0.5 )? randomAngleB : (randomAngleB + Math.PI);
        final Vector2D speed = Vector2D.fromAngle(randomAngle, 0.001);

        return new Ball(0.5f, 0.5f,speed);
    }

    public Ball move(float scale) {
        return new Ball( this.x + speed.x*scale, this.y + speed.y*scale, this.speed);
    }

    public Ball bounceX(Tuple2<Paddle, Paddle> paddles) {
        if ( this.x < 0 && speed.x < 0) {
            if (isClose(paddles._1, this.y)){
                return new Ball(0f, this.y, this.speed.bounceX());
            } else {
                return Ball.random();
            }

        }
        if ( this.x > 1.0f && speed.x > 0) {
            if (isClose(paddles._2, this.y)){
                return new Ball(1f, this.y, this.speed.bounceX());
            } else {
                return Ball.random();
            }

        }
        return this;
    }

    private boolean isClose(Paddle paddle, float y) {
        return Math.abs(paddle.y - y) < 0.05f;
    }

    public Ball bounceY() {
        if ( this.y < 0 && speed.y < 0) {
            return new Ball(this.x, 0f, this.speed.bounceY());
        }
        if ( this.y > 1.0f && speed.y > 0) {
            return new Ball(this.x, 1.0f, this.speed.bounceY());
        }
        return this;
    }


    public Ball bounce(Tuple2<Paddle,Paddle> paddles) {
        return this.bounceY().bounceX(paddles);
    }
}

