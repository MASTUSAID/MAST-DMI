package com.rmsi.mast.viewer.dao.hibernate;

import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.rmsi.mast.studio.dao.hibernate.GenericHibernateDAO;
import com.rmsi.mast.studio.domain.LaLease;
import com.rmsi.mast.studio.domain.LaMortgage;
import com.rmsi.mast.studio.domain.fetch.SpatialUnitTable;
import com.rmsi.mast.viewer.dao.LaMortgageDao;

/**
 * 
 * @author Abhay.Pandey
 *
 */

@Repository
public class LaMortgageHibernatedDao extends GenericHibernateDAO<LaMortgage, Integer> implements LaMortgageDao{

	Logger logger = Logger.getLogger(LaMortgageHibernatedDao.class);
	
	@Override
	public LaMortgage saveMortgage(LaMortgage laMortgage) {
		try {
            return makePersistent(laMortgage);

        } catch (Exception ex) {
            logger.error(ex);
            throw ex;
        }
	}

	@Override
	public LaMortgage getMortgageByLandId(Long landId) {
        try {
            Query query = getEntityManager().createQuery("Select lm from LaMortgage lm where lm.landid = :landid and lm.isactive = true");
            @SuppressWarnings("unchecked")
            List<LaMortgage> LaMortgageList = query.setParameter("landid", landId).getResultList();

            if (LaMortgageList.size() > 0) {
                return LaMortgageList.get(0);
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.error(e);
            return null;
        }
    }

	@Override
	public LaMortgage getMortgageByLandIdandprocessId(Long landId,
			Long processId) {
		try{
			 String strQuery = "select * from la_rrr_mortgage lm left join la_ext_transactiondetails trans on lm.mortgageid = trans.moduletransid where lm.isactive= true and trans.applicationstatusid=1 and lm.landid= "+landId+" and trans.processid="+processId;
				List<LaMortgage> laMortgage = getEntityManager().createNativeQuery(strQuery,LaMortgage.class).getResultList();
			if(laMortgage.size()>0){
			return laMortgage.get(0);
			}
			return null;
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
	}
	@Transactional
	@Override
	public boolean disablelease(Long personid, Long landid) {
		try {
            Query query = getEntityManager().createQuery("UPDATE LaMortgage SET isactive = false  where ownerid = :personid and landid = :landid");
            int updateFinal = query.setParameter("personid", personid).setParameter("landid", landid).executeUpdate();

            if (updateFinal > 0) 
            {
                return true;
            }

        } catch (Exception e) {

            logger.error(e);
            return false;
        }

        return false;
		
	}

	@Override
	public LaMortgage getMortgageByLandandTransactionId(Long landId, Integer transactionid) {
		try{
			 String strQuery = "select * from la_rrr_mortgage lm left join la_ext_transactiondetails trans on lm.mortgageid = trans.moduletransid where  lm.landid= "+landId+" and trans.transactionid="+transactionid+" and trans.processid=3";
				List<LaMortgage> laMortgage = getEntityManager().createNativeQuery(strQuery,LaMortgage.class).getResultList();
			if(laMortgage.size()>0){
			return laMortgage.get(0);
			}
			return null;
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public LaMortgage getMortgageByMotgageId(Integer motgageId) {
        try {
            Query query = getEntityManager().createQuery("Select lm from LaMortgage lm where lm.mortgageid = :landid");
            @SuppressWarnings("unchecked")
            List<LaMortgage> LaMortgageList = query.setParameter("landid", motgageId).getResultList();

            if (LaMortgageList.size() > 0) {
                return LaMortgageList.get(0);
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.error(e);
            return null;
        }
    }

}
