package com.rmsi.mast.studio.domain.fetch;

import java.io.Serializable;

import javax.persistence.*;

import com.rmsi.mast.studio.domain.Gender;
import com.rmsi.mast.studio.domain.MaritalStatus;
import com.rmsi.mast.studio.domain.SourceDocument;

import java.util.List;


/**
 * The persistent class for the person_administrator database table.
 * 
 */
@Entity
@Table(name="person_administrator")
public class PersonAdministrator implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "PERSON_ADMIN_ID_GENERATOR", sequenceName = "personadmin_gid_seq", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PERSON_ADMIN_ID_GENERATOR")
	private Long adminid;

	private String address;

	private Integer age;

	private String citizenship;

	private String firstname;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "gender", nullable = false)
	private Gender gender;

	private String lastname;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "maritalstatus")
	private MaritalStatus maritalstatus;

	private String middlename;

	private String phonenumber;

	private Boolean resident;
	
	private Boolean active; 

	//bi-directional many-to-one association to SourceDocument
	@OneToMany(fetch = FetchType.EAGER , cascade=CascadeType.ALL)
	@JoinColumn(name="adminid",referencedColumnName="adminid")
	private List<SourceDocument> sourceDocuments;

	public PersonAdministrator() {
	}

	public Long getAdminid() {
		return this.adminid;
	}

	public void setAdminid(Long adminid) {
		this.adminid = adminid;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getAge() {
		return this.age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getCitizenship() {
		return this.citizenship;
	}

	public void setCitizenship(String citizenship) {
		this.citizenship = citizenship;
	}

	public String getFirstname() {
		return this.firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}


	public String getLastname() {
		return this.lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	

	public String getMiddlename() {
		return this.middlename;
	}

	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}

	public String getPhonenumber() {
		return this.phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public Boolean getResident() {
		return this.resident;
	}

	public void setResident(Boolean resident) {
		this.resident = resident;
	}

	public List<SourceDocument> getSourceDocuments() {
		return this.sourceDocuments;
	}

	public void setSourceDocuments(List<SourceDocument> sourceDocuments) {
		this.sourceDocuments = sourceDocuments;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public MaritalStatus getMaritalstatus() {
		return maritalstatus;
	}

	public void setMaritalstatus(MaritalStatus maritalstatus) {
		this.maritalstatus = maritalstatus;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
	
}