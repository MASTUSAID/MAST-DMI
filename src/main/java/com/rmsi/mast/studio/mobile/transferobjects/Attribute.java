package com.rmsi.mast.studio.mobile.transferobjects;

import java.io.Serializable;

public class Attribute implements Serializable {
    private Long id;
    private String value;
    private Integer groupId;
    
    public Attribute(){
        
    }

    public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
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


