package com.rmsi.mast.studio.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.annotations.BatchSize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rmsi.mast.studio.dao.BookmarkDAO;
import com.rmsi.mast.studio.dao.LayerGroupDAO;
import com.rmsi.mast.studio.dao.MaptipDAO;
import com.rmsi.mast.studio.dao.ProjectAdjudicatorDAO;
import com.rmsi.mast.studio.dao.ProjectAreaDAO;
import com.rmsi.mast.studio.dao.ProjectAttributeDAO;
import com.rmsi.mast.studio.dao.ProjectBaselayerDAO;
import com.rmsi.mast.studio.dao.ProjectDAO;
import com.rmsi.mast.studio.dao.ProjectDataDAO;
import com.rmsi.mast.studio.dao.ProjectHamletDAO;
import com.rmsi.mast.studio.dao.ProjectLayergroupDAO;
import com.rmsi.mast.studio.dao.ProjectRegionDAO;
import com.rmsi.mast.studio.dao.UserDAO;
import com.rmsi.mast.studio.dao.UserProjectDAO;
import com.rmsi.mast.studio.dao.UserRoleDAO;
import com.rmsi.mast.studio.domain.Bookmark;
import com.rmsi.mast.studio.domain.Layergroup;
import com.rmsi.mast.studio.domain.Maptip;
import com.rmsi.mast.studio.domain.MaptipPK;
import com.rmsi.mast.studio.domain.Project;
import com.rmsi.mast.studio.domain.ProjectAdjudicator;
import com.rmsi.mast.studio.domain.ProjectArea;
import com.rmsi.mast.studio.domain.ProjectHamlet;
import com.rmsi.mast.studio.domain.ProjectLayergroup;
import com.rmsi.mast.studio.domain.ProjectRegion;
import com.rmsi.mast.studio.domain.ProjectSpatialData;
import com.rmsi.mast.studio.domain.Savedquery;
import com.rmsi.mast.studio.domain.User;
import com.rmsi.mast.studio.domain.UserProject;
import com.rmsi.mast.studio.domain.UserRole;
import com.rmsi.mast.studio.domain.fetch.ProjectData;
import com.rmsi.mast.studio.domain.fetch.ProjectDetails;
import com.rmsi.mast.studio.domain.fetch.ProjectLocation;
import com.rmsi.mast.studio.domain.fetch.ProjectTemp;
import com.rmsi.mast.studio.service.MaptipService;
import com.rmsi.mast.studio.service.ProjectService;
import com.rmsi.mast.viewer.dao.ProjectTempDao;
import com.rmsi.mast.viewer.web.mvc.LandRecordsController;

/**
 * @author Aparesh.Chakraborty
 *
 */
@Service
public class ProjectServiceImpl implements ProjectService {

    private static final Logger logger = Logger.getLogger(ProjectServiceImpl.class);

    @Autowired
    private ProjectDAO projectDAO;
    @Autowired
    private BookmarkDAO bookmarkDAO;
    @Autowired
    private ProjectAttributeDAO projectAttributeDAO;

    @Autowired
    private ProjectDataDAO projectDataDAO;
    @Autowired
    private ProjectLayergroupDAO projectLayergroupDAO;
    @Autowired
    private UserProjectDAO userProjectDAO;
    @Autowired
    private ProjectBaselayerDAO projectbaselayerDAO;

    @Autowired
    private UserRoleDAO userRoleDAO;

    @Autowired
    private UserDAO userdao;

    @Autowired
    private MaptipDAO maptipDAO;
    @Autowired
    private LayerGroupDAO layerGroupDAO;

    @Autowired
    private MaptipService maptipService;

    @Autowired
    private ProjectRegionDAO projectrRegionDAO;

    @Autowired
    private ProjectAreaDAO projectAreaDAO;

    @Autowired
    private ProjectTempDao projectTempDao;

    @Autowired
    private ProjectAdjudicatorDAO projectAdjudicatorDAO;

    @Autowired
    private ProjectHamletDAO projectHamletDAO;

    @Override
    //@TriggersRemove(cacheName = { "projectFBNCache", "userFBNCache" }, removeAll = true)
    @BatchSize(size = 20)
    // @TriggersRemove(cacheName ="projectFBNCache", removeAll = true)
    public void addSaveProject(Project project, Set<Layergroup> layergroups,
            List<Bookmark> bookmarks, String projectName) {
        System.out.println("--------projectIMPL" + project.getName());
        Iterator<Layergroup> lgitr = layergroups.iterator();
        for (; lgitr.hasNext();) {

            Layergroup layergroup = lgitr.next();
            //layergroup.setProjectLayergroups(new HashSet<ProjectLayergroup>());
            layerGroupDAO.makePersistent(layergroup);

        }

        projectDAO.makePersistent(project);
        /**
         * ********** save bookmark *********
         */

        if (bookmarks != null) {
            Iterator<Bookmark> bkitr = bookmarks.iterator();
            for (; bkitr.hasNext();) {

                Bookmark bookmark = bkitr.next();

                //	bookmark.setName(project.getName() + "_" + bookmark.getName());
                //bookmark.setName(project.getName() + "_"
                //			+ bookmark.getDescription());
                //bookmark.setProjectBean(project);
                bookmarkDAO.makePersistent(bookmark);

            }
        }
        /**
         * ******* Save Maptip *****************
         */
        List<Maptip> maptips = maptipDAO.getMaptipsByProject(projectName);
        Project proj = project;
        //	proj.setProjectLayergroups(new HashSet<ProjectLayergroup>());
        //proj.setUserProjects(new HashSet<UserProject>());
        if (maptips != null) {
            Iterator<Maptip> mitr = maptips.iterator();
            for (; mitr.hasNext();) {

                Maptip newMaptip = new Maptip();
                MaptipPK newMPK = new MaptipPK();

                Maptip maptip = mitr.next();
                MaptipPK mpk = maptip.getId();

                newMPK.setProject(proj.getName());
                newMPK.setLayer(mpk.getLayer());

                newMaptip.setId(newMPK);
                newMaptip.setField(maptip.getField());
                newMaptip.setLayerBean(maptip.getLayerBean());
                newMaptip.setName(proj.getName() + "_" + maptip.getName());
                newMaptip.setProjectBean(proj);
                newMaptip.setQueryexpression(maptip.getQueryexpression());

                /*
				 * System.out.println("-----------------------------------");
				 * System.out.println("-----getName"+newMaptip.getName());
				 * System.out.println("-----getField"+newMaptip.getField());
				 * System
				 * .out.println("-----getId().getLayer"+newMaptip.getId().
				 * getLayer ());
				 * System.out.println("-----getProject()"+newMaptip.getId().
				 * getProject());
				 * System.out.println("-----getLayerBean().getName"
				 * +newMaptip.getLayerBean().getName());
				 * System.out.println("-----getProjectBean().getName"
				 * +newMaptip.getProjectBean().getName());
				 * System.out.println("-----------------------------------");
                 */
                maptipDAO.makePersistent(newMaptip);

            }
        }

    }

    @Override
    //@TriggersRemove(cacheName = "projectFBNCache", removeAll = true)
    public void addProject(Project project) {
        // delete existing projectlayergroup
        if (null != project.getProjectnameid()) {
            try {
                projectLayergroupDAO.deleteProjectLayergroupByProjectId(project.getProjectnameid());
            } catch (Exception e) {
                logger.error(e);
            }
        }

        // delete existing project's baselayer
        if (null != project.getProjectnameid()) {
            try {
                projectbaselayerDAO.deleteProjectBaselayerByProjectId(project.getProjectnameid());
            } catch (Exception e) {
                logger.error(e);
            }
        }

        // delete existing project's ProjectArea
        if (null != project.getProjectnameid()) {
            try {
                projectAreaDAO.deleteProjectAreaByProjectId(project.getProjectnameid());
            } catch (Exception e) {
                logger.error(e);
            }
        }

        // delete existing user project
        if (null != project.getProjectnameid()) {
            try {
                projectAreaDAO.deleteProjectAreaByProjectId(project.getProjectnameid());
            } catch (Exception e) {
                logger.error(e);
            }
        }

        // delete existing user project
        if (null != project.getProjectnameid()) {
            try {
                userProjectDAO.deleteUserProjectByUser(project.getProjectnameid());
            } catch (Exception e) {
                logger.error(e);
            }
        }

        if (null != project.getProjectnameid()) {
            try {
                projectAreaDAO.deleteProjectAreaByProjectId(project.getProjectnameid());
            } catch (Exception e) {
                logger.error(e);
            }
        }

        try {
            projectDAO.makePersistent(project);
        } catch (Exception e1) {
            logger.error(e1);
        }

    }

    @Override
    public void deleteProject() {
        // TODO Auto-generated method stub

    }

    @Override
    //@TriggersRemove(cacheName = "projectFBNCache", removeAll = true)
    public void deleteProjectById(Integer id) {
        // delete uder project name
        try {
            userProjectDAO.deleteUserProjectByUser(id);

            // delete user layergroup by project name
            projectLayergroupDAO.deleteProjectLayergroupByProjectId(id);

            // delete project's baselayer
            projectbaselayerDAO.deleteProjectBaselayerByProjectId(id);

            // delete project's bookmark
            bookmarkDAO.deleteByProjectId(id);

            // delete project area
            projectAreaDAO.deleteProjectAreaByProjectId(id);

            //projectDataDAO.deleteByProjectName(name);
            //project attribute delete
            //projectAttributeDAO.deleteByProjectName(name);
            // delete project by project name
            projectDAO.deleteProject(id);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Override
    public void updateProject(Project project) {
        // TODO Auto-generated method stub

    }

    @Override
    //@Cacheable(cacheName = "projectFBNCache")
    public Project findProjectById(Long id) {
        return projectDAO.findById(id, false);

    }

    @Override
    //@Cacheable(cacheName = "projectFBNCache")
    public List<Project> findAllProjects() {
        // return projectDAO.findAll();
        return projectDAO.findAllProjects();
    }

    @Override
    public List<ProjectDetails> getAllProjectsDetails() {
        return projectDAO.getAllProjectsDetails();
    }

    @Override
    //@Cacheable(cacheName = "projectFBNCache")
    public Project findProjectByName(String name) {
        return projectDAO.findByName(name);
    }

    //@Cacheable(cacheName = "projectFBNCache")
    /*public List<Savedquery> getSavedqueryByProject(String projectname) {

		Project project = projectDAO.findByName(projectname);
		List<Savedquery> savedquerylist = new ArrayList<Savedquery>(
				project.getSavedqueries());

		return savedquerylist;
	} @*/
    //@Cacheable(cacheName = "projectFBNCache")
    public List<String> getAllProjectNames() {
        return projectDAO.getProjectNames();
    }

    @Override
    //@Cacheable(cacheName = "projectFBNCache")
    public List<String> getUsersByProject(String name) {
        // TODO Auto-generated method stub
        // return userProjectDAO.findAllUserProject(name);

        return userProjectDAO.getUsersByProject(name);
    }

    @Override
    //@Cacheable(cacheName = "projectFBNCache")
    public List<Project> getAllUserProjects() {
        return projectDAO.getAllUserProjects();

    }

    @Override
    //@Cacheable(cacheName = "projectFBNCache")
    public List<Project> getProjectsByOwner(String email) {

        return projectDAO.getProjectsByOwner(email);
    }

    /* ************@RMSI/NK add for country start****************************** 1-5   */
    public List<ProjectRegion> findAllCountry() {
        // return projectDAO.findAll();  getcountryval(this);
        return projectrRegionDAO.findAllCountry();
    }

    public List<ProjectRegion> findRegionByCountry(Integer Id) {
        return projectrRegionDAO.findRegionByCountry(Id);
    }

    public List<ProjectRegion> findDistrictByRegion(Integer Id) {
        return projectrRegionDAO.findDistrictByRegion(Id);
    }

    public List<ProjectRegion> findVillageByDistrict(Integer Id) {
        return projectrRegionDAO.findVillageByDistrict(Id);
    }

    public List<ProjectRegion> findPlaceByVillage(Integer Id) {
        return projectrRegionDAO.findPlaceByVillage(Id);
    }

    @Override
    public boolean checkprojectname(String projectName) {
        return projectAreaDAO.checkProjectName(projectName);
    }

    @Override
    public boolean updateProjectArea(String projectName) {
        return projectAreaDAO.updateProjectArea(projectName);
    }

    @Override
    public List<String> getUserEmailByProject(String id) {

        return userProjectDAO.getUserNameByProject(id);
    }

    @Override
    public List<UserRole> findAlluserrole(List<Integer> lstRole) {

        return userRoleDAO.selectedUserByUserRole(lstRole);
    }

    @Override
    public boolean checkduplicatename(String projectName) {
        return projectDAO.checkduplicatename(projectName);
    }

    @Override
    public ProjectTemp findProjectTempByName(String defaultproject) {
        return projectTempDao.findById(defaultproject, false);
    }

    @Override
    public void addAdjudicatorDetails(ProjectAdjudicator adjObj) {

        projectAdjudicatorDAO.makePersistent(adjObj);

    }

    @Override
    public List<ProjectAdjudicator> findAdjudicatorByProject(String projname) {
        return projectAdjudicatorDAO.findByProject(projname);
    }

    @Override
    public void deleteAdjByProject(String projectName) {
        projectAdjudicatorDAO.deleteByProject(projectName);

    }

    @Override
    public void addHamlets(ProjectHamlet hamletObj) {
        projectHamletDAO.makePersistent(hamletObj);

    }

    @Override
    public List<ProjectHamlet> findHamletByProject(String projname) {

        return projectHamletDAO.findHamlets(projname);
    }

    @Override
    public void deleteHamletByProject(String projectName) {
        projectHamletDAO.deleteEntries(projectName);
    }

    @Override
    public void saveHamlet(ProjectHamlet hamlet_Id) {
        projectHamletDAO.makePersistent(hamlet_Id);

    }

    @Override
    public ProjectHamlet findHamletById(long hamletId) {
        return projectHamletDAO.findById(hamletId, false);
    }

    @Override
    public long getHamletIdbyCode(String hamletcode, String projectName) {
        return projectHamletDAO.getHamletIdbyCode(hamletcode, projectName);
    }

    @Override
    public boolean deletHamletbyId(long hamlet_id) {
        try {
            projectHamletDAO.makeTransientByID(hamlet_id);
            return true;
        } catch (Exception e) {
            logger.error(e);
            return false;
        }
    }

    @Override
    public List<String> getHamletCodesbyProject(String projectName) {
        return projectHamletDAO.getHamletCodesbyProject(projectName);
    }

    @Override
    public List<Savedquery> getSavedqueryByProject(String project) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Project> getdAllProjectsNames() {
        // TODO Auto-generated method stub
        return projectDAO.getAllProjectsNames();
    }

    @Override
    public List<Bookmark> getBookmarksByProject(String project) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Project> getdAllProjectsdetails() {
        return projectDAO.getAllProjectsNames();
    }

    @Override
    public List<ProjectArea> getProjectArea(String projectName) {

        return projectAreaDAO.findByProjectName(projectName);

    }

    @Override
    public List<ProjectData> getAllProjectInfo() {
        // TODO Auto-generated method stub
        return projectDAO.getAllProjectInfo();
    }
    
    @Override
    public ProjectLocation getProjectLocation(Integer id){
        return projectDAO.getProjectLocation(id);
    }
}
