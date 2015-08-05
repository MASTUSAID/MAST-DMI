/**
 * 
 */
package com.rmsi.mast.studio.mobile.service;

import com.rmsi.mast.studio.domain.EducationLevel;
import com.rmsi.mast.studio.domain.PersonType;

/**
 * @author Shruti.Thakur
 *
 */
public interface PersonService {

	/**
	 * This will get person type from its id
	 * 
	 * @param personTypeGid
	 * @return
	 */
	PersonType getPersonTypeById(long personTypeGid);

	/**
	 * This will get the education level type form its id
	 * 
	 * @param educationLevelId
	 * @return
	 */
	EducationLevel getEducationLevelById(int educationLevelId);
}
