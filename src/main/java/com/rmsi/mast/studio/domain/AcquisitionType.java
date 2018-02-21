package com.rmsi.mast.studio.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "la_right_acquisitiontype")
public class AcquisitionType implements Serializable {

/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

//    private static final long serialVersionUID = 1L;
    
    
    @Id
	private Integer acquisitiontypeid;

	private String acquisitiontype;

	@Column(name="acquisitiontype_en")
	private String acquisitiontypeEn;

	private Boolean isactive;

	//bi-directional many-to-one association to LaSpatialunitLand
//	@OneToMany(mappedBy="laRightAcquisitiontype",fetch=FetchType.EAGER,cascade=CascadeType.ALL)
//	private List<SpatialUnit> laSpatialunitLands;
	
	public AcquisitionType() {
		
		    }

	public Integer getAcquisitiontypeid() {
		return acquisitiontypeid;
	}

	public void setAcquisitiontypeid(Integer acquisitiontypeid) {
		this.acquisitiontypeid = acquisitiontypeid;
	}

	public String getAcquisitiontype() {
		return acquisitiontype;
	}

	public void setAcquisitiontype(String acquisitiontype) {
		this.acquisitiontype = acquisitiontype;
	}

	public String getAcquisitiontypeEn() {
		return acquisitiontypeEn;
	}

	public void setAcquisitiontypeEn(String acquisitiontypeEn) {
		this.acquisitiontypeEn = acquisitiontypeEn;
	}

	public Boolean getIsactive() {
		return isactive;
	}

	public void setIsactive(Boolean isactive) {
		this.isactive = isactive;
	}

//	public List<SpatialUnit> getLaSpatialunitLands() {
//		return laSpatialunitLands;
//	}
//
//	public void setLaSpatialunitLands(List<SpatialUnit> laSpatialunitLands) {
//		this.laSpatialunitLands = laSpatialunitLands;
//	}
	
	
	
	
	

//    @Id
//    private int code;
//    @Column
//    private String name;
//    @Column(name = "name_other_lang")
//    private String nameOtherLang;
//    @Column
//    boolean active;
//
//    public AcquisitionType() {
//
//    }
//
//    public int getCode() {
//        return code;
//    }
//
//    public void setCode(int code) {
//        this.code = code;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getNameOtherLang() {
//        return nameOtherLang;
//    }
//
//    public void setNameOtherLang(String nameOtherLang) {
//        this.nameOtherLang = nameOtherLang;
//    }
//
//    public boolean isActive() {
//        return active;
//    }
//
//    public void setActive(boolean active) {
//        this.active = active;
//    }
}
