package com.rmsi.mast.studio.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.rmsi.mast.studio.domain.fetch.DisputeBasic;

@Entity
@Table(name="la_ext_disputetype")
public class DisputeType implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
	private Integer disputetypeid;

	private String disputetype;

	@Column(name="disputetype_en")
	private String disputetypeEn;

	private Boolean isactive;
	
//	@OneToMany(mappedBy="laExtDisputetype",fetch=FetchType.EAGER,cascade=CascadeType.ALL)
//	private List<DisputeBasic> disputes;
    
	  public DisputeType(){
        
    }

	public Integer getDisputetypeid() {
		return disputetypeid;
	}

	public void setDisputetypeid(Integer l) {
		this.disputetypeid = l;
	}

	public String getDisputetype() {
		return disputetype;
	}

	public void setDisputetype(String disputetype) {
		this.disputetype = disputetype;
	}

	public String getDisputetypeEn() {
		return disputetypeEn;
	}

	public void setDisputetypeEn(String disputetypeEn) {
		this.disputetypeEn = disputetypeEn;
	}

	public Boolean getIsactive() {
		return isactive;
	}

	public void setIsactive(Boolean isactive) {
		this.isactive = isactive;
	}

//	public List<DisputeBasic> getDisputes() {
//		return disputes;
//	}
//
//	public void setDisputes(List<DisputeBasic> disputes) {
//		this.disputes = disputes;
//	}

    
    
    
    
    
    
    
    
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long code;
//    @Column
//    private String name;
//    @Column(name = "name_other_lang")
//    private String nameOtherLang;
//    @Column
//    boolean active;
//    
//    public DisputeType(){
//        
//    }
//
//    public Long getCode() {
//        return code;
//    }
//
//    public void setCode(Long code) {
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
