
package com.rmsi.mast.studio.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rmsi.mast.studio.dao.ModuleDAO;
import com.rmsi.mast.studio.domain.Module;
import com.rmsi.mast.studio.service.ModuleService;

/**
 * @author Aparesh.Chakraborty
 *
 */
@Service
public class ModuleServiceImpl implements ModuleService{

	@Autowired
	private ModuleDAO moduleDAO;

	@Override
	public Module addModule(Module module) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteModule() {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteModuleById(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateModule(Module module) {
		// TODO Auto-generated method stub

	}

	@Override
	public Module findModuleById(Long id) {
		return moduleDAO.findById(id, false);

	}

	@Override
	public List<Module> findAllModule() {
		return moduleDAO.findAll();
	}

	@Override
	public Module findModuleByName(String name) {
		return moduleDAO.findByName(name);
	}
	
}
