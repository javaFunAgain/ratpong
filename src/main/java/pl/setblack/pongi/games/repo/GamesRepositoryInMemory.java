package pl.setblack.pongi.games.repo;

import javaslang.collection.HashMap;
import javaslang.collection.Seq;
import javaslang.control.Option;
import javaslang.control.Try;
import pl.setblack.pongi.games.api.GameInfo;
import pl.setblack.pongi.games.api.GameState;

/**
 * Created by jarek on 2/1/17.
 */
public class GamesRepositoryInMemory implements GamesRepository {
    private HashMap<String, GameInfo> allGamesInfo = HashMap.empty();

    private HashMap<String, GameState> allGamesState = HashMap.empty();


    @Override
    public Try<GameInfo> createGame(final String uuid, final String name, final String userId) {
        if (allGamesInfo.containsKey(uuid)) {
            return Try.failure(new IllegalArgumentException("game exists"));
        } else {
            final GameInfo newGame = new GameInfo(name, uuid, userId);
            allGamesInfo = allGamesInfo.put(uuid, newGame);
            return Try.success(newGame);
        }
    }
    @Override
    public Seq<GameInfo> listGames() {
        return allGamesInfo.values();
    }
    @Override
    public Option<GameState> startNewGame(final GameInfo info, long time) {
        final Option<GameState> state = GameState.startFrom(info, time);
        state.forEach( s -> this.allGamesState = this.allGamesState.put(info.uuid, s));
        return state;
    }
    @Override
    public Try<GameState> joinGame(final String uuid, final String userId, final long time) {
        return this.allGamesInfo.get(uuid)
                .flatMap(g -> g.withPlayer(userId))
                .flatMap(g -> {
                    this.allGamesInfo.put(uuid, g);
                    return startNewGame(g, time);
                })
                .toTry(() -> new IllegalStateException("unable to join game"));
    }
    @Override
    public Option<GameState> getGame(final String uuid) {
        return this.allGamesState.get(uuid);
    }
    @Override
    public boolean movePaddle(final String uuid, final String userId ,final float targetY) {
        return this.getGame(uuid).map( g->g.playerMovingTo(userId, targetY)).
                map( g -> {
                    this.allGamesState  = this.allGamesState.put(uuid, g);
                    return true;
                }).getOrElse(false);

    }
}
