

package com.rmsi.mast.studio.domain;


import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the maptip database table.
 * 
 */
@Embeddable
public class MaptipPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private String project;
	private String layer;

    public MaptipPK() {
    }
	
	public String getProject() {
		return this.project;
	}
	public void setProject(String project) {
		this.project = project;
	}
	public String getLayer() {
		return this.layer;
	}
	public void setLayer(String layer) {
		this.layer = layer;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof MaptipPK)) {
			return false;
		}
		MaptipPK castOther = (MaptipPK)other;
		return 
			this.project.equals(castOther.project)
			&& this.layer.equals(castOther.layer);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.project.hashCode();
		hash = hash * prime + this.layer.hashCode();
		
		return hash;
    }
}