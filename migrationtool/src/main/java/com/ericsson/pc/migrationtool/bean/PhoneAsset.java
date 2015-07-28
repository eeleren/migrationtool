package com.ericsson.pc.migrationtool.bean;

import java.util.ArrayList;
import java.util.List;


public class PhoneAsset extends Phone {	
	
	//FROM VARIANTS
	private String colorVariant;
	private String colorId;
	private String gradientColor;
	private String memoryVariant;
	

	
	
	public String getColorVariant() {
		return colorVariant;
	}
	public void setColorVariant(String colorVariant) {
		this.colorVariant = colorVariant;
	}
	public String getGradientColor() {
		return gradientColor;
	}
	public void setGradientColor(String gradientColor) {
		this.gradientColor = gradientColor;
	}
	public String getMemoryVariant() {
		return memoryVariant;
	}
	public void setMemoryVariant(String memoryVariant) {
		this.memoryVariant = memoryVariant;
	}	
	public String getColorId() {
		return colorId;
	}
	public void setColorId(String colorId) {
		this.colorId = colorId;
	}
	
}

	
