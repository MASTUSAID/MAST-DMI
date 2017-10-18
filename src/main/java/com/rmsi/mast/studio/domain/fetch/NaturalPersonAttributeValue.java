package com.rmsi.mast.studio.domain.fetch;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "natural_person_attributes")
public class NaturalPersonAttributeValue extends AttributeValue {
    public NaturalPersonAttributeValue(){
        super();
    }
}
