

package com.rmsi.mast.studio.dao;


import java.util.List;
import java.util.Set;

import com.rmsi.mast.studio.domain.Role;
import com.rmsi.mast.studio.domain.User;
import com.rmsi.mast.studio.domain.UserRole;

public interface UserRoleDAO extends GenericDAO<UserRole, Long> {
	
	boolean deleteUserRoleByRole(String rolename);
	
	boolean deleteUserRoleByUser(Integer username);
	
	//List<UserRole> findAllUserRole(String name);
	
	//void deleteUserRole(String name);
	
	void addUserRoles(Set<Role> roles,User user);

	List<UserRole> selectedUserByUserRole(List<String> lstRole);
	
}
