package com.prami.bean;

public class LocalStoreFeatures {
	private int featuresId ;
	private String featuresTitle;
	private String featuresDescription;
	private String featuresCover;
	private String featuresVideoLink;
	private int featuresType ;
	
	
	public LocalStoreFeatures() {
		super();
		// TODO Auto-generated constructor stub
	}
	public LocalStoreFeatures(int featuresId, String featuresTitle, String featuresDescription, String featuresCover,
			String featuresVideoLink, int featuresType) {
		super();
		this.featuresId = featuresId;
		this.featuresTitle = featuresTitle;
		this.featuresDescription = featuresDescription;
		this.featuresCover = featuresCover;
		this.featuresVideoLink = featuresVideoLink;
		this.featuresType = featuresType;
	}
	public int getFeaturesId() {
		return featuresId;
	}
	public void setFeaturesId(int featuresId) {
		this.featuresId = featuresId;
	}
	public String getFeaturesTitle() {
		return featuresTitle;
	}
	public void setFeaturesTitle(String featuresTitle) {
		this.featuresTitle = featuresTitle;
	}
	public String getFeaturesDescription() {
		return featuresDescription;
	}
	public void setFeaturesDescription(String featuresDescription) {
		this.featuresDescription = featuresDescription;
	}
	public String getFeaturesCover() {
		return featuresCover;
	}
	public void setFeaturesCover(String featuresCover) {
		this.featuresCover = featuresCover;
	}
	public String getFeaturesVideoLink() {
		return featuresVideoLink;
	}
	public void setFeaturesVideoLink(String featuresVideoLink) {
		this.featuresVideoLink = featuresVideoLink;
	}
	public int getFeaturesType() {
		return featuresType;
	}
	public void setFeaturesType(int featuresType) {
		this.featuresType = featuresType;
	}
	
	
}
