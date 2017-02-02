package pl.setblack.pongi.users.repo;

import pl.setblack.pongi.users.api.RegUserStatus;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by jarek on 1/30/17.
 */
public class UsersRepositoryNonBlocking {
    private final UsersRepository usersRepository;

    private final Executor writesExecutor = Executors.newSingleThreadExecutor();


    public UsersRepositoryNonBlocking(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public CompletionStage<RegUserStatus> addUser(final String login, final String pass) {
        final CompletableFuture<RegUserStatus> result = new CompletableFuture<>();
        writesExecutor.execute( ()-> {
            result.complete(this.usersRepository.addUser(login, pass));
        });
        return result;
    }

    public boolean login(final String login, final String pass) {
        return this.usersRepository.login(login, pass);
    }
}
