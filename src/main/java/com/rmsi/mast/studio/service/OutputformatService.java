

package com.rmsi.mast.studio.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.rmsi.mast.studio.domain.Outputformat;

/**
 * @author Aparesh.Chakraborty
 *
 */
public interface OutputformatService {

	@Transactional
	Outputformat addOutputformat(Outputformat outputformat);
	
	@Transactional
	void deleteOutputformat();

	@Transactional
	void deleteOutputformatById(Long id);

	@Transactional
	void updateOutputformat(Outputformat outputformat);

	@Transactional(readOnly=true)
	Outputformat findOutputformatById(Long id);

	@Transactional(readOnly=true)
	List<Outputformat> findAllOutputformat();
	
	
	Outputformat findOutputformatByName(String name);
	
}
