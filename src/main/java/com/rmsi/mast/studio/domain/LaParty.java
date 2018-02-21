package com.rmsi.mast.studio.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the la_party database table.
 * 
 */
@Entity
@Table(name="la_party")
@Inheritance(strategy = InheritanceType.JOINED)
public class LaParty implements Serializable {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

//	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="pk_la_party",sequenceName="la_party_partyid_seq", allocationSize=1) 
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_la_party") 
	private Long partyid;

	private Integer createdby;

	@Temporal(TemporalType.DATE)
	private Date createddate;


	//bi-directional many-to-one association to LaPartygroupPersontype
	@ManyToOne
	@JoinColumn(name="persontypeid")
	private PersonType laPartygroupPersontype;



	public LaParty() {
	}

	public Long getPartyid() {
		return partyid;
	}

	public void setPartyid(Long partyid) {
		this.partyid = partyid;
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



	public PersonType getLaPartygroupPersontype() {
		return laPartygroupPersontype;
	}

	public void setLaPartygroupPersontype(PersonType laPartygroupPersontype) {
		this.laPartygroupPersontype = laPartygroupPersontype;
	}

	

}