package com.rmsi.mast.studio.domain.fetch;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "media_attributes")
public class MediaAttributeValue extends AttributeValue {
    public MediaAttributeValue(){
        super();
    }
}
