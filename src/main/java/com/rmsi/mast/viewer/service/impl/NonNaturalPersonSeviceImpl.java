package com.rmsi.mast.viewer.service.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rmsi.mast.studio.domain.NonNaturalPerson;
import com.rmsi.mast.viewer.dao.LaPartyDao;
import com.rmsi.mast.viewer.service.NonNaturalPersonService;


@Service
public class NonNaturalPersonSeviceImpl implements NonNaturalPersonService{
	
	@Autowired
	LaPartyDao laPartyDao;
	
	@PersistenceContext
    private EntityManager em; 

	@Override
	@Transactional
	public NonNaturalPerson findByObject(
			NonNaturalPerson objSpatialunitPersonwithinterest) {
		NonNaturalPerson editednaturalperosn = (NonNaturalPerson) laPartyDao.getPartyIdByID(objSpatialunitPersonwithinterest.getPartyid());
		editednaturalperosn.setGroupType(objSpatialunitPersonwithinterest.getGroupType());
		editednaturalperosn.setContactno(objSpatialunitPersonwithinterest.getContactno());
		editednaturalperosn.setOrganizationname(objSpatialunitPersonwithinterest.getOrganizationname());
		em.merge(editednaturalperosn);
		
		return editednaturalperosn;
	}

}
