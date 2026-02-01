package com.prami.bean;

public class Image {
	int imageID;
	int productID;
	String imageURL;
	int imageType;
	String thumbImage;
	String mainImage;
	public Image() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Image(int imageID, int productID, String imageURL, int imageType) {
		super();
		this.imageID = imageID;
		this.productID = productID;
		this.imageURL = imageURL;
		this.imageType = imageType;
	}
	public Image(int imageID, int productID, String thumbImage, String mainImage) {
		super();
		this.imageID = imageID;
		this.productID = productID;
		this.thumbImage = thumbImage;
		this.mainImage = mainImage;
	}
	public int getImageID() {
		return imageID;
	}
	public void setImageID(int imageID) {
		this.imageID = imageID;
	}
	public int getProductID() {
		return productID;
	}
	public void setProductID(int productID) {
		this.productID = productID;
	}
	public String getImageURL() {
		return imageURL;
	}
	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}
	public int getImageType() {
		return imageType;
	}
	public void setImageType(int imageType) {
		this.imageType = imageType;
	}
	public String getThumbImage() {
		return thumbImage;
	}
	public void setThumbImage(String thumbImage) {
		this.thumbImage = thumbImage;
	}
	public String getMainImage() {
		return mainImage;
	}
	public void setMainImage(String mainImage) {
		this.mainImage = mainImage;
	}
	
	
}
