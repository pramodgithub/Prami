package com.prami.bean;


public class ProductSEO {
	private int productID;
	private String productName;
	private String productSEOTitle;
	private String productBrand;
	private float productPrice;
	private String seoDescription;
	private String productColor;
	private String seoProductTypes;
	private String thumbImage;
	private String seoKeywords;
	public ProductSEO(int productID, String productName, String productSEOTitle, String productBrand,
			float productPrice, String seoDescription, String productColor, String seoProductTypes, String thumbImage,
			String seoKeywords) {
		super();
		this.productID = productID;
		this.productName = productName;
		this.productSEOTitle = productSEOTitle;
		this.productBrand = productBrand;
		this.productPrice = productPrice;
		this.seoDescription = seoDescription;
		this.productColor = productColor;
		this.seoProductTypes = seoProductTypes;
		this.thumbImage = thumbImage;
		this.seoKeywords = seoKeywords;
	}
	public int getProductID() {
		return productID;
	}
	public void setProductID(int productID) {
		this.productID = productID;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductSEOTitle() {
		return productSEOTitle;
	}
	public void setProductSEOTitle(String productSEOTitle) {
		this.productSEOTitle = productSEOTitle;
	}
	public String getProductBrand() {
		return productBrand;
	}
	public void setProductBrand(String productBrand) {
		this.productBrand = productBrand;
	}
	public float getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(float productPrice) {
		this.productPrice = productPrice;
	}
	public String getSeoDescription() {
		return seoDescription;
	}
	public void setSeoDescription(String seoDescription) {
		this.seoDescription = seoDescription;
	}
	public String getProductColor() {
		return productColor;
	}
	public void setProductColor(String productColor) {
		this.productColor = productColor;
	}
	public String getSeoProductTypes() {
		return seoProductTypes;
	}
	public void setSeoProductTypes(String seoProductTypes) {
		this.seoProductTypes = seoProductTypes;
	}
	public String getThumbImage() {
		return thumbImage;
	}
	public void setThumbImage(String thumbImage) {
		this.thumbImage = thumbImage;
	}
	public String getSeoKeywords() {
		return seoKeywords;
	}
	public void setSeoKeywords(String seoKeywords) {
		this.seoKeywords = seoKeywords;
	}
}
