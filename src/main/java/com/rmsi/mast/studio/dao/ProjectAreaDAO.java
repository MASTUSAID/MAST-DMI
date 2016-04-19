

package com.rmsi.mast.studio.dao;




import java.util.List;

import com.rmsi.mast.studio.domain.Project;
import com.rmsi.mast.studio.domain.ProjectArea;

public interface  ProjectAreaDAO extends GenericDAO<ProjectArea, Long> {

	boolean checkProjectName(String  projectName);

	boolean updateProjectArea(String projectName);

	void deleteByProjectAreaName(String name);

	List<ProjectArea> findByProjectName(String projectName);

}