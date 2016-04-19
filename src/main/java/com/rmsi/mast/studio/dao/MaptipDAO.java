
package com.rmsi.mast.studio.dao;



import java.util.List;





import com.rmsi.mast.studio.domain.Maptip;

/**
 * @author Aparesh.Chakraborty
 *
 */


public interface MaptipDAO extends GenericDAO<Maptip, Long> {
	
	Maptip findByName(String name);

	List<Maptip> getMaptipsByLayer(String layer);
	
	boolean deleteMaptipByName(String name);
	
	boolean deleteMaptipByProject(String name);
	
	boolean checkIfKeyExists(String project, String layer);
	
	Maptip getMaptipByPK(String project, String layer);
	
	List<Maptip> getMaptipsByProject(String project);
}

