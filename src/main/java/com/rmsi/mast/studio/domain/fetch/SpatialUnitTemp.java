package com.rmsi.mast.studio.domain.fetch;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.postgis.Geometry;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.rmsi.mast.studio.domain.AcquisitionType;
import com.rmsi.mast.studio.domain.ClaimType;
import com.rmsi.mast.studio.domain.LaSpatialunitgroup;
import com.rmsi.mast.studio.domain.LandType;
import com.rmsi.mast.studio.domain.LandUseType;
import com.rmsi.mast.studio.domain.Project;
import com.rmsi.mast.studio.domain.ProjectHamlet;
import com.rmsi.mast.studio.domain.ProjectRegion;
import com.rmsi.mast.studio.domain.ShareType;
import com.rmsi.mast.studio.domain.SlopeValues;
import com.rmsi.mast.studio.domain.Status;
import com.rmsi.mast.studio.domain.TenureClass;
import com.rmsi.mast.studio.domain.Unit;
import com.rmsi.mast.studio.util.JsonDateSerializer;

@Entity
@Table(name = "la_spatialunit_land")

public class SpatialUnitTemp implements Serializable {

    private static final long serialVersionUID = 1L;

  
    @Id
	@Column(name="landid")
	private Long landid;

	@Column(name="area")
	private double area;

	@Column(name="createdby")
	private Integer createdby;

	@Temporal(TemporalType.DATE)
	private Date createddate;


	@Column(name="geometrytype")
	private String geometrytype;


	@Column(name="isactive")
	private Boolean isactive;

	@Column(name="landno")
	private String landno;

	@Column(name="modifiedby")
	private Integer modifiedby;

	@Temporal(TemporalType.DATE)
	private Date modifieddate;

	@Column(name="neighbor_east")
	private String neighborEast;

	@Column(name="neighbor_north")
	private String neighborNorth;

	@Column(name="neighbor_south")
	private String neighborSouth;

	@Column(name="neighbor_west")
	private String neighborWest;

	
	@Temporal(TemporalType.DATE)
	private Date surveydate;
	
	
	@ManyToOne
	@JoinColumn(name="landtypeid")
	private LandType laBaunitLandtype;

	//bi-directional many-to-one association to LaBaunitLandusetype
	@ManyToOne
	@JoinColumn(name="landusetypeid")
	private LandUseType laBaunitLandusetype;

	//bi-directional many-to-one association to LaExtSlopevalue
	@ManyToOne
	@JoinColumn(name="slopevalueid")
	private SlopeValues laExtSlopevalue;

	//bi-directional many-to-one association to LaExtUnit
	@ManyToOne
	@JoinColumn(name="unitid")
	private Unit laExtUnit;

	//bi-directional many-to-one association to LaRightAcquisitiontype
	@ManyToOne
	@JoinColumn(name="acquisitiontypeid")
	private AcquisitionType laRightAcquisitiontype;

	//bi-directional many-to-one association to LaRightClaimtype
	@ManyToOne
	@JoinColumn(name="claimtypeid")
	private ClaimType laRightClaimtype;

	//bi-directional many-to-one association to LaRightLandsharetype
	@ManyToOne
	@JoinColumn(name="landsharetypeid")
	private ShareType laRightLandsharetype;

	//bi-directional many-to-one association to LaRightTenureclass
	@ManyToOne
	@JoinColumn(name="tenureclassid")
	private TenureClass laRightTenureclass;

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
	
	private Integer projectnameid;
	
	private String other_use;
	
	
	
	
//  @ManyToOne
//	@JoinColumn(name="tenureclassid")
//	private TenureClass laRightTenureclass;
//  
  
	
	 public String getOther_use() {
		return other_use;
	}




	public void setOther_use(String other_use) {
		this.other_use = other_use;
	}

	public Long getLandid() {
		return landid;
	}

	public void setLandid(Long landid) {
		this.landid = landid;
	}

	public double getArea() {
		return area;
	}

	public void setArea(double area) {
		this.area = area;
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

	public String getGeometrytype() {
		return geometrytype;
	}

	public void setGeometrytype(String geometrytype) {
		this.geometrytype = geometrytype;
	}

	public Boolean getIsactive() {
		return isactive;
	}

	public void setIsactive(Boolean isactive) {
		this.isactive = isactive;
	}

	public String getLandno() {
		return landno;
	}

	public void setLandno(String landno) {
		this.landno = landno;
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

	public String getNeighborEast() {
		return neighborEast;
	}

	public void setNeighborEast(String neighborEast) {
		this.neighborEast = neighborEast;
	}

	public String getNeighborNorth() {
		return neighborNorth;
	}

	public void setNeighborNorth(String neighborNorth) {
		this.neighborNorth = neighborNorth;
	}

	public String getNeighborSouth() {
		return neighborSouth;
	}

	public void setNeighborSouth(String neighborSouth) {
		this.neighborSouth = neighborSouth;
	}

	public String getNeighborWest() {
		return neighborWest;
	}

	public void setNeighborWest(String neighborWest) {
		this.neighborWest = neighborWest;
	}

	public Date getSurveydate() {
		return surveydate;
	}

	public void setSurveydate(Date surveydate) {
		this.surveydate = surveydate;
	}

	public LandType getLaBaunitLandtype() {
		return laBaunitLandtype;
	}

	public void setLaBaunitLandtype(LandType laBaunitLandtype) {
		this.laBaunitLandtype = laBaunitLandtype;
	}

	public LandUseType getLaBaunitLandusetype() {
		return laBaunitLandusetype;
	}

	public void setLaBaunitLandusetype(LandUseType laBaunitLandusetype) {
		this.laBaunitLandusetype = laBaunitLandusetype;
	}

	public SlopeValues getLaExtSlopevalue() {
		return laExtSlopevalue;
	}

	public void setLaExtSlopevalue(SlopeValues laExtSlopevalue) {
		this.laExtSlopevalue = laExtSlopevalue;
	}

	public Unit getLaExtUnit() {
		return laExtUnit;
	}

	public void setLaExtUnit(Unit laExtUnit) {
		this.laExtUnit = laExtUnit;
	}

	public AcquisitionType getLaRightAcquisitiontype() {
		return laRightAcquisitiontype;
	}

	public void setLaRightAcquisitiontype(AcquisitionType laRightAcquisitiontype) {
		this.laRightAcquisitiontype = laRightAcquisitiontype;
	}

	public ClaimType getLaRightClaimtype() {
		return laRightClaimtype;
	}

	public void setLaRightClaimtype(ClaimType laRightClaimtype) {
		this.laRightClaimtype = laRightClaimtype;
	}

	public ShareType getLaRightLandsharetype() {
		return laRightLandsharetype;
	}

	public void setLaRightLandsharetype(ShareType laRightLandsharetype) {
		this.laRightLandsharetype = laRightLandsharetype;
	}

	public TenureClass getLaRightTenureclass() {
		return laRightTenureclass;
	}

	public void setLaRightTenureclass(TenureClass laRightTenureclass) {
		this.laRightTenureclass = laRightTenureclass;
	}

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

	public Integer getProjectnameid() {
		return projectnameid;
	}

	public void setProjectnameid(Integer projectnameid) {
		this.projectnameid = projectnameid;
	}
	
	
	
	
}
