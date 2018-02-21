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
import javax.persistence.Transient;

import org.postgis.Geometry;

import com.rmsi.mast.studio.domain.ShareType;

@Entity
@Table(name = "la_spatialunit_land")
public class ClaimBasicLand implements Serializable{


    private static final long serialVersionUID = 1L;
    

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
	@Column(name = "geometry")
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
//	@JoinColumn
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
	
	
	 /*@OneToMany(cascade = CascadeType.ALL,fetch=FetchType.EAGER)
	  @JoinColumn(name = "parentuid")
	  private List<ClaimAttributeValue> attributes;
	  
	  @OneToMany(mappedBy="laSpatialunitLand",fetch=FetchType.EAGER, cascade = CascadeType.ALL)
	  List<DisputeBasic> disputes;
	  
	  @OneToMany(mappedBy="laSpatialunitLand",fetch=FetchType.EAGER, cascade = CascadeType.ALL)
	  List<RightBasic> rights;
	  

	@OneToMany(mappedBy="laSpatialunitLand",fetch=FetchType.EAGER, cascade = CascadeType.ALL)
	  List<MediaBasic> media; Vishal*/
	//  
	//  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	//  @JoinColumn(name = "usin")
	//  List<PoiBasic> pois;
	//  
	/*  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	  @JoinColumn(name="landid")
	  List<SpatialunitDeceasedPerson> deceased; Vishal*/

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
//
//	//bi-directional many-to-one association to LaRightClaimtype
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

	//bi-directional many-to-one association to LaSpatialunitgroup
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
    
    
	 public ClaimBasicLand() {
		  }
	 
	/* public List<SpatialunitDeceasedPerson> getDeceased() {
		return deceased;
	}

	public void setDeceased(List<SpatialunitDeceasedPerson> deceased) {
		this.deceased = deceased;
	}

	public List<ClaimAttributeValue> getAttributes() {
			return attributes;
		}

		public void setAttributes(List<ClaimAttributeValue> attributes) {
			this.attributes = attributes;
		}

		public List<DisputeBasic> getDisputes() {
			return disputes;
		}

		public void setDisputes(List<DisputeBasic> disputes) {
			this.disputes = disputes;
		}


		public List<RightBasic> getRights() {
			return rights;
		}


		public void setRights(List<RightBasic> rights) {
			this.rights = rights;
		}


		public List<MediaBasic> getMedia() {
			return media;
		}


		public void setMedia(List<MediaBasic> media) {
			this.media = media;
		}
	 */
	 

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


	public Integer getProjectnameid() {
		return projectnameid;
	}


	public void setProjectnameid(Integer projectnameid) {
		this.projectnameid = projectnameid;
	}


	public Date getSurveydate() {
		return surveydate;
	}


	public void setSurveydate(Date surveydate) {
		this.surveydate = surveydate;
	}


//	public SoilQualityValues getLaBaunitLandsoilquality() {
//		return laBaunitLandsoilquality;
//	}
//
//
//	public void setLaBaunitLandsoilquality(SoilQualityValues laBaunitLandsoilquality) {
//		this.laBaunitLandsoilquality = laBaunitLandsoilquality;
//	}
//
//
//	public LandType getLaBaunitLandtype() {
//		return laBaunitLandtype;
//	}
//
//
//	public void setLaBaunitLandtype(LandType laBaunitLandtype) {
//		this.laBaunitLandtype = laBaunitLandtype;
//	}
//
//
//	public LandUseType getLaBaunitLandusetype() {
//		return laBaunitLandusetype;
//	}
//
//
//	public void setLaBaunitLandusetype(LandUseType laBaunitLandusetype) {
//		this.laBaunitLandusetype = laBaunitLandusetype;
//	}
//
//
//	public SlopeValues getLaExtSlopevalue() {
//		return laExtSlopevalue;
//	}
//
//
//	public void setLaExtSlopevalue(SlopeValues laExtSlopevalue) {
//		this.laExtSlopevalue = laExtSlopevalue;
//	}
//
//
//	public Unit getLaExtUnit() {
//		return laExtUnit;
//	}
//
//
//	public void setLaExtUnit(Unit laExtUnit) {
//		this.laExtUnit = laExtUnit;
//	}
//
//
//	public AcquisitionType getLaRightAcquisitiontype() {
//		return laRightAcquisitiontype;
//	}
//
//
//	public void setLaRightAcquisitiontype(AcquisitionType laRightAcquisitiontype) {
//		this.laRightAcquisitiontype = laRightAcquisitiontype;
//	}
//
//
//	public ClaimType getLaRightClaimtype() {
//		return laRightClaimtype;
//	}
//
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
//
//	public void setLaRightTenureclass(TenureClass laRightTenureclass) {
//		this.laRightTenureclass = laRightTenureclass;
//	}


//	public LaSpatialunitgroup getLaSpatialunitgroup1() {
//		return laSpatialunitgroup1;
//	}
//
//
//	public void setLaSpatialunitgroup1(LaSpatialunitgroup laSpatialunitgroup1) {
//		this.laSpatialunitgroup1 = laSpatialunitgroup1;
//	}
//
//
//	public LaSpatialunitgroup getLaSpatialunitgroup2() {
//		return laSpatialunitgroup2;
//	}
//
//
//	public void setLaSpatialunitgroup2(LaSpatialunitgroup laSpatialunitgroup2) {
//		this.laSpatialunitgroup2 = laSpatialunitgroup2;
//	}
//
//
//	public LaSpatialunitgroup getLaSpatialunitgroup3() {
//		return laSpatialunitgroup3;
//	}
//
//
//	public void setLaSpatialunitgroup3(LaSpatialunitgroup laSpatialunitgroup3) {
//		this.laSpatialunitgroup3 = laSpatialunitgroup3;
//	}
//
//
//	public LaSpatialunitgroup getLaSpatialunitgroup4() {
//		return laSpatialunitgroup4;
//	}
//
//
//	public void setLaSpatialunitgroup4(LaSpatialunitgroup laSpatialunitgroup4) {
//		this.laSpatialunitgroup4 = laSpatialunitgroup4;
//	}
//
//
//	public LaSpatialunitgroup getLaSpatialunitgroup5() {
//		return laSpatialunitgroup5;
//	}
//
//
//	public void setLaSpatialunitgroup5(LaSpatialunitgroup laSpatialunitgroup5) {
//		this.laSpatialunitgroup5 = laSpatialunitgroup5;
//	}
//
//
//	public LaSpatialunitgroup getLaSpatialunitgroup6() {
//		return laSpatialunitgroup6;
//	}
//
//
//	public void setLaSpatialunitgroup6(LaSpatialunitgroup laSpatialunitgroup6) {
//		this.laSpatialunitgroup6 = laSpatialunitgroup6;
//	}
//
//
//	public ProjectRegion getLaSpatialunitgroupHierarchy1() {
//		return laSpatialunitgroupHierarchy1;
//	}
//
//
//	public void setLaSpatialunitgroupHierarchy1(
//			ProjectRegion laSpatialunitgroupHierarchy1) {
//		this.laSpatialunitgroupHierarchy1 = laSpatialunitgroupHierarchy1;
//	}
//
//
//	public ProjectRegion getLaSpatialunitgroupHierarchy2() {
//		return laSpatialunitgroupHierarchy2;
//	}
//
//
//	public void setLaSpatialunitgroupHierarchy2(
//			ProjectRegion laSpatialunitgroupHierarchy2) {
//		this.laSpatialunitgroupHierarchy2 = laSpatialunitgroupHierarchy2;
//	}
//
//
//	public ProjectRegion getLaSpatialunitgroupHierarchy3() {
//		return laSpatialunitgroupHierarchy3;
//	}
//
//
//	public void setLaSpatialunitgroupHierarchy3(
//			ProjectRegion laSpatialunitgroupHierarchy3) {
//		this.laSpatialunitgroupHierarchy3 = laSpatialunitgroupHierarchy3;
//	}
//
//
//	public ProjectRegion getLaSpatialunitgroupHierarchy4() {
//		return laSpatialunitgroupHierarchy4;
//	}
//
//
//	public void setLaSpatialunitgroupHierarchy4(
//			ProjectRegion laSpatialunitgroupHierarchy4) {
//		this.laSpatialunitgroupHierarchy4 = laSpatialunitgroupHierarchy4;
//	}
//
//
//	public ProjectRegion getLaSpatialunitgroupHierarchy5() {
//		return laSpatialunitgroupHierarchy5;
//	}
//
//
//	public void setLaSpatialunitgroupHierarchy5(
//			ProjectRegion laSpatialunitgroupHierarchy5) {
//		this.laSpatialunitgroupHierarchy5 = laSpatialunitgroupHierarchy5;
//	}
//
//
//	public ProjectRegion getLaSpatialunitgroupHierarchy6() {
//		return laSpatialunitgroupHierarchy6;
//	}
//
//
//	public void setLaSpatialunitgroupHierarchy6(
//			ProjectRegion laSpatialunitgroupHierarchy6) {
//		this.laSpatialunitgroupHierarchy6 = laSpatialunitgroupHierarchy6;
//	}
    
    
    
    
    
    
    
    
    
    
    
    
    
    
//    @Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
//	@Column(name="landid")
//	private Long landid;
//
//	@Column(name="area")
//	private double area;
//
//	@Column(name="createdby")
//	private Integer createdby;
//
//	@Temporal(TemporalType.DATE)
//	private Date createddate;
//
//	@Transient
//	@Column(name="geometry")
//	private Geometry geometry;
//
//	@Column(name="geometrytype")
//	private String geometrytype;
//
////	@Column(name="hierarchyid6")
////	private Integer hierarchyid6;
//
//	@Column(name="isactive")
//	private Boolean isactive;
//
//	@Column(name="landno")
//	private String landno;
//
//	@Column(name="modifiedby")
//	private Integer modifiedby;
//
//	@Temporal(TemporalType.DATE)
//	private Date modifieddate;
//
//	@Column(name="neighbor_east")
//	private String neighborEast;
//
//	@Column(name="neighbor_north")
//	private String neighborNorth;
//
//	@Column(name="neighbor_south")
//	private String neighborSouth;
//
//	@Column(name="neighbor_west")
//	private String neighborWest;
//
////	@ManyToOne
////	@JoinColumn(name="projectnameid")
////	private Project projectnameid;
//	
//	
//	@Column(name="projectnameid")
//	private Integer projectnameid;
//
////	@Column(name="spatialunitgroupid6")
////	private Integer spatialunitgroupid6;
//
//	@Temporal(TemporalType.DATE)
//	private Date surveydate;
//	
////  @Column(name = "the_geom", columnDefinition = "Geometry")
////  private Geometry theGeom;
//	
////  @Column(columnDefinition = "geometry(LineString,4326)")
////  private LineString line;
////
////  @Column(columnDefinition = "geometry(Point,4326)")
////  private Point point;
////
//// 
////	
////  @Column(columnDefinition = "geometry(Polygon,4326)")
////  private Polygon polygon;
//
//	//bi-directional many-to-one association to LaExtDisputelandmapping
////	@OneToMany(mappedBy="laSpatialunitLand",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
////	private List<LaExtDisputelandmapping> laExtDisputelandmappings;
////
////	//bi-directional many-to-one association to LaExtDocumentdetail
////	@OneToMany(mappedBy="laSpatialunitLand",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
////	private List<SourceDocument> laExtDocumentdetails;
////
////	//bi-directional many-to-one association to LaExtPersonlandmapping
////	@OneToMany(mappedBy="laSpatialunitLand",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
////	private List<SocialTenureRelationship> laExtPersonlandmappings;
//
//	//bi-directional many-to-one association to LaBaunitLandsoilquality
////	@ManyToOne
////	@JoinColumn(name="landsoilqualityid")
////	private SoilQualityValues laBaunitLandsoilquality;
////
////	//bi-directional many-to-one association to LaBaunitLandtype
////	@ManyToOne
////	@JoinColumn(name="landtypeid")
////	private LandType laBaunitLandtype;
////
////	//bi-directional many-to-one association to LaBaunitLandusetype
////	@ManyToOne
////	@JoinColumn(name="landusetypeid")
////	private LandUseType laBaunitLandusetype;
////
////	//bi-directional many-to-one association to LaExtSlopevalue
////	@ManyToOne
////	@JoinColumn(name="slopevalueid")
////	private SlopeValues laExtSlopevalue;
////
////	//bi-directional many-to-one association to LaExtUnit
////	@ManyToOne
////	@JoinColumn(name="unitid")
////	private Unit laExtUnit;
////
////	
////
////	//bi-directional many-to-one association to LaRightAcquisitiontype
////	@ManyToOne
////	@JoinColumn(name="acquisitiontypeid")
////	private AcquisitionType laRightAcquisitiontype;
////
////	//bi-directional many-to-one association to LaRightClaimtype
////	@ManyToOne
////	@JoinColumn(name="claimtypeid")
////	private ClaimType laRightClaimtype;
////
////	//bi-directional many-to-one association to LaRightLandsharetype
//	@ManyToOne
//	@JoinColumn(name="landsharetypeid")
//	private ShareType laRightLandsharetype;
////
////	//bi-directional many-to-one association to LaRightTenureclass
////	@ManyToOne
////	@JoinColumn(name="tenureclassid")
////	private TenureClass laRightTenureclass;
////
////	//bi-directional many-to-one association to LaSpatialunitgroup
////	@ManyToOne
////	@JoinColumn(name="spatialunitgroupid1")
////	private LaSpatialunitgroup laSpatialunitgroup1;
////
////	//bi-directional many-to-one association to LaSpatialunitgroup
////	@ManyToOne
////	@JoinColumn(name="spatialunitgroupid2")
////	private LaSpatialunitgroup laSpatialunitgroup2;
////
////	//bi-directional many-to-one association to LaSpatialunitgroup
////	@ManyToOne
////	@JoinColumn(name="spatialunitgroupid3")
////	private LaSpatialunitgroup laSpatialunitgroup3;
////
////	//bi-directional many-to-one association to LaSpatialunitgroup
////	@ManyToOne
////	@JoinColumn(name="spatialunitgroupid4")
////	private LaSpatialunitgroup laSpatialunitgroup4;
////
////	//bi-directional many-to-one association to LaSpatialunitgroup
////	@ManyToOne
////	@JoinColumn(name="spatialunitgroupid5")
////	private LaSpatialunitgroup laSpatialunitgroup5;
////
////	//bi-directional many-to-one association to LaSpatialunitgroup
////	@ManyToOne
////	@JoinColumn(name="spatialunitgroupid6")
////	private LaSpatialunitgroup laSpatialunitgroup6;
////
////	//bi-directional many-to-one association to LaSpatialunitgroupHierarchy
////	@ManyToOne
////	@JoinColumn(name="hierarchyid1")
////	private ProjectRegion laSpatialunitgroupHierarchy1;
////
////	//bi-directional many-to-one association to LaSpatialunitgroupHierarchy
////	@ManyToOne
////	@JoinColumn(name="hierarchyid2")
////	private ProjectRegion laSpatialunitgroupHierarchy2;
////
////	//bi-directional many-to-one association to LaSpatialunitgroupHierarchy
////	@ManyToOne
////	@JoinColumn(name="hierarchyid3")
////	private ProjectRegion laSpatialunitgroupHierarchy3;
////
////	//bi-directional many-to-one association to LaSpatialunitgroupHierarchy
////	@ManyToOne
////	@JoinColumn(name="hierarchyid4")
////	private ProjectRegion laSpatialunitgroupHierarchy4;
////
////	//bi-directional many-to-one association to LaSpatialunitgroupHierarchy
////	@ManyToOne
////	@JoinColumn(name="hierarchyid5")
////	private ProjectRegion laSpatialunitgroupHierarchy5;
////
////	//bi-directional many-to-one association to LaSpatialunitgroupHierarchy
////	@ManyToOne
////	@JoinColumn(name="hierarchyid6")
////	private ProjectRegion laSpatialunitgroupHierarchy6;
//	
//  @OneToMany(cascade = CascadeType.ALL)
//  @BatchSize(size=16)
//  @JoinColumn(name = "parentuid")
//  private List<ClaimAttributeValue> attributes;
//  
//  @OneToMany(mappedBy="laSpatialunitLand", cascade = CascadeType.ALL)
//  @BatchSize(size=16)
////  @JoinColumn(name = "usin")
//  List<DisputeBasic> disputes;
//  
//  @OneToMany(mappedBy="laSpatialunitLand", cascade = CascadeType.ALL)
//  @BatchSize(size=16)
////  @JoinColumn(name = "usin")
//  List<RightBasic> rights;
//  
//  @OneToMany(mappedBy="laSpatialunitLand", cascade = CascadeType.ALL)
//  @BatchSize(size=16)
////  @JoinColumn(name = "usin")
//  List<MediaBasic> media;
////  
////  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
////  @JoinColumn(name = "usin")
////  List<PoiBasic> pois;
////  
////  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
////  @JoinColumn(name = "usin")
////  List<SpatialunitDeceasedPerson> deceased;
//  
//  public ClaimBasic() {
//  }
//    
//  
//	
//	
//	
//	
////    public Geometry getTheGeom() {
////		return theGeom;
////	}
////
////
////
////	public void setTheGeom(Geometry theGeom) {
////		this.theGeom = theGeom;
////	}
//
//
//
////	public LineString getLine() {
////    	return line;
////    }
////
////    public void setLine(LineString line) {
////    	this.line = line;
////    }
////
////    public Point getPoint() {
////    	return point;
////    }
////
////    public void setPoint(Point point) {
////    	this.point = point;
////    }
////
////    public Polygon getPolygon() {
////    	return polygon;
////    }
////
////    public void setPolygon(Polygon polygon) {
////    	this.polygon = polygon;
////    }
//
////  public SoilQualityValues getLaBaunitLandsoilquality() {
////		return laBaunitLandsoilquality;
////	}
////
////
////
////
////
////
////	public void setLaBaunitLandsoilquality(SoilQualityValues laBaunitLandsoilquality) {
////		this.laBaunitLandsoilquality = laBaunitLandsoilquality;
////	}
////
////
////
////
////
////
////	public LandType getLaBaunitLandtype() {
////		return laBaunitLandtype;
////	}
////
////
////
////
////
////
////	public void setLaBaunitLandtype(LandType laBaunitLandtype) {
////		this.laBaunitLandtype = laBaunitLandtype;
////	}
////
////
////
////
////
////
////	public LandUseType getLaBaunitLandusetype() {
////		return laBaunitLandusetype;
////	}
////
////
////
////
////
////
////	public void setLaBaunitLandusetype(LandUseType laBaunitLandusetype) {
////		this.laBaunitLandusetype = laBaunitLandusetype;
////	}
////
////
////
////
////
////
////	public SlopeValues getLaExtSlopevalue() {
////		return laExtSlopevalue;
////	}
////
////
////
////
////
////
////	public void setLaExtSlopevalue(SlopeValues laExtSlopevalue) {
////		this.laExtSlopevalue = laExtSlopevalue;
////	}
////
////
////
////
////
////
////	public Unit getLaExtUnit() {
////		return laExtUnit;
////	}
////
////
////
////
////
////
////	public void setLaExtUnit(Unit laExtUnit) {
////		this.laExtUnit = laExtUnit;
////	}
////  
////  
//	public List<ClaimAttributeValue> getAttributes() {
//	return attributes;
//}
//
//
//
//
//
//
//public void setAttributes(List<ClaimAttributeValue> attributes) {
//	this.attributes = attributes;
//}
//
//
//
//
//
//
//public List<DisputeBasic> getDisputes() {
//	return disputes;
//}
//
//
//
//
//
//
//public void setDisputes(List<DisputeBasic> disputes) {
//	this.disputes = disputes;
//}
//
//
//
//
//
//
//public List<RightBasic> getRights() {
//	return rights;
//}
//
//
//
//
//
//
//public void setRights(List<RightBasic> rights) {
//	this.rights = rights;
//}
//
//
//
//
//
//
//public List<MediaBasic> getMedia() {
//	return media;
//}
//
//
//
//
//
//
//public void setMedia(List<MediaBasic> media) {
//	this.media = media;
//}
//
////
////
////
////
////
////public List<PoiBasic> getPois() {
////	return pois;
////}
////
////
////
////
////
////
////public void setPois(List<PoiBasic> pois) {
////	this.pois = pois;
////}
////
////
////
////
////
////
////public List<SpatialunitDeceasedPerson> getDeceased() {
////	return deceased;
////}
////
////
////
////
////
////
////public void setDeceased(List<SpatialunitDeceasedPerson> deceased) {
////	this.deceased = deceased;
////}
////
//
//
//
//
//
//	public Long getLandid() {
//		return landid;
//	}
//
//	public void setProjectnameid(Integer projectnameid) {
//		this.projectnameid = projectnameid;
//	}
//
//
//	public Integer getProjectnameid() {
//		return projectnameid;
//	}
//
//
//	public void setLandid(Long landid) {
//		this.landid = landid;
//	}
//
//	public double getArea() {
//		return area;
//	}
//
//	public void setArea(double area) {
//		this.area = area;
//	}
//
//	public Integer getCreatedby() {
//		return createdby;
//	}
//
//	public void setCreatedby(Integer createdby) {
//		this.createdby = createdby;
//	}
//
//	public Date getCreateddate() {
//		return createddate;
//	}
//
//	public void setCreateddate(Date createddate) {
//		this.createddate = createddate;
//	}
//	
//	@JsonIgnore
//	public Geometry getGeometry() {
//		return geometry;
//	}
//
//	public void setGeometry(Geometry geometry) {
//		this.geometry = geometry;
//	}
//
//	public String getGeometrytype() {
//		return geometrytype;
//	}
//
//	public void setGeometrytype(String geometrytype) {
//		this.geometrytype = geometrytype;
//	}
//
////	public Integer getHierarchyid6() {
////		return hierarchyid6;
////	}
////
////	public void setHierarchyid6(Integer hierarchyid6) {
////		this.hierarchyid6 = hierarchyid6;
////	}
//
//	public Boolean getIsactive() {
//		return isactive;
//	}
//
//	public void setIsactive(Boolean isactive) {
//		this.isactive = isactive;
//	}
//
//	public String getLandno() {
//		return landno;
//	}
//
//	public void setLandno(String landno) {
//		this.landno = landno;
//	}
//
//	public Integer getModifiedby() {
//		return modifiedby;
//	}
//
//	public void setModifiedby(Integer modifiedby) {
//		this.modifiedby = modifiedby;
//	}
//
//	public Date getModifieddate() {
//		return modifieddate;
//	}
//
//	public void setModifieddate(Date modifieddate) {
//		this.modifieddate = modifieddate;
//	}
//
//	public String getNeighborEast() {
//		return neighborEast;
//	}
//
//	public void setNeighborEast(String neighborEast) {
//		this.neighborEast = neighborEast;
//	}
//
//	public String getNeighborNorth() {
//		return neighborNorth;
//	}
//
//	public void setNeighborNorth(String neighborNorth) {
//		this.neighborNorth = neighborNorth;
//	}
//
//	public String getNeighborSouth() {
//		return neighborSouth;
//	}
//
//	public void setNeighborSouth(String neighborSouth) {
//		this.neighborSouth = neighborSouth;
//	}
//
//	public String getNeighborWest() {
//		return neighborWest;
//	}
//
//	public void setNeighborWest(String neighborWest) {
//		this.neighborWest = neighborWest;
//	}
//
//	
//
////	public Integer getSpatialunitgroupid6() {
////		return spatialunitgroupid6;
////	}
////
////	public void setSpatialunitgroupid6(Integer spatialunitgroupid6) {
////		this.spatialunitgroupid6 = spatialunitgroupid6;
////	}
//
//	public Date getSurveydate() {
//		return surveydate;
//	}
//
//	public void setSurveydate(Date surveydate) {
//		this.surveydate = surveydate;
//	}
//
////	public List<LaExtDisputelandmapping> getLaExtDisputelandmappings() {
////		return laExtDisputelandmappings;
////	}
////
////	public void setLaExtDisputelandmappings(
////			List<LaExtDisputelandmapping> laExtDisputelandmappings) {
////		this.laExtDisputelandmappings = laExtDisputelandmappings;
////	}
////
////	public List<SourceDocument> getLaExtDocumentdetails() {
////		return laExtDocumentdetails;
////	}
////
////	public void setLaExtDocumentdetails(List<SourceDocument> laExtDocumentdetails) {
////		this.laExtDocumentdetails = laExtDocumentdetails;
////	}
////
////	public List<SocialTenureRelationship> getLaExtPersonlandmappings() {
////		return laExtPersonlandmappings;
////	}
////
////	public void setLaExtPersonlandmappings(
////			List<SocialTenureRelationship> laExtPersonlandmappings) {
////		this.laExtPersonlandmappings = laExtPersonlandmappings;
////	}
//
////	public SoilQualityValues getLaBaunitLandsoilquality() {
////		return laBaunitLandsoilquality;
////	}
////
////	public void setLaBaunitLandsoilquality(SoilQualityValues laBaunitLandsoilquality) {
////		this.laBaunitLandsoilquality = laBaunitLandsoilquality;
////	}
////
////	public LandType getLaBaunitLandtype() {
////		return laBaunitLandtype;
////	}
////
////	public void setLaBaunitLandtype(LandType laBaunitLandtype) {
////		this.laBaunitLandtype = laBaunitLandtype;
////	}
////
////	public LandUseType getLaBaunitLandusetype() {
////		return laBaunitLandusetype;
////	}
////
////	public void setLaBaunitLandusetype(LandUseType laBaunitLandusetype) {
////		this.laBaunitLandusetype = laBaunitLandusetype;
////	}
////
////	public SlopeValues getLaExtSlopevalue() {
////		return laExtSlopevalue;
////	}
////
////	public void setLaExtSlopevalue(SlopeValues laExtSlopevalue) {
////		this.laExtSlopevalue = laExtSlopevalue;
////	}
////
////	public Unit getLaExtUnit() {
////		return laExtUnit;
////	}
////
////	public void setLaExtUnit(Unit laExtUnit) {
////		this.laExtUnit = laExtUnit;
////	}
//
////	public AcquisitionType getLaRightAcquisitiontype() {
////		return laRightAcquisitiontype;
////	}
////
////	public void setLaRightAcquisitiontype(AcquisitionType laRightAcquisitiontype) {
////		this.laRightAcquisitiontype = laRightAcquisitiontype;
////	}
////
////	public ClaimType getLaRightClaimtype() {
////		return laRightClaimtype;
////	}
////
////	public void setLaRightClaimtype(ClaimType laRightClaimtype) {
////		this.laRightClaimtype = laRightClaimtype;
////	}
////
//	public ShareType getLaRightLandsharetype() {
//		return laRightLandsharetype;
//	}
//
//	public void setLaRightLandsharetype(ShareType laRightLandsharetype) {
//		this.laRightLandsharetype = laRightLandsharetype;
//	}
//
////	public TenureClass getLaRightTenureclass() {
////		return laRightTenureclass;
////	}
////
////	public void setLaRightTenureclass(TenureClass laRightTenureclass) {
////		this.laRightTenureclass = laRightTenureclass;
////	}
////
////	public LaSpatialunitgroup getLaSpatialunitgroup1() {
////		return laSpatialunitgroup1;
////	}
////
////	public void setLaSpatialunitgroup1(LaSpatialunitgroup laSpatialunitgroup1) {
////		this.laSpatialunitgroup1 = laSpatialunitgroup1;
////	}
////
////	public LaSpatialunitgroup getLaSpatialunitgroup2() {
////		return laSpatialunitgroup2;
////	}
////
////	public void setLaSpatialunitgroup2(LaSpatialunitgroup laSpatialunitgroup2) {
////		this.laSpatialunitgroup2 = laSpatialunitgroup2;
////	}
////
////	public LaSpatialunitgroup getLaSpatialunitgroup3() {
////		return laSpatialunitgroup3;
////	}
////
////	public void setLaSpatialunitgroup3(LaSpatialunitgroup laSpatialunitgroup3) {
////		this.laSpatialunitgroup3 = laSpatialunitgroup3;
////	}
////
////	public LaSpatialunitgroup getLaSpatialunitgroup4() {
////		return laSpatialunitgroup4;
////	}
////
////	public void setLaSpatialunitgroup4(LaSpatialunitgroup laSpatialunitgroup4) {
////		this.laSpatialunitgroup4 = laSpatialunitgroup4;
////	}
////
////	public LaSpatialunitgroup getLaSpatialunitgroup5() {
////		return laSpatialunitgroup5;
////	}
////
////	public void setLaSpatialunitgroup5(LaSpatialunitgroup laSpatialunitgroup5) {
////		this.laSpatialunitgroup5 = laSpatialunitgroup5;
////	}
////
////	public LaSpatialunitgroup getLaSpatialunitgroup6() {
////		return laSpatialunitgroup6;
////	}
////
////	public void setLaSpatialunitgroup6(LaSpatialunitgroup laSpatialunitgroup6) {
////		this.laSpatialunitgroup6 = laSpatialunitgroup6;
////	}
////
////	public ProjectRegion getLaSpatialunitgroupHierarchy1() {
////		return laSpatialunitgroupHierarchy1;
////	}
////
////	public void setLaSpatialunitgroupHierarchy1(
////			ProjectRegion laSpatialunitgroupHierarchy1) {
////		this.laSpatialunitgroupHierarchy1 = laSpatialunitgroupHierarchy1;
////	}
////
////	public ProjectRegion getLaSpatialunitgroupHierarchy2() {
////		return laSpatialunitgroupHierarchy2;
////	}
////
////	public void setLaSpatialunitgroupHierarchy2(
////			ProjectRegion laSpatialunitgroupHierarchy2) {
////		this.laSpatialunitgroupHierarchy2 = laSpatialunitgroupHierarchy2;
////	}
////
////	public ProjectRegion getLaSpatialunitgroupHierarchy3() {
////		return laSpatialunitgroupHierarchy3;
////	}
////
////	public void setLaSpatialunitgroupHierarchy3(
////			ProjectRegion laSpatialunitgroupHierarchy3) {
////		this.laSpatialunitgroupHierarchy3 = laSpatialunitgroupHierarchy3;
////	}
////
////	public ProjectRegion getLaSpatialunitgroupHierarchy4() {
////		return laSpatialunitgroupHierarchy4;
////	}
////
////	public void setLaSpatialunitgroupHierarchy4(
////			ProjectRegion laSpatialunitgroupHierarchy4) {
////		this.laSpatialunitgroupHierarchy4 = laSpatialunitgroupHierarchy4;
////	}
////
////	public ProjectRegion getLaSpatialunitgroupHierarchy5() {
////		return laSpatialunitgroupHierarchy5;
////	}
////
////	public void setLaSpatialunitgroupHierarchy5(
////			ProjectRegion laSpatialunitgroupHierarchy5) {
////		this.laSpatialunitgroupHierarchy5 = laSpatialunitgroupHierarchy5;
////	}
////
////	public ProjectRegion getLaSpatialunitgroupHierarchy6() {
////		return laSpatialunitgroupHierarchy6;
////	}
////
////	public void setLaSpatialunitgroupHierarchy6(
////			ProjectRegion laSpatialunitgroupHierarchy6) {
////		this.laSpatialunitgroupHierarchy6 = laSpatialunitgroupHierarchy6;
////	}
////    
//    
//    
//}
//    
//    
////    @Id
////	@GeneratedValue(strategy = GenerationType.AUTO)
////	@Column(name="landid")
////	private Long landid;
////
////	@Column(name="area")
////	private double area;
////
////	@Column(name="createdby")
////	private Integer createdby;
////
////	@Temporal(TemporalType.DATE)
////	private Date createddate;
////
////	@Transient
////	@Column(name="geometry")
////	private Geometry geometry;
////
////	@Column(name="geometrytype")
////	private String geometrytype;
////
//////	@Column(name="hierarchyid6")
//////	private Integer hierarchyid6;
////
////	@Column(name="isactive")
////	private Boolean isactive;
////
////	@Column(name="landno")
////	private String landno;
////
////	@Column(name="modifiedby")
////	private Integer modifiedby;
////
////	@Temporal(TemporalType.DATE)
////	private Date modifieddate;
////
////	@Column(name="neighbor_east")
////	private String neighborEast;
////
////	@Column(name="neighbor_north")
////	private String neighborNorth;
////
////	@Column(name="neighbor_south")
////	private String neighborSouth;
////
////	@Column(name="neighbor_west")
////	private String neighborWest;
////
//////	@ManyToOne
//////	@JoinColumn(name="projectnameid")
//////	private Project projectnameid;
////	
////	
////	@Column(name="projectnameid")
////	private Integer projectnameid;
////
//////	@Column(name="spatialunitgroupid6")
//////	private Integer spatialunitgroupid6;
////
////	@Temporal(TemporalType.DATE)
////	private Date surveydate;
////	
////	@ManyToOne
////	@JoinColumn(name="claimtypeid")
////	private ClaimType laRightClaimtype;
////    
////    
////
//////    @Id
//////    private long usin;
//////
//////    @Column(name="uka_propertyno")
//////    private String uka;
//////
//////    @Column(name = "hamlet_id")
//////    private Long hamletId;
//////
//////    @Column(name="current_workflow_status_id")
//////    private Long statusId;
//////    
//////    @Column(name = "workflow_status_update_time")
//////    @Temporal(javax.persistence.TemporalType.DATE)
//////    private Date statusUpdateTime;
//////    
//////    @Column(name = "polygon_number")
//////    private String claimNumber;
//////    
//////    @Column(name = "survey_date")
//////    @Temporal(javax.persistence.TemporalType.DATE)
//////    private Date surveyDate;
//////    
//////    @Column(name = "imei_number")
//////    private String imei;
//////
//////    @Column(name = "witness_1")
//////    private String adjudicator1;
//////
//////    @Column(name = "witness_2")
//////    private String adjudicator2;
//////
//////    @Column(name = "project_name")
//////    private String projectName;
//////
//////    @Column(name = "claim_type")
//////    private String claimType;
//////    
//////    @Column(name = "gtype")
//////    private String geomType;
//////    
//////    @Formula("get_coordinates(the_geom)")
//////    private String coordinates;
//////    
//////    @Column(name="userid")
//////    private Integer userId;
//////    
//////    @Column
//////    private Boolean active;
////
////    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
////    @JoinColumn(name = "parent_id")
////    private List<ClaimAttributeValue> attributes;
////    
////    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
////    @JoinColumn(name = "usin")
////    List<DisputeBasic> disputes;
////    
////    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
////    @JoinColumn(name = "usin")
////    List<RightBasic> rights;
////    
////    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
////    @JoinColumn(name = "usin")
////    List<MediaBasic> media;
////    
////    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
////    @JoinColumn(name = "usin")
////    List<PoiBasic> pois;
////    
////    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
////    @JoinColumn(name = "usin")
////    List<SpatialunitDeceasedPerson> deceased;
////    
////    public ClaimBasic() {
////    }
////
////    
////    
////    
//////    public long getUsin() {
//////        return usin;
//////    }
//////
//////    public void setUsin(long usin) {
//////        this.usin = usin;
//////    }
//////
//////    public String getUka() {
//////        return uka;
//////    }
//////
//////    public void setUka(String uka) {
//////        this.uka = uka;
//////    }
//////
//////    public Long getHamletId() {
//////        return hamletId;
//////    }
//////
//////    public void setHamletId(Long hamletId) {
//////        this.hamletId = hamletId;
//////    }
//////
//////    public Long getStatusId() {
//////        return statusId;
//////    }
//////
//////    public void setStatusId(Long statusId) {
//////        this.statusId = statusId;
//////    }
//////
//////    public String getClaimNumber() {
//////        return claimNumber;
//////    }
//////
//////    public void setClaimNumber(String claimNumber) {
//////        this.claimNumber = claimNumber;
//////    }
//////
//////    public Date getSurveyDate() {
//////        return surveyDate;
//////    }
//////
//////    public void setSurveyDate(Date surveyDate) {
//////        this.surveyDate = surveyDate;
//////    }
//////
//////    public String getImei() {
//////        return imei;
//////    }
//////
//////    public void setImei(String imei) {
//////        this.imei = imei;
//////    }
//////
//////    public String getAdjudicator1() {
//////        return adjudicator1;
//////    }
//////
//////    public void setAdjudicator1(String adjudicator1) {
//////        this.adjudicator1 = adjudicator1;
//////    }
//////
//////    public String getAdjudicator2() {
//////        return adjudicator2;
//////    }
//////
//////    public void setAdjudicator2(String adjudicator2) {
//////        this.adjudicator2 = adjudicator2;
//////    }
//////
//////    public String getProjectName() {
//////        return projectName;
//////    }
//////
//////    public void setProjectName(String projectName) {
//////        this.projectName = projectName;
//////    }
//////
//////    public String getClaimType() {
//////        return claimType;
//////    }
//////
//////    public void setClaimType(String claimType) {
//////        this.claimType = claimType;
//////    }
//////
//////    public String getGeomType() {
//////        return geomType;
//////    }
//////
//////    public void setGeomType(String geomType) {
//////        this.geomType = geomType;
//////    }
//////
//////    public String getCoordinates() {
//////        return coordinates;
//////    }
//////
//////    public void setCoordinates(String coordinates) {
//////        this.coordinates = coordinates;
//////    }
//////
//////    public Integer getUserId() {
//////        return userId;
//////    }
//////
//////    public void setUserId(Integer userId) {
//////        this.userId = userId;
//////    }
//////
//////    public Boolean getActive() {
//////        return active;
//////    }
//////
//////    public void setActive(Boolean active) {
//////        this.active = active;
//////    }
////    
////    
////    
////
////    public Long getLandid() {
////		return landid;
////	}
////
////
////
////
////	public ClaimType getLaRightClaimtype() {
////		return laRightClaimtype;
////	}
////
////
////
////
////	public void setLaRightClaimtype(ClaimType laRightClaimtype) {
////		this.laRightClaimtype = laRightClaimtype;
////	}
////
////
////
////
////	public void setLandid(Long landid) {
////		this.landid = landid;
////	}
////
////
////
////
////	public double getArea() {
////		return area;
////	}
////
////
////
////
////	public void setArea(double area) {
////		this.area = area;
////	}
////
////
////
////
////	public Integer getCreatedby() {
////		return createdby;
////	}
////
////
////
////
////	public void setCreatedby(Integer createdby) {
////		this.createdby = createdby;
////	}
////
////
////
////
////	public Date getCreateddate() {
////		return createddate;
////	}
////
////
////
////
////	public void setCreateddate(Date createddate) {
////		this.createddate = createddate;
////	}
////
////
////
////
////	public Geometry getGeometry() {
////		return geometry;
////	}
////
////
////
////
////	public void setGeometry(Geometry geometry) {
////		this.geometry = geometry;
////	}
////
////
////
////
////	public String getGeometrytype() {
////		return geometrytype;
////	}
////
////
////
////
////	public void setGeometrytype(String geometrytype) {
////		this.geometrytype = geometrytype;
////	}
////
////
////
////
////	public Boolean getIsactive() {
////		return isactive;
////	}
////
////
////
////
////	public void setIsactive(Boolean isactive) {
////		this.isactive = isactive;
////	}
////
////
////
////
////	public String getLandno() {
////		return landno;
////	}
////
////
////
////
////	public void setLandno(String landno) {
////		this.landno = landno;
////	}
////
////
////
////
////	public Integer getModifiedby() {
////		return modifiedby;
////	}
////
////
////
////
////	public void setModifiedby(Integer modifiedby) {
////		this.modifiedby = modifiedby;
////	}
////
////
////
////
////	public Date getModifieddate() {
////		return modifieddate;
////	}
////
////
////
////
////	public void setModifieddate(Date modifieddate) {
////		this.modifieddate = modifieddate;
////	}
////
////
////
////
////	public String getNeighborEast() {
////		return neighborEast;
////	}
////
////
////
////
////	public void setNeighborEast(String neighborEast) {
////		this.neighborEast = neighborEast;
////	}
////
////
////
////
////	public String getNeighborNorth() {
////		return neighborNorth;
////	}
////
////
////
////
////	public void setNeighborNorth(String neighborNorth) {
////		this.neighborNorth = neighborNorth;
////	}
////
////
////
////
////	public String getNeighborSouth() {
////		return neighborSouth;
////	}
////
////
////
////
////	public void setNeighborSouth(String neighborSouth) {
////		this.neighborSouth = neighborSouth;
////	}
////
////
////
////
////	public String getNeighborWest() {
////		return neighborWest;
////	}
////
////
////
////
////	public void setNeighborWest(String neighborWest) {
////		this.neighborWest = neighborWest;
////	}
////
////
////
////
////	public Integer getProjectnameid() {
////		return projectnameid;
////	}
////
////
////
////
////	public void setProjectnameid(Integer projectnameid) {
////		this.projectnameid = projectnameid;
////	}
////
////
////
////
////	public Date getSurveydate() {
////		return surveydate;
////	}
////
////
////
////
////	public void setSurveydate(Date surveydate) {
////		this.surveydate = surveydate;
////	}
////
////
////
////
////	public List<DisputeBasic> getDisputes() {
////        return disputes;
////    }
////
////    public void setDisputes(List<DisputeBasic> disputes) {
////        this.disputes = disputes;
////    }
////
////    public List<RightBasic> getRights() {
////        return rights;
////    }
////
////    public void setRights(List<RightBasic> rights) {
////        this.rights = rights;
////    }
////
////    public List<MediaBasic> getMedia() {
////        return media;
////    }
////
////    public void setMedia(List<MediaBasic> media) {
////        this.media = media;
////    }
////
////    public List<PoiBasic> getPois() {
////        return pois;
////    }
////
////    public void setPois(List<PoiBasic> pois) {
////        this.pois = pois;
////    }
////
////    public List<SpatialunitDeceasedPerson> getDeceased() {
////        return deceased;
////    }
////
////    public void setDeceased(List<SpatialunitDeceasedPerson> deceased) {
////        this.deceased = deceased;
////    }
////
//////    public Date getStatusUpdateTime() {
//////        return statusUpdateTime;
//////    }
//////
//////    public void setStatusUpdateTime(Date statusUpdateTime) {
//////        this.statusUpdateTime = statusUpdateTime;
//////    }
////
////    public List<ClaimAttributeValue> getAttributes() {
////        return attributes;
////    }
////
////    public void setAttributes(List<ClaimAttributeValue> attributes) {
////        this.attributes = attributes;
////    }


}
