package com.prami.bean;

import java.util.List;

public class LocalStore {
	private int storeId;
	private String storeName;
	private String storeTagLine;
	private String storeDescription;
	private String storeServicesDescription;
	private String storeLogo;
	private String storeImage;
	private int isStoreVerified;
	private String storeEstablishedDate;
	private String storeAddress;
	private int storeAreaId;
	private String storeAreaName;
	private int storeCityId;
	private String storeCityName;
	private String storeLandmark;
	private String storeGeoLocation;
	private String storePin;
	private String storeEmail;
	private String storeContact1;
	private String storeContact2;
	private String storeWebsite;
	private List<LocalStoreServices> LocalStoreServicesList;
	private List<LocalSocial> LocalStoreSocialList;
	private int column;
	
	
	public LocalStore() {
		super();
		// TODO Auto-generated constructor stub
	}
	public LocalStore(int storeId, String storeName, String storeTagLine, String storeDescription, String storeLogo, String storeImage,
			int isStoreVerified, String storeEstablishedDate, int storeAreaId, String storeAreaName, int storeCityId,
			String storeCityName, String storeLandmark, List<LocalStoreServices> localStoreServicesList, int column) {
		super();
		this.storeId = storeId;
		this.storeName = storeName;
		this.storeTagLine = storeTagLine;
		this.storeDescription = storeDescription;
		this.storeLogo = storeLogo;
		this.storeImage = storeImage;
		this.isStoreVerified = isStoreVerified;
		this.storeEstablishedDate = storeEstablishedDate;
		this.storeAreaId = storeAreaId;
		this.storeAreaName = storeAreaName;
		this.storeCityId = storeCityId;
		this.storeCityName = storeCityName;
		this.storeLandmark = storeLandmark;
		LocalStoreServicesList = localStoreServicesList;
		this.column = column;
	}
	public LocalStore(int storeId, String storeName, String storeTagLine, String storeDescription, String storeServicesDescription, String storeLogo, String storeImage,
			int isStoreVerified, String storeEstablishedDate, String storeAddress, int storeAreaId,
			String storeAreaName, int storeCityId, String storeCityName, String storeLandmark, String storeGeoLocation,
			String storePin, String storeEmail, String storeContact1, String storeContact2, String storeWebsite, List<LocalStoreServices> localStoreServicesList,List<LocalSocial> localStoreSocialList) {
		super();
		this.storeId = storeId;
		this.storeName = storeName;
		this.storeTagLine = storeTagLine;
		this.storeDescription = storeDescription;
		this.storeServicesDescription = storeServicesDescription;
		this.storeLogo = storeLogo;
		this.storeImage = storeImage;
		this.isStoreVerified = isStoreVerified;
		this.storeEstablishedDate = storeEstablishedDate;
		this.storeAddress = storeAddress;
		this.storeAreaId = storeAreaId;
		this.storeAreaName = storeAreaName;
		this.storeCityId = storeCityId;
		this.storeCityName = storeCityName;
		this.storeLandmark = storeLandmark;
		this.storeGeoLocation = storeGeoLocation;
		this.storePin = storePin;
		this.storeEmail = storeEmail;
		this.storeContact1 = storeContact1;
		this.storeContact2 = storeContact2;
		this.storeWebsite = storeWebsite;
		LocalStoreServicesList = localStoreServicesList;
		LocalStoreSocialList = localStoreSocialList;
	}
	public int getStoreId() {
		return storeId;
	}
	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getStoreTagLine() {
		return storeTagLine;
	}
	public void setStoreTagLine(String storeTagLine) {
		this.storeTagLine = storeTagLine;
	}
	public String getStoreDescription() {
		return storeDescription;
	}
	public void setStoreDescription(String storeDescription) {
		this.storeDescription = storeDescription;
	}
	public String getStoreServicesDescription() {
		return storeServicesDescription;
	}
	public void setStoreServicesDescription(String storeServicesDescription) {
		this.storeServicesDescription = storeServicesDescription;
	}
	public String getStoreLogo() {
		return storeLogo;
	}
	public void setStoreLogo(String storeLogo) {
		this.storeLogo = storeLogo;
	}
	public String getStoreImage() {
		return storeImage;
	}
	public void setStoreImage(String storeImage) {
		this.storeImage = storeImage;
	}
	public int getIsStoreVerified() {
		return isStoreVerified;
	}
	public void setIsStoreVerified(int isStoreVerified) {
		this.isStoreVerified = isStoreVerified;
	}
	public String getStoreEstablishedDate() {
		return storeEstablishedDate;
	}
	public void setStoreEstablishedDate(String storeEstablishedDate) {
		this.storeEstablishedDate = storeEstablishedDate;
	}
	public String getStoreAddress() {
		return storeAddress;
	}
	public void setStoreAddress(String storeAddress) {
		this.storeAddress = storeAddress;
	}
	public int getStoreAreaId() {
		return storeAreaId;
	}
	public void setStoreAreaId(int storeAreaId) {
		this.storeAreaId = storeAreaId;
	}
	public String getStoreAreaName() {
		return storeAreaName;
	}
	public void setStoreAreaName(String storeAreaName) {
		this.storeAreaName = storeAreaName;
	}
	public int getStoreCityId() {
		return storeCityId;
	}
	public void setStoreCityId(int storeCityId) {
		this.storeCityId = storeCityId;
	}
	public String getStoreCityName() {
		return storeCityName;
	}
	public void setStoreCityName(String storeCityName) {
		this.storeCityName = storeCityName;
	}
	public String getStoreLandmark() {
		return storeLandmark;
	}
	public void setStoreLandmark(String storeLandmark) {
		this.storeLandmark = storeLandmark;
	}
	public String getStoreGeoLocation() {
		return storeGeoLocation;
	}
	public void setStoreGeoLocation(String storeGeoLocation) {
		this.storeGeoLocation = storeGeoLocation;
	}
	public String getStorePin() {
		return storePin;
	}
	public void setStorePin(String storePin) {
		this.storePin = storePin;
	}
	public String getStoreEmail() {
		return storeEmail;
	}
	public void setStoreEmail(String storeEmail) {
		this.storeEmail = storeEmail;
	}
	public String getStoreContact1() {
		return storeContact1;
	}
	public void setStoreContact1(String storeContact1) {
		this.storeContact1 = storeContact1;
	}
	public String getStoreContact2() {
		return storeContact2;
	}
	public void setStoreContact2(String storeContact2) {
		this.storeContact2 = storeContact2;
	}
	public String getStoreWebsite() {
		return storeWebsite;
	}
	public void setStoreWebsite(String storeWebsite) {
		this.storeWebsite = storeWebsite;
	}
	public List<LocalStoreServices> getLocalStoreServicesList() {
		return LocalStoreServicesList;
	}
	public void setLocalStoreServicesList(List<LocalStoreServices> localStoreServicesList) {
		LocalStoreServicesList = localStoreServicesList;
	}
	public List<LocalSocial> getLocalStoreSocialList() {
		return LocalStoreSocialList;
	}
	public void setLocalStoreSocialList(List<LocalSocial> localStoreSocialList) {
		LocalStoreSocialList = localStoreSocialList;
	}
	public int getColumn() {
		return column;
	}
	public void setColumn(int column) {
		this.column = column;
	}
}
