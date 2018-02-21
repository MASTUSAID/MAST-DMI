package com.rmsi.mast.viewer.dao;

import java.util.List;

import com.rmsi.mast.studio.dao.GenericDAO;
import com.rmsi.mast.studio.domain.IdType;

/**
 * 
 * @author Abhay.Pandey
 *
 */
public interface IDTypeDAO extends GenericDAO<IdType, Integer>{

	List<IdType> getIDTypeDetails();
	IdType getIDTypeDetailsByID(Integer id);
}
