package com.prami.bean;

public class UpdateLog {
	int productID;
	long updateInfo;
	int updateType;
	String updateTime;
	String updateToken;
	StoreSale storeSaleObj;
	
	public UpdateLog(int productID, long updateInfo, int updateType, String updateTime) {
		super();
		this.productID = productID;
		this.updateInfo = updateInfo;
		this.updateType = updateType;
		this.updateTime = updateTime;
	}
	public UpdateLog(int productID, long updateInfo, int updateType, String updateTime, String updateToken) {
		super();
		this.productID = productID;
		this.updateInfo = updateInfo;
		this.updateType = updateType;
		this.updateTime = updateTime;
		this.updateToken = updateToken;
	}
	public UpdateLog(int productID, long updateInfo, int updateType, String updateTime, String updateToken,
			StoreSale storeSaleObj) {
		super();
		this.productID = productID;
		this.updateInfo = updateInfo;
		this.updateType = updateType;
		this.updateTime = updateTime;
		this.updateToken = updateToken;
		this.storeSaleObj = storeSaleObj;
	}
	public int getProductID() {
		return productID;
	}
	public void setProductID(int productID) {
		this.productID = productID;
	}
	public long getUpdateInfo() {
		return updateInfo;
	}
	public void setUpdateInfo(long updateInfo) {
		this.updateInfo = updateInfo;
	}
	public int getUpdateType() {
		return updateType;
	}
	public void setUpdateType(int updateType) {
		this.updateType = updateType;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getUpdateToken() {
		return updateToken;
	}
	public void setUpdateToken(String updateToken) {
		this.updateToken = updateToken;
	}
	public StoreSale getStoreSaleObj() {
		return storeSaleObj;
	}
	public void setStoreSaleObj(StoreSale storeSaleObj) {
		this.storeSaleObj = storeSaleObj;
	}
	
}
