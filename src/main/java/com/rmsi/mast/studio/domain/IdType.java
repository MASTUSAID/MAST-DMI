package com.rmsi.mast.studio.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "la_partygroup_identitytype")
public class IdType implements Serializable {

/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="identitytypeid")
    Integer identitytypeid;
    
    @Column(name="identitytype")
    private String identitytype;
    
    @Column(name="identitytype_en")
	private String identitytypeEn;
    
    @Column(name="isactive")
    private Boolean isactive;

    public IdType() {

    }

	public Integer getIdentitytypeid() {
		return identitytypeid;
	}

	public void setIdentitytypeid(Integer identitytypeid) {
		this.identitytypeid = identitytypeid;
	}

	public String getIdentitytype() {
		return identitytype;
	}

	public void setIdentitytype(String identitytype) {
		this.identitytype = identitytype;
	}

	public String getIdentitytypeEn() {
		return identitytypeEn;
	}

	public void setIdentitytypeEn(String identitytypeEn) {
		this.identitytypeEn = identitytypeEn;
	}

	public Boolean getIsactive() {
		return isactive;
	}

	public void setIsactive(Boolean isactive) {
		this.isactive = isactive;
	}
    

}
