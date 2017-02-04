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
public class GamesRepositoryNonBlocking {

    private final GamesRepository gamesRepo;

    private final Executor writesExecutor = Executors.newSingleThreadExecutor();

    public GamesRepositoryNonBlocking(GamesRepository gamesRepo) {
        this.gamesRepo = gamesRepo;
    }

    public CompletionStage<Seq<GameInfo>> listGames() {
        return CompletableFuture.completedFuture(gamesRepo.listGames());
    }


    public CompletionStage<Option<GameInfo>> createGame(String uuid, String name, String userId) {
        return callLongOneOperation(() -> gamesRepo.createGame(uuid, name, userId));
    }


    public CompletionStage<Option<GameState>> joinGame(final String uuid, final String userId, final long time) {
        return callLongOneOperation(() -> gamesRepo.joinGame(uuid, userId, time));
    }

    private <T> CompletionStage<T> callLongOneOperation(final Supplier<T> producer) {
        final CompletableFuture<T> promise = new CompletableFuture<>();
        writesExecutor.execute(() -> {
            promise.complete(producer.get());
        });
        return promise;
    }

    public CompletionStage<Option<GameState>> push(final String gameUUID,
                                                   final long time) {
        return callLongOneOperation( ()->gamesRepo.push(gameUUID, time));
    }
}
