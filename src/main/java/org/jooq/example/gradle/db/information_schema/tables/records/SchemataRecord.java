/**
 * This class is generated by jOOQ
 */
package org.jooq.example.gradle.db.information_schema.tables.records;


import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record5;
import org.jooq.Row5;
import org.jooq.example.gradle.db.information_schema.tables.Schemata;
import org.jooq.impl.TableRecordImpl;


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
public class SchemataRecord extends TableRecordImpl<SchemataRecord> implements Record5<String, String, String, String, String> {

	private static final long serialVersionUID = -1054293223;

	/**
	 * Setter for <code>information_schema.SCHEMATA.CATALOG_NAME</code>.
	 */
	public void setCatalogName(String value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>information_schema.SCHEMATA.CATALOG_NAME</code>.
	 */
	public String getCatalogName() {
		return (String) getValue(0);
	}

	/**
	 * Setter for <code>information_schema.SCHEMATA.SCHEMA_NAME</code>.
	 */
	public void setSchemaName(String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>information_schema.SCHEMATA.SCHEMA_NAME</code>.
	 */
	public String getSchemaName() {
		return (String) getValue(1);
	}

	/**
	 * Setter for <code>information_schema.SCHEMATA.DEFAULT_CHARACTER_SET_NAME</code>.
	 */
	public void setDefaultCharacterSetName(String value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>information_schema.SCHEMATA.DEFAULT_CHARACTER_SET_NAME</code>.
	 */
	public String getDefaultCharacterSetName() {
		return (String) getValue(2);
	}

	/**
	 * Setter for <code>information_schema.SCHEMATA.DEFAULT_COLLATION_NAME</code>.
	 */
	public void setDefaultCollationName(String value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>information_schema.SCHEMATA.DEFAULT_COLLATION_NAME</code>.
	 */
	public String getDefaultCollationName() {
		return (String) getValue(3);
	}

	/**
	 * Setter for <code>information_schema.SCHEMATA.SQL_PATH</code>.
	 */
	public void setSqlPath(String value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>information_schema.SCHEMATA.SQL_PATH</code>.
	 */
	public String getSqlPath() {
		return (String) getValue(4);
	}

	// -------------------------------------------------------------------------
	// Record5 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row5<String, String, String, String, String> fieldsRow() {
		return (Row5) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row5<String, String, String, String, String> valuesRow() {
		return (Row5) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field1() {
		return Schemata.SCHEMATA.CATALOG_NAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field2() {
		return Schemata.SCHEMATA.SCHEMA_NAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field3() {
		return Schemata.SCHEMATA.DEFAULT_CHARACTER_SET_NAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field4() {
		return Schemata.SCHEMATA.DEFAULT_COLLATION_NAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field5() {
		return Schemata.SCHEMATA.SQL_PATH;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value1() {
		return getCatalogName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value2() {
		return getSchemaName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value3() {
		return getDefaultCharacterSetName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value4() {
		return getDefaultCollationName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value5() {
		return getSqlPath();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SchemataRecord value1(String value) {
		setCatalogName(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SchemataRecord value2(String value) {
		setSchemaName(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SchemataRecord value3(String value) {
		setDefaultCharacterSetName(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SchemataRecord value4(String value) {
		setDefaultCollationName(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SchemataRecord value5(String value) {
		setSqlPath(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SchemataRecord values(String value1, String value2, String value3, String value4, String value5) {
		value1(value1);
		value2(value2);
		value3(value3);
		value4(value4);
		value5(value5);
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached SchemataRecord
	 */
	public SchemataRecord() {
		super(Schemata.SCHEMATA);
	}

	/**
	 * Create a detached, initialised SchemataRecord
	 */
	public SchemataRecord(String catalogName, String schemaName, String defaultCharacterSetName, String defaultCollationName, String sqlPath) {
		super(Schemata.SCHEMATA);

		setValue(0, catalogName);
		setValue(1, schemaName);
		setValue(2, defaultCharacterSetName);
		setValue(3, defaultCollationName);
		setValue(4, sqlPath);
	}
}
