

package com.rmsi.mast.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.userdetails.User;

import com.rmsi.mast.studio.domain.Role;
import com.rmsi.mast.studio.domain.UserRole;

@Service("assembler")
public class Assembler {
	@SuppressWarnings("deprecation")
	@Transactional(readOnly = true) 
	public User buildUserFromUserEntity(
			com.rmsi.mast.studio.domain.User userEntity) { 

		//String userName = userEntity.getEmail();
		//changed by Prashant from email to username
		
		// Need to impliment User roldid
		
		String userName = userEntity.getUsername();
		String password  = userEntity.getPassword();
		boolean enabled = userEntity.getActive();
		boolean accountNonExpired = userEntity.getActive();
		boolean credentialsNonExpired = userEntity.getActive();
		boolean accountNonLocked = userEntity.getActive();
		
		Collection<GrantedAuthority> authorities=null;;
		try {
			authorities = new ArrayList<GrantedAuthority>();
			//authorities.add(new GrantedAuthorityImpl("ROLE_ADMIN"));
			authorities.add(new GrantedAuthorityImpl(userEntity.getUserRole().iterator().next().getRoleBean().getName()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		/*for (UserRole role : userEntity.getUserRole()) {       
			authorities.add(new GrantedAuthorityImpl(role.getName()));  
		} */
		
		User user = new User(userName, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		
		return user;
	}

}
