package com.prami.bean;

import java.util.List;

public class LandingPageSection {
	private String sectionId;
	private String sectionName;
	private String sectionSubTitle;
	private String sectionFormat;
	private String sectionStartDate;
	private String sectionEndDate;
	private String sectionPage;
	
	private List<LandingPageItems> landingPageItems;
	

	
	public LandingPageSection() {
		super();
	}


	public String getSectionId() {
		return sectionId;
	}

	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	public String getSectionSubTitle() {
		return sectionSubTitle;
	}

	public void setSectionSubTitle(String sectionSubTitle) {
		this.sectionSubTitle = sectionSubTitle;
	}

	public String getSectionFormat() {
		return sectionFormat;
	}

	public void setSectionFormat(String sectionFormat) {
		this.sectionFormat = sectionFormat;
	}

	public String getSectionStartDate() {
		return sectionStartDate;
	}

	public void setSectionStartDate(String sectionStartDate) {
		this.sectionStartDate = sectionStartDate;
	}

	public String getSectionEndDate() {
		return sectionEndDate;
	}

	public void setSectionEndDate(String sectionEndDate) {
		this.sectionEndDate = sectionEndDate;
	}

	public List<LandingPageItems> getLandingPageItems() {
		return landingPageItems;
	}

	public void setLandingPageItems(List<LandingPageItems> landingPageItems) {
		this.landingPageItems = landingPageItems;
	}
	
	public String getSectionPage() {
		return sectionPage;
	}

	public void setSectionPage(String sectionPage) {
		this.sectionPage = sectionPage;
	}
	
	
	
	
}
