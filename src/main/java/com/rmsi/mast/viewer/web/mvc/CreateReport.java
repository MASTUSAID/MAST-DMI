
package com.rmsi.mast.viewer.web.mvc;

import gistoolkit.display.Converter;
import gistoolkit.features.Envelope;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javassist.expr.NewArray;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import net.opengis.context.DimensionType;
import net.opengis.context.LayerType;
import net.opengis.context.ViewContextType;

import org.apache.log4j.Logger;
import org.eclipse.birt.report.engine.api.HTMLRenderOption;
import org.eclipse.birt.report.engine.api.HTMLServerImageHandler;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;
import org.eclipse.birt.report.model.api.DesignElementHandle;
import org.eclipse.birt.report.model.elements.ImageItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.rmsi.mast.custom.dto.MapTemplateDto;
import com.rmsi.mast.report.BirtEngine;
import com.rmsi.mast.studio.domain.Layer;
import com.rmsi.mast.studio.domain.NaturalPerson;
import com.rmsi.mast.studio.domain.NonNaturalPerson;
import com.rmsi.mast.studio.domain.Project;
import com.rmsi.mast.studio.domain.SocialTenureRelationship;
import com.rmsi.mast.studio.domain.fetch.SpatialUnitTable;
import com.rmsi.mast.studio.service.LayerService;
import com.rmsi.mast.studio.service.ProjectService;
import com.rmsi.mast.viewer.service.LandRecordsService;
import com.sun.org.apache.xerces.internal.dom.ElementNSImpl;
//import org.apache.log4j.Logger;




@Controller
public class CreateReport {
	private static final long serialVersionUID = 1L;
	private IReportEngine birtReportEngine = null;
	protected static Logger logger = Logger.getLogger(CreateReport.class);

	private String outputUrl = null;
	private String mapFileName = null;
	private String access_land_sld_file=null;
	private String cosmetic_sld_file=null;
	private String rptFilePath = null;
	private String bbox = "";
	String pdfpath = null;
	String srn = null;
	Converter convert = null;
	@Autowired 
	private LandRecordsService landRecordsService;

	@Autowired
	private ProjectService projectService;

	@Autowired
	private LandRecordsController landRecordsController;
	
	@Autowired
	private LayerService layerService;
	

	private String watermarkImg="watermark.png";
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreateReport() {
		super();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@RequestMapping(value = "/viewer/print/", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView ExportToXLS(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		logger.debug("========================================");

		String title = request.getParameter("title");
		logger.debug("Title: " + title);

		String description = request.getParameter("description");
		logger.debug("Desc: " + description);

		String papersize = request.getParameter("papersize");
		logger.debug("Size: " + papersize);

		String templateType = request.getParameter("templateType");
		logger.debug("templateType: " + templateType);

		String orientation = request.getParameter("orientation");
		logger.debug("Orientation: " + orientation);

		String wmc = request.getParameter("wmc");
		logger.debug("WMC: " + wmc);

		String compiledBy = request.getParameter("hid-compiledBy");
		logger.debug("CompiledBy: " + compiledBy);

		String mapScale = request.getParameter("mapScale");
		logger.debug("mapScale: " + mapScale);

		access_land_sld_file = request.getParameter("access_land_sld_file");
		logger.debug("access_land_sld_file: " + access_land_sld_file);

		cosmetic_sld_file = request.getParameter("cosmetic_sld_file");
		logger.debug("cosmetic_sld_file: " + cosmetic_sld_file);




		logger.debug("=============================================");

		StringReader wmc_text = new StringReader(wmc);
		// FileWriter f = new FileWriter("F:/softwares/OGC/schemas/WMC.xml");
		// f.write(wmc);
		// f.flush();
		// f.close();

		// StringReader wmc_text = new StringReader(getContents(new File("F:/softwares/OGC/schemas/WMC.xml")));

		//String legendImgUrl = "";
		String layerImgUrl = "";

		int imgWidth = 0;
		int imgHeight = 0;
		String margeImgUrl = "";
		String margeLegendUrl = "";
		String finalIgmUrl="";
		File file = new File(request.getRealPath("") + "/resources/temp/images");
		boolean exists = file.exists();
		if (!exists) {

			boolean success = (new File(request.getRealPath("") + "/resources/temp/images"))
					.mkdir();

		}

		String outputFilePath = request.getRealPath("") + "/resources/temp/images";
		outputUrl = request.getScheme() + "://" + request.getServerName() + ":"
				+ Integer.toString(request.getServerPort())
				+ request.getContextPath() + "/resources/temp/images/";

		System.out.println("Output Url ---- : " + outputUrl);

		ServletContext sc = request.getSession().getServletContext();
		this.birtReportEngine = BirtEngine.getBirtEngine(sc);

		IReportRunnable design;
		FileOutputStream fos = null;

		try {

			JAXBContext context = JAXBContext
					.newInstance(ViewContextType.class);

			Unmarshaller unmarshaller = context.createUnmarshaller();

			javax.xml.bind.JAXBElement object = (javax.xml.bind.JAXBElement) unmarshaller
					.unmarshal(wmc_text);

			ViewContextType ctx = (ViewContextType) object.getValue();

			String srs = ctx.getGeneral().getBoundingBox().getSRS();

			logger.debug("SRS: " + srs);

			double bboxMinx = ctx.getGeneral().getBoundingBox().getMinx()
					.doubleValue();
			double bboxMiny = ctx.getGeneral().getBoundingBox().getMiny()
					.doubleValue();
			double bboxMaxx = ctx.getGeneral().getBoundingBox().getMaxx()
					.doubleValue();
			double bboxMaxy = ctx.getGeneral().getBoundingBox().getMaxy()
					.doubleValue();

			logger.debug("BBOX: " + bboxMinx + "," + bboxMiny + "," + bboxMaxx
					+ "," + bboxMaxy);

			// Open report design
			//			String reportName = "print-tmpl-" + papersize.toLowerCase() + "-"
			//					+ orientation.toLowerCase() + ".rptdesign";

			logger.debug("Report template " + templateType);

			rptFilePath = request.getRealPath("/") + "reports"+File.separator;
			design = birtReportEngine.openReportDesign(rptFilePath
					+ templateType + ".rptdesign");

			DesignElementHandle m = design.getDesignHandle().getDesignHandle()
					.findElement("mapimage");

			ImageItem item = (ImageItem) m.getElement();

			imgWidth = (int) item.handle(null).getWidth().getMeasure();
			imgHeight = (int) item.handle(null).getHeight().getMeasure();

			logger.debug("Img Width: " + imgWidth);
			logger.debug("Img Height: " + imgHeight);

			Envelope inScreenEnvelope = new Envelope(0, 0, imgWidth, imgHeight);
			Envelope inWorldEnvelope = new Envelope(bboxMinx, bboxMiny,
					bboxMaxx, bboxMaxy);
			convert = new Converter(inScreenEnvelope, inWorldEnvelope, true);
			Envelope newEnv = convert.getWorldEnvelope();
			bbox = newEnv.getMinX() + "," + newEnv.getMinY() + ","
					+ newEnv.getMaxX() + "," + newEnv.getMaxY();
			logger.debug("layer BBOX: " + bbox);

			List layerList = ctx.getLayerList().getLayer();
			Iterator layerIter = layerList.iterator();
			ArrayList<String> imagesArr = new ArrayList<String>();
			while (layerIter.hasNext()) {
				float opacity = 1;
				LayerType ctxLayer = (LayerType) layerIter.next();
				if (!ctxLayer.isHidden()) {
					logger.debug("layer: " + ctxLayer.getName());

					if(ctxLayer.getName().equalsIgnoreCase("snpa:SNPA_Boundary")){
						continue;
					}

					if (ctxLayer.getExtension() != null) {
						ElementNSImpl extElem = (ElementNSImpl) ctxLayer
								.getExtension().getAny();

						if (extElem.getNodeName().equalsIgnoreCase("env:opacity")) {
							logger.debug("**** layer opacity **** : "
									+ extElem.getTextContent());
							String lyrOpacity = extElem.getTextContent();

							if (lyrOpacity != null) {
								opacity = Float.parseFloat(lyrOpacity);
							}
						}
					}
					String ctxLayerName = encodeURI(ctxLayer.getName());
					String ctxLayerFormat = "image/png";
					// ************** Need to comment out once Envitia WMS
					// transparency is fixed
					// ctxLayer.getFormatList()
					// .getFormat().get(0).getValue();
					// **********************

					String ctxLayerStyle = null;
					if (ctxLayer.getStyleList() != null) {
						String styleName = ctxLayer.getStyleList().getStyle().get(0).getName();
						if(styleName == null){
							styleName = "";
						}

						ctxLayerStyle = encodeURI("");
						logger.debug("layer style: " + ctxLayerStyle);
					}
					String wmsURL = ctxLayer.getServer().getOnlineResource()
							.getHref();
					//if (!wmsURL.endsWith("?"))
					if (wmsURL.indexOf("?")<0)
						wmsURL = wmsURL + "?";


					if (ctxLayerStyle == null) {

						layerImgUrl = wmsURL
								+ "request=GetMap&transparent=true&format="
								+ ctxLayerFormat + "&srs=" + srs + "&layers="
								+ ctxLayerName;
						System.out.println("layer image url: " + layerImgUrl);

					} else {
						layerImgUrl = wmsURL
								+ "request=GetMap&transparent=true&format="
								+ ctxLayerFormat + "&styles=" + ctxLayerStyle
								+ "&srs=" + srs + "&layers=" + ctxLayerName;
						System.out.println("layer image url: " + layerImgUrl);
					}

					if (ctxLayer.getDimensionList() != null) {
						List<DimensionType> dimTypeList = ctxLayer
								.getDimensionList().getDimension();
						Iterator<DimensionType> dimTypeListIter = dimTypeList
								.iterator();
						while (dimTypeListIter.hasNext()) {
							DimensionType dimType = dimTypeListIter.next();
							String dimName = dimType.getName();
							String dimValue = dimType.getUserValue();
							logger.debug("+++ Dimension Name +++ " + dimName);
							logger.debug("+++ Dimension Value +++ " + dimValue);
							layerImgUrl = layerImgUrl + "&" + dimName + "="
									+ dimValue;
						}

					}
					System.out.println("-------ctxLayer.getName()" + ctxLayer.getName());

					finalIgmUrl=getWMSImage(layerImgUrl, imgWidth, imgHeight,
							outputFilePath, opacity,ctxLayer.getName());


					imagesArr.add(finalIgmUrl);

				}//ABU

			}

			String scale = calculateScale(imgWidth, imgHeight, newEnv);

			margeImgUrl = mergeImages(imagesArr, imgWidth, imgHeight,
					outputFilePath);
			System.out.println("----------MERGING URL: "+ margeImgUrl);	
			// create task to run and render report
			IRunAndRenderTask task = birtReportEngine
					.createRunAndRenderTask(design);

			// set output options
			HTMLRenderOption options = new HTMLRenderOption();
			// set the image handler to a HTMLServerImageHandler if you plan on
			// using the base image url.
			options.setImageHandler(new HTMLServerImageHandler());

			options.setOutputFormat(HTMLRenderOption.OUTPUT_FORMAT_PDF);

			// options.setOutputStream(response.getOutputStream());
			//			SimpleDateFormat formatter = new SimpleDateFormat(
			//					"dd.MM.yyyy_hh.mm.ss");
			//			Date day = new Date();
			//
			//			String pdfOutputFile = pdfpath + srn + "_" + formatter.format(day)
			//					+ ".pdf";
			//			logger.debug("PDF Output file " + pdfOutputFile);

			options.setOutputStream(response.getOutputStream());

			options.setBaseImageURL(request.getContextPath() + "/resources/temp/images");
			options.setImageDirectory(sc.getRealPath("/resources/temp/images"));

			task.setParameterValue("imgurl", margeImgUrl);
			task.setParameterValue("desc", description);
			task.setParameterValue("legendurl", margeLegendUrl);
			task.setParameterValue("title", title);
			task.setParameterValue("scale", scale);
			task.setParameterValue("compiledBy", compiledBy);
			task.setParameterValue("mapScale", mapScale);

			task.setRenderOption(options);

			logger.debug("Generating pdf......");

			// run report
			task.run();
			task.close();

			logger.debug("Generating pdf complete......");

			response.getOutputStream().println(
					"{\"success\": true,\"file\":\"" + "file" + "\"}");
			logger.debug("Sending success response complete......");

		} catch (Exception e) {

			response.getOutputStream().println(
					"{\"success\": false,\"message\":\"" + e.getMessage()
					+ "\"}");
			e.printStackTrace();

		} finally {

			//fos.flush();
			//fos.close();

		}

		return null;

	}

	public String getWMSImage(String wmsurl, int width, int height,
			String outputFile, float opacity,String layer_name) throws Exception {
		// String _bbox = bbox;

		BufferedImage img = null;
		Rectangle imageBounds = new Rectangle(0, 0, width, height);

		BufferedImage margeImg = new BufferedImage(imageBounds.width,
				imageBounds.height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = margeImg.createGraphics();
		g.setComposite(AlphaComposite.Clear);
		g.setPaint(Color.WHITE);
		g.fill(imageBounds);

		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				opacity));
		URL url=null;

		if(layer_name.equalsIgnoreCase("snpa:Access_Land_polygon")){
			url = new URL(wmsurl + "&BBOX=" + bbox + "&WIDTH=" + width
					+ "&HEIGHT=" + height+ "&antialiasing=on" + "&SLD=" + access_land_sld_file);			

		}
		else if(layer_name.equalsIgnoreCase("Cosmetic_Point,Cosmetic_Line,Cosmetic_Poly") && cosmetic_sld_file!="" ){
			url = new URL(wmsurl + "&BBOX=" + bbox + "&WIDTH=" + width
					+ "&HEIGHT=" + height+  "&antialiasing=on" + "&SLD=" + cosmetic_sld_file);			

		}
		else{
			url = new URL(wmsurl + "&BBOX=" + bbox + "&WIDTH=" + width
					+ "&HEIGHT=" + height+  "&antialiasing=on");
		}

		logger.debug("-------FINAL URL : "+ url);


		img = ImageIO.read(url);

		g.drawImage(img, 0, 0, null);

		mapFileName = System.currentTimeMillis() + "_map.png";
		ImageIO.write(margeImg, "PNG", new File(outputFile + "/" + mapFileName));

		return outputFile + "/" + mapFileName;
	}

	/**
	 * Fetches map images from remote WMS Server
	 * 
	 * @param imgStr
	 * @param width
	 * @param height
	 * @param outputFile
	 * @return
	 */
	public String mergeImages(ArrayList<String> images, int width, int height,
			String outputFile) throws Exception {

		// Add water mark Image added by Aparesh
		//images.add("D:\\apache-tomcat-7.0.25\\webapps\\spatialvue/resources/temp/images/watermark.png");
		//images.add(outputFile + "/"+ watermarkImg);		
		BufferedImage img = null;
		Rectangle imageBounds = new Rectangle(0, 0, width, height);		
		BufferedImage margeImg = new BufferedImage(imageBounds.width,
				imageBounds.height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = margeImg.createGraphics();
		g.setPaint(Color.WHITE);
		g.fill(imageBounds);

		for (int i = 0; i < images.size(); i++) {
			File f = new File(images.get(i));
			//System.out.println("printing: " + i + "  " + f.getName());
			img = ImageIO.read(new File(images.get(i)));
			//if(f.getName().equalsIgnoreCase(watermarkImg)){
			//g.drawImage(img, 160, 25, null);
			//	g.drawImage(img, 275, 170, null);
			//}else{
			g.drawImage(img, 0, 0, null);
			//}


		}

		mapFileName = System.currentTimeMillis() + "_map.png";
		ImageIO.write(margeImg, "PNG", new File(outputFile + "/" + mapFileName));

		return outputUrl + mapFileName;
	}

	public String encodeURI(String argString) {
		final String mark = "-_.!~*'()\"";

		StringBuilder uri = new StringBuilder(); // Encoded URL
		// thanks Marco!

		char[] chars = argString.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			char c = chars[i];
			if ((c >= '0' && c <= '9') || (c >= 'a' && c <= 'z')
					|| (c >= 'A' && c <= 'Z') || mark.indexOf(c) != -1) {
				uri.append(c);
			} else {
				uri.append("%");
				uri.append(Integer.toHexString((int) c));
			}
		}
		return uri.toString();
	}

	public String getContents(File aFile) {
		// ...checks on aFile are elided
		StringBuilder contents = new StringBuilder();

		try {
			// use buffering, reading one line at a time
			// FileReader always assumes default encoding is OK!
			BufferedReader input = new BufferedReader(new FileReader(aFile));
			try {
				String line = null; // not declared within while loop
				/*
				 * readLine is a bit quirky : it returns the content of a line
				 * MINUS the newline. it returns null only for the END of the
				 * stream. it returns an empty String if two newlines appear in
				 * a row.
				 */
				while ((line = input.readLine()) != null) {
					contents.append(line);
					contents.append(System.getProperty("line.separator"));
				}
			} finally {
				input.close();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		return contents.toString();
	}

	private String calculateScale(int inWidth, int inHeight, Envelope env) {

		double imageWidthInPixels = inWidth;
		double extentWidthInMeters = env.getWidth();

		// constants:
		double dpi = 96.0;
		double inchesPerPixel = 1.0 / dpi;
		double inchesPerMeter = 1.0 / 0.0254000508;

		double inchesOnScreen = inchesPerPixel * imageWidthInPixels;
		double inchesInReality = inchesPerMeter * extentWidthInMeters;

		double scaleDenominator = 1 / (inchesOnScreen / inchesInReality);

		// Rectangle tempScreenRect = new Rectangle(inWidth, inHeight);
		// double tempWorldX1 = convert.toWorldX(0);
		// double tempWidth = convert.toWorldX(tempScreenRect.width) -
		// tempWorldX1;
		// if (tempWidth < 0)
		// return "";

		return "1:" + Math.round(scaleDenominator);

	}


	@RequestMapping(value = "/viewer/createreport/", method = RequestMethod.POST)
	@ResponseBody
	public List<MapTemplateDto> getSpatailData(HttpServletRequest request,
			HttpServletResponse response) {

		String bbox="";
		String project_name="";
		List<SpatialUnitTable> spa=new ArrayList<SpatialUnitTable>();
		List<MapTemplateDto> mapdtoList= new ArrayList<MapTemplateDto>();


		try {
			bbox =  ServletRequestUtils.getRequiredStringParameter(request, "hid-bbox");
			project_name=ServletRequestUtils.getRequiredStringParameter(request, "project_name");
			spa=landRecordsService.getSpatialUnitByBbox(bbox,project_name);

			for (int i = 0; i < spa.size(); i++) {

				List<SocialTenureRelationship> personList = landRecordsService.findAllSocialTenureByUsin(spa.get(i).getUsin());
				List<String> pwi = landRecordsController.personWithInterest(spa.get(i).getUsin());
				for (int j = 0; j < personList.size(); j++) {

					List<NaturalPerson> naturalpersonList=new ArrayList<NaturalPerson>();
					if(personList.get(j).getPerson_gid().getPerson_type_gid().getPerson_type_gid()==1){
						naturalpersonList = landRecordsService.naturalPersonById(personList.get(j).getPerson_gid().getPerson_gid());
					}

					else if(personList.get(j).getPerson_gid().getPerson_type_gid().getPerson_type_gid()==2)
					{
						List<NonNaturalPerson> nonNaturalPerson = landRecordsService.nonnaturalPersonById(personList.get(j).getPerson_gid().getPerson_gid());
						naturalpersonList=landRecordsService.naturalPersonById(nonNaturalPerson.get(0).getPoc_gid());
					}	

					if(naturalpersonList.size()!=0){
						MapTemplateDto mapDto=new MapTemplateDto();
						mapDto.setUsin(spa.get(i).getUsin());
						mapDto.setHamletName("");
						if(spa.get(i).getHamlet_Id()!=null)
							mapDto.setHamletName(spa.get(i).getHamlet_Id().getHamletName());
						mapDto.setUka(spa.get(i).getPropertyno());
						mapDto.setName(naturalpersonList.get(0).getFirstName());
						mapDto.setPersonSubType("None");
						if(naturalpersonList.get(0).getPersonSubType()!=null)
							mapDto.setPersonSubType(naturalpersonList.get(0).getPersonSubType().getPersonType());
						mapDto.setPwi("");
						if(j==0 && pwi.size()!=0)
							mapDto.setPwi(pwi.toString().substring(1, pwi.toString().length()-1));
						mapdtoList.add(mapDto);


					}


				}


			}


		} catch (Exception e) {
			logger.error(e);
		}
		System.out.println(bbox);	
		return mapdtoList ;
	}
	
	
	@RequestMapping(value = "/viewer/printlayer/{project}", method = RequestMethod.GET)
	@ResponseBody
	public String printlayer(@PathVariable String project)
	
	{
		
		Project activeproject = projectService.findProjectByName(project);

		Layer layer1 = layerService.findLayerByName(activeproject.getOverlaymap());
		Layer layer2=layerService.findLayerByName(activeproject.getActivelayer());
		
		return layer1.getName()+","+layer2.getName();
	}
	
}