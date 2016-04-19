

package com.rmsi.mast.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public class User {
	private String userName;
	private String password;
	private boolean active;
	private Collection<GrantedAuthority> authorities;
	
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public Collection<GrantedAuthority> getAuthorities() {
		return authorities;
	}
	public void setAuthorities(Collection<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}
	
	
	
	
}
