package com.prami.bean;

public class Collection {
	int collectionID;
	String collectionName;
	String collectionCategoryName;
	String collectionTitle;
	String collectionDescription;
	String collectionImage;
	int collectionColumns;
	
	public Collection(int collectionID, String collectionName, String collectionCategoryName, String collectionTitle, String collectionDescription,
			String collectionImage, int collectionColumns) {
		super();
		this.collectionID = collectionID;
		this.collectionName = collectionName;
		this.collectionCategoryName = collectionCategoryName;
		this.collectionTitle = collectionTitle;
		this.collectionDescription = collectionDescription;
		this.collectionImage = collectionImage;
		this.collectionColumns = collectionColumns;
	}
	public Collection(int collectionID, String collectionName, String collectionTitle, String collectionDescription,
			String collectionImage) {
		super();
		this.collectionID = collectionID;
		this.collectionName = collectionName;
		this.collectionTitle = collectionTitle;
		this.collectionDescription = collectionDescription;
		this.collectionImage = collectionImage;
	}
	public Collection(int collectionID, String collectionName) {
		super();
		this.collectionID = collectionID;
		this.collectionName = collectionName;
	}
	
	public Collection(int collectionID, String collectionName, String collectionImage) {
		super();
		this.collectionID = collectionID;
		this.collectionName = collectionName;
		this.collectionImage = collectionImage;
	}
	public int getCollectionID() {
		return collectionID;
	}
	public void setCollectionID(int collectionID) {
		this.collectionID = collectionID;
	}
	public String getCollectionName() {
		return collectionName;
	}
	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}
	public String getCollectionCategoryName() {
		return collectionCategoryName;
	}
	public void setCollectionCategoryName(String collectionCategoryName) {
		this.collectionCategoryName = collectionCategoryName;
	}
	public String getCollectionTitle() {
		return collectionTitle;
	}
	public void setCollectionTitle(String collectionTitle) {
		this.collectionTitle = collectionTitle;
	}
	public String getCollectionDescription() {
		return collectionDescription;
	}
	public void setCollectionDescription(String collectionDescription) {
		this.collectionDescription = collectionDescription;
	}
	public String getCollectionImage() {
		return collectionImage;
	}
	public void setCollectionImage(String collectionImage) {
		this.collectionImage = collectionImage;
	}
	public int getCollectionColumns() {
		return collectionColumns;
	}
	public void setCollectionColumns(int collectionColumns) {
		this.collectionColumns = collectionColumns;
	}
}
