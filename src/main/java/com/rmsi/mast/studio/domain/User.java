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

import javax.persistence.*;

import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the user database table.
 * 
 */
@Entity
@Table(name="users")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	private String name;
	private Boolean active;
	private String defaultproject;
	private String email;
	private String phone;
	
	private Integer id;
	private Date lastactivitydate;
	private String password;
	private Date passwordexpires;
	private String tenantid;
	//private Set<UserGroup> userGroups;
	private Set<Project> projects;
	private Set<Role> roles;
	private String authkey;
	
	private String manager_name;
	private String name_user;
	
	//private int functionalRole;
	//private Set<UserReporting> userReportings2;

	
	@Column(name="name")
	public String getName_user() {
		return name_user;
	}

	public void setName_user(String name_user) {
		this.name_user = name_user;
	}



	private String reportingTo;
	
	@Transient
	public String getReportingTo() {
		return reportingTo;
	}

	//@Transient
	public void setReportingTo(String reportingTo) {
		this.reportingTo = reportingTo;
	}


	public User() {
    }
	/*@Column(name="functional_role")
	public int getFunctionalRole() {
		return functionalRole;
	}



	public void setFunctionalRole(int functionalRole) {
		this.functionalRole = functionalRole;
	}*/


	//@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="username")
	public String getName() {
		return this.name;
	}

	


	public String getManager_name() {
		return manager_name;
	}


	public void setManager_name(String manager_name) {
		this.manager_name = manager_name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Boolean getActive() {
		return this.active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}


	public String getDefaultproject() {
		return this.defaultproject;
	}

	public void setDefaultproject(String defaultproject) {
		this.defaultproject = defaultproject;
	}

	
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPhone(){
		return this.phone;
	}
	
	public void setPhone(String phone){
		this.phone = phone;
	}

	//@Transient
	//@Column(name="id")
	@Id	
	@SequenceGenerator(name="USER_ID_GENERATOR", sequenceName="users_gid_seq")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="USER_ID_GENERATOR")	
	@Column(name="id", insertable = false, updatable = false, unique=true, nullable=false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


    @Temporal( TemporalType.DATE)
	public Date getLastactivitydate() {
		return this.lastactivitydate;
	}

	public void setLastactivitydate(Date lastactivitydate) {
		this.lastactivitydate = lastactivitydate;
	}


	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


    @Temporal( TemporalType.DATE)
	public Date getPasswordexpires() {
		return this.passwordexpires;
	}

	public void setPasswordexpires(Date passwordexpires) {
		this.passwordexpires = passwordexpires;
	}


	public String getTenantid() {
		return this.tenantid;
	}

	public void setTenantid(String tenantid) {
		this.tenantid = tenantid;
	}


	
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "user_project", joinColumns = { @JoinColumn(name = "userid") }, inverseJoinColumns = { @JoinColumn(name = "project") })
		
	public Set<Project> getProjects() {
		return projects;
	}


	public void setProjects(Set<Project> projects) {
		this.projects = projects;
	}
	
	//bi-directional many-to-one association to UserRole
	//@OneToMany(mappedBy="user")
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "user_role", joinColumns = { @JoinColumn(name = "userid") }, inverseJoinColumns = { @JoinColumn(name = "role") })
	 public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}



	public String getAuthkey() {
		return authkey;
	}



	public void setAuthkey(String authkey) {
		this.authkey = authkey;
	}

	/*@Column(name="manager_id")
	public Integer getManagerid() {
		return this.managerid;
	}

	public void setManagerid(Integer managerid) {
		this.managerid = managerid;
	}
	*/
	
	
	
}