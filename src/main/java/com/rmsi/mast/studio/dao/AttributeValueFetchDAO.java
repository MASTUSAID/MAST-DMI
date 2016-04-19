

package com.rmsi.mast.studio.dao;

import com.rmsi.mast.studio.domain.fetch.AttributeValuesFetch;

public interface AttributeValueFetchDAO extends GenericDAO<AttributeValuesFetch, Long> {

	boolean updateValue(Long value_key, String alias);
	
	
	
}
