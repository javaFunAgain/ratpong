package pl.setblack.pongi.games.repo;

import javaslang.collection.Seq;
import javaslang.control.Option;
import pl.setblack.airomem.core.Persistent;
import pl.setblack.pongi.games.api.GameInfo;
import pl.setblack.pongi.games.api.GameState;

import java.nio.file.Paths;

/**
 * Created by jarek on 2/4/17.
 */
public class GamesRepoES implements GamesRepository {

    final Persistent<GamesRepositoryInMemory> persistent;

    public GamesRepoES() {
        persistent = Persistent.loadOptional(
                Paths.get("airomem/games"),
                ()->new GamesRepositoryInMemory()
        );
    }

    @Override
    public Option<GameInfo> createGame(String uuid, String name, String userId) {
        return persistent.executeAndQuery(rep -> rep.createGame(uuid, name, userId));
    }

    @Override
    public Seq<GameInfo> listGames() {
        return persistent.query( rep->rep.listGames());
    }

    @Override
    public Option<GameState> startNewGame(GameInfo info, long time) {
        return persistent.executeAndQuery(rep -> rep.startNewGame(info, time));
    }

    @Override
    public Option<GameState> joinGame(String uuid, String userId, long time) {
        return persistent.executeAndQuery(rep -> rep.joinGame(uuid, userId, time));
    }

    @Override
    public Option<GameState> getGame(String uuid) {
        return persistent.query( rep ->rep.getGame(uuid));
    }

    @Override
    public boolean movePaddle(String gameId, String userId, float targetY) {
        return persistent.executeAndQuery(rep->rep.movePaddle(gameId, userId, targetY));
    }

    @Override
    public Option<GameState> push(String gameUUID, long time) {
        return persistent.executeAndQuery(rep -> rep.push(gameUUID, time));
    }

    @Override
    public void removeGame(final String gameUUID) {
        persistent.execute(rep->rep.removeGame(gameUUID));
    }
}
