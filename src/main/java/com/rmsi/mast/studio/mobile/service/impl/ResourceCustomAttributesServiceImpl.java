package com.rmsi.mast.studio.mobile.service.impl;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rmsi.mast.studio.dao.ResourceCustomAttributesDAO;
import com.rmsi.mast.studio.dao.ResourceSubClassificationDAO;
import com.rmsi.mast.studio.domain.AttributeMaster;
import com.rmsi.mast.studio.domain.ResourceCustomAttributes;
import com.rmsi.mast.studio.mobile.dao.CustomAttributeOptionsDAO;
import com.rmsi.mast.studio.mobile.service.ResourceCustomAttributesService;

@Service
public class ResourceCustomAttributesServiceImpl implements ResourceCustomAttributesService{

	@Autowired
	ResourceCustomAttributesDAO resouceCustomAttributesdao;
	
	@Autowired
	CustomAttributeOptionsDAO customAttributeOptionsdao;

	@Override
	public List<ResourceCustomAttributes> getByProjectId(Integer Id) {
		// TODO Auto-generated method stub
		 List<ResourceCustomAttributes> resourceCustomAttributes = resouceCustomAttributesdao.getByProjectId(Id);
		 try {
	            Iterator<ResourceCustomAttributes> surveyProjectAttribItr = resourceCustomAttributes.iterator();
	            while (surveyProjectAttribItr.hasNext()) {
	            	ResourceCustomAttributes attributeMaster = surveyProjectAttribItr.next();
	                if (attributeMaster.getDatatypemasterid().getDatatype().equalsIgnoreCase("dropdown")) {
	                 attributeMaster.setOptions(customAttributeOptionsdao.getAttributeOptions(attributeMaster.getCustomattributeid()));
	                }
	            }
	        } catch (Exception ex) {
	            System.out.println("Exception ::: " + ex);
	        }
		 return resourceCustomAttributes;
	}

	@Override
	public ResourceCustomAttributes getByCustomattributeId(Integer Id) {
		// TODO Auto-generated method stub
		return resouceCustomAttributesdao.getByCustomattributeId(Id);
	}

}
