package com.rmsi.mast.studio.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vividsolutions.jts.geom.Geometry;

//import org.postgis.Geometry;

/**
 * Entity implementation class for Entity: SpatialUnit
 *
 */
@Entity
@Table(name = "la_spatialunit_land")
public class SpatialUnit implements Serializable {

/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

//    private static final long serialVersionUID = 1L;
    

	@Id
	@SequenceGenerator(name="pk_la_spatialunit_land",sequenceName="la_spatialunit_land_landid_seq", allocationSize=1) 
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_la_spatialunit_land") 
	private Long landid;

	@Column(name="area")
	private double area;

	@Column(name="createdby")
	private Integer createdby;

	@Temporal(TemporalType.DATE)
	private Date createddate;
	
	@Column(name = "geometry",columnDefinition = "geometry(Geometry,4326)")
	private Geometry geometry;
	
    @Column(name="geometrytype")
	private String geometrytype;

//	@Column(name="hierarchyid6")
//	private Integer hierarchyid6;
	
//	@Column(name="ImeiNo")
//	private String Imei;

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

	private Integer applicationstatusid;
	
	private Integer workflowstatusid;
	
	/*@ManyToOne
	@JoinColumn(name="projectnameid")*/
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
	/*@OneToMany(mappedBy="laSpatialunitLand")
	private List<LaExtDisputelandmapping> laExtDisputelandmappings;
*/
	//bi-directional many-to-one association to LaExtDocumentdetail
//	@OneToMany(mappedBy="laSpatialunitLand")
	@OneToMany(cascade = CascadeType.ALL,fetch=FetchType.EAGER)
	@JoinColumn(name = "landid")
	private List<SourceDocument> laExtDocumentdetails;

	//bi-directional many-to-one association to LaExtPersonlandmapping
	/*@OneToMany(mappedBy="laSpatialunitLand")
	private List<SocialTenureRelationship> laExtPersonlandmappings;
*/
	//bi-directional many-to-one association to LaBaunitLandsoilquality
	@ManyToOne
	@JoinColumn(name="landsoilqualityid")
	private SoilQualityValues laBaunitLandsoilquality;

	//bi-directional many-to-one association to LaBaunitLandtype
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
	
//	 @Formula("get_coordinates(the_geom)")
//   private String geometryformula;
   
	@Column(name="claimtypeid")
	private Integer claimtypeid;
	
	//bi-directional many-to-one association to LaRightLandsharetype
	@ManyToOne
	@JoinColumn(name="landsharetypeid")
	private ShareType laRightLandsharetype;

	//bi-directional many-to-one association to LaRightTenureclass
	/*@ManyToOne
	@JoinColumn(name="tenureclassid")
	private TenureClass laRightTenureclass;
*/
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
    
	@Transient
	private String geomStr;
	
	private Integer proposedused;
	
	private Integer claimno;
	
	public Integer getTenureclassid() {
		return tenureclassid;
	}

	public void setTenureclassid(Integer tenureclassid) {
		this.tenureclassid = tenureclassid;
	}

	private Integer occupancylength;
	
	private Integer tenureclassid;
	
    public SpatialUnit(){
    	
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


//	public String getGeometryformula() {
//		return geometryformula;
//	}
//
//
//	public void setGeometryformula(String geometryformula) {
//		this.geometryformula = geometryformula;
//	}


//	public String getImei() {
//		return Imei;
//	}
//
//
//	public void setImei(String imei) {
//		Imei = imei;
//	}


    
	public Long getLandid() {
		return landid;
	}

	public Integer getOccupancylength() {
		return occupancylength;
	}

	public void setOccupancylength(Integer occupancylength) {
		this.occupancylength = occupancylength;
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

	@JsonIgnore
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

	public Integer getProposedused() {
		return proposedused;
	}

	public void setProposedused(Integer proposedused) {
		this.proposedused = proposedused;
	}

	public Integer getClaimno() {
		return claimno;
	}

	public void setClaimno(Integer claimno) {
		this.claimno = claimno;
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

	public Integer getProjectnameid() {
		return projectnameid;
	}

	public void setProjectnameid(Integer projectnameid) {
		this.projectnameid = projectnameid;
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

/*	public List<LaExtDisputelandmapping> getLaExtDisputelandmappings() {
		return laExtDisputelandmappings;
	}

	public void setLaExtDisputelandmappings(
			List<LaExtDisputelandmapping> laExtDisputelandmappings) {
		this.laExtDisputelandmappings = laExtDisputelandmappings;
	}*/

	public List<SourceDocument> getLaExtDocumentdetails() {
		return laExtDocumentdetails;
	}

	public void setLaExtDocumentdetails(List<SourceDocument> laExtDocumentdetails) {
		this.laExtDocumentdetails = laExtDocumentdetails;
	}

	/*public List<SocialTenureRelationship> getLaExtPersonlandmappings() {
		return laExtPersonlandmappings;
	}

	public void setLaExtPersonlandmappings(
			List<SocialTenureRelationship> laExtPersonlandmappings) {
		this.laExtPersonlandmappings = laExtPersonlandmappings;
	}*/

	public SoilQualityValues getLaBaunitLandsoilquality() {
		return laBaunitLandsoilquality;
	}

	public void setLaBaunitLandsoilquality(SoilQualityValues laBaunitLandsoilquality) {
		this.laBaunitLandsoilquality = laBaunitLandsoilquality;
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

	public Integer getClaimtypeid() {
		return claimtypeid;
	}

	public void setClaimtypeid(Integer claimtypeid) {
		this.claimtypeid = claimtypeid;
	}


	public ShareType getLaRightLandsharetype() {
		return laRightLandsharetype;
	}

	public void setLaRightLandsharetype(ShareType laRightLandsharetype) {
		this.laRightLandsharetype = laRightLandsharetype;
	}

	/*public TenureClass getLaRightTenureclass() {
		return laRightTenureclass;
	}

	public void setLaRightTenureclass(TenureClass laRightTenureclass) {
		this.laRightTenureclass = laRightTenureclass;
	}*/

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

	public String getGeomStr() {
		return geomStr;
	}

	public void setGeomStr(String geomStr) {
		this.geomStr = geomStr;
	}

//    @Id
//    @SequenceGenerator(name = "SPATIAL_UNIT_ID_GENERATOR", sequenceName = "SPATIAL_UNIT_USIN_SEQ", allocationSize = 1)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SPATIAL_UNIT_ID_GENERATOR")
//    private long usin;
//
//    @Column(name = "spatial_unit_type")
//    private String type;
//
//    @Column(name = "project_name")
//    private String project;
//
//    @ManyToOne
//    @JoinColumn(name = "existing_use")
//    private LandUseType existingUse;
//
//    @ManyToOne
//    @JoinColumn(name = "proposed_use")
//    private LandUseType proposedUse;
//
//    @ManyToOne
//    @JoinColumn(name = "type_name")
//    private LandType typeName;
//
//    @Column(name = "identity", unique = true)
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
//    private float perimeter;
//
//    @Column(name = "house_shape")
//    private String houseshape;
//
//    private double area;
//
//    @ManyToOne
//    @JoinColumn(name = "measurement_unit", nullable = false)
//    private Unit measurementUnit;
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
//    //@Type(type = "org.hibernate.spatial.GeometryType")
//    @Column(columnDefinition = "geometry(LineString,4326)")
//    private LineString line;
//
//    //@Type(type = "org.hibernate.spatial.GeometryType")
//    @Column(columnDefinition = "geometry(Point,4326)")
//    private Point point;
//
//    //@Type(type = "org.hibernate.spatial.GeometryType")	
//    @Column(columnDefinition = "geometry(Polygon,4326)")
//    private Polygon polygon;
//
//    @Column(nullable = false)
//    private String gtype;
//
//    @ManyToOne
//    @JoinColumn(name = "current_workflow_status_id", nullable = false)
//    private Status status;
//
//    @Column(name = "workflow_status_update_time", nullable = false)
//    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
//    private Date statusUpdateTime;
//
//    @Column(nullable = false)
//    private int userid;
//
//    @ManyToOne
//    @JoinColumn(name = "userid", insertable = false, updatable = false)
//    private Usertable user;
//
//    @Column(name = "survey_date", nullable = false)
//    @Temporal(javax.persistence.TemporalType.DATE)
//    private Date surveyDate;
//
//    @Column(name = "imei_number", nullable = false)
//    private String imeiNumber;
//
//    //@Type(type = "org.hibernate.spatial.GeometryType")	
//    @Column(name = "the_geom", columnDefinition = "Geometry")
//    private Geometry theGeom;
//
//    @Formula("get_coordinates(the_geom)")
//    private String geometry;
//    
//    private String address1;
//
//    private String address2;
//
//    private String postal_code;
//
//    @Column(name = "neighbor_north")
//    private String neighborNorth;
//
//    @Column(name = "neighbor_south")
//    private String neighborSouth;
//
//    @Column(name = "neighbor_east")
//    private String neighborEast;
//
//    @Column(name = "neighbor_west")
//    private String neighborWest;
//
//    @Column(name = "witness_1")
//    private String witness1;
//
//    @Column(name = "witness_2")
//    private String witness2;
//
//    @Column(name = "witness_3")
//    private String witness3;
//
//    @Column(name = "witness_4")
//    private String witness4;
//
//    @ManyToOne
//    @JoinColumn(name = "quality_of_soil")
//    private SoilQualityValues soilQuality;
//
//    @ManyToOne
//    @JoinColumn(name = "slope")
//    private SlopeValues slope;
//
//    @Column(name = "usin_str")
//    private String usinStr;
//
//    private Boolean active;
//
//    @Column(name = "hamlet_id")
//    private Long hamletId;
//
//    @ManyToOne
//    @JoinColumn(name = "claim_type")
//    private ClaimType claimType;
//    
//    @Column(name = "polygon_number")
//    private String polygonNumber;
//
//    public ClaimType getClaimType() {
//        return claimType;
//    }
//
//    public void setClaimType(ClaimType claimType) {
//        this.claimType = claimType;
//    }
//
//    public String getPolygonNumber() {
//        return polygonNumber;
//    }
//
//    public void setPolygonNumber(String polygonNumber) {
//        this.polygonNumber = polygonNumber;
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
//    public Unit getMeasurementUnit() {
//        return measurementUnit;
//    }
//
//    public void setMeasurementUnit(Unit measurementUnit) {
//        this.measurementUnit = measurementUnit;
//    }
//
//    public Status getStatus() {
//        return status;
//    }
//
//    public void setStatus(Status status) {
//        this.status = status;
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
//    public String getGeometry() {
//        return geometry;
//    }
//
//    public long getUsin() {
//        return usin;
//    }
//
//    public void setUsin(long usin) {
//        this.usin = usin;
//    }
//
//    public void setGeometry(String geometry) {
//        this.geometry = geometry;
//    }
//
//    @JsonIgnore
//    public Geometry getTheGeom() {
//        return theGeom;
//    }
//
//    public void setTheGeom(Geometry theGeom) {
//        this.theGeom = theGeom;
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
//    public LandUseType getExistingUse() {
//        return existingUse;
//    }
//
//    public void setExistingUse(LandUseType existingUse) {
//        this.existingUse = existingUse;
//    }
//
//    public LandUseType getProposedUse() {
//        return proposedUse;
//    }
//
//    public void setProposedUse(LandUseType proposedUse) {
//        this.proposedUse = proposedUse;
//    }
//
//    public LandType getTypeName() {
//        return typeName;
//    }
//
//    public void setTypeName(LandType typeName) {
//        this.typeName = typeName;
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
//    public float getPerimeter() {
//        return perimeter;
//    }
//
//    public void setPerimeter(float perimeter) {
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
//    public double getArea() {
//        return area;
//    }
//
//    public void setArea(double area) {
//        this.area = area;
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
//    @JsonIgnore
//    public LineString getLine() {
//        return line;
//    }
//
//    public void setLine(LineString line) {
//        this.line = line;
//    }
//
//    @JsonIgnore
//    public Point getPoint() {
//        return point;
//    }
//
//    public void setPoint(Point point) {
//        this.point = point;
//    }
//
//    @JsonIgnore
//    public Polygon getPolygon() {
//        return polygon;
//    }
//
//    public void setPolygon(Polygon polygon) {
//        this.polygon = polygon;
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
//    @JsonSerialize(using = JsonDateSerializer.class)
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
//    public Usertable getUser() {
//        return user;
//    }
//
//    public void setUser(Usertable user) {
//        this.user = user;
//    }
//
//    public String getNeighborNorth() {
//        return neighborNorth;
//    }
//
//    public void setNeighborNorth(String neighborNorth) {
//        this.neighborNorth = neighborNorth;
//    }
//
//    public String getNeighborSouth() {
//        return neighborSouth;
//    }
//
//    public void setNeighborSouth(String neighborSouth) {
//        this.neighborSouth = neighborSouth;
//    }
//
//    public String getNeighborEast() {
//        return neighborEast;
//    }
//
//    public void setNeighborEast(String neighborEast) {
//        this.neighborEast = neighborEast;
//    }
//
//    public String getNeighborWest() {
//        return neighborWest;
//    }
//
//    public void setNeighborWest(String neighborWest) {
//        this.neighborWest = neighborWest;
//    }
//
//    public String getWitness1() {
//        return witness1;
//    }
//
//    public void setWitness1(String witness1) {
//        this.witness1 = witness1;
//    }
//
//    public String getWitness2() {
//        return witness2;
//    }
//
//    public void setWitness2(String witness2) {
//        this.witness2 = witness2;
//    }
//
//    public String getWitness3() {
//        return witness3;
//    }
//
//    public void setWitness3(String witness3) {
//        this.witness3 = witness3;
//    }
//
//    public String getWitness4() {
//        return witness4;
//    }
//
//    public void setWitness4(String witness4) {
//        this.witness4 = witness4;
//    }
//
//    public SoilQualityValues getSoilQuality() {
//        return soilQuality;
//    }
//
//    public void setSoilQuality(SoilQualityValues soilQuality) {
//        this.soilQuality = soilQuality;
//    }
//
//    public SlopeValues getSlope() {
//        return slope;
//    }
//
//    public void setSlope(SlopeValues slope) {
//        this.slope = slope;
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
//    public Boolean getActive() {
//        return active;
//    }
//
//    public void setActive(Boolean active) {
//        this.active = active;
//    }

}
