

package com.rmsi.mast.studio.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.rmsi.mast.studio.domain.Layertype;

/**
 * @author Aparesh.Chakraborty
 *
 */
public interface LayertypeService {

	@Transactional
	Layertype addLayertype(Layertype layertype);
	
	@Transactional
	void deleteLayertype();

	@Transactional
	void deleteLayertypeById(Long id);

	@Transactional
	void updateLayertype(Layertype layertype);

	@Transactional(readOnly=true)
	Layertype findLayertypeById(Long id);

	@Transactional(readOnly=true)
	List<Layertype> findAllLayertype();
	
	@Transactional(readOnly=true)
	Layertype findLayertypeByName(String name);
	
}
