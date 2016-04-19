

package com.rmsi.mast.studio.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails; 
import org.springframework.security.core.userdetails.UserDetailsService; 
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rmsi.mast.security.Assembler;
import com.rmsi.mast.studio.dao.UserDAO;
import com.rmsi.mast.studio.domain.User;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService{
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private Assembler assembler;
	
	@Override
	@Transactional(readOnly = true) 
	public UserDetails loadUserByUsername(String userName)
			throws UsernameNotFoundException, DataAccessException {
		
		UserDetails userDetails = null;
		User user = userDAO.findByUniqueName(userName);
		
		System.out.println("Check authentication success");
		if(user == null){
			throw new UsernameNotFoundException("user not found."); 
		}else{
			System.out.println("Check if password has not expired");
			Date pwdExpires = user.getPasswordexpires();
			Date currentDate = new Date();
			if(currentDate.after(pwdExpires)){
				//System.out.println("------Password expired-------");
				throw new UsernameNotFoundException("user password expired.");
			}else{
				Date d = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
				String date = sdf.format(d);
				System.out.println("=====" + user.getEmail() + " logged in successfully at " + date);
			}
		}
		return assembler.buildUserFromUserEntity(user);
	}
	

}
