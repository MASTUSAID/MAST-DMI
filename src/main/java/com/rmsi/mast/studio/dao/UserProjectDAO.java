

package com.rmsi.mast.studio.dao;




import java.util.List;
import java.util.Set;







import com.rmsi.mast.studio.domain.ProjectLayergroup;
import com.rmsi.mast.studio.domain.UserProject;
import com.rmsi.mast.studio.domain.UserRole;

public interface UserProjectDAO extends GenericDAO<UserProject, Long> {
	
	//List<UserProject> findAllUserProject(String name);
	
	//void deleteUserProject(String name);
	
	void addUserProject(Set<UserProject> userProject,String projectname);
	
	List<String> getUsersByProject(String name);
	List<String> getUserNameByProject(String id);
	void deleteUserProjectByProject(String project);
	void deleteUserProjectByUser(Integer id);

	boolean checkUserProject(Integer val, String defProjName);

	List<UserProject> findUserProjectsById(Integer id);
}
