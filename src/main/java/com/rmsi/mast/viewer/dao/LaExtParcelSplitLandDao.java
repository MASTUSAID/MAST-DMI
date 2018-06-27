package com.rmsi.mast.viewer.dao;

import com.rmsi.mast.studio.dao.GenericDAO;
import com.rmsi.mast.studio.domain.LaExtParcelSplitLand;

public interface LaExtParcelSplitLandDao extends GenericDAO<LaExtParcelSplitLand, Long>{

	boolean deleteLaExtParcelSplitLandService(Long landid);
}
