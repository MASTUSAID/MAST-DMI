package com.rmsi.mast.viewer.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rmsi.mast.studio.dao.LaExtDisputeDAO;
import com.rmsi.mast.studio.domain.LaExtDispute;
import com.rmsi.mast.viewer.service.LaExtDisputeService;

@Service
public class LaExtDisputeServiceImpl implements LaExtDisputeService {
	
	
	@Autowired
	LaExtDisputeDAO laExtDisputeDAO;

	@Override
	public LaExtDispute saveLaExtDispute(LaExtDispute objLaExtDispute) {
		return laExtDisputeDAO.saveLaExtDispute(objLaExtDispute);
	}

	@Override
	public LaExtDispute findLaExtDisputeByid(Integer disputeid) {
		// TODO Auto-generated method stub
		return laExtDisputeDAO.findLaExtDisputeByid(disputeid);
	}

	@Override
	public LaExtDispute findLaExtDisputeByLandId(Integer landid) {
		return laExtDisputeDAO.findLaExtDisputeByLandId(landid);
	}
	
	
}
