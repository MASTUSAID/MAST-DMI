package com.rmsi.mast.custom.dto;

import java.io.Serializable;

public class AttributeDto  implements Serializable{
	
	
	private static final long serialVersionUID = 1L;
	private String alias;
	private long Id;
	private boolean flag;
	private Long uid;
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public long getId() {
		return Id;
	}
	public void setId(long id) {
		Id = id;
	}
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
}
