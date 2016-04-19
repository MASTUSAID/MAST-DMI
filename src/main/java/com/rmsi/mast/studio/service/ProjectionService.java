

package com.rmsi.mast.studio.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.rmsi.mast.studio.domain.Projection;

/**
 * @author Aparesh.Chakraborty
 *
 */
public interface ProjectionService {

	@Transactional
	Projection addProjection(Projection rojection);
	
	@Transactional
	void deleteProjection();

	@Transactional
	void deleteProjectionById(Long id);

	@Transactional
	void updateProjection(Projection rojection);

	@Transactional(readOnly=true)
	Projection findProjectionById(Long id);

	@Transactional(readOnly=true)
	List<Projection> findAllProjection();
	
	
	Projection findProjectionByName(String name);
	
}
