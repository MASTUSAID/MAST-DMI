package com.rmsi.mast.studio.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="la_ext_categorytype")
public class AttributeCategoryType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	
	private Integer categorytypeid;

	private Boolean isactive;

	private String typename;


	public AttributeCategoryType() {
	}

	public Integer getCategorytypeid() {
		return this.categorytypeid;
	}

	public void setCategorytypeid(Integer categorytypeid) {
		this.categorytypeid = categorytypeid;
	}

	public Boolean getIsactive() {
		return this.isactive;
	}

	public void setIsactive(Boolean isactive) {
		this.isactive = isactive;
	}

	public String getTypename() {
		return this.typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

	
}
