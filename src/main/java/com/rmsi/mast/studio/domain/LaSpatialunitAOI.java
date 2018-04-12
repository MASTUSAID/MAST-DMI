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

import com.vividsolutions.jts.geom.Geometry;


@Entity
@Table(name="la_spatialunit_aoi")
public class LaSpatialunitAOI implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="pk_la_spatialunit_aoi",sequenceName="la_spatialunit_aoi_aoiid_seq", allocationSize=1) 
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_la_spatialunit_aoi") 
	private Integer aoiid;
	
	private Integer userid;
	
	private Integer projectnameid;
	
//	@ManyToOne()
//	@JoinColumn(name="applicationstatusid")
	private Integer applicationstatusid;
	
	private Geometry geometry;
	
	private Boolean isactive;
	
	private Integer createdby;
	
	@Temporal(TemporalType.DATE)
	private Date createddate;
	
	private Integer modifiedby;
	
	@Temporal(TemporalType.DATE)
	private Date modifieddate;
	
	@Transient
	private String geomStr;
	
	
	
	/*@SequenceGenerator(name = "la_spatialunit_aoi_id_SEQ", sequenceName = "la_spatialunit_resource_land_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "la_spatialunit_aoi_id_SEQ")
	@Column(name = "id")
	private Integer id;*/

	@Column(name="ogc_fid")
	private Integer ogc_fid;

	
	
	public LaSpatialunitAOI(){
		
	}


	public String getGeomStr() {
		return geomStr;
	}


	public void setGeomStr(String geomStr) {
		this.geomStr = geomStr;
	}


	public Integer getAoiid() {
		return aoiid;
	}


	public void setAoiid(Integer aoiid) {
		this.aoiid = aoiid;
	}


	public Integer getUserid() {
		return userid;
	}


	public void setUserid(Integer userid) {
		this.userid = userid;
	}


	public Integer getProjectnameid() {
		return projectnameid;
	}


	public void setProjectnameid(Integer projectnameid) {
		this.projectnameid = projectnameid;
	}


	public Integer getApplicationstatusid() {
		return applicationstatusid;
	}


	public void setApplicationstatusid(Integer applicationstatusid) {
		this.applicationstatusid = applicationstatusid;
	}


	public Geometry getGeometry() {
		return geometry;
	}


	public void setGeometry(Geometry geometry) {
		geometry.setSRID(4326);
		this.geometry = geometry;
	}


	public Boolean getIsactive() {
		return isactive;
	}


	public void setIsactive(Boolean isactive) {
		this.isactive = isactive;
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


	/*public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}*/


	public Integer getOgc_fid() {
		return ogc_fid;
	}


	public void setOgc_fid(Integer ogc_fid) {
		this.ogc_fid = ogc_fid;
	}
	
	
	
	
	

}
