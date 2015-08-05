/**
 * 
 */
package com.rmsi.mast.studio.mobile.dao;

import java.util.List;

import com.rmsi.mast.studio.dao.GenericDAO;
import com.rmsi.mast.studio.domain.SocialTenureRelationship;

/**
 * @author Shruti.Thakur
 *
 */
public interface SocialTenureDao extends GenericDAO<SocialTenureRelationship, Integer> {

	/**
	 * It will get List of social tenure 
	 * @return
	 */
	List<SocialTenureRelationship> getSocailTenure();
	
	/**
	 * It will add Social tenure to database
	 * 
	 * @param socialTenureRelationship
	 * @return
	 */
	SocialTenureRelationship addSocialTenure(SocialTenureRelationship socialTenureRelationship);
	
	/**
	 * It will fetch social tenure by usin 
	 * 
	 * @param usin
	 * @return
	 */
	List<SocialTenureRelationship> findSocailTenureByUsin(Long usin);
}
