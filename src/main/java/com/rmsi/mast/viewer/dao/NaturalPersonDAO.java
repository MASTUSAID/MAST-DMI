package com.rmsi.mast.viewer.dao;

import com.rmsi.mast.studio.dao.GenericDAO;
import com.rmsi.mast.studio.domain.NaturalPerson;

/**
 * 
 * @author Abhay.Pandey
 *
 */

public interface NaturalPersonDAO extends GenericDAO<NaturalPerson, Long>{

	NaturalPerson saveNaturalPerson(NaturalPerson naturalPerson);
}
