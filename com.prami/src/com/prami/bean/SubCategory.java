package com.prami.bean;

import java.util.List;

public class SubCategory {
	private int categoryId;
	private String name;
	private String title;
	private String description;
	String image;
	private int subCatID;
	private String subCatName;
	private String subCatImage;
	private List<Product> products; 

	public SubCategory() {
		super();
	}
	
	public SubCategory(int categoryId, String name, String title, String description, String image) {
		super();
		this.categoryId = categoryId;
		this.name = name;
		this.title = title;
		this.description = description;
		this.image = image;
	}
	public SubCategory(int subCatID, String subCatName, String subCatImage) {
		super();
		this.subCatID = subCatID;
		this.subCatName = subCatName;
		this.subCatImage = subCatImage;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public int getSubCatID() {
		return subCatID;
	}
	public void setSubCatID(int subCatID) {
		this.subCatID = subCatID;
	}
	public String getSubCatName() {
		return subCatName;
	}
	public void setSubCatName(String subCatName) {
		this.subCatName = subCatName;
	}
	public String getSubCatImage() {
		return subCatImage;
	}
	public void setSubCatImage(String subCatImage) {
		this.subCatImage = subCatImage;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}
}
