package com.rmsi.mast.studio.domain;



import java.io.Serializable;

import javax.persistence.*;

import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;


/**
 * The persistent class for the la_ext_spatialunit_personwithinterest database table.
 * 
 */
@Entity
@Transactional
@Table(name="la_ext_spatialunit_personwithinterest")
@NamedQuery(name="SpatialUnitPersonWithInterest.findAll", query="SELECT l FROM SpatialunitPersonwithinterest l")
public class SpatialUnitPersonWithInterest implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="LA_EXT_SPATIALUNIT_PERSONWITHINTEREST_ID_GENERATOR" ,sequenceName="la_ext_spatialunit_personwithinterest_id_seq", allocationSize=1 )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="LA_EXT_SPATIALUNIT_PERSONWITHINTEREST_ID_GENERATOR")
	private Long id;

	private String address;

	private Integer createdby;

	@Temporal(TemporalType.DATE)
	private Date createddate;

	@Temporal(TemporalType.DATE)
	private Date dob;

	@Column(name="first_name")
	private String firstName;

	private Integer gender;

	private Boolean isactive;

	private Long landid;

	@Column(name="last_name")
	private String lastName;

	@Column(name="middle_name")
	private String middleName;

	private Integer modifiedby;

	@Temporal(TemporalType.DATE)
	private Date modifieddate;

	private Long projectnameid;

	private Integer relation;
	
	
	private Integer typeid;
	
	private Integer transactionid;
	

	public SpatialUnitPersonWithInterest() {
	}


	public Integer getTransactionid() {
		return transactionid;
	}


	public void setTransactionid(Integer transactionid) {
		this.transactionid = transactionid;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public Integer getCreatedby() {
		return createdby;
	}


	public void setCreatedby(Integer createdby) {
		this.createdby = createdby;
	}


	public Date getCreateddate() {
		return createddate;
	}


	public void setCreateddate(Date createddate) {
		this.createddate = createddate;
	}


	public Date getDob() {
		return dob;
	}


	public void setDob(Date dob) {
		this.dob = dob;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public Integer getGender() {
		return gender;
	}


	public void setGender(Integer gender) {
		this.gender = gender;
	}


	public Boolean getIsactive() {
		return isactive;
	}


	public void setIsactive(Boolean isactive) {
		this.isactive = isactive;
	}


	public Long getLandid() {
		return landid;
	}


	public void setLandid(Long landid) {
		this.landid = landid;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getMiddleName() {
		return middleName;
	}


	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}


	public Integer getModifiedby() {
		return modifiedby;
	}


	public void setModifiedby(Integer modifiedby) {
		this.modifiedby = modifiedby;
	}


	public Date getModifieddate() {
		return modifieddate;
	}


	public void setModifieddate(Date modifieddate) {
		this.modifieddate = modifieddate;
	}


	public Long getProjectnameid() {
		return projectnameid;
	}


	public void setProjectnameid(Long projectnameid) {
		this.projectnameid = projectnameid;
	}


	public Integer getRelation() {
		return relation;
	}


	public void setRelation(Integer relation) {
		this.relation = relation;
	}


	public Integer getTypeid() {
		return typeid;
	}


	public void setTypeid(Integer typeid) {
		this.typeid = typeid;
	}


	
	
	
	
	
	
	
	
	
	
	

}