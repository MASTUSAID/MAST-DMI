package com.rmsi.mast.viewer.dao;

import java.util.List;

import com.rmsi.mast.studio.dao.GenericDAO;
import com.rmsi.mast.studio.domain.Gender;

public interface GenderLDao extends GenericDAO<Gender, Integer>{

	Gender getGenderById(long genderId);
	List<Gender> getGenderDetails(); 
}
