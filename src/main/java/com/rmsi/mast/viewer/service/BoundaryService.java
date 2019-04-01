package com.rmsi.mast.viewer.service;

import com.rmsi.mast.studio.domain.Boundary;
import com.rmsi.mast.studio.domain.BoundaryPoint;
import com.rmsi.mast.studio.domain.BoundaryPointDoc;
import java.util.List;

public interface BoundaryService {
    BoundaryPoint getBoundaryPoint(Integer id);
    List<BoundaryPoint> getBoundaryPointsByProject(int projectId);
    BoundaryPoint saveBoundaryPoint(BoundaryPoint point);
    List<BoundaryPointDoc> getBoundaryPointDocs(Integer pointId);
    BoundaryPointDoc getBoundaryPointDoc(Integer id);
    boolean deleteBoundaryPointDoc(Integer id);
    Boundary getBoundary(Integer id);
    Boundary saveBoundary(Boundary boundary);
}
