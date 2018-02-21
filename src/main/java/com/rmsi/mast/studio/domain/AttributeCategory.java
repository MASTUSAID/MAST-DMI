package com.rmsi.mast.studio.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;


/**
 * The persistent class for the attribute_category database table.
 * 
 */
@Entity
@Table(name="la_ext_attributecategory")
public class AttributeCategory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long attributecategoryid;

	@Column(name="categoryname")
	private String categoryName;
	
	private Integer categorydisplayorder;
	
	@ManyToOne
	@JoinColumn(name="categorytypeid")
	private AttributeCategoryType categorytype;

	public AttributeCategory() {
	}
	
	

	public Integer getCategorydisplayorder() {
		return categorydisplayorder;
	}



	public void setCategorydisplayorder(Integer categorydisplayorder) {
		this.categorydisplayorder = categorydisplayorder;
	}



	public Long getAttributecategoryid() {
		return attributecategoryid;
	}

	public void setAttributecategoryid(Long attributecategoryid) {
		this.attributecategoryid = attributecategoryid;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public AttributeCategoryType getCategorytype() {
		return categorytype;
	}

	public void setCategorytype(AttributeCategoryType categorytype) {
		this.categorytype = categorytype;
	}

	
	
}