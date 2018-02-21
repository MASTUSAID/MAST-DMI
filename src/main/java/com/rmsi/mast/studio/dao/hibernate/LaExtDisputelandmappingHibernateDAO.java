package com.rmsi.mast.studio.dao.hibernate;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.LaExtDisputelandmappingDAO;
import com.rmsi.mast.studio.domain.LaExtDisputelandmapping;


@Repository
public class LaExtDisputelandmappingHibernateDAO extends GenericHibernateDAO<LaExtDisputelandmapping, Integer> implements  LaExtDisputelandmappingDAO {

	@SuppressWarnings("unchecked")
	@Override
	public LaExtDisputelandmapping findLaExtDisputelandmappingByPartyId(long partyid) {

		try {
			List<LaExtDisputelandmapping> lstLaExtDisputelandmapping =
					getEntityManager().createQuery("Select d from LaExtDisputelandmapping d where d.partyid = :id").setParameter("id", partyid).getResultList();
				
				if(lstLaExtDisputelandmapping.size() > 0)
					return lstLaExtDisputelandmapping.get(0);
				
				else
					return null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public LaExtDisputelandmapping saveLaExtDisputelandmapping(LaExtDisputelandmapping objLaExtDisputelandmapping) {

		 try {
	            return makePersistent(objLaExtDisputelandmapping);

	        } catch (Exception ex) {
	            throw ex;
	        }
	}

	@Override
	public LaExtDisputelandmapping findLaExtDisputelandmappingById(Integer Id) {	
		
		try {
			@SuppressWarnings("unchecked")
			List<LaExtDisputelandmapping> lstLaExtDisputelandmapping =
					getEntityManager().createQuery("Select d from LaExtDisputelandmapping d where  d.disputelandid = :id").setParameter("id", Id).getResultList();
				
				if(lstLaExtDisputelandmapping.size() > 0)
					return lstLaExtDisputelandmapping.get(0);
				
				else
					return null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<LaExtDisputelandmapping>  findLaExtDisputelandmappingByLandId(Long Id) {

		try {
			@SuppressWarnings("unchecked")
			List<LaExtDisputelandmapping> lstLaExtDisputelandmapping =
					getEntityManager().createQuery("Select d from LaExtDisputelandmapping d where  d.landid = :id and d.isactive = true").setParameter("id", Id).getResultList();
				
				if(lstLaExtDisputelandmapping!=null)
					return lstLaExtDisputelandmapping;
				
				else
					return null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public LaExtDisputelandmapping findLaExtDisputelandmappingByLandIdDisputeIdAndPartyId(Long landId, Integer disputeId, Long partyId) {
		try {
			@SuppressWarnings("unchecked")
			List<LaExtDisputelandmapping> lstLaExtDisputelandmapping =
					getEntityManager().createQuery("Select d from LaExtDisputelandmapping d where d.partyid = :partyId and  d.landid = :landId  and d.laExtDispute.disputeid = :disputeId").
					setParameter("partyId", partyId)
					.setParameter("landId", landId)
					.setParameter("disputeId", disputeId).
					getResultList();
				
				if(lstLaExtDisputelandmapping.size() > 0)
					return lstLaExtDisputelandmapping.get(0);
				
				else
					return null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		
	}

	

}
