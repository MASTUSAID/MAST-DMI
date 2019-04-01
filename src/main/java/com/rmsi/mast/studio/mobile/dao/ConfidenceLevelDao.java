package com.rmsi.mast.studio.mobile.dao;

import com.rmsi.mast.studio.dao.GenericDAO;
import com.rmsi.mast.studio.domain.ConfidenceLevel;
import java.util.List;

public interface ConfidenceLevelDao extends GenericDAO<ConfidenceLevel, Integer> {
    List<ConfidenceLevel> getAll();
    ConfidenceLevel getById(int id);
}
