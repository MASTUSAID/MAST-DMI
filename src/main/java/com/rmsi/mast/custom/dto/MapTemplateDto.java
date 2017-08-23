package com.rmsi.mast.custom.dto;

import java.io.Serializable;

public class MapTemplateDto  implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long usin;
	private String name;
	private String personSubType;
	private String pwi;
	private String uka;
	private String hamletName;
	
	public long getUsin() {
		return usin;
	}
	public void setUsin(long usin) {
		this.usin = usin;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPersonSubType() {
		return personSubType;
	}
	public void setPersonSubType(String personSubType) {
		this.personSubType = personSubType;
	}
	public String getPwi() {
		return pwi;
	}
	public void setPwi(String pwi) {
		this.pwi = pwi;
	}
	public String getUka() {
		return uka;
	}
	public void setUka(String uka) {
		this.uka = uka;
	}
	public String getHamletName() {
		return hamletName;
	}
	public void setHamletName(String hamletName) {
		this.hamletName = hamletName;
	}
	
}
