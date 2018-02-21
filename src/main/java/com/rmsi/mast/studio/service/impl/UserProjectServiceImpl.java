package com.rmsi.mast.studio.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rmsi.mast.studio.dao.UserProjectDAO;
import com.rmsi.mast.studio.domain.UserProject;
import com.rmsi.mast.studio.service.UserProjectService;

@Service
public class UserProjectServiceImpl implements UserProjectService {

	@Autowired
	UserProjectDAO userProjectDAO;
	
	
	@Override
	public List<UserProject> findAllUserProjectByUserID(Long id) {
		// TODO Auto-generated method stub
		return userProjectDAO.findAllUserProjectByUserID(id);
	}

}
