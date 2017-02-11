/**
 * This class is generated by jOOQ
 */
package org.jooq.example.gradle.db.pongi.tables.daos;


import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.example.gradle.db.pongi.tables.Userscore;
import org.jooq.example.gradle.db.pongi.tables.records.UserscoreRecord;
import org.jooq.impl.DAOImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.4"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserscoreDao extends DAOImpl<UserscoreRecord, org.jooq.example.gradle.db.pongi.tables.pojos.Userscore, String> {

	/**
	 * Create a new UserscoreDao without any configuration
	 */
	public UserscoreDao() {
		super(Userscore.USERSCORE, org.jooq.example.gradle.db.pongi.tables.pojos.Userscore.class);
	}

	/**
	 * Create a new UserscoreDao with an attached configuration
	 */
	public UserscoreDao(Configuration configuration) {
		super(Userscore.USERSCORE, org.jooq.example.gradle.db.pongi.tables.pojos.Userscore.class, configuration);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getId(org.jooq.example.gradle.db.pongi.tables.pojos.Userscore object) {
		return object.getUser();
	}

	/**
	 * Fetch records that have <code>user IN (values)</code>
	 */
	public List<org.jooq.example.gradle.db.pongi.tables.pojos.Userscore> fetchByUser(String... values) {
		return fetch(Userscore.USERSCORE.USER, values);
	}

	/**
	 * Fetch a unique record that has <code>user = value</code>
	 */
	public org.jooq.example.gradle.db.pongi.tables.pojos.Userscore fetchOneByUser(String value) {
		return fetchOne(Userscore.USERSCORE.USER, value);
	}

	/**
	 * Fetch records that have <code>won IN (values)</code>
	 */
	public List<org.jooq.example.gradle.db.pongi.tables.pojos.Userscore> fetchByWon(Integer... values) {
		return fetch(Userscore.USERSCORE.WON, values);
	}

	/**
	 * Fetch records that have <code>lost IN (values)</code>
	 */
	public List<org.jooq.example.gradle.db.pongi.tables.pojos.Userscore> fetchByLost(Integer... values) {
		return fetch(Userscore.USERSCORE.LOST, values);
	}

	/**
	 * Fetch records that have <code>played IN (values)</code>
	 */
	public List<org.jooq.example.gradle.db.pongi.tables.pojos.Userscore> fetchByPlayed(Integer... values) {
		return fetch(Userscore.USERSCORE.PLAYED, values);
	}

	/**
	 * Fetch records that have <code>scoredPoint IN (values)</code>
	 */
	public List<org.jooq.example.gradle.db.pongi.tables.pojos.Userscore> fetchByScoredpoint(Integer... values) {
		return fetch(Userscore.USERSCORE.SCOREDPOINT, values);
	}

	/**
	 * Fetch records that have <code>lostPoint IN (values)</code>
	 */
	public List<org.jooq.example.gradle.db.pongi.tables.pojos.Userscore> fetchByLostpoint(Integer... values) {
		return fetch(Userscore.USERSCORE.LOSTPOINT, values);
	}
}
