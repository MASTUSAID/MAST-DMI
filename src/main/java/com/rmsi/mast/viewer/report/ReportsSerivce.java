package com.rmsi.mast.viewer.report;

import net.sf.jasperreports.engine.JasperPrint;

/**
 * Interface for reports functions
 */
public interface ReportsSerivce {
    JasperPrint getDenialLetter(long usin);
    
    JasperPrint getAdjudicationForms(String projectName, Long usin, int startRecord, int endRecord, String appUrl);
    
    JasperPrint getCcroForms(String projectName, Long usin, int startRecord, int endRecord, String appUrl);
    
    JasperPrint getDistrictRegistryBook(String projectName, String appUrl);
    
    JasperPrint getVillageRegistryBook(String projectName, String appUrl);
    
    JasperPrint getVillageIssuanceBook(String projectName);
    
    JasperPrint getTransactionSheet(String projectName, Long usin, String appUrl);
    
    JasperPrint getClaimsProfile(String projectName);
}
