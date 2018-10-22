/**
 *
 */
package com.rmsi.mast.studio.mobile.dao.hibernate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.hibernate.GenericHibernateDAO;
import com.rmsi.mast.studio.domain.SpatialUnit;
import com.rmsi.mast.studio.domain.fetch.ClaimBasic;
import com.rmsi.mast.studio.domain.fetch.DisputeBasic;
import com.rmsi.mast.studio.domain.fetch.MediaBasic;
import com.rmsi.mast.studio.domain.fetch.RightBasic;
import com.rmsi.mast.studio.domain.fetch.SpatialUnitTable;
import com.rmsi.mast.studio.mobile.dao.SpatialUnitDao;

/**
 * @author Shruti.Thakur
 *
 */
@Repository
public class SpatialUnitHibernateDao extends
        GenericHibernateDAO<SpatialUnit, Long> implements SpatialUnitDao {

    private static final Logger logger = Logger
            .getLogger(SpatialUnitHibernateDao.class);

    @Override
    public SpatialUnit addSpatialUnit(SpatialUnit spatialUnit) {

        try {
            return makePersistent(spatialUnit);

        } catch (Exception ex) {
            System.out.println("Exception while adding data...." + ex);
            logger.error(ex);
            throw ex;
        }
    }

    @Override
    public List<SpatialUnit> getSpatialUnitByProject(String projectId) {
        String query = "select s from SpatialUnit s where s.project.name = :projectId";

        try {
            @SuppressWarnings("unchecked")
            List<SpatialUnit> spatialUnit = getEntityManager()
                    .createQuery(query).setParameter("projectId", projectId)
                    .getResultList();

            if (!spatialUnit.isEmpty()) {
                return spatialUnit;
            }
        } catch (Exception ex) {
            System.out
                    .println("Exception while fetching the data from data base "
                            + ex);
            logger.error(ex);
        }
        return null;
    }

    @Override
    public SpatialUnit findByImeiandTimeStamp(String imeiNumber, Date date) {

        String query = "select s from SpatialUnit s where s.Imei =:imeiNumber";

        try {
        	Integer id=Integer.parseInt(imeiNumber);
            @SuppressWarnings("unchecked")
            List<SpatialUnit> spatialUnit = getEntityManager()
                    .createQuery(query).setParameter("imeiNumber", imeiNumber).getResultList();

            if (spatialUnit.size() > 0) {
                return spatialUnit.get(0);
            }
        } catch (Exception ex) {
            System.out.println("Exception while fetching data: : : " + ex);
            logger.error(ex);
            throw ex;
        }
        return null;
    }

    @Override
    public ClaimBasic getSpatialUnitByUsin(long usin) {

        String query = "select s from ClaimBasic s where s.landid =:usin";

        try {

            @SuppressWarnings("unchecked")
            List<ClaimBasic> spatialUnits = getEntityManager()
                    .createQuery(query).setParameter("usin", usin)
                    .getResultList();

            if (spatialUnits.size() > 0) {
                return spatialUnits.get(0);
            }
        } catch (Exception e) {
            logger.error(e);
            throw e;
        }
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ClaimBasic> findSpatialUnitByStatusId(String projectId,
            int statusId) {
        ArrayList<Integer> staList = new ArrayList<Integer>();
        staList.add(6);
        staList.add(7);
        String query = "select su from ClaimBasic su where "
                + "su.landapplicationstatusid.workflowstatusid in :statusId and "
                + "su.projectnameid =:projectId";

        try {
            List<ClaimBasic> spatialUnitList = null;
            if (statusId != 7) {

                spatialUnitList = getEntityManager()
                        .createQuery(query).setParameter("statusId", statusId)
                        .setParameter("projectId", projectId).getResultList();

            } else {

                spatialUnitList = getEntityManager()
                        .createQuery(query).setParameter("statusId", staList)
                        .setParameter("projectId", projectId).getResultList();

            }
            if (spatialUnitList.size() > 0) {
                return spatialUnitList;
            }
        } catch (Exception ex) {
            logger.error(ex);
            System.out.println("Exception while fetching SPATIAL UNIT:::: " + ex);
        }
        return new ArrayList<ClaimBasic>();
    }

    @Override
    public List<ClaimBasic> getClaimsBasicByStatus(Integer projectId, int statusId) {
        ArrayList<Integer> staList = new ArrayList<>();
        staList.add(6);
        staList.add(7);
        String query = "select su from ClaimBasic su where "
                + "su.statusId in :statusId and "
                + "su.projectName =:projectId and s.active = true";

        try {
            List<ClaimBasic> claims = null;
            if (statusId != 7) {
                claims = getEntityManager()
                        .createQuery(query).setParameter("statusId", statusId)
                        .setParameter("projectId", projectId).getResultList();

            } else {
                claims = getEntityManager()
                        .createQuery(query).setParameter("statusId", staList)
                        .setParameter("projectId", projectId).getResultList();
            }
            if (claims.size() > 0) {
                return claims;
            }
        } catch (Exception ex) {
            logger.error(ex);
            System.out.println("Exception while fetching SPATIAL UNIT:::: " + ex);
        }
        return new ArrayList<ClaimBasic>();
    }

    @Override
    public List<ClaimBasic> getClaimsBasicByProject(Integer projectId) {
        String query = "select s from ClaimBasic s where s.projectnameid = :projectId and s.isactive = true";

        try {
            @SuppressWarnings("unchecked")
            List<ClaimBasic> claims = getEntityManager()
                    .createQuery(query).setParameter("projectId", projectId)
                    .getResultList();

            if (!claims.isEmpty()) {
                return claims;
            }
        } catch (Exception ex) {
            System.out.println("Exception while fetching the data from data base " + ex);
            logger.error(ex);
        }
        return null;
    }

	@Override
	public List<ClaimBasic> getClaimsBasicByLandId(Long landid) {
		
		  String query = "select s from ClaimBasic s where s.landid = :landid and s.isactive = true";

	        try {
	            @SuppressWarnings("unchecked")
	            List<ClaimBasic> claims = getEntityManager()
	                    .createQuery(query).setParameter("landid", landid)
	                    .getResultList();

	            if (!claims.isEmpty()) {
	                return claims;
	            }
	        } catch (Exception ex) {
	            System.out.println("Exception while fetching the data from data base " + ex);
	            logger.error(ex);
	        }
	        return null;
	        
	}


}
