package com.prami.bean;

public class CollectionHistory {
	private int idcollectionHistory;
	private String token;
	private String collectionUrl;
	private int storeId;
	private int readCount;
	private int catId;
	private int subCatId;
	private String byUser;
	private String addedTime;
	private int status;
	
	
	public CollectionHistory() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CollectionHistory(int idcollectionHistory, String token, String collectionUrl, int storeId, int readCount,
			int catId, int subCatId, String byUser, String addedTime, int status) {
		super();
		this.idcollectionHistory = idcollectionHistory;
		this.token = token;
		this.collectionUrl = collectionUrl;
		this.storeId = storeId;
		this.readCount = readCount;
		this.catId = catId;
		this.subCatId = subCatId;
		this.byUser = byUser;
		this.addedTime = addedTime;
		this.status = status;
	}

	public int getIdcollectionHistory() {
		return idcollectionHistory;
	}

	public void setIdcollectionHistory(int idcollectionHistory) {
		this.idcollectionHistory = idcollectionHistory;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getCollectionUrl() {
		return collectionUrl;
	}

	public void setCollectionUrl(String collectionUrl) {
		this.collectionUrl = collectionUrl;
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public int getReadCount() {
		return readCount;
	}

	public void setReadCount(int readCount) {
		this.readCount = readCount;
	}

	public int getCatId() {
		return catId;
	}

	public void setCatId(int catId) {
		this.catId = catId;
	}

	public int getSubCatId() {
		return subCatId;
	}

	public void setSubCatId(int subCatId) {
		this.subCatId = subCatId;
	}

	public String getByUser() {
		return byUser;
	}

	public void setByUser(String byUser) {
		this.byUser = byUser;
	}

	public String getAddedTime() {
		return addedTime;
	}

	public void setAddedTime(String addedTime) {
		this.addedTime = addedTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	
	
}
