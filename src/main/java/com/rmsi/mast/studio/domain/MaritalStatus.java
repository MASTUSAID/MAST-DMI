package com.rmsi.mast.studio.domain;

import java.io.Serializable;
import java.lang.String;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: MaritalStatus
 *
 * @author Shruti.Thakur
 */
@Entity
@Table(name = "marital_status")
public class MaritalStatus implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "MARITAL_STATUS_ID_GENERATOR", sequenceName = "MARITAL_STATUS_ID_SEQ", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MARITAL_STATUS_ID_GENERATOR")
	@Column(name="maritalstatus_id")
	private int maritalStatusId;

	@Column(nullable = false)
	private String maritalStatus;
	
	@Column(name="maritalstatus_sw")
	private String maritalStatus_sw;

	public MaritalStatus() {
		super();
	}

	public int getMaritalStatusId() {
		return this.maritalStatusId;
	}

	public void setMaritalStatusId(int maritalStatusId) {
		this.maritalStatusId = maritalStatusId;
	}

	public String getMaritalStatus() {
		return this.maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getMaritalStatus_sw() {
		return maritalStatus_sw;
	}

	public void setMaritalStatus_sw(String maritalStatus_sw) {
		this.maritalStatus_sw = maritalStatus_sw;
	}

}
