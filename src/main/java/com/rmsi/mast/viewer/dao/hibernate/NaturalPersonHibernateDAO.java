package com.rmsi.mast.viewer.dao.hibernate;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.hibernate.GenericHibernateDAO;
import com.rmsi.mast.studio.domain.NaturalPerson;
import com.rmsi.mast.viewer.dao.NaturalPersonDAO;

/**
 * 
 * @author Abhay.Pandey
 *
 */

@Repository
public class NaturalPersonHibernateDAO extends
GenericHibernateDAO<NaturalPerson, Long> implements NaturalPersonDAO{

	Logger logger = Logger.getLogger(NaturalPersonHibernateDAO.class);
	@Override
	public NaturalPerson saveNaturalPerson(NaturalPerson naturalPerson) {
		try {
            return makePersistent(naturalPerson);

        } catch (Exception ex) {
            logger.error(ex);
            throw ex;
        }
	}

}
