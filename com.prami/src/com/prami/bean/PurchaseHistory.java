package com.prami.bean;

public class PurchaseHistory {

	private String productName;
	private String status;
	private String purchaseDate;
	public PurchaseHistory(String productName, String status, String purchaseDate) {
		super();
		this.productName = productName;
		this.status = status;
		this.purchaseDate = purchaseDate;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPurchaseDate() {
		return purchaseDate;
	}
	public void setPurchaseDate(String purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	
	
	
	
}
