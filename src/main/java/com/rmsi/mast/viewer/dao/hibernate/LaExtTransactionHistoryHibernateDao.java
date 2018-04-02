package com.rmsi.mast.viewer.dao.hibernate;

import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.hibernate.GenericHibernateDAO;
import com.rmsi.mast.studio.domain.LaExtTransactionHistory;
import com.rmsi.mast.studio.domain.LaLease;
import com.rmsi.mast.studio.domain.LaSurrenderLease;
import com.rmsi.mast.studio.domain.fetch.SpatialUnitTable;
import com.rmsi.mast.viewer.dao.LaExtTransactionHistoryDao;
import com.rmsi.mast.viewer.dao.LaLeaseDao;
import com.rmsi.mast.viewer.dao.LaSurrenderLeaseDao;

@Repository
public class LaExtTransactionHistoryHibernateDao extends GenericHibernateDAO<LaExtTransactionHistory, Integer> implements LaExtTransactionHistoryDao{


	Logger logger = Logger.getLogger(LaSurrenderLeaseHibernateDao.class);
	
	@Override
	public LaExtTransactionHistory saveTransactionHistory(LaExtTransactionHistory latranshist) {
		try {
            return makePersistent(latranshist);

        } catch (Exception ex) {
            logger.error(ex);
            throw ex;
        }
	}

	@Override
	public LaExtTransactionHistory getTransactionHistoryByTransId(
			Integer latranshist) {

		
		 List<LaExtTransactionHistory> historyobj =null;
        try {
            Query query = getEntityManager().createQuery("Select tr from LaExtTransactionHistory tr where tr.transactionid = :transactionid and tr.isactive = true");
            
             historyobj = query.setParameter("transactionid", latranshist).getResultList();
           

            if (historyobj.size() > 0) {
            	 
                return historyobj.get(0);
            }
            else{
            	return null;
            }
        } catch (Exception e) {

            logger.error(e);
            return null;
        }

    }


}
