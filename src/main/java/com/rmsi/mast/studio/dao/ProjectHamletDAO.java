

package com.rmsi.mast.studio.dao;

import java.util.List;

import com.rmsi.mast.studio.domain.ProjectAdjudicator;
import com.rmsi.mast.studio.domain.ProjectHamlet;

public interface ProjectHamletDAO extends GenericDAO<ProjectHamlet, Long> {

	List<ProjectHamlet> findHamlets(String projname);

	void deleteEntries(String projectName);

	long getHamletIdbyCode(String hamletcode,String projectName);

	List<String> getHamletCodesbyProject(String projectName);
	
	
}
