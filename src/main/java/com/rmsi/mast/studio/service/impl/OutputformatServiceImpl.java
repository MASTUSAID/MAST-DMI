

package com.rmsi.mast.studio.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rmsi.mast.studio.dao.OutputformatDAO;
import com.rmsi.mast.studio.domain.Outputformat;
import com.rmsi.mast.studio.service.OutputformatService;

/**
 * @author Aparesh.Chakraborty
 *
 */
@Service
public class OutputformatServiceImpl implements OutputformatService{

	@Autowired
	private OutputformatDAO outputformatDAO;

	@Override
	public Outputformat addOutputformat(Outputformat outputformat) {
		// TODO Auto-generated method stub
		
				
		return null;
	}

	@Override
	public void deleteOutputformat() {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteOutputformatById(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateOutputformat(Outputformat outputformat) {
		// TODO Auto-generated method stub
		
		
		
	}

	@Override
	public Outputformat findOutputformatById(Long id) {
		return outputformatDAO.findById(id, false);

	}

	@Override
	public List<Outputformat> findAllOutputformat() {
		return outputformatDAO.findAll();
	}

	@Override
	public Outputformat findOutputformatByName(String name) {
		return outputformatDAO.findByName(name);
	}
	
}
