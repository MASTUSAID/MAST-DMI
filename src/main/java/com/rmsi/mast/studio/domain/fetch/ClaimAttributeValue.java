package com.rmsi.mast.studio.domain.fetch;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "spatial_unit_attributes")
public class ClaimAttributeValue extends AttributeValue {
    public ClaimAttributeValue(){
        super();
    }
}
