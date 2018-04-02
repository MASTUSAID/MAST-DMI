package com.rmsi.mast.studio.domain.fetch;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the attribute database table.
 * 
 */
@Entity
public class UploadedDocumentDetailsForFetch implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long rnum;
	
	private String documentname;
	@Temporal(TemporalType.DATE)
	private Date docdate;
	private String description;
	private String documenttype;
	private Long transactionid;
	private Long partyid;
	private Long documentid;	
	
	
	public Long getRnum() {
		return rnum;
	}

	public void setRnum(Long rnum) {
		this.rnum = rnum;
	}

	public String getDocumentname() {
		return documentname;
	}

	public void setDocumentname(String documentname) {
		this.documentname = documentname;
	}

	public Date getDocdate() {
		return docdate;
	}

	public void setDocdate(Date docdate) {
		this.docdate = docdate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDocumenttype() {
		return documenttype;
	}

	public void setDocumenttype(String documenttype) {
		this.documenttype = documenttype;
	}

	public Long getTransactionid() {
		return transactionid;
	}

	public void setTransactionid(Long transactionid) {
		this.transactionid = transactionid;
	}

	public Long getPartyid() {
		return partyid;
	}

	public void setPartyid(Long partyid) {
		this.partyid = partyid;
	}

	public Long getDocumentid() {
		return documentid;
	}

	public void setDocumentid(Long documentid) {
		this.documentid = documentid;
	}

	
}