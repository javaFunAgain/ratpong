package pl.setblack.pongi.users.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.annotation.concurrent.Immutable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Created by jarek on 1/15/17.
 */
@Immutable
@JsonDeserialize
public class Session {
    public final String userId;

    public final UUID uuid;

    public final LocalDateTime expirationTime;

    @JsonCreator
    public Session(String userId, UUID uuid, LocalDateTime expirationTime) {
        this.userId = userId;
        this.uuid = uuid;
        this.expirationTime = expirationTime;
    }
}
