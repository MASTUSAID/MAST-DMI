package com.rmsi.mast.viewer.dao;

import com.rmsi.mast.studio.dao.GenericDAO;
import com.rmsi.mast.studio.domain.Status;

public interface StatusDAO extends GenericDAO<Status, Integer>{

	Status getStatusById(int statusId);
}
