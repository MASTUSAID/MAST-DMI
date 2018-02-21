package com.rmsi.mast.studio.dao.hibernate;

import java.util.List;

import com.rmsi.mast.studio.dao.DisputeTypeDao;
import com.rmsi.mast.studio.domain.DisputeType;
import com.rmsi.mast.studio.domain.LaExtDispute;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

@Repository
public class DisputeTypeHibernateDao extends GenericHibernateDAO<DisputeType, Integer> implements DisputeTypeDao {
    private static final Logger logger = Logger.getLogger(DisputeTypeHibernateDao.class);

	@Override
	public DisputeType findLaExtDisputeTypeByid(Integer disputeid) {
		
		try {
			String query = "select d  from DisputeType   d where d.disputetypeid = :id";
			@SuppressWarnings("unchecked")
			List<DisputeType> disputelst = getEntityManager().createQuery(query)
					.setParameter("id", disputeid).getResultList();

			if (disputelst != null && disputelst.size() > 0) {
				return disputelst.get(0);
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
