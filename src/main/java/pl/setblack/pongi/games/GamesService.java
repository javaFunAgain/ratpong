package pl.setblack.pongi.games;

import ratpack.func.Action;
import ratpack.handling.Chain;

import java.util.function.Function;

/**
 * Created by jarek on 2/1/17.
 */
public class GamesService {

    public Action<Chain> define() {
        return chain -> chain
                .prefix("games", listGames());
    }

    private Action<? super Chain> listGames() {
        return null;
    }
}
