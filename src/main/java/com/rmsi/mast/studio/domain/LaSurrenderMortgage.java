package com.rmsi.mast.studio.domain;

import java.io.Serializable;
import java.util.Date;

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
 * 
 * @author Abhay.Pandey
 *
 */
@Entity
@Table(name="la_surrendermortgage")
public class LaSurrenderMortgage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="pk_la_surrendermortgage",sequenceName="la_surrendermortgage_mortgageid_seq", allocationSize=1) 
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_la_surrendermortgage") 
	private Integer mortgageid;
	
	@ManyToOne
	@JoinColumn(name="financialagencyid")
	private LaExtFinancialagency laExtFinancialagency;
	private Date mortgagefrom;
	private Date mortgageto;
	private Double mortgageamount;
	private Boolean isactive;
	private Integer createdby;
	@Temporal(TemporalType.DATE)
	private Date createddate;
	private Integer modifiedby;
	@Temporal(TemporalType.DATE)
	private Date modifieddate;
	private Long landid;
	private Long ownerid;
	private String surrenderreason;
	
	public Integer getMortgageid() {
		return mortgageid;
	}
	public void setMortgageid(Integer mortgageid) {
		this.mortgageid = mortgageid;
	}
	
	public LaExtFinancialagency getLaExtFinancialagency() {
		return laExtFinancialagency;
	}
	public void setLaExtFinancialagency(LaExtFinancialagency laExtFinancialagency) {
		this.laExtFinancialagency = laExtFinancialagency;
	}
	/*public Integer getFinancialagencyid() {
		return financialagencyid;
	}
	public void setFinancialagencyid(Integer financialagencyid) {
		this.financialagencyid = financialagencyid;
	}*/
	public Date getMortgagefrom() {
		return mortgagefrom;
	}
	public void setMortgagefrom(Date mortgagefrom) {
		this.mortgagefrom = mortgagefrom;
	}
	public Date getMortgageto() {
		return mortgageto;
	}
	public void setMortgageto(Date mortgageto) {
		this.mortgageto = mortgageto;
	}
	public Double getMortgageamount() {
		return mortgageamount;
	}
	public void setMortgageamount(Double mortgageamount) {
		this.mortgageamount = mortgageamount;
	}
	public Boolean getIsactive() {
		return isactive;
	}
	public void setIsactive(Boolean isactive) {
		this.isactive = isactive;
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
	public Long getLandid() {
		return landid;
	}
	public void setLandid(Long landid) {
		this.landid = landid;
	}
	public Long getOwnerid() {
		return ownerid;
	}
	public void setOwnerid(Long ownerid) {
		this.ownerid = ownerid;
	}
	public String getSurrenderreason() {
		return surrenderreason;
	}
	public void setSurrenderreason(String surrenderreason) {
		this.surrenderreason = surrenderreason;
	}	
	
}
