package pl.setblack.pongi.games.repo;

import javaslang.collection.HashMap;
import javaslang.collection.Map;
import javaslang.collection.Seq;
import javaslang.control.Option;
import pl.setblack.pongi.games.api.GameInfo;
import pl.setblack.pongi.games.api.GameState;

import java.io.Serializable;
import java.time.Clock;
import java.util.Random;

public class GamesRepositoryInMemory implements GamesRepository, Serializable {
    private static final long serialVersionUID = 1L;
    private volatile Map<String, GameInfo> allGamesInfo = HashMap.empty();

    private volatile Map<String, GameState> allGamesState = HashMap.empty();

    private final Random random = new Random(137);

    private final Clock clock;

    public GamesRepositoryInMemory(Clock clock) {
        this.clock = clock;
    }

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

    public Option<GameState> startNewGame(final GameInfo info) {
        final Option<GameState> currentState =
                this.allGamesState.get(info.uuid);
        return currentState.orElse(() -> {
            Option<GameState> newState = GameState.startFrom(info, this.clock.millis(), this.random);
            newState.forEach(s -> this.allGamesState = this.allGamesState.put(info.uuid, s));
            return newState;
        });

    }

    @Override
    public Option<GameState> joinGame(final String uuid, final String userId) {
        return this.allGamesInfo.get(uuid)
                .flatMap(g -> g.withPlayer(userId))
                .flatMap(g -> {
                    this.allGamesInfo = this.allGamesInfo.put(uuid, g);

                    return startNewGame(g);
                });
    }

    @Override
    public Option<GameState> getGame(final String uuid) {
        return this.allGamesState.get(uuid);
    }

    @Override
    public boolean movePaddle(final String gameId, final String userId, final float targetY) {
        return this.getGame(gameId).map(g -> g.playerMovingTo(userId, targetY)).
                map(g -> {
                    this.allGamesState = this.allGamesState.put(gameId, g);
                    return true;
                }).getOrElse(false);

    }

    @Override
    public Option<GameState> push(String gameUUID) {
        final Option<GameState> gameOpt = this.allGamesState.get(gameUUID);
        return gameOpt.map(game -> {
            final GameState newState = game.push(this.clock.millis(), this.random);
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
