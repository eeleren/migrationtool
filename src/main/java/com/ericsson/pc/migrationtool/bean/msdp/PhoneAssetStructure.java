package com.ericsson.pc.migrationtool.bean.msdp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ericsson.pc.migrationtool.builder.phone.PhoneConstants;


public class PhoneAssetStructure {
	
	
	final static Logger logger = Logger.getLogger(PhoneAssetStructure.class);
	
	private List<PhoneField> phoneFieldList = new ArrayList<PhoneField>();
	
	private boolean withFeatures = false;
	
	public boolean isWithFeatures() {
		return withFeatures;
	}

	public void setWithFeatures(boolean withFeatures) {
		this.withFeatures = withFeatures;
	}

	//phone asset mandatory fields list
	private Map<String, String> mandatoryFields = new HashMap<String, String>();
	//private List<String> notMandatoryFields = new ArrayList<String>();
	private List<String> fieldOrderList = new LinkedList<String>();
	
	
public List<PhoneField> getPhoneFieldList() {
		return phoneFieldList;
	}

	public void setPhoneFieldList(List<PhoneField> phoneFieldList) {
		this.phoneFieldList = phoneFieldList;
	}
	
	public PhoneField getPhoneFieldByName(String fieldName) {
		List<PhoneField> phoneFieldList = this.getPhoneFieldList();
		
		for (PhoneField p : phoneFieldList) {
			if (fieldName.equalsIgnoreCase(p.getName())) {
				return p;
			}
		}		
		return null;
	}
	
	public void setPhoneFieldValueByFieldName(String fieldName, Object fieldValue) {
		List<PhoneField> phoneFieldList = this.getPhoneFieldList();
		PhoneField f;
		for (int i = 0; i < phoneFieldList.size(); i++) {
			if (fieldName.equalsIgnoreCase(phoneFieldList.get(i).getName())) {
				f = phoneFieldList.get(i);
				f.setValue(fieldValue);
				phoneFieldList.set(i, f);
			}
				
		}
		
	}
	
	public List<String> getFieldOrderList() {
		return fieldOrderList;
	}

	public void setFieldOrderList(List<String> fieldOrderList) {
		this.fieldOrderList = fieldOrderList;
	}

	public PhoneAssetStructure() {
		
		fieldOrderList.add(PhoneConstants.DEPLOYED_FIELD);
		fieldOrderList.add(PhoneConstants.SERVICE_FIELD);
		fieldOrderList.add(PhoneConstants.BRAND_ID_FIELD);
		fieldOrderList.add(PhoneConstants.MSRP_FIELD);
		fieldOrderList.add(PhoneConstants.EQP_FIELD);
		fieldOrderList.add(PhoneConstants.PHONE_THEME_START_DATE_FIELD);
		fieldOrderList.add(PhoneConstants.PHONE_THEME_START_VALIDUNTIL_FIELD);
		fieldOrderList.add(PhoneConstants.PHONE_THEME_NAME_FIELD);
		fieldOrderList.add(PhoneConstants.MIN_ADV_PRICE_FIELD);
		fieldOrderList.add(PhoneConstants.OOS_THRESHOLD_FIELD);
		fieldOrderList.add(PhoneConstants.AVAILABLE_FIELD);
		fieldOrderList.add(PhoneConstants.AVAILABLE_4_PREORDER_FIELD);
		fieldOrderList.add(PhoneConstants.AVAILABLE_4_BACKORDER_FIELD);
		fieldOrderList.add(PhoneConstants.GENERAL_AV_DATE_FIELD);
		fieldOrderList.add(PhoneConstants.OVERRIDE_OOS_FIELD);
		fieldOrderList.add(PhoneConstants.EXTERNAL_URL);
		fieldOrderList.add(PhoneConstants.SKU);
		fieldOrderList.add(PhoneConstants.GROUP_ID_FIELD); 
		fieldOrderList.add(PhoneConstants.BAZAAR_VOICE_VAR_ID_FIELD);
		fieldOrderList.add(PhoneConstants.VARIANT_LIST_ORDER_FIELD);
		fieldOrderList.add(PhoneConstants.GROUP_PURCH_LIMIT_FIELD);
		fieldOrderList.add(PhoneConstants.IS_REDVENTURES_FIELD);
		fieldOrderList.add(PhoneConstants.PHONE_GENIE_FIELD);
		fieldOrderList.add(PhoneConstants.GENIE_ORDER_FIELD);	
		fieldOrderList.add(PhoneConstants.IS_NEW_FIELD);
		fieldOrderList.add(PhoneConstants.IS_PREOWNED_FIELD);
		fieldOrderList.add(PhoneConstants.IS_HIDDEN_FIELD);
		fieldOrderList.add(PhoneConstants.IS_EOL_FIELD);
		fieldOrderList.add(PhoneConstants.SHORT_DESC_FIELD);
		fieldOrderList.add(PhoneConstants.EXT_DESC_FIELD);
		fieldOrderList.add(PhoneConstants.PHONE_NAME_FIELD);
		fieldOrderList.add(PhoneConstants.MANUFACT_NAME_FIELD);
		fieldOrderList.add(PhoneConstants.MANUFACT_RAW_FIELD);
		fieldOrderList.add(PhoneConstants.PHONE_NAME_RAW_FIELD);
		fieldOrderList.add(PhoneConstants.DISCLAIMER_MINI_FIELD);
		fieldOrderList.add(PhoneConstants.DISCLAIMER_FULL_FIELD);
		fieldOrderList.add(PhoneConstants.FOOTER_LEGAL_FIELD);
		fieldOrderList.add(PhoneConstants.COLOR_VARIANT_FIELD);
		fieldOrderList.add(PhoneConstants.COLOR_ID_FIELD);
		fieldOrderList.add(PhoneConstants.GRADIENT_COLOR_FIELD);
		fieldOrderList.add(PhoneConstants.MEMORY_VARIANT_FIELD);
		fieldOrderList.add(PhoneConstants.PHONE_TYPE);
		fieldOrderList.add(PhoneConstants.FEATURE_BAR_FIELD);
		fieldOrderList.add(PhoneConstants.FEATURE_SLIDER_FIELD);
		fieldOrderList.add(PhoneConstants.FEATURE_FLIP_FIELD);
		fieldOrderList.add(PhoneConstants.FEATURE_TOUCHSCREEN_FIELD);
		fieldOrderList.add(PhoneConstants.FEATURE_QWERTY_FIELD);
		fieldOrderList.add(PhoneConstants.FEATURE_CAMERA_FIELD);
		fieldOrderList.add(PhoneConstants.FEATURE_FRONT_FAC_CAM_FIELD);
		fieldOrderList.add(PhoneConstants.FEATURE_GPS_FIELD);
		fieldOrderList.add(PhoneConstants.FEATURE_4GWIMAX_FIELD);
		fieldOrderList.add(PhoneConstants.FEATURE_WIFI_FIELD);
		fieldOrderList.add(PhoneConstants.FEATURE_BLUETOOTH_FIELD);
		fieldOrderList.add(PhoneConstants.FEATURE_SPEAKERPHONE_FIELD);
		fieldOrderList.add(PhoneConstants.FEATURE_3G_FIELD);
		fieldOrderList.add(PhoneConstants.FEATURE_4GLTE_FIELD);
		fieldOrderList.add(PhoneConstants.FEATURE_4G_FIELD);
		fieldOrderList.add(PhoneConstants.FEATURE_TEXT_FIELD);
		fieldOrderList.add(PhoneConstants.FEATURE_VIDEO_FIELD);
		fieldOrderList.add(PhoneConstants.FEATURE_HOTSPOT_FIELD);
		fieldOrderList.add(PhoneConstants.FEATURE_EMAIL_FIELD);
		fieldOrderList.add(PhoneConstants.FEATURE_HTML_BROWSER_FIELD);
		fieldOrderList.add(PhoneConstants.FEATURE_ULE_CERTIFIED_FIELD);
		fieldOrderList.add(PhoneConstants.FEATURE_MUSIC_FIELD);
		fieldOrderList.add(PhoneConstants.FEATURE_VISUAL_VOICE_MAIL_FIELD);
		fieldOrderList.add(PhoneConstants.FEATURE_IS_PREMIUM_FIELD);
		fieldOrderList.add(PhoneConstants.SYNONYM_FIELD);
		fieldOrderList.add(PhoneConstants.ADDWORDS_LID_FIELD);
		fieldOrderList.add(PhoneConstants.ADDWORDS_S_KWGID_FIELD);
		fieldOrderList.add(PhoneConstants.BING_LID_FIELD);
		fieldOrderList.add(PhoneConstants.BING_KWGID_FIELD);
		fieldOrderList.add(PhoneConstants.ACCESSORY_LIST_FIELD);
		fieldOrderList.add(PhoneConstants.COMPARE_ITEM_OS_FIELD);
		fieldOrderList.add(PhoneConstants.COMPARE_ITEM_DISPLAY_FIELD);
		fieldOrderList.add(PhoneConstants.COMPARE_ITEM_CAMERA_FIELD);
		fieldOrderList.add(PhoneConstants.COMPARE_ITEM_WIFI_FIELD);
		fieldOrderList.add(PhoneConstants.COMPARE_ITEM_4G_FIELD); 
		fieldOrderList.add(PhoneConstants.COMPARE_ITEM_HOTSPOT_FIELD);
		fieldOrderList.add(PhoneConstants.COMPARE_ITEM_QWERTY_KEYBOARD_FIELD);
		fieldOrderList.add(PhoneConstants.COMPARE_ITEM_WEBBROWSER_FIELD);
		fieldOrderList.add(PhoneConstants.COMPARE_ITEM_FLASH_PLAYER_FIELD);
		fieldOrderList.add(PhoneConstants.COMPARE_ITEM_EMAIL_FIELD);
		fieldOrderList.add(PhoneConstants.COMPARE_ITEM_VIDEO_FIELD); 
		fieldOrderList.add(PhoneConstants.COMPARE_ITEM_MUSIC_PLAYER_FIELD);
		fieldOrderList.add(PhoneConstants.COMPARE_ITEM_GPS_FIELD);
		fieldOrderList.add(PhoneConstants.COMPARE_ITEM_SPEAKERPHONE_FIELD);
		fieldOrderList.add(PhoneConstants.COMPARE_ITEM_MEMORY_FIELD);
		fieldOrderList.add(PhoneConstants.COMPARE_ITEM_PROCESSOR_FIELD);
		fieldOrderList.add(PhoneConstants.COMPARE_ITEM_CALENDAR_FIELD);
		fieldOrderList.add(PhoneConstants.COMPARE_ITEM_VISUAL_VOICE_MAIL_FIELD);
		fieldOrderList.add(PhoneConstants.COMPARE_ITEM_3G_FIELD);
		fieldOrderList.add(PhoneConstants.COMPARE_ITEM_BLUETOOTH_FIELD);
		fieldOrderList.add(PhoneConstants.EXTRA_FEAT_COUNT_FIELD);
		fieldOrderList.add(PhoneConstants.FEATURES_FIELD);
		fieldOrderList.add(PhoneConstants.SPEC_FEAT_COUNT_LIST_FIELD);
		fieldOrderList.add(PhoneConstants.SPEC_FEATURES_FIELD);
		fieldOrderList.add(PhoneConstants.SPEC_HEIGHT_FIELD);
		fieldOrderList.add(PhoneConstants.SPEC_WIDTH_FIELD);
		fieldOrderList.add(PhoneConstants.SPEC_DEPTH_FIELD);
		fieldOrderList.add(PhoneConstants.SPEC_WEIGHT_FIELD);
		fieldOrderList.add(PhoneConstants.SPEC_SCREENSIZE_FIELD);
		fieldOrderList.add(PhoneConstants.SPEC_BATTERYTYPE_FIELD);
		fieldOrderList.add(PhoneConstants.SPEC_TALKINGTIME_FIELD);
		fieldOrderList.add(PhoneConstants.SPEC_REMOVEABLE_FIELD);
		fieldOrderList.add(PhoneConstants.SPEC_MEMORY_FIELD);
		fieldOrderList.add(PhoneConstants.SPEC_PROCESSOR_FIELD);
		fieldOrderList.add(PhoneConstants.SPEC_OS_FIELD);
		fieldOrderList.add(PhoneConstants.TECH_SPEC_GROUP_COUNT_FIELD);				
		fieldOrderList.add(PhoneConstants.TECH_SPEC_VARIANT_FIELD);
		fieldOrderList.add(PhoneConstants.PICTURES_FIELD);
		
		mandatoryFields.put(PhoneConstants.DEPLOYED_FIELD,PhoneConstants.DEPLOYED_VALUE);
		mandatoryFields.put(PhoneConstants.SERVICE_FIELD,PhoneConstants.SERVICE_VALUE);
		mandatoryFields.put(PhoneConstants.BRAND_ID_FIELD,PhoneConstants.BRAND_ID_VALUE);
		mandatoryFields.put(PhoneConstants.MSRP_FIELD,PhoneConstants.MSRP_VALUE);
		mandatoryFields.put(PhoneConstants.EQP_FIELD,PhoneConstants.EQP_VALUE);
		mandatoryFields.put(PhoneConstants.OOS_THRESHOLD_FIELD,PhoneConstants.OOS_THRESHOLD_VALUE);
		mandatoryFields.put(PhoneConstants.AVAILABLE_FIELD,PhoneConstants.AVAILABLE_VALUE);
		mandatoryFields.put(PhoneConstants.EXTERNAL_URL,PhoneConstants.EXTERNAL_URL_VALUE);
		mandatoryFields.put(PhoneConstants.SKU,PhoneConstants.SKU_VALUE);
		mandatoryFields.put(PhoneConstants.IS_REDVENTURES_FIELD,PhoneConstants.IS_REDVENTURES_VALUE);
		mandatoryFields.put(PhoneConstants.PHONE_GENIE_FIELD,PhoneConstants.PHONE_GENIE_VALUE);
		mandatoryFields.put(PhoneConstants.SHORT_DESC_FIELD,PhoneConstants.SHORT_DESC_VALUE);
		mandatoryFields.put(PhoneConstants.EXT_DESC_FIELD,PhoneConstants.EXT_DESC_VALUE);
		mandatoryFields.put(PhoneConstants.PHONE_NAME_FIELD,PhoneConstants.PHONE_NAME_VALUE);
		mandatoryFields.put(PhoneConstants.MANUFACT_NAME_FIELD,PhoneConstants.MANUFACT_NAME_VALUE);
		mandatoryFields.put(PhoneConstants.MANUFACT_RAW_FIELD,PhoneConstants.MANUFACT_RAW_VALUE);
		mandatoryFields.put(PhoneConstants.EXTRA_FEAT_COUNT_FIELD,PhoneConstants.EXTRA_FEAT_COUNT_VALUE);
		mandatoryFields.put(PhoneConstants.SPEC_FEAT_COUNT_LIST_FIELD,PhoneConstants.SPEC_FEAT_COUNT);	
		mandatoryFields.put(PhoneConstants.SPEC_HEIGHT_FIELD,PhoneConstants.SPEC_HEIGHT_VALUE);	
		mandatoryFields.put(PhoneConstants.SPEC_WIDTH_FIELD,PhoneConstants.SPEC_WIDTH_VALUE);	
		mandatoryFields.put(PhoneConstants.SPEC_DEPTH_FIELD,PhoneConstants.SPEC_DEPTH_VALUE);	
		mandatoryFields.put(PhoneConstants.SPEC_WEIGHT_FIELD,PhoneConstants.SPEC_WEIGHT_VALUE);	
		mandatoryFields.put(PhoneConstants.SPEC_SCREENSIZE_FIELD,PhoneConstants.SPEC_SCREENSIZE_VALUE);	
		mandatoryFields.put(PhoneConstants.SPEC_BATTERYTYPE_FIELD,PhoneConstants.SPEC_BATTERYTYPE_VALUE);	
		mandatoryFields.put(PhoneConstants.SPEC_TALKINGTIME_FIELD,PhoneConstants.SPEC_TALKINGTIME_VALUE);	
		mandatoryFields.put(PhoneConstants.SPEC_REMOVEABLE_FIELD,PhoneConstants.SPEC_REMOVEABLE_VALUE);	
		mandatoryFields.put(PhoneConstants.SPEC_MEMORY_FIELD,PhoneConstants.SPEC_MEMORY_VALUE);	
		mandatoryFields.put(PhoneConstants.SPEC_PROCESSOR_FIELD,PhoneConstants.SPEC_PROCESSOR_VALUE);	
		mandatoryFields.put(PhoneConstants.SPEC_OS_FIELD,PhoneConstants.SPEC_OS_VALUE);	
	}
	
	public PhoneField checkMandatory(PhoneField f) {		
		if (mandatoryFields.containsKey(f.getName())) {
			f.setMandatory(true);
			f.setDefaultValue(mandatoryFields.get(f.getName()));
			//logger.debug("value set to: " + mandatoryFields.get(f.getName()));
		} else {
			f.setMandatory(false);
		}
		return f;
	}
	
	public void init() {
		PhoneAssetStructure phoneAsset = new PhoneAssetStructure();
		//Create all asset fields according to the main list of parameters
		List<String> assetFieldsList =  this.getFieldOrderList();
		
		PhoneField phoneField; 
		  for (int i = 0; i < assetFieldsList.size(); i++) {
			    phoneField = new PhoneField();
	            phoneField.setName(assetFieldsList.get(i));
				phoneField.setOrder(i); 
				phoneField = phoneAsset.checkMandatory(phoneField);
				phoneFieldList.add(phoneField);
	        }
		 
		  //sort the list of fields according to the order value
		  Collections.sort(phoneFieldList);
		  phoneAsset.setPhoneFieldList(phoneFieldList);		
	}
}


