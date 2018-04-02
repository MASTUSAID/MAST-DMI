package com.rmsi.mast.viewer.dao.hibernate;

import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.hibernate.GenericHibernateDAO;
import com.rmsi.mast.studio.domain.LaLease;
import com.rmsi.mast.studio.domain.LaMortgage;
import com.rmsi.mast.studio.domain.LaSurrenderLease;
import com.rmsi.mast.viewer.dao.LaLeaseDao;
import com.rmsi.mast.viewer.dao.LaSurrenderLeaseDao;

@Repository
public class LaSurrenderLeaseHibernateDao extends GenericHibernateDAO<LaSurrenderLease, Integer> implements LaSurrenderLeaseDao{


	Logger logger = Logger.getLogger(LaSurrenderLeaseHibernateDao.class);
	
	@Override
	public LaSurrenderLease savesurrenderLease(LaSurrenderLease laLease) {
		try {
            return makePersistent(laLease);

        } catch (Exception ex) {
            logger.error(ex);
            throw ex;
        }
	}

	@Override
	public LaSurrenderLease getSurrenderleaseByLandandProcessId(Long landId,
			Long processId) {
		try{
			 String strQuery = "select * from la_surrenderlease ls left join la_ext_transactiondetails trans on ls.leaseid = trans.moduletransid where ls.isactive= true and trans.applicationstatusid=1 and ls.landid= "+landId+" and trans.processid="+processId;
				List<LaSurrenderLease> laSurenderLeaseobj = getEntityManager().createNativeQuery(strQuery,LaSurrenderLease.class).getResultList();
			if(laSurenderLeaseobj.size()>0){
			return laSurenderLeaseobj.get(0);
			}
			return null;
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public LaSurrenderLease getObjbylandId(Long landId) {
		
		try {
			Query query = getEntityManager().createQuery("select la from LaSurrenderLease la where la.landid = :landid and isactive=true");
			@SuppressWarnings("unchecked")
			List<LaSurrenderLease> laSurrenderLeaseobj = query.setParameter("landid", landId).getResultList();

			return laSurrenderLeaseobj.get(0);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


}
