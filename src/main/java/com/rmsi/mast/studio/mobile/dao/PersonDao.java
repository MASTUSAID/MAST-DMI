/**
 * 
 */
package com.rmsi.mast.studio.mobile.dao;

import java.util.List;

import com.rmsi.mast.studio.dao.GenericDAO;
import com.rmsi.mast.studio.domain.Person;

/**
 * @author Shruti.Thakur
 *
 */
public interface PersonDao extends GenericDAO<Person, Long> {

	/**
	 * This will add person to database
	 * 
	 * @param person
	 * @return
	 */
	Person addPerson(List<Person> person);

	/**
	 * This will fetch person based on person gid
	 * 
	 * @param usin
	 * @return
	 */
	Person findPersonById(Long usin);

}
