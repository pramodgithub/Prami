package com.prami.bean;

public class LocalStoreServices {
	private int serviceId;
	private String servicesName;
	private String servicesDescription;
	private String servicesImage;
	private int isServicesCustomizable;
	private String servicesPriceRange;
	
	
	public LocalStoreServices() {
		super();
		// TODO Auto-generated constructor stub
	}
	public LocalStoreServices(int serviceId, String servicesName, String servicesDescription, String servicesImage,
			int isServicesCustomizable, String servicesPriceRange) {
		super();
		this.serviceId = serviceId;
		this.servicesName = servicesName;
		this.servicesDescription = servicesDescription;
		this.servicesImage = servicesImage;
		this.isServicesCustomizable = isServicesCustomizable;
		this.servicesPriceRange = servicesPriceRange;
	}
	public int getServiceId() {
		return serviceId;
	}
	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}
	public String getServicesName() {
		return servicesName;
	}
	public void setServicesName(String servicesName) {
		this.servicesName = servicesName;
	}
	public String getServicesDescription() {
		return servicesDescription;
	}
	public void setServicesDescription(String servicesDescription) {
		this.servicesDescription = servicesDescription;
	}
	public String getServicesImage() {
		return servicesImage;
	}
	public void setServicesImage(String servicesImage) {
		this.servicesImage = servicesImage;
	}
	public int getIsServicesCustomizable() {
		return isServicesCustomizable;
	}
	public void setIsServicesCustomizable(int isServicesCustomizable) {
		this.isServicesCustomizable = isServicesCustomizable;
	}
	public String getServicesPriceRange() {
		return servicesPriceRange;
	}
	public void setServicesPriceRange(String servicesPriceRange) {
		this.servicesPriceRange = servicesPriceRange;
	}
	
}
