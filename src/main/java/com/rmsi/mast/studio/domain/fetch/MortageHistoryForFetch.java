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
public class MortageHistoryForFetch implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long rnum;
	
	private Long landid;
	private String financialagency;
		
	@Temporal(TemporalType.DATE)
	private Date mortgagefrom;
	
	@Temporal(TemporalType.DATE)
	private Date mortgageto;
	private Long mortgageamount;
	
	public Long getRnum() {
		return rnum;
	}

	public void setRnum(Long rnum) {
		this.rnum = rnum;
	}
	
	

	public Long getLandid() {
		return landid;
	}

	public void setLandid(Long landid) {
		this.landid = landid;
	}

	public String getFinancialagency() {
		return financialagency;
	}

	public void setFinancialagency(String financialagency) {
		this.financialagency = financialagency;
	}

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

	public Long getMortgageamount() {
		return mortgageamount;
	}

	public void setMortgageamount(Long mortgageamount) {
		this.mortgageamount = mortgageamount;
	}


}