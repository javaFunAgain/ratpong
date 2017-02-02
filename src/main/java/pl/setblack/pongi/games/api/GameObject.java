package pl.setblack.pongi.games.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.annotation.concurrent.Immutable;
import java.io.Serializable;

/**
 * Created by jarek on 1/23/17.
 */
@Immutable
@JsonDeserialize
public class GameObject implements Serializable{
    public final float x;
    public final float y;

    @JsonCreator
    public GameObject(float x, float y) {
        this.x = x;
        this.y = y;
    }
}
