package com.rmsi.mast.viewer.service.impl;

import com.rmsi.mast.studio.dao.BoundaryDao;
import com.rmsi.mast.studio.dao.BoundaryPointDao;
import com.rmsi.mast.studio.dao.BoundaryPointDocDao;
import com.rmsi.mast.studio.domain.Boundary;
import com.rmsi.mast.studio.domain.BoundaryPoint;
import com.rmsi.mast.studio.domain.BoundaryPointDoc;
import com.rmsi.mast.viewer.service.BoundaryService;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BoundaryServiceImpl implements BoundaryService {

    @Autowired
    BoundaryPointDao boundaryPointDao;

    @Autowired
    BoundaryDao boundaryDao;
    
    @Autowired
    BoundaryPointDocDao boundaryPointDocDao;

    private static final Logger logger = Logger.getLogger(BoundaryServiceImpl.class.getName());

    @Override
    public BoundaryPoint getBoundaryPoint(Integer id) {
        return boundaryPointDao.getPoint(id);
    }

    @Transactional
    @Override
    public BoundaryPoint saveBoundaryPoint(BoundaryPoint point) {
        return boundaryPointDao.save(point);
    }

    @Override
    public List<BoundaryPointDoc> getBoundaryPointDocs(Integer pointId) {
        return boundaryPointDocDao.getDocumentsByPoint(pointId);
    }

    @Override
    public BoundaryPointDoc getBoundaryPointDoc(Integer id) {
        return boundaryPointDocDao.getDocument(id);
    }

    @Override
    public boolean deleteBoundaryPointDoc(Integer id) {
        return boundaryPointDocDao.delete(id);
    }

    @Override
    public Boundary getBoundary(Integer id) {
        return boundaryDao.getBoundary(id);
    }

    @Transactional
    @Override
    public Boundary saveBoundary(Boundary boundary) {
        return boundaryDao.save(boundary);
    }
}
