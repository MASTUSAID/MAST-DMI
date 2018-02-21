package com.rmsi.mast.studio.domain.fetch;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.BatchSize;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rmsi.mast.studio.domain.LaExtTransactiondetail;
import com.rmsi.mast.studio.domain.LaParty;
import com.rmsi.mast.studio.domain.Outputformat;
import com.rmsi.mast.studio.domain.SpatialUnit;


@Entity
@Table(name = "la_ext_documentdetails")
public class MediaBasic implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="documentid")
	private Integer documentid;

    @Column(name="createdby")
	private Integer createdby;

    @Temporal(TemporalType.DATE)
	private Date createddate;

    @Column(name="documentlocation")
	private String documentlocation;

    @Column(name="documentname")
	private String documentname;

    @Column(name="isactive")
	private Boolean isactive;

    @Column(name="modifiedby")
	private Integer modifiedby;

    @Temporal(TemporalType.DATE)
	private Date modifieddate;

	//bi-directional many-to-one association to LaExtDocumentformat
	@ManyToOne
	@JoinColumn(name="documentformatid")
	private Outputformat laExtDocumentformat;

	//bi-directional many-to-one association to LaExtTransactiondetail
	@ManyToOne
	@JoinColumn(name="transactionid")
	private LaExtTransactiondetailBasic laExtTransactiondetail;

	//bi-directional many-to-one association to LaParty
	@ManyToOne
	@JoinColumn(name="partyid",insertable=false, updatable=false)
	private LaParty laParty;

	//bi-directional many-to-one association to LaSpatialunitLand
	@ManyToOne
	@JoinColumn(name="landid",insertable=false, updatable=false)
	private ClaimBasic laSpatialunitLand;
	
	 @OneToMany(fetch=FetchType.EAGER,cascade = CascadeType.ALL)
	 @JoinColumn(name = "parentuid")
	 private List<MediaAttributeValue> attributes;
    
	 public MediaBasic() {
//       super();
   }

	public LaExtTransactiondetailBasic getLaExtTransactiondetail() {
		return laExtTransactiondetail;
	}

	public void setLaExtTransactiondetail(
			LaExtTransactiondetailBasic laExtTransactiondetail) {
		this.laExtTransactiondetail = laExtTransactiondetail;
	}

	public Integer getDocumentid() {
		return documentid;
	}

	public void setDocumentid(Integer documentid) {
		this.documentid = documentid;
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

	public String getDocumentlocation() {
		return documentlocation;
	}

	public void setDocumentlocation(String documentlocation) {
		this.documentlocation = documentlocation;
	}

	public String getDocumentname() {
		return documentname;
	}

	public void setDocumentname(String documentname) {
		this.documentname = documentname;
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

	public Outputformat getLaExtDocumentformat() {
		return laExtDocumentformat;
	}

	public void setLaExtDocumentformat(Outputformat laExtDocumentformat) {
		this.laExtDocumentformat = laExtDocumentformat;
	}

//	public LaExtTransactiondetail getLaExtTransactiondetail() {
//		return laExtTransactiondetail;
//	}
//
//	public void setLaExtTransactiondetail(
//			LaExtTransactiondetail laExtTransactiondetail) {
//		this.laExtTransactiondetail = laExtTransactiondetail;
//	}

	public LaParty getLaParty() {
		return laParty;
	}

	public void setLaParty(LaParty laParty) {
		this.laParty = laParty;
	}

	@JsonIgnore
	public ClaimBasic getLaSpatialunitLand() {
		return laSpatialunitLand;
	}

	public void setLaSpatialunitLand(ClaimBasic laSpatialunitLand) {
		this.laSpatialunitLand = laSpatialunitLand;
	}

	public List<MediaAttributeValue> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<MediaAttributeValue> attributes) {
		this.attributes = attributes;
	}
    
	 
	 
	 
	 

//    @Id
//    private int gid;
//
//    @Column(name = "mediatype")
//    private String mediaType;
//
//    @Column
//    private Long usin;
//    
//    @Column(name = "dispute_id")
//    private Long disputeId;
//    
//    @Column
//    private Boolean active;
//    
//    @Column(name = "social_tenure_gid")
//    private Integer rightId;
//    
//    @Column(name = "person_gid")
//    private Long personId;
//    
//    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @JoinColumn(name = "parent_id")
//    private List<MediaAttributeValue> attributes;
//    
//    public MediaBasic() {
//        super();
//    }
//
//    public int getGid() {
//        return gid;
//    }
//
//    public void setGid(int gid) {
//        this.gid = gid;
//    }
//
//    public String getMediaType() {
//        return mediaType;
//    }
//
//    public void setMediaType(String mediaType) {
//        this.mediaType = mediaType;
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
//    public Long getDisputeId() {
//        return disputeId;
//    }
//
//    public void setDisputeId(Long disputeId) {
//        this.disputeId = disputeId;
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
//    public Integer getRightId() {
//        return rightId;
//    }
//
//    public void setRightId(Integer rightId) {
//        this.rightId = rightId;
//    }
//
//    public Long getPersonId() {
//        return personId;
//    }
//
//    public void setPersonId(Long personId) {
//        this.personId = personId;
//    }
//
//    public List<MediaAttributeValue> getAttributes() {
//        return attributes;
//    }
//
//    public void setAttributes(List<MediaAttributeValue> attributes) {
//        this.attributes = attributes;
//    }
}
