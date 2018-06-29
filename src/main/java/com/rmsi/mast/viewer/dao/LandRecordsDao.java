package com.rmsi.mast.viewer.dao;

import java.util.List;

import com.rmsi.mast.studio.dao.GenericDAO;
import com.rmsi.mast.studio.domain.LaSpatialunitLand;
import com.rmsi.mast.studio.domain.NaturalPerson;
import com.rmsi.mast.studio.domain.SpatialUnit;
import com.rmsi.mast.studio.domain.fetch.ClaimProfile;
import com.rmsi.mast.studio.domain.fetch.ClaimSummary;
import com.rmsi.mast.studio.domain.fetch.DataCorrectionReport;
import com.rmsi.mast.studio.domain.fetch.FarmReport;
import com.rmsi.mast.studio.domain.fetch.LeaseHistoryForFetch;
import com.rmsi.mast.studio.domain.fetch.MortageHistoryForFetch;
import com.rmsi.mast.studio.domain.fetch.NaturalPersonBasic;
import com.rmsi.mast.studio.domain.fetch.OwnerHistoryForFetch;
import com.rmsi.mast.studio.domain.fetch.PersonForEditing;
import com.rmsi.mast.studio.domain.fetch.PersonsReport;
import com.rmsi.mast.studio.domain.fetch.PoiReport;
import com.rmsi.mast.studio.domain.fetch.ProjectDetails;
import com.rmsi.mast.studio.domain.fetch.RegistryBook;
import com.rmsi.mast.studio.domain.fetch.ReportCertificateFetch;
import com.rmsi.mast.studio.domain.fetch.SpatialUnitBasic;
import com.rmsi.mast.studio.domain.fetch.SpatialUnitGeom;
import com.rmsi.mast.studio.domain.fetch.SpatialUnitTable;
import com.rmsi.mast.studio.domain.fetch.TransactionHistoryForFetch;
import com.rmsi.mast.studio.domain.fetch.UploadedDocumentDetailsForFetch;

import org.springframework.transaction.annotation.Transactional;

public interface LandRecordsDao extends GenericDAO<SpatialUnitTable, Long> {

    List<SpatialUnitTable> findallspatialUnit(String defaultProject);

    boolean updateApprove(Long id);

    boolean rejectStatus(Long id);

    List<SpatialUnitTable> search(String usinStr, String ukaNumber, String projname, String dateto, String datefrom,
            Long status, String claimType, Integer startpos);

    List<SpatialUnitTable> findSpatialUnitById(Long id);

    String findBiggestUkaNumber(String ukaPrefix);

    SpatialUnit getParcelGeometry(long usin);

    SpatialUnitTable getSpatialUnit(Long id);

    String findukaNumberByUsin(Long id);

    boolean updateFinal(Long id);

    boolean updateAdjudicated(Long id);

    boolean deleteSpatial(Long id);

    Integer searchSize(String usinStr, String ukaNumber, String projname,
            String dateto, String datefrom, Long status, String claimType);

    List<SpatialUnitTable> getSpatialUnitByBbox(String bbox, String project_name);

    boolean findExistingHamlet(long hamlet_id);

    boolean deleteAllVertexLabel();

    boolean addAllVertexLabel(int k, String lat, String lon);

    /** Returns list of claim summary based on usin, project name, status, results rage, and claim type */
    List<ClaimSummary> getClaimsSummary(Long usin, int startRecord, int endRecord, String projectName, int statusId, String claimType);
    
    List<RegistryBook> getRegistryBook(String projectName, long usin);
    
    ClaimProfile getClaimsProfile(String projectName);
    
    ProjectDetails getProjectDetails(String projectName);
    
    List<PersonForEditing> getPersonsForEditing(String projectName, long usin, String firstName, String lastName, String middleName, String idNumber, Integer claimNumber, String neighbourN, String neighbourS, String neighbourE, String neighbourW);
    
    SpatialUnitBasic getSpatialUnitBasic(Long usin);
    
    NaturalPersonBasic getNaturalPersonBasic(Long id);
    
    @Transactional
    PersonForEditing updatePersonForEditing(PersonForEditing pfe) throws Exception;
    
    @Transactional
    NaturalPerson updateNaturalPersonDataForEdit(NaturalPerson np) throws Exception;
    
    
    List<LaSpatialunitLand> findOrderedSpatialUnitRegistry(String defaultProject,int startfrom);
    
    List<LaSpatialunitLand> search(Long status, Integer claimType, String project,String communeId,String transId,String parcelId,Integer startpos);
    
	List<ReportCertificateFetch> getCertificatedetailsbytransactionid(Long usin);

	List<Object> findsummaryreport(String project);

	List<Object> findprojectdetailedsummaryreport(String project);
	List<Object> findprojectapplicationstatussummaryreport(String project);
	List<Object> findprojectapplicationtypesummaryreport(String project);
	List<Object> findprojectdetailedsummaryreportForCommune(String communeid);
	List<Object> findprojectworkflowsummaryreport(String project);
	List<ReportCertificateFetch> getCertificatedetailsinbatch(Long startRecord,Long endRecord);   
	List<Object> findprojectTenureTypesLandUnitsummaryreport(String project);
	Integer getTotalrecordByProject(String project);
	
	Integer searchCount(Long status, Integer claimType,String project,String communeId,String transId,String parcelId);
	 
	 Integer spatialUnitWorkflowCount(int[] workflow_ids,int[] claim_ids,int[] status_ids, String project);
	    
	List<LaSpatialunitLand> getspatialUnitWorkFlowResult(int[] workflow_ids,int[] claim_ids,int[] status_ids, Integer startfrom, String project);

	List<OwnerHistoryForFetch> getownerhistorydetails(Long landid);

	String checkruntopologychecks(String projectName);

	List<LeaseHistoryForFetch> getleasehistorydetails(Long landid);

	List<MortageHistoryForFetch> getmortagagedetails(Long landid);

	List<TransactionHistoryForFetch> gettransactiondetails(Long landid);
	 
	List<LeaseHistoryForFetch> findleasedetailbylandid(Long transactionid,Long landid);
	List<LeaseHistoryForFetch> findsurrenderleasedetailbylandid(Long transactionid,Long landid);
	List<MortageHistoryForFetch> findmortagagedetailbylandid(Long transactionid,Long landid);
	List<MortageHistoryForFetch> findSurrendermortagagedetailbylandid(Long transactionid,Long landid);
	List<UploadedDocumentDetailsForFetch>  viewdocumentdetailbytransactioid(Long transactionid);
	
	List<DataCorrectionReport> getDataCorrectionReport(Long transactionid,Long landId);
	public List<PoiReport> getDataCorrectionReportPOI(Long transactionid,Long landId);
	List<PersonsReport> getDataCorrectionPersonsReport(Long transactionid,Long landId);
	public List<FarmReport> getFarmReportByLandId(Long landId);

	List<Object> findLiberiaFarmummaryreport(String project);
}
