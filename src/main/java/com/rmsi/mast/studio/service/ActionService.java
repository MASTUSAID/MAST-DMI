

package com.rmsi.mast.studio.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.rmsi.mast.studio.domain.Action;

public interface ActionService {
	
	@Transactional
	Action addAction(Action action);

	@Transactional
	void deleteAction();

	@Transactional
	void deleteActionByName(String id);

	@Transactional
	void updateAction(Action action);

	@Transactional(readOnly=true)
	Action findActionById(Long id);

	@Transactional(readOnly=true)
	List<Action> findAllActions();

	@Transactional(readOnly=true)
	Action findActionByName(String name);

}
