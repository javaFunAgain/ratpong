package pl.setblack.pongi.games.repo;

import javaslang.collection.Seq;
import javaslang.control.Option;
import pl.setblack.airomem.core.Persistent;
import pl.setblack.pongi.games.api.GameInfo;
import pl.setblack.pongi.games.api.GameState;

import java.nio.file.Paths;
import java.time.Clock;

public class GamesRepoES implements GamesRepository {

    final Persistent<GamesRepositoryInMemory> persistent;

    public GamesRepoES(final Clock clock) {
        persistent = Persistent.loadOptional(
                Paths.get("airomem/games"),
                () -> new GamesRepositoryInMemory(clock)
        );
    }

    @Override
    public Option<GameInfo> createGame(String uuid, String name, String userId) {
        return persistent.executeAndQuery(rep -> rep.createGame(uuid, name, userId));
    }

    @Override
    public Seq<GameInfo> listGames() {
        return persistent.query(rep -> rep.listGames());
    }


    @Override
    public Option<GameState> joinGame(String uuid, String userId) {
        return persistent.executeAndQuery(rep -> rep.joinGame(uuid, userId));
    }

    @Override
    public Option<GameState> getGame(String uuid) {
        return persistent.query(rep -> rep.getGame(uuid));
    }

    @Override
    public boolean movePaddle(String gameId, String userId, float targetY) {
        return persistent.executeAndQuery(rep -> rep.movePaddle(gameId, userId, targetY));
    }

    @Override
    public Option<GameState> push(String gameUUID) {
        return persistent.executeAndQuery(rep -> rep.push(gameUUID));
    }

    @Override
    public void removeGame(final String gameUUID) {
        persistent.execute(rep -> rep.removeGame(gameUUID));
    }
}
