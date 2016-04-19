
package com.rmsi.mast.studio.dao.hibernate;

import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.SUnitHistoryDAO;
import com.rmsi.mast.studio.domain.fetch.SpatialUnitStatusHistory;


@Repository
public class SUnitHistoryHibernateDAO extends GenericHibernateDAO<SpatialUnitStatusHistory, Long>
		implements SUnitHistoryDAO {
	
}
