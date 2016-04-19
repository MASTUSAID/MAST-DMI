

package com.rmsi.mast.studio.domain;

import java.io.Serializable;
import java.util.Set;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the role database table.
 * 
 */
@Entity
@Table(name="role")
public class Role implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	private String description;
	private Integer id;
	private String tenantid;
	private Set<RoleModule> roleModules;
	private Set<UserRole> userRoles;
	private Set<Module> modules;
	
    public Role() {
    }


	@Id
	//@Column(unique=true, nullable=false, length=25)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}


	
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(nullable=false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	
	public String getTenantid() {
		return this.tenantid;
	}

	public void setTenantid(String tenantid) {
		this.tenantid = tenantid;
	}


//	//bi-directional many-to-one association to RoleModule
	@JsonIgnore
	@OneToMany(mappedBy="roleBean")
	public Set<RoleModule> getRoleModules() {
		return this.roleModules;
	}

	public void setRoleModules(Set<RoleModule> roleModules) {
		this.roleModules = roleModules;
	}
//	
//
//	//bi-directional many-to-one association to UserRole
	
	@JsonIgnore
	@OneToMany(mappedBy = "roleBean", fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	public Set<UserRole> getUserRoles() {
		return this.userRoles;
	}

	public void setUserRoles(Set<UserRole> userRoles) {
		this.userRoles = userRoles;
	}



	//@OneToMany(mappedBy = "moduleBean", fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "module_role", joinColumns = { @JoinColumn(name = "role") }, inverseJoinColumns = { @JoinColumn(name = "module") })
	public Set<Module> getModules() {
		return modules;
	}


	public void setModules(Set<Module> modules) {
		this.modules = modules;
	}
	
	
}