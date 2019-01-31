package com.rmsi.mast.studio.dao;

import com.rmsi.mast.studio.domain.BoundaryPoint;
import java.util.List;

public interface BoundaryPointDao extends GenericDAO<BoundaryPoint, Integer> {
    List<BoundaryPoint> getPointsByProject(Integer projectId);
    BoundaryPoint getPoint(Integer id);
    BoundaryPoint save(BoundaryPoint point);
    Boolean delete(BoundaryPoint point);
}
