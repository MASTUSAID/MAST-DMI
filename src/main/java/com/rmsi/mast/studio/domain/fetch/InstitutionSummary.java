package com.rmsi.mast.studio.domain.fetch;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Returns person of interest from the view
 */
@Entity
@Table(name = "view_non_natural_persons_with_right")
public class InstitutionSummary implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "gid")
    private Long id;

    @Column
    private Long usin;
    
    @Column(name = "instutution_name")
    private String instututionName;

    @Column
    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "institution_type")
    private String institutionType;

    @ManyToOne
    @JoinColumn(name = "rep_gid")
    private PersonSummary representative;
    
    public InstitutionSummary() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUsin() {
        return usin;
    }

    public void setUsin(Long usin) {
        this.usin = usin;
    }

    public String getInstututionName() {
        return instututionName;
    }

    public void setInstututionName(String instututionName) {
        this.instututionName = instututionName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getInstitutionType() {
        return institutionType;
    }

    public void setInstitutionType(String institutionType) {
        this.institutionType = institutionType;
    }

    public PersonSummary getRepresentative() {
        return representative;
    }

    public void setRepresentative(PersonSummary representative) {
        this.representative = representative;
    }
}
