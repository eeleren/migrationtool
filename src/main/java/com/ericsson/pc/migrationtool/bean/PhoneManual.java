package com.ericsson.pc.migrationtool.bean;

public class PhoneManual extends Model {
	
	private String id = "";
	private String fullName="";
	private String userGuideImage="";
	private String userGuideTitleImage="";
	private String pdfFileName = "";
	
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getUserGuideImage() {
		return userGuideImage;
	}
	public void setUserGuideImage(String userGuideImage) {
		this.userGuideImage = userGuideImage;
	}
	public String getUserGuideTitleImage() {
		return userGuideTitleImage;
	}
	public void setUserGuideTitleImage(String userGuideTitleImage) {
		this.userGuideTitleImage = userGuideTitleImage;
	}
	public String getPdfFileName() {
		return pdfFileName;
	}
	public void setPdfFileName(String pdfFileName) {
		this.pdfFileName = pdfFileName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	

}
