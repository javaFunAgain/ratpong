package pl.setblack.pongi.users.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import javaslang.control.Option;

import javax.annotation.concurrent.Immutable;

/**
 * Created by jarek on 1/15/17.
 */
@Immutable
@JsonDeserialize
public class RegUserStatus {

    public final boolean ok;

    public final Option<String> problem;

    @JsonCreator
    public RegUserStatus(Option<String> problem) {
        this.problem = problem;
        this.ok = problem.isEmpty();
    }
}
