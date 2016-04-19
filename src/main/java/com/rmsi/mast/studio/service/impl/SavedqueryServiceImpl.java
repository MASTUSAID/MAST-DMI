

package com.rmsi.mast.studio.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rmsi.mast.studio.dao.SavedqueryDAO;
import com.rmsi.mast.studio.domain.Savedquery;
import com.rmsi.mast.studio.service.SavedqueryService;

/**
 * @author Aparesh.Chakraborty
 *
 */
@Service
public class SavedqueryServiceImpl implements SavedqueryService{

	@Autowired
	private SavedqueryDAO savedqueryDAO;

	@Override
	public Savedquery addSavedquery(Savedquery savedquery) {

		try {
			return savedqueryDAO.makePersistent(savedquery);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public void deleteSavedquery() {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteSavedqueryById(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Savedquery updateSavedquery(Savedquery savedquery) {
		return savedqueryDAO.makePersistent(savedquery);

	}

	@Override
	public Savedquery findSavedqueryById(Long id) {
		return savedqueryDAO.findById(id, false);

	}

	@Override
	public List<Savedquery> findAllSavedquery() {
		return savedqueryDAO.findAll();
	}

	@Override
	public Savedquery findSavedqueryByName(String name) {
		return savedqueryDAO.findByName(name);
	}
	
	public List<String> getSavedQueryNames(String project, String layer){
		return savedqueryDAO.getSavedQueryNames(project, layer);
	}
	
	public List<String> getQueryExpression(String queryName){
		return savedqueryDAO.getQueryExpression(queryName);
	}
	
	public String getQueryDescriptionByQueryName(String queryName){
		return savedqueryDAO.getQueryDescription(queryName);
	}
}
