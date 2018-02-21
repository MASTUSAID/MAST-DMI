package com.rmsi.mast.studio.dao;

import com.rmsi.mast.studio.domain.DisputeType;
import com.rmsi.mast.studio.domain.LaExtDispute;

public interface DisputeTypeDao extends GenericDAO<DisputeType, Integer> {
	
	DisputeType findLaExtDisputeTypeByid(Integer disputeid); 

}
