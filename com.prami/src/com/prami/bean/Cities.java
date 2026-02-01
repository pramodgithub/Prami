package com.prami.bean;

public class Cities {
	private int locationId;
	private String locationName;
	private String locationLogo;
	public Cities(int locationId, String locationName, String locationLogo) {
		super();
		this.locationId = locationId;
		this.locationName = locationName;
		this.locationLogo = locationLogo;
	}
	public int getLocationId() {
		return locationId;
	}
	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	public String getLocationLogo() {
		return locationLogo;
	}
	public void setLocationLogo(String locationLogo) {
		this.locationLogo = locationLogo;
	}
	
	
	
	
	
	
}
