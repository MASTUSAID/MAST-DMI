package com.rmsi.mast.custom.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class CcroDto  implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private HashMap<String,String> personName_url;
	private long person_type;
	private int ownership;
	private String share_type;
	private String dlo;
	private String villageexecutive;
	private String villagechairman;
	private String neighbour_north;
	private String neighbour_south;
	private String neighbour_east;
	private String neighbour_west;
	private String hamlet;
	private String area;
	private String proposeduse;
	private String address;
	private List<String> adminName;
	private List<String> name;
	private String institute;
	private List<String> sharepercentage;
	private boolean resident;
	private String uka;
	private long usin;
	private HashMap<String,Long> personwithGid;
	private List<String> guardian;
	private List<String> guardianUrl;
	
	public HashMap<String, String> getPersonName_url() {
		return personName_url;
	}
	public void setPersonName_url(HashMap<String, String> personName_url) {
		this.personName_url = personName_url;
	}
	public long getPerson_type() {
		return person_type;
	}
	public void setPerson_type(long person_type) {
		this.person_type = person_type;
	}
	public int getOwnership() {
		return ownership;
	}
	public void setOwnership(int ownership) {
		this.ownership = ownership;
	}
	public String getShare_type() {
		return share_type;
	}
	public void setShare_type(String share_type) {
		this.share_type = share_type;
	}
	public String getDlo() {
		return dlo;
	}
	public void setDlo(String dlo) {
		this.dlo = dlo;
	}
	public String getVillageexecutive() {
		return villageexecutive;
	}
	public void setVillageexecutive(String villageexecutive) {
		this.villageexecutive = villageexecutive;
	}
	public String getVillagechairman() {
		return villagechairman;
	}
	public void setVillagechairman(String villagechairman) {
		this.villagechairman = villagechairman;
	}
	public String getNeighbour_north() {
		return neighbour_north;
	}
	public void setNeighbour_north(String neighbour_north) {
		this.neighbour_north = neighbour_north;
	}
	public String getNeighbour_south() {
		return neighbour_south;
	}
	public void setNeighbour_south(String neighbour_south) {
		this.neighbour_south = neighbour_south;
	}
	public String getNeighbour_east() {
		return neighbour_east;
	}
	public void setNeighbour_east(String neighbour_east) {
		this.neighbour_east = neighbour_east;
	}
	public String getNeighbour_west() {
		return neighbour_west;
	}
	public void setNeighbour_west(String neighbour_west) {
		this.neighbour_west = neighbour_west;
	}
	public String getHamlet() {
		return hamlet;
	}
	public void setHamlet(String hamlet) {
		this.hamlet = hamlet;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getProposeduse() {
		return proposeduse;
	}
	public void setProposeduse(String proposeduse) {
		this.proposeduse = proposeduse;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public List<String> getAdminName() {
		return adminName;
	}
	public void setAdminName(List<String> adminName) {
		this.adminName = adminName;
	}
	public List<String> getName() {
		return name;
	}
	public void setName(List<String> name) {
		this.name = name;
	}
	public String getInstitute() {
		return institute;
	}
	public void setInstitute(String institute) {
		this.institute = institute;
	}

	public boolean isResident() {
		return resident;
	}
	public void setResident(boolean resident) {
		this.resident = resident;
	}
	public String getUka() {
		return uka;
	}
	public void setUka(String uka) {
		this.uka = uka;
	}
	public long getUsin() {
		return usin;
	}
	public void setUsin(long usin) {
		this.usin = usin;
	}
	public HashMap<String, Long> getPersonwithGid() {
		return personwithGid;
	}
	public void setPersonwithGid(HashMap<String, Long> personwithGid) {
		this.personwithGid = personwithGid;
	}
	public List<String> getSharepercentage() {
		return sharepercentage;
	}
	public void setSharepercentage(List<String> sharepercentage) {
		this.sharepercentage = sharepercentage;
	}
	public List<String> getGuardian() {
		return guardian;
	}
	public void setGuardian(List<String> guardian) {
		this.guardian = guardian;
	}
	public List<String> getGuardianUrl() {
		return guardianUrl;
	}
	public void setGuardianUrl(List<String> guardianUrl) {
		this.guardianUrl = guardianUrl;
	}
	
	
	
}
