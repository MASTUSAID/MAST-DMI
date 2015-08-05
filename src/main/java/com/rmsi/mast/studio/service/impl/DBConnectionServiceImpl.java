package com.rmsi.mast.studio.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import com.googlecode.ehcache.annotations.Cacheable;
//import com.googlecode.ehcache.annotations.TriggersRemove;

import com.rmsi.mast.studio.dao.DBConnectionDAO;
import com.rmsi.mast.studio.domain.DbConnection;
import com.rmsi.mast.studio.service.DBConnectionService;

@Service
public class DBConnectionServiceImpl implements DBConnectionService {

	@Autowired
	private DBConnectionDAO connDao;
	
	@Override
	//@Cacheable(cacheName="dbconnFBNCache")
	public List<DbConnection> findAllConnections() {
		return connDao.findAll();
	}
	
	@Override
	//@TriggersRemove(cacheName="dbconnFBNCache", removeAll=true)
	public boolean deleteConnectionByName(String name){
		return connDao.deleteConnectionByName(name);
	}
	
	@Override
	//@Cacheable(cacheName="dbconnFBNCache")
	public DbConnection findConnectionByName(String name){
		return connDao.findByName(name);
	}
	
	@Override
	//@TriggersRemove(cacheName="dbconnFBNCache", removeAll=true)
	public DbConnection saveConnection(DbConnection connection) {
		return connDao.makePersistent(connection);

	}
}
