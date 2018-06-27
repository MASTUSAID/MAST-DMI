package com.rmsi.mast.viewer.dao;

import com.rmsi.mast.studio.dao.GenericDAO;
import com.rmsi.mast.studio.domain.LaMortgage;

/**
 * 
 * @author Abhay.Pandey
 *
 */

public interface LaMortgageDao extends GenericDAO<LaMortgage, Integer>{

	LaMortgage saveMortgage(LaMortgage laMortgage);
	
	LaMortgage getMortgageByLandId(Long landId);
	
	LaMortgage getMortgageByMotgageId(Integer motgageId);
	
	LaMortgage getMortgageByLandandTransactionId(Long landId, Integer transactionid);
	
	LaMortgage getMortgageByLandIdandprocessId(Long landId, Long processId);
	
	boolean disablelease(Long personid, Long landid);
}
