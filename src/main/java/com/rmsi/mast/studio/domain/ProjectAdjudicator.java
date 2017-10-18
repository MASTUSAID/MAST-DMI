package com.rmsi.mast.studio.domain;

import java.io.Serializable;

import javax.persistence.*;

/**
 * The persistent class for the project_adjudicators database table.
 *
 */
@Entity
@Table(name = "project_adjudicators")
public class ProjectAdjudicator implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "PROJECT_ADJUDICATOR_ID_GENERATOR", sequenceName = "project_adjudicators_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PROJECT_ADJUDICATOR_ID_GENERATOR")
    private Integer id;

    @Column(name = "adjudicator_name")
    private String adjudicatorName;

    @Column(name = "project_name")
    private String projectName;

    @Column(name = "signature_path")
    private String signaturePath;

    public ProjectAdjudicator() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAdjudicatorName() {
        return this.adjudicatorName;
    }

    public void setAdjudicatorName(String adjudicatorName) {
        this.adjudicatorName = adjudicatorName;
    }

    public String getProjectName() {
        return this.projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getSignaturePath() {
        return signaturePath;
    }

    public void setSignaturePath(String signaturePath) {
        this.signaturePath = signaturePath;
    }
}
