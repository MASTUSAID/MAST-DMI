package com.rmsi.mast.studio.dao.hibernate;

import com.rmsi.mast.studio.dao.BoundaryPointDocDao;
import com.rmsi.mast.studio.domain.BoundaryPointDoc;
import java.util.List;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

@Repository
public class BoundaryPointDocHidernateDao extends GenericHibernateDAO<BoundaryPointDoc, Integer> implements BoundaryPointDocDao {
    private static final Logger logger = Logger.getLogger(BoundaryPointDocHidernateDao.class);
    
    @Override
    public Boolean delete(Integer id) {
        try {
            BoundaryPointDoc doc = getDocument(id);
            getEntityManager().remove(doc);
            getEntityManager().flush();
            return true;
        } catch (Exception e) {
            logger.error(e);
            return false;
        }
    }

    @Override
    public List<BoundaryPointDoc> getDocumentsByPoint(Integer pointId){
        try {
            Query query = getEntityManager().createQuery("Select d from BoundaryPointDoc d where d.pointId = :pointId order by d.name");
            return query.setParameter("pointId", pointId).getResultList();
        } catch (Exception e) {
            logger.error(e);
            return null;
        }
    }
    
    @Override
    public BoundaryPointDoc save(BoundaryPointDoc doc) {
        try {
            return makePersistent(doc);
        } catch (Exception e) {
            logger.error(e);
            return null;
        }
    }

    @Override
    public BoundaryPointDoc getDocument(Integer id) {
        try {
            return findById(id, false);
        } catch (Exception e) {
            logger.error(e);
            return null;
        }
    }
}
