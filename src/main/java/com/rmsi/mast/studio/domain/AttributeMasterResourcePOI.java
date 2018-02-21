package com.rmsi.mast.studio.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="la_ext_resourcepoiattributemaster")
public class AttributeMasterResourcePOI implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@SequenceGenerator(name="pk_la_ext_resourcepoiattributemasterid_seq",sequenceName="la_ext_resourcepoiattributemasterid_seq", allocationSize=1) 
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_la_ext_resourcepoiattributemasterid_seq") 
	private Integer poiattributemasterid;

	private String fieldaliasname;

	private String fieldname;

	@Column(name="isactive")
	private Boolean isactive;

	private String listing;

	private Boolean mandatory;

	private String referencetable;

	private String size;

	private boolean masterattribute;
	
//	@ManyToOne
//	@JoinColumn(name="attributecategoryid")
//	private AttributeCategory laExtAttributecategory;
	
	private Integer attributecategoryid;

	//bi-directional many-to-one association to LaExtAttributedatatype
	@ManyToOne
	@JoinColumn(name="datatypemasterid")
	private DatatypeId laExtAttributedatatype;
	
	
    @OneToMany(mappedBy="attributeMaster", cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	private List<AttributeOptions> options;
	
    
    public AttributeMasterResourcePOI(){
    	
    }


	public Integer getPoiattributemasterid() {
		return poiattributemasterid;
	}


	public void setPoiattributemasterid(Integer poiattributemasterid) {
		this.poiattributemasterid = poiattributemasterid;
	}


	public String getFieldaliasname() {
		return fieldaliasname;
	}


	public void setFieldaliasname(String fieldaliasname) {
		this.fieldaliasname = fieldaliasname;
	}


	public String getFieldname() {
		return fieldname;
	}


	public void setFieldname(String fieldname) {
		this.fieldname = fieldname;
	}


	public Boolean getIsactive() {
		return isactive;
	}


	public void setIsactive(Boolean isactive) {
		this.isactive = isactive;
	}


	public String getListing() {
		return listing;
	}


	public void setListing(String listing) {
		this.listing = listing;
	}


	public Boolean getMandatory() {
		return mandatory;
	}


	public void setMandatory(Boolean mandatory) {
		this.mandatory = mandatory;
	}


	public String getReferencetable() {
		return referencetable;
	}


	public void setReferencetable(String referencetable) {
		this.referencetable = referencetable;
	}


	public String getSize() {
		return size;
	}


	public void setSize(String size) {
		this.size = size;
	}


	public boolean isMasterattribute() {
		return masterattribute;
	}


	public void setMasterattribute(boolean masterattribute) {
		this.masterattribute = masterattribute;
	}


	public Integer getAttributecategoryid() {
		return attributecategoryid;
	}


	public void setAttributecategoryid(Integer attributecategoryid) {
		this.attributecategoryid = attributecategoryid;
	}


	public DatatypeId getLaExtAttributedatatype() {
		return laExtAttributedatatype;
	}


	public void setLaExtAttributedatatype(DatatypeId laExtAttributedatatype) {
		this.laExtAttributedatatype = laExtAttributedatatype;
	}


	public List<AttributeOptions> getOptions() {
		return options;
	}


	public void setOptions(List<AttributeOptions> options) {
		this.options = options;
	}
    
    

}
