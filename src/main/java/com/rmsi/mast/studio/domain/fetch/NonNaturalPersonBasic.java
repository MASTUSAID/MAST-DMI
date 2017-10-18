package com.rmsi.mast.studio.domain.fetch;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "non_natural_person")
@PrimaryKeyJoinColumn(name="NON_NATURAL_PERSON_GID", referencedColumnName="PERSON_GID")
public class NonNaturalPersonBasic extends PersonBasic implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "instutution_name")
    private String instututionName;
    
    @Column
    private String address;
    
    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name="group_type")
    private Integer groupTypeId;

    @Column
    private Boolean active;
    
    @OneToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="poc_gid")
    private NaturalPersonBasic representative;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_id")
    private List<NonNaturalPersonAttributeValue> attributes;
    
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

    public Integer getGroupTypeId() {
        return groupTypeId;
    }

    public void setGroupTypeId(Integer groupTypeId) {
        this.groupTypeId = groupTypeId;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public NaturalPersonBasic getRepresentative() {
        return representative;
    }

    public void setRepresentative(NaturalPersonBasic representative) {
        this.representative = representative;
    }

    public List<NonNaturalPersonAttributeValue> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<NonNaturalPersonAttributeValue> attributes) {
        this.attributes = attributes;
    }
}
