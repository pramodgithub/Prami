package com.prami.bean;

public class CategoryType {
	int typeID;
	String typeName;
	String typeImage;
	String typeIcon;
	boolean typeStatus;
	
	public CategoryType(int typeID, String typeName, String typeImage) {
		super();
		this.typeID = typeID;
		this.typeName = typeName;
		this.typeImage = typeImage;
	}
	public int getTypeID() {
		return typeID;
	}
	public void setTypeID(int typeID) {
		this.typeID = typeID;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getTypeImage() {
		return typeImage;
	}
	public void setTypeImage(String typeImage) {
		this.typeImage = typeImage;
	}
	public String getTypeIcon() {
		return typeIcon;
	}
	public void setTypeIcon(String typeIcon) {
		this.typeIcon = typeIcon;
	}
	public boolean isTypeStatus() {
		return typeStatus;
	}
	public void setTypeStatus(boolean typeStatus) {
		this.typeStatus = typeStatus;
	}
	
		
}
