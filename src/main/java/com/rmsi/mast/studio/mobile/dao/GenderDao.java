/**
 * 
 */
package com.rmsi.mast.studio.mobile.dao;

import com.rmsi.mast.studio.dao.GenericDAO;
import com.rmsi.mast.studio.domain.Gender;

/**
 * @author Shruti.Thakur
 *
 */
public interface GenderDao extends GenericDAO<Gender, Integer>{
	
	Gender getGenderById(long genderId);

}
