package com.rmsi.mast.studio.domain.fetch;

import java.util.List;

/**
 *
 * Summary object for various claim profile statistics
 */
public class ClaimProfile {
    private List<ClaimsStat> claimsStatList;
    Integer uniqueMales = 0;
    Integer uniqueFemales = 0;
    Integer uniqueMalesDenied = 0;
    Integer uniqueFemalesDenied = 0;
    List<OwnershipTypeStat> ownershipTypeStatList;
    List<CcroOccurrenceStat> ccroOccurrenceStatList;
    List<ClaimantAgeStat> claimantsAgeList;
    List<DisputeStat> disputes;
            
    public ClaimProfile(){
    }

    public List<ClaimsStat> getClaimsStatList() {
        return claimsStatList;
    }

    public void setClaimsStatList(List<ClaimsStat> claimsStatList) {
        this.claimsStatList = claimsStatList;
    }

    public Integer getUniqueMales() {
        return uniqueMales;
    }

    public void setUniqueMales(Integer uniqueMales) {
        this.uniqueMales = uniqueMales;
    }

    public Integer getUniqueFemales() {
        return uniqueFemales;
    }

    public void setUniqueFemales(Integer uniqueFemales) {
        this.uniqueFemales = uniqueFemales;
    }

    public Integer getUniqueMalesDenied() {
        return uniqueMalesDenied;
    }

    public void setUniqueMalesDenied(Integer uniqueMalesDenied) {
        this.uniqueMalesDenied = uniqueMalesDenied;
    }

    public Integer getUniqueFemalesDenied() {
        return uniqueFemalesDenied;
    }

    public void setUniqueFemalesDenied(Integer uniqueFemalesDenied) {
        this.uniqueFemalesDenied = uniqueFemalesDenied;
    }

    public List<OwnershipTypeStat> getOwnershipTypeStatList() {
        return ownershipTypeStatList;
    }

    public void setOwnershipTypeStatList(List<OwnershipTypeStat> ownershipTypeStatList) {
        this.ownershipTypeStatList = ownershipTypeStatList;
    }

    public List<CcroOccurrenceStat> getCcroOccurrenceStatList() {
        return ccroOccurrenceStatList;
    }

    public void setCcroOccurrenceStatList(List<CcroOccurrenceStat> ccroOccurrenceStatList) {
        this.ccroOccurrenceStatList = ccroOccurrenceStatList;
    }

    public List<ClaimantAgeStat> getClaimantsAgeList() {
        return claimantsAgeList;
    }

    public void setClaimantsAgeList(List<ClaimantAgeStat> claimantsAgeList) {
        this.claimantsAgeList = claimantsAgeList;
    }

    public List<DisputeStat> getDisputes() {
        return disputes;
    }

    public void setDisputes(List<DisputeStat> disputes) {
        this.disputes = disputes;
    }
}
