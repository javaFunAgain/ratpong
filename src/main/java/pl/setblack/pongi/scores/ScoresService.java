package pl.setblack.pongi.scores;

import javaslang.control.Option;
import pl.setblack.pongi.scores.repo.ScoresRepositoryNonBlocking;
import ratpack.exec.Promise;
import ratpack.func.Action;
import ratpack.handling.Chain;

import ratpack.handling.Handler;
import ratpack.jackson.Jackson;

/**
 * Created by jarek on 2/5/17.
 */
public class ScoresService {

    private final ScoresRepositoryNonBlocking nonBlockingRepo;

    public ScoresService(ScoresRepositoryNonBlocking nonBlockingRepo) {
        this.nonBlockingRepo = nonBlockingRepo;
    }


    public Action<Chain> scores() {
        return chain -> chain
                .prefix("scores", ctx->ctx.get( getScores()));
    }

    private Handler getScores() {
        return ctx -> {
            final int limit =
                    Option.of(ctx.getRequest().getQueryParams().get("limit"))
                    .map( Integer::parseInt)
                    .getOrElse(20);
            ctx.render(Promise.async(
                    downstream->downstream.accept( nonBlockingRepo.getTopScores(limit).thenApply(Jackson::json))));
        };
    }
}
