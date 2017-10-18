package com.rmsi.mast.studio.domain.fetch;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "right_attributes")
public class RightAttributeValue extends AttributeValue {
    public RightAttributeValue(){
        super();
    }
}
