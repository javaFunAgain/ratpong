package pl.setblack.pongi.scores;

import javaslang.collection.List;
import org.junit.jupiter.api.Test;
import pl.setblack.pongi.Server;
import pl.setblack.pongi.scores.repo.ScoresRepository;
import pl.setblack.pongi.scores.repo.ScoresRepositoryInMem;
import pl.setblack.pongi.scores.repo.ScoresRepositoryNonBlocking;

import ratpack.http.client.ReceivedResponse;
import ratpack.test.embed.EmbeddedApp;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by jarek on 2/6/17.
 */
class ScoresServiceTest {




    @Test
    public void testEmptyTopScores() throws Exception{
        final ScoresRepository repo = new ScoresRepositoryInMem();
        final ScoresRepositoryNonBlocking snb = new ScoresRepositoryNonBlocking(repo);
        final ScoresService service = new ScoresService(snb);

        repo.registerScore(List.of(new ScoreRecord("aa",GameResult.WON,15,10,"uu")));

        EmbeddedApp.fromServer(
                Server.createServer( service.scores())
                )
                .test( testHttpClient -> {
                        final ReceivedResponse resp = testHttpClient.get("api/scores");
                        assertThat( resp.getBody().getText(), is(equalTo( "[]")));
                });
    }

}