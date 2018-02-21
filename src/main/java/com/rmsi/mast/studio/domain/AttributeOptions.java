package com.rmsi.mast.studio.domain;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "la_ext_attributeoptions")
public class AttributeOptions implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="Attribute_option_sequence",sequenceName="la_ext_attributeoptions_attributeoptionsid_seq", allocationSize=1) 
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="Attribute_option_sequence") 
	private Integer attributeoptionsid;

	private String optiontext;

	private Integer parentid;


	@ManyToOne
	@JoinColumn(name="attributemasterid")
	@JsonIgnore
	private AttributeMaster attributeMaster;
	

	public AttributeOptions() {
		super();
	}

	public Integer getAttributeoptionsid() {
		return attributeoptionsid;
	}

	public void setAttributeoptionsid(Integer attributeoptionsid) {
		this.attributeoptionsid = attributeoptionsid;
	}

	public String getOptiontext() {
		return optiontext;
	}

	public void setOptiontext(String optiontext) {
		this.optiontext = optiontext;
	}

	public Integer getParentid() {
		return parentid;
	}

	public void setParentid(Integer parentid) {
		this.parentid = parentid;
	}

	public AttributeMaster getAttributeMaster() {
		return attributeMaster;
	}

	public void setAttributeMaster(AttributeMaster attributeMaster) {
		this.attributeMaster = attributeMaster;
	}


	
	
}
