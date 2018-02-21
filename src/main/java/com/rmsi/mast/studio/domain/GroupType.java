package com.rmsi.mast.studio.domain;

import java.io.Serializable;
import java.lang.String;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: GroupType
 *
 */
@Entity
@Table(name = "la_ext_grouptype")
public class GroupType implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	private Integer grouptypeid;

	private String grouptype;

	@Column(name="grouptype_en")
	private String grouptypeEn;

	private Boolean isactive;
	

	public GroupType(){
		
	}


	public Integer getGrouptypeid() {
		return grouptypeid;
	}

	public void setGrouptypeid(Integer grouptypeid) {
		this.grouptypeid = grouptypeid;
	}

	public String getGrouptype() {
		return grouptype;
	}

	public void setGrouptype(String grouptype) {
		this.grouptype = grouptype;
	}

	public String getGrouptypeEn() {
		return grouptypeEn;
	}

	public void setGrouptypeEn(String grouptypeEn) {
		this.grouptypeEn = grouptypeEn;
	}

	public Boolean getIsactive() {
		return isactive;
	}

	public void setIsactive(Boolean isactive) {
		this.isactive = isactive;
	}
	
	
	
	
	
}
