

package com.rmsi.mast.studio.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * The persistent class for the module database table.
 * 
 */
@Entity
@Table(name = "module")
public class Module implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	private String description;
	// private Integer id;
	private String tenantid;
	private Set<Action> actions;

	// private Set<Role> roles;

	public Module() {
	}

	@Id
	// @Column(unique = true, nullable = false, length = 25)
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
	 * @GeneratedValue(strategy = GenerationType.IDENTITY)
	 * 
	 * @Column(nullable = false) public Integer getId() { return this.id; }
	 * 
	 * public void setId(Integer id) { this.id = id; }
	 */
	@Column(length = 25)
	public String getTenantid() {
		return this.tenantid;
	}

	public void setTenantid(String tenantid) {
		this.tenantid = tenantid;
	}

	// bi-directional many-to-one association to ModuleAction
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "module_action", joinColumns = { @JoinColumn(name = "module") }, inverseJoinColumns = { @JoinColumn(name = "action") })
	@JsonManagedReference
	public Set<Action> getActions() {
		return this.actions;
	}

	public void setActions(Set<Action> actions) {
		this.actions = actions;
	}

	// bi-directional many-to-one association to ModuleAction
	/*
	 * @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	 * 
	 * @JoinTable(name = "module_role", joinColumns = { @JoinColumn(name =
	 * "module") }, inverseJoinColumns = { @JoinColumn(name = "role") }) public
	 * Set<Role> getRoles() { return this.roles; }
	 * 
	 * public void setRoles(Set<Role> roles) { this.roles = roles; }
	 */
}