

package com.rmsi.mast.studio.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;










import com.rmsi.mast.studio.dao.ActionDAO;
import com.rmsi.mast.studio.domain.Action;
import com.rmsi.mast.studio.service.ActionService;

@Service
public class ActionServiceImpl implements ActionService {

	@Autowired
	private ActionDAO actionDAO;

	@Override
	public Action addAction(Action action) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteAction() {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteActionByName(String id) {
		actionDAO.deleteByName(id);
	}

	@Override
	public void updateAction(Action action) {
		// TODO Auto-generated method stub

	}

	@Override
	public Action findActionById(Long id) {
		return actionDAO.findById(id, false);

	}

	@Override
	public List<Action> findAllActions() {
		
		return actionDAO.findAll();
	}

	@Override
	public Action findActionByName(String name) {
		return actionDAO.findByName(name);
	}

	
}
