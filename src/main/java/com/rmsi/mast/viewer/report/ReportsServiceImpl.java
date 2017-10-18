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
import com.rmsi.mast.studio.domain.fetch.SpatialUnitTable;
import com.rmsi.mast.studio.util.StringUtils;
import com.rmsi.mast.viewer.service.LandRecordsService;
import java.net.URL;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
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

            if (claim == null || rights == null || rights.size() < 1 || rights.get(0).getPerson_gid() == null) {
                return null;
            }

            ProjectDetails project = landRecordsService.getProjectDetails(claim.getProject());
            String village = project.getVillage();
            String hamlet = claim.getHamlet_Id().getHamletName();
            String claimantName;

            if (rights.get(0).getPerson_gid().getPerson_type_gid().getPerson_type_gid() == PersonType.TYPE_NATURAL) {
                claimantName = getPersonName((NaturalPerson) rights.get(0).getPerson_gid());
            } else {
                NonNaturalPerson nonPerson = (NonNaturalPerson) rights.get(0).getPerson_gid();
                if (nonPerson.getPoc_gid() != null && nonPerson.getPoc_gid() > 0) {
                    claimantName = getPersonName((NaturalPerson) landRecordsService.findPersonGidById(nonPerson.getPoc_gid()));
                } else {
                    claimantName = StringUtils.empty(nonPerson.getInstitutionName());
                }
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

            HashMap params = new HashMap();
            params.put("CLAIMANT_NAME", claimantName);
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
        if (!StringUtils.isEmpty(person.getFirstName())) {
            name = person.getFirstName();
        }
        if (!StringUtils.isEmpty(person.getMiddleName())) {
            if (name.length() > 0) {
                name = name + " " + person.getMiddleName();
            } else {
                name = person.getMiddleName();
            }
        }
        if (!StringUtils.isEmpty(person.getLastName())) {
            if (name.length() > 0) {
                name = name + " " + person.getLastName();
            } else {
                name = person.getLastName();
            }
        }
        return name;
    }
}
