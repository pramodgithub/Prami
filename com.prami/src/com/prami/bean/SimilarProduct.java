package com.prami.bean;

import java.util.List;

public class SimilarProduct {
	int productID;
	int storeID;
	int similarityCheck;
	String productName;
	String productBrand;
	String productURL;
	String productImageURL;
	float productPrice;
	String productColor;
	String storeName;
	String storeDescription;
	List<StoreProperties> storePropertyList;
	Affiliate affiliate;
	public SimilarProduct(int productID, int storeID, String productName, String productBrand, String productURL,
			float productPrice, String productColor, String storeName, String storeDescription,
			Affiliate affiliate) {
		super();
		this.productID = productID;
		this.storeID = storeID;
		this.productName = productName;
		this.productBrand = productBrand;
		this.productURL = productURL;
		this.productPrice = productPrice;
		this.productColor = productColor;
		this.storeName = storeName;
		this.storeDescription = storeDescription;
		this.affiliate = affiliate;
	}
	
	public SimilarProduct(int productID, int storeID, String productName, String productImageURL, float productPrice,int similarityCheck) {
		super();
		this.productID = productID;
		this.storeID = storeID;
		this.productName = productName;
		this.productImageURL = productImageURL;
		this.productPrice = productPrice;
		this.similarityCheck = similarityCheck;
	}

	public int getProductID() {
		return productID;
	}
	public void setProductID(int productID) {
		this.productID = productID;
	}
	public int getStoreID() {
		return storeID;
	}
	public void setStoreID(int storeID) {
		this.storeID = storeID;
	}
	public int getSimilarityCheck() {
		return similarityCheck;
	}

	public void setSimilarityCheck(int similarityCheck) {
		this.similarityCheck = similarityCheck;
	}

	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductBrand() {
		return productBrand;
	}
	public void setProductBrand(String productBrand) {
		this.productBrand = productBrand;
	}
	public String getProductURL() {
		return productURL;
	}
	public void setProductURL(String productURL) {
		this.productURL = productURL;
	}
	public String getProductImageURL() {
		return productImageURL;
	}
	public void setProductImageURL(String productImageURL) {
		this.productImageURL = productImageURL;
	}
	public float getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(float productPrice) {
		this.productPrice = productPrice;
	}
	public String getProductColor() {
		return productColor;
	}
	public void setProductColor(String productColor) {
		this.productColor = productColor;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getStoreDescription() {
		return storeDescription;
	}
	public void setStoreDescription(String storeDescription) {
		this.storeDescription = storeDescription;
	}
	public List<StoreProperties> getStorePropertyList() {
		return storePropertyList;
	}
	public void setStorePropertyList(List<StoreProperties> storePropertyList) {
		this.storePropertyList = storePropertyList;
	}

	public Affiliate getAffiliate() {
		return affiliate;
	}

	public void setAffiliate(Affiliate affiliate) {
		this.affiliate = affiliate;
	}
	
}
