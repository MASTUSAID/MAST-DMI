

package com.rmsi.mast.studio.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.rmsi.mast.studio.domain.Action;
import com.rmsi.mast.studio.domain.Baselayer;

public interface BaselayerService {
	
	
	@Transactional(readOnly=true)
	List<Baselayer> findAllBaselayer();


}
