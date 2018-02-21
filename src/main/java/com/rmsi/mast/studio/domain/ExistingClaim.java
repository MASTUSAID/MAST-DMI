package com.rmsi.mast.studio.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="la_ext_existingclaim_documentdetails")
public class ExistingClaim implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="pk_la_ext_existingclaim",sequenceName="la_ext_existingclaim_documentid_seq", allocationSize=1) 
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_la_ext_existingclaim") 
	private Integer claimdocumentid;
	
	private String documenttype;
	
	private Integer documentrefno;
	
	private Integer landid;
	
	private Integer plotno;
	
	@Temporal(TemporalType.DATE)
	private Date documentdate;
	
	private boolean isactive;
	
	private Integer createdby;
	
	private Integer modifiedby;
	
	@Temporal(TemporalType.DATE)
	private Date createddate;
	
	@Temporal(TemporalType.DATE)
	private Date modifieddate;
	
	
	
	public ExistingClaim(){
		
	}



	public Integer getClaimdocumentid() {
		return claimdocumentid;
	}



	public void setClaimdocumentid(Integer claimdocumentid) {
		this.claimdocumentid = claimdocumentid;
	}



	public String getDocumenttype() {
		return documenttype;
	}



	public void setDocumenttype(String documenttype) {
		this.documenttype = documenttype;
	}



	public Integer getDocumentrefno() {
		return documentrefno;
	}



	public void setDocumentrefno(Integer documentrefno) {
		this.documentrefno = documentrefno;
	}



	public Integer getLandid() {
		return landid;
	}



	public void setLandid(Integer landid) {
		this.landid = landid;
	}



	public Integer getPlotno() {
		return plotno;
	}



	public void setPlotno(Integer plotno) {
		this.plotno = plotno;
	}



	public Date getDocumentdate() {
		return documentdate;
	}



	public void setDocumentdate(Date documentdate) {
		this.documentdate = documentdate;
	}



	public boolean isIsactive() {
		return isactive;
	}



	public void setIsactive(boolean isactive) {
		this.isactive = isactive;
	}



	public Integer getCreatedby() {
		return createdby;
	}



	public void setCreatedby(Integer createdby) {
		this.createdby = createdby;
	}



	public Integer getModifiedby() {
		return modifiedby;
	}



	public void setModifiedby(Integer modifiedby) {
		this.modifiedby = modifiedby;
	}



	public Date getCreateddate() {
		return createddate;
	}



	public void setCreateddate(Date createddate) {
		this.createddate = createddate;
	}



	public Date getModifieddate() {
		return modifieddate;
	}



	public void setModifieddate(Date modifieddate) {
		this.modifieddate = modifieddate;
	}



	

}
