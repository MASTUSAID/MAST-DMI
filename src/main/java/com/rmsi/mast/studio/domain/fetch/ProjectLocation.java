package com.rmsi.mast.studio.domain.fetch;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Returns project location names
 */
@Entity
public class ProjectLocation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String projectnameid;

    @Column
    private String country;

    @Column
    private String county;

    @Column
    private String district;

    @Column
    private String clan;

    @Column
    private String village;

    public ProjectLocation() {

    }

    public String getProjectnameid() {
        return projectnameid;
    }

    public void setProjectnameid(String projectnameid) {
        this.projectnameid = projectnameid;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getClan() {
        return clan;
    }

    public void setClan(String clan) {
        this.clan = clan;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

}
