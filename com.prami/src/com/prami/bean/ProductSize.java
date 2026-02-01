package com.prami.bean;

public class ProductSize {

	int productSizeId;
	int productId;
	String productSize;
	boolean productSizeStatus;
	
	public ProductSize(int productId, String productSize, boolean productSizeStatus) {
		super();
		this.productId = productId;
		this.productSize = productSize;
		this.productSizeStatus = productSizeStatus;
	}
	public ProductSize(String productSize, boolean productSizeStatus) {
		super();
		this.productSize = productSize;
		this.productSizeStatus = productSizeStatus;
	}
	
	public ProductSize(int productSizeId, int productId, String productSize, boolean productSizeStatus) {
		super();
		this.productSizeId = productSizeId;
		this.productId = productId;
		this.productSize = productSize;
		this.productSizeStatus = productSizeStatus;
	}
	public int getProductSizeId() {
		return productSizeId;
	}
	public void setProductSizeId(int productSizeId) {
		this.productSizeId = productSizeId;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getProductSize() {
		return productSize;
	}
	public void setProductSize(String productSize) {
		this.productSize = productSize;
	}
	public boolean isProductSizeStatus() {
		return productSizeStatus;
	}
	public void setProductSizeStatus(boolean productSizeStatus) {
		this.productSizeStatus = productSizeStatus;
	}
		
		
	
}
