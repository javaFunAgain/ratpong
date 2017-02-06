package pl.setblack.pongi.games.repo;

import javaslang.collection.Seq;
import javaslang.control.Option;
import pl.setblack.pongi.games.api.GameInfo;
import pl.setblack.pongi.games.api.GameState;

/**
 * Created by jarek on 2/2/17.
 */
public interface GamesRepository {
    Option<GameInfo> createGame(String uuid, String name, String userId);

    Seq<GameInfo> listGames();

    Option<GameState> startNewGame(GameInfo info, long time);

    Option<GameState> joinGame(String uuid, String userId, long time);

    Option<GameState> getGame(String uuid);

    boolean movePaddle(String gameId, String userId, float targetY);

    Option<GameState> push(String gameUUID, long time);

    void removeGame(String gameUUID);

}
