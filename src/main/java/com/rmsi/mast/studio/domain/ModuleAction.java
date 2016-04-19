

package com.rmsi.mast.studio.domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the module_action database table.
 * 
 */
@Entity
@Table(name="module_action")
public class ModuleAction implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String tenantid;
	private Action action;
	private Module module;

    public ModuleAction() {
    }


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	@Column(length=25)
	public String getTenantid() {
		return this.tenantid;
	}

	public void setTenantid(String tenantid) {
		this.tenantid = tenantid;
	}


	//bi-directional many-to-one association to Action
    @ManyToOne
	@JoinColumn(name="actionname", nullable=false)
	public Action getAction() {
		return this.action;
	}

	public void setAction(Action action) {
		this.action = action;
	}
	

	//bi-directional many-to-one association to Module
    @ManyToOne
	@JoinColumn(name="modulename", nullable=false)
	public Module getModule() {
		return this.module;
	}

	public void setModule(Module module) {
		this.module = module;
	}
	
}