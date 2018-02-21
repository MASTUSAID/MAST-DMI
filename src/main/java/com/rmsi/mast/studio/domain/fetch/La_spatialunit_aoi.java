package com.rmsi.mast.studio.domain.fetch;

import java.io.Serializable;

public class La_spatialunit_aoi implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer aoiid;
	private Integer userid;
	private Integer projectnameid;
	private Integer applicationstatusid;
	private boolean isactive;
	private String aoiname;
	private String username;
	private String projectName;
	
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
	public boolean isIsactive() {
		return isactive;
	}
	public void setIsactive(boolean isactive) {
		this.isactive = isactive;
	}
	public String getAoiname() {
		return aoiname;
	}
	public void setAoiname(String aoiname) {
		this.aoiname = aoiname;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	
	
	
	
	
}
