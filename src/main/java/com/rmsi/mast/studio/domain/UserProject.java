

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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.BatchSize;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * The persistent class for the user_project database table.
 * 
 */
@Entity
@Table(name="la_ext_userprojectmapping")
public class UserProject implements Serializable {
	private static final long serialVersionUID = 1L;
	

    public UserProject() {
    }
    
    @Id
	@SequenceGenerator(name="pk_user_project_id_seq",sequenceName="la_ext_userprojectmapping_userprojectid_seq", allocationSize=1) 
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_user_project_id_seq") 
	@Column(name="userprojectid", unique=true, nullable=false) 
    private Integer userprojectid;
    
    
    
    @Column(name="isactive")
    private Boolean active;
   // private String tenantid;
    
    private long createdby;
	private long modifiedby;
	
	@Temporal( TemporalType.DATE )
	private Date createddate;
	
	@Temporal( TemporalType.DATE )
	private Date modifieddate;
	
	//@JsonIgnore
	@ManyToOne
	@JoinColumn(name="projectnameid")
	private Project project;
	
	
	@Transient
	private String userProject;
	
	@Transient
	private String projectDescription;
	
	@ManyToOne
	@JoinColumn(name="userid")
	private User user;

	@JsonIgnore
	public Project getProject() {
		return project;
	}


	public void setProject(Project project) {
		this.project = project;
	}


	public Integer getUserprojectid() {
		return userprojectid;
	}


	public void setUserprojectid(Integer userprojectid) {
		this.userprojectid = userprojectid;
	}



	public Boolean getActive() {
		return active;
	}


	public void setActive(Boolean active) {
		this.active = active;
	}


	public long getCreatedby() {
		return createdby;
	}


	public void setCreatedby(long createdby) {
		this.createdby = createdby;
	}


	public long getModifiedby() {
		return modifiedby;
	}


	public void setModifiedby(long modifiedby) {
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


	@JsonIgnore
	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}

	@Transient
	public String getUserProject() {
		return userProject;
	}

	@Transient
	public void setUserProject(String userProject) {
		this.userProject = userProject;
	}

	@Transient
	public String getProjectDescription() {
		return projectDescription;
	}

	@Transient
	public void setProjectDescription(String projectDescription) {
		this.projectDescription = projectDescription;
	}
    
  
    
    
    
	
}