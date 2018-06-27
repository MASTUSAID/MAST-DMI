package com.rmsi.mast.viewer.dao.hibernate;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.hibernate.GenericHibernateDAO;
import com.rmsi.mast.studio.dao.hibernate.ProjectLayergroupHibernateDAO;
import com.rmsi.mast.studio.domain.LaExtParcelSplitLand;
import com.rmsi.mast.viewer.dao.LaExtParcelSplitLandDao;

@Repository
public class LaExtParcelSplitLandHibernateDao extends GenericHibernateDAO<LaExtParcelSplitLand, Long> implements LaExtParcelSplitLandDao{

	private static final Logger logger = Logger.getLogger(LaExtParcelSplitLandHibernateDao.class);

	@Override
	public boolean deleteLaExtParcelSplitLandService(Long landid) {
	
		boolean flag =false;
		try{
			Query query = getEntityManager().createQuery(
					"Delete from LaExtParcelSplitLand plg where plg.landid =:id").setParameter("id", landid);
			
			int count = query.executeUpdate();
			if(count>0)
				flag =true;
				
		}catch(Exception e){
			logger.error(e);
			flag =false;
			
		}
		return flag;
		
	}

}
