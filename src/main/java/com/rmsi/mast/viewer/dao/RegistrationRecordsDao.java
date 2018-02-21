package com.rmsi.mast.viewer.dao;

import java.util.List;

import com.rmsi.mast.studio.dao.GenericDAO;
import com.rmsi.mast.studio.domain.LaExtFinancialagency;
import com.rmsi.mast.studio.domain.LaSpatialunitLand;
import com.rmsi.mast.studio.domain.La_Month;

public interface RegistrationRecordsDao extends GenericDAO<LaSpatialunitLand, Long>{

	List<LaSpatialunitLand> findOrderedSpatialUnitRegistry(String defaultProject,int startfrom);
	
	List<LaSpatialunitLand> search(Long transactionid,Integer startfrom,String project,String communeId,String parcelId);
	
	List<LaExtFinancialagency> getFinancialagencyDetails();
	
	LaExtFinancialagency getFinancialagencyByID(int financial_AgenciesID);
	
	List<La_Month> getmonthofleaseDetails();
	
	La_Month getLaMonthById(int no_Of_month_Lease);
	Integer findSpatialUnitTempCount(String project, Integer startfrom);
	Integer searchCount( Long transactionid,Integer startfrom,String project,String communeId,String parcelId);
}
