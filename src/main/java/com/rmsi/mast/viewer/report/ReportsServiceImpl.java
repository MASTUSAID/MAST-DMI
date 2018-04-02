package com.rmsi.mast.viewer.report;

import com.rmsi.mast.studio.domain.NaturalPerson;
import com.rmsi.mast.studio.domain.NonNaturalPerson;
import com.rmsi.mast.studio.domain.PersonType;
import com.rmsi.mast.studio.domain.SocialTenureRelationship;
import com.rmsi.mast.studio.domain.Status;
import com.rmsi.mast.studio.domain.fetch.ClaimProfile;
import com.rmsi.mast.studio.domain.fetch.ClaimSummary;
import com.rmsi.mast.studio.domain.fetch.ProjectDetails;
import com.rmsi.mast.studio.domain.fetch.RegistryBook;
import com.rmsi.mast.studio.domain.fetch.ReportCertificateFetch;
import com.rmsi.mast.studio.domain.fetch.SpatialUnitTable;
import com.rmsi.mast.studio.util.StringUtils;
import com.rmsi.mast.viewer.service.LandRecordsService;

import java.net.URL;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This class contains methods to generate various reports
 */
@Service
public class ReportsServiceImpl implements ReportsSerivce {

    @Autowired
    private LandRecordsService landRecordsService;

    private static final Logger logger = Logger.getLogger(ReportsServiceImpl.class.getName());

    public ReportsServiceImpl() {
    }

    /**
     * Returns denial letter for claim
     *
     * @param usin Claim USIN number
     * @return
     */
    @Override
    public JasperPrint getDenialLetter(long usin) {
        try {
            List<SocialTenureRelationship> rights = landRecordsService.findAllSocialTenureByUsin(usin);
            SpatialUnitTable claim = landRecordsService.getSpatialUnit(usin);

            if (claim == null || rights == null || rights.size() < 1 || rights.get(0).getPersonlandid() == null) {
                return null;
            }

            ProjectDetails project = landRecordsService.getProjectDetails(claim.getProject());
            String village = project.getVillage();
            String hamlet = claim.getHamlet_Id().getHamletName();
            String claimantName;

//            if (rights.get(0).getLaPartygroupPersontype().getPersontypeid() == PersonType.TYPE_NATURAL) {
//                claimantName = getPersonName((NaturalPerson) rights.get(0).getPersonlandid());
//            } else {
//                NonNaturalPerson nonPerson = (NonNaturalPerson) rights.get(0).getPersonlandid();
//                if (nonPerson.getPoc_gid() != null && nonPerson.getPoc_gid() > 0) {
//                    claimantName = getPersonName((NaturalPerson) landRecordsService.findPersonGidById(nonPerson.getPoc_gid()));
//                } else {
//                    claimantName = StringUtils.empty(nonPerson.getInstitutionName());
//                }
//            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

            HashMap params = new HashMap();
//          params.put("CLAIMANT_NAME", claimantName);
            params.put("VILLAGE", village);
            params.put("HAMLET", hamlet);
            params.put("CLAIM_NUMBER", StringUtils.empty(claim.getClaimNumber()));
            params.put("CLAIM_DATE", dateFormat.format(claim.getSurveyDate()));

            Object[] beans = new Object[1];
            beans[0] = new Object();
            JRDataSource jds = new JRBeanArrayDataSource(beans);

            return JasperFillManager.fillReport(
                    ReportsServiceImpl.class.getResourceAsStream("/reports/DenialLetter.jasper"),
                    params, jds);
        } catch (Exception ex) {
            logger.error(ex);
            return null;
        }
    }

    @Override
    public JasperPrint getAdjudicationForms(String projectName, Long usin, int startRecord, int endRecord, String appUrl) {
        try {
            ProjectDetails project = landRecordsService.getProjectDetails(projectName);
            int statusId = Status.STATUS_VALIDATED;
            if (usin > 0) {
                // Any status if only 1 usin provided
                statusId = 0;
            }

            List<ClaimSummary> claims = landRecordsService.getClaimsForAdjudicationForms(usin, startRecord, endRecord, statusId, projectName);

            if (project == null || claims == null || claims.size() < 1) {
                return null;
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String vcDate = "";
            if (project.getVcMeetingDate() != null) {
                vcDate = dateFormat.format(project.getVcMeetingDate());
            }

            HashMap params = new HashMap();
            params.put("VILLAGE", project.getVillage());
            params.put("APP_URL", appUrl);
            params.put("VC_DATE", vcDate);
            params.put("CHAIR_PERSON", project.getVillageChairman());
            params.put("CHAIR_PERSON_SIGNATURE", (StringUtils.isEmpty(project.getVillageChairmanSignature()) ? "0" : project.getVillageChairmanSignature()));
            params.put("EXECUTIVE_PERSON", project.getVillageExecutive());
            params.put("EXECUTIVE_PERSON_SIGNATURE", (StringUtils.isEmpty(project.getVillageExecutiveSignature()) ? "0" : project.getVillageExecutiveSignature()));
            
            ClaimSummary[] beans = claims.toArray(new ClaimSummary[claims.size()]);
            JRDataSource jds = new JRBeanArrayDataSource(beans);

            return JasperFillManager.fillReport(
                    ReportsServiceImpl.class.getResourceAsStream("/reports/AdjudicationForm.jasper"),
                    params, jds);
        } catch (Exception ex) {
            logger.error(ex);
            return null;
        }
    }

    @Override
    public JasperPrint getCcroForms(String projectName, Long usin, int startRecord, int endRecord, String appUrl) {
        try {
            ProjectDetails project = landRecordsService.getProjectDetails(projectName);
            List<ClaimSummary> claims = landRecordsService.getClaimsForCcro(usin, startRecord, endRecord, projectName);

            if (project == null || claims == null || claims.size() < 1) {
                return null;
            }

            HashMap params = new HashMap();
            params.put("VILLAGE", project.getVillage());
            params.put("VILLAGE_ADDRESS", project.getAddress());
            params.put("APP_URL", appUrl);
            params.put("VC_DATE", project.getVcMeetingDate());
            params.put("CHAIR_PERSON", project.getVillageChairman());
            params.put("CHAIR_PERSON_SIGNATURE", (StringUtils.isEmpty(project.getVillageChairmanSignature()) ? "0" : project.getVillageChairmanSignature()));
            params.put("EXECUTIVE_PERSON", project.getVillageExecutive());
            params.put("EXECUTIVE_PERSON_SIGNATURE", (StringUtils.isEmpty(project.getVillageExecutiveSignature()) ? "0" : project.getVillageExecutiveSignature()));
            params.put("DLO_OFFICER", project.getDistrictOfficer());
            params.put("DLO_OFFICER_SIGNATURE", (StringUtils.isEmpty(project.getDistrictOfficerSignature()) ? "0" : project.getDistrictOfficerSignature()));

            URL resource = ReportsServiceImpl.class.getResource("/reports/CrroNonPersonSignature.jasper");
            params.put("SUBREPORT_PATH", Paths.get(resource.toURI()).toAbsolutePath().toString());

            ClaimSummary[] beans = claims.toArray(new ClaimSummary[claims.size()]);
            JRDataSource jds = new JRBeanArrayDataSource(beans);

            return JasperFillManager.fillReport(
                    ReportsServiceImpl.class.getResourceAsStream("/reports/Ccro.jasper"),
                    params, jds);
        } catch (Exception ex) {
            logger.error(ex);
            return null;
        }
    }
    
    @Override
    public JasperPrint getCcroFormsinbatch(String projectName, Long usin, Long startRecord, Long endRecord, String appUrl) {
        try 
        {
           // ProjectDetails project = landRecordsService.getProjectDetails(projectName);
        	List<ReportCertificateFetch> report = landRecordsService.getCertificatedetailsinbatch(startRecord,endRecord);
        	// List<ClaimSummary> claims = landRecordsService.getClaimsForCcro(usin, startRecord, endRecord, projectName);

            if (report == null || report.size() < 1) {
                return null;
            }
            HashMap params = new HashMap();
         
               
                               
//                params.put("usin", objReportCertificateFetch.getUsin());
//                params.put("OWNER_NAME", objReportCertificateFetch.getFirstname());
//                params.put("CERTI_NUMBER", objReportCertificateFetch.getCertificateno());
//                params.put("PARCEL_NUMBER", objReportCertificateFetch.getLandno());
//                params.put("ADDRESS", objReportCertificateFetch.getAddress());
//                params.put("AREA", objReportCertificateFetch.getArea());
//                params.put("LANDUSE", objReportCertificateFetch.getLandusetype());
//                params.put("EAST", objReportCertificateFetch.getNeighbor_east());
//                params.put("WEST", objReportCertificateFetch.getNeighbor_west());
//                params.put("NORTH", objReportCertificateFetch.getNeighbor_north());
//                params.put("SOUTH", objReportCertificateFetch.getNeighbor_south());
//                params.put("sharepercentage", objReportCertificateFetch.getSharepercentage());
//                params.put("partyid", objReportCertificateFetch.getPartyid());
                params.put("LAND_OFFICER_SIGNATURE", (StringUtils.isEmpty(report.get(0).getLandofficersignature()) ? "0" : report.get(0).getLandofficersignature()));
               
               
                params.put("APP_URL", appUrl);

                
            
            ReportCertificateFetch[] beans = report.toArray(new ReportCertificateFetch[report.size()]);
            
            JRDataSource jds = new JRBeanArrayDataSource(beans);

            return JasperFillManager.fillReport(
                    ReportsServiceImpl.class.getResourceAsStream("/reports/Certificates.jasper"),
                    params, jds);
            
                    } 
        catch (Exception ex)
        {
            logger.error(ex);
            return null;
        }
    }
    
    @Override
    public JasperPrint getLandFormsinbatch(String projectName, Long usin, Long startRecord, Long endRecord, String appUrl) {
    	try 
    	{	
    		List<ReportCertificateFetch> report = landRecordsService.getCertificatedetailsinbatch(startRecord,endRecord);
    	
    		if (report == null || report.size() < 1) {
    			return null;
    		}
    		HashMap params = new HashMap();
    		params.put("LAND_OFFICER_SIGNATURE", (StringUtils.isEmpty(report.get(0).getLandofficersignature()) ? "0" : report.get(0).getLandofficersignature()));
    		params.put("APP_URL", appUrl);
    		ReportCertificateFetch[] beans = report.toArray(new ReportCertificateFetch[report.size()]);

    		JRDataSource jds = new JRBeanArrayDataSource(beans);
    		return JasperFillManager.fillReport(
    				ReportsServiceImpl.class.getResourceAsStream("/reports/LandForm.jasper"),
    				params, jds);

    	} 
    	catch (Exception ex)
    	{
    		logger.error(ex);
    		return null;
    	}
    }
    
    /*@Override
    public JasperPrint getCcroFormsinbatch(String projectName, Long usin, Long startRecord, Long endRecord, String appUrl) {
        try 
        {
           // ProjectDetails project = landRecordsService.getProjectDetails(projectName);
        	List<ReportCertificateFetch> report = landRecordsService.getCertificatedetailsinbatch(startRecord,endRecord);
        	// List<ClaimSummary> claims = landRecordsService.getClaimsForCcro(usin, startRecord, endRecord, projectName);

            if (report == null || report.size() < 1) {
                return null;
            }
            HashMap params = new HashMap();
            List<ReportCertificateFetch> reporttemp = new ArrayList<ReportCertificateFetch>();
            for (ReportCertificateFetch objdata :report )
            {
            	
            	ReportCertificateFetch objReportCertificateFetch= new ReportCertificateFetch();
            	
            	objReportCertificateFetch.setUsin(objdata.getUsin());
            	objReportCertificateFetch.setCertificateno(objdata.getCertificateno());
            	objReportCertificateFetch.setLandno(objdata.getLandno());
            	objReportCertificateFetch.setDate(objdata.getDate());
            	objReportCertificateFetch.setArea(objdata.getArea());
            	objReportCertificateFetch.setLandusetype(objdata.getLandusetype());
            	objReportCertificateFetch.setNeighbor_east(objdata.getNeighbor_east());
            	objReportCertificateFetch.setNeighbor_west(objdata.getNeighbor_west());
            	objReportCertificateFetch.setNeighbor_north(objdata.getNeighbor_north());
            	objReportCertificateFetch.setNeighbor_south(objdata.getNeighbor_south());
            	objReportCertificateFetch.setSharepercentage(objdata.getSharepercentage());
            	objReportCertificateFetch.setFirstname(objdata.getFirstname());
            	objReportCertificateFetch.setMiddlename(objdata.getMiddlename());
            	objReportCertificateFetch.setLastname(objdata.getLastname());
            	objReportCertificateFetch.setAddress(objdata.getAddress());
            	objReportCertificateFetch.setLandofficersignature(objdata.getLandofficersignature());
            	objReportCertificateFetch.setPartyid(objdata.getPartyid());
            	
            	reporttemp.add(objReportCertificateFetch);
               
                               
                params.put("usin", objReportCertificateFetch.getUsin());
                params.put("OWNER_NAME", objReportCertificateFetch.getFirstname());
                params.put("CERTI_NUMBER", objReportCertificateFetch.getCertificateno());
                params.put("PARCEL_NUMBER", objReportCertificateFetch.getLandno());
                params.put("ADDRESS", objReportCertificateFetch.getAddress());
                params.put("AREA", objReportCertificateFetch.getArea());
                params.put("LANDUSE", objReportCertificateFetch.getLandusetype());
                params.put("EAST", objReportCertificateFetch.getNeighbor_east());
                params.put("WEST", objReportCertificateFetch.getNeighbor_west());
                params.put("NORTH", objReportCertificateFetch.getNeighbor_north());
                params.put("SOUTH", objReportCertificateFetch.getNeighbor_south());
                params.put("sharepercentage", objReportCertificateFetch.getSharepercentage());
                params.put("partyid", objReportCertificateFetch.getPartyid());
                params.put("LAND_OFFICER_SIGNATURE", (StringUtils.isEmpty(objReportCertificateFetch.getLandofficersignature()) ? "0" : objReportCertificateFetch.getLandofficersignature()));
                
                params.put("APP_URL", appUrl);

                
            }
            ReportCertificateFetch[] beans = reporttemp.toArray(new ReportCertificateFetch[reporttemp.size()]);
            
            JRDataSource jds = new JRBeanArrayDataSource(beans);

            return JasperFillManager.fillReport(
                    ReportsServiceImpl.class.getResourceAsStream("/reports/Ccro.jasper"),
                    params, jds);
            
                    } 
        catch (Exception ex)
        {
            logger.error(ex);
            return null;
        }
    }*/
    
    @Override
    public JasperPrint getCcroFormsLadm(String projectName, Long usin, int startRecord, int endRecord, String appUrl) {
        try {
        	/*ProjectDetails project = landRecordsService.getProjectDetails(projectName);
        	List<ClaimSummary> claims = landRecordsService.getClaimsForCcro(usin, startRecord, endRecord, projectName);

            if (project == null || claims == null || claims.size() < 1) {
                return null;
            }*/
        	
        	List<ReportCertificateFetch> report = landRecordsService.getCertificatedetailsbytransactionid(usin);
        	ReportCertificateFetch objReportCertificateFetch= new ReportCertificateFetch();
        	
        	objReportCertificateFetch.setUsin(report.get(0).getUsin());
        	objReportCertificateFetch.setCertificateno(report.get(0).getCertificateno());
        	objReportCertificateFetch.setLandno(report.get(0).getLandno());
        	objReportCertificateFetch.setDate(report.get(0).getDate());
        	objReportCertificateFetch.setArea(report.get(0).getArea());
        	objReportCertificateFetch.setLandusetype(report.get(0).getLandusetype());
        	objReportCertificateFetch.setNeighbor_east(report.get(0).getNeighbor_east());
        	objReportCertificateFetch.setNeighbor_west(report.get(0).getNeighbor_west());
        	objReportCertificateFetch.setNeighbor_north(report.get(0).getNeighbor_north());
        	objReportCertificateFetch.setNeighbor_south(report.get(0).getNeighbor_south());
        	objReportCertificateFetch.setSharepercentage(report.get(0).getSharepercentage());
        	objReportCertificateFetch.setFirstname(report.get(0).getFirstname());
        	objReportCertificateFetch.setMiddlename(report.get(0).getMiddlename());
        	objReportCertificateFetch.setLastname(report.get(0).getLastname());
        	objReportCertificateFetch.setAddress(report.get(0).getAddress());
        	objReportCertificateFetch.setUdparcelno(report.get(0).getUdparcelno());
        	objReportCertificateFetch.setLandofficersignature(report.get(0).getLandofficersignature());
        	objReportCertificateFetch.setPartyid(report.get(0).getPartyid());
        	
        	//report.add(objReportCertificateFetch);
            HashMap params = new HashMap();
           
            if(report !=null && report.size()>1)
            {
            	params.put("OWNER_NAME1", report.get(1).getFirstname());
                params.put("ADDRESS1", report.get(1).getAddress());
            }               
            params.put("usin", objReportCertificateFetch.getUsin());
            params.put("OWNER_NAME", objReportCertificateFetch.getFirstname());          
            params.put("CERTI_NUMBER", objReportCertificateFetch.getCertificateno());
            params.put("PARCEL_NUMBER", objReportCertificateFetch.getLandno());
            params.put("ADDRESS", objReportCertificateFetch.getAddress());
            params.put("AREA", objReportCertificateFetch.getArea());
            params.put("LANDUSE", objReportCertificateFetch.getLandusetype());
            params.put("EAST", objReportCertificateFetch.getNeighbor_east());
            params.put("WEST", objReportCertificateFetch.getNeighbor_west());
            params.put("NORTH", objReportCertificateFetch.getNeighbor_north());
            params.put("SOUTH", objReportCertificateFetch.getNeighbor_south());
            params.put("sharepercentage", objReportCertificateFetch.getSharepercentage());
            params.put("partyid", objReportCertificateFetch.getPartyid());
            params.put("udparcelno", objReportCertificateFetch.getUdparcelno());  
            params.put("LAND_OFFICER_SIGNATURE", (StringUtils.isEmpty(objReportCertificateFetch.getLandofficersignature()) ? "0" : objReportCertificateFetch.getLandofficersignature()));
            
          
            

            params.put("APP_URL", appUrl);
            
            /*URL resource = ReportsServiceImpl.class.getResource("/reports/CrroNonPersonSignature.jasper");
            params.put("SUBREPORT_PATH", Paths.get(resource.toURI()).toAbsolutePath().toString());*/

            ReportCertificateFetch[] beans = report.toArray(new ReportCertificateFetch[report.size()]);
            JRDataSource jds = new JRBeanArrayDataSource(beans);

            return JasperFillManager.fillReport(
                    ReportsServiceImpl.class.getResourceAsStream("/reports/Ccro.jasper"),
                    params, jds);
        } catch (Exception ex) {
            logger.error(ex);
            return null;
        }
    }
    
    @Override
    public JasperPrint getlandverificationForm(String projectName, Long usin, int startRecord, int endRecord, String appUrl) {
        try 
        {
        	
        	List<ReportCertificateFetch> report = landRecordsService.getCertificatedetailsbytransactionid(usin);
        	ReportCertificateFetch objReportCertificateFetch= new ReportCertificateFetch();
        	
        	objReportCertificateFetch.setUsin(report.get(0).getUsin());
        	objReportCertificateFetch.setCertificateno(report.get(0).getCertificateno());
        	objReportCertificateFetch.setLandno(report.get(0).getLandno());
        	objReportCertificateFetch.setDate(report.get(0).getDate());
        	objReportCertificateFetch.setArea(report.get(0).getArea());
        	objReportCertificateFetch.setLandusetype(report.get(0).getLandusetype());
        	objReportCertificateFetch.setNeighbor_east(report.get(0).getNeighbor_east());
        	objReportCertificateFetch.setNeighbor_west(report.get(0).getNeighbor_west());
        	objReportCertificateFetch.setNeighbor_north(report.get(0).getNeighbor_north());
        	objReportCertificateFetch.setNeighbor_south(report.get(0).getNeighbor_south());
        	objReportCertificateFetch.setSharepercentage(report.get(0).getSharepercentage());
        	objReportCertificateFetch.setFirstname(report.get(0).getFirstname());
        	objReportCertificateFetch.setMiddlename(report.get(0).getMiddlename());
        	objReportCertificateFetch.setLastname(report.get(0).getLastname());
        	objReportCertificateFetch.setAddress(report.get(0).getAddress());
        	objReportCertificateFetch.setLandofficersignature(report.get(0).getLandofficersignature());
        	objReportCertificateFetch.setPartyid(report.get(0).getPartyid());
        	
        	// report.add(objReportCertificateFetch);
            HashMap params = new HashMap();
                           
            params.put("usin", objReportCertificateFetch.getUsin());
            params.put("OWNER_NAME", objReportCertificateFetch.getFirstname());
            params.put("CERTI_NUMBER", objReportCertificateFetch.getCertificateno());
            params.put("PARCEL_NUMBER", objReportCertificateFetch.getLandno());
            params.put("ADDRESS", objReportCertificateFetch.getAddress());
            params.put("AREA", objReportCertificateFetch.getArea());
            params.put("LANDUSE", objReportCertificateFetch.getLandusetype());
            params.put("EAST", objReportCertificateFetch.getNeighbor_east());
            params.put("WEST", objReportCertificateFetch.getNeighbor_west());
            params.put("NORTH", objReportCertificateFetch.getNeighbor_north());
            params.put("SOUTH", objReportCertificateFetch.getNeighbor_south());
            params.put("sharepercentage", objReportCertificateFetch.getSharepercentage());
            params.put("partyid", objReportCertificateFetch.getPartyid());
            params.put("LAND_OFFICER_SIGNATURE", (StringUtils.isEmpty(objReportCertificateFetch.getLandofficersignature()) ? "0" : objReportCertificateFetch.getLandofficersignature()));
            
            params.put("APP_URL", appUrl);
           
            ReportCertificateFetch[] beans = report.toArray(new ReportCertificateFetch[report.size()]);
            JRDataSource jds = new JRBeanArrayDataSource(beans);

            return JasperFillManager.fillReport(
                    ReportsServiceImpl.class.getResourceAsStream("/reports/LandVerificationForm.jasper"),
                    params, jds);
        } catch (Exception ex) {
            logger.error(ex);
            return null;
        }
    }
    

    @Override
    public JasperPrint getDistrictRegistryBook(String projectName, String appUrl) {
        return getRegistryBook(projectName, "/reports/DistrictRegBook.jasper", appUrl);
    }

    @Override
    public JasperPrint getVillageRegistryBook(String projectName, String appUrl) {
        return getRegistryBook(projectName, "/reports/VillageRegBook.jasper", appUrl);
    }

    @Override
    public JasperPrint getVillageIssuanceBook(String projectName) {
        return getRegistryBook(projectName, "/reports/CcroIssuenceBook.jasper", "");
    }

    @Override
    public JasperPrint getTransactionSheet(String projectName, Long usin, String appUrl) {
        try {
            ProjectDetails project = landRecordsService.getProjectDetails(projectName);
            List<RegistryBook> registryBook = landRecordsService.getRegistryBook(projectName, usin);

            if (project == null || registryBook == null || registryBook.size() < 1) {
                return null;
            }

            // Add dummy record to make empty table rows
            int ownersCount = 0;
            int size = registryBook.size();
            int addedRows = 0;
            int totalRows = 17;

            for (int i = 0; i < size; i++) {
                RegistryBook rb = registryBook.get(i + addedRows);
                ownersCount += 1;
                
                if (i == size - 1 || rb.getUsin() != registryBook.get(i + addedRows + 1).getUsin()) {
                    if (totalRows > ownersCount) {
                        for (int j = 0; j < totalRows - ownersCount; j++) {
                            addedRows += 1;
                            RegistryBook rbEmpty = new RegistryBook();
                            rbEmpty.setUsin(rb.getUsin());
                            rbEmpty.setPersonType("1");
                            rbEmpty.setId(0);
                            registryBook.add(i + addedRows, rbEmpty);
                        }
                    }
                    ownersCount = 0;
                } 
            }

            HashMap params = new HashMap();
            params.put("VILLAGE", project.getVillage());
            params.put("DISTRICT", project.getRegion());
            params.put("APP_URL", appUrl);
            params.put("EXECUTIVE_PERSON_SIGNATURE", (StringUtils.isEmpty(project.getVillageExecutiveSignature()) ? "0" : project.getVillageExecutiveSignature()));
            params.put("DLO_OFFICER_SIGNATURE", (StringUtils.isEmpty(project.getDistrictOfficerSignature()) ? "0" : project.getDistrictOfficerSignature()));

            RegistryBook[] beans = registryBook.toArray(new RegistryBook[registryBook.size()]);
            JRDataSource jds = new JRBeanArrayDataSource(beans);

            return JasperFillManager.fillReport(
                    ReportsServiceImpl.class.getResourceAsStream("/reports/TransactionSheet.jasper"),
                    params, jds);
        } catch (Exception ex) {
            logger.error(ex);
            return null;
        }
    }

    @Override
    public JasperPrint getClaimsProfile(String projectName){
        try {
            ClaimProfile profile = landRecordsService.getClaimsProfile(projectName);

            if (profile == null) {
                return null;
            }

            if(StringUtils.isEmpty(projectName)){
                projectName = "ALL";
            }
            
            HashMap params = new HashMap();
            params.put("VILLAGE", projectName);
            ClaimProfile[] beans = new ClaimProfile[]{profile};
            JRDataSource jds = new JRBeanArrayDataSource(beans);

            return JasperFillManager.fillReport(
                    ReportsServiceImpl.class.getResourceAsStream("/reports/ClaimsProfile.jasper"),
                    params, jds);
        } catch (Exception ex) {
            logger.error(ex);
            return null;
        }
    }
    
    private JasperPrint getRegistryBook(String projectName, String reportPath, String appUrl) {
        try {
            ProjectDetails project = landRecordsService.getProjectDetails(projectName);
            List<RegistryBook> registryBook = landRecordsService.getRegistryBook(projectName, 0);

            if (project == null || registryBook == null || registryBook.size() < 1) {
                return null;
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String vcDate = "";
            if (project.getVcMeetingDate() != null) {
                vcDate = dateFormat.format(project.getVcMeetingDate());
            }

            HashMap params = new HashMap();
            params.put("VILLAGE", project.getVillage());
            params.put("VLC", project.getVillageCode());
            params.put("EXECUTIVE_PERSON_SIGNATURE", (StringUtils.isEmpty(project.getVillageExecutiveSignature()) ? "0" : project.getVillageExecutiveSignature()));
            params.put("DLO_OFFICER_SIGNATURE", (StringUtils.isEmpty(project.getDistrictOfficerSignature()) ? "0" : project.getDistrictOfficerSignature()));
            params.put("APP_URL", appUrl);
            params.put("VC_DATE", vcDate);

            RegistryBook[] beans = registryBook.toArray(new RegistryBook[registryBook.size()]);
            JRDataSource jds = new JRBeanArrayDataSource(beans);

            return JasperFillManager.fillReport(
                    ReportsServiceImpl.class.getResourceAsStream(reportPath),
                    params, jds);
        } catch (Exception ex) {
            logger.error(ex);
            return null;
        }
    }

    private String getPersonName(NaturalPerson person) {
    	  String name = "";
          if (!StringUtils.isEmpty(person.getFirstname())) {
              name = person.getFirstname();
          }
          if (!StringUtils.isEmpty(person.getMiddlename())) {
              if (name.length() > 0) {
                  name = name + " " + person.getMiddlename();
              } else {
                  name = person.getMiddlename();
              }
          }
          if (!StringUtils.isEmpty(person.getLastname())) {
              if (name.length() > 0) {
                  name = name + " " + person.getLastname();
              } else {
                  name = person.getLastname();
              }
          }
          return name;
      }
  }