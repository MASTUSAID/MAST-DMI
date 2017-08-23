package com.rmsi.mast.studio.domain;

import java.io.Serializable;
import java.lang.String;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: TenureClass
 *
 * @author Shruti.Thakur
 */
@Entity
@Table(name = "tenure_class")
public class TenureClass implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "tenureclass_id")
	private int tenureId;

	@Column(name = "tenure_class", nullable = false)
	private String tenureClass;
	
	private boolean active;

	public TenureClass() {
		super();
	}

	public int getTenureId() {
		return this.tenureId;
	}

	public void setTenureId(int tenureId) {
		this.tenureId = tenureId;
	}

	public String getTenureClass() {
		return this.tenureClass;
	}

	public void setTenureClass(String tenureClass) {
		this.tenureClass = tenureClass;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

}
