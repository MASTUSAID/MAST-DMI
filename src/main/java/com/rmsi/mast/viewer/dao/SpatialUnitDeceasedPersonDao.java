/**
 * 
 */
package com.rmsi.mast.viewer.dao;

import java.util.List;

import com.rmsi.mast.studio.dao.GenericDAO;
import com.rmsi.mast.studio.domain.fetch.SpatialunitDeceasedPerson;


public interface SpatialUnitDeceasedPersonDao extends GenericDAO<SpatialunitDeceasedPerson, Long> {

	List<SpatialunitDeceasedPerson> findPersonByUsin(Long usin);

	SpatialunitDeceasedPerson addDeceasedPerson(List<SpatialunitDeceasedPerson> deceasedPersonList,
			long usin);
	
}
