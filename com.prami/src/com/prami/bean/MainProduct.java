package com.prami.bean;

import java.util.List;

public class MainProduct {
	List<SimilarProduct> similarList;
	List<Image> image;
	List<ProductSize> productSizesList;
	int views;
	public MainProduct(List<SimilarProduct> similarList, List<Image> image, List<ProductSize> productSizesList, int views) {
		super();
		this.similarList = similarList;
		this.image = image;
		this.views = views;
		this.productSizesList = productSizesList;
	}
	public List<SimilarProduct> getSimilarList() {
		return similarList;
	}
	public void setSimilarList(List<SimilarProduct> similarList) {
		this.similarList = similarList;
	}
	public List<Image> getImage() {
		return image;
	}
	public void setImage(List<Image> image) {
		this.image = image;
	}
	public int getViews() {
		return views;
	}
	public void setViews(int views) {
		this.views = views;
	}
	public List<ProductSize> getProductSizesList() {
		return productSizesList;
	}
	public void setProductSizesList(List<ProductSize> productSizesList) {
		this.productSizesList = productSizesList;
	}
	
}
