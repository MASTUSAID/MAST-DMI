

package com.rmsi.mast.studio.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.rmsi.mast.studio.domain.Module;

/**
 * @author Aparesh.Chakraborty
 *
 */
public interface ModuleService {

	@Transactional
	Module addModule(Module module);
	
	@Transactional
	void deleteModule();

	@Transactional
	void deleteModuleById(Long id);

	@Transactional
	void updateModule(Module module);

	@Transactional(readOnly=true)
	Module findModuleById(Long id);

	@Transactional(readOnly=true)
	List<Module> findAllModule();
	
	@Transactional(readOnly=true)
	Module findModuleByName(String name);
	
}
