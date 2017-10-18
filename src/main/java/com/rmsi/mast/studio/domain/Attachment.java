

package com.rmsi.mast.studio.domain;

import java.io.Serializable;
import java.util.Comparator;

import javax.persistence.*;


/**
 * The persistent class for the attachment database table.
 * 
 */
@Entity
public class Attachment implements Serializable,Comparator<Attachment> {
	private static final long serialVersionUID = 1L;
	
	//@EmbeddedId
	//private AttachmentPK attachmentId;
	
	private Integer associationid;
	private String description;
	private String extension;
	private String filepath;
	//private Integer id;
	private String keyfield;
	private String tenantid;
	private String filename;
	private Layer layer;
	private Integer  gid;
	
	public Attachment() {
    }
	
	public Integer getGid() {
		return gid;
	}

	public void setGid(Integer gid) {
		this.gid = gid;
	}

	//bi-directional many-to-one association to Layer
	//@JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL, optional=false)
	@JoinColumn(name="layername")
	public Layer getLayer() {
		return this.layer;
	}

	public void setLayer(Layer layer) {
		this.layer = layer;
	}
	//@JsonBackReference
	


	@Id 	
	@SequenceGenerator(name="ATTACHMENT_ASSOCIATIONID_GENERATOR", sequenceName="attachment_associationid_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ATTACHMENT_ASSOCIATIONID_GENERATOR")
	@Column(name="associationid", unique=true, nullable=false)
	
	public Integer getAssociationid() {
		return this.associationid;
	}

	public void setAssociationid(Integer associationid) {
		this.associationid = associationid;
	}
    
    /*public AttachmentPK getAttachmentId() {
		return attachmentId;
	}


	public void setAttachmentId(AttachmentPK attachmentId) {
		this.attachmentId = attachmentId;
	}*/


	public String getDescription() {
		return this.description;
	}

	
	public void setDescription(String description) {
		this.description = description;
	}


	public String getExtension() {
		return this.extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}


	public String getFilepath() {
		return this.filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public String getKeyfield() {
		return this.keyfield;
	}

	public void setKeyfield(String keyfield) {
		this.keyfield = keyfield;
	}


	public String getTenantid() {
		return this.tenantid;
	}

	public void setTenantid(String tenantid) {
		this.tenantid = tenantid;
	}


	public String getFilename() {
		return this.filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	public int compare(Attachment a1, Attachment a2){
		if(a1.associationid > a2.associationid){
			return 1;
		}else if(a1.associationid < a2.associationid){
			return -1;
		}else{
			return 0;
		}
	}


	
	


	
	
}