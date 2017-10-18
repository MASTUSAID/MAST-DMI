package com.rmsi.mast.studio.domain.fetch;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Show different claim types statistics
 */
@Entity
public class ClaimsStat implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "claim_type")
    private String claimType;

    @Column
    private int collected;

    @Column
    int approved;
    
    @Column
    private int denied;

    public String getClaimType() {
        return claimType;
    }

    public void setClaimType(String claimType) {
        this.claimType = claimType;
    }

    public int getCollected() {
        return collected;
    }

    public void setCollected(int collected) {
        this.collected = collected;
    }

    public int getApproved() {
        return approved;
    }

    public void setApproved(int approved) {
        this.approved = approved;
    }

    public int getDenied() {
        return denied;
    }

    public void setDenied(int denied) {
        this.denied = denied;
    }
}
