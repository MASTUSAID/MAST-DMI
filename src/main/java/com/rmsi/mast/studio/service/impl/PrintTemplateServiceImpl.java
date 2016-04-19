

package com.rmsi.mast.studio.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rmsi.mast.studio.dao.PrintTemplateDAO;
import com.rmsi.mast.studio.dao.UserDAO;
import com.rmsi.mast.studio.dao.UserProjectDAO;
import com.rmsi.mast.studio.dao.UserRoleDAO;
import com.rmsi.mast.studio.domain.Printtemplate;
import com.rmsi.mast.studio.domain.Project;
import com.rmsi.mast.studio.domain.Role;
import com.rmsi.mast.studio.domain.User;
import com.rmsi.mast.studio.service.PrintTemplateService;
import com.rmsi.mast.studio.service.UserService;

/**
 * @author Aparesh.Chakraborty
 *
 */
@Service
public class PrintTemplateServiceImpl implements PrintTemplateService{

	@Autowired
	private PrintTemplateDAO printTemplateDAO;
	

	@Override
	public List<Printtemplate> findByProjectName(String name) {
		
		return printTemplateDAO.findByProjectName(name);
		
	}
	
	@Override
	public List<Printtemplate> findAllPrintTemplates() {
		
		return printTemplateDAO.findAll();
		
	}

	
}
