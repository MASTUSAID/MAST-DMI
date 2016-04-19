

package com.rmsi.mast.studio.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the action database table.
 * 
 */
@Entity
@Table(name = "action")
public class Action implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	private String description;
	// private Integer id;
	private String tenantid;

	// private Set<Module> modules;
	// private Set<RoleModule> roleModules;

	public Action() {
	}

	// @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	// @JoinTable(name = "module_action", joinColumns = { @JoinColumn(name =
	// "actionname") }, inverseJoinColumns = { @JoinColumn(name = "modulename")
	// })
	// public Set<Module> getModules() {
	// return this.modules;
	// }
	//
	// public void setModules(Set<Module> modules) {
	// this.modules = modules;
	// }

	@Id
	// @Column(unique=true, nullable=false, length=255)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(length = 255)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/*
	 * public Integer getId() { return this.id; }
	 * 
	 * public void setId(Integer id) { this.id = id; }
	 */

	@Column(length = 50)
	public String getTenantid() {
		return this.tenantid;
	}

	public void setTenantid(String tenantid) {
		this.tenantid = tenantid;
	}

	// //bi-directional many-to-one association to ModuleAction
	// @OneToMany(mappedBy="action", fetch = FetchType.EAGER)
	// public Set<ModuleAction> getModuleActions() {
	// return this.moduleActions;
	// }
	//
	// public void setModuleActions(Set<ModuleAction> moduleActions) {
	// this.moduleActions = moduleActions;
	// }
	//
	//
	// //bi-directional many-to-one association to RoleModule
	// @OneToMany(mappedBy="actionBean", fetch = FetchType.EAGER)
	// public Set<RoleModule> getRoleModules() {
	// return this.roleModules;
	// }
	//
	// public void setRoleModules(Set<RoleModule> roleModules) {
	// this.roleModules = roleModules;
	// }

}