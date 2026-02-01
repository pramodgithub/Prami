package com.prami.bean;

public class Activity {
	
	String userId;
	int activityType;
	int productId;
	String userIP;
	String country;
	String region;
	String city;
	String zip;
	String latitude;
	String longitude;
	public Activity(String userId, int productId, String userIP, String country, String region, String city, String zip,
			String latitude, String longitude) {
		super();
		this.userId = userId;
		this.productId = productId;
		this.userIP = userIP;
		this.country = country;
		this.region = region;
		this.city = city;
		this.zip = zip;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	public Activity(int activityType, String userId,  int productId, String userIP, String country, String region,
			String city, String zip, String latitude, String longitude) {
		super();
		this.userId = userId;
		this.activityType = activityType;
		this.productId = productId;
		this.userIP = userIP;
		this.country = country;
		this.region = region;
		this.city = city;
		this.zip = zip;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getActivityType() {
		return activityType;
	}
	public void setActivityType(int activityType) {
		this.activityType = activityType;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getUserIP() {
		return userIP;
	}
	public void setUserIP(String userIP) {
		this.userIP = userIP;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
	

}
