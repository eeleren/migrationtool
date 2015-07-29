package com.ericsson.pc.migrationtool.bean;

public class SpecialFeature {
	
	/**
	 *   If the specialFeature appears with the following structure, the parameter @specialFeatureName will be used        
	 *          <list> 
                <item><![CDATA[3-way calling]]></item> 
				...
				</list>
	 * 
	 * */
	private String specialFeatureName;
	
	/**
	 *   If the specialFeature appears with the following structure, the parameter @specialFeatureId will be used        
	 *           
	 *           <feature id="microsd-2gb-card-included" featured="false" url="" order="11"></feature>
	 * 
	 * */
	private String specialFeatureId;
	
	
	public String getSpecialFeatureId() {
		return specialFeatureId;
	}
	public void setSpecialFeatureId(String specialFeatureId) {
		this.specialFeatureId = specialFeatureId;
	}
	public String getSpecialFeatureName() {
		return specialFeatureName;
	}
	public void setSpecialFeatureName(String specialFeatureName) {
		this.specialFeatureName = specialFeatureName;
	}

}
