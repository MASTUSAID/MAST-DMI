

package com.rmsi.mast.studio.domain;


import java.io.Serializable;
import javax.persistence.*;



/**
 * The persistent class for the maptip database table.
 * 
 */
@Entity
public class Maptip implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private MaptipPK id;
	
	private String field;
	private String name;
	private String queryexpression;

	//bi-directional many-to-one association to Layer
    @ManyToOne(cascade = CascadeType.ALL, optional=false)
	@JoinColumn(name="layer", insertable=false, updatable=false)
	private Layer layerBean;

	//bi-directional many-to-one association to Project
    @ManyToOne(cascade = CascadeType.ALL, optional=false)
	@JoinColumn(name="project", insertable=false, updatable=false)
	private Project projectBean;

    public Maptip() {
    }

	public MaptipPK getId() {
		return this.id;
	}

	public void setId(MaptipPK id) {
		this.id = id;
	}
	
	public String getField() {
		return this.field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getQueryexpression() {
		return this.queryexpression;
	}

	public void setQueryexpression(String queryexpression) {
		this.queryexpression = queryexpression;
	}

	public Layer getLayerBean() {
		return this.layerBean;
	}

	public void setLayerBean(Layer layerBean) {
		this.layerBean = layerBean;
	}
	
	public Project getProjectBean() {
		return this.projectBean;
	}

	public void setProjectBean(Project projectBean) {
		this.projectBean = projectBean;
	}
	
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
}