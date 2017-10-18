package com.rmsi.mast.studio.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rmsi.mast.studio.dao.AttributeCategoryDAO;
import com.rmsi.mast.studio.dao.AttributeMasterDAO;
import com.rmsi.mast.studio.dao.DataTypeIdDAO;
import com.rmsi.mast.studio.dao.ProjectAttributeDAO;
import com.rmsi.mast.studio.domain.AttributeCategory;
import com.rmsi.mast.studio.domain.AttributeMaster;
import com.rmsi.mast.studio.domain.DatatypeId;
import com.rmsi.mast.studio.service.AttributeMasterService;

@Service
public class AttributeMasterServiceImpl implements AttributeMasterService {

    private static final Logger logger = Logger.getLogger(AttributeMasterServiceImpl.class);

    @Autowired
    private AttributeMasterDAO attributemasterDAO;

    @Autowired
    private DataTypeIdDAO dataTypeIdDAO;

    @Autowired
    private AttributeCategoryDAO attributecategoryDAO;

    @Autowired
    private ProjectAttributeDAO projectAttributeDAO;

    @Override
    public DatatypeId findDataTypeById(long id) {
        return dataTypeIdDAO.findById(id, false);

    }

    @Override
    public AttributeMaster addAttributeMaster(AttributeMaster attributemaster) {
        try {
            return attributemasterDAO.makePersistent(attributemaster);
        } catch (Exception e) {
            logger.error(e);
        }
        return null;
    }

    @Override
    public List<AttributeMaster> findAllAttributeMasater() {
        return attributemasterDAO.findAllAttributeMaster();
    }

    @Override
    public boolean deleteAttribute(Long id) {
        return attributemasterDAO.deleteEntry(id);
    }

    @Override
    public AttributeCategory findCategoryById(long id) {
        return attributecategoryDAO.findById(id, false);
    }

    @Override
    public List<AttributeMaster> displaySelectedCategory(Long id) {
        return attributemasterDAO.selectedCategory(id);

    }

    @Override
    public List<AttributeMaster> displayAttribute(Long uid) {
        return attributemasterDAO.selectedList(uid);
    }

    @Override
    public boolean checkduplicate(long categoryId, String alias, String fieldName) {
        return attributemasterDAO.duplicatevalidate(categoryId, alias, fieldName);
    }

    @Override
    public boolean checkdeleteAttribute(Long id) {
        return projectAttributeDAO.checkdeleteAttribute(id);
    }

    @Override
    public List<AttributeMaster> findbyAttributeId(Long id) {
        return attributemasterDAO.findByAttributeId(id);
    }

    @Override
    public boolean checkeditduplicate(long id, long categoryId, String alias,
            String fieldName) {
        return attributemasterDAO.duplicateEditvalidate(id, categoryId, alias, fieldName);
    }

}
