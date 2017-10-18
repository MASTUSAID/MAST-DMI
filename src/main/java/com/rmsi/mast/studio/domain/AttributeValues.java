package com.rmsi.mast.studio.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the attribute database table.
 * 
 */

@Entity
@Table(name = "attribute")
public class AttributeValues implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "ATTRIBUTE_VALUE_ID_GENERATOR", sequenceName = "ATTRIBUTE_VALUE_ID_SEQ", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ATTRIBUTE_VALUE_ID_GENERATOR")
	private Long attributevalueid;

	private Long parentuid;

	private String value;

	// bi-directional many-to-one association to Surveyprojectattribute
	private Long uid;

	public AttributeValues() {
	}

	public Long getParentuid() {
		return this.parentuid;
	}

	public void setParentuid(Long parentuid) {
		this.parentuid = parentuid;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Long getAttributevalueid() {
		return attributevalueid;
	}

	public void setAttributevalueid(Long attributevalueid) {
		this.attributevalueid = attributevalueid;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

}