package com.rmsi.mast.studio.domain;

import java.io.Serializable;
import java.lang.String;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: PersonType
 *
 * @author Shruti.Thakur
 */
@Entity
@Table(name = "person_type")
public class PersonType implements Serializable {

    private static final long serialVersionUID = 1L;
    public static int TYPE_NATURAL = 1;
    public static int TYPE_NON_NATURAL = 2;
    public static int TYPE_OWNER = 3;
    public static int TYPE_ADMINISTRATOR = 4;
    public static int TYPE_GUARDIAN = 5;

    @Id
    @SequenceGenerator(name = "PERSON_TYPE_ID_GENERATOR", sequenceName = "PERSON_TYPE_GID_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PERSON_TYPE_ID_GENERATOR")
    private long person_type_gid;

    @Column(name = "person_type")
    private String PersonType;

    @Column(name = "person_type_sw")
    private String PersonType_sw;

    public PersonType() {
        super();
    }

    public long getPerson_type_gid() {
        return this.person_type_gid;
    }

    public void setPerson_type_gid(long person_type_gid) {
        this.person_type_gid = person_type_gid;
    }

    public String getPersonType() {
        return this.PersonType;
    }

    public void setPersonType(String PersonType) {
        this.PersonType = PersonType;
    }

    public String getPersonType_sw() {
        return PersonType_sw;
    }

    public void setPersonType_sw(String personType_sw) {
        PersonType_sw = personType_sw;
    }

}
