package com.prami.bean;

public class CollectionSEO {
	private int collectionId;
	private String collectionName, subcatName, subcatTitle, subcatDescription, subcatImage, seoKeywords;

	public CollectionSEO(String collectionName, String subcatName, String subcatTitle, String subcatDescription, String subcatImage,
			String seoKeywords) {
		super();
		this.collectionName = collectionName;
		this.subcatName = subcatName;
		this.subcatTitle = subcatTitle;
		this.subcatDescription = subcatDescription;
		this.subcatImage = subcatImage;
		this.seoKeywords = seoKeywords;
		
	}
	public CollectionSEO(int collectionId, String collectionName, String subcatName, String subcatTitle, String subcatDescription, String subcatImage,
			String seoKeywords) {
		super();
		this.collectionId = collectionId;
		this.collectionName = collectionName;
		this.subcatName = subcatName;
		this.subcatTitle = subcatTitle;
		this.subcatDescription = subcatDescription;
		this.subcatImage = subcatImage;
		this.seoKeywords = seoKeywords;
		
	}

	public int getCollectionId() {
		return collectionId;
	}

	public void setCollectionId(int collectionId) {
		this.collectionId = collectionId;
	}

	public String getCollectionName() {
		return collectionName;
	}

	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}

	public String getSubcatName() {
		return subcatName;
	}

	public void setSubcatName(String subcatName) {
		this.subcatName = subcatName;
	}

	public String getSubcatTitle() {
		return subcatTitle;
	}

	public void setSubcatTitle(String subcatTitle) {
		this.subcatTitle = subcatTitle;
	}

	public String getSubcatDescription() {
		return subcatDescription;
	}

	public void setSubcatDescription(String subcatDescription) {
		this.subcatDescription = subcatDescription;
	}

	public String getSubcatImage() {
		return subcatImage;
	}

	public void setSubcatImage(String subcatImage) {
		this.subcatImage = subcatImage;
	}

	public String getSeoKeywords() {
		return seoKeywords;
	}

	public void setSeoKeywords(String seoKeywords) {
		this.seoKeywords = seoKeywords;
	}

}