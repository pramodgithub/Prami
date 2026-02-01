package com.prami.bean;

import java.util.List;

public class UpdateProduct {
	int productId;
	String productURL;
	Float productPrice;
	int productStatus;
	int storeId;
	String storeName;
	List<ProductSize> productSizesList;
	
	public UpdateProduct(int productId, String productURL, Float productPrice) {
		super();
		this.productId = productId;
		this.productURL = productURL;
		this.productPrice = productPrice;
	}
	public UpdateProduct(int productId, String productURL, Float productPrice, int productStatus) {
		super();
		this.productId = productId;
		this.productURL = productURL;
		this.productPrice = productPrice;
		this.productStatus = productStatus;
	}
	public UpdateProduct(int productId, String productURL, Float productPrice, int productStatus, List<ProductSize> productSizesList) {
		super();
		this.productId = productId;
		this.productURL = productURL;
		this.productPrice = productPrice;
		this.productStatus = productStatus;
		this.productSizesList = productSizesList;
	}
	public UpdateProduct(int productId, String productURL, int productStatus, Float productPrice, int storeId, String storeName, List<ProductSize> productSizesList) {
		super();
		this.productId = productId;
		this.productURL = productURL;
		this.productStatus = productStatus;
		this.productPrice = productPrice;
		this.storeId = storeId;
		this.storeName = storeName;
		this.productSizesList = productSizesList;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getProductURL() {
		return productURL;
	}
	public void setProductURL(String productURL) {
		this.productURL = productURL;
	}
	public Float getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(Float productPrice) {
		this.productPrice = productPrice;
	}
	public int getProductStatus() {
		return productStatus;
	}
	public void setProductStatus(int productStatus) {
		this.productStatus = productStatus;
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
	public List<ProductSize> getProductSizesList() {
		return productSizesList;
	}
	public void setProductSizesList(List<ProductSize> productSizesList) {
		this.productSizesList = productSizesList;
	}
	
}
