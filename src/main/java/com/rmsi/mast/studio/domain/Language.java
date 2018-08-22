package com.rmsi.mast.studio.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="la_ext_language")
public class Language implements Serializable {
    
    @Id
    @Column
    private String code;
    
    @Column
    private String val;
    
    @Column
    private boolean active;
    
    @Column(name="is_default")
    private boolean isDefault;
    
    @Column(name="item_order")
    private int itemOrder;
    
    @Column
    private boolean ltr;
    
    public Language(){
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    public int getItemOrder() {
        return itemOrder;
    }

    public void setItemOrder(int itemOrder) {
        this.itemOrder = itemOrder;
    }

    public boolean getLtr() {
        return ltr;
    }

    public void setLtr(boolean ltr) {
        this.ltr = ltr;
    }
}
