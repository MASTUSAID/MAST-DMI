package com.rmsi.mast.studio.mobile.service.impl;

import com.rmsi.mast.studio.dao.GenericDAO;
import com.rmsi.mast.studio.domain.fetch.DisputeBasic;

public interface DisputeBasicDAO extends GenericDAO<DisputeBasic, Long>  {
	 DisputeBasic save(DisputeBasic dispute);

}
