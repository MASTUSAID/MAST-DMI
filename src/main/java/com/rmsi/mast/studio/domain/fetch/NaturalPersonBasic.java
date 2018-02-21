package com.rmsi.mast.studio.domain.fetch;

import com.rmsi.mast.studio.domain.EducationLevel;
import com.rmsi.mast.studio.domain.IdType;
import com.rmsi.mast.studio.domain.LaParty;
import com.rmsi.mast.studio.domain.LaPartygroupOccupation;
import com.rmsi.mast.studio.domain.LaSpatialunitgroup;
import com.rmsi.mast.studio.domain.MaritalStatus;
import com.rmsi.mast.studio.domain.Person;
import com.rmsi.mast.studio.domain.ProjectRegion;
import com.rmsi.mast.studio.domain.RelationshipType;
import com.rmsi.mast.studio.domain.TenureClass;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "la_party_person")
//@PrimaryKeyJoinColumn(name = "GID", referencedColumnName = "PERSON_GID")
public class NaturalPersonBasic implements Serializable {

/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

//    private static final long serialVersionUID = 1L;
    
    @Id
	private Long personid;

    @Column(name="createdby")
	private Integer createdby;

    @Temporal(TemporalType.DATE)
	private Date createddate;
    
	@Temporal(TemporalType.DATE)
	private Date dateofbirth;

	@Column(name="fathername")
	private String fathername;

	@Column(name="firstname")
	private String firstname;

	@Column(name="genderid")
	private Integer genderid;
	
	@Column(name="contactno")
	private String contactno;

//	@Column(name="hierarchyid6")
//	private Integer hierarchyid6;

	@Column(name="husbandname")
	private String husbandname;

	@Column(name="identityno")
	private String identityno;

	@Column(name="isactive")
	private Boolean isactive;

	@Column(name="lastname")
	private String lastname;

	@Column(name="middlename")
	private String middlename;

	@Column(name="modifiedby")
	private Integer modifiedby;

	@Temporal(TemporalType.DATE)
	private Date modifieddate;

	@Column(name="mothername")
	private String mothername;
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	private String address;

		//bi-directional one-to-one association to LaParty
//	@OneToOne
//	@JoinColumn(name="personid")
//	private LaParty laParty;
//
//	//bi-directional many-to-one association to LaPartygroupEducationlevel
//	@ManyToOne
//	@JoinColumn(name="educationlevelid")
//	private EducationLevel laPartygroupEducationlevel;
//
//	//bi-directional many-to-one association to LaPartygroupIdentitytype
//	@ManyToOne
//	@JoinColumn(name="identitytypeid")
//	private IdType laPartygroupIdentitytype;
//
//	//bi-directional many-to-one association to LaPartygroupMaritalstatus
//	@ManyToOne
//	@JoinColumn(name="maritalstatusid")
//	private MaritalStatus laPartygroupMaritalstatus;
//
//	//bi-directional many-to-one association to LaPartygroupOccupation
//	@ManyToOne
//	@JoinColumn(name="occupationid")
//	private LaPartygroupOccupation laPartygroupOccupation;
//
//	//bi-directional many-to-one association to LaPartygroupRelationshiptype
	@ManyToOne
	@JoinColumn(name="relationshiptypeid")
	private RelationshipType laPartygroupRelationshiptype;

	//bi-directional many-to-one association to LaRightTenureclass
//	@ManyToOne
//	@JoinColumn(name="tenureclassid")
//	private TenureClass laRightTenureclass;
	 @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
   @JoinColumn(name = "parentuid")
   private List<NaturalPersonAttributeValue> attributes;

	public LaSpatialunitgroup getLaSpatialunitgroup1() {
		return laSpatialunitgroup1;
	}

	public void setLaSpatialunitgroup1(LaSpatialunitgroup laSpatialunitgroup1) {
		this.laSpatialunitgroup1 = laSpatialunitgroup1;
	}

	//bi-directional many-to-one association to LaSpatialunitgroup
	@ManyToOne
	@JoinColumn(name="spatialunitgroupid1")
	private LaSpatialunitgroup laSpatialunitgroup1;

	//bi-directional many-to-one association to LaSpatialunitgroup
	@ManyToOne
	@JoinColumn(name="spatialunitgroupid2")
	private LaSpatialunitgroup laSpatialunitgroup2;

	//bi-directional many-to-one association to LaSpatialunitgroup
	@ManyToOne
	@JoinColumn(name="spatialunitgroupid3")
	private LaSpatialunitgroup laSpatialunitgroup3;

	//bi-directional many-to-one association to LaSpatialunitgroup
	@ManyToOne
	@JoinColumn(name="spatialunitgroupid4")
	private LaSpatialunitgroup laSpatialunitgroup4;

	//bi-directional many-to-one association to LaSpatialunitgroup
	@ManyToOne
	@JoinColumn(name="spatialunitgroupid5")
	private LaSpatialunitgroup laSpatialunitgroup5;

	//bi-directional many-to-one association to LaSpatialunitgroup
	@ManyToOne
	@JoinColumn(name="spatialunitgroupid6")
	private LaSpatialunitgroup laSpatialunitgroup6;

	//bi-directional many-to-one association to LaSpatialunitgroupHierarchy
	@ManyToOne
	@JoinColumn(name="hierarchyid1")
	private ProjectRegion laSpatialunitgroupHierarchy1;
	
	public NaturalPersonBasic(){
		
	}
	

	public String getContactno() {
		return contactno;
	}

	public void setContactno(String contactno) {
		this.contactno = contactno;
	}

//	public LaPartygroupOccupation getLaPartygroupOccupation() {
//		return laPartygroupOccupation;
//	}
//
//	public void setLaPartygroupOccupation(
//			LaPartygroupOccupation laPartygroupOccupation) {
//		this.laPartygroupOccupation = laPartygroupOccupation;
//	}

	public LaSpatialunitgroup getLaSpatialunitgroup2() {
		return laSpatialunitgroup2;
	}

	public void setLaSpatialunitgroup2(LaSpatialunitgroup laSpatialunitgroup2) {
		this.laSpatialunitgroup2 = laSpatialunitgroup2;
	}

	public LaSpatialunitgroup getLaSpatialunitgroup3() {
		return laSpatialunitgroup3;
	}

	public void setLaSpatialunitgroup3(LaSpatialunitgroup laSpatialunitgroup3) {
		this.laSpatialunitgroup3 = laSpatialunitgroup3;
	}

	public LaSpatialunitgroup getLaSpatialunitgroup4() {
		return laSpatialunitgroup4;
	}

	public void setLaSpatialunitgroup4(LaSpatialunitgroup laSpatialunitgroup4) {
		this.laSpatialunitgroup4 = laSpatialunitgroup4;
	}

	public LaSpatialunitgroup getLaSpatialunitgroup5() {
		return laSpatialunitgroup5;
	}

	public void setLaSpatialunitgroup5(LaSpatialunitgroup laSpatialunitgroup5) {
		this.laSpatialunitgroup5 = laSpatialunitgroup5;
	}

	public LaSpatialunitgroup getLaSpatialunitgroup6() {
		return laSpatialunitgroup6;
	}

	public void setLaSpatialunitgroup6(LaSpatialunitgroup laSpatialunitgroup6) {
		this.laSpatialunitgroup6 = laSpatialunitgroup6;
	}

	//bi-directional many-to-one association to LaSpatialunitgroupHierarchy
	@ManyToOne
	@JoinColumn(name="hierarchyid2")
	private ProjectRegion laSpatialunitgroupHierarchy2;

	//bi-directional many-to-one association to LaSpatialunitgroupHierarchy
	@ManyToOne
	@JoinColumn(name="hierarchyid3")
	private ProjectRegion laSpatialunitgroupHierarchy3;

	//bi-directional many-to-one association to LaSpatialunitgroupHierarchy
	@ManyToOne
	@JoinColumn(name="hierarchyid4")
	private ProjectRegion laSpatialunitgroupHierarchy4;

	//bi-directional many-to-one association to LaSpatialunitgroupHierarchy
	@ManyToOne
	@JoinColumn(name="hierarchyid5")
	private ProjectRegion laSpatialunitgroupHierarchy5;

	//bi-directional many-to-one association to LaSpatialunitgroupHierarchy
	@ManyToOne
	@JoinColumn(name="hierarchyid6")
	private ProjectRegion laSpatialunitgroupHierarchy6;

	public Long getPersonid() {
		return personid;
	}

	public void setPersonid(Long personid) {
		this.personid = personid;
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

	public Date getDateofbirth() {
		return dateofbirth;
	}

	public void setDateofbirth(Date dateofbirth) {
		this.dateofbirth = dateofbirth;
	}

	public String getFathername() {
		return fathername;
	}

	public void setFathername(String fathername) {
		this.fathername = fathername;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public Integer getGenderid() {
		return genderid;
	}

	public void setGenderid(Integer genderid) {
		this.genderid = genderid;
	}

//	public Integer getHierarchyid6() {
//		return hierarchyid6;
//	}
//
//	public void setHierarchyid6(Integer hierarchyid6) {
//		this.hierarchyid6 = hierarchyid6;
//	}

	public String getHusbandname() {
		return husbandname;
	}

	public void setHusbandname(String husbandname) {
		this.husbandname = husbandname;
	}

	public String getIdentityno() {
		return identityno;
	}

	public void setIdentityno(String identityno) {
		this.identityno = identityno;
	}

	public Boolean getIsactive() {
		return isactive;
	}

	public void setIsactive(Boolean isactive) {
		this.isactive = isactive;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getMiddlename() {
		return middlename;
	}

	public void setMiddlename(String middlename) {
		this.middlename = middlename;
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

	public String getMothername() {
		return mothername;
	}

	public void setMothername(String mothername) {
		this.mothername = mothername;
	}

//	public Integer getSpatialunitgroupid6() {
//		return spatialunitgroupid6;
//	}
//
//	public void setSpatialunitgroupid6(Integer spatialunitgroupid6) {
//		this.spatialunitgroupid6 = spatialunitgroupid6;
//	}

	
	
	
//	public LaParty getLaParty() {
//		return laParty;
//	}

	public List<NaturalPersonAttributeValue> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<NaturalPersonAttributeValue> attributes) {
		this.attributes = attributes;
	}

//	public void setLaParty(LaParty laParty) {
//		this.laParty = laParty;
//	}
////
//	public EducationLevel getLaPartygroupEducationlevel() {
//		return laPartygroupEducationlevel;
//	}
//
//	public void setLaPartygroupEducationlevel(
//			EducationLevel laPartygroupEducationlevel) {
//		this.laPartygroupEducationlevel = laPartygroupEducationlevel;
//	}
//
//	public IdType getLaPartygroupIdentitytype() {
//		return laPartygroupIdentitytype;
//	}
//
//	public void setLaPartygroupIdentitytype(IdType laPartygroupIdentitytype) {
//		this.laPartygroupIdentitytype = laPartygroupIdentitytype;
//	}
//
//	public MaritalStatus getLaPartygroupMaritalstatus() {
//		return laPartygroupMaritalstatus;
//	}
//
//	public void setLaPartygroupMaritalstatus(MaritalStatus laPartygroupMaritalstatus) {
//		this.laPartygroupMaritalstatus = laPartygroupMaritalstatus;
//	}
//
	public RelationshipType getLaPartygroupRelationshiptype() {
		return laPartygroupRelationshiptype;
	}

	public void setLaPartygroupRelationshiptype(
			RelationshipType laPartygroupRelationshiptype) {
		this.laPartygroupRelationshiptype = laPartygroupRelationshiptype;
	}
//
//	public TenureClass getLaRightTenureclass() {
//		return laRightTenureclass;
//	}
//
//	public void setLaRightTenureclass(TenureClass laRightTenureclass) {
//		this.laRightTenureclass = laRightTenureclass;
//	}

	public ProjectRegion getLaSpatialunitgroupHierarchy1() {
		return laSpatialunitgroupHierarchy1;
	}

	public void setLaSpatialunitgroupHierarchy1(
			ProjectRegion laSpatialunitgroupHierarchy1) {
		this.laSpatialunitgroupHierarchy1 = laSpatialunitgroupHierarchy1;
	}

	public ProjectRegion getLaSpatialunitgroupHierarchy2() {
		return laSpatialunitgroupHierarchy2;
	}

	public void setLaSpatialunitgroupHierarchy2(
			ProjectRegion laSpatialunitgroupHierarchy2) {
		this.laSpatialunitgroupHierarchy2 = laSpatialunitgroupHierarchy2;
	}

	public ProjectRegion getLaSpatialunitgroupHierarchy3() {
		return laSpatialunitgroupHierarchy3;
	}

	public void setLaSpatialunitgroupHierarchy3(
			ProjectRegion laSpatialunitgroupHierarchy3) {
		this.laSpatialunitgroupHierarchy3 = laSpatialunitgroupHierarchy3;
	}

	public ProjectRegion getLaSpatialunitgroupHierarchy4() {
		return laSpatialunitgroupHierarchy4;
	}

	public void setLaSpatialunitgroupHierarchy4(
			ProjectRegion laSpatialunitgroupHierarchy4) {
		this.laSpatialunitgroupHierarchy4 = laSpatialunitgroupHierarchy4;
	}

	public ProjectRegion getLaSpatialunitgroupHierarchy5() {
		return laSpatialunitgroupHierarchy5;
	}

	public void setLaSpatialunitgroupHierarchy5(
			ProjectRegion laSpatialunitgroupHierarchy5) {
		this.laSpatialunitgroupHierarchy5 = laSpatialunitgroupHierarchy5;
	}

	public ProjectRegion getLaSpatialunitgroupHierarchy6() {
		return laSpatialunitgroupHierarchy6;
	}

	public void setLaSpatialunitgroupHierarchy6(
			ProjectRegion laSpatialunitgroupHierarchy6) {
		this.laSpatialunitgroupHierarchy6 = laSpatialunitgroupHierarchy6;
	}
    
    
    
    
    
    
    
    
    

//    @Column(name = "first_name")
//    private String firstName;
//    
//    @Column(name = "last_name")
//    private String lastName;
//    
//    @Column(name = "middle_name")
//    private String middleName;
//
//    @Column
//    private Integer gender;
//
//    @Column
//    private String mobile;
//    
//    @Column
//    private String identity;
//    
//    @Column
//    private Integer age;
//    
//    @Column
//    private String occupation;
//    
//    @Column(name = "tenure_relation")
//    private String tenureRelation;
//    
//    @Column(name = "marital_status")
//    private Integer maritalStatus;
//    
//    @Column
//    private Boolean active;
//    
//    @Column
//    private Integer education;
//
//    @Column(name = "resident_of_village")
//    private Boolean residentOfVillage;
//
//    @Column(name = "personsub_type")
//    private Integer personType;
//    
//    @Column(name = "citizenship_id")
//    private Long citizenshipId;
//    
//    @Column(name = "id_number")
//    private String idNumber;
//    
//    @Column
//    @Temporal(javax.persistence.TemporalType.DATE)
//    private Date dob;
//
//    @Column(name = "id_type")
//    private Integer idType;
//
//    @Column
//    private String share;
//
//    @Column(name = "acquisition_type")
//    private Integer acquisitionType;
//
//    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @JoinColumn(name = "parent_id")
//    private List<NaturalPersonAttributeValue> attributes;
//    
//    public String getFirstName() {
//        return firstName;
//    }
//
//    public void setFirstName(String firstName) {
//        this.firstName = firstName;
//    }
//
//    public String getLastName() {
//        return lastName;
//    }
//
//    public void setLastName(String lastName) {
//        this.lastName = lastName;
//    }
//
//    public String getMiddleName() {
//        return middleName;
//    }
//
//    public void setMiddleName(String middleName) {
//        this.middleName = middleName;
//    }
//
//    public Integer getGender() {
//        return gender;
//    }
//
//    public void setGender(Integer gender) {
//        this.gender = gender;
//    }
//
//    public String getMobile() {
//        return mobile;
//    }
//
//    public void setMobile(String mobile) {
//        this.mobile = mobile;
//    }
//
//    public String getIdentity() {
//        return identity;
//    }
//
//    public void setIdentity(String identity) {
//        this.identity = identity;
//    }
//
//    public Integer getAge() {
//        return age;
//    }
//
//    public void setAge(Integer age) {
//        this.age = age;
//    }
//
//    public String getOccupation() {
//        return occupation;
//    }
//
//    public void setOccupation(String occupation) {
//        this.occupation = occupation;
//    }
//
//    public String getTenureRelation() {
//        return tenureRelation;
//    }
//
//    public void setTenureRelation(String tenureRelation) {
//        this.tenureRelation = tenureRelation;
//    }
//
//    public Integer getMaritalStatus() {
//        return maritalStatus;
//    }
//
//    public void setMaritalStatus(Integer maritalStatus) {
//        this.maritalStatus = maritalStatus;
//    }
//
//    public Boolean getActive() {
//        return active;
//    }
//
//    public void setActive(Boolean active) {
//        this.active = active;
//    }
//
//    public Integer getEducation() {
//        return education;
//    }
//
//    public void setEducation(Integer education) {
//        this.education = education;
//    }
//
//    public Boolean getResidentOfVillage() {
//        return residentOfVillage;
//    }
//
//    public void setResidentOfVillage(Boolean residentOfVillage) {
//        this.residentOfVillage = residentOfVillage;
//    }
//
//    public Integer getPersonType() {
//        return personType;
//    }
//
//    public void setPersonType(Integer personType) {
//        this.personType = personType;
//    }
//
//    public Long getCitizenshipId() {
//        return citizenshipId;
//    }
//
//    public void setCitizenshipId(Long citizenshipId) {
//        this.citizenshipId = citizenshipId;
//    }
//
//    public String getIdNumber() {
//        return idNumber;
//    }
//
//    public void setIdNumber(String idNumber) {
//        this.idNumber = idNumber;
//    }
//
//    public Date getDob() {
//        return dob;
//    }
//
//    public void setDob(Date dob) {
//        this.dob = dob;
//    }
//
//    public Integer getIdType() {
//        return idType;
//    }
//
//    public void setIdType(Integer idType) {
//        this.idType = idType;
//    }
//
//    public String getShare() {
//        return share;
//    }
//
//    public void setShare(String share) {
//        this.share = share;
//    }
//
//    public Integer getAcquisitionType() {
//        return acquisitionType;
//    }
//
//    public void setAcquisitionType(Integer acquisitionType) {
//        this.acquisitionType = acquisitionType;
//    }
//
//    public List<NaturalPersonAttributeValue> getAttributes() {
//        return attributes;
//    }
//
//    public void setAttributes(List<NaturalPersonAttributeValue> attributes) {
//        this.attributes = attributes;
//    }
}
