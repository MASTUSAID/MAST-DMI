package com.rmsi.mast.studio.domain;


import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.BatchSize;





/**
 * The persistent class for the project database table.
 * 
 */
@Entity
@Table(name = "la_spatialsource_projectname")
public class Project implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	
	
	
	@Id
	@SequenceGenerator(name="pk_la_spatialsource_projectname",sequenceName="la_spatialsource_projectname_projectnameid_seq", allocationSize=1) 
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_la_spatialsource_projectname") 
	@Column(name="projectnameid") 
	private Integer projectnameid;
	
	@Column(name="projectname")
	private String name;
	@Column(name="minresolution")
	private Double minresolutions;
	@Column(name="maxresolution")
	private Double maxresolutions;
	private Integer zoomlevelextent;
	private String maxextent;
	private String minextent;
	private String activelayer;
	private String overlaymap;
	private String disclaimer;
	private String description;
	@Column(name="isactive")
	private Boolean active;
	
	private long createdby;
	private long modifiedby;
	
	@Temporal( TemporalType.DATE )
    private Date createddate;
	
	@Temporal( TemporalType.DATE )
    private Date modifieddate;
    
   
	//private List<ProjectArea> projectAreas;
	
	private Integer workflowdefid;
	
	
	
	@OneToMany(mappedBy="project",cascade=CascadeType.ALL,fetch = FetchType.EAGER)
	private Set<ProjectBaselayer> projectBaselayers;
	
	
	@OneToMany(mappedBy="projectBean" , cascade=CascadeType.ALL,fetch = FetchType.EAGER)
	private Set<ProjectLayergroup> projectLayergroups;
	
	
	@OneToMany(mappedBy="project", fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	private Set<UserProject> userProjects;
	
	
	@ManyToOne
	@JoinColumn(name="documentformatid")
	@BatchSize(size=16)
	private Outputformat outputformat;
	

	
/*	@ManyToOne
	@JoinColumn(name="projectionid",insertable=false, updatable=false)
	private Projection displayProjection;*/
	
	
	@OneToMany(mappedBy="project", fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private Set<ProjectArea> ProjectArea;
	
	@ManyToOne
	@JoinColumn(name="unitid")
	@BatchSize(size=16)
	private Unit unit;
	
	
	@ManyToOne
	@JoinColumn(name="projectionid")
	@BatchSize(size=16)
	private Projection projection;


	public Project() {}
	

			
		public Outputformat getOutputformat() {
			return this.outputformat;
		}

		public void setOutputformat(Outputformat outputformat) {
			this.outputformat = outputformat;
		}
		
		
		
		
	/*	public Projection getDisplayProjection() {
			return this.displayProjection;
		}

		public void setDisplayProjection(Projection displayProjection) {
			this.displayProjection = displayProjection;
		}
*/
		
		
	

	public Integer getProjectnameid() {
		return projectnameid;
	}



	public void setProjectnameid(Integer projectnameid) {
		this.projectnameid = projectnameid;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public Double getMinresolutions() {
		return minresolutions;
	}



	public void setMinresolutions(Double minresolutions) {
		this.minresolutions = minresolutions;
	}



	public Double getMaxresolutions() {
		return maxresolutions;
	}



	public void setMaxresolutions(Double maxresolutions) {
		this.maxresolutions = maxresolutions;
	}



	public Boolean getActive() {
		return active;
	}



	public void setActive(Boolean active) {
		this.active = active;
	}



	public String getMaxextent() {
		return maxextent;
	}



	public void setMaxextent(String maxextent) {
		this.maxextent = maxextent;
	}



	public String getMinextent() {
		return minextent;
	}



	public void setMinextent(String minextent) {
		this.minextent = minextent;
	}



	public Integer getZoomlevelextent() {
		return zoomlevelextent;
	}



	public void setZoomlevelextent(Integer zoomlevelextent) {
		this.zoomlevelextent = zoomlevelextent;
	}



	public String getActivelayer() {
		return activelayer;
	}



	public void setActivelayer(String activelayer) {
		this.activelayer = activelayer;
	}



	public String getOverlaymap() {
		return overlaymap;
	}



	public void setOverlaymap(String overlaymap) {
		this.overlaymap = overlaymap;
	}



	public String getDisclaimer() {
		return disclaimer;
	}



	public void setDisclaimer(String disclaimer) {
		this.disclaimer = disclaimer;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
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



	public Set<ProjectBaselayer> getProjectBaselayers() {
		return projectBaselayers;
	}



	public void setProjectBaselayers(Set<ProjectBaselayer> projectBaselayers) {
		this.projectBaselayers = projectBaselayers;
	}



	public Set<ProjectLayergroup> getProjectLayergroups() {
		return projectLayergroups;
	}



	public void setProjectLayergroups(Set<ProjectLayergroup> projectLayergroups) {
		this.projectLayergroups = projectLayergroups;
	}



	public Set<UserProject> getUserProjects() {
		return userProjects;
	}



	public void setUserProjects(Set<UserProject> userProjects) {
		this.userProjects = userProjects;
	}



	public Set<ProjectArea> getProjectArea() {
		return ProjectArea;
	}



	public void setProjectArea(Set<ProjectArea> projectArea) {
		ProjectArea = projectArea;
	}



	public Unit getUnit() {
		return unit;
	}



	public void setUnit(Unit unit) {
		this.unit = unit;
	}



	public Projection getProjection() {
		return projection;
	}



	public void setProjection(Projection projection) {
		this.projection = projection;
	}



	public Integer getWorkflowdefid() {
		return workflowdefid;
	}



	public void setWorkflowdefid(Integer workflowdefid) {
		this.workflowdefid = workflowdefid;
	}

	
	
	
	
	

}