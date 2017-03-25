package pl.setblack.pongi.scores.repo;

import javaslang.collection.List;
import javaslang.control.Option;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import pl.setblack.pong.jooq.public_.tables.Scorecord;
import pl.setblack.pong.jooq.public_.tables.records.ScorecordRecord;
import pl.setblack.pongi.scores.GameResult;
import pl.setblack.pongi.scores.ScoreRecord;
import pl.setblack.pongi.scores.UserScore;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by jarek on 2/11/17.
 */
public class ScoresRepositorySQL implements ScoresRepository {


    private final Connection dbConnection;

    public ScoresRepositorySQL() {
        String userName = "sa";
        String password = "";
        String url = "jdbc:h2:~/test-gradle";
        try {
            this.dbConnection = DriverManager.getConnection(url, userName, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ;
    }


    @Override
    public void registerScore(List<ScoreRecord> rec) {
        DSLContext create = DSL.using(dbConnection, SQLDialect.H2);
        create.transaction(txConf ->
                rec.forEach(record ->
                        DSL.using(txConf).insertInto(Scorecord.SCORECORD).set(new ScorecordRecord(
                                record.userId,
                                record.result == GameResult.WON ,
                                record.playerScored,
                                record.opponentScore,
                                record.gameId)).execute()
                ));

    }

    @Override
    public Option<UserScore> getUserScore(String userId) {


        return Option.none();
    }

    @Override
    public List<UserScore> getTopScores(int limit) {
        return List.ofAll(DSL.using(dbConnection).select(
                Scorecord.SCORECORD.USER,
                Scorecord.SCORECORD.WON.sum(),
                Scorecord.SCORECORD.GAMEID.count(),
                Scorecord.SCORECORD.SCOREDPOINT.sum(),
                Scorecord.SCORECORD.LOSTPOINT.sum()
                ).from(Scorecord.SCORECORD).groupBy(Scorecord.SCORECORD.USER).orderBy(Scorecord.SCORECORD.WON.sum().desc()).fetch().map(
                score ->
                        new UserScore(
                                score.value1(),
                                score.value2().intValue() * 5,
                                score.value2().intValue(),
                                score.value3().intValue() - score.value2().intValue(),
                                score.value3().intValue(),
                                score.value4().intValue(),
                                score.value5().intValue()))
        );
    }
}