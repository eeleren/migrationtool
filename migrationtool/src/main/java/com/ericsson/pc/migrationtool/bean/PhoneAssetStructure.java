package com.ericsson.pc.migrationtool.bean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.ericsson.pc.migrationtool.builder.PhoneConstants;
import com.ericsson.pc.migrationtool.util.ApplicationPropertiesReader;

public class PhoneAssetStructure {
	
	private List<PhoneField> phoneFieldList = new ArrayList();
	
	//phone asset mandatory fields list
	private Map<String, String> mandatoryFields = new HashMap<String, String>();
	//private List<String> notMandatoryFields = new ArrayList<String>();
	private List<String> fieldOrderList = new LinkedList<String>();
	
	
	public static void main(String[] args) {
		PhoneAssetStructure structure = new PhoneAssetStructure();
		PhoneField test = new PhoneField();
		test.setName("deployed");
		structure.checkMandatory(test);		
		//structure.init();
	}	

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
	
	public void setPhoneFieldValueByFieldName(String fieldName, String fieldValue) {
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
		
		//fieldOrderList.add(PhoneConstants.ASSET_FIELD);
		//fieldOrderList.add(PhoneConstants.PHONE_FIELD);
		//fieldOrderList.add(PhoneConstants.EXTERNAL_ID);
		fieldOrderList.add(PhoneConstants.DEPLOYED_FIELD);
		fieldOrderList.add(PhoneConstants.SERVICE_FIELD);
		fieldOrderList.add(PhoneConstants.BRAND_ID_FIELD);
		fieldOrderList.add(PhoneConstants.MSRP_FIELD);
		fieldOrderList.add(PhoneConstants.EQP_FIELD);
		fieldOrderList.add(PhoneConstants.PHONE_THEME_START_DATE_FIELD);
		fieldOrderList.add(PhoneConstants.PHONE_THEME_START_VALIDUNTIL_FIELD);
		fieldOrderList.add(PhoneConstants.PHONE_THEME_NAME_FIELD);
		fieldOrderList.add(PhoneConstants.PPROMOTION_EXPIRY_DATE_FIELD);
		fieldOrderList.add(PhoneConstants.MIN_ADV_PRICE_FIELD);
		fieldOrderList.add(PhoneConstants.OOS_THRESHOLD_FIELD);
		fieldOrderList.add(PhoneConstants.AVAILABLE_FIELD);
		fieldOrderList.add(PhoneConstants.AVAILABLE_4_PREORDER_FIELD);
		fieldOrderList.add(PhoneConstants.GENERAL_AV_DATE_FIELD);
		fieldOrderList.add(PhoneConstants.OVERRIDE_OOS_FIELD);
		fieldOrderList.add(PhoneConstants.PHONE_ID_FIELD);
		fieldOrderList.add(PhoneConstants.GROUP_ID_FIELD); 
		fieldOrderList.add(PhoneConstants.DEFAULT_VARIANT_ID_FIELD);
		fieldOrderList.add(PhoneConstants.BAZAAR_VOICE_VAR_ID_FIELD);
		fieldOrderList.add(PhoneConstants.VARIANT_PRIORITY_FIELD);
		fieldOrderList.add(PhoneConstants.GROUP_PURCH_LIMIT_FIELD);
		fieldOrderList.add(PhoneConstants.IS_REDVENTURES_FIELD);
		fieldOrderList.add(PhoneConstants.IMPORT_PATH_FIELD);
		fieldOrderList.add(PhoneConstants.GENIE_ORDER_FIELD);	
		fieldOrderList.add(PhoneConstants.IS_NEW_FIELD);
		fieldOrderList.add(PhoneConstants.IS_PREOWNED_FIELD);
		fieldOrderList.add(PhoneConstants.IS_HIDDEN_FIELD);
		fieldOrderList.add(PhoneConstants.DATE_LAUNCH_FIELD);
		fieldOrderList.add(PhoneConstants.IS_EOL_FIELD);
		fieldOrderList.add(PhoneConstants.SHORT_DESC_FIELD);
		fieldOrderList.add(PhoneConstants.EXT_DESC_FIELD);
		fieldOrderList.add(PhoneConstants.PHONE_NAME_FIELD);
		fieldOrderList.add(PhoneConstants.MANUFACT_NAME_FIELD);
		fieldOrderList.add(PhoneConstants.MANUFACT_RAW_FIELD);
		fieldOrderList.add(PhoneConstants.PHONE_NAME_RAW_FIELD);
		fieldOrderList.add(PhoneConstants.DISCLAIMER_MINI_FIELD);
		fieldOrderList.add(PhoneConstants.DISCLAIMER_FULL_FIELD);
		fieldOrderList.add(PhoneConstants.COLOR_VARIANT_FIELD);
		fieldOrderList.add(PhoneConstants.COLOR_ID_FIELD);
		fieldOrderList.add(PhoneConstants.GRADIENT_COLOR_FIELD);
		fieldOrderList.add(PhoneConstants.MEMORY_VARIANT_FIELD);
		fieldOrderList.add(PhoneConstants.FEATURE_MEMORY_FIELD);
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
		fieldOrderList.add(PhoneConstants.SPEC_FEAT_COUNT_LIST_FIELD);
		fieldOrderList.add(PhoneConstants.TECH_SPEC_GROUP_COUNT_FIELD);				
		
		//mandatoryFields.put(PhoneConstants.ASSET_FIELD,PhoneConstants.ASSET_VALUE);
		//mandatoryFields.put(PhoneConstants.PHONE_FIELD,PhoneConstants.PHONE_VALUE);
		//mandatoryFields.put(PhoneConstants.EXTERNAL_ID,PhoneConstants.EXTERNAL_ID_VALUE);
		mandatoryFields.put(PhoneConstants.DEPLOYED_FIELD,PhoneConstants.DEPLOYED_VALUE);
		mandatoryFields.put(PhoneConstants.SERVICE_FIELD,PhoneConstants.SERVICE_VALUE);
		mandatoryFields.put(PhoneConstants.BRAND_ID_FIELD,PhoneConstants.BRAND_ID_VALUE);
		mandatoryFields.put(PhoneConstants.MSRP_FIELD,PhoneConstants.MSRP_VALUE);
		mandatoryFields.put(PhoneConstants.EQP_FIELD,PhoneConstants.EQP_VALUE);
		mandatoryFields.put(PhoneConstants.OOS_THRESHOLD_FIELD,PhoneConstants.OOS_THRESHOLD_VALUE);
		mandatoryFields.put(PhoneConstants.AVAILABLE_FIELD,PhoneConstants.AVAILABLE_VALUE);
		mandatoryFields.put(PhoneConstants.PHONE_ID_FIELD,PhoneConstants.PHONE_ID_VALUE);
		mandatoryFields.put(PhoneConstants.IS_REDVENTURES_FIELD,PhoneConstants.IS_REDVENTURES_VALUE);
		mandatoryFields.put(PhoneConstants.SHORT_DESC_FIELD,PhoneConstants.SHORT_DESC_VALUE);
		mandatoryFields.put(PhoneConstants.PHONE_NAME_FIELD,PhoneConstants.PHONE_NAME_VALUE);
		mandatoryFields.put(PhoneConstants.MANUFACT_NAME_FIELD,PhoneConstants.MANUFACT_NAME_VALUE);
		mandatoryFields.put(PhoneConstants.MANUFACT_RAW_FIELD,PhoneConstants.MANUFACT_RAW_VALUE);
		mandatoryFields.put(PhoneConstants.EXTRA_FEAT_COUNT_FIELD,PhoneConstants.EXTRA_FEAT_COUNT_VALUE);
		mandatoryFields.put(PhoneConstants.SPEC_FEAT_COUNT_LIST_FIELD,PhoneConstants.SPEC_FEAT_COUNT);
		
	/*	//notMandatoryFields.add

		notMandatoryFields.add(PhoneConstants.PHONE_THEME_START_DATE_FIELD);
		notMandatoryFields.add(PhoneConstants.PHONE_THEME_START_VALIDUNTIL_FIELD);
		notMandatoryFields.add(PhoneConstants.PHONE_THEME_NAME_FIELD);
		notMandatoryFields.add(PhoneConstants.PPROMOTION_EXPIRY_DATE_FIELD);
		notMandatoryFields.add(PhoneConstants.MIN_ADV_PRICE_FIELD);


		notMandatoryFields.add(PhoneConstants.AVAILABLE_4_PREORDER_FIELD);
		notMandatoryFields.add(PhoneConstants.GENERAL_AV_DATE_FIELD);
		notMandatoryFields.add(PhoneConstants.OVERRIDE_OOS_FIELD);

		notMandatoryFields.add(PhoneConstants.GROUP_ID_FIELD); 
		notMandatoryFields.add(PhoneConstants.DEFAULT_VARIANT_ID_FIELD);
		notMandatoryFields.add(PhoneConstants.BAZAAR_VOICE_VAR_ID_FIELD);
		notMandatoryFields.add(PhoneConstants.VARIANT_PRIORITY_FIELD);
		notMandatoryFields.add(PhoneConstants.GROUP_PURCH_LIMIT_FIELD);

		notMandatoryFields.add(PhoneConstants.IMPORT_PATH_FIELD);
		notMandatoryFields.add(PhoneConstants.GENIE_ORDER_FIELD);	
		notMandatoryFields.add(PhoneConstants.IS_NEW_FIELD);
		notMandatoryFields.add(PhoneConstants.IS_PREOWNED_FIELD);
		notMandatoryFields.add(PhoneConstants.IS_HIDDEN_FIELD);
		notMandatoryFields.add(PhoneConstants.DATE_LAUNCH_FIELD);
		notMandatoryFields.add(PhoneConstants.IS_EOL_FIELD);

		notMandatoryFields.add(PhoneConstants.EXT_DESC_FIELD);


		notMandatoryFields.add(PhoneConstants.PHONE_NAME_RAW_FIELD);
		notMandatoryFields.add(PhoneConstants.DISCLAIMER_MINI_FIELD);
		notMandatoryFields.add(PhoneConstants.DISCLAIMER_FULL_FIELD);
		notMandatoryFields.add(PhoneConstants.COLOR_VARIANT_FIELD);
		notMandatoryFields.add(PhoneConstants.COLOR_ID_FIELD);
		notMandatoryFields.add(PhoneConstants.GRADIENT_COLOR_FIELD);
		notMandatoryFields.add(PhoneConstants.MEMORY_VARIANT_FIELD);
		notMandatoryFields.add(PhoneConstants.FEATURE_MEMORY_FIELD);
		notMandatoryFields.add(PhoneConstants.FEATURE_BAR_FIELD);
		notMandatoryFields.add(PhoneConstants.FEATURE_SLIDER_FIELD);
		notMandatoryFields.add(PhoneConstants.FEATURE_FLIP_FIELD);
		notMandatoryFields.add(PhoneConstants.FEATURE_TOUCHSCREEN_FIELD);
		notMandatoryFields.add(PhoneConstants.FEATURE_QWERTY_FIELD);
		notMandatoryFields.add(PhoneConstants.FEATURE_CAMERA_FIELD);
		notMandatoryFields.add(PhoneConstants.FEATURE_FRONT_FAC_CAM_FIELD);
		notMandatoryFields.add(PhoneConstants.FEATURE_GPS_FIELD);
		notMandatoryFields.add(PhoneConstants.FEATURE_4GWIMAX_FIELD);
		notMandatoryFields.add(PhoneConstants.FEATURE_WIFI_FIELD);
		notMandatoryFields.add(PhoneConstants.FEATURE_BLUETOOTH_FIELD);
		notMandatoryFields.add(PhoneConstants.FEATURE_SPEAKERPHONE_FIELD);
		notMandatoryFields.add(PhoneConstants.FEATURE_3G_FIELD);
		notMandatoryFields.add(PhoneConstants.FEATURE_4GLTE_FIELD);
		notMandatoryFields.add(PhoneConstants.FEATURE_4G_FIELD);
		notMandatoryFields.add(PhoneConstants.FEATURE_TEXT_FIELD);
		notMandatoryFields.add(PhoneConstants.FEATURE_VIDEO_FIELD);
		notMandatoryFields.add(PhoneConstants.FEATURE_HOTSPOT_FIELD);
		notMandatoryFields.add(PhoneConstants.FEATURE_EMAIL_FIELD);
		notMandatoryFields.add(PhoneConstants.FEATURE_HTML_BROWSER_FIELD);
		notMandatoryFields.add(PhoneConstants.FEATURE_ULE_CERTIFIED_FIELD);
		notMandatoryFields.add(PhoneConstants.FEATURE_MUSIC_FIELD);
		notMandatoryFields.add(PhoneConstants.FEATURE_VISUAL_VOICE_MAIL_FIELD);
		notMandatoryFields.add(PhoneConstants.FEATURE_IS_PREMIUM_FIELD);
		notMandatoryFields.add(PhoneConstants.SYNONYM_FIELD);
		notMandatoryFields.add(PhoneConstants.ADDWORDS_LID_FIELD);
		notMandatoryFields.add(PhoneConstants.ADDWORDS_S_KWGID_FIELD);
		notMandatoryFields.add(PhoneConstants.BING_LID_FIELD);
		notMandatoryFields.add(PhoneConstants.BING_KWGID_FIELD);
		notMandatoryFields.add(PhoneConstants.ACCESSORY_LIST_FIELD);
		notMandatoryFields.add(PhoneConstants.COMPARE_ITEM_OS_FIELD);
		notMandatoryFields.add(PhoneConstants.COMPARE_ITEM_DISPLAY_FIELD);
		notMandatoryFields.add(PhoneConstants.COMPARE_ITEM_CAMERA_FIELD);
		notMandatoryFields.add(PhoneConstants.COMPARE_ITEM_WIFI_FIELD);
		notMandatoryFields.add(PhoneConstants.COMPARE_ITEM_4G_FIELD); 
		notMandatoryFields.add(PhoneConstants.COMPARE_ITEM_HOTSPOT_FIELD);
		notMandatoryFields.add(PhoneConstants.COMPARE_ITEM_QWERTY_KEYBOARD_FIELD);
		notMandatoryFields.add(PhoneConstants.COMPARE_ITEM_WEBBROWSER_FIELD);
		notMandatoryFields.add(PhoneConstants.COMPARE_ITEM_FLASH_PLAYER_FIELD);
		notMandatoryFields.add(PhoneConstants.COMPARE_ITEM_EMAIL_FIELD);
		notMandatoryFields.add(PhoneConstants.COMPARE_ITEM_VIDEO_FIELD); 
		notMandatoryFields.add(PhoneConstants.COMPARE_ITEM_MUSIC_PLAYER_FIELD);
		notMandatoryFields.add(PhoneConstants.COMPARE_ITEM_GPS_FIELD);
		notMandatoryFields.add(PhoneConstants.COMPARE_ITEM_SPEAKERPHONE_FIELD);
		notMandatoryFields.add(PhoneConstants.COMPARE_ITEM_MEMORY_FIELD);
		notMandatoryFields.add(PhoneConstants.COMPARE_ITEM_PROCESSOR_FIELD);
		notMandatoryFields.add(PhoneConstants.COMPARE_ITEM_CALENDAR_FIELD);
		notMandatoryFields.add(PhoneConstants.COMPARE_ITEM_VISUAL_VOICE_MAIL_FIELD);
		notMandatoryFields.add(PhoneConstants.COMPARE_ITEM_3G_FIELD);
		notMandatoryFields.add(PhoneConstants.COMPARE_ITEM_BLUETOOTH_FIELD);		
		notMandatoryFields.add(PhoneConstants.TECH_SPEC_GROUP_COUNT_FIELD);*/
		
	}
	
	public PhoneField checkMandatory(PhoneField f) {		
		if (mandatoryFields.containsKey(f.getName())) {
			f.setMandatory(true);
			f.setDefaultValue(mandatoryFields.get(f.getName()));
			System.out.println("value set to:" +mandatoryFields.get(f.getName()));
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
				System.out.println("Field: "+ phoneField.getName()+" order:" + phoneField.getOrder()+ "added!");
				//checkMandatory(phoneField);			
				phoneFieldList.add(phoneField);
	        }
		 
		  //sort the list of fields according to the order value
		  Collections.sort(phoneFieldList);
		  phoneAsset.setPhoneFieldList(phoneFieldList);
		
		//  Setting mandatory parameters
		  
		
		/*//create all asset fields
		PhoneField deployed = new PhoneField();
		deployed.setMandatory(true);
		deployed.setOrder(1);
		deployed.setName(PhoneConstants.DEPLOYED_FIELD);
		deployed.setValue("true");
		phoneFieldList.add(deployed);
		
		PhoneField service = new PhoneField();
		service.setMandatory(true);
		service.setOrder(2);
		service.setName(PhoneConstants.SERVICE_FIELD);
		//service.setValue(ApplicationPropertiesReader.getInstance().getProperty(""));	
		service.setValue("service");
		phoneFieldList.add(service);
		
		PhoneField msrp = new PhoneField();
		msrp.setMandatory(true);
		msrp.setOrder(3);
		msrp.setName(PhoneConstants.MSRP_FIELD);
		msrp.setValue(PhoneConstants.MSRP_VALUE);	
		phoneFieldList.add(msrp);
		
		PhoneField eqp = new PhoneField();
		eqp.setMandatory(true);
		eqp.setOrder(4);
		eqp.setName(PhoneConstants.EQP_FIELD);
		eqp.setValue(PhoneConstants.EQP_VALUE);	
		phoneFieldList.add(eqp);
		
		PhoneField minimumAdvPrice = new PhoneField();
		minimumAdvPrice.setMandatory(false);
		minimumAdvPrice.setOrder(5);
		minimumAdvPrice.setName(PhoneConstants.MIN_ADV_PRICE_FIELD);
		phoneFieldList.add(minimumAdvPrice);*/
		
		
	}

/*	public void setMandatoryFields(List<String> mandatoryFields) {
		this.mandatoryFields = mandatoryFields;
	}
	
	public String getMandatoryFieldByName(String name) {
		for(String s: mandatoryFields) {
			if( s.equals(name)){
				return s;
			}
			
		}
		return null;
	}*/
	
/*	public List<String> getMandatoryFieldsName() {
		List<String> mandatoryFieldsNames = new ArrayList<String>();
		Iterator it = mandatoryFields.entrySet().iterator();
		while(it.hasNext()) {
			 Map.Entry pair = (Map.Entry)it.next();
			 mandatoryFieldsNames.add((String) pair.getKey());
			 System.out.println("added mandatory key: "+pair.getKey());
		}       
       return mandatoryFieldsNames;
    }*/
}


