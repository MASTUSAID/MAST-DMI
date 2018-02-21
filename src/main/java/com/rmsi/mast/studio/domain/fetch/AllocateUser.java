package com.rmsi.mast.studio.domain.fetch;

import java.io.Serializable;

public class AllocateUser  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long userid;
	
	private String name;

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

}
