package com.rmsi.mast.studio.mobile.transferobjects;

import java.io.Serializable;

public class ResourceCustomAttributes implements Serializable {
	
	private Integer attribId;
	
	private String attribValue;
	
	private String Subclassificationid;
	
	public ResourceCustomAttributes(){
		
	}
	
	

	public String getSubclassificationid() {
		return Subclassificationid;
	}



	public void setSubclassificationid(String subclassificationid) {
		Subclassificationid = subclassificationid;
	}



	

	public Integer getAttribId() {
		return attribId;
	}



	public void setAttribId(Integer attribId) {
		this.attribId = attribId;
	}



	public String getAttribValue() {
		return attribValue;
	}

	public void setAttribValue(String attribValue) {
		this.attribValue = attribValue;
	}
	
	

}
