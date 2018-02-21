

package com.rmsi.mast.studio.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the user_role database table.
 * 
 */
@Entity
@Table(name="la_ext_userrolemapping")
public class UserRole implements Serializable {
	private static final long serialVersionUID = 1L;
    
    @Id
	@SequenceGenerator(name="pk_sequence_user_role",sequenceName="la_ext_userrolemapping_userroleid_seq", allocationSize=1) 
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence_user_role") 
	@Column(name="userroleid", insertable = false, updatable = false, unique=true, nullable=false) 
    private Integer userroleid;
    
    

    public UserRole() {
    
    }

    
    @Column(name="isactive")
    private Boolean active;
 	
   private long createdby;
   private long modifiedby;
   private Date createddate;  
   private Date modifieddate;
   
  // private Integer userid;
   
	
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="roleid")
	private Role roleBean;
   
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="userid")
	private User user;
	
	public Integer getUserroleid() {
		return userroleid;
	}
	
	public void setUserroleid(Integer userroleid) {
		this.userroleid = userroleid;
	}
	
	
	
	public Boolean getActive() {
		return active;
	}
	
	public void setActive(Boolean active) {
		this.active = active;
	}
	
	
	public long getCreatedby() {
		return createdby;
	}
	
	public void setCreatedby(long createdby) {
		this.createdby = createdby;
	}
	
	public long getModifiedby() {
		return modifiedby;
	}
	
	public void setModifiedby(long modifiedby) {
		this.modifiedby = modifiedby;
	}
	
	public Date getCreateddate() {
		return createddate;
	}
	
	public void setCreateddate(Date createddate) {
		this.createddate = createddate;
	}
	
	public Date getModifieddate() {
		return modifieddate;
	}
	
	public void setModifieddate(Date modifieddate) {
		this.modifieddate = modifieddate;
	}
	
	public Role getRoleBean() {
		return roleBean;
	}
	
	public void setRoleBean(Role roleBean) {
		this.roleBean = roleBean;
	}
/*
	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}*/
	
	@JsonIgnore
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}

	  
	
	
	
	
}