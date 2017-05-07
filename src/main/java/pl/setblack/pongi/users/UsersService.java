package pl.setblack.pongi.users;

import javaslang.control.Option;
import pl.setblack.pongi.JsonMapping;
import pl.setblack.pongi.users.api.LoginData;
import pl.setblack.pongi.users.api.NewUser;
import pl.setblack.pongi.users.repo.SessionsRepo;
import pl.setblack.pongi.users.repo.UsersRepository;
import pl.setblack.pongi.users.repo.UsersRepositoryProcessor;
import ratpack.func.Action;
import ratpack.handling.Chain;
import ratpack.handling.Handler;
import ratpack.jackson.Jackson;

/**
 * Created by jarek on 1/29/17.
 */
public class UsersService {

    private final UsersRepositoryProcessor usersRepo;

    private final SessionsRepo sessionsRepo;

    public UsersService(UsersRepository usersRepo, SessionsRepo sessionsRepo) {
        this.usersRepo = new UsersRepositoryProcessor(usersRepo);
        this.sessionsRepo = sessionsRepo;
    }

    public Action<Chain> usersApi() {
        return apiChain -> apiChain
                .prefix("users", users())
                .prefix("sessions", sessions());

    }

    private Action<Chain> users() {
        return chain -> chain
                .post(":id", addUser());

    }

    private Action<Chain> sessions() {
        return chain -> chain
                .post(":id", loginUser());
    }

    private Handler addUser() {
        return ctx -> {
            final String userId = ctx.getPathTokens().get("id");
            ctx.parse(NewUser.class).then(
                    newUser -> {
                        ctx.render(JsonMapping.toJsonPromise(usersRepo.addUser(userId, newUser.password)));
                    }
            );
        };
    }


    private Handler loginUser() {
        return ctx -> {
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
        };
    }
}
