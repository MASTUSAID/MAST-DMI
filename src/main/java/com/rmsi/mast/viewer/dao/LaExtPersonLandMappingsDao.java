package com.rmsi.mast.viewer.dao;

import java.util.List;

import com.rmsi.mast.studio.dao.GenericDAO;
import com.rmsi.mast.studio.domain.LaExtPersonLandMapping;

public interface LaExtPersonLandMappingsDao extends GenericDAO<LaExtPersonLandMapping, Integer>{

	public LaExtPersonLandMapping getPersonLandMapDetails(Integer landid);
	public List<LaExtPersonLandMapping> getPersonOfInterst(Integer landid,Integer persontypeid);
	public List<LaExtPersonLandMapping> getPersonLandMapBylandId(Long Id);
	

}
