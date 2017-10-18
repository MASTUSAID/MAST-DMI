package com.rmsi.mast.studio.domain.fetch;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import com.vividsolutions.jts.geom.Geometry;
import org.hibernate.annotations.Formula;

/**
 * Class for fetching parcel geometry and it's label
 */
@Entity
@Table(name = "spatial_unit")
public class SpatialUnitGeom implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private long usin;

    @Column(name = "uka_propertyno", insertable = false, updatable = false)
    private String propertyno;
	
    @Column(name = "the_geom", columnDefinition = "Geometry", insertable = false, updatable = false)
    private Geometry theGeom;

    @Formula("st_astext(st_transform(the_geom,32736))")
    private String utmCoordinates;
    
    public long getUsin() {
        return usin;
    }

    public void setUsin(long usin) {
        this.usin = usin;
    }

    public String getPropertyno() {
        return propertyno;
    }

    public void setPropertyno(String propertyno) {
        this.propertyno = propertyno;
    }

    public Geometry getTheGeom() {
        return theGeom;
    }

    public void setTheGeom(Geometry theGeom) {
        this.theGeom = theGeom;
    }

    public String getUtmCoordinates() {
        return utmCoordinates;
    }

    public void setUtmCoordinates(String utmCoordinates) {
        this.utmCoordinates = utmCoordinates;
    }
}
