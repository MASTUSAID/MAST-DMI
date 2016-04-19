

package com.rmsi.mast.studio.dao;

import java.util.List;

import com.rmsi.mast.studio.domain.AttributeCategory;
import com.rmsi.mast.studio.domain.AttributeMaster;
import com.rmsi.mast.studio.domain.Project;
import com.rmsi.mast.studio.domain.Surveyprojectattribute;

public interface ProjectAttributeDAO extends GenericDAO<Surveyprojectattribute, Long> {


	List<Surveyprojectattribute> selectedCategory(Long uid,String name);

	List<Surveyprojectattribute> selectedAttributes(Long uid);

	List<Surveyprojectattribute> displayselectedlist(Long uid, String project);

	boolean deleteEntries(int attributeCategory,String project);

	boolean checkdeleteAttribute(Long id);

	void deleteByProjectName(String name);

	

	


}
