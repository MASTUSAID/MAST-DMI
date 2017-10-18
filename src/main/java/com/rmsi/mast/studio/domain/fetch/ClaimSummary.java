package com.rmsi.mast.studio.domain.fetch;

import com.rmsi.mast.studio.domain.PersonType;
import com.rmsi.mast.studio.domain.ShareType;
import com.rmsi.mast.studio.util.StringUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 * Returns claim summary from the view
 */
@Entity
@Table(name = "view_claims")
public class ClaimSummary implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private long usin;

    @Column(name = "usin_str")
    private String usinStr;

    @Column
    private String uka;

    @Column
    private Double acres;

    @Column(name = "hamlet_name")
    private String hamletName;

    @Column(name = "existing_use")
    private String existingUse;

    @Column(name = "proposed_use")
    private String proposedUse;

    @Column(name = "land_type")
    private String landType;

    @Column(name = "neighbor_north")
    private String neighborNorth;

    @Column(name = "neighbor_south")
    private String neighborSouth;

    @Column(name = "neighbor_east")
    private String neighborEast;

    @Column(name = "neighbor_west")
    private String neighborWest;

    @Column
    private String adjudicator1;

    @Column(name = "adjudicator1_signature")
    private String adjudicator1Signature;
    
    @Column
    private String adjudicator2;

    @Column(name = "adjudicator2_signature")
    private String adjudicator2Signature;
    
    @Column(name = "application_date")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date applicationDate;

    @Column(name = "status_id")
    private Integer statusId;

    @Column(name = "status_date")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date statusDate;
    
    @Column(name = "project_name")
    private String projectName;

    @Column(name = "claim_type")
    private String claimType;

    @Column(name = "tenure_class")
    private String tenureClass;

    @Column(name = "ownership_type_id")
    private int ownershipTypeId;

    @Column(name = "ownership_type")
    private String ownershipType;

    @Column
    private Double duration;

    @Column(name = "cert_date")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date certDate;

    @Column(name = "cert_number")
    private String certNumber;

    @Column(name = "acquisition_type")
    private String acquisitionType;

    @Column(name = "file_number")
    private String fileNumber;

    @Column(name = "relationship_type")
    private String relationshipType;

    @Column
    private String recorder;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "usin")
    List<PersonWithRightSummary> naturalOwners;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "usin")
    List<InstitutionSummary> nonNaturalOwners;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "usin")
    List<Poi> pois;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "usin")
    List<SpatialunitDeceasedPerson> deceasedPersons;

    public ClaimSummary() {
    }

    public long getUsin() {
        return usin;
    }

    public void setUsin(long usin) {
        this.usin = usin;
    }

    public String getUka() {
        return uka;
    }

    public void setUka(String uka) {
        this.uka = uka;
    }

    public Double getAcres() {
        return acres;
    }

    public void setAcres(Double acres) {
        this.acres = acres;
    }

    public String getHamletName() {
        return hamletName;
    }

    public void setHamletName(String hamletName) {
        this.hamletName = hamletName;
    }

    public String getExistingUse() {
        return existingUse;
    }

    public void setExistingUse(String existingUse) {
        this.existingUse = existingUse;
    }

    public String getProposedUse() {
        return proposedUse;
    }

    public void setProposedUse(String proposedUse) {
        this.proposedUse = proposedUse;
    }

    public String getLandType() {
        return landType;
    }

    public void setLandType(String landType) {
        this.landType = landType;
    }

    public String getNeighborNorth() {
        return neighborNorth;
    }

    public void setNeighborNorth(String neighborNorth) {
        this.neighborNorth = neighborNorth;
    }

    public String getNeighborSouth() {
        return neighborSouth;
    }

    public void setNeighborSouth(String neighborSouth) {
        this.neighborSouth = neighborSouth;
    }

    public String getNeighborEast() {
        return neighborEast;
    }

    public void setNeighborEast(String neighborEast) {
        this.neighborEast = neighborEast;
    }

    public String getNeighborWest() {
        return neighborWest;
    }

    public void setNeighborWest(String neighborWest) {
        this.neighborWest = neighborWest;
    }

    public String getAdjudicator1() {
        return adjudicator1;
    }

    public void setAdjudicator1(String adjudicator1) {
        this.adjudicator1 = adjudicator1;
    }

    public String getAdjudicator2() {
        return adjudicator2;
    }

    public void setAdjudicator2(String adjudicator2) {
        this.adjudicator2 = adjudicator2;
    }

    public Date getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(Date applicationDate) {
        this.applicationDate = applicationDate;
    }

    public String getUsinStr() {
        return usinStr;
    }

    public void setUsinStr(String usinStr) {
        this.usinStr = usinStr;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getClaimType() {
        return claimType;
    }

    public void setClaimType(String claimType) {
        this.claimType = claimType;
    }

    public String getTenureClass() {
        return tenureClass;
    }

    public void setTenureClass(String tenureClass) {
        this.tenureClass = tenureClass;
    }

    public String getOwnershipType() {
        return ownershipType;
    }

    public void setOwnershipType(String ownershipType) {
        this.ownershipType = ownershipType;
    }

    public int getOwnershipTypeId() {
        return ownershipTypeId;
    }

    public void setOwnershipTypeId(int ownershipTypeId) {
        this.ownershipTypeId = ownershipTypeId;
    }

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public Date getCertDate() {
        return certDate;
    }

    public void setCertDate(Date certDate) {
        this.certDate = certDate;
    }

    public String getCertNumber() {
        return certNumber;
    }

    public void setCertNumber(String certNumber) {
        this.certNumber = certNumber;
    }

    public String getAcquisitionType() {
        return acquisitionType;
    }

    public void setAcquisitionType(String acquisitionType) {
        this.acquisitionType = acquisitionType;
    }

    public String getFileNumber() {
        return fileNumber;
    }

    public void setFileNumber(String fileNumber) {
        this.fileNumber = fileNumber;
    }

    public String getRelationshipType() {
        return relationshipType;
    }

    public void setRelationshipType(String relationshipType) {
        this.relationshipType = relationshipType;
    }

    public String getRecorder() {
        return recorder;
    }

    public void setRecorder(String recorder) {
        this.recorder = recorder;
    }

    public List<PersonWithRightSummary> getNaturalOwners() {
        return naturalOwners;
    }

    public void setNaturalOwners(List<PersonWithRightSummary> naturalOwners) {
        this.naturalOwners = naturalOwners;
    }

    public List<InstitutionSummary> getNonNaturalOwners() {
        return nonNaturalOwners;
    }

    public void setNonNaturalOwners(List<InstitutionSummary> nonNaturalOwners) {
        this.nonNaturalOwners = nonNaturalOwners;
    }

    public List<Poi> getPois() {
        return pois;
    }

    public void setPois(List<Poi> pois) {
        this.pois = pois;
    }

    public List<SpatialunitDeceasedPerson> getDeceasedPersons() {
        return deceasedPersons;
    }

    public void setDeceasedPersons(List<SpatialunitDeceasedPerson> deceasedPersons) {
        this.deceasedPersons = deceasedPersons;
    }

    public String getAdjudicator1Signature() {
        return adjudicator1Signature;
    }

    public void setAdjudicator1Signature(String adjudicator1Signature) {
        this.adjudicator1Signature = adjudicator1Signature;
    }

    public String getAdjudicator2Signature() {
        return adjudicator2Signature;
    }

    public void setAdjudicator2Signature(String adjudicator2Signature) {
        this.adjudicator2Signature = adjudicator2Signature;
    }

    public Date getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }

    public List<PersonWithRightSummary> getPersonsForSignature() {
        ArrayList<PersonWithRightSummary> persons = new ArrayList<>();

        if (getNaturalOwners() == null || getNaturalOwners().size() < 1) {
            return persons;
        }

        if (getOwnershipTypeId() == ShareType.SHARE_GUARDIAN) {
            for (PersonWithRightSummary person : getNaturalOwners()) {
                if (person.getPersonTypeId() != PersonType.TYPE_OWNER) {
                    persons.add(person);
                }
            }
            return persons;
        } else {
            return getNaturalOwners();
        }
    }

    public String getOwnerNames() {
        if ((getNaturalOwners() == null || getNaturalOwners().size() < 1)
                && (getNonNaturalOwners() == null || getNonNaturalOwners().size() < 1)) {
            return "";
        }

        String names = "";
        String owners = "";
        String ownersWithShare = "";
        String guardians = "";
        String administrators = "";
        String deceased = "";

        // Find owners, guardians and admins
        if (getNaturalOwners() != null && getNaturalOwners().size() > 0) {
            for (int i = 0; i < getNaturalOwners().size(); i++) {

                PersonWithRightSummary person = getNaturalOwners().get(i);

                if (person.getPersonTypeId() == PersonType.TYPE_OWNER) {
                    String share = StringUtils.empty(person.getShare()).trim();
                    String owner = "";
                    
                    if (!share.endsWith("%")) {
                        share = share + "%";
                    }

                    if (owners.length() > 0) {
                        // Check if last owner
                        if (i + 1 == getNaturalOwners().size() || getNaturalOwners().get(i + 1).getPersonTypeId() != PersonType.TYPE_OWNER) {
                            owner = " na <b>" + person.getFullName() + "</b>";
                            owners = owners + owner;
                        } else {
                            owner = ", <b>" + person.getFullName() + "</b>";
                            owners = owners + owner;
                        }
                    } else {
                        owner = "<b>" + person.getFullName() + "</b>";
                        owners = owner;
                    }

                    ownersWithShare = ownersWithShare + owner + " (<b>" + share + "</b>)";
                } else if (person.getPersonTypeId() == PersonType.TYPE_GUARDIAN) {
                    if (guardians.length() > 0) {
                        guardians = guardians + ", <b>" + person.getFullName() + "</b>";
                    } else {
                        guardians = "<b>" + person.getFullName() + "</b>";
                    }
                } else if (person.getPersonTypeId() == PersonType.TYPE_ADMINISTRATOR) {
                    if (administrators.length() > 0) {
                        administrators = administrators + ", <b>" + person.getFullName() + "</b>";
                    } else {
                        administrators = "<b>" + person.getFullName() + "</b>";
                    }
                }
            }
        } else if (getNonNaturalOwners() != null && getNonNaturalOwners().size() > 0) {
            for (InstitutionSummary nonPerson : getNonNaturalOwners()) {
                if (owners.length() > 0) {
                    owners = owners + ", <b>" + StringUtils.empty(nonPerson.getInstututionName()) + "</b>";
                } else {
                    owners = "<b>" + StringUtils.empty(nonPerson.getInstututionName()) + "</b>";
                }
            }
        }

        // Find deceased persons
        if (getDeceasedPersons() != null && getDeceasedPersons().size() > 0) {
            for (SpatialunitDeceasedPerson dPerson : getDeceasedPersons()) {
                if (deceased.length() > 0) {
                    deceased = deceased + ", <b>" + dPerson.getFullName() + "</b>";
                } else {
                    deceased = "<b>" + dPerson.getFullName() + "</b>";
                }
            }
        }

        String resident = "Mkazi";
        String residents = "Wakazi";
        
        if(getHasNonResident()){
            resident = "Sio mkazi";
            residents = "Sio Wakazi";
        }
                
        if (getOwnershipTypeId() == ShareType.SHARE_SINGLE || getOwnershipTypeId() == ShareType.SHARE_INSTITUTION) {
            names = owners + " (humu ndani akirejewa kama \"" + resident + "\")";
        } else if (getOwnershipTypeId() == ShareType.SHARE_MULTIPLE_JOINT) {
            names = owners + " kwa umiliki wa pamoja usio gawanyika (humu ndani wakirejewa kama \"" + residents + "\")";
        } else if (getOwnershipTypeId() == ShareType.SHARE_MULTIPLE_COMMON) {
            names = ownersWithShare + " kwa umiliki wa hisa (humu ndani wakirejewa kama \"" + residents + "\")";
        } else if (getOwnershipTypeId() == ShareType.SHARE_GUARDIAN) {
            names = guardians + " msimamizi mlezi wa " + owners + " (humu ndani akirejewa kama \"" + resident + "\")";
        } else if (getOwnershipTypeId() == ShareType.SHARE_ADMINISTRATOR) {
            names = administrators + " ambaye ni msimamizi wa mirathi ya Marehemu " + deceased;
        }
        return names;
    }
    
    public boolean getHasNonResident(){
        if(getNaturalOwners() != null && getNaturalOwners().size() > 0){
            for(PersonWithRightSummary person : getNaturalOwners()){
                if(person.getVillageResident()){
                    return false;
                }
            }
        }
        return false;
    }
    
    public Date getStartDate(){
        if(getCertDate() != null){
            Calendar cal = Calendar.getInstance();
            cal.setTime(getCertDate());
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DATE);
            int newMonth = 0;
            
            if((month == Calendar.APRIL || month == Calendar.JULY || month == Calendar.OCTOBER 
                    || month == Calendar.JANUARY) && day == 1){
                return getCertDate();
            }
            
            if(month == Calendar.JANUARY || month == Calendar.FEBRUARY || month == Calendar.MARCH){
                newMonth = Calendar.JANUARY;
            }
            
            if(month == Calendar.APRIL || month == Calendar.MAY || month == Calendar.JUNE){
                newMonth = Calendar.APRIL;
            }
            
            if(month == Calendar.JULY || month == Calendar.AUGUST || month == Calendar.SEPTEMBER){
                newMonth = Calendar.JULY;
            }
            
            if(month == Calendar.OCTOBER || month == Calendar.NOVEMBER || month == Calendar.DECEMBER){
                newMonth = Calendar.OCTOBER;
            }
            
            cal.set(Calendar.DATE, 1);
            cal.set(Calendar.MONTH, newMonth);
            return cal.getTime();
        }
        return null;
    }
}
