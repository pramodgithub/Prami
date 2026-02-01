package com.prami.bean;

public class Group {
	private int groupId;
	private String groupName;
	private String groupImage;
	private String groupDesc;
	private int groupStatus;
	private String groupSeoKeywords;
	private int catID;
	private String catName;
	
	public Group(int groupId, String groupName, String groupImage, String groupDesc, int groupStatus) {
		super();
		this.groupId = groupId;
		this.groupName = groupName;
		this.groupImage = groupImage;
		this.groupDesc = groupDesc;
		this.groupStatus = groupStatus;
	}
	
	public Group(int groupId, String groupName, String groupImage, String groupDesc,
			String groupSeoKeywords) {
		super();
		this.groupId = groupId;
		this.groupName = groupName;
		this.groupImage = groupImage;
		this.groupDesc = groupDesc;
		this.groupSeoKeywords = groupSeoKeywords;
		
	}

	public Group(int groupId, String groupName, String groupImage, int catID,
			String catName) {
		super();
		this.groupId = groupId;
		this.groupName = groupName;
		this.groupImage = groupImage;
		this.catID = catID;
		this.catName = catName;
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
	public int getGroupStatus() {
		return groupStatus;
	}
	public void setGroupStatus(int groupStatus) {
		this.groupStatus = groupStatus;
	}
	public String getGroupSeoKeywords() {
		return groupSeoKeywords;
	}
	public void setGroupSeoKeywords(String groupSeoKeywords) {
		this.groupSeoKeywords = groupSeoKeywords;
	}
	public int getCatID() {
		return catID;
	}
	public void setCatID(int catID) {
		this.catID = catID;
	}
	public String getCatName() {
		return catName;
	}
	public void setCatName(String catName) {
		this.catName = catName;
	}
	
	
}
