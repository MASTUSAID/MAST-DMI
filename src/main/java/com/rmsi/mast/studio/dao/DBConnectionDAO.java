package com.rmsi.mast.studio.dao;

import com.rmsi.mast.studio.domain.DbConnection;

public interface DBConnectionDAO extends GenericDAO<DbConnection, Long>  {

	boolean deleteConnectionByName(String name);
	
	DbConnection findByName(String name);
}
