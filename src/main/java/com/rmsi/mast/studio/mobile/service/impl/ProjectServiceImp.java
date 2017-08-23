/**
 * 
 */
package com.rmsi.mast.studio.mobile.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rmsi.mast.studio.dao.ProjectAreaDAO;
import com.rmsi.mast.studio.domain.ProjectArea;
import com.rmsi.mast.studio.mobile.service.ProjectService;

/**
 * @author Shruti.Thakur
 *
 */
@Service
public class ProjectServiceImp implements ProjectService{

	@Autowired
	ProjectAreaDAO projectAreaDao;
	
	@Override
	public ProjectArea getProjectArea(String projectName) {
		
		return projectAreaDao.findByProjectName(projectName).get(0);
		
	}

}
