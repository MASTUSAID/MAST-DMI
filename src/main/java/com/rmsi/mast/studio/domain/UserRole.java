

package com.rmsi.mast.studio.domain;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the user_role database table.
 * 
 */
@Entity
@Table(name="user_role")
public class UserRole implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String tenantid;
	private Role roleBean;
	private User user;

    public UserRole() {
    }

    //@Id
    //@GeneratedValue(strategy=GenerationType.IDENTITY)
	
    @Id
	@SequenceGenerator(name="pk_sequence_user_role",sequenceName="user_role_id_seq", allocationSize=1) 
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence_user_role") 
	@Column(name="id", insertable = false, updatable = false, unique=true, nullable=false) 
        
    
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


	//bi-directional many-to-one association to Role
    @ManyToOne	//(cascade = CascadeType.MERGE)
	@JoinColumn(name="role")
	public Role getRoleBean() {
		return this.roleBean;
	}

	public void setRoleBean(Role roleBean) {
		this.roleBean = roleBean;
	}
	

	//bi-directional many-to-one association to User
    @ManyToOne
	@JoinColumn(name="userid")
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}