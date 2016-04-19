

package com.rmsi.mast.studio.domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the user_group database table.
 * 
 */
@Entity
@Table(name="user_group")
public class UserGroup implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String tenantid;
	private Group group;
	private User user;

    public UserGroup() {
    }

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
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


	//bi-directional many-to-one association to Group
    @ManyToOne
	@JoinColumn(name="groupname")
	public Group getGroup() {
		return this.group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}
	

	//bi-directional many-to-one association to User
    @ManyToOne
	@JoinColumn(name="username")
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}