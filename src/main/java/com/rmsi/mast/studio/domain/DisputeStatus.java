package com.rmsi.mast.studio.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="la_ext_disputestatus")
public class DisputeStatus implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    
    public DisputeStatus(){
    	
    }

    @Id
    private int disputestatusid;
    
    private String disputestatus;
    private String disputestatus_en;
    private Boolean isactive;


	public int getDisputestatusid() {
		return disputestatusid;
	}


	public void setDisputestatusid(int disputestatusid) {
		this.disputestatusid = disputestatusid;
	}


	public String getDisputestatus() {
		return disputestatus;
	}


	public void setDisputestatus(String disputestatus) {
		this.disputestatus = disputestatus;
	}


	public String getDisputestatus_en() {
		return disputestatus_en;
	}


	public void setDisputestatus_en(String disputestatus_en) {
		this.disputestatus_en = disputestatus_en;
	}


	public Boolean getIsactive() {
		return isactive;
	}


	public void setIsactive(Boolean isactive) {
		this.isactive = isactive;
	}
    
    
    
    
    
    
    
    
    
    
    
    
}
