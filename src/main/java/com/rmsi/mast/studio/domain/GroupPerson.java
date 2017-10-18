package com.rmsi.mast.studio.domain;

import java.io.Serializable;
import java.lang.String;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: GroupPerson
 *
 * @author Shruti.Thakur
 */
@Entity
@Table(name = "group_person")
public class GroupPerson implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "GRPOUP_PERSON_ID_GENERATOR", sequenceName = "GRPOUP_PERSON_GID_SEQ", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GRPOUP_PERSON_ID_GENERATOR")
	private int gid;
	private String groupName;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "person_gid", nullable = false)
	private Person person;

	private String ownershipType;

	public GroupPerson() {
		super();
	}

	public int getGid() {
		return this.gid;
	}

	public void setGid(int gid) {
		this.gid = gid;
	}

	public String getGroupName() {
		return this.groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public String getOwnershipType() {
		return ownershipType;
	}

	public void setOwnershipType(String ownershipType) {
		this.ownershipType = ownershipType;
	}

}
