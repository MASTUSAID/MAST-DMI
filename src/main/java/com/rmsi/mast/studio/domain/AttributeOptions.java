package com.rmsi.mast.studio.domain;

import java.io.Serializable;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: AttributeOptions
 *
 */
@Entity
@Table(name = "attribute_options")
public class AttributeOptions implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@Id
	private int id;
		
	private String optiontext;
		
	@Column(name = "attribute_id")
	private int attributeId;
	
	@Column(name = "parent_id")
	private int parentId;
	
//	@ManyToOne
//	@JoinColumn(name ="attribute_id")
//	private AttributeMaster attributeId;
		
	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	private String optiontext_second_language;

	public String getOptiontext_second_language() {
		return optiontext_second_language;
	}

	public void setOptiontext_second_language(String optiontext_second_language) {
		this.optiontext_second_language = optiontext_second_language;
	}

	public String getOptiontext() {
		return optiontext;
	}

	public void setOptiontext(String optiontext) {
		this.optiontext = optiontext;
	}

	public void setAttributeId(int attributeId) {
		this.attributeId = attributeId;
	}

	public int getAttributeId() {
		return attributeId;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	/*public AttributeMaster getAttributeId() {
		return attributeId;
	}

	public void setAttributeId(AttributeMaster attributeId) {
		this.attributeId = attributeId;
	}*/

	public AttributeOptions() {
		super();
	}
   
}
