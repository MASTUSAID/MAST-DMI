

package com.rmsi.mast.studio.dao;


import java.util.List;

import com.rmsi.mast.studio.domain.RoleModule;


public interface RoleModuleDAO extends GenericDAO<RoleModule, Long> {
	
		
	boolean deleteRoleModuleByRole(String rolename);
	List<RoleModule> getRoleModuleByroleId(Integer roleid);
	
	
}
