package com.rmsi.mast.studio.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 
 * @author Abhay.Pandey
 *
 */

@Entity
@Table(name="la_ext_financialagency")
public class LaExtFinancialagency implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="pk_la_ext_financialagency",sequenceName="la_ext_financialagency_financialagencyid_seq", allocationSize=1) 
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_la_ext_financialagency") 
	private Integer financialagencyid;
	private String financialagency;
	private String financialagency_en;
	private Boolean isactive;
	public Integer getFinancialagencyid() {
		return financialagencyid;
	}
	public void setFinancialagencyid(Integer financialagencyid) {
		this.financialagencyid = financialagencyid;
	}
	public String getFinancialagency() {
		return financialagency;
	}
	public void setFinancialagency(String financialagency) {
		this.financialagency = financialagency;
	}
	public String getFinancialagency_en() {
		return financialagency_en;
	}
	public void setFinancialagency_en(String financialagency_en) {
		this.financialagency_en = financialagency_en;
	}
	public Boolean getIsactive() {
		return isactive;
	}
	public void setIsactive(Boolean isactive) {
		this.isactive = isactive;
	}
	
	

}
