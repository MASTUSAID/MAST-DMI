package com.rmsi.mast.studio.web.mvc;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rmsi.mast.studio.domain.AttributeCategory;
import com.rmsi.mast.studio.domain.AttributeCategoryType;
import com.rmsi.mast.studio.domain.AttributeMaster;
import com.rmsi.mast.studio.domain.AttributeOptions;
import com.rmsi.mast.studio.domain.Baselayer;
import com.rmsi.mast.studio.domain.DatatypeId;
import com.rmsi.mast.studio.domain.ProjectBaselayer;
import com.rmsi.mast.studio.service.AttributeCategoryService;
import com.rmsi.mast.studio.service.AttributeCategoryTypeService;
import com.rmsi.mast.studio.service.AttributeMasterService;
import com.rmsi.mast.studio.service.AttributeOptionsService;
import com.rmsi.mast.studio.service.DataTypeIdService;

@Controller
public class AttributeMasterController {

    private static final Logger logger = Logger.getLogger(AttributeMasterController.class);

    @Autowired
    private AttributeMasterService masterAttributeService;

    @Autowired
    private DataTypeIdService dataTypeIdservice;

    @Autowired
    private AttributeCategoryService attributecategoryService;
    
    
    @Autowired
    private AttributeCategoryTypeService attributeCategoryTypeService;
    
    
    
    @Autowired
    private AttributeOptionsService attributeOptionsService;

    @RequestMapping(value = "/studio/masterattrib/", method = RequestMethod.GET)
    @ResponseBody
    public List<AttributeMaster> list() {
        return masterAttributeService.findAllAttributeMasater();
    }
    
    @RequestMapping(value = "/studio/masterattributes/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<AttributeMaster> list(@PathVariable Integer id) {
        return masterAttributeService.getAttributeMasterByAttributeMasterId(id);
    }
    
    
    
    @RequestMapping(value = "/studio/attribcategoryType/", method = RequestMethod.GET)
    @ResponseBody
    public List<AttributeCategoryType> getAttributeCategoryTypelist() {
        return attributeCategoryTypeService.getAllAttributeCategoryType();
    }

    
    
    

    @RequestMapping(value = "/studio/masterattrib/create", method = RequestMethod.POST)
    @ResponseBody
    public String createMasterAttribute(HttpServletRequest request, HttpServletResponse response) {
        long id = 0;
        long dataTypeId = 0L;
        long categoryId = 0;
        String size = "";
        String fieldName = "";
        String alias = "";
        String alias_otherlang = "";
        String mandatory = "";
        String attribute_option[] = null;

        AttributeMaster attributemaster = new AttributeMaster();
        try {

            dataTypeId = ServletRequestUtils.getRequiredIntParameter(request, "type");
            alias = ServletRequestUtils.getRequiredStringParameter(request, "alias");
            size = ServletRequestUtils.getRequiredStringParameter(request, "size");
            fieldName = ServletRequestUtils.getRequiredStringParameter(request, "fieldName");
            categoryId = ServletRequestUtils.getRequiredIntParameter(request, "category");
            alias_otherlang = ServletRequestUtils.getRequiredStringParameter(request, "alias_other");
            try {
                mandatory = ServletRequestUtils.getRequiredStringParameter(request, "mandatory");
            } catch (Exception e) {

                logger.error(e);
            }

            attributemaster.setFieldaliasname(alias);
            attributemaster.setIsactive(true);
            attributemaster.setReferencetable("custom");
            attributemaster.setSize(size);
            if (mandatory != "") {
                attributemaster.setMandatory(Boolean.parseBoolean(mandatory.toString()));
            }
            if (mandatory == "") {
                attributemaster.setMandatory(false);

            }

            //attributemaster.setMaster_attrib(false);
            attributemaster.setFieldname(fieldName);
         //   attributemaster.setAlias_second_language(alias_otherlang);
            if (dataTypeId != 0) {
                DatatypeId datatypeIdObj = masterAttributeService.findDataTypeById(dataTypeId);
                attributemaster.setLaExtAttributedatatype(datatypeIdObj);
            }

            if (categoryId != 0) {
                AttributeCategory categoryIdObj = masterAttributeService.findCategoryById(categoryId);
                attributemaster.setLaExtAttributecategory(categoryIdObj);
            }
            attributemaster.setListing("1");
            
            List<AttributeOptions> options = new ArrayList<AttributeOptions>();
            
            try{
            	attribute_option = request.getParameterValues("selected_options");
            	
            	
            }catch(Exception e)
            {
            	e.printStackTrace();
            }
            
            if (attribute_option != null) {
                for (int j = 0; j < attribute_option.length; j++) {
                	AttributeOptions ObjAttributeOptions = new AttributeOptions();
                	ObjAttributeOptions.setOptiontext(attribute_option[j]);
                	ObjAttributeOptions.setAttributeMaster(attributemaster);
                	options.add(ObjAttributeOptions);
                }
                
                attributemaster.setOptions(options); 
            }
            

            boolean value1 = masterAttributeService.checkduplicate(categoryId, alias, fieldName);

            if (value1) {
                return "duplicate";
            } else {
                masterAttributeService.addAttributeMaster(attributemaster);
                return "true";
            }
        } catch (Exception e) {
            logger.error(e);
            return "false";
        }

    }

    @RequestMapping(value = "/studio/dataType/", method = RequestMethod.GET)
    @ResponseBody
    public List<DatatypeId> listdata() {
        List<DatatypeId> Datatypelst = new ArrayList<DatatypeId>();

        try {

            Datatypelst = dataTypeIdservice.findallDataType();

        } catch (Exception e) {

            logger.error(e);
            return Datatypelst;
        }

        return Datatypelst;

    }

    @RequestMapping(value = "/studio/attribcategory/", method = RequestMethod.GET)
    @ResponseBody
    public List<AttributeCategory> listcategory() {
        List<AttributeCategory> categorylst = new ArrayList<AttributeCategory>();

        try {

            categorylst = attributecategoryService.findallAttributeCategories();

        } catch (Exception e) {

            logger.error(e);
            return categorylst;
        }

        return categorylst;

    }
    
    
    @RequestMapping(value = "/studio/attribcategoryById/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<AttributeCategory> AttributeCategoryByTypeid(@PathVariable Integer id) {
    	   List<AttributeCategory> categorylst = new ArrayList<AttributeCategory>();

           try {

               categorylst = attributecategoryService.findAttributeCategoryByTypeId(id);

           } catch (Exception e) {

               logger.error(e);
               return categorylst;
           }

           return categorylst;
    }
    

    @RequestMapping(value = "/studio/masterattrib/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public boolean deleteAttribute(@PathVariable Long id) {

        boolean val1 = masterAttributeService.checkdeleteAttribute(id);
        if (val1 == true) {

            return false;

        } else {
           	attributeOptionsService.deleteAttributeOptionsbyId(id);
            masterAttributeService.deleteAttribute(id);
            return true;
        }

    }

    @RequestMapping(value = "/studio/masterattrib/display/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<AttributeMaster> displaySelectedCategory(@PathVariable Long id) {

        return masterAttributeService.displaySelectedCategory(id);

    }

//    @RequestMapping(value = "/studio/masterattrib/{id}", method = RequestMethod.GET)
//    @ResponseBody
//    public List<AttributeMaster> editAttribute(@PathVariable Long id) {
//
//        return masterAttributeService.findbyAttributeId(id);
//
//    }

    @RequestMapping(value = "/studio/masterattrib/updateattribute", method = RequestMethod.POST)
    @ResponseBody
    public String editMasterAttribute(HttpServletRequest request, HttpServletResponse response) {
        long id = 0;
        long dataTypeId = 0L;
        long categoryId = 0;
        String size = "";
        String fieldName = "";
        String alias = "";
        String alias_otherlang = "";
        String attribute_option[] = null;
        
        AttributeMaster attributemaster = new AttributeMaster();
        try {
            id = ServletRequestUtils.getRequiredLongParameter(request, "primeryky");
            dataTypeId = ServletRequestUtils.getRequiredIntParameter(request, "type");
            alias = ServletRequestUtils.getRequiredStringParameter(request, "alias");
            size = ServletRequestUtils.getRequiredStringParameter(request, "size");
            fieldName = ServletRequestUtils.getRequiredStringParameter(request, "fieldName");
            categoryId = ServletRequestUtils.getRequiredIntParameter(request, "category");
            alias_otherlang = ServletRequestUtils.getRequiredStringParameter(request, "alias_other");

            if (id != 0) {
                attributemaster.setAttributemasterid(id);

            }

            if (masterAttributeService.checkeditduplicate(id, categoryId, alias, fieldName)) {
                return "duplicate";

            }
            AttributeMaster attributelst = masterAttributeService.findbyAttributeId(id);
            attributemaster = attributelst;
            attributemaster.setFieldaliasname(alias);
            attributemaster.setSize(size);

            try {
                attributemaster.setMandatory(Boolean.parseBoolean(ServletRequestUtils
                        .getRequiredStringParameter(request, "mandatory").toString()));
            } catch (Exception e) {

                logger.error(e);
            }
            attributemaster.setFieldname(fieldName);
            
            if(attributemaster.getLaExtAttributedatatype().getDatatypeId()==6){
            	attributeOptionsService.deleteAttributeOptionsbyId(attributemaster.getAttributemasterid());
            }
           // attributemaster.setAlias_second_language(alias_otherlang);
            if (dataTypeId != 0) {
                DatatypeId datatypeIdObj = masterAttributeService.findDataTypeById(dataTypeId);
                attributemaster.setLaExtAttributedatatype(datatypeIdObj);
            }

            if (categoryId != 0) {
                AttributeCategory categoryIdObj = masterAttributeService.findCategoryById(categoryId);
                attributemaster.setLaExtAttributecategory(categoryIdObj);
            }
            
            List<AttributeOptions> options = new ArrayList<AttributeOptions>();

            try{
            	attribute_option = request.getParameterValues("selected_options");
            	
            	
            }catch(Exception e)
            {
            	e.printStackTrace();
            }
            
            if (attribute_option != null) {
                for (int j = 0; j < attribute_option.length; j++) {
                	AttributeOptions ObjAttributeOptions = new AttributeOptions();
                	ObjAttributeOptions.setOptiontext(attribute_option[j]);
                	ObjAttributeOptions.setAttributeMaster(attributemaster);
                	options.add(ObjAttributeOptions);
                }
                
                attributemaster.setOptions(options); 
            }
            

            masterAttributeService.addAttributeMaster(attributemaster);
            return "true";

        } catch (Exception e) {
            logger.error(e);
            return "false";
        }

    }
}
