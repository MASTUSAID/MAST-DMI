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

package com.rmsi.mast.studio.web.mvc;
import java.util.ArrayList;


import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rmsi.mast.studio.domain.Baselayer;
import com.rmsi.mast.studio.domain.Bookmark;
import com.rmsi.mast.studio.domain.LayerLayergroup;
import com.rmsi.mast.studio.domain.Layergroup;
import com.rmsi.mast.studio.domain.Project;
import com.rmsi.mast.studio.domain.ProjectAdjudicator;
import com.rmsi.mast.studio.domain.ProjectArea;
import com.rmsi.mast.studio.domain.ProjectBaselayer;
import com.rmsi.mast.studio.domain.ProjectHamlet;
import com.rmsi.mast.studio.domain.ProjectLayergroup;
import com.rmsi.mast.studio.domain.ProjectRegion;
import com.rmsi.mast.studio.domain.Savedquery;
import com.rmsi.mast.studio.domain.User;
import com.rmsi.mast.studio.domain.UserProject;
import com.rmsi.mast.studio.domain.UserRole;
import com.rmsi.mast.studio.service.BookmarkService;
import com.rmsi.mast.studio.service.OutputformatService;
import com.rmsi.mast.studio.service.ProjectService;
import com.rmsi.mast.studio.service.ProjectionService;
import com.rmsi.mast.studio.service.RoleService;
import com.rmsi.mast.studio.service.UnitService;
import com.rmsi.mast.studio.service.UserService;
import com.rmsi.mast.studio.util.SaveProject;

/**
 * @author Aparesh.Chakraborty
 * 
 */
@Controller
public class ProjectController {

	private static final Logger logger = Logger.getLogger(ProjectController.class);
	
	@Autowired
	ProjectService projectService;
	


	@Autowired
	RoleService roleService;
	
	
	@Autowired
	UserService userService;
	
	@Autowired
	private ProjectionService projectionService;

	@Autowired
	private UnitService unitService;
	@Autowired
	private OutputformatService outputformatService;
	
	@Autowired
	private BookmarkService bookmarkService;
	
	
	@RequestMapping(value = "/studio/userproject/", method = RequestMethod.GET)
	@ResponseBody
	public List<Project> getAllUserProjects() {
		return projectService.getAllUserProjects();
	}
	
	
	@RequestMapping(value = "/studio/ownerproject/", method = RequestMethod.POST)
	@ResponseBody
	public List<Project> getProjectsByOwner(HttpServletRequest request,
			HttpServletResponse response) {
		String emailid = request.getParameter("email");
		return projectService.getProjectsByOwner(emailid);
		
	}
	
	
	
	@RequestMapping(value = "/studio/project/", method = RequestMethod.GET)
	@ResponseBody
	public List<Project> list() {
		return projectService.findAllProjects();
	}
	
	
	@RequestMapping(value = "/studio/project/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Project getProjectById(@PathVariable String id) {
		return projectService.findProjectByName(id);
	}

	
	@RequestMapping(value = "/studio/project/delete/", method = RequestMethod.GET)
	@ResponseBody
	public void deleteProject() {
		projectService.deleteProject();
	}

	
	@RequestMapping(value = "/studio/project/delete/{id}", method = RequestMethod.GET)
	@ResponseBody
	public void deleteProjectById(@PathVariable String id) {
		projectService.deleteProjectById(id);
	}

	
	@RequestMapping(value = "/studio/project/create", method = RequestMethod.POST)
	@ResponseBody
	public String createProject(HttpServletRequest request,HttpServletResponse response) 
	{
	
		String projectName;
		Project project ;
		String[] project_adjudicatorhid=null;
		String[] hamlet_name=null;
		String[] hamlet_alias=null;
		String[] hamlet_code=null;
		int hamlet_length=0;
	
		try {
			projectName = ServletRequestUtils.getRequiredStringParameter(request, "name");
			
			try {
				project_adjudicatorhid = ServletRequestUtils.getRequiredStringParameters(request, "project_adjudicatorhid");
			} catch (Exception e) {
				logger.error(e);
			}
			hamlet_length=ServletRequestUtils.getRequiredIntParameter(request, "hamlets_length");
			try {
				hamlet_name=ServletRequestUtils.getRequiredStringParameters(request, "hamletName");
				hamlet_alias=ServletRequestUtils.getRequiredStringParameters(request, "hamletAlias");
				hamlet_code=ServletRequestUtils.getRequiredStringParameters(request, "hamletCode");
				
			} catch (Exception e1) {
			logger.error(e1);
			}
			
			
			
			String idseq = ServletRequestUtils.getRequiredStringParameter(request, "hid_idseq");
			if(idseq=="")
				
			{
				if(projectService.checkduplicatename(projectName))
				
				{
					
					return "DuplicateName";
				}
				
				if(projectName=="")
					
				{
					
					return "EnterName";
				}
				
			}
			project = getProjectById(projectName);
			
			if(project==null){
				
				project=new Project();				
			}
			System.out.println("-----------------"+ request.getParameter("emailid"));
			project.setName(projectName);
			/*project.setActive(Boolean.parseBoolean(ServletRequestUtils
					.getRequiredStringParameter(request, "active")));*/
			project.setActive(true);
			
			project.setActivelayer(ServletRequestUtils
					.getRequiredStringParameter(request, "activelayer"));
			
			project.setCopyright(ServletRequestUtils
					.getRequiredStringParameter(request, "copyright"));
			project.setCosmetic(Boolean.parseBoolean(ServletRequestUtils
					.getRequiredStringParameter(request, "cosmetic")));
			project.setDescription(ServletRequestUtils
					.getRequiredStringParameter(request, "description"));
			project.setDisclaimer(ServletRequestUtils
					.getRequiredStringParameter(request, "disclaimer"));

			// project.setWidth(Integer.parseInt(ServletRequestUtils.getRequiredStringParameter(request,"width")));
			// project.setHeight(Integer.parseInt(ServletRequestUtils.getRequiredStringParameter(request,"height")));
			project.setMinextent(ServletRequestUtils
					.getRequiredStringParameter(request, "minextent"));
			project.setMaxextent(ServletRequestUtils
					.getRequiredStringParameter(request, "maxextent"));
			// project.setMaxresolutions(Integer.parseInt(ServletRequestUtils.getRequiredStringParameter(request,"maxresolutions")));
			// project.setMinresolutions(Integer.parseInt(ServletRequestUtils.getRequiredStringParameter(request,"minresolutions")));
			project.setNumzoomlevels(Integer.parseInt(ServletRequestUtils
					.getRequiredStringParameter(request, "numzoomlevels")));
			

			project.setOverlaymap(ServletRequestUtils
					.getRequiredStringParameter(request, "overlaymap"));
			
			
			project.setRestrictedextent(ServletRequestUtils
					.getRequiredStringParameter(request, "restrictedextent"));
			// project.setThumbnail(ServletRequestUtils.getRequiredStringParameter(request,"thumbnail"));
			project.setWatermask(ServletRequestUtils
					.getRequiredStringParameter(request, "watermask"));

			project.setUnit(unitService.findUnitByName(ServletRequestUtils
					.getRequiredStringParameter(request, "unit.name")));
			
			
			/*project.setActivelayer(ActivelayerService.findActivelayerByName(ServletRequestUtils
					.getRequiredStringParameter(request, "activelayer.name")));*/
		/*	
			project.setUnit(unitService.findUnitByName(ServletRequestUtils
					.getRequiredStringParameter(request, "overlaymap.name")));*/
			
			
			project.setProjection(projectionService.findProjectionByName(ServletRequestUtils
						.getRequiredStringParameter(request,"projection.code")));
			
			project.setDisplayProjection(projectionService.findProjectionByName(ServletRequestUtils
							.getRequiredStringParameter(request,"displayProjection.code")));
			
			project.setOutputformat(outputformatService.findOutputformatByName(ServletRequestUtils
							.getRequiredStringParameter(request,"outputFormat.name")));
			
			//by Aparesh/
			project.setAdmincreated(true);
			project.setOwner(request.getParameter("emailid"));
			project.setCopyright("custom");
			project.setWatermask("custom");

			String layerGroup[] = request
					.getParameterValues("selectedLayergroups");

		
			String users[]=request.getParameterValues("project_user");
			
			String baselayers[]=null;
			
			try{
				baselayers = request.getParameterValues("selectedBaselayer");
				}catch(Exception e){
					logger.error(e);
				}
			
			
			Set<UserProject> userProjectList = new HashSet<UserProject> ();			
			Set<ProjectBaselayer> projectBaselayerList = new HashSet<ProjectBaselayer> ();
			List<ProjectArea> projectAreaList = new ArrayList<ProjectArea> ();	
			
			ProjectArea projectArea = new ProjectArea();	
			//SET PRoject Area
			
			String countryname="";
			String region="";
			String districtname="";
			String village="";
			String hamlet="";
			String name="";
			String id="";
			
			//add for save 
			String districtOfficer="";
			String villageChairman="";
			String approvingExecutive="";
			
		//districtOfficer,villageChairman,approvingExecutive
			
			try {
				try {
					countryname = ServletRequestUtils.getRequiredStringParameter(request, "countryId");
				} catch (Exception e) {
					
					logger.error(e);
				}
				try {
					region = ServletRequestUtils.getRequiredStringParameter(request, "regionId");
				} catch (Exception e) {
					
					logger.error(e);
				}
				try {
					districtname = ServletRequestUtils.getRequiredStringParameter(request, "districtId");
				} catch (Exception e) {
					
					logger.error(e);
				}
				try {
					village = ServletRequestUtils.getRequiredStringParameter(request, "villageId");
				} catch (Exception e) {
				
					logger.error(e);
				}
				try {
					hamlet = ServletRequestUtils.getRequiredStringParameter(request, "hamletId");
				} catch (Exception e) {
					
					logger.error(e);
				}
				try {
					name= ServletRequestUtils.getRequiredStringParameter(request, "name");
				} catch (Exception e) {
					
					logger.error(e);
				}
				try {
					id= ServletRequestUtils.getRequiredStringParameter(request, "hid_id");
				} catch (Exception e) {
						
					logger.error(e);
				}
				
				//add for save VillageChairman By RM
				
				try {
					villageChairman= ServletRequestUtils.getRequiredStringParameter(request, "villagechairman");
				} catch (Exception e) {
						
					logger.error(e);
				}
				
				try {
					approvingExecutive= ServletRequestUtils.getRequiredStringParameter(request, "executiveofficer");
				} catch (Exception e) {
						
					logger.error(e);
				}
				
				try {
					districtOfficer= ServletRequestUtils.getRequiredStringParameter(request, "districtofficer");
				} catch (Exception e) {
						
					logger.error(e);
				}
				
				
				
				//
			        
				if(id!="")
				{
					projectArea.setAreaId(Long.parseLong(id));
					
				}
					
				projectArea.setCountryName(countryname);
				projectArea.setRegion(region);
				projectArea.setDistrictName(districtname);
				projectArea.setVillage(village);

				/*projectArea.setHamlet(hamlet);*/

				projectArea.setInitiationDate(new Date());
				projectArea.setProjectName(projectName);
				
				projectArea.setVillageChairman(villageChairman);
				projectArea.setApprovingExecutive(approvingExecutive);
				projectArea.setDistrictOfficer(districtOfficer);
				
				projectAreaList.add(projectArea);
				
			} catch (Exception e) {
				
				logger.error(e);
			}
	            
			//SET user
			for(int i = 0; i < users.length; i++)
			{
	            UserProject userProject=new UserProject();
	            User obuser=userService.findUserByUserId(Integer.parseInt(users[i]));
	            userProject.setUser(obuser);
	            userProject.setProjectBean(project);
	            userProjectList.add(userProject);  
	        }
	
			
			//SET Baselayer
			if(baselayers!=null){
				for(int j = 0; j < baselayers.length; j++){
					ProjectBaselayer projectBaselayer=new ProjectBaselayer();
		            Baselayer baselayer=new Baselayer();
		            baselayer.setName(baselayers[j]);
		            
		            projectBaselayer.setBaselayerBean(baselayer);
		            projectBaselayer.setProjectBean(project);
		            projectBaselayer.setBaselayerorder(j+1);
		            
					System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+ baselayer.getName());
					projectBaselayerList.add(projectBaselayer);	           
		        }
			}
			
			Set<ProjectLayergroup> plgList = new HashSet<ProjectLayergroup>();
			//Set<ProjectLayergroup> plgList = project.getProjectLayergroups();
			
			for (int i = 0; i < layerGroup.length; i++) {
				ProjectLayergroup plg = new ProjectLayergroup();
				Layergroup lg = new Layergroup();
				//Project proj = new Project();

				lg.setName(layerGroup[i]);
				//proj.setName(projName);

				plg.setLayergroupBean(lg);
				plg.setProjectBean(project);
				plg.setGrouporder(i + 1);
				plgList.add(plg);
			}
			
			// add for project configuration
			
		/*	if(baselayers!=null){
				for(int j = 0; j < baselayers.length; j++){
					ProjectBaselayer projectBaselayer=new ProjectBaselayer();
		            Baselayer baselayer=new Baselayer();
		            baselayer.setName(baselayers[j]);
		            
		            projectBaselayer.setBaselayerBean(baselayer);
		            projectBaselayer.setProjectBean(project);
		            projectBaselayer.setBaselayerorder(j+1);
		            
					System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+ baselayer.getName());
					projectBaselayerList.add(projectBaselayer);	           
		        }
			}
		*/

			project.setProjectLayergroups(plgList);
			project.setUserProjects(userProjectList);
			project.setProjectBaselayers(projectBaselayerList);
			project.setProjectAreas(projectAreaList);
					
			projectService.addProject(project);
			
			projectService.deleteAdjByProject(projectName);
			
			
			ProjectAdjudicator adjObj=new ProjectAdjudicator();
			
				for(String str:project_adjudicatorhid){  
				adjObj.setAdjudicatorName(str);
				adjObj.setProjectName(projectName);
				projectService.addAdjudicatorDetails(adjObj);
			}
			projectService.deleteHamletByProject(projectName);
			
				ProjectHamlet hamletObj=new ProjectHamlet();
				
				for (int j = 0; j < hamlet_length; j++) {
					hamletObj.setHamletName(hamlet_name[j]);
					hamletObj.setHamletNameSecondLanguage(hamlet_alias[j]);
					hamletObj.setHamletCode(hamlet_code[j]);
					hamletObj.setProjectName(projectName);
					projectService.addHamlets(hamletObj);
					
				}
					
				
			
			return "ProjectAdded";
		} catch (Exception e) 
		{			
			logger.error(e);
			return "false";
		}
		

	}

	
	@RequestMapping(value = "/studio/project/{id}/bookmark/", method = RequestMethod.GET)
	@ResponseBody
	public List<Bookmark> getBookmarksByProject(@PathVariable String id) {

		return projectService.getBookmarksByProject(id);
	}

	
	@RequestMapping(value = "/studio/project/{id}/savedquery/", method = RequestMethod.GET)
	@ResponseBody
	public List<Savedquery> getSavedqueryByProject(@PathVariable String id) {

		return projectService.getSavedqueryByProject(id);
	}
	
	
	@RequestMapping(value = "/studio/project/names", method = RequestMethod.GET)
	@ResponseBody
	public List<String> getAllProjectNames(){
		List<String> results = projectService.getAllProjectNames();
		
		return results;
	}
	
	
	@RequestMapping(value = "/studio/project/{id}/user", method = RequestMethod.GET)
	@ResponseBody
	public List<String> getUsersByProject(@PathVariable String id){
		
		//List<String> results = projectService.getUsersByProject(id);		
		List<String> results = projectService.getUserEmailByProject(id);
		return results;
	}
	
	@RequestMapping(value = "/studio/project/save", method = RequestMethod.POST)
	@ResponseBody
	//public boolean saveProject(HttpServletRequest request){
	public boolean saveProject(@RequestBody SaveProject saveProject){
		

		
		String[][] layerVis = saveProject.getLayerVisibility();
		
		String[] users = saveProject.getUsers();
		
		System.out.println("------------USERS----------");
		for(int i=0;i<users.length;i++){
			System.out.println(users[i]);
		}
		System.out.println("----------------------");
		
		Project project = getProjectById(saveProject.getActualProjectName());
		try{
			Project newProject = (Project)project.clone();
			newProject.setMinextent(saveProject.getExtent());
			newProject.setName(saveProject.getNewProjectName());
			newProject.setDescription(saveProject.getNewProjectDescription());
			newProject.setAdmincreated(false);
			newProject.setOwner(saveProject.getOwner());
			
			//Get the layergroup records from projectlayergroups and create new instance of layergroup
			Set<ProjectLayergroup> projectLayergroups = newProject.getProjectLayergroups();
			Iterator<ProjectLayergroup> itr = projectLayergroups.iterator();
			Set<Layergroup> layerGroups = new HashSet<Layergroup>();
			Set<ProjectLayergroup> projLayerGroups = new HashSet<ProjectLayergroup>();
			
			for(;itr.hasNext();){
				 ProjectLayergroup projectLayerGroup = itr.next();
				 Layergroup lyrGroupBean = projectLayerGroup.getLayergroupBean();
				 Layergroup lyrGroup = new Layergroup();
				 lyrGroup.setName(newProject.getName()+"_" + lyrGroupBean.getName());
				 lyrGroup.setAlias(newProject.getName()+"_" +lyrGroupBean.getAlias());
				 lyrGroup.setTenantid(lyrGroupBean.getTenantid());
				 
				 //Add layer_layergroup collection
				  Set<LayerLayergroup> lyrLayerGroups = lyrGroupBean.getLayerLayergroups();
				  Iterator<LayerLayergroup> itrLyrGrp = lyrLayerGroups.iterator();
				  HashSet<LayerLayergroup> setLyrLayerGroups = new HashSet<LayerLayergroup>();
				  for(;itrLyrGrp.hasNext();){
					  LayerLayergroup lyrLayerGroup = itrLyrGrp.next();
					  LayerLayergroup newLayerLayerGroup = new LayerLayergroup();
					  newLayerLayerGroup.setLayer(lyrLayerGroup.getLayer());
					  newLayerLayerGroup.setLayerorder(lyrLayerGroup.getLayerorder());
					  newLayerLayerGroup.setTenantid(lyrLayerGroup.getTenantid());
					  newLayerLayerGroup.setLayervisibility(getLayerVisibilityState(lyrLayerGroup.getLayer(), layerVis));
					  newLayerLayerGroup.setLayergroupBean(lyrGroup);
					  setLyrLayerGroups.add(newLayerLayerGroup);
				  }
				  lyrGroup.setLayerLayergroups(setLyrLayerGroups);
				  
				  ProjectLayergroup newProjLayerGroup = new ProjectLayergroup();
				  newProjLayerGroup.setGrouporder(projectLayerGroup.getGrouporder());
				  newProjLayerGroup.setLayergroupBean(lyrGroup);
				  newProjLayerGroup.setTenantid(projectLayerGroup.getTenantid());
				  newProjLayerGroup.setProjectBean(newProject);
				  projLayerGroups.add(newProjLayerGroup);
				  
				  lyrGroup.setProjectLayergroups(projLayerGroups);
				  layerGroups.add(lyrGroup);
			}
			newProject.setProjectLayergroups(projLayerGroups);
			
			/**************	set project's base layer **********************/
			
			Set<ProjectBaselayer> projectBaselayerList = newProject.getProjectBaselayers();
			Iterator<ProjectBaselayer> baselyritr = projectBaselayerList.iterator();
			HashSet<ProjectBaselayer> newProjectBaselayerList = new HashSet<ProjectBaselayer>();
			Project baseLyrProj=new Project();
			baseLyrProj.setName(newProject.getName());
			
			for(;baselyritr.hasNext();){
				
				ProjectBaselayer newProjectBaselayer = baselyritr.next();
				
				newProjectBaselayer.setProjectBean(newProject);
				newProjectBaselayer.setId(null);
				
				newProjectBaselayerList.add(newProjectBaselayer);
			}			 						
			
			newProject.setProjectBaselayers(newProjectBaselayerList);

			/**************	set Associated users **********************/
			
			Set<UserProject> userProjecs = new HashSet<UserProject> ();
			for(int i = 0; i < users.length; i++){
	            UserProject userProject=new UserProject();
	            User usr=new User();
	            //usr.setName(users[i]);
	            usr.setEmail(users[i]);
	            userProject.setUser(usr);
	            userProject.setProjectBean(newProject);
	            
	            userProjecs.add(userProject);	           
	        }
			newProject.setUserProjects(userProjecs);
			
			
			
			//Verify by Iterating layer groups
			Iterator<Layergroup> itrLg = layerGroups.iterator();
			Layergroup lg = null;
			for(;itrLg.hasNext();){
				lg = itrLg.next();
				
				Set<LayerLayergroup> setLLg = lg.getLayerLayergroups();
				Iterator<LayerLayergroup> itrLLg = setLLg.iterator();
				System.out.println("----------Printing Associated LayerLayergroup-------------");
				for(; itrLLg.hasNext();){
					LayerLayergroup _llg = itrLLg.next();
					
				}
			}
			
			
			Set<ProjectLayergroup> setPLg = lg.getProjectLayergroups();
			Iterator<ProjectLayergroup> itrPlg = setPLg.iterator();
			for(;itrPlg.hasNext();){
				ProjectLayergroup plg = itrPlg.next();
				
			}
			
			
			Set<ProjectBaselayer> setBlyr = newProject.getProjectBaselayers();
			Iterator<ProjectBaselayer> itrBlyr = setBlyr.iterator();
			for(;itrBlyr.hasNext();){
				ProjectBaselayer blyr = itrBlyr.next();
				
			}
			
			
			Set<UserProject> setusrproj = newProject.getUserProjects();
			Iterator<UserProject> itrusrprojr = setusrproj.iterator();
			for(;itrusrprojr.hasNext();){
				UserProject up = itrusrprojr.next();
							
			}
			
			
			//get project's bookmark
			List<Bookmark> bookmarks=bookmarkService.getBookmarksByProject(saveProject.getActualProjectName());
			
			
			
			projectService.addSaveProject(newProject,layerGroups,bookmarks,saveProject.getActualProjectName());
			
		}catch(CloneNotSupportedException e){
			logger.error(e);
		}
		return true;
	}
	
	private boolean getLayerVisibilityState(String layer, String[][] lyrVisibility){
		boolean bVisState = false;
		for(int i=0; i<lyrVisibility.length; i++){
			if(layer.equals(lyrVisibility[i][0])){
				bVisState = Boolean.parseBoolean(lyrVisibility[i][1]);
				break;
			}
		}
		return bVisState;
	}
	
	
	/* ************@RMSI/NK add for country,region, district,village,hamlet * start ***1-5 ***********/
	
	@RequestMapping(value = "/studio/projectcontry/", method = RequestMethod.GET)
	@ResponseBody
	public List<ProjectRegion> getList() {
		return projectService.findAllCountry();
	}
	
	
	
	@RequestMapping(value = "/studio/projectregion/{countryname}", method = RequestMethod.GET)
	@ResponseBody
	public List<ProjectRegion> getList(@PathVariable String countryname) {
		return projectService.findRegionByCountry(countryname);
	}
	
	@RequestMapping(value = "/studio/projectdistrict/{countryname}", method = RequestMethod.GET)
	@ResponseBody
	public List<ProjectRegion> getListRegion(@PathVariable String countryname) {
		return projectService.findDistrictByRegion(countryname);
	}
	
	
	
	@RequestMapping(value = "/studio/projectvillage/{countryname}", method = RequestMethod.GET)
	@ResponseBody
	public List<ProjectRegion> getListVillage(@PathVariable String countryname) {
		return projectService.findVillageByDistrict(countryname);
	}
	
	
	
	@RequestMapping(value = "/studio/projecthamlet/{countryname}", method = RequestMethod.GET)
	@ResponseBody
	public List<ProjectRegion> getListHamlet(@PathVariable String countryname) {
		return projectService.findHamletByVillage(countryname);
	}
	
	@RequestMapping(value = "studio/project/userbyrole/", method = RequestMethod.GET)
	@ResponseBody
	public List<User> userrolelist()
	{
		List<User> userrolelst = new ArrayList<User>();
		ArrayList<Integer> userid= new ArrayList<Integer>();
	    
		List<String> lstRole= new ArrayList<String>();
	    lstRole.add("ROLE_TRUSTED_INTERMEDIARY");
	    lstRole.add("ROLE_PM");
	    lstRole.add("ROLE_LAO");
	    lstRole.add("ROLE_ADJUDICATOR");
		
		 List<UserRole> userroleid = projectService.findAlluserrole(lstRole);
		
		 for (int i = 0; i < userroleid.size(); i++) {

			 Integer id=userroleid.get(i).getUser().getId();
			 
			 userid.add(id);
		 }
			 try {
				
				 userrolelst=userService.findUserById(userid);
				
				 return userrolelst;
			} catch (Exception e) {
				logger.error(e);
			}
		 return userrolelst;
			
		
		
		
				
	}
	
	@RequestMapping(value = "/studio/adjudicators/{projname}", method = RequestMethod.GET)
	@ResponseBody
	public List<ProjectAdjudicator> getListAdjudicators(@PathVariable String projname) {
		return projectService.findAdjudicatorByProject(projname);
	}
	
	@RequestMapping(value = "/studio/hamlet/{projname}", method = RequestMethod.GET)
	@ResponseBody
	public List<ProjectHamlet> getListHamlets(@PathVariable String projname) {
		return projectService.findHamletByProject(projname);
	}
	
	/* ************@RMSI/NK add for country,region, district,village,hamlet * start ***1-5 ***********/
	
}
