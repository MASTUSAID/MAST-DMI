

package com.rmsi.mast.studio.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.rmsi.mast.studio.domain.Unit;

/**
 * @author Aparesh.Chakraborty
 *
 */
public interface UnitService {

	@Transactional
	void addUnit(Unit unit);
	
	@Transactional
	void deleteUnit();

	@Transactional
	void deleteUnitById(String name);

	@Transactional
	void updateUnit(Unit unit);

	//@Transactional(readOnly=true)
	Unit findUnitById(Long id);

	//@Transactional(readOnly=true)
	List<Unit> findAllUnit();
	
	
	Unit findUnitByName(String name);	
	
	@Transactional
	boolean deleteUnitByName(String id);
	
}
