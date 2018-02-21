package com.rmsi.mast.studio.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the la_ext_transactiondetails database table.
 * 
 */
@Entity
@Table(name="la_ext_transactiondetails")
@NamedQuery(name="LaExtTransactiondetail.findAll", query="SELECT l FROM LaExtTransactiondetail l")
public class LaExtTransactiondetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="pk_la_transactiondetails",sequenceName="la_ext_transactiondetails_transactionid_seq", allocationSize=1) 
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_la_transactiondetails") 
	@Column(name="transactionid")
	private Integer transactionid;

	@Column(name="createdby")
	private Integer createdby;

	@Temporal(TemporalType.DATE)
	private Date createddate;

	@Column(name="isactive")
	private Boolean isactive;

	@Column(name="remarks")
	private String remarks;
	
	private Integer moduletransid;

	


	//bi-directional many-to-one association to LaExtApplicationstatus
	@ManyToOne
	@JoinColumn(name="applicationstatusid")
	private Status laExtApplicationstatus;

	
	@Column(name="processid")
	private Long processid;

	public LaExtTransactiondetail() {
	}

	public Integer getTransactionid() {
		return this.transactionid;
	}

	public void setTransactionid(Integer transactionid) {
		this.transactionid = transactionid;
	}

	public Integer getCreatedby() {
		return this.createdby;
	}

	public void setCreatedby(Integer createdby) {
		this.createdby = createdby;
	}

	public Date getCreateddate() {
		return this.createddate;
	}

	public void setCreateddate(Date createddate) {
		this.createddate = createddate;
	}

	public Boolean getIsactive() {
		return this.isactive;
	}

	public void setIsactive(Boolean isactive) {
		this.isactive = isactive;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}


	public Status getLaExtApplicationstatus() {
		return laExtApplicationstatus;
	}

	public void setLaExtApplicationstatus(Status laExtApplicationstatus) {
		this.laExtApplicationstatus = laExtApplicationstatus;
	}



	public Integer getModuletransid() {
		return moduletransid;
	}

	public Long getProcessid() {
		return processid;
	}

	public void setProcessid(Long processid) {
		this.processid = processid;
	}

	public void setModuletransid(Integer moduletransid) {
		this.moduletransid = moduletransid;
	}

}