package com.rmsi.mast.studio.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rmsi.mast.studio.dao.RoleModuleDAO;
import com.rmsi.mast.studio.domain.RoleModule;
import com.rmsi.mast.studio.service.RoleModuleService;

@Service
public class RoleModuleServiceimpl implements RoleModuleService {

	
	@Autowired
	RoleModuleDAO reoleModuleDAO;

	@Override
	public List<RoleModule> getRoleModuleByroleId(Integer roleid) {
		// TODO Auto-generated method stub
		return reoleModuleDAO.getRoleModuleByroleId(roleid);
	}
	
	
	
	
}
