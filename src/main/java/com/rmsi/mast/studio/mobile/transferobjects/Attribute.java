package com.rmsi.mast.studio.mobile.transferobjects;

import java.io.Serializable;

public class Attribute implements Serializable {
    private Long id;
    private String value;
    
    public Attribute(){
        
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}


