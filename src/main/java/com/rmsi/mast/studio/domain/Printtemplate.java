

package com.rmsi.mast.studio.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the printtemplate database table.
 * 
 */
@Entity
@org.hibernate.annotations.Cache(usage = org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE)
public class Printtemplate implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	//private Integer id;
	private String templatefile;
	private String tenantid;
	private Project projectBean;

    public Printtemplate() {
    }


	@Id
	//@GeneratedValue(strategy=GenerationType.IDENTITY)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}


	/*public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}*/


	public String getTemplatefile() {
		return this.templatefile;
	}

	public void setTemplatefile(String templatefile) {
		this.templatefile = templatefile;
	}


	public String getTenantid() {
		return this.tenantid;
	}

	public void setTenantid(String tenantid) {
		this.tenantid = tenantid;
	}


	//bi-directional many-to-one association to Project
    @JsonIgnore
	@ManyToOne
	@JoinColumn(name="project")
	public Project getProjectBean() {
		return this.projectBean;
	}

	public void setProjectBean(Project projectBean) {
		this.projectBean = projectBean;
	}
	
}