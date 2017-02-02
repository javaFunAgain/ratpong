package pl.setblack.pongi.users.repo;

import javax.annotation.concurrent.Immutable;

/**
 * Created by jarek on 1/14/17.
 */
@Immutable
public class UserData {
    public final String id;
    public final String hashedPassword;

    public UserData(String id, String hashedPassword) {
        this.id = id;
        this.hashedPassword = hashedPassword;
    }
}
