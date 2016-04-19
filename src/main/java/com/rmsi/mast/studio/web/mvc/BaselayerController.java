
package com.rmsi.mast.studio.web.mvc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rmsi.mast.studio.domain.Action;
import com.rmsi.mast.studio.domain.Baselayer;
import com.rmsi.mast.studio.service.ActionService;
import com.rmsi.mast.studio.service.BaselayerService;

@Controller
public class BaselayerController {

	@Autowired
	BaselayerService baselayerService;
	
	@RequestMapping(value = "/studio/baselayer/", method = RequestMethod.GET)
	@ResponseBody
    public List<Baselayer> list(){
		return 	baselayerService.findAllBaselayer();	
	}
	
}
