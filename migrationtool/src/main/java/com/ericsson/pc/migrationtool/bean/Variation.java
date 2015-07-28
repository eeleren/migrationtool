package com.ericsson.pc.migrationtool.bean;

public class Variation {
	
	private String id;
	private String oosThresholdOverride;
	private String map;
	private String colorVariant; //white, pink, yellow, etc
	private String colorId; //@fced6b
	private String gradientColor;
	private String memoryVariant;
	
	
	public String getColorVariant() {
		return colorVariant;
	}
	public void setColorVariant(String colorVariant) {
		this.colorVariant = colorVariant;
	}
	public String getColorId() {
		return colorId;
	}
	public void setColorId(String colorId) {
		this.colorId = colorId;
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOosThresholdOverride() {
		return oosThresholdOverride;
	}
	public void setOosThresholdOverride(String oosThresholdOverride) {
		this.oosThresholdOverride = oosThresholdOverride;
	}
	public String getMap() {
		return map;
	}
	public void setMap(String map) {
		this.map = map;
	}
	
	
}
