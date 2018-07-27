package com.rmsi.mast.studio.domain.fetch;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;
import org.postgis.Geometry;

import com.rmsi.mast.studio.domain.AcquisitionType;
import com.rmsi.mast.studio.domain.ClaimType;
import com.rmsi.mast.studio.domain.LaExtDisputelandmapping;
import com.rmsi.mast.studio.domain.LaSpatialunitgroup;
import com.rmsi.mast.studio.domain.LandType;
import com.rmsi.mast.studio.domain.LandUseType;
import com.rmsi.mast.studio.domain.Project;
import com.rmsi.mast.studio.domain.ProjectRegion;
import com.rmsi.mast.studio.domain.ShareType;
import com.rmsi.mast.studio.domain.SlopeValues;
import com.rmsi.mast.studio.domain.SocialTenureRelationship;
import com.rmsi.mast.studio.domain.SoilQualityValues;
import com.rmsi.mast.studio.domain.SourceDocument;
import com.rmsi.mast.studio.domain.TenureClass;
import com.rmsi.mast.studio.domain.Unit;


@Entity
@Table(name = "la_spatialunit_land")
public class SpatialUnitBasic implements Serializable {

/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

//    private static final long serialVersionUID = 1L;
    

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="landid")
	private Long landid;

	@Column(name="area")
	private double area;

	@Column(name="createdby")
	private Integer createdby;

	@Temporal(TemporalType.DATE)
	private Date createddate;

	@Transient
	@Column(name="geometry")
	private Geometry geometry;

	@Column(name="geometrytype")
	private String geometrytype;

//	@Column(name="hierarchyid6")
//	private Integer hierarchyid6;

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

//	@ManyToOne
//	@JoinColumn(name="projectnameid")
//	private Project projectnameid;
	
	
	@Column(name="projectnameid")
	private Integer projectnameid;

//	@Column(name="spatialunitgroupid6")
//	private Integer spatialunitgroupid6;

	@Temporal(TemporalType.DATE)
	private Date surveydate;
	
//  @Column(name = "the_geom", columnDefinition = "Geometry")
//  private Geometry theGeom;
	
//  @Column(columnDefinition = "geometry(LineString,4326)")
//  private LineString line;
//
//  @Column(columnDefinition = "geometry(Point,4326)")
//  private Point point;
//
// 
//	
//  @Column(columnDefinition = "geometry(Polygon,4326)")
//  private Polygon polygon;

	//bi-directional many-to-one association to LaExtDisputelandmapping
//	@OneToMany(mappedBy="laSpatialunitLand")
//	private List<LaExtDisputelandmapping> laExtDisputelandmappings;
//
//	//bi-directional many-to-one association to LaExtDocumentdetail
//	@OneToMany(mappedBy="laSpatialunitLand")
//	private List<SourceDocument> laExtDocumentdetails;
//
//	//bi-directional many-to-one association to LaExtPersonlandmapping
//	@OneToMany(mappedBy="laSpatialunitLand")
//	private List<SocialTenureRelationship> laExtPersonlandmappings;

	//bi-directional many-to-one association to LaBaunitLandsoilquality
//	@ManyToOne
//	@JoinColumn(name="landsoilqualityid")
//	private SoilQualityValues laBaunitLandsoilquality;
//
//	//bi-directional many-to-one association to LaBaunitLandtype
//	@ManyToOne
//	@JoinColumn(name="landtypeid")
//	private LandType laBaunitLandtype;
//
//	//bi-directional many-to-one association to LaBaunitLandusetype
//	@ManyToOne
//	@JoinColumn(name="landusetypeid")
//	private LandUseType laBaunitLandusetype;
//
//	//bi-directional many-to-one association to LaExtSlopevalue
//	@ManyToOne
//	@JoinColumn(name="slopevalueid")
//	private SlopeValues laExtSlopevalue;
//
//	//bi-directional many-to-one association to LaExtUnit
//	@ManyToOne
//	@JoinColumn(name="unitid")
//	private Unit laExtUnit;
//
//	//bi-directional many-to-one association to LaRightAcquisitiontype
//	@ManyToOne
//	@JoinColumn(name="acquisitiontypeid")
//	private AcquisitionType laRightAcquisitiontype;

	//bi-directional many-to-one association to LaRightClaimtype
//	@ManyToOne
//	@JoinColumn(name="claimtypeid")
//	private ClaimType laRightClaimtype;

	//bi-directional many-to-one association to LaRightLandsharetype
	@ManyToOne
	@JoinColumn(name="landsharetypeid")
	private ShareType laRightLandsharetype;

	//bi-directional many-to-one association to LaRightTenureclass
//	@ManyToOne
//	@JoinColumn(name="tenureclassid")
//	private TenureClass laRightTenureclass;
//
//	//bi-directional many-to-one association to LaSpatialunitgroup
//	@ManyToOne
//	@JoinColumn(name="spatialunitgroupid1")
//	private LaSpatialunitgroup laSpatialunitgroup1;
//
//	//bi-directional many-to-one association to LaSpatialunitgroup
//	@ManyToOne
//	@JoinColumn(name="spatialunitgroupid2")
//	private LaSpatialunitgroup laSpatialunitgroup2;
//
//	//bi-directional many-to-one association to LaSpatialunitgroup
//	@ManyToOne
//	@JoinColumn(name="spatialunitgroupid3")
//	private LaSpatialunitgroup laSpatialunitgroup3;
//
//	//bi-directional many-to-one association to LaSpatialunitgroup
//	@ManyToOne
//	@JoinColumn(name="spatialunitgroupid4")
//	private LaSpatialunitgroup laSpatialunitgroup4;
//
//	//bi-directional many-to-one association to LaSpatialunitgroup
//	@ManyToOne
//	@JoinColumn(name="spatialunitgroupid5")
//	private LaSpatialunitgroup laSpatialunitgroup5;
//
//	//bi-directional many-to-one association to LaSpatialunitgroup
//	@ManyToOne
//	@JoinColumn(name="spatialunitgroupid6")
//	private LaSpatialunitgroup laSpatialunitgroup6;
//
//	//bi-directional many-to-one association to LaSpatialunitgroupHierarchy
//	@ManyToOne
//	@JoinColumn(name="hierarchyid1")
//	private ProjectRegion laSpatialunitgroupHierarchy1;
//
//	//bi-directional many-to-one association to LaSpatialunitgroupHierarchy
//	@ManyToOne
//	@JoinColumn(name="hierarchyid2")
//	private ProjectRegion laSpatialunitgroupHierarchy2;
//
//	//bi-directional many-to-one association to LaSpatialunitgroupHierarchy
//	@ManyToOne
//	@JoinColumn(name="hierarchyid3")
//	private ProjectRegion laSpatialunitgroupHierarchy3;
//
//	//bi-directional many-to-one association to LaSpatialunitgroupHierarchy
//	@ManyToOne
//	@JoinColumn(name="hierarchyid4")
//	private ProjectRegion laSpatialunitgroupHierarchy4;
//
//	//bi-directional many-to-one association to LaSpatialunitgroupHierarchy
//	@ManyToOne
//	@JoinColumn(name="hierarchyid5")
//	private ProjectRegion laSpatialunitgroupHierarchy5;
//
//	//bi-directional many-to-one association to LaSpatialunitgroupHierarchy
//	@ManyToOne
//	@JoinColumn(name="hierarchyid6")
//	private ProjectRegion laSpatialunitgroupHierarchy6;
	
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
    
    public SpatialUnitBasic(){
    	
    }
    
    
//    public Geometry getTheGeom() {
//		return theGeom;
//	}
//
//
//
//	public void setTheGeom(Geometry theGeom) {
//		this.theGeom = theGeom;
//	}



//	public LineString getLine() {
//    	return line;
//    }
//
//    public void setLine(LineString line) {
//    	this.line = line;
//    }
//
//    public Point getPoint() {
//    	return point;
//    }
//
//    public void setPoint(Point point) {
//    	this.point = point;
//    }
//
//    public Polygon getPolygon() {
//    	return polygon;
//    }
//
//    public void setPolygon(Polygon polygon) {
//    	this.polygon = polygon;
//    }


	public Long getLandid() {
		return landid;
	}

	public void setProjectnameid(Integer projectnameid) {
		this.projectnameid = projectnameid;
	}


	public Integer getProjectnameid() {
		return projectnameid;
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

	public Geometry getGeometry() {
		return geometry;
	}

	public void setGeometry(Geometry geometry) {
		this.geometry = geometry;
	}

	public String getGeometrytype() {
		return geometrytype;
	}

	public void setGeometrytype(String geometrytype) {
		this.geometrytype = geometrytype;
	}

//	public Integer getHierarchyid6() {
//		return hierarchyid6;
//	}
//
//	public void setHierarchyid6(Integer hierarchyid6) {
//		this.hierarchyid6 = hierarchyid6;
//	}

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

	

//	public Integer getSpatialunitgroupid6() {
//		return spatialunitgroupid6;
//	}
//
//	public void setSpatialunitgroupid6(Integer spatialunitgroupid6) {
//		this.spatialunitgroupid6 = spatialunitgroupid6;
//	}

	public Date getSurveydate() {
		return surveydate;
	}

	public void setSurveydate(Date surveydate) {
		this.surveydate = surveydate;
	}

//	public List<LaExtDisputelandmapping> getLaExtDisputelandmappings() {
//		return laExtDisputelandmappings;
//	}
//
//	public void setLaExtDisputelandmappings(
//			List<LaExtDisputelandmapping> laExtDisputelandmappings) {
//		this.laExtDisputelandmappings = laExtDisputelandmappings;
//	}
//
//	public List<SourceDocument> getLaExtDocumentdetails() {
//		return laExtDocumentdetails;
//	}
//
//	public void setLaExtDocumentdetails(List<SourceDocument> laExtDocumentdetails) {
//		this.laExtDocumentdetails = laExtDocumentdetails;
//	}
//
//	public List<SocialTenureRelationship> getLaExtPersonlandmappings() {
//		return laExtPersonlandmappings;
//	}
//
//	public void setLaExtPersonlandmappings(
//			List<SocialTenureRelationship> laExtPersonlandmappings) {
//		this.laExtPersonlandmappings = laExtPersonlandmappings;
//	}

//	public SoilQualityValues getLaBaunitLandsoilquality() {
//		return laBaunitLandsoilquality;
//	}
//
//	public void setLaBaunitLandsoilquality(SoilQualityValues laBaunitLandsoilquality) {
//		this.laBaunitLandsoilquality = laBaunitLandsoilquality;
//	}
//
//	public LandType getLaBaunitLandtype() {
//		return laBaunitLandtype;
//	}
//
//	public void setLaBaunitLandtype(LandType laBaunitLandtype) {
//		this.laBaunitLandtype = laBaunitLandtype;
//	}
//
//	public LandUseType getLaBaunitLandusetype() {
//		return laBaunitLandusetype;
//	}
//
//	public void setLaBaunitLandusetype(LandUseType laBaunitLandusetype) {
//		this.laBaunitLandusetype = laBaunitLandusetype;
//	}
//
//	public SlopeValues getLaExtSlopevalue() {
//		return laExtSlopevalue;
//	}
//
//	public void setLaExtSlopevalue(SlopeValues laExtSlopevalue) {
//		this.laExtSlopevalue = laExtSlopevalue;
//	}
//
//	public Unit getLaExtUnit() {
//		return laExtUnit;
//	}
//
//	public void setLaExtUnit(Unit laExtUnit) {
//		this.laExtUnit = laExtUnit;
//	}
//
//	public AcquisitionType getLaRightAcquisitiontype() {
//		return laRightAcquisitiontype;
//	}
//
//	public void setLaRightAcquisitiontype(AcquisitionType laRightAcquisitiontype) {
//		this.laRightAcquisitiontype = laRightAcquisitiontype;
//	}

//	public ClaimType getLaRightClaimtype() {
//		return laRightClaimtype;
//	}
//
//	public void setLaRightClaimtype(ClaimType laRightClaimtype) {
//		this.laRightClaimtype = laRightClaimtype;
//	}

	public ShareType getLaRightLandsharetype() {
		return laRightLandsharetype;
	}

	public void setLaRightLandsharetype(ShareType laRightLandsharetype) {
		this.laRightLandsharetype = laRightLandsharetype;
	}

//	public TenureClass getLaRightTenureclass() {
//		return laRightTenureclass;
//	}
//
//	public void setLaRightTenureclass(TenureClass laRightTenureclass) {
//		this.laRightTenureclass = laRightTenureclass;
//	}
//
//	public LaSpatialunitgroup getLaSpatialunitgroup1() {
//		return laSpatialunitgroup1;
//	}
//
//	public void setLaSpatialunitgroup1(LaSpatialunitgroup laSpatialunitgroup1) {
//		this.laSpatialunitgroup1 = laSpatialunitgroup1;
//	}
//
//	public LaSpatialunitgroup getLaSpatialunitgroup2() {
//		return laSpatialunitgroup2;
//	}
//
//	public void setLaSpatialunitgroup2(LaSpatialunitgroup laSpatialunitgroup2) {
//		this.laSpatialunitgroup2 = laSpatialunitgroup2;
//	}
//
//	public LaSpatialunitgroup getLaSpatialunitgroup3() {
//		return laSpatialunitgroup3;
//	}
//
//	public void setLaSpatialunitgroup3(LaSpatialunitgroup laSpatialunitgroup3) {
//		this.laSpatialunitgroup3 = laSpatialunitgroup3;
//	}
//
//	public LaSpatialunitgroup getLaSpatialunitgroup4() {
//		return laSpatialunitgroup4;
//	}
//
//	public void setLaSpatialunitgroup4(LaSpatialunitgroup laSpatialunitgroup4) {
//		this.laSpatialunitgroup4 = laSpatialunitgroup4;
//	}
//
//	public LaSpatialunitgroup getLaSpatialunitgroup5() {
//		return laSpatialunitgroup5;
//	}
//
//	public void setLaSpatialunitgroup5(LaSpatialunitgroup laSpatialunitgroup5) {
//		this.laSpatialunitgroup5 = laSpatialunitgroup5;
//	}
//
//	public LaSpatialunitgroup getLaSpatialunitgroup6() {
//		return laSpatialunitgroup6;
//	}
//
//	public void setLaSpatialunitgroup6(LaSpatialunitgroup laSpatialunitgroup6) {
//		this.laSpatialunitgroup6 = laSpatialunitgroup6;
//	}
//
//	public ProjectRegion getLaSpatialunitgroupHierarchy1() {
//		return laSpatialunitgroupHierarchy1;
//	}
//
//	public void setLaSpatialunitgroupHierarchy1(
//			ProjectRegion laSpatialunitgroupHierarchy1) {
//		this.laSpatialunitgroupHierarchy1 = laSpatialunitgroupHierarchy1;
//	}
//
//	public ProjectRegion getLaSpatialunitgroupHierarchy2() {
//		return laSpatialunitgroupHierarchy2;
//	}
//
//	public void setLaSpatialunitgroupHierarchy2(
//			ProjectRegion laSpatialunitgroupHierarchy2) {
//		this.laSpatialunitgroupHierarchy2 = laSpatialunitgroupHierarchy2;
//	}
//
//	public ProjectRegion getLaSpatialunitgroupHierarchy3() {
//		return laSpatialunitgroupHierarchy3;
//	}
//
//	public void setLaSpatialunitgroupHierarchy3(
//			ProjectRegion laSpatialunitgroupHierarchy3) {
//		this.laSpatialunitgroupHierarchy3 = laSpatialunitgroupHierarchy3;
//	}
//
//	public ProjectRegion getLaSpatialunitgroupHierarchy4() {
//		return laSpatialunitgroupHierarchy4;
//	}
//
//	public void setLaSpatialunitgroupHierarchy4(
//			ProjectRegion laSpatialunitgroupHierarchy4) {
//		this.laSpatialunitgroupHierarchy4 = laSpatialunitgroupHierarchy4;
//	}
//
//	public ProjectRegion getLaSpatialunitgroupHierarchy5() {
//		return laSpatialunitgroupHierarchy5;
//	}
//
//	public void setLaSpatialunitgroupHierarchy5(
//			ProjectRegion laSpatialunitgroupHierarchy5) {
//		this.laSpatialunitgroupHierarchy5 = laSpatialunitgroupHierarchy5;
//	}
//
//	public ProjectRegion getLaSpatialunitgroupHierarchy6() {
//		return laSpatialunitgroupHierarchy6;
//	}
//
//	public void setLaSpatialunitgroupHierarchy6(
//			ProjectRegion laSpatialunitgroupHierarchy6) {
//		this.laSpatialunitgroupHierarchy6 = laSpatialunitgroupHierarchy6;
//	}

    
    
    
    
    
    
    
    
    

//    @Id
//    private long usin;
//
//    @Column(name = "spatial_unit_type")
//    private String type;
//
//    @Column(name = "project_name")
//    private String project;
//
//    @Column(name = "existing_use")
//    private Integer existingUse;
//
//    @Column(name = "proposed_use")
//    private Integer proposedUse;
//
//    @Column(name = "identity")
//    private String identity;
//
//    @Column(name = "house_type")
//    private String houseType;
//
//    @Column(name = "total_househld_no")
//    private int househidno;
//
//    @Column(name = "other_use_type")
//    private String otherUseType;
//
//    private Double perimeter;
//
//    @Column(name = "house_shape")
//    private String houseshape;
//
//    private Double area;
//
//    @Column(name = "measurement_unit")
//    private String measurementUnit;
//
//    @Column(name = "land_owner")
//    private String landOwner;
//
//    @Column(name = "uka_propertyno", nullable = false)
//    private String propertyno;
//
//    @Column(nullable = false)
//    private String comments;
//
//    @Column(nullable = false)
//    private String gtype;
//
//    @Column(name = "current_workflow_status_id")
//    private Long status;
//
//    @Column(name = "workflow_status_update_time")
//    private Date statusUpdateTime;
//
//    @Column
//    private int userid;
//
//    @Column(name = "survey_date", nullable = false)
//    private Date surveyDate;
//
//    @Column(name = "imei_number", nullable = false)
//    private String imeiNumber;
//
//    private String address1;
//
//    private String address2;
//
//    private String postal_code;
//
//    private String neighbor_north;
//    private String neighbor_south;
//    private String neighbor_east;
//    private String neighbor_west;
//    private String witness_1;
//    private String witness_2;
//    private String witness_3;
//    private String witness_4;
//
//    @Column(name = "usin_str")
//    private String usinStr;
//
//    @Column(name = "quality_of_soil")
//    private Integer soilQualityValues;
//
//    @Column(name = "slope")
//    private Integer slopeValues;
//
//    @Column(name = "type_name")
//    private String landType;
//
//    private Boolean active;
//
//    @Column(name = "hamlet_id")
//    private Long hamletId;
//
//    @Column(name = "claim_type")
//    String claimType;
//    
//    @Column(name = "polygon_number")
//    String claimNumber;
//
//    public long getUsin() {
//        return usin;
//    }
//
//    public void setUsin(long usin) {
//        this.usin = usin;
//    }
//
//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }
//
//    public String getProject() {
//        return project;
//    }
//
//    public void setProject(String project) {
//        this.project = project;
//    }
//
//    public Integer getExistingUse() {
//        return existingUse;
//    }
//
//    public void setExistingUse(Integer existingUse) {
//        this.existingUse = existingUse;
//    }
//
//    public Integer getProposedUse() {
//        return proposedUse;
//    }
//
//    public void setProposedUse(Integer proposedUse) {
//        this.proposedUse = proposedUse;
//    }
//
//    public String getIdentity() {
//        return identity;
//    }
//
//    public void setIdentity(String identity) {
//        this.identity = identity;
//    }
//
//    public String getHouseType() {
//        return houseType;
//    }
//
//    public void setHouseType(String houseType) {
//        this.houseType = houseType;
//    }
//
//    public int getHousehidno() {
//        return househidno;
//    }
//
//    public void setHousehidno(int househidno) {
//        this.househidno = househidno;
//    }
//
//    public String getOtherUseType() {
//        return otherUseType;
//    }
//
//    public void setOtherUseType(String otherUseType) {
//        this.otherUseType = otherUseType;
//    }
//
//    public Double getPerimeter() {
//        return perimeter;
//    }
//
//    public void setPerimeter(Double perimeter) {
//        this.perimeter = perimeter;
//    }
//
//    public String getHouseshape() {
//        return houseshape;
//    }
//
//    public void setHouseshape(String houseshape) {
//        this.houseshape = houseshape;
//    }
//
//    public Double getArea() {
//        return area;
//    }
//
//    public void setArea(Double area) {
//        this.area = area;
//    }
//
//    public String getMeasurementUnit() {
//        return measurementUnit;
//    }
//
//    public void setMeasurementUnit(String measurementUnit) {
//        this.measurementUnit = measurementUnit;
//    }
//
//    public String getLandOwner() {
//        return landOwner;
//    }
//
//    public void setLandOwner(String landOwner) {
//        this.landOwner = landOwner;
//    }
//
//    public String getPropertyno() {
//        return propertyno;
//    }
//
//    public void setPropertyno(String propertyno) {
//        this.propertyno = propertyno;
//    }
//
//    public String getComments() {
//        return comments;
//    }
//
//    public void setComments(String comments) {
//        this.comments = comments;
//    }
//
//    public String getGtype() {
//        return gtype;
//    }
//
//    public void setGtype(String gtype) {
//        this.gtype = gtype;
//    }
//
//    public Long getStatus() {
//        return status;
//    }
//
//    public void setStatus(Long status) {
//        this.status = status;
//    }
//
//    public Date getStatusUpdateTime() {
//        return statusUpdateTime;
//    }
//
//    public void setStatusUpdateTime(Date statusUpdateTime) {
//        this.statusUpdateTime = statusUpdateTime;
//    }
//
//    public int getUserid() {
//        return userid;
//    }
//
//    public void setUserid(int userid) {
//        this.userid = userid;
//    }
//
//    public Date getSurveyDate() {
//        return surveyDate;
//    }
//
//    public void setSurveyDate(Date surveyDate) {
//        this.surveyDate = surveyDate;
//    }
//
//    public String getImeiNumber() {
//        return imeiNumber;
//    }
//
//    public void setImeiNumber(String imeiNumber) {
//        this.imeiNumber = imeiNumber;
//    }
//
//    public String getAddress1() {
//        return address1;
//    }
//
//    public void setAddress1(String address1) {
//        this.address1 = address1;
//    }
//
//    public String getAddress2() {
//        return address2;
//    }
//
//    public void setAddress2(String address2) {
//        this.address2 = address2;
//    }
//
//    public String getPostal_code() {
//        return postal_code;
//    }
//
//    public void setPostal_code(String postal_code) {
//        this.postal_code = postal_code;
//    }
//
//    public String getNeighbor_north() {
//        return neighbor_north;
//    }
//
//    public void setNeighbor_north(String neighbor_north) {
//        this.neighbor_north = neighbor_north;
//    }
//
//    public String getNeighbor_south() {
//        return neighbor_south;
//    }
//
//    public void setNeighbor_south(String neighbor_south) {
//        this.neighbor_south = neighbor_south;
//    }
//
//    public String getNeighbor_east() {
//        return neighbor_east;
//    }
//
//    public void setNeighbor_east(String neighbor_east) {
//        this.neighbor_east = neighbor_east;
//    }
//
//    public String getNeighbor_west() {
//        return neighbor_west;
//    }
//
//    public void setNeighbor_west(String neighbor_west) {
//        this.neighbor_west = neighbor_west;
//    }
//
//    public String getWitness_1() {
//        return witness_1;
//    }
//
//    public void setWitness_1(String witness_1) {
//        this.witness_1 = witness_1;
//    }
//
//    public String getWitness_2() {
//        return witness_2;
//    }
//
//    public void setWitness_2(String witness_2) {
//        this.witness_2 = witness_2;
//    }
//
//    public String getWitness_3() {
//        return witness_3;
//    }
//
//    public void setWitness_3(String witness_3) {
//        this.witness_3 = witness_3;
//    }
//
//    public String getWitness_4() {
//        return witness_4;
//    }
//
//    public void setWitness_4(String witness_4) {
//        this.witness_4 = witness_4;
//    }
//
//    public String getUsinStr() {
//        return usinStr;
//    }
//
//    public void setUsinStr(String usinStr) {
//        this.usinStr = usinStr;
//    }
//
//    public Integer getSoilQualityValues() {
//        return soilQualityValues;
//    }
//
//    public void setSoilQualityValues(Integer soilQualityValues) {
//        this.soilQualityValues = soilQualityValues;
//    }
//
//    public Integer getSlopeValues() {
//        return slopeValues;
//    }
//
//    public void setSlopeValues(Integer slopeValues) {
//        this.slopeValues = slopeValues;
//    }
//
//    public String getLandType() {
//        return landType;
//    }
//
//    public void setLandType(String landType) {
//        this.landType = landType;
//    }
//
//    public Boolean getActive() {
//        return active;
//    }
//
//    public void setActive(Boolean active) {
//        this.active = active;
//    }
//
//    public Long getHamletId() {
//        return hamletId;
//    }
//
//    public void setHamletId(Long hamletId) {
//        this.hamletId = hamletId;
//    }
//
//    public String getClaimType() {
//        return claimType;
//    }
//
//    public void setClaimType(String claimType) {
//        this.claimType = claimType;
//    }
//
//    public String getClaimNumber() {
//        return claimNumber;
//    }
//
//    public void setClaimNumber(String claimNumber) {
//        this.claimNumber = claimNumber;
//    }
}
