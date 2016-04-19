

package com.rmsi.mast.studio.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import com.googlecode.ehcache.annotations.Cacheable;
//import com.googlecode.ehcache.annotations.TriggersRemove;

import com.rmsi.mast.studio.dao.MaptipDAO;
import com.rmsi.mast.studio.domain.Maptip;
import com.rmsi.mast.studio.service.MaptipService;

;

/**
 * @author Aparesh.Chakraborty
 * 
 */
@Service
public class MaptipServiceImpl implements MaptipService {

	@Autowired
	private MaptipDAO maptipDAO;

	@Override
	//@TriggersRemove(cacheName="maptipFBNCache", removeAll=true)
	public Maptip addMaptip(Maptip maptip) {
		if(maptipDAO.findByName(maptip.getName()) == null){
			if (!maptipDAO.checkIfKeyExists(maptip.getId().getProject(), maptip
					.getId().getLayer())) {
				return maptipDAO.makePersistent(maptip);
			} else {
				return null;
			}
		}else{
			return null;
		}
	}

	@Override
	public void deleteMaptip() {
		// TODO Auto-generated method stub

	}

	@Override
	//@TriggersRemove(cacheName="maptipFBNCache", removeAll=true)
	public boolean deleteMaptipByName(String name) {
		return maptipDAO.deleteMaptipByName(name);

	}

	@Override
	//@TriggersRemove(cacheName="maptipFBNCache", removeAll=true)
	public Maptip updateMaptip(Maptip maptip) {
		return maptipDAO.makePersistent(maptip);

	}

	@Override
	//@Cacheable(cacheName="maptipFBNCache")
	public Maptip findMaptipById(Long id) {
		return maptipDAO.findById(id, false);

	}

	@Override
	//@Cacheable(cacheName="maptipFBNCache")
	public List<Maptip> findAllMaptip() {
		return maptipDAO.findAll();
	}

	@Override
	//@Cacheable(cacheName="maptipFBNCache")
	public Maptip findMaptipByName(String name) {
		return maptipDAO.findByName(name);
	}

	@Override
	//@Cacheable(cacheName="maptipFBNCache")
	public List<Maptip> getMaptipsByLayer(String layer) {

		return maptipDAO.getMaptipsByLayer(layer);
	}
	
	//@Cacheable(cacheName="maptipFBNCache")
	public Maptip findMaptipbyPK(String project, String layer){
		return maptipDAO.getMaptipByPK(project, layer);
	}

}
