/**
 * 
 */
package com.rmsi.mast.studio.util;

import java.util.Iterator;
import java.util.List;

import com.rmsi.mast.studio.domain.SpatialUnit;
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

/**
 * This class will be used in converting Geometry Type to String and vice-versa
 * 
 * @author shruti.thakur
 */
public class GeometryConversion {

	/**
	 * This method converts the Geometry to String
	 * 
	 * @param spatialUnitList
	 * @return
	 */
	public List<SpatialUnit> converGeometryToString(
			List<SpatialUnit> spatialUnitList) {

		Iterator<SpatialUnit> spatialUnitIter = spatialUnitList.iterator();

		Geometry geom = null;

		SpatialUnit spatialUnitLocal = null;

		String coords = "";

		while (spatialUnitIter.hasNext()) {

			spatialUnitLocal = spatialUnitIter.next();

			coords = "";

			if (spatialUnitLocal.getGtype().equalsIgnoreCase("point")) {
				geom = spatialUnitLocal.getPoint();
				String coString = geom.toText();
				System.out.println("Point coordinates:" + coString);
				coords = coString.substring(coString.indexOf("(") + 1);
				coords = coords.substring(0, coords.indexOf(")"));
				spatialUnitLocal.setGeometry(coords);
			} else if (spatialUnitLocal.getGtype().equalsIgnoreCase("line")) {
				geom = spatialUnitLocal.getLine();
				
				String coString = geom.toText();
				System.out.println("Line coordinates:" + coString);
				coords = coString.substring(coString.indexOf("(") + 1);
				coords = coords.substring(0, coords.indexOf(")"));
				spatialUnitLocal.setGeometry(coords);
			} else if (spatialUnitLocal.getGtype().equalsIgnoreCase("polygon")) {
				geom = spatialUnitLocal.getPolygon();
				
				String coString = geom.toText();
				System.out.println("Polygon coordinates:" + coString);
				coords = coString.substring(coString.indexOf("((") + 2);
				coords = coords.substring(0, coords.indexOf("))"));
				spatialUnitLocal.setGeometry(coords);
			} else {
				System.out.println("GType doesn't contain relevent value");
			}

		}
		return spatialUnitList;
	}

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
	
	
}
