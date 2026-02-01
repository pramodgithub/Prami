package com.prami.bean;

import java.util.List;

public class Product {
	int intProductID;
	String strProductName;
	String strProductBrand;
	String strProductURL;
	float fltProductPrice;
	String strProductDescription;
	String strProductColor;
	int intCatID;
	int intSubCatID;
	int intProductTypeID;
	List<String> strProductType;
	int inrStoreID;
	String strStoreName;
	String storeDescription;
	String storeLogo;
	int intProdWebType;
	long longProductAddedDate;
	long longProductmodificationDate;
	String strProductUpdatedBy;
	int intProductStatus;
	List<Image> image;
	String productSizes;
	int views;
	private String strOriginalURL;
	List<SimilarProduct> similarList;
	Affiliate affiliate;
	public Product() {
		super();
	}

	
	public Product(List<Image> image, int views, List<SimilarProduct> similarList) {
		super();
		this.image = image;
		this.views = views;
		this.similarList = similarList;
	}


	public Product(String strProductName, String strProductBrand, String strProductURL, float fltProductPrice,
			String strProductDescription, String strProductColor, int intCatID, int intSubCatID, int intProductTypeID,
			int inrStoreID, int intProdWebType, long longProductAddedDate, long longProductmodificationDate,
			String strProductUpdatedBy, int intProductStatus) {
		super();
		this.strProductName = strProductName;
		this.strProductBrand = strProductBrand;
		this.strProductURL = strProductURL;
		this.fltProductPrice = fltProductPrice;
		this.strProductDescription = strProductDescription;
		this.strProductColor = strProductColor;
		this.intCatID = intCatID;
		this.intSubCatID = intSubCatID;
		this.intProductTypeID = intProductTypeID;
		this.inrStoreID = inrStoreID;
		this.intProdWebType = intProdWebType;
		this.longProductAddedDate = longProductAddedDate;
		this.longProductmodificationDate = longProductmodificationDate;
		this.strProductUpdatedBy = strProductUpdatedBy;
		this.intProductStatus = intProductStatus;
	}
	
	public Product(int intProductID, String strProductName, String strProductBrand, String strProductURL,
			float fltProductPrice, String strProductDescription, String strProductColor, List<Image> image) {
		super();
		this.intProductID = intProductID;
		this.strProductName = strProductName;
		this.strProductBrand = strProductBrand;
		this.strProductURL = strProductURL;
		this.fltProductPrice = fltProductPrice;
		this.strProductDescription = strProductDescription;
		this.strProductColor = strProductColor;
		this.image = image;
	}
	
	public Product(int intProductID, String strProductName, String strProductBrand, String strProductURL,
			float fltProductPrice, String strProductDescription, String strProductColor, List<Image> image, Affiliate affiliate) {
		super();
		this.intProductID = intProductID;
		this.strProductName = strProductName;
		this.strProductBrand = strProductBrand;
		this.strProductURL = strProductURL;
		this.fltProductPrice = fltProductPrice;
		this.strProductDescription = strProductDescription;
		this.strProductColor = strProductColor;
		this.image = image;
		this.affiliate = affiliate;
	}
	
	public Product(int intProductID, String strProductName, String strProductBrand, String strProductURL,
			float fltProductPrice, String strProductDescription, String strProductColor, int intProductStatus) {
		super();
		this.intProductID = intProductID;
		this.strProductName = strProductName;
		this.strProductBrand = strProductBrand;
		this.strProductURL = strProductURL;
		this.fltProductPrice = fltProductPrice;
		this.strProductDescription = strProductDescription;
		this.strProductColor = strProductColor;
		this.intProductStatus = intProductStatus;
	}
	public Product(int intProductID, String strProductName, String strProductBrand, String strProductURL,
			float fltProductPrice, String strProductDescription, String strProductColor,String strStoreName,String storeDescription,int intCatID, List<Image> image,int views) {
		super();
		this.intProductID = intProductID;
		this.strProductName = strProductName;
		this.strProductBrand = strProductBrand;
		this.strProductURL = strProductURL;
		this.fltProductPrice = fltProductPrice;
		this.strProductDescription = strProductDescription;
		this.strProductColor = strProductColor;
		this.strStoreName = strStoreName;
		this.storeDescription = storeDescription;
		this.intCatID = intCatID;
		this.image = image;
		this.views = views;
		
	}
	public Product(String strProductName, String strProductBrand, String strProductURL, String strOriginalURL, float fltProductPrice,
			String strProductDescription, String strProductColor, int intCatID, int intSubCatID, int inrStoreID,
			int intProdWebType, long longProductAddedDate, long longProductmodificationDate, String strProductUpdatedBy,
			int intProductStatus, List<String> strProductType, String productSizes) {
		super();
		this.strProductName = strProductName;
		this.strProductBrand = strProductBrand;
		this.strProductURL = strProductURL;
		this.strOriginalURL = strOriginalURL;
		this.fltProductPrice = fltProductPrice;
		this.strProductDescription = strProductDescription;
		this.strProductColor = strProductColor;
		this.intCatID = intCatID;
		this.intSubCatID = intSubCatID;
		this.inrStoreID = inrStoreID;
		this.intProdWebType = intProdWebType;
		this.longProductAddedDate = longProductAddedDate;
		this.longProductmodificationDate = longProductmodificationDate;
		this.strProductUpdatedBy = strProductUpdatedBy;
		this.intProductStatus = intProductStatus;
		this.strProductType = strProductType;
		this.productSizes = productSizes;
	}	
	public Product(int intProductID, String strProductURL, String strStoreName, String storeLogo, Affiliate affiliate) {
		this.intProductID = intProductID;
		this.strProductURL = strProductURL;
		this.strStoreName = strStoreName;
		this.storeLogo = storeLogo;
		this.affiliate = affiliate;
	}


	public List<Image> getImage() {
		return image;
	}
	public void setImage(List<Image> image) {
		this.image = image;
	}

	public int getIntProductID() {
		return intProductID;
	}

	public void setIntProductID(int intProductID) {
		this.intProductID = intProductID;
	}

	public String getStrProductName() {
		return strProductName;
	}
	public void setStrProductName(String strProductName) {
		this.strProductName = strProductName;
	}
	public String getStrProductBrand() {
		return strProductBrand;
	}
	public void setStrProductBrand(String strProductBrand) {
		this.strProductBrand = strProductBrand;
	}
	public String getStrProductURL() {
		return strProductURL;
	}
	public void setStrProductURL(String strProductURL) {
		this.strProductURL = strProductURL;
	}
	public float getFltProductPrice() {
		return fltProductPrice;
	}
	public void setFltProductPrice(float fltProductPrice) {
		this.fltProductPrice = fltProductPrice;
	}
	public String getStrProductDescription() {
		return strProductDescription;
	}
	public void setStrProductDescription(String strProductDescription) {
		this.strProductDescription = strProductDescription;
	}
	public String getStrProductColor() {
		return strProductColor;
	}
	public void setStrProductColor(String strProductColor) {
		this.strProductColor = strProductColor;
	}
	public int getIntProductStatus() {
		return intProductStatus;
	}
	public void setIntProductStatus(int intProductStatus) {
		this.intProductStatus = intProductStatus;
	}
	public int getIntCatID() {
		return intCatID;
	}
	public void setIntCatID(int intCatID) {
		this.intCatID = intCatID;
	}
	public int getIntSubCatID() {
		return intSubCatID;
	}
	public void setIntSubCatID(int intSubCatID) {
		this.intSubCatID = intSubCatID;
	}
	public int getIntProductTypeID() {
		return intProductTypeID;
	}
	public void setIntProductTypeID(int intProductTypeID) {
		this.intProductTypeID = intProductTypeID;
	}
	public List<String> getStrProductType() {
		return strProductType;
	}

	public void setStrProductType(List<String> strProductType) {
		this.strProductType = strProductType;
	}

	public int getIntStoreID() {
		return inrStoreID;
	}
	public void setInrStoreID(int inrStoreID) {
		this.inrStoreID = inrStoreID;
	}
	public String getStrStoreName() {
		return strStoreName;
	}

	public void setStrStoreName(String strStoreName) {
		this.strStoreName = strStoreName;
	}

	public String getStoreDescription() {
		return storeDescription;
	}

	public void setStoreDescription(String storeDescription) {
		this.storeDescription = storeDescription;
	}
	public String getStoreLogo() {
		return storeLogo;
	}
	public void setStoreLogo(String storeLogo) {
		this.storeLogo = storeLogo;
	}
	public int getInrStoreID() {
		return inrStoreID;
	}
	public int getIntProdWebType() {
		return intProdWebType;
	}
	public void setIntProdWebType(int intProdWebType) {
		this.intProdWebType = intProdWebType;
	}
	public long getLongProductAddedDate() {
		return longProductAddedDate;
	}
	public void setLongProductAddedDate(long longProductAddedDate) {
		this.longProductAddedDate = longProductAddedDate;
	}
	public long getLongProductmodificationDate() {
		return longProductmodificationDate;
	}
	public void setLongProductmodificationDate(long longProductmodificationDate) {
		this.longProductmodificationDate = longProductmodificationDate;
	}
	public String getStrProductUpdatedBy() {
		return strProductUpdatedBy;
	}
	public void setStrProductUpdatedBy(String strProductUpdatedBy) {
		this.strProductUpdatedBy = strProductUpdatedBy;
	}
	public int getViews() {
		return views;
	}
	public void setViews(int views) {
		this.views = views;
	}
	public String getStrOriginalURL() {
		return strOriginalURL;
	}
	public void setStrOriginalURL(String strOriginalURL) {
		this.strOriginalURL = strOriginalURL;
	}
	public List<SimilarProduct> getSimilarList() {
		return similarList;
	}
	public void setSimilarList(List<SimilarProduct> similarList) {
		this.similarList = similarList;
	}
	public String getProductSizes() {
		return productSizes;
	}
	public void setProductSizes(String productSizes) {
		this.productSizes = productSizes;
	}


	public Affiliate getAffiliate() {
		return affiliate;
	}


	public void setAffiliate(Affiliate affiliate) {
		this.affiliate = affiliate;
	}
	
}
