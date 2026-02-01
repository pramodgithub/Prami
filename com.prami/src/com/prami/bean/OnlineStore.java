package com.prami.bean;



public class OnlineStore {
 private int storeId;
 private String storeName;
 private int storePoints;
 private String storeDescription;
 private String storeProperty;
 private String storePropertyDesc;
 private int storeProprtyPoints;
 private String storeLogo;
 private String productCount;
 
 
 public OnlineStore() {
		super();
} 
public OnlineStore(int storeId, String storeName, int storePoints, String storeDescription) {
	super();
	this.storeId = storeId;
	this.storeName = storeName;
	this.storePoints = storePoints;
	this.storeDescription = storeDescription;
}

public OnlineStore(String storeProperty, int storeProprtyPoints, String storePropertyDesc ) {
	super();
	this.storeProperty = storeProperty;
	this.storePropertyDesc = storePropertyDesc;
	this.storeProprtyPoints = storeProprtyPoints;
}
public OnlineStore(int storeId, String storeName, String storeLogo, String storeDescription) {
	super();
	this.storeId = storeId;
	this.storeName = storeName;
	this.storeLogo = storeLogo;
	this.storeDescription = storeDescription;
}
public OnlineStore(int storeId, String storeName, String storeLogo) {
	super();
	this.storeId = storeId;
	this.storeName = storeName;
	this.storeLogo = storeLogo;
	
}
public String getStoreLogo() {
	return storeLogo;
}
public void setStoreLogo(String storeLogo) {
	this.storeLogo = storeLogo;
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
public int getStorePoints() {
	return storePoints;
}
public void setStorePoints(int storePoints) {
	this.storePoints = storePoints;
}
public String getStoreDescription() {
	return storeDescription;
}
public void setStoreDescription(String storeDescription) {
	this.storeDescription = storeDescription;
}

public String getStoreProperty() {
	return storeProperty;
}

public void setStoreProperty(String storeProperty) {
	this.storeProperty = storeProperty;
}

public String getStorePropertyDesc() {
	return storePropertyDesc;
}

public void setStorePropertyDesc(String storePropertyDesc) {
	this.storePropertyDesc = storePropertyDesc;
}

public int getStoreProprtyPoints() {
	return storeProprtyPoints;
}

public void setStoreProprtyPoints(int storeProprtyPoints) {
	this.storeProprtyPoints = storeProprtyPoints;
}
public String getProductCount() {
	return productCount;
}
public void setProductCount(String productCount) {
	this.productCount = productCount;
}
}
