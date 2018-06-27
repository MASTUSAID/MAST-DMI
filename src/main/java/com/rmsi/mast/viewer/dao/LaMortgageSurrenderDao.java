package com.rmsi.mast.viewer.dao;

import com.rmsi.mast.studio.dao.GenericDAO;
import com.rmsi.mast.studio.domain.LaMortgage;
import com.rmsi.mast.studio.domain.LaSurrenderMortgage;

/**
 * 
 * @author Abhay.Pandey
 *
 */

public interface LaMortgageSurrenderDao extends GenericDAO<LaSurrenderMortgage, Integer>{

	LaSurrenderMortgage saveMortgage(LaSurrenderMortgage laMortgage);
	
	LaSurrenderMortgage getMortgageByLandId(Long landId);
	
	LaSurrenderMortgage getMortgageBysurMortgageId(Integer motgageId);

	
	LaSurrenderMortgage getMortgageByLandIdandprocessId(Long landId, Long processId);
	
	LaSurrenderMortgage getMortgageByLandIdandTransId(Long landId, Integer transactionId);
}
