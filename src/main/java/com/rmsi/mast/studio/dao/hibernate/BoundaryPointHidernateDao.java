package com.rmsi.mast.studio.dao.hibernate;

import com.rmsi.mast.studio.dao.BoundaryPointDao;
import com.rmsi.mast.studio.domain.BoundaryPoint;
import java.util.List;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

@Repository
public class BoundaryPointHidernateDao extends GenericHibernateDAO<BoundaryPoint, Integer> implements BoundaryPointDao {
    private static final Logger logger = Logger.getLogger(BoundaryPointHidernateDao.class);
    
    @Override
    public Boolean delete(BoundaryPoint point) {
        try {
            getEntityManager().remove(point);
            getEntityManager().flush();
            return true;
        } catch (Exception e) {
            logger.error(e);
            return false;
        }
    }

    @Override
    public List<BoundaryPoint> getPointsByProject(Integer projectId){
        try {
            Query query = getEntityManager().createQuery("Select p from BoundaryPoint p where p.isactive=true and p.projectId = :projectId order by p.id");
            return query.setParameter("projectId", projectId).getResultList();
        } catch (Exception e) {
            logger.error(e);
            return null;
        }
    }
    
    @Override
    public BoundaryPoint getPoint(Integer id){
        try {
            return findById(id, false);
        } catch (Exception e) {
            logger.error(e);
            return null;
        }
    }
    
    @Override
    public BoundaryPoint save(BoundaryPoint point) {
        try {
            return makePersistent(point);
        } catch (Exception e) {
            logger.error(e);
            return null;
        }
    }
}
