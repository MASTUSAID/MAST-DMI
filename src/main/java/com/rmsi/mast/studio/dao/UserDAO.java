

package com.rmsi.mast.studio.dao;

import java.util.ArrayList;
import java.util.List;

import com.rmsi.mast.studio.domain.User;
import com.rmsi.mast.studio.domain.UserOrder;

/**
 * @author Aparesh.Chakraborty
 *
 */


public interface UserDAO extends GenericDAO<User, Long> {
	
	User findByName(String name);
	boolean deleteUserByName(Integer id);
	User findByUniqueName(String username);
	User findByEmail(String email);
	List<UserOrder> getUserOrderedById();
	//Added by PBJ
	User findUserByUserId(Integer id);
	
	List<User> findUserByRole();
	List<User> selectedUserByUserRole(List<String> lstRole);
	boolean duplicatevalidate(String userName);
	boolean checkreprotinmngr(String repotingId);
	List<User> findUserByUser(ArrayList<Integer> userid);
	List<User> findAllActiveUser();
	List<User> findactiveUsers();
	
	
	//User findUserById(Integer id);
}

