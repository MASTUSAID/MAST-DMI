package com.rmsi.mast.studio.domain.fetch;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the attribute database table.
 * 
 */
@Entity
@Table(name = "attribute")
public class AttributeValuesFetch implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long attributevalueid;

	private String value;
	
	@Column(insertable = false, updatable = false)
	private String alias;

	@Column(insertable = false, updatable = false)
	private int datatypeid;
	
	public int getDatatypeid() {
		return datatypeid;
	}

	public void setDatatypeid(int datatypeid) {
		this.datatypeid = datatypeid;
	}

	public AttributeValuesFetch() {
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

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	

}