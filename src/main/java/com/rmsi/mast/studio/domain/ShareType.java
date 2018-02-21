package com.rmsi.mast.studio.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity implementation class for Entity: SocialTenureRelationType
 *
 * @author Shruti.Thakur
 */

@Entity
@Table(name = "la_right_landsharetype")
public class ShareType implements Serializable {

	private static final long serialVersionUID = 1L;

    public static final String  SHARE_MULTIPLE_COMMON = "";

    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="landsharetypeid")
	private Integer landsharetypeid;
    
    @Column(name="isactive")
	private Boolean isactive;

    @Column(name="landsharetype")
	private String landsharetype;

	@Column(name="landsharetype_en")
	private String landsharetypeEn;

    public ShareType() {
        super();
    }

	public Integer getLandsharetypeid() {
		return landsharetypeid;
	}

	public void setLandsharetypeid(Integer landsharetypeid) {
		this.landsharetypeid = landsharetypeid;
	}

	public Boolean getIsactive() {
		return isactive;
	}

	public void setIsactive(Boolean isactive) {
		this.isactive = isactive;
	}

	public String getLandsharetype() {
		return landsharetype;
	}

	public void setLandsharetype(String landsharetype) {
		this.landsharetype = landsharetype;
	}

	public String getLandsharetypeEn() {
		return landsharetypeEn;
	}

	public void setLandsharetypeEn(String landsharetypeEn) {
		this.landsharetypeEn = landsharetypeEn;
	}

}
