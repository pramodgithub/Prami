package com.prami.bean;

import java.util.List;

public class UpdateSimilarity {
	int productId;
	List<Integer> currProductId;
	public UpdateSimilarity(int productId, List<Integer> currProductId) {
		super();
		this.productId = productId;
		this.currProductId = currProductId;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public List<Integer> getCurrProductId() {
		return currProductId;
	}
	public void setCurrProductId(List<Integer> currProductId) {
		this.currProductId = currProductId;
	}

}
