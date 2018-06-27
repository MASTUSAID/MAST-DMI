package com.rmsi.mast.viewer.dao.hibernate;

import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.hibernate.GenericHibernateDAO;
import com.rmsi.mast.studio.domain.LaLease;
import com.rmsi.mast.studio.domain.LaSurrenderLease;
import com.rmsi.mast.studio.domain.SocialTenureRelationship;
import com.rmsi.mast.studio.domain.Status;
import com.rmsi.mast.viewer.dao.LaLeaseDao;

@Repository
public class LaLeaseHibernateDao extends GenericHibernateDAO<LaLease, Integer> implements LaLeaseDao{


	Logger logger = Logger.getLogger(LaLeaseHibernateDao.class);
	
	@Override
	public LaLease saveLease(LaLease laLease) {
		try {
            return makePersistent(laLease);

        } catch (Exception ex) {
            logger.error(ex);
            throw ex;
        }
	}

	@Override
	public LaLease getLeaseById(Integer leaseId) {
		
		try {
			Query query = getEntityManager().createQuery("select la from LaLease la where la.leaseid = :leaseid");
			@SuppressWarnings("unchecked")
			List<LaLease> lstLaExtFinancialagency = query.setParameter("leaseid", leaseId).getResultList();

			return lstLaExtFinancialagency.get(0);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public LaSurrenderLease getSurrenderLeaseById(Integer leaseId) {
		
		try {
			Query query = getEntityManager().createQuery("select la from LaSurrenderLease la where la.leaseid = :leaseid");
			@SuppressWarnings("unchecked")
			List<LaSurrenderLease> lstLaExtFinancialagency = query.setParameter("leaseid", leaseId).getResultList();

			return lstLaExtFinancialagency.get(0);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean disablelease(Long personid, Long landid) 
	{
		try {
            Query query = getEntityManager().createQuery("UPDATE LaLease SET isactive = false  where personid = :personid and landid = :landid");
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
	public boolean islandunderlease(Long landid) 
	{
		try {
            Query query = getEntityManager().createQuery("Select su from LaLease su where su.landid = :landid and su.isactive=true");
            List<LaLease> updateFinal = query.setParameter("landid", landid).getResultList();
            
            //int updateFinal = query.setParameter("landid", landid).getResultList();

            if (updateFinal.size() > 0) 
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
	public List<LaLease> getleaseeListByLandId(Long landId) {
		
		try {
			Query query = getEntityManager().createQuery("select la from LaLease la where la.landid = :landid and isactive=true");
			@SuppressWarnings("unchecked")
			List<LaLease> lstLaExtFinancialagency = query.setParameter("landid", landId).getResultList();

			return lstLaExtFinancialagency;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public LaLease getleaseobjbylandandprocessid(Long landId, Long processId) {
		try{
			 String strQuery = "select * from la_lease l left join la_ext_transactiondetails trans on l.leaseid = trans.moduletransid where l.isactive= true and trans.applicationstatusid=1 and l.landid= "+landId+" and trans.processid="+processId;
				List<LaLease> laLease = getEntityManager().createNativeQuery(strQuery,LaLease.class).getResultList();
			if(laLease.size()>0){
			return laLease.get(0);
			}
			return null;
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public List<LaLease> getleaseeListByLandandPersonId(Long landId,
			Long personid) {
		
		try {
			Query query = getEntityManager().createQuery("select la from LaLease la where la.landid = :landid and la.personid = :personid");
			@SuppressWarnings("unchecked")
			List<LaLease> lstLaExtFinancialagency = query.setParameter("landid", landId).setParameter("personid", personid).getResultList();

			return lstLaExtFinancialagency;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<LaLease> getleaseobjbylandandprocessidList(Long landId,
			Long processId) {
		try{
			 String strQuery = "select * from la_lease l left join la_ext_transactiondetails trans on l.leaseid = trans.moduletransid where l.isactive= true and trans.applicationstatusid=1 and l.landid= "+landId+" and trans.processid="+processId;
				List<LaLease> laLease = getEntityManager().createNativeQuery(strQuery,LaLease.class).getResultList();
			if(laLease.size()>0){
			return laLease;
			}
			return null;
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
	}
	

}
