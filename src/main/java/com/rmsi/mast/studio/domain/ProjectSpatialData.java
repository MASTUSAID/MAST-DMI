package com.rmsi.mast.studio.domain;

import java.io.Serializable;

import javax.persistence.*;

/**
 * The persistent class for the project_spatial_data database table.
 * 
 */
@Entity
@Table(name = "project_spatial_data")
public class ProjectSpatialData implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "PROJECT_SPATIAL_DATA_ID_GENERATOR", sequenceName = "PROJECT_SPATIAL_DATA_ID_SEQ", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PROJECT_SPATIAL_DATA_ID_GENERATOR")
	private Integer id;

	@Column(name = "file_extension")
	private String fileExtension;

	@Column(name = "file_location")
	private String fileLocation;

	@Column(name = "file_name")
	private String fileName;
	@Column(name="file_size")
	private Long size;
	
	@Column(name="alias")
	private String alias;
	

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	private String name;

	public ProjectSpatialData() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFileExtension() {
		return this.fileExtension;
	}

	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}

	public String getFileLocation() {
		return this.fileLocation;
	}

	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}