package com.rmsi.mast.custom.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;

import com.rmsi.mast.studio.domain.ResourceAttributeValues;

public class ResourceAttributeValueDto implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long landID;
	private Long projectId;
	Map<Integer, List<ResourceAttributeValues>> map = new HashMap<Integer, List<ResourceAttributeValues>>();
	Map<Integer, List<Object[]>> map2 = new HashMap<Integer, List<Object[]>>();
	
	
	public Long getLandID() {
		return landID;
	}
	public void setLandID(Long landID) {
		this.landID = landID;
	}
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	public Map<Integer, List<ResourceAttributeValues>> getMap() {
		return map;
	}
	public void setMap(Map<Integer, List<ResourceAttributeValues>> map) {
		this.map = map;
	}
	public Map<Integer, List<Object[]>> getMap2() {
		return map2;
	}
	public void setMap2(Map<Integer, List<Object[]>> map2) {
		this.map2 = map2;
	}
	
	
	
	
	
	


}
