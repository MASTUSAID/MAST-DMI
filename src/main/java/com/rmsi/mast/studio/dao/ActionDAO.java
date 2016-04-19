

package com.rmsi.mast.studio.dao;

import java.util.List;






import com.rmsi.mast.studio.domain.Action;

public interface ActionDAO extends GenericDAO<Action, Long> {
	
	Action findByName(String name);
	boolean deleteByName(String name);
}
