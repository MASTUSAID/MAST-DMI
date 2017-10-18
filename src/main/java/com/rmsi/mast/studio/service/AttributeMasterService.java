package com.rmsi.mast.studio.service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import com.rmsi.mast.studio.domain.AttributeCategory;
import com.rmsi.mast.studio.domain.AttributeMaster;
import com.rmsi.mast.studio.domain.DatatypeId;

public interface AttributeMasterService {

    DatatypeId findDataTypeById(long dataTypeId);

    @Transactional

    AttributeMaster addAttributeMaster(AttributeMaster attributemaster);

    @Transactional
    boolean deleteAttribute(Long id);

    List<AttributeMaster> findAllAttributeMasater();

    AttributeCategory findCategoryById(long categoryId);

    List<AttributeMaster> displaySelectedCategory(Long id);

    List<AttributeMaster> displayAttribute(Long uid);

    boolean checkduplicate(long categoryId, String alias, String fieldName);

    boolean checkdeleteAttribute(Long id);

    List<AttributeMaster> findbyAttributeId(Long id);

    boolean checkeditduplicate(long id, long categoryId, String alias, String fieldName);

}
