package com.prami.bean;

public class Structure {
	int structureId;
	int sequence;
	int subCatId;
	int pageColumn;
	int prodCount;
	int prodColumns;
	String font;
	SubCategory objSubCategory;
		
	public Structure(int structureId, int sequence, int subCatId, int pageColumn, int prodCount, int prodColumns,
			 SubCategory objSubCategory) {
		super();
		this.structureId = structureId;
		this.sequence = sequence;
		this.subCatId = subCatId;
		this.pageColumn = pageColumn;
		this.prodCount = prodCount;
		this.prodColumns = prodColumns;
		this.objSubCategory = objSubCategory;
	}
	
	public Structure(int pageColumn, int prodColumns, int prodCount) {
		super();
		this.pageColumn = pageColumn;
		this.prodCount = prodCount;
		this.prodColumns = prodColumns;
	}

	public int getStructureId() {
		return structureId;
	}
	public void setStructureId(int structureId) {
		this.structureId = structureId;
	}
	public int getSequence() {
		return sequence;
	}
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	public int getSubCatId() {
		return subCatId;
	}
	public void setSubCatId(int subCatId) {
		this.subCatId = subCatId;
	}
	public int getPageColumn() {
		return pageColumn;
	}
	public void setPageColumn(int pageColumn) {
		this.pageColumn = pageColumn;
	}
	public int getProdCount() {
		return prodCount;
	}
	public void setProdCount(int prodCount) {
		this.prodCount = prodCount;
	}
	public int getProdColumns() {
		return prodColumns;
	}
	public void setProdColumns(int prodColumns) {
		this.prodColumns = prodColumns;
	}
	public String getFont() {
		return font;
	}
	public void setFont(String font) {
		this.font = font;
	}
	public SubCategory getObjSubCategory() {
		return objSubCategory;
	}
	public void setObjSubCategory(SubCategory objSubCategory) {
		this.objSubCategory = objSubCategory;
	}
}
