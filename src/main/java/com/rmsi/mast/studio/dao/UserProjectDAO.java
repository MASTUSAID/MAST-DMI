

package com.rmsi.mast.studio.dao;




import java.util.List;
import java.util.Set;







import com.rmsi.mast.studio.domain.ProjectLayergroup;
import com.rmsi.mast.studio.domain.UserProject;
import com.rmsi.mast.studio.domain.UserRole;

public interface UserProjectDAO extends GenericDAO<UserProject, Long> {
	
	
	void addUserProject(Set<UserProject> userProject,String projectname);
	
	List<String> getUsersByProject(String name);
	List<String> getUserNameByProject(String id);
	void deleteUserProjectByProjectId(Integer id);
	void deleteUserProjectByUser(Integer id);

	boolean checkUserProject(Integer val, String defProjName);

	List<UserProject> findUserProjectsById(Long id);
	List<UserProject> findAllUserProjectByUserID(Long id);
}
