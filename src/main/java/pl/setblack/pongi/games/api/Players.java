package pl.setblack.pongi.games.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import javaslang.Function1;
import javaslang.collection.LinearSeq;
import javaslang.collection.List;

import javax.annotation.concurrent.Immutable;
import java.util.function.Function;


@Immutable
@JsonDeserialize
public class Players {
    public final Player player1;
    public final Player player2;

    @JsonCreator
    public Players(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    static Players of(Player player1, Player player2) {
        return new Players(player1, player2);
    }

    GamePhase phaseFromScore() {
        if (allPlayers().filter(p -> p.score >= 15).isEmpty()) {
            return GamePhase.STARTED;
        } else {
            return GamePhase.ENDED;
        }
    }


    private LinearSeq<Player> allPlayers() {
        return List.of(player1, player2);
    }

    Players mapPlayer(final int index, Function1<Player, Player> func) {
        switch (index) {
            case 1:
                return Players.of(func.apply(player1), player2);
            case 2:
                return Players.of(player1, func.apply(player2));
            default:
                throw new IllegalArgumentException("No player:" + index);
        }
    }

    public Players map(Function<Player, Player> func) {
        return Players.of(func.apply(player1), func.apply(player2));
    }
}
