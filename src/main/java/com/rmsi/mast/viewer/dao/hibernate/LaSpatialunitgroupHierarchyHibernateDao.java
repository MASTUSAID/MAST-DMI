package com.rmsi.mast.viewer.dao.hibernate;

import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.hibernate.GenericHibernateDAO;
import com.rmsi.mast.studio.domain.ProjectRegion;
import com.rmsi.mast.viewer.dao.LaSpatialunitgroupHierarchyDao;

@Repository
public class LaSpatialunitgroupHierarchyHibernateDao extends GenericHibernateDAO<ProjectRegion, Integer>
implements  LaSpatialunitgroupHierarchyDao{
	
	Logger logger = Logger.getLogger(LaSpatialunitgroupHierarchyHibernateDao.class);

	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectRegion> getAllCountry() {
		try {
			String sql = "select pr from ProjectRegion pr where laSpatialunitgroup = 1";
			Query query = getEntityManager().createQuery(sql);
			List<ProjectRegion> lstProjectRegion = query.getResultList();
			return lstProjectRegion;
		} catch (Exception e) {
			logger.error(e);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectRegion> getAllRegion(Integer country_r_id) {
		
		try {
			String sql = "select pr from ProjectRegion pr where uperhierarchyid = " + country_r_id;
			Query query = getEntityManager().createQuery(sql);
			List<ProjectRegion> lstProjectRegion = query.getResultList();
			return lstProjectRegion;
		} catch (Exception e) {
			logger.error(e);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectRegion> getAllProvience(Integer region_r_id) {
		
		try {
			String sql = "select pr from ProjectRegion pr where uperhierarchyid = " + region_r_id + " and spatialunitgroupid =3";
			Query query = getEntityManager().createQuery(sql);
			List<ProjectRegion> lstProjectRegion = query.getResultList();
			return lstProjectRegion;
		} catch (Exception e) {
			logger.error(e);
		}
		return null;
	}

}
