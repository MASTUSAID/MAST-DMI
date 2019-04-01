package com.rmsi.mast.viewer.web.mvc;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.security.Principal;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rmsi.mast.custom.dto.ResourceAttributeValueDto;
import com.rmsi.mast.studio.dao.ProjectDAO;
import com.rmsi.mast.studio.dao.ProjectRegionDAO;
import com.rmsi.mast.studio.dao.RelationshipTypeDao;
import com.rmsi.mast.studio.dao.ResourceCustomAttributesDAO;
import com.rmsi.mast.studio.dao.UserDAO;
import com.rmsi.mast.studio.domain.AttributeMasterResourcePOI;
import com.rmsi.mast.studio.domain.AttributeOptions;
import com.rmsi.mast.studio.domain.Boundary;
import com.rmsi.mast.studio.domain.BoundaryFeatureType;
import com.rmsi.mast.studio.domain.BoundaryPoint;
import com.rmsi.mast.studio.domain.BoundaryPointDoc;
import com.rmsi.mast.studio.domain.ConfidenceLevel;
import com.rmsi.mast.studio.domain.CustomAttributes;
import com.rmsi.mast.studio.domain.Project;
import com.rmsi.mast.studio.domain.ProjectRegion;
import com.rmsi.mast.studio.domain.RelationshipType;
import com.rmsi.mast.studio.domain.ResourceAttributeValues;
import com.rmsi.mast.studio.domain.ResourcePOIAttributeValues;
import com.rmsi.mast.studio.domain.User;
import com.rmsi.mast.studio.domain.fetch.ResourceDetails;
import com.rmsi.mast.studio.mobile.dao.AttributeOptionsDao;
import com.rmsi.mast.studio.mobile.dao.BoundaryFeatureTypeDao;
import com.rmsi.mast.studio.mobile.dao.ConfidenceLevelDao;
import com.rmsi.mast.studio.mobile.dao.CustomAttributesDAO;
import com.rmsi.mast.studio.mobile.dao.ResourceAttributeValuesDAO;
import com.rmsi.mast.studio.mobile.dao.SpatialUnitResourceLineDao;
import com.rmsi.mast.viewer.service.BoundaryService;
import com.rmsi.mast.viewer.service.ResourceAttributeValuesService;
import java.util.Calendar;
import org.apache.log4j.Logger;

@Controller
public class LandResourceController {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    ResourceAttributeValuesService resourceAttributeValuesService;

    @Autowired
    ProjectDAO projectDAO;

    @Autowired
    ResourceAttributeValuesService resourceAttributeValuesservice;

    @Autowired
    AttributeOptionsDao attributeOptionsDao;

    @Autowired
    ResourceCustomAttributesDAO resourceCustomAttributesdao;

    @Autowired
    CustomAttributesDAO customAttributesdao;

    @Autowired
    UserDAO userdao;

    @Autowired
    SpatialUnitResourceLineDao lineDao;

    @Autowired
    ResourceAttributeValuesDAO resourceattributeValuesdao;

    @Autowired
    RelationshipTypeDao realtionshipdao;

    @Autowired
    BoundaryService boundaryService;

    @Autowired
    ProjectRegionDAO projectRegion;
    
    @Autowired
    ConfidenceLevelDao confidenceLevelDao;

    @Autowired
    BoundaryFeatureTypeDao boundaryFeatureTypeDao;
    
    private static final Logger logger = Logger.getLogger(LandResourceController.class);

    @RequestMapping(value = "/viewer/resource/allAttribue/{landid}/{projectId}", method = RequestMethod.GET)
    @ResponseBody
    public Map<Integer, List<ResourceAttributeValues>> getAllResourceAttribute(@PathVariable Integer projectId, @PathVariable Integer landid) {
        ResourceAttributeValueDto objDto = new ResourceAttributeValueDto();
        List<ResourceAttributeValues> lst = new ArrayList<ResourceAttributeValues>();
        lst = resourceAttributeValuesService.getResourceAttributeValuesBylandId(projectId, landid);
        Map<Integer, List<ResourceAttributeValues>> map = new HashMap<Integer, List<ResourceAttributeValues>>();
        int groupId = 0;
        List<ResourceAttributeValues> lstres = new ArrayList<ResourceAttributeValues>();
        for (ResourceAttributeValues obj : lst) {

            if (groupId != obj.getGroupid()) {
                groupId = obj.getGroupid();
                lstres = new ArrayList<ResourceAttributeValues>();
                lstres.add(obj);
                map.put(obj.getGroupid(), lstres);
            } else {
                lstres.add(obj);
            }
        }

        objDto.setMap(map);
        return map;
    }

    @RequestMapping(value = "/viewer/resource/allCustomAttribue/{landid}/{projectId}", method = RequestMethod.GET)
    @ResponseBody
    public List<CustomAttributes> getAllCustomResourceAttribute(@PathVariable Integer projectId, @PathVariable Integer landid) {
        ResourceAttributeValueDto objDto = new ResourceAttributeValueDto();
        List<CustomAttributes> lst = new ArrayList<>();
        lst = customAttributesdao.getResourceAttributeValuesBylandId(projectId, landid);

        return lst;
    }

    @RequestMapping(value = "/viewer/resource/allAttribtesDatatype/{landid}/{projectId}", method = RequestMethod.GET)
    @ResponseBody
    public List<Object[]> getAllResourceAttributesDatatype(@PathVariable Integer projectId, @PathVariable Integer landid) {
        ResourceAttributeValueDto objDto = new ResourceAttributeValueDto();
        List<Object[]> lst = new ArrayList<>();
        lst = resourceAttributeValuesService.getResourceAttributeValuesAndDatatypeBylandId(projectId, landid);

        return lst;
    }

    @RequestMapping(value = "/viewer/resource/allCustomAttribtesDatatype/{landid}/{projectId}", method = RequestMethod.GET)
    @ResponseBody
    public List<Object[]> getAllResourceCustomAttributesDatatype(@PathVariable Integer projectId, @PathVariable Integer landid) {

        List<Object[]> lst = new ArrayList<>();

        lst = resourceCustomAttributesdao.getResourceCustomAttributeValuesAndDatatypeBylandId(projectId, landid);

        return lst;
    }

    @RequestMapping(value = "/viewer/resource/allPoiDatatype/{landid}/{projectId}", method = RequestMethod.GET)
    @ResponseBody
    public List<Object[]> getAllResourcePoiDatatype(@PathVariable Integer projectId, @PathVariable Integer landid) {

        List<Object[]> lst = new ArrayList<>();

        lst = resourceCustomAttributesdao.getResourcePoiDatatypeBylandId(projectId, landid);

        return lst;
    }

    @RequestMapping(value = "/viewer/resource/PoiData/{landid}/{projectId}", method = RequestMethod.GET)
    @ResponseBody
    public Map<Integer, List<ResourcePOIAttributeValues>> getAllResourcePoiData(@PathVariable Integer projectId, @PathVariable Integer landid) {

        List<ResourcePOIAttributeValues> lst = resourceCustomAttributesdao.getResourcePoiDataBylandId(projectId, landid);

        Map<Integer, List<ResourcePOIAttributeValues>> map = new HashMap<Integer, List<ResourcePOIAttributeValues>>();
        int groupId = 0;
        List<ResourcePOIAttributeValues> lstres = null;
        for (ResourcePOIAttributeValues obj : lst) {

            if (groupId != obj.getGroupid()) {
                groupId = obj.getGroupid();
                lstres = new ArrayList<ResourcePOIAttributeValues>();
                lstres.add(obj);
                map.put(obj.getGroupid(), lstres);
            } else {
                lstres.add(obj);
            }
        }

        return map;
    }

    @RequestMapping(value = "/viewer/resource/getAllresouce/{project}/{startfrom}", method = RequestMethod.GET)
    @ResponseBody
    public List<ResourceDetails> getAllresouce(@PathVariable String project, @PathVariable Integer startfrom, Principal principal) {

        Integer projectId = 0;

        try {
            Project objproject = projectDAO.findByName(project);
            projectId = objproject.getProjectnameid();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resourceAttributeValuesService.getAllresouceByproject(projectId + "", startfrom);
    }

    @RequestMapping(value = "/viewer/resource/getAllresouceCount/{project}", method = RequestMethod.GET)
    @ResponseBody
    public Integer getAllresouceCount(@PathVariable String project) {

        Integer projectId = 0;

        try {
            Project objproject = projectDAO.findByName(project);
            projectId = objproject.getProjectnameid();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resourceAttributeValuesService.getAllresouceCountByproject(projectId + "");
    }

    @RequestMapping(value = "/viewer/landrecords/saveResourcePersonForEditing/{landid}", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public ResponseEntity saveResourcelPersonForEditing(HttpServletRequest request, @PathVariable Integer landid, HttpServletResponse response) {
        try {
            Integer landId = 0;
            String firstname = "";
            String middlename = "";
            String lastname = "";
            String address = "";
            String genderid = "";
            String dob = "";
            String MobileNo = "";
            String Address = "";
            String maritalstatusid = "";
            String groupId = "";
            String Citizenship = "";
            String Ethnicity = "";
            String resident = "";
            String Otherdetails = "";
            String Institutionname = "";
            String Agencyname = "";
            String Community = "";
            String Consession_handled = "";

            String RegistartionNo = "";
            String RegistrationDate = "";
            String levelofauthority = "";
            String Total_members = "";
            String Totalmemb_collective_Org = "";
            String output = "";
            String registartionOutputdate = "";
            String community_area = "";

            try {
                RegistartionNo = ServletRequestUtils.getRequiredStringParameter(request, "Registration No");
            } catch (Exception e) {
            }
            try {
                RegistrationDate = ServletRequestUtils.getRequiredStringParameter(request, "Registration Date");
            } catch (Exception e) {
            }
            try {
                levelofauthority = ServletRequestUtils.getRequiredStringParameter(request, "Level of Authority");
            } catch (Exception e) {
            }
            try {
                Total_members = ServletRequestUtils.getRequiredStringParameter(request, "How many members?");
            } catch (Exception e) {
            }
            try {
                Totalmemb_collective_Org = ServletRequestUtils.getRequiredStringParameter(request, "How many members in collective organization?");
            } catch (Exception e) {
            }

            try {
                Otherdetails = ServletRequestUtils.getRequiredStringParameter(request, "Other details");
            } catch (Exception e) {
            }
            try {
                Institutionname = ServletRequestUtils.getRequiredStringParameter(request, "Institution Name");
            } catch (Exception e) {
            }
            try {
                Agencyname = ServletRequestUtils.getRequiredStringParameter(request, "Agency Name");
            } catch (Exception e) {
            }
            try {
                Community = ServletRequestUtils.getRequiredStringParameter(request, "Community or Parties");
            } catch (Exception e) {
            }
            try {
                Consession_handled = ServletRequestUtils.getRequiredStringParameter(request, "How are concessions to land handled?");
            } catch (Exception e) {
            }
            try {
                landId = ServletRequestUtils.getRequiredIntParameter(request, "landid");
            } catch (Exception e) {
            }
            try {
                firstname = ServletRequestUtils.getRequiredStringParameter(request, "firstname");
            } catch (Exception e) {
            }
            try {
                middlename = ServletRequestUtils.getRequiredStringParameter(request, "middlename");
            } catch (Exception e) {
            }
            try {
                lastname = ServletRequestUtils.getRequiredStringParameter(request, "lastname");
            } catch (Exception e) {
            }
            try {
                groupId = ServletRequestUtils.getRequiredStringParameter(request, "groupId");
            } catch (Exception e) {
            }
            try {
                genderid = ServletRequestUtils.getRequiredStringParameter(request, "genderid");
            } catch (Exception e) {
            }
            try {
                dob = ServletRequestUtils.getRequiredStringParameter(request, "Dob");
            } catch (Exception e) {
            }
            //try{Long mobileno = ServletRequestUtils.getRequiredLongParameter(request, "mobileno");}catch(Exception e){}
            try {
                Citizenship = ServletRequestUtils.getRequiredStringParameter(request, "Citizenship");
            } catch (Exception e) {
            }
            try {
                Ethnicity = ServletRequestUtils.getRequiredStringParameter(request, "Ethnicity");
            } catch (Exception e) {
            }
            try {
                resident = ServletRequestUtils.getRequiredStringParameter(request, "Resident of Community");
            } catch (Exception e) {
            }

            try {
                MobileNo = ServletRequestUtils.getRequiredStringParameter(request, "Mobile No");
            } catch (Exception e) {
            }
            try {
                Address = ServletRequestUtils.getRequiredStringParameter(request, "Address");
            } catch (Exception e) {
            }
            try {
                maritalstatusid = ServletRequestUtils.getRequiredStringParameter(request, "Marital Status");
            } catch (Exception e) {
            }
            try {
                community_area = ServletRequestUtils.getRequiredStringParameter(request, "Community Area");
            } catch (Exception e) {
            }
            List<ResourceAttributeValues> resobj = null;
            ResourceAttributeValues newobj = null;
            if (null != dob && dob != "") {
                DateFormat inputFormat = new SimpleDateFormat(
                        "E MMM dd yyyy HH:mm:ss 'GMT'z", Locale.ENGLISH);
                Date date = inputFormat.parse(dob);

                DateFormat outputFormat = new SimpleDateFormat("MM-dd-yyyy");

                output = outputFormat.format(date);
            }

            if (null != RegistrationDate && RegistrationDate != "") {
                DateFormat inputFormat = new SimpleDateFormat(
                        "E MMM dd yyyy HH:mm:ss 'GMT'z", Locale.ENGLISH);
                Date date = inputFormat.parse(RegistrationDate);

                DateFormat outputFormat = new SimpleDateFormat("MM-dd-yyyy");

                registartionOutputdate = outputFormat.format(date);
            }

            resobj = resourceAttributeValuesService.getResourceAttributeValuesByMasterlandid(landId);

            if (groupId.equalsIgnoreCase("")) {
                Integer groupid = 0;
                resobj = resourceAttributeValuesService.getResourceAttributeValuesByMasterlandid(landid);
                for (ResourceAttributeValues objlst : resobj) {

                    if (groupid <= objlst.getGroupid()) {
                        groupid = objlst.getGroupid() + 1;
                    }
                }
                for (ResourceAttributeValues objlst : resobj) {
                    newobj = new ResourceAttributeValues();
//					newobj= objlst;
                    newobj.setAttributemaster(objlst.getAttributemaster());
                    newobj.setGeomtype(objlst.getGeomtype());
                    newobj.setProjectid(objlst.getProjectid());
                    newobj.setLandid(objlst.getLandid());
                    newobj.setGroupid(groupid);
//					ResourceAttributeValues resmergeobj = new ResourceAttributeValues();
//					if(objlst.getAttributemaster().getFieldname().equalsIgnoreCase("firstname")){
                    if (objlst.getAttributemaster().getAttributemasterid() == 1017
                            || objlst.getAttributemaster().getAttributemasterid() == 1035
                            || objlst.getAttributemaster().getAttributemasterid() == 1063
                            || objlst.getAttributemaster().getAttributemasterid() == 1079
                            || objlst.getAttributemaster().getAttributemasterid() == 1088
                            || objlst.getAttributemaster().getAttributemasterid() == 1097
                            || objlst.getAttributemaster().getAttributemasterid() == 1108
                            || objlst.getAttributemaster().getAttributemasterid() == 1115) {
                        newobj.setAttributevalue(firstname);
//						objlst.setGroupid(groupid);
//						em.merge(newobj);
                        if (!firstname.equalsIgnoreCase("")) {
                            resourceattributeValuesdao.addResourceAttributeValues(newobj);
                        }
                    } else if (objlst.getAttributemaster().getAttributemasterid() == 1018
                            || objlst.getAttributemaster().getAttributemasterid() == 1036
                            || objlst.getAttributemaster().getAttributemasterid() == 1065
                            || objlst.getAttributemaster().getAttributemasterid() == 1080
                            || objlst.getAttributemaster().getAttributemasterid() == 1089
                            || objlst.getAttributemaster().getAttributemasterid() == 1109
                            || objlst.getAttributemaster().getAttributemasterid() == 1098
                            || objlst.getAttributemaster().getAttributemasterid() == 1117) {
                        newobj.setAttributevalue(middlename);
//						objlst.setGroupid(groupid);
//						em.merge(newobj);
                        if (!middlename.equalsIgnoreCase("")) {
                            resourceattributeValuesdao.addResourceAttributeValues(newobj);
                        }
                    } else if (objlst.getAttributemaster().getAttributemasterid() == 1019
                            || objlst.getAttributemaster().getAttributemasterid() == 1037
                            || objlst.getAttributemaster().getAttributemasterid() == 1066
                            || objlst.getAttributemaster().getAttributemasterid() == 1081
                            || objlst.getAttributemaster().getAttributemasterid() == 1090
                            || objlst.getAttributemaster().getAttributemasterid() == 1099
                            || objlst.getAttributemaster().getAttributemasterid() == 1110
                            || objlst.getAttributemaster().getAttributemasterid() == 1118) {
                        newobj.setAttributevalue(lastname);
//						objlst.setGroupid(groupid);
//						em.merge(newobj);
                        if (!lastname.equalsIgnoreCase("")) {
                            resourceattributeValuesdao.addResourceAttributeValues(newobj);
                        }
                    } else if (objlst.getAttributemaster().getAttributemasterid() == 1042
                            || objlst.getAttributemaster().getAttributemasterid() == 1030
                            || objlst.getAttributemaster().getAttributemasterid() == 1073
                            || objlst.getAttributemaster().getAttributemasterid() == 1086
                            || objlst.getAttributemaster().getAttributemasterid() == 1095
                            || objlst.getAttributemaster().getAttributemasterid() == 1105
                            || objlst.getAttributemaster().getAttributemasterid() == 1125) {
                        newobj.setAttributevalue(MobileNo);
//						objlst.setGroupid(groupid);
//						em.merge(newobj);
                        if (!MobileNo.equalsIgnoreCase("")) {
                            resourceattributeValuesdao.addResourceAttributeValues(newobj);
                        }
                    } else if (objlst.getAttributemaster().getAttributemasterid() == 1038
                            || objlst.getAttributemaster().getAttributemasterid() == 1026
                            || objlst.getAttributemaster().getAttributemasterid() == 1074
                            || objlst.getAttributemaster().getAttributemasterid() == 1082
                            || objlst.getAttributemaster().getAttributemasterid() == 1091
                            || objlst.getAttributemaster().getAttributemasterid() == 1100
                            || objlst.getAttributemaster().getAttributemasterid() == 1111
                            || objlst.getAttributemaster().getAttributemasterid() == 1126) {
                        newobj.setAttributevalue(Address);
//						objlst.setGroupid(groupid);
//						em.merge(newobj);
                        if (!Address.equalsIgnoreCase("")) {
                            resourceattributeValuesdao.addResourceAttributeValues(newobj);
                        }
                    } else if (objlst.getAttributemaster().getAttributemasterid() == 1021
                            || objlst.getAttributemaster().getAttributemasterid() == 1068
                            || objlst.getAttributemaster().getAttributemasterid() == 1129
                            || objlst.getAttributemaster().getAttributemasterid() == 1120) {
                        newobj.setAttributevalue(output.toString());
//						objlst.setGroupid(groupid);
//						em.merge(newobj);
                        if (!output.toString().equalsIgnoreCase("")) {
                            resourceattributeValuesdao.addResourceAttributeValues(newobj);
                        }
                    } else if (objlst.getAttributemaster().getAttributemasterid() == 1022
                            || objlst.getAttributemaster().getAttributemasterid() == 1064
                            || objlst.getAttributemaster().getAttributemasterid() == 1116) {
                        if (maritalstatusid.equalsIgnoreCase("un-Married")) {
                            AttributeOptions attroptions = attributeOptionsDao.getAttributeOptionsId(1011);
                            newobj.setAttributevalue(attroptions.getOptiontext());
//							objlst.setGroupid(groupid);
                        } else if (maritalstatusid.equalsIgnoreCase("married")) {
                            AttributeOptions attroptions = attributeOptionsDao.getAttributeOptionsId(1007);
                            newobj.setAttributevalue(attroptions.getOptiontext());
//							objlst.setGroupid(groupid);
                        } else if (maritalstatusid.equalsIgnoreCase("divorced")) {
                            AttributeOptions attroptions = attributeOptionsDao.getAttributeOptionsId(1008);
                            newobj.setAttributevalue(attroptions.getOptiontext());
//							objlst.setGroupid(groupid);
                        } else if (maritalstatusid.equalsIgnoreCase("widow")) {
                            AttributeOptions attroptions = attributeOptionsDao.getAttributeOptionsId(1009);
                            newobj.setAttributevalue(attroptions.getOptiontext());
//							objlst.setGroupid(groupid);
                        } else if (maritalstatusid.equalsIgnoreCase("widower")) {
                            AttributeOptions attroptions = attributeOptionsDao.getAttributeOptionsId(1010);
                            newobj.setAttributevalue(attroptions.getOptiontext());
//							objlst.setGroupid(groupid);
                        }

//						em.merge(newobj);
                        if (!maritalstatusid.equalsIgnoreCase("")) {
                            resourceattributeValuesdao.addResourceAttributeValues(newobj);
                        }
                    } else if (objlst.getAttributemaster().getAttributemasterid() == 1025
                            || objlst.getAttributemaster().getAttributemasterid() == 1071
                            || objlst.getAttributemaster().getAttributemasterid() == 1123) {
                        if (resident.equalsIgnoreCase("Yes")) {
                            AttributeOptions attroptions = attributeOptionsDao.getAttributeOptionsId(1012);
                            newobj.setAttributevalue(attroptions.getOptiontext());
//							objlst.setGroupid(groupid);
                        } else if (resident.equalsIgnoreCase("No")) {
                            AttributeOptions attroptions = attributeOptionsDao.getAttributeOptionsId(1013);
                            newobj.setAttributevalue(attroptions.getOptiontext());
//							objlst.setGroupid(groupid);
                        }
//						em.merge(newobj);
                        if (!resident.equalsIgnoreCase("")) {
                            resourceattributeValuesdao.addResourceAttributeValues(newobj);
                        }
                    } else if (objlst.getAttributemaster().getAttributemasterid() == 1024
                            || objlst.getAttributemaster().getAttributemasterid() == 1070
                            || objlst.getAttributemaster().getAttributemasterid() == 1122) {
                        if (Ethnicity.equalsIgnoreCase("Ethnicity 1")) {
                            AttributeOptions attroptions = attributeOptionsDao.getAttributeOptionsId(1017);
                            newobj.setAttributevalue(attroptions.getOptiontext());
//							objlst.setGroupid(groupid);
                        } else if (Ethnicity.equalsIgnoreCase("Nimba")) {
                            AttributeOptions attroptions = attributeOptionsDao.getAttributeOptionsId(1017);
                            newobj.setAttributevalue(attroptions.getOptiontext());
//							objlst.setGroupid(groupid);
                        } else if (Ethnicity.equalsIgnoreCase("Ethnicity 2")) {
                            AttributeOptions attroptions = attributeOptionsDao.getAttributeOptionsId(1018);
                            newobj.setAttributevalue(attroptions.getOptiontext());
//							objlst.setGroupid(groupid);
                        } else if (Ethnicity.equalsIgnoreCase("Ethnicity 3")) {
                            AttributeOptions attroptions = attributeOptionsDao.getAttributeOptionsId(1019);
                            newobj.setAttributevalue(attroptions.getOptiontext());
//							objlst.setGroupid(groupid);
                        } else if (Ethnicity.equalsIgnoreCase("Ethnicity 4")) {
                            AttributeOptions attroptions = attributeOptionsDao.getAttributeOptionsId(1020);
                            newobj.setAttributevalue(attroptions.getOptiontext());
//							objlst.setGroupid(groupid);
                        }

//						em.merge(newobj);
                        if (!Ethnicity.equalsIgnoreCase("")) {
                            newobj.setAttributevalue(Ethnicity);
                            resourceattributeValuesdao.addResourceAttributeValues(newobj);
                        }
                    } else if (objlst.getAttributemaster().getAttributemasterid() == 1023
                            || objlst.getAttributemaster().getAttributemasterid() == 1069
                            || objlst.getAttributemaster().getAttributemasterid() == 1121) {
                        if (Citizenship.equalsIgnoreCase("Not Known")) {
                            AttributeOptions attroptions = attributeOptionsDao.getAttributeOptionsId(1014);
                            newobj.setAttributevalue(attroptions.getOptiontext());
//							objlst.setGroupid(groupid);
                        } else if (Citizenship.equalsIgnoreCase("Liberian")) {
                            AttributeOptions attroptions = attributeOptionsDao.getAttributeOptionsId(1014);
                            newobj.setAttributevalue(attroptions.getOptiontext());
//							objlst.setGroupid(groupid);
                        } else if (Citizenship.equalsIgnoreCase("Country1")) {
                            AttributeOptions attroptions = attributeOptionsDao.getAttributeOptionsId(1015);
                            newobj.setAttributevalue(attroptions.getOptiontext());
//							objlst.setGroupid(groupid);
                        } else if (Citizenship.equalsIgnoreCase("Country2")) {
                            AttributeOptions attroptions = attributeOptionsDao.getAttributeOptionsId(1016);
                            newobj.setAttributevalue(attroptions.getOptiontext());
//							objlst.setGroupid(groupid);
                        } else if (Citizenship.equalsIgnoreCase("Others")) {
                            AttributeOptions attroptions = attributeOptionsDao.getAttributeOptionsId(1142);
                            newobj.setAttributevalue(attroptions.getOptiontext());
//							objlst.setGroupid(groupid);
                        }
//						em.merge(newobj);
                        if (!Citizenship.equalsIgnoreCase("")) {
                            newobj.setAttributevalue(Citizenship);
                            resourceattributeValuesdao.addResourceAttributeValues(newobj);
                        }
                    } else if (objlst.getAttributemaster().getAttributemasterid() == 1020
                            || objlst.getAttributemaster().getAttributemasterid() == 1067
                            || objlst.getAttributemaster().getAttributemasterid() == 1119) {
                        if (genderid.equalsIgnoreCase("male")) {
                            AttributeOptions attroptions = attributeOptionsDao.getAttributeOptionsId(1005);
                            newobj.setAttributevalue(attroptions.getOptiontext());
//							objlst.setGroupid(groupid);
                        } else if (genderid.equalsIgnoreCase("female")) {
                            AttributeOptions attroptions = attributeOptionsDao.getAttributeOptionsId(1006);
                            newobj.setAttributevalue(attroptions.getOptiontext());
//							objlst.setGroupid(groupid);
                        }
//						em.merge(newobj);
                        if (!genderid.equalsIgnoreCase("")) {
                            resourceattributeValuesdao.addResourceAttributeValues(newobj);
                        }
                    } else if (objlst.getAttributemaster().getAttributemasterid() == 1053
                            || objlst.getAttributemaster().getAttributemasterid() == 1055) {

                        newobj.setAttributevalue(Otherdetails);
//						objlst.setGroupid(groupid);
//						em.merge(newobj);
                        if (!Otherdetails.equalsIgnoreCase("")) {
                            resourceattributeValuesdao.addResourceAttributeValues(newobj);
                        }
                    } else if (objlst.getAttributemaster().getAttributemasterid() == 1031
                            || objlst.getAttributemaster().getAttributemasterid() == 1077) {

                        newobj.setAttributevalue(Institutionname);
//						objlst.setGroupid(groupid);
//						em.merge(newobj);
                        if (!Institutionname.equalsIgnoreCase("")) {
                            resourceattributeValuesdao.addResourceAttributeValues(newobj);
                        }
                    } else if (objlst.getAttributemaster().getAttributemasterid() == 1106) {

                        newobj.setAttributevalue(Agencyname);
//						objlst.setGroupid(groupid);
//						em.merge(newobj);
                        if (!Agencyname.equalsIgnoreCase("")) {
                            resourceattributeValuesdao.addResourceAttributeValues(newobj);
                        }
                    } else if (objlst.getAttributemaster().getAttributemasterid() == 1096) {

                        newobj.setAttributevalue(Community);
//						objlst.setGroupid(groupid);
//						em.merge(newobj);
                        if (!Community.equalsIgnoreCase("")) {
                            resourceattributeValuesdao.addResourceAttributeValues(newobj);
                        }
                    } else if (objlst.getAttributemaster().getAttributemasterid() == 1060) {

                        newobj.setAttributevalue(Consession_handled);
//						objlst.setGroupid(groupid);
//						em.merge(newobj);
                        if (!Consession_handled.equalsIgnoreCase("")) {
                            resourceattributeValuesdao.addResourceAttributeValues(newobj);
                        }
                    } else if (objlst.getAttributemaster().getAttributemasterid() == 1032) {
                        newobj.setAttributevalue(RegistartionNo);
//						objlst.setGroupid(groupid);
//						em.merge(newobj);
                        if (!RegistartionNo.equalsIgnoreCase("")) {
                            resourceattributeValuesdao.addResourceAttributeValues(newobj);
                        }
                    } else if (objlst.getAttributemaster().getAttributemasterid() == 1034
                            || objlst.getAttributemaster().getAttributemasterid() == 1078) {
                        newobj.setAttributevalue(Total_members);
//						objlst.setGroupid(groupid);
//						em.merge(newobj);
                        if (!Total_members.equalsIgnoreCase("")) {
                            resourceattributeValuesdao.addResourceAttributeValues(newobj);
                        }
                    } else if (objlst.getAttributemaster().getAttributemasterid() == 1033) {
                        newobj.setAttributevalue(registartionOutputdate);
//						objlst.setGroupid(groupid);
//						em.merge(newobj);
                        if (!registartionOutputdate.equalsIgnoreCase("")) {
                            resourceattributeValuesdao.addResourceAttributeValues(newobj);
                        }
                    } else if (objlst.getAttributemaster().getAttributemasterid() == 1107) {
                        if (levelofauthority.equalsIgnoreCase("National")) {
                            AttributeOptions attroptions = attributeOptionsDao.getAttributeOptionsId(1145);
                            newobj.setAttributevalue(attroptions.getOptiontext());
//							objlst.setGroupid(groupid);
                        } else if (levelofauthority.equalsIgnoreCase("Regional")) {
                            AttributeOptions attroptions = attributeOptionsDao.getAttributeOptionsId(1146);
                            newobj.setAttributevalue(attroptions.getOptiontext());
//							objlst.setGroupid(groupid);
                        } else if (levelofauthority.equalsIgnoreCase("District")) {
                            AttributeOptions attroptions = attributeOptionsDao.getAttributeOptionsId(1147);
                            newobj.setAttributevalue(attroptions.getOptiontext());
//							objlst.setGroupid(groupid);
                        } else if (levelofauthority.equalsIgnoreCase("Local ")) {
                            AttributeOptions attroptions = attributeOptionsDao.getAttributeOptionsId(1148);
                            newobj.setAttributevalue(attroptions.getOptiontext());
//							objlst.setGroupid(groupid);
                        }

//						em.merge(newobj);
                        if (!levelofauthority.equalsIgnoreCase("")) {
                            resourceattributeValuesdao.addResourceAttributeValues(newobj);
                        }
                    } else if (objlst.getAttributemaster().getAttributemasterid() == 1087) {
                        newobj.setAttributevalue(Totalmemb_collective_Org);
//						objlst.setGroupid(groupid);
//						em.merge(newobj);
                        if (!Totalmemb_collective_Org.equalsIgnoreCase("")) {
                            resourceattributeValuesdao.addResourceAttributeValues(newobj);
                        }
                    } else if (objlst.getAttributemaster().getAttributemasterid() == 1112) {
                        newobj.setAttributevalue(community_area);
                        if (!community_area.equalsIgnoreCase("")) {
                            resourceattributeValuesdao.addResourceAttributeValues(newobj);
                        }
                    }
                }
                return ResponseEntity.status(HttpStatus.CREATED).body(newobj);
            } else {
                for (ResourceAttributeValues objlst : resobj) {
                    if (objlst.getGroupid().toString().equalsIgnoreCase(groupId.toString())) {
//				ResourceAttributeValues resmergeobj = new ResourceAttributeValues();
//				if(objlst.getAttributemaster().getFieldname().equalsIgnoreCase("firstname")){
                        if (objlst.getAttributemaster().getAttributemasterid() == 1017
                                || objlst.getAttributemaster().getAttributemasterid() == 1035
                                || objlst.getAttributemaster().getAttributemasterid() == 1063
                                || objlst.getAttributemaster().getAttributemasterid() == 1079
                                || objlst.getAttributemaster().getAttributemasterid() == 1088
                                || objlst.getAttributemaster().getAttributemasterid() == 1097
                                || objlst.getAttributemaster().getAttributemasterid() == 1108
                                || objlst.getAttributemaster().getAttributemasterid() == 1115) {
                            objlst.setAttributevalue(firstname);
                            em.merge(objlst);
                        } else if (objlst.getAttributemaster().getAttributemasterid() == 1018
                                || objlst.getAttributemaster().getAttributemasterid() == 1036
                                || objlst.getAttributemaster().getAttributemasterid() == 1065
                                || objlst.getAttributemaster().getAttributemasterid() == 1080
                                || objlst.getAttributemaster().getAttributemasterid() == 1089
                                || objlst.getAttributemaster().getAttributemasterid() == 1109
                                || objlst.getAttributemaster().getAttributemasterid() == 1098
                                || objlst.getAttributemaster().getAttributemasterid() == 1117) {
                            objlst.setAttributevalue(middlename);
                            em.merge(objlst);
                        } else if (objlst.getAttributemaster().getAttributemasterid() == 1019
                                || objlst.getAttributemaster().getAttributemasterid() == 1037
                                || objlst.getAttributemaster().getAttributemasterid() == 1066
                                || objlst.getAttributemaster().getAttributemasterid() == 1081
                                || objlst.getAttributemaster().getAttributemasterid() == 1090
                                || objlst.getAttributemaster().getAttributemasterid() == 1099
                                || objlst.getAttributemaster().getAttributemasterid() == 1110
                                || objlst.getAttributemaster().getAttributemasterid() == 1118) {
                            objlst.setAttributevalue(lastname);
                            em.merge(objlst);
                        } else if (objlst.getAttributemaster().getAttributemasterid() == 1042
                                || objlst.getAttributemaster().getAttributemasterid() == 1030
                                || objlst.getAttributemaster().getAttributemasterid() == 1073
                                || objlst.getAttributemaster().getAttributemasterid() == 1086
                                || objlst.getAttributemaster().getAttributemasterid() == 1095
                                || objlst.getAttributemaster().getAttributemasterid() == 1105
                                || objlst.getAttributemaster().getAttributemasterid() == 1125) {
                            objlst.setAttributevalue(MobileNo);
                            em.merge(objlst);
                        } else if (objlst.getAttributemaster().getAttributemasterid() == 1038
                                || objlst.getAttributemaster().getAttributemasterid() == 1026
                                || objlst.getAttributemaster().getAttributemasterid() == 1074
                                || objlst.getAttributemaster().getAttributemasterid() == 1082
                                || objlst.getAttributemaster().getAttributemasterid() == 1091
                                || objlst.getAttributemaster().getAttributemasterid() == 1100
                                || objlst.getAttributemaster().getAttributemasterid() == 1111
                                || objlst.getAttributemaster().getAttributemasterid() == 1126) {
                            objlst.setAttributevalue(Address);
                            em.merge(objlst);
                        } else if (objlst.getAttributemaster().getAttributemasterid() == 1021
                                || objlst.getAttributemaster().getAttributemasterid() == 1068
                                || objlst.getAttributemaster().getAttributemasterid() == 1129
                                || objlst.getAttributemaster().getAttributemasterid() == 1120) {
                            objlst.setAttributevalue(output.toString());
                            em.merge(objlst);
                        } else if (objlst.getAttributemaster().getAttributemasterid() == 1022
                                || objlst.getAttributemaster().getAttributemasterid() == 1064
                                || objlst.getAttributemaster().getAttributemasterid() == 1116) {
                            if (maritalstatusid.equalsIgnoreCase("un-Married")) {
                                AttributeOptions attroptions = attributeOptionsDao.getAttributeOptionsId(1011);
                                objlst.setAttributevalue(attroptions.getOptiontext());
                            } else if (maritalstatusid.equalsIgnoreCase("married")) {
                                AttributeOptions attroptions = attributeOptionsDao.getAttributeOptionsId(1007);
                                objlst.setAttributevalue(attroptions.getOptiontext());
                            } else if (maritalstatusid.equalsIgnoreCase("divorced")) {
                                AttributeOptions attroptions = attributeOptionsDao.getAttributeOptionsId(1008);
                                objlst.setAttributevalue(attroptions.getOptiontext());
                            } else if (maritalstatusid.equalsIgnoreCase("widow")) {
                                AttributeOptions attroptions = attributeOptionsDao.getAttributeOptionsId(1009);
                                objlst.setAttributevalue(attroptions.getOptiontext());
                            } else if (maritalstatusid.equalsIgnoreCase("widower")) {
                                AttributeOptions attroptions = attributeOptionsDao.getAttributeOptionsId(1010);
                                objlst.setAttributevalue(attroptions.getOptiontext());
                            }

                            em.merge(objlst);
                        } else if (objlst.getAttributemaster().getAttributemasterid() == 1025
                                || objlst.getAttributemaster().getAttributemasterid() == 1071
                                || objlst.getAttributemaster().getAttributemasterid() == 1123) {
                            if (resident.equalsIgnoreCase("Yes")) {
                                AttributeOptions attroptions = attributeOptionsDao.getAttributeOptionsId(1012);
                                objlst.setAttributevalue(attroptions.getOptiontext());
                            } else if (resident.equalsIgnoreCase("No")) {
                                AttributeOptions attroptions = attributeOptionsDao.getAttributeOptionsId(1013);
                                objlst.setAttributevalue(attroptions.getOptiontext());
                            }
                            em.merge(objlst);
                        } else if (objlst.getAttributemaster().getAttributemasterid() == 1024
                                || objlst.getAttributemaster().getAttributemasterid() == 1070
                                || objlst.getAttributemaster().getAttributemasterid() == 1122) {

                            objlst.setAttributevalue(Ethnicity);

                            em.merge(objlst);
                        } else if (objlst.getAttributemaster().getAttributemasterid() == 1023
                                || objlst.getAttributemaster().getAttributemasterid() == 1069
                                || objlst.getAttributemaster().getAttributemasterid() == 1121) {

                            objlst.setAttributevalue(Citizenship);
                            em.merge(objlst);
                        } else if (objlst.getAttributemaster().getAttributemasterid() == 1020
                                || objlst.getAttributemaster().getAttributemasterid() == 1067
                                || objlst.getAttributemaster().getAttributemasterid() == 1119) {
                            if (genderid.equalsIgnoreCase("male")) {
                                AttributeOptions attroptions = attributeOptionsDao.getAttributeOptionsId(1005);
                                objlst.setAttributevalue(attroptions.getOptiontext());
                            } else if (genderid.equalsIgnoreCase("female")) {
                                AttributeOptions attroptions = attributeOptionsDao.getAttributeOptionsId(1006);
                                objlst.setAttributevalue(attroptions.getOptiontext());
                            }
                            em.merge(objlst);
                        } else if (objlst.getAttributemaster().getAttributemasterid() == 1053
                                || objlst.getAttributemaster().getAttributemasterid() == 1055) {

                            objlst.setAttributevalue(Otherdetails);
                            em.merge(objlst);
                        } else if (objlst.getAttributemaster().getAttributemasterid() == 1031
                                || objlst.getAttributemaster().getAttributemasterid() == 1077) {

                            objlst.setAttributevalue(Institutionname);
                            em.merge(objlst);
                        } else if (objlst.getAttributemaster().getAttributemasterid() == 1106) {

                            objlst.setAttributevalue(Agencyname);
                            em.merge(objlst);
                        } else if (objlst.getAttributemaster().getAttributemasterid() == 1096) {

                            objlst.setAttributevalue(Community);
                            em.merge(objlst);
                        } else if (objlst.getAttributemaster().getAttributemasterid() == 1060) {

                            objlst.setAttributevalue(Consession_handled);
                            em.merge(objlst);
                        } else if (objlst.getAttributemaster().getAttributemasterid() == 1032) {
                            objlst.setAttributevalue(RegistartionNo);
                            em.merge(objlst);
                        } else if (objlst.getAttributemaster().getAttributemasterid() == 1034
                                || objlst.getAttributemaster().getAttributemasterid() == 1078) {
                            objlst.setAttributevalue(Total_members);
                            em.merge(objlst);
                        } else if (objlst.getAttributemaster().getAttributemasterid() == 1033) {
                            objlst.setAttributevalue(registartionOutputdate);
                            em.merge(objlst);
                        } else if (objlst.getAttributemaster().getAttributemasterid() == 1107) {
                            if (levelofauthority.equalsIgnoreCase("National")) {
                                AttributeOptions attroptions = attributeOptionsDao.getAttributeOptionsId(1145);
                                objlst.setAttributevalue(attroptions.getOptiontext());
                            } else if (levelofauthority.equalsIgnoreCase("Regional")) {
                                AttributeOptions attroptions = attributeOptionsDao.getAttributeOptionsId(1146);
                                objlst.setAttributevalue(attroptions.getOptiontext());
                            } else if (levelofauthority.equalsIgnoreCase("District")) {
                                AttributeOptions attroptions = attributeOptionsDao.getAttributeOptionsId(1147);
                                objlst.setAttributevalue(attroptions.getOptiontext());
                            } else if (levelofauthority.equalsIgnoreCase("Local ")) {
                                AttributeOptions attroptions = attributeOptionsDao.getAttributeOptionsId(1148);
                                objlst.setAttributevalue(attroptions.getOptiontext());
                            }

                            em.merge(objlst);
                        } else if (objlst.getAttributemaster().getAttributemasterid() == 1087) {
                            objlst.setAttributevalue(Totalmemb_collective_Org);
                            em.merge(objlst);
                        } else if (objlst.getAttributemaster().getAttributemasterid() == 1112) {
                            objlst.setAttributevalue(community_area);
                            em.merge(objlst);
                        }

//				else if(objlst.getAttributemaster().getAttributemasterid()== 1021){
//					objlst.setAttributevalue(age.toString());
//				}
//				else if(objlst.getAttributemaster().getAttributemasterid()== 1022){
//					objlst.setAttributevalue(maritalstatusid);
//				}
                    } else {
                        System.out.println("Do Nothing");
                    }
                }
                return ResponseEntity.status(HttpStatus.CREATED).body(resobj);
            }

        } catch (Exception e) {
            return null;
        }
    }

    @RequestMapping(value = "/viewer/landrecords/saveResourceCustomAttributes", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public ResponseEntity saveResourceCustomattributes(HttpServletRequest request, HttpServletResponse response, Principal principal) {

        Integer projectId = 0;

        try {
            Integer landId = 0;
            String custom1 = "";
            String custom2 = "";
            String custom3 = "";
            String custom4 = "";
            String custom5 = "";
            String custom6 = "";
            String custom7 = "";
            String custom8 = "";
            String custom9 = "";

            User userobj = userdao.findByName(principal.getName());
            String project = userobj.getDefaultproject();

            Project objproject = projectDAO.findByName(project);
            projectId = objproject.getProjectnameid();
            /*  try{landId = ServletRequestUtils.getRequiredIntParameter(request, "landid");}catch(Exception e){}
			try{custom1 =  ServletRequestUtils.getRequiredStringParameter(request, "Primary Crop");}catch(Exception e){}
			try{custom2 =ServletRequestUtils.getRequiredStringParameter(request, "Plant Date (Primary Crop)");}catch(Exception e){}
			try{custom3 =ServletRequestUtils.getRequiredStringParameter(request, "Duration (Primary Crop)");}catch(Exception e){}
			try{custom4 =ServletRequestUtils.getRequiredStringParameter(request, "Secondary Crop");}catch(Exception e){}
			try{custom5 =ServletRequestUtils.getRequiredStringParameter(request, "Plant Date (Secondary Crop)");}catch(Exception e){}
			try{custom6 =ServletRequestUtils.getRequiredStringParameter(request, "Duration (Secondary Crop)");}catch(Exception e){}
			try{custom7 =ServletRequestUtils.getRequiredStringParameter(request, "Total Expenditures (Farmer)");}catch(Exception e){}
			try{custom8 =ServletRequestUtils.getRequiredStringParameter(request, "Total Sales (Farmer)");}catch(Exception e){}
			try{custom9 =ServletRequestUtils.getRequiredStringParameter(request, "Name of Enterprise Farm");}catch(Exception e){}
             */

            try {
                landId = ServletRequestUtils.getRequiredIntParameter(request, "landid");
            } catch (Exception e) {
            }
            try {
                custom1 = ServletRequestUtils.getRequiredStringParameter(request, "priamaryCrop");
            } catch (Exception e) {
            }
            try {
                custom2 = ServletRequestUtils.getRequiredStringParameter(request, "primaryplantdate");
            } catch (Exception e) {
            }
            try {
                custom3 = ServletRequestUtils.getRequiredStringParameter(request, "primaryduration");
            } catch (Exception e) {
            }
            try {
                custom4 = ServletRequestUtils.getRequiredStringParameter(request, "secCrop");
            } catch (Exception e) {
            }
            try {
                custom5 = ServletRequestUtils.getRequiredStringParameter(request, "secplantdate");
            } catch (Exception e) {
            }
            try {
                custom6 = ServletRequestUtils.getRequiredStringParameter(request, "secduration");
            } catch (Exception e) {
            }
            try {
                custom7 = ServletRequestUtils.getRequiredStringParameter(request, "totalExpenditure");
            } catch (Exception e) {
            }
            try {
                custom8 = ServletRequestUtils.getRequiredStringParameter(request, "totalsales");
            } catch (Exception e) {
            }
            try {
                custom9 = ServletRequestUtils.getRequiredStringParameter(request, "Name of Enterprise Farm");
            } catch (Exception e) {
            }

            /* try{custom1 =  ServletRequestUtils.getRequiredStringParameter(request, "Color of sky");}catch(Exception e){}
			try{custom2 =ServletRequestUtils.getRequiredStringParameter(request, "Color of bear");}catch(Exception e){}
			try{custom3 =ServletRequestUtils.getRequiredStringParameter(request, "Color of soil");}catch(Exception e){}*/
            List<CustomAttributes> resobj = customAttributesdao.getResourceCustomAttributeValuesBylandId(objproject.getProjectnameid(), landId);
            for (CustomAttributes objlst : resobj) {

                if (objlst.getAttributeoptionsid() == 103 || objlst.getAttributeoptionsid() == 122 || objlst.getAttributeoptionsid() == 131 || objlst.getAttributeoptionsid() == 140 || objlst.getAttributeoptionsid() == 149 || objlst.getAttributeoptionsid() == 158) {
                    if (!custom1.equalsIgnoreCase("")) {

                        objlst.setAttributevalue(custom1);
                        em.merge(objlst);
                    }
                } else if (objlst.getAttributeoptionsid() == 104 || objlst.getAttributeoptionsid() == 123 || objlst.getAttributeoptionsid() == 132 || objlst.getAttributeoptionsid() == 141 || objlst.getAttributeoptionsid() == 150 || objlst.getAttributeoptionsid() == 159) {
                    if (!custom2.equalsIgnoreCase("")) {

                        objlst.setAttributevalue(custom2);
                        em.merge(objlst);
                    }
                } else if (objlst.getAttributeoptionsid() == 105 || objlst.getAttributeoptionsid() == 124 || objlst.getAttributeoptionsid() == 133 || objlst.getAttributeoptionsid() == 142 || objlst.getAttributeoptionsid() == 151 || objlst.getAttributeoptionsid() == 160) {
                    if (!custom3.equalsIgnoreCase("")) {

                        objlst.setAttributevalue(custom3);
                        em.merge(objlst);
                    }
                } else if (objlst.getAttributeoptionsid() == 116 || objlst.getAttributeoptionsid() == 125 || objlst.getAttributeoptionsid() == 134 || objlst.getAttributeoptionsid() == 143 || objlst.getAttributeoptionsid() == 152 || objlst.getAttributeoptionsid() == 161) {
                    if (!custom4.equalsIgnoreCase("")) {
                        objlst.setAttributevalue(custom4);
                        em.merge(objlst);
                    }
                } else if (objlst.getAttributeoptionsid() == 117 || objlst.getAttributeoptionsid() == 126 || objlst.getAttributeoptionsid() == 135 || objlst.getAttributeoptionsid() == 144 || objlst.getAttributeoptionsid() == 153 || objlst.getAttributeoptionsid() == 162) {
                    if (!custom5.equalsIgnoreCase("")) {

                        objlst.setAttributevalue(custom5);
                        em.merge(objlst);
                    }
                } else if (objlst.getAttributeoptionsid() == 118 || objlst.getAttributeoptionsid() == 127 || objlst.getAttributeoptionsid() == 136 || objlst.getAttributeoptionsid() == 145 || objlst.getAttributeoptionsid() == 154 || objlst.getAttributeoptionsid() == 163) {
                    if (!custom6.equalsIgnoreCase("")) {

                        objlst.setAttributevalue(custom6);
                        em.merge(objlst);
                    }
                } else if (objlst.getAttributeoptionsid() == 119 || objlst.getAttributeoptionsid() == 128 || objlst.getAttributeoptionsid() == 137 || objlst.getAttributeoptionsid() == 146 || objlst.getAttributeoptionsid() == 155 || objlst.getAttributeoptionsid() == 164) {
                    if (!custom7.equalsIgnoreCase("")) {
                        objlst.setAttributevalue(custom7);
                        em.merge(objlst);
                    }
                } else if (objlst.getAttributeoptionsid() == 120 || objlst.getAttributeoptionsid() == 129 || objlst.getAttributeoptionsid() == 138 || objlst.getAttributeoptionsid() == 147 || objlst.getAttributeoptionsid() == 156 || objlst.getAttributeoptionsid() == 165) {
                    if (!custom8.equalsIgnoreCase("")) {
                        objlst.setAttributevalue(custom8);
                        em.merge(objlst);
                    }
                } else if (objlst.getAttributeoptionsid() == 121) {
                    if (!custom9.equalsIgnoreCase("")) {
                        objlst.setAttributevalue(custom9);
                        em.merge(objlst);
                    }
                }
                /*if(objlst.getCustomattributeid().getCustomattributeid()==2){
					objlst.setAttributevalue(custom1);
					em.merge(objlst);
				}
				else if(objlst.getCustomattributeid().getCustomattributeid()==5){
					objlst.setAttributevalue(custom2);
					em.merge(objlst);
				}
				else if(objlst.getCustomattributeid().getCustomattributeid()==6){
					objlst.setAttributevalue(custom3);
					em.merge(objlst);
				}
				else if(objlst.getCustomattributeid().getCustomattributeid()==7){
					objlst.setAttributevalue(custom4);
					em.merge(objlst);
				}*/

//				else if(objlst.getAttributemaster().getAttributemasterid()== 1021){
//					objlst.setAttributevalue(age.toString());
//				}
//				else if(objlst.getAttributemaster().getAttributemasterid()== 1022){
//					objlst.setAttributevalue(maritalstatusid);
//				}
            }

            return ResponseEntity.status(HttpStatus.CREATED).body(resobj);

        } catch (Exception e) {
            return null;
        }
    }

    @RequestMapping(value = "/viewer/landrecords/saveResourcePoi/{landid}/{geomName}", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public ResponseEntity saveResourcePoi(HttpServletRequest request, @PathVariable String geomName, @PathVariable Integer landid, HttpServletResponse response, Principal principal) {

        Integer projectId = 0;
        List<ResourcePOIAttributeValues> resobj = null;
        ResourcePOIAttributeValues newobj = null;

        try {
            Integer newGroupID = 1;
            Integer landId = 0;
            String fName = "";
            String mName = "";
            String lName = "";
            String dob = "";
            String relType = "";
            String gender = "";
            String output = "";
            String groupId = "";

            User userobj = userdao.findByName(principal.getName());
            String project = userobj.getDefaultproject();

            Project objproject = projectDAO.findByName(project);
            projectId = objproject.getProjectnameid();
            try {
                landId = ServletRequestUtils.getRequiredIntParameter(request, "landid");
            } catch (Exception e) {
            }
            try {
                fName = ServletRequestUtils.getRequiredStringParameter(request, "First Name");
            } catch (Exception e) {
            }
            try {
                mName = ServletRequestUtils.getRequiredStringParameter(request, "Middle Name");
            } catch (Exception e) {
            }
            try {
                lName = ServletRequestUtils.getRequiredStringParameter(request, "Last Name");
            } catch (Exception e) {
            }
            try {
                dob = ServletRequestUtils.getRequiredStringParameter(request, "DOB");
            } catch (Exception e) {
            }
            try {
                relType = ServletRequestUtils.getRequiredStringParameter(request, "Relationship Type");
            } catch (Exception e) {
            }

            try {
                gender = ServletRequestUtils.getRequiredStringParameter(request, "Gender");
            } catch (Exception e) {
            }
            try {
                groupId = ServletRequestUtils.getRequiredStringParameter(request, "groupId");
            } catch (Exception e) {
            }

            resobj = customAttributesdao.getResourcePoiValuesBylandId(objproject.getProjectnameid(), landId);

            /*for(int i=0; i<resobj.size(); i++){
				if(Integer.parseInt(resobj.get(i)[1].toString())== 1){
					em.merge(objlst);
				}
			}*/
            if (null != dob && dob != "") {
                DateFormat inputFormat = new SimpleDateFormat(
                        "E MMM dd yyyy HH:mm:ss 'GMT'z", Locale.ENGLISH);
                Date date = inputFormat.parse(dob);

                DateFormat outputFormat = new SimpleDateFormat("MM-dd-yyyy");

                output = outputFormat.format(date);
            }
            if (groupId.equalsIgnoreCase("")) {
                Integer groupid = 0;
                Integer compare_id = 0;
                resobj = customAttributesdao.getResourcePoiValuesBylandId(objproject.getProjectnameid(), landid);

                if (resobj.size() > 0) {
                    for (ResourcePOIAttributeValues objlst : resobj) {

                        if (groupid <= objlst.getGroupid()) {
                            groupid = objlst.getGroupid() + 1;
                        }

                    }
                    compare_id = groupid - 1;

                    for (ResourcePOIAttributeValues objlst : resobj) {
                        if (objlst.getGroupid() == compare_id) {

                            newobj = new ResourcePOIAttributeValues();
//					newobj= objlst;
                            newobj.setAttributemaster(objlst.getAttributemaster());
                            newobj.setGeomtype(objlst.getGeomtype());
                            newobj.setProjectid(objlst.getProjectid());
                            newobj.setLandid(objlst.getLandid());
                            newobj.setGroupid(groupid);

                            if (objlst.getAttributemaster().getPoiattributemasterid() == 1) {
                                newobj.setAttributevalue(fName);
                                em.merge(newobj);
                            } else if (objlst.getAttributemaster().getPoiattributemasterid() == 2) {
                                newobj.setAttributevalue(mName);
                                em.merge(newobj);
                            } else if (objlst.getAttributemaster().getPoiattributemasterid() == 3) {
                                newobj.setAttributevalue(lName);
                                em.merge(newobj);
                            } else if (objlst.getAttributemaster().getPoiattributemasterid() == 4) {
                                newobj.setAttributevalue(output);
                                em.merge(newobj);
                            } else if (objlst.getAttributemaster().getPoiattributemasterid() == 5) {
                                newobj.setAttributevalue(relType);
                                em.merge(newobj);
                            } else if (objlst.getAttributemaster().getPoiattributemasterid() == 6) {
                                newobj.setAttributevalue(gender);
                                em.merge(newobj);
                            }
                        }
                    }
                } else {
                    if (!fName.equalsIgnoreCase("")) {
                        newobj = new ResourcePOIAttributeValues();
//							newobj= objlst;
                        AttributeMasterResourcePOI attmasterobj = new AttributeMasterResourcePOI();
                        attmasterobj.setPoiattributemasterid(1);
                        newobj.setAttributemaster(attmasterobj);
                        newobj.setGeomtype(geomName);
                        newobj.setProjectid(objproject.getProjectnameid());
                        newobj.setLandid(landid);
                        newobj.setGroupid(newGroupID);
                        newobj.setAttributevalue(fName);
                        em.merge(newobj);
                    }
                    if (!mName.equalsIgnoreCase("")) {
                        newobj = new ResourcePOIAttributeValues();
//							newobj= objlst;
                        AttributeMasterResourcePOI attmasterobj = new AttributeMasterResourcePOI();
                        attmasterobj.setPoiattributemasterid(2);
                        newobj.setAttributemaster(attmasterobj);
                        newobj.setGeomtype(geomName);
                        newobj.setProjectid(objproject.getProjectnameid());
                        newobj.setLandid(landid);
                        newobj.setGroupid(newGroupID);
                        newobj.setAttributevalue(mName);
                        em.merge(newobj);

                    }
                    if (!lName.equalsIgnoreCase("")) {
                        newobj = new ResourcePOIAttributeValues();
//							newobj= objlst;
                        AttributeMasterResourcePOI attmasterobj = new AttributeMasterResourcePOI();
                        attmasterobj.setPoiattributemasterid(3);
                        newobj.setAttributemaster(attmasterobj);
                        newobj.setGeomtype(geomName);
                        newobj.setProjectid(objproject.getProjectnameid());
                        newobj.setLandid(landid);
                        newobj.setGroupid(newGroupID);
                        newobj.setAttributevalue(lName);
                        em.merge(newobj);

                    }
                    if (!output.equalsIgnoreCase("")) {
                        newobj = new ResourcePOIAttributeValues();
//							newobj= objlst;
                        AttributeMasterResourcePOI attmasterobj = new AttributeMasterResourcePOI();
                        attmasterobj.setPoiattributemasterid(4);
                        newobj.setAttributemaster(attmasterobj);
                        newobj.setGeomtype(geomName);
                        newobj.setProjectid(objproject.getProjectnameid());
                        newobj.setLandid(landid);
                        newobj.setGroupid(newGroupID);
                        newobj.setAttributevalue(output);
                        em.merge(newobj);

                    }
                    if (!relType.equalsIgnoreCase("")) {
                        newobj = new ResourcePOIAttributeValues();
//							newobj= objlst;
                        AttributeMasterResourcePOI attmasterobj = new AttributeMasterResourcePOI();
                        attmasterobj.setPoiattributemasterid(5);
                        newobj.setAttributemaster(attmasterobj);
                        newobj.setGeomtype(geomName);
                        newobj.setProjectid(objproject.getProjectnameid());
                        newobj.setLandid(landid);
                        newobj.setGroupid(newGroupID);
                        newobj.setAttributevalue(relType);
                        em.merge(newobj);

                    }
                    if (!gender.equalsIgnoreCase("")) {
                        newobj = new ResourcePOIAttributeValues();
//							newobj= objlst;
                        AttributeMasterResourcePOI attmasterobj = new AttributeMasterResourcePOI();
                        attmasterobj.setPoiattributemasterid(6);
                        newobj.setAttributemaster(attmasterobj);
                        newobj.setGeomtype(geomName);
                        newobj.setProjectid(objproject.getProjectnameid());
                        newobj.setLandid(landid);
                        newobj.setGroupid(newGroupID);
                        newobj.setAttributevalue(gender);
                        em.merge(newobj);

                    }

                }

            } else {

                for (ResourcePOIAttributeValues objlst : resobj) {

                    if (objlst.getGroupid().toString().equalsIgnoreCase(groupId.toString())) {

                        if (objlst.getAttributemaster().getPoiattributemasterid() == 1) {
                            objlst.setAttributevalue(fName);
                            em.merge(objlst);
                        } else if (objlst.getAttributemaster().getPoiattributemasterid() == 2) {
                            objlst.setAttributevalue(mName);
                            em.merge(objlst);
                        } else if (objlst.getAttributemaster().getPoiattributemasterid() == 3) {
                            objlst.setAttributevalue(lName);
                            em.merge(objlst);
                        } else if (objlst.getAttributemaster().getPoiattributemasterid() == 4) {
                            objlst.setAttributevalue(output);
                            em.merge(objlst);
                        } else if (objlst.getAttributemaster().getPoiattributemasterid() == 5) {
                            objlst.setAttributevalue(relType);
                            em.merge(objlst);
                        } else if (objlst.getAttributemaster().getPoiattributemasterid() == 6) {
                            objlst.setAttributevalue(gender);
                            em.merge(objlst);
                        }
                    }
                }
            }

            return ResponseEntity.status(HttpStatus.CREATED).body(resobj);

        } catch (Exception e) {
            return null;
        }
    }

    @RequestMapping(value = "/viewer/resource/landdata/{landId}", method = RequestMethod.GET)
    @ResponseBody
    public Object getLanddataBylandId(@PathVariable Long landId) {
        try {
            Object obj = null;

            obj = lineDao.getLandObject(landId);
            if (null != obj) {
                return obj;
            } else {
                return null;
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value = "/viewer/resource/saveattributes", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public String savefinalsaledata(HttpServletRequest request, HttpServletResponse response) {

        try {
            Long landId = ServletRequestUtils.getRequiredLongParameter(request, "landId");
            String institutionValue = ServletRequestUtils.getRequiredStringParameter(request, "Institution_id");
            String institutionId = ServletRequestUtils.getRequiredStringParameter(request, "Institution_id_id");

            String membersId = ServletRequestUtils.getRequiredStringParameter(request, "members_id");
            String membersValue = ServletRequestUtils.getRequiredStringParameter(request, "members");
            List<ResourceAttributeValues> resobj = resourceAttributeValuesService.getResourceAttributeValuesByMasterlandid(landId.intValue());
            for (ResourceAttributeValues obj : resobj) {

                if (obj.getAttributevalueid() == Integer.parseInt(institutionId)) {
                    obj.setAttributevalue(institutionValue);
                    em.merge(obj);
                }
                if (obj.getAttributevalueid() == Integer.parseInt(membersId)) {
                    obj.setAttributevalue(membersValue);
                    em.merge(obj);
                }
            }

        } catch (ServletRequestBindingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return "Success";
    }

    @RequestMapping(value = "/viewer/resource/alloptions/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> getAllResourceOptions(@PathVariable Integer id) {
        List<Map> lst = new ArrayList<>();

        List<AttributeOptions> attoptions = attributeOptionsDao.getAttributeOptions(id.longValue());

        for (AttributeOptions obj : attoptions) {
            Map map = new HashMap<String, String>();
            map.put("id", obj.getParentid());
            map.put("name", obj.getOptiontext());
            lst.add(map);

        }

        return lst;
    }

    @RequestMapping(value = "/viewer/resource/relationshipTypes/", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> getRelationShipTypes() {
        List<Map> finallst = new ArrayList<>();
        List<RelationshipType> relationlst = new ArrayList<>();

        try {
            relationlst = realtionshipdao.findAll();

            if (relationlst.size() > 0) {
                for (RelationshipType obj : relationlst) {
                    if (obj.getIsactive()) {
                        Map<String, String> map = new HashMap<>();
                        map.put("id", obj.getRelationshiptypeid().toString());
                        map.put("name", obj.getRelationshiptypeEn());
                        finallst.add(map);
                    }
                }
            }
            return finallst;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value = "/viewer/resource/getBoundaryPoint/{id}", method = RequestMethod.GET)
    @ResponseBody
    public BoundaryPoint getBoundaryPoint(@PathVariable Integer id) {
        return boundaryService.getBoundaryPoint(id);
    }
    
    @RequestMapping(value = "/viewer/resource/getBoundaryPointsByProject/{projectId}", method = RequestMethod.GET)
    @ResponseBody
    public List<BoundaryPoint> getBoundaryPointsByProject(@PathVariable Integer projectId) {
        return boundaryService.getBoundaryPointsByProject(projectId);
    }

    @RequestMapping(value = "/viewer/resource/saveBoundaryPoint", method = RequestMethod.POST)
    @ResponseBody
    public boolean saveBoundaryPoint(HttpServletRequest request, Principal principal) {
        try {
            int id = ServletRequestUtils.getIntParameter(request, "hPointId", 0);
            if (id == 0) {
                return false;
            }
            BoundaryPoint point = boundaryService.getBoundaryPoint(id);

            if (point == null) {
                return false;
            }

            User user = userdao.findByName(principal.getName());
            int villageId = ServletRequestUtils.getIntParameter(request, "cbxPointVillageId", 0);
            int featureType = ServletRequestUtils.getIntParameter(request, "cbxPointFeatureType", 0);
            String featureDescription = ServletRequestUtils.getStringParameter(request, "txtPointFeatureDescription", null);
            int confidenceLevel = ServletRequestUtils.getIntParameter(request, "cbxPointConfidenceLevel", 0);
            boolean verified = ServletRequestUtils.getBooleanParameter(request, "chbxVerified", false);
            
            if (villageId > 0) {
                point.setNeighborVillageId(villageId);
            } else {
                point.setNeighborVillageId(null);
            }
            if (confidenceLevel > 0) {
                point.setConfidenceLevel(confidenceLevel);
            } else {
                point.setConfidenceLevel(null);
            }
            point.setVerified(verified);
            point.setFeatureType(featureType);
            point.setFeatureDescription(featureDescription);
            point.setModifiedBy((int)user.getId());
            point.setModifyDate(Calendar.getInstance().getTime());
            
            boundaryService.saveBoundaryPoint(point);
            return true;
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
            return false;
        }
    }

    @RequestMapping(value = "/viewer/resource/getBoundaryPointDocs/{pointId}", method = RequestMethod.GET)
    @ResponseBody
    public List<BoundaryPointDoc> getBoundaryPointDocs(@PathVariable Integer pointId) {
        return boundaryService.getBoundaryPointDocs(pointId);
    }

    @RequestMapping(value = "/viewer/resource/deleteBoundaryPointDoc/{id}", method = RequestMethod.GET)
    @ResponseBody
    public boolean deleteBoundaryPointDoc(@PathVariable Integer id) {
        return boundaryService.deleteBoundaryPointDoc(id);
    }

    @RequestMapping(value = "/viewer/resource/getProjectVillages/{projectId}", method = RequestMethod.GET)
    @ResponseBody
    public List<ProjectRegion> getProjectVillages(@PathVariable Integer projectId) {
        return projectRegion.getVillagesByProject(projectId);
    }
    
    @RequestMapping(value = "/viewer/resource/getProjectNeighborVillages/{projectId}", method = RequestMethod.GET)
    @ResponseBody
    public List<ProjectRegion> getProjectNeighborVillages(@PathVariable Integer projectId) {
        return projectRegion.getProjectNeighborVillages(projectId);
    }

    @RequestMapping(value = "/viewer/resource/getBoundary/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Boundary getBoundary(@PathVariable Integer id) {
        return boundaryService.getBoundary(id);
    }
    
    @RequestMapping(value = "/viewer/resource/getConfidenceLevels", method = RequestMethod.GET)
    @ResponseBody
    public List<ConfidenceLevel> getConfidenceLevels() {
        return confidenceLevelDao.getAll();
    }
    
    @RequestMapping(value = "/viewer/resource/getFeatureTypes", method = RequestMethod.GET)
    @ResponseBody
    public List<BoundaryFeatureType> getFeatureTypes() {
        return boundaryFeatureTypeDao.getAll();
    }
    
    @RequestMapping(value = "/viewer/resource/saveBoundary", method = RequestMethod.POST)
    @ResponseBody
    public boolean saveBoundary(HttpServletRequest request, Principal principal) {
        try {
            int id = ServletRequestUtils.getIntParameter(request, "hVillageBoundaryId", 0);
            User user = userdao.findByName(principal.getName());
            
            Boundary boundary;
            Date currentDate = Calendar.getInstance().getTime();
            
            if (id > 0) {
                boundary = boundaryService.getBoundary(id);

                if (boundary == null) {
                    return false;
                }
            } else {
                boundary = new Boundary();
                String geom = ServletRequestUtils.getStringParameter(request, "hVillageBoundaryGeom", null);
                int projectId = ServletRequestUtils.getIntParameter(request, "hVillageBoundaryProjectId", 0);
                
                if(geom == null || projectId < 1){
                    return false;
                }
                
                boundary.setGeometry(geom);
                boundary.setProjectId(projectId);
                boundary.setSurveyDate(currentDate);
                boundary.setIsactive(true);
                boundary.setCreatedBy((int)user.getId());
                boundary.setCreateDate(currentDate);
            }
            
            String leaderName = ServletRequestUtils.getStringParameter(request, "txtVillageLeader", null);
            int villageQuarters = ServletRequestUtils.getIntParameter(request, "txtVillageQuarters", 0);
            int villagePopulation = ServletRequestUtils.getIntParameter(request, "txtVillagePopulation", 0);
            
            boundary.setVillageLeader(leaderName);
            boundary.setQuartersNum(villageQuarters);
            boundary.setPopulation(villagePopulation);
            boundary.setModifiedBy((int)user.getId());
            boundary.setModifyDate(Calendar.getInstance().getTime());

            boundaryService.saveBoundary(boundary);
            return true;
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
            return false;
        }
    }
}
