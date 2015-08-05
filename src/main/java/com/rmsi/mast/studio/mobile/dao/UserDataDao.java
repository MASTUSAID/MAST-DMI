/**
 * 
 */
package com.rmsi.mast.studio.mobile.dao;

import com.rmsi.mast.studio.dao.GenericDAO;
import com.rmsi.mast.studio.domain.User;

/**
 * @author shruti.thakur
 *
 */
public interface UserDataDao extends GenericDAO<User, Long>{
	
	/**
	 * This method will be used to authenticate user based on emailId of the user
	 * 
	 * @param email
	 * @return
	 */
	User authenticateMobileUser(String email);

	/**
	 * This method will get User by userId 
	 * 
	 * @param userId
	 * @return
	 */
	User getUserByUserId(int userId);
	
}
