

package com.rmsi.mast.studio.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.rmsi.mast.studio.domain.Role;

/**
 * @author Aparesh.Chakraborty
 *
 */
public interface RoleService {

	@Transactional
	void addRole(Role role);
	
	@Transactional
	void deleteRole();

	@Transactional
	void deleteRoleById(String id);

	@Transactional
	void updateRole(Role role);

	@Transactional(readOnly=true)
	Role findRoleById(Long id);

	@Transactional(readOnly=true)
	List<Role> findAllRole();
	
	@Transactional(readOnly=true)
	Role findRoleByName(String name);

	String getRestrictedRoles();

	List<Role> findAllRole(int roleId);
	
}
