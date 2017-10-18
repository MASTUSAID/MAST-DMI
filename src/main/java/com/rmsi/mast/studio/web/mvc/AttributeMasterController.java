package com.rmsi.mast.studio.web.mvc;

import java.util.ArrayList;
import java.util.List;
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
import com.rmsi.mast.studio.domain.AttributeMaster;
import com.rmsi.mast.studio.domain.DatatypeId;
import com.rmsi.mast.studio.service.AttributeCategoryService;
import com.rmsi.mast.studio.service.AttributeMasterService;
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

    @RequestMapping(value = "/studio/masterattrib/", method = RequestMethod.GET)
    @ResponseBody
    public List<AttributeMaster> list() {
        return masterAttributeService.findAllAttributeMasater();
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

            attributemaster.setAlias(alias);
            attributemaster.setActive(true);
            attributemaster.setReftable("custom");
            attributemaster.setSize(size);
            if (mandatory != "") {
                attributemaster.setMandatory(Boolean.parseBoolean(mandatory.toString()));
            }
            if (mandatory == "") {
                attributemaster.setMandatory(false);

            }

            attributemaster.setMaster_attrib(false);
            attributemaster.setFieldname(fieldName);
            attributemaster.setAlias_second_language(alias_otherlang);
            if (dataTypeId != 0) {
                DatatypeId datatypeIdObj = masterAttributeService.findDataTypeById(dataTypeId);
                attributemaster.setDatatypeIdBean(datatypeIdObj);
            }

            if (categoryId != 0) {
                AttributeCategory categoryIdObj = masterAttributeService.findCategoryById(categoryId);
                attributemaster.setAttributeCategory(categoryIdObj);
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

    @RequestMapping(value = "/studio/masterattrib/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public boolean deleteAttribute(@PathVariable Long id) {

        boolean val1 = masterAttributeService.checkdeleteAttribute(id);
        if (val1 == true) {

            return false;

        } else {
            masterAttributeService.deleteAttribute(id);
            return true;
        }

    }

    @RequestMapping(value = "/studio/masterattrib/display/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<AttributeMaster> displaySelectedCategory(@PathVariable Long id) {

        return masterAttributeService.displaySelectedCategory(id);

    }

    @RequestMapping(value = "/studio/masterattrib/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<AttributeMaster> editAttribute(@PathVariable Long id) {

        return masterAttributeService.findbyAttributeId(id);

    }

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
                attributemaster.setId(id);

            }

            if (masterAttributeService.checkeditduplicate(id, categoryId, alias, fieldName)) {
                return "duplicate";

            }
            List<AttributeMaster> attributelst = masterAttributeService.findbyAttributeId(id);
            attributemaster = attributelst.get(0);
            attributemaster.setAlias(alias);
            attributemaster.setSize(size);

            try {
                attributemaster.setMandatory(Boolean.parseBoolean(ServletRequestUtils
                        .getRequiredStringParameter(request, "mandatory").toString()));
            } catch (Exception e) {

                logger.error(e);
            }
            attributemaster.setFieldname(fieldName);
            attributemaster.setAlias_second_language(alias_otherlang);
            if (dataTypeId != 0) {
                DatatypeId datatypeIdObj = masterAttributeService.findDataTypeById(dataTypeId);
                attributemaster.setDatatypeIdBean(datatypeIdObj);
            }

            if (categoryId != 0) {
                AttributeCategory categoryIdObj = masterAttributeService.findCategoryById(categoryId);
                attributemaster.setAttributeCategory(categoryIdObj);
            }

            masterAttributeService.addAttributeMaster(attributemaster);
            return "true";

        } catch (Exception e) {
            logger.error(e);
            return "false";
        }

    }
}
