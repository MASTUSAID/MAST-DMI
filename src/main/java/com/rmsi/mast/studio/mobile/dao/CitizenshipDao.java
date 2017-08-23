/**
 * 
 */
package com.rmsi.mast.studio.mobile.dao;

import com.rmsi.mast.studio.dao.GenericDAO;
import com.rmsi.mast.studio.domain.Citizenship;


public interface CitizenshipDao extends GenericDAO<Citizenship, Long> {

	Citizenship getCitizensbyId(int val);

	Citizenship findBycitizenId(long citizenship);
	
}
