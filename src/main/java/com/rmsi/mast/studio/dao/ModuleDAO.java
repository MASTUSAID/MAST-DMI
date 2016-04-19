
package com.rmsi.mast.studio.dao;

import java.util.List;

import com.rmsi.mast.studio.domain.Module;

/**
 * @author Aparesh.Chakraborty
 *
 */


public interface ModuleDAO extends GenericDAO<Module, Long> {
	
	Module findByName(String name);

	
}

