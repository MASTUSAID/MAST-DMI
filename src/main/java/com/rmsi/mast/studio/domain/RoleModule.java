

package com.rmsi.mast.studio.domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the role_module database table.
 * 
 */
@Entity
@Table(name="module_role")
public class RoleModule implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String tenantid;
	private Action actionBean;
	private Module moduleBean;
	private Role roleBean;

    public RoleModule() {
    }


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	
	
	/*@Id
	@SequenceGenerator(name="pk_sequence",sequenceName="role_module_id_seq", allocationSize=1) 
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence") 
	@Column(name="id", unique=true, nullable=false) */
	
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
	@JoinColumn(name="action", nullable=false)
	public Action getActionBean() {
		return this.actionBean;
	}

	public void setActionBean(Action actionBean) {
		this.actionBean = actionBean;
	}
	

	//bi-directional many-to-one association to Module
    @ManyToOne
	@JoinColumn(name="module", nullable=false)
	public Module getModuleBean() {
		return this.moduleBean;
	}

	public void setModuleBean(Module moduleBean) {
		this.moduleBean = moduleBean;
	}
	

	//bi-directional many-to-one association to Role
    @ManyToOne
	@JoinColumn(name="role", nullable=false)
	public Role getRoleBean() {
		return this.roleBean;
	}

	public void setRoleBean(Role roleBean) {
		this.roleBean = roleBean;
	}
	
}