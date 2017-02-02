package pl.setblack.pongi.users.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.annotation.concurrent.Immutable;

/**
 * Created by jarek on 1/15/17.
 */
@Immutable
@JsonDeserialize
public class LoginData {
    public final String password;

    @JsonCreator
    public LoginData(String password) {
        this.password = password;
    }
}
