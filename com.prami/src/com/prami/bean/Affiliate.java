package com.prami.bean;

public class Affiliate {
	private boolean isAffiliated;
	private int offer;
	
	public Affiliate() {
		super();
	}

	public Affiliate(boolean isAffiliated, int offer) {
		super();
		this.isAffiliated = isAffiliated;
		this.offer = offer;
	}

	public boolean isAffiliated() {
		return isAffiliated;
	}

	public void setAffiliated(boolean isAffiliated) {
		this.isAffiliated = isAffiliated;
	}

	public int getOffer() {
		return offer;
	}

	public void setOffer(int offer) {
		this.offer = offer;
	}

		
	
}
