package com.rmsi.mast.viewer.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.hibernate.GenericHibernateDAO;
import com.rmsi.mast.studio.domain.fetch.SpatialunitPersonwithinterest;
import com.rmsi.mast.viewer.dao.SpatialUnitPersonWithInterestDao;

@Repository
public class SpatialUnitPersonWithInterestHibernateDAO extends GenericHibernateDAO<SpatialunitPersonwithinterest, Long>
        implements SpatialUnitPersonWithInterestDao {

    private static final Logger logger = Logger.getLogger(LandRecordsHibernateDAO.class);

    @SuppressWarnings("unchecked")
    @Override
    public List<SpatialunitPersonwithinterest> findByUsin(Long usin) {

        try {

            Query query = getEntityManager().createQuery("Select sp from SpatialunitPersonwithinterest sp where sp.usin = :usin order by sp.id asc ");
            List<SpatialunitPersonwithinterest> personinterest = query.setParameter("usin", usin).getResultList();

            if (personinterest.size() > 0) {
                return personinterest;
            } else {
                return new ArrayList<SpatialunitPersonwithinterest>();
            }
        } catch (Exception e) {

            logger.error(e);
            return new ArrayList<SpatialunitPersonwithinterest>();
        }

    }

}
