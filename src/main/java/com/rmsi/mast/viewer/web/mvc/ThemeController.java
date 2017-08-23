

package com.rmsi.mast.viewer.web.mvc;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.apache.log4j.Logger;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.exceptions.EncryptionOperationNotPossibleException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


@Controller
public class ThemeController implements ServletContextAware {
	private static final Logger logger = Logger.getLogger(ThemeController.class);
	private ServletContext context;
	
	/*@Autowired
	private TaskService taskService;*/
	
	@RequestMapping(value = "/viewer/theme/createSLD", method = RequestMethod.POST)
	@ResponseBody
	public String createSLD(HttpServletRequest request){
		try{
			long ticks = 0;
			String data = request.getParameter("data");
			String s = URLDecoder.decode(data, "UTF-8");
			synchronized (s) {
				Date date = new Date();
				ticks = date.getTime();
			}
			String fileName = String.valueOf(ticks) +".xml";
			String relativePath = request.getContextPath();
			String absolutePath = context.getRealPath(request.getContextPath());
			//System.out.println("------- AbsolutePtah: " + absolutePath);
			
			int index = absolutePath.lastIndexOf(File.separator);
			//System.out.println("----index value: " + index);
			absolutePath = absolutePath.substring(0, index) +File.separator+"resources"+File.separator+"temp"+File.separator+"sld"+File.separator+fileName;
			//System.out.println("------- Absolute Path: " + absolutePath);
			
			//Write the SLD file on the disk
			FileWriter writer = new FileWriter(new File(absolutePath));
			writer.write(s);
			writer.close();
			
			InetAddress address = InetAddress.getLocalHost();
		
			
			int port = request.getLocalPort();
			String url = "http://" + address.getHostAddress() + ":" + String.valueOf(port) + relativePath
				+ "/viewer/resources/temp/sld/" + fileName;
			
			//System.out.println("--- URL Address: " + url);
			
			return url;
		}catch(Exception e){
			logger.error(e);
		}
		return null;
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		this.context = servletContext;
		
	}
	
	@RequestMapping(value = "/viewer/theme/createMarkupSLD", method = RequestMethod.POST)
	@ResponseBody
	public String createMarkupSLD(HttpServletRequest request, HttpServletResponse response){
		//String principal = getPrincipalFromToken(request, response);
		String principal = request.getUserPrincipal().getName();
		System.out.println("Principal name ----- " + principal);
		
		String fileName = null;
		boolean appendFlag = false;
		try{
			String data = request.getParameter("data");
			String s = URLDecoder.decode(data, "UTF-8");
			//System.out.println("########### Decoded String: "+ s);
			
			String relativePath = request.getContextPath();
			String absolutePath = context.getRealPath(request.getContextPath());
			//System.out.println("------- AbsolutePath: " + absolutePath);
			
			int index = absolutePath.lastIndexOf("\\");
			//System.out.println("----index value: " + index);
			fileName = principal + "_sld.xml";
			absolutePath = absolutePath.substring(0, index) + "\\resources\\temp\\sld\\" + fileName;
			//System.out.println("------- Absolute Path: " + absolutePath);
			//Check if file exists
			File file = new File(absolutePath);
			
			synchronized (s) {
				if(! file.exists()){
					//Write the SLD file on the disk
					FileWriter writer = new FileWriter(new File(absolutePath));
					FileWriter writer1 = new FileWriter(absolutePath);
					writer.write(s);
					writer.close();
				}else{
					appendFlag = true;
				}
			}
			
			if(appendFlag){
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				factory.setValidating(false);
				Document document = factory.newDocumentBuilder().parse(file);
				document = parseDOM(document, s);
				synchronized(document){
					Source source = new DOMSource(document);
				    Result result = new StreamResult(file);
				   
				    // Write the DOM document to the file
				    Transformer xformer = TransformerFactory.newInstance().newTransformer();
				    xformer.transform(source, result);
				}
			}
			
			String address = request.getLocalAddr();
			int port = request.getLocalPort();
			String url = "http://" + address + ":" + String.valueOf(port) + relativePath
				+ "/resources/temp/sld/" + fileName;
			
			//System.out.println("--- URL Address: " + url);
			
			return url;
		}catch(Exception e){
			logger.error(e);
		}
		return null;
	}
	
	private Document parseDOM(Document document, String sld){
		ArrayList<String> namedLayers = new ArrayList<String>();
		boolean bNamedLayerMatch = false;
		
		try{
			//Parse the String
			int startPos = sld.indexOf("<sld:NamedLayer>");
			do{
				if(startPos != -1){
					int endPos = sld.indexOf("</sld:NamedLayer>");
					endPos +=  "</sld:NamedLayer>".length();
					if(endPos != -1){
						String s = sld.substring(startPos, endPos);
						namedLayers.add(s);
						
						sld = sld.substring(endPos);
					}
				}
				startPos = sld.indexOf("<sld:NamedLayer>");
			}while(startPos != -1);
			
			 XPath xpath = XPathFactory.newInstance().newXPath();
		     XPathExpression expr = xpath.compile("/StyledLayerDescriptor/NamedLayer/Name");
		     Object result = expr.evaluate(document, XPathConstants.NODESET);   
		     NodeList nodes = (NodeList) result;
		     
			for(String namedLayer:namedLayers){
				String fragment = namedLayer;
				
				// Create a DOM builder and parse the fragment
		        DocumentBuilderFactory _factory = DocumentBuilderFactory.newInstance();
		        Node fragmentNode  = _factory.newDocumentBuilder().parse(
		            new InputSource(new StringReader(fragment))).getDocumentElement();
		        
		        //System.out.println(fragmentNode.getNodeName());
		        //System.out.println(document.getFirstChild().getNodeName());
		        
		        //Check if namedlayer name exists in the existing document
		        String nodeValue = fragmentNode.getFirstChild().getLocalName();
		       // System.out.println(nodeValue);
		        
		        for (int i = 0; i < nodes.getLength(); i++) {
		        	if(nodes.item(i).getLocalName().equals(nodeValue)){
		        		//NamedLayer already exists, hence just add the rule
		        		Node featStyleNode = nodes.item(i).getNextSibling().getFirstChild();
		        		//System.out.println(featStyleNode.getNodeName());
		        		
		        		//System.out.println(fragmentNode.getNodeName());
		        		NodeList nodeList = fragmentNode.getLastChild().getFirstChild().getChildNodes();
		        		for(int j=0; j<nodeList.getLength(); j++){
		        			Node ruleNode = nodeList.item(j);
		        			//System.out.println(ruleNode.getNodeName());
			        		//Add the fragment ruleNode
			        		fragmentNode = document.importNode(ruleNode, true);
			        		featStyleNode.appendChild(fragmentNode);
		        		}
		        		bNamedLayerMatch = true;
		        		break;
		        	}	
		        }
		        if(!bNamedLayerMatch){
	        		//Add the NamedLayer
	        		fragmentNode = document.importNode(fragmentNode, true);
			        document.getFirstChild().appendChild(fragmentNode);
		        }
			}
		}catch(IOException ioe){
			logger.error(ioe);
		}catch(ParserConfigurationException pce){
			logger.error(pce);
		}catch(SAXException saxe){
			logger.error(saxe);
		}catch(Exception e){
			logger.error(e);
		}
		return document;
	}
	
	
	private String getPrincipalFromToken(HttpServletRequest request, HttpServletResponse response){
		
    	/*Enumeration<String> emn =  request.getParameterNames();
		for(;emn.hasMoreElements();){
			String elm = emn.nextElement();
			System.out.println("----Param Names: " + elm);
		}*/
		
		 String token = request.getParameter("_token");
		 if(token == null){
			 token = (String) request.getSession().getAttribute("auth");
			 System.out.println("------- token: " + token);
		 }
		 final String ENCRYPT_KEY = "HG58YZ3CR9";
		 String principal = "";
		  //System.out.println("-------Encrypted Token: " + token);
		  StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		  encryptor.setPassword(ENCRYPT_KEY);
		  encryptor.setAlgorithm("PBEWithMD5AndTripleDES");
		  try{
			  token = encryptor.decrypt(token);
			  //System.out.println("--------Decrypted token: " + token);
			  String[] tokens = token.split("\\|");
			  principal = tokens[0];
		  }catch(EncryptionOperationNotPossibleException e){
			  e.printStackTrace();
			  try{
				  response.sendError(403);
			  }catch(Exception ex){
				 logger.error(ex);
			  }
		  }
		  return principal;
	}
	
	@RequestMapping(value = "/viewer/theme/checkSldExists/", method = RequestMethod.GET)
	@ResponseBody
	public boolean markupSLDExists(HttpServletRequest request,Principal principal){
		
		String user=principal.getName();
		String fileName=user+"_sld";
		String absolutePath = context.getRealPath(request.getContextPath());		
		int index = absolutePath.lastIndexOf("\\");
		absolutePath = absolutePath.substring(0, index)+"\\resources\\temp\\sld\\"+fileName+".xml";
		File file = new File(absolutePath);
		boolean value= file.exists();
		return value;
	}
	
	@RequestMapping(value = "/viewer/theme/date", method = RequestMethod.GET)
	@ResponseBody
	public String getDate(){
		Date d = new Date();
		String pattern = "yyyy-MM-dd";
		
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern(pattern);
		return sdf.format(d);
	}
	
	/*@RequestMapping(value = "/viewer/theme/promptdate", method = RequestMethod.GET)
	@ResponseBody
	public String getPromptDate(){
		Date d = null;
		String pattern = "yyyy-MM-dd";
		int task_prompt = 0;
		//Get task prompt
		List<Task> tasks = taskService.getTaskByTaskName("Programmed Survey");
		if(tasks.size() > 0){
			for(Task t:tasks){
				if(t.getSurveyType().equalsIgnoreCase("PROW")){
					Set<com.rmsi.spatialvue.studio.domain.TaskScheduler> task_schedule
								= t.getTaskSchedulers();
					
					for(Iterator<com.rmsi.spatialvue.studio.domain.TaskScheduler> itr = task_schedule.iterator(); 
							itr.hasNext();){
						com.rmsi.spatialvue.studio.domain.TaskScheduler ts = itr.next();
						task_prompt = ts.getTaskPrompt();
						System.out.println("---- Task_Prompt: " + task_prompt);
						break;
					}
				}
			}
		}else{
			task_prompt = 7;
		}
		
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, 7);
		d = c.getTime();
		
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern(pattern);
		return sdf.format(d);
	}*/
	
	
	

	
}
