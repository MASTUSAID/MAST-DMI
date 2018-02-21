package com.rmsi.mast.viewer.dao;

import java.util.List;

import com.rmsi.mast.studio.dao.GenericDAO;
import com.rmsi.mast.studio.domain.ShareType;

public interface LandShareTypeDao extends GenericDAO<ShareType, Integer>{

	List<ShareType> getAlllandsharetype();
}
