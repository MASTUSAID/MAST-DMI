/* ----------------------------------------------------------------------
 * Copyright (c) 2011 by RMSI.
 * All Rights Reserved
 *
 * Permission to use this program and its related files is at the
 * discretion of RMSI Pvt Ltd.
 *
 * The licensee of RMSI Software agrees that:
 *    - Redistribution in whole or in part is not permitted.
 *    - Modification of source is not permitted.
 *    - Use of the source in whole or in part outside of RMSI is not
 *      permitted.
 *
 * THIS SOFTWARE IS PROVIDED ''AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL RMSI OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 * ----------------------------------------------------------------------
 */

package com.rmsi.mast.studio.service;

import java.util.List;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;

import com.rmsi.mast.studio.domain.Bookmark;
import com.rmsi.mast.studio.domain.Layergroup;
import com.rmsi.mast.studio.domain.Project;
import com.rmsi.mast.studio.domain.ProjectAdjudicator;
import com.rmsi.mast.studio.domain.ProjectHamlet;
import com.rmsi.mast.studio.domain.ProjectRegion;
import com.rmsi.mast.studio.domain.Savedquery;
import com.rmsi.mast.studio.domain.User;
import com.rmsi.mast.studio.domain.UserProject;
import com.rmsi.mast.studio.domain.UserRole;
import com.rmsi.mast.studio.domain.fetch.ProjectTemp;



public interface ProjectService {
	
	
	@Transactional
	void addSaveProject(Project project,Set<Layergroup> layergroups,List<Bookmark> bookmarks,String projectName);
	
	@Transactional
	void addProject(Project project);
	
	@Transactional
	void deleteProject();
	
	@Transactional
	void deleteProjectById(String name);
	
	@Transactional
	void updateProject(Project project);
	
	Project findProjectById(Long id);
	List<Project> findAllProjects();	
	Project findProjectByName(String name);	
	List<Bookmark> getBookmarksByProject(String project);
	List<Savedquery> getSavedqueryByProject(String project);
	List<String> getAllProjectNames();
	List<String> getUsersByProject(String name);
	
	List<Project> getAllUserProjects();
	List<Project> getProjectsByOwner(String email);
	
	List<ProjectRegion> findAllCountry();
	
	List<ProjectRegion> findRegionByCountry(String countryname);
	
	List<ProjectRegion> findDistrictByRegion(String countryname);
	
	List<ProjectRegion> findVillageByDistrict(String countryname);
	
	List<ProjectRegion> findHamletByVillage(String countryname);

	boolean checkprojectname(String projectName);

	boolean updateProjectArea(String projectName);



	List<String> getUserEmailByProject(String id);


	List<UserRole> findAlluserrole(List<String> lstRole);

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

}
