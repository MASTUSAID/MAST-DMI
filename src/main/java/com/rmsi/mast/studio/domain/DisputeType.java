package com.rmsi.mast.studio.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="dispute_type")
public class DisputeType implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private int code;
    @Column
    private String name;
    @Column(name = "name_other_lang")
    private String nameOtherLang;
    @Column
    boolean active;
    
    public DisputeType(){
        
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameOtherLang() {
        return nameOtherLang;
    }

    public void setNameOtherLang(String nameOtherLang) {
        this.nameOtherLang = nameOtherLang;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    
}
