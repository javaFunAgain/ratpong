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
public class InnodbFtConfig implements Serializable {

	private static final long serialVersionUID = 150410622;

	private String key;
	private String value;

	public InnodbFtConfig() {}

	public InnodbFtConfig(InnodbFtConfig value) {
		this.key = value.key;
		this.value = value.value;
	}

	public InnodbFtConfig(
		String key,
		String value
	) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("InnodbFtConfig (");

		sb.append(key);
		sb.append(", ").append(value);

		sb.append(")");
		return sb.toString();
	}
}