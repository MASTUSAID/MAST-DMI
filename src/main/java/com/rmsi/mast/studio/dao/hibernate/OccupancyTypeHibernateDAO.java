
package com.rmsi.mast.studio.dao.hibernate;

import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.OccupancyDAO;
import com.rmsi.mast.studio.domain.OccupancyType;


@Repository
public class OccupancyTypeHibernateDAO extends GenericHibernateDAO<OccupancyType, Integer>
		implements OccupancyDAO {
	
	
}
