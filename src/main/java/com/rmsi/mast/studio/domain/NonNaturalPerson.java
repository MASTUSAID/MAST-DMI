package com.rmsi.mast.studio.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * Entity implementation class for Entity: NonNaturalPerson
 * 
 * @author Shruti.Thakur
 */
@Entity
@Table(name = "non_natural_person")
@PrimaryKeyJoinColumn(name="NON_NATURAL_PERSON_GID", referencedColumnName="PERSON_GID")
public class NonNaturalPerson extends Person implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name="instutution_name")
	private String institutionName;
	@Column(nullable = false)
	private String address;
	@Column(name="phone_number")
	private String phoneNumber;
	
	private Long poc_gid;
	
	private boolean active;
	
	@ManyToOne
	@JoinColumn(name = "group_type")
	private GroupType groupType;

	public GroupType getGroupType() {
		return groupType;
	}

	public void setGroupType(GroupType groupType) {
		this.groupType = groupType;
	}

	public NonNaturalPerson() {
		super();
	}

	public String getInstitutionName() {
		return this.institutionName;
	}

	public void setInstitutionName(String institutionName) {
		this.institutionName = institutionName;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Long getPoc_gid() {
		return poc_gid;
	}

	public void setPoc_gid(Long poc_gid) {
		this.poc_gid = poc_gid;
	}
	
}