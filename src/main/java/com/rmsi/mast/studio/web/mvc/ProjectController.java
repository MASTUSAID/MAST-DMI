package com.rmsi.mast.studio.web.mvc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.rmsi.mast.studio.domain.Baselayer;
import com.rmsi.mast.studio.domain.Bookmark;
import com.rmsi.mast.studio.domain.LaSpatialunitgroup;
import com.rmsi.mast.studio.domain.Layergroup;
import com.rmsi.mast.studio.domain.Project;
import com.rmsi.mast.studio.domain.ProjectAdjudicator;
import com.rmsi.mast.studio.domain.ProjectArea;
import com.rmsi.mast.studio.domain.ProjectBaselayer;
import com.rmsi.mast.studio.domain.ProjectHamlet;
import com.rmsi.mast.studio.domain.ProjectLayergroup;
import com.rmsi.mast.studio.domain.ProjectRegion;
import com.rmsi.mast.studio.domain.Projection;
import com.rmsi.mast.studio.domain.Role;
import com.rmsi.mast.studio.domain.Savedquery;
import com.rmsi.mast.studio.domain.User;
import com.rmsi.mast.studio.domain.UserProject;
import com.rmsi.mast.studio.domain.UserRole;
import com.rmsi.mast.studio.domain.fetch.ProjectData;
import com.rmsi.mast.studio.service.BaselayerService;
import com.rmsi.mast.studio.service.BookmarkService;
import com.rmsi.mast.studio.service.LaSpatialunitgroupService;
import com.rmsi.mast.studio.service.LayerGroupService;
import com.rmsi.mast.studio.service.OutputformatService;
import com.rmsi.mast.studio.service.ProjectAreaService;
import com.rmsi.mast.studio.service.ProjectRegionService;
import com.rmsi.mast.studio.service.ProjectService;
import com.rmsi.mast.studio.service.ProjectionService;
import com.rmsi.mast.studio.service.RoleService;
import com.rmsi.mast.studio.service.UnitService;
import com.rmsi.mast.studio.service.UserProjectService;
import com.rmsi.mast.studio.service.UserService;
import com.rmsi.mast.studio.util.FileUtils;
import com.rmsi.mast.studio.util.StringUtils;
import com.rmsi.mast.viewer.service.LandRecordsService;

@Controller
public class ProjectController {

    private static final Logger logger = Logger.getLogger(ProjectController.class);

    @Autowired
    ProjectService projectService;

    @Autowired
    LandRecordsService landRecordsService;

    @Autowired
    RoleService roleService;

    @Autowired
    UserService userService;

    
    @Autowired 
    BaselayerService baselayerService;
    
    @Autowired
    private ProjectionService projectionService;

    @Autowired
    private UnitService unitService;
    
    @Autowired
    private OutputformatService outputformatService;

    @Autowired
    private BookmarkService bookmarkService;
    
    
    @Autowired
    private LayerGroupService layerGroupService;
    
    
    @Autowired
    private ProjectAreaService projectAreaService;
    
    
    @Autowired
    private ProjectRegionService  projectRegionService;
    
    
    @Autowired
    private ProjectionService ProjectionService;
    
    @Autowired
    LaSpatialunitgroupService LaSpatialunitgroupService;
    
    
    @Autowired
    UserProjectService userProjectService;

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
        projectService.deleteProjectById(Integer.parseInt(id));
    }
    
    
    @RequestMapping(value = "/studio/defaultproject", method = RequestMethod.GET)
    @ResponseBody
    public void gatAllProjectName() {
        projectService.getAllProjectNames();
    }
    
    
   
    @RequestMapping(value = "/studio/project/create", method = RequestMethod.POST)
    @ResponseBody
    public String createProject(HttpServletRequest request, HttpServletResponse response,Principal principal) throws ServletRequestBindingException {

        String projectName;
        Project project;
        String extention;
        
        String username = principal.getName();
		User userObj = userService.findByUniqueName(username);
		
		Long user_id = userObj.getId();
		extention = ServletRequestUtils.getRequiredStringParameter(request, "extention");
		String ar[] = extention.split("/");
       
        try {
            projectName = ServletRequestUtils.getRequiredStringParameter(request, "name");
           
            String idseq = ServletRequestUtils.getRequiredStringParameter(request, "hid_idseq");
            if ("".equals(idseq)) {
                if (projectService.checkduplicatename(projectName)) {
                    return "DuplicateName";
                }

                if ("".equals(projectName)) {
                    return "EnterName";
                }

            }
            project = getProjectById(projectName);

            if (project == null) {
                project = new Project();
            }

            project.setName(projectName);
            project.setActive(true);
            //project.setActivelayer(ServletRequestUtils.getRequiredStringParameter(request, "activelayer"));
            project.setDescription(ServletRequestUtils.getRequiredStringParameter(request, "description"));
            project.setDisclaimer(ServletRequestUtils.getRequiredStringParameter(request, "disclaimer"));
            project.setMinextent(ServletRequestUtils.getRequiredStringParameter(request, "minextent"));
            project.setMaxextent(ServletRequestUtils.getRequiredStringParameter(request, "maxextent"));
            project.setZoomlevelextent(Integer.parseInt(ServletRequestUtils.getRequiredStringParameter(request, "numzoomlevels")));
            //project.setOverlaymap(ServletRequestUtils.getRequiredStringParameter(request, "overlaymap"));
            //project.setRestrictedextent(ServletRequestUtils.getRequiredStringParameter(request, "restrictedextent"));
            project.setUnit(unitService.findUnitById(Integer.parseInt(ServletRequestUtils.getRequiredStringParameter(request, "project_unit"))));
         //  project.setDisplayProjection(projectionService.findProjectionByName(ServletRequestUtils.getRequiredStringParameter(request, "displayProjection.code")));
            project.setProjection(projectionService.findProjectionById(Integer.parseInt(ServletRequestUtils.getRequiredStringParameter(request, "projection_code"))));
            project.setOutputformat(outputformatService.findOutputformatById(Integer.parseInt(ServletRequestUtils.getRequiredStringParameter(request, "project_outputFormat"))));
            project.setCreatedby(1);
            project.setCreateddate(new Date());
            
            String layerGroup[] = request.getParameterValues("selectedLayergroups");
            String users[] = request.getParameterValues("project_user");
            String baselayers[] = null;

            try {
                baselayers = request.getParameterValues("selectedBaselayer");
            } catch (Exception e) {
                logger.error(e);
            }

            Set<UserProject> userProjectList = new HashSet<UserProject>();
            Set<ProjectBaselayer> projectBaselayerList = new HashSet<ProjectBaselayer>();
            Set<ProjectArea> projectAreaset = new HashSet<ProjectArea>();

            ProjectArea projectArea = new ProjectArea();
            String countryId = "";
            String regionId = "";
            String districtId = "";
            String communeid = "";
            String placeId="";
            String id = "";
            String districtOfficer = "";
            String villageChairman = "";
            String approvingExecutive = "";
            String certificationNumber = "";
            String postalcode = "";
            String vcmeetingdate = "";

            try {
                try {
                	countryId = ServletRequestUtils.getRequiredStringParameter(request, "countryId");
                	if(countryId!="")
                	{
                		LaSpatialunitgroup objLaSpatialunitgroupService=LaSpatialunitgroupService.findLaSpatialunitgroupById(1);
                		ProjectRegion objProjectRegion =projectRegionService.findProjectRegionById(Integer.parseInt(countryId));
                		projectArea.setLaSpatialunitgroup1(objLaSpatialunitgroupService);
                		projectArea.setLaSpatialunitgroupHierarchy1(objProjectRegion);
                		
                	}
                } catch (Exception e) {
                    logger.error(e);
                }
                try {
                	regionId = ServletRequestUtils.getRequiredStringParameter(request, "regionId");
                	if(regionId!=""){
                		
                		LaSpatialunitgroup objLaSpatialunitgroupService=LaSpatialunitgroupService.findLaSpatialunitgroupById(2);
                		ProjectRegion objProjectRegion =projectRegionService.findProjectRegionById(Integer.parseInt(regionId));
                		projectArea.setLaSpatialunitgroup2(objLaSpatialunitgroupService);
                		projectArea.setLaSpatialunitgroupHierarchy2(objProjectRegion);
                	}
                	
                } catch (Exception e) {
                    logger.error(e);
                }
                try {
                	districtId = ServletRequestUtils.getRequiredStringParameter(request, "districtId");
                	if(districtId!=""){
                		
                		LaSpatialunitgroup objLaSpatialunitgroupService=LaSpatialunitgroupService.findLaSpatialunitgroupById(3);
                		ProjectRegion objProjectRegion =projectRegionService.findProjectRegionById(Integer.parseInt(districtId));
                		projectArea.setLaSpatialunitgroup3(objLaSpatialunitgroupService);
                		projectArea.setLaSpatialunitgroupHierarchy3(objProjectRegion);
                	}
                } catch (Exception e) {
                    logger.error(e);
                }
                try {
                	communeid = ServletRequestUtils.getRequiredStringParameter(request, "CommuneId");
                	if(communeid!=""){
                		
                		LaSpatialunitgroup objLaSpatialunitgroupService=LaSpatialunitgroupService.findLaSpatialunitgroupById(4);
                		ProjectRegion objProjectRegion =projectRegionService.findProjectRegionById(Integer.parseInt(communeid));
                		projectArea.setLaSpatialunitgroup4(objLaSpatialunitgroupService);
                		projectArea.setLaSpatialunitgroupHierarchy4(objProjectRegion);
                	}
                } catch (Exception e) {
                    logger.error(e);
                }
                try {
                	placeId = ServletRequestUtils.getRequiredStringParameter(request, "placeId");
                	if(placeId!=""){
                		
                		LaSpatialunitgroup objLaSpatialunitgroupService=LaSpatialunitgroupService.findLaSpatialunitgroupById(5);
                		ProjectRegion objProjectRegion =projectRegionService.findProjectRegionById(Integer.parseInt(placeId));
                		projectArea.setLaSpatialunitgroup5(objLaSpatialunitgroupService);
                		projectArea.setLaSpatialunitgroupHierarchy5(objProjectRegion);
                	}
                } catch (Exception e) {
                    logger.error(e);
                }
                try {
                    id = ServletRequestUtils.getRequiredStringParameter(request, "hid_id");
                } catch (Exception e) {
                    logger.error(e);
                }
                try {
                    villageChairman = ServletRequestUtils.getRequiredStringParameter(request, "villagechairman");
                } catch (Exception e) {
                    logger.error(e);
                }
                try {
                    approvingExecutive = ServletRequestUtils.getRequiredStringParameter(request, "executiveofficer");
                } catch (Exception e) {
                    logger.error(e);
                }
                try {
                    districtOfficer = ServletRequestUtils.getRequiredStringParameter(request, "districtofficer");
                } catch (Exception e) {
                    logger.error(e);
                }
                try {
                	certificationNumber = ServletRequestUtils.getRequiredStringParameter(request, "villagecode");
                } catch (Exception e) {
                    logger.error(e);
                }
               
                try {
                	postalcode = ServletRequestUtils.getRequiredStringParameter(request, "villagepostalcode");
                } catch (Exception e) {
                    logger.error(e);
                }
                try {
                    vcmeetingdate = ServletRequestUtils.getRequiredStringParameter(request, "vcmeetingdate");
                } catch (Exception e) {
                    logger.error(e);
                }

                if (id != "") {
                    projectArea.setProjectareaid(Long.parseLong(id));
                    projectArea.setModifiedby(user_id.intValue());
                    projectArea.setModifieddate(new Date());
                }

                  projectArea.setIsactive(true);
                  projectArea.setCreatedby(user_id.intValue());
                  projectArea.setCreateddate(new Date());
                  projectArea.setAuthorizedmember(villageChairman);
                  projectArea.setExecutiveofficer(approvingExecutive);
                  projectArea.setLandofficer(districtOfficer);
                  projectArea.setCertificatenumber(certificationNumber);
                  projectArea.setPostalcode(postalcode);
                  try {
					projectArea.setAuthorizedmembersignature(ServletRequestUtils.getStringParameter(request, "hSignatureVillageChairman", "")+"."+ar[1]);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
                  try {
					projectArea.setExecutiveofficersignature(ServletRequestUtils.getStringParameter(request, "hSignatureVillageExecutive", "")+"."+ar[1]);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
                  try {
					projectArea.setLandofficersignature(ServletRequestUtils.getStringParameter(request, "hSignatureDistrictOfficer", "")+"."+ar[1]);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
                  projectArea.setProject(project);

                if (StringUtils.isEmpty(vcmeetingdate)) {
                    projectArea.setVcMeetingDate(null);
                } else {
                    projectArea.setVcMeetingDate(new SimpleDateFormat("yyyy-MM-dd").parse(vcmeetingdate));
                }
                projectAreaset.add(projectArea);
            } catch (Exception e) {
                logger.error(e);
            }

            //SET users
            for (String user : users) {
                UserProject userProject = new UserProject();
                User obuser = userService.findUserByUserId(Integer.parseInt(user));
                userProject.setUser(obuser);
                userProject.setProject(project);
                userProject.setActive(true);
                userProject.setCreatedby(user_id.longValue());
                userProject.setCreateddate(new Date());
                userProjectList.add(userProject);
            }

            //SET Baselayer
            if (baselayers != null) {
                for (int j = 0; j < baselayers.length; j++) {
                    ProjectBaselayer projectBaselayer = new ProjectBaselayer();
                    Baselayer baselayer = baselayerService.findBaselayerById(Integer.parseInt(baselayers[j]));
                    projectBaselayer.setBaselayers(baselayer);
                    projectBaselayer.setProject(project);
                    //projectBaselayer.setBaselayerorder(j + 1);
                    projectBaselayer.setIsactive(true);
                    projectBaselayer.setCreatedby(user_id.intValue());
                    projectBaselayer.setCreateddate(new Date());
                    projectBaselayerList.add(projectBaselayer);
                }
            }

            Set<ProjectLayergroup> plgList = new HashSet<ProjectLayergroup>();

            //Set project layer Group
            for (int i = 0; i < layerGroup.length; i++) {
                ProjectLayergroup plg = new ProjectLayergroup();
                Layergroup lg = layerGroupService.findLayerGroupsById(Integer.parseInt(layerGroup[i]));
                plg.setLayergroupBean(lg);
                plg.setProjectBean(project);
                plg.setIsactive(true);
                plg.setCreatedby(user_id.intValue());
                plg.setCreateddate(new Date());
                plgList.add(plg);
            }

            project.setProjectLayergroups(plgList);
            project.setUserProjects(userProjectList);
            project.setProjectBaselayers(projectBaselayerList);
            project.setProjectArea(projectAreaset);

            projectService.addProject(project);
                    
            
            return "ProjectAdded";
        } catch (Exception e) {
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
    public List<String> getAllProjectNames() {
        List<String> results = projectService.getAllProjectNames();

        return results;
    }

    @RequestMapping(value = "/studio/project/{id}/user", method = RequestMethod.GET)
    @ResponseBody
    public List<String> getUsersByProject(@PathVariable String id) {

        //List<String> results = projectService.getUsersByProject(id);		
        List<String> results = projectService.getUserEmailByProject(id);
        return results;
    }

   
    @RequestMapping(value = "/studio/projectcontry/", method = RequestMethod.GET)
    @ResponseBody
    public List<ProjectRegion> getList() {
        return projectService.findAllCountry();
    }

   
    @RequestMapping(value = "/studio/projectregion/{Id}", method = RequestMethod.GET)
    @ResponseBody
    public List<ProjectRegion> getList(@PathVariable Integer Id) {
        return projectService.findRegionByCountry(Id);
    }

    @RequestMapping(value = "/studio/projectdistrict/{Id}", method = RequestMethod.GET)
    @ResponseBody
    public List<ProjectRegion> getListRegion(@PathVariable Integer Id) {
        return projectService.findDistrictByRegion(Id);
    }

    @RequestMapping(value = "/studio/projectvillage/{Id}", method = RequestMethod.GET)
    @ResponseBody
    public List<ProjectRegion> getListVillage(@PathVariable Integer Id) {
        return projectService.findVillageByDistrict(Id);
    }

    @RequestMapping(value = "/studio/projecthamlet/{Id}", method = RequestMethod.GET)
    @ResponseBody
    public List<ProjectRegion> getListHamlet(@PathVariable Integer Id) {
        return projectService.findPlaceByVillage(Id);
    }
    
    
    @RequestMapping(value = "/studio/projection", method = RequestMethod.GET)
    @ResponseBody
    public List<Projection> getAllProjection() {
    	
        return projectionService.findAllProjection();
    }

    @RequestMapping(value = "studio/project/userbyrole/", method = RequestMethod.GET)
    @ResponseBody
    public List<User> userrolelist() {
        List<User> userrolelst = new ArrayList<User>();
        ArrayList<Integer> userid = new ArrayList<Integer>();

        List<Integer> lstRole = new ArrayList<Integer>();
        lstRole.add(Role.DPI);
        lstRole.add(Role.SFR);
        lstRole.add(Role.ROLE_ADMIN);
        
        List<UserRole> userroleid = projectService.findAlluserrole(lstRole);

        for (int i = 0; i < userroleid.size(); i++) {

        	Integer  id = (int)userroleid.get(i).getUser().getId();

           userid.add(id);
        	
        }
        try {

            userrolelst = userService.findUserById(userid);

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

    @RequestMapping(value = "/studio/project/delethamlet/{hamletcode}/{projectName}", method = RequestMethod.GET)
    @ResponseBody
    public boolean deleteHamlet(@PathVariable String hamletcode, @PathVariable String projectName) {

        long hamlet_id = projectService.getHamletIdbyCode(hamletcode, projectName);
        boolean check = landRecordsService.findExistingHamlet(hamlet_id);
        if (!check) {
            return projectService.deletHamletbyId(hamlet_id);
        } else {
            return false;
        }
    }

    @RequestMapping(value = "/studio/project/uploadsignature", method = RequestMethod.POST)
    @ResponseBody
    public String uploadSignature(MultipartHttpServletRequest request, HttpServletResponse response) {
        try {
            // Save signature
            byte[] signature;
            Iterator<String> files = request.getFileNames();

            while (files.hasNext()) {
                MultipartFile mpFile = request.getFile(files.next());
                signature = mpFile.getBytes();
                String fileName = mpFile.getOriginalFilename();
                String uid = UUID.randomUUID().toString();
               
                fileName = uid + "." + fileName.substring(fileName.indexOf(".") + 1, fileName.length()).toLowerCase();

                String outDirPath = FileUtils.getFielsFolder(request) + "resources" + File.separator + "signatures";
                File outDir = new File(outDirPath);
                if (!outDir.exists()) {
                    (new File(outDirPath)).mkdirs();
                }

                try (FileOutputStream uploadfile = new FileOutputStream(outDirPath + File.separator + fileName)) {
                    uploadfile.write(signature);
                    uploadfile.flush();
                } catch (Exception e) {
                    logger.error(e);
                    return null;
                }
                return uid;
            }
            return null;
        } catch (Exception e) {
            logger.error(e);
            return null;
        }
    }

    @RequestMapping(value = "/studio/project/signatureexists/{fileName}", method = RequestMethod.GET)
    @ResponseBody
    public boolean checkSignatureExists(@PathVariable String fileName, HttpServletRequest request) {
        File signature = new File(FileUtils.getFielsFolder(request) + "resources" + File.separator + "signatures" + File.separator + fileName + ".gif");
        File signature2 = new File(FileUtils.getFielsFolder(request) + "resources" + File.separator + "signatures" + File.separator + fileName + ".png");
        File signature3 = new File(FileUtils.getFielsFolder(request) + "resources" + File.separator + "signatures" + File.separator + fileName + ".jpg");
        if(signature.exists() ||signature2.exists() ||signature3.exists() ){
        	
        	return true;
        	
        }else{
        
        	return false;
        
        }
    }

    @RequestMapping(value = "/studio/project/getsignature/{fileName}", method = RequestMethod.GET)
    @ResponseBody
    public void getSignature(@PathVariable String fileName, HttpServletRequest request, HttpServletResponse response) {
        try {
        	byte[] data = null ;
            Path path = Paths.get(FileUtils.getFielsFolder(request) + "resources" + File.separator + "signatures" + File.separator + fileName + ".jpeg");

            try {
				if (path.toFile().exists()) {
				   // writeEmptyImage(request, response);
				    data = Files.readAllBytes(path);
				    response.setHeader("Content-Type", "image/jpeg");
				    response.setContentLength(data.length);
				}
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
            Path path1 = Paths.get(FileUtils.getFielsFolder(request) + "resources" + File.separator + "signatures" + File.separator + fileName + ".png");
            
            try {
				if (path1.toFile().exists()) {
				  //  writeEmptyImage(request, response);
				    data = Files.readAllBytes(path1);
				    response.setHeader("Content-Type", "image/png");
				    response.setContentLength(data.length);
				}
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
            Path path2 = Paths.get(FileUtils.getFielsFolder(request) + "resources" + File.separator + "signatures" + File.separator + fileName + ".gif");
            
            try {
				if (path2.toFile().exists()) {
				   // writeEmptyImage(request, response);
				    data = Files.readAllBytes(path2);
				    response.setHeader("Content-Type", "image/jpg");
				    response.setContentLength(data.length);
				}
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
           
           // data = Files.readAllBytes(path);
		   // response.setHeader("Content-Type", "image/png");
           // response.setContentLength(data.length);
            response.addHeader("Content-disposition", "inline; inline; filename=\"" + fileName + "\"");

            try (OutputStream out = response.getOutputStream()) {
                out.write(data);
                out.flush();
            }
        } catch (Exception e) {
            logger.error(e);
        }
    }

    @RequestMapping(value = "/studio/project/deletesignature/{fileName}", method = RequestMethod.GET)
    @ResponseBody
    public boolean deleteSignature(@PathVariable String fileName, HttpServletRequest request) {
        File signature = new File(FileUtils.getFielsFolder(request) + "resources" + File.separator + "signatures" + File.separator + fileName + ".gif");
        if (signature.exists()) {
            try {
                signature.delete();
            } catch (Exception e) {
                logger.error(e);
                return false;
            }
        }
        
        File signature1 = new File(FileUtils.getFielsFolder(request) + "resources" + File.separator + "signatures" + File.separator + fileName + ".png");
        if (signature1.exists()) {
            try {
            	signature1.delete();
            } catch (Exception e) {
                logger.error(e);
                return false;
            }
        }
        
        
        File signature2 = new File(FileUtils.getFielsFolder(request) + "resources" + File.separator + "signatures" + File.separator + fileName + ".jpeg");
        if (signature2.exists()) {
            try {
            	signature2.delete();
            } catch (Exception e) {
                logger.error(e);
                return false;
            }
        }
        return true;
    } 

    public void writeEmptyImage(HttpServletRequest request, HttpServletResponse response) {
        try {
            byte[] data = Files.readAllBytes(Paths.get(request.getSession().getServletContext().getRealPath("/resources/images/pixel.png")));
            response.setContentLength(data.length);
            response.setHeader("Content-Type", "image/png");
            response.addHeader("Content-disposition", "inline; inline; filename=\"photo.png\"");

            try (OutputStream out = response.getOutputStream()) {
                out.write(data);
                out.flush();
            }
        } catch (Exception e) {
            logger.error(e);
        }
    }
    
    
    @RequestMapping(value = "/studio/project/Allproject/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<UserProject> getAllUserProject(@PathVariable String id) {
        return userProjectService.findAllUserProjectByUserID(Long.parseLong(id));
    }
    
    
    @RequestMapping(value = "/studio/project/info", method = RequestMethod.GET)
    @ResponseBody
    public List<ProjectData>  gatAllProjectInfo() {
        return  projectService.getAllProjectInfo();
    }
    
    
   
    
}
