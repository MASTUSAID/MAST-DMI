

package com.rmsi.mast.security;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ContextLoader implements ServletContextListener{
	ServletContext context;
	private static final Logger logger = LoggerFactory.getLogger(ContextLoader.class);
			
	public void contextInitialized(ServletContextEvent contextEvent) {
		logger.debug("Context Created");
		context = contextEvent.getServletContext();
		String productkey = context.getInitParameter("productkey");
		if(productkey == null || productkey.length()< 1){
			throw new RuntimeException("++++++++++++++ PRODUCT KEY MISSING ++++++++++++++++++");
		}
		logger.debug("Context path " + context.getRealPath("/"));
		ProductKeyValidator keyValidtor = new ProductKeyValidator();
		keyValidtor.validateKey(productkey);
		
	}
	public void contextDestroyed(ServletContextEvent contextEvent) {
		context = contextEvent.getServletContext();
		logger.debug("============ Context Destroyed ============");
	}
}