package com.prami.bean;

public class Category {

	int categoryID;
	String categoryName;
	String categoryImage;
	int categoryPattern;
	int isSelected;
	public Category() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Category(int categoryID, String categoryName, String categoryImage) {
		super();
		this.categoryID = categoryID;
		this.categoryName = categoryName;
		this.categoryImage = categoryImage;
	}
	
	public Category(int categoryID, String categoryName, String categoryImage, int categoryPattern) {
		super();
		this.categoryID = categoryID;
		this.categoryName = categoryName;
		this.categoryImage = categoryImage;
		this.categoryPattern = categoryPattern;
	}
	public Category(int categoryID, String categoryName, int isSelected) {
		super();
		this.categoryID = categoryID;
		this.categoryName = categoryName;
		this.isSelected = isSelected;
	}
	public Category(int categoryID, String categoryName, int categoryPattern, int isSelected) {
		super();
		this.categoryID = categoryID;
		this.categoryName = categoryName;
		this.categoryPattern = categoryPattern;
		this.isSelected = isSelected;
	}
	public int getCategoryID() {
		return categoryID;
	}
	public void setCategoryID(int categoryID) {
		this.categoryID = categoryID;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getCategoryImage() {
		return categoryImage;
	}
	public void setCategoryImage(String categoryImage) {
		this.categoryImage = categoryImage;
	}
	public int getCategoryPattern() {
		return categoryPattern;
	}
	public void setCategoryPattern(int categoryPattern) {
		this.categoryPattern = categoryPattern;
	}
	public int getIsSelected() {
		return isSelected;
	}
	public void setIsSelected(int isSelected) {
		this.isSelected = isSelected;
	}
	
	
}
