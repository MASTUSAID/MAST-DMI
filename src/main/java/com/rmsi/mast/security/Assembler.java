

package com.rmsi.mast.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.userdetails.User;

import com.rmsi.mast.studio.domain.Role;

@Service("assembler")
public class Assembler {
	@Transactional(readOnly = true) 
	public User buildUserFromUserEntity(
			com.rmsi.mast.studio.domain.User userEntity) { 

		//String userName = userEntity.getEmail();
		//changed by Prashant from email to username
		String userName = userEntity.getUsername();
		String password  = userEntity.getPassword();
		boolean enabled = userEntity.getActive();
		boolean accountNonExpired = userEntity.getActive();
		boolean credentialsNonExpired = userEntity.getActive();
		boolean accountNonLocked = userEntity.getActive();
		
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();     
		for (Role role : userEntity.getRoles()) {       
			authorities.add(new GrantedAuthorityImpl(role.getName()));  
		} 
		
		User user = new User(userName, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		
		return user;
	}

}
