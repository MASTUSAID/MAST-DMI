package com.rmsi.mast.studio.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "claim_type")
public class ClaimType implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String CODE_NEW = "newClaim";
    public static final String CODE_EXISTING = "existingClaim";
    public static final String CODE_DISPUTED = "dispute";
    public static final String CODE_UNCLAIMED = "unclaimed";

    @Id
    private String code;
    @Column
    private String name;
    @Column(name = "name_other_lang")
    private String nameOtherLang;
    @Column
    boolean active;

    public ClaimType() {

    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
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
