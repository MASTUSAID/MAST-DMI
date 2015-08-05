/**
 * 
 */
package com.rmsi.mast.studio.mobile.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rmsi.mast.studio.domain.EducationLevel;
import com.rmsi.mast.studio.domain.PersonType;
import com.rmsi.mast.studio.mobile.dao.EducationLevelDao;
import com.rmsi.mast.studio.mobile.dao.PersonTypeDao;
import com.rmsi.mast.studio.mobile.service.PersonService;

/**
 * @author Shruti.Thakur
 *
 */
@Service
public class PersonServiceImp implements PersonService {

	@Autowired
	PersonTypeDao personTypeDao;

	@Autowired
	EducationLevelDao educationLevelDao;

	@Override
	public PersonType getPersonTypeById(long personTypeGid) {

		return personTypeDao.getPersonTypeById(personTypeGid);

	}

	@Override
	public EducationLevel getEducationLevelById(int educationLevelId) {

		return educationLevelDao.getEducationLevelById(educationLevelId);

	}

}
