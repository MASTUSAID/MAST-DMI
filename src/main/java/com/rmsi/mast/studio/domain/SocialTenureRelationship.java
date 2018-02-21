package com.rmsi.mast.studio.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;


/**
 * Entity implementation class for Entity: SocialTenureRelationship
 */
@Entity
@Table(name="la_ext_personlandmapping")
public class SocialTenureRelationship implements Serializable {

/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

//    private static final long serialVersionUID = 1L;
    
    @Id
    @SequenceGenerator(name="pk_la_personlandmapping",sequenceName="la_ext_personlandmapping_personlandid_seq", allocationSize=1) 
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_la_personlandmapping") 
    @Column(name="personlandid")
	private Long personlandid;
    
    @Column(name="createdby")
	private Integer createdby;

    @Temporal(TemporalType.DATE)
	private Date createddate;

    @Column(name="isactive")
	private Boolean isactive;

    @Column(name="modifiedby")
	private Integer modifiedby;

    @Temporal(TemporalType.DATE)
	private Date modifieddate;
    
    @Column(name = "certificateno")
    private String certNumber;
  
    @Temporal(TemporalType.DATE)
    @Column(name = "certificateissuedate")
    private Date certIssueDate;

	//bi-directional many-to-one association to LaExtTransactiondetail
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="transactionid")
	private LaExtTransactiondetail laExtTransactiondetail;

	
	@Column(name="partyid")
	private Long partyid;

	@Transient
	private LaParty laParty;
	
	
	//bi-directional many-to-one association to LaPartygroupPersontype
	@ManyToOne
	@JoinColumn(name="persontypeid", nullable = false, updatable = false)
	private PersonType laPartygroupPersontype;

	
	@Column(name="landid")
	private Long landid;
	
	 public SocialTenureRelationship() {
       super();
	 }
	 
	public String getCertNumber() {
		return certNumber;
	}

	public void setCertNumber(String certNumber) {
		this.certNumber = certNumber;
	}
	
	public Date getCertIssueDate() {
		return certIssueDate;
	}

	public void setCertIssueDate(Date certIssueDate) {
		this.certIssueDate = certIssueDate;
	}

	public Long getPersonlandid() {
		return personlandid;
	}

	public void setPersonlandid(Long personlandid) {
		this.personlandid = personlandid;
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

	public Boolean getIsactive() {
		return isactive;
	}

	public void setIsactive(Boolean isactive) {
		this.isactive = isactive;
	}

	public Integer getModifiedby() {
		return modifiedby;
	}

	public void setModifiedby(Integer modifiedby) {
		this.modifiedby = modifiedby;
	}

	
	public LaExtTransactiondetail getLaExtTransactiondetail() {
		return laExtTransactiondetail;
	}

	public Date getModifieddate() {
		return modifieddate;
	}

	public void setModifieddate(Date modifieddate) {
		this.modifieddate = modifieddate;
	}

	public void setLaExtTransactiondetail(
			LaExtTransactiondetail laExtTransactiondetail) {
		this.laExtTransactiondetail = laExtTransactiondetail;
	}

	public PersonType getLaPartygroupPersontype() {
		return laPartygroupPersontype;
	}

	public Long getPartyid() {
		return partyid;
	}

	public void setPartyid(Long partyid) {
		this.partyid = partyid;
	}

	public void setLaPartygroupPersontype(PersonType laPartygroupPersontype) {
		this.laPartygroupPersontype = laPartygroupPersontype;
	}

	public Long getLandid() {
		return landid;
	}

	public void setLandid(Long landid) {
		this.landid = landid;
	}

	@Transient
	public LaParty getLaParty() {
		return laParty;
	}

	@Transient
	public void setLaParty(LaParty laParty) {
		this.laParty = laParty;
	}


	
	
	
}
