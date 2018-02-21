package com.rmsi.mast.studio.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

/**
 * The persistent class for the project_spatial_data database table.
 * 
 */
@Entity
@Table(name = "la_ext_projectfile")
public class ProjectSpatialData implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "pk_la_ext_projectfile", sequenceName = "la_ext_projectfile_projectfileid_seq", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_la_ext_projectfile")
	 @Column(name = "projectfileid")
	private Integer id;
	
	
	@Column(name = "projectfilename")
	private String fileName;
	
	private Integer projectnameid;
	@Column(name = "filelocation")
	private String fileLocation;
	private String description;
	private Boolean isactive;
	
	private long createdby;
	private long modifiedby;
    private Date createddate;
    private Date modifieddate;
	
    //below before extra column
	@Column(name = "documentformatid")
	private Integer documentformatid;
	@Column(name="filesize")
	private Long size;
//	@Column(name="alias")
//	private String alias;

	
	/*@ManyToOne
	@JoinColumn(name="projectnameid")
	private LaSpatialsourceProjectname laSpatialsourceProjectname;*/
	
   public ProjectSpatialData(){
	
}


public Integer getId() {
	return id;
}


public void setId(Integer id) {
	this.id = id;
}


public String getFileName() {
	return fileName;
}


public void setFileName(String fileName) {
	this.fileName = fileName;
}


public Integer getProjectnameid() {
	return projectnameid;
}


public void setProjectnameid(Integer projectnameid) {
	this.projectnameid = projectnameid;
}


public String getFileLocation() {
	return fileLocation;
}


public void setFileLocation(String fileLocation) {
	this.fileLocation = fileLocation;
}


public String getDescription() {
	return description;
}


public void setDescription(String description) {
	this.description = description;
}


public Boolean getIsactive() {
	return isactive;
}


public void setIsactive(Boolean isactive) {
	this.isactive = isactive;
}


public long getCreatedby() {
	return createdby;
}


public void setCreatedby(long createdby) {
	this.createdby = createdby;
}


public long getModifiedby() {
	return modifiedby;
}


public void setModifiedby(long modifiedby) {
	this.modifiedby = modifiedby;
}


public Date getCreateddate() {
	return createddate;
}


public void setCreateddate(Date createddate) {
	this.createddate = createddate;
}


public Date getModifieddate() {
	return modifieddate;
}


public void setModifieddate(Date modifieddate) {
	this.modifieddate = modifieddate;
}


public Integer getDocumentformatid() {
	return documentformatid;
}


public void setDocumentformatid(Integer documentformatid) {
	this.documentformatid = documentformatid;
}


public Long getSize() {
	return size;
}


public void setSize(Long size) {
	this.size = size;
}
	
	
	
	
	
	
	
	

}