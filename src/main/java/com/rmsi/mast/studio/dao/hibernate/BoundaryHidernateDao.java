package com.rmsi.mast.studio.dao.hibernate;

import com.rmsi.mast.studio.dao.BoundaryDao;
import com.rmsi.mast.studio.domain.Boundary;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

@Repository
public class BoundaryHidernateDao extends GenericHibernateDAO<Boundary, Integer> implements BoundaryDao {
    private static final Logger logger = Logger.getLogger(BoundaryHidernateDao.class);

    @Override
    public Boundary getBoundaryByProject(Integer projectId) {
        try {
            Query query = getEntityManager().createQuery("Select b from Boundary b where b.isactive=true and b.projectId = :projectId");
            return (Boundary) query.setParameter("projectId", projectId).getSingleResult();
        } catch (Exception e) {
            logger.error(e);
            return null;
        }
    }

    @Override
    public Boundary getBoundary(Integer id) {
        try {
            return findById(id, false);
        } catch (Exception e) {
            logger.error(e);
            return null;
        }
    }

    @Override
    public Boundary save(Boundary boundary) {
        try {
            return makePersistent(boundary);
        } catch (Exception e) {
            logger.error(e);
            return null;
        }
    }

    @Override
    public Boolean delete(Boundary boundary) {
        try {
            getEntityManager().remove(boundary);
            getEntityManager().flush();
            return true;
        } catch (Exception e) {
            logger.error(e);
            return false;
        }
    }
}
