package com.rmsi.mast.studio.domain;


import java.io.Serializable;
import java.util.List;
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

import org.hibernate.annotations.BatchSize;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the project database table.
 * 
 */
@Entity
public class Project implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	private String name;
	private Boolean active;
	private String activelayer;
	private String copyright;
	private Boolean cosmetic;
	private String description;
	private String disclaimer;
	
	private Integer height;
	private Integer id;
	private String maxextent;
	private Double maxresolutions;
	private String minextent;
	private Double minresolutions;
	private Integer numzoomlevels;
	private String overlaymap;
	private String restrictedextent;
	private String tenantid;
	private byte[] thumbnail;
	private String watermask;
	private Integer width;
	private Set<Overviewmap> overviewmaps;
	private Set<Printtemplate> printtemplates;
	private Outputformat outputformat;
	private Projection projection;
	private Projection displayProjection;
	private Unit unit;
	private List<ProjectArea> projectAreas;
	private Set<ProjectLayergroup> projectLayergroups;
	private Set<Savedquery> savedqueries;
	private Set<UserProject> userProjects;
	private Set<ProjectBaselayer> projectBaselayers;
	private Boolean admincreated;
	private String owner;

	public Project() 
	{
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public Boolean getAdmincreated() {
		return admincreated;
	}

	public void setAdmincreated(Boolean admincreated) {
		this.admincreated = admincreated;
	}

	//bi-directional many-to-one association to ProjectLayergroup
	//@JsonIgnore
	@OneToMany(mappedBy="projectBean" , fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	public Set<ProjectBaselayer> getProjectBaselayers() {
		return projectBaselayers;
	}

	public void setProjectBaselayers(Set<ProjectBaselayer> projectBaselayers) {
		this.projectBaselayers = projectBaselayers;
	}


	@Id
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getActive() {
		return this.active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}


	public String getActivelayer() {
		return this.activelayer;
	}

	public void setActivelayer(String activelayer) {
		this.activelayer = activelayer;
	}


	public String getCopyright() {
		return this.copyright;
	}

	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}


	public Boolean getCosmetic() {
		return this.cosmetic;
	}

	public void setCosmetic(Boolean cosmetic) {
		this.cosmetic = cosmetic;
	}


	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	public String getDisclaimer() {
		return this.disclaimer;
	}

	public void setDisclaimer(String disclaimer) {
		this.disclaimer = disclaimer;
	}


	public Integer getHeight() {
		return this.height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	@SequenceGenerator(name="PROJECT_ID_GENERATOR", sequenceName="project_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PROJECT_ID_GENERATOR")	
	@Column(name="id", insertable = false, updatable = false, unique=true, nullable=false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public String getMaxextent() {
		return this.maxextent;
	}

	public void setMaxextent(String maxextent) {
		this.maxextent = maxextent;
	}


	public Double getMaxresolutions() {
		return this.maxresolutions;
	}

	public void setMaxresolutions(Double maxresolutions) {
		this.maxresolutions = maxresolutions;
	}


	public String getMinextent() {
		return this.minextent;
	}

	public void setMinextent(String minextent) {
		this.minextent = minextent;
	}


	public Double getMinresolutions() {
		return this.minresolutions;
	}

	public void setMinresolutions(Double minresolutions) {
		this.minresolutions = minresolutions;
	}


	public Integer getNumzoomlevels() {
		return this.numzoomlevels;
	}

	public void setNumzoomlevels(Integer numzoomlevels) {
		this.numzoomlevels = numzoomlevels;
	}


	public String getOverlaymap() {
		return this.overlaymap;
	}

	public void setOverlaymap(String overlaymap) {
		this.overlaymap = overlaymap;
	}


	public String getRestrictedextent() {
		return this.restrictedextent;
	}

	public void setRestrictedextent(String restrictedextent) {
		this.restrictedextent = restrictedextent;
	}


	public String getTenantid() {
		return this.tenantid;
	}

	public void setTenantid(String tenantid) {
		this.tenantid = tenantid;
	}


	public byte[] getThumbnail() {
		return this.thumbnail;
	}

	public void setThumbnail(byte[] thumbnail) {
		this.thumbnail = thumbnail;
	}


	public String getWatermask() {
		return this.watermask;
	}

	public void setWatermask(String watermask) {
		this.watermask = watermask;
	}


	public Integer getWidth() {
		return this.width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	@OneToMany(mappedBy="projectBean", fetch = FetchType.EAGER, cascade=CascadeType.ALL )
	@BatchSize(size=16)
	public Set<Overviewmap> getOverviewmaps() {
		return this.overviewmaps;
	}

	public void setOverviewmaps(Set<Overviewmap> overviewmaps) {
		this.overviewmaps = overviewmaps;
	}


	//bi-directional many-to-one association to Printtemplate
	@OneToMany(mappedBy="projectBean", fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	@BatchSize(size=16)

	public Set<Printtemplate> getPrinttemplates() {
		return this.printtemplates;
	}

	public void setPrinttemplates(Set<Printtemplate> printtemplates) {
		this.printtemplates = printtemplates;
	}


	//bi-directional many-to-one association to Outputformat
	@ManyToOne
	@JoinColumn(name="outputformat")
	@BatchSize(size=16)
	public Outputformat getOutputformat() {
		return this.outputformat;
	}

	public void setOutputformat(Outputformat outputformat) {
		this.outputformat = outputformat;
	}


	//bi-directional many-to-one association to Projection
	@ManyToOne
	@JoinColumn(name="projection")
	@BatchSize(size=16)
	public Projection getProjection() {
		return this.projection;
	}

	public void setProjection(Projection projection) {
		this.projection = projection;
	}


	//bi-directional many-to-one association to Projection
	@ManyToOne
	@JoinColumn(name="displayprojection")
	@BatchSize(size=16)
	public Projection getDisplayProjection() {
		return this.displayProjection;
	}

	public void setDisplayProjection(Projection displayProjection) {
		this.displayProjection = displayProjection;
	}



	//Uni-directional many-to-one association to Unit
	@ManyToOne
	@JoinColumn(name="unit")
	@BatchSize(size=16)
	public Unit getUnit() {
		return this.unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}


	//bi-directional many-to-one association to ProjectLayergroup
	//@JsonIgnore
	@OneToMany(mappedBy="projectBean" , fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	@BatchSize(size=16)

	@javax.persistence.OrderBy("grouporder")
	public Set<ProjectLayergroup> getProjectLayergroups() {
		return this.projectLayergroups;
	}

	public void setProjectLayergroups(Set<ProjectLayergroup> projectLayergroups) {
		this.projectLayergroups = projectLayergroups;
	}


	//bi-directional many-to-one association to Savedquery	
	@OneToMany(mappedBy = "projectBean", fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	@BatchSize(size=16)
	public Set<Savedquery> getSavedqueries() {
		return this.savedqueries;
	}

	public void setSavedqueries(Set<Savedquery> savedqueries) {
		this.savedqueries = savedqueries;
	}


	//bi-directional many-to-one association to UserProject
	@JsonIgnore
	@OneToMany(mappedBy="projectBean", fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	@BatchSize(size=16)
	public Set<UserProject> getUserProjects() {
		return this.userProjects;
	}

	public void setUserProjects(Set<UserProject> userProjects) {
		this.userProjects = userProjects;
	}

	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	//bi-directional many-to-one association to ProjectArea
	@OneToMany(fetch = FetchType.EAGER , cascade=CascadeType.ALL)
	@JoinColumn(name="name",referencedColumnName="name")
	public List<ProjectArea> getProjectAreas() {
		return projectAreas;
	}

	public void setProjectAreas(List<ProjectArea> projectAreas) {
		this.projectAreas = projectAreas;
	}


}