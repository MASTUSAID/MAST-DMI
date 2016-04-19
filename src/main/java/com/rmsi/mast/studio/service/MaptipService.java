

package com.rmsi.mast.studio.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.rmsi.mast.studio.domain.Maptip;

/**
 * @author Aparesh.Chakraborty
 *
 */
public interface MaptipService {

	@Transactional
	Maptip addMaptip(Maptip maptip);
	
	@Transactional
	void deleteMaptip();

	@Transactional
	boolean deleteMaptipByName(String name);

	@Transactional
	Maptip updateMaptip(Maptip maptip);

	@Transactional(readOnly=true)
	Maptip findMaptipById(Long id);

	@Transactional(readOnly=true)
	List<Maptip> findAllMaptip();
	
	Maptip findMaptipByName(String name);
	List<Maptip> getMaptipsByLayer(String name);
	Maptip findMaptipbyPK(String project, String layer);
}
