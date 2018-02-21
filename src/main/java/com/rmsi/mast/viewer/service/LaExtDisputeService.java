package com.rmsi.mast.viewer.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.rmsi.mast.studio.domain.LaExtDispute;

public interface LaExtDisputeService {

	
	@Transactional
	LaExtDispute saveLaExtDispute(LaExtDispute objLaExtDispute);
	
	
	LaExtDispute findLaExtDisputeByid(Integer disputeid); 
	LaExtDispute findLaExtDisputeByLandId(Integer landid); 
 

}
