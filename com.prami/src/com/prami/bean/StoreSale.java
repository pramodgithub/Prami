package com.prami.bean;

public class StoreSale {
	int infoId;
	int storeId;
	String storeSaleName;
	String storeSaleDescription;
	String saleTime;
	public StoreSale(int infoId, int storeId, String storeSaleName, String storeSaleDescription, String saleTime) {
		super();
		this.infoId = infoId;
		this.storeId = storeId;
		this.storeSaleName = storeSaleName;
		this.storeSaleDescription = storeSaleDescription;
		this.saleTime = saleTime;
	}
	public int getInfoId() {
		return infoId;
	}
	public void setInfoId(int infoId) {
		this.infoId = infoId;
	}
	public int getStoreId() {
		return storeId;
	}
	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}
	public String getStoreSaleName() {
		return storeSaleName;
	}
	public void setStoreSaleName(String storeSaleName) {
		this.storeSaleName = storeSaleName;
	}
	public String getStoreSaleDescription() {
		return storeSaleDescription;
	}
	public void setStoreSaleDescription(String storeSaleDescription) {
		this.storeSaleDescription = storeSaleDescription;
	}
	public String getSaleTime() {
		return saleTime;
	}
	public void setSaleTime(String saleTime) {
		this.saleTime = saleTime;
	}
	
}
