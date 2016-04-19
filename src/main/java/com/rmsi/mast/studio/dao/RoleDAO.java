


package com.rmsi.mast.studio.dao;

import java.util.List;

import com.rmsi.mast.studio.domain.Role;

/**
 * @author Aparesh.Chakraborty
 *
 */


public interface RoleDAO extends GenericDAO<Role, Long> {
	
	Role findByName(String name);

	boolean deleteRole(String name);

	List<Role> findAll(int roleId);
}

