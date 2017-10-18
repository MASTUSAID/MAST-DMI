package com.rmsi.mast.studio.mobile.web;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
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
import com.rmsi.mast.studio.domain.AttributeMaster;
import com.rmsi.mast.studio.domain.AttributeValues;
import com.rmsi.mast.studio.domain.Dispute;
import com.rmsi.mast.studio.domain.Project;
import com.rmsi.mast.studio.domain.ProjectSpatialData;
import com.rmsi.mast.studio.domain.SourceDocument;
import com.rmsi.mast.studio.domain.SpatialUnit;
import com.rmsi.mast.studio.domain.User;
import com.rmsi.mast.studio.mobile.service.PersonService;
import com.rmsi.mast.studio.mobile.service.ProjectService;
import com.rmsi.mast.studio.mobile.service.SpatialDataService;
import com.rmsi.mast.studio.mobile.service.SpatialUnitService;
import com.rmsi.mast.studio.mobile.service.SurveyProjectAttributeService;
import com.rmsi.mast.studio.mobile.service.UserDataService;
import com.rmsi.mast.studio.mobile.transferobjects.Attribute;
import com.rmsi.mast.studio.mobile.transferobjects.Property;
import com.rmsi.mast.studio.service.UserService;
import com.rmsi.mast.studio.util.FileUtils;
import com.rmsi.mast.studio.util.StringUtils;
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

                // Add List of claim types
                configurationData.put("ClaimType", spatialUnitService.getClaimTypes());

                // Add List of relationship types
                configurationData.put("RelationshipType", personService.getRelationshipTypes());

                // Add List of share types
                configurationData.put("ShareType", spatialUnitService.getShareTypes());

                // Add List of right types
                configurationData.put("RightType", spatialUnitService.getTenureClasses());

                // Add List of genders
                configurationData.put("Genders", spatialUnitService.getGenders());

                // Add List of genders
                configurationData.put("DisputeType", spatialUnitService.getDisputeTypes());

                // Add List of genders
                configurationData.put("AcquisitionType", spatialUnitService.getAcquisitionTypes());

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
     * It will enable the user to download project properties based on userId
     *
     * @param userId
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/sync/mobile/project/getProperties/{userId}", method = RequestMethod.POST)
    public List<Property> getProjectProperties(
            @PathVariable int userId, HttpServletRequest request, HttpServletResponse response) {

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
    synchronized public String upload(MultipartHttpServletRequest request, HttpServletResponse response) {
        try {
            SourceDocument sourceDocument = new SourceDocument();
            Iterator<String> files = request.getFileNames();
            String attributes = request.getParameter("fileattribs");
            String mediaId = null;

            while (files.hasNext()) {
                String fileName = files.next();
                System.out.println("FILE NAME:::: " + fileName);
                MultipartFile mpFile = request.getFile(fileName);
                String originalFileName = mpFile.getOriginalFilename();
                sourceDocument.setActive(true);
                mediaId = setDocumentAttributes(sourceDocument, attributes);

                if (!"".equals(originalFileName) && mobileUserService.findMultimedia(originalFileName, sourceDocument.getUsin()) == null) {

                    // Setting File Name
                    sourceDocument.setLocScannedSourceDoc("resources"
                            + "/"
                            + "documents"
                            + "/"
                            + spatialUnitService.getSpatialUnitByUsin(sourceDocument.getUsin()).getProject()
                            + "/" + "multimedia");

                    // Creating documents directory to store file
                    File documentsDir = new File(FileUtils.getFielsFolder(request) + sourceDocument.getLocScannedSourceDoc());

                    if (!documentsDir.exists()) {
                        documentsDir.mkdirs();
                    }

                    // Setting File Name
                    sourceDocument.setScanedSourceDoc(originalFileName);

                    // Add data to Source Document
                    mobileUserService.uploadMultimedia(sourceDocument, mpFile, documentsDir);
                }
            }

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
    synchronized public Map<String, String> syncAttributes(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> result = new IdentityHashMap<String, String>();

        try {
            //BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
            String projectName = request.getParameter("projectName");
            int userId = Integer.parseInt(request.getParameter("userId"));
            String jsonString = request.getParameter("data");

            if (StringUtils.isEmpty(jsonString)) {
                return result;
            }

            // Transfor JSON to transfer object
            Gson gson = new Gson();
            Type type = new TypeToken<List<Property>>() {}.getType();
            List<Property> properties = gson.fromJson(jsonString, type);

            return mobileUserService.saveClaims(properties, projectName, userId);
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

    private String setDocumentAttributes(SourceDocument document, String attributes) {
        try {
            String mediaId = null;
            JSONArray sourceDocumentAttribute = new JSONArray(attributes);
            JSONArray media = sourceDocumentAttribute.getJSONArray(0);

            if (media.length() > 0) {
                long usin = Long.parseLong(media.get(0).toString());
                document.setUsin(usin);

                if (!media.get(1).toString().isEmpty()) {
                    document.setPerson_gid(mobileUserService.findPersonByMobileGroupId(media.get(1).toString(), usin));
                } else {
                    if (!media.get(5).toString().isEmpty() && Integer.parseInt(media.get(5).toString()) > 0) {
                        List<Dispute> disputes = disputeDao.findByPropId(usin);
                        if(disputes != null && disputes.size() > 0)
                            document.setDisputeId(disputes.get(0).getId());
                    }
                    
                    JSONArray attributeValueArray = sourceDocumentAttribute.getJSONArray(1);
                    for (int j = 0; j < attributeValueArray.length(); j++) {
                        JSONArray values = attributeValueArray.getJSONArray(j);
                        int attributeId = Integer.parseInt(values.getString(0));
                        if (attributeId == 10) {
                            document.setComments(values.get(1).toString());
                        }
                        if (attributeId == 11) {
                            document.setEntity_name(values.getString(1));
                        }
                        if (attributeId == 340) {
                            document.setDocumentType(documentTypeDao.getTypeByAttributeOptionId(Integer.parseInt(values.getString(1))));
                        }
                    }
                }

                mediaId = media.getString(2);
                document.setMediaType(media.getString(4));
            }

            document.setActive(true);
            document.setRecordation(new SimpleDateFormat("dd/MM/yyyy")
                    .parse(new SimpleDateFormat("dd/MM/yyyy")
                            .format(new Date())));

            return mediaId;

        } catch (ParseException | JSONException pex) {
            logger.error("Exception", pex);
            System.out.println("Exception in setting data in SOURCE DOCUMNET: " + pex);
        }
        return null;
    }
}
