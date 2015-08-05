package com.rmsi.mast.studio.util;

public class SaveProject {

	private String extent;
	private String actualProjectName;
	private String newProjectName;
	private String[][] layerVisibility;
	private String newProjectDescription;
	private String[] users;
	private String activelayer;
	private String owner;
	
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getActivelayer() {
		return activelayer;
	}
	public void setActivelayer(String activelayer) {
		this.activelayer = activelayer;
	}
	public String[] getUsers() {
		return users;
	}
	public void setUsers(String[] users) {
		this.users = users;
	}
	public String getNewProjectDescription() {
		return newProjectDescription;
	}
	public void setNewProjectDescription(String newProjectDescription) {
		this.newProjectDescription = newProjectDescription;
	}
	public String getActualProjectName() {
		return actualProjectName;
	}
	public void setActualProjectName(String actualProjectName) {
		this.actualProjectName = actualProjectName;
	}
	public String getNewProjectName() {
		return newProjectName;
	}
	public void setNewProjectName(String newProjectName) {
		this.newProjectName = newProjectName;
	}
	public String getExtent() {
		return extent;
	}
	public void setExtent(String extent) {
		this.extent = extent;
	}
	public String[][] getLayerVisibility() {
		return layerVisibility;
	}
	public void setLayerVisibility(String[][] layerVisibility) {
		this.layerVisibility = layerVisibility;
	}
	
	
	
	
}
