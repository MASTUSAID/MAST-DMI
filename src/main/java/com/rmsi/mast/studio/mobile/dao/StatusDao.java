/**
 * 
 */
package com.rmsi.mast.studio.mobile.dao;

import com.rmsi.mast.studio.dao.GenericDAO;
import com.rmsi.mast.studio.domain.Status;

/**
 * @author Shruti.Thakur
 *
 */
public interface StatusDao extends GenericDAO<Status, Integer>{

	public Status getStatusById(Integer statusId);
}
