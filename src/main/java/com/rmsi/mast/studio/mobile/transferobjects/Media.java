package com.rmsi.mast.studio.mobile.transferobjects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Media implements Serializable {
    private Long id;
    private String path;
    private String type;
    private List<Attribute> attributes = new ArrayList<>();

    public Media(){
        
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }
}
