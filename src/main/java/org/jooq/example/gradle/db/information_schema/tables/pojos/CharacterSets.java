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
public class CharacterSets implements Serializable {

	private static final long serialVersionUID = -471320003;

	private String characterSetName;
	private String defaultCollateName;
	private String description;
	private Long   maxlen;

	public CharacterSets() {}

	public CharacterSets(CharacterSets value) {
		this.characterSetName = value.characterSetName;
		this.defaultCollateName = value.defaultCollateName;
		this.description = value.description;
		this.maxlen = value.maxlen;
	}

	public CharacterSets(
		String characterSetName,
		String defaultCollateName,
		String description,
		Long   maxlen
	) {
		this.characterSetName = characterSetName;
		this.defaultCollateName = defaultCollateName;
		this.description = description;
		this.maxlen = maxlen;
	}

	public String getCharacterSetName() {
		return this.characterSetName;
	}

	public void setCharacterSetName(String characterSetName) {
		this.characterSetName = characterSetName;
	}

	public String getDefaultCollateName() {
		return this.defaultCollateName;
	}

	public void setDefaultCollateName(String defaultCollateName) {
		this.defaultCollateName = defaultCollateName;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getMaxlen() {
		return this.maxlen;
	}

	public void setMaxlen(Long maxlen) {
		this.maxlen = maxlen;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("CharacterSets (");

		sb.append(characterSetName);
		sb.append(", ").append(defaultCollateName);
		sb.append(", ").append(description);
		sb.append(", ").append(maxlen);

		sb.append(")");
		return sb.toString();
	}
}
