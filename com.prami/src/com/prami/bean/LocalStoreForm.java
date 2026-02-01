package com.prami.bean;

public class LocalStoreForm {
	int formId;
	String inputName;
	String inputValue;
	
	public LocalStoreForm(int formId, String inputName, String inputValue) {
		super();
		this.formId = formId;
		this.inputName = inputName;
		this.inputValue = inputValue;
	}
	public int getFormId() {
		return formId;
	}
	public void setFormId(int formId) {
		this.formId = formId;
	}
	public String getInputName() {
		return inputName;
	}
	public void setInputName(String inputName) {
		this.inputName = inputName;
	}
	public String getInputValue() {
		return inputValue;
	}
	public void setInputValue(String inputValue) {
		this.inputValue = inputValue;
	}
	
	
}
