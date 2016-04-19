

package com.rmsi.mast.studio.dao;


import com.rmsi.mast.studio.domain.RoleModule;


public interface RoleModuleDAO extends GenericDAO<RoleModule, Long> {
	
		
	boolean deleteRoleModuleByRole(String rolename);
	
}
