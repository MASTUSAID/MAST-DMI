/**
 * 
 */
package com.rmsi.mast.viewer.dao;

import java.util.List;

import com.rmsi.mast.studio.dao.GenericDAO;
import com.rmsi.mast.studio.domain.fetch.SpatialunitPersonadministrator;

/**
 * @author Shruti.Thakur
 *
 */
public interface SpatialUnitPersonAdministratorDao extends GenericDAO<SpatialunitPersonadministrator, Long> {

	List<Long> findAdminIdbyUsin(Long id);

	
	
	
	
}
