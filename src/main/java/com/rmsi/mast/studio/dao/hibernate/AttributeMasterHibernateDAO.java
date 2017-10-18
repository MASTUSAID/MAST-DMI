package com.rmsi.mast.studio.dao.hibernate;

import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.AttributeMasterDAO;
import com.rmsi.mast.studio.domain.AttributeMaster;
import com.rmsi.mast.studio.domain.fetch.AttributeValuesFetch;

@Repository
public class AttributeMasterHibernateDAO extends GenericHibernateDAO<AttributeMaster, Long>
        implements AttributeMasterDAO {

    private static final Logger logger = Logger.getLogger(AttributeMasterHibernateDAO.class);

    @Override
    public List<AttributeMaster> findAllAttributeMaster() {
        Query query = getEntityManager().createQuery("Select d from AttributeMaster d where d.active = true");
        List<AttributeMaster> definedAttribs = query.getResultList();

        if (definedAttribs.size() > 0) {
            return definedAttribs;
        } else {
            return null;
        }
    }

    @Override
    public boolean deleteEntry(Long id) {
        Query query = getEntityManager().createQuery("UPDATE AttributeMaster d SET d.active = false  where d.id = :id and d.active = true");

        query.setParameter("id", id);

        int rows = query.executeUpdate();

        if (rows > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<AttributeMaster> selectedCategory(Long id) {
        if (id == 0) {
            Query query = getEntityManager().createQuery("Select d from AttributeMaster d where d.active = true");
            List<AttributeMaster> definedAttribs = query.getResultList();

            if (definedAttribs.size() > 0) {
                return definedAttribs;
            } else {
                return null;
            }
        } else {
            Query query = getEntityManager().createQuery("Select c from AttributeMaster c where c.attributeCategory.attributecategoryid = :id and c.active=true");
            query.setParameter("id", id);
            List<AttributeMaster> selectedcategory = query.getResultList();

            if (selectedcategory.size() > 0) {
                return selectedcategory;
            } else {
                return null;
            }
        }

    }

    @Override
    public List<AttributeMaster> selectedList(Long uid) {
        Query query = getEntityManager().createQuery("Select c from AttributeMaster c where c.attributeCategory.attributecategoryid = :uid and c.active=true");
        query.setParameter("uid", uid);
        List<AttributeMaster> selectedcategory = query.getResultList();

        if (selectedcategory.size() > 0) {
            return selectedcategory;
        } else {
            return null;
        }

    }

    @Override
    public boolean duplicatevalidate(Long id, String alias, String fieldname) {
        try {
            Query query = getEntityManager().createQuery("Select c from AttributeMaster c where c.attributeCategory.attributecategoryid = :uid and ((lower(c.alias) =:alias or lower(c.fieldname) =:fieldname)and c.active=true)");

            query.setParameter("uid", id);
            query.setParameter("alias", alias.toLowerCase());
            query.setParameter("fieldname", fieldname.toLowerCase());

            List rows = query.getResultList();

            if (rows.size() > 0) {

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
    public List<AttributeMaster> findByAttributeId(Long id) {
        try {
            Query query = getEntityManager().createQuery("Select d from AttributeMaster d where d.id = :id");
            List<AttributeMaster> editAttrib = query.setParameter("id", id).getResultList();

            if (editAttrib.size() > 0) {
                return editAttrib;
            } else {
                return null;
            }
        } catch (Exception e) {

            logger.error(e);
            return null;
        }
    }

    @Override
    public boolean duplicateEditvalidate(long id, long categoryId, String alias,
            String fieldName) {
        try {
            Query query = getEntityManager().createQuery("Select c from AttributeMaster c where c.attributeCategory.attributecategoryid = :uid and ((lower(c.alias) =:alias or lower(c.fieldname) =:fieldname)and c.active=true) and c.id !=:id");

            query.setParameter("uid", categoryId);
            query.setParameter("alias", alias.toLowerCase());
            query.setParameter("fieldname", fieldName.toLowerCase());
            query.setParameter("id", id);

            List rows = query.getResultList();

            if (rows.size() > 0) {
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
    public List<AttributeMaster> findByCategoryId(Long id) {

        try {
            Query query = getEntityManager().createQuery("Select c from AttributeMaster c where c.attributeCategory.attributecategoryid = :uid and ( c.active=true and c.master_attrib=false)");

            query.setParameter("uid", id);

            List<AttributeMaster> rows = query.getResultList();

            if (rows.size() > 0) {
                return rows;
            } else {
                return null;
            }
        } catch (Exception e) {

            logger.error(e);
            return null;
        }
    }

    @Override
    public List<AttributeValuesFetch> fetchCustomAttribs(Long parentgid, int category) {
        try {
            String sql = null;
            if (category != 1) {
                sql = "select at.value,at.attributevalueid,am.alias,am.datatype_id as datatypeid from attribute at "
                        + "inner join surveyprojectattributes spa on spa.uid = at.uid "
                        + "inner join attribute_master am on  spa.id= am.id "
                        + "where am.master_attrib=false and at.parentuid = " + parentgid
                        + " and spa.attributecategoryid = " + category
                        + " order by spa.attributeorder";
            }

            if (category == 1) {
                sql = "select at.value,at.attributevalueid,am.alias,am.datatype_id as datatypeid from attribute at "
                        + "inner join surveyprojectattributes spa on spa.uid = at.uid "
                        + "inner join attribute_master am on  spa.id= am.id "
                        + "where am.master_attrib=false and at.parentuid = " + parentgid
                        + " and spa.attributecategoryid in(1,7)"
                        + " order by spa.attributeorder";

            }
            Query query = getEntityManager().createNativeQuery(sql, AttributeValuesFetch.class);
            List<AttributeValuesFetch> attribValues = query.getResultList();

            if (attribValues.size() > 0) {
                /*	for (int i = 0; i < attribValues.size(); i++) {
					if(attribValues.get(i).getDatatypeid()==2){
						String date = attribValues.get(i).getValue();
						SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MMM/yyyy");
						Date value=sdfDate.parse(date);
						SimpleDateFormat returnDate = new SimpleDateFormat("yyyy-MM-dd");
						date=returnDate.format(value);
						attribValues.get(i).setValue(date);
					}	
				}*/
                return attribValues;
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.error(e);
            return null;
        }
    }

}
