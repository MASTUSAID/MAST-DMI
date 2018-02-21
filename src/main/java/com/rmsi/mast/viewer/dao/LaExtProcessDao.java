package com.rmsi.mast.viewer.dao;

import java.util.List;

import com.rmsi.mast.studio.dao.GenericDAO;
import com.rmsi.mast.studio.domain.LaExtProcess;

public interface LaExtProcessDao extends GenericDAO<LaExtProcess, Integer>{

	List<LaExtProcess> getAllProcessDetails();
}
