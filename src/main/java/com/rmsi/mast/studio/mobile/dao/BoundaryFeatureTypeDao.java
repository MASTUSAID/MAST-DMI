package com.rmsi.mast.studio.mobile.dao;

import com.rmsi.mast.studio.dao.GenericDAO;
import com.rmsi.mast.studio.domain.BoundaryFeatureType;
import java.util.List;

public interface BoundaryFeatureTypeDao extends GenericDAO<BoundaryFeatureType, Integer> {
    List<BoundaryFeatureType> getAll();
    BoundaryFeatureType getById(int id);
}
