

package com.rmsi.mast.studio.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rmsi.mast.studio.dao.ProjectionDAO;
import com.rmsi.mast.studio.domain.Projection;
import com.rmsi.mast.studio.service.ProjectionService;

/**
 * @author Aparesh.Chakraborty
 *
 */
@Service
public class ProjectionServiceImpl implements ProjectionService{

	@Autowired
	private ProjectionDAO projectionDAO;

	@Override
	public Projection addProjection(Projection Projection) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteProjection() {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteProjectionById(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateProjection(Projection Projection) {
		// TODO Auto-generated method stub

	}

	@Override
	public Projection findProjectionById(Long id) {
		return projectionDAO.findById(id, false);

	}

	@Override
	public List<Projection> findAllProjection() {
		return projectionDAO.findAll();
	}

	@Override
	public Projection findProjectionByName(String name) {
		return projectionDAO.findByName(name);
	}
	
}
