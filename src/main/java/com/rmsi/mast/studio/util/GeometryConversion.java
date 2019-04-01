package com.rmsi.mast.studio.util;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.ibm.icu.text.DecimalFormat;
import com.rmsi.mast.studio.domain.SpatialUnit;
import com.rmsi.mast.studio.mobile.service.impl.SurveyProjectAttributeServiceImp;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.geom.PrecisionModel;
import com.vividsolutions.jts.geom.impl.CoordinateArraySequence;

import org.apache.log4j.Logger;
import org.hibernate.annotations.common.util.impl.Log_$logger;
import org.postgis.MultiLineString;

/**
 * This class will be used in converting Geometry Type to String and vice-versa
 */
public class GeometryConversion {

    private static final Logger logger = Logger.getLogger(GeometryConversion.class.getName());

    /**
     * This method will get Coordinate Sequence
     *
     * @param wkt
     * @return
     */
    public CoordinateArraySequence getCoordinateSequence(String wkt) {

        String[] coord = wkt.split(",");

        try {
            Coordinate[] coordinate = new Coordinate[coord.length];

            for (int i = 0; i < coord.length; i++) {

                String axis[] = coord[i].split(" ");

                coordinate[i] = new Coordinate(Double.parseDouble(axis[0]),
                        Double.parseDouble(axis[1]));

            }

            return new CoordinateArraySequence(coordinate);
        } catch (Exception ex) {

            System.out.println("Exception while fetching coordinates::::::"
                    + ex);
            return null;
        }
    }

    /**
     * This method will convert wkt to Polygon
     *
     * @param wkt
     * @return
     */
    public Polygon convertWktToPolygon(String wkt) {

        return new Polygon(new LinearRing(getCoordinateSequence(wkt),
                new GeometryFactory(new PrecisionModel(), 4326)), null, new GeometryFactory());
    }

    /**
     * This method will convert wkt to LineString
     *
     * @param wkt
     * @return
     */
    public LineString convertWktToLineString(String wkt) {

        return new LineString(getCoordinateSequence(wkt), new GeometryFactory(new PrecisionModel(), 4326));
    }

    /**
     * This method will convert wkt to Point
     *
     * @param wkt
     * @return
     */
    public Point convertWktToPoint(String wkt) {

        return new Point(getCoordinateSequence(wkt), new GeometryFactory(new PrecisionModel(), 4326));
    }
    
    
    public double getArea(String wkt) {
    	String[] coord = wkt.split(",");
    	List<Coordinate>  lstcoordinate = new ArrayList<Coordinate>();
        try {
           

            for (int i = 0; i < coord.length; i++) {

                String axis[] = coord[i].split(" ");

                Coordinate objCoordinate = new Coordinate(Double.parseDouble(axis[0]),Double.parseDouble(axis[1]));
                lstcoordinate.add(objCoordinate);
                
            }

            }catch(Exception e)
            {
            	e.printStackTrace();
            }
    	List<Coordinate> tmpJtsPoints = new ArrayList<Coordinate>(lstcoordinate);
        tmpJtsPoints.add(tmpJtsPoints.get(0)); // adding first pointat last to create Ring
        Coordinate[] coordinates = tmpJtsPoints.toArray(new Coordinate[tmpJtsPoints.size()]);
        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
        LinearRing lr =geometryFactory.createLinearRing(coordinates);
        com.vividsolutions.jts.geom.Polygon polygonJts =geometryFactory.createPolygon(lr, null);
        double area = polygonJts.getArea();
        double areaMeter = area * (Math.pow(111319.9, 2)) *Math.cos(Math.toRadians(tmpJtsPoints.get(0).y));
        //double areaAcer=areaMeter* 0.00024710538149159;
        
        return areaMeter;
  }

    
   

}
