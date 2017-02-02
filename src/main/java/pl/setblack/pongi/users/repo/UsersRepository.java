package pl.setblack.pongi.users.repo;

import pl.setblack.pongi.users.api.RegUserStatus;

/**
 * Created by jarek on 1/30/17.
 */
public interface UsersRepository {

    RegUserStatus addUser(final String login, final String pass);

    boolean login(final String login, final String password);
}
