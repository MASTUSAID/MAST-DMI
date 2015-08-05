/**
 * 
 */
package com.rmsi.mast.studio.mobile.dao;

import com.rmsi.mast.studio.dao.GenericDAO;
import com.rmsi.mast.studio.domain.EducationLevel;

/**
 * @author shruti.thakur
 *
 */
public interface EducationLevelDao extends GenericDAO<EducationLevel, Integer> {

	EducationLevel getEducationLevelById(int educationLevelId);
}
