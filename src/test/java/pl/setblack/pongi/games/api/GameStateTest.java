package pl.setblack.pongi.games.api;

import javaslang.Tuple;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by jarek on 4/13/17.
 */
class GameStateTest {
    @Test
    public void shouldMoveBallOnPush() {

    }


    private GameState createGameState(
            final Player player1, final Player player2, final Ball ball ) {
        final GameState state = new GameState(ball, Tuple.of(player1, player2), 0);
        return state;
    }

    private GameState createGameState( final float player1pos, final float player2pos,
                                       Ball ball) {
        throw new UnsupportedOperationException();
    }

}