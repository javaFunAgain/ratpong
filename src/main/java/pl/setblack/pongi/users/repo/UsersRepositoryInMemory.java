package pl.setblack.pongi.users.repo;

import javaslang.collection.HashMap;
import javaslang.control.Option;
import pl.setblack.pongi.users.api.RegUserStatus;
import pl.setblack.pongi.users.api.Session;

import java.io.Serializable;

public class UsersRepositoryInMemory implements UsersRepository, Serializable {

    private volatile HashMap<String, UserData> allUsers = HashMap.empty();



    public RegUserStatus addUser(final String login, final String pass) {
        if (!allUsers.containsKey(login)) {
            allUsers = allUsers.put(login, new UserData(login, pass));
            return new RegUserStatus(Option.none());
        } else {
            return new RegUserStatus(Option.some("user existed"));
        }
    }

    public boolean login(final String login, final String password) {
        return allUsers.get(login).map( userData -> userData
                .hashedPassword
                .equals(password))
                .getOrElse(false);

    }

}
