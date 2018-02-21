package com.rmsi.mast.studio.mobile.transferobjects;

import java.io.Serializable;

public class ClassificationAttributes implements Serializable {
	
	private Integer attribID;
	
	private String attribValue;
	
	
	public ClassificationAttributes(){
		
	}


	public Integer getAttribID() {
		return attribID;
	}


	public void setAttribID(Integer attribID) {
		this.attribID = attribID;
	}


	public String getAttribValue() {
		return attribValue;
	}


	public void setAttribValue(String attribValue) {
		this.attribValue = attribValue;
	}
	
	
	
}
