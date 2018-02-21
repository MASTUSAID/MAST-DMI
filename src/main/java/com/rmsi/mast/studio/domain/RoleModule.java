

package com.rmsi.mast.studio.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.*;



/**
 * The persistent class for the role_module database table.
 * 
 */
@Entity
@Table(name="la_ext_rolemodulemapping")
public class RoleModule implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	@Id
	private Integer rolemoduleid;

	private Integer createdby;

	private Date createddate;

	private Boolean isactive;

	private Integer modifiedby;

	private Date modifieddate;

	//bi-directional many-to-one association to LaExtModule
	@ManyToOne
	@JoinColumn(name="moduleid")
	private Module module;

	//bi-directional many-to-one association to LaExtRole
	@ManyToOne
	@JoinColumn(name="roleid")
	private Role role;

	public RoleModule() {
	}

	public Integer getRolemoduleid() {
		return rolemoduleid;
	}

	public void setRolemoduleid(Integer rolemoduleid) {
		this.rolemoduleid = rolemoduleid;
	}

	public Integer getCreatedby() {
		return createdby;
	}

	public void setCreatedby(Integer createdby) {
		this.createdby = createdby;
	}

	public Date getCreateddate() {
		return createddate;
	}

	public void setCreateddate(Date createddate) {
		this.createddate = createddate;
	}

	public Boolean getIsactive() {
		return isactive;
	}

	public void setIsactive(Boolean isactive) {
		this.isactive = isactive;
	}

	public Integer getModifiedby() {
		return modifiedby;
	}

	public void setModifiedby(Integer modifiedby) {
		this.modifiedby = modifiedby;
	}

	public Date getModifieddate() {
		return modifieddate;
	}

	public void setModifieddate(Date modifieddate) {
		this.modifieddate = modifieddate;
	}

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	
	
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	
	

	
	
	
	
	
	
}