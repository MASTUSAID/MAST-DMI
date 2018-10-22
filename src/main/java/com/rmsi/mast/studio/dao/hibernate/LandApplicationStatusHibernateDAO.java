package com.rmsi.mast.studio.dao.hibernate;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.LandApplicationStatusDAO;
import com.rmsi.mast.studio.domain.LaExtDispute;
import com.rmsi.mast.studio.domain.LaExtPersonLandMapping;
import com.rmsi.mast.studio.domain.LandApplicationStatus;
import com.rmsi.mast.studio.mobile.dao.hibernate.GenderHibernateDao;

@Repository
public class LandApplicationStatusHibernateDAO extends GenericHibernateDAO<LandApplicationStatus, Integer> implements LandApplicationStatusDAO {

	@Override
	public LandApplicationStatus getLandApplicationStatusByLandId(Long landId) {
		try {
			String query = "select s from LandApplicationStatus s where s.landid="+ landId;

			@SuppressWarnings("unchecked")
			LandApplicationStatus landApplicationStatus =(LandApplicationStatus) getEntityManager()
					.createQuery(query).getSingleResult();

			if (null!=landApplicationStatus) {
				return landApplicationStatus;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		return null;
	}

	
	@Override
    public LandApplicationStatus addLandApplicationStatus(LandApplicationStatus spatialUnit) {

        try {
            return makePersistent(spatialUnit);

        } catch (Exception ex) {
            System.out.println("Exception while adding data...." + ex);
            
            throw ex;
        }
    }
	
}
