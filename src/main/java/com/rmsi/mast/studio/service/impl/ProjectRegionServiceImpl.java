package com.rmsi.mast.studio.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rmsi.mast.studio.dao.ProjectRegionDAO;
import com.rmsi.mast.studio.domain.ProjectRegion;
import com.rmsi.mast.studio.service.ProjectRegionService;


@Service
public class ProjectRegionServiceImpl implements ProjectRegionService {

	
	@Autowired
	ProjectRegionDAO projectRegionDAO;
	
	@Override
	public ProjectRegion findProjectRegionById(Integer id) {
		// TODO Auto-generated method stub
		return projectRegionDAO.findProjectRegionById(id);
	}

}
