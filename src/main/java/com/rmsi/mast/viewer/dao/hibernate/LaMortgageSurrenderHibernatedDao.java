package com.rmsi.mast.viewer.dao.hibernate;

import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.hibernate.GenericHibernateDAO;
import com.rmsi.mast.studio.domain.LaLease;
import com.rmsi.mast.studio.domain.LaMortgage;
import com.rmsi.mast.studio.domain.LaSurrenderMortgage;
import com.rmsi.mast.studio.domain.fetch.SpatialUnitTable;
import com.rmsi.mast.viewer.dao.LaMortgageDao;
import com.rmsi.mast.viewer.dao.LaMortgageSurrenderDao;

/**
 * 
 * @author Abhay.Pandey
 *
 */

@Repository
public class LaMortgageSurrenderHibernatedDao extends GenericHibernateDAO<LaSurrenderMortgage, Integer> implements LaMortgageSurrenderDao{

	@Override
	public LaSurrenderMortgage saveMortgage(LaSurrenderMortgage laMortgage) {
		try {
            return makePersistent(laMortgage);

        } catch (Exception ex) {
          
            throw ex;
        }
	}

	@Override
	public LaSurrenderMortgage getMortgageByLandId(Long landId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LaSurrenderMortgage getMortgageByLandIdandprocessId(Long landId,
			Long processId) {
		try{
			 String strQuery = "select * from la_surrendermortgage lm left join la_ext_transactiondetails trans on lm.mortgageid = trans.moduletransid where lm.isactive= true and trans.applicationstatusid=1 and lm.landid= "+landId+" and trans.processid="+processId;
				List<LaSurrenderMortgage> lasurrenderMortgage = getEntityManager().createNativeQuery(strQuery,LaSurrenderMortgage.class).getResultList();
			if(lasurrenderMortgage.size()>0){
			return lasurrenderMortgage.get(0);
			}
			return null;
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
	}}
