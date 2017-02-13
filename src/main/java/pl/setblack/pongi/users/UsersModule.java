package pl.setblack.pongi.users;

import pl.setblack.pongi.users.api.Session;
import pl.setblack.pongi.users.repo.SessionsRepo;
import pl.setblack.pongi.users.repo.UsersRepoES;
import pl.setblack.pongi.users.repo.UsersRepository;

import java.nio.file.Paths;
import java.time.Clock;
import java.util.function.Supplier;


public class UsersModule {

    private final UsersRepository usersRepo;
    private final SessionsRepo sessionsRepo;


    public UsersModule(UsersRepository usersRepo, SessionsRepo sessionsRepo) {
        this.usersRepo = usersRepo;
        this.sessionsRepo = sessionsRepo;
    }

    public UsersModule (Clock clock) {
        this(
                new UsersRepoES(Paths.get("airomem/users")),
                new SessionsRepo(clock));
    }

    public UsersService createService() {
        return new UsersService(this.usersRepo, this.sessionsRepo);
    }

    public SessionsRepo getSessionsRepo () {
        return this.sessionsRepo;
    }

}
