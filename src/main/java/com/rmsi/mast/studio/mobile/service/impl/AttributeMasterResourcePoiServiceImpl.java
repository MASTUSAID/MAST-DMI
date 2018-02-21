package com.rmsi.mast.studio.mobile.service.impl;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rmsi.mast.studio.domain.AttributeMaster;
import com.rmsi.mast.studio.domain.AttributeMasterResourcePOI;
import com.rmsi.mast.studio.domain.Project;
import com.rmsi.mast.studio.mobile.dao.AttributeMasterResourcePoiDAO;
import com.rmsi.mast.studio.mobile.service.AttributeMasterResourcePoiService;

@Service
public class AttributeMasterResourcePoiServiceImpl implements AttributeMasterResourcePoiService{
	
	@Autowired
	AttributeMasterResourcePoiDAO attributeMasterResourcePoidao;

	@Override
	public List<AttributeMasterResourcePOI> getAllPOIAttributteMaster(Integer projectId) { 
		List<AttributeMasterResourcePOI> attributeMasterList = attributeMasterResourcePoidao.getAttributeMasterResourcePOIByProject(projectId);
    try {
        Iterator<AttributeMasterResourcePOI> surveyProjectAttribItr = attributeMasterList.iterator();
        while (surveyProjectAttribItr.hasNext()) {
        	AttributeMasterResourcePOI attributeMaster = surveyProjectAttribItr.next();
            if (attributeMaster.getLaExtAttributedatatype().getDatatype().equalsIgnoreCase("dropdown")) {
//             attributeMaster.setOptions(attributeOptions.getAttributeOptions(attributeMaster.getPoiattributemasterid()));
            }
        }
    } catch (Exception ex) {
        System.out.println("Exception ::: " + ex);
    }
    return attributeMasterList;}

}
