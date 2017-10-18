package com.rmsi.mast.studio.mobile.service;

import com.rmsi.mast.studio.domain.EducationLevel;
import com.rmsi.mast.studio.domain.IdType;
import com.rmsi.mast.studio.domain.PersonType;
import com.rmsi.mast.studio.domain.RelationshipType;
import java.util.List;

public interface PersonService {

    /**
     * This will get person type from its id
     *
     * @param personTypeGid
     * @return
     */
    PersonType getPersonTypeById(long personTypeGid);

    /**
     * This will get the education level type form its id
     *
     * @param educationLevelId
     * @return
     */
    EducationLevel getEducationLevelById(int educationLevelId);

    /**
     * Returns ID type by option id
     *
     * @param optId Option id
     * @return
     */
    IdType getIdTypeByAttributeOptionId(int optId);

    /**
     * Returns Relationship type by option id
     *
     * @param optId Option id
     * @return
     */
    RelationshipType getRelationshipTypeByAttributeOptionId(int optId);
    
    /**
     * Returns List of Relationship types
     *
     * @return
     */
    List<RelationshipType> getRelationshipTypes();
}
