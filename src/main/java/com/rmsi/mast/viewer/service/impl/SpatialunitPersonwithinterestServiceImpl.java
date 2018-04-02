package com.rmsi.mast.viewer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rmsi.mast.studio.domain.SpatialUnitPersonWithInterest;
import com.rmsi.mast.studio.mobile.dao.SpatialUnitPersonWithInterestDao;
import com.rmsi.mast.viewer.service.SpatialunitPersonwithinterestService;


@Service
public class SpatialunitPersonwithinterestServiceImpl implements SpatialunitPersonwithinterestService {

	@Autowired
	SpatialUnitPersonWithInterestDao  spatialUnitPersonWithInterestDao;
	


	@Override
	@Transactional
	public SpatialUnitPersonWithInterest save(SpatialUnitPersonWithInterest objSpatialunitPersonwithinterest) {
		try{
			
			return spatialUnitPersonWithInterestDao.makePersistent(objSpatialunitPersonwithinterest);
		}catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
		
	}



	@Override
	public SpatialUnitPersonWithInterest findSpatialUnitPersonWithInterestById(Long id) {
		
		return spatialUnitPersonWithInterestDao.findSpatialUnitPersonWithInterestById(id);
	}



	@Override
	@Transactional
	public SpatialUnitPersonWithInterest findByObject(
			SpatialUnitPersonWithInterest objSpatialunitPersonwithinterest, Long landId) {
		// TODO Auto-generated method stub
		return	spatialUnitPersonWithInterestDao.findSpatialUnitPersonWithInterestByObj(objSpatialunitPersonwithinterest, landId);
		
		  
	}

}
