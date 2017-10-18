package com.rmsi.mast.studio.domain.fetch;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Show different ownership types statistics
 */
@Entity
public class OwnershipTypeStat implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ownership_type")
    private String ownershipType;
    @Column
    private int males;
    @Column
    private int females;
    @Column
    private int total;

    public String getOwnershipType() {
        return ownershipType;
    }

    public void setOwnershipType(String ownershipType) {
        this.ownershipType = ownershipType;
    }

    public int getMales() {
        return males;
    }

    public void setMales(int males) {
        this.males = males;
    }

    public int getFemales() {
        return females;
    }

    public void setFemales(int females) {
        this.females = females;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
