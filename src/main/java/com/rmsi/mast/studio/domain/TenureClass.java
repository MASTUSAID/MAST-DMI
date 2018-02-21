package com.rmsi.mast.studio.domain;

import java.io.Serializable;
import java.lang.String;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: TenureClass
 *
 * @author Shruti.Thakur
 */
@Entity
@Table(name = "la_right_tenureclass")
public class TenureClass implements Serializable {

/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

//    private static final long serialVersionUID = 1L;
    
    
    
    @Id
	private Integer tenureclassid;

	private Boolean isactive;

	private String tenureclass;

	@Column(name="tenureclass_en")
	private String tenureclassEn;

	//bi-directional many-to-one association to LaPartyPerson
	/*@OneToMany(mappedBy="laRightTenureclass",fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	private List<NaturalPerson> laPartyPersons;*/

	//bi-directional many-to-one association to LaSpatialunitLand
	/*@OneToMany(mappedBy="laRightTenureclass",fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	private List<SpatialUnit> laSpatialunitLands;
	*/
	public TenureClass() {
      super();
  }

	public Integer getTenureclassid() {
		return tenureclassid;
	}

	public void setTenureclassid(Integer tenureclassid) {
		this.tenureclassid = tenureclassid;
	}

	public Boolean getIsactive() {
		return isactive;
	}

	public void setIsactive(Boolean isactive) {
		this.isactive = isactive;
	}

	public String getTenureclass() {
		return tenureclass;
	}

	public void setTenureclass(String tenureclass) {
		this.tenureclass = tenureclass;
	}

	public String getTenureclassEn() {
		return tenureclassEn;
	}

	public void setTenureclassEn(String tenureclassEn) {
		this.tenureclassEn = tenureclassEn;
	}

	/*public List<NaturalPerson> getLaPartyPersons() {
		return laPartyPersons;
	}

	public void setLaPartyPersons(List<NaturalPerson> laPartyPersons) {
		this.laPartyPersons = laPartyPersons;
	}
*/
	
}
