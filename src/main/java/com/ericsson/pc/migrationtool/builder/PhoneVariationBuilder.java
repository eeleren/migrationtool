package com.ericsson.pc.migrationtool.builder;


import java.util.List;

import com.ericsson.pc.migrationtool.bean.Phone;
import com.ericsson.pc.migrationtool.bean.PhoneAsset;
import com.ericsson.pc.migrationtool.bean.Variation;
import com.ericsson.pc.migrationtool.bean.msdp.PhoneAssetStructure;

public class PhoneVariationBuilder extends Builder {
	
	
	private PhoneAsset variantPhone; 
	
	public void setAssetName(String assetBrand, String assetName, String variantColor) {		
		this.assetName = assetBrand + "_" + assetName + "_" + variantColor;
		this.normalizeAssetName();		
	}
	
	public void createPhoneVariations(Phone phone) {
		variantPhone = new PhoneAsset(phone);
		List<Variation> variationList = phone.getVariations();
		
		for (Variation v : variationList) {
			variantPhone.setId(v.getId());
			variantPhone.setOosThresholdOverride(v.getOosThresholdOverride());
			variantPhone.setMap(v.getMap());
			variantPhone.setColorVariant(v.getColorVariant());
			variantPhone.setGradientColor(v.getGradientColor());
			variantPhone.setMemoryVariant(v.getMemoryVariant());
			
			setAssetName(variantPhone.getManufacturerRaw(), variantPhone.getPhoneNameRaw(), variantPhone.getColorVariant());
			setAssetOutputDir(getAssetName());
			createPhoneAsset(variantPhone);		
	}		
	}
	
	/**
	 * Translates a phone data coming from boost into msdp phone assets structure
	 * */
	public PhoneAssetStructure buildAssetStructure(Phone phone) {
		
					
			PhoneAssetStructure asset = new PhoneAssetStructure();
			asset.init();				
			logger.debug("preowned: "+phone.isPreowned());
			//setting asset fields with boost values
		
			asset.setPhoneFieldValueByFieldName(PhoneConstants.MIN_ADV_PRICE_FIELD, phone.getMap());		
			asset.setPhoneFieldValueByFieldName(PhoneConstants.OVERRIDE_OOS_FIELD, phone.getOosThresholdOverride());			
			asset.setPhoneFieldValueByFieldName(PhoneConstants.PHONE_ID_FIELD, phone.getId());	
			asset.setPhoneFieldValueByFieldName(PhoneConstants.GROUP_ID_FIELD, phone.getDefaultId());	
			asset.setPhoneFieldValueByFieldName(PhoneConstants.DEFAULT_VARIANT_ID_FIELD, phone.getDefaultId());				
			asset.setPhoneFieldValueByFieldName(PhoneConstants.IS_REDVENTURES_FIELD, phone.getRedVentures());
			asset.setPhoneFieldValueByFieldName(PhoneConstants.GENIE_ORDER_FIELD, phone.getGenieOrder());
			asset.setPhoneFieldValueByFieldName(PhoneConstants.IS_NEW_FIELD, phone.getIsNew());
			asset.setPhoneFieldValueByFieldName(PhoneConstants.IS_PREOWNED_FIELD, phone.isPreowned());			
			asset.setPhoneFieldValueByFieldName(PhoneConstants.DATE_LAUNCH_FIELD, phone.getDateLaunch());
			asset.setPhoneFieldValueByFieldName(PhoneConstants.IS_EOL_FIELD, phone.getEol());
			asset.setPhoneFieldValueByFieldName(PhoneConstants.SHORT_DESC_FIELD, phone.getShortDescription());
			asset.setPhoneFieldValueByFieldName(PhoneConstants.EXT_DESC_FIELD, phone.getExtendedDescription());
			asset.setPhoneFieldValueByFieldName(PhoneConstants.PHONE_NAME_FIELD, phone.getPhoneName());
			asset.setPhoneFieldValueByFieldName(PhoneConstants.MANUFACT_NAME_FIELD, phone.getManufacturerName());
			asset.setPhoneFieldValueByFieldName(PhoneConstants.MANUFACT_RAW_FIELD, phone.getManufacturerRaw());
			asset.setPhoneFieldValueByFieldName(PhoneConstants.PHONE_NAME_RAW_FIELD, phone.getPhoneNameRaw());
			asset.setPhoneFieldValueByFieldName(PhoneConstants.DISCLAIMER_MINI_FIELD, phone.getDisclaimerMini());				
			asset.setPhoneFieldValueByFieldName(PhoneConstants.DISCLAIMER_FULL_FIELD, phone.getDisclaimerFull());
			asset.setPhoneFieldValueByFieldName(PhoneConstants.FOOTER_LEGAL_FIELD, phone.getFooterLegal());

			//asset.setPhoneFieldValueByFieldName(PhoneConstants.FEATURE_MEMORY_FIELD, p.getFeature());
			asset.setPhoneFieldValueByFieldName(PhoneConstants.FEATURE_BAR_FIELD, phone.getFeatureBar());
			asset.setPhoneFieldValueByFieldName(PhoneConstants.FEATURE_SLIDER_FIELD, phone.getFeatureSlider());
			asset.setPhoneFieldValueByFieldName(PhoneConstants.FEATURE_FLIP_FIELD, phone.getFeatureFlip());
			asset.setPhoneFieldValueByFieldName(PhoneConstants.FEATURE_TOUCHSCREEN_FIELD, phone.getFeatureTouchScreen());
			asset.setPhoneFieldValueByFieldName(PhoneConstants.FEATURE_QWERTY_FIELD, phone.getFeatureQWERTY());
			asset.setPhoneFieldValueByFieldName(PhoneConstants.FEATURE_CAMERA_FIELD, phone.getFeatureCamera());
			asset.setPhoneFieldValueByFieldName(PhoneConstants.FEATURE_FRONT_FAC_CAM_FIELD, phone.getFeatureFronFacingCamera());
			asset.setPhoneFieldValueByFieldName(PhoneConstants.FEATURE_GPS_FIELD, phone.getFeatureGPS());
			asset.setPhoneFieldValueByFieldName(PhoneConstants.FEATURE_4GWIMAX_FIELD, phone.getFeature4GWimax());
			asset.setPhoneFieldValueByFieldName(PhoneConstants.FEATURE_WIFI_FIELD, phone.getFeatureWiFi());
			asset.setPhoneFieldValueByFieldName(PhoneConstants.FEATURE_BLUETOOTH_FIELD, phone.getFeatureBluetooth());
			asset.setPhoneFieldValueByFieldName(PhoneConstants.FEATURE_SPEAKERPHONE_FIELD, phone.getFeatureSpeakerphone());
			asset.setPhoneFieldValueByFieldName(PhoneConstants.FEATURE_3G_FIELD, phone.getFeature3G());
			asset.setPhoneFieldValueByFieldName(PhoneConstants.FEATURE_4GLTE_FIELD, phone.getFeature4GLTE());
			asset.setPhoneFieldValueByFieldName(PhoneConstants.FEATURE_4G_FIELD, phone.getFeature4G());
			asset.setPhoneFieldValueByFieldName(PhoneConstants.FEATURE_TEXT_FIELD, phone.getFeatureText());
			asset.setPhoneFieldValueByFieldName(PhoneConstants.FEATURE_VIDEO_FIELD, phone.getFeatureVideo());
			asset.setPhoneFieldValueByFieldName(PhoneConstants.FEATURE_HOTSPOT_FIELD, phone.getFeatureHotspot());
			asset.setPhoneFieldValueByFieldName(PhoneConstants.FEATURE_EMAIL_FIELD, phone.getFeatureEmail());
			asset.setPhoneFieldValueByFieldName(PhoneConstants.FEATURE_HTML_BROWSER_FIELD, phone.getFeatureHTMLBrowser());
			asset.setPhoneFieldValueByFieldName(PhoneConstants.FEATURE_ULE_CERTIFIED_FIELD, phone.getFeatureULECertified());
			asset.setPhoneFieldValueByFieldName(PhoneConstants.FEATURE_MUSIC_FIELD, phone.getFeatureMusic());
			asset.setPhoneFieldValueByFieldName(PhoneConstants.FEATURE_VISUAL_VOICE_MAIL_FIELD, phone.getFeatureVisualVoicemail());
			asset.setPhoneFieldValueByFieldName(PhoneConstants.FEATURE_IS_PREMIUM_FIELD, phone.getIsPremium());
			asset.setPhoneFieldValueByFieldName(PhoneConstants.SYNONYM_FIELD, phone.getSynonyms());
			asset.setPhoneFieldValueByFieldName(PhoneConstants.ADDWORDS_LID_FIELD, phone.getAdwordsLid());
			asset.setPhoneFieldValueByFieldName(PhoneConstants.ADDWORDS_S_KWGID_FIELD, phone.getAdwordsDsSKwgid());
			asset.setPhoneFieldValueByFieldName(PhoneConstants.BING_LID_FIELD, phone.getBingLid());
			asset.setPhoneFieldValueByFieldName(PhoneConstants.BING_KWGID_FIELD, phone.getBingDsSKwgid());
			asset.setPhoneFieldValueByFieldName(PhoneConstants.ACCESSORY_LIST_FIELD, getAccessories(phone.getAccessories()));
			asset.setPhoneFieldValueByFieldName(PhoneConstants.COMPARE_ITEM_OS_FIELD, phone.getCompareItemOS());
			asset.setPhoneFieldValueByFieldName(PhoneConstants.COMPARE_ITEM_DISPLAY_FIELD, phone.getCompareItemDisplay());
			asset.setPhoneFieldValueByFieldName(PhoneConstants.COMPARE_ITEM_CAMERA_FIELD, phone.getCompareItemCamera());
			asset.setPhoneFieldValueByFieldName(PhoneConstants.COMPARE_ITEM_WIFI_FIELD, phone.getCompareItemWifi());
			asset.setPhoneFieldValueByFieldName(PhoneConstants.COMPARE_ITEM_4G_FIELD, phone.getCompareItem4G());
			asset.setPhoneFieldValueByFieldName(PhoneConstants.COMPARE_ITEM_HOTSPOT_FIELD, phone.getCompareItemHotspot());
			asset.setPhoneFieldValueByFieldName(PhoneConstants.COMPARE_ITEM_QWERTY_KEYBOARD_FIELD, phone.getCompareItemQWERTYKeyboard());
			asset.setPhoneFieldValueByFieldName(PhoneConstants.COMPARE_ITEM_WEBBROWSER_FIELD, phone.getCompareItemWebBrowser());
			asset.setPhoneFieldValueByFieldName(PhoneConstants.COMPARE_ITEM_FLASH_PLAYER_FIELD, phone.getCompareItemFlashPlayer());
			asset.setPhoneFieldValueByFieldName(PhoneConstants.COMPARE_ITEM_EMAIL_FIELD, phone.getCompareItemEmail());
			asset.setPhoneFieldValueByFieldName(PhoneConstants.COMPARE_ITEM_VIDEO_FIELD, phone.getCompareItemVideo());
			asset.setPhoneFieldValueByFieldName(PhoneConstants.COMPARE_ITEM_MUSIC_PLAYER_FIELD, phone.getCompareItemMusicPlayer());
			asset.setPhoneFieldValueByFieldName(PhoneConstants.COMPARE_ITEM_GPS_FIELD, phone.getCompareItemGPS());
			asset.setPhoneFieldValueByFieldName(PhoneConstants.COMPARE_ITEM_SPEAKERPHONE_FIELD, phone.getCompareItemSpeakerphone());
			asset.setPhoneFieldValueByFieldName(PhoneConstants.COMPARE_ITEM_MEMORY_FIELD, phone.getCompareItemMemory());
			asset.setPhoneFieldValueByFieldName(PhoneConstants.COMPARE_ITEM_PROCESSOR_FIELD, phone.getCompareItemProcessor());
			asset.setPhoneFieldValueByFieldName(PhoneConstants.COMPARE_ITEM_CALENDAR_FIELD, phone.getCompareItemCalendar());
			asset.setPhoneFieldValueByFieldName(PhoneConstants.COMPARE_ITEM_VISUAL_VOICE_MAIL_FIELD, phone.getCompareItemVisualVoicemail());
			asset.setPhoneFieldValueByFieldName(PhoneConstants.COMPARE_ITEM_3G_FIELD, phone.getCompareItem3G());
			asset.setPhoneFieldValueByFieldName(PhoneConstants.COMPARE_ITEM_BLUETOOTH_FIELD, phone.getCompareItemBluetooth());
			asset.setPhoneFieldValueByFieldName(PhoneConstants.FEATURES_FIELD, phone.getFeatureList());
			asset.setPhoneFieldValueByFieldName(PhoneConstants.SPEC_FEAT_COUNT_LIST_FIELD, phone.getSpecialFeatureList().size()+"");
			asset.setPhoneFieldValueByFieldName(PhoneConstants.SPEC_FEATURES_FIELD, phone.getSpecialFeatureList());
			asset.setPhoneFieldValueByFieldName(PhoneConstants.TECH_SPEC_GROUP_COUNT_FIELD, phone.getGroupList().size()+"");
			asset.setPhoneFieldValueByFieldName(PhoneConstants.TECH_SPEC_VARIANT_FIELD, phone.getGroupList());
			asset.setPhoneFieldValueByFieldName(PhoneConstants.PICTURES_FIELD, phone.getGalleryImages());	
						
			if (phone.isPreowned().equalsIgnoreCase("true")) {
				setAssetName(getAssetName() + "_preowned");
			}
		
	
		return asset;
	}
	

}
