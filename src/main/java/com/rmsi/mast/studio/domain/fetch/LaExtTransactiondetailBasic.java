package com.rmsi.mast.studio.domain.fetch;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.rmsi.mast.studio.domain.Status;


@Entity
@Table(name="la_ext_transactiondetails")
public class LaExtTransactiondetailBasic implements Serializable{
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
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

	/*@OneToMany(mappedBy="laExtTransactiondetail",fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	private List<DisputeBasic> laExtDisputelandmappings;
	
	@OneToMany(mappedBy="laExtTransactiondetail",fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	private List<RightBasic> laExtPersonlandmappings;

*/
	//bi-directional many-to-one association to LaExtApplicationstatus
	@ManyToOne
	@JoinColumn(name="applicationstatusid")
	private Status laExtApplicationstatus;

	
	public LaExtTransactiondetailBasic() {
	}

	public Integer getTransactionid() {
		return transactionid;
	}

	public void setTransactionid(Integer transactionid) {
		this.transactionid = transactionid;
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

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Integer getModuletransid() {
		return moduletransid;
	}

	public void setModuletransid(Integer moduletransid) {
		this.moduletransid = moduletransid;
	}

//	public List<RightBasic> getLaExtPersonlandmappings() {
//		return laExtPersonlandmappings;
//	}
//
//	public void setLaExtPersonlandmappings(List<RightBasic> laExtPersonlandmappings) {
//		this.laExtPersonlandmappings = laExtPersonlandmappings;
//	}

	public Status getLaExtApplicationstatus() {
		return laExtApplicationstatus;
	}

	public void setLaExtApplicationstatus(Status laExtApplicationstatus) {
		this.laExtApplicationstatus = laExtApplicationstatus;
	}
//
//	public List<DisputeBasic> getLaExtDisputelandmappings() {
//		return laExtDisputelandmappings;
//	}
//
//	public void setLaExtDisputelandmappings(
//			List<DisputeBasic> laExtDisputelandmappings) {
//		this.laExtDisputelandmappings = laExtDisputelandmappings;
//	}
	
	
	

}
