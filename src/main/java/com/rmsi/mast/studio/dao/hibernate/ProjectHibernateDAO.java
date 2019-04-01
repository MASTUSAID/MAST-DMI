package com.rmsi.mast.studio.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.ProjectDAO;
import com.rmsi.mast.studio.domain.LaSpatialunitLand;
import com.rmsi.mast.studio.domain.Project;
import com.rmsi.mast.studio.domain.fetch.ProjectData;
import com.rmsi.mast.studio.domain.fetch.ProjectDetails;
import com.rmsi.mast.studio.domain.fetch.ProjectLocation;

@Repository
public class ProjectHibernateDAO extends GenericHibernateDAO<Project, Long>
        implements ProjectDAO {

    private static final Logger logger = Logger.getLogger(ProjectHibernateDAO.class);

    @SuppressWarnings("unchecked")
    public Project findByName(String name) {
        List<Project> project
                = getEntityManager().createQuery("Select p from Project p where p.name = :name").setParameter("name", name).getResultList();

        if (project.size() > 0) {
            return project.get(0);
        } else {
            return null;
        }
    }

    public List<String> getProjectNames() {

        TypedQuery<String> query = getEntityManager().createQuery("Select p.name from Project p order by p.name", String.class);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public boolean deleteProject(Integer id) {

        try {
            Query query = getEntityManager().createQuery("UPDATE Project p SET p.active = false where p.projectnameid = :id and p.active = true");

            query.setParameter("id", id);

            int rows = query.executeUpdate();

            if (rows > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {

            logger.error(e);
            return false;
        }

    }
//(p.admincreated=true and p.active = true)

    @SuppressWarnings("unchecked")
    public List<Project> findAllProjects() {

        //String hqlstr= "Select p from Project p inner join ProjectLayergroup plg on p.name = plg.projectBean.name"; 
        String hqlstr = "Select p from Project p where  p.active=true order by p.name";
        try {
            List<Project> project
                    = getEntityManager().createQuery(hqlstr).getResultList();
            return project;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public List<ProjectDetails> getAllProjectsDetails() {
        return getEntityManager().createQuery("Select p from ProjectDetails p order by p.name").getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Project> getAllUserProjects() {
        //String hqlstr= "Select p from Project p inner join ProjectLayergroup plg on p.name = plg.projectBean.name"; 
        String hqlstr = "Select p from Project p where p.active=true order by p.name";
        List<Project> project
                = getEntityManager().createQuery(hqlstr).getResultList();

        return project;
    }

    @SuppressWarnings("unchecked")
    public List<Project> getProjectsByOwner(String email) {

        String hqlstr = "Select p from Project p where p.admincreated=false and p.owner=:email order by p.name";
        List<Project> project
                = getEntityManager().createQuery(hqlstr).setParameter("email", email).getResultList();

        return project;
    }

    @Override
    public boolean checkduplicatename(String projectName) {

        try {
            String hqlstr = "Select p from Project p where p.name=:name";
            List<Project> project
                    = getEntityManager().createQuery(hqlstr).setParameter("name", projectName).getResultList();

            if (project.size() > 0) {
                return true;

            } else {
                return false;

            }
        } catch (Exception e) {

            logger.error(e);
            return false;
        }

    }

    @Override
    public List<Project> getAllProjectsNames() {

        List<Project> projectlst = new ArrayList<Project>();

        try {
            String hqlstr = "Select p from Project p where p.active=true";
            List<Project> project
                    = getEntityManager().createQuery(hqlstr).getResultList();

            if (project.size() > 0) {
                return project;

            } else {
                return null;

            }
        } catch (Exception e) {

            logger.error(e);
            return null;
        }

    }

    @Override
    public Project findByProjectId(Integer Id) {
        Project project = (Project) getEntityManager().createQuery("Select p from Project p where p.projectnameid = :name").setParameter("name", Id).getSingleResult();
        return project;
    }

    @Override
    public List<ProjectData> getAllProjectInfo() {
        List<ProjectData> pdata = new ArrayList<ProjectData>();

        try {
            String hqlstr = "Select p from Project p where p.active=true";
            List<Project> project
                    = getEntityManager().createQuery(hqlstr).getResultList();

            if (project.size() > 0) {
                for (Project obj : project) {
                    ProjectData objPd = new ProjectData();
                    objPd.setId(obj.getProjectnameid());
                    objPd.setName(obj.getName());
                    pdata.add(objPd);
                }

            }

        } catch (Exception e) {

            logger.error(e);
            return null;
        }
        return pdata;
    }

    @Override
    public ProjectLocation getProjectLocation(Integer id) {
        Query q = getEntityManager().createNativeQuery("select pa.projectnameid, l1.name as country, l2.name as county, l3.name as district, l4.name as clan, l5.name as village "
                + "from la_ext_projectarea pa left join la_spatialunitgroup_hierarchy l1 on pa.hierarchyid1 = l1.hierarchyid "
                + "  left join la_spatialunitgroup_hierarchy l2 on pa.hierarchyid2 = l2.hierarchyid "
                + "  left join la_spatialunitgroup_hierarchy l3 on pa.hierarchyid3 = l3.hierarchyid "
                + "  left join la_spatialunitgroup_hierarchy l4 on pa.hierarchyid4 = l4.hierarchyid "
                + "  left join la_spatialunitgroup_hierarchy l5 on pa.hierarchyid5 = l5.hierarchyid "
                + "where pa.projectnameid = :id", ProjectLocation.class);
        q.setParameter("id", id);
        return (ProjectLocation) q.getSingleResult();
    }
}
