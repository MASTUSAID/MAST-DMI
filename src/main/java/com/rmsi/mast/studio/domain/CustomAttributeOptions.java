package com.rmsi.mast.studio.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="la_ext_customattributeoptions")
public class CustomAttributeOptions implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="pk_la_ext_customattributeoptionsid_seq",sequenceName="la_ext_customattributeoptionsid_seq", allocationSize=1) 
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_la_ext_customattributeoptionsid_seq") 
	private Integer attributeoptionsid;

	private String optiontext;

	private Integer parentid;


	@ManyToOne
	@JoinColumn(name="customattributeid")
	@JsonIgnore
	private ResourceCustomAttributes customattributeid;
	
 public CustomAttributeOptions(){
		
	}

public Integer getAttributeoptionsid() {
	return attributeoptionsid;
}

public void setAttributeoptionsid(Integer attributeoptionsid) {
	this.attributeoptionsid = attributeoptionsid;
}

public String getOptiontext() {
	return optiontext;
}

public void setOptiontext(String optiontext) {
	this.optiontext = optiontext;
}

public Integer getParentid() {
	return parentid;
}

public void setParentid(Integer parentid) {
	this.parentid = parentid;
}

public ResourceCustomAttributes getCustomattributeid() {
	return customattributeid;
}

public void setCustomattributeid(ResourceCustomAttributes customattributeid) {
	this.customattributeid = customattributeid;
}

}
