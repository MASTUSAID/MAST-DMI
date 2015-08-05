package com.rmsi.mast.studio.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity implementation class for Entity: EducationLevel
 *
 */
@Entity
@Table(name="education_level")
public class EducationLevel implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "level_Id")
	private int levelId;

	@Column(name = "education_level")
	private String educationLevel;

	public EducationLevel() {
		super();
	}

	public int getLevelId() {
		return this.levelId;
	}

	public void setLevelId(int levelId) {
		this.levelId = levelId;
	}

	public String getEducationLevel() {
		return this.educationLevel;
	}

	public void setEducationLevel(String educationLevel) {
		this.educationLevel = educationLevel;
	}

}
