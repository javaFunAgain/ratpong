package pl.setblack.pongi.scores.repo;

import javaslang.collection.List;
import javaslang.control.Option;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.example.gradle.db.pongi.tables.Scorecord;
import org.jooq.example.gradle.db.pongi.tables.records.ScorecordRecord;
import org.jooq.impl.DSL;
import pl.setblack.pongi.scores.GameResult;
import pl.setblack.pongi.scores.ScoreRecord;
import pl.setblack.pongi.scores.UserScore;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by jarek on 2/11/17.
 */
public class ScoresRepositoryMYSQL implements ScoresRepository {


    private final Connection dbConnection;

    public ScoresRepositoryMYSQL() {
        String userName = "pongi";
        String password = "pongi";
        String url = "jdbc:mysql://localhost:3306/pongi";
        try {
            this.dbConnection = DriverManager.getConnection(url, userName, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ;
    }


    @Override
    public void registerScore(List<ScoreRecord> rec) {
        DSLContext create = DSL.using(dbConnection, SQLDialect.MYSQL);
        create.transaction(txConf ->
                rec.forEach(record ->
                    DSL.using(txConf).insertInto(Scorecord.SCORECORD).set(new ScorecordRecord(
                            record.userId,
                            Byte.valueOf((byte) (record.result == GameResult.WON ? 1 : 0)),
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
     return  List.ofAll(DSL.using(dbConnection).select(
             Scorecord.SCORECORD.USER,
             Scorecord.SCORECORD.WON.sum(),
             Scorecord.SCORECORD.GAMEID.count(),
             Scorecord.SCORECORD.SCOREDPOINT.sum(),
             Scorecord.SCORECORD.LOSTPOINT.sum()
             ).from(Scorecord.SCORECORD).groupBy(Scorecord.SCORECORD.USER).orderBy( Scorecord.SCORECORD.WON.sum().desc()).fetch().map(
                score ->
                new UserScore(score.value1(),
                        score.value2().intValue()*5,
                        score.value2().intValue(),
                        score.value3().intValue()-score.value2().intValue(),
                        score.value3().intValue(),
                        score.value4().intValue(),
                        score.value5().intValue()))
        );
    }
}
