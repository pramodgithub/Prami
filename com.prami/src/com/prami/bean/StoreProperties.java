package com.prami.bean;

public class StoreProperties {
	private String storePropetyName;
	private int storeProprtyPoint;
	private String storePropertyDesc;
	private String storepropertyBadge;
	private String storepropertyIcon;
	
	
	
	public StoreProperties(String storePropetyName, int storeProprtyPoint, String storePropertyDesc,
			String storepropertyBadge, String storepropertyIcon) {
		super();
		this.storePropetyName = storePropetyName;
		this.storeProprtyPoint = storeProprtyPoint;
		this.storePropertyDesc = storePropertyDesc;
		this.storepropertyBadge = storepropertyBadge;
		this.storepropertyIcon = storepropertyIcon;
	}
	public StoreProperties(String storePropetyName, int storeProprtyPoint, String storePropertyDesc) {
		super();
		this.storePropetyName = storePropetyName;
		this.storeProprtyPoint = storeProprtyPoint;
		this.storePropertyDesc = storePropertyDesc;
	}
	public String getStorePropetyName() {
		return storePropetyName;
	}
	public void setStorePropetyName(String storePropetyName) {
		this.storePropetyName = storePropetyName;
	}
	public int getStoreProprtyPoint() {
		return storeProprtyPoint;
	}
	public void setStoreProprtyPoint(int storeProprtyPoint) {
		this.storeProprtyPoint = storeProprtyPoint;
	}
	public String getStorePropertyDesc() {
		return storePropertyDesc;
	}
	public void setStorePropertyDesc(String storePropertyDesc) {
		this.storePropertyDesc = storePropertyDesc;
	}
	public String getStorepropertyBadge() {
		return storepropertyBadge;
	}
	public void setStorepropertyBadge(String storepropertyBadge) {
		this.storepropertyBadge = storepropertyBadge;
	}
	public String getStorepropertyIcon() {
		return storepropertyIcon;
	}
	public void setStorepropertyIcon(String storepropertyIcon) {
		this.storepropertyIcon = storepropertyIcon;
	}
		
}
