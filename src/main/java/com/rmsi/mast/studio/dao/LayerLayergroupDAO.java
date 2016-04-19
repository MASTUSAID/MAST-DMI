

package com.rmsi.mast.studio.dao;




import java.util.List;





import com.rmsi.mast.studio.domain.LayerLayergroup;
import com.rmsi.mast.studio.domain.ProjectLayergroup;

public interface LayerLayergroupDAO extends GenericDAO<LayerLayergroup, Long> {
	
	List<LayerLayergroup> findAllLayerLayergroup(String name);
	
	void deleteLayerLayergroupByName(String name);
	
}
