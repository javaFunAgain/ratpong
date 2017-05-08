package pl.setblack.pongi.games.api;

import org.junit.jupiter.api.Test;


class GameStateTest {
    private static final float MINIMAL_DIST = 0.001f;

    @Test
    public void shouldMoveBallOnPush() {

    }


    private GameState createGameState(
            final Player player1, final Player player2, final Ball ball ) {
        final GameState state = new GameState(ball, Players.of(player1, player2), 0);
        return state;
    }

    private GameState createGameState( final float player1pos, final float player2pos,
                                       Ball ball) {
        return  createGameState(
                new Player( 0, "ply1",
                        movePaddleAllTheWay(Paddle.createPaddleForPlayer(1), player1pos)),
                new Player( 0, "ply2",
                        movePaddleAllTheWay(Paddle.createPaddleForPlayer(2), player2pos)),
                ball);

    }


    private Paddle movePaddleAllTheWay( Paddle paddle, float pos) {
        final Paddle p = paddle.movingTo(pos);
        if ( Math.abs(p.y - pos) < MINIMAL_DIST) {
               return p;
        } else {
            return movePaddleAllTheWay(p.paddleMove(1000), pos);
        }
    }
}