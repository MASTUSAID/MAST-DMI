/**
 * 
 */
package com.rmsi.mast.studio.mobile.dao;

import com.rmsi.mast.studio.dao.GenericDAO;
import com.rmsi.mast.studio.domain.TenureClass;

/**
 * @author Shruti.Thakur
 *
 */
public interface TenureClassDao extends GenericDAO<TenureClass, Integer> {

	TenureClass getTenureClassById(int tenureClassId);

}
