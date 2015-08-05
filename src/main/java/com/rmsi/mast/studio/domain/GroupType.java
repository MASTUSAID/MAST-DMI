package com.rmsi.mast.studio.domain;

import java.io.Serializable;
import java.lang.String;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: GroupType
 *
 */
@Entity
@Table(name = "group_type")
public class GroupType implements Serializable {

	private static final long serialVersionUID = 1L;
	   
	@Id
	@Column(name = "group_id")
	private int groupId;

	@Column(name = "group_value")
	private String groupValue;
	
	@Column(name = "group_value_sw")
	private String groupValue_sw;

	public GroupType() {
		super();
	}   
	public int getGroupId() {
		return this.groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}   
	public String getGroupValue() {
		return this.groupValue;
	}

	public void setGroupValue(String groupValue) {
		this.groupValue = groupValue;
	}
	public String getGroupValue_sw() {
		return groupValue_sw;
	}
	public void setGroupValue_sw(String groupValue_sw) {
		this.groupValue_sw = groupValue_sw;
	}
   
}
