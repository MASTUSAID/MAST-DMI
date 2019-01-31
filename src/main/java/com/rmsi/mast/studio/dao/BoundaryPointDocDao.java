package com.rmsi.mast.studio.dao;

import com.rmsi.mast.studio.domain.BoundaryPointDoc;
import java.util.List;

public interface BoundaryPointDocDao extends GenericDAO<BoundaryPointDoc, Integer> {
    List<BoundaryPointDoc> getDocumentsByPoint(Integer pointId);
    BoundaryPointDoc getDocument(Integer id);
    BoundaryPointDoc save(BoundaryPointDoc doc);
    Boolean delete(Integer id);
}
