package com.rmsi.mast.studio.domain;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.rmsi.mast.studio.util.JsonDateSerializer;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * The persistent class for the user database table.
 *
 */
@Entity
@Table(name = "la_ext_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    //@Column(name="userid",insertable=false, updatable=false)
    //private long id;
	@Id
    @SequenceGenerator(name = "USER_ID_GENERATOR", sequenceName = "la_ext_user_userid_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_ID_GENERATOR")
    @Column(name = "userid", insertable = false, updatable = false, unique = true, nullable = false)
	private long id;
	
    private String name;
	@Column(name="isactive")
    private Boolean active;
	//private String defaultproject;
	@Column(name="emailid")
    private String email;
	
	@Column(name="contactno")
    private String phone;
    
    private String password;
    
    @Column(name="authenticationkey")
    private String authkey;
	
    @Column(name="managername")
    private String manager_name;
    private String username;
    
    private Long createdby;
	private Long modifiedby;
	private Date createddate;
    private Date modifieddate;
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date passwordexpires ;
    private Date lastactivitydate ;
    
    
    private String defaultproject;
	
    public Date getPasswordexpires() {
		return passwordexpires;
	}

	public void setPasswordexpires(Date passwordexpires) {
		this.passwordexpires = passwordexpires;
	}

	public Date getLastactivitydate() {
		return lastactivitydate;
	}

	public void setLastactivitydate(Date lastactivitydate) {
		this.lastactivitydate = lastactivitydate;
	}

	@Column(name="address")
	private String address;
	
	@Transient
	 private String reportingTo;
	    
	 
	@Transient
	private  Set<Project> project;
	
	
  

    public String getReportingTo() {
		return reportingTo;
	}

	
	@Column(name="genderid")
	private int gender;
    
   
    
 
    @OneToMany(mappedBy="user",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	private Set<UserRole> userRole;

	
 
	/*@OneToMany(mappedBy="user",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	private Set<UserProject> userProject;
*/
	
   
	public User() {
    }

	public long getId() {
		return id;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAuthkey() {
		return authkey;
	}

	public void setAuthkey(String authkey) {
		this.authkey = authkey;
	}

	public String getManager_name() {
		return manager_name;
	}

	public void setManager_name(String manager_name) {
		this.manager_name = manager_name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	

	public Long getCreatedby() {
		return createdby;
	}

	public void setCreatedby(Long createdby) {
		this.createdby = createdby;
	}

	public Long getModifiedby() {
		return modifiedby;
	}

	public void setModifiedby(Long modifiedby) {
		this.modifiedby = modifiedby;
	}

	public Date getCreateddate() {
		return createddate;
	}

	public void setCreateddate(Date createddate) {
		this.createddate = createddate;
	}

	public Date getModifieddate() {
		return modifieddate;
	}

	public void setModifieddate(Date modifieddate) {
		this.modifieddate = modifieddate;
	}

	public Set<UserRole> getUserRole() {
		return userRole;
	}

	public void setUserRole(Set<UserRole> userRole) {
		this.userRole = userRole;
	}

	public void setReportingTo(String reportingTo) {
		this.reportingTo = reportingTo;
	}

	public String getDefaultproject() {
		return defaultproject;
	}

	public void setDefaultproject(String defaultproject) {
		this.defaultproject = defaultproject;
	}

	@Transient
	public Set<Project> getProject() {
		return project;
	}

	@Transient
	public void setProject(Set<Project> project) {
		this.project = project;
	}

	
	
    
}
