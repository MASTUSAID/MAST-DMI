package com.rmsi.mast.studio.service;

import java.util.List;

import com.rmsi.mast.studio.domain.RoleModule;

public interface RoleModuleService {

	
	List<RoleModule> getRoleModuleByroleId(Integer roleid);
}
