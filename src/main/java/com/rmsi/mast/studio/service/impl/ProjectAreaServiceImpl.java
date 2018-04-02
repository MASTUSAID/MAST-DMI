package com.rmsi.mast.studio.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rmsi.mast.studio.dao.ProjectAreaDAO;
import com.rmsi.mast.studio.domain.ProjectArea;
import com.rmsi.mast.studio.service.ProjectAreaService;

@Service
public class ProjectAreaServiceImpl implements ProjectAreaService{

	
	@Autowired
	ProjectAreaDAO projectAreaDAO;
	
	@Override
	public ProjectArea findProjectAreaById(Long id) {
		// TODO Auto-generated method stub
		return projectAreaDAO.findProjectAreaById(id);
	}

	@Override
	public ProjectArea findProjectAreaByProjectId(Integer id) {

		return projectAreaDAO.findProjectAreaByProjectId(id);
	}
	
}
