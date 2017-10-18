package com.rmsi.mast.studio.domain.fetch;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class DisputeStat implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "dispute_type")
    private String disputeType;

    @Column
    private Integer resolved;
    
    @Column
    private Integer unresolved;
    
    @Column
    private Integer total;

    public String getDisputeType() {
        return disputeType;
    }

    public void setDisputeType(String disputeType) {
        this.disputeType = disputeType;
    }

    public Integer getResolved() {
        return resolved;
    }

    public void setResolved(Integer resolved) {
        this.resolved = resolved;
    }

    public Integer getUnresolved() {
        return unresolved;
    }

    public void setUnresolved(Integer unresolved) {
        this.unresolved = unresolved;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
