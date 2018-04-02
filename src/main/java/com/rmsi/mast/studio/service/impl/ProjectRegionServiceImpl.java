package com.rmsi.mast.studio.service.impl;

import java.util.List;

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

	@Override
	public List<ProjectRegion> findAllProjectRegion() {
		// TODO Auto-generated method stub
		return projectRegionDAO.findAllProjectRegion();
	}

}
