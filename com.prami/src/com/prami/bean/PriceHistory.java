package com.prami.bean;

public class PriceHistory {
	int updateInfo;
	String logTypeName;
	String readableDate;
	String updateType;
	
	public PriceHistory() {
		super();
	}
	public PriceHistory(int updateInfo, String logTypeName, String readableDate, String updateType) {
		super();
		this.updateInfo = updateInfo;
		this.logTypeName = logTypeName;
		this.readableDate = readableDate;
		this.updateType = updateType;
	}
	public int getUpdateInfo() {
		return updateInfo;
	}
	public void setUpdateInfo(int updateInfo) {
		this.updateInfo = updateInfo;
	}
	public String getLogTypeName() {
		return logTypeName;
	}
	public void setLogTypeName(String logTypeName) {
		this.logTypeName = logTypeName;
	}
	public String getReadableDate() {
		return readableDate;
	}
	public void setReadableDate(String readableDate) {
		this.readableDate = readableDate;
	}
	public String getUpdateType() {
		return updateType;
	}
	public void setUpdateType(String updateType) {
		this.updateType = updateType;
	}
	
	
}
