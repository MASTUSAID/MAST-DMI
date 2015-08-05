/**
 * 
 */
package com.rmsi.mast.studio.mobile.dao;

import com.rmsi.mast.studio.dao.GenericDAO;
import com.rmsi.mast.studio.domain.MaritalStatus;

/**
 * @author Shruti.Thakur
 *
 */
public interface MaritalStatusDao extends GenericDAO<MaritalStatus, Integer> {

	MaritalStatus getMaritalStatusById(int maritalStatusId);

}
