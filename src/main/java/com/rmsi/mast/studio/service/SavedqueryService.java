

package com.rmsi.mast.studio.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.rmsi.mast.studio.domain.Savedquery;

/**
 * @author Aparesh.Chakraborty
 *
 */
public interface SavedqueryService {

	@Transactional
	Savedquery addSavedquery(Savedquery savedquery);
	
	@Transactional
	void deleteSavedquery();

	@Transactional
	void deleteSavedqueryById(Long id);

	@Transactional
	Savedquery updateSavedquery(Savedquery savedquery);

	@Transactional(readOnly=true)
	Savedquery findSavedqueryById(Long id);

	@Transactional(readOnly=true)
	List<Savedquery> findAllSavedquery();
	
	Savedquery findSavedqueryByName(String name);
	
	List<String> getSavedQueryNames(String project, String layer);
	List<String> getQueryExpression(String queryName);
	String getQueryDescriptionByQueryName(String queryName);
}
