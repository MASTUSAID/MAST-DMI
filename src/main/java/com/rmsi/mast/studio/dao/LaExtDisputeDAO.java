package com.rmsi.mast.studio.dao;

import java.util.List;

import com.rmsi.mast.studio.domain.LaExtDispute;

public interface LaExtDisputeDAO {

	
	LaExtDispute findLaExtDisputeByid(Integer disputeid); 
	LaExtDispute saveLaExtDispute(LaExtDispute objLaExtDispute);
	LaExtDispute findLaExtDisputeByLandId(Integer landid); 
	
}
