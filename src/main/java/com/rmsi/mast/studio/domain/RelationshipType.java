package com.rmsi.mast.studio.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="la_partygroup_relationshiptype")
public class RelationshipType implements Serializable {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    
    
    @Id
	private Long relationshiptypeid;

	private Boolean isactive;

	private String relationshiptype;

	@Column(name="relationshiptype_en")
	private String relationshiptypeEn;

	
	  public RelationshipType(){
        
    }

	public Long getRelationshiptypeid() {
		return relationshiptypeid;
	}

	public void setRelationshiptypeid(Long relationshiptypeid) {
		this.relationshiptypeid = relationshiptypeid;
	}

	public Boolean getIsactive() {
		return isactive;
	}

	public void setIsactive(Boolean isactive) {
		this.isactive = isactive;
	}

	public String getRelationshiptype() {
		return relationshiptype;
	}

	public void setRelationshiptype(String relationshiptype) {
		this.relationshiptype = relationshiptype;
	}

	public String getRelationshiptypeEn() {
		return relationshiptypeEn;
	}

	public void setRelationshiptypeEn(String relationshiptypeEn) {
		this.relationshiptypeEn = relationshiptypeEn;
	}


}
