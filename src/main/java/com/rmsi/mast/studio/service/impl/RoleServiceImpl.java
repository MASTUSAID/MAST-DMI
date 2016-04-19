

package com.rmsi.mast.studio.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import com.googlecode.ehcache.annotations.Cacheable;
//import com.googlecode.ehcache.annotations.TriggersRemove;

import com.rmsi.mast.studio.dao.RoleDAO;
import com.rmsi.mast.studio.dao.RoleModuleDAO;
import com.rmsi.mast.studio.dao.UserRoleDAO;
import com.rmsi.mast.studio.domain.Role;
import com.rmsi.mast.studio.service.RoleService;
import com.rmsi.mast.studio.util.ConfigurationUtil;

/**
 * @author Aparesh.Chakraborty
 *
 */
@Service
public class RoleServiceImpl implements RoleService{

	@Autowired
	private RoleDAO roleDAO;
	
	@Autowired
	private RoleModuleDAO roleModuleDAO;
	
	@Autowired
	private UserRoleDAO userRoleDAO;

	@Override
	//@TriggersRemove(cacheName="roleFBNCache", removeAll=true)
	public void addRole(Role role) {
		
		roleDAO.makePersistent(role);
	}

	@Override
	public void deleteRole() {
		// TODO Auto-generated method stub

	}

	@Override
	//@TriggersRemove(cacheName="roleFBNCache", removeAll=true)
	public void deleteRoleById(String role) {
		
		System.out.println("######Role name: "+role);
				//delete roles from userrole
		userRoleDAO.deleteUserRoleByRole(role);
		
				//delete roles from rolemodule
		roleModuleDAO.deleteRoleModuleByRole(role);
		
				//delete role from role
		roleDAO.deleteRole(role);
		
	}

	@Override
	public void updateRole(Role role) {
		// TODO Auto-generated method stub

	}

	@Override
	//@Cacheable(cacheName="roleFBNCache")
	public Role findRoleById(Long id) {
		return roleDAO.findById(id, false);

	}

	@Override
	//@Cacheable(cacheName="roleFBNCache")
	public List<Role> findAllRole() {
		return roleDAO.findAll();
	}

	@Override
	//@Cacheable(cacheName="roleFBNCache")
	public Role findRoleByName(String name) {
		return roleDAO.findByName(name);
	}
	
	@Override
	public String getRestrictedRoles(){
		String displayRolesAndAssignRoles = ConfigurationUtil.getProperty("workcommitment.backlog.displayroles")+
		":" +
		ConfigurationUtil.getProperty("workcommitment.assignment.enableroles")+":" +
		ConfigurationUtil.getProperty("workcommitment.superUsersRole"); 
		return displayRolesAndAssignRoles;
		
	}

	@Override
	public List<Role> findAllRole(int roleId) {
		return roleDAO.findAll(roleId);
	}
	
	
}
