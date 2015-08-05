/**
 * 
 */
package com.rmsi.mast.viewer.dao;

import com.rmsi.mast.studio.dao.GenericDAO;
import com.rmsi.mast.studio.domain.fetch.PersonAdministrator;

/**
 * @author Shruti.Thakur
 *
 */
public interface PersonAdministratorDao extends GenericDAO<PersonAdministrator, Long> {

	PersonAdministrator findAdminById(Long admiLong);

	boolean deleteAdminById(Long id);

	boolean addAdmin(Long adminId);
	
	
	
}
