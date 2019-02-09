package com.rmsi.mast.studio.dao;

import com.rmsi.mast.studio.domain.Boundary;

public interface BoundaryDao extends GenericDAO<Boundary, Integer> {
    Boundary getBoundaryByProject(Integer projectId);
    Boundary getBoundary(Integer id);
    Boundary save(Boundary boundary);
    Boolean delete(Boundary boundary);
}
