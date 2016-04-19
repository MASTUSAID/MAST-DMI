

package com.rmsi.mast.studio.dao;




import java.util.List;
import java.util.Set;





import com.rmsi.mast.studio.domain.ProjectBaselayer;
import com.rmsi.mast.studio.domain.ProjectLayergroup;
import com.rmsi.mast.studio.domain.UserProject;
import com.rmsi.mast.studio.domain.UserRole;

public interface ProjectBaselayerDAO extends GenericDAO<ProjectBaselayer, Long> {
	
	void deleteProjectBaselayer(String name);
	
	void addProjectBaselayer(Set<ProjectBaselayer> projectBaselayer,String projectname);

}
