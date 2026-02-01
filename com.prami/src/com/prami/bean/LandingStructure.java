package com.prami.bean;

import java.util.List;

public class LandingStructure {
	int groupId;
	String groupName;
	String groupImage;
	String groupDesc;

	List<Collection> collections;
	
	public LandingStructure() {
		super();
		// TODO Auto-generated constructor stub
	}
	public LandingStructure(int groupId, String groupName, String groupImage, 
			List<Collection> collections) {
		super();
		this.groupId = groupId;
		this.groupName = groupName;
		this.groupImage = groupImage;
		this.collections = collections;
	}
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getGroupImage() {
		return groupImage;
	}
	public void setGroupImage(String groupImage) {
		this.groupImage = groupImage;
	}
	public String getGroupDesc() {
		return groupDesc;
	}
	public void setGroupDesc(String groupDesc) {
		this.groupDesc = groupDesc;
	}
	public List<Collection> getCollections() {
		return collections;
	}
	public void setCollections(List<Collection> collections) {
		this.collections = collections;
	}
	
}
