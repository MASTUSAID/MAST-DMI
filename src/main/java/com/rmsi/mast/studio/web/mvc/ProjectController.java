package com.rmsi.mast.studio.web.mvc;

import java.util.ArrayList;

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
import com.rmsi.mast.studio.domain.SourceDocument;
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
import com.rmsi.mast.studio.util.FileUtils;
import com.rmsi.mast.studio.util.SaveProject;
import com.rmsi.mast.studio.util.StringUtils;
import com.rmsi.mast.viewer.service.LandRecordsService;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

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
    public String createProject(HttpServletRequest request, HttpServletResponse response) {

        String projectName;
        Project project;
        String[] project_adjudicatorhid = null;
        String[] adjudicatorsSignature = null;
        String[] hamlet_name = null;
        String[] hamlet_alias = null;
        String[] hamlet_code = null;
        String[] hamlet_leader = null;

        try {
            projectName = ServletRequestUtils.getRequiredStringParameter(request, "name");

            try {
                project_adjudicatorhid = ServletRequestUtils.getRequiredStringParameters(request, "project_adjudicatorhid");
                adjudicatorsSignature = ServletRequestUtils.getRequiredStringParameters(request, "hSignatureAdjudicator");
            } catch (Exception e) {
                logger.error(e);
            }

            try {
                hamlet_name = ServletRequestUtils.getRequiredStringParameters(request, "hamletName");
                hamlet_alias = ServletRequestUtils.getRequiredStringParameters(request, "hamletAlias");
                hamlet_code = ServletRequestUtils.getRequiredStringParameters(request, "hamletCode");
                hamlet_leader = ServletRequestUtils.getRequiredStringParameters(request, "hamletLeaderName");
            } catch (Exception e1) {
                logger.error(e1);
            }

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
            project.setActivelayer(ServletRequestUtils.getRequiredStringParameter(request, "activelayer"));
            project.setCopyright(ServletRequestUtils.getRequiredStringParameter(request, "copyright"));
            project.setCosmetic(Boolean.parseBoolean(ServletRequestUtils.getRequiredStringParameter(request, "cosmetic")));
            project.setDescription(ServletRequestUtils.getRequiredStringParameter(request, "description"));
            project.setDisclaimer(ServletRequestUtils.getRequiredStringParameter(request, "disclaimer"));
            project.setMinextent(ServletRequestUtils.getRequiredStringParameter(request, "minextent"));
            project.setMaxextent(ServletRequestUtils.getRequiredStringParameter(request, "maxextent"));
            project.setNumzoomlevels(Integer.parseInt(ServletRequestUtils.getRequiredStringParameter(request, "numzoomlevels")));
            project.setOverlaymap(ServletRequestUtils.getRequiredStringParameter(request, "overlaymap"));
            project.setRestrictedextent(ServletRequestUtils.getRequiredStringParameter(request, "restrictedextent"));
            project.setWatermask(ServletRequestUtils.getRequiredStringParameter(request, "watermask"));
            project.setUnit(unitService.findUnitByName(ServletRequestUtils.getRequiredStringParameter(request, "unit.name")));
            project.setProjection(projectionService.findProjectionByName(ServletRequestUtils.getRequiredStringParameter(request, "projection.code")));
            project.setDisplayProjection(projectionService.findProjectionByName(ServletRequestUtils.getRequiredStringParameter(request, "displayProjection.code")));
            project.setOutputformat(outputformatService.findOutputformatByName(ServletRequestUtils.getRequiredStringParameter(request, "outputFormat.name")));
            project.setAdmincreated(true);
            project.setOwner(request.getParameter("emailid"));
            project.setCopyright("custom");
            project.setWatermask("custom");

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
            List<ProjectArea> projectAreaList = new ArrayList<ProjectArea>();

            ProjectArea projectArea = new ProjectArea();
            String countryname = "";
            String region = "";
            String districtname = "";
            String village = "";
            String id = "";
            String districtOfficer = "";
            String villageChairman = "";
            String approvingExecutive = "";
            String villagecode = "";
            String regionCode = "";
            String villagepostalcode = "";
            String vcmeetingdate = "";

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
                    villagecode = ServletRequestUtils.getRequiredStringParameter(request, "villagecode");
                } catch (Exception e) {
                    logger.error(e);
                }
                try {
                    regionCode = ServletRequestUtils.getRequiredStringParameter(request, "regioncode");
                } catch (Exception e) {
                    logger.error(e);
                }
                try {
                    villagepostalcode = ServletRequestUtils.getRequiredStringParameter(request, "villagepostalcode");
                } catch (Exception e) {
                    logger.error(e);
                }
                try {
                    vcmeetingdate = ServletRequestUtils.getRequiredStringParameter(request, "vcmeetingdate");
                } catch (Exception e) {
                    logger.error(e);
                }

                if (id != "") {
                    projectArea.setAreaId(Long.parseLong(id));
                }

                projectArea.setCountryName(countryname);
                projectArea.setRegion(region);
                projectArea.setRegionCode(regionCode);
                projectArea.setDistrictName(districtname);
                projectArea.setVillage(village);
                projectArea.setInitiationDate(new Date());
                projectArea.setProjectName(projectName);
                projectArea.setVillageChairman(villageChairman);
                projectArea.setApprovingExecutive(approvingExecutive);
                projectArea.setDistrictOfficer(districtOfficer);
                projectArea.setVillage_code(villagecode);
                projectArea.setAddress(villagepostalcode);
                projectArea.setVillageChairmanSignaturePath(ServletRequestUtils.getStringParameter(request, "hSignatureVillageChairman", ""));
                projectArea.setVillageExecutiveSignaturePath(ServletRequestUtils.getStringParameter(request, "hSignatureVillageExecutive", ""));
                projectArea.setDistrictOfficerSignaturePath(ServletRequestUtils.getStringParameter(request, "hSignatureDistrictOfficer", ""));

                if (StringUtils.isEmpty(vcmeetingdate)) {
                    projectArea.setVcMeetingDate(null);
                } else {
                    projectArea.setVcMeetingDate(new SimpleDateFormat("yyyy-MM-dd").parse(vcmeetingdate));
                }
                projectAreaList.add(projectArea);
            } catch (Exception e) {
                logger.error(e);
            }

            //SET users
            for (String user : users) {
                UserProject userProject = new UserProject();
                User obuser = userService.findUserByUserId(Integer.parseInt(user));
                userProject.setUser(obuser);
                userProject.setProjectBean(project);
                userProjectList.add(userProject);
            }

            //SET Baselayer
            if (baselayers != null) {
                for (int j = 0; j < baselayers.length; j++) {
                    ProjectBaselayer projectBaselayer = new ProjectBaselayer();
                    Baselayer baselayer = new Baselayer();
                    baselayer.setName(baselayers[j]);
                    projectBaselayer.setBaselayerBean(baselayer);
                    projectBaselayer.setProjectBean(project);
                    projectBaselayer.setBaselayerorder(j + 1);
                    projectBaselayerList.add(projectBaselayer);
                }
            }

            Set<ProjectLayergroup> plgList = new HashSet<ProjectLayergroup>();

            for (int i = 0; i < layerGroup.length; i++) {
                ProjectLayergroup plg = new ProjectLayergroup();
                Layergroup lg = new Layergroup();
                lg.setName(layerGroup[i]);
                plg.setLayergroupBean(lg);
                plg.setProjectBean(project);
                plg.setGrouporder(i + 1);
                plgList.add(plg);
            }

            project.setProjectLayergroups(plgList);
            project.setUserProjects(userProjectList);
            project.setProjectBaselayers(projectBaselayerList);
            project.setProjectAreas(projectAreaList);

            projectService.addProject(project);
            projectService.deleteAdjByProject(projectName);

            ProjectAdjudicator adjObj = new ProjectAdjudicator();

            if (project_adjudicatorhid != null && adjudicatorsSignature != null && project_adjudicatorhid.length == adjudicatorsSignature.length) {
                for (int j = 0; j < project_adjudicatorhid.length; j++) {
                    adjObj.setAdjudicatorName(project_adjudicatorhid[j]);
                    adjObj.setProjectName(projectName);
                    adjObj.setSignaturePath(adjudicatorsSignature[j]);
                    projectService.addAdjudicatorDetails(adjObj);
                }
            }

            List<String> hamlettmplst = projectService.getHamletCodesbyProject(projectName);

            if (hamlet_name != null) {
                for (int j = 0; j < hamlet_name.length; j++) {
                    ProjectHamlet hamletObj = new ProjectHamlet();
                    hamletObj.setHamletName(hamlet_name[j]);
                    hamletObj.setHamletNameSecondLanguage(hamlet_alias[j]);
                    hamletObj.setHamletCode(hamlet_code[j]);
                    hamletObj.setHamletLeaderName(hamlet_leader[j]);
                    hamletObj.setProjectName(projectName);
                    hamletObj.setCount(0);
                    if (!hamlettmplst.contains(hamlet_code[j])) {
                        projectService.addHamlets(hamletObj);
                    }
                }
            }
            
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

    @RequestMapping(value = "/studio/project/save", method = RequestMethod.POST)
    @ResponseBody
    //public boolean saveProject(HttpServletRequest request){
    public boolean saveProject(@RequestBody SaveProject saveProject) {

        String[][] layerVis = saveProject.getLayerVisibility();

        String[] users = saveProject.getUsers();

        System.out.println("------------USERS----------");
        for (int i = 0; i < users.length; i++) {
            System.out.println(users[i]);
        }
        System.out.println("----------------------");

        Project project = getProjectById(saveProject.getActualProjectName());
        try {
            Project newProject = (Project) project.clone();
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

            for (; itr.hasNext();) {
                ProjectLayergroup projectLayerGroup = itr.next();
                Layergroup lyrGroupBean = projectLayerGroup.getLayergroupBean();
                Layergroup lyrGroup = new Layergroup();
                lyrGroup.setName(newProject.getName() + "_" + lyrGroupBean.getName());
                lyrGroup.setAlias(newProject.getName() + "_" + lyrGroupBean.getAlias());
                lyrGroup.setTenantid(lyrGroupBean.getTenantid());

                //Add layer_layergroup collection
                Set<LayerLayergroup> lyrLayerGroups = lyrGroupBean.getLayerLayergroups();
                Iterator<LayerLayergroup> itrLyrGrp = lyrLayerGroups.iterator();
                HashSet<LayerLayergroup> setLyrLayerGroups = new HashSet<LayerLayergroup>();
                for (; itrLyrGrp.hasNext();) {
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

            /**
             * ************	set project's base layer *********************
             */
            Set<ProjectBaselayer> projectBaselayerList = newProject.getProjectBaselayers();
            Iterator<ProjectBaselayer> baselyritr = projectBaselayerList.iterator();
            HashSet<ProjectBaselayer> newProjectBaselayerList = new HashSet<ProjectBaselayer>();
            Project baseLyrProj = new Project();
            baseLyrProj.setName(newProject.getName());

            for (; baselyritr.hasNext();) {

                ProjectBaselayer newProjectBaselayer = baselyritr.next();

                newProjectBaselayer.setProjectBean(newProject);
                newProjectBaselayer.setId(null);

                newProjectBaselayerList.add(newProjectBaselayer);
            }

            newProject.setProjectBaselayers(newProjectBaselayerList);

            /**
             * ************	set Associated users *********************
             */
            Set<UserProject> userProjecs = new HashSet<UserProject>();
            for (int i = 0; i < users.length; i++) {
                UserProject userProject = new UserProject();
                User usr = new User();
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
            for (; itrLg.hasNext();) {
                lg = itrLg.next();

                Set<LayerLayergroup> setLLg = lg.getLayerLayergroups();
                Iterator<LayerLayergroup> itrLLg = setLLg.iterator();
                System.out.println("----------Printing Associated LayerLayergroup-------------");
                for (; itrLLg.hasNext();) {
                    LayerLayergroup _llg = itrLLg.next();

                }
            }

            Set<ProjectLayergroup> setPLg = lg.getProjectLayergroups();
            Iterator<ProjectLayergroup> itrPlg = setPLg.iterator();
            for (; itrPlg.hasNext();) {
                ProjectLayergroup plg = itrPlg.next();

            }

            Set<ProjectBaselayer> setBlyr = newProject.getProjectBaselayers();
            Iterator<ProjectBaselayer> itrBlyr = setBlyr.iterator();
            for (; itrBlyr.hasNext();) {
                ProjectBaselayer blyr = itrBlyr.next();

            }

            Set<UserProject> setusrproj = newProject.getUserProjects();
            Iterator<UserProject> itrusrprojr = setusrproj.iterator();
            for (; itrusrprojr.hasNext();) {
                UserProject up = itrusrprojr.next();

            }

            //get project's bookmark
            List<Bookmark> bookmarks = bookmarkService.getBookmarksByProject(saveProject.getActualProjectName());

            projectService.addSaveProject(newProject, layerGroups, bookmarks, saveProject.getActualProjectName());

        } catch (CloneNotSupportedException e) {
            logger.error(e);
        }
        return true;
    }

    private boolean getLayerVisibilityState(String layer, String[][] lyrVisibility) {
        boolean bVisState = false;
        for (int i = 0; i < lyrVisibility.length; i++) {
            if (layer.equals(lyrVisibility[i][0])) {
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
    public List<User> userrolelist() {
        List<User> userrolelst = new ArrayList<User>();
        ArrayList<Integer> userid = new ArrayList<Integer>();

        List<String> lstRole = new ArrayList<String>();
        lstRole.add("ROLE_TRUSTED_INTERMEDIARY");
        lstRole.add("ROLE_PM");
        lstRole.add("ROLE_LAO");
        lstRole.add("ROLE_ADJUDICATOR");

        List<UserRole> userroleid = projectService.findAlluserrole(lstRole);

        for (int i = 0; i < userroleid.size(); i++) {

            Integer id = userroleid.get(i).getUser().getId();

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
        File signature = new File(FileUtils.getFielsFolder(request) + "resources" + File.separator + "signatures" + File.separator + fileName + ".jpg");
        return signature.exists();
    }

    @RequestMapping(value = "/studio/project/getsignature/{fileName}", method = RequestMethod.GET)
    @ResponseBody
    public void getSignature(@PathVariable String fileName, HttpServletRequest request, HttpServletResponse response) {
        try {
            Path path = Paths.get(FileUtils.getFielsFolder(request) + "resources" + File.separator + "signatures" + File.separator + fileName + ".jpg");

            if (!path.toFile().exists()) {
                writeEmptyImage(request, response);
                return;
            }

            byte[] data = Files.readAllBytes(path);
            response.setContentLength(data.length);
            response.setHeader("Content-Type", "image/jpeg");
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
        File signature = new File(FileUtils.getFielsFolder(request) + "resources" + File.separator + "signatures" + File.separator + fileName + ".jpg");
        if (signature.exists()) {
            try {
                signature.delete();
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
}
