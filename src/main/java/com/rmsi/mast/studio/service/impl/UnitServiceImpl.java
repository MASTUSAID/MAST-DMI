

package com.rmsi.mast.studio.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rmsi.mast.studio.dao.UnitDAO;
import com.rmsi.mast.studio.domain.Unit;
import com.rmsi.mast.studio.domain.User;
import com.rmsi.mast.studio.service.UnitService;

/**
 * @author Aparesh.Chakraborty
 *
 */
@Service
public class UnitServiceImpl implements UnitService{

	@Autowired
	private UnitDAO unitDAO;

	@Override
	public void addUnit(Unit unit) {
		unitDAO.makePersistent(unit);
		//return null;
	}

	@Override
	public void deleteUnit() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean deleteUnitByName(String unitName) {		
		return unitDAO.deleteUnitByName(unitName);				

	}

	@Override
	public void updateUnit(Unit unit) {
		// TODO Auto-generated method stub

	}

	@Override
	public Unit findUnitById(Long id) {
		return unitDAO.findById(id, false);

	}

	@Override
	public List<Unit> findAllUnit() {
		return unitDAO.findAll();
	}

	@Override
	public Unit findUnitByName(String name) {
		return unitDAO.findByName(name);
	}

	@Override
	public void deleteUnitById(String name) {
		// TODO Auto-generated method stub
		
	}
	
}
