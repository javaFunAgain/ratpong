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
public class InnodbFtDefaultStopword implements Serializable {

	private static final long serialVersionUID = -1615401606;

	private String value;

	public InnodbFtDefaultStopword() {}

	public InnodbFtDefaultStopword(InnodbFtDefaultStopword value) {
		this.value = value.value;
	}

	public InnodbFtDefaultStopword(
		String value
	) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("InnodbFtDefaultStopword (");

		sb.append(value);

		sb.append(")");
		return sb.toString();
	}
}
