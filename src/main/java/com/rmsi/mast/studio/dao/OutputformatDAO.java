
package com.rmsi.mast.studio.dao;



import com.rmsi.mast.studio.domain.Outputformat;

/**
 * @author Aparesh.Chakraborty
 *
 */


public interface OutputformatDAO extends GenericDAO<Outputformat, Long> {
	
	Outputformat findByName(String name);

	
}

