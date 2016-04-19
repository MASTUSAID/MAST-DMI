

package com.rmsi.mast.studio.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.rmsi.mast.studio.domain.Printtemplate;

/**
 * @author Aparesh.Chakraborty
 *
 */
public interface PrintTemplateService {

	
	List<Printtemplate> findByProjectName(String name);
	List<Printtemplate> findAllPrintTemplates();

	
}
