package com.rmsi.mast.studio.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "la_ext_disputestatus")
public class Dispute implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @SequenceGenerator(name = "pk_la_ext_disputestatus", sequenceName = "la_ext_disputestatus_disputestatusid_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_la_ext_disputestatus")
    @Column(name="disputestatusid")
    private Integer id;
    
    @Column(name="disputestatus")
    private String description;
    private String disputestatus_en;
    @Column(name="isactive")
    private Boolean active;
    
   
    
    public Dispute(){
        
    }



	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public String getDisputestatus_en() {
		return disputestatus_en;
	}



	public void setDisputestatus_en(String disputestatus_en) {
		this.disputestatus_en = disputestatus_en;
	}



	public Boolean getActive() {
		return active;
	}



	public void setActive(Boolean active) {
		this.active = active;
	}
    
   
    
    
}
