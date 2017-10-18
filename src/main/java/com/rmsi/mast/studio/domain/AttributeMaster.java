package com.rmsi.mast.studio.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * The persistent class for the attribute_master database table.
 * 
 */
@Entity
@Table(name = "attribute_master")
public class AttributeMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "ATTRIBUTE_MASTER_ID_GENERATOR", sequenceName = "ATTRIBUTE_MASTER_ID_SEQ", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ATTRIBUTE_MASTER_ID_GENERATOR")
	private long id;

	private String alias;

	private String fieldname;

	private Integer listing;

	private Boolean mandatory;

	private String reftable;

	private String size;

	private boolean active;
	
	private String alias_second_language;
	
	private boolean master_attrib;
	
	public boolean isMaster_attrib() {
		return master_attrib;
	}

	public void setMaster_attrib(boolean master_attrib) {
		this.master_attrib = master_attrib;
	}

	// bi-directional many-to-one association to AttributeCategory
	@ManyToOne
	@JoinColumn(name = "attributecategoryid")
	private AttributeCategory attributeCategory;

	// bi-directional many-to-one association to DatatypeId
	@ManyToOne
	@JoinColumn(name = "datatype_id")
	private DatatypeId datatypeIdBean;

	/*
	 * //bi-directional many-to-one association to Surveyprojectattribute
	 * 
	 * @OneToMany(mappedBy="attributeMaster", fetch=FetchType.EAGER) private
	 * List<Surveyprojectattribute> surveyprojectattributes;
	 */

	@Transient
	private List<AttributeOptions> attributeOptions;

	public List<AttributeOptions> getAttributeOptions() {
		return attributeOptions;
	}

	public void setAttributeOptions(List<AttributeOptions> attributeOptions) {
		this.attributeOptions = attributeOptions;
	}

	public AttributeMaster() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAlias() {
		return this.alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getFieldname() {
		return this.fieldname;
	}

	public String getAlias_second_language() {
		return alias_second_language;
	}

	public void setAlias_second_language(String alias_second_language) {
		this.alias_second_language = alias_second_language;
	}

	public void setFieldname(String fieldname) {
		this.fieldname = fieldname;
	}

	public Integer getListing() {
		return this.listing;
	}

	public void setListing(Integer listing) {
		this.listing = listing;
	}

	public Boolean getMandatory() {
		return this.mandatory;
	}

	public void setMandatory(Boolean mandatory) {
		this.mandatory = mandatory;
	}

	public String getReftable() {
		return this.reftable;
	}

	public void setReftable(String reftable) {
		this.reftable = reftable;
	}

	public String getSize() {
		return this.size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public AttributeCategory getAttributeCategory() {
		return this.attributeCategory;
	}

	public void setAttributeCategory(AttributeCategory attributeCategory) {
		this.attributeCategory = attributeCategory;
	}

	public DatatypeId getDatatypeIdBean() {
		return this.datatypeIdBean;
	}

	public void setDatatypeIdBean(DatatypeId datatypeIdBean) {
		this.datatypeIdBean = datatypeIdBean;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	/*
	 * public List<Surveyprojectattribute> getSurveyprojectattributes() { return
	 * this.surveyprojectattributes; }
	 * 
	 * public void setSurveyprojectattributes(List<Surveyprojectattribute>
	 * surveyprojectattributes) { this.surveyprojectattributes =
	 * surveyprojectattributes; }
	 * 
	 * public Surveyprojectattribute
	 * addSurveyprojectattribute(Surveyprojectattribute surveyprojectattribute)
	 * { getSurveyprojectattributes().add(surveyprojectattribute);
	 * surveyprojectattribute.setAttributeMaster(this);
	 * 
	 * return surveyprojectattribute; }
	 * 
	 * public Surveyprojectattribute
	 * removeSurveyprojectattribute(Surveyprojectattribute
	 * surveyprojectattribute) {
	 * getSurveyprojectattributes().remove(surveyprojectattribute);
	 * surveyprojectattribute.setAttributeMaster(null);
	 * 
	 * return surveyprojectattribute; }
	 */

}