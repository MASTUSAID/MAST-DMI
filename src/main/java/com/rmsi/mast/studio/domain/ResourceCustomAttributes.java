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
@Table(name = "la_ext_resource_custom_attribute")
public class ResourceCustomAttributes implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@SequenceGenerator(name="pk_la_ext_resource_custom_attribute",sequenceName="la_ext_resource_custom_attribute_customattributeid_seq", allocationSize=1) 
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_la_ext_resource_custom_attribute") 
	private Integer customattributeid;

	private String fieldaliasname;

	private String fieldname;

	@Column(name="isactive")
	private Boolean isactive;

	private String listing;

	private Boolean mandatory;

	private String referencetable;

	private String size;

	private boolean masterattribute;
	
	private Integer subclassificationid;
	
	private Integer projectid;
	
	@ManyToOne
	@JoinColumn(name="attributecategoryid")
	private AttributeCategory attributecategoryid;

	//bi-directional many-to-one association to LaExtAttributedatatype
	@ManyToOne
	@JoinColumn(name="datatypemasterid")
	private DatatypeId datatypemasterid;
	
	
	@OneToMany(mappedBy="customattributeid", cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	private List<CustomAttributeOptions> options;
	
	public ResourceCustomAttributes(){
		
		}

	public Integer getCustomattributeid() {
		return customattributeid;
	}

	public void setCustomattributeid(Integer customattributeid) {
		this.customattributeid = customattributeid;
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

	public Integer getSubclassificationid() {
		return subclassificationid;
	}

	public void setSubclassificationid(Integer subclassificationid) {
		this.subclassificationid = subclassificationid;
	}

	public Integer getProjectid() {
		return projectid;
	}

	public void setProjectid(Integer projectid) {
		this.projectid = projectid;
	}

	public AttributeCategory getAttributecategoryid() {
		return attributecategoryid;
	}

	public void setAttributecategoryid(AttributeCategory attributecategoryid) {
		this.attributecategoryid = attributecategoryid;
	}

	public DatatypeId getDatatypemasterid() {
		return datatypemasterid;
	}

	public void setDatatypemasterid(DatatypeId datatypemasterid) {
		this.datatypemasterid = datatypemasterid;
	}

	public List<CustomAttributeOptions> getOptions() {
		return options;
	}

	public void setOptions(List<CustomAttributeOptions> options) {
		this.options = options;
	}
	
	
	
}
