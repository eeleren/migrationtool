package com.ericsson.pc.migrationtool.msdp;

import org.apache.log4j.Logger;

import com.ericsson.pc.migrationtool.builder.manual.ManualConstants;
import com.ericsson.pc.migrationtool.builder.phone.PhoneConstants;


public class ManualAssetStructure extends AssetStructure {
	
	
	final static Logger logger = Logger.getLogger(ManualAssetStructure.class);	


	public ManualAssetStructure() {
		
		getFieldOrderList().add(ManualConstants.DEPLOYED_FIELD);
		getFieldOrderList().add(ManualConstants.SERVICE_FIELD);
		getFieldOrderList().add(ManualConstants.FILENAME);
		getFieldOrderList().add(ManualConstants.BRAND_ID_FIELD);
		getFieldOrderList().add(ManualConstants.ID_FIELD);
		getFieldOrderList().add(ManualConstants.USER_GUIDE_IMAGE);
		getFieldOrderList().add(ManualConstants.USER_GUIDE_TITLE_IMAGE);
		getFieldOrderList().add(ManualConstants.MANUAL_PDF);
		
		getMandatoryFields().put(ManualConstants.DEPLOYED_FIELD,PhoneConstants.DEPLOYED_VALUE);
		getMandatoryFields().put(ManualConstants.SERVICE_FIELD, ManualConstants.SERVICE_VALUE);
		getMandatoryFields().put(ManualConstants.BRAND_ID_FIELD,PhoneConstants.BRAND_ID_VALUE);
		getMandatoryFields().put(ManualConstants.FILENAME, ManualConstants.FILENAME_VALUE);
		
	}
}