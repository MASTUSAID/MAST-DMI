package com.rmsi.mast.studio.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rmsi.mast.studio.dao.GenderDAO;
import com.rmsi.mast.studio.domain.Gender;
import com.rmsi.mast.studio.service.GenderService;

@Service
public class GenderServiceimpl implements GenderService {

	
	@Autowired
	GenderDAO genderDAO;
	
	@Override
	public List<Gender> getAllGender() {
		return genderDAO.getAllGender();
	}

}
