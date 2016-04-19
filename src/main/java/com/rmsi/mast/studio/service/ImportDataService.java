

package com.rmsi.mast.studio.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.rmsi.mast.studio.domain.Action;

public interface ImportDataService {
	
	@Transactional
	Action addAction(Action action);

}
