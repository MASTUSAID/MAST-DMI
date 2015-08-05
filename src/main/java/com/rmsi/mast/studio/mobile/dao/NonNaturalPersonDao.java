
package com.rmsi.mast.studio.mobile.dao;

import java.util.List;

import com.rmsi.mast.studio.dao.GenericDAO;
import com.rmsi.mast.studio.domain.NonNaturalPerson;

/**
 * @author Shruti.Thakur
 *
 */
public interface NonNaturalPersonDao extends GenericDAO<NonNaturalPerson, Long> {

	/**
	 * Used to add or update list of Non Natural Person to database
	 * 
	 * @param nonNaturalPersonList
	 */
	void addOrUpdateNonNaturalPerson(
			List<NonNaturalPerson> nonNaturalPersonList);

	NonNaturalPerson addNonNaturalPerson(
			NonNaturalPerson nonNaturalPerson);

	List<NonNaturalPerson> findById(Long id);

}
