package com.rmsi.mast.studio.mobile.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.rmsi.mast.studio.domain.SpatialUnit;
import com.rmsi.mast.studio.mobile.service.SpatialDataService;

@RestController
public class SpatialUnitController {

	@Autowired
	SpatialDataService spatialDataService;
	
	/**
	 * This method will add spatial unit data to the table "Spatial_Unit"
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="studio/mobile/spatialunit/add", method=RequestMethod.POST)
	public @ResponseBody SpatialUnit addOrSaveSpatialUnit(@RequestBody SpatialUnit spatialUnit){

		System.out.println("Output ::"+ spatialUnit.getArea());
		
		return spatialUnit;
	}
	
	/**
	 * This method will add spatial unit data to the table "Spatial_Unit"
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="studio/mobile/spatialunit/addList", method=RequestMethod.POST)
	public @ResponseBody List<SpatialUnit> addOrSaveSpatialUnit(@RequestBody List<SpatialUnit> spatialUnit){
		
		return spatialUnit;
	}

}
