package pl.setblack.pongi.games.api;

import org.junit.jupiter.api.Test;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.core.Is.is;

class PaddleTest {
  @Test
    public void shouldMarkPaddleAsMoving() {
      final Paddle paddle = new Paddle(0f, 0.5f);
      final Paddle directed = paddle.movingTo(1f);
      assertThat(directed.targetY, is(equalTo(1f)));

  }

    @Test
    public void shouldMovePaddle() {
        final Paddle paddle = new Paddle(0f, 0.5f);
        final Paddle directed = paddle.movingTo(1f);
        final Paddle moving = directed.paddleMove(100);

        assertThat(directed.y, is(lessThan(moving.y)));

    }


}