package pl.setblack.pongi.games.repo;

import javaslang.collection.Seq;
import javaslang.control.Option;
import pl.setblack.pongi.games.api.GameInfo;
import pl.setblack.pongi.games.api.GameState;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

/**
 * Created by jarek on 2/2/17.
 */
public class GamesRepositoryProcessor {

    private final GamesRepository gamesRepo;

    private final Executor writesExecutor = Executors.newSingleThreadExecutor();

    public GamesRepositoryProcessor(GamesRepository gamesRepo) {
        this.gamesRepo = gamesRepo;
    }

    public CompletionStage<Seq<GameInfo>> listGames() {
        return CompletableFuture.completedFuture(gamesRepo.listGames());
    }


    public CompletionStage<Option<GameInfo>> createGame(String uuid, String name, String userId) {
        return callLongOneOperation(() -> gamesRepo.createGame(uuid, name, userId));
    }


    public CompletionStage<Option<GameState>> joinGame(final String uuid, final String userId) {
        return callLongOneOperation(() -> gamesRepo.joinGame(uuid, userId));
    }



    public CompletionStage<Option<GameState>> push(final String gameUUID) {
        return callLongOneOperation( ()->gamesRepo.push(gameUUID));
    }

    public CompletionStage<Boolean> movePaddle(String gameId, String userId, float targetY) {
        return callLongOneOperation( ()->gamesRepo.movePaddle(gameId, userId, targetY));
    }

    public void removeGame(final String gameUUID) {
        writesExecutor.execute(() -> this.gamesRepo.removeGame(gameUUID));
    }

    private <T> CompletionStage<T> callLongOneOperation(final Supplier<T> producer) {
        final CompletableFuture<T> promise = new CompletableFuture<>();
        writesExecutor.execute(() -> {
            promise.complete(producer.get());
        });
        return promise;
    }


}
