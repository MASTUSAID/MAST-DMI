

package com.rmsi.mast.studio.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import com.googlecode.ehcache.annotations.Cacheable;

import com.rmsi.mast.studio.dao.ActionDAO;
import com.rmsi.mast.studio.dao.BaselayerDAO;
import com.rmsi.mast.studio.domain.Action;
import com.rmsi.mast.studio.domain.Baselayer;
import com.rmsi.mast.studio.service.ActionService;
import com.rmsi.mast.studio.service.BaselayerService;

@Service
public class BaselayerServiceImpl implements BaselayerService {

	@Autowired
	private BaselayerDAO baselayerDAO;
	
	@Override
	//@Cacheable(cacheName = "baseLayerFBNCache")	
	public List<Baselayer> findAllBaselayer() {
		return baselayerDAO.findAll();
	}

	

	

	
	
	

	
}
