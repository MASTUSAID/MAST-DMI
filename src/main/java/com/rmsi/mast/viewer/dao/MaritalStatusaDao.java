package com.rmsi.mast.viewer.dao;

import java.util.List;

import com.rmsi.mast.studio.dao.GenericDAO;
import com.rmsi.mast.studio.domain.MaritalStatus;

/**
 * 
 * @author Abhay.Pandey
 *
 */

public interface MaritalStatusaDao extends GenericDAO<MaritalStatus, Integer> {

	public List<MaritalStatus> getMaritalStatus();
	
	MaritalStatus getMaritalStatusByID(Integer id);
}
