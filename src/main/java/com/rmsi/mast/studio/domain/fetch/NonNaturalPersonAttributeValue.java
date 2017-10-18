package com.rmsi.mast.studio.domain.fetch;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "nonnatural_person_attributes")
public class NonNaturalPersonAttributeValue extends AttributeValue {
    public NonNaturalPersonAttributeValue(){
        super();
    }
}
