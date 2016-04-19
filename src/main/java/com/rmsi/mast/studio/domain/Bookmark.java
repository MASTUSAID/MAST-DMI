

package com.rmsi.mast.studio.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the bookmark database table.
 * 
 */
@Entity
public class Bookmark implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	private String description;
	//private Long id;
	private double maxx;
	private double maxy;
	private double minx;
	private double miny;
	private String tenantid;
	
	private Project projectBean;

    public Bookmark() {
    }


    @Id
	//@GeneratedValue(strategy=GenerationType.IDENTITY)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
	/*public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}*/


	public double getMaxx() {
		return this.maxx;
	}

	public void setMaxx(double maxx) {
		this.maxx = maxx;
	}


	public double getMaxy() {
		return this.maxy;
	}

	public void setMaxy(double maxy) {
		this.maxy = maxy;
	}


	public double getMinx() {
		return this.minx;
	}

	public void setMinx(double minx) {
		this.minx = minx;
	}


	public double getMiny() {
		return this.miny;
	}

	public void setMiny(double miny) {
		this.miny = miny;
	}


	public String getTenantid() {
		return this.tenantid;
	}

	public void setTenantid(String tenantid) {
		this.tenantid = tenantid;
	}


	//bi-directional many-to-one association to Project
//	@JsonIgnore	
	@ManyToOne	
	@JoinColumn(name="project", nullable=false)

	public Project getProjectBean() {
		return this.projectBean;
	}

	public void setProjectBean(Project projectBean) {
		this.projectBean = projectBean;
	}
	
}