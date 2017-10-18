package com.rmsi.mast.studio.dao;

import com.rmsi.mast.studio.domain.AcquisitionType;

public interface AcquisitionTypeDao extends GenericDAO<AcquisitionType, Integer> {
    AcquisitionType getTypeByAttributeOptionId(int optId);
}
