/**
 * This class is generated by jOOQ
 */
package org.jooq.example.gradle.db.information_schema.tables;


import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.example.gradle.db.information_schema.InformationSchema;
import org.jooq.example.gradle.db.information_schema.tables.records.ProcesslistRecord;
import org.jooq.impl.TableImpl;
import org.jooq.types.ULong;


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
public class Processlist extends TableImpl<ProcesslistRecord> {

	private static final long serialVersionUID = 1928350863;

	/**
	 * The reference instance of <code>information_schema.PROCESSLIST</code>
	 */
	public static final Processlist PROCESSLIST = new Processlist();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<ProcesslistRecord> getRecordType() {
		return ProcesslistRecord.class;
	}

	/**
	 * The column <code>information_schema.PROCESSLIST.ID</code>.
	 */
	public final TableField<ProcesslistRecord, ULong> ID = createField("ID", org.jooq.impl.SQLDataType.BIGINTUNSIGNED.nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>information_schema.PROCESSLIST.USER</code>.
	 */
	public final TableField<ProcesslistRecord, String> USER = createField("USER", org.jooq.impl.SQLDataType.VARCHAR.length(32).nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>information_schema.PROCESSLIST.HOST</code>.
	 */
	public final TableField<ProcesslistRecord, String> HOST = createField("HOST", org.jooq.impl.SQLDataType.VARCHAR.length(64).nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>information_schema.PROCESSLIST.DB</code>.
	 */
	public final TableField<ProcesslistRecord, String> DB = createField("DB", org.jooq.impl.SQLDataType.VARCHAR.length(64), this, "");

	/**
	 * The column <code>information_schema.PROCESSLIST.COMMAND</code>.
	 */
	public final TableField<ProcesslistRecord, String> COMMAND = createField("COMMAND", org.jooq.impl.SQLDataType.VARCHAR.length(16).nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>information_schema.PROCESSLIST.TIME</code>.
	 */
	public final TableField<ProcesslistRecord, Integer> TIME = createField("TIME", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>information_schema.PROCESSLIST.STATE</code>.
	 */
	public final TableField<ProcesslistRecord, String> STATE = createField("STATE", org.jooq.impl.SQLDataType.VARCHAR.length(64), this, "");

	/**
	 * The column <code>information_schema.PROCESSLIST.INFO</code>.
	 */
	public final TableField<ProcesslistRecord, String> INFO = createField("INFO", org.jooq.impl.SQLDataType.CLOB, this, "");

	/**
	 * Create a <code>information_schema.PROCESSLIST</code> table reference
	 */
	public Processlist() {
		this("PROCESSLIST", null);
	}

	/**
	 * Create an aliased <code>information_schema.PROCESSLIST</code> table reference
	 */
	public Processlist(String alias) {
		this(alias, PROCESSLIST);
	}

	private Processlist(String alias, Table<ProcesslistRecord> aliased) {
		this(alias, aliased, null);
	}

	private Processlist(String alias, Table<ProcesslistRecord> aliased, Field<?>[] parameters) {
		super(alias, InformationSchema.INFORMATION_SCHEMA, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Processlist as(String alias) {
		return new Processlist(alias, this);
	}

	/**
	 * Rename this table
	 */
	public Processlist rename(String name) {
		return new Processlist(name, null);
	}
}
