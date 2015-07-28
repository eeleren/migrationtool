package com.ericsson.pc.migrationtool.msdp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ericsson.pc.migrationtool.builder.accessory.AccessoryConstants;


public class AccessoryAssetStructure {
	
	final static Logger logger = Logger.getLogger(AccessoryAssetStructure.class);
	
	private String externalId = "";
	
	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	private List<AccessoryField> accessoryFieldList = new ArrayList<AccessoryField>();

	public List<AccessoryField> getAccessoryFieldList() {
		return accessoryFieldList;
	}

	public void setAccessoryFieldList(List<AccessoryField> accessoryFieldList) {
		this.accessoryFieldList = accessoryFieldList;
	}

	//accessory asset mandatory fields list
	private Map<String, String> mandatoryFields = new HashMap<String, String>();
	
	public Map<String, String> getMandatoryFields() {
		return mandatoryFields;
	}

	public void setMandatoryFields(Map<String, String> mandatoryFields) {
		this.mandatoryFields = mandatoryFields;
	}

	public List<String> getFieldOrderList() {
		return fieldOrderList;
	}

	public void setFieldOrderList(List<String> fieldOrderList) {
		this.fieldOrderList = fieldOrderList;
	}

	private List<String> fieldOrderList = new LinkedList<String>();
	
public AccessoryAssetStructure() {
		
	fieldOrderList.add(AccessoryConstants.DEPLOYED_FIELD);
	fieldOrderList.add(AccessoryConstants.SERVICE_FIELD);
	fieldOrderList.add(AccessoryConstants.SKU_FIELD);
	fieldOrderList.add(AccessoryConstants.BRAND_ID_FIELD);
	fieldOrderList.add(AccessoryConstants.ORIGINAL_PRICE);
	fieldOrderList.add(AccessoryConstants.SALE_PRICE);
	fieldOrderList.add(AccessoryConstants.MIN_ADV_PRICE_FIELD);
	fieldOrderList.add(AccessoryConstants.THEME_NAME_FIELD);
	fieldOrderList.add(AccessoryConstants.THEME_START_DATE_FIELD);
	fieldOrderList.add(AccessoryConstants.THEME_STOP_DATE_FIELD);
	fieldOrderList.add(AccessoryConstants.ASSET_DESCRIPTION_FIELD);
	fieldOrderList.add(AccessoryConstants.LABEL_FIELD);	
	fieldOrderList.add(AccessoryConstants.OOS_THRESHOLD_FIELD);	
	fieldOrderList.add(AccessoryConstants.AVAILABLE_FIELD);
	fieldOrderList.add(AccessoryConstants.AVAILABLE_4_PREORDER_FIELD);
	fieldOrderList.add(AccessoryConstants.GENERAL_AV_DATE_FIELD);
	fieldOrderList.add(AccessoryConstants.OVERRIDE_OOS_FIELD);
	fieldOrderList.add(AccessoryConstants.GROUP_ID_FIELD); 
	fieldOrderList.add(AccessoryConstants.DEFAULT_VARIANT_ID_FIELD);
	fieldOrderList.add(AccessoryConstants.BAZAAR_VOICE_VAR_ID_FIELD);
	fieldOrderList.add(AccessoryConstants.VARIANT_PRIORITY_FIELD);
	fieldOrderList.add(AccessoryConstants.GROUP_PURCH_LIMIT_FIELD);
	fieldOrderList.add(AccessoryConstants.PRICE_START_DATE);
	fieldOrderList.add(AccessoryConstants.TYPE);
	fieldOrderList.add(AccessoryConstants.PICTURE_SHOP);
	fieldOrderList.add(AccessoryConstants.PICTURE_CHECKOUT);
	
		
	mandatoryFields.put(AccessoryConstants.DEPLOYED_FIELD,AccessoryConstants.DEPLOYED_VALUE);
	mandatoryFields.put(AccessoryConstants.SERVICE_FIELD,AccessoryConstants.SERVICE_VALUE);
	mandatoryFields.put(AccessoryConstants.SKU_FIELD,AccessoryConstants.SKU_FIELD);
	mandatoryFields.put(AccessoryConstants.BRAND_ID_FIELD,AccessoryConstants.BRAND_ID_VALUE);
	mandatoryFields.put(AccessoryConstants.ORIGINAL_PRICE,AccessoryConstants.ORIGINAL_PRICE_VALUE);
	mandatoryFields.put(AccessoryConstants.SALE_PRICE,AccessoryConstants.SALE_PRICE_VALUE);
	mandatoryFields.put(AccessoryConstants.OOS_THRESHOLD_FIELD,AccessoryConstants.OOS_THRESHOLD_VALUE);
	mandatoryFields.put(AccessoryConstants.AVAILABLE_FIELD,AccessoryConstants.AVAILABLE_VALUE);
	mandatoryFields.put(AccessoryConstants.TYPE,AccessoryConstants.TYPE_VALUE);

	}


public void init() {
	AccessoryAssetStructure accessoryAsset = new AccessoryAssetStructure();
	//Create all asset fields according to the main list of parameters
	List<String> assetFieldsList =  this.getFieldOrderList();
	
	AccessoryField accessoryField; 
	  for (int i = 0; i < assetFieldsList.size(); i++) {
		  accessoryField = new AccessoryField();
		  accessoryField.setName(assetFieldsList.get(i));
		  accessoryField.setOrder(i); 
		  accessoryField = accessoryAsset.checkMandatory(accessoryField);
		  accessoryFieldList.add(accessoryField);
        }
	 
	  //sort the list of fields according to the order value
	  Collections.sort(accessoryFieldList);
	  accessoryAsset.setAccessoryFieldList(accessoryFieldList);	
}
	public AccessoryField checkMandatory(AccessoryField f) {		
	if (mandatoryFields.containsKey(f.getName())) {
		f.setMandatory(true);
		f.setDefaultValue(mandatoryFields.get(f.getName()));
		logger.debug("value set to: " + mandatoryFields.get(f.getName()));
	} else {
		f.setMandatory(false);
	}
	return f;
}
	
	public void setAccessoryFieldValueByFieldName(String fieldName, Object fieldValue) {
		List<AccessoryField> accessoryFieldList = this.getAccessoryFieldList();
		AccessoryField f;
		for (int i = 0; i < accessoryFieldList.size(); i++) {
			if (fieldName.equalsIgnoreCase(accessoryFieldList.get(i).getName())) {
				f = accessoryFieldList.get(i);
				f.setValue(fieldValue);
				accessoryFieldList.set(i, f);
			}
				
		}
		
	}
	public AccessoryField getAccessoryFieldByName(String fieldName) {
		List<AccessoryField> accessoryFieldList = this.getAccessoryFieldList();
		
		for (AccessoryField a : accessoryFieldList) {
			if (fieldName.equalsIgnoreCase(a.getName())) {
				return a;
			}
		}		
		return null;
	}
	

}
