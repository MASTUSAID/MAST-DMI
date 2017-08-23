/**
 * 
 */
package com.rmsi.mast.viewer.dao;

import java.util.List;

import com.rmsi.mast.studio.dao.GenericDAO;
import com.rmsi.mast.studio.domain.fetch.SpatialUnitTable;

/**
 * @author Shruti.Thakur
 *
 */
public interface LandRecordsDao extends GenericDAO<SpatialUnitTable, Long> {

	List<SpatialUnitTable> findallspatialUnit(String defaultProject);

	boolean updateApprove(Long id);

	boolean rejectStatus(Long id);

	List<SpatialUnitTable> search(String usinStr, String ukaNumber,String
			projname, String dateto, String datefrom, Long status, Integer startpos);

	List<SpatialUnitTable> findSpatialUnitById(Long id);

	String findukaNumberByUsin(Long id);

	boolean updateFinal(Long id);

	boolean updateAdjudicated(Long id);


	boolean deleteSpatial(Long id);

	Integer searchSize(String usinStr, String ukaNumber, String projname,
			String dateto, String datefrom, Long status);

	List<SpatialUnitTable> getSpatialUnitByBbox(String bbox, String project_name);

	boolean findExistingHamlet(long hamlet_id);

	boolean deleteAllVertexLabel();

	boolean addAllVertexLabel(int k, String lat, String lon);



}
