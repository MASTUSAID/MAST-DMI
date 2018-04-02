

package com.rmsi.mast.studio.service;

import java.util.List;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;

import com.rmsi.mast.studio.domain.Bookmark;
import com.rmsi.mast.studio.domain.Layergroup;
import com.rmsi.mast.studio.domain.Project;
import com.rmsi.mast.studio.domain.ProjectAdjudicator;
import com.rmsi.mast.studio.domain.ProjectArea;
import com.rmsi.mast.studio.domain.ProjectHamlet;
import com.rmsi.mast.studio.domain.ProjectRegion;
import com.rmsi.mast.studio.domain.Savedquery;
import com.rmsi.mast.studio.domain.User;
import com.rmsi.mast.studio.domain.UserProject;
import com.rmsi.mast.studio.domain.UserRole;
import com.rmsi.mast.studio.domain.fetch.ProjectData;
import com.rmsi.mast.studio.domain.fetch.ProjectDetails;
import com.rmsi.mast.studio.domain.fetch.ProjectTemp;



public interface ProjectService {


	@Transactional
	void addSaveProject(Project project,Set<Layergroup> layergroups,List<Bookmark> bookmarks,String projectName);

	@Transactional
	void addProject(Project project);

	@Transactional
	void deleteProject();

	@Transactional
	void deleteProjectById(Integer id);

	@Transactional
	void updateProject(Project project);

	Project findProjectById(Long id);
	List<Project> findAllProjects();
        List<ProjectDetails> getAllProjectsDetails();
	Project findProjectByName(String name);	
	List<Bookmark> getBookmarksByProject(String project);
	List<Savedquery> getSavedqueryByProject(String project);
	List<String> getAllProjectNames();
	List<String> getUsersByProject(String name);

	List<Project> getAllUserProjects();
	List<Project> getProjectsByOwner(String email);

	List<ProjectRegion> findAllCountry();

	List<ProjectRegion> findRegionByCountry(Integer Id);

	List<ProjectRegion> findDistrictByRegion(Integer Id);

	List<ProjectRegion> findVillageByDistrict(Integer Id);

	List<ProjectRegion> findPlaceByVillage(Integer Id);

	boolean checkprojectname(String projectName);

	boolean updateProjectArea(String projectName);



	List<String> getUserEmailByProject(String id);


	List<UserRole> findAlluserrole(List<Integer> lstRole);

	boolean checkduplicatename(String projectName);

	ProjectTemp findProjectTempByName(String defaultproject);
	@Transactional
	void addAdjudicatorDetails(ProjectAdjudicator adjObj);

	List<ProjectAdjudicator> findAdjudicatorByProject(String projname);

	@Transactional
	void deleteAdjByProject(String projectName);

	@Transactional
	void addHamlets(ProjectHamlet hamletObj);

	List<ProjectHamlet> findHamletByProject(String projname);

	@Transactional
	void deleteHamletByProject(String projectName);

	@Transactional
	void saveHamlet(ProjectHamlet hamlet_Id);

	ProjectHamlet findHamletById(long hamletId);

	long getHamletIdbyCode(String hamletcode,String projectName);

	@Transactional
	boolean deletHamletbyId(long hamlet_id);

	List<String> getHamletCodesbyProject(String projectName);

	 public List<Project> getdAllProjectsNames();

	List<Project> getdAllProjectsdetails();
	public List<ProjectArea> getProjectArea(String projectName);

	
	public List<ProjectData> getAllProjectInfo();
	
	
}
