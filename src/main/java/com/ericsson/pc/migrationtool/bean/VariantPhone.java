package com.ericsson.pc.migrationtool.bean;


public class VariantPhone extends Phone {	
	
	//FROM VARIANTS
	private String colorVariant;
	private String colorId;
	private String gradientColor;
	private String memoryVariant;
	private String parent;
	private String originalPrice;
	
	public VariantPhone(Phone p) {
		super(p);
	}
	
	public String getOriginalPrice() {
		return originalPrice;
	}
	public void setOriginalPrice(String originalPrice) {
		this.originalPrice = originalPrice;
	}
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

	public void setParent(String parent) {
		this.parent = parent;
		
	}
	
	public String getParent() {
		return parent;
		
	}
	
}

	
