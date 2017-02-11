/**
 * This class is generated by jOOQ
 */
package org.jooq.example.gradle.db.information_schema.tables.pojos;


import java.io.Serializable;

import javax.annotation.Generated;


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
public class ReferentialConstraints implements Serializable {

	private static final long serialVersionUID = 40703913;

	private String constraintCatalog;
	private String constraintSchema;
	private String constraintName;
	private String uniqueConstraintCatalog;
	private String uniqueConstraintSchema;
	private String uniqueConstraintName;
	private String matchOption;
	private String updateRule;
	private String deleteRule;
	private String tableName;
	private String referencedTableName;

	public ReferentialConstraints() {}

	public ReferentialConstraints(ReferentialConstraints value) {
		this.constraintCatalog = value.constraintCatalog;
		this.constraintSchema = value.constraintSchema;
		this.constraintName = value.constraintName;
		this.uniqueConstraintCatalog = value.uniqueConstraintCatalog;
		this.uniqueConstraintSchema = value.uniqueConstraintSchema;
		this.uniqueConstraintName = value.uniqueConstraintName;
		this.matchOption = value.matchOption;
		this.updateRule = value.updateRule;
		this.deleteRule = value.deleteRule;
		this.tableName = value.tableName;
		this.referencedTableName = value.referencedTableName;
	}

	public ReferentialConstraints(
		String constraintCatalog,
		String constraintSchema,
		String constraintName,
		String uniqueConstraintCatalog,
		String uniqueConstraintSchema,
		String uniqueConstraintName,
		String matchOption,
		String updateRule,
		String deleteRule,
		String tableName,
		String referencedTableName
	) {
		this.constraintCatalog = constraintCatalog;
		this.constraintSchema = constraintSchema;
		this.constraintName = constraintName;
		this.uniqueConstraintCatalog = uniqueConstraintCatalog;
		this.uniqueConstraintSchema = uniqueConstraintSchema;
		this.uniqueConstraintName = uniqueConstraintName;
		this.matchOption = matchOption;
		this.updateRule = updateRule;
		this.deleteRule = deleteRule;
		this.tableName = tableName;
		this.referencedTableName = referencedTableName;
	}

	public String getConstraintCatalog() {
		return this.constraintCatalog;
	}

	public void setConstraintCatalog(String constraintCatalog) {
		this.constraintCatalog = constraintCatalog;
	}

	public String getConstraintSchema() {
		return this.constraintSchema;
	}

	public void setConstraintSchema(String constraintSchema) {
		this.constraintSchema = constraintSchema;
	}

	public String getConstraintName() {
		return this.constraintName;
	}

	public void setConstraintName(String constraintName) {
		this.constraintName = constraintName;
	}

	public String getUniqueConstraintCatalog() {
		return this.uniqueConstraintCatalog;
	}

	public void setUniqueConstraintCatalog(String uniqueConstraintCatalog) {
		this.uniqueConstraintCatalog = uniqueConstraintCatalog;
	}

	public String getUniqueConstraintSchema() {
		return this.uniqueConstraintSchema;
	}

	public void setUniqueConstraintSchema(String uniqueConstraintSchema) {
		this.uniqueConstraintSchema = uniqueConstraintSchema;
	}

	public String getUniqueConstraintName() {
		return this.uniqueConstraintName;
	}

	public void setUniqueConstraintName(String uniqueConstraintName) {
		this.uniqueConstraintName = uniqueConstraintName;
	}

	public String getMatchOption() {
		return this.matchOption;
	}

	public void setMatchOption(String matchOption) {
		this.matchOption = matchOption;
	}

	public String getUpdateRule() {
		return this.updateRule;
	}

	public void setUpdateRule(String updateRule) {
		this.updateRule = updateRule;
	}

	public String getDeleteRule() {
		return this.deleteRule;
	}

	public void setDeleteRule(String deleteRule) {
		this.deleteRule = deleteRule;
	}

	public String getTableName() {
		return this.tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getReferencedTableName() {
		return this.referencedTableName;
	}

	public void setReferencedTableName(String referencedTableName) {
		this.referencedTableName = referencedTableName;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("ReferentialConstraints (");

		sb.append(constraintCatalog);
		sb.append(", ").append(constraintSchema);
		sb.append(", ").append(constraintName);
		sb.append(", ").append(uniqueConstraintCatalog);
		sb.append(", ").append(uniqueConstraintSchema);
		sb.append(", ").append(uniqueConstraintName);
		sb.append(", ").append(matchOption);
		sb.append(", ").append(updateRule);
		sb.append(", ").append(deleteRule);
		sb.append(", ").append(tableName);
		sb.append(", ").append(referencedTableName);

		sb.append(")");
		return sb.toString();
	}
}
