package com.rmsi.mast.viewer.dao.hibernate;

import org.springframework.stereotype.Repository;
import com.rmsi.mast.studio.dao.hibernate.GenericHibernateDAO;
import com.rmsi.mast.studio.domain.fetch.ProjectTemp;
import com.rmsi.mast.viewer.dao.ProjectTempDao;

@Repository
public class ProjectTempHibernateDAO extends GenericHibernateDAO<ProjectTemp, String> implements ProjectTempDao {
	
}




