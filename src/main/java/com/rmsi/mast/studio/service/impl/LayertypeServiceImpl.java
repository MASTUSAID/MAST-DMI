

package com.rmsi.mast.studio.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import com.googlecode.ehcache.annotations.Cacheable;

import com.rmsi.mast.studio.dao.LayertypeDAO;
import com.rmsi.mast.studio.domain.Layertype;
import com.rmsi.mast.studio.service.LayertypeService;

/**
 * @author Aparesh.Chakraborty
 *
 */
@Service
public class LayertypeServiceImpl implements LayertypeService{

	@Autowired
	private LayertypeDAO layertypeDAO;

	@Override
	public Layertype addLayertype(Layertype layertype) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteLayertype() {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteLayertypeById(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateLayertype(Layertype layertype) {
		// TODO Auto-generated method stub

	}

	@Override
	//@Cacheable(cacheName="layertypeFBNCache")
	public Layertype findLayertypeById(Long id) {
		return layertypeDAO.findById(id, false);

	}

	@Override
	//@Cacheable(cacheName="layertypeFBNCache")
	public List<Layertype> findAllLayertype() {
		return layertypeDAO.findAll();
	}

	@Override
	//@Cacheable(cacheName="layertypeFBNCache")
	public Layertype findLayertypeByName(String name) {
		return layertypeDAO.findByName(name);
	}
	
}
