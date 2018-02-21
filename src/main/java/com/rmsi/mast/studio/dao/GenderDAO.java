package com.rmsi.mast.studio.dao;
import java.util.List;

import com.rmsi.mast.studio.domain.Gender;

public interface GenderDAO extends GenericDAO<Gender, Long> {
	
	public List<Gender> getAllGender();

}
