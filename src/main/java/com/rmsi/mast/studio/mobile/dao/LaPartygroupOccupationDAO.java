package com.rmsi.mast.studio.mobile.dao;

import com.rmsi.mast.studio.dao.GenericDAO;
import com.rmsi.mast.studio.domain.AttributeOptions;
import com.rmsi.mast.studio.domain.LaPartygroupOccupation;

public interface LaPartygroupOccupationDAO extends GenericDAO<LaPartygroupOccupation, Integer> {
	
	LaPartygroupOccupation getOccupation(Integer Id);

}
