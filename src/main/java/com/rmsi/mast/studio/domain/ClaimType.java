package com.rmsi.mast.studio.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.rmsi.mast.studio.domain.fetch.SpatialUnitTable;

@Entity
@Table(name = "la_right_claimtype")

public class ClaimType implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
  // private static final long serialVersionUID = 1L;
	public static final String CODE_NEW = "newClaim";
	public static final String CODE_EXISTING = "existingClaim";
	public static final String CODE_DISPUTED = "dispute";
	public static final String CODE_UNCLAIMED = "unclaimed";


	@Id
	@Column(name="claimtypeid")
	private String code;
	@Column(name="claimtype")
	private String name;

	@Column(name="claimtype_en")
	private String nameOtherLang;
	@Column(name="isactive")
	boolean active;

/*	@OneToMany(mappedBy="laRightClaimtype")
	private List<LaSpatialunitLand> laSpatialunitLands;*/
	
	
	  	//bi-directional many-to-one association to LaSpatialunitLand
	/*	@OneToMany(mappedBy="laRightClaimtype")
	     private List<SpatialUnitTable> laSpatialunitLands;*/

	


	public ClaimType() {

	}




	public String getCode() {
		return code;
	}




	public void setCode(String code) {
		this.code = code;
	}




	public String getName() {
		return name;
	}




	public void setName(String name) {
		this.name = name;
	}




	public String getNameOtherLang() {
		return nameOtherLang;
	}




	public void setNameOtherLang(String nameOtherLang) {
		this.nameOtherLang = nameOtherLang;
	}




	public boolean isActive() {
		return active;
	}




	public void setActive(boolean active) {
		this.active = active;
	}

	

	
	
	//@@ old below for reference 
	
	/*	public List<SpatialUnit> getLaSpatialunitLands() {
			return laSpatialunitLands;
		}
	
		public void setLaSpatialunitLands(List<SpatialUnit> laSpatialunitLands) {
			this.laSpatialunitLands = laSpatialunitLands;
		}
		*/




	//    @Id
	//    private String code;
	//    @Column
	//    private String name;
	//    @Column(name = "name_other_lang")
	//    private String nameOtherLang;
	//    @Column
	//    boolean active;
	//
	//    public ClaimType() {
	//
	//    }
	//
	//    public String getCode() {
	//        return code;
	//    }
	//
	//    public void setCode(String code) {
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
