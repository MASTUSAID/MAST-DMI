package com.rmsi.mast.viewer.dao;

import com.rmsi.mast.studio.dao.GenericDAO;
import com.rmsi.mast.studio.domain.PersonType;

public interface PersonTypeLDao extends GenericDAO<PersonType, Integer>{

	PersonType getPersonTypeById(int personTypeGid);
}
