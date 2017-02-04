package pl.setblack.pongi.users.repo;

import javax.annotation.concurrent.Immutable;
import java.io.Serializable;

/**
 * Created by jarek on 1/14/17.
 */
@Immutable
public class UserData implements Serializable{
    private static final long serialVersionUID = 1L;
    public final String id;
    public final String hashedPassword;

    public UserData(String id, String hashedPassword) {
        this.id = id;
        this.hashedPassword = hashedPassword;
    }
}
