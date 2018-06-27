package com.rmsi.mast.viewer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.rmsi.mast.studio.domain.LaExtRegistrationLandShareType;
import com.rmsi.mast.viewer.dao.hibernate.LaExtRegistrationLandShareTypeHibernateDao;

import com.rmsi.mast.studio.domain.LaExtRegistrationLandShareType;
import com.rmsi.mast.viewer.dao.LaExtRegistrationLandShareTypeDao;

import com.rmsi.mast.viewer.service.LaExtRegistrationLandShareTypeService;


@Service
public class LaExtRegistrationLandShareTypeServiceImpl implements LaExtRegistrationLandShareTypeService {

	
	@Autowired
	LaExtRegistrationLandShareTypeHibernateDao laExtRegistrationLandShareTypeHibernateDao;
	
	
	@Override
	public void addLaExtRegistrationLandShareType(LaExtRegistrationLandShareType objLaExtRegistrationLandShareType) {
	   
		try{
		laExtRegistrationLandShareTypeHibernateDao.makePersistent(objLaExtRegistrationLandShareType);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}


	@Autowired
	LaExtRegistrationLandShareTypeDao laExtRegistrationLandShareTypedao;
	
	@Override
	public LaExtRegistrationLandShareType getShareTypeObjectByLandId(Long landid) {
		// TODO Auto-generated method stub
		return laExtRegistrationLandShareTypedao.getShareTypeObjectByLandId(landid);
	}

	@Override
	public boolean updateRegistrationSharetype(
			Long sharetypeid, Long landId) {
		return laExtRegistrationLandShareTypedao.updateRegistrationSharetype(sharetypeid, landId);
		
		// TODO Auto-generated method stub
		
	}


}
