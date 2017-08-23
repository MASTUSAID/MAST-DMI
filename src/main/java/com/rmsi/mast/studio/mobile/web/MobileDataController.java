package com.rmsi.mast.studio.mobile.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
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

import com.ibm.icu.text.SimpleDateFormat;
import com.rmsi.mast.studio.domain.AttributeMaster;
import com.rmsi.mast.studio.domain.AttributeValues;
import com.rmsi.mast.studio.domain.NaturalPerson;
import com.rmsi.mast.studio.domain.NonNaturalPerson;
import com.rmsi.mast.studio.domain.ProjectSpatialData;
import com.rmsi.mast.studio.domain.SocialTenureRelationship;
import com.rmsi.mast.studio.domain.SourceDocument;
import com.rmsi.mast.studio.domain.SpatialUnit;
import com.rmsi.mast.studio.domain.SpatialUnitPersonWithInterest;
import com.rmsi.mast.studio.domain.User;
import com.rmsi.mast.studio.domain.fetch.SpatialunitDeceasedPerson;
import com.rmsi.mast.studio.mobile.service.PersonService;
import com.rmsi.mast.studio.mobile.service.ProjectService;
import com.rmsi.mast.studio.mobile.service.SpatialDataService;
import com.rmsi.mast.studio.mobile.service.SpatialUnitService;
import com.rmsi.mast.studio.mobile.service.SurveyProjectAttributeService;
import com.rmsi.mast.studio.mobile.service.UserDataService;
import com.rmsi.mast.studio.service.UserService;
import com.rmsi.mast.studio.util.GeometryConversion;

/**
 * @author Prashant.Nigam
 */
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

	private SpatialUnit spatialUnit;

	private NaturalPerson naturalPerson;

	private NonNaturalPerson nonNaturalPerson;

	private SocialTenureRelationship socialTenure;

	private AttributeValues attributeValues;

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
	@RequestMapping(value = "sync/mobile/user/download/configuration/{userId}", method = RequestMethod.POST)
	public Map<String, Object> downloadConfigurationByProjectId(
			@PathVariable int userId, HttpServletRequest request,
			HttpServletResponse response) {

		String projectId = mobileUserService.getDefaultProjectByUserId(userId);

		if (projectId != null) {
			Map<String, Object> configurationData = new ConcurrentHashMap<String, Object>();

			try {

				// Add Village Name to Map
				configurationData.put("Village",
						projectService.getProjectArea(projectId).getVillage());

				// Add Attributes to the map
				configurationData.put("Attributes", surveyProjectAttribute
						.getSurveyAttributesByProjectId(projectId));

				// Add Project Spatial Data
				configurationData.put("SpatialData", spatialDataService
						.getProjectSpatialDataByProjectId(projectId));

				// Add List of Adjudicator
				configurationData.put("Adjudicator", surveyProjectAttribute
						.getProjectAdjudicatorByProjectId(projectId));

				// Add List of Hamlet
				configurationData.put("Hamlet", surveyProjectAttribute
						.getProjectHamletsByProjectId(projectId));

				// Return map containing Attributes and SpatialUnit for Download
				// Configuration
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

		return surveyProjectAttribute.getSurveyAttributesByProjectId(request
				.getParameter("projectId"));
	}

	/**
	 * It will enable the user to download data based on userId
	 * 
	 * @param userId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/sync/mobile/project/attributeValues/{userId}", method = RequestMethod.POST)
	public Map<Long, List<List<Object>>> getProjectAttributeValues(
			@PathVariable int userId, HttpServletRequest request,
			HttpServletResponse response) {

		// Get default project by userId
		String projectId = mobileUserService.getDefaultProjectByUserId(userId);

		try {

			return surveyProjectAttribute.getSurveyAttributeValuesByProjectId(
					projectId, 4);

		} catch (Exception ex) {
			logger.error("Exception while DOWNLOADING Attribute Values", ex);
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

			SourceDocument sourceDocument = new SourceDocument();

			Iterator<String> file = request.getFileNames();

			String filename = request.getParameter("fileattribs");

			String mediaId = null;

			while (file.hasNext()) {
				String fileName = file.next();

				System.out.println("FILE NAME:::: " + fileName);

				MultipartFile mpFile = request.getFile(fileName);

				String originalFileName = mpFile.getOriginalFilename();

				sourceDocument.setActive(true);

				mediaId = saveSourceDocumentAttributes(sourceDocument, filename);

				if (originalFileName != ""
						&& mobileUserService.findMultimedia(originalFileName,
								sourceDocument.getUsin()) == null) {

					/** Setting File Name */
					sourceDocument.setLocScannedSourceDoc("resources"
							+ "/"
							+ "documents"
							+ "/"
							+ spatialUnitService.getSpatialUnitByUsin(
									sourceDocument.getUsin()).getProject()
							+ "/" + "multimedia");

					/** Creating documents directory to store file */
					File documentsDir = new File(request.getServletContext()
							.getRealPath(
									sourceDocument.getLocScannedSourceDoc()).replace("mast", ""));

					if (!documentsDir.exists()) {
						documentsDir.mkdirs();
					}

					/** Setting File Name */
					sourceDocument.setScanedSourceDoc(originalFileName);

					/** Add data to Source Document */

					mobileUserService.uploadMultimedia(sourceDocument, mpFile,
							documentsDir);

				}
			}

			return mediaId;

		} catch (Exception e) {
			logger.error("Exception", e);
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

		List<NaturalPerson> naturalPersonList = new ArrayList<NaturalPerson>();
		List<NonNaturalPerson> nonNaturalPersonList = new ArrayList<NonNaturalPerson>();
		List<SocialTenureRelationship> socialTenureList = new ArrayList<SocialTenureRelationship>();
		List<SpatialUnitPersonWithInterest> nextOfKinList = new ArrayList<SpatialUnitPersonWithInterest>();
		List<SpatialunitDeceasedPerson> deceasedPersonList= new ArrayList<SpatialunitDeceasedPerson>();

		SpatialUnitPersonWithInterest nextOfKin;
		SpatialunitDeceasedPerson deceasePerson;

		Map<String, String> result = new IdentityHashMap<String, String>();

		try {

			// Main Array
			JSONArray attributesArray = new JSONArray(
					request.getParameter("syncData"));

			for (int i = 0; i < attributesArray.length(); i++) {

				// SurveyProject Object
				JSONObject surveyProject = attributesArray.getJSONObject(i);

				spatialUnit = new SpatialUnit();
				naturalPerson = new NaturalPerson();
				nonNaturalPerson = new NonNaturalPerson();
				socialTenure = new SocialTenureRelationship();
		
				if (surveyProject.has("SpatialFeatures")) {
					// SpatialFeature Array
					JSONArray spatialFeature = surveyProject
							.getJSONArray("SpatialFeatures");

					if (spatialFeature.length() > 0) {

						GeometryConversion geometryConversion = new GeometryConversion();

						// Saving FeatureId of mobile
						featureId = spatialFeature.get(0).toString();

						// Setting Geometry Type
						spatialUnit.setGtype(spatialFeature.get(1).toString());

						// Setting Coordinates based on gType
						if (spatialUnit.getGtype().equalsIgnoreCase("point")) {

							spatialUnit.setPoint(geometryConversion
									.convertWktToPoint(spatialFeature.get(2)
											.toString()));
							spatialUnit.getPoint().setSRID(4326);
							spatialUnit.setTheGeom(spatialUnit.getPoint());
						} else if (spatialUnit.getGtype().equalsIgnoreCase(
								"line")) {
							spatialUnit.setLine(geometryConversion
									.convertWktToLineString(spatialFeature.get(
											2).toString()));
							spatialUnit.getLine().setSRID(4326);
							spatialUnit.setTheGeom(spatialUnit.getLine());
						} else if (spatialUnit.getGtype().equalsIgnoreCase(
								"polygon")) {
							spatialUnit.setPolygon(geometryConversion
									.convertWktToPolygon(spatialFeature.get(2)
											.toString()));
							spatialUnit.setArea(spatialUnit.getPolygon()
									.getArea());
							// spatialUnit.setArea(geometryConversion.getArea(spatialUnit.getPolygon()));
							spatialUnit.getPolygon().setSRID(4326);
							spatialUnit.setPerimeter((float) spatialUnit
									.getPolygon().getLength());
							spatialUnit.setTheGeom(spatialUnit.getPolygon());
						}

						spatialUnit.getTheGeom().setSRID(4326);

						// Setting "Survey Date"
						spatialUnit.setSurveyDate(new SimpleDateFormat(
								"dd-MM-yyyy HH:mm:ss a").parse(spatialFeature
								.get(3).toString()));

						// Setting update time
						spatialUnit.setStatusUpdateTime(new SimpleDateFormat(
								"dd-MM-yyyy HH:mm:ss a").parse(spatialFeature
								.get(4).toString()));

						// Setting IMEI
						spatialUnit.setImeiNumber(spatialFeature.getString(5));

						// Setting PersonType
						if (spatialFeature.getString(6).equalsIgnoreCase(
								"Natural")) {
							naturalPerson.setPerson_type_gid(personService
									.getPersonTypeById(1));
						} else if (spatialFeature.getString(6)
								.equalsIgnoreCase("Non-Natural")) {
							nonNaturalPerson.setPerson_type_gid(personService
									.getPersonTypeById(2));
							naturalPerson.setPerson_type_gid(personService
									.getPersonTypeById(1));
						}

						// Setting Project
						spatialUnit.setProject(spatialFeature.getString(7)
								.toString());

						// Setting User id
						spatialUnit.setUserid(Integer.parseInt(spatialFeature
								.getString(8)));

						// Setting Hamlet Id
						spatialUnit.setHamletId(Integer.parseInt(spatialFeature
								.getString(9)));

						// Setting Witness1
						spatialUnit.setWitness1(spatialFeature.getString(10));

						// Setting Witness2
						spatialUnit.setWitness2(spatialFeature.getString(11));
						
						// Setting active=true
						spatialUnit.setActive(true);
					}
				}

				if (surveyProject.has("NextOfKin")) {

					// NextOfKin Array
					JSONArray nextOfKinArr = surveyProject
							.getJSONArray("NextOfKin");

					if (nextOfKinArr.length() > 0) {

						for (int k = 0; k < nextOfKinArr.length(); k++) {
							nextOfKin = new SpatialUnitPersonWithInterest();
							nextOfKin.setPerson_name(nextOfKinArr.getJSONArray(k).getString(0));
							nextOfKinList.add(nextOfKin);
						}
					}
				}
				
				if (surveyProject.has("DeceasedPerson")) {

					// NextOfKin Array
					JSONArray deceasedPersonArr = surveyProject
							.getJSONArray("DeceasedPerson");

					if (deceasedPersonArr.length() > 0) {

						for (int k = 0; k < deceasedPersonArr.length(); k++) {
							JSONArray deceasedArr = deceasedPersonArr.getJSONArray(k);
							deceasePerson=new SpatialunitDeceasedPerson();
							deceasePerson.setFirstname(deceasedArr.getString(0));
							deceasePerson.setMiddlename(deceasedArr.getString(1));
							deceasePerson.setLastname(deceasedArr.getString(2));
							
							deceasedPersonList.add(deceasePerson);
						}
					}
				}
				
				if (surveyProject.has("AttributeValue")) {

					// AttributeValue Array
					JSONArray attributeValue = surveyProject
							.getJSONArray("AttributeValue");

					// Get General Array
					JSONArray general = attributeValue.getJSONArray(0);

					if (general.length() > 0) {

						attributeCategoryType = "general";

						for (int k = 0; k < general.length(); k++) {

							JSONArray generalArray = general.getJSONArray(k);

							setAttributes(generalArray.getString(0),
									generalArray.getString(1),
									generalArray.getInt(2),
									generalArray.getString(3));
						}
					}

					// Get Natural Array
					JSONArray natural = attributeValue.getJSONArray(1);

					if (natural.length() > 0) {

						attributeCategoryType = "natural";

						for (int k = 0; k < natural.length(); k++) {

							JSONArray naturalArray = natural.getJSONArray(k);

							if (!prevGroupId.equalsIgnoreCase(naturalArray
									.getString(1)) && !prevGroupId.isEmpty()) {
								naturalPersonList.add(naturalPerson);

								System.out.println("Natural List: "
										+ naturalPersonList);
								naturalListByGId.add(naturalList);
								prevGroupId = "";

								naturalPerson = new NaturalPerson();
								naturalList = new ArrayList<AttributeValues>();
								naturalPerson.setPerson_type_gid(personService
										.getPersonTypeById(1));
							}

							setAttributes(naturalArray.getString(0),
									naturalArray.getString(1),
									naturalArray.getInt(2),
									naturalArray.getString(3));

						}
						naturalPersonList.add(naturalPerson);
						System.out
								.println("Natural List: " + naturalPersonList);
						naturalListByGId.add(naturalList);
						prevGroupId = "";
						naturalPerson = new NaturalPerson();
						naturalList = new ArrayList<AttributeValues>();
					}

					// Get Non-Natural Array
					JSONArray nonNatural = attributeValue.getJSONArray(2);

					if (nonNatural.length() > 0
							&& (nonNaturalPerson.getPerson_type_gid() != null)) {

						attributeCategoryType = "nonnatural";

						for (int k = 0; k < nonNatural.length(); k++) {
							JSONArray nonNaturalArray = nonNatural
									.getJSONArray(k);

							setAttributes(nonNaturalArray.getString(0),
									nonNaturalArray.getString(1),
									nonNaturalArray.getInt(2),
									nonNaturalArray.getString(3));

						}
						
						nonNaturalPersonList.add(nonNaturalPerson);
						System.out.println("Non Natural List: "
								+ nonNaturalPersonList);
						nonNaturalListByGId.add(nonNaturalList);
						prevGroupId = "";
						nonNaturalPerson = new NonNaturalPerson();
					}

					// Get Tenure Array
					JSONArray tenure = attributeValue.getJSONArray(3);

					if (tenure.length() > 0) {

						attributeCategoryType = "tenure";

						for (int k = 0; k < tenure.length(); k++) {

							JSONArray tenureArray = tenure.getJSONArray(k);

							if (!prevGroupId.equalsIgnoreCase(tenureArray
									.getString(1)) && !prevGroupId.isEmpty()) {
								socialTenureList.add(socialTenure);

								System.out
										.println("Tenure List: " + tenureList);

								tenureListByGId.add(tenureList);
								tenureList = new ArrayList<AttributeValues>();
								prevGroupId = "";

								socialTenure = new SocialTenureRelationship();
							}

							setAttributes(tenureArray.getString(0),
									tenureArray.getString(1),
									tenureArray.getInt(2),
									tenureArray.getString(3));

						}
						socialTenureList.add(socialTenure);

						System.out.println("Tenure List: " + tenureList);
						prevGroupId = "";

						tenureListByGId.add(tenureList);

						socialTenure = new SocialTenureRelationship();
						tenureList = new ArrayList<AttributeValues>();

					}

					// Get Custom Array
					JSONArray custom = attributeValue.getJSONArray(4);

					if (custom.length() > 0) {

						attributeCategoryType = "custom";

						AttributeValues attributeValues = new AttributeValues();

						for (int k = 0; k < custom.length(); k++) {

							JSONArray customArray = custom.getJSONArray(k);

							if (!prevGroupId.equalsIgnoreCase(customArray
									.getString(1)) && !prevGroupId.isEmpty()) {
								attributeValuesList.add(attributeValues);

								System.out.println("Attribute Values List: "
										+ attributeValuesList);

								prevGroupId = "";
							}

							setAttributes(customArray.getString(0),
									customArray.getString(1),
									customArray.getInt(2),
									customArray.getString(3));
						}
					}
				}
				addAttributeValues();
				result.put(
						featureId,
						mobileUserService.syncSurveyProjectData(spatialUnit,
								deceasedPersonList,nextOfKinList, naturalPersonList,
								nonNaturalPersonList, socialTenureList,
								attributeValuesList, attributeValuesMap)
								.toString());
				System.out.println("Data Persisted Successfully");

				/** Clearing all lists and map **/

				// Clearing all local lists
				naturalPersonList.clear();
				nonNaturalPersonList.clear();
				socialTenureList.clear();
				nextOfKinList.clear();
				deceasedPersonList.clear();

				// Clear all static lists and map
				clearAll();

			}
			return result;
		} catch (JSONException jsex) {
			logger.error("Exception", jsex);
			result.put("Exception", jsex.toString());
			jsex.printStackTrace();
		} catch (Exception e) {
			logger.error("Exception", e);
			result.put("Exception at feature Id: " + featureId, e.toString());
			e.printStackTrace();
		} finally {

			/** Clearing all lists and map **/

			// Clearing all local lists
			naturalPersonList.removeAll(Collections.singleton(naturalPerson));
			nonNaturalPersonList.removeAll(Collections
					.singleton(nonNaturalPerson));
			socialTenureList.removeAll(Collections.singleton(socialTenure));

			// Clear all static lists and map
			clearAll();
		}
		return result;
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

	@RequestMapping(value = "/sync/mobile/download/FinalDataSet/{userId}", method = RequestMethod.POST)
	public Map<Long, List<List<Object>>> downloadFinalDataset(
			@PathVariable int userId, HttpServletRequest request,
			HttpServletResponse response) {

		// Get default project by userId
		String projectId = mobileUserService.getDefaultProjectByUserId(userId);

		try {

			return surveyProjectAttribute.getSurveyAttributeValuesByProjectId(
					projectId, 7);

		} catch (Exception ex) {
			logger.error("Exception while DOWNLOADING FINAL DATASET", ex);
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

		if (projectSpatialData.getFileLocation().endsWith("/")) {
			// if FileLocation ends with "/"
			return projectSpatialData.getFileLocation() + mbTiles + "."
					+ projectSpatialData.getFileExtension();
		} else {
			// if FileLocation doesn't ends with "/"
			return projectSpatialData.getFileLocation() + "/" + mbTiles + "."
					+ projectSpatialData.getFileExtension();
		}
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

	/**
	 * Setting the attributes according to category in their specific tables
	 * 
	 * @param attributeFeatureId
	 * @param currentGroupId
	 * @param attributeId
	 * @param value
	 */
	private void setAttributes(String attributeFeatureId,
			String currentGroupId, int attributeId, String value) {

		try {

			if (featureId.equals(attributeFeatureId)) {

				if (attributeCategoryType.equalsIgnoreCase("general")) {

					if (attributeId == 9) {
						spatialUnit.setProposedUse(spatialUnitService
								.getLandUseTypeById(Integer.parseInt(value)));
					} else if (attributeId == 15) {
						spatialUnit.setHousehidno(Integer.parseInt(value));
					} else if (attributeId == 16) {
						spatialUnit.setExistingUse(spatialUnitService
								.getLandUseTypeById(Integer.parseInt(value)));
					} else if (attributeId == 17) {
						spatialUnit.setComments(value);
					} else if (attributeId == 28) {
						spatialUnit.setLandOwner(value);
					} else if (attributeId == 34) {
						spatialUnit.setAddress1(value);
					} else if (attributeId == 35) {
						spatialUnit.setAddress2(value);
					} else if (attributeId == 36) {
						spatialUnit.setPostal_code(value);
					} else if (attributeId == 37) {
						spatialUnit.setTypeName(spatialUnitService
								.getLandTypeById(Integer.parseInt(value)));
					} else if (attributeId == 38) {
						spatialUnit.setSoilQuality(spatialUnitService
								.getSoilQualityValuesById(Integer
										.parseInt(value)));
					} else if (attributeId == 39) {
						spatialUnit.setSlope(spatialUnitService
								.getSlopeValuesById(Integer.parseInt(value)));
					} else if (attributeId == 44) {
						spatialUnit.setNeighborNorth(value);
					} else if (attributeId == 45) {
						spatialUnit.setNeighborSouth(value);
					} else if (attributeId == 46) {
						spatialUnit.setNeighborEast(value);
					} else if (attributeId == 47) {
						spatialUnit.setNeighborWest(value);
					} else if (attributeId == 53) {
						spatialUnit.setOtherUseType(value);
					}
				} else if (attributeCategoryType.equalsIgnoreCase("natural")) {

					if (prevGroupId.isEmpty()
							|| prevGroupId.equalsIgnoreCase(currentGroupId)) {

						if (prevGroupId.isEmpty()) {
							prevGroupId = currentGroupId;
							naturalPerson.setMobileGroupId(currentGroupId);
						}
						naturalPerson.setAlias("");
						if (attributeId == 1) {
							naturalPerson.setFirstName(value);
						} else if (attributeId == 2) {
							naturalPerson.setLastName(value);
						} else if (attributeId == 3) {
							naturalPerson.setMiddleName(value);
						} else if (attributeId == 29) {
							naturalPerson.setAlias(value);
						} else if (attributeId == 4) {
							naturalPerson.setGender(spatialUnitService
									.getGenderById(Long.parseLong(value)));
						} else if (attributeId == 5) {
							naturalPerson.setMobile(value);
						} else if (attributeId == 30) {
							naturalPerson.setIdentity(value);
						} else if (attributeId == 21) {
							naturalPerson.setAge(Integer.parseInt(value));
						} else if (attributeId == 19) {
							naturalPerson.setOccupation(value);
						} else if (attributeId == 20) {
							naturalPerson.setEducation(personService
									.getEducationLevelById(Integer
											.parseInt(value)));
						} else if (attributeId == 25) {
							naturalPerson.setTenure_Relation(value);
						} else if (attributeId == 26) {
							naturalPerson.setHouseholdRelation(value);
						} else if (attributeId == 27) {
							naturalPerson.setWitness(value);
						} else if (attributeId == 22) {
							naturalPerson
									.setMarital_status(spatialUnitService
											.getMartitalStatus(Integer
													.parseInt(value)));
						} else if (attributeId == 40) {
							if (value.equalsIgnoreCase("yes")) {
								value = "true";
							} else {
								value = "false";
							}
							naturalPerson.setOwner(Boolean.valueOf(value));
						} else if (attributeId == 41) {
							naturalPerson.setAdministator(value);
						} else if (attributeId == 42) {
							/*naturalPerson.setCitizenship(value);*/
							naturalPerson
							.setCitizenship_id(spatialUnitService
									.getCitizenship(Integer.parseInt(value)));
						} else if (attributeId == 43) {
							if (value.equalsIgnoreCase("yes")) {
								value = "true";
							} else {
								value = "false";
							}
							naturalPerson.setResident_of_village(Boolean
									.valueOf(value));
						} 
						
						// parse in long instead of Int 25-Sep
						else if (attributeId == 54) {
							
							try {
								if(value.equals("10"))
									naturalPerson
									.setPersonSubType(personService
											.getPersonTypeById(3l));
								
								else if(value.equals("11"))
									naturalPerson
									.setPersonSubType(personService
											.getPersonTypeById(4l));
								
								else if(value.equals("12"))
									naturalPerson
									.setPersonSubType(personService
											.getPersonTypeById(5l));
								
								else
								naturalPerson
										.setPersonSubType(personService
												.getPersonTypeById(Long.parseLong(value)));
							} catch (Exception e) {
								logger.error(e);
							}
							
						/*	else if(value.equals("11"))
								naturalPerson
										.setPersonSubType(personService
												.getPersonTypeById(4l));
								
							else if(value.equals("12"))
								naturalPerson
										.setPersonSubType(personService
												.getPersonTypeById(5l));
								*/
						}
					}
				} else if (attributeCategoryType.equalsIgnoreCase("nonnatural")
						&& !currentGroupId.equals("null")) {
					if (prevGroupId.isEmpty()
							|| prevGroupId.equalsIgnoreCase(currentGroupId)) {

						if (prevGroupId.isEmpty()) {
							prevGroupId = currentGroupId;

							nonNaturalPerson.setMobileGroupId(currentGroupId);
						}

						if (attributeId == 6) {
							nonNaturalPerson.setInstitutionName(value);
						} else if (attributeId == 7) {
							nonNaturalPerson.setAddress(value);
						} else if (attributeId == 8) {
							nonNaturalPerson.setPhoneNumber(value);
						} else if (attributeId == 52) {
							nonNaturalPerson.setGroupType(spatialUnitService
									.getGroupTypeById(Integer.parseInt(value)));
						}
					}
				} else if (attributeCategoryType.equalsIgnoreCase("tenure")) {
					if (prevGroupId.isEmpty()
							|| prevGroupId.equalsIgnoreCase(currentGroupId)) {

						if (prevGroupId.isEmpty()) {
							prevGroupId = currentGroupId;
						}

						socialTenure.setIsActive(true);

						if (attributeId == 31) {
							socialTenure.setShare_type(spatialUnitService
									.getShareTypeById(Integer.parseInt(value)));
						} /*
						 * else if (attributeId == 12) {
						 * socialTenure.setShare(Integer.parseInt(value)); }
						 */else if (attributeId == 24) {
							socialTenure
									.setOccupancy_type_id(spatialUnitService
											.getOccupancyTypeById(Integer
													.parseInt(value)));
						} else if (attributeId == 23) {
							socialTenure
									.setTenureclass_id(spatialUnitService
											.getTenureClassById(Integer
													.parseInt(value)));
							
							// updated on 20-Oct as resident introduced for tenure class
							if(value.equals("30"))
							socialTenure.setResident(false);
							else if(value.equals("29"))
								socialTenure.setResident(true);
						} 
						
						else if (attributeId == 55) {
							if (value.equalsIgnoreCase("Yes")) {
								value = "true";
								socialTenure.setResident(true);
							} else {
								value = "false";
								socialTenure.setResident(false);
							}
							
						}
						else if (attributeId == 32) {
							try {
								socialTenure
										.setSocial_tenure_startdate(new SimpleDateFormat(
												"dd/MM/yyyy").parse(value
												.trim()));
							} catch (java.text.ParseException e) {
								e.printStackTrace();
							}
						} else if (attributeId == 33) {
							try {
								socialTenure
										.setSocial_tenure_enddate(new SimpleDateFormat(
												"dd/MM/yyyy").parse(value
												.trim()));
							} catch (java.text.ParseException e) {
								e.printStackTrace();
							}
						} else if (attributeId == 13) {
							socialTenure.setTenure_duration(Float
									.parseFloat(value));
						}
					}
				}

				// Persisting data in AttributesValue
				if (!value.equals("null")) {
					if(attributeId!=54)
					addAllAttributeValues(value, attributeId);
				}
			}
		} catch (Exception ex) {
			logger.error("Exception", ex);
			throw ex;
		}
	}

	/**
	 * Persists data in AttributeValues and adds it to the list based on the
	 * category
	 * 
	 * @param value
	 * @param attributeId
	 */
	private void addAllAttributeValues(String value, int attributeId) {

		attributeValues = new AttributeValues();

		try {
			// Setting Data in AttributeValues entity
			attributeValues.setValue(value);
			// attributeValues.setAttributevalueid((long) attributeId);
			attributeValues.setUid(surveyProjectAttribute
					.getSurveyProjectAttributeId((long) attributeId,
							spatialUnit.getProject()));

			if (attributeCategoryType.equalsIgnoreCase("general")
					|| attributeCategoryType.equalsIgnoreCase("custom")) {

				attributeValuesList.add(attributeValues);

			} else if (attributeCategoryType.equalsIgnoreCase("natural")) {

				naturalList.add(attributeValues);

			} else if (attributeCategoryType.equalsIgnoreCase("nonnatural")) {

				nonNaturalList.add(attributeValues);

			} else if (attributeCategoryType.equalsIgnoreCase("tenure")) {

				tenureList.add(attributeValues);
			}
		} catch (Exception ex) {
			logger.error("Exception", ex);
			System.out.println("Attribute ID::::" + attributeId);
			throw ex;
		}

	}

	/**
	 * Adds lists into HashMap
	 */
	private void addAttributeValues() {

		/**
		 * If natural person list is not empty than add it to the concurrent map
		 */
		if (naturalListByGId.size() > 0) {
			attributeValuesMap.put("NaturalPerson", naturalListByGId);
		}

		/**
		 * If non natural person list is not empty than add it to the concurrent
		 * map
		 */
		if (nonNaturalListByGId.size() > 0) {
			attributeValuesMap.put("NonNaturalPerson", nonNaturalListByGId);
		}

		attributeValuesMap.put("SocialTenure", tenureListByGId);
	}

	/**
	 * Clears all data in list and HashMap
	 */
	private void clearAll() {

		// Clears attribute value list
		attributeValuesList.clear();

		// Clears person List
		naturalList.clear();

		nonNaturalList.clear();

		// Clears tenure list
		tenureList.clear();

		// Clears natural list by gid
		naturalListByGId.clear();

		// Clears non natural list by gid
		nonNaturalListByGId.clear();

		// Clears tenure List by GId
		tenureListByGId.clear();

		// Clears attribute values Hash Map
		attributeValuesMap.clear();
	}

	private String saveSourceDocumentAttributes(SourceDocument sourceDocument,
			String attributes) {

		try {
			String mediaId = null;

			JSONArray sourceDocumentAttribute = new JSONArray(attributes);

			JSONArray gid = sourceDocumentAttribute.getJSONArray(0);

			if (gid.length() > 0) {

				sourceDocument.setUsin(Long.parseLong(gid.get(0).toString()));

				if (!gid.get(1).toString().isEmpty()) {
					sourceDocument.setPerson_gid(mobileUserService
							.findPersonByMobileGroupIdandUsin(gid.get(1)
									.toString(), sourceDocument.getUsin()));
				} else {
					JSONArray attributeValueArray = sourceDocumentAttribute
							.getJSONArray(1);

					for (int j = 0; j < attributeValueArray.length(); j++) {

						JSONArray values = attributeValueArray.getJSONArray(j);

						int attributeId = Integer.parseInt(values.getString(0));

						if (attributeId == 10) {
							sourceDocument
									.setComments(values.get(1).toString());
						}
						if (attributeId == 11) {
							sourceDocument.setEntity_name(values.getString(1)
									.toString());
						}
					}
				}

				mediaId = gid.getString(2);

				sourceDocument.setMediaType(gid.getString(4));
			}

			sourceDocument.setActive(true);

			sourceDocument.setRecordation(new SimpleDateFormat("dd/MM/yyyy")
					.parse(new SimpleDateFormat("dd/MM/yyyy")
							.format(new Date())));

			return mediaId;

		} catch (ParseException | JSONException pex) {
			logger.error("Exception", pex);
			System.out.println("Exception in setting data in SOURCE DOCUMNET: "
					+ pex);
		}
		return null;
	}
}