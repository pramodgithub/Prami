package com.prami.bean;

public class ProductUpdateLog {
	int updateID, productID, storeID, productStatus,updateType, storeSaleInfoId;
	double productPrice, updateInfo;
	String orginalURL, productDate, productModificationDate;
	
	public ProductUpdateLog(int updateID, int productID, int storeID, int productStatus, int updateType,
			int storeSaleInfoId, double productPrice, double updateInfo, String orginalURL, String productDate,
			String productModificationDate) {
		super();
		this.updateID = updateID;
		this.productID = productID;
		this.storeID = storeID;
		this.productStatus = productStatus;
		this.updateType = updateType;
		this.storeSaleInfoId = storeSaleInfoId;
		this.productPrice = productPrice;
		this.updateInfo = updateInfo;
		this.orginalURL = orginalURL;
		this.productDate = productDate;
		this.productModificationDate = productModificationDate;
	}
	
	public int getUpdateID() {
		return updateID;
	}
	public void setUpdateID(int updateID) {
		this.updateID = updateID;
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
	public int getProductStatus() {
		return productStatus;
	}
	public void setProductStatus(int productStatus) {
		this.productStatus = productStatus;
	}
	public int getUpdateType() {
		return updateType;
	}
	public void setUpdateType(int updateType) {
		this.updateType = updateType;
	}
	public int getStoreSaleInfoId() {
		return storeSaleInfoId;
	}
	public void setStoreSaleInfoId(int storeSaleInfoId) {
		this.storeSaleInfoId = storeSaleInfoId;
	}
	public double getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}
	public double getUpdateInfo() {
		return updateInfo;
	}
	public void setUpdateInfo(double updateInfo) {
		this.updateInfo = updateInfo;
	}
	public String getOrginalURL() {
		return orginalURL;
	}
	public void setOrginalURL(String orginalURL) {
		this.orginalURL = orginalURL;
	}
	public String getProductDate() {
		return productDate;
	}
	public void setProductDate(String productDate) {
		this.productDate = productDate;
	}
	public String getProductModificationDate() {
		return productModificationDate;
	}
	public void setProductModificationDate(String productModificationDate) {
		this.productModificationDate = productModificationDate;
	}
}
