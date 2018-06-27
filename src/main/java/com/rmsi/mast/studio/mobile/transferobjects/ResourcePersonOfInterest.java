package com.rmsi.mast.studio.mobile.transferobjects;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.rmsi.mast.studio.domain.AttributeMasterResourcePOI;

public class ResourcePersonOfInterest implements Serializable{


		private Long id;
	    private String value;
	    private String dob;
	    private int genderId;
	    public Integer getGroupId() {
			return groupId;
		}


		public void setGroupId(Integer groupId) {
			this.groupId = groupId;
		}


		private int relationshipId;
	    private Integer groupId;

    
    public ResourcePersonOfInterest(){
        
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


	public String getDob() {
		return dob;
	}


	public void setDob(String dob) {
		this.dob = dob;
	}


	public int getGenderId() {
		return genderId;
	}


	public void setGenderId(int genderId) {
		this.genderId = genderId;
	}


	public int getRelationshipId() {
		return relationshipId;
	}


	public void setRelationshipId(int relationshipId) {
		this.relationshipId = relationshipId;
	}

  
}



