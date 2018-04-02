package com.rmsi.mast.studio.domain.fetch;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class UserParcel implements Serializable  {

    private static final long serialVersionUID = 1L;

    @Id
    private long landid;
    
    @Column
    private String hierarchy1;
    
    
    @Column
    private String hierarchy2;
    
    
    
    @Column
    private String hierarchy3;



	public long getLandid() {
		return landid;
	}



	public void setLandid(long landid) {
		this.landid = landid;
	}



	public String getHierarchy1() {
		return hierarchy1;
	}



	public void setHierarchy1(String hierarchy1) {
		this.hierarchy1 = hierarchy1;
	}



	public String getHierarchy2() {
		return hierarchy2;
	}



	public void setHierarchy2(String hierarchy2) {
		this.hierarchy2 = hierarchy2;
	}



	public String getHierarchy3() {
		return hierarchy3;
	}



	public void setHierarchy3(String hierarchy3) {
		this.hierarchy3 = hierarchy3;
	}
    
    
    
    
    
    
    
    
}
