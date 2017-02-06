package pl.setblack.pongi.games.repo;

import javaslang.collection.HashMap;
import javaslang.collection.Seq;
import javaslang.control.Option;
import pl.setblack.pongi.games.api.GameInfo;
import pl.setblack.pongi.games.api.GameState;

import java.io.Serializable;
import java.util.Random;

/**
 * Created by jarek on 2/1/17.
 */
public class GamesRepositoryInMemory implements GamesRepository, Serializable {
    private static final long serialVersionUID = 1L;
    private volatile HashMap<String, GameInfo> allGamesInfo = HashMap.empty();

    private volatile HashMap<String, GameState> allGamesState = HashMap.empty();

    private final Random random = new Random(137);

    @Override
    public Option<GameInfo> createGame(final String uuid, final String name, final String userId) {
        if (allGamesInfo.containsKey(uuid)) {
            return Option.none();
        } else {
            final GameInfo newGame = new GameInfo(name, uuid, userId);
            allGamesInfo = allGamesInfo.put(uuid, newGame);
            return Option.some(newGame);
        }
    }
    @Override
    public Seq<GameInfo> listGames() {
        return allGamesInfo.values();
    }

    @Override
    public Option<GameState> startNewGame(final GameInfo info, long time) {
        final Option<GameState> currentState =
                this.allGamesState.get(info.uuid);
        return currentState.orElse(() -> {
            Option<GameState> newState = GameState.startFrom(info, time, this.random);
            newState.forEach( s -> this.allGamesState = this.allGamesState.put(info.uuid, s));
            return newState;
        });

    }
    @Override
    public Option<GameState> joinGame(final String uuid, final String userId, final long time) {
        return this.allGamesInfo.get(uuid)
                .flatMap(g -> g.withPlayer(userId))
                .flatMap(g -> {
                    this.allGamesInfo = this.allGamesInfo.put(uuid, g);

                    return startNewGame(g, time);
                });
    }
    @Override
    public Option<GameState> getGame(final String uuid) {
        return this.allGamesState.get(uuid);
    }

    @Override
    public boolean movePaddle(final String gameId, final String userId ,final float targetY) {
        return this.getGame(gameId).map( g->g.playerMovingTo(userId, targetY)).
                map( g -> {
                    this.allGamesState  = this.allGamesState.put(gameId, g);
                    return true;
                }).getOrElse(false);

    }

    @Override
    public Option<GameState> push(String gameUUID, long time) {
        final Option<GameState> gameOpt = this.allGamesState.get( gameUUID);
        return gameOpt.map( game -> {
            final GameState newState= game.push(time, this.random);
            this.allGamesState = this.allGamesState.put(gameUUID, newState);
            return newState;
        });
    }

    @Override
    public void removeGame(String gameUUID) {
        this.allGamesInfo = this.allGamesInfo.remove(gameUUID);
        this.allGamesState = this.allGamesState.remove(gameUUID);
    }
}
