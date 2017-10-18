

package com.rmsi.mast.studio.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the user_project database table.
 * 
 */
@Entity
@Table(name="user_project")
public class UserProject implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String tenantid;
	private Project projectBean;
	private User user;

    public UserProject() {
    }
    
    //@Id
    //@GeneratedValue(strategy=GenerationType.IDENTITY)
	
    @Id
	@SequenceGenerator(name="pk_user_project_id_seq",sequenceName="user_project_id_seq", allocationSize=1) 
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_user_project_id_seq") 
	@Column(name="id", unique=true, nullable=false) 
	
    
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


	//bi-directional many-to-one association to Project
    @ManyToOne
	@JoinColumn(name="project")
	public Project getProjectBean() {
		return this.projectBean;
	}

	public void setProjectBean(Project projectBean) {
		this.projectBean = projectBean;
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