

package com.rmsi.mast.studio.dao;




import java.util.List;





import com.rmsi.mast.studio.domain.ProjectLayergroup;

public interface ProjectLayergroupDAO extends GenericDAO<ProjectLayergroup, Long> {
	
	List<ProjectLayergroup> findAllProjectLayergroup(String name);
	
	void deleteProjectLayergroupByProjectName(String name);
	
	List<String> getLayersByProjectName(String project);
	
	void deleteProjectLayergroupByLG(String name);
	
	List<String> getProjectsByLayergroup(String layergroup);
	
}
