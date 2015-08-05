package com.rmsi.mast.studio.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.rmsi.mast.studio.domain.DbConnection;

public interface DBConnectionService {
	
	@Transactional(readOnly=true)
	List<DbConnection> findAllConnections();
	
	@Transactional
	boolean deleteConnectionByName(String name);
	
	@Transactional(readOnly=true)
	DbConnection findConnectionByName(String name);

	@Transactional
	DbConnection saveConnection(DbConnection connection);
}
