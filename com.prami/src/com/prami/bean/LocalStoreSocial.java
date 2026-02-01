package com.prami.bean;

public class LocalStoreSocial {
	
	private int socialId;
	private String socialName;
	private String socialUrl;
	private int socialStatus;
	public LocalStoreSocial() {
		super();
		// TODO Auto-generated constructor stub
	}
	public LocalStoreSocial(int socialId, String socialName, String socialUrl, int socialStatus) {
		super();
		this.socialId = socialId;
		this.socialName = socialName;
		this.socialUrl = socialUrl;
		this.socialStatus = socialStatus;
	}
	public int getSocialId() {
		return socialId;
	}
	public void setSocialId(int socialId) {
		this.socialId = socialId;
	}
	public String getSocialName() {
		return socialName;
	}
	public void setSocialName(String socialName) {
		this.socialName = socialName;
	}
	public String getSocialUrl() {
		return socialUrl;
	}
	public void setSocialUrl(String socialUrl) {
		this.socialUrl = socialUrl;
	}
	public int getSocialStatus() {
		return socialStatus;
	}
	public void setSocialStatus(int socialStatus) {
		this.socialStatus = socialStatus;
	}
	
	
	
}
