

package com.rmsi.mast.studio.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rmsi.mast.studio.dao.DataTypeIdDAO;
import com.rmsi.mast.studio.domain.DatatypeId;
import com.rmsi.mast.studio.service.DataTypeIdService;

@Service
public class DataTypeIdServiceImpl implements DataTypeIdService {

	@Autowired
	DataTypeIdDAO dataTypeIdDao;
	
	
	
	@Override
	public List<DatatypeId> findallDataType() {
		return  dataTypeIdDao.findAll();
		
	}

	


}
