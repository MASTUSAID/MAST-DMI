package com.rmsi.mast.studio.mobile.web;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.security.Principal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.rmsi.mast.studio.dao.DisputeDao;
import com.rmsi.mast.studio.dao.DocumentTypeDao;
import com.rmsi.mast.studio.dao.LaExtDisputelandmappingDAO;
import com.rmsi.mast.studio.dao.LandTypeDAO;
import com.rmsi.mast.studio.dao.OutputformatDAO;
import com.rmsi.mast.studio.dao.ProjectDAO;
import com.rmsi.mast.studio.dao.RelationshipTypeDao;
import com.rmsi.mast.studio.dao.ResourceSubClassificationDAO;
import com.rmsi.mast.studio.dao.SlopeValuesDAO;
import com.rmsi.mast.studio.dao.SocialTenureRelationshipDAO;
import com.rmsi.mast.studio.dao.SourceDocumentDAO;
import com.rmsi.mast.studio.dao.UnitDAO;
import com.rmsi.mast.studio.dao.UserDAO;
import com.rmsi.mast.studio.dao.hibernate.AttributeCategoryHibernateDAO;
import com.rmsi.mast.studio.dao.hibernate.PersonHibernateDAO;
import com.rmsi.mast.studio.domain.AttributeMaster;
import com.rmsi.mast.studio.domain.AttributeValues;
import com.rmsi.mast.studio.domain.Dispute;
import com.rmsi.mast.studio.domain.LaExtDisputelandmapping;
import com.rmsi.mast.studio.domain.LaExtTransactiondetail;
import com.rmsi.mast.studio.domain.LaParty;
import com.rmsi.mast.studio.domain.LaPartygroupOccupation;
import com.rmsi.mast.studio.domain.Outputformat;
import com.rmsi.mast.studio.domain.Project;
import com.rmsi.mast.studio.domain.ProjectSpatialData;
import com.rmsi.mast.studio.domain.ResourceSourceDocument;
import com.rmsi.mast.studio.domain.SocialTenureRelationship;
import com.rmsi.mast.studio.domain.SourceDocument;
import com.rmsi.mast.studio.domain.SpatialUnit;
import com.rmsi.mast.studio.domain.Status;
import com.rmsi.mast.studio.domain.User;
import com.rmsi.mast.studio.mobile.dao.AttributeMasterResourcePoiDAO;
import com.rmsi.mast.studio.mobile.dao.EducationLevelDao;
import com.rmsi.mast.studio.mobile.dao.LaPartygroupOccupationDAO;
import com.rmsi.mast.studio.mobile.dao.LandUseTypeDao;
import com.rmsi.mast.studio.mobile.dao.SoilQualityValuesDao;
import com.rmsi.mast.studio.mobile.dao.hibernate.SocialTenureHibernateDao;
import com.rmsi.mast.studio.mobile.service.AttributeMasterResourcePoiService;
import com.rmsi.mast.studio.mobile.service.LaSpatialunitAOIService;
import com.rmsi.mast.studio.mobile.service.PersonService;
import com.rmsi.mast.studio.mobile.service.ProjectService;
import com.rmsi.mast.studio.mobile.service.ResourceCustomAttributesService;
import com.rmsi.mast.studio.mobile.service.ResourceClassificationServise;
import com.rmsi.mast.studio.mobile.service.ResourceSubClassificationService;
import com.rmsi.mast.studio.mobile.service.SpatialDataService;
import com.rmsi.mast.studio.mobile.service.SpatialUnitService;
import com.rmsi.mast.studio.mobile.service.SurveyProjectAttributeService;
import com.rmsi.mast.studio.mobile.service.UserDataService;
import com.rmsi.mast.studio.mobile.transferobjects.Attribute;
import com.rmsi.mast.studio.mobile.transferobjects.Property;
import com.rmsi.mast.studio.service.AttributeCategoryService;
import com.rmsi.mast.studio.service.UserService;
import com.rmsi.mast.studio.util.FileUtils;
import com.rmsi.mast.studio.util.StringUtils;
import com.rmsi.mast.viewer.dao.IDTypeDAO;
import com.rmsi.mast.viewer.dao.LaExtTransactiondetailDao;
import com.rmsi.mast.viewer.dao.LaPartyDao;
import com.rmsi.mast.viewer.dao.MaritalStatusaDao;
import com.rmsi.mast.viewer.dao.SpatialUnitDAO;
import com.rmsi.mast.viewer.service.RegistrationRecordsService;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;

@RestController
public class MobileDataController {

	@Autowired
	UserService userService;

	@Autowired
	UserDataService mobileUserService;

	@Autowired
	SpatialDataService spatialDataService;

	@Autowired
	SurveyProjectAttributeService surveyProjectAttribute;

	@Autowired
	SpatialUnitService spatialUnitService;

	@Autowired
	PersonService personService;

	@Autowired
	ProjectService projectService;

	@Autowired
	com.rmsi.mast.studio.service.ProjectService studioProjectService;

	@Autowired
	DocumentTypeDao documentTypeDao;

	@Autowired
	DisputeDao disputeDao;

	@Autowired
	ProjectDAO projectDao;

	@Autowired
	SpatialUnitDAO spatialUnitDao;

	@Autowired
	PersonHibernateDAO personhibernatedao;

	@Autowired
	LaPartyDao lapartydao;

	@Autowired
	UserDAO userDao;

	@Autowired
	LaExtTransactiondetailDao transactiondao;

	@Autowired
	RegistrationRecordsService registrationRecordsService;

	@Autowired
	ResourceSubClassificationService resourceSubclassificationService;

	@Autowired
	ResourceClassificationServise resourceClassificationServise;

	@Autowired
	SocialTenureRelationshipDAO socialTenureRelationshipdao;

	@Autowired
	OutputformatDAO Outputformatdao;

	@Autowired
	ResourceCustomAttributesService resouceCustomAttributesService;

	@Autowired
	LaSpatialunitAOIService laSpatialunitAOIService;

	@Autowired
	AttributeCategoryService attributeCategoryService;

	@Autowired
	AttributeMasterResourcePoiDAO attributeMasterResourcePoidao;
	
	@Autowired
	LandTypeDAO landTypedao;
	
	@Autowired
	LandUseTypeDao landusetypedao;
	
	@Autowired
	SoilQualityValuesDao soilQualityValuesDao;
	
	@Autowired
	SlopeValuesDAO slopevaluedao;
	
	@Autowired
	UnitDAO unitDao;
	
	@Autowired
	IDTypeDAO IdTypedao;
	
	@Autowired
	LaPartygroupOccupationDAO laPartygroupOccupationdao;
	
	
	@Autowired
	EducationLevelDao educationleveldao;
	
	@Autowired
	MaritalStatusaDao maritalstatusdao;
	
	@Autowired
	RelationshipTypeDao relationshipTypedao;
	
	@Autowired
	LaExtDisputelandmappingDAO laExtDisputelandmappingDAO;
	
	@Autowired
	SourceDocumentDAO sourceDocumentDAO;
	
	
	


	private List<AttributeValues> attributeValuesList = new ArrayList<AttributeValues>();

	private List<AttributeValues> tenureList = new ArrayList<AttributeValues>();

	private List<AttributeValues> naturalList = new ArrayList<AttributeValues>();

	private List<AttributeValues> nonNaturalList = new ArrayList<AttributeValues>();

	private List<List<AttributeValues>> naturalListByGId = new ArrayList<List<AttributeValues>>();

	private List<List<AttributeValues>> nonNaturalListByGId = new ArrayList<List<AttributeValues>>();

	private List<List<AttributeValues>> tenureListByGId = new ArrayList<List<AttributeValues>>();

	private Map<String, List<List<AttributeValues>>> attributeValuesMap = new ConcurrentHashMap<String, List<List<AttributeValues>>>();

	static String prevGroupId = "";

	static String featureId;

	static String attributeCategoryType;

	private static final Logger logger = Logger
			.getLogger(MobileDataController.class.getName());

	/**
	 * Size of a byte buffer to read/write file
	 */
	private static final int BUFFER_SIZE = 26214400;

	@RequestMapping(value = "/studio/mobile/user/{id}", method = RequestMethod.GET)
	public User getUserById(@PathVariable String id) {
		User usr = userService.findUserByName(id);
		return usr;
	}

	/**
	 * This method will authenticate the user on given URL s
	 *
	 * @param request
	 * @param response
	 * @return: Returns user object of the user after successful authentication
	 * @author shruti.thakur
	 */
	@RequestMapping(value = "/sync/mobile/user/auth", method = RequestMethod.POST)
	public User authenticateUser(HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println("AA");
		return mobileUserService
				.authenticateByEmail(request.getParameter("email"),
						request.getParameter("password"));
	}

	/**
	 * This method will allow the user to download MbTiles
	 *
	 * @param request
	 * @param redsponse
	 * @author shruti.thakur
	 */
	@RequestMapping(value = "sync/mobile/user/download/mbTiles/{mbTilesId}", method = RequestMethod.GET)
	public String downloadMbTilesByProjectId(@PathVariable int mbTilesId,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		// Get object of ProjectSpatailData
		ProjectSpatialData projectSpatialData = spatialDataService
				.getProjectSpatialData(mbTilesId);

		// Download the file from the path specified in ProjectSpatialData
		return downloadData(
				response,
				request.getServletContext().getRealPath(
						getFullFilePath(projectSpatialData, mbTilesId)),
				request.getServletContext());
	}

	/**
	 * This method will allow the user to download Survey Project Attributes and
	 * Spatial Data
	 *
	 * @param projectId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "sync/mobile/user/download/configuration/{userId}", method = RequestMethod.GET)
    public Map<String, Object> downloadConfigurationByProjectId(
            @PathVariable int userId, HttpServletRequest request,
            HttpServletResponse response) {

        String projectId = mobileUserService.getDefaultProjectByUserId(userId);
        Project projobj = projectDao.findByName(projectId);

        if (projectId != null) {
            Map<String, Object> configurationData = new ConcurrentHashMap<String, Object>();

            try {
                // Add project information
                Project project = studioProjectService.findProjectByName(projectId);
                String projectExtent = "";

                if (project.getMinextent() != null && !project.getMinextent().equals("")) {
                    projectExtent = project.getMinextent();
                }

                if (project.getMaxextent() != null && !project.getMaxextent().equals("")) {
                    projectExtent = project.getMaxextent();
                }

                configurationData.put("Extent", projectExtent);

//                // Add Village Name to Map
                configurationData.put("Village",
                        projectService.getProjectArea(projectId).get(0).getLaSpatialunitgroupHierarchy3().getNameEn());

                // Add Attributes to the map 
                configurationData.put("Attributes", surveyProjectAttribute
                        .getSurveyAttributesByProjectId(projobj));
//
//                // Add Project Spatial Data
                configurationData.put("SpatialData", spatialDataService
                        .getProjectSpatialDataByProjectId(projobj.getProjectnameid()));

//                // Add List of Adjudicator
//                configurationData.put("Adjudicator", surveyProjectAttribute
//                        .getProjectAdjudicatorByProjectId(projectId));
//
//                // Add List of Hamlet
//                configurationData.put("Hamlet", surveyProjectAttribute
//                        .getProjectHamletsByProjectId(projectId));

//                 Add List of claim types
                configurationData.put("ClaimType", spatialUnitService.getClaimTypes());

                // Add List of relationship types
                configurationData.put("RelationshipType", relationshipTypedao.findAll());

                // Add List of share types
                configurationData.put("ShareType", spatialUnitService.getShareTypes());

                // Add List of right types
                configurationData.put("RightType", spatialUnitService.getTenureClasses());

                // Add List of genders
                configurationData.put("Genders", spatialUnitService.getGenders());

                // Add List of DisputeType
                configurationData.put("DisputeType", spatialUnitService.getDisputeTypes());

                // Add List of AcquisitionType
                configurationData.put("AcquisitionType", spatialUnitService.getAcquisitionTypes());
                
                // Add List of ResourceSubClassification
                configurationData.put("ResourceSubClassification", resourceSubclassificationService.getAllSubClassifications());

                // Add List of ResourceClassification
                configurationData.put("ResourceClassification", resourceClassificationServise.getAllClassifications());
                
                // Add List of ResourceCustomAttributes
                configurationData.put("ResourceCustomAttributes", resouceCustomAttributesService.getByProjectId(projobj.getProjectnameid()));
              
                // Add List of AOI
                configurationData.put("AOI", laSpatialunitAOIService.getByUserId(userId));
                
                // Add List of Tenure Type
                configurationData.put("TenureType", attributeCategoryService.findAllAttributeCategory());
                
                // Add List of AttributeMasterResourcePOI
                configurationData.put("AttributeMasterResourcePOI", attributeMasterResourcePoidao.findAll());
                
                
//                // Add List of LandType
//                configurationData.put("LandType", landTypedao.findAll());
//                
//                
//                // Add List of LandUseType
//                configurationData.put("LandUseType", landusetypedao.findAll());
//                
//              
//                

                
                
                
                // Add List of LandSoilQuality
                configurationData.put("LandSoilQuality", soilQualityValuesDao.findAll());
                
                // Add List of SlopeValue
                configurationData.put("SlopeValue", slopevaluedao.findAll());

                // Add List of Unit
                configurationData.put("Unit", unitDao.findAll());
//                
//                // Add List of IdentityType
//                configurationData.put("IdentityType", IdTypedao.findAll());
//                
//                // Add List of Occupation
//                configurationData.put("Occupation", laPartygroupOccupationdao.findAll());
//                
//                
//                // Add List of Education
//                configurationData.put("Education", educationleveldao.findAll());
//                
//                
//                // Add List of MarritalStatus
//                configurationData.put("MarritalStatus", maritalstatusdao.findAll());
//                
                
                
            	
                
                
                
                
                
                // Return map containing Attributes and SpatialUnit for Download 
                // Configuration
                System.out.println(configurationData);
                logger.info("Json Download" +configurationData);
                return configurationData;
                

            } catch (Exception ex) {
                logger.error("NullPointerException", ex);
                System.out
                        .println("Either Attributes or Spatial Data not present in database"
                                + ex);
                return null;
            }
        } else {
            System.out.println("User doesn't exist");
            return null;
        }
    }

	/**
	 * It will get SurveyProject attributes based on Project ID
	 *
	 * @author shruti.thakur
	 * @param reponse
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/studio/mobile/project/attributes", method = RequestMethod.POST)
	public List<AttributeMaster> getProjectAttributes(
			HttpServletResponse reponse, HttpServletRequest request) {
		// Project projobj = projectDao.findByName(projectId);
		// return surveyProjectAttribute.getSurveyAttributesByProjectId(request
		// .getParameter("projectId"));
		return null;
	}

	/**
	 * It will enable the user to download project properties based on userId
	 *
	 * @param userId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/sync/mobile/project/getProperties/{userId}", method = RequestMethod.POST)
	public List<Property> getProjectProperties(@PathVariable int userId,
			HttpServletRequest request, HttpServletResponse response) {

		// Get default project by userId
		String projectId = mobileUserService.getDefaultProjectByUserId(userId);

		try {
			return surveyProjectAttribute.getProperties(projectId, 0);
		} catch (Exception ex) {
			logger.error("Exception while DOWNLOADING project properties", ex);
			return null;
		}
	}

	/**
	 *
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/studio/mobile/spatialunit", method = RequestMethod.POST)
	@ResponseBody
	public List<SpatialUnit> getSpatialUnit(HttpServletRequest request,
			HttpServletResponse response) {

		return spatialUnitService.getSpatialUnitDataByProjectId("CB_Proj");
	}

	/**
	 * This controller will upload multimedia from mobile to server
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/sync/mobile/document/upload/", method = RequestMethod.POST)
	@ResponseBody
	synchronized public String upload(MultipartHttpServletRequest request,
			HttpServletResponse response) {
		try {
			int counter  =0;
			SourceDocument sourceDocument = new SourceDocument();
			ResourceSourceDocument resourceDocument = new ResourceSourceDocument();
			Iterator<String> files = request.getFileNames();
			String attributes = request.getParameter("fileattribs");
		
			JSONArray name=null;
			JSONArray comments=null;
			String mediaId = null;
			JSONArray sourceDocumentAttribute = new JSONArray(attributes);
			JSONArray media = sourceDocumentAttribute.getJSONArray(0);
			String flag=media.get(7).toString();
			if(null != sourceDocumentAttribute.getJSONArray(1) && flag.equalsIgnoreCase("M")){
			 name = sourceDocumentAttribute.getJSONArray(1).getJSONArray(0);
			 comments = sourceDocumentAttribute.getJSONArray(1).getJSONArray(1);
			}
			while (files.hasNext()) {
				String fileName = files.next();
				System.out.println("FILE NAME:::: " + fileName);
				MultipartFile mpFile = request.getFile(fileName);
				String originalFileName = mpFile.getOriginalFilename();
				sourceDocument.setIsactive(true);
				resourceDocument.setIsactive(true);

				// LaExtTransactiondetail laExtTransactiondetail = new
				// LaExtTransactiondetail();
				// laExtTransactiondetail.setCreatedby(Integer.parseInt(media.get(6).toString()));
				// laExtTransactiondetail.setCreateddate(new Date());
				// laExtTransactiondetail.setIsactive(true);
				//
				// Status status = registrationRecordsService.getStatusById(1);
				//
				// laExtTransactiondetail.setLaExtApplicationstatus(status);
				// laExtTransactiondetail.setModuletransid(1);
				// laExtTransactiondetail.setRemarks("");
				// laExtTransactiondetail.setProcessid(1l);

				// sourceDocument.setLaExtTransactiondetail(laExtTransactiondetail);
				resourceDocument.setCreatedby(Integer.parseInt(media.get(6).toString()));
				resourceDocument.setCreateddate(new Date());
				resourceDocument.setRemarks("");
				resourceDocument.setDocumentname(originalFileName);
				resourceDocument.setRecordationdate(new Date());
				
				
				
				sourceDocument.setCreatedby(Integer.parseInt(media.get(6)
						.toString()));
				sourceDocument.setCreateddate(new Date());
				sourceDocument.setRemarks("");
				sourceDocument.setDocumentname(originalFileName);
				sourceDocument.setRecordationdate(new Date());
				Outputformat outputformat = Outputformatdao.findByName(media
						.get(4).toString()
						+ "/"
						+ FileUtils.getFileExtension(sourceDocument
								.getDocumentname()));
				sourceDocument.setLaExtDocumentformat(outputformat);
				resourceDocument.setLaExtDocumentformat(outputformat.getDocumentformatid());
				
				
				
				mediaId = setDocumentAttributes(sourceDocument,resourceDocument, attributes, counter, flag);

				counter=counter+1;
				if (!"".equals(originalFileName)
						&& mobileUserService.findMultimedia(originalFileName,
								sourceDocument.getLaSpatialunitLand()) == null) {

					// // Setting File Name
					// sourceDocument.setLocScannedSourceDoc("resources"
					// + "/"
					// + "documents"
					// + "/"
					// +
					// spatialUnitService.getSpatialUnitByUsin(sourceDocument.getUsin()).getProject()
					// + "/" + "multimedia");

					// Creating documents directory to store file
					

					// Setting File Name
					// sourceDocument.setDocumentname(originalFileName);

					// Add data to Source Document
					if(sourceDocument.getLaExtTransactiondetail()!=null && (flag.equalsIgnoreCase("P") ||flag.equalsIgnoreCase("M"))){
						File documentsDir = new File(
								FileUtils.getFielsFolder(request)
										+ sourceDocument.getDocumentlocation());

						if (!documentsDir.exists()) {
							documentsDir.mkdirs();
						}
						if(flag.equalsIgnoreCase("M")){
							sourceDocument.setDocumentname(name.get(1).toString());
							sourceDocument.setRemarks(comments.get(1).toString());
							
						}
						mobileUserService.uploadMultimedia(sourceDocument, mpFile,
								documentsDir);
						
					}
					else if(flag.equalsIgnoreCase("R")){
						File documentsDir = new File(
								FileUtils.getFielsFolder(request)
										+ resourceDocument.getDocumentlocation());

						if (!documentsDir.exists()) {
							documentsDir.mkdirs();
						}
					
					mobileUserService.uploadResourceMultimedia(resourceDocument, mpFile, documentsDir);
					}
				}
			}
			
//			long usin = Long.parseLong(media.get(0).toString());
//			List<LaExtDisputelandmapping> disputelandmapping = laExtDisputelandmappingDAO.findLaExtDisputelandmappingByLandId(usin);
//			List<SourceDocument> sourcedocument = sourceDocumentDAO.findByGId(usin);
//			for(int i =0 ; i< disputelandmapping.size() ; i++){
//				LaParty partyobj = new LaParty();
//				partyobj.setPartyid(disputelandmapping.get(i).getPartyid());
//				if(sourcedocument.get(i) != null){
//				sourcedocument.get(i).setLaParty(partyobj);
//				}
//				
//				
//			}
				
			return mediaId;

		} catch (Exception e) {
			logger.error("Exception", e);
			e.printStackTrace();
			return "error uploading";
		}
	}

	/**
	 * This controller will upload the data from mobile to server
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/sync/mobile/attributes/sync", method = RequestMethod.POST)
	synchronized public Map<String, String> syncAttributes(
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> result = new IdentityHashMap<String, String>();

		try {
			// BufferedReader br = new BufferedReader(new
			// InputStreamReader(request.getInputStream()));
			String projectName = request.getParameter("projectName");
			int userId = Integer.parseInt(request.getParameter("userId"));
			String jsonString = request.getParameter("data");

			if (StringUtils.isEmpty(jsonString)) {
				return result;
			}

			// Transfor JSON to transfer object
			Gson gson = new Gson();
			Type type = new TypeToken<List<Property>>() {
			}.getType();
			List<Property> properties = gson.fromJson(jsonString, type);

			return mobileUserService
					.saveClaims(properties, projectName, userId);
		} catch (Exception e) {
			logger.error("Exception", e);
			e.printStackTrace();
			result.put("Exception while saving claims.", e.toString());
			return result;
		}
	}

	/**
	 * This controller will upload the data from mobile to server
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/sync/mobile/resource/sync", method = RequestMethod.POST)
	synchronized public Map<String, String> syncResource(
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> result = new IdentityHashMap<String, String>();

		try {
			// BufferedReader br = new BufferedReader(new
			// InputStreamReader(request.getInputStream()));
			String projectName = request.getParameter("projectName");
			int userId = Integer.parseInt(request.getParameter("userId"));
			String jsonString = request.getParameter("data");

			if (StringUtils.isEmpty(jsonString)) {
				return result;
			}

			// Transfor JSON to transfer object
			Gson gson = new Gson();
			Type type = new TypeToken<List<Property>>() {
			}.getType();
			List<Property> properties = gson.fromJson(jsonString, type);

			return mobileUserService.saveResource(properties, projectName,
					userId);
		} catch (Exception e) {
			logger.error("Exception", e);
			e.printStackTrace();
			result.put("Exception while saving claims.", e.toString());
			return result;
		}
	}

	@RequestMapping(value = "/sync/mobile/sync/RejectedSpatialUnit/{userId}", method = RequestMethod.POST)
	public List<Long> syncRejectedSpatialUnit(@PathVariable int userId,
			HttpServletRequest request, HttpServletResponse response) {

		String projectId = mobileUserService.getDefaultProjectByUserId(userId);

		if (projectId != null) {

			return spatialUnitService.getSpatialUnitByStatusId(projectId, 5);

		}
		return null;

	}

	@RequestMapping(value = "/sync/mobile/sync/adjudicatedData", method = RequestMethod.POST)
	public synchronized List<Long> updateAdjudicatedData(
			HttpServletRequest request, HttpServletResponse response) {

		try {

			ArrayList<Long> usinList = new ArrayList<Long>();

			JSONObject adjudicateData = new JSONObject(
					request.getParameter("adjudicatedData"));

			// Get User Id
			Long userId = Long.valueOf(adjudicateData.keys().next().toString());

			// Get the array of the dynamic key
			JSONArray usin = adjudicateData.getJSONArray(userId.toString());

			if (usin.length() > 0) {

				for (int i = 0; i < usin.length(); i++) {
					usinList.add(Long.valueOf(usin.get(i).toString()));
				}
			}

			return mobileUserService.updateAdjudicatedData(userId, usinList);
		} catch (JSONException e) {
			logger.error("Error while parsing JSon for UPLOADING ADJUDICATED DATA::: "
					+ e);
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * This method gets the complete path of mbTiles
	 *
	 * @param projectSpatialData
	 * @param mbTilesId
	 * @return
	 */
	public String getFullFilePath(ProjectSpatialData projectSpatialData,
			int mbTiles) {

		// if (projectSpatialData.getFileLocation().endsWith("/")) {
		// // if FileLocation ends with "/"
		// return projectSpatialData.getFileLocation() + mbTiles + "."
		// + projectSpatialData.getFileExtension();
		// } else {
		// // if FileLocation doesn't ends with "/"
		// return projectSpatialData.getFileLocation() + "/" + mbTiles + "."
		// + projectSpatialData.getFileExtension();
		// }
		return null;
	}

	/**
	 * This method downloads the file from the specified path
	 *
	 * @author Shruti.Thakur
	 * @param response
	 * @param filePath
	 * @param context
	 * @throws IOException
	 */
	public String downloadData(HttpServletResponse response, String filePath,
			ServletContext context) throws IOException {

		try {

			File downloadFile = new File(filePath);
			FileInputStream inputStream = new FileInputStream(downloadFile);

			// Get MIME type of the file
			String mimeTpye = context.getMimeType(filePath);
			if (mimeTpye == null) {
				// Set to binary if MIME mapping not found
				mimeTpye = "application/octet-stream";
			}
			System.out.println("MIME Type: " + mimeTpye);

			// Set content attribute for the response
			response.setContentType(mimeTpye);
			response.setContentLength((int) (downloadFile.length()));

			// Set header of the response
			String headerKey = "Content-Disposition";
			String headerValue = String.format("attachment; filename=\"%s\"",
					downloadFile.getName());
			response.setHeader(headerKey, headerValue);

			// Get output stream of the response
			OutputStream outStream = response.getOutputStream();

			byte[] buffer = new byte[BUFFER_SIZE];
			int bytesRead = -1;

			// Writing bytes from input stream to output stream
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, bytesRead);
			}

			inputStream.close();
			outStream.close();

			return "File Downloaded successfully";
		} catch (FileNotFoundException fnfex) {
			logger.error("Exception", fnfex);
			return "Downloading Fail: File not found";
		} catch (IOException ioex) {
			logger.error("Exception", ioex);
			System.out.println("Exception while downloading: " + ioex);
			return "Downloading Fail: IO Exception";
		}
	}

	private String setDocumentAttributes(SourceDocument document,ResourceSourceDocument resdoc,
			String attributes, int counter, String flag) {
		try {
			String mediaId = null;
		
			JSONArray sourceDocumentAttribute = new JSONArray(attributes);
			JSONArray media = sourceDocumentAttribute.getJSONArray(0);
			SpatialUnit laSpatialunitLand = new SpatialUnit();

			if (media.length() > 0) {
				long usin = Long.parseLong(media.get(0).toString());

				SocialTenureHibernateDao socialtenuredao = new SocialTenureHibernateDao();

				// List<SocialTenureRelationship> socialtenurerelationship =
				// socialtenuredao.findSocailTenureByUsin(usin);
				// List<SocialTenureRelationship> socialtenurerelationship =
				// lapartydao.findSocailTenureByUsin(usin);
				List<SocialTenureRelationship> socialtenurerelationship = socialTenureRelationshipdao.findbyUsin(usin);
				List<LaExtDisputelandmapping> disputelandmapping = laExtDisputelandmappingDAO.findLaExtDisputelandmappingByLandId(usin);
				List<SourceDocument> sourcedocument = sourceDocumentDAO.findByGId(usin);
				LaExtTransactiondetail transobj =null;
				if(socialtenurerelationship.size()>0 && flag.equalsIgnoreCase("P")){
					
						
				 transobj = transactiondao
						.getLaExtTransactiondetailByLandid(socialtenurerelationship
								.get(0).getLaExtTransactiondetail()
								.getTransactionid());
				 document.setLaSpatialunitLand(usin);
					document.setDocumentlocation("/storage/emulated/0/MAST/multimedia/Parcel_Media");
					document.setLaExtTransactiondetail(transobj);
					
					document.setLaParty(lapartydao
							.getPartyIdByID(socialtenurerelationship.get(0)
									.getPartyid()));
					
					if(null!=sourcedocument  && sourcedocument.size() >0 ){
						for(int j=0; j< sourcedocument.size(); j++){
						if(null!=sourcedocument.get(j) ){
							document.setLaParty(lapartydao
									.getPartyIdByID(socialtenurerelationship.get(j+1)
											.getPartyid()));
						}
						}
					}
				
					mediaId = media.getString(2);
				}
				else if(disputelandmapping.size() > 0 && flag.equalsIgnoreCase("P")){
					 transobj = transactiondao
							.getLaExtTransactiondetailByLandid(disputelandmapping
									.get(0).getLaExtTransactiondetail()
									.getTransactionid());
					 document.setLaSpatialunitLand(usin);
						document.setDocumentlocation("/storage/emulated/0/MAST/multimedia/Parcel_Media");
						document.setLaExtTransactiondetail(transobj);
						document.setLaParty(lapartydao
								.getPartyIdByID(disputelandmapping.get(0)
										.getPartyid()));
						
						if(null!=sourcedocument  && sourcedocument.size() >0 ){
							for(int j=0; j< sourcedocument.size(); j++){
							if(null!=sourcedocument.get(j) ){
						document.setLaParty(lapartydao
								.getPartyIdByID(disputelandmapping.get(j+1)
										.getPartyid()));
							
							}
							/*else if(null != sourcedocument.get(j)){
								document.setLaParty(lapartydao
										.getPartyIdByID(disputelandmapping.get(j+1)
												.getPartyid()));
									}
							else if(null != sourcedocument.get(j)){
								document.setLaParty(lapartydao
										.getPartyIdByID(disputelandmapping.get(j+1)
												.getPartyid()));
									}
							else if(null != sourcedocument.get(j)){
								document.setLaParty(lapartydao
										.getPartyIdByID(disputelandmapping.get(j+1)
												.getPartyid()));
									}
							else if(null != sourcedocument.get(j)){
								document.setLaParty(lapartydao
										.getPartyIdByID(disputelandmapping.get(j+1)
												.getPartyid()));
									}
							else if(null != sourcedocument.get(j)){
								document.setLaParty(lapartydao
										.getPartyIdByID(disputelandmapping.get(j+1)
												.getPartyid()));
									}*/
						}
					
				}
				
				// document.setLaSpatialunitLand(laSpatialunitLand.setLandid(usin));
				

				

				mediaId = media.getString(2);
				// document.setMediaType(media.getString(4));
				
				

			}
				else if(socialtenurerelationship.size()>0 && flag.equalsIgnoreCase("M")){
					
						
				 transobj = transactiondao
						.getLaExtTransactiondetailByLandid(socialtenurerelationship
								.get(0).getLaExtTransactiondetail()
								.getTransactionid());
				 document.setLaSpatialunitLand(usin);
					document.setDocumentlocation("/storage/emulated/0/MAST/multimedia/Parcel_Media");
					document.setLaExtTransactiondetail(transobj);
					
				
					mediaId = media.getString(2);
				}
				else if(disputelandmapping.size() > 0 && flag.equalsIgnoreCase("M")){
					 transobj = transactiondao
							.getLaExtTransactiondetailByLandid(disputelandmapping
									.get(0).getLaExtTransactiondetail()
									.getTransactionid());
					 document.setLaSpatialunitLand(usin);
						document.setDocumentlocation("/storage/emulated/0/MAST/multimedia/Parcel_Media");
						document.setLaExtTransactiondetail(transobj);
						mediaId = media.getString(2);
		
			}
				
				else if(flag.equalsIgnoreCase("R")){
					resdoc.setLaSpatialunitLand(usin);
					resdoc.setDocumentlocation("/storage/emulated/0/MAST/multimedia/Resource_Media");
					mediaId = media.getString(2);
//					resdoc.setLaExtTransactiondetail(99999);
//					resdoc.setLaParty(99999);
				}
			
			document.setIsactive(true);
			// document.setRecordation(new SimpleDateFormat("dd/MM/yyyy")
			// .parse(new SimpleDateFormat("dd/MM/yyyy")
			// .format(new Date())));
			}
			return mediaId;

		} catch (JSONException pex) {
			logger.error("Exception", pex);
			System.out.println("Exception in setting data in SOURCE DOCUMNET: "
					+ pex);
		}
		return null;
	}

}
