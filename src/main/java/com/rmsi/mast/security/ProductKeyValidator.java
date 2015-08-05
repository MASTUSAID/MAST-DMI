package com.rmsi.mast.security;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.exceptions.EncryptionOperationNotPossibleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ProductKeyValidator {

	private static final Logger logger = LoggerFactory.getLogger(ProductKeyValidator.class);
	String DECRYPT_KEY = "spatialvue_2011@1234";
	static String productKey = null;
	
	
	
	
	public ProductKeyValidator(){
		
	}
	//@Scheduled(cron="0 0/1 * * * ?")
	public void validateKey(String key) {		
		
		ProductKeyValidator.productKey = key;
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		encryptor.setPassword(DECRYPT_KEY);
		encryptor.setAlgorithm("PBEWithMD5AndTripleDES");
		try{
			String expireDate =  encryptor.decrypt(key);
			
			DateFormat df = new SimpleDateFormat("EEE MMM dd hh:mm:ss zzz yyyy");
			
			try {
				if(new Date().before(df.parse(expireDate))){
					logger.debug("============= PRODUCT KEY VALID ============== "  + ProductKeyValidator.productKey);
					logger.debug("============= PRODUCT KEY EXPIRES ON ============== "  + expireDate);
				}else{	
					
					logger.debug("******************* PRODUCT KEY EXPIRED *******************");
					throw new RuntimeException("******************* PRODUCT KEY EXPIRED *******************");
					
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}catch(EncryptionOperationNotPossibleException ex){
			throw new RuntimeException("@@@@@@@@@ PRODUCT KEY INVALID @@@@@@@@@@@@@2");
		}
	}
	
	//@Scheduled(cron="0 0/1 * * * ?")
	public void validateKeyTimer() {
		//logger.debug("^^^^^^^^^^^^^^^^^^ Start product key validation ^^^^^^^^^^^^^^^");
		//validateKey(ProductKeyValidator.productKey);
		//logger.debug("^^^^^^^^^^^^^^^^^^ Stop product key validation ^^^^^^^^^^^^^^^");
	}
}
