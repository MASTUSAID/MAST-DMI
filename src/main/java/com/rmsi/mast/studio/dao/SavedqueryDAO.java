

package com.rmsi.mast.studio.dao;

import java.util.List;

import com.rmsi.mast.studio.domain.Savedquery;

/**
 * @author Aparesh.Chakraborty
 *
 */


public interface SavedqueryDAO extends GenericDAO<Savedquery, Long> {
	
	Savedquery findByName(String name);
	List<String> getSavedQueryNames(String project, String layer);
	List<String> getQueryExpression(String queryName);
	String getQueryDescription(String qryName);
}

