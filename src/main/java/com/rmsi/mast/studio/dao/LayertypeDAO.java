

package com.rmsi.mast.studio.dao;



import com.rmsi.mast.studio.domain.Layertype;

/**
 * @author Aparesh.Chakraborty
 *
 */


public interface LayertypeDAO extends GenericDAO<Layertype, Long> {
	
	Layertype findByName(String name);

}

