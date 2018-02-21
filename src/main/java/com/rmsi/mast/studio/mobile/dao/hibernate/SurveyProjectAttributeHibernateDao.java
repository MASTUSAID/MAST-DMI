/**
 *
 */
package com.rmsi.mast.studio.mobile.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.mapping.Array;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.hibernate.GenericHibernateDAO;
import com.rmsi.mast.studio.domain.AttributeMaster;
import com.rmsi.mast.studio.domain.AttributeValues;
import com.rmsi.mast.studio.domain.Project;
import com.rmsi.mast.studio.domain.Surveyprojectattribute;
import com.rmsi.mast.studio.mobile.dao.SurveyProjectAttributeDao;

/**
 * @author shruti.thakur
 *
 */
@Repository
public class SurveyProjectAttributeHibernateDao extends
        GenericHibernateDAO<Surveyprojectattribute, Long> implements
        SurveyProjectAttributeDao {

    private static final Logger logger = Logger.getLogger(SurveyProjectAttributeHibernateDao.class);

    @Override
    public List<AttributeMaster> getSurveyAttributes(Project projectId) {
        String query = "select s from Surveyprojectattribute s where s.name"
                + " = :projectId and s.attributeMaster.isactive = true and s.attributeMaster.masterattribute=true "
                + "ORDER BY s.attributecategoryid ASC, s.attributeMaster.listing ASC  ";
        
        /*String query = "select s from Surveyprojectattribute s where s.name"
                + " = :projectId and s.attributeMaster.isactive = true and s.attributeMaster.masterattribute=true "
                + "ORDER BY s.attributecategoryid ASC, s.attributeorder ASC  ";*/
        
        List<AttributeMaster> result = new ArrayList<>();
        try{
        @SuppressWarnings("unchecked")
        List<Surveyprojectattribute> surveyAttributes = getEntityManager()
                .createQuery(query).setParameter("projectId", projectId)
                .getResultList();
        
        if (surveyAttributes != null && surveyAttributes.size() > 0) {
            for(Surveyprojectattribute sAttribute : surveyAttributes){
                // Override order
                //sAttribute.getAttributeMaster().setListing(sAttribute.getAttributeorder().toString());
                result.add(sAttribute.getAttributeMaster());
            }
            return result;
        } 
        }catch(Exception e){
        	e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<AttributeValues> getSurveyAttributeValues(String projectId) {
        String query = "select s.attributes from Surveyprojectattribute s where s.name = :projectId";

        @SuppressWarnings("unchecked")
        List<AttributeValues> attributeValues = getEntityManager()
                .createQuery(query).setParameter("projectId", projectId)
                .getResultList();

        if (!attributeValues.isEmpty()) {
            return attributeValues;
        }

        return null;
    }

    @Override
    public Surveyprojectattribute getSurveyProjectAttributeId(long attributeId,
            String projectId) {
        String query = "select s from Surveyprojectattribute s where s.attributeMaster.id = :attributeId and s.name.projectnameid = :projectId";
try{
        @SuppressWarnings("unchecked")
        List<Surveyprojectattribute> surveyprojectattributes = getEntityManager()
                .createQuery(query).setParameter("attributeId", attributeId)
                .setParameter("projectId", Integer.parseInt(projectId)).getResultList();

        if (surveyprojectattributes != null && surveyprojectattributes.size() > 0) {
            return surveyprojectattributes.get(0);
        }
}catch(Exception e){
	e.printStackTrace();
}
        return null;
    }

    @Override
    public List<Surveyprojectattribute> getSurveyProjectAttributes(String projectId) {
    	Integer id=Integer.parseInt(projectId);
        String query = "select s from Surveyprojectattribute s where s.name.projectnameid = :projectId";
        return getEntityManager().createQuery(query).setParameter("projectId", id).getResultList();
    }

    // add by RMSI NK for save up and down project attribute start
    @Override
    public boolean updatesurveyProject(
            Surveyprojectattribute surveyprojectattribute) {

     // @  long Id = surveyprojectattribute.getAttributeMaster().getAttributemasterid();
        String name = surveyprojectattribute.getName().getName();
        surveyprojectattribute.getAttributecategoryid();
        Integer order = surveyprojectattribute.getAttributeorder();

        try {
            String query = "UPDATE Surveyprojectattribute s SET s.attributeorder = :order  where s.name = :name and s.attributeMaster.id = :attributeMaster";

            int row = getEntityManager().createQuery(query)
                   //@ .setParameter("attributeMaster", Id)
                    .setParameter("name", name).setParameter("order", order)
                    .executeUpdate();

            if (row > 0) {

                return true;
            } else {

                return false;
            }
        } catch (Exception e) {// TODO Auto-generated catch block
            logger.error(e);
            return false;
        }

    }

    // add by RMSI NK for save up and down project attribute start
    @Override
    public boolean surveyAttributesByName(long id, Integer name,
            Integer attributeorder, Long attributecategory) {
    	
        try {
            String query = "UPDATE Surveyprojectattribute s SET s.attributeorder = :order  where s.name.projectnameid = :name and s.attributeMaster.attributemasterid = :attributeMaster";

            int row = getEntityManager().createQuery(query)
                    .setParameter("attributeMaster", id)
                    .setParameter("name", name)
                    .setParameter("order", attributeorder).executeUpdate();

            if (row > 0) {

                return true;
            } else {

                return false;
            }
        } catch (Exception e) {// TODO Auto-generated catch block
            logger.error(e);
            return false;
        }
    }
    // add by RMSI NK for save up and down project attribute end

    @Override
    public boolean deleteMappedAttribute(List<Long> uids) {
        try {
            String query = "DELETE from Surveyprojectattribute s where s.uid in (:uids)";

            getEntityManager().createQuery(query).setParameter("uids", uids).executeUpdate();
        } catch (Exception e) {
            logger.error(e);
            return false;
        }

        return true;
    }

    @Override
    public List<String> findnaturalCustom(String project) {

        List<String> customlst = new ArrayList<String>();
        try {
            String query = "select s from Surveyprojectattribute s where s.name.name = :projectId and s.attributecategoryid=2";

            @SuppressWarnings("unchecked")
            List<Surveyprojectattribute> surveyprojectattributes = getEntityManager()
                    .createQuery(query)
                    .setParameter("projectId", project).getResultList();

            if (!surveyprojectattributes.isEmpty()) {
                for (int i = 0; i < surveyprojectattributes.size(); i++) {
                    Surveyprojectattribute lstobj = surveyprojectattributes.get(i);
                    if (true) {
                       //@ customlst.add(lstobj.getAttributeMaster().getFieldaliasname());
                        customlst.add(lstobj.getUid().toString());
                        //@customlst.add(String.valueOf(lstobj.getAttributeMaster().getLaExtAttributedatatype().getDatatypeId()));

                    }
                }
                return customlst;
            }
        } catch (Exception e) {
            logger.error(e);
        }
        return customlst;

    }

}
