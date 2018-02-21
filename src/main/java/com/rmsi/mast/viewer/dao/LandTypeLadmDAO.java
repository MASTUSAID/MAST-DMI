package com.rmsi.mast.viewer.dao;

import java.util.List;

import com.rmsi.mast.studio.dao.GenericDAO;
import com.rmsi.mast.studio.domain.LandType;

/**
 * 
 * @author Abhay.Pandey
 *
 */

public interface LandTypeLadmDAO extends GenericDAO<LandType, Integer> {
	
	List<LandType> getAllLandType();
}