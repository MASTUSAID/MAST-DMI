package com.rmsi.mast.db.util;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;

import com.rmsi.mast.studio.util.ConfigurationUtil;

// Added by saurabh singh to get Connection
public class DataAccessFactory {

	private static DataAccessFactory dataAccessFactory=null;
	private Connection connection=null;
	private String driverClassName=null;
	private String dbUrl=null;
	private String dbUserName=null;
	private String dbPassword=null;
	
	private DataAccessFactory(){
		loadConfiguration();
	}
	
	public static DataAccessFactory getInstance(){
		if(dataAccessFactory==null)
		   dataAccessFactory=new DataAccessFactory();
		return dataAccessFactory;
	}	
	
	private void loadConfiguration(){
		try {
			   
			   String tomcatDbReourseName=ConfigurationUtil.getProperty("tomcat.db.resource.name");
	           Context initContext = new InitialContext();
	           Context envContext = (Context) initContext.lookup("java:/comp/env");
	           Class classDataSource= envContext.lookup(tomcatDbReourseName).getClass();
	           Class classDataSourceProxy=classDataSource.getSuperclass();		           
	           Method[] dbProxyClassMethods = classDataSourceProxy.getMethods();
	           Method method=null;
	           
	           Method[] childClassMethods =null;
	           for(int i=0;i<dbProxyClassMethods.length;i++){
	        	   try{
	        		   method=dbProxyClassMethods[i];
	        		   if("getPoolProperties".equalsIgnoreCase(method.getName())){
	        			  Class classPoolProperties= method.invoke(envContext.lookup(tomcatDbReourseName), new Object[0]).getClass();
	        			  childClassMethods= classPoolProperties.getMethods();
	        			  for(Method childMethod:childClassMethods){
	        				  if("getPassword".equalsIgnoreCase(childMethod.getName())){
	        					  dbPassword=String.valueOf(childMethod.invoke(method.invoke(envContext.lookup(tomcatDbReourseName), new Object[0]), new Object[0]));
			        		       break;
	        				  }
	        			  }
	        		   }
	        		   else if("getDriverClassName".equalsIgnoreCase(method.getName())){
	        			   driverClassName=String.valueOf(method.invoke(envContext.lookup(tomcatDbReourseName), new Object[0]));
	        		   }else if("getUrl".equalsIgnoreCase(method.getName())){
	        			   dbUrl=String.valueOf(method.invoke(envContext.lookup(tomcatDbReourseName), new Object[0]));	        		   
	        		   }else if("getUsername".equalsIgnoreCase(method.getName())){
	        			   dbUserName=String.valueOf(method.invoke(envContext.lookup(tomcatDbReourseName), new Object[0]));
	        		   }
	        	   }catch(Exception e){
	        		   e.printStackTrace();
	        	   }
	           }	
	           
	                   
	        }catch (Exception ex){
	        	 ex.printStackTrace();
	        }	
	}
	
	public Connection getConnection(EntityManager entityManager){
		try{
		if(connection==null || connection.isClosed()){	
	        connection=null;			
			Class.forName(driverClassName);
			connection = DriverManager.getConnection (dbUrl, dbUserName,dbPassword);
			connection.setAutoCommit(false);
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return connection;
	}
	
	public void closeDBResource(Connection con, Statement stmt, ResultSet rset,PreparedStatement ps){
		
		if(ps != null){
			try{
				ps.close();
				}catch(Exception e){
					e.printStackTrace();
			};
		}
		
		if(rset != null){
			try{
				rset.close();
				}catch(Exception e){
					e.printStackTrace();
			};
		}		
		if(stmt != null){
			try{
				stmt.close();
			}catch(Exception e){
				e.printStackTrace();
			};
		}
		if(con != null){
			try{
				con.close();
			}catch(Exception e){
				e.printStackTrace();
			};
		}
		
	} 
	
}
