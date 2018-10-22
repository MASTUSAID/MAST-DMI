package com.rmsi.mast.studio.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rmsi.mast.studio.domain.fetch.ClaimBasic;

@Entity
@Table(name="la_ext_landapplicationstatus")
public class LandApplicationStatus implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	@Id
	@SequenceGenerator(name="pk_la_ext_landapplicationstatus_landapplicationstatusid",sequenceName="la_ext_landapplicationstatus_landapplicationstatusid_seq", allocationSize=1) 
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_la_ext_landapplicationstatus_landapplicationstatusid") 
	private Integer landapplicationstatusid;
	
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "landid")
	@JsonIgnore
	private ClaimBasic landid;
	
	private Integer applicationstatusid;
	
	private Integer workflowstatusid;
	
	private Integer occupancylength;
	
public LandApplicationStatus(){
		
	}

public Integer getLandapplicationstatusid() {
	return landapplicationstatusid;
}

public void setLandapplicationstatusid(Integer landapplicationstatusid) {
	this.landapplicationstatusid = landapplicationstatusid;
}

/*public Integer getLandid() {
	return landid;
}

public void setLandid(Integer landid) {
	this.landid = landid;
}*/

public Integer getApplicationstatusid() {
	return applicationstatusid;
}

public void setApplicationstatusid(Integer applicationstatusid) {
	this.applicationstatusid = applicationstatusid;
}

public Integer getWorkflowstatusid() {
	return workflowstatusid;
}

public void setWorkflowstatusid(Integer workflowstatusid) {
	this.workflowstatusid = workflowstatusid;
}

public Integer getOccupancylength() {
	return occupancylength;
}

public void setOccupancylength(Integer occupancylength) {
	this.occupancylength = occupancylength;
}


public ClaimBasic getLandid() {
	return landid;
}

public void setLandid(ClaimBasic landid) {
	this.landid = landid;
}



	

}
