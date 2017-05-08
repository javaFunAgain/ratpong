package pl.setblack.pongi.users.repo;

import pl.setblack.airomem.core.Persistent;
import pl.setblack.pongi.users.api.RegUserStatus;

import java.nio.file.Path;

public class UsersRepoES implements UsersRepository {


    private final Persistent<UsersRepositoryInMemory> controller;


    public UsersRepoES(Path where) {
        controller = Persistent.loadOptional(
                where,
                () -> new UsersRepositoryInMemory());
    }

    public void close() {
        this.controller.close();
    }

    @Override
    public RegUserStatus addUser(String login, String pass) {
        return controller.executeAndQuery(usersRepo -> usersRepo.addUser(login, pass));
    }

    @Override
    public boolean login(String login, String password) {
        return controller.query(usersRepo -> usersRepo.login(login, password));
    }
}
