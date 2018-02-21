package com.rmsi.mast.studio.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "la_ext_process")
public class LaExtProcess implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private  Integer processid;
	private  String processname;
	private  String processname_en;
	private  boolean isactive;
	
	public Integer getProcessid() {
		return processid;
	}
	public void setProcessid(Integer processid) {
		this.processid = processid;
	}
	public String getProcessname() {
		return processname;
	}
	public void setProcessname(String processname) {
		this.processname = processname;
	}
	public String getProcessname_en() {
		return processname_en;
	}
	public void setProcessname_en(String processname_en) {
		this.processname_en = processname_en;
	}
	public boolean isIsactive() {
		return isactive;
	}
	public void setIsactive(boolean isactive) {
		this.isactive = isactive;
	}

	
}
