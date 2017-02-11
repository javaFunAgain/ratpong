/**
 * This class is generated by jOOQ
 */
package org.jooq.example.gradle.db.pongi;


import javax.annotation.Generated;

import org.jooq.UniqueKey;
import org.jooq.example.gradle.db.pongi.tables.Scorecord;
import org.jooq.example.gradle.db.pongi.tables.Userscore;
import org.jooq.example.gradle.db.pongi.tables.records.ScorecordRecord;
import org.jooq.example.gradle.db.pongi.tables.records.UserscoreRecord;
import org.jooq.impl.AbstractKeys;


/**
 * A class modelling foreign key relationships between tables of the <code>pongi</code> 
 * schema
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.4"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

	// -------------------------------------------------------------------------
	// IDENTITY definitions
	// -------------------------------------------------------------------------


	// -------------------------------------------------------------------------
	// UNIQUE and PRIMARY KEY definitions
	// -------------------------------------------------------------------------

	public static final UniqueKey<ScorecordRecord> KEY_SCORECORD_PRIMARY = UniqueKeys0.KEY_SCORECORD_PRIMARY;
	public static final UniqueKey<UserscoreRecord> KEY_USERSCORE_PRIMARY = UniqueKeys0.KEY_USERSCORE_PRIMARY;

	// -------------------------------------------------------------------------
	// FOREIGN KEY definitions
	// -------------------------------------------------------------------------


	// -------------------------------------------------------------------------
	// [#1459] distribute members to avoid static initialisers > 64kb
	// -------------------------------------------------------------------------

	private static class UniqueKeys0 extends AbstractKeys {
		public static final UniqueKey<ScorecordRecord> KEY_SCORECORD_PRIMARY = createUniqueKey(Scorecord.SCORECORD, Scorecord.SCORECORD.USER, Scorecord.SCORECORD.GAMEID);
		public static final UniqueKey<UserscoreRecord> KEY_USERSCORE_PRIMARY = createUniqueKey(Userscore.USERSCORE, Userscore.USERSCORE.USER);
	}
}
