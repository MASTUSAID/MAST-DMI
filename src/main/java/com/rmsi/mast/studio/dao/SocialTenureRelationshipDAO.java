

package com.rmsi.mast.studio.dao;

import java.util.List;

import com.rmsi.mast.studio.domain.SocialTenureRelationship;

public interface SocialTenureRelationshipDAO extends GenericDAO<SocialTenureRelationship, Long> {

	List<SocialTenureRelationship> findbyUsin(Long id);

	List<SocialTenureRelationship> findByGid(Integer id);
        
        SocialTenureRelationship getSocialTenure(long id);
        
        SocialTenureRelationship getSocialTenureObj(Long partyid, Long landId);

	boolean deleteTenure(Long id);

	boolean deleteNatural(Long id);
	
	boolean deleteNonNatural(Long id);
	
	List<SocialTenureRelationship> findDeletedPerson(Long id);

	boolean addDeletedPerson(Long gid);

	boolean updateSharePercentage(String alias, long personGid);
	//Abhay
	public SocialTenureRelationship getPersonLandMapDetails(Integer landid);
}
