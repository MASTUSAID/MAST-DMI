package com.rmsi.mast.studio.dao.hibernate;

import java.util.List;

import com.rmsi.mast.studio.dao.DisputeStatusDao;
import com.rmsi.mast.studio.domain.DisputeStatus;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

@Repository
public class DisputeStatusHibernateDao extends GenericHibernateDAO<DisputeStatus, Integer> implements DisputeStatusDao {
    private static final Logger logger = Logger.getLogger(DisputeStatusHibernateDao.class);

	@Override
	public DisputeStatus getDisputeStatusById(int id) {
		
		String query = "select d from DisputeStatus d where d.disputestatusid = :id";

		@SuppressWarnings("unchecked")
		List<DisputeStatus> dispute_status = getEntityManager().createQuery(query)
				.setParameter("id", id).getResultList();

		if (dispute_status != null && dispute_status.size() > 0) {
			return dispute_status.get(0);
		}else{
			return null;
		}
	}

	@Override
	public List<DisputeStatus> getAllDisputeStatus() {
	
		String query = "select d from DisputeStatus d where d.isactive=true";
		
		try {
			@SuppressWarnings("unchecked")
			List<DisputeStatus> dispute_status = getEntityManager().createQuery(query).getResultList();
			if (dispute_status != null ){
				return dispute_status;
			}else{
				return null;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		
	}
    
    
    
}
