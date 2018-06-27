package com.rmsi.mast.viewer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rmsi.mast.studio.domain.LaExtParcelSplitLand;
import com.rmsi.mast.viewer.dao.LaExtParcelSplitLandDao;
import com.rmsi.mast.viewer.service.LaExtParcelSplitLandService;


@Service
public class LaExtParcelSplitLandServiceimpl implements LaExtParcelSplitLandService {

	
	
	@Autowired
	LaExtParcelSplitLandDao  laExtParcelSplitLandDao;
	
	
	@Override
	public void addLaExtParcelSplitLandService(LaExtParcelSplitLand objLaExtParcelSplitLandService) {
	
		try{
			laExtParcelSplitLandDao.makePersistent(objLaExtParcelSplitLandService);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}


	@Override
	public boolean deleteLaExtParcelSplitBylandId(Long landid) {
		return laExtParcelSplitLandDao.deleteLaExtParcelSplitLandService(landid);
	}

}
