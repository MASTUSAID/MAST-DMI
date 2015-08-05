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
		String userName = userEntity.getName();
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
