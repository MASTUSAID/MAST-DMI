package com.rmsi.mast.studio.domain.fetch;

import java.io.Serializable;


public class Commune  implements Serializable{

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer Communeid;
	
	private String commune;

	public Integer getCommuneid() {
		return Communeid;
	}

	public void setCommuneid(Integer communeid) {
		Communeid = communeid;
	}

	public String getCommune() {
		return commune;
	}

	public void setCommune(String commune) {
		this.commune = commune;
	}
	
	
	
	
	
}
