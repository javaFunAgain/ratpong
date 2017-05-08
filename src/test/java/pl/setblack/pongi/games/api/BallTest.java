package pl.setblack.pongi.games.api;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.core.Is.is;

class BallTest {

    @Test
    public void ballShouldMove() {
        final Ball ball = new Ball(0.5f, 0.5f, Vector2D.fromAngle(Math.PI / 2, 0.01f));
        final Ball moved = ball.move(0.1f);
        assertThat(ball.x, is(lessThan(moved.x)));
    }

    @Test
    public void ballShouldBonuceFromTop() {
        final Ball ball = new Ball(0.5f, 1f, Vector2D.fromAngle(0, 0.01f));
        Random rnd = new Random(42);
        final Ball bounced = ball.move(1f).bounce(createPlayers(), rnd)._1;
        assertThat(bounced.speed.y, is(lessThan(0f)));
    }


    private Players createPlayers() {
        return Players.of(
                new Player(0, "player1", Paddle.createPaddleForPlayer(1)),
                new Player(0, "player2", Paddle.createPaddleForPlayer(2))
        );
    }

}