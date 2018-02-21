package com.rmsi.mast.studio.domain.fetch;

import java.io.Serializable;
import java.util.List;

import com.rmsi.mast.studio.domain.LaParty;

public class LaExtDisputeDTO  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer disputeid;


	private Integer disputetypeid;
	
	private Long landid;
	
	private Integer status;
	 
	 private List<LaParty> laParty;
	 
	 private String comment;

	public Integer getDisputeid() {
		return disputeid;
	}

	public void setDisputeid(Integer disputeid) {
		this.disputeid = disputeid;
	}

	
	public Long getLandid() {
		return landid;
	}

	public void setLandid(Long landid) {
		this.landid = landid;
	}

	
	public List<LaParty> getLaParty() {
		return laParty;
	}

	public void setLaParty(List<LaParty> laParty) {
		this.laParty = laParty;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Integer getDisputetypeid() {
		return disputetypeid;
	}

	public void setDisputetypeid(Integer disputetypeid) {
		this.disputetypeid = disputetypeid;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
	
	  
	  
}
