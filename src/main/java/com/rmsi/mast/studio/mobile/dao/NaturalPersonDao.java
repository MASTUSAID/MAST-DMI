/**
 * 
 */
package com.rmsi.mast.studio.mobile.dao;

import java.util.ArrayList;
import java.util.List;

import com.rmsi.mast.studio.dao.GenericDAO;
import com.rmsi.mast.studio.domain.NaturalPerson;

/**
 * @author Shruti.Thakur
 *
 */
public interface NaturalPersonDao extends GenericDAO<NaturalPerson, Integer>{

	public NaturalPerson addNaturalPerson(NaturalPerson naturalPerson);
	
	public void getNaturalPersonByProjectId(String projectId);


	public List<NaturalPerson> findById(Long id);


	public List<NaturalPerson> getNaturalPerson();

	public ArrayList<Long> findOwnerByGid(ArrayList<Long> naturalPerson);

	

	

}
