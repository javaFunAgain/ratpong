package pl.setblack.pongi.games.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import javaslang.Tuple2;
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
    public final Players players;
    public final long updateTime;


    @JsonCreator
    public GameState(
            final Ball ball,
            final Players players,
            final long updateTime) {
        this.ball = ball;
        this.players = players;
        this.updateTime = updateTime;
        this.phase = players.phaseFromScore();
    }


    public static Option<GameState> startFrom(
            GameInfo info,
            long startTime,
            final Random rnd) {

        if (info.players.size() == 2) {
            final Ball ball = new Ball(0.5f, 0.5f);
            final Player player1 = new Player(0, info.players.get(0), Paddle.createPaddleForPlayer(1));
            final Player player2 = new Player(0, info.players.get(1), Paddle.createPaddleForPlayer(2));
            return Option.some(new GameState(ball, Players.of(player1, player2), startTime).start(startTime, rnd));
        } else {
            return Option.none();
        }
    }


    private GameState start(long startTime, final Random rnd) {

        return new GameState(
                Ball.randomDirection(rnd),
                this.players,
                startTime);

    }

    public GameState push(long newTime, final Random rnd) {
        if (this.phase == GamePhase.STARTED) {
            long diff = newTime - this.updateTime;
            float scale = diff / GameParams.RELATIVE_SPEED;
            final Tuple2<Ball, Players> newPositions = this.ball
                    .move(scale)
                    .bounce(this.players, rnd);
            final Function<Player, Player> movePaddle = player -> player.movePaddle(diff);
            final Players newPlayers = newPositions._2.map(movePaddle);

            return new GameState(newPositions._1, newPlayers, newTime);
        } else {
            return this;
        }

    }


    public GameState playerMovingTo(String userId, float targetY) {
        final Function<Player, Player> movePaddle = player -> player.makeMoving(userId, targetY);
        final Players newPlayers = this.players.map(movePaddle);
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
