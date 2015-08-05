package com.rmsi.mast.studio.web.mvc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rmsi.mast.studio.domain.DbConnection;
import com.rmsi.mast.studio.service.DBConnectionService;
import com.rmsi.mast.viewer.web.mvc.LandRecordsController;

@Controller
public class DBConnectionController {

	private static final Logger logger = Logger.getLogger(DBConnectionController.class);
	
	@Autowired
	private DBConnectionService connService;
	
	@RequestMapping(value = "dbconnection/", method = RequestMethod.GET)
	@ResponseBody
	public List<DbConnection> list(){
		return 	connService.findAllConnections();
	}
	
	@RequestMapping(value = "dbconnection/delete/{name}", method = RequestMethod.GET)
	@ResponseBody
	public boolean deleteConnectionByName(@PathVariable String name){
		return connService.deleteConnectionByName(name);
	}
	
	@RequestMapping(value = "dbconnection/{name}", method = RequestMethod.GET)
	@ResponseBody
    public DbConnection getMaptipByName(@PathVariable String name){		
		return connService.findConnectionByName(name);
	}
	
	@RequestMapping(value = "dbconnection/edit", method = RequestMethod.POST)
	@ResponseBody
    public DbConnection editConnection(HttpServletRequest request){
		try{
			String name = ServletRequestUtils.getStringParameter(request, "_hidname");
			String dbType = ServletRequestUtils.getStringParameter(request, "dbType");
			String dbName = ServletRequestUtils.getStringParameter(request, "database");
			String serverName = ServletRequestUtils.getStringParameter(request, "server");
			String portName = ServletRequestUtils.getStringParameter(request, "port");
			String user = ServletRequestUtils.getStringParameter(request, "userid");
			String pwd = ServletRequestUtils.getStringParameter(request, "password");
			System.out.println(" --- " + name + " " + dbType + " " + dbName + " " + serverName + " " + Integer.parseInt(portName));
			System.out.println(" ---- " + user + " " + pwd);
			
			DbConnection connection = connService.findConnectionByName(name);
			connection.setDatabaseType(dbType);
			connection.setDatabaseName(dbName);
			connection.setServerName(serverName);
			connection.setServerPort(Integer.parseInt(portName));
			connection.setUserId(user);
			connection.setPassword(pwd);
			
			return connService.saveConnection(connection);
			
		}catch(Exception e){
			logger.error(e);
			return null;
		}
	}
	
	@RequestMapping(value = "dbconnection/create", method = RequestMethod.POST)
	@ResponseBody
    public DbConnection createConnection(HttpServletRequest request){
		try{
			String name = ServletRequestUtils.getStringParameter(request, "name");
			String dbType = ServletRequestUtils.getStringParameter(request, "dbType");
			String dbName = ServletRequestUtils.getStringParameter(request, "database");
			String serverName = ServletRequestUtils.getStringParameter(request, "server");
			String portName = ServletRequestUtils.getStringParameter(request, "port");
			String user = ServletRequestUtils.getStringParameter(request, "userid");
			String pwd = ServletRequestUtils.getStringParameter(request, "password");
			System.out.println(" --- " + name + " " + dbType + " " + dbName + " " + serverName + " " + Integer.parseInt(portName));
			System.out.println(" ---- Saving Connection ------");
			
			DbConnection connection = new DbConnection();
			connection.setConnectionName(name);
			connection.setDatabaseType(dbType);
			connection.setDatabaseName(dbName);
			connection.setServerName(serverName);
			connection.setServerPort(Integer.parseInt(portName));
			connection.setUserId(user);
			connection.setPassword(pwd);
			
			return connService.saveConnection(connection);
			
		}catch(Exception e){
			logger.error(e);
			return null;
		}
	}
	
	@RequestMapping(value = "dbconnection/validate", method = RequestMethod.POST)
	@ResponseBody
    public boolean validateConnection(HttpServletRequest request){
		try{
			String name = ServletRequestUtils.getStringParameter(request, "_hidname");
			String dbType = ServletRequestUtils.getStringParameter(request, "dbType");
			String dbName = ServletRequestUtils.getStringParameter(request, "database");
			String serverName = ServletRequestUtils.getStringParameter(request, "server");
			String portName = ServletRequestUtils.getStringParameter(request, "port");
			String user = ServletRequestUtils.getStringParameter(request, "userid");
			String pwd = ServletRequestUtils.getStringParameter(request, "password");
			System.out.println(" --- " + dbType + " " + dbName + " " + serverName + " " + Integer.parseInt(portName));
			System.out.println(" ---- " + user + " " + pwd);
			
			String connUrl = null;
			if(dbType.equalsIgnoreCase("PostGreSQL")){
				connUrl = "jdbc:postgresql://" + serverName + ":" 
					+ portName + "/" + dbName;
				
				System.out.println("---- Connection URL: " + connUrl + " --------");
				String driver = "org.postgresql.Driver";
				try{
					Class.forName(driver);
					Connection connection = DriverManager.getConnection(connUrl, user, pwd);
					if(connection != null){
						return true;
					}else{
						return false;
					}
				}catch(Exception e){
					logger.error(e);
				}
			}else if(dbType.equalsIgnoreCase("MSSQL Server")){
				
			}else if(dbType.equalsIgnoreCase("MYSQL")){
				
			}else if(dbType.equalsIgnoreCase("Oracle")){
				
			}
		}catch(Exception e){
			logger.error(e);
		}
		return false;
	}
}
