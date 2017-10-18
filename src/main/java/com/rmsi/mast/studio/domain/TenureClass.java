package com.rmsi.mast.studio.domain;

import java.io.Serializable;
import java.lang.String;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: TenureClass
 *
 * @author Shruti.Thakur
 */
@Entity
@Table(name = "tenure_class")
public class TenureClass implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "tenureclass_id")
    private int tenureId;

    @Column(name = "tenure_class", nullable = false)
    private String tenureClass;

    @Column(name = "tenure_class_sw", nullable = false)
    private String tenureClassSw;
    
    @Column
    private boolean active;

    @Column(name = "for_adjudication")
    private boolean forAdjudication;

    public TenureClass() {
        super();
    }

    public int getTenureId() {
        return this.tenureId;
    }

    public void setTenureId(int tenureId) {
        this.tenureId = tenureId;
    }

    public String getTenureClass() {
        return this.tenureClass;
    }

    public void setTenureClass(String tenureClass) {
        this.tenureClass = tenureClass;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isForAdjudication() {
        return forAdjudication;
    }

    public void setForAdjudication(boolean forAdjudication) {
        this.forAdjudication = forAdjudication;
    }

    public String getTenureClassSw() {
        return tenureClassSw;
    }

    public void setTenureClassSw(String tenureClassSw) {
        this.tenureClassSw = tenureClassSw;
    }
}
