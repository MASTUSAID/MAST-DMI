package com.rmsi.mast.studio.service;

import org.springframework.transaction.annotation.Transactional;

public interface AttributeOptionsService {
	
	@Transactional
	boolean deleteAttributeOptionsbyId(Long id);

}
