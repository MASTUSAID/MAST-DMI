package com.rmsi.mast.studio.domain.fetch;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
public class DataCorrectionReport implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@Id
	private Long landId; //
	
	@Temporal(TemporalType.DATE)
	private Date claimdate;
	
	private double  area;//
	private String neighbor_east;//
	private String neighbor_west;//
	private String neighbor_north;//
	private String neighbor_south;	//
	private Integer partyid;
	
	

	private String landno;
	private String county;
	private String region;
	private String province;
	private String commune;
	private String place;
	
	private String  landtype; //
	private String  landusetype; // existing use
	private String claimtype; //
	private String landsharetype;  //
	private String tenureclasstype; //
	private Integer occupancylength;//
	private String projectName; //
	private String proposedused; //
	private Integer claimno; //
	private Integer transactionid;
	private String other_use;
	
	@Transient
	private String landnumber;
	
	public Long getLandId() {
		return landId;
	}
	public void setLandId(Long landId) {
		this.landId = landId;
	}
	
	
	public double getArea() {
		return area;
	}
	public void setArea(double area) {
		this.area = area;
	}
	public String getNeighbor_east() {
		return neighbor_east;
	}
	public void setNeighbor_east(String neighbor_east) {
		this.neighbor_east = neighbor_east;
	}
	public String getNeighbor_west() {
		return neighbor_west;
	}
	public void setNeighbor_west(String neighbor_west) {
		this.neighbor_west = neighbor_west;
	}
	public String getNeighbor_north() {
		return neighbor_north;
	}
	public void setNeighbor_north(String neighbor_north) {
		this.neighbor_north = neighbor_north;
	}
	public String getNeighbor_south() {
		return neighbor_south;
	}
	public void setNeighbor_south(String neighbor_south) {
		this.neighbor_south = neighbor_south;
	}
	
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCommune() {
		return commune;
	}
	public void setCommune(String commune) {
		this.commune = commune;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	
	public String getLandtype() {
		return landtype;
	}
	public void setLandtype(String landtype) {
		this.landtype = landtype;
	}
	public String getLandusetype() {
		return landusetype;
	}
	public void setLandusetype(String landusetype) {
		this.landusetype = landusetype;
	}
	
	public String getClaimtype() {
		return claimtype;
	}
	public void setClaimtype(String claimtype) {
		this.claimtype = claimtype;
	}
	public String getLandsharetype() {
		return landsharetype;
	}
	public void setLandsharetype(String landsharetype) {
		this.landsharetype = landsharetype;
	}
	public String getTenureclasstype() {
		return tenureclasstype;
	}
	public void setTenureclasstype(String tenureclasstype) {
		this.tenureclasstype = tenureclasstype;
	}
	
	public Integer getOccupancylength() {
		return occupancylength;
	}
	public void setOccupancylength(Integer occupancylength) {
		this.occupancylength = occupancylength;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getProposedused() {
		return proposedused;
	}
	public void setProposedused(String proposedused) {
		this.proposedused = proposedused;
	}
	
	public void setClaimno(Integer claimno) {
		this.claimno = claimno;
	}
	public Integer getClaimno() {
		return claimno;
	}
	public String getLandno() {
		return landno;
	}
	public void setLandno(String landno) {
		this.landno = landno;
	}
	public Integer getTransactionid() {
		return transactionid;
	}
	public void setTransactionid(Integer transactionid) {
		this.transactionid = transactionid;
	}
	public Date getClaimdate() {
		return claimdate;
	}
	public void setClaimdate(Date claimdate) {
		this.claimdate = claimdate;
	}
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	public Integer getPartyid() {
		return partyid;
	}
	public void setPartyid(Integer partyid) {
		this.partyid = partyid;
	}
	
	@Transient
	public String getLandnumber() {
		return landnumber;
	}
	
	@Transient
	public void setLandnumber(String landnumber) {
		this.landnumber = landnumber;
	}
	public String getOther_use() {
		return other_use;
	}
	public void setOther_use(String other_use) {
		this.other_use = other_use;
	}
	
		
	
}
