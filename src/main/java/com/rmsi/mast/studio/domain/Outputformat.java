package com.rmsi.mast.studio.domain;
import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;


/**
 * The persistent class for the la_ext_documentformat database table.
 * 
 */
@Entity
@Table(name="la_ext_documentformat")
public class Outputformat implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Integer documentformatid;

	private String documentformat;

	@Column(name="documentformat_en")
	private String documentformatEn;

	private Boolean isactive;

	

	public Outputformat() {
	}

	public Integer getDocumentformatid() {
		return this.documentformatid;
	}

	public void setDocumentformatid(Integer documentformatid) {
		this.documentformatid = documentformatid;
	}

	public String getDocumentformat() {
		return this.documentformat;
	}

	public void setDocumentformat(String documentformat) {
		this.documentformat = documentformat;
	}

	public String getDocumentformatEn() {
		return this.documentformatEn;
	}

	public void setDocumentformatEn(String documentformatEn) {
		this.documentformatEn = documentformatEn;
	}

	public Boolean getIsactive() {
		return this.isactive;
	}

	public void setIsactive(Boolean isactive) {
		this.isactive = isactive;
	}

	



}