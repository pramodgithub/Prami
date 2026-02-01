package com.prami.bean;

public class LocalSocial {
	private int socialId;
	private String socialUrl;
	private String socialName;
	private String socialClass;
	public LocalSocial() {
		super();
		// TODO Auto-generated constructor stub
	}
	public LocalSocial(int socialId, String socialUrl, String socialName, String socialClass) {
		super();
		this.socialId = socialId;
		this.socialUrl = socialUrl;
		this.socialName = socialName;
		this.socialClass = socialClass;
	}
	public int getSocialId() {
		return socialId;
	}
	public void setSocialId(int socialId) {
		this.socialId = socialId;
	}
	public String getSocialUrl() {
		return socialUrl;
	}
	public void setSocialUrl(String socialUrl) {
		this.socialUrl = socialUrl;
	}
	public String getSocialName() {
		return socialName;
	}
	public void setSocialName(String socialName) {
		this.socialName = socialName;
	}
	public String getSocialClass() {
		return socialClass;
	}
	public void setSocialClass(String socialClass) {
		this.socialClass = socialClass;
	}
	

}
