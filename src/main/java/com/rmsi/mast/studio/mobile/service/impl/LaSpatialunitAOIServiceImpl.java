package com.rmsi.mast.studio.mobile.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rmsi.mast.studio.dao.LaSpatialunitAoiDAO;
import com.rmsi.mast.studio.domain.LaSpatialunitAOI;
import com.rmsi.mast.studio.mobile.service.LaSpatialunitAOIService;
import com.rmsi.mast.studio.util.GeometryConversion;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;

@Service
public class LaSpatialunitAOIServiceImpl implements LaSpatialunitAOIService {
	
	@Autowired
	LaSpatialunitAoiDAO laSpatialunitAoidAO;

	@Override
	public List<LaSpatialunitAOI> getAllClassifications() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LaSpatialunitAOI> getByUserId(Integer Id) {
		// TODO Auto-generated method stub
		List<LaSpatialunitAOI> LaSpatialunitAoi = new ArrayList<LaSpatialunitAOI>();
		List<LaSpatialunitAOI> laSpatialunitAoi = laSpatialunitAoidAO.getByUserId(Id);
		for(LaSpatialunitAOI LaSpatialunitaoi: laSpatialunitAoi){
		String newString = LaSpatialunitaoi.getGeomStr().replace("POLYGON((", "");
		String newString2 = newString.replace("))", "");
		LaSpatialunitaoi.setGeomStr(newString2);
		LaSpatialunitAoi.add(LaSpatialunitaoi);
		}
		return LaSpatialunitAoi;
	}

}
