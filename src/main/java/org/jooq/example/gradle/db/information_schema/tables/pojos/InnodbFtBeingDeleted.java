/**
 * This class is generated by jOOQ
 */
package org.jooq.example.gradle.db.information_schema.tables.pojos;


import java.io.Serializable;

import javax.annotation.Generated;

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
public class InnodbFtBeingDeleted implements Serializable {

	private static final long serialVersionUID = -1183353921;

	private ULong docId;

	public InnodbFtBeingDeleted() {}

	public InnodbFtBeingDeleted(InnodbFtBeingDeleted value) {
		this.docId = value.docId;
	}

	public InnodbFtBeingDeleted(
		ULong docId
	) {
		this.docId = docId;
	}

	public ULong getDocId() {
		return this.docId;
	}

	public void setDocId(ULong docId) {
		this.docId = docId;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("InnodbFtBeingDeleted (");

		sb.append(docId);

		sb.append(")");
		return sb.toString();
	}
}
