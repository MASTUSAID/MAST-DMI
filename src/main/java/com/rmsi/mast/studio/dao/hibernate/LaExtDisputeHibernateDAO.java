package com.rmsi.mast.studio.dao.hibernate;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.LaExtDisputeDAO;
import com.rmsi.mast.studio.domain.LaExtDispute;


@Repository
public class LaExtDisputeHibernateDAO extends GenericHibernateDAO<LaExtDispute, Integer> implements LaExtDisputeDAO  {

	@Override
	public LaExtDispute findLaExtDisputeByid(Integer disputeid) {
		
		try {
			String query = "select d  from LaExtDispute   d where d.disputeid = :id";
			@SuppressWarnings("unchecked")
			List<LaExtDispute> disputelst = getEntityManager().createQuery(query)
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

	@Override
	public LaExtDispute saveLaExtDispute(LaExtDispute objLaExtDispute) {
		try {
            return makePersistent(objLaExtDispute);

        } catch (Exception ex) {
            throw ex;
        }
	}

	@Override
	public LaExtDispute findLaExtDisputeByLandId(Integer landid) {

		try {
			String query = "select d  from LaExtDispute   d  where d.status =1 and  d.landid = :id";
			@SuppressWarnings("unchecked")
			List<LaExtDispute> disputelst = getEntityManager().createQuery(query)
					.setParameter("id", new Long(landid)).getResultList();

			if (disputelst != null  && disputelst.size()>0 ) {
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
