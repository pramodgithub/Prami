package com.prami.bean;

public class CollectionCount {
	int collectionId;
	String collectionName;
	int collectionCatId;
	String collectionCatName;
	int collectionCount;
	int collecitonDisabledCount;
	public CollectionCount(int collectionId, String collectionName, int collectionCatId, String collectionCatName,
			int collectionCount, int collecitonDisabledCount) {
		super();
		this.collectionId = collectionId;
		this.collectionName = collectionName;
		this.collectionCatId = collectionCatId;
		this.collectionCatName = collectionCatName;
		this.collectionCount = collectionCount;
		this.collecitonDisabledCount = collecitonDisabledCount;
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
	public int getCollectionCatId() {
		return collectionCatId;
	}
	public void setCollectionCatId(int collectionCatId) {
		this.collectionCatId = collectionCatId;
	}
	public String getCollectionCatName() {
		return collectionCatName;
	}
	public void setCollectionCatName(String collectionCatName) {
		this.collectionCatName = collectionCatName;
	}
	public int getCollectionCount() {
		return collectionCount;
	}
	public void setCollectionCount(int collectionCount) {
		this.collectionCount = collectionCount;
	}
	public int getCollecitonDisabledCount() {
		return collecitonDisabledCount;
	}
	public void setCollecitonDisabledCount(int collecitonDisabledCount) {
		this.collecitonDisabledCount = collecitonDisabledCount;
	}
	
	

}
