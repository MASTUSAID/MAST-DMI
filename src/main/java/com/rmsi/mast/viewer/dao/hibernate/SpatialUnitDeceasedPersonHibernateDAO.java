package com.rmsi.mast.viewer.dao.hibernate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.hibernate.GenericHibernateDAO;
import com.rmsi.mast.studio.domain.SpatialUnitPersonWithInterest;
import com.rmsi.mast.studio.domain.fetch.SpatialunitDeceasedPerson;
import com.rmsi.mast.viewer.dao.SpatialUnitDeceasedPersonDao;



@Repository

public class SpatialUnitDeceasedPersonHibernateDAO extends GenericHibernateDAO<SpatialunitDeceasedPerson, Long>
implements SpatialUnitDeceasedPersonDao {
	private static final Logger logger = Logger.getLogger(LandRecordsHibernateDAO.class);

	@Override
	public List<SpatialunitDeceasedPerson> findPersonByUsin(Long usin) {
		try {
			Query query = getEntityManager().createQuery("Select sd from SpatialunitDeceasedPerson sd where sd.usin = :usin");
			@SuppressWarnings("unchecked")
			List<SpatialunitDeceasedPerson> spatialUnitDeceasedPerson = query.setParameter("usin", usin).getResultList();


			if(spatialUnitDeceasedPerson.size() > 0){
				return spatialUnitDeceasedPerson;
			}		
			else
			{
				return new ArrayList<SpatialunitDeceasedPerson>();
			}
		} catch (Exception e) {

			logger.error(e);
			return new ArrayList<SpatialunitDeceasedPerson>();
		}
	}

	@Override
	public SpatialunitDeceasedPerson addDeceasedPerson(
			List<SpatialunitDeceasedPerson> deceasedPersonList, long usin) {
		try {
			Iterator<SpatialunitDeceasedPerson> deceasedPersonter = deceasedPersonList
					.iterator();

			SpatialunitDeceasedPerson deceasedPerson = null;

			while (deceasedPersonter.hasNext()) {

				deceasedPerson = deceasedPersonter.next();
				deceasedPerson.setUsin(usin);

				makePersistent(deceasedPerson);

			}
		} catch (Exception ex) {
			logger.error(ex);
			throw ex;
		}
		return null;
	}

		
	
	
}




