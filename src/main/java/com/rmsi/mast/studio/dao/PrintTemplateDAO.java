
package com.rmsi.mast.studio.dao;

import java.util.List;

import com.rmsi.mast.studio.domain.Printtemplate;

/**
 * @author Aparesh.Chakraborty
 *
 */


public interface PrintTemplateDAO extends GenericDAO<Printtemplate, Long> {
	
	List<Printtemplate> findByProjectName(String name);
	
}

