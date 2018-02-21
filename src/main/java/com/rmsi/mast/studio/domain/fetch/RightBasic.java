package com.rmsi.mast.studio.domain.fetch;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.BatchSize;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rmsi.mast.studio.domain.LaExtTransactiondetail;
import com.rmsi.mast.studio.domain.LaParty;
import com.rmsi.mast.studio.domain.PersonType;
import com.rmsi.mast.studio.domain.SpatialUnit;

@Entity
@Table(name = "la_ext_personlandmapping")
public class RightBasic implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="personlandid")
	private Integer personlandid;
    
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
	@ManyToOne
	@JoinColumn(name="transactionid")
	private LaExtTransactiondetailBasic laExtTransactiondetail;

	//bi-directional many-to-one association to LaParty
	@ManyToOne
	@JoinColumn(name="partyid",insertable=false, updatable=false)
	private LaParty laParty;

	//bi-directional many-to-one association to LaPartygroupPersontype
//	@ManyToOne
//	@JoinColumn(name="persontypeid")
//	private PersonTypeBasic laPartygroupPersontype;

	//bi-directional many-to-one association to LaSpatialunitLand
	@ManyToOne
	@JoinColumn(name="landid",insertable=false, updatable=false)
	private ClaimBasic laSpatialunitLand;
	
   @OneToMany(fetch=FetchType.EAGER,cascade = CascadeType.ALL)
   @JoinColumn(name = "parentuid")
   private List<RightAttributeValue> attributes;
	
    
	 public RightBasic() {
	    }

	 
	 
	 
	public List<RightAttributeValue> getAttributes() {
		return attributes;
	}




	public void setAttributes(List<RightAttributeValue> attributes) {
		this.attributes = attributes;
	}




	public Integer getPersonlandid() {
		return personlandid;
	}

	public void setPersonlandid(Integer personlandid) {
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

	public Date getModifieddate() {
		return modifieddate;
	}

	public void setModifieddate(Date modifieddate) {
		this.modifieddate = modifieddate;
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

	public LaExtTransactiondetailBasic getLaExtTransactiondetail() {
		return laExtTransactiondetail;
	}

	public void setLaExtTransactiondetail(
			LaExtTransactiondetailBasic laExtTransactiondetail) {
		this.laExtTransactiondetail = laExtTransactiondetail;
	}

	public LaParty getLaParty() {
		return laParty;
	}

	public void setLaParty(LaParty laParty) {
		this.laParty = laParty;
	}

//	public PersonTypeBasic getLaPartygroupPersontype() {
//		return laPartygroupPersontype;
//	}
//
//	public void setLaPartygroupPersontype(PersonTypeBasic laPartygroupPersontype) {
//		this.laPartygroupPersontype = laPartygroupPersontype;
//	}
	@JsonIgnore
	public ClaimBasic getLaSpatialunitLand() {
		return laSpatialunitLand;
	}

	public void setLaSpatialunitLand(ClaimBasic laSpatialunitLand) {
		this.laSpatialunitLand = laSpatialunitLand;
	}
    
	 
	 
	 
	 
    
//    @Id
//    private Integer gid;
//    
//    @Column
//    private Long usin;
//
//    @Column(name="tenureclass_id")
//    private Integer rightTypeId;
//
//    @Column(name = "share")
//    private Integer shareTypeId;
//
//    @Column(name="cert_number")
//    private String certNumber;
//    
//    @Column(name = "ccro_issue_date")
//    @Temporal(javax.persistence.TemporalType.DATE)
//    private Date certDate;
//    
//    @Column(name = "juridical_area")
//    private Double juridicalArea;
//
//    @Column(name = "relationship_type")
//    private Integer relationshipTypeId;
//
//    @OneToOne(fetch=FetchType.EAGER)
//    @JoinColumn(name="person_gid")
//    PersonBasic person;
//        
//    @Column(name = "isactive")
//    private Boolean active;
//    
//    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @JoinColumn(name = "parent_id")
//    private List<RightAttributeValue> attributes;
//    
//    public RightBasic() {
//    }
//
//    public Integer getGid() {
//        return gid;
//    }
//
//    public void setGid(Integer gid) {
//        this.gid = gid;
//    }
//
//    public Integer getRightTypeId() {
//        return rightTypeId;
//    }
//
//    public void setRightTypeId(Integer rightTypeId) {
//        this.rightTypeId = rightTypeId;
//    }
//
//    public Integer getShareTypeId() {
//        return shareTypeId;
//    }
//
//    public void setShareTypeId(Integer shareTypeId) {
//        this.shareTypeId = shareTypeId;
//    }
//
//    public String getCertNumber() {
//        return certNumber;
//    }
//
//    public void setCertNumber(String certNumber) {
//        this.certNumber = certNumber;
//    }
//
//    public Date getCertDate() {
//        return certDate;
//    }
//
//    public void setCertDate(Date certDate) {
//        this.certDate = certDate;
//    }
//
//    public Double getJuridicalArea() {
//        return juridicalArea;
//    }
//
//    public void setJuridicalArea(Double juridicalArea) {
//        this.juridicalArea = juridicalArea;
//    }
//
//    public Integer getRelationshipTypeId() {
//        return relationshipTypeId;
//    }
//
//    public void setRelationshipTypeId(Integer relationshipTypeId) {
//        this.relationshipTypeId = relationshipTypeId;
//    }
//
//    public Long getUsin() {
//        return usin;
//    }
//
//    public void setUsin(Long usin) {
//        this.usin = usin;
//    }
//
//    public PersonBasic getPerson() {
//        return person;
//    }
//
//    public void setPerson(PersonBasic person) {
//        this.person = person;
//    }
//
//    public Boolean getActive() {
//        return active;
//    }
//
//    public void setActive(Boolean active) {
//        this.active = active;
//    }
//
//    public List<RightAttributeValue> getAttributes() {
//        return attributes;
//    }
//
//    public void setAttributes(List<RightAttributeValue> attributes) {
//        this.attributes = attributes;
//    }
}
