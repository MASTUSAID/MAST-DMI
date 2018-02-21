package com.rmsi.mast.studio.domain.fetch;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import com.rmsi.mast.studio.domain.AttributeMaster;

@MappedSuperclass
public class AttributeValue implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
	private Integer surveyprojectattributesid;

	private String attributevalue;

	private Integer parentuid;
	
	 private String listing;
	 
	 private Integer datatypemasterid;
	
//	private Long uid;


	//bi-directional many-to-one association to LaExtAttributemaster
//	@ManyToOne
//	@JoinColumn(name="attributemasterid")
//	private AttributeMaster laExtAttributemaster;

	
	public AttributeValue() {
		}


//	public Integer getAttributeid() {
//		return attributeid;
//	}
//
//
//	public void setAttributeid(Integer attributeid) {
//		this.attributeid = attributeid;
//	}

	
	

	public String getAttributevalue() {
		return attributevalue;
	}


	public Integer getSurveyprojectattributesid() {
		return surveyprojectattributesid;
	}


	public void setSurveyprojectattributesid(Integer surveyprojectattributesid) {
		this.surveyprojectattributesid = surveyprojectattributesid;
	}


	public Integer getParentuid() {
		return parentuid;
	}


	public void setParentuid(Integer parentuid) {
		this.parentuid = parentuid;
	}


	public String getListing() {
		return listing;
	}


	public void setListing(String listing) {
		this.listing = listing;
	}


	public Integer getDatatypemasterid() {
		return datatypemasterid;
	}


	public void setDatatypemasterid(Integer datatypemasterid) {
		this.datatypemasterid = datatypemasterid;
	}


	public void setAttributevalue(String attributevalue) {
		this.attributevalue = attributevalue;
	}


//	public Long getParentuid() {
//		return parentuid;
//	}
//
//
//	public void setParentuid(Long parentuid) {
//		this.parentuid = parentuid;
//	}


//	public AttributeMaster getLaExtAttributemaster() {
//		return laExtAttributemaster;
//	}
//
//
//	public void setLaExtAttributemaster(AttributeMaster laExtAttributemaster) {
//		this.laExtAttributemaster = laExtAttributemaster;
//	}
//
//	
	
	
	
	
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getValue() {
//        return value;
//    }
//
//    public void setValue(String value) {
//        this.value = value;
//    }
//
//    public Integer getListing() {
//        return listing;
//    }
//
//    public void setListing(Integer listing) {
//        this.listing = listing;
//    }
//
//    public Integer getDataTypeId() {
//        return dataTypeId;
//    }
//
//    public void setDataTypeId(Integer dataTypeId) {
//        this.dataTypeId = dataTypeId;
//    }
//
//    public Long getParentId() {
//        return parentId;
//    }
//
//    public void setParentId(Long parentId) {
//        this.parentId = parentId;
//    }
}
