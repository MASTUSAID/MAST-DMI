package com.rmsi.mast.studio.domain.fetch;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "person")
public class PersonBasic implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "person_gid")
    private long personGid;

    @Column(name = "person_type_gid")
    private Integer personTypeId;

    @Column
    private boolean resident;

    @Column(name = "mobile_group_id")
    private String mobileGroupId;

    public PersonBasic() {
        super();
    }

    public long getPersonGid() {
        return personGid;
    }

    public void setPersonGid(long personGid) {
        this.personGid = personGid;
    }

    public Integer getPersonTypeId() {
        return personTypeId;
    }

    public void setPersonTypeId(Integer personTypeId) {
        this.personTypeId = personTypeId;
    }

    public boolean isResident() {
        return resident;
    }

    public void setResident(boolean resident) {
        this.resident = resident;
    }

    public String getMobileGroupId() {
        return mobileGroupId;
    }

    public void setMobileGroupId(String mobileGroupId) {
        this.mobileGroupId = mobileGroupId;
    }
}
