package pl.setblack.pongi.games.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import javaslang.Tuple;
import javaslang.Tuple2;
import javaslang.collection.List;
import javaslang.control.Option;

import javax.annotation.concurrent.Immutable;
import java.io.Serializable;
import java.util.Random;
import java.util.function.Function;

@Immutable
@JsonDeserialize
public class GameState implements Serializable {
    private static final long serialVersionUID = 1L;
    public final GamePhase phase;
    public final Ball ball;
    public final Tuple2<Player, Player> players;
    public final long updateTime;


    @JsonCreator
    public GameState(Ball ball, Tuple2<Player, Player> players, long updateTime) {
        this.ball = ball;
        this.players = players;
        this.updateTime = updateTime;
        this.phase = phaseFromScore(players);
    }

    private GamePhase phaseFromScore(Tuple2<Player,Player> players) {
        if ( List.of(players._1, players._2).filter(p -> p.score>=15).isEmpty()) {
            return GamePhase.STARTED;
        } else {
            return GamePhase.ENDED;
        }
    }

    public static Option<GameState> startFrom(
            GameInfo info,
            long startTime,
            final Random rnd) {

        if (info.players.size() == 2) {
            final Ball ball = new Ball(0.5f, 0.5f);
            final Player player1 = new Player(0, info.players.get(0), createPaddle(1));
            final Player player2 = new Player(0, info.players.get(1), createPaddle(2));
            return Option.some(new GameState(ball, Tuple.of(player1, player2), startTime).start(startTime, rnd));
        } else {
            return Option.none();
        }
    }

    private static Paddle createPaddle(int playerNr) {
        final float x = (float)playerNr -1;
        return  new Paddle(x, 0.5f);
    }

    public GameState start(long startTime, final Random rnd) {

        return new GameState(
                Ball.random(rnd),
                this.players,
                startTime);

    }

    public GameState push(long newTime, final Random rnd) {
        if ( this.phase == GamePhase.STARTED) {
            long diff = newTime - this.updateTime;
            float scale = diff / 5.0f;
            final Tuple2<Ball, Tuple2<Player,Player>> newPositions = this.ball
                    .move(scale)
                    .bounce(this.players,rnd );
            final Function<Player, Player> movePaddle = player -> player.movePaddle(diff);
            final Tuple2<Player, Player> newPlayers = newPositions._2.map(movePaddle, movePaddle);

            return new GameState(newPositions._1, newPlayers, newTime);
        } else {
            return this;
        }

    }


    public GameState playerMovingTo(String userId, float targetY) {
        final Function<Player, Player> movePaddle = player -> player.makeMoving(userId, targetY);
        final Tuple2<Player, Player> newPlayers = this.players.map(movePaddle, movePaddle);
        return new GameState(this.ball, newPlayers, this.updateTime);
    }

    @Override
    public String toString() {
        return "GameState{" +
                "ball=" + ball +
                ", players=" + players +
                ", updateTime=" + updateTime +
                '}';
    }
}
