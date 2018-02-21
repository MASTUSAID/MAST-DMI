package com.rmsi.mast.studio.domain;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.BatchSize;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

/**
 * The persistent class for the project_area database table.
 */
@Entity
@Table(name = "la_ext_projectarea")
public class ProjectArea implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
   	@SequenceGenerator(name="ProjectArea_Sequence",sequenceName="la_ext_projectarea_projectareaid_seq", allocationSize=1) 
   	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="ProjectArea_Sequence") 
    @Column(name="projectareaid")
	private Long projectareaid;

    @Column(name="createdby")
	private Integer createdby;

    @Column(name="createddate")
	private Date createddate;

    @Column(name="description")
	private String description;

//    @Column(name="hierarchyid6")
//	private Integer hierarchyid6;

	@Temporal(TemporalType.DATE)
	private Date initiationdate;

	@Column(name="isactive")
	private Boolean isactive;

	@Column(name="modifiedby")
	private Integer modifiedby;

	@Column(name="modifieddate")
	private Date modifieddate;

//	@Column(name="spatialunitgroupid6")
//	private Integer spatialunitgroupid6;
	
	@Column(name = "vc_meetingdate")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date vcMeetingDate;

	

	//bi-directional many-to-one association to LaSpatialunitgroup
	@ManyToOne
	@JoinColumn(name="spatialunitgroupid1")
	private LaSpatialunitgroup laSpatialunitgroup1;

	//bi-directional many-to-one association to LaSpatialunitgroup
	@ManyToOne
	@JoinColumn(name="spatialunitgroupid2")
	private LaSpatialunitgroup laSpatialunitgroup2;

	//bi-directional many-to-one association to LaSpatialunitgroup
	@ManyToOne
	@JoinColumn(name="spatialunitgroupid3")
	private LaSpatialunitgroup laSpatialunitgroup3;

	//bi-directional many-to-one association to LaSpatialunitgroup
	@ManyToOne
	@JoinColumn(name="spatialunitgroupid4")
	private LaSpatialunitgroup laSpatialunitgroup4;

	//bi-directional many-to-one association to LaSpatialunitgroup
	@ManyToOne
	@JoinColumn(name="spatialunitgroupid5")
	private LaSpatialunitgroup laSpatialunitgroup5;

	//bi-directional many-to-one association to LaSpatialunitgroup
	@ManyToOne
	@JoinColumn(name="spatialunitgroupid6")
	private LaSpatialunitgroup laSpatialunitgroup6;

	//bi-directional many-to-one association to LaSpatialunitgroupHierarchy
	@ManyToOne
	@JoinColumn(name="hierarchyid1")
	private ProjectRegion laSpatialunitgroupHierarchy1;

	//bi-directional many-to-one association to LaSpatialunitgroupHierarchy
	@ManyToOne
	@JoinColumn(name="hierarchyid2")
	private ProjectRegion laSpatialunitgroupHierarchy2;

	//bi-directional many-to-one association to LaSpatialunitgroupHierarchy
	@ManyToOne
	@JoinColumn(name="hierarchyid3")
	private ProjectRegion laSpatialunitgroupHierarchy3;

	//bi-directional many-to-one association to LaSpatialunitgroupHierarchy
	@ManyToOne
	@JoinColumn(name="hierarchyid4")
	private ProjectRegion laSpatialunitgroupHierarchy4;

	//bi-directional many-to-one association to LaSpatialunitgroupHierarchy
	@ManyToOne
	@JoinColumn(name="hierarchyid5")
	private ProjectRegion laSpatialunitgroupHierarchy5;

	//bi-directional many-to-one association to LaSpatialunitgroupHierarchy
	@ManyToOne
	@JoinColumn(name="hierarchyid6")
	private ProjectRegion laSpatialunitgroupHierarchy6;
	
	
	@ManyToOne
    @JoinColumn(name="projectnameid")
	private Project project;
	
	private String authorizedmember;

	private String authorizedmembersignature;

	private String certificatenumber;
	

	private String executiveofficer;

	private String executiveofficersignature;
	

	private String landofficer;

	private String landofficersignature;
	
	private String postalcode;

	
	public ProjectArea(){
		
	}
	

	public Date getVcMeetingDate() {
		return vcMeetingDate;
	}


	public void setVcMeetingDate(Date vcMeetingDate) {
		this.vcMeetingDate = vcMeetingDate;
	}


	public Long getProjectareaid() {
		return projectareaid;
	}

	public void setProjectareaid(Long projectareaid) {
		this.projectareaid = projectareaid;
	}

	public Integer getCreatedby() {
		return createdby;
	}

	public void setCreatedby(Integer createdby) {
		this.createdby = createdby;
	}

	

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

//	public Integer getHierarchyid6() {
//		return hierarchyid6;
//	}
//
//	public void setHierarchyid6(Integer hierarchyid6) {
//		this.hierarchyid6 = hierarchyid6;
//	}

	public Date getInitiationdate() {
		return initiationdate;
	}

	public void setInitiationdate(Date initiationdate) {
		this.initiationdate = initiationdate;
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

//	public Integer getSpatialunitgroupid6() {
//		return spatialunitgroupid6;
//	}
//
//	public void setSpatialunitgroupid6(Integer spatialunitgroupid6) {
//		this.spatialunitgroupid6 = spatialunitgroupid6;
//	}

	

	public LaSpatialunitgroup getLaSpatialunitgroup1() {
		return laSpatialunitgroup1;
	}

	public void setLaSpatialunitgroup1(LaSpatialunitgroup laSpatialunitgroup1) {
		this.laSpatialunitgroup1 = laSpatialunitgroup1;
	}

	public LaSpatialunitgroup getLaSpatialunitgroup2() {
		return laSpatialunitgroup2;
	}

	public void setLaSpatialunitgroup2(LaSpatialunitgroup laSpatialunitgroup2) {
		this.laSpatialunitgroup2 = laSpatialunitgroup2;
	}

	public LaSpatialunitgroup getLaSpatialunitgroup3() {
		return laSpatialunitgroup3;
	}

	public void setLaSpatialunitgroup3(LaSpatialunitgroup laSpatialunitgroup3) {
		this.laSpatialunitgroup3 = laSpatialunitgroup3;
	}

	public LaSpatialunitgroup getLaSpatialunitgroup4() {
		return laSpatialunitgroup4;
	}

	public void setLaSpatialunitgroup4(LaSpatialunitgroup laSpatialunitgroup4) {
		this.laSpatialunitgroup4 = laSpatialunitgroup4;
	}

	public LaSpatialunitgroup getLaSpatialunitgroup5() {
		return laSpatialunitgroup5;
	}

	public void setLaSpatialunitgroup5(LaSpatialunitgroup laSpatialunitgroup5) {
		this.laSpatialunitgroup5 = laSpatialunitgroup5;
	}

	public LaSpatialunitgroup getLaSpatialunitgroup6() {
		return laSpatialunitgroup6;
	}

	public void setLaSpatialunitgroup6(LaSpatialunitgroup laSpatialunitgroup6) {
		this.laSpatialunitgroup6 = laSpatialunitgroup6;
	}

	public ProjectRegion getLaSpatialunitgroupHierarchy1() {
		return laSpatialunitgroupHierarchy1;
	}

	public void setLaSpatialunitgroupHierarchy1(
			ProjectRegion laSpatialunitgroupHierarchy1) {
		this.laSpatialunitgroupHierarchy1 = laSpatialunitgroupHierarchy1;
	}

	public ProjectRegion getLaSpatialunitgroupHierarchy2() {
		return laSpatialunitgroupHierarchy2;
	}

	public void setLaSpatialunitgroupHierarchy2(
			ProjectRegion laSpatialunitgroupHierarchy2) {
		this.laSpatialunitgroupHierarchy2 = laSpatialunitgroupHierarchy2;
	}

	public ProjectRegion getLaSpatialunitgroupHierarchy3() {
		return laSpatialunitgroupHierarchy3;
	}

	public void setLaSpatialunitgroupHierarchy3(
			ProjectRegion laSpatialunitgroupHierarchy3) {
		this.laSpatialunitgroupHierarchy3 = laSpatialunitgroupHierarchy3;
	}

	public ProjectRegion getLaSpatialunitgroupHierarchy4() {
		return laSpatialunitgroupHierarchy4;
	}

	public void setLaSpatialunitgroupHierarchy4(
			ProjectRegion laSpatialunitgroupHierarchy4) {
		this.laSpatialunitgroupHierarchy4 = laSpatialunitgroupHierarchy4;
	}

	public ProjectRegion getLaSpatialunitgroupHierarchy5() {
		return laSpatialunitgroupHierarchy5;
	}

	public void setLaSpatialunitgroupHierarchy5(
			ProjectRegion laSpatialunitgroupHierarchy5) {
		this.laSpatialunitgroupHierarchy5 = laSpatialunitgroupHierarchy5;
	}

	public ProjectRegion getLaSpatialunitgroupHierarchy6() {
		return laSpatialunitgroupHierarchy6;
	}

	public void setLaSpatialunitgroupHierarchy6(
			ProjectRegion laSpatialunitgroupHierarchy6) {
		this.laSpatialunitgroupHierarchy6 = laSpatialunitgroupHierarchy6;
	}


	@JsonIgnore
	public Project getProject() {
		return project;
	}


	public void setProject(Project project) {
		this.project = project;
	}


	public Date getCreateddate() {
		return createddate;
	}


	public void setCreateddate(Date createddate) {
		this.createddate = createddate;
	}


	public String getAuthorizedmember() {
		return authorizedmember;
	}


	public void setAuthorizedmember(String authorizedmember) {
		this.authorizedmember = authorizedmember;
	}


	public String getAuthorizedmembersignature() {
		return authorizedmembersignature;
	}


	public void setAuthorizedmembersignature(String authorizedmembersignature) {
		this.authorizedmembersignature = authorizedmembersignature;
	}


	public String getCertificatenumber() {
		return certificatenumber;
	}


	public void setCertificatenumber(String certificatenumber) {
		this.certificatenumber = certificatenumber;
	}


	public String getExecutiveofficer() {
		return executiveofficer;
	}


	public void setExecutiveofficer(String executiveofficer) {
		this.executiveofficer = executiveofficer;
	}


	public String getExecutiveofficersignature() {
		return executiveofficersignature;
	}


	public void setExecutiveofficersignature(String executiveofficersignature) {
		this.executiveofficersignature = executiveofficersignature;
	}


	public String getLandofficer() {
		return landofficer;
	}


	public void setLandofficer(String landofficer) {
		this.landofficer = landofficer;
	}


	public String getLandofficersignature() {
		return landofficersignature;
	}


	public void setLandofficersignature(String landofficersignature) {
		this.landofficersignature = landofficersignature;
	}


	public String getPostalcode() {
		return postalcode;
	}


	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}
	
	
    
    
    
    
    
   
}
