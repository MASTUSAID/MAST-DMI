package com.rmsi.mast.studio.dao.hibernate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.exolab.castor.types.Date;
import org.geotools.geometry.jts.WKTWriter2;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.LaSpatialunitAoiDAO;
import com.rmsi.mast.studio.dao.ResourceClassificationDAO;
import com.rmsi.mast.studio.domain.LaSpatialunitAOI;
import com.rmsi.mast.studio.domain.ResourceClassification;
import com.rmsi.mast.studio.domain.SpatialUnit;
import com.rmsi.mast.studio.util.GeometryConversion;
import com.vividsolutions.jts.io.WKTReader;
import com.vividsolutions.jts.io.WKTWriter;

@Repository
public class LaSpatialunitAoiHibernateDAO extends GenericHibernateDAO<LaSpatialunitAOI, Integer> implements LaSpatialunitAoiDAO {

	@Override
	public List<LaSpatialunitAOI> getByUserId(Integer Id) {
        try {
            //String hql = "SELECT  ST_AsText(geometry),landno FROM la_spatialunit_land where landid = " + landid;
            
    		List<LaSpatialunitAOI> LaSpatialunitAoi = new ArrayList<LaSpatialunitAOI>();

        	
            String hql = "Select ST_AsText(slu.geometry), slu.projectnameid, slu.userid, slu.aoiid, slu.createddate, slu.modifieddate, slu.applicationstatusid, slu.isactive From la_spatialunit_aoi slu"
            		+ "   Where slu.userid = "+Id+" and slu.applicationstatusid="+ 1;
            
            List<Object[]> lstObject = getEntityManager().createNativeQuery(hql).getResultList();
           
            for(Object[] arrObj : lstObject){
            	 LaSpatialunitAOI spatialUnit = new LaSpatialunitAOI();
            	spatialUnit.setGeomStr(arrObj[0].toString());
            	spatialUnit.setProjectnameid(Integer.parseInt(arrObj[1].toString()));
            	spatialUnit.setUserid(Integer.parseInt(arrObj[2].toString()));
            	spatialUnit.setAoiid(Integer.parseInt(arrObj[3].toString()));
            	
            	spatialUnit.setCreatedby(Integer.parseInt(arrObj[2].toString()));
            	spatialUnit.setModifiedby(Integer.parseInt(arrObj[2].toString()));
            	if(arrObj[4] != null){
               	spatialUnit.setCreateddate(new SimpleDateFormat("yyyy-MM-dd").parse(arrObj[4].toString()));
            	}
            	if(arrObj[5] != null){
            	spatialUnit.setModifieddate(new SimpleDateFormat("yyyy-MM-dd").parse(arrObj[5].toString()));
            	}
            	spatialUnit.setApplicationstatusid(Integer.parseInt(arrObj[6].toString()));
            	spatialUnit.setIsactive((Boolean) arrObj[7]);
            	LaSpatialunitAoi.add(spatialUnit);
            }
            return LaSpatialunitAoi;
        } catch (Exception e) {
           e.printStackTrace();
            return null;
        }
    }


}
