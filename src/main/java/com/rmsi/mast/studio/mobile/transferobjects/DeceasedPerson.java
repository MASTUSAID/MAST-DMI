package com.rmsi.mast.studio.mobile.transferobjects;

import java.io.Serializable;

public class DeceasedPerson implements Serializable {
    private Long id;
    private String firstName;
    private String lastName;
    private String middleName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public DeceasedPerson(){

    }
}
