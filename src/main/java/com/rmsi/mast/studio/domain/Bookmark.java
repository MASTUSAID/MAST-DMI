package com.rmsi.mast.studio.domain;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.Date;


/**
 * The persistent class for the la_ext_bookmark database table.
 * 
 */
@Entity
@Table(name="la_ext_bookmark")
public class Bookmark implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="LA_EXT_BOOKMARK_BOOKMARKID_GENERATOR",sequenceName="la_ext_bookmark_bookmarkid_seq" ,allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="LA_EXT_BOOKMARK_BOOKMARKID_GENERATOR")
	private Integer bookmarkid;

	private String bookmarkname;

	@Transient
	private String projectName;
	
	private Integer createdby;

	@Temporal( TemporalType.DATE )
	private Date createddate;

	private String description;

	private Boolean isactive;

	private double maxx;

	private double maxy;

	private double minx;

	private double miny;

	private Integer modifiedby;

	@Temporal( TemporalType.DATE )
	private Date modifieddate;

	private Integer projectnameid;
	
	public Bookmark() {
	}

	public Integer getBookmarkid() {
		return bookmarkid;
	}

	public void setBookmarkid(Integer bookmarkid) {
		this.bookmarkid = bookmarkid;
	}

	public String getBookmarkname() {
		return bookmarkname;
	}

	public void setBookmarkname(String bookmarkname) {
		this.bookmarkname = bookmarkname;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getIsactive() {
		return isactive;
	}

	public void setIsactive(Boolean isactive) {
		this.isactive = isactive;
	}

	public double getMaxx() {
		return maxx;
	}

	public void setMaxx(double maxx) {
		this.maxx = maxx;
	}

	public double getMaxy() {
		return maxy;
	}

	public void setMaxy(double maxy) {
		this.maxy = maxy;
	}

	public double getMinx() {
		return minx;
	}

	public void setMinx(double minx) {
		this.minx = minx;
	}

	public double getMiny() {
		return miny;
	}

	public void setMiny(double miny) {
		this.miny = miny;
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

	public Integer getProjectnameid() {
		return projectnameid;
	}

	public void setProjectnameid(Integer projectnameid) {
		this.projectnameid = projectnameid;
	}

	@Transient
	public String getProjectName() {
		return projectName;
	}

	@Transient
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	

}