package com.rmsi.mast.viewer.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.hibernate.GenericHibernateDAO;
import com.rmsi.mast.studio.domain.LaSpatialunitLand;
import com.rmsi.mast.studio.domain.LandUseType;
import com.rmsi.mast.viewer.dao.LaSpatialunitLandDao;

@Repository
public class LaSpatialunitLandHibernateDao extends GenericHibernateDAO<LaSpatialunitLand, Integer>
implements LaSpatialunitLandDao{
	
	Logger logger = Logger.getLogger(SpatialUnitHibernateDAO.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public List<LaSpatialunitLand> getLaSpatialunitLandDetails(Long landid) {
		try {
			String query = "select ld.landid,ld.landno, ld.spatialunitgroupid1, ld.hierarchyid1, ld.spatialunitgroupid2, ld.hierarchyid2, ld.spatialunitgroupid3, ld.hierarchyid3, " +
							"ld.spatialunitgroupid4, ld.hierarchyid4, ld.spatialunitgroupid5, ld.hierarchyid5, ld.spatialunitgroupid6, ld.hierarchyid6, ld.landtypeid, ld.landusetypeid, "+ 
							"ld.landsoilqualityid, ld.claimtypeid, ld.landsharetypeid, ld.unitid, ld.area, lt.landusetype_en, ld.projectnameid, " +
							"ld.acquisitiontypeid, ld.tenureclassid, ld.slopevalueid, ld.neighbor_east, ld.neighbor_west, ld.neighbor_north, ld.neighbor_south, " +
							"ld.surveydate, ld.geometrytype,ld.proposedused from la_spatialunit_land ld" +
							" inner Join la_baunit_landusetype lt on ld.landusetypeid = lt.landusetypeid" +
							" where landid = " + landid + " and ld.isactive = true"; //, ld.geometry
			List<Object[]> arrObject = getEntityManager().createNativeQuery(query).getResultList();
			List<LaSpatialunitLand> lstLaSpatialunitLands = new ArrayList<LaSpatialunitLand>();
			for(Object [] object : arrObject){
				LaSpatialunitLand laSpatialunitLand = new LaSpatialunitLand();
				laSpatialunitLand.setLandid(Long.valueOf(object[0].toString()));
				laSpatialunitLand.setLandno((String)object[1]);
				
				if(object[2] != null && !object[2].toString().isEmpty())
					laSpatialunitLand.setSpatialunitgroupid1(Integer.parseInt(object[2].toString()));
				if(object[3] != null && !object[3].toString().isEmpty())
					laSpatialunitLand.setHierarchyid1(Integer.parseInt(object[3].toString()));
				if(object[4] != null && !object[4].toString().isEmpty())
					laSpatialunitLand.setSpatialunitgroupid2(Integer.parseInt(object[4].toString()));
				if(object[5] != null && !object[5].toString().isEmpty())
					laSpatialunitLand.setHierarchyid2(Integer.parseInt(object[5].toString()));
				if(object[6] != null && !object[6].toString().isEmpty())
					laSpatialunitLand.setSpatialunitgroupid3(Integer.parseInt(object[6].toString()));
				if(object[7] != null && !object[7].toString().isEmpty())
					laSpatialunitLand.setHierarchyid3(Integer.parseInt(object[7].toString()));
				if(object[8] != null && !object[8].toString().isEmpty())
					laSpatialunitLand.setSpatialunitgroupid4(Integer.parseInt(object[8].toString()));
				if(object[9] != null && !object[9].toString().isEmpty())
					laSpatialunitLand.setHierarchyid4(Integer.parseInt(object[9].toString()));
				if(object[10] != null && !object[10].toString().isEmpty())
					laSpatialunitLand.setSpatialunitgroupid5(Integer.parseInt(object[10].toString()));
				if(object[11] != null && !object[11].toString().isEmpty())
					laSpatialunitLand.setHierarchyid5(Integer.parseInt(object[11].toString()));
				if(object[12] != null && !object[12].toString().isEmpty())
					laSpatialunitLand.setSpatialunitgroupid6(Integer.parseInt(object[12].toString()));
				if(object[13] != null && !object[13].toString().isEmpty())
					laSpatialunitLand.setHierarchyid6(Integer.parseInt(object[13].toString()));
				if(object[14] != null && !object[14].toString().isEmpty())
					laSpatialunitLand.setLandtypeid(Integer.parseInt(object[14].toString()));
				if(object[15] != null && !object[15].toString().isEmpty())
					laSpatialunitLand.setLandusetypeid(Integer.parseInt(object[15].toString()));
				if(object[16] != null && !object[16].toString().isEmpty())
					laSpatialunitLand.setLandsoilqualityid(Integer.parseInt(object[16].toString()));
				
				//laSpatialunitLand.setClaimtypeid(object[17].toString())
				
				if(object[17] != null && !object[17].toString().isEmpty())
					laSpatialunitLand.setClaimtypeid(Integer.parseInt(object[17].toString()));
				
				
				if(object[18] != null && !object[18].toString().isEmpty())
				laSpatialunitLand.setLandsharetypeid(Integer.parseInt(object[18].toString()));
				
				//laSpatialunitLand.setUnitid(Integer.parseInt(object[19].toString()));
				if(object[20] != null && !object[20].toString().isEmpty())
					laSpatialunitLand.setArea(Double.parseDouble(object[20].toString()));
				
				if(object[21] != null && !object[21].toString().isEmpty())
					laSpatialunitLand.setLandusetype_en(object[21].toString());
				
				
				
				if(object[22] != null && !object[22].toString().isEmpty())
					laSpatialunitLand.setProjectnameid(Integer.parseInt(object[22].toString()));
				
				if(object[23] != null && !object[23].toString().isEmpty())
					laSpatialunitLand.setAcquisitiontypeid(Integer.parseInt(object[23].toString()));
				
				if(object[24] != null && !object[24].toString().isEmpty())
					laSpatialunitLand.setTenureclassid(Integer.parseInt(object[24].toString()));
				
				if(object[25] != null && !object[25].toString().isEmpty())
					laSpatialunitLand.setSlopevalueid(Integer.parseInt(object[25].toString()));
				//4 east west nort south
				if(object[26] != null && !object[26].toString().isEmpty())
					laSpatialunitLand.setNeighbor_east(object[26].toString());
				if(object[27] != null && !object[27].toString().isEmpty())
					laSpatialunitLand.setNeighbor_west(object[27].toString());
				if(object[28] != null && !object[28].toString().isEmpty())
					laSpatialunitLand.setNeighbor_north(object[28].toString());
				
				if(object[29] != null && !object[29].toString().isEmpty())
						laSpatialunitLand.setNeighbor_south(object[29].toString());
				
				if(object[32] != null && !object[32].toString().isEmpty())
					laSpatialunitLand.setProposedused(Integer.parseInt(object[32].toString()));
				
				
				
				lstLaSpatialunitLands.add(laSpatialunitLand);
			}
			return lstLaSpatialunitLands;
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return null;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LaSpatialunitLand> getLaSpatialunitLandDetailsQ(Integer landid) {
		Query query= getEntityManager().createQuery("select LU from LaSpatialunitLand LU where LU.landid =:landId");
		try {
			List<LaSpatialunitLand> lstLaSpatialunitLands = query.setParameter("landId", Long.valueOf(landid  + "")).getResultList();
			return lstLaSpatialunitLands;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			throw e;
		}
	}

	@Override
	public boolean updateLaSpatialunitLand(LaSpatialunitLand laSpatialunitLand) {
		String query = "update la_spatialunit_land set isactive = true where landid = "+ laSpatialunitLand.getLandid() + " and isactive = true";
		int update = getEntityManager().createNativeQuery(query).executeUpdate();
		if(update>0)
			return true;
		else
			return false;
		
	}

	@Override
	public boolean addLaSpatialunitLand(LaSpatialunitLand laSpatialunitLand) {
		
		return false;
	}

}
