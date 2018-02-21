package com.rmsi.mast.studio.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rmsi.mast.studio.domain.LaSpatialunitgroup;
import com.rmsi.mast.studio.mobile.dao.LaSpatialunitgroupDao;
import com.rmsi.mast.studio.service.LaSpatialunitgroupService;

@Service
public class LaSpatialunitgroupServiceImpl implements LaSpatialunitgroupService {

	
	@Autowired
	LaSpatialunitgroupDao laSpatialunitgroupDao;
	
	@Override
	public LaSpatialunitgroup findLaSpatialunitgroupById(Integer id) {
		
		return laSpatialunitgroupDao.findLaSpatialunitgroupById(id);
		
	}

}
