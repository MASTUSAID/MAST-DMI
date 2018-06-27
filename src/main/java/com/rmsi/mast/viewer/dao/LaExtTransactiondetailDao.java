package com.rmsi.mast.viewer.dao;

import org.springframework.transaction.annotation.Transactional;

import com.rmsi.mast.studio.dao.GenericDAO;
import com.rmsi.mast.studio.domain.LaExtTransactiondetail;
import com.rmsi.mast.studio.domain.NaturalPerson;

public interface LaExtTransactiondetailDao extends GenericDAO<LaExtTransactiondetail, Integer>{

	LaExtTransactiondetail getLaExtTransactiondetail(Integer id);
	LaExtTransactiondetail getLaExtTransactionByLeaseeid(Long id);
	LaExtTransactiondetail getpoiByLeaseeid(Long id);
	LaExtTransactiondetail getLaExtTransactionByLeaseeidForSurrenderLease(Long id);
	LaExtTransactiondetail getLaExtTransactiondetailByLandid(long id);
	@Transactional
	 public LaExtTransactiondetail addLaExtTransactiondetail(LaExtTransactiondetail laExtTransaction);
	
	LaExtTransactiondetail getLaExtTransactionforMortgage(Long longValue);
	LaExtTransactiondetail getLaExtTransactionforSurrenderMortgage(Long longValue);
}

