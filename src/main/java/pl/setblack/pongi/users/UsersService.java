package pl.setblack.pongi.users;

import javaslang.control.Option;
import pl.setblack.pongi.users.api.LoginData;
import pl.setblack.pongi.users.api.NewUser;
import pl.setblack.pongi.users.repo.SessionsRepo;
import pl.setblack.pongi.users.repo.UsersRepository;
import pl.setblack.pongi.users.repo.UsersRepositoryNonBlocking;
import ratpack.exec.Promise;
import ratpack.func.Action;
import ratpack.handling.Chain;
import ratpack.jackson.Jackson;

/**
 * Created by jarek on 1/29/17.
 */
public class UsersService {

    private final UsersRepositoryNonBlocking usersRepo;

    private final SessionsRepo sessionsRepo;


    public UsersService(UsersRepository usersRepo, SessionsRepo sessionsRepo) {
        this.usersRepo = new UsersRepositoryNonBlocking(usersRepo);
        this.sessionsRepo = sessionsRepo;
    }

    public Action<Chain> users() {
        return chain -> chain
                .prefix("add", addUser())
                .prefix("login", loginUser());
    }

    private Action<Chain> addUser() {
        return chain -> chain.post(":id", ctx -> {
            final String userId = ctx.getPathTokens().get("id");
            ctx.parse(NewUser.class).then(
              newUser -> {
                    final Promise result = Promise.async(
                            d -> d.accept(usersRepo.addUser(userId, newUser.password).thenApply(Jackson::json)
                            ));
                    ctx.render(result);
              }
            );
        });
    }


    private Action<Chain> loginUser() {
        return chain -> chain.post(":id", ctx -> {
            final String userId = ctx.getPathTokens().get("id");
            ctx.parse(LoginData.class).then(
                    loginData -> {
                        final boolean login = usersRepo.login(userId, loginData.password);
                        ctx.render(Jackson.json(
                                login ?
                                Option.some(this.sessionsRepo.startSession(userId))
                                :
                                Option.none()));
                    }
            );
        });
    }
}
