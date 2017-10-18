package com.rmsi.mast.studio.domain.fetch;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "source_document")
public class MediaBasic implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private int gid;

    @Column(name = "mediatype")
    private String mediaType;

    @Column
    private Long usin;
    
    @Column(name = "dispute_id")
    private Long disputeId;
    
    @Column
    private Boolean active;
    
    @Column(name = "social_tenure_gid")
    private Integer rightId;
    
    @Column(name = "person_gid")
    private Long personId;
    
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_id")
    private List<MediaAttributeValue> attributes;
    
    public MediaBasic() {
        super();
    }

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public Long getUsin() {
        return usin;
    }

    public void setUsin(Long usin) {
        this.usin = usin;
    }

    public Long getDisputeId() {
        return disputeId;
    }

    public void setDisputeId(Long disputeId) {
        this.disputeId = disputeId;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Integer getRightId() {
        return rightId;
    }

    public void setRightId(Integer rightId) {
        this.rightId = rightId;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public List<MediaAttributeValue> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<MediaAttributeValue> attributes) {
        this.attributes = attributes;
    }
}
