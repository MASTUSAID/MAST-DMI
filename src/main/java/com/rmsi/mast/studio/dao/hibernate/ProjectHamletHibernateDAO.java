
package com.rmsi.mast.studio.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.ProjectHamletDAO;
import com.rmsi.mast.studio.domain.ProjectHamlet;
import com.rmsi.mast.viewer.dao.hibernate.LandRecordsHibernateDAO;


@Repository
public class ProjectHamletHibernateDAO extends GenericHibernateDAO<ProjectHamlet, Long>
		implements ProjectHamletDAO {
	private static final Logger logger = Logger.getLogger(LandRecordsHibernateDAO.class);

	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectHamlet> findHamlets(String projname) {

		try {
			Query query = getEntityManager().createQuery("Select ph from ProjectHamlet ph where ph.projectName = :projname");
			List<ProjectHamlet> hamletList = query.setParameter("projname", projname).getResultList();
			

			if(hamletList.size() > 0){
				return hamletList;
			}		
			else
			{
				return null;
			}
		} catch (Exception e) {

			logger.error(e);
			return null;
		}

	}

	@Override
	public void deleteEntries(String projectName) {
		
		try{
			String qry = "Delete from ProjectHamlet ph where ph.projectName =:projectName";
			Query query = getEntityManager().createQuery(qry).setParameter("projectName", projectName);
				int count = query.executeUpdate();
	
		}
		catch(Exception e){
			logger.error(e);
			
	}

		
	}

	@Override
	public long getHamletIdbyCode(String hamletCode,String projectName) {
		try {
			Query query = getEntityManager().createQuery("Select ph.id from ProjectHamlet ph where ph.hamletCode = :hamletCode and ph.projectName=:projectName");
			@SuppressWarnings("unchecked")
			List<Long> hamletList = query.setParameter("hamletCode", hamletCode).setParameter("projectName", projectName).getResultList();
			

			if(hamletList.size() > 0){
				return hamletList.get(0);
			}		
			else
			{
				return 0;
			}
		} catch (Exception e) {

			logger.error(e);
			return 0;
		}
	}

	@Override
	public List<String> getHamletCodesbyProject(String projectName) {
		try {
			Query query = getEntityManager().createQuery("Select ph.hamletCode from ProjectHamlet ph where ph.projectName = :projname");
			List<String> hamletList = query.setParameter("projname", projectName).getResultList();
			

			if(hamletList.size() > 0){
				return hamletList;
			}		
			else
			{
				return new ArrayList<String>();
			}
		} catch (Exception e) {

			logger.error(e);
			return  new ArrayList<String>();
		}
	}
	
}
