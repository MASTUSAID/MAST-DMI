package com.rmsi.mast.viewer.dao.hibernate;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.hibernate.GenericHibernateDAO;
import com.rmsi.mast.studio.domain.LaMortgage;
import com.rmsi.mast.viewer.dao.LaMortgageDao;

/**
 * 
 * @author Abhay.Pandey
 *
 */

@Repository
public class LaMortgageHibernatedDao extends GenericHibernateDAO<LaMortgage, Integer> implements LaMortgageDao{

	Logger logger = Logger.getLogger(LaMortgageHibernatedDao.class);
	
	@Override
	public LaMortgage saveMortgage(LaMortgage laMortgage) {
		try {
            return makePersistent(laMortgage);

        } catch (Exception ex) {
            logger.error(ex);
            throw ex;
        }
	}

}
