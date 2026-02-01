package com.prami.bean;

public class ProductType {
	int productTypeID;
	String porductTypeName;
	String productTypeIcon;
	String productTypeImage;
	int productTypeStatus;
	String subCategoryname;
	
	public ProductType() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ProductType(int productTypeID, String porductTypeName, String productTypeIcon, String productTypeImage) {
		super();
		this.productTypeID = productTypeID;
		this.porductTypeName = porductTypeName;
		this.productTypeIcon = productTypeIcon;
		this.productTypeImage = productTypeImage;
	}
	public ProductType(int productTypeID, String porductTypeName, String subCategoryname, String productTypeImage,
			int productTypeStatus) {
		super();
		this.productTypeID = productTypeID;
		this.porductTypeName = porductTypeName;
		this.subCategoryname = subCategoryname;
		this.productTypeImage = productTypeImage;
		this.productTypeStatus = productTypeStatus;
	}
	public int getProductTypeID() {
		return productTypeID;
	}
	public void setProductTypeID(int productTypeID) {
		this.productTypeID = productTypeID;
	}
	public String getPorductTypeName() {
		return porductTypeName;
	}
	public void setPorductTypeName(String porductTypeName) {
		this.porductTypeName = porductTypeName;
	}
	public String getProductTypeIcon() {
		return productTypeIcon;
	}
	public void setProductTypeIcon(String productTypeIcon) {
		this.productTypeIcon = productTypeIcon;
	}
	public String getProductTypeImage() {
		return productTypeImage;
	}
	public void setProductTypeImage(String productTypeImage) {
		this.productTypeImage = productTypeImage;
	}
	public int getProductTypeStatus() {
		return productTypeStatus;
	}
	public void setProductTypeStatus(int productTypeStatus) {
		this.productTypeStatus = productTypeStatus;
	}
	public String getsubCategoryname() {
		return subCategoryname;
	}
	public void setsubCategoryname(String subCategoryname) {
		this.subCategoryname = subCategoryname;
	}
		
}
