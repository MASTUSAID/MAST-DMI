package com.rmsi.mast.viewer.dao;

import java.util.List;

import com.rmsi.mast.studio.dao.GenericDAO;
import com.rmsi.mast.studio.domain.LaParty;
import com.rmsi.mast.studio.domain.NaturalPerson;
import com.rmsi.mast.studio.domain.SocialTenureRelationship;

public interface LaPartyDao extends GenericDAO<LaParty, Long>{

	LaParty saveParty(LaParty laParty);
	
	LaParty getPartyIdByID(Long id);
	
	List<NaturalPerson> getObjectsBypartyId(String id);
	
	List<SocialTenureRelationship> findSocailTenureByUsin(Long usin);
	
	List<LaParty>  getPartyListIdByID(Long id);
}
