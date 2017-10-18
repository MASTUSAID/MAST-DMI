package com.rmsi.mast.studio.domain.fetch;

import com.rmsi.mast.studio.util.StringUtils;
import java.io.Serializable;

import javax.persistence.*;

/**
 * The persistent class for the spatialunit_pesonwithinterest database table.
 *
 */
@Entity
@Table(name = "spatialunit_deceased_person")
public class SpatialunitDeceasedPerson implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "DECEASED_PERSON_ID_GENERATOR", sequenceName = "spatial_unit_deceased_person_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DECEASED_PERSON_ID_GENERATOR")
    private Long id;
    
    @Column
    private String firstname;
    
    @Column
    private String middlename;
    
    @Column
    private String lastname;
    
    @Column
    private long usin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public long getUsin() {
        return usin;
    }

    public void setUsin(long usin) {
        this.usin = usin;
    }

    public String getFullName() {
        String name = "";
        if (!StringUtils.isEmpty(getFirstname())) {
            name = getFirstname().trim();
        }
        if (!StringUtils.isEmpty(getMiddlename())) {
            if (name.length() > 0) {
                name = name + " " + getMiddlename().trim();
            } else {
                name = getMiddlename().trim();
            }
        }
        if (!StringUtils.isEmpty(getLastname())) {
            if (name.length() > 0) {
                name = name + " " + getLastname().trim();
            } else {
                name = getLastname().trim();
            }
        }
        return name;
    }
}
