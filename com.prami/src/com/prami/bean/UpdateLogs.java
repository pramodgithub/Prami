package com.prami.bean;

import java.util.List;

public class UpdateLogs {
	private String updateToken, updateTime;
	private int totalLogs, ProductDisabled, PriceUpdated, FailedToDisable, PriceUpdateFailed, PriceSame, ProductUnavailable, ProductUnavailableUpdateFailed, ProductAdded, ReUpdate;
	List<Integer> storeList;
	
	public UpdateLogs(String updateToken, String updateTime, int totalLogs, int productDisabled, int priceUpdated,
			int failedToDisable, int priceUpdateFailed, int priceSame, int productUnavailable,
			int productUnavailableUpdateFailed, int productAdded, List<Integer> storeList, int ReUpdate) {
		super();
		this.updateToken = updateToken;
		this.updateTime = updateTime;
		this.totalLogs = totalLogs;
		this.ProductDisabled = productDisabled;
		this.PriceUpdated = priceUpdated;
		this.FailedToDisable = failedToDisable;
		this.PriceUpdateFailed = priceUpdateFailed;
		this.PriceSame = priceSame;
		this.ProductUnavailable = productUnavailable;
		this.ProductUnavailableUpdateFailed = productUnavailableUpdateFailed;
		this.ProductAdded = productAdded;
		this.ReUpdate = ReUpdate;
		this.storeList = storeList;
	}
	public String getUpdateToken() {
		return updateToken;
	}
	public void setUpdateToken(String updateToken) {
		this.updateToken = updateToken;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public int getTotalLogs() {
		return totalLogs;
	}
	public void setTotalLogs(int totalLogs) {
		this.totalLogs = totalLogs;
	}
	public int getProductDisabled() {
		return ProductDisabled;
	}
	public void setProductDisabled(int productDisabled) {
		ProductDisabled = productDisabled;
	}
	public int getPriceUpdated() {
		return PriceUpdated;
	}
	public void setPriceUpdated(int priceUpdated) {
		PriceUpdated = priceUpdated;
	}
	public int getFailedToDisable() {
		return FailedToDisable;
	}
	public void setFailedToDisable(int failedToDisable) {
		FailedToDisable = failedToDisable;
	}
	public int getPriceUpdateFailed() {
		return PriceUpdateFailed;
	}
	public void setPriceUpdateFailed(int priceUpdateFailed) {
		PriceUpdateFailed = priceUpdateFailed;
	}
	public int getPriceSame() {
		return PriceSame;
	}
	public void setPriceSame(int priceSame) {
		PriceSame = priceSame;
	}
	public int getProductUnavailable() {
		return ProductUnavailable;
	}
	public void setProductUnavailable(int productUnavailable) {
		ProductUnavailable = productUnavailable;
	}
	public int getProductUnavailableUpdateFailed() {
		return ProductUnavailableUpdateFailed;
	}
	public void setProductUnavailableUpdateFailed(int productUnavailableUpdateFailed) {
		ProductUnavailableUpdateFailed = productUnavailableUpdateFailed;
	}
	public int getProductAdded() {
		return ProductAdded;
	}
	public void setProductAdded(int productAdded) {
		ProductAdded = productAdded;
	}
	public int getReUpdate() {
		return ReUpdate;
	}
	public void setReUpdate(int reUpdate) {
		ReUpdate = reUpdate;
	}
	public List<Integer> getStoreList() {
		return storeList;
	}
	public void setStoreList(List<Integer> storeList) {
		this.storeList = storeList;
	}
	
	
}
