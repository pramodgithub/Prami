package com.prami.bean;

public class UpdateStore {
	private int storeId;
	private String latestToken;
	private String updateTime;
	private int updateState;
	public UpdateStore(int storeId, String latestToken, String updateTime, int updateState) {
		super();
		this.storeId = storeId;
		this.latestToken = latestToken;
		this.updateTime = updateTime;
		this.updateState = updateState;
	}
	public int getStoreId() {
		return storeId;
	}
	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}
	public String getLatestToken() {
		return latestToken;
	}
	public void setLatestToken(String latestToken) {
		this.latestToken = latestToken;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public int getUpdateState() {
		return updateState;
	}
	public void setUpdateState(int updateState) {
		this.updateState = updateState;
	}
	
	
}
