package pl.setblack.pongi.games.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import javaslang.collection.List;
import javaslang.control.Option;

import javax.annotation.concurrent.Immutable;


@Immutable
@JsonDeserialize
public class GameInfo {
    public final String name;

    public final String uuid;

    public final List<String> players;

    @JsonCreator
    public GameInfo(String name, String uuid, List<String> players) {
        this.name = name;
        this.uuid = uuid;
        this.players = players;
    }

    public GameInfo(String name, String uuid, String player1) {
        this(name,uuid, List.of(player1));
    }

    public Option<GameInfo> withPlayer(String userId) {
        if ( this.players.contains(userId)) {
            return Option.some(this);
        } else if ( this.players.size() <=1 ){
            return  Option.some( new GameInfo(this.name, this.uuid, this.players.append(userId)));
        } else {
            return Option.none();
        }

    }

    @Override
    public String toString() {
        return "GameInfo{" +
                "name='" + name + '\'' +
                ", uuid='" + uuid + '\'' +
                ", players=" + players +
                '}';
    }
}
