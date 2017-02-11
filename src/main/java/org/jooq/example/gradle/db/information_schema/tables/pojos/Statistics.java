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
public class Statistics implements Serializable {

	private static final long serialVersionUID = 31394133;

	private String tableCatalog;
	private String tableSchema;
	private String tableName;
	private Long   nonUnique;
	private String indexSchema;
	private String indexName;
	private Long   seqInIndex;
	private String columnName;
	private String collation;
	private Long   cardinality;
	private Long   subPart;
	private String packed;
	private String nullable;
	private String indexType;
	private String comment;
	private String indexComment;

	public Statistics() {}

	public Statistics(Statistics value) {
		this.tableCatalog = value.tableCatalog;
		this.tableSchema = value.tableSchema;
		this.tableName = value.tableName;
		this.nonUnique = value.nonUnique;
		this.indexSchema = value.indexSchema;
		this.indexName = value.indexName;
		this.seqInIndex = value.seqInIndex;
		this.columnName = value.columnName;
		this.collation = value.collation;
		this.cardinality = value.cardinality;
		this.subPart = value.subPart;
		this.packed = value.packed;
		this.nullable = value.nullable;
		this.indexType = value.indexType;
		this.comment = value.comment;
		this.indexComment = value.indexComment;
	}

	public Statistics(
		String tableCatalog,
		String tableSchema,
		String tableName,
		Long   nonUnique,
		String indexSchema,
		String indexName,
		Long   seqInIndex,
		String columnName,
		String collation,
		Long   cardinality,
		Long   subPart,
		String packed,
		String nullable,
		String indexType,
		String comment,
		String indexComment
	) {
		this.tableCatalog = tableCatalog;
		this.tableSchema = tableSchema;
		this.tableName = tableName;
		this.nonUnique = nonUnique;
		this.indexSchema = indexSchema;
		this.indexName = indexName;
		this.seqInIndex = seqInIndex;
		this.columnName = columnName;
		this.collation = collation;
		this.cardinality = cardinality;
		this.subPart = subPart;
		this.packed = packed;
		this.nullable = nullable;
		this.indexType = indexType;
		this.comment = comment;
		this.indexComment = indexComment;
	}

	public String getTableCatalog() {
		return this.tableCatalog;
	}

	public void setTableCatalog(String tableCatalog) {
		this.tableCatalog = tableCatalog;
	}

	public String getTableSchema() {
		return this.tableSchema;
	}

	public void setTableSchema(String tableSchema) {
		this.tableSchema = tableSchema;
	}

	public String getTableName() {
		return this.tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Long getNonUnique() {
		return this.nonUnique;
	}

	public void setNonUnique(Long nonUnique) {
		this.nonUnique = nonUnique;
	}

	public String getIndexSchema() {
		return this.indexSchema;
	}

	public void setIndexSchema(String indexSchema) {
		this.indexSchema = indexSchema;
	}

	public String getIndexName() {
		return this.indexName;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	public Long getSeqInIndex() {
		return this.seqInIndex;
	}

	public void setSeqInIndex(Long seqInIndex) {
		this.seqInIndex = seqInIndex;
	}

	public String getColumnName() {
		return this.columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getCollation() {
		return this.collation;
	}

	public void setCollation(String collation) {
		this.collation = collation;
	}

	public Long getCardinality() {
		return this.cardinality;
	}

	public void setCardinality(Long cardinality) {
		this.cardinality = cardinality;
	}

	public Long getSubPart() {
		return this.subPart;
	}

	public void setSubPart(Long subPart) {
		this.subPart = subPart;
	}

	public String getPacked() {
		return this.packed;
	}

	public void setPacked(String packed) {
		this.packed = packed;
	}

	public String getNullable() {
		return this.nullable;
	}

	public void setNullable(String nullable) {
		this.nullable = nullable;
	}

	public String getIndexType() {
		return this.indexType;
	}

	public void setIndexType(String indexType) {
		this.indexType = indexType;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getIndexComment() {
		return this.indexComment;
	}

	public void setIndexComment(String indexComment) {
		this.indexComment = indexComment;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Statistics (");

		sb.append(tableCatalog);
		sb.append(", ").append(tableSchema);
		sb.append(", ").append(tableName);
		sb.append(", ").append(nonUnique);
		sb.append(", ").append(indexSchema);
		sb.append(", ").append(indexName);
		sb.append(", ").append(seqInIndex);
		sb.append(", ").append(columnName);
		sb.append(", ").append(collation);
		sb.append(", ").append(cardinality);
		sb.append(", ").append(subPart);
		sb.append(", ").append(packed);
		sb.append(", ").append(nullable);
		sb.append(", ").append(indexType);
		sb.append(", ").append(comment);
		sb.append(", ").append(indexComment);

		sb.append(")");
		return sb.toString();
	}
}
