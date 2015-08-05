/* ----------------------------------------------------------------------
 * Copyright (c) 2011 by RMSI.
 * All Rights Reserved
 *
 * Permission to use this program and its related files is at the
 * discretion of RMSI Pvt Ltd.
 *
 * The licensee of RMSI Software agrees that:
 *    - Redistribution in whole or in part is not permitted.
 *    - Modification of source is not permitted.
 *    - Use of the source in whole or in part outside of RMSI is not
 *      permitted.
 *
 * THIS SOFTWARE IS PROVIDED ''AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL RMSI OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 * ----------------------------------------------------------------------
 */

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