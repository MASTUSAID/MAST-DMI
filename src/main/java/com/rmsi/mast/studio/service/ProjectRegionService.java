package com.rmsi.mast.studio.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rmsi.mast.studio.domain.ProjectRegion;



public interface ProjectRegionService {

	
	ProjectRegion findProjectRegionById(Integer id); 
	List<ProjectRegion> findAllProjectRegion();
	
	
}
