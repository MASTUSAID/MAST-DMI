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

/**
 * Entity implementation class for Entity: SourceDocument
 *
 * @author Shruti.Thakur
 */
@Entity
@Table(name = "la_ext_documentdetails")
public class SourceDocument implements Serializable {

/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

//    private static final long serialVersionUID = 1L;
    
	@Id
	@SequenceGenerator(name="la_ext_documentdetails",sequenceName="la_ext_documentdetails_documentid_seq", allocationSize=1) 
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="la_ext_documentdetails") 
    @Column(name="documentid")
	private Long documentid;

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
    
    @Column(name="remarks")
	private String remarks;
    
    @Temporal(TemporalType.DATE)
	private Date recordationdate;
    
    @Column(name="documenttypeid")
    private Integer documenttypeid;

	//bi-directional many-to-one association to LaExtDocumentformat
	@ManyToOne
	@JoinColumn(name="documentformatid")
	private Outputformat laExtDocumentformat;

	//bi-directional many-to-one association to LaExtTransactiondetail
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="transactionid")
	private LaExtTransactiondetail laExtTransactiondetail;

	//bi-directional many-to-one association to LaParty
	@ManyToOne
	@JoinColumn(name="partyid")
	private LaParty laParty;

	//bi-directional many-to-one association to LaSpatialunitLand
//	@ManyToOne
	@Column(name="landid")
	private Long laSpatialunitLand;
    
	public SourceDocument(){
		
	}

	public Long getDocumentid() {
		return documentid;
	}

	public void setDocumentid(Long documentid) {
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
	
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Date getRecordationdate() {
		return recordationdate;
	}

	public void setRecordationdate(Date recordationdate) {
		this.recordationdate = recordationdate;
	}
	
	public Integer getDocumenttypeid() {
		return documenttypeid;
	}

	public void setDocumenttypeid(Integer documenttypeid) {
		this.documenttypeid = documenttypeid;
	}

	public Outputformat getLaExtDocumentformat() {
		return laExtDocumentformat;
	}

	public void setLaExtDocumentformat(Outputformat laExtDocumentformat) {
		this.laExtDocumentformat = laExtDocumentformat;
	}

	public LaExtTransactiondetail getLaExtTransactiondetail() {
		return laExtTransactiondetail;
	}

	public void setLaExtTransactiondetail(
			LaExtTransactiondetail laExtTransactiondetail) {
		this.laExtTransactiondetail = laExtTransactiondetail;
	}

	public LaParty getLaParty() {
		return laParty;
	}

	public void setLaParty(LaParty laParty) {
		this.laParty = laParty;
	}

	public Long getLaSpatialunitLand() {
		return laSpatialunitLand;
	}

	public void setLaSpatialunitLand(Long laSpatialunitLand) {
		this.laSpatialunitLand = laSpatialunitLand;
	}
	
	

}
