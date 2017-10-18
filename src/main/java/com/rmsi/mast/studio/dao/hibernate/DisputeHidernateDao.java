package com.rmsi.mast.studio.dao.hibernate;

import com.rmsi.mast.studio.dao.DisputeDao;
import com.rmsi.mast.studio.domain.Dispute;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

@Repository
public class DisputeHidernateDao extends GenericHibernateDAO<Dispute, Long> implements DisputeDao {
    private static final Logger logger = Logger.getLogger(DisputeHidernateDao.class);
    
    @Override
    public List<Dispute> findByPropId(Long usin) {
        try {
            Query query = getEntityManager().createQuery("Select d from Dispute d where d.usin = :usin");
            @SuppressWarnings("unchecked")
            List<Dispute> disputes = query.setParameter("usin", usin).getResultList();

            if (disputes.size() > 0) {
                return disputes;
            } else {
                return new ArrayList<Dispute>();
            }
        } catch (Exception e) {
            logger.error(e);
            return new ArrayList<Dispute>();
        }
    }

    @Override
    public List<Dispute> findActiveByPropId(Long usin){
        try {
            Query query = getEntityManager().createQuery("Select d from Dispute d where d.deleted = false and d.usin = :usin order by d.status.code, d.regDate desc");
            @SuppressWarnings("unchecked")
            List<Dispute> disputes = query.setParameter("usin", usin).getResultList();

            if (disputes.size() > 0) {
                return disputes;
            } else {
                return new ArrayList<Dispute>();
            }
        } catch (Exception e) {
            logger.error(e);
            return new ArrayList<Dispute>();
        }
    }
    
    @Override
    public Dispute save(Dispute dispute) {
        try {
            return makePersistent(dispute);
        } catch (Exception e) {
            logger.error(e);
            return null;
        }
    }
}
