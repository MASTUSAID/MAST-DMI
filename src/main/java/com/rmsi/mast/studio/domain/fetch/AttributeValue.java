package com.rmsi.mast.studio.domain.fetch;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class AttributeValue implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @Id
    private String value;

    @Id
    private Integer listing;

    @Id
    @Column(name = "datatype_id")
    private Integer dataTypeId;

    @Id
    @Column(name = "parent_id")
    private Long parentId;
    
    public AttributeValue() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getListing() {
        return listing;
    }

    public void setListing(Integer listing) {
        this.listing = listing;
    }

    public Integer getDataTypeId() {
        return dataTypeId;
    }

    public void setDataTypeId(Integer dataTypeId) {
        this.dataTypeId = dataTypeId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}
