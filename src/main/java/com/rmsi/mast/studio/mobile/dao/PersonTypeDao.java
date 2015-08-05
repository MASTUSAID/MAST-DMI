/**
 * 
 */
package com.rmsi.mast.studio.mobile.dao;

import com.rmsi.mast.studio.dao.GenericDAO;
import com.rmsi.mast.studio.domain.PersonType;

/**
 * @author Shruti.Thakur
 *
 */
public interface PersonTypeDao extends GenericDAO<PersonType, Long> {

	/**
	 * This will get person type by its Id
	 * 
	 * @param personTypeGid
	 * @return
	 */
	PersonType getPersonTypeById(long personTypeGid);

}
