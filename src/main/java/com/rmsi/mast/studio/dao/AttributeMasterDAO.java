

package com.rmsi.mast.studio.dao;

import java.util.List;

import com.rmsi.mast.studio.domain.AttributeMaster;
import com.rmsi.mast.studio.domain.fetch.AttributeValuesFetch;


public interface AttributeMasterDAO extends GenericDAO<AttributeMaster, Long> {

	List<AttributeMaster> findAllAttributeMaster();

	boolean deleteEntry(Long id);

	List<AttributeMaster> selectedCategory(Long id);

	List<AttributeMaster> selectedList(Long uid);

	boolean duplicatevalidate(Long id, String alias, String fieldname);

	List<AttributeMaster> findByAttributeId(Long id);

	boolean duplicateEditvalidate(long id,long categoryId, String alias,
			String fieldName);

	List<AttributeValuesFetch> fetchCustomAttribs(Long parentgid, int category);

	List<AttributeMaster> findByCategoryId(Long id);

}
