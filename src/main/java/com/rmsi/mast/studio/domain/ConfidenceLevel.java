package com.rmsi.mast.studio.domain;

import java.io.Serializable;
import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "la_ext_confidence_level")
public class ConfidenceLevel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private int id;

    @Column
    private String name;

    @Column(name = "name_en")
    private String nameEn;

    @Column(name="isactive")
    boolean active;
    
    public ConfidenceLevel() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
