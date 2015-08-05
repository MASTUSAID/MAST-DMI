package com.rmsi.mast.studio.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.rmsi.mast.studio.util.JsonDateSerializer;

/**
 * Entity implementation class for Entity: SocialTenureRelationship
 *
 * @author Shruti.Thakur
 */
@Entity
@Table(name = "social_tenure_relationship")
public class SocialTenureRelationship implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "social_tenure_relationship_id", sequenceName = "social_tenure_relationship_gid_seq")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "social_tenure_relationship_id")
	private int gid;

	@ManyToOne
	@JoinColumn(name = "share", nullable = false)
	private ShareType share_type;

	/*
	 * @ManyToOne(cascade = CascadeType.ALL)
	 * 
	 * @JoinColumn(name = "usin", nullable = false) private SpatialUnit usin;
	 */
	private Long usin;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "person_gid", updatable = false)
	private Person person_gid;

	@ManyToOne
	@JoinColumn(name = "occupancy_type_id")
	private OccupancyType occupancy_type_id;

	@ManyToOne
	@JoinColumn(name = "tenureclass_id")
	private TenureClass tenureclass_id;

	private Date social_tenure_startdate;
	private Date social_tenure_enddate;
	private float tenure_duration;
	private boolean isActive;

	public SocialTenureRelationship() {
		super();
	}

	public ShareType getShare_type() {
		return this.share_type;
	}

	public void setShare_type(ShareType share_type) {
		this.share_type = share_type;
	}

	public int getGid() {
		return this.gid;
	}

	public void setGid(int gid) {
		this.gid = gid;
	}

	public Long getUsin() {
		return this.usin;
	}

	public void setUsin(Long usin) {
		this.usin = usin;
	}

	public Person getPerson_gid() {
		return this.person_gid;
	}

	public void setPerson_gid(Person person_gid) {
		this.person_gid = person_gid;
	}

	public OccupancyType getOccupancy_type_id() {
		return this.occupancy_type_id;
	}

	public void setOccupancy_type_id(OccupancyType occupancy_type_id) {
		this.occupancy_type_id = occupancy_type_id;
	}

	public TenureClass getTenureclass_id() {
		return this.tenureclass_id;
	}

	public void setTenureclass_id(TenureClass tenureclass_id) {
		this.tenureclass_id = tenureclass_id;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getSocial_tenure_startdate() {
		return this.social_tenure_startdate;
	}

	public void setSocial_tenure_startdate(Date social_tenure_startdate) {
		this.social_tenure_startdate = social_tenure_startdate;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getSocial_tenure_enddate() {
		return this.social_tenure_enddate;
	}

	public void setSocial_tenure_enddate(Date social_tenure_enddate) {
		this.social_tenure_enddate = social_tenure_enddate;
	}

	public float getTenure_duration() {
		return this.tenure_duration;
	}

	public void setTenure_duration(float tenure_duration) {
		this.tenure_duration = tenure_duration;
	}

	public boolean getIsActive() {
		return this.isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}

}
