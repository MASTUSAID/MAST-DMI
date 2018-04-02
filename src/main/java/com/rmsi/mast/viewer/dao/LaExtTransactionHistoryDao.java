package com.rmsi.mast.viewer.dao;

import com.rmsi.mast.studio.dao.GenericDAO;
import com.rmsi.mast.studio.domain.LaExtTransactionHistory;
import com.rmsi.mast.studio.domain.LaLease;
import com.rmsi.mast.studio.domain.LaSurrenderLease;

/**
 * 
 * @author Abhay.Pandey
 *
 */

public interface LaExtTransactionHistoryDao extends GenericDAO<LaExtTransactionHistory, Integer>{

	LaExtTransactionHistory saveTransactionHistory(LaExtTransactionHistory latranshist);
	LaExtTransactionHistory getTransactionHistoryByTransId(Integer latranshist);
	
}
