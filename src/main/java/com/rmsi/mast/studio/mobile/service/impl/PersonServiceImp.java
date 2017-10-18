/**
 *
 */
package com.rmsi.mast.studio.mobile.service.impl;

import com.rmsi.mast.studio.dao.IdTypeDao;
import com.rmsi.mast.studio.dao.RelationshipTypeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rmsi.mast.studio.domain.EducationLevel;
import com.rmsi.mast.studio.domain.IdType;
import com.rmsi.mast.studio.domain.PersonType;
import com.rmsi.mast.studio.domain.RelationshipType;
import com.rmsi.mast.studio.mobile.dao.EducationLevelDao;
import com.rmsi.mast.studio.mobile.dao.PersonTypeDao;
import com.rmsi.mast.studio.mobile.service.PersonService;
import java.util.List;

@Service
public class PersonServiceImp implements PersonService {

    @Autowired
    PersonTypeDao personTypeDao;

    @Autowired
    EducationLevelDao educationLevelDao;

    @Autowired
    IdTypeDao idTypeDao;
    
    @Autowired
    RelationshipTypeDao relationshipTypeDao;
    
    @Override
    public PersonType getPersonTypeById(long personTypeGid) {
        return personTypeDao.getPersonTypeById(personTypeGid);
    }

    @Override
    public EducationLevel getEducationLevelById(int educationLevelId) {
        return educationLevelDao.getEducationLevelById(educationLevelId);
    }

    @Override
    public IdType getIdTypeByAttributeOptionId(int optId){
        return idTypeDao.getTypeByAttributeOptionId(optId);
    }
    
    @Override
    public RelationshipType getRelationshipTypeByAttributeOptionId(int optId){
        return relationshipTypeDao.getTypeByAttributeOptionId(optId);
    }
    
    @Override
    public List<RelationshipType> getRelationshipTypes(){
        return relationshipTypeDao.findAll();
    }
}
