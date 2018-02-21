package com.rmsi.mast.studio.domain;

import java.io.Serializable;
import java.lang.String;
import java.util.List;

import javax.persistence.*;


/**
 * Entity implementation class for Entity: PersonType
 *
 * @author Shruti.Thakur
 */
@Entity
@Table(name = "la_partygroup_persontype")
public class PersonType implements Serializable {

/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//    private static final long serialVersionUID = 1L;
    public static int TYPE_NATURAL = 1;
    public static int TYPE_NON_NATURAL = 2;
    public static int TYPE_OWNER = 3;
    public static int TYPE_ADMINISTRATOR = 4;
    public static int TYPE_GUARDIAN = 5;
    
    @Id
    @SequenceGenerator(name="pk_la_persontype",sequenceName="la_partygroup_persontype_persontypeid_seq", allocationSize=1) 
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_la_persontype") 
	@Column(name="persontypeid")
	private Integer persontypeid;

	@Column(name="isactive")
	private Boolean isactive;

	@Column(name="persontype")
	private String persontype;

	@Column(name="persontype_en")
	private String persontypeEn;

	//bi-directional many-to-one association to LaExtDisputelandmapping
//	@OneToMany(mappedBy="laPartygroupPersontype")
//	private List<LaExtDisputelandmapping> laExtDisputelandmappings;

	//bi-directional many-to-one association to LaExtPersonlandmapping
	/*@OneToMany(mappedBy="laPartygroupPersontype")
	private List<SocialTenureRelationship> laExtPersonlandmappings;
*/
	//bi-directional many-to-one association to LaParty
//	@OneToMany(mappedBy="laPartygroupPersontype")
//	private List<LaParty> laParties;

	public Integer getPersontypeid() {
		return persontypeid;
	}

	public void setPersontypeid(Integer persontypeid) {
		this.persontypeid = persontypeid;
	}

	public Boolean getIsactive() {
		return isactive;
	}

	public void setIsactive(Boolean isactive) {
		this.isactive = isactive;
	}

	public String getPersontype() {
		return persontype;
	}

	public void setPersontype(String persontype) {
		this.persontype = persontype;
	}

	public String getPersontypeEn() {
		return persontypeEn;
	}

	public void setPersontypeEn(String persontypeEn) {
		this.persontypeEn = persontypeEn;
	}

//	public List<LaExtDisputelandmapping> getLaExtDisputelandmappings() {
//		return laExtDisputelandmappings;
//	}
//
//	public void setLaExtDisputelandmappings(
//			List<LaExtDisputelandmapping> laExtDisputelandmappings) {
//		this.laExtDisputelandmappings = laExtDisputelandmappings;
//	}

	/*public List<SocialTenureRelationship> getLaExtPersonlandmappings() {
		return laExtPersonlandmappings;
	}

	public void setLaExtPersonlandmappings(
			List<SocialTenureRelationship> laExtPersonlandmappings) {
		this.laExtPersonlandmappings = laExtPersonlandmappings;
	}*/

//	public List<LaParty> getLaParties() {
//		return laParties;
//	}
//
//	public void setLaParties(List<LaParty> laParties) {
//		this.laParties = laParties;
//	}
	
	

//    @Id
//    @SequenceGenerator(name = "PERSON_TYPE_ID_GENERATOR", sequenceName = "PERSON_TYPE_GID_SEQ", allocationSize = 1)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PERSON_TYPE_ID_GENERATOR")
//    private long person_type_gid;
//
//    @Column(name = "person_type")
//    private String PersonType;
//
//    @Column(name = "person_type_sw")
//    private String PersonType_sw;
//
//    public PersonType() {
//        super();
//    }
//
//    public long getPerson_type_gid() {
//        return this.person_type_gid;
//    }
//
//    public void setPerson_type_gid(long person_type_gid) {
//        this.person_type_gid = person_type_gid;
//    }
//
//    public String getPersonType() {
//        return this.PersonType;
//    }
//
//    public void setPersonType(String PersonType) {
//        this.PersonType = PersonType;
//    }
//
//    public String getPersonType_sw() {
//        return PersonType_sw;
//    }
//
//    public void setPersonType_sw(String personType_sw) {
//        PersonType_sw = personType_sw;
//    }

}
