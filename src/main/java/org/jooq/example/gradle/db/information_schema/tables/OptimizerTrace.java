/**
 * This class is generated by jOOQ
 */
package org.jooq.example.gradle.db.information_schema.tables;


import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.example.gradle.db.information_schema.InformationSchema;
import org.jooq.example.gradle.db.information_schema.tables.records.OptimizerTraceRecord;
import org.jooq.impl.TableImpl;


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
public class OptimizerTrace extends TableImpl<OptimizerTraceRecord> {

	private static final long serialVersionUID = -264078200;

	/**
	 * The reference instance of <code>information_schema.OPTIMIZER_TRACE</code>
	 */
	public static final OptimizerTrace OPTIMIZER_TRACE = new OptimizerTrace();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<OptimizerTraceRecord> getRecordType() {
		return OptimizerTraceRecord.class;
	}

	/**
	 * The column <code>information_schema.OPTIMIZER_TRACE.QUERY</code>.
	 */
	public final TableField<OptimizerTraceRecord, String> QUERY = createField("QUERY", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

	/**
	 * The column <code>information_schema.OPTIMIZER_TRACE.TRACE</code>.
	 */
	public final TableField<OptimizerTraceRecord, String> TRACE = createField("TRACE", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

	/**
	 * The column <code>information_schema.OPTIMIZER_TRACE.MISSING_BYTES_BEYOND_MAX_MEM_SIZE</code>.
	 */
	public final TableField<OptimizerTraceRecord, Integer> MISSING_BYTES_BEYOND_MAX_MEM_SIZE = createField("MISSING_BYTES_BEYOND_MAX_MEM_SIZE", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>information_schema.OPTIMIZER_TRACE.INSUFFICIENT_PRIVILEGES</code>.
	 */
	public final TableField<OptimizerTraceRecord, Byte> INSUFFICIENT_PRIVILEGES = createField("INSUFFICIENT_PRIVILEGES", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaulted(true), this, "");

	/**
	 * Create a <code>information_schema.OPTIMIZER_TRACE</code> table reference
	 */
	public OptimizerTrace() {
		this("OPTIMIZER_TRACE", null);
	}

	/**
	 * Create an aliased <code>information_schema.OPTIMIZER_TRACE</code> table reference
	 */
	public OptimizerTrace(String alias) {
		this(alias, OPTIMIZER_TRACE);
	}

	private OptimizerTrace(String alias, Table<OptimizerTraceRecord> aliased) {
		this(alias, aliased, null);
	}

	private OptimizerTrace(String alias, Table<OptimizerTraceRecord> aliased, Field<?>[] parameters) {
		super(alias, InformationSchema.INFORMATION_SCHEMA, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OptimizerTrace as(String alias) {
		return new OptimizerTrace(alias, this);
	}

	/**
	 * Rename this table
	 */
	public OptimizerTrace rename(String name) {
		return new OptimizerTrace(name, null);
	}
}
