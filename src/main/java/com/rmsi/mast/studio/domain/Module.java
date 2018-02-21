

package com.rmsi.mast.studio.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * The persistent class for the module database table.
 * 
 */
@Entity
@Table(name = "la_ext_module")
public class Module implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	private Integer moduleid;

	private String description;

	private Boolean isactive;

	private String modulename;

	@Column(name="modulename_en")
	private String modulenameEn;

	//bi-directional many-to-one association to LaExtRolemodulemapping
	@OneToMany(mappedBy="module", fetch=FetchType.EAGER)
	private List<RoleModule> roelModule;

	

	public Module() {
	}

	public Integer getModuleid() {
		return this.moduleid;
	}

	public void setModuleid(Integer moduleid) {
		this.moduleid = moduleid;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getIsactive() {
		return this.isactive;
	}

	public void setIsactive(Boolean isactive) {
		this.isactive = isactive;
	}

	public String getModulename() {
		return this.modulename;
	}

	public void setModulename(String modulename) {
		this.modulename = modulename;
	}

	public String getModulenameEn() {
		return this.modulenameEn;
	}

	public void setModulenameEn(String modulenameEn) {
		this.modulenameEn = modulenameEn;
	}

	
	
	
}