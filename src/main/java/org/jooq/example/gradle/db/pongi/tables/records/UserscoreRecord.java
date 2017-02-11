/**
 * This class is generated by jOOQ
 */
package org.jooq.example.gradle.db.pongi.tables.records;


import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record6;
import org.jooq.Row6;
import org.jooq.example.gradle.db.pongi.tables.Userscore;
import org.jooq.impl.UpdatableRecordImpl;


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
public class UserscoreRecord extends UpdatableRecordImpl<UserscoreRecord> implements Record6<String, Integer, Integer, Integer, Integer, Integer> {

	private static final long serialVersionUID = 609726665;

	/**
	 * Setter for <code>pongi.USERSCORE.user</code>.
	 */
	public void setUser(String value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>pongi.USERSCORE.user</code>.
	 */
	public String getUser() {
		return (String) getValue(0);
	}

	/**
	 * Setter for <code>pongi.USERSCORE.won</code>.
	 */
	public void setWon(Integer value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>pongi.USERSCORE.won</code>.
	 */
	public Integer getWon() {
		return (Integer) getValue(1);
	}

	/**
	 * Setter for <code>pongi.USERSCORE.lost</code>.
	 */
	public void setLost(Integer value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>pongi.USERSCORE.lost</code>.
	 */
	public Integer getLost() {
		return (Integer) getValue(2);
	}

	/**
	 * Setter for <code>pongi.USERSCORE.played</code>.
	 */
	public void setPlayed(Integer value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>pongi.USERSCORE.played</code>.
	 */
	public Integer getPlayed() {
		return (Integer) getValue(3);
	}

	/**
	 * Setter for <code>pongi.USERSCORE.scoredPoint</code>.
	 */
	public void setScoredpoint(Integer value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>pongi.USERSCORE.scoredPoint</code>.
	 */
	public Integer getScoredpoint() {
		return (Integer) getValue(4);
	}

	/**
	 * Setter for <code>pongi.USERSCORE.lostPoint</code>.
	 */
	public void setLostpoint(Integer value) {
		setValue(5, value);
	}

	/**
	 * Getter for <code>pongi.USERSCORE.lostPoint</code>.
	 */
	public Integer getLostpoint() {
		return (Integer) getValue(5);
	}

	// -------------------------------------------------------------------------
	// Primary key information
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Record1<String> key() {
		return (Record1) super.key();
	}

	// -------------------------------------------------------------------------
	// Record6 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row6<String, Integer, Integer, Integer, Integer, Integer> fieldsRow() {
		return (Row6) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row6<String, Integer, Integer, Integer, Integer, Integer> valuesRow() {
		return (Row6) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field1() {
		return Userscore.USERSCORE.USER;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field2() {
		return Userscore.USERSCORE.WON;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field3() {
		return Userscore.USERSCORE.LOST;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field4() {
		return Userscore.USERSCORE.PLAYED;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field5() {
		return Userscore.USERSCORE.SCOREDPOINT;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field6() {
		return Userscore.USERSCORE.LOSTPOINT;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value1() {
		return getUser();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value2() {
		return getWon();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value3() {
		return getLost();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value4() {
		return getPlayed();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value5() {
		return getScoredpoint();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value6() {
		return getLostpoint();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserscoreRecord value1(String value) {
		setUser(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserscoreRecord value2(Integer value) {
		setWon(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserscoreRecord value3(Integer value) {
		setLost(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserscoreRecord value4(Integer value) {
		setPlayed(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserscoreRecord value5(Integer value) {
		setScoredpoint(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserscoreRecord value6(Integer value) {
		setLostpoint(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserscoreRecord values(String value1, Integer value2, Integer value3, Integer value4, Integer value5, Integer value6) {
		value1(value1);
		value2(value2);
		value3(value3);
		value4(value4);
		value5(value5);
		value6(value6);
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached UserscoreRecord
	 */
	public UserscoreRecord() {
		super(Userscore.USERSCORE);
	}

	/**
	 * Create a detached, initialised UserscoreRecord
	 */
	public UserscoreRecord(String user, Integer won, Integer lost, Integer played, Integer scoredpoint, Integer lostpoint) {
		super(Userscore.USERSCORE);

		setValue(0, user);
		setValue(1, won);
		setValue(2, lost);
		setValue(3, played);
		setValue(4, scoredpoint);
		setValue(5, lostpoint);
	}
}
