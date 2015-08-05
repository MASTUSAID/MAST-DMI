package com.rmsi.mast.studio.domain.fetch;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.rmsi.mast.studio.domain.ProjectHamlet;
import com.rmsi.mast.studio.domain.Status;
import com.rmsi.mast.studio.util.JsonDateSerializer;

@Entity
@Table(name = "spatial_unit")

public class SpatialUnitTemp implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private long usin;

	
	@Column(name = "project_name")
	private String project;


	@Column(name = "uka_propertyno", nullable = false)
	private String propertyno;


	@ManyToOne
	@JoinColumn(name = "current_workflow_status_id", nullable = false)	
	private Status status;

	@Column(name = "workflow_status_update_time", nullable = false)
	private Date statusUpdateTime;

	@Column(nullable = false)
	private int userid;

	@ManyToOne
	@JoinColumn(name = "userid", insertable = false, updatable = false)
	private Usertable user;
	
	
	@Column(name = "survey_date", nullable = false)
	private Date surveyDate;


	@Column(name = "usin_str")
	private String usinStr;
	
	private Boolean active;
	
	
	@ManyToOne
	@JoinColumn(name="hamlet_id")
	private ProjectHamlet hamlet_Id;


	public long getUsin() {
		return usin;
	}


	public void setUsin(long usin) {
		this.usin = usin;
	}


	public String getProject() {
		return project;
	}


	public void setProject(String project) {
		this.project = project;
	}


	public String getPropertyno() {
		return propertyno;
	}


	public void setPropertyno(String propertyno) {
		this.propertyno = propertyno;
	}


	public Status getStatus() {
		return status;
	}


	public void setStatus(Status status) {
		this.status = status;
	}


	public Date getStatusUpdateTime() {
		return statusUpdateTime;
	}


	public void setStatusUpdateTime(Date statusUpdateTime) {
		this.statusUpdateTime = statusUpdateTime;
	}


	public int getUserid() {
		return userid;
	}


	public void setUserid(int userid) {
		this.userid = userid;
	}


	public Usertable getUser() {
		return user;
	}


	public void setUser(Usertable user) {
		this.user = user;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getSurveyDate() {
		return surveyDate;
	}


	public void setSurveyDate(Date surveyDate) {
		this.surveyDate = surveyDate;
	}


	public String getUsinStr() {
		return usinStr;
	}


	public void setUsinStr(String usinStr) {
		this.usinStr = usinStr;
	}
	

	public Boolean getactive() {
		return active;
	}


	public void setactive(Boolean active) {
		this.active = active;
	}


	public ProjectHamlet getHamlet_Id() {
		return hamlet_Id;
	}


	public void setHamlet_Id(ProjectHamlet hamlet_Id) {
		this.hamlet_Id = hamlet_Id;
	}
	

}
