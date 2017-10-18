package com.rmsi.mast.studio.domain;

import java.io.Serializable;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Person
 *
 * @author Shruti.Thakur
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "PERSON_ID_GENERATOR", sequenceName = "PERSON_GID_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PERSON_ID_GENERATOR")
    @Column(name = "person_gid")
    private long person_gid;

    @ManyToOne
    @JoinColumn(name = "person_type_gid", nullable = false)
    private PersonType person_type_gid;

    @Column(nullable = false)
    private boolean resident;

    @Column(name = "mobile_group_id")
    private String mobileGroupId;

    public Person() {
        super();
    }

    public long getPerson_gid() {
        return this.person_gid;
    }

    public void setPerson_gid(long person_gid) {
        this.person_gid = person_gid;
    }

    public PersonType getPerson_type_gid() {
        return this.person_type_gid;
    }

    public void setPerson_type_gid(PersonType person_type_gid) {
        this.person_type_gid = person_type_gid;
    }

    public boolean getResident() {
        return this.resident;
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
