package pl.setblack.pongi.games.api;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.Is.is;
class PlayerTest {

    @Test
    public void playerShouldBeSetAsMoving() {
        final Player player = new Player(0, "player1", Paddle.createPaddleForPlayer(1));
        final Player moving =player.makeMoving("player1" , 1f);
        assertThat(moving.paddle.targetY, is(equalTo(1f)));
    }

    @Test
    public void playerShouldMove() {
        final Player player = new Player(0, "player1", Paddle.createPaddleForPlayer(1));
        final Player moving =player.makeMoving("player1" , 1f);
        final Player moved  = moving.movePaddle(100);
        assertThat(moved.paddle.y, is(greaterThan(moving.paddle.y)));
    }

    @Test
    public void playerShouldScore() {
        final Player player = new Player(10, "player1", Paddle.createPaddleForPlayer(1));
        final Player scored =  player.score();
        assertThat(scored.score, is(equalTo(11)));
    }

}