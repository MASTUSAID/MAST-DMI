package com.rmsi.mast.studio.domain;

import java.io.Serializable;

import javax.persistence.*;

/**
 * The persistent class for the project_hamlets database table.
 *
 */
@Entity
@Table(name = "project_hamlets")
public class ProjectHamlet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "PROJECT_HAMLET_ID_GENERATOR", sequenceName = "project_hamlets_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PROJECT_HAMLET_ID_GENERATOR")
    private Long id;

    @Column(name = "hamlet_code")
    private String hamletCode;

    @Column(name = "hamlet_name")
    private String hamletName;

    @Column(name = "hamlet_name_second_language")
    private String hamletNameSecondLanguage;

    @Column(name = "project_name")
    private String projectName;

    @Column(name = "hamlet_leader_name")
    private String hamletLeaderName;

    private Integer count;

    public ProjectHamlet() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHamletLeaderName() {
        return hamletLeaderName;
    }

    public void setHamletLeaderName(String hamletLeaderName) {
        this.hamletLeaderName = hamletLeaderName;
    }

    public String getHamletCode() {
        return this.hamletCode;
    }

    public void setHamletCode(String hamletCode) {
        this.hamletCode = hamletCode;
    }

    public String getHamletName() {
        return this.hamletName;
    }

    public void setHamletName(String hamletName) {
        this.hamletName = hamletName;
    }

    public String getHamletNameSecondLanguage() {
        return this.hamletNameSecondLanguage;
    }

    public void setHamletNameSecondLanguage(String hamletNameSecondLanguage) {
        this.hamletNameSecondLanguage = hamletNameSecondLanguage;
    }

    public String getProjectName() {
        return this.projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

}
