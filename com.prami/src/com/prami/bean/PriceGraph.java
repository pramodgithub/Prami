package com.prami.bean;

import java.util.List;

public class PriceGraph {
	List<String> dateList;
	List<Integer> priceList;
	int min;
	int max;
	int predictedDiscount;
	String helpString;
	public PriceGraph(List<String> dateList, List<Integer> priceList, int min, int max, int predictedDiscount,
			String helpString) {
		super();
		this.dateList = dateList;
		this.priceList = priceList;
		this.min = min;
		this.max = max;
		this.predictedDiscount = predictedDiscount;
		this.helpString = helpString;
	}
	
	public PriceGraph(String helpString) {
		super();
		this.helpString = helpString;
	}

	public List<String> getDateList() {
		return dateList;
	}
	public void setDateList(List<String> dateList) {
		this.dateList = dateList;
	}
	public List<Integer> getPriceList() {
		return priceList;
	}
	public void setPriceList(List<Integer> priceList) {
		this.priceList = priceList;
	}
	public int getMin() {
		return min;
	}
	public void setMin(int min) {
		this.min = min;
	}
	public int getMax() {
		return max;
	}
	public void setMax(int max) {
		this.max = max;
	}
	public int getPredictedDiscount() {
		return predictedDiscount;
	}
	public void setPredictedDiscount(int predictedDiscount) {
		this.predictedDiscount = predictedDiscount;
	}
	public String getHelpString() {
		return helpString;
	}
	public void setHelpString(String helpString) {
		this.helpString = helpString;
	}
		
	
}
