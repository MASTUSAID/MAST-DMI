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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rmsi.mast.studio.domain.DisputeType;
import com.rmsi.mast.studio.domain.LaExtTransactiondetail;
import com.rmsi.mast.studio.domain.LaParty;

@Entity
@Table(name = "la_ext_disputelandmapping")
public class DisputeBasic implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="disputelandid")
	private Integer disputelandid;

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

	//bi-directional many-to-one association to LaExtDisputetype
	@ManyToOne
	@JoinColumn(name="disputetypeid",insertable=false, updatable=false)
	private DisputeType laExtDisputetype;

	//bi-directional many-to-one association to LaExtTransactiondetail
	@ManyToOne
	@JoinColumn(name="transactionid")
	private LaExtTransactiondetailBasic laExtTransactiondetail;

	//bi-directional many-to-one association to LaParty
	@ManyToOne
	@JoinColumn(name="partyid",insertable=false, updatable=false)
	private LaParty laParty;

	private String comment;

	@ManyToOne
	@JoinColumn(name="landid")
	@JsonIgnore
	private ClaimBasic laSpatialunitLand;
    
	public DisputeBasic() {

	}

	public LaParty getLaParty() {
		return laParty;
	}

	public void setLaParty(LaParty laParty) {
		this.laParty = laParty;
	}

	public Integer getDisputelandid() {
		return disputelandid;
	}

	public void setDisputelandid(Integer disputelandid) {
		this.disputelandid = disputelandid;
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

	public DisputeType getLaExtDisputetype() {
		return laExtDisputetype;
	}

	public void setLaExtDisputetype(DisputeType laExtDisputetype) {
		this.laExtDisputetype = laExtDisputetype;
	}

	public LaExtTransactiondetailBasic getLaExtTransactiondetail() {
		return laExtTransactiondetail;
	}

	public void setLaExtTransactiondetail(
			LaExtTransactiondetailBasic laExtTransactiondetail) {
		this.laExtTransactiondetail = laExtTransactiondetail;
	}


	
	@JsonIgnore
	public ClaimBasic getLaSpatialunitLand() {
		return laSpatialunitLand;
	}

	public void setLaSpatialunitLand(ClaimBasic laSpatialunitLand) {
		this.laSpatialunitLand = laSpatialunitLand;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
    
	
	
	
    
    
  
}
