package com.rmsi.mast.studio.domain.fetch;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rmsi.mast.studio.domain.LaParty;
import com.rmsi.mast.studio.domain.PersonType;
import com.rmsi.mast.studio.util.StringUtils;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

/**
 * The persistent class for the spatialunit_pesonwithinterest database table.
 *
 */
@Entity
@Table(name = "la_party_deceasedperson")
@PrimaryKeyJoinColumn(name = "PARTYID", referencedColumnName = "PARTYID")
public class SpatialunitDeceasedPerson extends LaParty implements Serializable {

    private static final long serialVersionUID = 1L;

   
    @Column(name="firstname")
    private String firstname;
    
    @Column(name="middlename")
    private String middlename;
    
    @Column(name="lastname")
    private String lastname;
    
    @ManyToOne
	@JoinColumn(name="persontypeid")
	private PersonType laPartygroupPersontype;

    private Boolean isactive;
    
    @Column(name="modifiedby")
	private Integer modifiedby;

	@Temporal(TemporalType.DATE)
	private Date modifieddate;
    
//    @ManyToOne
//	@JoinColumn(name="landid")
    @Column(name="landid")
	private Long laSpatialunitLand;
    

    @Column(name="createdby")
	private Integer createdby;

	@Temporal(TemporalType.DATE)
	private Date createddate;
	
	

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    


	public Long getLaSpatialunitLand() {
		return laSpatialunitLand;
	}

	public void setLaSpatialunitLand(Long laSpatialunitLand) {
		this.laSpatialunitLand = laSpatialunitLand;
	}

	public String getFullName() {
        String name = "";
        if (!StringUtils.isEmpty(getFirstname())) {
            name = getFirstname().trim();
        }
        if (!StringUtils.isEmpty(getMiddlename())) {
            if (name.length() > 0) {
                name = name + " " + getMiddlename().trim();
            } else {
                name = getMiddlename().trim();
            }
        }
        if (!StringUtils.isEmpty(getLastname())) {
            if (name.length() > 0) {
                name = name + " " + getLastname().trim();
            } else {
                name = getLastname().trim();
            }
        }
        return name;
    }

	public PersonType getLaPartygroupPersontype() {
		return laPartygroupPersontype;
	}

	public void setLaPartygroupPersontype(PersonType laPartygroupPersontype) {
		this.laPartygroupPersontype = laPartygroupPersontype;
	}

	public Integer getModifiedby() {
		return modifiedby;
	}

	public void setModifiedby(Integer modifiedby) {
		this.modifiedby = modifiedby;
	}

	public Date getModifieddate() {
		return modifieddate;
	}

	public void setModifieddate(Date modifieddate) {
		this.modifieddate = modifieddate;
	}

	public Integer getCreatedby() {
		return createdby;
	}

	public void setCreatedby(Integer createdby) {
		this.createdby = createdby;
	}

	public Date getCreateddate() {
		return createddate;
	}

	public void setCreateddate(Date createddate) {
		this.createddate = createddate;
	}

	public Boolean getIsactive() {
		return isactive;
	}

	public void setIsactive(Boolean isactive) {
		this.isactive = isactive;
	}
	
	
	
	
	
	
	
	
	
	
	
}
