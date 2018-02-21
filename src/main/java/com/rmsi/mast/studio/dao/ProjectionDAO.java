

package com.rmsi.mast.studio.dao;


import java.util.List;

import com.rmsi.mast.studio.domain.Projection;

/**
 * @author Aparesh.Chakraborty
 *
 */


public interface ProjectionDAO extends GenericDAO<Projection, Long> {
	
	Projection findByName(String name);
    List<Projection> findAllProjection();
    public Projection findProjectionById(Integer id) ;
	
}

