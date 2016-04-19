

package com.rmsi.mast.studio.domain;

import java.io.Serializable;

import javax.persistence.Embeddable;


/**
 * The persistent class for the attachment database table.
 * 
 */
@Embeddable
public class AttachmentPK implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer associationid;	
	private String layername;

    public AttachmentPK() {
    }

	public Integer getAssociationid() {
		return this.associationid;
	}

	public void setAssociationid(Integer associationid) {
		this.associationid = associationid;
	}

	public String getLayername() {
		return layername;
	}

	public void setLayername(String layername) {
		this.layername = layername;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof AttachmentPK)) {
			return false;
		}
		AttachmentPK castOther = (AttachmentPK)other;
		return 
			this.associationid == castOther.associationid
			&& this.layername.equals(castOther.layername);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.associationid.hashCode();
		hash = hash * prime + this.layername.hashCode();
		
		return hash;
    }
}