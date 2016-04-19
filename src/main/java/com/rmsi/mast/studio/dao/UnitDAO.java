

package com.rmsi.mast.studio.dao;

import java.util.List;

import com.rmsi.mast.studio.domain.Unit;

/**
 * @author Aparesh.Chakraborty
 *
 */


public interface UnitDAO extends GenericDAO<Unit, Long> {
	
	Unit findByName(String name);
	
	boolean deleteUnitByName(String name);
	
}

