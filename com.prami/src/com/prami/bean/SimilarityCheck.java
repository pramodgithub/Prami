package com.prami.bean;

public class SimilarityCheck {
	int collectionId;
	String collectionName;
	int toCheckCount;
	public SimilarityCheck(int collectionId, String collectionName, int toCheckCount) {
		super();
		this.collectionId = collectionId;
		this.collectionName = collectionName;
		this.toCheckCount = toCheckCount;
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
	public int getToCheckCount() {
		return toCheckCount;
	}
	public void setToCheckCount(int toCheckCount) {
		this.toCheckCount = toCheckCount;
	}
	
}
