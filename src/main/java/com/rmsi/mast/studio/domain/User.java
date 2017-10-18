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
@Table(name = "users")
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
    private Set<Project> projects;
    private Set<Role> roles;
    private String authkey;
    private String manager_name;
    private String username;
    private String reportingTo;
    
    @Transient
    public String getReportingTo() {
        return reportingTo;
    }

    public void setReportingTo(String reportingTo) {
        this.reportingTo = reportingTo;
    }

    public User() {
    }

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

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Id
    @SequenceGenerator(name = "USER_ID_GENERATOR", sequenceName = "users_gid_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_ID_GENERATOR")
    @Column(name = "id", insertable = false, updatable = false, unique = true, nullable = false)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Temporal(TemporalType.DATE)
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

    @Temporal(TemporalType.DATE)
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
    @JoinTable(name = "user_project", joinColumns = {
        @JoinColumn(name = "userid")}, inverseJoinColumns = {
        @JoinColumn(name = "project")})

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = {
        @JoinColumn(name = "userid")}, inverseJoinColumns = {
        @JoinColumn(name = "role")})
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
