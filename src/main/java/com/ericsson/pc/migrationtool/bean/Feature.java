package com.ericsson.pc.migrationtool.bean;

public class Feature {
	
	
	/**
	 * parameters for features original structure
	 * e.g.:
	 * <features> 
            <feature icon="android"> 
                <title><![CDATA[Android&#8482; 4.4 (KitKat)]]></title> 
                <description><![CDATA[Work and play with the sleek simplicity, speed and most-loved features of Android.]]></description> 
            </feature> 
		...
		</features>
	 */
	private String extraFeatureTitle;
	private String extraFeatureDescription;
	private String extraFeatureIconName;
	
	/**
	 * parameters for features alternative structure
	 * e.g.:
	 *	<features>
     *       <feature id="android-2point3" featured="true" url="" order="1"></feature>
     *  ...
	 *	</features>
	 *
	 */
	private String featureId;
	private String featured;
	
	public String getFeatureId() {
		return featureId;
	}
	public void setFeatureId(String featureId) {
		this.featureId = featureId;
	}
	public String getExtraFeatureTitle() {
		return extraFeatureTitle;
	}
	public void setExtraFeatureTitle(String extraFeatureTitle) {
		this.extraFeatureTitle = extraFeatureTitle;
	}
	public String getExtraFeatureDescription() {
		return extraFeatureDescription;
	}
	public void setExtraFeatureDescription(String extraFeatureDescription) {
		this.extraFeatureDescription = extraFeatureDescription;
	}
	public String getExtraFeatureIconName() {
		return extraFeatureIconName;
	}
	public void setExtraFeatureIconName(String extraFeatureIconName) {
		this.extraFeatureIconName = extraFeatureIconName;
	}
	public String getFeatured() {
		return featured;
	}
	public void setFeatured(String featured) {
		this.featured = featured;
	}

	

}
