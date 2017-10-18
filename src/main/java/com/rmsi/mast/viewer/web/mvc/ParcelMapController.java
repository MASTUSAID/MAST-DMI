package com.rmsi.mast.viewer.web.mvc;

import com.rmsi.mast.studio.domain.fetch.SpatialUnitGeom;
import com.rmsi.mast.studio.util.StringUtils;
import com.rmsi.mast.viewer.service.LandRecordsService;
import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.io.ParseException;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperExportManager;
import org.apache.log4j.Logger;
import org.geotools.data.DataUtilities;
import org.geotools.factory.CommonFactoryFinder;
import org.geotools.feature.DefaultFeatureCollection;
import org.geotools.feature.SchemaException;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.geotools.geometry.jts.WKTReader2;
import org.geotools.map.FeatureLayer;
import org.geotools.map.Layer;
import org.geotools.map.MapContent;
import org.geotools.referencing.CRS;
import org.geotools.styling.SLDParser;
import org.geotools.styling.Style;
import org.geotools.styling.StyleFactory;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.TransformException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Contains methods to generate parcel map
 */
@Controller
public class ParcelMapController {

    private static final Logger logger = Logger.getLogger(ParcelMapController.class);

    private static final int DPI = 96;
    private static final String resourcesPath = "/styles/";
    private final int mapMargin = 30;
    private final int minGridCuts = 1;
    private final int coordWidth = 67;
    private final int roundNumber = 5;
    private final int height = 300;
    private final int width = 350;

    @Autowired
    private LandRecordsService landRecordsService;

    @RequestMapping(value = "/viewer/parcelmap/map/{usin}", method = RequestMethod.GET)
    @ResponseBody
    public void getParcelMap(@PathVariable Long usin, HttpServletRequest request, HttpServletResponse response) {
        try {
            BufferedImage mapImage = getMapImage(usin, width, height, false, "");
            if (mapImage == null) {
                mapImage = drawEmptyImage(width, height);
            }

            response.setHeader("Content-Type", "image/jpeg");
            response.addHeader("Content-disposition", "inline; inline; filename=\"map.jpeg\"");
            try (ServletOutputStream out = response.getOutputStream()) {
                ImageIO.write(mapImage, "jpeg", out);
                out.flush();
            }
        } catch (Exception e) {
        }
    }

    // Returns map filled with parcels
    private MapContent getMap(long usin, int width, int height) {
        try {
//            String crsWkt = "GEOGCS[\"WGS 84\", \n"
//                    + "  DATUM[\"World Geodetic System 1984\", \n"
//                    + "    SPHEROID[\"WGS 84\", 6378137.0, 298.257223563, AUTHORITY[\"EPSG\",\"7030\"]], \n"
//                    + "    AUTHORITY[\"EPSG\",\"6326\"]], \n"
//                    + "  PRIMEM[\"Greenwich\", 0.0, AUTHORITY[\"EPSG\",\"8901\"]], \n"
//                    + "  UNIT[\"degree\", 0.017453292519943295], \n"
//                    + "  AXIS[\"Geodetic latitude\", NORTH], \n"
//                    + "  AXIS[\"Geodetic longitude\", EAST], \n"
//                    + "  AUTHORITY[\"EPSG\",\"4326\"]]";

            String crsWkt = "PROJCS[\"WGS 84 / UTM zone 36S\",GEOGCS[\"WGS 84\",DATUM[\"WGS_1984\",SPHEROID[\"WGS 84\",6378137,298.257223563,AUTHORITY[\"EPSG\",\"7030\"]],AUTHORITY[\"EPSG\",\"6326\"]],PRIMEM[\"Greenwich\",0,AUTHORITY[\"EPSG\",\"8901\"]],UNIT[\"degree\",0.0174532925199433,AUTHORITY[\"EPSG\",\"9122\"]],AUTHORITY[\"EPSG\",\"4326\"]],PROJECTION[\"Transverse_Mercator\"],PARAMETER[\"latitude_of_origin\",0],PARAMETER[\"central_meridian\",33],PARAMETER[\"scale_factor\",0.9996],PARAMETER[\"false_easting\",500000],PARAMETER[\"false_northing\",10000000],UNIT[\"metre\",1,AUTHORITY[\"EPSG\",\"9001\"]],AXIS[\"Easting\",EAST],AXIS[\"Northing\",NORTH],AUTHORITY[\"EPSG\",\"32736\"]]";
            CoordinateReferenceSystem crs;
            crs = CRS.parseWKT(crsWkt);

            SimpleFeatureTypeBuilder builder = new SimpleFeatureTypeBuilder();

            builder.setName("parcel");
            builder.setCRS(crs);

            // add attributes in order
            builder.add("geom", Polygon.class);
            builder.length(25).add("label", String.class);
            builder.add("target", Boolean.class);

            // build the type
            final SimpleFeatureType TYPE = builder.buildFeatureType();

            DefaultFeatureCollection parcelFeatures = new DefaultFeatureCollection("parcels", TYPE);

            // Get parcels
            SpatialUnitGeom parcel = landRecordsService.getParcelGeometry(usin);
            WKTReader2 wkt = new WKTReader2();
            
            if (parcel == null) {
                return null;
            }

            String claimLabel = StringUtils.empty(parcel.getPropertyno());

            SimpleFeature parcelFeature = SimpleFeatureBuilder.build(
                    TYPE,
                    new Object[]{wkt.read(parcel.getUtmCoordinates()), claimLabel, true},
                    Long.toString(usin));

            parcelFeatures.add(parcelFeature);

            // Create a map content and add our shapefile to it
            MapContent map = new MapContent();
            map.setTitle("Parcel plan");
            map.getViewport().setCoordinateReferenceSystem(crs);

            StyleFactory styleFactory = CommonFactoryFinder.getStyleFactory();
            SLDParser stylereader;
            URL sldURL;

            // Add parcels layer
            sldURL = ParcelMapController.class.getResource(resourcesPath + "cert_parcel.xml");
            stylereader = new SLDParser(styleFactory, sldURL);
            Style sldStyle = stylereader.readXML()[0];

            Layer layer = new FeatureLayer(DataUtilities.source(parcelFeatures), sldStyle);
            map.addLayer(layer);

            // Set map zoom to the main parcel size + 5%
            Envelope env = ((Polygon) parcelFeature.getDefaultGeometry()).getEnvelopeInternal();
            double percent = 0.05;
            double deltaX = (env.getMaxX() - env.getMinX()) * percent;
            double deltaY = (env.getMaxY() - env.getMinY()) * percent;

            // Make map extent with the same ratio as requested image 
            double imageRatio = (double) height / (double) width;
            double mapRatio = map.getMaxBounds().getHeight() / map.getMaxBounds().getWidth();
            double minX = env.getMinX() - deltaX;
            double maxX = env.getMaxX() + deltaX;
            double minY = env.getMinY() - deltaY;
            double maxY = env.getMaxY() + deltaY;

            if (imageRatio != mapRatio) {
                if (imageRatio < mapRatio) {
                    double newMapWidth = env.getHeight() / imageRatio;
                    minX = minX - newMapWidth / 2;
                    maxX = maxX + newMapWidth / 2;
                } else {
                    double newMapHeight = env.getWidth() * imageRatio;
                    minY = minY - newMapHeight / 2;
                    maxY = maxY + newMapHeight / 2;
                }
            }

            ReferencedEnvelope extent = new ReferencedEnvelope(minX, maxX, minY, maxY,
                    map.getViewport().getCoordinateReferenceSystem());

            map.getViewport().setScreenArea(new Rectangle(width, height));
            map.getViewport().getBounds().expandToInclude(extent);
            map.getViewport().setBounds(extent);

            return map;
        } catch (Exception e) {
            logger.error("Failed to create map", e);
            return null;
        }
    }

    private BufferedImage getMapImage(long usin, int width, int height, boolean drawScale, String scaleLabel) {
        width = width - mapMargin;
        try {
            MapContent map = getMap(usin, width - mapMargin, height - mapMargin);
            //double scale = getBestScaleForMapImage(map, width);

            if (map == null) {
                return null;
            }

            ReferencedEnvelope extent = map.getViewport().getBounds();

            // Set map ratio
            double mapRatio = extent.getHeight() / extent.getWidth();
            double distance = calcDistance(extent);

            // Meters per pixel
            double mpp = distance / width;
            // Degrees per pixel
            double degPp = map.getViewport().getBounds().getWidth() / width;
            double realScale = mpp * (DPI / 2.54) * 100;
            double scale = realScale; // temporary use for any scale. remove if defined scales should be used instead

            if (scale != realScale) {
                double newMpp = scale / 100 / (DPI / 2.54);
                double newDistance = newMpp * width;

                //double mDiff = Math.abs(newDistance - distance);
                double mDiff = newDistance - distance;
                mDiff = mDiff / 2; // 50% required to extend each side

                // Differenr in pixels
                double pDiff = mDiff / mpp;

                // Difference in degrees
                double degDiff = degPp * pDiff;

                extent = new ReferencedEnvelope(
                        map.getViewport().getBounds().getMinX() - degDiff,
                        map.getViewport().getBounds().getMaxX() + degDiff,
                        map.getViewport().getBounds().getMinY() - (degDiff * mapRatio),
                        map.getViewport().getBounds().getMaxY() + (degDiff * mapRatio),
                        map.getViewport().getCoordinateReferenceSystem());

                map.getViewport().getBounds().expandToInclude(extent);
                map.getViewport().setBounds(extent);
            }
            return getMapImage(map, scale, drawScale, scaleLabel);
        } catch (Exception e) {
            logger.error("Failed to generate map image", e);
            return null;
        }
    }

    private BufferedImage getMapImage(MapContent map, double scale, boolean drawScale, String scaleLabel)
            throws SchemaException, ParseException, FactoryException, IOException,
            TransformException, NoninvertibleTransformException {

        if (map == null) {
            return null;
        }

        int width = (int) map.getViewport().getScreenArea().getWidth();
        int height = (int) map.getViewport().getScreenArea().getHeight();
        boolean isWgs84 = map.getViewport().getCoordinateReferenceSystem()
                .getIdentifiers().iterator().next().getCode().equals("4326");

        ReferencedEnvelope extent = map.getViewport().getBounds();

        double distance = calcDistance(extent);

        // Meters per pixel
        double mpp = distance / width;

        // Draw image
        org.geotools.renderer.GTRenderer renderer = new org.geotools.renderer.lite.StreamingRenderer();
        renderer.setMapContent(map);

        Rectangle fullBounds = new Rectangle(0, 0, width + mapMargin, height + mapMargin);
        Rectangle mapBounds = new Rectangle(0, 0, width, height);

        BufferedImage mapImage = new BufferedImage(mapBounds.width, mapBounds.height, BufferedImage.TYPE_INT_RGB);
        BufferedImage fullImage = new BufferedImage(fullBounds.width, fullBounds.height, BufferedImage.TYPE_INT_RGB);

        Graphics2D grMapImage = mapImage.createGraphics();
        grMapImage.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        grMapImage.setPaint(Color.WHITE);
        grMapImage.fill(mapBounds);

        // Draw map
        renderer.paint(grMapImage, mapBounds, extent, map.getViewport().getWorldToScreen());

        // Draw north arrow
        BufferedImage arrow = ImageIO.read(ParcelMapController.class.getResourceAsStream(resourcesPath + "north_arrow.png"));
        grMapImage.drawImage(arrow, width - arrow.getWidth() - 10, 10, null);

        // Draw grid cut
        Graphics2D grFullImage = fullImage.createGraphics();
        Font currentFont = grFullImage.getFont();
        Font newFont = currentFont.deriveFont(currentFont.getSize() * 0.8F);
        grFullImage.setFont(newFont);
        
        grFullImage.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        grFullImage.setPaint(Color.WHITE);
        grFullImage.fill(fullBounds);

        grFullImage.drawImage(mapImage, mapMargin / 2, mapMargin / 2, null);

        grFullImage.setColor(Color.BLACK);
        grFullImage.drawRect((mapMargin / 2) - 1, (mapMargin / 2) - 1, mapBounds.width + 1, mapBounds.height + 1);

        //int gridSize = getBestGridSize(gridDistanceX, gridDistanceY, width);
        int gridSize = 1; //getBestGridSize(width, height, mpp);
        int stepSize = (int) Math.round(width / 4); //(int) Math.round(gridSize / mpp);
        int cutLen = 8;
        grFullImage.setColor(new Color(100, 100, 100));
        grFullImage.setStroke(new BasicStroke(1));
        AffineTransform tr = map.getViewport().getScreenToWorld();

        if (gridSize > 0) {
            // Draw grid
            ArrayList<Integer> xPoints = new ArrayList<Integer>();
            ArrayList<Integer> yPoints = new ArrayList<Integer>();

            int nextX = stepSize + mapMargin / 2;
            int nextY = height + mapMargin / 2 - stepSize;

            // If projected CRS, adjust first point to the round number
            if (!isWgs84) {
                Point2D pointHrz = tr.transform(new Point2D.Double(nextX - (mapMargin / 2), height + mapMargin / 2), null);
                Point2D pointVrt = tr.transform(new Point2D.Double((mapMargin / 2) - 2, nextY - (mapMargin / 2)), null);

                if (Math.round(pointHrz.getX()) % gridSize > 0) {
                    pointHrz.setLocation(Math.round(pointHrz.getX()) - (Math.round(pointHrz.getX()) % gridSize), pointHrz.getY());
                    nextX = (int) Math.round(tr.inverseTransform(pointHrz, null).getX()) + (mapMargin / 2);
                }

                if (Math.round(pointVrt.getY()) % gridSize > 0) {
                    pointVrt.setLocation(pointVrt.getX(), Math.round(pointVrt.getY()) - (Math.round(pointVrt.getY()) % gridSize));
                    nextY = (int) Math.round(tr.inverseTransform(pointVrt, null).getY()) + (mapMargin / 2);
                }
            }

            while (true) {
                // Draw horizontal
                if ((nextX > coordWidth / 2 + mapMargin / 2) && (width + mapMargin / 2 - coordWidth / 2 >= nextX)) {
                    grFullImage.drawLine(nextX, height + mapMargin / 2, nextX, height + mapMargin / 2 - cutLen);
                    grFullImage.drawLine(nextX, mapMargin / 2, nextX, mapMargin / 2 + cutLen);

                    Point2D pointHrz = tr.transform(new Point2D.Double(nextX - (mapMargin / 2), height + mapMargin / 2), null);
                    String pointLabel;

                    if (isWgs84) {
                        pointLabel = Double.toString(round(pointHrz.getX(), roundNumber));
                    } else {
                        pointLabel = Long.toString(Math.round(pointHrz.getX() / 10) * 10);
                    }

                    drawText(grFullImage, pointLabel, nextX, fullBounds.height - 2, true);
                    drawText(grFullImage, pointLabel, nextX, (mapMargin / 2) - 3, true);

                    xPoints.add(nextX);
                }

                // Draw vertical
                if ((mapMargin / 2 + coordWidth / 2 <= nextY) && (nextY < height + mapMargin / 2 - coordWidth / 2)) {
                    grFullImage.drawLine(mapMargin / 2, nextY, mapMargin / 2 + cutLen, nextY);
                    grFullImage.drawLine(width + (mapMargin / 2), nextY, width + (mapMargin / 2) - cutLen, nextY);

                    AffineTransform originalTransform = grFullImage.getTransform();
                    Point2D pointVrt = tr.transform(new Point2D.Double((mapMargin / 2) - 2, nextY - (mapMargin / 2)), null);
                    String pointLabel;

                    if (isWgs84) {
                        pointLabel = Double.toString(round(pointVrt.getY(), roundNumber));
                    } else {
                        pointLabel = Long.toString(Math.round(pointVrt.getY() / 10) * 10);
                    }

                    grFullImage.rotate(-Math.PI / 2, (mapMargin / 2) - 3, nextY);
                    drawText(grFullImage, pointLabel, (mapMargin / 2) - 3, nextY, true);

                    grFullImage.setTransform(originalTransform);

                    grFullImage.rotate(-Math.PI / 2, fullBounds.width - 3, nextY);
                    drawText(grFullImage, pointLabel, fullBounds.width - 3, nextY, true);

                    grFullImage.setTransform(originalTransform);

                    yPoints.add(nextY);
                }

                if ((nextX > width + mapMargin / 2 - coordWidth / 2) && (nextY < mapMargin / 2 + coordWidth / 2)) {
                    break;
                }

                nextX = nextX + stepSize;
                nextY = nextY - stepSize;
            }

            // Draw intersection of xy
            if (xPoints.size() > 0 && yPoints.size() > 0) {
                for (int x : xPoints) {
                    for (int y : yPoints) {
                        grFullImage.drawLine(x, y + cutLen / 2, x, y - cutLen / 2);
                        grFullImage.drawLine(x - cutLen / 2, y, x + cutLen / 2, y);
                    }
                }
            }

        } else {
            // Draw coordinates in the middle only
            grFullImage.drawLine(fullBounds.width / 2, height + mapMargin / 2, fullBounds.width / 2, height + mapMargin / 2 - cutLen);
            grFullImage.drawLine(fullBounds.width / 2, mapMargin / 2, fullBounds.width / 2, mapMargin / 2 + cutLen);

            Point2D pointHrz = tr.transform(new Point2D.Double(fullBounds.width / 2 - (mapMargin / 2), height + mapMargin / 2), null);
            String pointLabel;

            if (isWgs84) {
                pointLabel = Double.toString(round(pointHrz.getX(), roundNumber));
            } else {
                pointLabel = Long.toString(Math.round(pointHrz.getX()));
            }

            drawText(grFullImage, pointLabel, fullBounds.width / 2, fullBounds.height - 2, true);
            drawText(grFullImage, pointLabel, fullBounds.width / 2, (mapMargin / 2) - 3, true);

            // Vertical
            grFullImage.drawLine(mapMargin / 2, fullBounds.height / 2, mapMargin / 2 + cutLen, fullBounds.height / 2);
            grFullImage.drawLine(width + (mapMargin / 2), fullBounds.height / 2, width + (mapMargin / 2) - cutLen, fullBounds.height / 2);

            AffineTransform originalTransform = grFullImage.getTransform();
            Point2D pointVrt = tr.transform(new Point2D.Double((mapMargin / 2) - 2, fullBounds.height / 2 - (mapMargin / 2)), null);

            if (isWgs84) {
                pointLabel = Double.toString(round(pointVrt.getY(), roundNumber));
            } else {
                pointLabel = Long.toString(Math.round(pointVrt.getY()));
            }

            grFullImage.rotate(-Math.PI / 2, (mapMargin / 2) - 3, fullBounds.height / 2);
            drawText(grFullImage, pointLabel, (mapMargin / 2) - 3, fullBounds.height / 2, true);

            grFullImage.setTransform(originalTransform);

            grFullImage.rotate(-Math.PI / 2, fullBounds.width - 3, fullBounds.height / 2);
            drawText(grFullImage, pointLabel, fullBounds.width - 3, fullBounds.height / 2, true);

            grFullImage.setTransform(originalTransform);

            // Cross 
            grFullImage.drawLine(fullBounds.width / 2, fullBounds.height / 2 + cutLen / 2,
                    fullBounds.width / 2, fullBounds.height / 2 - cutLen / 2);
            grFullImage.drawLine(fullBounds.width / 2 - cutLen / 2, fullBounds.height / 2,
                    fullBounds.width / 2 + cutLen / 2, fullBounds.height / 2);
        }

        // Id drawing scale is requested
        if (drawScale) {
            BufferedImage fullImageWithScale = new BufferedImage(fullImage.getWidth(), fullImage.getHeight() + 30, BufferedImage.TYPE_INT_RGB);
            Graphics2D grFullImageWithScale = fullImageWithScale.createGraphics();
            grFullImageWithScale.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            grFullImageWithScale.setPaint(Color.WHITE);
            grFullImageWithScale.fill(new Rectangle(fullImageWithScale.getWidth(), fullImageWithScale.getHeight()));
            grFullImageWithScale.drawImage(fullImage, 0, 0, null);

            grFullImageWithScale.setPaint(Color.BLACK);
            grFullImageWithScale.setFont(new Font("SansSerif", Font.BOLD, 16));

            DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.getDefault());
            DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
            symbols.setGroupingSeparator(' ');
            formatter.setDecimalFormatSymbols(symbols);
            drawText(grFullImageWithScale,
                    String.format("%s 1:%s", scaleLabel, formatter.format(scale)),
                    fullImageWithScale.getWidth() / 2,
                    fullImageWithScale.getHeight() - 1,
                    true);

            return fullImageWithScale;
        } else {
            return fullImage;
        }
    }

    private BufferedImage drawEmptyImage(int width, int height) {
        BufferedImage mapImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D grMapImage = mapImage.createGraphics();
        grMapImage.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        grMapImage.setPaint(Color.WHITE);
        grMapImage.fill(new Rectangle(width, height));
        grMapImage.setPaint(Color.RED);

        String txt = "FAILED TO GENERATE IMAGE";
        FontMetrics fontMetrics = grMapImage.getFontMetrics();
        int txtWidth = fontMetrics.stringWidth(txt);
        grMapImage.drawString(txt, (width / 2) - (txtWidth / 2), (height / 2) + 5);
        return mapImage;
    }

    private double round(double number, int precision) {
        return Math.round(number * Math.pow(10, precision)) / Math.pow(10, precision);
    }

    private void drawText(Graphics2D graphics, String txt, int x, int y, boolean center) {
        if (center) {
            FontMetrics fontMetrics = graphics.getFontMetrics();
            int txtWidth = fontMetrics.stringWidth(txt);
            x = x - txtWidth / 2;
        }
        Color originalColor = graphics.getColor();
        graphics.setColor(Color.BLACK);
        graphics.drawString(txt, x, y);
        graphics.setColor(originalColor);
    }

    private int getBestGridSize(double width, double height, double mpp) {
        // Calculate how many coordinates can fit the width
        int cutsPerWidth = (int) Math.round(width / (coordWidth * 2));

        if (cutsPerWidth <= 0 || width * mpp < 1 || height * mpp < 1) {
            return 0;
        }

        if (cutsPerWidth > minGridCuts) {
            cutsPerWidth = minGridCuts;
        }

        int[] steps = {100000000, 10000000, 1000000, 100000, 10000, 1000, 500, 100, 50, 10, 1};

        for (int cuts = cutsPerWidth; cuts >= 1; cuts--) {
            for (int step : steps) {
                if ((cuts * step) <= (width - (coordWidth / 2)) * mpp && step <= (height - (coordWidth / 2)) * mpp) {
                    return step;
                }
            }
        }
        return 0;
    }

    private int getBestScaleForMapImage(MapContent map, int width) {
        try {
            ReferencedEnvelope extent = map.getViewport().getBounds();

            double distance = calcDistance(extent);

            // Meters per pixel
            double mpp = distance / width;
            double realScale = mpp * (DPI / 2.54) * 100;

            int[] scales = {100, 500, 1000, 2000, 5000, 10000, 15000, 20000, 25000,
                50000, 75000, 100000, 150000, 200000, 250000, 500000, 750000, 1000000};
            for (int scale : scales) {
                if (realScale < scale) {
                    return scale;
                }
            }
            return 1000000;
        } catch (Exception e) {
            logger.error("Failed to calculate best scale for the map image", e);
            return 0;
        }
    }

    private double calcDistance(ReferencedEnvelope extent) {
        if (isWgs84(extent.getCoordinateReferenceSystem())) {
            double earthRadius = 6371000; //meters
            double dLat = Math.toRadians(extent.getMaxX() - extent.getMinX());
            double dLng = Math.toRadians(extent.getMaxY() - extent.getMaxY());
            double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                    + Math.cos(Math.toRadians(extent.getMinX())) * Math.cos(Math.toRadians(extent.getMaxX()))
                    * Math.sin(dLng / 2) * Math.sin(dLng / 2);
            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
            float dist = (float) (earthRadius * c);
            return dist;
        } else {
            return extent.getMaxX() - extent.getMinX();
        }
    }

    private boolean isWgs84(CoordinateReferenceSystem crs) {
        return crs.getIdentifiers().iterator().next().getCode().equals("4326");
    }
}
