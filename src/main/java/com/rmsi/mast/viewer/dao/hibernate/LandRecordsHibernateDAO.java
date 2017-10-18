package com.rmsi.mast.viewer.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.hibernate.GenericHibernateDAO;
import com.rmsi.mast.studio.domain.Status;
import com.rmsi.mast.studio.domain.fetch.CcroOccurrenceStat;
import com.rmsi.mast.studio.domain.fetch.ClaimProfile;
import com.rmsi.mast.studio.domain.fetch.ClaimSummary;
import com.rmsi.mast.studio.domain.fetch.ClaimantAgeStat;
import com.rmsi.mast.studio.domain.fetch.ClaimsStat;
import com.rmsi.mast.studio.domain.fetch.DisputeStat;
import com.rmsi.mast.studio.domain.fetch.NaturalPersonBasic;
import com.rmsi.mast.studio.domain.fetch.OwnershipTypeStat;
import com.rmsi.mast.studio.domain.fetch.PersonForEditing;
import com.rmsi.mast.studio.domain.fetch.ProjectDetails;
import com.rmsi.mast.studio.domain.fetch.RegistryBook;
import com.rmsi.mast.studio.domain.fetch.SpatialUnitBasic;
import com.rmsi.mast.studio.domain.fetch.SpatialUnitGeom;
import com.rmsi.mast.studio.domain.fetch.SpatialUnitTable;
import com.rmsi.mast.studio.util.ClaimsSorter;
import com.rmsi.mast.studio.util.DateUtils;
import com.rmsi.mast.studio.util.StringUtils;
import com.rmsi.mast.viewer.dao.LandRecordsDao;
import java.util.Collections;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

@Repository
public class LandRecordsHibernateDAO extends GenericHibernateDAO<SpatialUnitTable, Long>
        implements LandRecordsDao {

    private static final Logger logger = Logger.getLogger(LandRecordsHibernateDAO.class);

    @SuppressWarnings("unchecked")
    @Override
    public List<SpatialUnitTable> findallspatialUnit(String defaultProject) {

        try {
            Query query = getEntityManager().createQuery("Select su from SpatialUnitTable su where su.project = :project_name and su.active=true order by su.usin desc");
            List<SpatialUnitTable> spatialUnit = query.setParameter("project_name", defaultProject).getResultList();

            if (spatialUnit.size() > 0) {
                return spatialUnit;
            } else {
                return null;
            }
        } catch (Exception e) {

            logger.error(e);
            return null;
        }

    }

    @Override
    public boolean updateApprove(Long id) {

        try {
            Query query = getEntityManager().createQuery("UPDATE SpatialUnitTable su SET su.status.workflowStatusId = :statusId  where su.usin = :usin");
            int updateFinal = query.setParameter("statusId", Status.STATUS_APPROVED).setParameter("usin", id).executeUpdate();

            if (updateFinal > 0) {
                return true;
            }

        } catch (Exception e) {

            logger.error(e);
            return false;
        }

        return false;

    }

    @Override
    public PersonForEditing updatePersonForEditing(PersonForEditing pfe) throws Exception {
        // Check for claim to be editable
        SpatialUnitBasic su = getSpatialUnitBasic(pfe.getUsin());
        NaturalPersonBasic person = getNaturalPersonBasic(pfe.getPersonId());

        if (su == null) {
            throw new Exception("Claim was not found");
        }
        if (person == null) {
            throw new Exception("Person was not found");
        }
        if (su.getStatus() != Status.STATUS_NEW && su.getStatus() != Status.STATUS_VALIDATED && su.getStatus() != Status.STATUS_REFERRED) {
            throw new Exception("Record cannot be modified because of underlaying claim status");
        }

        // Calculate age
        if (pfe.getDob() != null) {
            pfe.setAge(DateUtils.getAge(pfe.getDob()));
        } else {
            pfe.setAge(null);
        }

        // Update claim
        if (StringUtils.isNotEmpty(su.getPropertyno())) {
            pfe.setHamletId(su.getHamletId());
        } else {
            su.setHamletId(pfe.getHamletId());
        }

        su.setSurveyDate(pfe.getClaimDate());
        su.setNeighbor_north(pfe.getNeighborNorth());
        su.setNeighbor_south(pfe.getNeighborSouth());
        su.setNeighbor_east(pfe.getNeighborEast());
        su.setNeighbor_west(pfe.getNeighborWest());

        getEntityManager().merge(su);
        
        // Update person
        person.setFirstName(pfe.getFirstName());
        person.setLastName(pfe.getLastName());
        person.setMiddleName(pfe.getMiddleName());
        person.setIdType(pfe.getIdType());
        person.setIdNumber(pfe.getIdNumber());
        person.setDob(pfe.getDob());
        person.setAge(pfe.getAge());
        person.setGender(pfe.getGender());
        person.setMaritalStatus(pfe.getMaritalStatus());
        
        getEntityManager().merge(person);

        return pfe;
    }

    @Override
    public boolean rejectStatus(Long id) {

        try {
            int finalstatus = 5;
            Query query = getEntityManager().createQuery("UPDATE SpatialUnitTable su SET su.status.workflowStatusId = :statusId  where su.usin = :usin");
            int rejectStatus = query.setParameter("statusId", finalstatus).setParameter("usin", id).executeUpdate();

            if (rejectStatus > 0) {
                return true;
            }
        } catch (Exception e) {

            logger.error(e);
            return false;
        }

        return false;

    }

    @Override
    public List<SpatialUnitTable> search(String usinStr, String ukaNumber,
            String projname, String dateto, String datefrom,
            Long status, String claimType, Integer startpos) {

        ArrayList<Long> newUsin = new ArrayList<>();
        try {
            StringBuilder queryStr = new StringBuilder("Select su from SpatialUnitTable su where su.project = :project_name and su.active=true ");
            if (!"".equals(ukaNumber)) {
                queryStr.append("and su.propertyno like :propertyno ");
            }
            if (!"".equals(usinStr)) {
                queryStr.append("and su.usin in :usin ");
            }
            if (!dateto.isEmpty() || !datefrom.isEmpty()) {
                queryStr.append("and (str(su.surveyDate) BETWEEN :stDate AND :edDate) ");
            }
            if (claimType != null && claimType.length() > 0) {
                queryStr.append("and su.claimType.code=:claimType ");
            }
            if (status != 0) {
                queryStr.append("and su.status.workflowStatusId=:workflowStatusId ");
            }

            queryStr.append("order by su.usin desc ");

            Query query = getEntityManager().createQuery(queryStr.toString());
            query.setParameter("project_name", projname);

            if (!"".equals(ukaNumber)) {
                query.setParameter("propertyno", "%" + ukaNumber.trim() + "%");
            }
            if (!"".equals(usinStr)) {
                for (String retval : usinStr.split(",")) {
                    newUsin.add(Long.parseLong(retval.trim()));
                }
                query.setParameter("usin", newUsin);
            }
            if (!dateto.isEmpty() || !datefrom.isEmpty()) {
                query.setParameter("stDate", datefrom).setParameter("edDate", dateto);
            }
            if (status != 0) {
                query.setParameter("workflowStatusId", status.intValue());
            }
            if (claimType != null && claimType.length() > 0) {
                query.setParameter("claimType", claimType);
            }

            @SuppressWarnings("unchecked")
            List<SpatialUnitTable> spatialUnit = query.setFirstResult(startpos).setMaxResults(20).getResultList();
            if (spatialUnit.size() > 0) {
                return spatialUnit;
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.error(e);
            return null;
        }
    }

    @Override
    public List<SpatialUnitTable> findSpatialUnitById(Long id) {
        try {
            Query query = getEntityManager().createQuery("Select su from SpatialUnitTable su where su.usin = :usin and su.active = true");
            @SuppressWarnings("unchecked")
            List<SpatialUnitTable> spatialUnitlst = query.setParameter("usin", id).getResultList();

            if (spatialUnitlst.size() > 0) {
                return spatialUnitlst;
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.error(e);
            return null;
        }
    }

    @Override
    public List<ClaimSummary> getClaimsSummary(Long usin, int startRecord, int endRecord, String projectName, int statusId, String claimType) {
        try {
            Query query = getEntityManager().createQuery("Select cs from ClaimSummary cs where "
                    + "(cs.usin = :usin or :usin = 0) "
                    + "and cs.projectName = :projectName "
                    + "and (:statusId = 0 or cs.statusId = :statusId) "
                    + "and cs.claimType = :claimType");

            List<ClaimSummary> claims
                    = query.setParameter("usin", usin)
                    .setParameter("projectName", projectName)
                    .setParameter("claimType", claimType)
                    .setParameter("statusId", statusId)
                    .getResultList();

            if (claims != null && claims.size() > 0) {
                if (claims.size() > 1) {
                    // Sort by owner name, assuming that list already sorted by hamlets
                    Collections.sort(claims, new ClaimsSorter());
                }

                if (startRecord > endRecord) {
                    int tmpNumber = endRecord;
                    endRecord = startRecord;
                    startRecord = tmpNumber;
                }

                if (startRecord > 1 || endRecord < claims.size()) {
                    // return requested range
                    if (startRecord > claims.size()) {
                        return null;
                    }

                    startRecord = startRecord - 1;

                    if (startRecord < 0) {
                        startRecord = 0;
                    }

                    if (endRecord > claims.size()) {
                        endRecord = claims.size();
                    }

                    return claims.subList(startRecord, endRecord);
                }
            }
            return claims;
        } catch (Exception e) {
            logger.error(e);
            return null;
        }
    }

    @Override
    public List<RegistryBook> getRegistryBook(String projectName, long usin) {
        try {
            Query query = getEntityManager().createQuery("Select rb from RegistryBook rb where rb.projectName = :projectName and (:usin = 0L or rb.usin = :usin)");
            return query.setParameter("projectName", projectName).setParameter("usin", usin).getResultList();
        } catch (Exception e) {
            logger.error(e);
            return null;
        }
    }

    @Override
    public List<PersonForEditing> getPersonsForEditing(String projectName,
            long usin,
            String firstName,
            String lastName,
            String middleName,
            String idNumber,
            String claimNumber,
            String neighbourN,
            String neighbourS,
            String neighbourE,
            String neighbourW) {
        try {
            Query query = getEntityManager().createQuery("Select p from PersonForEditing p where p.projectName = :projectName and "
                    + "(p.usin = :usin or :usin = 0) and "
                    + "(lower(trim(p.firstName)) like :firstName or p.firstName is null) and "
                    + "(lower(trim(p.lastName)) like :lastName or p.lastName is null) and "
                    + "(lower(trim(p.middleName)) like :middleName or p.middleName is null) and "
                    + "(lower(trim(p.idNumber)) like :idNumber or p.idNumber is null) and "
                    + "(lower(trim(p.claimNumber)) like :claimNumber or p.claimNumber is null) and "
                    + "(lower(trim(p.neighborNorth)) like :neighbourN or p.neighborNorth is null) and "
                    + "(lower(trim(p.neighborSouth)) like :neighbourS or p.neighborSouth is null) and "
                    + "(lower(trim(p.neighborEast)) like :neighbourE or p.neighborEast is null) and "
                    + "(lower(trim(p.neighborWest)) like :neighbourW or p.neighborWest is null)");

            List<PersonForEditing> result = query.setParameter("projectName", projectName)
                    .setParameter("usin", usin)
                    .setParameter("firstName", StringUtils.empty(firstName).toLowerCase() + "%")
                    .setParameter("lastName", StringUtils.empty(lastName).toLowerCase() + "%")
                    .setParameter("middleName", StringUtils.empty(middleName).toLowerCase() + "%")
                    .setParameter("idNumber", StringUtils.empty(idNumber).toLowerCase() + "%")
                    .setParameter("claimNumber", StringUtils.empty(claimNumber).toLowerCase() + "%")
                    .setParameter("neighbourN", StringUtils.empty(neighbourN).toLowerCase() + "%")
                    .setParameter("neighbourS", StringUtils.empty(neighbourS).toLowerCase() + "%")
                    .setParameter("neighbourE", StringUtils.empty(neighbourE).toLowerCase() + "%")
                    .setParameter("neighbourW", StringUtils.empty(neighbourW).toLowerCase() + "%")
                    .getResultList();
            return result;
        } catch (Exception e) {
            logger.error(e);
            return null;
        }
    }

    @Override
    public ProjectDetails getProjectDetails(String projectName) {
        try {
            Query query = getEntityManager().createQuery("Select p from ProjectDetails p where p.name = :projectName");
            return (ProjectDetails) query.setParameter("projectName", projectName).getSingleResult();
        } catch (Exception e) {
            logger.error(e);
            return null;
        }
    }

    @Override
    public ClaimProfile getClaimsProfile(String projectName) {
        try {
            ClaimProfile profile = new ClaimProfile();
            if (StringUtils.empty(projectName).equalsIgnoreCase("ALL")) {
                projectName = "";
            }

            // Get claims stat
            StoredProcedureQuery query = getEntityManager().createStoredProcedureQuery("get_claims_stat", ClaimsStat.class)
                    .registerStoredProcedureParameter("projectName", String.class, ParameterMode.IN)
                    .setParameter("projectName", projectName);
            profile.setClaimsStatList(query.getResultList());

            // Get unique claimants
            query = getEntityManager().createStoredProcedureQuery("get_unique_claimants")
                    .registerStoredProcedureParameter("projectName", String.class, ParameterMode.IN)
                    .setParameter("projectName", projectName);
            Object[] result = (Object[]) query.getSingleResult();

            if (result != null && result[0] != null) {
                profile.setUniqueMales((int) result[0]);
            }
            if (result != null && result[1] != null) {
                profile.setUniqueFemales((int) result[1]);
            }

            // Get unique claimants with denied CCRO
            query = getEntityManager().createStoredProcedureQuery("get_unique_claimants_denied")
                    .registerStoredProcedureParameter("projectName", String.class, ParameterMode.IN)
                    .setParameter("projectName", projectName);
            result = (Object[]) query.getSingleResult();

            if (result != null && result[0] != null) {
                profile.setUniqueMalesDenied((int) result[0]);
            }
            if (result != null && result[1] != null) {
                profile.setUniqueFemalesDenied((int) result[1]);
            }

            // Get ownership types stat
            query = getEntityManager().createStoredProcedureQuery("get_ownership_types_approved", OwnershipTypeStat.class)
                    .registerStoredProcedureParameter("projectName", String.class, ParameterMode.IN)
                    .setParameter("projectName", projectName);
            profile.setOwnershipTypeStatList(query.getResultList());

            // Get ccro occurrence stat
            query = getEntityManager().createStoredProcedureQuery("get_ccro_occurrence", CcroOccurrenceStat.class)
                    .registerStoredProcedureParameter("projectName", String.class, ParameterMode.IN)
                    .setParameter("projectName", projectName);
            profile.setCcroOccurrenceStatList(query.getResultList());
            
            // Get claimants age stat
            query = getEntityManager().createStoredProcedureQuery("get_unique_claimants_with_age", ClaimantAgeStat.class)
                    .registerStoredProcedureParameter("projectName", String.class, ParameterMode.IN)
                    .setParameter("projectName", projectName);
            profile.setClaimantsAgeList(query.getResultList());
            
            // Get disputes stat
            query = getEntityManager().createStoredProcedureQuery("get_disputes_stat", DisputeStat.class)
                    .registerStoredProcedureParameter("projectName", String.class, ParameterMode.IN)
                    .setParameter("projectName", projectName);
            profile.setDisputes(query.getResultList());

            return profile;
        } catch (Exception e) {
            logger.error(e);
            return null;
        }
    }

    @Override
    public String findBiggestUkaNumber(String ukaPrefix) {
        try {
            Query query = getEntityManager().createQuery("Select max(su.propertyno) from SpatialUnitTable su where su.propertyno like :ukaPrefix");
            return (String) query.setParameter("ukaPrefix", ukaPrefix + "%").getSingleResult();
        } catch (Exception e) {
            logger.error(e);
            return null;
        }
    }

    @Override
    public SpatialUnitGeom getParcelGeometry(long usin) {
        try {
            Query query = getEntityManager().createQuery("Select su from SpatialUnitGeom su where su.usin = :usin");
            return (SpatialUnitGeom) query.setParameter("usin", usin).getSingleResult();
        } catch (Exception e) {
            logger.error(e);
            return null;
        }
    }

    @Override
    public SpatialUnitBasic getSpatialUnitBasic(Long usin) {
        try {
            Query query = getEntityManager().createQuery("Select su from SpatialUnitBasic su where su.usin = :usin");
            @SuppressWarnings("unchecked")
            SpatialUnitBasic result = (SpatialUnitBasic) query.setParameter("usin", usin).getSingleResult();
            return result;
        } catch (Exception e) {
            logger.error(e);
            return null;
        }
    }

    @Override
    public NaturalPersonBasic getNaturalPersonBasic(Long id) {
        try {
            Query query = getEntityManager().createQuery("Select p from NaturalPersonBasic p where p.personGid = :id");
            @SuppressWarnings("unchecked")
            NaturalPersonBasic result = (NaturalPersonBasic) query.setParameter("id", id).getSingleResult();
            return result;
        } catch (Exception e) {
            logger.error(e);
            return null;
        }
    }

    @Override
    public SpatialUnitTable getSpatialUnit(Long id) {
        try {
            Query query = getEntityManager().createQuery("Select su from SpatialUnitTable su where su.usin = :usin and su.active = true");
            @SuppressWarnings("unchecked")
            List<SpatialUnitTable> spatialUnitlst = query.setParameter("usin", id).getResultList();

            if (spatialUnitlst.size() > 0) {
                return spatialUnitlst.get(0);
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.error(e);
            return null;
        }
    }

    @Override
    public String findukaNumberByUsin(Long id) {

        try {
            Query query = getEntityManager().createQuery("Select su from SpatialUnitTable su where su.usin = :usin and su.active = true");
            @SuppressWarnings("unchecked")
            List<SpatialUnitTable> spatialUnitlst = query.setParameter("usin", id).getResultList();
            String uka = spatialUnitlst.get(0).getPropertyno();

            if (spatialUnitlst.size() > 0) {
                return uka;
            } else {
                return "";
            }
        } catch (Exception e) {

            logger.error(e);
            return "";
        }

    }

    @Override
    public boolean updateFinal(Long id) {

        try {
            int finalstatus = 7;
            Query query = getEntityManager().createQuery("UPDATE SpatialUnitTable su SET su.status.workflowStatusId = :statusId  where su.usin = :usin");
            int updateFinal = query.setParameter("statusId", finalstatus).setParameter("usin", id).executeUpdate();

            if (updateFinal > 0) {
                return true;
            }
        } catch (Exception e) {

            logger.error(e);
            return false;
        }

        return false;

    }

    @Override
    public boolean updateAdjudicated(Long id) {

        try {
            int finalstatus = 2;
            Query query = getEntityManager().createQuery("UPDATE SpatialUnitTable su SET su.status.workflowStatusId = :statusId  where su.usin = :usin");
            int updateFinal = query.setParameter("statusId", finalstatus).setParameter("usin", id).executeUpdate();

            if (updateFinal > 0) {
                return true;
            }
        } catch (Exception e) {

            logger.error(e);
            return false;
        }

        return false;

    }

    @Override
    public boolean deleteSpatial(Long id) {

        try {
            Query query = getEntityManager().createQuery("UPDATE SpatialUnitTable su SET su.active = false  where su.usin = :usin");
            int updateFinal = query.setParameter("usin", id).executeUpdate();

            if (updateFinal > 0) {
                return true;
            }
        } catch (Exception e) {

            logger.error(e);
            return false;
        }
        return false;
    }

    @Override
    public Integer searchSize(String usinStr, String ukaNumber,
            String projname, String dateto, String datefrom, Long status, String claimType) {

        Integer count = 0;

        ArrayList<Long> newUsin = new ArrayList<Long>();
        try {
            // and (str(su.surveyDate) BETWEEN :stDate AND :edDate) and su.project = :project_name ")
            StringBuffer queryStr = new StringBuffer("Select count(*) from SpatialUnitTable su where su.project = :project_name and su.active=true ");
            if (ukaNumber != "") {
                queryStr.append("and su.propertyno like :propertyno ");
            }
            if (usinStr != "") {

                queryStr.append("and su.usin in :usin ");

            }
            if (!dateto.isEmpty() || !datefrom.isEmpty()) {
                queryStr.append("and (str(su.surveyDate) BETWEEN :stDate AND :edDate) ");
            }
            if (status != 0) {
                queryStr.append("and su.status.workflowStatusId=:workflowStatusId ");
            }
            if (claimType != null && claimType.length() > 0) {
                queryStr.append("and su.claimType.code=:claimType ");
            }

            Query query = getEntityManager().createQuery(queryStr.toString());
            query.setParameter("project_name", projname);

            if (ukaNumber != "") {
                query.setParameter("propertyno", "%" + ukaNumber + "%");
            }
            if (usinStr != "") {
                for (String retval : usinStr.split(",")) {
                    newUsin.add(Long.parseLong(retval));
                }
                query.setParameter("usin", newUsin);
            }
            if (!dateto.isEmpty() || !datefrom.isEmpty()) {
                query.setParameter("stDate", datefrom).setParameter("edDate", dateto);
            }
            if (status != 0) {
                query.setParameter("workflowStatusId", status.intValue());
            }
            if (claimType != null && claimType.length() > 0) {
                query.setParameter("claimType", claimType);
            }

            @SuppressWarnings("unchecked")
            List<?> spatialUnit = query.getResultList();
            if (spatialUnit.size() > 0) {
                count = Integer.valueOf(spatialUnit.get(0).toString());
            }

        } catch (Exception e) {

            logger.error(e);

        }

        return count;

    }

    @Override
    public List<SpatialUnitTable> getSpatialUnitByBbox(String bbox, String project_name) {

        List<SpatialUnitTable> spatialUnit = new ArrayList<SpatialUnitTable>();
        try {

            Query query = getEntityManager().createNativeQuery("SELECT * from spatial_unit where ST_WITHIN(the_geom, ST_MakeEnvelope(" + bbox + ",4326)) and (project_name=" + "'" + project_name + "'" + " and active=true) ", SpatialUnitTable.class);
            spatialUnit = query.getResultList();
        } catch (Exception e) {
            logger.error(e);
        }

        return spatialUnit;

    }

    @Override
    public boolean findExistingHamlet(long hamlet_id) {
        try {
            Query query = getEntityManager().createQuery("Select su from SpatialUnitTable su where su.hamlet_Id.id = :hamlet_id");
            @SuppressWarnings("unchecked")
            List<SpatialUnitTable> spatialUnitlst = query.setParameter("hamlet_id", hamlet_id).getResultList();
            if (spatialUnitlst.size() > 0) {
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
    public boolean deleteAllVertexLabel() {

        try {
            Query query = getEntityManager().createNativeQuery("DELETE FROM vertexlabel");
            int spatialUnit = query.executeUpdate();

            if (spatialUnit > 0) {
                return true;
            } else {
                return true;
            }
        } catch (Exception e) {

            logger.error(e);
            return false;
        }
    }

    @Override
    public boolean addAllVertexLabel(int k, String lat, String lon) {
        try {
            Query query = getEntityManager().createNativeQuery("insert into vertexlabel(gid,the_geom) values(" + k + ",ST_SetSRID(ST_MakePoint(" + lon + "," + lat + "), 4326));");
            int spatialUnit = query.executeUpdate();

            if (spatialUnit > 0) {
                return true;
            } else {
                return true;
            }
        } catch (Exception e) {

            logger.error(e);
            return false;
        }
    }

}
