package pl.setblack.pongi.users.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.annotation.concurrent.Immutable;

@Immutable
@JsonDeserialize
public class NewUser {
    public final String password;

    @JsonCreator
    public NewUser(String password) {
        this.password = password;
    }

}
