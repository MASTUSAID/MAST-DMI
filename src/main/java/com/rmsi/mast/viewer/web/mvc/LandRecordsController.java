package com.rmsi.mast.viewer.web.mvc;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.rmsi.mast.studio.dao.DisputeStatusDao;
import com.rmsi.mast.studio.domain.AcquisitionType;
import com.rmsi.mast.studio.domain.AttributeCategory;
import com.rmsi.mast.studio.domain.AttributeValues;
import com.rmsi.mast.studio.domain.EducationLevel;
import com.rmsi.mast.studio.domain.Gender;
import com.rmsi.mast.studio.domain.GroupType;
import com.rmsi.mast.studio.domain.LandType;
import com.rmsi.mast.studio.domain.LandUseType;
import com.rmsi.mast.studio.domain.MaritalStatus;
import com.rmsi.mast.studio.domain.NaturalPerson;
import com.rmsi.mast.studio.domain.NonNaturalPerson;
import com.rmsi.mast.studio.domain.OccupancyType;
import com.rmsi.mast.studio.domain.Person;
import com.rmsi.mast.studio.domain.PersonType;
import com.rmsi.mast.studio.domain.ProjectArea;
import com.rmsi.mast.studio.domain.ProjectHamlet;
import com.rmsi.mast.studio.domain.ShareType;
import com.rmsi.mast.studio.domain.SlopeValues;
import com.rmsi.mast.studio.domain.SocialTenureRelationship;
import com.rmsi.mast.studio.domain.SoilQualityValues;
import com.rmsi.mast.studio.domain.SourceDocument;
import com.rmsi.mast.studio.domain.Status;
import com.rmsi.mast.studio.domain.TenureClass;
import com.rmsi.mast.studio.domain.User;
import com.rmsi.mast.studio.domain.fetch.AttributeValuesFetch;
import com.rmsi.mast.studio.domain.fetch.PersonAdministrator;
import com.rmsi.mast.studio.domain.fetch.ProjectTemp;
import com.rmsi.mast.studio.domain.fetch.SpatialUnitTable;
import com.rmsi.mast.studio.domain.fetch.SpatialUnitTemp;
import com.rmsi.mast.studio.domain.fetch.SpatialunitDeceasedPerson;
import com.rmsi.mast.studio.domain.fetch.SpatialunitPersonadministrator;
import com.rmsi.mast.studio.domain.fetch.SpatialunitPersonwithinterest;
import com.rmsi.mast.studio.mobile.service.SpatialUnitService;
import com.rmsi.mast.studio.mobile.service.UserDataService;
import com.rmsi.mast.studio.service.ProjectService;
import com.rmsi.mast.studio.service.UserService;
import com.rmsi.mast.viewer.service.LandRecordsService;
import com.rmsi.mast.studio.domain.Citizenship;
import com.rmsi.mast.studio.domain.ClaimType;
import com.rmsi.mast.studio.domain.Dispute;
import com.rmsi.mast.studio.domain.DisputeStatus;
import com.rmsi.mast.studio.domain.DisputeType;
import com.rmsi.mast.studio.domain.DocumentType;
import com.rmsi.mast.studio.domain.IdType;
import com.rmsi.mast.studio.domain.Project;
import com.rmsi.mast.studio.domain.RelationshipType;
import com.rmsi.mast.studio.domain.fetch.PersonForEditing;
import com.rmsi.mast.studio.domain.fetch.ProjectDetails;
import com.rmsi.mast.studio.util.DateUtils;
import com.rmsi.mast.studio.util.FileUtils;
import com.rmsi.mast.studio.util.StringUtils;
import com.rmsi.mast.viewer.report.ReportsSerivce;
import javax.servlet.ServletOutputStream;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class LandRecordsController {

    private static final Logger logger = Logger.getLogger(LandRecordsController.class);

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @Autowired
    private LandRecordsService landRecordsService;

    @Autowired
    private SpatialUnitService spatialUnitService;

    @Autowired
    private UserDataService userDataService;

    @Autowired
    private DisputeStatusDao disputeStatusDao;

    @Autowired
    private ReportsSerivce reportsService;

    public static final String RESPONSE_OK = "OK";

    @RequestMapping(value = "/viewer/landrecords/", method = RequestMethod.GET)
    @ResponseBody
    public ProjectTemp list(Principal principal) {
        String username = principal.getName();
        User user = userService.findByUniqueName(username);
        return projectService.findProjectTempByName(user.getDefaultproject());
    }

    @RequestMapping(value = "/viewer/landrecords/allprojects/", method = RequestMethod.GET)
    @ResponseBody
    public List<String> getAllProjects() {
        return projectService.getAllProjectNames();
    }

    @RequestMapping(value = "/viewer/landrecords/personsforediting/{projectName}", method = RequestMethod.GET)
    @ResponseBody
    public List<PersonForEditing> getPersonsForEditing(HttpServletRequest request, @PathVariable String projectName) {
        String firstName = ServletRequestUtils.getStringParameter(request, "firstName", "");
        String lastName = ServletRequestUtils.getStringParameter(request, "lastName", "");
        String middleName = ServletRequestUtils.getStringParameter(request, "middleName", "");
        String idNumber = ServletRequestUtils.getStringParameter(request, "idNumber", "");
        String claimNumber = ServletRequestUtils.getStringParameter(request, "claimNumber", "");
        String neighbourN = ServletRequestUtils.getStringParameter(request, "neighbourNorth", "");
        String neighbourS = ServletRequestUtils.getStringParameter(request, "neighbourSouth", "");
        String neighbourE = ServletRequestUtils.getStringParameter(request, "neighbourEast", "");
        String neighbourW = ServletRequestUtils.getStringParameter(request, "neighbourWest", "");
        long usin = ServletRequestUtils.getLongParameter(request, "usin", 0);

        return landRecordsService.getPersonsForEditing(projectName, usin, firstName, lastName, middleName, idNumber, claimNumber, neighbourN, neighbourS, neighbourE, neighbourW);
    }

    @RequestMapping(value = "/viewer/landrecords/savepersonforediting", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity savePersonForEditing(HttpServletResponse response, @RequestBody PersonForEditing pfe) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(landRecordsService.updatePersonForEditing(pfe));
        } catch (Exception e) {
            logger.error(e);
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Failed to save record - " + e.getMessage());
        }
    }

    @RequestMapping(value = "/viewer/landrecords/tenuretype/", method = RequestMethod.GET)
    @ResponseBody
    public List<ShareType> tenureList() {

        return landRecordsService.findAllTenureList();

    }

    @RequestMapping(value = "/viewer/landrecords/socialtenure/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<SocialTenureRelationship> socialTenureList(@PathVariable Long id) {

        return landRecordsService.findAllSocialTenureByUsin(id);
    }

    @RequestMapping(value = "/viewer/landrecords/spatialunitlist/", method = RequestMethod.GET)
    @ResponseBody
    public List<SpatialUnitTable> allspatialUnitList() {

        return landRecordsService.findAllSpatialUnitlist();

    }

    @RequestMapping(value = "/viewer/landrecords/editattribute/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<SpatialUnitTable> editAttribute(@PathVariable Long id) {

        return landRecordsService.findSpatialUnitbyId(id);

    }

    @RequestMapping(value = "/viewer/landrecords/updateattributes", method = RequestMethod.POST)
    @ResponseBody
    public boolean updateAttributes(HttpServletRequest request, HttpServletResponse response) {
        try {
            long Usin = ServletRequestUtils.getRequiredLongParameter(request, "primary");
            int existingUse = ServletRequestUtils.getRequiredIntParameter(request, "existing_use");
            int proposedUse = ServletRequestUtils.getRequiredIntParameter(request, "proposed_use");
            String claimNumber = ServletRequestUtils.getRequiredStringParameter(request, "claimNumber");
            int length_general = ServletRequestUtils.getRequiredIntParameter(request, "general_length");
            String witness1 = ServletRequestUtils.getRequiredStringParameter(request, "witness_1");
            String witness2 = ServletRequestUtils.getRequiredStringParameter(request, "witness_2");
            String neighbour_north = ServletRequestUtils.getRequiredStringParameter(request, "neighbor_north");
            String neighbour_south = ServletRequestUtils.getRequiredStringParameter(request, "neighbor_south");
            String neighbour_east = ServletRequestUtils.getRequiredStringParameter(request, "neighbor_east");
            String neighbour_west = ServletRequestUtils.getRequiredStringParameter(request, "neighbor_west");
            int landType = ServletRequestUtils.getRequiredIntParameter(request, "land_type");

            SpatialUnitTable spatialUnit = landRecordsService.findSpatialUnitbyId(Usin).get(0);

            spatialUnit.setStatusUpdateTime(new Date());
            spatialUnit.setClaimNumber(claimNumber);
            spatialUnit.setNeighbor_east(neighbour_east);
            spatialUnit.setNeighbor_north(neighbour_north);
            spatialUnit.setNeighbor_south(neighbour_south);
            spatialUnit.setNeighbor_west(neighbour_west);
            spatialUnit.setWitness_1(witness1);
            spatialUnit.setWitness_2(witness2);

            if (existingUse != 0) {
                LandUseType existingObj = landRecordsService.findLandUseById(existingUse);
                spatialUnit.setExistingUse(existingObj);
            }

            if (proposedUse != 0) {
                LandUseType proposedobj = landRecordsService.findProposedUseById(proposedUse);
                spatialUnit.setProposedUse(proposedobj);
            } else {
                spatialUnit.setProposedUse(null);
            }

            if (landType != 0) {
                LandType landTypeObj = landRecordsService.findLandType(landType);
                spatialUnit.setLandType(landTypeObj);
            }

            if (length_general > 0) {
                for (int i = 0; i < length_general; i++) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("alias");
                    sb.append(i);
                    String alias = sb.toString();
                    Long value_key = 0l;

                    alias = ServletRequestUtils.getRequiredStringParameter(request, "alias_general" + i);
                    value_key = ServletRequestUtils.getRequiredLongParameter(request, "alias_general_key" + i);
                    if (value_key != 0) {
                        if (!landRecordsService.updateAttributeValues(value_key, alias)) {
                            return false;
                        }
                    }
                }
            }

            // Update Attribute Values Table
            try {
                if (!userDataService.updateGeneralAttribValues(spatialUnit, spatialUnit.getProject())) {
                    return false;
                }
            } catch (Exception e) {
                logger.error(e);
            }

            return landRecordsService.update(spatialUnit);

        } catch (ServletRequestBindingException e) {
            logger.error(e);
            return false;
        }
    }

    @RequestMapping(value = "/viewer/landrecords/reject/{id}", method = RequestMethod.GET)
    @ResponseBody
    public boolean rejectClaim(@PathVariable Long id, Principal principal) {
        String username = principal.getName();
        User user = userService.findByUniqueName(username);
        long userid = user.getId();
        return landRecordsService.rejectStatus(id, userid);
    }

    @RequestMapping(value = "/viewer/landrecords/search/{project}/{startpos}", method = RequestMethod.POST)
    @ResponseBody
    public List<SpatialUnitTable> searchUnitList(HttpServletRequest request, HttpServletResponse response, Principal principal, @PathVariable String project, @PathVariable Integer startpos) {
        String UkaNumber = "";
        String dateto = "";
        String datefrom = "";
        long status = 0;
        String claimType = "";
        String UsinStr = "";

        String username = principal.getName();
        User user = userService.findByUniqueName(username);
        String projname = user.getDefaultproject();
        if (project != "") {
            projname = project;
        }

        try {
            try {
                UsinStr = ServletRequestUtils.getRequiredStringParameter(request, "usinstr_id");
            } catch (Exception e) {
                logger.error(e);
            }
            try {
                UkaNumber = ServletRequestUtils.getRequiredStringParameter(request, "uka_id");
            } catch (Exception e) {
                logger.error(e);
            }

            try {
                dateto = ServletRequestUtils.getRequiredStringParameter(request, "to_id");
            } catch (Exception e) {
                logger.error(e);
            }

            try {
                datefrom = ServletRequestUtils.getRequiredStringParameter(request, "from_id");
            } catch (Exception e) {
                logger.error(e);
            }

            try {
                status = ServletRequestUtils.getRequiredLongParameter(request, "status_id");
            } catch (Exception e) {
                logger.error(e);
            }

            try {
                claimType = ServletRequestUtils.getRequiredStringParameter(request, "claim_type");
            } catch (Exception e) {
                logger.error(e);
            }

            if (dateto == "") {
                SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
                Date now = new Date();
                dateto = sdfDate.format(now);
            }

            if (datefrom == "") {
                datefrom = "1990-01-01 00:00:00";
            }

            return landRecordsService.search(UsinStr, UkaNumber, projname,
                    dateto, datefrom, status, claimType, startpos);

        } catch (Exception e) {
            logger.error(e);
            return null;
        }
    }

    @RequestMapping(value = "/viewer/landrecords/claimtypes/", method = RequestMethod.GET)
    @ResponseBody
    public List<ClaimType> getClaimTypes() {
        List<ClaimType> list = new ArrayList<>();
        try {
            list = landRecordsService.getClaimTypes();
        } catch (Exception e) {
            logger.error(e);
            return list;
        }
        return list;
    }

    @RequestMapping(value = "/viewer/landrecords/status/", method = RequestMethod.GET)
    @ResponseBody
    public List<Status> getClaimStatuses() {
        List<Status> statuslst = new ArrayList<Status>();
        try {
            statuslst = landRecordsService.findallStatus();
        } catch (Exception e) {
            logger.error(e);
            return statuslst;
        }
        return statuslst;
    }

    @RequestMapping(value = "/viewer/landrecords/naturalperson/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<NaturalPerson> naturalPerson(@PathVariable Long id) {
        List<NaturalPerson> persons = landRecordsService.naturalPersonById(id);
        return persons;
    }

    @RequestMapping(value = "/viewer/landrecords/nonnaturalperson/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<NonNaturalPerson> nonnaturalPerson(@PathVariable Long id) {
        return landRecordsService.nonnaturalPersonById(id);

    }

    @RequestMapping(value = "/viewer/landrecords/gendertype/", method = RequestMethod.GET)
    @ResponseBody
    public List<Gender> genderList() {
        return landRecordsService.findAllGenders();

    }

    @RequestMapping(value = "/viewer/landrecords/idtype/", method = RequestMethod.GET)
    @ResponseBody
    public List<IdType> getIdTypes() {
        return landRecordsService.getIdTypes();

    }

    @RequestMapping(value = "/viewer/landrecords/maritalstatus/", method = RequestMethod.GET)
    @ResponseBody
    public List<MaritalStatus> maritalStatusList() {

        return landRecordsService.findAllMaritalStatus();

    }

    @RequestMapping(value = "/viewer/landrecords/updatenatural", method = RequestMethod.POST)
    @ResponseBody
    public NaturalPerson updateNaturalPerson(HttpServletRequest request, HttpServletResponse response) {
        Long id = 0L;
        Long genderid = 0L;
        int maritalid = 0;
        long persontype = 1;
        String first_name;
        String middle_name;
        String last_name;
        String mobile_number;
        int age = 0;
        String dob;
        Date dobDate = null;
        int length_natural = 0;
        String project_name;
        long citizenship = 0;
        int newnatural_length = 0;
        long usin = 0;
        long person_subType = 0;
        long parentNonNaturalId;
        long disputeId;
        int idTypeCode;
        String idNumber;
        String shareSize;
        boolean isResident;

        NaturalPerson person = new NaturalPerson();
        person.setResident(true);
        person.setResident_of_village(true);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            try {
                id = ServletRequestUtils.getRequiredLongParameter(request, "natural_key");
            } catch (Exception e2) {
                logger.error(e2);
            }

            try {
                usin = ServletRequestUtils.getRequiredLongParameter(request, "natural_usin");
            } catch (Exception e) {
                logger.error(e);
            }

            parentNonNaturalId = ServletRequestUtils.getLongParameter(request, "parentNonNaturalId", 0);
            disputeId = ServletRequestUtils.getLongParameter(request, "personDisputeId", 0);
            first_name = ServletRequestUtils.getRequiredStringParameter(request, "fname");
            middle_name = ServletRequestUtils.getRequiredStringParameter(request, "mname");
            last_name = ServletRequestUtils.getRequiredStringParameter(request, "lname");
            mobile_number = ServletRequestUtils.getRequiredStringParameter(request, "mobile_natural");
            isResident = ServletRequestUtils.getBooleanParameter(request, "personResident", false);

            try {
                dob = ServletRequestUtils.getRequiredStringParameter(request, "dob");
                if (!StringUtils.isEmpty(dob)) {
                    dobDate = dateFormat.parse(dob);
                    age = DateUtils.getAge(dobDate);
                }
            } catch (Exception e) {
                logger.error(e);
            }

            idTypeCode = ServletRequestUtils.getRequiredIntParameter(request, "idType");
            idNumber = ServletRequestUtils.getRequiredStringParameter(request, "idNumber");
            shareSize = ServletRequestUtils.getRequiredStringParameter(request, "shareSize");
            maritalid = ServletRequestUtils.getRequiredIntParameter(request, "marital_status");
            genderid = ServletRequestUtils.getRequiredLongParameter(request, "gender");
            length_natural = ServletRequestUtils.getRequiredIntParameter(request, "natual_length");
            project_name = ServletRequestUtils.getRequiredStringParameter(request, "projectname_key");
            citizenship = ServletRequestUtils.getRequiredLongParameter(request, "citizenship");
            person_subType = ServletRequestUtils.getRequiredLongParameter(request, "person_subType");

            try {
                newnatural_length = ServletRequestUtils.getRequiredIntParameter(request, "newnatural_length");
            } catch (Exception e) {
                logger.error(e);
            }

            if (id != 0) {
                person = landRecordsService.naturalPersonById(id).get(0);
            }

            person.setAlias(first_name);
            person.setFirstName(first_name);
            person.setMiddleName(middle_name);
            person.setLastName(last_name);
            person.setMobile(mobile_number);
            person.setDob(dobDate);
            person.setAge(age);
            person.setShare(shareSize);
            person.setIdNumber(idNumber);
            person.setResident(isResident);
            person.setResident_of_village(isResident);
            person.setActive(true);

            if (idTypeCode != 0) {
                person.setIdType(landRecordsService.getIdType(idTypeCode));
            } else {
                person.setIdType(null);
            }

            if (citizenship != 0) {
                person.setCitizenship_id(landRecordsService.findcitizenship(citizenship));
            }

            if (genderid != 0) {
                person.setGender(landRecordsService.findGenderById(genderid));
            }

            person.setPerson_type_gid(landRecordsService.findPersonTypeById(persontype));

            if (maritalid != 0) {
                person.setMarital_status(landRecordsService.findMaritalById(maritalid));
            }

            if (person_subType != 0) {
                person.setPersonSubType(landRecordsService.findPersonTypeById(person_subType));
            }

            //AttributeValues attributeValues=new AttributeValues();
            if (length_natural > 0) {
                for (int i = 0; i < length_natural; i++) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("alias");
                    sb.append(i);
                    String alias = sb.toString();
                    Long value_key = 0l;

                    alias = ServletRequestUtils.getRequiredStringParameter(request, "alias_natural" + i);
                    value_key = ServletRequestUtils.getRequiredLongParameter(request, "alias_natural_key" + i);
                    if (value_key != 0) {
                        landRecordsService.updateAttributeValues(value_key, alias);
                    }
                }
            }

            //For updating in Attribute Values Table
            person = landRecordsService.editnatural(person);

            // Create right for new person (id = 0) and skip for non natural representatives (parentNonNaturalId > 0)
            if (id == 0 && parentNonNaturalId == 0 && usin > 0 && disputeId == 0) {
                SocialTenureRelationship right = landRecordsService.findAllSocialTenureByUsin(usin).get(0);
                // Check if right has person assigned, if not, it will be saved with current person, if yes, new right will be created
                if (right.getPerson_gid() != null) {
                    right.setGid(0);
                }
                right.setPerson_gid(person);
                landRecordsService.edittenure(right);
            }

            // Update NonNatural, if person is representative (parentNonNaturalId > 0)
            if (parentNonNaturalId > 0) {
                NonNaturalPerson nonPerson = landRecordsService.getNonNaturalPersonBy(parentNonNaturalId);
                if (nonPerson != null) {
                    nonPerson.setPoc_gid(person.getPerson_gid());
                    landRecordsService.editNonNatural(nonPerson);
                }
            }

            // Add person to disputing parties
            if (disputeId > 0 && id == 0) {
                Dispute dispute = landRecordsService.getDispute(disputeId);
                if (dispute != null) {
                    if (dispute.getDisputingPersons() == null) {
                        dispute.setDisputingPersons(new ArrayList<NaturalPerson>());
                    }
                    dispute.getDisputingPersons().add(person);
                    landRecordsService.updateDispute(dispute);
                }
            }

            if (newnatural_length != 0) {
                AttributeValues tmpvalue = new AttributeValues();
                for (int i = 0; i < newnatural_length; i++) {
                    int j = i + 1;
                    StringBuilder sb = new StringBuilder();
                    sb.append("alias");
                    sb.append(i);
                    String alias_value = sb.toString();
                    Long uid = 0l;

                    alias_value = ServletRequestUtils.getRequiredStringParameter(request, "alias_nat_custom" + j);
                    uid = ServletRequestUtils.getRequiredLongParameter(request, "alias_uid" + j);

                    tmpvalue.setValue(alias_value);
                    tmpvalue.setParentuid(person.getPerson_gid());
                    tmpvalue.setUid(uid);
                    landRecordsService.saveAttributealues(tmpvalue);
                }
            }

            try {
                userDataService.updateNaturalPersonAttribValues(person, project_name);
            } catch (Exception e) {
                logger.error(e);
            }

            return person;

        } catch (ServletRequestBindingException e) {
            logger.error(e);
        }
        return null;
    }

    @RequestMapping(value = "/viewer/landrecords/updatenonnatural", method = RequestMethod.POST)
    @ResponseBody
    public boolean updateNonNaturalPerson(HttpServletRequest request, HttpServletResponse response) {

        Long id = 0L;
        String institute_name = "";
        String address = "";
        String phone_no = "";
        long persontype = 2;
        long poc_id = 0;
        String mobileGroupId = "";
        int length_nonnatural = 0;
        String project_nonnatural = "";
        int group_type = 0;

        NonNaturalPerson nonPerson = new NonNaturalPerson();

        try {
            id = ServletRequestUtils.getRequiredLongParameter(request, "non_natural_key");
            long usin = ServletRequestUtils.getLongParameter(request, "nonnatural_usin", 0);
            institute_name = ServletRequestUtils.getRequiredStringParameter(request, "institution");
            address = ServletRequestUtils.getRequiredStringParameter(request, "address");
            phone_no = ServletRequestUtils.getRequiredStringParameter(request, "mobile_no");
            poc_id = ServletRequestUtils.getRequiredLongParameter(request, "poc_id");
            mobileGroupId = ServletRequestUtils.getRequiredStringParameter(request, "mobileGroupId");
            length_nonnatural = ServletRequestUtils.getRequiredIntParameter(request, "nonnatual_length");
            project_nonnatural = ServletRequestUtils.getRequiredStringParameter(request, "projectname_key2");

            group_type = ServletRequestUtils.getRequiredIntParameter(request, "group_type");

            nonPerson.setPerson_gid(id);
            nonPerson.setAddress(address);
            nonPerson.setInstitutionName(institute_name);
            nonPerson.setPhoneNumber(phone_no);
            nonPerson.setActive(true);

            nonPerson.setPerson_type_gid(landRecordsService.findPersonTypeById(persontype));
            nonPerson.setMobileGroupId(mobileGroupId);
            if (poc_id > 0) {
                nonPerson.setPoc_gid(poc_id);
            } else {
                nonPerson.setPoc_gid(null);
            }

            if (group_type != 0) {
                nonPerson.setGroupType(landRecordsService.findGroupType(group_type));
            }

            nonPerson = landRecordsService.editNonNatural(nonPerson);

            // Create new right if non person is new
            if (id == 0 && usin > 0) {
                SocialTenureRelationship right = landRecordsService.findAllSocialTenureByUsin(usin).get(0);
                // Check if right has person assigned, if not, it will be saved with current person, if yes, new right will be created
                if (right.getPerson_gid() != null) {
                    right.setGid(0);
                }
                right.setPerson_gid(nonPerson);
                landRecordsService.edittenure(right);
            }

            //AttributeValues attributeValues=new AttributeValues();
            if (length_nonnatural > 0) {
                for (int i = 0; i < length_nonnatural; i++) {

                    StringBuilder sb = new StringBuilder();
                    sb.append("alias");
                    sb.append(i);
                    String alias = sb.toString();
                    Long value_key = 0l;

                    alias = ServletRequestUtils.getRequiredStringParameter(request, "alias_nonnatural" + i);
                    value_key = ServletRequestUtils.getRequiredLongParameter(request, "alias_nonnatural_key" + i);
                    if (value_key != 0) {
                        landRecordsService.updateAttributeValues(value_key, alias);
                    }
                }
            }

            //For Updating Non Natural in Attribute Vlaues
            try {
                userDataService.updateNonNaturalPersonAttribValues(nonPerson, project_nonnatural);
            } catch (Exception e) {
                logger.error(e);
            }

            return true;

        } catch (ServletRequestBindingException e) {
            logger.error(e);
            return false;
        }
    }

    @RequestMapping(value = "/viewer/landrecords/deletedispute/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String deleteDispute(@PathVariable Long id) {
        try {
            Dispute dispute = landRecordsService.getDispute(id);

            if (dispute == null) {
                return "Dispute not found.";
            }

            String validation = validateDisputeForDelete(dispute);
            if (!validation.equals(RESPONSE_OK)) {
                return validation;
            }

            // Delete dispute 
            if (landRecordsService.deleteDispute(dispute)) {
                return RESPONSE_OK;
            } else {
                return "Failed to delete dispute";
            }

        } catch (Exception e) {
            logger.error(e);
            return "Failed to delete dispute";
        }
    }

    @RequestMapping(value = "/viewer/landrecords/resolvedispute", method = RequestMethod.POST)
    @ResponseBody
    public String resolveDispute(HttpServletRequest request, HttpServletResponse response) {
        try {
            long id = ServletRequestUtils.getRequiredLongParameter(request, "resolveDisputeId");
            String resolutionText = ServletRequestUtils.getRequiredStringParameter(request, "resolutionText");

            Dispute dispute = landRecordsService.getDispute(id);

            if (dispute == null) {
                return "Dispute not found.";
            }

            String validation = validateDisputeForDelete(dispute);

            if (!validation.equals(RESPONSE_OK)) {
                return validation;
            }

            // Resolve dispute 
            dispute.setResolutionText(resolutionText);

            if (landRecordsService.resolveDispute(dispute)) {
                return RESPONSE_OK;
            } else {
                return "Failed to resolve dispute";
            }

        } catch (Exception e) {
            logger.error(e);
            return "Failed to resolve dispute";
        }
    }

    private String validateDisputeForDelete(Dispute dispute) {
        SpatialUnitTable parcel = landRecordsService.findSpatialUnitbyId(dispute.getUsin()).get(0);

        // Allow delete only for parcels with new and referred statuses
        if (parcel.getStatus().getWorkflowStatusId() != Status.STATUS_NEW
                && parcel.getStatus().getWorkflowStatusId() != Status.STATUS_REFERRED) {
            return "Disputes can not be managed for this claim";
        }

        // Allow delete only for parcels with new or disputed type
        if (!parcel.getClaimType().getCode().equalsIgnoreCase(ClaimType.CODE_NEW)
                && !parcel.getClaimType().getCode().equalsIgnoreCase(ClaimType.CODE_DISPUTED)) {
            return "Disputes can not be managed for this type of claims";
        }

        // Allow deletion only if ownership information exists
        List<SocialTenureRelationship> rights = landRecordsService.findAllSocialTenureByUsin(parcel.getUsin());

        if (rights == null || rights.size() < 1) {
            return "Ownership rights must be recorded.";
        }

        boolean activeRightExists = false;

        for (SocialTenureRelationship right : rights) {
            if (right.getIsActive()) {
                // Check for person
                if (right.getPerson_gid() != null) {
                    activeRightExists = true;
                    break;
                }
            }
        }

        if (!activeRightExists) {
            return "Right holders must be recorded.";
        }

        return RESPONSE_OK;
    }

    @RequestMapping(value = "/viewer/landrecords/updatedispute", method = RequestMethod.POST)
    @ResponseBody
    public String updateDispute(HttpServletRequest request, HttpServletResponse response) {
        try {
            long usin = ServletRequestUtils.getRequiredLongParameter(request, "disputeUsin");
            long id = ServletRequestUtils.getRequiredLongParameter(request, "disputeId");
            int disputeTypeCode = ServletRequestUtils.getRequiredIntParameter(request, "cbxDisputeTypes");
            String description = ServletRequestUtils.getRequiredStringParameter(request, "txtDisputeDescription");

            if (usin < 1) {
                return "Spatial unit was not found";
            }

            Dispute dispute;

            if (id > 0) {
                dispute = landRecordsService.getDispute(id);
                if (dispute == null) {
                    return "Dispute not found";
                }
            } else {
                // New dispute
                SpatialUnitTable parcel = landRecordsService.findSpatialUnitbyId(usin).get(0);
                // Check for parcel to have new or referred status and be new claim or disputed
                if ((parcel.getStatus().getWorkflowStatusId() != Status.STATUS_NEW
                        && parcel.getStatus().getWorkflowStatusId() != Status.STATUS_REFERRED)
                        || (!parcel.getClaimType().getCode().equals(ClaimType.CODE_NEW)
                        && !parcel.getClaimType().getCode().equals(ClaimType.CODE_DISPUTED))) {
                    return "Dispute can not be registered for this claim";
                }
                dispute = new Dispute();
                dispute.setStatus(disputeStatusDao.findById(DisputeStatus.STATUS_ACTIVE, false));
                dispute.setDeleted(false);
                dispute.setRegDate(Calendar.getInstance().getTime());
                dispute.setId(id);
                dispute.setUsin(usin);
            }

            if (disputeTypeCode > 0) {
                dispute.setDisputeType(landRecordsService.getDisputeType(disputeTypeCode));
            }

            dispute.setDescription(description);
            landRecordsService.updateDispute(dispute);

            return RESPONSE_OK;

        } catch (Exception e) {
            logger.error(e);
            return "Failed to save dispute";
        }
    }

    @RequestMapping(value = "/viewer/landrecords/deleteDisputant/{disputeId}/{partyId}", method = RequestMethod.GET)
    @ResponseBody
    public boolean deleteDisputant(@PathVariable Long disputeId, @PathVariable Long partyId) {
        return landRecordsService.deleteDisputant(disputeId, partyId);
    }

    @RequestMapping(value = "/viewer/landrecords/updatetenure", method = RequestMethod.POST)
    @ResponseBody
    public boolean updateTenure(HttpServletRequest request, HttpServletResponse response) {
        String certNumber;
        String certDate;
        Long tenureType;
        int rightType;
        int length;
        long usin;
        Integer acquisitionTypeCode;
        long relationshipTypeCode;
        float tenureDuration = 0;
        long rightId;
        Double juridicalArea;
        String projectName;

        try {

            usin = ServletRequestUtils.getRequiredLongParameter(request, "usin");
            List<SpatialUnitTable> parcels = landRecordsService.findSpatialUnitbyId(usin);

            if (parcels == null || parcels.size() < 1) {
                throw new RuntimeException("Spatial unit with usin = " + usin + " was not found.");
            }

            SpatialUnitTable parcel = parcels.get(0);

            if (parcel.getStatus().getWorkflowStatusId() == Status.STATUS_APPROVED || parcel.getStatus().getWorkflowStatusId() == Status.STATUS_DENIED) {
                throw new RuntimeException("Spatial unit with usin = " + usin + " cannot be modified.");
            }

            certNumber = ServletRequestUtils.getStringParameter(request, "txtCertNumber", "");
            certDate = ServletRequestUtils.getStringParameter(request, "txtCertDate", "");
            tenureType = ServletRequestUtils.getLongParameter(request, "tenure_type", 0);
            rightId = ServletRequestUtils.getLongParameter(request, "tenure_key", 0);
            rightType = ServletRequestUtils.getIntParameter(request, "tenureclass_id", 0);
            acquisitionTypeCode = ServletRequestUtils.getRequiredIntParameter(request, "lstAcquisitionTypes");
            relationshipTypeCode = ServletRequestUtils.getLongParameter(request, "lstRelationshipTypes", 0);
            juridicalArea = ServletRequestUtils.getDoubleParameter(request, "txtJuridicalArea", 0);
            tenureDuration = ServletRequestUtils.getFloatParameter(request, "tenureDuration", 0);
            length = ServletRequestUtils.getRequiredIntParameter(request, "tenure_length");
            projectName = ServletRequestUtils.getRequiredStringParameter(request, "projectname_key3");

            List<SocialTenureRelationship> ownershipRights = landRecordsService.findAllSocialTenureByUsin(usin);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            if (ownershipRights == null) {
                ownershipRights = new ArrayList<>();
            }

            if (rightId == 0) {
                // This is new right. Add it to the list
                SocialTenureRelationship right = new SocialTenureRelationship();
                right.setUsin(usin);
                right.setTenureclassId(landRecordsService.findtenureClasseById(2));
                right.setIsActive(true);
                if (rightType > 0) {
                    right.setTenureclassId(landRecordsService.findtenureClasseById(rightType));
                }
                ownershipRights.add(right);
            }

            if (ownershipRights.size() > 0) {
                for (int i = 0; i < ownershipRights.size(); i++) {
                    SocialTenureRelationship ownershipRight = ownershipRights.get(i);

                    if (tenureType > 0) {
                        ownershipRight.setShare_type(landRecordsService.findTenureType(tenureType));
                    }

                    if (parcel.getClaimType().getCode().equals(ClaimType.CODE_EXISTING)) {
                        ownershipRight.setCertNumber(certNumber);
                        if (!StringUtils.isEmpty(certDate)) {
                            ownershipRight.setCertIssueDate(dateFormat.parse(certDate));
                        } else {
                            ownershipRight.setCertIssueDate(null);
                        }
                    }

                    if (acquisitionTypeCode > 0) {
                        ownershipRight.setAcquisitionType(landRecordsService.findAcquisitionType(acquisitionTypeCode));
                    }

                    if (relationshipTypeCode > 0) {
                        ownershipRight.setRelationshipType(landRecordsService.findRelationshipType(relationshipTypeCode));
                    } else {
                        ownershipRight.setRelationshipType(null);
                    }

                    ownershipRight.setJuridicalArea(juridicalArea);
                    ownershipRight.setTenureDuration(tenureDuration);

                    // Update/save tenure
                    try {
                        ownershipRight = landRecordsService.edittenure(ownershipRight);
                        // Refresh right after saving if it's new
                        if (rightId == 0 && ownershipRight != null) {
                            ownershipRights.set(i, ownershipRight);
                        }
                    } catch (Exception e) {
                        logger.error(e);
                        return false;
                    }

                    //For Updating tenure in AttributeValues
                    try {
                        userDataService.updateTenureAttribValues(ownershipRight, projectName);
                    } catch (Exception e) {
                        logger.error(e);
                    }
                }
            }

            // Custom attributes
            if (length > 0 && ownershipRights != null && ownershipRights.size() > 0) {
                for (int i = 0; i < length; i++) {
                    long uid = 0l;
                    for (int j = 0; j < ownershipRights.size(); j++) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("alias");
                        sb.append(i);
                        String alias = sb.toString();
                        Long value_key = 0l;

                        value_key = ServletRequestUtils.getRequiredLongParameter(request, "alias_tenure_key" + i);
                        Long attributeKey = 0L;

                        if (value_key != 0) {
                            AttributeValues attributefetch = landRecordsService.getAttributeValue(value_key);
                            uid = attributefetch.getUid();
                            attributeKey = landRecordsService.getAttributeKey(ownershipRights.get(j).getGid(), uid);
                        }

                        alias = ServletRequestUtils.getRequiredStringParameter(request, "alias_tenure" + i);
                        if (attributeKey != 0) {
                            landRecordsService.updateAttributeValues(attributeKey, alias);
                        }
                    }
                }
            }

            return true;
        } catch (Exception e) {
            logger.error(e);
            return false;
        }
    }

    @RequestMapping(value = "/viewer/landrecords/updatemultimedia", method = RequestMethod.POST)
    @ResponseBody
    public boolean updateMultimedia(MultipartHttpServletRequest request, HttpServletResponse response, Principal principal) {
        int gid;
        String comments;
        long usin;
        int docType;
        String docName;
        String projectName;

        SourceDocument sourceDocument = new SourceDocument();

        try {
            Iterator<String> file = request.getFileNames();
            usin = ServletRequestUtils.getRequiredLongParameter(request, "usink");
            projectName = ServletRequestUtils.getRequiredStringParameter(request, "projectname_multimedia_key");
            gid = ServletRequestUtils.getRequiredIntParameter(request, "primary_key");
            docType = ServletRequestUtils.getRequiredIntParameter(request, "docType");
            docName = ServletRequestUtils.getRequiredStringParameter(request, "docName");
            comments = ServletRequestUtils.getRequiredStringParameter(request, "comments_multimedia");

            if (gid > 0) {
                sourceDocument = landRecordsService.getDocumentbyGid(new Long(gid));
            } else {
                sourceDocument.setGid(gid);
                sourceDocument.setRecordation(Calendar.getInstance().getTime());
            }

            sourceDocument.setEntity_name(docName);
            sourceDocument.setComments(comments);
            sourceDocument.setUsin(usin);
            sourceDocument.setActive(true);

            if (docType > 0) {
                sourceDocument.setDocumentType(landRecordsService.getDocumentType(docType));
            } else {
                sourceDocument.setDocumentType(null);
            }

            if (gid == 0) {
                // Save new document
                byte[] document = null;
                while (file.hasNext()) {
                    String fileName = file.next();
                    MultipartFile mpFile = request.getFile(fileName);
                    String originalFileName = mpFile.getOriginalFilename();
                    String fileExtension = originalFileName.substring(originalFileName.indexOf(".") + 1, originalFileName.length()).toLowerCase();

                    if (!"".equals(originalFileName)) {
                        document = mpFile.getBytes();
                    }

                    String uploadFileName = null;
                    String outDirPath = FileUtils.getFielsFolder(request) + "resources" + File.separator + "documents" + File.separator + projectName + File.separator + "webupload";
                    File outDir = new File(outDirPath);
                    boolean exists = outDir.exists();

                    if (!exists) {
                        (new File(outDirPath)).mkdirs();
                    }

                    sourceDocument.setScanedSourceDoc(originalFileName);
                    uploadFileName = ("resources/documents/" + projectName + "/webupload");
                    sourceDocument.setLocScannedSourceDoc(uploadFileName);
                    sourceDocument.setActive(true);
                    sourceDocument.setRecordation(new Date());
                    sourceDocument.setUsin(usin);

                    sourceDocument = landRecordsService.saveUploadedDocuments(sourceDocument);

                    try {
                        FileOutputStream uploadfile = new FileOutputStream(outDirPath + File.separator + sourceDocument.getGid() + "." + fileExtension);
                        uploadfile.write(document);
                        uploadfile.flush();
                        uploadfile.close();
                    } catch (Exception e) {
                        logger.error(e);
                        return false;
                    }
                }
            } else {
                landRecordsService.updateMultimedia(sourceDocument);
            }

            //For Updating tenure in AttributeValues
            userDataService.updateMultimediaAttribValues(sourceDocument, projectName);

            return true;
        } catch (Exception e) {
            logger.error(e);
            return false;
        }
    }

    @RequestMapping(value = "/viewer/landrecords/socialtenure/edit/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<SocialTenureRelationship> SocialTenurebyGidList(@PathVariable Integer id) {
        return landRecordsService.findSocialTenureByGid(id);
    }

    @RequestMapping(value = "/viewer/landrecords/multimedia/edit/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<SourceDocument> MultimediaList(@PathVariable Long id) {
        return landRecordsService.findMultimediaByUsin(id);
    }

    @RequestMapping(value = "/viewer/landrecords/multimedia/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<SourceDocument> MultimediaGidList(@PathVariable Long id) {
        return landRecordsService.findMultimediaByGid(id);
    }

    @RequestMapping(value = "/viewer/landrecords/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public boolean deleteMultimedia(@PathVariable Long id) {
        return landRecordsService.deleteMultimedia(id);
    }

    @RequestMapping(value = "/viewer/landrecords/deleteNatural/{id}", method = RequestMethod.GET)
    @ResponseBody
    public boolean deleteNatural(@PathVariable Long id) {
        return landRecordsService.deleteNatural(id);
    }

    @RequestMapping(value = "/viewer/landrecords/deletenonnatural/{id}", method = RequestMethod.GET)
    @ResponseBody
    public boolean deleteNonNatural(@PathVariable Long id) {
        return landRecordsService.deleteNonNatural(id);
    }

    @RequestMapping(value = "/viewer/landrecords/deleteTenure/{id}", method = RequestMethod.GET)
    @ResponseBody
    public boolean deleteTenure(@PathVariable Long id) {
        return landRecordsService.deleteTenure(id);
    }

    @RequestMapping(value = "/viewer/landrecords/educationlevel/", method = RequestMethod.GET)
    @ResponseBody
    public List<EducationLevel> educationList() {
        return landRecordsService.findAllEducation();
    }

    @RequestMapping(value = "/viewer/landrecords/landusertype/", method = RequestMethod.GET)
    @ResponseBody
    public List<LandUseType> landUserList() {
        return landRecordsService.findAllLanduserType();
    }

    @RequestMapping(value = "/viewer/landrecords/tenureclass/", method = RequestMethod.GET)
    @ResponseBody
    public List<TenureClass> tenureclassList() {
        return landRecordsService.findAllTenureClass();
    }

    @RequestMapping(value = "/viewer/landrecords/doctypes/", method = RequestMethod.GET)
    @ResponseBody
    public List<DocumentType> getDocumentTypes() {
        return landRecordsService.getDocumentTypes();
    }

    @RequestMapping(value = "/viewer/landrecords/ukanumber/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String ukaNumberByUsin(@PathVariable Long id) {
        return landRecordsService.findukaNumberByUsin(id);
    }

    @RequestMapping(value = "/viewer/landrecords/hamletname/{project}", method = RequestMethod.GET)
    @ResponseBody
    public List<ProjectHamlet> findAllHamlet(@PathVariable String project) {
        return projectService.findHamletByProject(project);
    }

    @RequestMapping(value = "/viewer/landrecords/occupancytype/", method = RequestMethod.GET)
    @ResponseBody
    public List<OccupancyType> OccTypeList() {
        return landRecordsService.findAllOccupancyTypes();
    }

    @RequestMapping(value = "/viewer/landrecords/acquisitiontypes/", method = RequestMethod.GET)
    @ResponseBody
    public List<AcquisitionType> getAcquisitionTypes() {
        return landRecordsService.findAllAcquisitionTypes();
    }

    @RequestMapping(value = "/viewer/landrecords/relationshiptypes/", method = RequestMethod.GET)
    @ResponseBody
    public List<RelationshipType> getRelationshipTypes() {
        return landRecordsService.findAllRelationshipTypes();
    }

    @RequestMapping(value = "/viewer/landrecords/attributecategory/", method = RequestMethod.GET)
    @ResponseBody
    public List<AttributeCategory> attribList() {
        return landRecordsService.findAllAttributeCategories();
    }

    @RequestMapping(value = "/viewer/landrecords/getCCRO/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<SpatialUnitTable> CCROList(@PathVariable Long id, Principal principal) {
        return landRecordsService.findSpatialUnitbyId(id);
    }

    @RequestMapping(value = "/viewer/landrecords/attributedata/{categoryid}/{parentid}", method = RequestMethod.GET)
    @ResponseBody
    public List<AttributeValuesFetch> attributeDataList(@PathVariable long categoryid, @PathVariable long parentid) {
        return landRecordsService.findAttributelistByCategoryId(parentid, categoryid);
    }

    @RequestMapping(value = "/viewer/landrecords/naturalpersondata/{personid}", method = RequestMethod.GET)
    @ResponseBody
    public Person naturalPersonList(@PathVariable long personid) {
        return landRecordsService.findPersonGidById(personid);
    }

    @RequestMapping(value = "/viewer/landrecords/uploadweb/", method = RequestMethod.POST)
    @ResponseBody
    public String uploadSpatialData(MultipartHttpServletRequest request, HttpServletResponse response, Principal principal) {
        String username = principal.getName();
        User user = userService.findByUniqueName(username);
        String projectName = user.getDefaultproject();

        try {

            Iterator<String> file = request.getFileNames();
            Long usin = 0l;
            usin = ServletRequestUtils.getRequiredLongParameter(request, "Usin_Upload");
            String document_name = "";
            document_name = ServletRequestUtils.getRequiredStringParameter(request, "document_name");
            String document_comments = "";
            try {
                document_comments = ServletRequestUtils.getRequiredStringParameter(request, "document_comments");
            } catch (Exception e2) {
                logger.error(e2);

            }

            byte[] document = null;

            while (file.hasNext()) {
                String fileName = file.next();
                MultipartFile mpFile = request.getFile(fileName);

                String originalFileName = mpFile.getOriginalFilename();
                SourceDocument sourceDocument = new SourceDocument();

                String fileExtension = originalFileName.substring(originalFileName.indexOf(".") + 1, originalFileName.length()).toLowerCase();

                if (originalFileName != "") {
                    document = mpFile.getBytes();
                }

                String uploadFileName = null;

                String tmpDirPath = request.getSession().getServletContext().getRealPath(File.separator);

                String outDirPath = tmpDirPath.replace("mast", "") + "resources" + File.separator + "documents" + File.separator + projectName + File.separator + "webupload";

                File outDir = new File(outDirPath);
                boolean exists = outDir.exists();
                if (!exists) {

                    (new File(outDirPath)).mkdirs();

                }

                sourceDocument.setScanedSourceDoc(originalFileName);
                uploadFileName = ("resources/documents/" + projectName + "/webupload");
                sourceDocument.setLocScannedSourceDoc(uploadFileName);
                sourceDocument.setEntity_name(document_name);
                sourceDocument.setComments(document_comments);
                sourceDocument.setActive(true);
                sourceDocument.setRecordation(new Date());
                sourceDocument.setUsin(usin);

                sourceDocument = landRecordsService.saveUploadedDocuments(sourceDocument);

                Integer id = sourceDocument.getGid();

                try {
                    FileOutputStream uploadfile = new FileOutputStream(outDirPath + File.separator + id + "." + fileExtension);
                    uploadfile.write(document);
                    uploadfile.flush();
                    uploadfile.close();

                    //use for Compression if needed
                    // return compressPicture(outDirPath,id,fileExtension);
                    return "Success";

                } catch (Exception e) {

                    logger.error(e);
                    return "Error";
                }

            }

        } catch (Exception e) {
            logger.error(e);
            return "Error";
        }
        return "false";
    }

    @RequestMapping(value = "/viewer/landrecords/sourcedocument/{usinId}", method = RequestMethod.GET)
    @ResponseBody
    public List<SourceDocument> sourcedocumentList(@PathVariable long usinId) {
        return landRecordsService.findMultimediaByUsin(usinId);
    }

    @RequestMapping(value = "/viewer/landrecords/disputes/{usin}", method = RequestMethod.GET)
    @ResponseBody
    public List<Dispute> getDisputes(@PathVariable long usin) {
        return landRecordsService.getDisputes(usin);
    }

    @RequestMapping(value = "/viewer/landrecords/dispute/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Dispute getDispute(@PathVariable long id) {
        return landRecordsService.getDispute(id);
    }

    @RequestMapping(value = "/viewer/landrecords/disputetypes/", method = RequestMethod.GET)
    @ResponseBody
    public List<DisputeType> getDisputeType() {
        return landRecordsService.getDisputeTypes();
    }

    @RequestMapping(value = "/viewer/landrecords/download/{id}", method = RequestMethod.GET)
    @ResponseBody
    public void download(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response) {

        SourceDocument doc = landRecordsService.getDocumentbyGid(id);
        String filepath = FileUtils.getFielsFolder(request) + doc.getLocScannedSourceDoc()
                + File.separator + id + "." + FileUtils.getFileExtension(doc.getScanedSourceDoc());
        Path path = Paths.get(filepath);
        try {
            byte[] data = Files.readAllBytes(path);

            response.setContentLength(data.length);
            OutputStream out = response.getOutputStream();
            out.write(data);
            out.flush();
            out.close();

        } catch (Exception e) {
            logger.error(e);
        }
    }

    @RequestMapping(value = "/viewer/landrecords/denialletter/{usin}", method = RequestMethod.GET)
    @ResponseBody
    public void getDenialLetter(@PathVariable Long usin, HttpServletRequest request, HttpServletResponse response) {
        writeReport(reportsService.getDenialLetter(usin), "DenialLetter", response);
    }

    @RequestMapping(value = "/viewer/landrecords/adjudicationform/{usin}", method = RequestMethod.GET)
    @ResponseBody
    public void getAdjudicationForm(@PathVariable Long usin, HttpServletRequest request, HttpServletResponse response) {
        try {
            SpatialUnitTable claim = landRecordsService.getSpatialUnit(usin);
            if (claim != null) {
                writeReport(reportsService.getAdjudicationForms(claim.getProject(), usin, 1, 1, getApplicationUrl(request)), "AdjudicationForm", response);
            }
        } catch (Exception e) {
            logger.error(e);
        }
    }

    @RequestMapping(value = "/viewer/landrecords/adjudicationforms/{projectName}/{startRecord}/{endRecord}", method = RequestMethod.GET)
    @ResponseBody
    public void getAdjudicationForms(@PathVariable String projectName, @PathVariable int startRecord, @PathVariable int endRecord, HttpServletRequest request, HttpServletResponse response) {
        writeReport(reportsService.getAdjudicationForms(projectName, 0L, startRecord, endRecord, getApplicationUrl(request)), "AdjudicationForms", response);
    }

    @RequestMapping(value = "/viewer/landrecords/ccroform/{usin}", method = RequestMethod.GET)
    @ResponseBody
    public void getCcroForm(@PathVariable Long usin, HttpServletRequest request, HttpServletResponse response) {
        try {
            SpatialUnitTable claim = landRecordsService.getSpatialUnit(usin);
            if (claim != null) {
                writeReport(reportsService.getCcroForms(claim.getProject(), usin, 1, 1, getApplicationUrl(request)), "CcroForm", response);
            }
        } catch (Exception e) {
            logger.error(e);
        }
    }

    @RequestMapping(value = "/viewer/landrecords/ccroforms/{projectName}/{startRecord}/{endRecord}", method = RequestMethod.GET)
    @ResponseBody
    public void getCcroForms(@PathVariable String projectName, @PathVariable int startRecord, @PathVariable int endRecord, HttpServletRequest request, HttpServletResponse response) {
        writeReport(reportsService.getCcroForms(projectName, 0L, startRecord, endRecord, getApplicationUrl(request)), "CcroForms", response);
    }

    @RequestMapping(value = "/viewer/landrecords/districtregbook/{projectName}", method = RequestMethod.GET)
    @ResponseBody
    public void getDistrictRegistryBook(@PathVariable String projectName, HttpServletRequest request, HttpServletResponse response) {
        writeReport(reportsService.getDistrictRegistryBook(projectName, getApplicationUrl(request)), "DistrictRegistryBook", response);
    }

    @RequestMapping(value = "/viewer/landrecords/villageregbook/{projectName}", method = RequestMethod.GET)
    @ResponseBody
    public void getVillageRegistryBook(@PathVariable String projectName, HttpServletRequest request, HttpServletResponse response) {
        writeReport(reportsService.getVillageRegistryBook(projectName, getApplicationUrl(request)), "VillageRegistryBook", response);
    }

    @RequestMapping(value = "/viewer/landrecords/villageissuancebook/{projectName}", method = RequestMethod.GET)
    @ResponseBody
    public void getVillageIssuanceBook(@PathVariable String projectName, HttpServletRequest request, HttpServletResponse response) {
        writeReport(reportsService.getVillageIssuanceBook(projectName), "VillageIssuanceBook", response);
    }

    @RequestMapping(value = "/viewer/landrecords/transactionsheet/{usin}/{projectName}", method = RequestMethod.GET)
    @ResponseBody
    public void getTransactionSheet(@PathVariable long usin, @PathVariable String projectName, HttpServletRequest request, HttpServletResponse response) {
        writeReport(reportsService.getTransactionSheet(projectName, usin, getApplicationUrl(request)), "TransactionSheet", response);
    }

    @RequestMapping(value = "/viewer/landrecords/claimsprofile/{projectName}", method = RequestMethod.GET)
    @ResponseBody
    public void getClaimsProfile(@PathVariable String projectName, HttpServletRequest request, HttpServletResponse response) {
        writeReport(reportsService.getClaimsProfile(projectName), "ClaimsProfile", response);
    }

    @RequestMapping(value = "/viewer/landrecords/checkvcdate/{projectName}", method = RequestMethod.GET)
    @ResponseBody
    public String checkVillageCouncilDate(@PathVariable String projectName) {
        try {
            ProjectDetails project = landRecordsService.getProjectDetails(projectName);
            if (project == null || project.getVcMeetingDate() == null) {
                return "Set Village Council meeting date in the project configuration.";
            }
            return RESPONSE_OK;
        } catch (Exception e) {
            logger.error(e);
            return "Failed to check Village Council date.";
        }
    }

    private String getApplicationUrl(HttpServletRequest r) {
        try {
            String appUrl = r.getRequestURL().substring(0, r.getRequestURL().length() - r.getRequestURI().length() + r.getContextPath().length());
            // JasperREports has issues with HTTPS protocol when generating output in PDF format. 
            // So, try to replace https to http for workaround
            appUrl = appUrl.replace("https:", "http:").replace(":8181", ":8080").replace(":443", "");
            return appUrl;
        } catch (Exception e) {
            logger.error(e);
            return "";
        }
    }

    private void writeReport(JasperPrint report, String name, HttpServletResponse response) {
        try {
            if (report == null) {
                return;
            }
            if (StringUtils.isEmpty(name)) {
                name = "report";
            }
            response.setHeader("Content-Type", "application/pdf");
            response.addHeader("Content-disposition", "inline; filename=" + name + ".pdf");
            try (ServletOutputStream out = response.getOutputStream()) {
                JasperExportManager.exportReportToPdfStream(report, out);
                out.flush();
            }

        } catch (Exception e) {
            logger.error(e);
        }
    }

    @RequestMapping(value = "/viewer/landrecords/personphoto/{id}", method = RequestMethod.GET)
    @ResponseBody
    public void getPersonPhoto(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response) {
        try {
            SourceDocument doc = landRecordsService.getdocumentByPerson(id);
            if (doc == null || !doc.isActive()) {
                writeEmptyImage(request, response);
                return;
            }

            String fileType = FileUtils.getFileExtension(doc.getScanedSourceDoc());
            String filepath = FileUtils.getFielsFolder(request) + doc.getLocScannedSourceDoc() + File.separator + doc.getGid() + "." + fileType;
            Path path = Paths.get(filepath);

            if (!path.toFile().exists()) {
                writeEmptyImage(request, response);
                return;
            }

            byte[] data = Files.readAllBytes(path);

            response.setContentLength(data.length);
            response.setHeader("Content-Type", "image/jpeg");
            response.addHeader("Content-disposition", "inline; inline; filename=\"photo.jpeg\"");

            try (OutputStream out = response.getOutputStream()) {
                out.write(data);
                out.flush();
            }
        } catch (Exception e) {
            logger.error(e);
        }
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

    @RequestMapping(value = "/viewer/landrecords/Adjuticator/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<SpatialUnitTable> AdjuticatorList(Principal principal, @PathVariable Long id) {
        return landRecordsService.findSpatialUnitbyId(id);
    }

    @RequestMapping(value = "/viewer/landrecords/projectarea/", method = RequestMethod.GET)
    @ResponseBody
    public List<ProjectArea> updateFinal(Principal principal) {

        String username = principal.getName();
        User user = userService.findByUniqueName(username);
        String projectName = user.getDefaultproject();
        return landRecordsService.findProjectArea(projectName);

    }

    @RequestMapping(value = "/viewer/landrecords/soilquality/", method = RequestMethod.GET)
    @ResponseBody
    public List<SoilQualityValues> soilQualityList() {

        return landRecordsService.findAllsoilQuality();
    }

    @RequestMapping(value = "/viewer/landrecords/slope/", method = RequestMethod.GET)
    @ResponseBody
    public List<SlopeValues> slopeList() {

        return landRecordsService.findAllSlopeValues();

    }

    @RequestMapping(value = "/viewer/landrecords/typeofland/", method = RequestMethod.GET)
    @ResponseBody
    public List<LandType> landTypeList() {

        return landRecordsService.findAllLandType();
    }

    @RequestMapping(value = "/viewer/landrecords/grouptype/", method = RequestMethod.GET)
    @ResponseBody
    public List<GroupType> groupTypeList() {

        return landRecordsService.findAllGroupType();
    }

    @RequestMapping(value = "/viewer/landrecords/personbyusin/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<SocialTenureRelationship> personByUsin(@PathVariable Long id) {
        return landRecordsService.findAllSocialTenureByUsin(id);
    }

    @RequestMapping(value = "/viewer/landrecords/ccrodownload/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<String> ccrodownload(@PathVariable Long id) {

        List<SourceDocument> doc = landRecordsService.findMultimediaByUsin(id);
        ArrayList<String> ccrodoc = new ArrayList<String>();
        ArrayList<Long> naturalGid = landRecordsService.findOwnerPersonByUsin(id);

        try {
            for (int i = 0; i < doc.size(); i++) {
                if ((naturalGid.contains(doc.get(i).getPerson_gid()))) {
                    String fileName = doc.get(i).getScanedSourceDoc();
                    if (fileName.toLowerCase().contains("jpg")) {
                        String fileType = fileName.substring(fileName.indexOf(".") + 1, fileName.length()).toLowerCase();
                        String filepath = doc.get(i).getLocScannedSourceDoc() + File.separator + doc.get(i).getGid() + "." + fileType;
                        NaturalPerson naturalpersontmp = landRecordsService.naturalPersonById(doc.get(i).getPerson_gid()).get(0);
                        naturalGid.remove(doc.get(i).getPerson_gid());
                        String name = naturalpersontmp.getFirstName() + " " + naturalpersontmp.getMiddleName() + " " + naturalpersontmp.getLastName();
                        //doc.get(i).getPerson_gid();
                        ccrodoc.add(name);
                        ccrodoc.add(filepath);

                    }
                }
            }

            if (naturalGid.size() != 0) {
                for (int i = 0; i < naturalGid.size(); i++) {
                    NaturalPerson naturalpersontmp = landRecordsService.naturalPersonById(naturalGid.get(i)).get(0);
                    String name = naturalpersontmp.getFirstName() + " " + naturalpersontmp.getMiddleName() + " " + naturalpersontmp.getLastName();
                    ccrodoc.add(name);
                    ccrodoc.add("");
                }

            }
        } catch (Exception e) {

            logger.error(e);
        }

        return ccrodoc;

    }

    @RequestMapping(value = "/viewer/landrecords/getpersontype/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Integer getpersontypeByUsin(@PathVariable Long id) {

        List<SocialTenureRelationship> socialtenuretmp = landRecordsService.findAllSocialTenureByUsin(id);

        if (socialtenuretmp.size() > 0) {
            if (socialtenuretmp.size() > 1) {
                return 1;
            } else if (socialtenuretmp.get(0).getPerson_gid().getPerson_type_gid().getPerson_type_gid() == 1) {
                return 0;
            } else if (socialtenuretmp.get(0).getPerson_gid().getPerson_type_gid().getPerson_type_gid() == 2) {
                return 2;
            }

        }
        return null;
    }

    @RequestMapping(value = "/viewer/landrecords/getinstitutename/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String Institute(@PathVariable Long id) {

        List<SocialTenureRelationship> socialtenuretmp = landRecordsService.findAllSocialTenureByUsin(id);
        long gid = socialtenuretmp.get(0).getPerson_gid().getPerson_gid();

        List<NonNaturalPerson> nonNaturalpersonList;
        try {
            nonNaturalpersonList = landRecordsService.nonnaturalPersonById(gid);
            return nonNaturalpersonList.get(0).getInstitutionName();
        } catch (Exception e) {
            logger.error(e);
            return null;
        }

    }

    @RequestMapping(value = "/viewer/landrecords/ccroinstituteperson/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String InstitutePerson(@PathVariable Long id) {

        List<SocialTenureRelationship> socialtenuretmp = landRecordsService.findAllSocialTenureByUsin(id);
        long gid = socialtenuretmp.get(0).getPerson_gid().getPerson_gid();

        List<NonNaturalPerson> nonNaturalpersonList = landRecordsService.nonnaturalPersonById(gid);
        long naturalid = nonNaturalpersonList.get(0).getPoc_gid();

        NaturalPerson naturaltmp = landRecordsService.naturalPersonById(naturalid).get(0);
        String name = naturaltmp.getFirstName() + " " + naturaltmp.getMiddleName() + " " + naturaltmp.getLastName();
        return name;

    }

    @RequestMapping(value = "/viewer/landrecords/personadmin/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<String> PersonAdmin(@PathVariable Long id) {

        List<String> adminName = new ArrayList<String>();

        try {
            List<Long> adminList = landRecordsService.getAdminId(id);

            for (Long adminID : adminList) {

                PersonAdministrator personadmin = landRecordsService.getAdministratorById(adminID);
                SourceDocument admindoc = landRecordsService.getdocumentByAdminId(adminID);
                String name = personadmin.getFirstname() + " " + personadmin.getMiddlename() + " " + personadmin.getLastname();
                String fileName = admindoc.getScanedSourceDoc();
                String fileType = fileName.substring(fileName.indexOf(".") + 1, fileName.length()).toLowerCase();
                String filepath = admindoc.getLocScannedSourceDoc() + File.separator + admindoc.getGid() + "." + fileType;
                adminName.add(name);
                adminName.add(filepath);
            }
        } catch (Exception e) {
            logger.error(e);
        }

        return adminName;
    }

    @RequestMapping(value = "/viewer/landrecords/spatialunit/{project}", method = RequestMethod.GET)
    @ResponseBody
    public Integer totalRecords(Principal principal, @PathVariable String project) {

        String loggeduser = principal.getName();
        User user = userService.findByUniqueName(loggeduser);
        String defaultProject = user.getDefaultproject();
        if (project != null) {
            defaultProject = project;
        }

        //return landRecordsService.findAllSpatialUnit(defaultProject);
        return landRecordsService.AllSpatialUnitTemp(defaultProject);

    }

    @RequestMapping(value = "/viewer/landrecords/changeccrostatus/{id}", method = RequestMethod.GET)
    @ResponseBody
    public boolean changeStatus(@PathVariable Long id, Principal principal) {
        String username = principal.getName();
        User user = userService.findByUniqueName(username);
        long userid = user.getId();
        List<SpatialUnitTable> spatialunit = landRecordsService.findSpatialUnitbyId(id);
        if (spatialunit.get(0).getStatus().getWorkflowStatusId() == 4) {
            return landRecordsService.updateCCRO(id, userid);
        } else {

            return false;
        }

    }

    @RequestMapping(value = "/viewer/landrecords/{project}", method = RequestMethod.GET)
    @ResponseBody
    public ProjectTemp list(@PathVariable String project) {
        return projectService.findProjectTempByName(project);
    }

    @RequestMapping(value = "/viewer/landrecords/spatialunit/{project}/{startfrom}", method = RequestMethod.GET)
    @ResponseBody
    public List<SpatialUnitTemp> spatialUnitList(@PathVariable String project, @PathVariable Integer startfrom) {
        return landRecordsService.findAllSpatialUnitTemp(project, startfrom);
    }

    @RequestMapping(value = "/viewer/landrecords/spatialfalse/{id}", method = RequestMethod.GET)
    @ResponseBody
    public boolean spatialUnitList(@PathVariable Long id) {
        return landRecordsService.deleteSpatialUnit(id);
    }

    @RequestMapping(value = "/viewer/landrecords/showndisputers/{usin}", method = RequestMethod.GET)
    @ResponseBody
    public List<NaturalPerson> showDisputers(@PathVariable Long usin) {
        List<Dispute> disputes = landRecordsService.getDisputes(usin);
        List<NaturalPerson> persons = new ArrayList<>();

        if (disputes != null && disputes.size() > 0) {
            for (Dispute dispute : disputes) {
                if (!dispute.isDeleted() && dispute.getDisputingPersons() != null) {
                    persons.addAll(dispute.getDisputingPersons());
                }
            }
        }

        return persons;
    }

    @RequestMapping(value = "/viewer/landrecords/addexistingperson/{usin}/{personId}", method = RequestMethod.GET)
    @ResponseBody
    public boolean addExistingPerson(@PathVariable Long usin, @PathVariable Long personId) {
        try {
            if (personId > 0 && usin > 0) {
                Person person = landRecordsService.findPersonGidById(personId);
                List<SocialTenureRelationship> rights = landRecordsService.findAllSocialTenureByUsin(usin);

                if (person != null && rights != null && rights.size() > 0) {
                    // Check person doesn't exist already
                    for (SocialTenureRelationship right : rights) {
                        if (right.getPerson_gid() != null && right.getPerson_gid().getPerson_gid() == personId) {
                            return true;
                        }
                    }
                    // Add person to right
                    SocialTenureRelationship right = rights.get(0);
                    if (right.getPerson_gid() != null) {
                        right.setGid(0);
                    }
                    right.setPerson_gid(person);
                    landRecordsService.edittenure(right);
                }
            }

            return true;

        } catch (Exception e) {
            logger.error(e);
            return false;
        }
    }

    @RequestMapping(value = "/viewer/landrecords/shownatural/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<SocialTenureRelationship> showDeletedNatural(@PathVariable Long id) {
        ArrayList<SocialTenureRelationship> objtemp = new ArrayList<SocialTenureRelationship>();
        List<SocialTenureRelationship> obj = landRecordsService.showDeletedPerson(id);
        for (int i = 0; i < obj.size(); i++) {
            if (obj.get(i).getPerson_gid().getPerson_type_gid().getPerson_type_gid() == 1) {
                objtemp.add(obj.get(i));
            }
        }

        return objtemp;
    }

    @RequestMapping(value = "/viewer/landrecords/addnatural/{gid}", method = RequestMethod.GET)
    @ResponseBody
    public boolean addDeletedNatural(@PathVariable Long gid) {
        return landRecordsService.addDeletedPerson(gid);
    }

    @RequestMapping(value = "/viewer/landrecords/shownonnatural/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<SocialTenureRelationship> showDeletedNonNatural(@PathVariable Long id) {
        ArrayList<SocialTenureRelationship> objtemp = new ArrayList<SocialTenureRelationship>();
        List<SocialTenureRelationship> obj = landRecordsService.showDeletedPerson(id);
        for (int i = 0; i < obj.size(); i++) {
            if (obj.get(i).getPerson_gid().getPerson_type_gid().getPerson_type_gid() == 2) {
                objtemp.add(obj.get(i));
            }
        }

        return objtemp;
    }

    @RequestMapping(value = "/viewer/landrecords/addnonnatural/{gid}", method = RequestMethod.GET)
    @ResponseBody
    public boolean addDeletedNonNatural(@PathVariable Long gid) {
        return landRecordsService.addDeletedPerson(gid);
    }

    @RequestMapping(value = "/viewer/landrecords/search/{project}", method = RequestMethod.POST)
    @ResponseBody
    public Integer searchListSize(HttpServletRequest request, HttpServletResponse response, Principal principal, @PathVariable String project) {
        String UkaNumber = "";
        String dateto = "";
        String datefrom = "";
        long status = 0;
        String UsinStr = "";
        String claimType = "";
        String username = principal.getName();
        User user = userService.findByUniqueName(username);
        String projname = user.getDefaultproject();

        if (!"".equals(project)) {
            projname = project;
        }

        try {
            try {
                UsinStr = ServletRequestUtils.getRequiredStringParameter(request, "usinstr_id");
            } catch (Exception e) {
                logger.error(e);
            }
            try {
                UkaNumber = ServletRequestUtils.getRequiredStringParameter(request, "uka_id");
            } catch (Exception e) {
                logger.error(e);
            }

            try {
                dateto = ServletRequestUtils.getRequiredStringParameter(request, "to_id");
            } catch (Exception e) {
                logger.error(e);
            }

            try {
                datefrom = ServletRequestUtils.getRequiredStringParameter(request, "from_id");
            } catch (Exception e) {
                logger.error(e);
            }

            try {
                status = ServletRequestUtils.getRequiredLongParameter(request, "status_id");
            } catch (Exception e) {
                logger.error(e);
            }

            try {
                claimType = ServletRequestUtils.getRequiredStringParameter(request, "claim_type");
            } catch (Exception e) {
                logger.error(e);
            }

            if ("".equals(dateto)) {
                SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
                Date now = new Date();
                dateto = sdfDate.format(now);
            } else if (!"".equals(dateto)) {
                SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");//dd/MM/yyyy
                Date date = sdfDate.parse(dateto);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                calendar.add(Calendar.DATE, 1);
                dateto = sdfDate.format(calendar.getTime());
            }
            if ("".equals(datefrom)) {
                datefrom = "1990-01-01 00:00:00";
            }

            return landRecordsService.searchListSize(UsinStr, UkaNumber, projname, dateto, datefrom, status, claimType);
        } catch (Exception e) {
            logger.error(e);
            return 0;
        }
    }

    @RequestMapping(value = "/viewer/landrecords/adminfetch/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<PersonAdministrator> adminList(@PathVariable Long id) {
        List<Long> adminList = landRecordsService.getAdminId(id);
        List<PersonAdministrator> personadminList = new ArrayList<PersonAdministrator>();
        if (adminList != null) {
            for (Long adminID : adminList) {

                PersonAdministrator personadmin = landRecordsService.getAdministratorById(adminID);
                if (personadmin.getActive()) {
                    personadminList.add(personadmin);
                }
            }
        }
        return personadminList;
    }

    @RequestMapping(value = "/viewer/landRecords/check/naturalimage/{person_gid}/{admin_id}", method = RequestMethod.GET)
    @ResponseBody
    public List<String> naturalImageUrl(@PathVariable Long person_gid, @PathVariable Long admin_id) {
        SourceDocument sourcetemp = new SourceDocument();
        ArrayList<String> resulttmp = new ArrayList<String>();
        if (person_gid != 0) {
            sourcetemp = landRecordsService.getdocumentByPerson(person_gid);
        } else if (admin_id != 0) {
            sourcetemp = landRecordsService.getdocumentByAdminId(admin_id);
        }

        if (sourcetemp != null && sourcetemp.isActive()) {
            resulttmp.add(sourcetemp.getEntity_name());
            resulttmp.add(sourcetemp.getLocScannedSourceDoc() + "/" + sourcetemp.getGid() + ".jpg");

            return resulttmp;
        } else {
            return resulttmp;
        }

    }

    @RequestMapping(value = "/viewer/landrecords/upload/personimage/", method = RequestMethod.POST)
    @ResponseBody
    public String uploadNaturalImage(MultipartHttpServletRequest request, HttpServletResponse response, Principal principal) {
        try {
            Long usin = 0l;
            String document_name = "";
            String document_comments = "";
            String projectName = "";
            long person_gid = 0;
            long admin_id = 0;

            Iterator<String> file = request.getFileNames();

            usin = ServletRequestUtils.getRequiredLongParameter(request, "Usin_Upload");
            document_name = ServletRequestUtils.getRequiredStringParameter(request, "document_name");
            try {
                projectName = ServletRequestUtils.getRequiredStringParameter(request, "proj_name");
            } catch (Exception e) {
                logger.error(e);

            }

            try {
                person_gid = ServletRequestUtils.getRequiredLongParameter(request, "person_gid");
            } catch (Exception e) {
                logger.error(e);

            }
            try {
                admin_id = ServletRequestUtils.getRequiredLongParameter(request, "admin_id");
            } catch (Exception e) {
                logger.error(e);

            }

            byte[] document = null;
            while (file.hasNext()) {
                String fileName = file.next();
                MultipartFile mpFile = request.getFile(fileName);

                String originalFileName = mpFile.getOriginalFilename();
                SourceDocument sourceDocument1 = new SourceDocument();
                if (person_gid != 0) {
                    sourceDocument1 = landRecordsService.getdocumentByPerson(person_gid);
                }
                if (admin_id != 0) {
                    sourceDocument1 = landRecordsService.getdocumentByAdminId(admin_id);
                }
                if (sourceDocument1 == null) {
                    sourceDocument1 = new SourceDocument();
                }
                String fileExtension = FileUtils.getFileExtension(originalFileName);

                if (!"".equals(originalFileName)) {
                    document = mpFile.getBytes();
                }

                String uploadFileName = null;
                String outDirPath = FileUtils.getFielsFolder(request) + "resources" + File.separator + "documents" + File.separator + projectName + File.separator + "multimedia";
                sourceDocument1.setScanedSourceDoc(originalFileName);
                uploadFileName = ("resources/documents/" + projectName + "/multimedia");

                sourceDocument1.setLocScannedSourceDoc(uploadFileName);
                if (person_gid != 0) {
                    sourceDocument1.setPerson_gid(person_gid);
                }
                if (admin_id != 0) {
                    sourceDocument1.setAdminid(admin_id);
                }
                sourceDocument1.setEntity_name(document_name);
                sourceDocument1.setComments(document_comments);

                sourceDocument1.setActive(true);
                try {
                    sourceDocument1.setRecordation(new Date());
                    sourceDocument1.setUsin(usin);
                } catch (Exception e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                sourceDocument1 = landRecordsService.saveUploadedDocuments(sourceDocument1);

                Integer id = sourceDocument1.getGid();

                try {
                    FileOutputStream uploadfile = new FileOutputStream(outDirPath + File.separator + id + "." + fileExtension);
                    uploadfile.write(document);
                    uploadfile.flush();
                    uploadfile.close();
                    return "Success";
                } catch (Exception e) {
                    logger.error(e);
                    return "Error";
                }
            }
        } catch (Exception e) {
            logger.error(e);
            return "Error";
        }
        return "false";
    }

    @RequestMapping(value = "/viewer/landrecords/administrator/{id}", method = RequestMethod.GET)
    @ResponseBody
    public PersonAdministrator findadminByID(@PathVariable Long id) {
        return landRecordsService.getAdministratorById(id);
    }

    @RequestMapping(value = "/viewer/landrecords/updateadmin/{id}", method = RequestMethod.POST)
    @ResponseBody
    public PersonAdministrator updateAdmin(HttpServletRequest request, HttpServletResponse response, @PathVariable Long id) {
        Long adminid = 0L;
        String first_name = "";
        String middle_name = "";
        String last_name = "";
        long genderid = 0;
        int maritalid = 0;
        int age = 0;
        String citizenship = "";
        int resident;
        String mobile_number = "";
        String address = "";
        Long usin = 0L;

        PersonAdministrator personobj = new PersonAdministrator();
        try {
            try {
                adminid = ServletRequestUtils.getRequiredLongParameter(request, "adminId");
            } catch (Exception e2) {
                logger.error(e2);
            }
            first_name = ServletRequestUtils.getRequiredStringParameter(request, "admin_fname");
            middle_name = ServletRequestUtils.getRequiredStringParameter(request, "admin_mname");
            last_name = ServletRequestUtils.getRequiredStringParameter(request, "admin_lname");
            mobile_number = ServletRequestUtils.getRequiredStringParameter(request, "admin_mobile");

            try {
                age = ServletRequestUtils.getRequiredIntParameter(request, "admin_age");
            } catch (Exception e1) {
                logger.error(e1);
            }
            maritalid = ServletRequestUtils.getRequiredIntParameter(request, "admin_marital_status");
            address = ServletRequestUtils.getRequiredStringParameter(request, "admin_address");

            genderid = ServletRequestUtils.getRequiredLongParameter(request, "admin_gender");

            ServletRequestUtils.getRequiredStringParameter(request, "projectname_key");

            resident = ServletRequestUtils.getRequiredIntParameter(request, "admin_resident");
            citizenship = ServletRequestUtils.getRequiredStringParameter(request, "admin_citizenship");
            usin = ServletRequestUtils.getRequiredLongParameter(request, "admin_usin");

            if (adminid != 0) {
                personobj.setAdminid(adminid);
            }
            personobj.setFirstname(first_name);
            personobj.setMiddlename(middle_name);
            personobj.setLastname(last_name);
            personobj.setPhonenumber(mobile_number);
            personobj.setCitizenship(citizenship);
            personobj.setAddress(address);
            personobj.setActive(true);
            if (age != 0) {
                personobj.setAge(age);
            }
            if (genderid != 0) {
                Gender genderIdObj = landRecordsService.findGenderById(genderid);
                personobj.setGender(genderIdObj);

            }

            personobj.setResident(false);
            if (resident == 1) {
                personobj.setResident(true);

            }

            if (maritalid != 0) {

                MaritalStatus maritalIdObj = landRecordsService.findMaritalById(maritalid);
                personobj.setMaritalstatus(maritalIdObj);
            }
            //landRecordsService.editAdmin(personobj);

            //For updating Spacial Unit Administrator
            if (adminid != 0) {
                return landRecordsService.editAdmin(personobj);

            } else {

                SpatialunitPersonadministrator spaobj = new SpatialunitPersonadministrator();
                spaobj.setPersonAdministrator(personobj);
                spaobj.setUsin(usin);
                landRecordsService.updateSpatialAdmin(spaobj);
                return personobj;
            }

            //For updating in Attribute Values Table
            /*try {
				userDataService.updateNaturalPersonAttribValues(personobj,project_name);
			} catch (Exception e) {
				logger.error(e);
			}*/
        } catch (ServletRequestBindingException e) {
            logger.error(e);
            return null;
        }

    }

    @RequestMapping(value = "/viewer/landrecords/deleteadmin/{id}", method = RequestMethod.GET)
    @ResponseBody
    public boolean deleteAdmin(@PathVariable Long id) {

        return landRecordsService.deleteAdminById(id);

    }

    @RequestMapping(value = "/viewer/landrecords/existingadmin/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<PersonAdministrator> deletedAdminList(@PathVariable Long id) {
        List<Long> adminList = landRecordsService.getAdminId(id);
        List<PersonAdministrator> personadminList = new ArrayList<PersonAdministrator>();
        if (adminList != null) {
            for (Long adminID : adminList) {

                PersonAdministrator personadmin = landRecordsService.getAdministratorById(adminID);
                if (!personadmin.getActive()) {
                    personadminList.add(personadmin);
                }
            }
        }
        return personadminList;
    }

    @RequestMapping(value = "/viewer/landrecords/addadmin/{adminId}", method = RequestMethod.GET)
    @ResponseBody
    public boolean existingadminList(@PathVariable Long adminId) {
        return landRecordsService.addAdminById(adminId);

    }

    @RequestMapping(value = "/viewer/landrecords/naturalcustom/{project}", method = RequestMethod.GET)
    @ResponseBody
    public List<String> naturalCustom(@PathVariable String project) {

        return landRecordsService.findnaturalCustom(project);

    }

    @RequestMapping(value = "/viewer/landrecords/personofinterest/{usin}", method = RequestMethod.GET)
    @ResponseBody
    public List<String> personWithInterest(@PathVariable Long usin) {
        ArrayList<String> tempList = new ArrayList<String>();
        List<SpatialunitPersonwithinterest> personList = landRecordsService.findpersonInterestByUsin(usin);
        for (int i = 0; i < personList.size(); i++) {
            tempList.add(personList.get(i).getPersonName());

        }
        return tempList;
    }

    @RequestMapping(value = "/viewer/landrecords/deceasedperson/{usin}", method = RequestMethod.GET)
    @ResponseBody
    public List<SpatialunitDeceasedPerson> deceasedPerson(@PathVariable Long usin) {
        return landRecordsService.findDeceasedPersonByUsin(usin);
    }

    @RequestMapping(value = "/viewer/landrecords/refer/{usin}", method = RequestMethod.GET)
    @ResponseBody
    public String referClaim(@PathVariable Long usin, Principal principal) {
        try {
            SpatialUnitTable claim = landRecordsService.getSpatialUnit(usin);
            if (claim == null) {
                return "Claim was not found";
            }

            // Check parcel for appropriate status and type
            if ((claim.getClaimType().getCode().equalsIgnoreCase(ClaimType.CODE_NEW)
                    || claim.getClaimType().getCode().equalsIgnoreCase(ClaimType.CODE_DISPUTED))
                    && claim.getStatus().getWorkflowStatusId() == Status.STATUS_NEW) {

                User user = userService.findByUniqueName(principal.getName());

                if (landRecordsService.referClaim(claim, user.getId())) {
                    return RESPONSE_OK;
                } else {
                    return "Failed to refer claim";
                }
            } else {
                return "This claim cannot be referred";
            }
        } catch (Exception e) {
            logger.error(e);
            return "Failed to refer claim";
        }
    }

    @RequestMapping(value = "/viewer/landrecords/makevalidated/{usin}", method = RequestMethod.GET)
    @ResponseBody
    public List<String> makeClaimValidated(@PathVariable Long usin, Principal principal) {
        List<String> errors = new ArrayList<>();

        try {
            SpatialUnitTable claim = landRecordsService.getSpatialUnit(usin);
            errors = validateClaim(claim, true);

            if (errors != null && errors.size() > 0) {
                return errors;
            }

            if (claim.getClaimType().getCode().equalsIgnoreCase(ClaimType.CODE_NEW)
                    && claim.getStatus().getWorkflowStatusId() == Status.STATUS_NEW) {
                User user = userService.findByUniqueName(principal.getName());
                if (StringUtils.isEmpty(claim.getPropertyno())) {
                    // Generate UKA number
                    claim.setPropertyno(generateUkaNumber(claim));
                }
                if (!landRecordsService.makeClaimValidated(claim, user.getId())) {
                    errors.add("Failed to make claim validated");
                }
            } else {
                errors.add("This claim cannot be marked as validated");
            }

            return errors;

        } catch (Exception e) {
            logger.error(e);
            errors.add("Failed to make claim validated");
            return errors;
        }
    }

    @RequestMapping(value = "/viewer/landrecords/approve/{usin}", method = RequestMethod.GET)
    @ResponseBody
    public List<String> approveClaim(@PathVariable Long usin, Principal principal) {
        List<String> errors = new ArrayList<>();

        try {
            SpatialUnitTable claim = landRecordsService.getSpatialUnit(usin);
            errors = validateClaim(claim, false);

            if (errors != null && errors.size() > 0) {
                return errors;
            }

            if ((claim.getClaimType().getCode().equalsIgnoreCase(ClaimType.CODE_NEW)
                    && claim.getStatus().getWorkflowStatusId() == Status.STATUS_VALIDATED)
                    || (claim.getClaimType().getCode().equalsIgnoreCase(ClaimType.CODE_EXISTING)
                    && claim.getStatus().getWorkflowStatusId() == Status.STATUS_NEW)) {

                if (StringUtils.isEmpty(claim.getPropertyno())) {
                    // Generate UKA number
                    claim.setPropertyno(generateUkaNumber(claim));
                }

                User user = userService.findByUniqueName(principal.getName());

                if (!landRecordsService.approveClaim(claim, user.getId())) {
                    errors.add("Failed to make claim validated");
                }
            } else {
                errors.add("This claim cannot be marked as validated");
            }

            return errors;

        } catch (Exception e) {
            logger.error(e);
            errors.add("Failed to make claim validated");
            return errors;
        }
    }

    private List<String> validateClaim(SpatialUnitTable claim, boolean validateStatus) {
        List<String> errors = new ArrayList<>();

        try {
            if (claim == null) {
                errors.add("Claim was not found");
                return errors;
            }

            long usin = claim.getUsin();

            // Check claim type
            if (!claim.getClaimType().getCode().equalsIgnoreCase(ClaimType.CODE_NEW)
                    && !claim.getClaimType().getCode().equalsIgnoreCase(ClaimType.CODE_EXISTING)) {
                errors.add("This type of claim cannot be validated.");
                return errors;
            }

            // Check claim status
            if (validateStatus) {
                if (claim.getStatus().getWorkflowStatusId() != Status.STATUS_NEW && claim.getStatus().getWorkflowStatusId() != Status.STATUS_REFERRED) {
                    errors.add("Claim with status \"" + claim.getStatus().getWorkflowStatus() + "\" cannot be validated");
                    return errors;
                }
            }

            // UKA Number
            if (claim.getStatus().getWorkflowStatusId() == Status.STATUS_VALIDATED && StringUtils.isEmpty(claim.getPropertyno())) {
                errors.add("UKA number must be assigned");
                return errors;
            }

            // Check for any disputes
            List<Dispute> disputes = landRecordsService.getDisputes(usin);
            if (disputes != null && disputes.size() > 0) {
                for (Dispute dispute : disputes) {
                    if (dispute.getStatus().getCode() == DisputeStatus.STATUS_ACTIVE) {
                        errors.add("There are unresolved disputes found. You have to resolve them first.");
                        return errors;
                    }
                }
            }

            // Validate rights
            List<SocialTenureRelationship> rights = landRecordsService.findAllSocialTenureByUsin(usin);
            if (rights == null || rights.size() < 1) {
                errors.add("No ownership rights found");
                return errors;
            }

            // Check general properties
            if (claim.getExistingUse() == null) {
                errors.add("Select Existing land use");
            }
            if (claim.getProposedUse() == null) {
                errors.add("Select Proposed land use");
            }

            // Get deceased persons
            List<SpatialunitDeceasedPerson> deceasedPersons = landRecordsService.findDeceasedPersonByUsin(usin);

            // Check rights
            int owners = 0;
            int admins = 0;
            int guardians = 0;
            int nonNatural = 0;
            boolean hasRepresentative = false;

            for (SocialTenureRelationship right : rights) {
                if (right.getPerson_gid() != null) {
                    if (right.getPerson_gid().getPerson_type_gid().getPerson_type_gid() != PersonType.TYPE_NATURAL) {
                        nonNatural += 1;
                        NonNaturalPerson nonPerson = (NonNaturalPerson) right.getPerson_gid();
                        if (nonPerson.getPoc_gid() != null && nonPerson.getPoc_gid() > 0) {
                            hasRepresentative = true;
                        }
                    } else {
                        NaturalPerson person = (NaturalPerson) right.getPerson_gid();
                        if (person.getPersonSubType() != null) {
                            if (person.getPersonSubType().getPerson_type_gid() == PersonType.TYPE_ADMINISTRATOR) {
                                admins += 1;
                            }
                            if (person.getPersonSubType().getPerson_type_gid() == PersonType.TYPE_OWNER) {
                                owners += 1;
                            }
                            if (person.getPersonSubType().getPerson_type_gid() == PersonType.TYPE_GUARDIAN) {
                                guardians += 1;
                            }
                        }
                    }
                }
            }

            int i = 0;

            for (SocialTenureRelationship right : rights) {
                // Validate only first right, considering that all others must be the same
                if (i == 0) {
                    if (right.getTenureclassId() == null) {
                        errors.add("Enter type of right.");
                    }

                    if (right.getShare_type() == null) {
                        errors.add("Select tenure type");
                    }

                    // For existing claims
                    if (claim.getClaimType().getCode().equalsIgnoreCase(ClaimType.CODE_EXISTING)) {
                        if (right.getJuridicalArea() == null || right.getJuridicalArea() == 0) {
                            errors.add("Enter Juridical area more than 0");
                        }
                    }

                    if (right.getPerson_gid() == null) {
                        errors.add("Add at least 1 person");
                    }

                    // Check share type
                    if (right.getShare_type() != null) {
                        if (right.getShare_type().getGid() != ShareType.SHARE_INSTITUTION && nonNatural > 0) {
                            errors.add("Only natural persons are allowed for " + right.getShare_type().getShareType());
                        }

                        if (right.getShare_type().getGid() == ShareType.SHARE_SINGLE) {
                            // Only 1 natural person is allowed
                            if (owners == 0) {
                                errors.add("Add owner");
                            }
                            if (owners > 1) {
                                errors.add("Only 1 owner is allowed for " + right.getShare_type().getShareType());
                            }
                            if (admins > 0) {
                                errors.add("Administrators are not allowed for " + right.getShare_type().getShareType());
                            }
                            if (guardians > 0) {
                                errors.add("Guardians are not allowed for " + right.getShare_type().getShareType());
                            }
                        }

                        if (right.getShare_type().getGid() == ShareType.SHARE_ADMINISTRATOR) {
                            // 1 or many owners, max 2 admins and deceased person
                            if (deceasedPersons == null || deceasedPersons.size() < 0) {
                                errors.add("Add deceased person");
                            }
                            if (admins == 0) {
                                errors.add("Add at least 1 administrator");
                            }
                            if (admins > 2) {
                                errors.add("Maximum 2 administrators are allowed for " + right.getShare_type().getShareType());
                            }
                            if (guardians > 0) {
                                errors.add("Guardians are not allowed for " + right.getShare_type().getShareType());
                            }
                        }

                        if (right.getShare_type().getGid() == ShareType.SHARE_GUARDIAN) {
                            // 1 or many owners (minors), max 2 guardians
                            if (owners == 0) {
                                errors.add("Add at least 1 owner (minor)");
                            }
                            if (guardians == 0) {
                                errors.add("Add at least 1 guardian");
                            }
                            if (guardians > 2) {
                                errors.add("Maximum 2 guardians are allowed for " + right.getShare_type().getShareType());
                            }
                            if (admins > 0) {
                                errors.add("Administrators are not allowed for " + right.getShare_type().getShareType());
                            }
                        }

                        if (right.getShare_type().getGid() == ShareType.SHARE_MULTIPLE_COMMON) {
                            // At least 2 owners 
                            if (owners < 2) {
                                errors.add("Add at least 2 owners");
                            }
                            if (admins > 0) {
                                errors.add("Administrators are not allowed for " + right.getShare_type().getShareType());
                            }
                            if (guardians > 0) {
                                errors.add("Guardians are not allowed for " + right.getShare_type().getShareType());
                            }
                        }

                        if (right.getShare_type().getGid() == ShareType.SHARE_MULTIPLE_JOINT) {
                            // 2 owners 
                            if (owners == 0) {
                                errors.add("Add 2 owners");
                            }
                            if (owners > 2) {
                                errors.add("Maximum 2 owners are allowed for " + right.getShare_type().getShareType());
                            }
                            if (admins > 0) {
                                errors.add("Administrators are not allowed for " + right.getShare_type().getShareType());
                            }
                            if (guardians > 0) {
                                errors.add("Guardians are not allowed for " + right.getShare_type().getShareType());
                            }
                        }

                        if (right.getShare_type().getGid() == ShareType.SHARE_INSTITUTION) {
                            // 1 non natural person should be 
                            if (nonNatural == 0) {
                                errors.add("Add 1 Non Natural person");
                            }
                            if (nonNatural > 1) {
                                errors.add("Maximum 1 Non Natural person is allowed for " + right.getShare_type().getShareType());
                            }
                            if (!hasRepresentative) {
                                errors.add("One representative person has to be added");
                            }
                            if (owners > 0) {
                                errors.add("Natural persons as owners are not allowed for " + right.getShare_type().getShareType());
                            }
                            if (admins > 0) {
                                errors.add("Administrators are not allowed for " + right.getShare_type().getShareType());
                            }
                            if (guardians > 0) {
                                errors.add("Guardians are not allowed for " + right.getShare_type().getShareType());
                            }
                        }
                    }
                }

                i += 1;

                // Check person
                if (right.getShare_type() != null && right.getPerson_gid() != null) {

                    NonNaturalPerson nonPerson = null;
                    NaturalPerson person = null;

                    if (right.getPerson_gid().getPerson_type_gid().getPerson_type_gid() != PersonType.TYPE_NATURAL) {
                        nonPerson = (NonNaturalPerson) right.getPerson_gid();
                        if (nonPerson.getPoc_gid() != null && nonPerson.getPoc_gid() > 0) {
                            person = (NaturalPerson) landRecordsService.findPersonGidById(nonPerson.getPoc_gid());
                        }
                    } else {
                        person = (NaturalPerson) right.getPerson_gid();
                    }

                    // Validate non natural
                    if (nonPerson != null) {
                        if (StringUtils.isEmpty(nonPerson.getInstitutionName())) {
                            errors.add("Enter Non Natural person name");
                        }
                        if (StringUtils.isEmpty(nonPerson.getAddress())) {
                            errors.add("Enter Non Natural person address");
                        }
                        if (nonPerson.getGroupType() == null) {
                            errors.add("Select type of Non Natural person");
                        }
                    }

                    // Validate natural
                    if (person != null) {
                        if (StringUtils.isEmpty(person.getFirstName())) {
                            errors.add("Enter first name for " + getPersonName(person));
                        }
                        if (StringUtils.isEmpty(person.getLastName())) {
                            errors.add("Enter last name for " + getPersonName(person));
                        }
                        if (person.getPersonSubType() == null && right.getShare_type().getGid() != ShareType.SHARE_INSTITUTION) {
                            errors.add("Select owner type for " + getPersonName(person));
                        }
                        if (person.getIdType() == null) {
                            errors.add("Select ID type for " + getPersonName(person));
                        }
                        if (StringUtils.isEmpty(person.getIdNumber())) {
                            errors.add("Enter ID number for " + getPersonName(person));
                        }
                        if (person.getGender() == null) {
                            errors.add("Select gender for " + getPersonName(person));
                        }
                        if (person.getDob() == null) {
                            errors.add("Enter date of birth for " + getPersonName(person));
                        }
                        if (person.getCitizenship_id() == null) {
                            errors.add("Select citizenship for " + getPersonName(person));
                        }
                        if (person.getMarital_status() == null) {
                            errors.add("Select marital status for " + getPersonName(person));
                        }

                        // Check share size
                        if (right.getShare_type().getGid() == ShareType.SHARE_MULTIPLE_COMMON
                                && person.getPersonSubType() != null 
                                && person.getPersonSubType().getPerson_type_gid() == PersonType.TYPE_OWNER
                                && StringUtils.isEmpty(person.getShare())) {
                            errors.add("Enter share size for " + getPersonName(person));
                        }

                        // Check age
                        int age = 0;

                        // Give priority to dob to calculate age
                        if (person.getDob() != null) {
                            age = DateUtils.getAge(person.getDob());
                        } else {
                            age = person.getAge();
                        }

                        if (person.getPersonSubType() != null && person.getPersonSubType().getPerson_type_gid() == PersonType.TYPE_OWNER
                                && right.getShare_type().getGid() == ShareType.SHARE_GUARDIAN) {
                            if (age > 17) {
                                errors.add(getPersonName(person) + " age must be less than 18 years");
                            }
                        } else if (age < 18 || age > 110) {
                            errors.add(getPersonName(person) + " age must be between 18 and 110 years");
                        }

                        // Check person photo
                        SourceDocument photo = landRecordsService.getdocumentByPerson(person.getPerson_gid());
                        if (photo == null) {
                            errors.add("Add photo for " + getPersonName(person));
                        }
                    }
                }
            }

            return errors;

        } catch (Exception e) {
            logger.error(e);
            errors.clear();
            errors.add("Failed to validate claim");
            return errors;
        }
    }

    private String getPersonName(NaturalPerson person) {
        String name = "";
        if (!StringUtils.isEmpty(person.getFirstName())) {
            name = person.getFirstName();
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

    private String generateUkaNumber(SpatialUnitTable claim) {
        Project project = projectService.findProjectByName(claim.getProject());
        String village = project.getProjectAreas().get(0).getVillage_code();

        try {
            return landRecordsService.findNextUkaNumber(village + "/" + claim.getHamlet_Id().getHamletCode() + "/");
        } catch (Exception e) {
            logger.error(e);
            return null;
        }
    }

    @RequestMapping(value = "/viewer/landrecords/updatehamlet/{project}", method = RequestMethod.POST)
    @ResponseBody
    public boolean updateUkaNum(HttpServletRequest request, HttpServletResponse response, @PathVariable String project) {
        try {
            long usin = ServletRequestUtils.getRequiredLongParameter(request, "primeryky");
            long hamletId = ServletRequestUtils.getRequiredLongParameter(request, "cbxHamlets");

            SpatialUnitTable claim = landRecordsService.findSpatialUnitbyId(usin).get(0);

            if (claim.getHamlet_Id().getId() == hamletId) {
                return true;
            }

            ProjectHamlet hamlet = projectService.findHamletById(hamletId);
            long oldHamletId = claim.getHamlet_Id().getId();

            claim.setHamlet_Id(hamlet);

            if (!StringUtils.isEmpty(claim.getPropertyno()) && hamletId != oldHamletId) {
                claim.setPropertyno(generateUkaNumber(claim));
            }

            return landRecordsService.update(claim);

        } catch (ServletRequestBindingException e) {
            logger.error(e);
            return false;
        }
    }

    @RequestMapping(value = "/viewer/landrecords/personwithinterest/{usin}", method = RequestMethod.GET)
    @ResponseBody
    public List<SpatialunitPersonwithinterest> nxtTokin(@PathVariable Long usin) {
        return landRecordsService.findpersonInterestByUsin(usin);
    }

    @RequestMapping(value = "/viewer/landrecords/poi/{id}", method = RequestMethod.GET)
    @ResponseBody
    public SpatialunitPersonwithinterest getPoi(@PathVariable Long id) {
        return landRecordsService.getPoi(id);
    }

    @RequestMapping(value = "/viewer/landrecords/updatepwi", method = RequestMethod.POST)
    @ResponseBody
    public boolean updatepwi(HttpServletRequest request, HttpServletResponse response) {
        long Usin = 0;
        String name;
        String dob;
        Date dobDate = null;
        long relationshipType;
        long genderCode;
        long id = 0;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SpatialunitPersonwithinterest spi = new SpatialunitPersonwithinterest();

        try {
            Usin = ServletRequestUtils.getRequiredLongParameter(request, "usin_kin");
            name = ServletRequestUtils.getRequiredStringParameter(request, "name_kin");
            id = ServletRequestUtils.getRequiredLongParameter(request, "id_kin");
            genderCode = ServletRequestUtils.getRequiredLongParameter(request, "poiGender");
            relationshipType = ServletRequestUtils.getRequiredLongParameter(request, "poiRelType");
            try {
                dob = ServletRequestUtils.getRequiredStringParameter(request, "poiDob");
                if (!StringUtils.isEmpty(dob)) {
                    dobDate = dateFormat.parse(dob);
                }
            } catch (Exception e) {
                logger.error(e);
            }

            if (id != 0) {
                spi.setId(id);
            }
            spi.setPersonName(name);
            spi.setUsin(Usin);
            spi.setDob(dobDate);

            if (genderCode != 0) {
                spi.setGender(landRecordsService.findGenderById(genderCode));
            } else {
                spi.setGender(null);
            }

            if (relationshipType > 0) {
                spi.setRelationshipType(landRecordsService.findRelationshipType(relationshipType));
            } else {
                spi.setRelationshipType(null);
            }

            return landRecordsService.addnxtTokin(spi);
        } catch (ServletRequestBindingException e) {
            logger.error(e);
            return false;
        }
    }

    @RequestMapping(value = "/viewer/landrecords/deletekin/{id}", method = RequestMethod.GET)
    @ResponseBody
    public boolean deletePersonWithInterest(@PathVariable Long id) {

        return landRecordsService.deletePersonWithInterest(id);

    }

    @RequestMapping(value = "/viewer/landrecords/personsubtype", method = RequestMethod.GET)
    @ResponseBody
    public List<PersonType> PersonTypelst() {

        return landRecordsService.AllPersonType();

    }

    @RequestMapping(value = "/viewer/landrecords/hamletbyusin/{usin}", method = RequestMethod.GET)
    @ResponseBody
    public List<SpatialUnitTable> hamletName(@PathVariable long usin) {

        return landRecordsService.findSpatialUnitbyId(usin);

    }

    @RequestMapping(value = "/viewer/landrecords/updatedeceased", method = RequestMethod.POST)
    @ResponseBody
    public boolean updateDeceasedPerson(HttpServletRequest request, HttpServletResponse response) {
        long Usin = 0;
        String firstname = "";
        String middlename = "";
        String lastname = "";
        long id = 0;

        SpatialunitDeceasedPerson spdeceased = new SpatialunitDeceasedPerson();

        try {
            Usin = ServletRequestUtils.getRequiredLongParameter(request, "usin_deceased");
            firstname = ServletRequestUtils.getRequiredStringParameter(request, "d_firstname");
            middlename = ServletRequestUtils.getRequiredStringParameter(request, "d_middlename");
            lastname = ServletRequestUtils.getRequiredStringParameter(request, "d_lastname");
            id = ServletRequestUtils.getRequiredLongParameter(request, "deceased_key");

            if (id != 0) {
                spdeceased.setId(id);
            }
            spdeceased.setFirstname(firstname);
            spdeceased.setMiddlename(middlename);
            spdeceased.setLastname(lastname);
            spdeceased.setUsin(Usin);

            return landRecordsService.saveDeceasedPerson(spdeceased);

        } catch (ServletRequestBindingException e) {

            logger.error(e);
            return false;

        }
    }

    @RequestMapping(value = "/viewer/landrecords/deletedeceased/{id}", method = RequestMethod.GET)
    @ResponseBody
    public boolean deleteDeceasedPerson(@PathVariable Long id) {

        return landRecordsService.deleteDeceasedPerson(id);

    }

    @RequestMapping(value = "/viewer/landrecords/deceasedpersonbyid/{usin}", method = RequestMethod.GET)
    @ResponseBody
    public ArrayList<String> deceasedPersonAdjudication(@PathVariable Long usin) {
        ArrayList<String> tempList = new ArrayList<String>();
        List<SpatialunitDeceasedPerson> deceasedobj = landRecordsService.findDeceasedPersonByUsin(usin);
        for (int i = 0; i < deceasedobj.size(); i++) {

            String name = deceasedobj.get(i).getFirstname() + " " + deceasedobj.get(i).getMiddlename() + " " + deceasedobj.get(i).getLastname();
            tempList.add(name);
        }
        return tempList;
    }

    @RequestMapping(value = "/viewer/landrecords/vertexlabel", method = RequestMethod.POST)
    @ResponseBody
    public boolean vertexLabel(HttpServletRequest request, HttpServletResponse response) {

        try {
            String vertexData = request.getParameter("vertexList");
            //ArrayList<Double> bbox=new ArrayList<Double>();
            if (landRecordsService.deleteAllVertexLabel()) {

                String[] arr = vertexData.split(",");
                int serialId = 1;
                for (int i = 0; i < arr.length;) {

                    landRecordsService.addAllVertexLabel(serialId, arr[i + 1], arr[i]);
                    serialId++;
                    i = i + 2;
                }

            }
            return true;
        } catch (Exception e) {
            logger.error(e);
            return false;
        }

    }

    @RequestMapping(value = "/viewer/landrecords/updateshare", method = RequestMethod.POST)
    @ResponseBody
    public boolean updateShare(HttpServletRequest request, HttpServletResponse response) {
        int length_share = 0;

        try {
            length_share = ServletRequestUtils.getRequiredIntParameter(request, "length_person");

            if (length_share > 0) {
                for (int i = 0; i < length_share; i++) {

                    String alias = "";
                    long personGid = 0;
                    try {
                        alias = ServletRequestUtils.getRequiredStringParameter(request, "alias_person" + i);
                        personGid = ServletRequestUtils.getRequiredLongParameter(request, "person_gid" + i);
                        landRecordsService.updateSharePercentage(alias, personGid);
                    } catch (ServletRequestBindingException e) {
                        logger.error(e);
                        return false;
                    }

                }

                return true;

            } else {
                return false;
            }
        } catch (ServletRequestBindingException e) {
            logger.error(e);
            return false;
        }

    }

// compression method to compress image file
    @SuppressWarnings("unused")
    private String compressPicture(String outDirPath, Integer id, String fileExtension) {

        File compressedImageFile = new File(outDirPath + File.separator + id + "." + fileExtension);
        float k = 1.0f;
        try {
            while (compressedImageFile.length() > 50 * 1024) {
                File input = new File(outDirPath + File.separator + id + "." + fileExtension);
                BufferedImage image = ImageIO.read(input);

                compressedImageFile = new File(outDirPath + File.separator + id + "." + fileExtension);
                OutputStream os = new FileOutputStream(compressedImageFile);

                Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName(fileExtension);
                ImageWriter writer = (ImageWriter) writers.next();

                ImageOutputStream ios = ImageIO.createImageOutputStream(os);
                writer.setOutput(ios);

                ImageWriteParam param = writer.getDefaultWriteParam();

                param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                param.setCompressionQuality((float) (0.5 * k));
                writer.write(null, new IIOImage(image, null, null), param);

                os.flush();
                os.close();
                ios.flush();
                ios.close();
                writer.dispose();

                k = (float) (k * 0.5);
            }
            return "Success";

        } catch (FileNotFoundException e) {
            logger.error(e);
            return "Error";
        } catch (IOException e) {
            logger.error(e);
            return "Error";
        }

    }

    @RequestMapping(value = "/viewer/landrecords/citizenship/", method = RequestMethod.GET)
    @ResponseBody
    public List<Citizenship> citizenList() {

        return landRecordsService.findAllCitizenShip();

    }

    @RequestMapping(value = "/viewer/landrecords/deletenaturalphoto/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String deleteNaturalImage(@PathVariable Long id) {

        if (landRecordsService.checkActivePerson(id) == false) {
            return "false";
        } else {
            landRecordsService.deleteNaturalImage(id);
            return "true";
        }

    }

}
