package com.rmsi.mast.viewer.web.mvc;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;

import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.rmsi.mast.studio.dao.DisputeStatusDao;
import com.rmsi.mast.studio.dao.DisputeTypeDao;
import com.rmsi.mast.studio.dao.LaExtDisputelandmappingDAO;
import com.rmsi.mast.studio.dao.LandApplicationStatusDAO;
import com.rmsi.mast.studio.dao.OutputformatDAO;
import com.rmsi.mast.studio.dao.ProjectAreaDAO;
import com.rmsi.mast.studio.dao.ProjectDAO;
import com.rmsi.mast.studio.dao.ResourceCustomAttributesDAO;
import com.rmsi.mast.studio.dao.ShareTypeDAO;
import com.rmsi.mast.studio.dao.SocialTenureRelationshipDAO;
import com.rmsi.mast.studio.dao.SourceDocumentDAO;
import com.rmsi.mast.studio.dao.UserRoleDAO;
import com.rmsi.mast.studio.domain.AcquisitionType;
import com.rmsi.mast.studio.domain.AttributeCategory;
import com.rmsi.mast.studio.domain.AttributeValues;
import com.rmsi.mast.studio.domain.Citizenship;
import com.rmsi.mast.studio.domain.ClaimType;
import com.rmsi.mast.studio.domain.Dispute;
import com.rmsi.mast.studio.domain.DisputeStatus;
import com.rmsi.mast.studio.domain.DisputeType;
import com.rmsi.mast.studio.domain.DocumentType;
import com.rmsi.mast.studio.domain.EducationLevel;
import com.rmsi.mast.studio.domain.Gender;
import com.rmsi.mast.studio.domain.GroupType;
import com.rmsi.mast.studio.domain.IdType;
import com.rmsi.mast.studio.domain.LaExtDispute;
import com.rmsi.mast.studio.domain.LaExtDisputelandmapping;
import com.rmsi.mast.studio.domain.LaExtParcelSplitLand;
import com.rmsi.mast.studio.domain.LaExtRegistrationLandShareType;
import com.rmsi.mast.studio.domain.LaExtTransactionHistory;
import com.rmsi.mast.studio.domain.LaExtTransactiondetail;
import com.rmsi.mast.studio.domain.LaParty;
import com.rmsi.mast.studio.domain.LaPartyPerson;
import com.rmsi.mast.studio.domain.LaPartygroupOccupation;
import com.rmsi.mast.studio.domain.LaSpatialunitLand;
import com.rmsi.mast.studio.domain.LandApplicationStatus;
import com.rmsi.mast.studio.domain.LandType;
import com.rmsi.mast.studio.domain.LandUseType;
import com.rmsi.mast.studio.domain.MaritalStatus;
import com.rmsi.mast.studio.domain.NaturalPerson;
import com.rmsi.mast.studio.domain.NonNaturalPerson;
import com.rmsi.mast.studio.domain.OccupancyType;
import com.rmsi.mast.studio.domain.Outputformat;
import com.rmsi.mast.studio.domain.Person;
import com.rmsi.mast.studio.domain.PersonType;
import com.rmsi.mast.studio.domain.Project;
import com.rmsi.mast.studio.domain.ProjectArea;
import com.rmsi.mast.studio.domain.ProjectHamlet;
import com.rmsi.mast.studio.domain.ProjectSpatialData;
import com.rmsi.mast.studio.domain.RelationshipType;
import com.rmsi.mast.studio.domain.ResourceAttributeValues;
import com.rmsi.mast.studio.domain.ResourcePOIAttributeValues;
import com.rmsi.mast.studio.domain.ShareType;
import com.rmsi.mast.studio.domain.SlopeValues;
import com.rmsi.mast.studio.domain.SocialTenureRelationship;
import com.rmsi.mast.studio.domain.SoilQualityValues;
import com.rmsi.mast.studio.domain.SourceDocument;
import com.rmsi.mast.studio.domain.SpatialUnitPersonWithInterest;
import com.rmsi.mast.studio.domain.Status;
import com.rmsi.mast.studio.domain.TenureClass;
import com.rmsi.mast.studio.domain.User;
import com.rmsi.mast.studio.domain.UserRole;
import com.rmsi.mast.studio.domain.fetch.AllocateUser;
import com.rmsi.mast.studio.domain.fetch.AttributeValuesFetch;
import com.rmsi.mast.studio.domain.fetch.ClaimBasic;
import com.rmsi.mast.studio.domain.fetch.Commune;
import com.rmsi.mast.studio.domain.fetch.FarmReport;
import com.rmsi.mast.studio.domain.fetch.LaExtDisputeDTO;
import com.rmsi.mast.studio.domain.fetch.La_spatialunit_aoi;
import com.rmsi.mast.studio.domain.fetch.PersonAdministrator;
import com.rmsi.mast.studio.domain.fetch.PersonForEditing;
import com.rmsi.mast.studio.domain.fetch.PoiReport;
import com.rmsi.mast.studio.domain.fetch.ProjectDetails;
import com.rmsi.mast.studio.domain.fetch.ProjectTemp;
import com.rmsi.mast.studio.domain.fetch.SpatialUnitTable;
import com.rmsi.mast.studio.domain.fetch.SpatialUnitTemp;
import com.rmsi.mast.studio.domain.fetch.SpatialunitDeceasedPerson;
import com.rmsi.mast.studio.domain.fetch.SpatialunitPersonadministrator;
import com.rmsi.mast.studio.domain.fetch.SpatialunitPersonwithinterest;
import com.rmsi.mast.studio.mobile.dao.EducationLevelDao;
import com.rmsi.mast.studio.mobile.dao.GenderDao;
import com.rmsi.mast.studio.mobile.dao.GroupTypeDao;
import com.rmsi.mast.studio.mobile.dao.NaturalPersonDao;
import com.rmsi.mast.studio.mobile.dao.NonNaturalPersonDao;
import com.rmsi.mast.studio.mobile.dao.SocialTenureDao;
import com.rmsi.mast.studio.mobile.dao.SpatialUnitPersonWithInterestDao;
import com.rmsi.mast.studio.mobile.service.SpatialUnitService;
import com.rmsi.mast.studio.mobile.service.UserDataService;
import com.rmsi.mast.studio.service.ClaimBasicService;
import com.rmsi.mast.studio.service.ProjectService;
import com.rmsi.mast.studio.service.UserService;
import com.rmsi.mast.studio.util.DateUtils;
import com.rmsi.mast.studio.util.FileUtils;
import com.rmsi.mast.studio.util.StringUtils;
import com.rmsi.mast.viewer.dao.AllocateUserDao;
import com.rmsi.mast.viewer.dao.LaExtTransactionHistoryDao;
import com.rmsi.mast.viewer.dao.LaExtTransactiondetailDao;
import com.rmsi.mast.viewer.dao.LaPartyDao;
import com.rmsi.mast.viewer.dao.LaPartyPersonDao;
import com.rmsi.mast.viewer.dao.SocialTenureRelationshipDao;
import com.rmsi.mast.viewer.dao.SourceDocumentsDao;
import com.rmsi.mast.viewer.report.ReportsSerivce;
import com.rmsi.mast.viewer.service.LaExtDisputeService;
import com.rmsi.mast.viewer.service.LaExtDisputelandmappingService;
import com.rmsi.mast.viewer.service.LaExtParcelSplitLandService;
import com.rmsi.mast.viewer.service.LaExtRegistrationLandShareTypeService;
import com.rmsi.mast.viewer.service.LandRecordsService;
import com.rmsi.mast.viewer.service.NonNaturalPersonService;
import com.rmsi.mast.viewer.service.RegistrationRecordsService;
import com.rmsi.mast.viewer.service.ResourceAttributeValuesService;
import com.rmsi.mast.viewer.service.SpatialunitPersonwithinterestService;

/**
 * 
 * @author Abhay.Pandey
 *
 */

@Controller
public class LandRecordsController {

    private static final Logger logger = Logger.getLogger(LandRecordsController.class);
    
    @Autowired
    NaturalPersonDao naturalPersonDao;

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
    
    @Autowired
    private DisputeTypeDao disputeTypeDao;  
    
    @Autowired
    private ClaimBasicService claimBasicService;

    
    @Autowired
	RegistrationRecordsService registrationRecordsService;
	
    @Autowired
    SocialTenureRelationshipDao socialTenureRelationshipDao;
  
   
    @Autowired
    LaExtDisputelandmappingDAO laExtDisputelandmappingDAO;
    
    @Autowired
    LaPartyDao laPartyDao;
    
    @Autowired
    LaExtDisputelandmappingService laExtDisputelandmappingService;
    
    @Autowired
    SpatialunitPersonwithinterestService spatialunitPersonwithinterestService;
    
    @Autowired
    SpatialUnitPersonWithInterestDao spatialunitpersonwithinterestdao;
    
    @Autowired
    LaExtDisputeService laExtDisputeService;
    
    @Autowired
    ProjectAreaDAO projectAreaDAO;

    
    @Autowired
    GroupTypeDao groupTypeDao;
    
    
    @Autowired
    UserRoleDAO userRoleDAO;
    
    
    @Autowired
    AllocateUserDao allocateUserDao;

    @Autowired
    SpatialunitPersonwithinterestService spatialUnitPersonWithInterestService;

    @Autowired
    LaPartyPersonDao laPartyPersonDao;
    
    @Autowired
    NonNaturalPersonService  nonNaturalPersonService;
    
    @Autowired
    ProjectDAO projectDAO; 
    
    
    @Autowired
    ShareTypeDAO ShareTypeDao;
    
    @Autowired
    ResourceAttributeValuesService resourceAttributeValuesservice;
    
    @Autowired
    EducationLevelDao educationLevelDao;
    
    @Autowired
    LaExtTransactiondetailDao laExtTransactiondetailDao;
    
    @Autowired
    SourceDocumentsDao sourceDocumentsDao;
    
    @Autowired
	OutputformatDAO Outputformatdao;
    
    @Autowired
    SocialTenureRelationshipDAO socialTenureRelationshipDAO;
    
    @Autowired
    LaExtTransactionHistoryDao laExtTransactionHistorydao;
    
    @Autowired
    GenderDao Genderdao;
    
    @Autowired
    SourceDocumentDAO sourcedocdao;
    
    
    @Autowired
	ResourceCustomAttributesDAO resourceCustomAttributesdao;
    
    @Autowired
	ResourceAttributeValuesService resourceAttributeValuesService;
    
    
    @Autowired
    LaExtParcelSplitLandService laExtParcelSplitLandService;
    
    
    
    @Autowired
    LaExtRegistrationLandShareTypeService laExtRegistrationLandShareTypeService;
    
    @Autowired
    LandApplicationStatusDAO landApplicationStatusDAO;
   
    
    public static final String RESPONSE_OK = "OK";


    @RequestMapping(value = "/viewer/landrecords/allprojects/", method = RequestMethod.GET)
    @ResponseBody
    public List<String> getAllProjects() {
        return projectService.getAllProjectNames();
    }
    
    @RequestMapping(value = "/viewer/landrecordsdetails/getAllProjectsdetailsCall/", method = RequestMethod.GET)
    @ResponseBody
    public List<Project> getdAllProjectsdetails() {
        return projectService.getdAllProjectsdetails();
    }
    
    
    @RequestMapping(value = "/viewer/landrecords/personsforediting/{projectName}", method = RequestMethod.GET)
    @ResponseBody
    public List<PersonForEditing> getPersonsForEditing(HttpServletRequest request, @PathVariable String projectName) {
    	String firstName = ServletRequestUtils.getStringParameter(request, "firstName", "");
        String lastName = ServletRequestUtils.getStringParameter(request, "lastName", "");
        String middleName = ServletRequestUtils.getStringParameter(request, "middleName", "");
        String idNumber = ServletRequestUtils.getStringParameter(request, "idNumber", "");
        Integer claimNumber = ServletRequestUtils.getIntParameter(request, "claimNumber", 0);
        String neighbourN = ServletRequestUtils.getStringParameter(request, "neighbourNorth", "");
        String neighbourS = ServletRequestUtils.getStringParameter(request, "neighbourSouth", "");
        String neighbourE = ServletRequestUtils.getStringParameter(request, "neighbourEast", "");
        String neighbourW = ServletRequestUtils.getStringParameter(request, "neighbourWest", "");
        Long landid = ServletRequestUtils.getLongParameter(request, "landid", 0);

        return landRecordsService.getPersonsForEditing(projectName, landid, firstName, lastName, middleName, idNumber, claimNumber, neighbourN, neighbourS, neighbourE, neighbourW);
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

    
    @RequestMapping(value = "/viewer/landrecords/savePersonOfInterestForEditing/{landId}/{transactionid}", method = RequestMethod.POST)
    @ResponseBody
    public SpatialUnitPersonWithInterest savePersonOfInterestForEditing(HttpServletRequest request, @PathVariable Long landId,@PathVariable Long transactionid, Principal principal) {
        
    	SpatialUnitPersonWithInterest personinterest =null;
    	Date date1 =null;
    	try {
        	Integer PoiId =0;
        	Integer genderid =0;
        	Integer realtionid =0;
        	String dateofbirth ="";
        	String firstname ="";
        	String middlename ="";
        	String lastname ="";
        	
        	
			try{firstname =  ServletRequestUtils.getRequiredStringParameter(request, "firstName");}catch(Exception e){}
			try{middlename =ServletRequestUtils.getRequiredStringParameter(request, "middleName");}catch(Exception e){}
			try{lastname =ServletRequestUtils.getRequiredStringParameter(request, "lastName");}catch(Exception e){}
			try{genderid =ServletRequestUtils.getRequiredIntParameter(request, "gender");}catch(Exception e){}
			try{realtionid= ServletRequestUtils.getRequiredIntParameter(request, "relation");}catch(Exception e){}
			try{dateofbirth=ServletRequestUtils.getRequiredStringParameter(request, "dob");}catch(Exception e){}
			try{PoiId=ServletRequestUtils.getRequiredIntParameter(request, "id");}catch(Exception e){}
			
			if(dateofbirth != ""){
				
				 DateFormat inputFormat = new SimpleDateFormat(
					        "E MMM dd yyyy HH:mm:ss 'GMT'z", Locale.ENGLISH);
					     date1 = inputFormat.parse(dateofbirth);
    		
    		
//			 date1=new SimpleDateFormat("yyyy-MM-dd").parse(finaldob);
			}

			personinterest = spatialUnitPersonWithInterestService.findSpatialUnitPersonWithInterestById(PoiId.longValue());
        	
			if(null ==personinterest){
            	personinterest = new SpatialUnitPersonWithInterest();
            	 personinterest.setFirstName(firstname);
                 personinterest.setMiddleName(middlename);
                 personinterest.setLastName(lastname);
                 personinterest.setDob(date1);
                 personinterest.setGender(genderid);
                 personinterest.setRelation(realtionid);
 	           personinterest.setLandid(landId);
 	          personinterest.setCreatedby(1);
 	         personinterest.setCreateddate(new Date());
 	        personinterest.setIsactive(true);
 	        personinterest.setTransactionid(transactionid.intValue());
            }
            else{
            personinterest.setFirstName(firstname);
            personinterest.setMiddleName(middlename);
            personinterest.setLastName(lastname);
            personinterest.setDob(date1);
            personinterest.setGender(genderid);
            personinterest.setRelation(realtionid);
            }
    		
			spatialUnitPersonWithInterestService.save(personinterest);
        	
            return personinterest;
        } catch (Exception e) {
            logger.error(e);
            return null;
        }
    }
    
    
    // Non-Natural Person Update
    
    @RequestMapping(value = "/viewer/landrecords/saveNonNaturalPersonForEditing", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity saveNonNaturalPersonForEditing(HttpServletResponse response, @RequestBody NonNaturalPerson pfe) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(nonNaturalPersonService.findByObject(pfe));
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
    
    	List<ShareType> sharetype = new ArrayList<ShareType>();
    	List<ShareType> sharetypelist = landRecordsService.findAllTenureList();
    	for(ShareType obj: sharetypelist){
    		if(obj.getIsactive()){
    			sharetype.add(obj);
    		}
    		
    	}
    	
        return sharetype;

         

    }

    @RequestMapping(value = "/viewer/landrecords/socialtenure/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<SocialTenureRelationship> socialTenureList(@PathVariable Long id) {
  
    	 List<SocialTenureRelationship>  lst= new ArrayList<SocialTenureRelationship>();
    	 lst= landRecordsService.findAllSocialTenureByUsin(id);;
    	 if(lst.size()>0)
    	 {
    		 for(SocialTenureRelationship obj:lst){
    			 
    			 LaParty objLaParty=laPartyDao.getPartyIdByID(obj.getPartyid()); 
    			 obj.setLaParty(objLaParty);
    		 }
    	 }
    	 
        return lst;
    }

    @RequestMapping(value = "/viewer/landrecords/spatialunitlist/", method = RequestMethod.GET)
    @ResponseBody
    public List<SpatialUnitTable> allspatialUnitList() {

        return landRecordsService.findAllSpatialUnitlist();

    }//

    @RequestMapping(value = "/viewer/landrecords/editattribute/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<ClaimBasic> editAttribute(@PathVariable Long id) {

        try {
        	List<ClaimBasic> listobject= new ArrayList<ClaimBasic>();
			 listobject=spatialUnitService.getClaimsBasicByLandId(id);
			 if(null!=listobject){
				 return listobject;
			 }else{
			 return null;
			 }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

    }

    @RequestMapping(value = "/viewer/landrecords/updateattributes", method = RequestMethod.POST)
    @ResponseBody
    public boolean updateAttributes(HttpServletRequest request, HttpServletResponse response) {
        try {
            long Usin = ServletRequestUtils.getRequiredLongParameter(request, "primary");
            int existingUse = ServletRequestUtils.getRequiredIntParameter(request, "existing_use");
            int proposedUse = ServletRequestUtils.getRequiredIntParameter(request, "proposed_use");
            int claimNumber = ServletRequestUtils.getRequiredIntParameter(request, "claimNumber");
            int aquisitiontype = ServletRequestUtils.getRequiredIntParameter(request, "aquisition_id");
            String other_use = ServletRequestUtils.getRequiredStringParameter(request, "other_use");
          //  int length_general = ServletRequestUtils.getRequiredIntParameter(request, "general_length");
        
        
            String neighbour_north = ServletRequestUtils.getRequiredStringParameter(request, "neighbor_north");
            String neighbour_south = ServletRequestUtils.getRequiredStringParameter(request, "neighbor_south");
            String neighbour_east = ServletRequestUtils.getRequiredStringParameter(request, "neighbor_east");
            String neighbour_west = ServletRequestUtils.getRequiredStringParameter(request, "neighbor_west");
            int landType = ServletRequestUtils.getRequiredIntParameter(request, "land_type");
            int tenureType=ServletRequestUtils.getRequiredIntParameter(request, "tenure_type"); // share 
            int tenureclass_id=ServletRequestUtils.getRequiredIntParameter(request, "tenureclass_id"); //TenureClass
            int tenureDuration =ServletRequestUtils.getRequiredIntParameter(request, "tenureDuration"); 
            String Area =ServletRequestUtils.getRequiredStringParameter(request, "area"); 
            ClaimBasic spatialUnit = spatialUnitService.getClaimsBasicByLandId(Usin).get(0);
            
            Double area = new Double(Area);
            
            
            if(tenureType>0){
            	ShareType objShareType=	ShareTypeDao.getShareTypeById(tenureType);
            	spatialUnit.setLaRightLandsharetype(objShareType);
            }
            if(aquisitiontype>0){
            	AcquisitionType aquisitionobj= new AcquisitionType();
            	aquisitionobj.setAcquisitiontypeid(aquisitiontype);
            	spatialUnit.setLaRightAcquisitiontype(aquisitionobj);
            }
            if(tenureclass_id>0){
            	spatialUnit.setTenureclassid(tenureclass_id);
            }
            
            if(tenureDuration>0){
            	LandApplicationStatus landApplicationStatus = landApplicationStatusDAO.getLandApplicationStatusByLandId(Usin);
            		if(null!=landApplicationStatus){
            			landApplicationStatus.setOccupancylength(tenureDuration);
            			landApplicationStatusDAO.addLandApplicationStatus(landApplicationStatus);
            			}
            }
            
            spatialUnit.setClaimno(claimNumber);
            
            if(proposedUse>0){
            	spatialUnit.setProposedused(proposedUse);
            }
            
            spatialUnit.setNeighborEast(neighbour_east);
            spatialUnit.setNeighborNorth(neighbour_north);
            
            spatialUnit.setNeighborSouth(neighbour_south);
            spatialUnit.setNeighborWest(neighbour_west);
            spatialUnit.setArea(area);
            spatialUnit.setOther_use(other_use);

            if (existingUse != 0) {
                LandUseType existingObj = landRecordsService.findLandUseById(existingUse);
                spatialUnit.setLaBaunitLandusetype(existingObj);
            }

            

          if (landType != 0) {
                LandType landTypeObj = landRecordsService.findLandType(landType);
                spatialUnit.setLaBaunitLandtype(landTypeObj);
            }



            
            claimBasicService.saveClaimBasicDAO(spatialUnit);
            return true;
        } catch (Exception e) {
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
        String projname = null;
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
        	List<ClaimType>  ClaimTypelist = landRecordsService.getClaimTypes();
        	for(ClaimType obj: ClaimTypelist){
        		if(obj.isActive()){
        		list.add(obj);
        		}
        	}
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
    public List<LaParty> naturalPerson(@PathVariable Long id) {
        List<LaParty> persons = laPartyDao.getPartyListIdByID(id);
        return persons;
    }

    @RequestMapping(value = "/viewer/landrecords/nonnaturalperson/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<LaParty> nonnaturalPerson(@PathVariable Long id) {
        return laPartyDao.getPartyListIdByID(id);

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
    public boolean updateNaturalPerson(HttpServletRequest request, HttpServletResponse response, Principal principal) {
        Long id = 0L;
        int genderid = 0;
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
        int newnatural_length = 0;
        long usin = 0;
        long parentNonNaturalId;
        long disputeId;
        int idTypeCode;
        String idNumber;
        String address="";
        
        String username = principal.getName();
		User userObj = userService.findByUniqueName(username);
		
		Long user_id = userObj.getId();
		
        NaturalPerson person = new NaturalPerson();
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

           // parentNonNaturalId = ServletRequestUtils.getLongParameter(request, "parentNonNaturalId", 0);
            disputeId = ServletRequestUtils.getLongParameter(request, "personDisputeId", 0);
            first_name = ServletRequestUtils.getRequiredStringParameter(request, "fname");
            middle_name = ServletRequestUtils.getRequiredStringParameter(request, "mname");
            last_name = ServletRequestUtils.getRequiredStringParameter(request, "lname");
            mobile_number = ServletRequestUtils.getRequiredStringParameter(request, "mobile_natural");
            address  =  ServletRequestUtils.getRequiredStringParameter(request, "address");    
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
            maritalid = ServletRequestUtils.getRequiredIntParameter(request, "marital_status");
            genderid = ServletRequestUtils.getRequiredIntParameter(request, "gender");
           // length_natural = ServletRequestUtils.getRequiredIntParameter(request, "natual_length");
            project_name = ServletRequestUtils.getRequiredStringParameter(request, "projectname_key");
            
            LaParty laParty = new LaParty();
            try {
                newnatural_length = ServletRequestUtils.getRequiredIntParameter(request, "newnatural_length");
            } catch (Exception e) {
                logger.error(e);
            }

            if (disputeId ==0){
            PersonType personType = registrationRecordsService.getPersonTypeById(3);
            laParty.setLaPartygroupPersontype(personType);
        	person.setLaPartygroupPersontype(personType);
            }else
            {
            	PersonType personType = registrationRecordsService.getPersonTypeById(10);
                laParty.setLaPartygroupPersontype(personType);
            	person.setLaPartygroupPersontype(personType);
            }


            if (id != 0) {
            	person.setPartyid(id);
            }else
            {
            	laParty.setCreatedby(user_id.intValue());
     			laParty.setCreateddate(new Date());
     			//laParty.setLaPartygroupPersontype(personType);
     			
     			 person.setModifiedby(user_id.intValue());
                 person.setModifieddate(new Date());
            }
            
            person.setFirstname(first_name);
            person.setMiddlename(middle_name);
            person.setLastname(last_name);

            person.setAddress(address);
            person.setDateofbirth(dobDate);
            person.setIdentityno(idNumber);
            person.setIsactive(true);
            person.setCreatedby(user_id.intValue());
            person.setCreateddate(new Date());
            person.setContactno(mobile_number);
            
            try {
				ProjectArea obj=projectAreaDAO.findProjectAreaByProjectId(Integer.parseInt(project_name));
				
				person.setLaSpatialunitgroup1(obj.getLaSpatialunitgroup1());
				person.setLaSpatialunitgroup2(obj.getLaSpatialunitgroup2());
				person.setLaSpatialunitgroup3(obj.getLaSpatialunitgroup3());
				person.setLaSpatialunitgroup4(obj.getLaSpatialunitgroup4());
				
				person.setLaSpatialunitgroupHierarchy1(obj.getLaSpatialunitgroupHierarchy1());
				person.setLaSpatialunitgroupHierarchy2(obj.getLaSpatialunitgroupHierarchy2());
				person.setLaSpatialunitgroupHierarchy3(obj.getLaSpatialunitgroupHierarchy3());
				person.setLaSpatialunitgroupHierarchy4(obj.getLaSpatialunitgroupHierarchy4());
				
				
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
            
            if (idTypeCode != 0) {
            	IdType idType = registrationRecordsService.getIDTypeDetailsByID(idTypeCode);
                person.setLaPartygroupIdentitytype(idType);
            } else {
                person.setLaPartygroupIdentitytype(null);
            }


            if (genderid != 0) {
                person.setGenderid(genderid);
            }

          
            if (maritalid != 0) {
            	  MaritalStatus maritalStatus = registrationRecordsService.getMaritalStatusByID(maritalid);
                person.setLaPartygroupMaritalstatus(maritalStatus);
            }

         
            //For updating in Attribute Values Table
            person = landRecordsService.editnatural(person);
            
			Long partyId = 0l;
			try {
							person = registrationRecordsService.saveNaturalPerson(person);
				partyId = person.getPartyid();
			} catch (Exception er) {
				er.printStackTrace();
				return false;
			}
			Status status = registrationRecordsService.getStatusById(1);
			
			 if (disputeId ==0){
				 SocialTenureRelationship socialTenureRelationship = new SocialTenureRelationship();
				 LaExtTransactiondetail laExtTransactiondetail = new LaExtTransactiondetail();
				  if (id != 0) {
					  socialTenureRelationship=socialTenureRelationshipDao.getSocialTenureRelationshipByLandIdPartyIdandPersonTypeID(usin, id, 3);
					  laExtTransactiondetail=socialTenureRelationship.getLaExtTransactiondetail();
		            }else
		            {
		            	socialTenureRelationship = new SocialTenureRelationship();
		            	 laExtTransactiondetail = new LaExtTransactiondetail();
		            }
				  
				
				
				
				socialTenureRelationship.setCreatedby(user_id.intValue());
				socialTenureRelationship.setPartyid(partyId);
				socialTenureRelationship.setLaPartygroupPersontype(registrationRecordsService.getPersonTypeById(3));
				socialTenureRelationship.setCreateddate(new Date());
				socialTenureRelationship.setIsactive(true);
				socialTenureRelationship.setLandid(usin);
				
			
				laExtTransactiondetail.setCreatedby(user_id.intValue());
				laExtTransactiondetail.setCreateddate(new Date());
				laExtTransactiondetail.setIsactive(true);
				laExtTransactiondetail.setLaExtApplicationstatus(status);//new approved?
				laExtTransactiondetail.setModuletransid(partyId.intValue());
				laExtTransactiondetail.setRemarks("");
				// laExtTransactiondetail.setLaExtPersonlandmappings(lstSocialTenureRelationships);

				socialTenureRelationship.setLaExtTransactiondetail(laExtTransactiondetail);
				
				
				try {
					socialTenureRelationship =registrationRecordsService.saveSocialTenureRelationship(socialTenureRelationship);
				} catch (Exception er) {
					er.printStackTrace();
					return false;
				}
				 
			 }
			  
			

           // Add person to disputing parties
 
            if (disputeId > 0 ) {
            	LaExtDispute  dispute = laExtDisputeService.findLaExtDisputeByid((int)disputeId);
                if (dispute != null) {
                	LaExtTransactiondetail laExtTransactiondetail1= new  LaExtTransactiondetail();
                	LaExtDisputelandmapping objLaExtDisputelandmapping= new LaExtDisputelandmapping();
                	 if (id != 0) {
                		 objLaExtDisputelandmapping= laExtDisputelandmappingDAO.findLaExtDisputelandmappingByLandIdDisputeIdAndPartyId(usin, (int)disputeId, id);
                		 laExtTransactiondetail1=objLaExtDisputelandmapping.getLaExtTransactiondetail();
   		            }else
   		            {
   		            	laExtTransactiondetail1 = new LaExtTransactiondetail();
   		            	objLaExtDisputelandmapping = new LaExtDisputelandmapping();
   		            }
                	 
                	    laExtTransactiondetail1.setCreatedby(user_id.intValue());
                	    laExtTransactiondetail1.setCreateddate(new Date());
        				laExtTransactiondetail1.setIsactive(true);
        				laExtTransactiondetail1.setLaExtApplicationstatus(status);//new approved?
        				laExtTransactiondetail1.setModuletransid(partyId.intValue());
        				laExtTransactiondetail1.setRemarks("");
        				
        				objLaExtDisputelandmapping.setPersontypeid(10);
        				objLaExtDisputelandmapping.setComment("");
        				objLaExtDisputelandmapping.setCreatedby(user_id.intValue());
        				objLaExtDisputelandmapping.setCreateddate(new Date());
//        				objLaExtDisputelandmapping.setLaExtDisputetype(dispute.getLaExtDisputetype());
        				objLaExtDisputelandmapping.setIsactive(true);
//        				objLaExtDisputelandmapping.setLaExtDispute(dispute);
        				objLaExtDisputelandmapping.setLandid(usin);
        				objLaExtDisputelandmapping.setPartyid(partyId);
        			   objLaExtDisputelandmapping.setLaExtTransactiondetail(laExtTransactiondetail1);
                	 
                	 
        				try{
        					
        					laExtDisputelandmappingService.saveLaExtDisputelandmapping(objLaExtDisputelandmapping);
        				}catch(Exception e)
        				{
        					e.printStackTrace();
        					return false;
        				}
                	
                }
            }

         

         /*   try {
                userDataService.updateNaturalPersonAttribValues(person, project_name);
            } catch (Exception e) {
                logger.error(e);
            }*/

            return true;

        } catch (Exception e) {
            logger.error(e);
            return false;
        }
		
    
    }

    @RequestMapping(value = "/viewer/landrecords/updatenonnatural", method = RequestMethod.POST)
    @ResponseBody
    public boolean updateNonNaturalPerson(HttpServletRequest request, HttpServletResponse response,Principal principal) {

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
        LaParty laParty = new LaParty();
        String username = principal.getName();
		
        User userObj = userService.findByUniqueName(username);
		Long user_id = userObj.getId();
		Long partyId = 0l;
		
		
        try {
            id = ServletRequestUtils.getRequiredLongParameter(request, "non_natural_key");
            long usin = ServletRequestUtils.getLongParameter(request, "nonnatural_usin", 0);
            institute_name = ServletRequestUtils.getRequiredStringParameter(request, "institution");
            address = ServletRequestUtils.getRequiredStringParameter(request, "address");
            phone_no = ServletRequestUtils.getRequiredStringParameter(request, "mobile_no");
           // poc_id = ServletRequestUtils.getRequiredLongParameter(request, "poc_id");
           // mobileGroupId = ServletRequestUtils.getRequiredStringParameter(request, "mobileGroupId");
          //  length_nonnatural = ServletRequestUtils.getRequiredIntParameter(request, "nonnatual_length");
            project_nonnatural = ServletRequestUtils.getRequiredStringParameter(request, "projectname_key2");

            group_type = ServletRequestUtils.getRequiredIntParameter(request, "group_type");
            nonPerson.setOrganizationname(institute_name);
            nonPerson.setIsactive(true);
            nonPerson.setContactno(phone_no);
            
            PersonType personType = registrationRecordsService.getPersonTypeById(2);
            Status status = registrationRecordsService.getStatusById(1);
            GroupType objGroupType= groupTypeDao.getGroupTypeById(group_type);

            nonPerson.setCreatedby(user_id.intValue());
        	nonPerson.setCreateddate(new Date());
        	nonPerson.setLaPartygroupPersontype(personType);
 			
        	
        	
            if (id != 0) {
            	nonPerson.setPartyid(id);
            }else
            {
            	
            	nonPerson.setModifiedby(user_id.intValue());
            	nonPerson.setModifieddate(new Date());
            }
            
            
            nonPerson.setCreatedby(user_id.intValue());
        	nonPerson.setModifieddate(new Date());
        	nonPerson.setGroupType(objGroupType);
        	
        	
        	
        	  try {
  				ProjectArea obj=projectAreaDAO.findProjectAreaByProjectId(Integer.parseInt(project_nonnatural));
  				
  				nonPerson.setLaSpatialunitgroup1(obj.getLaSpatialunitgroup1());
  				nonPerson.setLaSpatialunitgroup2(obj.getLaSpatialunitgroup2());
  				nonPerson.setLaSpatialunitgroup3(obj.getLaSpatialunitgroup3());
  				nonPerson.setLaSpatialunitgroup4(obj.getLaSpatialunitgroup4());
  				
  				nonPerson.setLaSpatialunitgroupHierarchy1(obj.getLaSpatialunitgroupHierarchy1());
  				nonPerson.setLaSpatialunitgroupHierarchy2(obj.getLaSpatialunitgroupHierarchy2());
  				nonPerson.setLaSpatialunitgroupHierarchy3(obj.getLaSpatialunitgroupHierarchy3());
  				nonPerson.setLaSpatialunitgroupHierarchy4(obj.getLaSpatialunitgroupHierarchy4());
  				
  				
  			} catch (Exception e) {
  				// TODO Auto-generated catch block
  				e.printStackTrace();
  			}
        	  
        	  
        	  try {

          		nonPerson.setLaPartygroupPersontype(personType);
          		nonPerson = landRecordsService.addNonNaturalPerson(nonPerson);
          		partyId = nonPerson.getPartyid();
          	} catch (Exception er) {
          		er.printStackTrace();
          		return false;
          	}

        	  
        	  
        	  

            // Create new right if non person is new
        	  SocialTenureRelationship socialTenureRelationship = new SocialTenureRelationship();
        	  LaExtTransactiondetail laExtTransactiondetail = new LaExtTransactiondetail();
        	  
			  if (id != 0) {
				  socialTenureRelationship=socialTenureRelationshipDao.getSocialTenureRelationshipByLandIdPartyIdandPersonTypeID(usin, id, 2);
				  laExtTransactiondetail=socialTenureRelationship.getLaExtTransactiondetail();
	            }else
	            {
	            	socialTenureRelationship = new SocialTenureRelationship();
	            	laExtTransactiondetail = new LaExtTransactiondetail();
	            }
			  
			
			
			
			socialTenureRelationship.setCreatedby(user_id.intValue());
			socialTenureRelationship.setPartyid(partyId);
			socialTenureRelationship.setLaPartygroupPersontype(personType);
			socialTenureRelationship.setCreateddate(new Date());
			socialTenureRelationship.setIsactive(true);
			socialTenureRelationship.setLandid(usin);
			
			
			laExtTransactiondetail.setCreatedby(user_id.intValue());
			laExtTransactiondetail.setCreateddate(new Date());
			laExtTransactiondetail.setIsactive(true);
			laExtTransactiondetail.setLaExtApplicationstatus(status);//new approved?
			laExtTransactiondetail.setModuletransid(partyId.intValue());
			laExtTransactiondetail.setRemarks("");

			socialTenureRelationship.setLaExtTransactiondetail(laExtTransactiondetail);
			
			
			try {
				socialTenureRelationship =registrationRecordsService.saveSocialTenureRelationship(socialTenureRelationship);
			} catch (Exception er) {
				er.printStackTrace();
				return false;
			}
			 
		 
        
        	  

            //AttributeValues attributeValues=new AttributeValues();
         /*   if (length_nonnatural > 0) {
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
            }*/

            //For Updating Non Natural in Attribute Vlaues
           /* try {
                userDataService.updateNonNaturalPersonAttribValues(nonPerson, project_nonnatural);
            } catch (Exception e) {
                logger.error(e);
            }*/

            return true;

        } catch (Exception e) {
            logger.error(e);
            return false;
        }
    }

    @RequestMapping(value = "/viewer/landrecords/deletedispute/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String deleteDispute(@PathVariable Long id) {
        try {
           // Dispute dispute = landRecordsService.getDispute(id);
        	
            LaExtDisputelandmapping   dispute=laExtDisputelandmappingDAO.findLaExtDisputelandmappingById(id.intValue());

            if (dispute == null) {
                return "Dispute not found.";
            }

            String validation = validateDisputeForDelete(dispute);
            if (!validation.equals(RESPONSE_OK)) {
                return validation;
            }

            // Delete dispute 
//            if (landRecordsService.deleteDispute(dispute)) {
//                return RESPONSE_OK;
//            } else {
//                return "Failed to delete dispute";
//            }
            
            return RESPONSE_OK;

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

           // Dispute dispute = landRecordsService.getDispute(id);
            LaExtDisputelandmapping   dispute=laExtDisputelandmappingDAO.findLaExtDisputelandmappingById((int) id);

            if (dispute == null) {
                return "Dispute not found.";
            }

            String validation = validateDisputeForDelete(dispute);

            if (!validation.equals(RESPONSE_OK)) {
                return validation;
            }

            // Resolve dispute 
//            dispute.setResolutionText(resolutionText);

//            if (landRecordsService.resolveDispute(dispute)) {
//                return RESPONSE_OK;
//            } else {
//                return "Failed to resolve dispute";
//            }
            
            return RESPONSE_OK;

        } catch (Exception e) {
            logger.error(e);
            return "Failed to resolve dispute";
        }
    }

    private String validateDisputeForDelete(LaExtDisputelandmapping dispute) {
       // SpatialUnitTable parcel = landRecordsService.findSpatialUnitbyId((long)dispute.getId()).get(0);
        ClaimBasic parcel = spatialUnitService.getClaimsBasicByLandId(dispute.getLandid()).get(0);
        
        // Allow delete only for parcels with new and referred statuses
      /*  if (parcel.getStatus().getWorkflowStatusId() != Status.STATUS_NEW
                && parcel.getStatus().getWorkflowStatusId() != Status.STATUS_REFERRED) {
            return "Disputes can not be managed for this claim";
        }*/
        
       

        // Allow delete only for parcels with new or disputed type
       /* if (!parcel.getClaimType().getCode().toString().equalsIgnoreCase(ClaimType.CODE_NEW)
                && !parcel.getClaimType().getCode().toString().equalsIgnoreCase(ClaimType.CODE_DISPUTED)) {
            return "Disputes can not be managed for this type of claims";
        }*/

        
        if (parcel.getClaimtypeid()!=3   && parcel.getClaimtypeid()!=1) {
            return "Disputes can not be managed for this type of claims";
        }
        
        
        // Allow deletion only if ownership information exists
        List<SocialTenureRelationship> rights = landRecordsService.findAllSocialTenureByUsin(dispute.getLandid());

        if (rights == null || rights.size() < 1) {
            return "Ownership rights must be recorded.";
        }

        boolean activeRightExists = false;

        for (SocialTenureRelationship right : rights) {
            if (right.getIsactive()) {
                // Check for person
                if (right.getPersonlandid() != null) {
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
    public String updateDispute(HttpServletRequest request, HttpServletResponse response,Principal principal) {
    	
    	LaExtDispute objLaExtDispute = new LaExtDispute();
    	String username = principal.getName();
		User userObj = userService.findByUniqueName(username);
		Long user_id = userObj.getId();
		boolean disputedPerson = false;
		boolean non_disputedPerson  = false;
		
		
        try {
            long usin = ServletRequestUtils.getRequiredLongParameter(request, "disputeUsin");
            int disputeTypeCode = ServletRequestUtils.getRequiredIntParameter(request, "cbxDisputeTypes");
            int disputestatus = ServletRequestUtils.getRequiredIntParameter(request, "Status");
            
            String description = ServletRequestUtils.getRequiredStringParameter(request, "txtDisputeDescription");

            if (usin < 1) {
                return "Spatial unit was not found";
            }
            if(disputestatus==2){
            List<LaExtDisputelandmapping> disputelandlst = laExtDisputelandmappingService.findLaExtDisputelandmappingListByLandId(usin);
            for(LaExtDisputelandmapping obj: disputelandlst ){
            	if(obj.getPersontypeid()== 10){
            		disputedPerson=true;
            	}
            	else if(obj.getPersontypeid()== 3){
            		non_disputedPerson= true;
            	}
            }
            if(true){
            for(LaExtDisputelandmapping obj: disputelandlst ){
            	
            	obj.setIsactive(false);
            	laExtDisputelandmappingService.saveLaExtDisputelandmapping(obj);
            	
            	SocialTenureRelationship right = new SocialTenureRelationship();
                right.setCreatedby(user_id.intValue());
                right.setLandid(usin);
                right.setPartyid(obj.getPartyid());
                PersonType persontype = new PersonType();
            	if(obj.getPersontypeid()== 10){
            		persontype.setPersontypeid(10);
            	}
            	else if(obj.getPersontypeid()== 3){
            		persontype.setPersontypeid(1);
            	}
                
                
                right.setLaPartygroupPersontype(persontype);
                right.setLaExtTransactiondetail(obj.getLaExtTransactiondetail());
                
                long time= obj.getCreateddate().getTime();
                right.setCreateddate(new Timestamp(time));
                right.setIsactive(true);
                
                SocialTenureRelationship  socialTenurerelationship  = socialTenureRelationshipDao.saveSocialTenureRelationship(right);
                
                NaturalPerson personobj=null;
                personobj=(NaturalPerson) laPartyDao.getPartyIdByID(socialTenurerelationship.getPartyid());
                PersonType persontypeobj= new PersonType();
                if(obj.getPersontypeid()== 10){
                	persontypeobj.setPersontypeid(10);
            	}
            	else if(obj.getPersontypeid()== 3){
            		persontypeobj.setPersontypeid(1);
            	}
                
                
                personobj.setLaPartygroupPersontype(persontypeobj);
                naturalPersonDao.addNaturalPerson(personobj);
            }
            
            ClaimBasic spatialUnit = spatialUnitService.getClaimsBasicByLandId(usin).get(0);
            spatialUnit.setClaimtypeid(1);
            claimBasicService.saveClaimBasicDAO(spatialUnit);
          

            
            if(usin>0){
            	objLaExtDispute =laExtDisputeService.findLaExtDisputeByLandId( (new Long(usin).intValue()) );	
            	objLaExtDispute.setLandid(usin);
            	objLaExtDispute.setComment(description);
            	objLaExtDispute.setStatus(disputestatus);
            	objLaExtDispute.setDisputetypeid(disputeTypeCode);
            	objLaExtDispute.setIsactive(false);
            }

            
            laExtDisputeService.saveLaExtDispute(objLaExtDispute);
            
            return "true";
            }
            
            
                return "False";
                
            
            }
            else {
            	return "false";
            }

           

        } catch (Exception e) {
            logger.error(e);
            return "Failed to save dispute";
        }
    }

//    @RequestMapping(value = "/viewer/landrecords/deleteDisputant/{disputeId}/{partyId}", method = RequestMethod.GET)
//    @ResponseBody
//    public boolean deleteDisputant(@PathVariable Long disputeId, @PathVariable Long partyId) {
//        return landRecordsService.deleteDisputant(disputeId, partyId);
//    }

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
                right.setPersonlandid(usin);
//                right.setTenureclassId(landRecordsService.findtenureClasseById(2));
                right.setIsactive(true);
//                if (rightType > 0) {
//                    right.setTenureclassId(landRecordsService.findtenureClasseById(rightType));
//                }
                ownershipRights.add(right);
            }

            if (ownershipRights.size() > 0) {
                for (int i = 0; i < ownershipRights.size(); i++) {
                    SocialTenureRelationship ownershipRight = ownershipRights.get(i);

//                    if (tenureType > 0) {
//                        ownershipRight.setShare_type(landRecordsService.findTenureType(tenureType));
//                    }

//                    if (parcel.getClaimType().getCode().equals(ClaimType.CODE_EXISTING)) {
//                        ownershipRight.setCertNumber(certNumber);
//                        if (!StringUtils.isEmpty(certDate)) {
//                            ownershipRight.setCertIssueDate(dateFormat.parse(certDate));
//                        } else {
//                            ownershipRight.setCertIssueDate(null);
//                        }
//                    }

                    /*if (acquisitionTypeCode > 0) {
                        ownershipRight.getLaSpatialunitLand().getLaRightAcquisitiontype().setAcquisitiontypeid(acquisitionTypeCode);
                    }

                    if (relationshipTypeCode > 0) {
                        ownershipRight.getLaParty().getLaPartyPerson().getLaPartygroupRelationshiptype().setRelationshiptypeid(relationshipTypeCode);
                    } else {
                        ownershipRight.getLaParty().getLaPartyPerson().getLaPartygroupRelationshiptype().setRelationshiptypeid(null);
                    }*/

//                    ownershipRight.setJuridicalArea(juridicalArea);
//                    ownershipRight.setTenureDuration(tenureDuration);

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
                            uid = attributefetch.getAttributeid();
                            attributeKey = landRecordsService.getAttributeKey(ownershipRights.get(j).getLaPartygroupPersontype().getPersontypeid(), uid);
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
    public boolean updateMultimedia(HttpServletRequest request, HttpServletResponse response, Principal principal) {
        Long gid;
        String comments;
        long usin;
        int docType;
        int docformat;
        int partyid;
        String docName;
        String projectName;
        String recordationdate;
        SourceDocument sourceDocument = new  SourceDocument();
        
        String username = principal.getName();
		User userObj = userService.findByUniqueName(username);
		Long user_id = userObj.getId();

        List<SourceDocument> sourceDocumentlist = new ArrayList();

        try {
//            Iterator<String> file = request.getFileNames();
            usin = ServletRequestUtils.getRequiredLongParameter(request, "landId");
//            projectName = ServletRequestUtils.getRequiredStringParameter(request, "projectname_multimedia_key");
//            MultipartHttpServletRequest request, HttpServletResponse response,
            gid = ServletRequestUtils.getRequiredLongParameter(request, "documentid");
            docType = ServletRequestUtils.getRequiredIntParameter(request, "documenttype");
            docName = ServletRequestUtils.getRequiredStringParameter(request, "documentname");
            docformat = ServletRequestUtils.getRequiredIntParameter(request, "docs_format");
            recordationdate = ServletRequestUtils.getRequiredStringParameter(request, "recordationdate");
            partyid = ServletRequestUtils.getRequiredIntParameter(request, "personname");


            comments = ServletRequestUtils.getRequiredStringParameter(request, "remarkds");

            if (gid > 0) {
                sourceDocumentlist = landRecordsService.findMultimediaByGid(new Long(usin));
            }
            for(SourceDocument obj: sourceDocumentlist){
            if(obj.getDocumentid().longValue()==gid.longValue()){
            sourceDocument.setDocumentname(docName);
            sourceDocument.setRemarks(comments);
            sourceDocument.setLaSpatialunitLand(usin);
            sourceDocument.setIsactive(true);
            sourceDocument.setCreatedby(user_id.intValue());
            sourceDocument.setCreateddate(obj.getCreateddate());
            sourceDocument.setLaExtTransactiondetail(obj.getLaExtTransactiondetail());
            sourceDocument.setDocumentlocation(obj.getDocumentlocation());
            sourceDocument.setLaParty(obj.getLaParty());
            sourceDocument.setRecordationdate(obj.getRecordationdate());

            if (docType > 0) {
                sourceDocument.setDocumenttypeid(docType);
            } else {
                sourceDocument.setDocumenttypeid(null);
            }
            	}
            }
            if (gid == 0) {
//                // Save new document
//                byte[] document = null;
//                while (file.hasNext()) {
//                    String fileName = file.next();
//                    MultipartFile mpFile = request.getFile(fileName);
//                    String originalFileName = mpFile.getOriginalFilename();
//                    String fileExtension = originalFileName.substring(originalFileName.indexOf(".") + 1, originalFileName.length()).toLowerCase();
//
//                    if (!"".equals(originalFileName)) {
//                        document = mpFile.getBytes();
//                    }
//
////                    String uploadFileName = null;
////                    String outDirPath = FileUtils.getFielsFolder(request) + "resources" + File.separator + "documents" + File.separator + projectName + File.separator + "webupload";
////                    File outDir = new File(outDirPath);
////                    boolean exists = outDir.exists();
////
////                    if (!exists) {
////                        (new File(outDirPath)).mkdirs();
////                    }
//
//                    sourceDocument.setDocumentname(originalFileName);
////                    uploadFileName = ("resources/documents/" + projectName + "/webupload");
////                    sourceDocument.setDocumentlocation(uploadFileName);
//                    sourceDocument.setIsactive(true);
////                    sourceDocument.setRecordation(new Date());
//                    sourceDocument.setDocumentid(usin);
//
//                    sourceDocument = landRecordsService.saveUploadedDocuments(sourceDocument);
//
//                    try {
////                        FileOutputStream uploadfile = new FileOutputStream(outDirPath + File.separator + sourceDocument.getDocumentid() + "." + fileExtension);
////                        uploadfile.write(document);
////                        uploadfile.flush();
////                        uploadfile.close();
//                    } catch (Exception e) {
//                        logger.error(e);
//                        return false;
//                    }
//                }
            } else {
            	sourceDocumentsDao.saveUploadedDocuments(sourceDocument);
            }

            //For Updating tenure in AttributeValues
//            userDataService.updateMultimediaAttribValues(sourceDocument, projectName);

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
    	boolean flag =true;
    	List<TenureClass> tenure = new ArrayList<TenureClass>();
    	List<TenureClass> tenureobj = landRecordsService.findAllTenureClass();
    	for(TenureClass obj: tenureobj){
    		if(obj.getIsactive()== flag){
    			tenure.add(obj);
    		}
    		else if(obj.getIsactive() != flag){
    			
    		}
    	}
    	
        return tenure;
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
    
//    public List<Project> findAllProjects(@PathVariable String project){
//    	
//    }
    
    

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
        String projectName = null;

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

                sourceDocument.setDocumentname(originalFileName);
                uploadFileName = ("resources/documents/" + projectName + "/webupload");
                sourceDocument.setDocumentlocation(uploadFileName);
                sourceDocument.setDocumentname(document_name);
//                sourceDocument.setComments(document_comments);
                sourceDocument.setIsactive(true);
//                sourceDocument.setRecordation(new Date());
                sourceDocument.setDocumentid(usin);

                sourceDocument = landRecordsService.saveUploadedDocuments(sourceDocument);

                Long id = sourceDocument.getDocumentid();

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
    public LaExtDisputeDTO getDisputes(@PathVariable long usin) {
    	LaExtDispute objLaExtDispute=  laExtDisputeService.findLaExtDisputeByLandId((int)usin);
    	LaExtDisputeDTO objLaExtDisputeDTO = new LaExtDisputeDTO();
    	List<LaParty> lstLaParty= new ArrayList<LaParty>();
    	if(null!=objLaExtDispute)
    	{
    			objLaExtDisputeDTO.setComment(objLaExtDispute.getComment());
    			objLaExtDisputeDTO.setDisputeid(objLaExtDispute.getDisputeid());
    			objLaExtDisputeDTO.setLandid(objLaExtDispute.getLandid());
    			objLaExtDisputeDTO.setStatus(objLaExtDispute.getStatus());
   		    	objLaExtDisputeDTO.setStatus(objLaExtDispute.getStatus());
   		    	objLaExtDisputeDTO.setDisputetypeid(objLaExtDispute.getDisputetypeid());
    			
    	}
    		
    	   	return objLaExtDisputeDTO;
    		
    	}
    	
 
    	
    	
    	
    

    @RequestMapping(value = "/viewer/landrecords/dispute/{id}", method = RequestMethod.GET)
    @ResponseBody
    public LaExtDispute getDispute(@PathVariable Integer id) {
        return laExtDisputeService.findLaExtDisputeByid(id);
    }

    @RequestMapping(value = "/viewer/landrecords/disputetypes/", method = RequestMethod.GET)
    @ResponseBody
    public List<DisputeType> getDisputeType() {
        return disputeTypeDao.findAll();
    }
    
    
    @RequestMapping(value = "/viewer/landrecords/disputestatus/", method = RequestMethod.GET)
    @ResponseBody
    public List<DisputeStatus> getDisputeStatus() {
        return disputeStatusDao.getAllDisputeStatus();
    }

    

    @RequestMapping(value = "/viewer/landrecords/download/{id}", method = RequestMethod.GET)
    @ResponseBody
    public void download(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response) {
    	
        SourceDocument doc = landRecordsService.getDocumentbyGid(id);
        String filepath = FileUtils.getFielsFolder(request) + doc.getDocumentlocation()
                + File.separator + id + "." + FileUtils.getFileExtension(doc.getDocumentname());
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
    
    
    @RequestMapping(value = "/viewer/landrecords/download/{id}/{transactionid}/{Personusin}", method = RequestMethod.GET)
    @ResponseBody
    public void PersonMultimediaShow(@PathVariable Long id,@PathVariable Long transactionid,@PathVariable Long Personusin,  HttpServletRequest request, HttpServletResponse response) {
    	byte[] data =null;
    	SourceDocument doc =null;
    	if(transactionid ==0){
    	SocialTenureRelationship socialtenureobj =socialTenureRelationshipDAO.getSocialTenureObj(id, Personusin);
    	 doc =  sourceDocumentsDao.findBypartyandtransid(id, socialtenureobj.getLaExtTransactiondetail().getTransactionid().longValue());
    	}
    	else{
    	  doc =  sourceDocumentsDao.findBypartyandtransid(id, transactionid);
    	  
    	}
    	 if(doc==null){
    		 response.setContentLength(data.length);
    	 }
        String filepath = FileUtils.getFielsFolder(request) + doc.getDocumentlocation()
                +"/"+ doc.getDocumentname();
         filepath =  filepath.replace("\\mast\\..", "");
        Path path = Paths.get(filepath);
        try {
            data = Files.readAllBytes(path);
            response.setContentLength(data.length);
            OutputStream out = response.getOutputStream();
            out.write(data);
            out.flush();
            out.close();

        } catch (Exception e) {
            logger.error(e);
        }
    }

    
    @RequestMapping(value = "/viewer/landrecords/mediaavail/{id}/{transactionid}/{Personusin}", method = RequestMethod.GET)
    @ResponseBody
    public boolean isPersonMultimediaexist(@PathVariable Long id,@PathVariable Long transactionid,@PathVariable Long Personusin,  HttpServletRequest request, HttpServletResponse response) {
    	SourceDocument doc = null;
    	if(transactionid==0){
    		SocialTenureRelationship socialtenureobj =socialTenureRelationshipDAO.getSocialTenureObj(id, Personusin);
    		  doc =  sourceDocumentsDao.findBypartyandtransid(id, socialtenureobj.getLaExtTransactiondetail().getTransactionid().longValue());
    	}
    	else if(transactionid !=0){
    	  doc =  sourceDocumentsDao.findBypartyandtransid(id, transactionid);
    	 
    	}
    	if(doc==null){
    		return false;
    	 }
       
        return true;
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
    
    @RequestMapping(value = "/viewer/registrationdetails/viewdoc/{partyid}/{transactionid}", method = RequestMethod.GET)
    @ResponseBody
    public void viewdocforTransaction(@PathVariable Long partyid,@PathVariable Long transactionid,  HttpServletRequest request, HttpServletResponse response) {
    	 SourceDocument doc =  sourceDocumentsDao.findBypartyandtransid(partyid, transactionid);
        String filepath = FileUtils.getFielsFolder(request) + doc.getDocumentlocation();
                // +"/"+ doc.getDocumentname();
         filepath =  filepath.replace("\\mast\\..", "");
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
    
    @RequestMapping(value = "/viewer/landrecords/ccroformladm/{usin}", method = RequestMethod.GET)
    @ResponseBody
    public void getCcroFormladm(@PathVariable Long usin, HttpServletRequest request, HttpServletResponse response) {
        try {
            /*SpatialUnitTable claim = landRecordsService.getSpatialUnit(usin);
            if (claim != null) {
                writeReport(reportsService.getCcroForms(claim.getProject(), usin, 1, 1, getApplicationUrl(request)), "CcroForm", response);
            }*/
        	
        	writeReport(reportsService.getCcroFormsLadm("", usin, 1, 1, getApplicationUrl(request)), "CcroForm", response);
        	
        } catch (Exception e) {
            logger.error(e);
        }
    }
    
    @RequestMapping(value = "/viewer/landrecords/landverification/{usin}", method = RequestMethod.GET)
    @ResponseBody
    public void getlandverificationForm(@PathVariable Long usin, HttpServletRequest request, HttpServletResponse response) {
        try {
            /*SpatialUnitTable claim = landRecordsService.getSpatialUnit(usin);
            if (claim != null) {
                writeReport(reportsService.getCcroForms(claim.getProject(), usin, 1, 1, getApplicationUrl(request)), "CcroForm", response);
            }*/
        	
        	writeReport(reportsService.getlandverificationForm("", usin, 1, 1, getApplicationUrl(request)), "CcroForm", response);
        	
        } catch (Exception e) {
            logger.error(e);
        }
    }

   /* @RequestMapping(value = "/viewer/landrecords/ccroforms/{projectName}/{startRecord}/{endRecord}", method = RequestMethod.GET)
    @ResponseBody
    public void getCcroForms(@PathVariable String projectName, @PathVariable int startRecord, @PathVariable int endRecord, HttpServletRequest request, HttpServletResponse response) {
        writeReport(reportsService.getCcroForms(projectName, 0L, startRecord, endRecord, getApplicationUrl(request)), "CcroForms", response);
    }*/
    
    @RequestMapping(value = "/viewer/landrecords/ccroformsinbatch/{projectName}/{startRecord}/{endRecord}", method = RequestMethod.GET)
    @ResponseBody
    public void getCcroForms(@PathVariable String projectName, @PathVariable Long startRecord, @PathVariable Long endRecord, HttpServletRequest request, HttpServletResponse response) {
        writeReport(reportsService.getCcroFormsinbatch(projectName, 0L, startRecord, endRecord, getApplicationUrl(request)), "CcroForms", response);
    }
    
    @RequestMapping(value = "/viewer/landrecords/landformsinbatch/{projectName}/{startRecord}/{endRecord}", method = RequestMethod.GET)
    @ResponseBody
    public void getLandForms(@PathVariable String projectName, @PathVariable Long startRecord, @PathVariable Long endRecord, HttpServletRequest request, HttpServletResponse response) {
        writeReport(reportsService.getLandFormsinbatch(projectName, 0L, startRecord, endRecord, getApplicationUrl(request)), "CcroForms", response);
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
    
    @RequestMapping(value = "/studio/landrecords/runtopology/{projectName}", method = RequestMethod.GET)
    @ResponseBody
    public String checkruntopologychecks(@PathVariable String projectName) {
        try 
        {
            String project = landRecordsService.checkruntopologychecks(projectName);           
            return RESPONSE_OK;
        }
        catch (Exception e) 
        {
            logger.error(e);
            return "Failed to run topology checks";
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
            if (doc == null || !doc.getIsactive()) {
                writeEmptyImage(request, response);
                return;
            }

            String fileType = FileUtils.getFileExtension(doc.getDocumentname());
           String filepath = FileUtils.getFielsFolder(request) + doc.getDocumentlocation() + File.separator + doc.getDocumentname();
            //String filepath = FileUtils.getFielsFolder(request) + doc.getDocumentlocation() + File.separator + doc.getDocumentid() + "."+fileType;

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
    
    @RequestMapping(value = "/viewer/landrecords/personphotofortransaction/{transactionid}/{partyid}", method = RequestMethod.GET)
    @ResponseBody
    public void getPersonPhotofortransaction(@PathVariable Long transactionid,@PathVariable Long partyid, HttpServletRequest request, HttpServletResponse response) {
        try {
            SourceDocument doc = landRecordsService.getdocumentByPersonfortransaction(transactionid, partyid);
            if (doc == null || !doc.getIsactive()) {
                writeEmptyImage(request, response);
                return;
            }

            // String fileType = FileUtils.getFileExtension(doc.getDocumentname());
            String filepath = FileUtils.getFielsFolder(request) + doc.getDocumentlocation() + File.separator + doc.getDocumentname();
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
        String projectName = null;
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
                if ((naturalGid.contains(doc.get(i).getLaParty().getLaPartygroupPersontype().getPersontypeid()))) {
                    String fileName = doc.get(i).getDocumentname();
                    if (fileName.toLowerCase().contains("jpg")) {
                        String fileType = fileName.substring(fileName.indexOf(".") + 1, fileName.length()).toLowerCase();
                        String filepath = doc.get(i).getDocumentlocation() + File.separator + doc.get(i).getDocumentid() + "." + fileType;
                        NaturalPerson naturalpersontmp = landRecordsService.naturalPersonById((long)doc.get(i).getLaParty().getLaPartygroupPersontype().getPersontypeid()).get(0);
                        naturalGid.remove(doc.get(i).getLaParty().getLaPartygroupPersontype().getPersontypeid());
                        String name = naturalpersontmp.getFirstname() + " " + naturalpersontmp.getMiddlename() + " " + naturalpersontmp.getLastname();
                        //doc.get(i).getPerson_gid();
                        ccrodoc.add(name);
                        ccrodoc.add(filepath);

                    }
                }
            }

            if (naturalGid.size() != 0) {
                for (int i = 0; i < naturalGid.size(); i++) {
                    NaturalPerson naturalpersontmp = landRecordsService.naturalPersonById(naturalGid.get(i)).get(0);
                    String name = naturalpersontmp.getFirstname() + " " + naturalpersontmp.getMiddlename() + " " + naturalpersontmp.getLastname();
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
            } else if (socialtenuretmp.get(0).getLaPartygroupPersontype().getPersontypeid()== 1) {
                return 0;
            } else if (socialtenuretmp.get(0).getLaPartygroupPersontype().getPersontypeid()== 2) {
                return 2;
            }

        }
        return null;
    }

    @RequestMapping(value = "/viewer/landrecords/getinstitutename/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String Institute(@PathVariable Long id) {

        List<SocialTenureRelationship> socialtenuretmp = landRecordsService.findAllSocialTenureByUsin(id);
        long gid = socialtenuretmp.get(0).getLaPartygroupPersontype().getPersontypeid();

        List<NonNaturalPerson> nonNaturalpersonList;
        try {
            nonNaturalpersonList = landRecordsService.nonnaturalPersonById(gid);
            return nonNaturalpersonList.get(0).getOrganizationname();
        } catch (Exception e) {
            logger.error(e);
            return null;
        }

    }

    @RequestMapping(value = "/viewer/landrecords/ccroinstituteperson/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String InstitutePerson(@PathVariable Long id) {

        /*List<SocialTenureRelationship> socialtenuretmp = landRecordsService.findAllSocialTenureByUsin(id);
        long gid = socialtenuretmp.get(0).getLaPartygroupPersontype().getPersontypeid();

        List<NonNaturalPerson> nonNaturalpersonList = landRecordsService.nonnaturalPersonById(gid);
       // long naturalid = nonNaturalpersonList.get(0).getLaParty().getLaPartyPerson().getPersonid();

        NaturalPerson naturaltmp = landRecordsService.naturalPersonById(naturalid).get(0);
        String name = naturaltmp.getFirstname() + " " + naturaltmp.getMiddlename() + " " + naturaltmp.getLastname();
        return name;*/
    	return null;

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
                String fileName = admindoc.getDocumentname();
                String fileType = fileName.substring(fileName.indexOf(".") + 1, fileName.length()).toLowerCase();
                String filepath = admindoc.getDocumentlocation() + File.separator + admindoc.getDocumentid() + "." + fileType;
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
    public List<SpatialUnitTemp> totalRecords(Principal principal, @PathVariable Integer project) {

        String loggeduser = principal.getName();
        User user = userService.findByUniqueName(loggeduser);
        Integer  id = null;
        if (project != null) {
            id = project;
        }

        //return landRecordsService.findAllSpatialUnit(defaultProject);
        return landRecordsService.AllSpatialUnitTemp(id);

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
//                if (!dispute.isDeleted() && dispute.getDisputingPersons() != null) {
//                    persons.addAll(dispute.getDisputingPersons());
//                }
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
                        if (right.getPersonlandid() != null && (long)right.getLaPartygroupPersontype().getPersontypeid() == personId) {
                            return true;
                        }
                    }
                    // Add person to right
                    SocialTenureRelationship right = rights.get(0);
                    if (right.getLaPartygroupPersontype().getPersontypeid() != null) {
                        right.setPersonlandid(0L);
                    }
//                    right.setPerson_gid(person);
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
            if (obj.get(i).getPersonlandid()== 1) {
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
            if (obj.get(i).getPersonlandid() == 2) {
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
        String projname = null;

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

        if (sourcetemp != null && sourcetemp.getIsactive()) {
            resulttmp.add(sourcetemp.getDocumentname());
            resulttmp.add(sourcetemp.getDocumentlocation() + "/" + sourcetemp.getDocumentid() + ".jpg");

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
                NaturalPerson laPartyPerson = new NaturalPerson();
                SourceDocument sourceDocument1 = new SourceDocument();
                PersonType personType = new PersonType();
                LaParty laParty = new LaParty();
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
                sourceDocument1.setDocumentname(originalFileName);
                uploadFileName = ("resources/documents/" + projectName + "/multimedia");

                sourceDocument1.setDocumentlocation(uploadFileName);
                if (person_gid != 0) {
                    sourceDocument1.getLaParty().getLaPartygroupPersontype().setPersontypeid((int)person_gid);
                }
                if (admin_id != 0) {
                    sourceDocument1.getLaParty().getLaPartygroupPersontype().setPersontypeid((int)admin_id);;
                }
                sourceDocument1.setDocumentname(document_name);
//                sourceDocument1.setComments(document_comments);

                sourceDocument1.setIsactive(true);
//                try {
//                    sourceDocument1.setRecordation(new Date());
//                    sourceDocument1.setUsin(usin);
//                } catch (Exception e1) {
//                    // TODO Auto-generated catch block
//                    e1.printStackTrace();
//                }
                sourceDocument1 = landRecordsService.saveUploadedDocuments(sourceDocument1);

                Long id = sourceDocument1.getDocumentid();

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
            tempList.add(personList.get(i).getFirstName());

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

            //@@  Check parcel for appropriate status and type getcode new domain change
            if ((claim.getClaimType().getCode().toString().equalsIgnoreCase(ClaimType.CODE_NEW)
                    || claim.getClaimType().getCode().toString().equalsIgnoreCase(ClaimType.CODE_DISPUTED))
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

            if (claim.getClaimType().getCode().toString().equalsIgnoreCase(ClaimType.CODE_NEW)
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

            if ((claim.getClaimType().getCode().toString().equalsIgnoreCase(ClaimType.CODE_NEW)
                    && claim.getStatus().getWorkflowStatusId() == Status.STATUS_VALIDATED)
                    || (claim.getClaimType().getCode().toString().equalsIgnoreCase(ClaimType.CODE_EXISTING)
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

    private List<String> validateClaim(SpatialUnitTable claim, boolean validateStatus) {/*
        List<String> errors = new ArrayList<>();

        try {
            if (claim == null) {
                errors.add("Claim was not found");
                return errors;
            }

            long usin = claim.getUsin();

            // Check claim type
            if (!claim.getClaimType().getCode().toString().equalsIgnoreCase(ClaimType.CODE_NEW)
                    && !claim.getClaimType().getCode().toString().equalsIgnoreCase(ClaimType.CODE_EXISTING)) {
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
//                    if (dispute.getStatus().getCode() == DisputeStatus.STATUS_ACTIVE) {
//                        errors.add("There are unresolved disputes found. You have to resolve them first.");
//                        return errors;
//                    }
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

//            for (SocialTenureRelationship right : rights) {
//                if (right.getPersonlandid() != null) {
//                    if (right.getLaPartygroupPersontype().getPersontypeid() != PersonType.TYPE_NATURAL) {
//                        nonNatural += 1;
//                        NonNaturalPerson nonPerson = (NonNaturalPerson) right.getPerson_gid();
//                        if (nonPerson.getPoc_gid() != null && nonPerson.getPoc_gid() > 0) {
//                            hasRepresentative = true;
//                        }
//                    } else {
//                        NaturalPerson person = (NaturalPerson) right.getPerson_gid();
//                        if (person.getPersonSubType() != null) {
//                            if (person.getPersonSubType().getPerson_type_gid() == PersonType.TYPE_ADMINISTRATOR) {
//                                admins += 1;
//                            }
//                            if (person.getPersonSubType().getPerson_type_gid() == PersonType.TYPE_OWNER) {
//                                owners += 1;
//                            }
//                            if (person.getPersonSubType().getPerson_type_gid() == PersonType.TYPE_GUARDIAN) {
//                                guardians += 1;
//                            }
//                        }
//                    }
//                }
//            }

            int i = 0;

            for (SocialTenureRelationship right : rights) {
                // Validate only first right, considering that all others must be the same
                if (i == 0) {
//                    if (right.getTenureclassId() == null) {
//                        errors.add("Enter type of right.");
//                    }

                    if (right.getLaSpatialunitLand().getLaRightLandsharetype() == null) {
                        errors.add("Select tenure type");
                    }

                    // For existing claims
//                    if (claim.getClaimType().getCode().equalsIgnoreCase(ClaimType.CODE_EXISTING)) {
//                        if (right.getJuridicalArea() == null || right.getJuridicalArea() == 0) {
//                            errors.add("Enter Juridical area more than 0");
//                        }
//                    }

                    if (right.getLaPartygroupPersontype().getPersontypeid() == null) {
                        errors.add("Add at least 1 person");
                    }

                    // Check share type
                    if (right.getLaSpatialunitLand().getLaRightLandsharetype()!= null) {
                        if (right.getLaSpatialunitLand().getLaRightLandsharetype().getLandsharetypeid() != ShareType.SHARE_INSTITUTION && nonNatural > 0) {
                            errors.add("Only natural persons are allowed for " + right.getLaSpatialunitLand().getLaRightLandsharetype().getLandsharetypeEn());
                        }

                        if (right.getLaSpatialunitLand().getLaRightLandsharetype().getLandsharetypeid() == ShareType.SHARE_SINGLE) {
                            // Only 1 natural person is allowed
                            if (owners == 0) {
                                errors.add("Add owner");
                            }
                            if (owners > 1) {
                                errors.add("Only 1 owner is allowed for " + right.getLaSpatialunitLand().getLaRightLandsharetype().getLandsharetypeEn());
                            }
                            if (admins > 0) {
                                errors.add("Administrators are not allowed for " + right.getLaSpatialunitLand().getLaRightLandsharetype().getLandsharetypeEn());
                            }
                            if (guardians > 0) {
                                errors.add("Guardians are not allowed for " + right.getLaSpatialunitLand().getLaRightLandsharetype().getLandsharetypeEn());
                            }
                        }

                        if (right.getLaSpatialunitLand().getLaRightLandsharetype().getLandsharetypeid() == ShareType.SHARE_ADMINISTRATOR) {
                            // 1 or many owners, max 2 admins and deceased person
                            if (deceasedPersons == null || deceasedPersons.size() < 0) {
                                errors.add("Add deceased person");
                            }
                            if (admins == 0) {
                                errors.add("Add at least 1 administrator");
                            }
                            if (admins > 2) {
                                errors.add("Maximum 2 administrators are allowed for " + right.getLaSpatialunitLand().getLaRightLandsharetype().getLandsharetypeEn());
                            }
                            if (guardians > 0) {
                                errors.add("Guardians are not allowed for " + right.getLaSpatialunitLand().getLaRightLandsharetype().getLandsharetypeEn());
                            }
                        }

                        if (right.getLaSpatialunitLand().getLaRightLandsharetype().getLandsharetypeid() == ShareType.SHARE_GUARDIAN) {
                            // 1 or many owners (minors), max 2 guardians
                            if (owners == 0) {
                                errors.add("Add at least 1 owner (minor)");
                            }
                            if (guardians == 0) {
                                errors.add("Add at least 1 guardian");
                            }
                            if (guardians > 2) {
                                errors.add("Maximum 2 guardians are allowed for " + right.getLaSpatialunitLand().getLaRightLandsharetype().getLandsharetypeEn());
                            }
                            if (admins > 0) {
                                errors.add("Administrators are not allowed for " + right.getLaSpatialunitLand().getLaRightLandsharetype().getLandsharetypeEn());
                            }
                        }

                        if (right.getLaSpatialunitLand().getLaRightLandsharetype().getLandsharetypeid() == ShareType.SHARE_MULTIPLE_COMMON) {
                            // At least 2 owners 
                            if (owners < 2) {
                                errors.add("Add at least 2 owners");
                            }
                            if (admins > 0) {
                                errors.add("Administrators are not allowed for " + right.getLaSpatialunitLand().getLaRightLandsharetype().getLandsharetypeEn());
                            }
                            if (guardians > 0) {
                                errors.add("Guardians are not allowed for " + right.getLaSpatialunitLand().getLaRightLandsharetype().getLandsharetypeEn());
                            }
                        }

                        if (right.getLaSpatialunitLand().getLaRightLandsharetype().getLandsharetypeid() == ShareType.SHARE_MULTIPLE_JOINT) {
                            // 2 owners 
                            if (owners == 0) {
                                errors.add("Add 2 owners");
                            }
                            if (owners > 2) {
                                errors.add("Maximum 2 owners are allowed for " + right.getLaSpatialunitLand().getLaRightLandsharetype().getLandsharetypeEn());
                            }
                            if (admins > 0) {
                                errors.add("Administrators are not allowed for " + right.getLaSpatialunitLand().getLaRightLandsharetype().getLandsharetypeEn());
                            }
                            if (guardians > 0) {
                                errors.add("Guardians are not allowed for " + right.getLaSpatialunitLand().getLaRightLandsharetype().getLandsharetypeEn());
                            }
                        }

                        if (right.getLaSpatialunitLand().getLaRightLandsharetype().getLandsharetypeid() == ShareType.SHARE_INSTITUTION) {
                            // 1 non natural person should be 
                            if (nonNatural == 0) {
                                errors.add("Add 1 Non Natural person");
                            }
                            if (nonNatural > 1) {
                                errors.add("Maximum 1 Non Natural person is allowed for " + right.getLaSpatialunitLand().getLaRightLandsharetype().getLandsharetypeEn());
                            }
                            if (!hasRepresentative) {
                                errors.add("One representative person has to be added");
                            }
                            if (owners > 0) {
                                errors.add("Natural persons as owners are not allowed for " + right.getLaSpatialunitLand().getLaRightLandsharetype().getLandsharetypeEn());
                            }
                            if (admins > 0) {
                                errors.add("Administrators are not allowed for " + right.getLaSpatialunitLand().getLaRightLandsharetype().getLandsharetypeEn());
                            }
                            if (guardians > 0) {
                                errors.add("Guardians are not allowed for " + right.getLaSpatialunitLand().getLaRightLandsharetype().getLandsharetypeEn());
                            }
                        }
                    }
                }

                i += 1;

                // Check person
                if (right.getLaSpatialunitLand().getLaRightLandsharetype().getLandsharetypeEn() != null && right.getLaSpatialunitLand().getLaRightLandsharetype().getLandsharetypeid() != null) {

                    NonNaturalPerson nonPerson = null;
                    NaturalPerson person = null;

//                    if (right.getPerson_gid().getPerson_type_gid().getPerson_type_gid() != PersonType.TYPE_NATURAL) {
//                        nonPerson = (NonNaturalPerson) right.getPerson_gid();
//                        if (nonPerson.getPoc_gid() != null && nonPerson.getPoc_gid() > 0) {
//                            person = (NaturalPerson) landRecordsService.findPersonGidById(nonPerson.getPoc_gid());
//                        }
//                    } else {
//                        person = (NaturalPerson) right.getPerson_gid();
//                    }

                    // Validate non natural
                    if (nonPerson != null) {
                        if (StringUtils.isEmpty(nonPerson.getOrganizationname())) {
                            errors.add("Enter Non Natural person name");
                        }
//                        if (StringUtils.isEmpty(nonPerson.getAddress())) {
//                            errors.add("Enter Non Natural person address");
//                        }
                        if (nonPerson.getLaPartygroupIdentitytype() == null) {
                            errors.add("Select type of Non Natural person");
                        }
                    }

                    // Validate natural
                    if (person != null) {
                        if (StringUtils.isEmpty(person.getFirstname())) {
                            errors.add("Enter first name for " + getPersonName(person));
                        }
                        if (StringUtils.isEmpty(person.getLastname())) {
                            errors.add("Enter last name for " + getPersonName(person));
                        }
//                        if (person.getPersonSubType() == null && right.getShare_type().getGid() != ShareType.SHARE_INSTITUTION) {
//                            errors.add("Select owner type for " + getPersonName(person));
//                        }
                        if (person.getPersonid() == null) {
                            errors.add("Select ID type for " + getPersonName(person));
                        }
                        if (StringUtils.isEmpty(person.getIdentityno())) {
                            errors.add("Enter ID number for " + getPersonName(person));
                        }
                        if (person.getGenderid() == null) {
                            errors.add("Select gender for " + getPersonName(person));
                        }
                        if (person.getDateofbirth() == null) {
                            errors.add("Enter date of birth for " + getPersonName(person));
                        }
//                        if (person.getCitizenship_id() == null) {
//                            errors.add("Select citizenship for " + getPersonName(person));
//                        }
                        if (person.getLaPartygroupMaritalstatus() == null) {
                            errors.add("Select marital status for " + getPersonName(person));
                        }

                        // Check share size
                        if (right.getLaSpatialunitLand().getLaRightLandsharetype().getLandsharetypeid() == ShareType.SHARE_MULTIPLE_COMMON
                                && person.getLaPartygroupIdentitytype() != null 
                                && person.getLaParty().getLaPartygroupPersontype().getPersontypeid() == PersonType.TYPE_OWNER){
                                //&& StringUtils.isEmpty(person.getLaParty().getLaExtPersonlandmappings().get(0).getLaSpatialunitLand().getLaRightLandsharetype().getLandsharetypeEn())) {
                           		//@@ vishal && StringUtils.isEmpty(person.getLaParty().getLaExtPersonlandmappings().get(0).getLaSpatialunitLand().getLaRightLandsharetype().getLandsharetypeEn())) {
                            errors.add("Enter share size for " + getPersonName(person));
                        }

                        // Check age
                        int age = 0;

                        // Give priority to dob to calculate age
                        if (person.getDateofbirth() != null) {
                            age = DateUtils.getAge(person.getDateofbirth());
                        }
//                        } else {
//                            age = person.getAge();
//                        }

                        if (person.getLaPartygroupIdentitytype() != null && person.getLaParty().getLaPartygroupPersontype().getPersontypeid() == PersonType.TYPE_OWNER
                                && right.getLaSpatialunitLand().getLaRightLandsharetype().getLandsharetypeid() == ShareType.SHARE_GUARDIAN) {
                            if (age > 17) {
                                errors.add(getPersonName(person) + " age must be less than 18 years");
                            }
                        } else if (age < 18 || age > 110) {
                            errors.add(getPersonName(person) + " age must be between 18 and 110 years");
                        }

                        // Check person photo
                        //SourceDocument photo = landRecordsService.getdocumentByPerson(person.getPersonid());
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
    */
    	return null;
    	}

    private String getPersonName(NaturalPerson person) {
        String name = "";
        if (!StringUtils.isEmpty(person.getFirstname())) {
            name = person.getFirstname();
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

    private String generateUkaNumber(SpatialUnitTable claim) {
        Project project = projectService.findProjectByName(claim.getProject());
//        String village = project.getLaExtProjectareas().get(0).getLaSpatialunitgroup2().getHierarchyEn();
        String village = null;

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

    @RequestMapping(value = "/viewer/landrecords/personwithinterest/{usin}/{transid}", method = RequestMethod.GET)
    @ResponseBody
    public List<SpatialUnitPersonWithInterest> nxtTokin(@PathVariable Long usin, @PathVariable Long transid) {
       List<SpatialUnitPersonWithInterest> obj = spatialunitpersonwithinterestdao.findByUsinandTransid(usin, transid.longValue());
       if(obj.size()>0){
    	   return obj;
       }else{
    	   return null;
       }
    }
    
    @RequestMapping(value = "/viewer/landrecords/personwithinterest/{usin}", method = RequestMethod.GET)
    @ResponseBody
    public List<SpatialUnitPersonWithInterest> getParcelPOI(@PathVariable Long usin) {
       List<SpatialUnitPersonWithInterest> obj = spatialunitpersonwithinterestdao.findByUsin(usin);
       if(obj.size()>0){
    	   return obj;
       }else{
    	   return null;
       }
    }

    @RequestMapping(value = "/viewer/landrecords/poi/{id}", method = RequestMethod.GET)
    @ResponseBody
    public SpatialunitPersonwithinterest getPoi(@PathVariable Long id) {
        return landRecordsService.getPoi(id);
    }

    @RequestMapping(value = "/viewer/landrecords/updatepwi", method = RequestMethod.POST)
    @ResponseBody
    public boolean updatepwi(HttpServletRequest request, HttpServletResponse response,Principal principal) {
        long Usin = 0;
        String name;
        String dob;
        Date dobDate = null;
        long relationshipType;
        long genderCode;
        long id = 0;
        String firstName;
        String middleName;
        String lastName;
        
        String username = principal.getName();
		User userObj = userService.findByUniqueName(username);
		Long user_id = userObj.getId();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		SpatialUnitPersonWithInterest objSpatialUnitPersonWithInterest = null;
      
        try {
            Usin = ServletRequestUtils.getRequiredLongParameter(request, "usin_kin");
            name = ServletRequestUtils.getRequiredStringParameter(request, "name_kin");
            id = ServletRequestUtils.getRequiredLongParameter(request, "id_kin");
            genderCode = ServletRequestUtils.getRequiredLongParameter(request, "poiGender");
           relationshipType = ServletRequestUtils.getRequiredLongParameter(request, "poiRelType");
            firstName= ServletRequestUtils.getRequiredStringParameter(request, "fname_kin");
            middleName= ServletRequestUtils.getRequiredStringParameter(request, "mname_kin");
            lastName= ServletRequestUtils.getRequiredStringParameter(request, "lname_kin");
            try {
                dob = ServletRequestUtils.getRequiredStringParameter(request, "poiDob");
                if (!StringUtils.isEmpty(dob)) {
                    dobDate = dateFormat.parse(dob);
                }
            } catch (Exception e) {
                logger.error(e);
            }

            if (id != 0) {
            	objSpatialUnitPersonWithInterest =spatialunitPersonwithinterestService.findSpatialUnitPersonWithInterestById(id);
            }else
            {
            	
            	objSpatialUnitPersonWithInterest = new SpatialUnitPersonWithInterest();
    			
            }

            if (genderCode != 0) {
            	objSpatialUnitPersonWithInterest.setGender((int)genderCode);
            } else {
            	objSpatialUnitPersonWithInterest.setGender(null);
            }

            if (relationshipType > 0) {
            	objSpatialUnitPersonWithInterest.setRelation((int)relationshipType);
            } else {
            	objSpatialUnitPersonWithInterest.setRelation(null);
            }
            
            objSpatialUnitPersonWithInterest.setDob(dobDate);
            
            
            try{
            	
            	ClaimBasic spatialUnit = spatialUnitService.getClaimsBasicByLandId(Usin).get(0);
            	if(spatialUnit!=null){
            		objSpatialUnitPersonWithInterest.setProjectnameid(spatialUnit.getProjectnameid().longValue());
            	}
            	
           	 
           }catch(Exception e)
           {
           	e.printStackTrace();
           }
            
            objSpatialUnitPersonWithInterest.setFirstName(firstName);
            objSpatialUnitPersonWithInterest.setMiddleName(middleName);
            objSpatialUnitPersonWithInterest.setLastName(lastName);
            objSpatialUnitPersonWithInterest.setCreatedby(user_id.intValue());
            objSpatialUnitPersonWithInterest.setCreateddate(new Date());
            objSpatialUnitPersonWithInterest.setLandid(Usin);
            objSpatialUnitPersonWithInterest.setIsactive(true);
            
            spatialunitPersonwithinterestService.save(objSpatialUnitPersonWithInterest);
         
            return true;
        } catch (Exception e) {
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
    public boolean updateDeceasedPerson(HttpServletRequest request, HttpServletResponse response,Principal principal) {
        long Usin = 0;
        String firstname = "";
        String middlename = "";
        String lastname = "";
        long id = 0;
        
        String username = principal.getName();
		User userObj = userService.findByUniqueName(username);
		
		Long user_id = userObj.getId();
		
        LaParty laParty = new LaParty();
        SpatialunitDeceasedPerson spdeceased = new SpatialunitDeceasedPerson();

        try {
            Usin = ServletRequestUtils.getRequiredLongParameter(request, "usin_deceased");
            firstname = ServletRequestUtils.getRequiredStringParameter(request, "d_firstname");
            middlename = ServletRequestUtils.getRequiredStringParameter(request, "d_middlename");
            lastname = ServletRequestUtils.getRequiredStringParameter(request, "d_lastname");
            id = ServletRequestUtils.getRequiredLongParameter(request, "deceased_key");

            PersonType personType = registrationRecordsService.getPersonTypeById(8);
            if (id != 0) {
                spdeceased.setPartyid(id);
            }else
            {
            	
            	
    			laParty.setCreatedby(user_id.intValue());
    			laParty.setCreateddate(new Date());
    			laParty.setLaPartygroupPersontype(personType);
            }
            
            spdeceased.setFirstname(firstname);
            spdeceased.setMiddlename(middlename);
            spdeceased.setLastname(lastname);
            spdeceased.setLaSpatialunitLand(Usin);
            spdeceased.setCreatedby(user_id.intValue());
            spdeceased.setCreateddate(new Date());
            spdeceased.setLaPartygroupPersontype(personType);
            spdeceased.setIsactive(true);
            
            

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
    

    
    @RequestMapping(value = "/viewer/landrecords/spatialunit/{project}/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<SpatialUnitTemp> spatialUnitList(@PathVariable String project, @PathVariable Integer id) {
        return landRecordsService.findAllSpatialUnitTemp(project, id);
    }
    //for mapping
    
    
   @RequestMapping(value = "/viewer/landrecords/spatialunit/landrecord/{project}/{startfrom}", method = RequestMethod.GET)
    @ResponseBody
    public List<LaSpatialunitLand> spatialUnitList_p(@PathVariable String project, @PathVariable Integer startfrom,Principal principal) {
	   Integer projectId=0;
	   if(project.equalsIgnoreCase("default")){
		   return null;
	   }else{
		   try{
				 Project objproject=projectDAO.findByName(project);
				 projectId= objproject.getProjectnameid();
			}catch(Exception e){
				e.printStackTrace();
			}
        return landRecordsService.findAllSpatialUnitTemp_P(projectId+"", startfrom);
	   }
    }
   
   
   @RequestMapping(value = "/viewer/landrecords/spatialunit/totalRecord/{project}", method = RequestMethod.GET)
   @ResponseBody
   public Integer totalRecords(@PathVariable String project) {
	   Integer projectId=0;
	   if(null==project){
		   return 0;
	   }else{
		   try{
				 Project objproject=projectDAO.findByName(project);
				 projectId= objproject.getProjectnameid();
			}catch(Exception e){
				e.printStackTrace();
			}
		   return landRecordsService.getTotalrecordByProject(projectId+"");   
	   }
       
   }
   
   
   
   @RequestMapping(value = "/viewer/landrecords/spatialunit/commune/{project}", method = RequestMethod.GET)
   @ResponseBody
   public List<Commune> getprojectCommune(@PathVariable String project) {
	   
	  List<Commune> lstcommune=new ArrayList<Commune>();
	  List<ProjectArea> lst= projectAreaDAO.findByProjectName(project);
	  
	  if(lst.size()>0){
		for(ProjectArea obj:lst)  {
			Commune objc= new Commune();
			objc.setCommuneid(obj.getLaSpatialunitgroupHierarchy4().getHierarchyid());
			objc.setCommune(obj.getLaSpatialunitgroupHierarchy4().getNameEn());
			lstcommune.add(objc);
		}
		  
	  }
	return lstcommune;
   }
   
   
   
   @RequestMapping(value = "/viewer/landrecords/search1count/{project}",method = RequestMethod.POST)
   @ResponseBody
   public Integer search1Count(HttpServletRequest request, HttpServletResponse response,  Principal principal, @PathVariable String project)
   {
     
	   
	   Integer claimType =0;
	   Long appstatus=0l;
	   Integer communeId =0;
	   String transaction_id="";
	   String parce_id="";
	   Integer projectId=0;
	   
	   try {
          /* try {
        	   claimType = ServletRequestUtils.getRequiredIntParameter(request, "claim_type");
           } catch (Exception e) {
               logger.error(e);
           }
           
           try {
        	   appstatus = ServletRequestUtils.getRequiredLongParameter(request, "status_id");
           } catch (Exception e) {
               logger.error(e);
           }*/
           
           try {
        	   communeId = ServletRequestUtils.getRequiredIntParameter(request, "community_id");
           } catch (Exception e) {
               logger.error(e);
           }
           
           try {
        	   transaction_id = ServletRequestUtils.getRequiredStringParameter(request, "transaction_id");
           } catch (Exception e) {
               logger.error(e);
           }
           try {
        	   parce_id = ServletRequestUtils.getRequiredStringParameter(request, "parce_id");
           } catch (Exception e) {
               logger.error(e);
           }
           
           try{
				 Project objproject=projectDAO.findByName(project);
				 projectId= objproject.getProjectnameid();
			}catch(Exception e){
				e.printStackTrace();
			}
           
           return landRecordsService.searchCount(0l, 0, projectId+"",communeId+"",transaction_id,parce_id);
           
	   }catch(Exception e) {
           logger.error(e);
           return null;
       }
	   
	   
   
   }
   
    

   @RequestMapping(value = "/viewer/landrecords/search1/{project}/{startpos}" ,method = RequestMethod.POST)
   @ResponseBody
   public List<LaSpatialunitLand> searchSpatialUnit(HttpServletRequest request, HttpServletResponse response,  Principal principal, @PathVariable String project,@PathVariable Integer startpos)
   {
     
	   Integer claimType =0;
	   Long appstatus=0l;
	   Integer communeId =0;
	   String transaction_id="";
	   String parce_id="";
	   Integer projectId=0;
	  
	   
	   try {
          /* try {
        	   claimType = ServletRequestUtils.getRequiredIntParameter(request, "claim_type");
           } catch (Exception e) {
               logger.error(e);
           }
           
           try {
        	   appstatus = ServletRequestUtils.getRequiredLongParameter(request, "status_id");
           } catch (Exception e) {
               logger.error(e);
           }*/
           
           
           try {
        	   communeId = ServletRequestUtils.getRequiredIntParameter(request, "community_id");
           } catch (Exception e) {
               logger.error(e);
           }
           
           try {
        	   transaction_id = ServletRequestUtils.getRequiredStringParameter(request, "transaction_id");
           } catch (Exception e) {
               logger.error(e);
           }
           try {
        	   parce_id = ServletRequestUtils.getRequiredStringParameter(request, "parce_id");
           } catch (Exception e) {
               logger.error(e);
           }
           
           try{
				 Project objproject=projectDAO.findByName(project);
				 projectId= objproject.getProjectnameid();
			}catch(Exception e){
				e.printStackTrace();
			}
           
           return landRecordsService.search(0l, 0, projectId+"", communeId+"", transaction_id, parce_id, startpos);
           
	   }catch(Exception e) {
           logger.error(e);
           return null;
       }
	   
	   
   
   }
   
   
   
	@RequestMapping(value = "/viewer/landrecords/spatialunitbyworkflow/{project}/{startfrom}", method = RequestMethod.POST)
	@ResponseBody
	public List<LaSpatialunitLand> spatialUnitbyWorkflowList(HttpServletRequest request, HttpServletResponse response,@PathVariable String project, @PathVariable Integer startfrom,Principal principal) {
   
		int[] workflow_ids = null;
		int[] claim_ids = null;
		int[] status_ids = null;
		int projectId=0;
		
		
		try {
			
						
						try {
							workflow_ids = ServletRequestUtils.getRequiredIntParameters(request, "workflow");
						} catch (Exception e) {
							e.printStackTrace();
						}
		
		
						try {
							claim_ids = ServletRequestUtils.getRequiredIntParameters(request, "claimType");
						} catch (Exception e) {
							e.printStackTrace();
						}
		
					
						try {
							status_ids = ServletRequestUtils.getRequiredIntParameters(request, "statusType");
						} catch (Exception e) {
							e.printStackTrace();
						}
						
						try{
							 Project objproject=projectDAO.findByName(project);
							 projectId= objproject.getProjectnameid();
						}catch(Exception e){
							e.printStackTrace();
						}
						
					

			
			return landRecordsService.getspatialUnitWorkFlowResult(workflow_ids, claim_ids, status_ids, startfrom, projectId+"");
			
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
		
		
		
		
		
		
		
	}
	
	
	
	@RequestMapping(value = "/viewer/landrecords/spatialunitbyworkflow/count/{project}", method = RequestMethod.POST)
	@ResponseBody
	public Integer spatialUnitbyWorkflowCount(HttpServletRequest request, HttpServletResponse response,@PathVariable String project, Principal principal) {
   
   
		int[] workflow_ids = null;
		int[] claim_ids = null;
		int[] status_ids = null;
		int projectId=0;
		try {
			
			
				
				try {
					workflow_ids = ServletRequestUtils.getRequiredIntParameters(request, "workflow");
				} catch (Exception e) {
					e.printStackTrace();
				}

			

				try {
					claim_ids = ServletRequestUtils.getRequiredIntParameters(request, "claimType");
				} catch (Exception e) {
					e.printStackTrace();
				}

			
			
				try {
					status_ids = ServletRequestUtils.getRequiredIntParameters(request, "statusType");
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			try{
			 Project objproject=projectDAO.findByName(project);
			 projectId= objproject.getProjectnameid();
			}catch(Exception e){
				e.printStackTrace();
			}
				
			
			return landRecordsService.spatialUnitWorkflowCount(workflow_ids,claim_ids, status_ids,projectId+"");
			
			
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
		
		
		
		
		
	}
	
	
   
   @RequestMapping(value = "/viewer/landrecords/parcelcountbytenure/{project}/{tag}/{villageId}", method = RequestMethod.GET)
   @ResponseBody
   public void report1(HttpServletRequest request,HttpServletResponse response,@PathVariable String project,@PathVariable String tag) 
		   throws FileNotFoundException, IOException {

	   Workbook wb = new HSSFWorkbook();

	   String filename="";
	   if(tag.equalsIgnoreCase("NEW")){
		   filename="Report_By_Tenure_Map_register.xls";
	   }
	   else if(tag.equalsIgnoreCase("REGISTERED")){
		   filename="Report_By_Tenure_Application_register.xls";
	   }
	   else if(tag.equalsIgnoreCase("APFR")){
		   filename="Report_By_Tenure_APFR_register.xls";
	   }

	   // Create a blank sheet
	   Sheet sheet = wb.createSheet("new sheet");

	   List<Object> vertexLst = landRecordsService.findsummaryreport(project);
	   String[] columnList = { "Commune_Name", "Parcels", "Total Percentage", "Total Area", "Average Area", "Certificate Issued" };

	   int rowCount = 0;

	   Row row = sheet.createRow(rowCount++);

	   int columnCount = 0;
	   for (String header : columnList) {
		   Cell cell = row.createCell(columnCount++);

		   cell.setCellValue((String) header);

	   }
	   String Totalstr = "Total";
	   Double totala = 0.0;
	   Double totalb = 0.0;
	   Double totalc = 0.0;
	   Double totald = 0.0;
	   Double totale = 0.0;
	   
	   try {
		   if(vertexLst!=null)
			   for (int i = 0; i < vertexLst.size(); i++) {
				   row = sheet.createRow(rowCount++);
				   columnCount = 0;

				   Object[] obj = (Object[]) vertexLst.get(i);
				   String id = (String) obj[0];
				   Cell cell = row.createCell(columnCount++);
				   cell.setCellValue(id);

				   String a = (String) obj[1].toString();
				   Double dla=0.0;
				   if(a!="")
					   dla=Double.parseDouble(a);
				   cell = row.createCell(columnCount++);
				   cell.setCellValue(dla);
				  
				   totala = totala + dla;
				   
				   String b = (String) obj[2].toString();
				   Double dlb=0.0;
				   if(b!="")
					   dlb=Double.parseDouble(b);
				   cell = row.createCell(columnCount++);
				   cell.setCellValue(dlb);
				   totalb = totalb + dlb;
				   
				   String c = (String) obj[3].toString();
				   Double dlc=0.0;
				   if(c!="")
					   dlc=Double.parseDouble(c);
				   cell = row.createCell(columnCount++);
				   cell.setCellValue(dlc);
				   totalc = totalc + dlc;
				   
				   
				   String d = (String) obj[4].toString();
				   Double dld=0.0;
				   if(d!="")
					   dld=Double.parseDouble(d);
				   cell = row.createCell(columnCount++);
				   cell.setCellValue(dld);
				   totald = totald + dld;
				   
				 
				   String e = (String) obj[5].toString();
				   Double dle=0.0;
				   if(e!="")
					   dle=Double.parseDouble(e);
				   cell = row.createCell(columnCount++);
				   cell.setCellValue(dle);
				   totale = totale + dle;
				   
				   
				   cell = row.createCell(columnCount++);
				   cell.setCellValue("");
				   
			   }
		   
		   Double[] sumList = { 0.0, totala, totalb, totalc, totald, totale };
		   int columnCounttotal = 0;
		   Row rowtotal = sheet.createRow(rowCount++);
		   rowtotal = sheet.createRow(rowCount++);
		   
		  // for (Double footer : sumList) 
			   for ( int i = 0; i < sumList.length; i++)
		   {
			   Cell cell = rowtotal.createCell(columnCounttotal++);
			   if(i == 0.0)
			   {
				   cell.setCellValue(Totalstr);
			   }
			   else
			   {
				   cell.setCellValue((Double) sumList[i]);
			   }


		   }
		   


	   } catch (Exception e) {
		   logger.error(e);
	   }


	   response.setHeader("Content-Disposition", "attachment; filename="+filename);
	   response.setContentType("application/xls");
	   OutputStream out = response.getOutputStream();

	   wb.write(out);
	   out.flush();
	   out.close();
   }
   
   @RequestMapping(value = "/viewer/landrecords/projectdetailedsummaryreport/{project}/{tag}/{villageId}", method = RequestMethod.GET)
   @ResponseBody
   public void report2(HttpServletRequest request,HttpServletResponse response,@PathVariable String project,@PathVariable String tag) throws FileNotFoundException, IOException 
   {

	   Workbook wb = new HSSFWorkbook();

	   String filename="";
	   if(tag.equalsIgnoreCase("NEW")){
		   filename="Project_Detailed_Summary_Report.xls";
	   }
	   else if(tag.equalsIgnoreCase("REGISTERED")){
		   filename="Report_By_Tenure_Application_register.xls";
	   }
	   else if(tag.equalsIgnoreCase("APFR")){
		   filename="Report_By_Tenure_APFR_register.xls";
	   }

	   // Create a blank sheet
	   Sheet sheet = wb.createSheet("new sheet");

	   List<Object> vertexLst = landRecordsService.findprojectdetailedsummaryreport(project);
	   String[] columnList = { "Tenure Type", "Gender", "Parcels",  "Total Percentage", "Total Area", "Average Area", "Certificate Issued" };

	   int rowCount = 0;

	   Row row = sheet.createRow(rowCount++);

	   int columnCount = 0;
	   for (String header : columnList) {
		   Cell cell = row.createCell(columnCount++);

		   cell.setCellValue((String) header);

	   }
	   
	   String Totalstr = "Total";
	   Double totala = 0.0;
	   Double totalb = 0.0;
	   Double totalc = 0.0;
	   Double totald = 0.0;
	   Double totale = 0.0;
	   Double totalf = 0.0;
	   
	   try {
		   if(vertexLst!=null)
			   for (int i = 0; i < vertexLst.size(); i++) {
				   row = sheet.createRow(rowCount++);
				   columnCount = 0;

				   Object[] obj = (Object[]) vertexLst.get(i);
				   String id = (String) obj[0];
				   Cell cell = row.createCell(columnCount++);
				   cell.setCellValue(id);

				   String a = (String) obj[1].toString();
				   cell = row.createCell(columnCount++);
				   cell.setCellValue(a.toString());
				   
				   String b = (String) obj[2].toString();
				   Double dlb=0.0;
				   if(b!="")
					   dlb=Double.parseDouble(b);
				   cell = row.createCell(columnCount++);
				   cell.setCellValue(dlb);
				   totalb = totalb + dlb;
				   
				   String c = (String) obj[3].toString();
				   Double dlc=0.0;
				   if(c!="")
					   dlc=Double.parseDouble(c);
				   cell = row.createCell(columnCount++);
				   cell.setCellValue(dlc);
				   totalc = totalc + dlc;
				   
				   
				   String d = (String) obj[4].toString();
				   Double dld=0.0;
				   if(d!="")
					   dld=Double.parseDouble(d);
				   cell = row.createCell(columnCount++);
				   cell.setCellValue(dld);
				   totald = totald + dld;
				   
				 
				   String e = (String) obj[5].toString();
				   Double dle=0.0;
				   if(e!="")
					   dle=Double.parseDouble(e);
				   cell = row.createCell(columnCount++);
				   cell.setCellValue(dle);
				   totale = totale + dle;
				   
				   String f = (String) obj[6].toString();
				   Double dlf=0.0;
				   if(f!="")
					   dlf=Double.parseDouble(f);
				   cell = row.createCell(columnCount++);
				   cell.setCellValue(dlf);
				   totalf = totalf + dlf;
				   
				   cell = row.createCell(columnCount++);
				   cell.setCellValue("");
				   
			   }
		   
		   Double[] sumList = { 0.0, totala, totalb, totalc, totald, totale, totalf };
		   int columnCounttotal = 0;
		   Row rowtotal = sheet.createRow(rowCount++);
		   rowtotal = sheet.createRow(rowCount++);
		   
		  // for (Double footer : sumList) 
			   for ( int i = 0; i < sumList.length; i++)
		   {
			   Cell cell = rowtotal.createCell(columnCounttotal++);
			   if(i == 0.0)
			   {
				   cell.setCellValue(Totalstr);
			   }
			   else
			   {
				   cell.setCellValue((Double) sumList[i]);
			   }


		   }
		   


	   } catch (Exception e) {
		   logger.error(e);
	   }


	   response.setHeader("Content-Disposition", "attachment; filename="+filename);
	   response.setContentType("application/xls");
	   OutputStream out = response.getOutputStream();

	   wb.write(out);
	   out.flush();
	   out.close();
   }

   
   @RequestMapping(value = "/viewer/landrecords/projectdetailedappstatussummaryreport/{project}/{tag}/{villageId}", method = RequestMethod.GET)
   @ResponseBody
   public void report5(HttpServletRequest request,HttpServletResponse response,@PathVariable String project,@PathVariable String tag) throws FileNotFoundException, IOException 
   {

	   Workbook wb = new HSSFWorkbook();

	   String filename="";
	   if(tag.equalsIgnoreCase("NEW")){
		   filename="Project_Application_Status_Summary_Report.xls";
	   }
	   else if(tag.equalsIgnoreCase("REGISTERED")){
		   filename="Report_By_Tenure_Application_register.xls";
	   }
	   else if(tag.equalsIgnoreCase("APFR")){
		   filename="Report_By_Tenure_APFR_register.xls";
	   }

	   // Create a blank sheet
	   Sheet sheet = wb.createSheet("new sheet");

	   List<Object> vertexLst = landRecordsService.findprojectapplicationstatussummaryreport(project);
	   String[] columnList = { "New Application", "Approved application", "Rejected application",  "Pending application", "Registred application" };

	   int rowCount = 0;

	   Row row = sheet.createRow(rowCount++);

	   int columnCount = 0;
	   for (String header : columnList) {
		   Cell cell = row.createCell(columnCount++);

		   cell.setCellValue((String) header);

	   }
	   
	   String Totalstr = "Total";
	   Double totala = 0.0;
	   Double totalb = 0.0;
	   Double totalc = 0.0;
	   Double totald = 0.0;
	   Double totale = 0.0;
	  
	   
	   try {
		   if(vertexLst!=null)
			   for (int i = 0; i < vertexLst.size(); i++) {
				   row = sheet.createRow(rowCount++);
				   columnCount = 0;

				   Object[] obj = (Object[]) vertexLst.get(i);
				   String id = obj[0].toString();
				   Cell cell = row.createCell(columnCount++);
				   cell.setCellValue(id);

				   /*String a = (String) obj[1].toString();
				   cell = row.createCell(columnCount++);
				   cell.setCellValue(a.toString());*/
				   
				   String a = obj[1].toString();
				   Double dla=0.0;
				   if(a!="")
					   dla=Double.parseDouble(a);
				   cell = row.createCell(columnCount++);
				   cell.setCellValue(dla);
				   totala = totala + dla;
				   
				   
				   String b = obj[2].toString();
				   Double dlb=0.0;
				   if(b!="")
					   dlb=Double.parseDouble(b);
				   cell = row.createCell(columnCount++);
				   cell.setCellValue(dlb);
				   totalb = totalb + dlb;
				   
				   String c = obj[3].toString();
				   Double dlc=0.0;
				   if(c!="")
					   dlc=Double.parseDouble(c);
				   cell = row.createCell(columnCount++);
				   cell.setCellValue(dlc);
				   totalc = totalc + dlc;
				   
				   
				   String d = obj[4].toString();
				   Double dld=0.0;
				   if(d!="")
					   dld=Double.parseDouble(d);
				   cell = row.createCell(columnCount++);
				   cell.setCellValue(dld);
				   //totald = totald + dld;
				   
				 
				   /*String e = obj[5].toString();
				   Double dle=0.0;
				   if(e!="")
					   dle=Double.parseDouble(e);
				   cell = row.createCell(columnCount++);
				   cell.setCellValue(dle);
				   totale = totale + dle;*/
				   
				  
				   
				   cell = row.createCell(columnCount++);
				   cell.setCellValue("");
				   
			   }
		   
		  /* Double[] sumList = { 0.0, totala, totalb, totalc, totald, totale };
		   int columnCounttotal = 0;
		   Row rowtotal = sheet.createRow(rowCount++);
		   rowtotal = sheet.createRow(rowCount++);
		   
		  // for (Double footer : sumList) 
			   for ( int i = 0; i < sumList.length; i++)
		   {
			   Cell cell = rowtotal.createCell(columnCounttotal++);
			   if(i == 0.0)
			   {
				   cell.setCellValue(Totalstr);
			   }
			   else
			   {
				   cell.setCellValue((Double) sumList[i]);
			   }


		   }*/
		   


	   } catch (Exception e) {
		   logger.error(e);
	   }


	   response.setHeader("Content-Disposition", "attachment; filename="+filename);
	   response.setContentType("application/xls");
	   OutputStream out = response.getOutputStream();

	   wb.write(out);
	   out.flush();
	   out.close();
   }
   
   @RequestMapping(value = "/viewer/landrecords/projectdetailedtypestatussummaryreport/{project}/{tag}/{villageId}", method = RequestMethod.GET)
   @ResponseBody
   public void report6(HttpServletRequest request,HttpServletResponse response,@PathVariable String project,@PathVariable String tag) throws FileNotFoundException, IOException 
   {

	   Workbook wb = new HSSFWorkbook();

	   String filename="";
	   if(tag.equalsIgnoreCase("NEW")){
		   filename="Project_Application_Type_Summary_Report.xls";
	   }
	   else if(tag.equalsIgnoreCase("REGISTERED")){
		   filename="Report_By_Tenure_Application_register.xls";
	   }
	   else if(tag.equalsIgnoreCase("APFR")){
		   filename="Report_By_Tenure_APFR_register.xls";
	   }

	   // Create a blank sheet
	   Sheet sheet = wb.createSheet("new sheet");

	   List<Object> vertexLst = landRecordsService.findprojectapplicationtypesummaryreport(project);
	   String[] columnList = { "Disputed Application", "Existing Rights", "New Claim",  "Unclaimed", "No Claim" };

	   int rowCount = 0;

	   Row row = sheet.createRow(rowCount++);

	   int columnCount = 0;
	   for (String header : columnList) {
		   Cell cell = row.createCell(columnCount++);

		   cell.setCellValue((String) header);

	   }
	   
	   String Totalstr = "Total";
	   Double totala = 0.0;
	   Double totalb = 0.0;
	   Double totalc = 0.0;
	   Double totald = 0.0;
	   Double totale = 0.0;
	  
	   
	   try {
		   if(vertexLst!=null)
			   for (int i = 0; i < vertexLst.size(); i++) {
				   row = sheet.createRow(rowCount++);
				   columnCount = 0;

				   Object[] obj = (Object[]) vertexLst.get(i);
				   String id = obj[0].toString();
				   Cell cell = row.createCell(columnCount++);
				   cell.setCellValue(id);

				   /*String a = (String) obj[1].toString();
				   cell = row.createCell(columnCount++);
				   cell.setCellValue(a.toString());*/
				   
				   String a = obj[1].toString();
				   Double dla=0.0;
				   if(a!="")
					   dla=Double.parseDouble(a);
				   cell = row.createCell(columnCount++);
				   cell.setCellValue(dla);
				   totala = totala + dla;
				   
				   
				   String b = obj[2].toString();
				   Double dlb=0.0;
				   if(b!="")
					   dlb=Double.parseDouble(b);
				   cell = row.createCell(columnCount++);
				   cell.setCellValue(dlb);
				   totalb = totalb + dlb;
				   
				   String c = obj[3].toString();
				   Double dlc=0.0;
				   if(c!="")
					   dlc=Double.parseDouble(c);
				   cell = row.createCell(columnCount++);
				   cell.setCellValue(dlc);
				   totalc = totalc + dlc;
				   
				   
				   String d = obj[4].toString();
				   Double dld=0.0;
				   if(d!="")
					   dld=Double.parseDouble(d);
				   cell = row.createCell(columnCount++);
				   cell.setCellValue(dld);
				   //totald = totald + dld;
				   
				 
				   /*String e = obj[5].toString();
				   Double dle=0.0;
				   if(e!="")
					   dle=Double.parseDouble(e);
				   cell = row.createCell(columnCount++);
				   cell.setCellValue(dle);
				   totale = totale + dle;*/
				   
				  
				   
				   cell = row.createCell(columnCount++);
				   cell.setCellValue("");
				   
			   }
		   
		  /* Double[] sumList = { 0.0, totala, totalb, totalc, totald, totale };
		   int columnCounttotal = 0;
		   Row rowtotal = sheet.createRow(rowCount++);
		   rowtotal = sheet.createRow(rowCount++);
		   
		  // for (Double footer : sumList) 
			   for ( int i = 0; i < sumList.length; i++)
		   {
			   Cell cell = rowtotal.createCell(columnCounttotal++);
			   if(i == 0.0)
			   {
				   cell.setCellValue(Totalstr);
			   }
			   else
			   {
				   cell.setCellValue((Double) sumList[i]);
			   }


		   }*/
		   


	   } catch (Exception e) {
		   logger.error(e);
	   }


	   response.setHeader("Content-Disposition", "attachment; filename="+filename);
	   response.setContentType("application/xls");
	   OutputStream out = response.getOutputStream();

	   wb.write(out);
	   out.flush();
	   out.close();
   }
   
   @RequestMapping(value = "/viewer/landrecords/projectdetailedworkflowsummaryreport/{project}/{tag}/{villageId}", method = RequestMethod.GET)
   @ResponseBody
   public void report7(HttpServletRequest request,HttpServletResponse response,@PathVariable String project,@PathVariable String tag) throws FileNotFoundException, IOException 
   {

	   Workbook wb = new HSSFWorkbook();

	   String filename="";
	   if(tag.equalsIgnoreCase("NEW")){
		   filename="Project_WorkFlow_Summary_Report.xls";
	   }
	   else if(tag.equalsIgnoreCase("REGISTERED")){
		   filename="Report_By_Tenure_Application_register.xls";
	   }
	   else if(tag.equalsIgnoreCase("APFR")){
		   filename="Report_By_Tenure_APFR_register.xls";
	   }

	   // Create a blank sheet
	   Sheet sheet = wb.createSheet("new sheet");

	   List<Object> vertexLst = landRecordsService.findprojectworkflowsummaryreport(project);
	   String[] columnList = { "Parcel No", "Captured Time", "Processing Date",  "Approval Date", "Certificate Date" };

	   int rowCount = 0;

	   Row row = sheet.createRow(rowCount++);

	   int columnCount = 0;
	   for (String header : columnList) {
		   Cell cell = row.createCell(columnCount++);

		   cell.setCellValue((String) header);

	   }
	   
	   String Totalstr = "Total";
	   Double totala = 0.0;
	   Double totalb = 0.0;
	   Double totalc = 0.0;
	   Double totald = 0.0;
	   Double totale = 0.0;
	  
	   
	   try {
		   if(vertexLst!=null)
			   for (int i = 0; i < vertexLst.size(); i++) {
				   row = sheet.createRow(rowCount++);
				   columnCount = 0;

				   Object[] obj = (Object[]) vertexLst.get(i);
				   
				   String id = obj[0].toString();
				   Cell cell = row.createCell(columnCount++);
				   cell.setCellValue(id);

				   if(obj[1] != null)
				   {
					   String a = obj[1].toString();
					   cell = row.createCell(columnCount++);
					   cell.setCellValue(a);
				   }
				   
				   if(obj[2] != null)
				   {
					   String b = obj[2].toString();
					   cell = row.createCell(columnCount++);
					   cell.setCellValue(b);
				   }
				   
				   if(obj[3] != null)
				   {
					   String c = obj[3].toString();
					   cell = row.createCell(columnCount++);
					   cell.setCellValue(c);
				   }
				   
				   if(obj[4] != null)
				   {
					   String d = obj[4].toString();
					   Double dld=0.0;
					   cell = row.createCell(columnCount++);
					   cell.setCellValue(d);
				   }
				   
				   
				   cell = row.createCell(columnCount++);
				   cell.setCellValue("");
				   
			   }


	   } catch (Exception e) {
		   logger.error(e);
	   }


	   response.setHeader("Content-Disposition", "attachment; filename="+filename);
	   response.setContentType("application/xls");
	   OutputStream out = response.getOutputStream();

	   wb.write(out);
	   out.flush();
	   out.close();
   }
   
   @RequestMapping(value = "/viewer/landrecords/projectdetailedTenureTypesLandUnitSummaryreport/{project}/{tag}/{villageId}", method = RequestMethod.GET)
   @ResponseBody
   public void report8(HttpServletRequest request,HttpServletResponse response,@PathVariable String project,@PathVariable String tag) throws FileNotFoundException, IOException 
   {

	   Workbook wb = new HSSFWorkbook();

	   String filename="";
	   if(tag.equalsIgnoreCase("NEW")){
		   filename="Project_Tenure_Types_LandUnit_Summary_Report.xls";
	   }
	   else if(tag.equalsIgnoreCase("REGISTERED")){
		   filename="Report_By_Tenure_Application_register.xls";
	   }
	   else if(tag.equalsIgnoreCase("APFR")){
		   filename="Report_By_Tenure_APFR_register.xls";
	   }

	   // Create a blank sheet
	   Sheet sheet = wb.createSheet("new sheet");

	   List<Object> vertexLst = landRecordsService.findprojectTenureTypesLandUnitsummaryreport(project);
	   String[] columnList = { "Parcel No", "Owner", "Gender", "OwnershipType", "Age" };

	   int rowCount = 0;

	   Row row = sheet.createRow(rowCount++);

	   int columnCount = 0;
	   for (String header : columnList) {
		   Cell cell = row.createCell(columnCount++);

		   cell.setCellValue((String) header);

	   }
	   
	   String Totalstr = "Total";
	   Double totala = 0.0;
	   Double totalb = 0.0;
	   Double totalc = 0.0;
	   Double totald = 0.0;
	   Double totale = 0.0;
	  
	   
	   try {
		   if(vertexLst!=null)
			   for (int i = 0; i < vertexLst.size(); i++) {
				   row = sheet.createRow(rowCount++);
				   columnCount = 0;

				   Object[] obj = (Object[]) vertexLst.get(i);
				   
				   String id = obj[0].toString();
				   Cell cell = row.createCell(columnCount++);
				   cell.setCellValue(id);

				   if(obj[1] != null)
				   {
					   String a = obj[1].toString();
					   cell = row.createCell(columnCount++);
					   cell.setCellValue(a);
				   }
				   
				   if(obj[2] != null)
				   {
					   String b = obj[2].toString();
					   cell = row.createCell(columnCount++);
					   cell.setCellValue(b);
				   }
				   
				   if(obj[3] != null)
				   {
					   String c = obj[3].toString();
					   cell = row.createCell(columnCount++);
					   cell.setCellValue(c);
				   }
				   
				   if(obj[4] != null)
				   {
					   String d = obj[4].toString();
					   Double dld=0.0;
					   cell = row.createCell(columnCount++);
					   cell.setCellValue(d);
				   }
				   
				   
				   cell = row.createCell(columnCount++);
				   cell.setCellValue("");
				   
			   }


	   } catch (Exception e) {
		   logger.error(e);
	   }


	   response.setHeader("Content-Disposition", "attachment; filename="+filename);
	   response.setContentType("application/xls");
	   OutputStream out = response.getOutputStream();

	   wb.write(out);
	   out.flush();
	   out.close();
   }
   
   @RequestMapping(value = "/viewer/landrecords/projectdetailedliberiafarmSummaryreport/{project}/{tag}/{villageId}", method = RequestMethod.GET)
   @ResponseBody
   public void reportliberia(HttpServletRequest request,HttpServletResponse response,@PathVariable String project,@PathVariable String tag) throws FileNotFoundException, IOException 
   {

	   Workbook wb = new HSSFWorkbook();

	   String filename="";
	   if(tag.equalsIgnoreCase("NEW")){
		   filename="Liberia_Farm_Summary_Report.xls";
	   }
	   else if(tag.equalsIgnoreCase("REGISTERED")){
		   filename="Report_By_Tenure_Application_register.xls";
	   }
	   else if(tag.equalsIgnoreCase("APFR")){
		   filename="Report_By_Tenure_APFR_register.xls";
	   }

	   // Create a blank sheet
	   Sheet sheet = wb.createSheet("new sheet");

	   List<Object> vertexLst = landRecordsService.findLiberiaFarmummaryreport(project);
	   String[] columnList = { "Field ID", "Date of collection", "Data Collector", "Enterprise Group Name", "County", "District", "Clan Name", "Community Forest", "Name of Town", 
			   "Resource Classification", "Resource SubClassification", "Size of Plot", "Type of Tenure", "Type of person", "Name", "POI - Yes/No", "Marital Status",
			   "Relationship", "Gender", "Ethnicity/Clan", "Resident", "DOB", "Mobile No", "Primary Crop", "Primary Crop date", "Primary Crop duration", "Secondary Crop", "Secondary Crop date","Secondary Crop duration"};

	   int rowCount = 0;

	   Row row = sheet.createRow(rowCount++);

	   int columnCount = 0;
	   for (String header : columnList) {
		   Cell cell = row.createCell(columnCount++);

		   cell.setCellValue((String) header);

	   }
	   
	   try {
		   if(vertexLst!=null)
			   for (int i = 0; i < vertexLst.size(); i++) {
				   row = sheet.createRow(rowCount++);
				   columnCount = 0;

				   Object[] obj = (Object[]) vertexLst.get(i);
				   
				   String id = obj[0].toString();
				   Cell cell = row.createCell(columnCount++);
				   cell.setCellValue(id);

				   if(obj[1] != null)
				   {
					   String a = obj[1].toString();
					   cell = row.createCell(columnCount++);
					   cell.setCellValue(a);
				   }
				   else
				   {
					   cell = row.createCell(columnCount++);
					   cell.setCellValue("");
				   }
				   
				   if(obj[2] != null)
				   {
					   String b = obj[2].toString();
					   cell = row.createCell(columnCount++);
					   cell.setCellValue(b);
				   }
				   else
				   {
					   cell = row.createCell(columnCount++);
					   cell.setCellValue("");
				   }
				   
				   if(obj[3] != null)
				   {
					   String c = obj[3].toString();
					   cell = row.createCell(columnCount++);
					   cell.setCellValue(c);
				   }
				   else
				   {
					   cell = row.createCell(columnCount++);
					   cell.setCellValue("");
				   }
				   
				   
				   if(obj[4] != null)
				   {
					   String d = obj[4].toString();					   
					   cell = row.createCell(columnCount++);
					   cell.setCellValue(d);
				   }
				   else
				   {
					   cell = row.createCell(columnCount++);
					   cell.setCellValue("");
				   }
				   
				   
				   if(obj[5] != null)
				   {
					   String e = obj[5].toString();					   
					   cell = row.createCell(columnCount++);
					   cell.setCellValue(e);
				   }
				   else
				   {
					   cell = row.createCell(columnCount++);
					   cell.setCellValue("");
				   }
				   
				   
				   if(obj[6] != null)
				   {
					   String f = obj[6].toString();					   
					   cell = row.createCell(columnCount++);
					   cell.setCellValue(f);
				   }
				   else
				   {
					   cell = row.createCell(columnCount++);
					   cell.setCellValue("");
				   }
				   
				   
				   if(obj[7] != null)
				   {
					   String g = obj[7].toString();					   
					   cell = row.createCell(columnCount++);
					   cell.setCellValue(g);
				   }
				   else
				   {
					   cell = row.createCell(columnCount++);
					   cell.setCellValue("");
				   }
				   
				   
				   if(obj[8] != null)
				   {
					   String h = obj[8].toString();					   
					   cell = row.createCell(columnCount++);
					   cell.setCellValue(h);
				   }
				   else
				   {
					   cell = row.createCell(columnCount++);
					   cell.setCellValue("");
				   }
				   
				   
				   if(obj[9] != null)
				   {
					   String ii = obj[9].toString();					   
					   cell = row.createCell(columnCount++);
					   cell.setCellValue(ii);
				   }
				   else
				   {
					   cell = row.createCell(columnCount++);
					   cell.setCellValue("");
				   }
				   
				   
				   if(obj[10] != null)
				   {
					   String j = obj[10].toString();					   
					   cell = row.createCell(columnCount++);
					   cell.setCellValue(j);
				   }
				   else
				   {
					   cell = row.createCell(columnCount++);
					   cell.setCellValue("");
				   }
				   
				   
				   if(obj[11] != null)
				   {
					   String k = obj[11].toString();					   
					   cell = row.createCell(columnCount++);
					   cell.setCellValue(k);
				   }

				   else
				   {
					   cell = row.createCell(columnCount++);
					   cell.setCellValue("");
				   }
				   
				   if(obj[12] != null)
				   {
					   String l = obj[12].toString();					   
					   cell = row.createCell(columnCount++);
					   cell.setCellValue(l);
				   }
				   else
				   {
					   cell = row.createCell(columnCount++);
					   cell.setCellValue("");
				   }
				   
				   
				   if(obj[13] != null)
				   {
					   String m = obj[13].toString();					   
					   cell = row.createCell(columnCount++);
					   cell.setCellValue(m);
				   }
				   else
				   {
					   cell = row.createCell(columnCount++);
					   cell.setCellValue("");
				   }
				   
				   
				   if(obj[14] != null)
				   {
					   String n = obj[14].toString();					   
					   cell = row.createCell(columnCount++);
					   cell.setCellValue(n);
				   }
				   else
				   {
					   cell = row.createCell(columnCount++);
					   cell.setCellValue("");
				   }
				   
				   
				   if(obj[15] != null)
				   {
					   String o = obj[15].toString();					   
					   cell = row.createCell(columnCount++);
					   cell.setCellValue(0);
				   }
				   else
				   {
					   cell = row.createCell(columnCount++);
					   cell.setCellValue("");
				   }
				   
				   
				   if(obj[16] != null)
				   {
					   String p = obj[16].toString();					   
					   cell = row.createCell(columnCount++);
					   cell.setCellValue(p);
				   }
				   else
				   {
					   cell = row.createCell(columnCount++);
					   cell.setCellValue("");
				   }
				   
				   
				   if(obj[17] != null)
				   {
					   String q = obj[17].toString();					   
					   cell = row.createCell(columnCount++);
					   cell.setCellValue(q);
				   }
				   else
				   {
					   cell = row.createCell(columnCount++);
					   cell.setCellValue("");
				   }
				   
				   if(obj[18] != null)
				   {
					   String r = obj[18].toString();					   
					   cell = row.createCell(columnCount++);
					   cell.setCellValue(r);
				   }
				   else
				   {
					   cell = row.createCell(columnCount++);
					   cell.setCellValue("");
				   }
				   
				   
				   if(obj[19] != null)
				   {
					   String s = obj[19].toString();					   
					   cell = row.createCell(columnCount++);
					   cell.setCellValue(s);
				   }
				   else
				   {
					   cell = row.createCell(columnCount++);
					   cell.setCellValue("");
				   }
				   
				   
				   if(obj[20] != null)
				   {
					   String t = obj[20].toString();					   
					   cell = row.createCell(columnCount++);
					   cell.setCellValue(t);
				   }

				   else
				   {
					   cell = row.createCell(columnCount++);
					   cell.setCellValue("");
				   }
				   
				   if(obj[21] != null)
				   {
					   String u = obj[21].toString();					   
					   cell = row.createCell(columnCount++);
					   cell.setCellValue(u);
				   }
				   else
				   {
					   cell = row.createCell(columnCount++);
					   cell.setCellValue("");
				   }
				   

				   if(obj[22] != null)
				   {
					   String v = obj[22].toString();					   
					   cell = row.createCell(columnCount++);
					   cell.setCellValue(v);
				   }
				   else
				   {
					   cell = row.createCell(columnCount++);
					   cell.setCellValue("");
				   }
				   

				   if(obj[23] != null)
				   {
					   String w = obj[23].toString();					   
					   cell = row.createCell(columnCount++);
					   cell.setCellValue(w);
				   }
				   else
				   {
					   cell = row.createCell(columnCount++);
					   cell.setCellValue("");
				   }
				   

				   if(obj[24] != null)
				   {
					   String x = obj[24].toString();					   
					   cell = row.createCell(columnCount++);
					   cell.setCellValue(x);
				   }
				   else
				   {
					   cell = row.createCell(columnCount++);
					   cell.setCellValue("");
				   }
				   

				   if(obj[25] != null)
				   {
					   String y = obj[25].toString();					   
					   cell = row.createCell(columnCount++);
					   cell.setCellValue(y);
				   }
				   else
				   {
					   cell = row.createCell(columnCount++);
					   cell.setCellValue("");
				   }
				   

				   if(obj[26] != null)
				   {
					   String z = obj[26].toString();					   
					   cell = row.createCell(columnCount++);
					   cell.setCellValue(z);
				   }
				   else
				   {
					   cell = row.createCell(columnCount++);
					   cell.setCellValue("");
				   }
				   

				   if(obj[27] != null)
				   {
					   String z1 = obj[27].toString();					   
					   cell = row.createCell(columnCount++);
					   cell.setCellValue(z1);
				   }
				   else
				   {
					   cell = row.createCell(columnCount++);
					   cell.setCellValue("");
				   }
				   

				   if(obj[28] != null)
				   {
					   String z2 = obj[28].toString();					   
					   cell = row.createCell(columnCount++);
					   cell.setCellValue(z2);
				   }
				   else
				   {
					   cell = row.createCell(columnCount++);
					   cell.setCellValue("");
				   }
				   

				  /* if(obj[29] != null)
				   {
					   String z3 = obj[29].toString();					   
					   cell = row.createCell(columnCount++);
					   cell.setCellValue(z3);
				   }*/

				   cell = row.createCell(columnCount++);
				   cell.setCellValue("");
				   
			   }


	   } catch (Exception e) {
		   logger.error(e);
	   }


	   response.setHeader("Content-Disposition", "attachment; filename="+filename);
	   response.setContentType("application/xls");
	   OutputStream out = response.getOutputStream();

	   wb.write(out);
	   out.flush();
	   out.close();
   }
   
   
   @RequestMapping(value = "/viewer/landrecords/projectdetailedsummaryreportForCommune/{communeid}/{tag}/{villageId}", method = RequestMethod.GET)
   @ResponseBody
   public void report3(HttpServletRequest request,HttpServletResponse response,@PathVariable String communeid,@PathVariable String tag) throws FileNotFoundException, IOException 
   {

	   Workbook wb = new HSSFWorkbook();

	   String filename="";
	   if(tag.equalsIgnoreCase("NEW")){
		   filename="Project_Detailed_Summary_Report.xls";
	   }
	   else if(tag.equalsIgnoreCase("REGISTERED")){
		   filename="Report_By_Tenure_Application_register.xls";
	   }
	   else if(tag.equalsIgnoreCase("APFR")){
		   filename="Report_By_Tenure_APFR_register.xls";
	   }

	   // Create a blank sheet
	   Sheet sheet = wb.createSheet("new sheet");

	   List<Object> vertexLst = landRecordsService.findprojectdetailedsummaryreportForCommune(communeid);
	   String[] columnList = { "Tenure Type", "Gender", "Commne Name", "Parcels",  "Total Percentage", "Total Area", "Average Area", "Certificate Issued" };

	   int rowCount = 0;

	   Row row = sheet.createRow(rowCount++);

	   int columnCount = 0;
	   for (String header : columnList) {
		   Cell cell = row.createCell(columnCount++);

		   cell.setCellValue((String) header);

	   }

	   String Totalstr = "Total";
	   Double totala = 0.0;
	   Double totalb = 0.0;
	   Double totalc = 0.0;
	   Double totald = 0.0;
	   Double totale = 0.0;
	   Double totalf = 0.0;
	   Double totalg = 0.0;
	   try {
		   if(vertexLst!=null)
			   for (int i = 0; i < vertexLst.size(); i++) {
				   row = sheet.createRow(rowCount++);
				   columnCount = 0;

				   Object[] obj = (Object[]) vertexLst.get(i);
				   String id = (String) obj[0];
				   Cell cell = row.createCell(columnCount++);
				   cell.setCellValue(id);

				   String a = (String) obj[1].toString();
				   cell = row.createCell(columnCount++);
				   cell.setCellValue(a.toString());
				   
				   String b = (String) obj[2].toString();
				   cell = row.createCell(columnCount++);
				   cell.setCellValue(b.toString());
				   
				   String c = (String) obj[3].toString();
				   Double dlc=0.0;
				   if(c!="")
					   dlc=Double.parseDouble(c);
				   cell = row.createCell(columnCount++);
				   cell.setCellValue(dlc);
				   totalc = totalc + dlc;
				   
				   
				   String d = (String) obj[4].toString();
				   Double dld=0.0;
				   if(d!="")
					   dld=Double.parseDouble(d);
				   cell = row.createCell(columnCount++);
				   cell.setCellValue(dld);
				   totald = totald + dld;
				   
				 
				   String e = (String) obj[5].toString();
				   Double dle=0.0;
				   if(e!="")
					   dle=Double.parseDouble(e);
				   cell = row.createCell(columnCount++);
				   cell.setCellValue(dle);
				   totale = totale + dle;
				   
				   String f = (String) obj[6].toString();
				   Double dlf=0.0;
				   if(f!="")
					   dlf=Double.parseDouble(f);
				   cell = row.createCell(columnCount++);
				   cell.setCellValue(dlf);
				   totalf = totalf + dlf;
				   
				   String g = (String) obj[6].toString();
				   Double dlg=0.0;
				   if(g!="")
					   dlg=Double.parseDouble(g);
				   cell = row.createCell(columnCount++);
				   cell.setCellValue(dlg);
				   totalg = totalg + dlg;
				   
				   cell = row.createCell(columnCount++);
				   cell.setCellValue("");
				   
			   }
		   
		   Double[] sumList = { 0.0, totala, totalb, totalc, totald, totale, totalf , totalg  };
		   int columnCounttotal = 0;
		   Row rowtotal = sheet.createRow(rowCount++);
		   rowtotal = sheet.createRow(rowCount++);
		   
		  // for (Double footer : sumList) 
			   for ( int i = 0; i < sumList.length; i++)
		   {
			   Cell cell = rowtotal.createCell(columnCounttotal++);
			   if(i == 0.0)
			   {
				   cell.setCellValue(Totalstr);
			   }
			   else
			   {
				   cell.setCellValue((Double) sumList[i]);
			   }


		   }
		   


	   } catch (Exception e) {
		   logger.error(e);
	   }


	   response.setHeader("Content-Disposition", "attachment; filename="+filename);
	   response.setContentType("application/xls");
	   OutputStream out = response.getOutputStream();

	   wb.write(out);
	   out.flush();
	   out.close();
   }
   
   @RequestMapping(value = "/viewer/landrecords/parcelhistory/{landid}", method = RequestMethod.GET)
	@ResponseBody
	public List<Object>  getParcelHistorybylandid(@PathVariable Long landid)
	{
	   	List<Object> object=new ArrayList<Object>(); 
	   	Object objsaledetails = null;
		Object objleasedetails = null; 
		Object objmortagedetails = null;
		Object objtransactiondetails = null;
			
	   	try 
	   	{
			objsaledetails= landRecordsService.getownerhistorydetails(landid);
			objleasedetails= landRecordsService.getleasehistorydetails(landid);
			objmortagedetails= landRecordsService.getmortagagedetails(landid);
			objtransactiondetails= landRecordsService.gettransactiondetails(landid);
			
//	   		List<OwnerHistoryForFetch> ownersaledetails= landRecordsService.getownerhistorydetails(landid);
//	   	 for(OwnerHistoryForFetch obj: ownersaledetails){
//	   		
//	   			
//	   			
//	   			LaExtTransactiondetail transactionObj= laExtTransactiondetailDao.getLaExtTransactiondetail(obj.getTransactionid());
//	   			if(transactionObj.getProcessid() == 1){
//	   				objleasedetails = transactionObj;
//	   				object.add(objleasedetails);
//	   				}
//	   			else if(transactionObj.getProcessid() == 2){
//	   				objsaledetails = transactionObj;
//	   				object.add(objsaledetails);
//	   				}
//	   			else if(transactionObj.getProcessid() == 3){
//	   				objmortagedetails = transactionObj;
//	   				object.add(objmortagedetails);
//	   				}
//	   	 }
//	   		 
//	   		
//	   		 
//	   	 
////			objleasedetails= landRecordsService.getownerhistorydetails(landid);
////			objmortagedetails= landRecordsService.getownerhistorydetails(landid);
//			
			object.add(objsaledetails);
			object.add(objleasedetails);
			object.add(objmortagedetails); 
			object.add(objtransactiondetails);
			return object;
		} 
	   	catch (Exception e)
	   	{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
   
   
   
   @RequestMapping(value = "/viewer/landrecords/findsaledetailbytransid/{transactionid}", method = RequestMethod.GET)
 	@ResponseBody
 	public List<Object>  findsaledetailbyTransid(@PathVariable Long transactionid)
 	{
 	   	List<Object> object=new ArrayList<Object>(); 
 	   LaExtTransactionHistory lasaleobj =null;
 	   	//Object objsaledetails = null;
 		Object newobjperson = null; 
 		Object oldobjperson = null;
 		/*Object objtransactiondetails = null;*/
 			
 	   	try 
 	   	{
 	   		
 	   	lasaleobj = 	laExtTransactionHistorydao.getTransactionHistoryByTransId(transactionid.intValue());
 			//objsaledetails= landRecordsService.getownerhistorydetails(landid);
 	   
 	 
 	   	List<NaturalPerson> oldpersonobj =  laPartyDao.getObjectsBypartyId(lasaleobj.getOldownerid());
 	   	List<NaturalPerson> newpersonobj =  laPartyDao.getObjectsBypartyId(lasaleobj.getNewownerid());
 	  
 	 /*Gender genderobj = Genderdao.getGenderById(oldpersonobj.getGenderid().longValue());
 	oldpersonobj.setGender(genderobj.getGender_en());
 	
 	 Gender newgenderobj = Genderdao.getGenderById(newpersonobj.getGenderid().longValue());
 	newpersonobj.setGender(newgenderobj.getGender_en());*/
 	
 	newobjperson =(Object) newpersonobj;
 	oldobjperson =(Object) oldpersonobj;
 			/*objmortagedetails= landRecordsService.getmortagagedetails(landid);
 			objtransactiondetails= landRecordsService.gettransactiondetails(landid);*/
  			
 			//object.add(objsaledetails);
 			object.add(0, oldobjperson);
 			object.add(1, newobjperson); 
 			//object.add(objtransactiondetails);
 			return object;
 		} 
 	   	catch (Exception e)
 	   	{
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 			return null;
 		}
 	}
   
   @RequestMapping(value = "/viewer/landrecords/findleasedetailbylandid/{transactionid}/{landid}", method = RequestMethod.GET)
  	@ResponseBody
  	public List<Object>  findleasedetailbylandid(@PathVariable Long transactionid,@PathVariable Long landid)
  	{
  	   	List<Object> object=new ArrayList<Object>(); 
  	   	//Object objsaledetails = null;
  		Object objleasedetails = null; 
  		Object objdocumentdetails = null;
  		/*Object objtransactiondetails = null;*/
  			
  	   	try 
  	   	{
  			//objsaledetails= landRecordsService.getownerhistorydetails(landid);
  			objleasedetails= landRecordsService.findleasedetailbylandid(transactionid,landid);
  			objdocumentdetails= landRecordsService.viewdocumentdetailbytransactioid(transactionid);
  			/*objmortagedetails= landRecordsService.getmortagagedetails(landid);
  			objtransactiondetails= landRecordsService.gettransactiondetails(landid);*/
   			
  			//object.add(objsaledetails);
  			object.add(objleasedetails);
  			object.add(objdocumentdetails); 
  			//object.add(objtransactiondetails);
  			return object;
  		} 
  	   	catch (Exception e)
  	   	{
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  			return null;
  		}
  	}
   
   @RequestMapping(value = "/viewer/landrecords/findsurrenderleasedetailbylandid/{transactionid}/{landid}", method = RequestMethod.GET)
 	@ResponseBody
 	public List<Object>  findsurrenderleasedetailbylandid(@PathVariable Long transactionid,@PathVariable Long landid)
 	{
 	   	List<Object> object=new ArrayList<Object>(); 	   
 		Object objleasedetails = null; 
 		Object objdocumentdetails = null;
 	   	try 
 	   	{	
 			objleasedetails= landRecordsService.findsurrenderleasedetailbylandid(transactionid,landid);
 			objdocumentdetails= landRecordsService.viewdocumentdetailbytransactioid(transactionid);
 			object.add(objleasedetails);
 			object.add(objdocumentdetails); 
 			return object;
 		} 
 	   	catch (Exception e)
 	   	{
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 			return null;
 		}
 	}
   
   @RequestMapping(value = "/viewer/landrecords/findmortagagedetailbylandid/{transactionid}/{landid}", method = RequestMethod.GET)
 	@ResponseBody
 	public List<Object>  findmortagagedetailbylandid(@PathVariable Long transactionid,@PathVariable Long landid)
 	{
 	   	List<Object> object=new ArrayList<Object>();  	   	
 		Object objmortagedetails = null; 
 		Object objdocumentdetails = null;
 			
 	   	try 
 	   	{
 		
 	   	objmortagedetails= landRecordsService.findmortagagedetailbylandid(transactionid,landid);
 	   objdocumentdetails= landRecordsService.viewdocumentdetailbytransactioid(transactionid);
 			object.add(objmortagedetails);
 			object.add(objdocumentdetails); 
 			//object.add(objmortagedetails); 
 			//object.add(objtransactiondetails);
 			return object;
 		} 
 	   	catch (Exception e)
 	   	{
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 			return null;
 		}
 	}
   
   @RequestMapping(value = "/viewer/landrecords/findSurrendermortagagedetailbylandid/{transactionid}/{landid}", method = RequestMethod.GET)
	@ResponseBody
	public List<Object>  findSurrendermortagagedetailbylandid(@PathVariable Long transactionid,@PathVariable Long landid)
	{
	   	List<Object> object=new ArrayList<Object>();  	   	
		Object objmortagedetails = null; 
		Object objdocumentdetails = null;
			
	   	try 
	   	{
		
	   	objmortagedetails= landRecordsService.findSurrendermortagagedetailbylandid(transactionid,landid);
	   objdocumentdetails= landRecordsService.viewdocumentdetailbytransactioid(transactionid);
			object.add(objmortagedetails);
			object.add(objdocumentdetails); 
			//object.add(objmortagedetails); 
			//object.add(objtransactiondetails);
			return object;
		} 
	   	catch (Exception e)
	   	{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
   
   @RequestMapping(value = "/viewer/landrecords/viewdocumentdetail/{transactionid}", method = RequestMethod.GET)
	@ResponseBody
	public List<Object>  viewdocumentdetailbytransactioid(@PathVariable Long transactionid)
	{
	   	List<Object> object=new ArrayList<Object>();  	   	
		Object objmortagedetails = null; 
		
			
	   	try 
	   	{
		
	   	objmortagedetails= landRecordsService.viewdocumentdetailbytransactioid(transactionid);
			
			object.add(objmortagedetails);
			//object.add(objmortagedetails); 
			//object.add(objtransactiondetails);
			return object;
		} 
	   	catch (Exception e)
	   	{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

   
   @RequestMapping(value = "/viewer/user", method = RequestMethod.GET)
   @ResponseBody
   public List<AllocateUser> getUserByIdAom() {
   	return allocateUserDao.getAllocateUser();
   }
   
   

   @RequestMapping(value = "/viewer/landrecords/updateResource", method = RequestMethod.POST)
   @ResponseBody
   public boolean updateResouceAllocation(HttpServletRequest request, HttpServletResponse response,Principal principal) {
	   
	   String userId[] = null ;
		String allocId[] = null;

		String username = principal.getName();
		User userObj = userService.findByUniqueName(username);

		Long created_by = userObj.getId();
		Long modifiedby = created_by;// As discussed with rajendra sir modified by also same as created by
		int applicationstatusid =1;
		try {
			userId = request.getParameterValues("userId");
		} catch (Exception e) {
			logger.error(e);
		}

		try {
			allocId = request.getParameterValues("hid_allocid");
		} catch (Exception e) {
			logger.error(e);
		}

		return  allocateUserDao.updateUserAllocation(userId, allocId,1l, created_by.intValue(), modifiedby.intValue(), applicationstatusid);
   }
   
   @RequestMapping(value = "/viewer/landrecords/personsDataNew/{id}", method = RequestMethod.GET)
   @ResponseBody
   public List<NaturalPerson> getpersonsDataNew(HttpServletRequest request, @PathVariable Long id) {
	   
	   
	   
	   List<NaturalPerson> lstdata = new ArrayList<NaturalPerson>();
	   
  	 List<SocialTenureRelationship>  lst= new ArrayList<SocialTenureRelationship>();
  	 lst= landRecordsService.findAllSocialTenureByUsin(id);
  	 if(lst.size()>0)
  	 {
  		 for(SocialTenureRelationship obj:lst){
  			 
  			 if(obj.getLaPartygroupPersontype().getPersontypeid()== 1)
  			 {
  				NaturalPerson naturalobj = (NaturalPerson) laPartyDao.getActivePartyIdByID(obj.getPartyid());
  				if(null!=naturalobj){
  				lstdata.add(naturalobj);
  				}
  			 }
  			  			 
  		 }
  	 }
  	 /*if(lstdata.size()>0){
  		Collections.sort(lstdata);
  	 }*/
      return lstdata;
  
   }
   
   
   @RequestMapping(value = "/viewer/landrecords/Disputeperson/{id}", method = RequestMethod.GET)
   @ResponseBody
   public List<NaturalPerson> getDisputedperson(HttpServletRequest request, @PathVariable Long id) {
	   
	   
	   
	   List<NaturalPerson> lstdata = new ArrayList<NaturalPerson>();
	   
	   List<LaExtDisputelandmapping>  disputelandlst= new ArrayList<LaExtDisputelandmapping>();
		disputelandlst= laExtDisputelandmappingService.findLaExtDisputelandmappingListByLandId(id);
  	 if(disputelandlst.size()>0)
  	 {
  		for(LaExtDisputelandmapping obj:disputelandlst){
			 
			 if(obj.getPersontypeid()== 3)
			 {
				lstdata.add((NaturalPerson) laPartyDao.getPartyIdByID(obj.getPartyid()));
			 }
			 if(obj.getPersontypeid()== 10)
			 {
				lstdata.add((NaturalPerson) laPartyDao.getPartyIdByID(obj.getPartyid()));
			 }
			  			 
		 }
  	 }
      return lstdata;
  
   }
   
   
   
   @RequestMapping(value = "/viewer/landrecords/NonNaturalpersonsDataNew/{id}", method = RequestMethod.GET)
   @ResponseBody
   public List<NonNaturalPerson> getNonNaturalpersonsDataNew(HttpServletRequest request, @PathVariable Long id) {
	   
	   
	   
	   List<NonNaturalPerson> lstdata = new ArrayList<NonNaturalPerson>();
	   
  	 List<SocialTenureRelationship>  lst= new ArrayList<SocialTenureRelationship>();
  	 lst= landRecordsService.findAllSocialTenureByUsin(id);
  	 if(lst.size()>0)
  	 {
  		 for(SocialTenureRelationship obj:lst){
  			 
  			 if(obj.getLaPartygroupPersontype().getPersontypeid()== 2)
  			 {
  				lstdata.add((NonNaturalPerson) laPartyDao.getPartyIdByID(obj.getPartyid()));
  			 }
  			  			 
  		 }
  	 }
      return lstdata;
  
   }
   
   
   
	@RequestMapping(value = "/viewer/landrecords/updateNaturalPersonDataForEdit", method = RequestMethod.POST)
	@ResponseBody
   public NaturalPerson updateNaturalPersonDataForEdit(HttpServletRequest request, HttpServletResponse response,Principal principal)
	{
		try 
		{
			Long personid = 0L;
			String firstname = "";
			String contactno ="";
			String middlename =""; 
			String lastname ="";
			String address ="";			
			String identityno ="";
			String dateofbirth = "";
			Integer Gender = 0;
			Integer identitytypeid =0; 
			Integer maritalstatusid = 0;
			Integer occupationid = 0;
			Integer educationlevelid = 0;
			Integer transactionid = 0;
			Long landid = 0L;
			Integer persontypeid= 0;
			Integer ethnicity=0;
			Integer citizenship=0;
			String resident="";
			 String username = principal.getName();
		        User user = userService.findByUniqueName(username);
		        Long userid = user.getId();
		        String projectName = userDataService.getDefaultProjectByUserId(userid.intValue());
		        Project project= projectDAO.findByName(projectName);
                
		        ProjectArea projectArea = projectService.getProjectArea(project.getName()).get(0);
		
			try{landid = ServletRequestUtils.getRequiredLongParameter(request, "landid");}catch(Exception e){}
			try{transactionid=ServletRequestUtils.getRequiredIntParameter(request, "transactionid");}catch(Exception e){}
			try{persontypeid=ServletRequestUtils.getRequiredIntParameter(request, "persontypeid");}catch(Exception e){}
			try{contactno =ServletRequestUtils.getRequiredStringParameter(request, "contactno");}catch(Exception e){}

			try{personid = ServletRequestUtils.getRequiredLongParameter(request, "personid");}catch(Exception e){}
			try{firstname =  ServletRequestUtils.getRequiredStringParameter(request, "firstname");}catch(Exception e){}
			try{middlename =ServletRequestUtils.getRequiredStringParameter(request, "middlename");}catch(Exception e){}
			try{lastname =ServletRequestUtils.getRequiredStringParameter(request, "lastname");}catch(Exception e){}
			try{address =ServletRequestUtils.getRequiredStringParameter(request, "address");}catch(Exception e){}
			try{identityno= ServletRequestUtils.getRequiredStringParameter(request, "identityno");}catch(Exception e){}
			try{dateofbirth=ServletRequestUtils.getRequiredStringParameter(request, "dateofbirth");}catch(Exception e){}
			try{educationlevelid = ServletRequestUtils.getRequiredIntParameter(request, "educationlevel");}catch(Exception e){}
			
			try{Gender=ServletRequestUtils.getRequiredIntParameter(request, "genderid");}catch(Exception e){}
			try{identitytypeid=ServletRequestUtils.getRequiredIntParameter(request, "identitytypeid");}catch(Exception e){}
			try{maritalstatusid=ServletRequestUtils.getRequiredIntParameter(request, "maritalstatusid");}catch(Exception e){}
			try{occupationid=ServletRequestUtils.getRequiredIntParameter(request, "occupation");}catch(Exception e){}

			try{ethnicity=ServletRequestUtils.getRequiredIntParameter(request, "ethnicity");}catch(Exception e){}

			try{citizenship=ServletRequestUtils.getRequiredIntParameter(request, "citizenship");}catch(Exception e){}

			try{resident=ServletRequestUtils.getRequiredStringParameter(request, "resident");}catch(Exception e){}

			
			EducationLevel educationlevel =educationLevelDao.getEducationLevelBypk(educationlevelid);
			NaturalPerson editednaturalperosn = (NaturalPerson) laPartyDao.getPartyIdByID(personid);
			
			// add new person for all claim types
			
			if(editednaturalperosn== null){     
				LaExtTransactiondetail LaExtTransactionObj =null;
				
				
				//If Unclaimed
				if(transactionid==null || transactionid==0){
					SocialTenureRelationship socialTenureobj = socialTenureRelationshipDao.getSocialTenureRelationshipByLandId(landid);
					if(null == socialTenureobj){
						LaExtTransactiondetail laExtTransactiondetail = new LaExtTransactiondetail();
				laExtTransactiondetail.setCreatedby(userid.intValue());
				laExtTransactiondetail.setCreateddate(new Date());
				laExtTransactiondetail.setIsactive(true);
				
				Status status = registrationRecordsService.getStatusById(1);
				
				laExtTransactiondetail.setLaExtApplicationstatus(status);
				laExtTransactiondetail.setModuletransid(1);
				laExtTransactiondetail.setRemarks("");
				laExtTransactiondetail.setProcessid(1l);
				LaExtTransactionObj =laExtTransactiondetailDao.addLaExtTransactiondetail(laExtTransactiondetail);
					}
					else{
						LaExtTransactionObj = socialTenureobj.getLaExtTransactiondetail();
					}
				}
				
				
				
				NaturalPerson naturalperson= new NaturalPerson();
				
				IdType  idtype= new IdType();
				idtype.setIdentitytypeid(identitytypeid);
				
				try{naturalperson.setFirstname(firstname);}catch(Exception e){}
				try{naturalperson.setContactno(contactno);}catch(Exception e){}
				try{naturalperson.setMiddlename(middlename);}catch(Exception e){}
				try{naturalperson.setLastname(lastname);}catch(Exception e){}
				try{naturalperson.setAddress(address);}catch(Exception e){}
				try{naturalperson.setLaPartygroupIdentitytype(idtype);}catch(Exception e){}
				try{naturalperson.setIdentityno(identityno);}catch(Exception e){}
				try{naturalperson.setGenderid(Gender);}catch(Exception e){}
				try{naturalperson.setLaPartygroupEducationlevel(educationlevel);}catch(Exception e){}
				try{naturalperson.setCreatedby(userid.intValue());}catch(Exception e){}
				try{naturalperson.setCreateddate(new Date());}catch(Exception e){}
				try{naturalperson.setCitizenship(citizenship);}catch(Exception e){}
				try{naturalperson.setEthnicity(ethnicity);}catch(Exception e){}
				if(occupationid != 0){
				LaPartygroupOccupation occupationobject=  new LaPartygroupOccupation(); 
				occupationobject.setOccupationid(occupationid);
				
				try{naturalperson.setLaPartygroupOccupation(occupationobject);}catch(Exception e){}
				}
				try{naturalperson.setResident(resident);}catch(Exception e){}
				if(personid==0L){
					naturalperson.setOwnertype(2);
				}
				naturalperson.setLaSpatialunitgroup1(projectArea.getLaSpatialunitgroup1());
				naturalperson.setLaSpatialunitgroup3(projectArea.getLaSpatialunitgroup3());
                 naturalperson.setLaSpatialunitgroup2(projectArea.getLaSpatialunitgroup2());
                 naturalperson.setLaSpatialunitgroup4(projectArea.getLaSpatialunitgroup4());
                 naturalperson.setLaSpatialunitgroup5(projectArea.getLaSpatialunitgroup5());
                 naturalperson.setLaSpatialunitgroupHierarchy1(projectArea.getLaSpatialunitgroupHierarchy1());
                 naturalperson.setLaSpatialunitgroupHierarchy2(projectArea.getLaSpatialunitgroupHierarchy2());
                 naturalperson.setLaSpatialunitgroupHierarchy3(projectArea.getLaSpatialunitgroupHierarchy3());
                 naturalperson.setLaSpatialunitgroupHierarchy4(projectArea.getLaSpatialunitgroupHierarchy4());
                 naturalperson.setLaSpatialunitgroupHierarchy5(projectArea.getLaSpatialunitgroupHierarchy5());
                 naturalperson.setIsactive(true);
				PersonType laPartygroupPersontype = new PersonType();
				laPartygroupPersontype.setPersontypeid(1);
				try{naturalperson.setLaPartygroupPersontype(laPartygroupPersontype);}catch(Exception e){}
		        try{ 
		        		String[] datearr = dateofbirth.split("-");
		        		
		        		Long month = Long.parseLong(datearr[1])+1L;
		        		String finaldob = datearr[0] +"-"+ month.toString() +"-"+datearr[2];
						Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(finaldob);
						naturalperson.setDateofbirth(date1);
					}
		        catch(Exception e){
		        	e.printStackTrace();
		        }
		        MaritalStatus maritalstatus = new MaritalStatus();
		        maritalstatus.setMaritalstatusid(maritalstatusid);
		        try{naturalperson.setLaPartygroupMaritalstatus(maritalstatus);}catch(Exception e){}
		        
		        
				NaturalPerson obj = naturalPersonDao.addNaturalPerson(naturalperson);
				
				   SocialTenureRelationship right = new SocialTenureRelationship();
                   right.setCreatedby(userid.intValue());
                   right.setLandid(landid);
                   right.setPartyid(obj.getPartyid());
                   right.setLaPartygroupPersontype(obj.getLaPartygroupPersontype());
                   if(LaExtTransactionObj==null){
                   LaExtTransactiondetail transobj= laExtTransactiondetailDao.getLaExtTransactiondetail(transactionid);
                   right.setLaExtTransactiondetail(transobj);
                   }
                   else{
                   right.setLaExtTransactiondetail(LaExtTransactionObj);
                   }
                   right.setIsactive(true);
                   
                   
                   right.setCreateddate(new Date());
                   
                   SocialTenureRelationship  socialTenurerelationship  = socialTenureRelationshipDao.saveSocialTenureRelationship(right);
				
				return obj;
			}
			
			
		
				
				
				
			try{editednaturalperosn.setFirstname(firstname);}catch(Exception e){}
			try{editednaturalperosn.setContactno(contactno);}catch(Exception e){}
			try{editednaturalperosn.setMiddlename(middlename);}catch(Exception e){}
			try{editednaturalperosn.setLastname(lastname);}catch(Exception e){}
			try{editednaturalperosn.setAddress(address);}catch(Exception e){}
			try{editednaturalperosn.getLaPartygroupIdentitytype().setIdentitytypeid(identitytypeid);}catch(Exception e){}
			try{editednaturalperosn.setIdentityno(identityno);}catch(Exception e){}
			try{editednaturalperosn.setGenderid(Gender);}catch(Exception e){}
			try{editednaturalperosn.setLaPartygroupEducationlevel(educationlevel);}catch(Exception e){}
			try{editednaturalperosn.setCitizenship(citizenship);}catch(Exception e){}
			try{editednaturalperosn.setEthnicity(ethnicity);}catch(Exception e){}
			if(occupationid != 0){
			LaPartygroupOccupation occupationobject=  new LaPartygroupOccupation(); 
			occupationobject.setOccupationid(occupationid);
			
			try{editednaturalperosn.setLaPartygroupOccupation(occupationobject);}catch(Exception e){}
			}
			try{editednaturalperosn.setResident(resident);}catch(Exception e){}
	        try{ 
	        		String[] datearr = dateofbirth.split("-");
	        		
	        		Long month = Long.parseLong(datearr[1])+1L;
	        		String finaldob = datearr[0] +"-"+ month.toString() +"-"+datearr[2];
					Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(finaldob);
	        	 	editednaturalperosn.setDateofbirth(date1);
				}
	        catch(Exception e){
	        	e.printStackTrace();
	        }
	        
	        MaritalStatus maritalstatus = new MaritalStatus();
	        maritalstatus.setMaritalstatusid(maritalstatusid);
	        try{editednaturalperosn.setLaPartygroupMaritalstatus(maritalstatus);}catch(Exception e){}
	        
	        PersonType laPartygroupPersontype = new PersonType();
	        if(persontypeid !=0){
			laPartygroupPersontype.setPersontypeid(persontypeid);
			try{editednaturalperosn.setLaPartygroupPersontype(laPartygroupPersontype);}catch(Exception e){}
	        }
	        
	        
			return landRecordsService.updateNaturalPersonDataForEdit(editednaturalperosn);
		} 
		catch (Exception e) {
			logger.error(e);
			return null;
		}
	}
   
	
	@RequestMapping(value = "/viewer/landrecords/updateResourceaoi", method = RequestMethod.POST)
	   @ResponseBody
	   public boolean updateResouceAoiName(HttpServletRequest request, HttpServletResponse response,Principal principal) {

		
		String userId[] = null ;
		String project="";
		String username = principal.getName();
		User userObj = userService.findByUniqueName(username);

		Long created_by = userObj.getId();
		Long modifiedby = created_by;// As discussed with rajendra sir modified by also same as created by
		int applicationstatusid =1;
		Long projectId=0l;
		try {
			userId = request.getParameterValues("userId");
		} catch (Exception e) {
			logger.error(e);
		}

		String allocId[] = null;
			
		try {
			allocId= ServletRequestUtils.getRequiredStringParameters(request, "hid_allocid");
		} catch (Exception e) {
			logger.error(e);
		}
		
		try{
			project=ServletRequestUtils.getRequiredStringParameter(request, "hid_projectID");
			 Project objproject= projectDAO.findByName(project);
			 projectId=objproject.getProjectnameid().longValue();
		}catch(Exception e){
			e.printStackTrace();
		}

		  
		
		return  allocateUserDao.updateUserAllocation(userId, allocId, projectId, created_by.intValue(), modifiedby.intValue(), applicationstatusid);
	   }
	
	@RequestMapping(value = "/viewer/landrecords/resourceinfo/{aoiid}", method = RequestMethod.GET)
	   @ResponseBody
	   public List<La_spatialunit_aoi> getResouceInfo(HttpServletRequest request, HttpServletResponse response,Principal principal, @PathVariable Long aoiid) {
		   
			String allocId[] = new String[1];
			allocId[0]=aoiid+"";
			try {
				return  allocateUserDao.getResourceAllInfo(allocId);
			} catch (Exception e) {
				logger.error(e);
				return  null;
			}

			
	   }
	   
	   @RequestMapping(value = "/viewer/upload/media/", method = RequestMethod.POST)
	   @ResponseBody
	   public String upload(MultipartHttpServletRequest request, HttpServletResponse response, Principal principal) {	
			try {
				
				String userId[] = null ;
				String project="";
				String username = principal.getName();
				User userObj = userService.findByUniqueName(username);

				Long created_by = userObj.getId();
				Long modifiedby = created_by;// As discussed with rajendra sir modified by also same as created by

				Iterator<String> file = request.getFileNames();
				
				
				Integer partyid =null;
				partyid= ServletRequestUtils.getRequiredIntParameter(request, "partyid");
				
				Integer transid =null;
				transid= ServletRequestUtils.getRequiredIntParameter(request, "transid");
				
				Long landid = 0L;
				landid= ServletRequestUtils.getRequiredLongParameter(request, "landid");
				
				
				byte[] document = null;
				while(file.hasNext()) 
				{
					String fileName = file.next();
					String projName="";
					MultipartFile mpFile = request.getFile(fileName);
					long size = mpFile.getSize();
					String originalFileName = mpFile.getOriginalFilename();
					SourceDocument objDocument = new SourceDocument();
					
					

					if (originalFileName != "") {
						document = mpFile.getBytes();
					}
			    	SourceDocument doc =  sourceDocumentsDao.findBypartyandtransid(partyid.longValue(), transid.longValue());
			    	if(doc==null){
			    		//String filepath = "/storage/emulated/0/MAST/multimedia/" +originalFileName;
			    		String filepath = FileUtils.getFielsFolder(request) +"/storage/emulated/0/MAST/multimedia/Parcel_Media";
				        filepath =  filepath.replace("\\mast\\..", "");
				        Path path = Paths.get(filepath);
				        File existingdr =new File(filepath);
				        boolean exist = existingdr.exists();
						if (!exist) {
							
							boolean success = (new File(filepath)).mkdirs();
							
						}
						
				        try {
				        	File serverFile = new File(existingdr + File.separator
			                        + originalFileName);
				            
				            FileOutputStream uploadfile = new FileOutputStream(serverFile, false);
							uploadfile.write(document);
							uploadfile.flush();
							uploadfile.close();

				        } catch (Exception e) {
				            logger.error(e);
				        }
				        Outputformat outputformat = Outputformatdao.findByName("image"+"/" +FileUtils.getFileExtension(originalFileName));
				        objDocument.setLaExtDocumentformat(outputformat);
				        objDocument.setCreatedby(created_by.intValue());
				        objDocument.setCreateddate(new Date());
				        objDocument.setModifiedby(created_by.intValue());
				        objDocument.setModifieddate(new Date());
				        objDocument.setRecordationdate(new Date());
				        objDocument.setRemarks("");
				        objDocument.setDocumentlocation("/storage/emulated/0/MAST/multimedia/Parcel_Media");
				        objDocument.setIsactive(true);
				        objDocument.setLaSpatialunitLand(landid);
				        objDocument.setLaParty(laPartyDao.getPartyListIdByID(partyid.longValue()).get(0));       
				        objDocument.setLaExtTransactiondetail( laExtTransactiondetailDao.getLaExtTransactiondetail(transid));
				        if(transid==0){
				        	SocialTenureRelationship socialtenureobj =socialTenureRelationshipDAO.getSocialTenureObj(partyid.longValue(), landid);
					        objDocument.setLaExtTransactiondetail(socialtenureobj.getLaExtTransactiondetail());

				        }
				        objDocument.setDocumentname(originalFileName);
				        sourceDocumentsDao.saveUploadedDocuments(objDocument);
						

			    	}
			        String filepath = FileUtils.getFielsFolder(request) + doc.getDocumentlocation();
			        filepath =  filepath.replace("\\mast\\..", "");
			        Path path = Paths.get(filepath);
			        File existingdr =new File(filepath);

			        
			        boolean exist = existingdr.exists();
					if (!exist) {
						
						boolean success = (new File(filepath)).mkdirs();
						
					}
					
					
			        try {
			        	File serverFile = new File(existingdr + File.separator
		                        + originalFileName);
			            
			            FileOutputStream uploadfile = new FileOutputStream(serverFile, false);
						uploadfile.write(document);
						uploadfile.flush();
						uploadfile.close();

			        } catch (Exception e) {
			            logger.error(e);
			        }
			    
			        objDocument= doc;
			        objDocument.setDocumentname(originalFileName);
			        sourceDocumentsDao.saveUploadedDocuments(objDocument);
					
					
				 System.out.println("true");

			} 
				
			}catch (Exception e) {
				logger.error(e);
			}		
			return "Success";
		}

	   @RequestMapping(value = "/viewer/delete/media/", method = RequestMethod.POST)
	   @ResponseBody
	   public String delete(HttpServletRequest request, HttpServletResponse response, Principal principal) {	
			try {
				
				String userId[] = null ;
				String project="";
				String username = principal.getName();
				User userObj = userService.findByUniqueName(username);

				Long created_by = userObj.getId();
				Long modifiedby = created_by;// As discussed with rajendra sir modified by also same as created by

				SourceDocument doc = null;
				
				
				Integer partyid =null;
				partyid= ServletRequestUtils.getRequiredIntParameter(request, "partyid");
				
				Integer transid =null;
				transid= ServletRequestUtils.getRequiredIntParameter(request, "transid");
				
				Long landid = 0L;
				landid= ServletRequestUtils.getRequiredLongParameter(request, "landid");
				if(transid==0){
					SocialTenureRelationship socialtenureobj =socialTenureRelationshipDAO.getSocialTenureObj(partyid.longValue(), landid);
					doc = sourceDocumentsDao.findBypartyandtransid(partyid.longValue(), socialtenureobj.getLaExtTransactiondetail().getTransactionid().longValue());
				}
				else{
				 doc = sourceDocumentsDao.findBypartyandtransid(partyid.longValue(), transid.longValue());
				}
				doc.setIsactive(false);
				
				sourceDocumentsDao.saveUploadedDocuments(doc);
				
			}
			catch(Exception e){
				e.printStackTrace();
			}
			return "success";
				
	   }
				
		
	   @RequestMapping(value = "/viewer/landrecords/updateParcelNumberSplit/{parcelid}", method = RequestMethod.GET)
	   @ResponseBody
	   public String  updateSplitParcel(HttpServletRequest request, HttpServletResponse response,Principal principal, @PathVariable Long parcelid) {
		   String username = principal.getName();
			User userObj = userService.findByUniqueName(username);
			Long user_id = userObj.getId();
		   List<SocialTenureRelationship> lstSocialTenureRelationshipNatural = new ArrayList<SocialTenureRelationship>();	
		   List<SocialTenureRelationship> lstSocialTenureRelationshipNonNatural = new ArrayList<SocialTenureRelationship>();	
		   
		   ClaimBasic objClaimBasic=new  ClaimBasic();
		   long parentLandId=0l;
		   try {
			   LandApplicationStatus landapplicationbject = landApplicationStatusDAO.getLandApplicationStatusByLandId(parcelid);
			 objClaimBasic =spatialUnitService.getClaimsBasicByLandId(parcelid).get(0);
			   parentLandId=objClaimBasic.getOldlandid();
			   Status status = registrationRecordsService.getStatusById(2);
			   if(parentLandId>0){
				   lstSocialTenureRelationshipNatural=socialTenureRelationshipDao.getSocialTenureRelationshipBylandID(parentLandId);

				   if(null!=lstSocialTenureRelationshipNatural && lstSocialTenureRelationshipNatural.size()>0){

					   LaExtTransactiondetail laExtTransactiondetailNatural = new LaExtTransactiondetail(); 
					   laExtTransactiondetailNatural.setCreatedby(1);
					   laExtTransactiondetailNatural.setCreateddate(new Date());
					   laExtTransactiondetailNatural.setIsactive(true);
					   laExtTransactiondetailNatural.setLaExtApplicationstatus(status);// approved?
					   laExtTransactiondetailNatural.setModuletransid(1);
					   laExtTransactiondetailNatural.setRemarks("");
					   laExtTransactiondetailNatural =laExtTransactiondetailDao.addLaExtTransactiondetail(laExtTransactiondetailNatural);

					   for(SocialTenureRelationship obj:lstSocialTenureRelationshipNatural){
						   SocialTenureRelationship socialTenureRelationship = new SocialTenureRelationship();
						   socialTenureRelationship.setCreatedby(user_id.intValue());
						   socialTenureRelationship.setPartyid(obj.getPartyid());
						   socialTenureRelationship.setLaPartygroupPersontype(obj.getLaPartygroupPersontype());
						   socialTenureRelationship.setCreateddate(new Date());
						   socialTenureRelationship.setIsactive(true);
						   socialTenureRelationship.setLandid(parcelid);
						   socialTenureRelationship.setLaExtTransactiondetail(laExtTransactiondetailNatural);
						   try {
							   socialTenureRelationship =registrationRecordsService.saveSocialTenureRelationship(socialTenureRelationship);
						   } catch (Exception er) {
							   er.printStackTrace();
							   return "Error";

						   }

					   }
				   }
				   lstSocialTenureRelationshipNonNatural= socialTenureRelationshipDao.getSocialTenureRelationshipBylandIDAndPartyID(parentLandId);

				   if(null!=lstSocialTenureRelationshipNonNatural && lstSocialTenureRelationshipNonNatural.size()>0){


					   LaExtTransactiondetail laExtTransactiondetailNon = new LaExtTransactiondetail(); 
					   laExtTransactiondetailNon.setCreatedby(1);
					   laExtTransactiondetailNon.setCreateddate(new Date());
					   laExtTransactiondetailNon.setIsactive(true);
					   laExtTransactiondetailNon.setLaExtApplicationstatus(status);// approved?
					   laExtTransactiondetailNon.setModuletransid(1);
					   laExtTransactiondetailNon.setRemarks("");
					   laExtTransactiondetailNon =laExtTransactiondetailDao.addLaExtTransactiondetail(laExtTransactiondetailNon);

					   for(SocialTenureRelationship obj:lstSocialTenureRelationshipNonNatural){
						   SocialTenureRelationship socialTenureRelationship = new SocialTenureRelationship();
						   socialTenureRelationship.setCreatedby(user_id.intValue());
						   socialTenureRelationship.setPartyid(obj.getPartyid());
						   socialTenureRelationship.setLaPartygroupPersontype(obj.getLaPartygroupPersontype());
						   socialTenureRelationship.setCreateddate(new Date());
						   socialTenureRelationship.setIsactive(true);
						   socialTenureRelationship.setLandid(parcelid);
						   socialTenureRelationship.setLaExtTransactiondetail(laExtTransactiondetailNon);
						   try {
							   socialTenureRelationship =registrationRecordsService.saveSocialTenureRelationship(socialTenureRelationship);
						   } catch (Exception er) {
							   er.printStackTrace();
							   return "Error";

						   }

					   }

				   }
				   
				   // enter  parcel id   LaExtParcelSplitLand for new  parcel 
				    LaExtParcelSplitLand objLaExtParcelSplitLand = new LaExtParcelSplitLand();
			    	objLaExtParcelSplitLand.setLandid(parcelid);
			    	objLaExtParcelSplitLand.setIsactive(true);
			    	objLaExtParcelSplitLand.setCreateddate(new Date());
			    	objLaExtParcelSplitLand.setCreatedby(user_id.intValue());
			    	try{
			    		laExtParcelSplitLandService.addLaExtParcelSplitLandService(objLaExtParcelSplitLand);
			    	}catch(Exception e){
			    		e.printStackTrace();
			    		 return "Error";
			    	}
				   
			    	
			    	try{
			    		LandApplicationStatus landApplicationStatus = landApplicationStatusDAO.getLandApplicationStatusByLandId(parcelid);
	            		if(null!=landApplicationStatus){
	            			landApplicationStatus.setApplicationstatusid(5);
	            			landApplicationStatus.setWorkflowstatusid(6);
	            			landApplicationStatusDAO.makePersistent(landApplicationStatus);
	            			}
			    	
			    		 claimBasicService.saveClaimBasicDAO(objClaimBasic);
			    		 
			    		 landapplicationbject.setApplicationstatusid(5);
			    		 landapplicationbject.setWorkflowstatusid(6);
			    		 landApplicationStatusDAO.makePersistent(landapplicationbject);
			    	}catch(Exception e){
						   e.printStackTrace();
						   return "Error";
					   }
			    	
			    	
			    	
			    	// entry for landShareType in 
			    	LaExtRegistrationLandShareType objLaExtRegistrationLandShareType = new LaExtRegistrationLandShareType();
					objLaExtRegistrationLandShareType.setLandid(objClaimBasic.getLandid());
					objLaExtRegistrationLandShareType.setLandsharetypeid((long)objClaimBasic.getLaRightLandsharetype().getLandsharetypeid());
					objLaExtRegistrationLandShareType.setIsactive(true);
					objLaExtRegistrationLandShareType.setCreateddate(new Date());
					objLaExtRegistrationLandShareType.setCreatedby(user_id.intValue());
					try{
					laExtRegistrationLandShareTypeService.addLaExtRegistrationLandShareType(objLaExtRegistrationLandShareType);
					}catch(Exception e){
						e.printStackTrace();
						return "Error";
					}
			    	
					
					try {
						List<SocialTenureRelationship> socialTenureRelationshipSize = registrationRecordsService.getSocialTenureRelationshipListForSellerByLandId(objClaimBasic.getLandid());
						

						if(null!=socialTenureRelationshipSize){
							if(socialTenureRelationshipSize.size()==1){
								laExtRegistrationLandShareTypeService.updateRegistrationSharetype(6L, objClaimBasic.getLandid());
								
							
							}else if(socialTenureRelationshipSize.size()==2){
								laExtRegistrationLandShareTypeService.updateRegistrationSharetype(7L, objClaimBasic.getLandid());
								
							}else if(socialTenureRelationshipSize.size()>2){
								laExtRegistrationLandShareTypeService.updateRegistrationSharetype(8L, objClaimBasic.getLandid());
								
							}
							
						}
					} catch (Exception e) {
						e.printStackTrace();
						return "Error";
					}
					
					
				   // delete parcel id from table  LaExtParcelSplitLand for old parcel 
				   try{
					   laExtParcelSplitLandService.deleteLaExtParcelSplitBylandId(parentLandId);
				   }catch(Exception e){
					   e.printStackTrace();
					   return "Error";
				   }
				   // delete parcel id from table  LaExtParcelSplitLand for new  parcel 
				   try{
					   laExtParcelSplitLandService.deleteLaExtParcelSplitBylandId(parcelid);
				   }catch(Exception e){
					   e.printStackTrace();
					   return "Error";
				   }


			   }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return "Error";
		}
		   
		   int length = 9;
			String str = "";
			for(int i=0;i<length-parcelid.toString().length(); i++){
				str = str+"0";
			}
			 
			
		   return "New Parcel created with ParcelId: " +str+""+parcelid; 
		   
		   
		
			
	   }
	   
	   @RequestMapping(value = "/viewer/landrecords/checkShareType/{id}", method = RequestMethod.GET)
	    @ResponseBody
	    public String landsharetype(@PathVariable Long id) {
		  
		   String data ="true";
		  List<ClaimBasic> claimBasicobj =  spatialUnitService.getClaimsBasicByLandId(id);
		 List<SocialTenureRelationship> socialtenureobj = socialTenureRelationshipDao.getSocialTenureRelationshipBylandID(claimBasicobj.get(0).getLandid());
		  if(socialtenureobj.size() <= 1 && claimBasicobj.get(0).getLaRightLandsharetype().getLandsharetypeid()==6){
			  data = "You cant't add more than 1-person for Single Tenancy";
			  return "You cant't add more than 1-person for single Tenancy";
		  }
		  else  if(socialtenureobj.size() >= 2 && claimBasicobj.get(0).getLaRightLandsharetype().getLandsharetypeid()==7){
			  data = "You cant't add more than 2-person for Joint Tenancy";
			  return "You cant't add more than 2-person for Joint Tenancy";
		  }
		  else  if(socialtenureobj.size() >= 0 && claimBasicobj.get(0).getLaRightLandsharetype().getLandsharetypeid()==8){
			  return "true";
		  }
		  else  if(socialtenureobj.size() >= 0 && socialtenureobj.size() < 1 && claimBasicobj.get(0).getLaRightLandsharetype().getLandsharetypeid()==9){
			  return "true";
		  }
		  else{ 
	        return  data;
		  }
	    }
	   
	   
	   @RequestMapping(value = "/viewer/landrecords/aquisitiontype/", method = RequestMethod.GET)
	    @ResponseBody
	    public List<AcquisitionType> getAquisitionList(Principal principal) {
	        return  spatialUnitService.getAcquisitionTypes();

	    }
	  
	   
	    @RequestMapping(value = "/viewer/landrecords/landcorrectionreport/{transid}/{usin}", method = RequestMethod.GET)
	    @ResponseBody
	    public List<Object> getDataCorrectionRep(@PathVariable Long transid,@PathVariable Long usin,  HttpServletRequest request, HttpServletResponse response, Principal principal ) {


	    	List<Object> object=new ArrayList<Object>(); 
	    	Object objlanddetails = null;
	    	Object objpersondetails = null; 
	    	Object objpoidetails = null;
	    	Object objnonnaturalpersondetails = null;
	    	Object objdocs = null;
	    	Object signdocs = null;

	    	try 
	    	{
	    		objlanddetails= landRecordsService.getDataCorrectionReport(transid, usin);
	    		object.add(objlanddetails);

	    	}catch(Exception e){
	    		e.printStackTrace(); 
	    	}
	    	try 
	    	{
	    		objpoidetails= landRecordsService.getDataCorrectionReportPOI(transid, usin);
	    		object.add(objpoidetails);

	    	}catch(Exception e){
	    		e.printStackTrace(); 
	    	}
	    	
	    	try 
	    	{
	    		objpersondetails= landRecordsService.getDataCorrectionPersonsReport(transid, usin);
	    		object.add(objpersondetails);

	    	}catch(Exception e){
	    		e.printStackTrace(); 
	    	}
	    	
	    	try 
	    	{
	    		List<SourceDocument> doc =   sourcedocdao.findSourceDocumentByLandIdandTransactionid(usin, transid.intValue());
	    		
		    	objdocs = (Object) doc;
		    	object.add(objdocs);

	    	}catch(Exception e){
	    		e.printStackTrace(); 
	    	}
	    	
	    	try 
	    	{
	    		 String username = principal.getName();
			        User user = userService.findByUniqueName(username);
			        Long userid = user.getId();
			        String projectName = userDataService.getDefaultProjectByUserId(userid.intValue());
			        Project project= projectDAO.findByName(projectName);
	                
			        ProjectArea projectArea = projectService.getProjectArea(project.getName()).get(0);
	    		
			        signdocs = (Object) projectArea;
		    	object.add(signdocs);

	    	}catch(Exception e){
	    		e.printStackTrace(); 
	    	}

	 	   try {
			List<NonNaturalPerson> lstdata = new ArrayList<>();
			   
			 List<SocialTenureRelationship>  lst= new ArrayList<SocialTenureRelationship>();
				 lst= landRecordsService.findAllSocialTenureByUsin(usin);
			  	 if(lst.size()>0)
			  	 {
			  		 for(SocialTenureRelationship obj:lst){
			  			 
			  			 if(obj.getLaPartygroupPersontype().getPersontypeid()== 2)
			  			 {
			  				lstdata.add((NonNaturalPerson) laPartyDao.getPartyIdByID(obj.getPartyid()));
			  			 }
			  			  			 
			  		 }
			  	 }
			  	objnonnaturalpersondetails =(Object) lstdata;
			  	object.add(objnonnaturalpersondetails);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    	
	    	return object;
	    }
	    
	    @RequestMapping(value = "/viewer/landrecords/landPOI/{transid}/{usin}", method = RequestMethod.GET)
	    @ResponseBody
	    public List<SpatialUnitPersonWithInterest> getPOI(@PathVariable Long transid,@PathVariable Long usin,  HttpServletRequest request, HttpServletResponse response, Principal principal ) {
	    	List<SpatialUnitPersonWithInterest> obj = null;
	    	
	    	 obj = spatialunitpersonwithinterestdao.findByUsinandTransid(usin, transid.longValue());
	    	 if(null!=obj){
	    	 return obj;
	    	}
	    	
	    	else {
	    		return null;
	    	
	    	}
	    
	    	
	    
	    }
	    @RequestMapping(value = "/viewer/landrecords/farmreport/{usin}/{projectId}", method = RequestMethod.GET)
	    @ResponseBody
	    public List<Object> getFarmReport(@PathVariable Long usin,@PathVariable Integer projectId,  HttpServletRequest request, HttpServletResponse response, Principal principal ) {
	    	
	    	
	    	
	    	try {
				List<Object> lstobject=new ArrayList<Object>(); 
				Object objframdetails = null;
				Object objpiodetails = null;
				Object objpersondetails = null;
				List<Map> list = new ArrayList<>();
				List<Map> personList = new ArrayList<>();
				Map<String, String> map= null;
				Map<String, String> personmap= null;
//	    	Map<String, String> map =  new HashMap<String, String>();
				Integer groupId= 0;
				Integer persongroupId= 0;
				int count=0;
				int personcount=0;
				
				objframdetails= landRecordsService.getFarmReportByLandId(usin);
				lstobject.add(objframdetails);
				List<ResourceAttributeValues> lst = new ArrayList<ResourceAttributeValues>();
				lst=resourceAttributeValuesService.getResourceAttributeValuesBylandId(projectId, usin.intValue());
				for(ResourceAttributeValues personobj: lst){
					
					
					if(persongroupId==personobj.getGroupid()){
				 if(personobj.getAttributevalueid() == 1017 ||
						    personobj.getAttributevalueid() == 1035 ||
							personobj.getAttributevalueid() == 1063 ||
							personobj.getAttributevalueid() == 1079 ||
							personobj.getAttributevalueid() == 1088 ||
							personobj.getAttributevalueid() == 1097 ||
							personobj.getAttributevalueid() == 1108 ||
							personobj.getAttributevalueid() == 1115){
					 if(null ==personmap || personcount>0){
					 personmap =  new HashMap<String, String>();
					 }
					 personmap.put("firstName", personobj.getAttributevalue());
						 }
						 else if(personobj.getAttributevalueid() == 1018 ||
								    personobj.getAttributevalueid() == 1036 ||
									personobj.getAttributevalueid() == 1065 ||
									personobj.getAttributevalueid() == 1080 ||
									personobj.getAttributevalueid() == 1089 ||
									personobj.getAttributevalueid() == 1109 ||
									personobj.getAttributevalueid() == 1098 ||
									personobj.getAttributevalueid() == 1117){
							 personmap.put("middleName", personobj.getAttributevalue());
						 }
						 else if(personobj.getAttributevalueid() == 1019 ||
								    personobj.getAttributevalueid() == 1037 ||
									personobj.getAttributevalueid() == 1066 ||
									personobj.getAttributevalueid() == 1081 ||
									personobj.getAttributevalueid() == 1090 ||
									personobj.getAttributevalueid() == 1099 ||
									personobj.getAttributevalueid() == 1110 ||
									personobj.getAttributevalueid() == 1118){
							 personmap.put("lastName", personobj.getAttributevalue());
						 }
						 else if(personobj.getAttributevalueid() == 1042 ||
								    personobj.getAttributevalueid() == 1030 ||
									personobj.getAttributevalueid() == 1073 ||
									personobj.getAttributevalueid() == 1086 ||
									personobj.getAttributevalueid() == 1095 ||
									personobj.getAttributevalueid() == 1105 ||
									personobj.getAttributevalueid() == 1125){
							 personmap.put("mobileNo", personobj.getAttributevalue());
						 }
						 else if(personobj.getAttributevalueid() == 1021 ||
								    personobj.getAttributevalueid() == 1068 ||
									personobj.getAttributevalueid() == 1129 ||
									personobj.getAttributevalueid() == 1120){
							 personmap.put("dob", personobj.getAttributevalue());
						 }
						 else if(personobj.getAttributevalueid() == 1022 ||
								    personobj.getAttributevalueid() == 1064 ||
									personobj.getAttributevalueid() == 1116){
							 personmap.put("maritalStatus", personobj.getAttributevalue());
						 }
						 else if(personobj.getAttributevalueid() == 1020 ||
								    personobj.getAttributevalueid() == 1067 ||
									personobj.getAttributevalueid() == 1119){
							 personmap.put("gender", personobj.getAttributevalue());
						 }
						 else if(personobj.getAttributevalueid() == 1023 ||
								    personobj.getAttributevalueid() == 1069 ||
									personobj.getAttributevalueid() == 1121){
							 personmap.put("citizenship", personobj.getAttributevalue());
						 }
						 else if(personobj.getAttributevalueid() == 1024 ||
								    personobj.getAttributevalueid() == 1070 ||
									personobj.getAttributevalueid() == 1122){
							 personmap.put("ethnicity", personobj.getAttributevalue());
						 }
						 else if(personobj.getAttributevalueid() == 1025){
							 personmap.put("resident", personobj.getAttributevalue());
						 }
						 else if(personobj.getAttributevalueid() == 1071 ||
								   personobj.getAttributevalueid() == 1123){
							 personmap.put("resident", personobj.getAttributevalue());
						 }
						
						
						 else if(personobj.getAttributevalueid() == 1031||
								    personobj.getAttributevalueid() == 1077){
							 personmap =  new HashMap<String, String>();
							 personmap.put("institutionName", personobj.getAttributevalue());
				        	
				        	 
						 }
						 else if(! personobj.getFieldAliasName().equalsIgnoreCase("Community Area")){
							 if(personobj.getFieldname().equalsIgnoreCase("Address") || personobj.getFieldname().equalsIgnoreCase("Address/Street")){
								 personmap.put("address", personobj.getAttributevalue());
							 }
						}
						/* else if(personobj.getAttributevalueid() == 1032){
							 $("#reg_no").val(personobj.attributevalue);
							 Registration_No = personobj.attributevalue;
							 $("#regNo").show();
//						 $("#tenure_occupancy").show();
						 }
						 else if(personobj.getAttributevalueid() == 1033){
							 
				        	
							 Registration_Date = personobj.attributevalue;
							
						    	
						    	{
						    		if(Registration_Date!=null && Registration_Date!="")
						    		{
						    			  var registartion_date_date = new Date(Registration_Date);
						    			
//					    			  $("#reg_date").val(registartion_date_date);
//					    			  $("#reg_date_td").show();
//					    				 $("#tenure_occupancy").show();
						    			
//					    			dob = date.getFullYear()+ '-' + date.getMonth() + '-' + date.getDate();
						    		}
						    	}
						 
							
						 }
						 else if(personobj.getAttributevalueid() == 1034 ||
								    personobj.attributevalueid == 1078){
							 $("#members").val(personobj.attributevalue);
							 No_Of_members = personobj.attributevalue;
							 $("#members_tr").show();
//						 $("#tenure_occupancy").show();
							 $("#members_id").val(personobj.attributevalueid);
						 }
						 else if(personobj.getAttributevalueid() == 1053 ||
								    personobj.getAttributevalueid() == 1055){
							 Other_details = personobj.attributevalue;
						 }
						 else if(personobj.getAttributevalueid() == 1106){
							 Agency = personobj.attributevalue;
						 }
						 else if(personobj.getAttributevalueid() == 1060){
							 land_handled = personobj.attributevalue;
						 }
						 else if(personobj.getAttributevalueid() == 1096){
							 Community = personobj.attributevalue;
						 }
						 else if(personobj.getAttributevalueid() == 1107){
							 Authority = personobj.attributevalue;
						 }
						 else if(personobj.getAttributevalueid() == 1087){
							 collective_members_no = personobj.attributevalue;
						 }
						 else if(personobj.getAttributevalueid() == 1112){
							 Community_Area = personobj.attributevalue;
						 }
						 else if(personobj.fieldAliasName != "Community Area"){
							 if(personobj.fieldname == "Address" || personobj.fieldname == "Address/Street"){
								 Address = personobj.attributevalue;
							 }
						}*/
				
				}
					
					else if(persongroupId != personobj.getGroupid()){
						
						if(null != personmap){
						personList.add(personcount, personmap);
						personcount++;
						}
						
						persongroupId=personobj.getGroupid();
						
					}
					
					
				}
				
				personList.add(personcount, personmap);
				    

				    
				 objpersondetails =(Object) personList;
				 
				
				
				
				
				 List<ResourcePOIAttributeValues> poilst=resourceCustomAttributesdao.getResourcePoiDataBylandId(projectId, usin.intValue());
				 if(null != poilst){
				 for(ResourcePOIAttributeValues obj : poilst){
						
						if(groupId==obj.getGroupid()){
							
							 if(obj.getPoiattributevalueid()==2){
								map.put("middleName", obj.getAttributevalue());
							}
							else if(obj.getPoiattributevalueid()==3){
								map.put("lastName", obj.getAttributevalue());
							}
							else if(obj.getPoiattributevalueid()==4){
								map.put("dob", obj.getAttributevalue());
							}
							else if(obj.getPoiattributevalueid()==5){
								map.put("relationship", obj.getAttributevalue());
							}
							else if(obj.getPoiattributevalueid()==6){
								map.put("gender", obj.getAttributevalue());
							}
							
							
						}
						else if(groupId != obj.getGroupid()){
							
							if(null != map){
							list.add(count, map);
							count++;
							}
							
							groupId=obj.getGroupid();
							if(obj.getPoiattributevalueid()==1){
								 map =  new HashMap<String, String>();
								map.put("firstName", obj.getAttributevalue());
							}
						}
					
					
					
				}
				 list.add(count, map);
   

   
				 objpiodetails =(Object) list;
				 lstobject.add(objpiodetails);
				 }else{
					 lstobject.add((Object) new ArrayList<>());
					 
				 }
				
				 
				
				 lstobject.add(objpersondetails);
				
				
				return lstobject;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
	    }
	    
	    
	    @RequestMapping(value = "/viewer/landrecords/landPOIBuyer/{usin}/{processid}", method = RequestMethod.GET)
	    @ResponseBody
	    public List<SpatialUnitPersonWithInterest> poiByLandid(@PathVariable Long usin, @PathVariable Long processid) {
	    	
	    	List<SpatialUnitPersonWithInterest> obj = null;
	    	SocialTenureRelationship plmobj = registrationRecordsService.getSocialTenureRelationshipByLandIdForBuyer(usin, processid);
	    	if(null != plmobj){
	    		Integer transid = plmobj.getLaExtTransactiondetail().getTransactionid();
	    	 obj = spatialunitpersonwithinterestdao.findByUsinandTransid(usin, transid.longValue());
	    	 return obj;
	    	}
	    	
	    	else {
	    		return null;
	    	
	    	}
	    }
	    
	    @RequestMapping(value = "/viewer/landrecords/editlandPOIBuyer/{usin}/{transid}", method = RequestMethod.GET)
	    @ResponseBody
	    public List<SpatialUnitPersonWithInterest> editpoiByLandid(@PathVariable Long usin, @PathVariable Long transid) {
	    	
	    	List<SpatialUnitPersonWithInterest> obj = null;
	    	/*	SocialTenureRelationship plmobj = registrationRecordsService.getSocialTenureRelationshipForSellerByLandId(usin);
	    	if(null != plmobj){
	    		Integer transid = plmobj.getLaExtTransactiondetail().getTransactionid();*/
	    	 obj = spatialunitpersonwithinterestdao.findByUsinandTransid(usin, transid.longValue());
	    	 
	    	 if(obj.size()>0){
	    	 return obj;
	    	 }
	   
	    	else {
	    		return null;
	    	
	    	}
	    }
	    
	    
	    @RequestMapping(value = "/viewer/landrecords/saveRegPersonOfInterestForEditing/{landId}/{processid}", method = RequestMethod.POST)
	    @ResponseBody
	    public SpatialUnitPersonWithInterest saveRegPersonOfInterestForEditing(HttpServletRequest request, @PathVariable Long landId, @PathVariable Long processid, Principal principal) {
	        
	    	SpatialUnitPersonWithInterest personinterest =null;
	    	Date date1 =null;
	    	try {
	        	Integer PoiId =0;
	        	Integer genderid =0;
	        	Integer realtionid =0;
	        	String dateofbirth ="";
	        	String firstname ="";
	        	String middlename ="";
	        	String lastname ="";
	        	SocialTenureRelationship socialTenureRelationshipBuyerDetails=null;
	        	
	        	try{firstname =  ServletRequestUtils.getRequiredStringParameter(request, "firstname_sale_poi");}catch(Exception e){}
				try{middlename =ServletRequestUtils.getRequiredStringParameter(request, "middlename_sale_poi");}catch(Exception e){}
				try{lastname =ServletRequestUtils.getRequiredStringParameter(request, "lastname_sale_poi");}catch(Exception e){}
				try{genderid =ServletRequestUtils.getRequiredIntParameter(request, "gender_sale_POI");}catch(Exception e){}
				try{realtionid= ServletRequestUtils.getRequiredIntParameter(request, "Relationship_sale_POI");}catch(Exception e){}
				try{dateofbirth=ServletRequestUtils.getRequiredStringParameter(request, "date_Of_birthPOI_sale");}catch(Exception e){}
				try{PoiId=ServletRequestUtils.getRequiredIntParameter(request, "leaseepoiid");}catch(Exception e){}
				Integer editflag = ServletRequestUtils.getRequiredIntParameter(request, "editflag");
				if(dateofbirth != ""){
					
					
				     date1 = new SimpleDateFormat("YYYY-MM-DD").parse(dateofbirth);
		
		
//		 date1=new SimpleDateFormat("yyyy-MM-dd").parse(finaldob);
		}
				if(editflag==0){
				 socialTenureRelationshipBuyerDetails = registrationRecordsService.getSocialTenureRelationshipByLandIdForBuyer(landId,processid);
				}else if(editflag==1){
					 socialTenureRelationshipBuyerDetails = registrationRecordsService.getAllSocialTenureRelationshipByTransactionId(processid);

				}

					if(null !=socialTenureRelationshipBuyerDetails){
				personinterest = spatialUnitPersonWithInterestService.findSpatialUnitPersonWithInterestById(PoiId.longValue());
	        	
				if(null ==personinterest){
	            	personinterest = new SpatialUnitPersonWithInterest();
	            	 personinterest.setFirstName(firstname);
	                 personinterest.setMiddleName(middlename);
	                 personinterest.setLastName(lastname);
	                 personinterest.setDob(date1);
	                 personinterest.setGender(genderid);
	                 personinterest.setRelation(realtionid);
	 	           personinterest.setLandid(landId);
	 	          personinterest.setCreatedby(1);
	 	         personinterest.setCreateddate(new Date());
	 	        personinterest.setIsactive(true);
	 	       personinterest.setTransactionid(socialTenureRelationshipBuyerDetails.getLaExtTransactiondetail().getTransactionid());
	            }
	            else{
	            personinterest.setFirstName(firstname);
	            personinterest.setMiddleName(middlename);
	            personinterest.setLastName(lastname);
	            personinterest.setDob(date1);
	            personinterest.setGender(genderid);
	            personinterest.setRelation(realtionid);
		 	    personinterest.setTransactionid(socialTenureRelationshipBuyerDetails.getLaExtTransactiondetail().getTransactionid());

	            }
	    		
				spatialUnitPersonWithInterestService.save(personinterest);
				
					}
	        	
	            return personinterest;
	        } catch (Exception e) {
	            logger.error(e);
	            return null;
	        }
	    }
	    
	    
	    
	    
	    
	    @RequestMapping(value = "/viewer/landrecords/editRegPersonOfInterestForEditing/{landId}/{transid}", method = RequestMethod.POST)
	    @ResponseBody
	    public SpatialUnitPersonWithInterest editRegPersonOfInterestForEditing(HttpServletRequest request, @PathVariable Long landId, @PathVariable Long transid, Principal principal) {
	        
	    	SpatialUnitPersonWithInterest personinterest =null;
	    	Date date1 =null;
	    	try {
	        	Integer PoiId =0;
	        	Integer genderid =0;
	        	Integer realtionid =0;
	        	String dateofbirth ="";
	        	String firstname ="";
	        	String middlename ="";
	        	String lastname ="";
	        	
	        	
				try{firstname =  ServletRequestUtils.getRequiredStringParameter(request, "firstName");}catch(Exception e){}
				try{middlename =ServletRequestUtils.getRequiredStringParameter(request, "middleName");}catch(Exception e){}
				try{lastname =ServletRequestUtils.getRequiredStringParameter(request, "lastName");}catch(Exception e){}
				try{genderid =ServletRequestUtils.getRequiredIntParameter(request, "gender");}catch(Exception e){}
				try{realtionid= ServletRequestUtils.getRequiredIntParameter(request, "relation");}catch(Exception e){}
				try{dateofbirth=ServletRequestUtils.getRequiredStringParameter(request, "dob");}catch(Exception e){}
				try{PoiId=ServletRequestUtils.getRequiredIntParameter(request, "id");}catch(Exception e){}
				
				if(dateofbirth != ""){
					
					 DateFormat inputFormat = new SimpleDateFormat(
						        "E MMM dd yyyy HH:mm:ss 'GMT'z", Locale.ENGLISH);
						     date1 = inputFormat.parse(dateofbirth);
	    		
						     
//				 date1=new SimpleDateFormat("yyyy-MM-dd").parse(finaldob);
				}
				
				/*SocialTenureRelationship socialTenureRelationshipSellerDetails = registrationRecordsService.getSocialTenureRelationshipForSellerByLandId(landId);

					if(null !=socialTenureRelationshipSellerDetails){*/
				personinterest = spatialUnitPersonWithInterestService.findSpatialUnitPersonWithInterestById(PoiId.longValue());
	        	
				if(null ==personinterest){
	            	personinterest = new SpatialUnitPersonWithInterest();
	            	 personinterest.setFirstName(firstname);
	                 personinterest.setMiddleName(middlename);
	                 personinterest.setLastName(lastname);
	                 personinterest.setDob(date1);
	                 personinterest.setGender(genderid);
	                 personinterest.setRelation(realtionid);
	 	           personinterest.setLandid(landId);
	 	          personinterest.setCreatedby(1);
	 	         personinterest.setCreateddate(new Date());
	 	        personinterest.setIsactive(true);
	 	       personinterest.setTransactionid(transid.intValue());
	            }
	            else{
	            personinterest.setFirstName(firstname);
	            personinterest.setMiddleName(middlename);
	            personinterest.setLastName(lastname);
	            personinterest.setDob(date1);
	            personinterest.setGender(genderid);
	            personinterest.setRelation(realtionid);
		 	    personinterest.setTransactionid(transid.intValue());

	            }
	    		
				spatialUnitPersonWithInterestService.save(personinterest);
				
//					}
	        	
	            return personinterest;
	        } catch (Exception e) {
	            logger.error(e);
	            return null;
	        }
	    }
	    
	    
	    @RequestMapping(value = "/viewer/landrecords/landPOIstatus/{usin}/{processid}", method = RequestMethod.GET)
	    @ResponseBody
	    public String addPoiStatus(@PathVariable Long usin, @PathVariable Long processid) {
	    	
	    	List<SpatialUnitPersonWithInterest> obj = null;
	    	SocialTenureRelationship plmobj = registrationRecordsService.getSocialTenureRelationshipByLandIdForBuyer(usin, processid);
	    	if(null == plmobj && processid==2){
	    		
	    	 return "Add Buyer details First to add POI's";
	    	}
	    	else if (null == plmobj && (processid==4 || processid==6 || processid==7)){
	    		return "Add New Owner details First to add POI's";
	    	}
	    	else{
	    		return "true";
	    	}
	    	
	    	
	    	}
	    
	    @RequestMapping(value = "/viewer/landrecords/batchlandcorrectionreport/{transidstart}/{transidend}/{usin}", method = RequestMethod.GET)
	    @ResponseBody
	    public List<List<Object>> getBatchDataCorrectionRep(@PathVariable Long transidstart,@PathVariable Long transidend,@PathVariable Long usin,  HttpServletRequest request, HttpServletResponse response, Principal principal ) {

	    	List<List<Object>> batchobject=new ArrayList<>(); 
	    	 
	    	Object objlanddetails = null;
	    	Object objpersondetails = null; 
	    	Object objpoidetails = null;
	    	Object objnonnaturalpersondetails = null;
	    	Object objdocs = null;
	    	Object signdocs = null;
	    	
	    	for(Long transid =transidstart; transid<=transidend; transid++){
	    		List<Object> object=new ArrayList<Object>();
	    		try 
		    	{
		    		objlanddetails= landRecordsService.getDataCorrectionReport(transid, usin);
		    		if(null!=objlanddetails){
		    		object.add(objlanddetails);
		    		}

		    	}catch(Exception e){
		    		e.printStackTrace(); 
		    	}
		    	try 
		    	{
		    		objpoidetails= landRecordsService.getDataCorrectionReportPOI(transid, usin);
		    		if(null!=objlanddetails){
		    		object.add(objpoidetails);
		    		}

		    	}catch(Exception e){
		    		e.printStackTrace(); 
		    	}
		    	
		    	try 
		    	{
		    		objpersondetails= landRecordsService.getDataCorrectionPersonsReport(transid, usin);
		    		if(null!=objlanddetails){
		    			object.add(objpersondetails);
		    		}

		    	}catch(Exception e){
		    		e.printStackTrace(); 
		    	}
		    	
		    	try 
		    	{
		    		List<SourceDocument> doc =   sourcedocdao.findBatchSourceDocumentByLandIdandTransactionid(transid);
		    		
			    	objdocs = (Object) doc;
			    	if(null!=objlanddetails){
			    		object.add(objdocs);
			    	}

		    	}catch(Exception e){
		    		e.printStackTrace(); 
		    	}
		    	
		    	try 
		    	{
		    		 String username = principal.getName();
				        User user = userService.findByUniqueName(username);
				        Long userid = user.getId();
				        String projectName = userDataService.getDefaultProjectByUserId(userid.intValue());
				        Project project= projectDAO.findByName(projectName);
		                
				        ProjectArea projectArea = projectService.getProjectArea(project.getName()).get(0);
		    		
				        signdocs = (Object) projectArea;
				        if(null!=objlanddetails){
				        	object.add(signdocs);
				        }

		    	}catch(Exception e){
		    		e.printStackTrace(); 
		    	}

		 	   try {
				List<NonNaturalPerson> lstdata = new ArrayList<>();
				   
				 List<SocialTenureRelationship>  lst= new ArrayList<SocialTenureRelationship>();
					 lst= landRecordsService.findBatchAllSocialTenureByUsin(transid);
				  	 if(lst.size()>0)
				  	 {
				  		 for(SocialTenureRelationship obj:lst){
				  			 
				  			 if(obj.getLaPartygroupPersontype().getPersontypeid()== 2)
				  			 {
				  				lstdata.add((NonNaturalPerson) laPartyDao.getPartyIdByID(obj.getPartyid()));
				  			 }
				  			  			 
				  		 }
				  	 }
				  	objnonnaturalpersondetails =(Object) lstdata;
				  	if(null!=objlanddetails){
				  		object.add(objnonnaturalpersondetails);
				  	}
				  	if(null!=objlanddetails){
				  	batchobject.add(object);
				  	}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	}
	    	
	    	return batchobject;
	    }
	    
	    
	    
	    
	    @RequestMapping(value = "/viewer/landrecords/landDocs/{usin}", method = RequestMethod.GET)
	    @ResponseBody
	    public List<SourceDocument> getlandDocs(@PathVariable Long usin) {
	    	
	    	List<SourceDocument> sourcedocsList=  new ArrayList<SourceDocument>();
	    	
	    	List<SourceDocument> sourceDocumentlist = landRecordsService.findMultimediaByGid(new Long(usin));
	    	
	    	for(SourceDocument obj: sourceDocumentlist){
	    		
	    		if(null==obj.getLaParty() ){
//	    			if(obj.getIsactive()){
	    			sourcedocsList.add(obj);
//	    			}
	    			
	    		}
	    	}
	    	
	    
	    	
	    	return sourcedocsList;
	    	}
	    
	   
	    @RequestMapping(value = "/viewer/landrecords/downloadlandmedia/{id}", method = RequestMethod.GET)
	    @ResponseBody
	    public void landMultimediaShow(@PathVariable Long id,  HttpServletRequest request, HttpServletResponse response) {
	    	byte[] data =null;
	    	 String[] format=null;
	    	SourceDocument doc =null;
	    	
	    		  doc =  sourcedocdao.findDocumentByDocumentId(id);
	    	  
	    	
	    	 if(doc==null){
	    		 response.setContentLength(data.length);
	    		/* if(null!=doc.getLaExtDocumentformat()){
	    		  format=doc.getLaExtDocumentformat().getDocumentformatEn().split("/");
	    		 }*/
	    	 }
	        String filepath = FileUtils.getFielsFolder(request) + doc.getDocumentlocation()
	                +"/"+ doc.getDocumentname();
	         filepath =  filepath.replace("\\mast\\..", "");
	        Path path = Paths.get(filepath);
	        try {
	            data = Files.readAllBytes(path);
	            response.setContentLength(data.length);
	            OutputStream out = response.getOutputStream();
	            out.write(data);
	            out.flush();
	            out.close();

	        } catch (Exception e) {
	            logger.error(e);
	        }
	    }

	    
	    @RequestMapping(value = "/viewer/landrecords/landmediaavail/{id}", method = RequestMethod.GET)
	    @ResponseBody
	    public boolean isLandMultimediaexist(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response) {
	    	SourceDocument doc = null;
	    	
	    	    
	    		  doc =  sourcedocdao.findDocumentByDocumentId(id);
	    	
	    	
	    	
	    	if(null!=doc){
	    		return true;
	    	}else{
	    		
	    		return false;
	    	}
	    	 
	    	
	    		
	    	 
	       
	        
	    }
	    
	    @RequestMapping(value = "/viewer/upload/landmedia/", method = RequestMethod.POST)
		   @ResponseBody
		   public String uploadLandMedia(MultipartHttpServletRequest request, HttpServletResponse response, Principal principal) {	
				try {
					
					String userId[] = null ;
					String project="";
					String username = principal.getName();
					User userObj = userService.findByUniqueName(username);
					SourceDocument doc = null;
					byte[] document = null; 
					SourceDocument objDocument = new SourceDocument();

					Long created_by = userObj.getId();
					Long modifiedby = created_by;// As discussed with rajendra sir modified by also same as created by

					Iterator<String> file = request.getFileNames();
					
					
					Integer docid =null;
					docid= ServletRequestUtils.getRequiredIntParameter(request, "docsid");
					
					Integer transid =null;
					transid= ServletRequestUtils.getRequiredIntParameter(request, "transid");
					
					Long landid = 0L;
					landid= ServletRequestUtils.getRequiredLongParameter(request, "landid");
					
					
					if(null != docid){
					  doc =  sourcedocdao.findDocumentByDocumentId(docid.longValue());
					  
					  while(file.hasNext()) 
						{
							String fileName = file.next();
							String projName="";
							MultipartFile mpFile = request.getFile(fileName);
							long size = mpFile.getSize();
							String originalFileName = mpFile.getOriginalFilename();
//							
							
							

							if (originalFileName != "") {
								document = mpFile.getBytes();
							}
					  String filepath = FileUtils.getFielsFolder(request) + doc.getDocumentlocation();
				        filepath =  filepath.replace("\\mast\\..", "");
				        Path path = Paths.get(filepath);
				        File existingdr =new File(filepath);

				        
				        boolean exist = existingdr.exists();
						if (!exist) {
							
							boolean success = (new File(filepath)).mkdirs();
							
						}
						
						
				        try {
				        	File serverFile = new File(existingdr + File.separator
			                        + originalFileName);
				            
				            FileOutputStream uploadfile = new FileOutputStream(serverFile, false);
							uploadfile.write(document);
							uploadfile.flush();
							uploadfile.close();

				        } catch (Exception e) {
				            logger.error(e);
				        }
				        doc.setDocumentname(originalFileName);
				        
				        
				        sourceDocumentsDao.saveUploadedDocuments(doc);
					  
					}
					/*byte[] document = null;
					while(file.hasNext()) 
					{
						String fileName = file.next();
						String projName="";
						MultipartFile mpFile = request.getFile(fileName);
						long size = mpFile.getSize();
						String originalFileName = mpFile.getOriginalFilename();
						SourceDocument objDocument = new SourceDocument();
						
						

						if (originalFileName != "") {
							document = mpFile.getBytes();
						}
				    	 doc =  sourcedocdao.findDocumentByDocumentId(docid.longValue());
				    	if(doc==null){
				    		//String filepath = "/storage/emulated/0/MAST/multimedia/" +originalFileName;
				    		String filepath = FileUtils.getFielsFolder(request) +"/storage/emulated/0/MAST/multimedia/Parcel_Media";
					        filepath =  filepath.replace("\\mast\\..", "");
					        Path path = Paths.get(filepath);
					        File existingdr =new File(filepath);
					        boolean exist = existingdr.exists();
							if (!exist) {
								
								boolean success = (new File(filepath)).mkdirs();
								
							}
							
					        try {
					        	File serverFile = new File(existingdr + File.separator
				                        + originalFileName);
					            
					            FileOutputStream uploadfile = new FileOutputStream(serverFile, false);
								uploadfile.write(document);
								uploadfile.flush();
								uploadfile.close();

					        } catch (Exception e) {
					            logger.error(e);
					        }
					        Outputformat outputformat = Outputformatdao.findByName("image"+"/" +FileUtils.getFileExtension(originalFileName));
					        objDocument.setLaExtDocumentformat(outputformat);
					        objDocument.setCreatedby(created_by.intValue());
					        objDocument.setCreateddate(new Date());
					        objDocument.setModifiedby(created_by.intValue());
					        objDocument.setModifieddate(new Date());
					        objDocument.setRecordationdate(new Date());
					        objDocument.setRemarks("");
					        objDocument.setDocumentlocation("/storage/emulated/0/MAST/multimedia/Parcel_Media");
					        objDocument.setIsactive(true);
					        objDocument.setLaSpatialunitLand(landid);
					        objDocument.setLaParty(laPartyDao.getPartyListIdByID(partyid.longValue()).get(0));       
					        objDocument.setLaExtTransactiondetail( laExtTransactiondetailDao.getLaExtTransactiondetail(transid));
					        if(transid==0){
					        	SocialTenureRelationship socialtenureobj =socialTenureRelationshipDAO.getSocialTenureObj(partyid.longValue(), landid);
						        objDocument.setLaExtTransactiondetail(socialtenureobj.getLaExtTransactiondetail());

					        }
					        objDocument.setDocumentname(originalFileName);
					        sourceDocumentsDao.saveUploadedDocuments(objDocument);
							

				    	}
				        String filepath = FileUtils.getFielsFolder(request) + doc.getDocumentlocation();
				        filepath =  filepath.replace("\\mast\\..", "");
				        Path path = Paths.get(filepath);
				        File existingdr =new File(filepath);

				        
				        boolean exist = existingdr.exists();
						if (!exist) {
							
							boolean success = (new File(filepath)).mkdirs();
							
						}
						
						
				        try {
				        	File serverFile = new File(existingdr + File.separator
			                        + originalFileName);
				            
				            FileOutputStream uploadfile = new FileOutputStream(serverFile, false);
							uploadfile.write(document);
							uploadfile.flush();
							uploadfile.close();

				        } catch (Exception e) {
				            logger.error(e);
				        }
				    
				        objDocument= doc;
				        objDocument.setDocumentname(originalFileName);
				        sourceDocumentsDao.saveUploadedDocuments(objDocument);
						*/
						
					 System.out.println("true");

				}else{
					
					
					while(file.hasNext()) 
					{
						String fileName = file.next();
						String projName="";
						MultipartFile mpFile = request.getFile(fileName);
						long size = mpFile.getSize();
						String originalFileName = mpFile.getOriginalFilename();
						
						
						

						if (originalFileName != "") {
							document = mpFile.getBytes();
						}
				    	
				    		
				    		String filepath = FileUtils.getFielsFolder(request) +"/storage/emulated/0/MAST/multimedia/Parcel_Media";
					        filepath =  filepath.replace("\\mast\\..", "");
					        Path path = Paths.get(filepath);
					        File existingdr =new File(filepath);
					        boolean exist = existingdr.exists();
							if (!exist) {
								
								boolean success = (new File(filepath)).mkdirs();
								
							}
							
					        try {
					        	File serverFile = new File(existingdr + File.separator
				                        + originalFileName);
					            
					            FileOutputStream uploadfile = new FileOutputStream(serverFile, false);
								uploadfile.write(document);
								uploadfile.flush();
								uploadfile.close();

					        } catch (Exception e) {
					            logger.error(e);
					        }
					        Outputformat outputformat = Outputformatdao.findByName("image"+"/" +FileUtils.getFileExtension(originalFileName));
					        objDocument.setLaExtDocumentformat(outputformat);
					        objDocument.setCreatedby(created_by.intValue());
					        objDocument.setCreateddate(new Date());
					        objDocument.setModifiedby(created_by.intValue());
					        objDocument.setModifieddate(new Date());
					        objDocument.setRecordationdate(new Date());
					        objDocument.setRemarks("");
					        objDocument.setDocumentlocation("/storage/emulated/0/MAST/multimedia/Parcel_Media");
					        objDocument.setIsactive(true);
					        objDocument.setLaSpatialunitLand(landid);
					        objDocument.setLaExtTransactiondetail( laExtTransactiondetailDao.getLaExtTransactiondetail(transid));
					        objDocument.setDocumentname(originalFileName);
					        sourceDocumentsDao.saveUploadedDocuments(objDocument);
					}
					}
					
				}catch (Exception e) {
					logger.error(e);
				}		
				return "Success";
			}
	    
	    
	    @RequestMapping(value = "/viewer/delete/landmedia/", method = RequestMethod.POST)
		   @ResponseBody
		   public String landMediaDelete(HttpServletRequest request, HttpServletResponse response, Principal principal) {	
				try {
					
					String userId[] = null ;
					String project="";
					String username = principal.getName();
					User userObj = userService.findByUniqueName(username);

					Long created_by = userObj.getId();
					Long modifiedby = created_by;// As discussed with rajendra sir modified by also same as created by

					SourceDocument doc = null;
					
					
					Integer docid =null;
					docid= ServletRequestUtils.getRequiredIntParameter(request, "docid");
					
					Integer transid =null;
					transid= ServletRequestUtils.getRequiredIntParameter(request, "transid");
					
					Long landid = 0L;
					landid= ServletRequestUtils.getRequiredLongParameter(request, "landid");
					
					doc =  sourcedocdao.findDocumentByDocumentId(docid.longValue());
					doc.setIsactive(false);
					
					sourceDocumentsDao.saveUploadedDocuments(doc);
					
				}
				catch(Exception e){
					e.printStackTrace();
				}
				return "success";
					
		   }
	    
	    
	    @RequestMapping(value = "/viewer/landrecords/splitupdate/{landId}", method = RequestMethod.GET)
	    @ResponseBody
	    public boolean updateParcelsplit(HttpServletRequest request, @PathVariable Long landId, Principal principal) {

	    	String username = principal.getName();
	    	User user = userService.findByUniqueName(username);
	    	Long userid = user.getId();

	    	LaExtParcelSplitLand objLaExtParcelSplitLand = new LaExtParcelSplitLand();
	    	objLaExtParcelSplitLand.setLandid(landId);
	    	objLaExtParcelSplitLand.setIsactive(true);
	    	objLaExtParcelSplitLand.setCreateddate(new Date());
	    	objLaExtParcelSplitLand.setCreatedby(userid.intValue());
	    	try{
	    		laExtParcelSplitLandService.addLaExtParcelSplitLandService(objLaExtParcelSplitLand);
	    	}catch(Exception e){
	    		e.printStackTrace();
	    		return false;
	    	}
	    	return true;

}
	    
	    
	    @RequestMapping(value = "/viewer/landrecords/deleteperson/{partyid}/{landId}", method = RequestMethod.GET)
	    @ResponseBody
	    public boolean deletePerson(HttpServletRequest request, @PathVariable Long partyid,@PathVariable Long landId, Principal principal) {
	    	
	    	 /*List<ClaimBasic> claimBasicobj =  spatialUnitService.getClaimsBasicByLandId(landId);*/
			 List<SocialTenureRelationship> socialtenureobj = socialTenureRelationshipDao.getSocialTenureRelationshipBylandID(landId);
			 
			 if(null!=socialtenureobj && socialtenureobj.size()>0){

				for(SocialTenureRelationship socialobj:socialtenureobj){
					if(socialobj.getPartyid().longValue()==partyid.longValue()){
	    	NaturalPerson laparty= (NaturalPerson) laPartyDao.getActivePartyIdByID(partyid);
	    	if(null!= laparty){
	    		if(laparty.getOwnertype().intValue() != 1){
	    		laparty.setIsactive(false);
	    		NaturalPerson obj = naturalPersonDao.addNaturalPerson(laparty);
	    		socialobj.setIsactive(false);
	    		registrationRecordsService.saveSocialTenureRelationship(socialobj);
	    		}else{
	    			return false;
	    		}
	    		
	    		
	    	}
	    	else{
	    		return false;
	    	}
					}
				}
				return true;
			 }
			 return false;
}
	   
	    
	    @RequestMapping(value = "/viewer/landrecords/deletepOI/{PoiId}", method = RequestMethod.GET)
	    @ResponseBody
	    public boolean deletePOI(HttpServletRequest request, @PathVariable Long PoiId, Principal principal) {
	    	
	    	 /*List<ClaimBasic> claimBasicobj =  spatialUnitService.getClaimsBasicByLandId(landId);*/
			
			 
			
	    	SpatialUnitPersonWithInterest  personinterest = spatialUnitPersonWithInterestService.findSpatialUnitPersonWithInterestById(PoiId.longValue());
	    	if(null!= personinterest){
	    		
	    		personinterest.setIsactive(false);
	    		spatialUnitPersonWithInterestService.save(personinterest);
	    		
	    		
	    		return true;
	    	}
	    	else{
	    		return false;
	    	}
					
}
	    
}