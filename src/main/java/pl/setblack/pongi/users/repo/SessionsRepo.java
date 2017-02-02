package pl.setblack.pongi.users.repo;

import javaslang.collection.HashMap;
import javaslang.control.Option;
import pl.setblack.pongi.users.api.Session;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by jarek on 1/30/17.
 */
public class SessionsRepo {
    private AtomicReference<HashMap<String, Session>> activeSesssions =
            new AtomicReference<>(HashMap.empty());

    private final Clock clock;

    public SessionsRepo(Clock clock) {
        this.clock = clock;
    }


    public  Session startSession(String userId) {
        final UUID  uuid = UUID.randomUUID();
        final LocalDateTime now = LocalDateTime.now(this.clock);
        final LocalDateTime expirationTime = now.plusDays(1);
        final Session sess = new Session(userId, uuid,expirationTime);
        this.activeSesssions.updateAndGet(map -> map.put(uuid.toString(), sess));
        return sess;
    }

    public Option<Session> getSesstion (final String uuid) {
        return this.activeSesssions.get().get(uuid);
    }

}
