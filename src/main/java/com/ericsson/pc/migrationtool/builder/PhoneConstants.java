package com.ericsson.pc.migrationtool.builder;

import com.ericsson.pc.migrationtool.util.ApplicationPropertiesReader;

public class PhoneConstants {
	
	public static final String NAMESPACE = "http://www.w3.org/2001/XMLSchema-instance";
	public static final String PREFIX = "xmlns:xsi";
	public static final String FILE_EXTENSION = "_asset.xml";
	public static final String FEATURES_EXTENSION = "_features.xml";
	public static final String SPEC_FEATURES_EXTENSION = "_spec_features.xml";
	public static final String TECHSPEC_EXTENSION = "_techspec_features.xml";
	
	//asset fields
	public static final String ASSET_FIELD = "asset";
	public static final String PHONE_FIELD = "phone";
	public static final String EXTERNAL_ID = "externalId";
	public static final String DEPLOYED_FIELD = "deployed";
	public static final String SERVICE_FIELD = "service";
	public static final String BRAND_ID_FIELD = "brandId";
	public static final String MSRP_FIELD = "msrp";
	public static final String EQP_FIELD = "eqp";
	public static final String PHONE_THEME_START_DATE_FIELD = "phoneThemeStartDate";
	public static final String PHONE_THEME_START_VALIDUNTIL_FIELD = "phoneThemeVailidUntil";
	public static final String PHONE_THEME_NAME_FIELD = "phoneThemeName";
	public static final String PPROMOTION_EXPIRY_DATE_FIELD = "promotionExpiryDate";	
	public static final String MIN_ADV_PRICE_FIELD = "minimumAdvPrice";
	public static final String OOS_THRESHOLD_FIELD = "oosThreshold";
	public static final String AVAILABLE_FIELD = "available";
	public static final String AVAILABLE_4_PREORDER_FIELD = "available4preorder";
	public static final String GENERAL_AV_DATE_FIELD = "generalAvailabilityDate";
	public static final String OVERRIDE_OOS_FIELD = "overrideOOS";
	public static final String PHONE_ID_FIELD = "phoneId";
	public static final String GROUP_ID_FIELD = "groupId";
	public static final String DEFAULT_VARIANT_ID_FIELD = "defaultVariantId";	
	public static final String BAZAAR_VOICE_VAR_ID_FIELD = "bazaarVoiceVariationId";
	public static final String VARIANT_PRIORITY_FIELD = "variantPriority";
	public static final String GROUP_PURCH_LIMIT_FIELD = "groupPurchaseLimit";	
	public static final String IS_REDVENTURES_FIELD = "isRedVentures";	
	public static final String IMPORT_PATH_FIELD = "importPath";	
	public static final String GENIE_ORDER_FIELD = "genieOrder";	
	public static final String IS_NEW_FIELD = "isNew";	
	public static final String IS_PREOWNED_FIELD = "isPreOwned";
	public static final String IS_HIDDEN_FIELD = "isHidden";
	public static final String DATE_LAUNCH_FIELD = "dateLaunch";
	public static final String IS_EOL_FIELD = "isEOL";
	public static final String SHORT_DESC_FIELD = "shortDescription";
	public static final String EXT_DESC_FIELD = "extendedDescription";
	public static final String PHONE_NAME_FIELD = "phoneName";
	public static final String MANUFACT_NAME_FIELD = "manufacturerName";
	public static final String MANUFACT_RAW_FIELD = "manufacturerRaw";
	public static final String PHONE_NAME_RAW_FIELD = "phoneNameRaw";
	public static final String DISCLAIMER_MINI_FIELD = "disclaimerMini";
	public static final String DISCLAIMER_FULL_FIELD = "disclaimerFull";
	public static final String FOOTER_LEGAL_FIELD = "footerLegal";
	public static final String COLOR_VARIANT_FIELD = "colorVariant";
	public static final String COLOR_ID_FIELD = "colorId";
	public static final String GRADIENT_COLOR_FIELD = "gradientColor";
	public static final String MEMORY_VARIANT_FIELD = "memoryVariant";	
	public static final String FEATURE_MEMORY_FIELD = "featureMemory";
	public static final String FEATURE_BAR_FIELD = "featureBar";
	public static final String FEATURE_SLIDER_FIELD = "featureSlider";
	public static final String FEATURE_FLIP_FIELD = "featureFlip";
	public static final String FEATURE_TOUCHSCREEN_FIELD = "featureTouchScreen";
	public static final String FEATURE_QWERTY_FIELD = "featureQwerty";
	public static final String FEATURE_CAMERA_FIELD = "featureCamera";
	public static final String FEATURE_FRONT_FAC_CAM_FIELD = "featureFrontFacingCamera";
	public static final String FEATURE_GPS_FIELD = "featureGPS";
	public static final String FEATURE_4GWIMAX_FIELD = "feature4GWiMax";
	public static final String FEATURE_WIFI_FIELD = "featureWifi";
	public static final String FEATURE_BLUETOOTH_FIELD = "featureBluetooth";
	public static final String FEATURE_SPEAKERPHONE_FIELD = "featureSpeakerPhone";
	public static final String FEATURE_3G_FIELD = "feature3G";
	public static final String FEATURE_4GLTE_FIELD = "feature4GLTE";
	public static final String FEATURE_4G_FIELD = "feature4G";
	public static final String FEATURE_TEXT_FIELD = "featureText";
	public static final String FEATURE_VIDEO_FIELD = "featureVideo";
	public static final String FEATURE_HOTSPOT_FIELD = "featureHotSpot";
	public static final String FEATURE_EMAIL_FIELD = "featureEmail";
	public static final String FEATURE_HTML_BROWSER_FIELD = "featureHtmlBrowser";
	public static final String FEATURE_ULE_CERTIFIED_FIELD = "featureUleCertified";
	public static final String FEATURE_MUSIC_FIELD = "featureMusic";
	public static final String FEATURE_VISUAL_VOICE_MAIL_FIELD = "featureVisualVoiceMail";
	public static final String FEATURE_IS_PREMIUM_FIELD = "isPremium";
	public static final String SYNONYM_FIELD = "synonym";	
	public static final String ADDWORDS_LID_FIELD = "addwordsLID";
	public static final String ADDWORDS_S_KWGID_FIELD = "addwordsDS_S_KWGID";
	public static final String BING_LID_FIELD = "bing_lid";
	public static final String BING_KWGID_FIELD = "bing_kwgid";
	public static final String ACCESSORY_LIST_FIELD = "associatedAccessoryId";	
	public static final String COMPARE_ITEM_OS_FIELD = "compareItemOS";
	public static final String COMPARE_ITEM_DISPLAY_FIELD = "compareItemDisplay";
	public static final String COMPARE_ITEM_CAMERA_FIELD = "compareItemCamera";
	public static final String COMPARE_ITEM_WIFI_FIELD = "compareItemWifi";
	public static final String COMPARE_ITEM_4G_FIELD = "compareItem4G";
	public static final String COMPARE_ITEM_HOTSPOT_FIELD = "compareItemHotspot";
	public static final String COMPARE_ITEM_QWERTY_KEYBOARD_FIELD = "compareItemQWERTYKeyboard";
	public static final String COMPARE_ITEM_WEBBROWSER_FIELD = "compareItemWebBrowser";
	public static final String COMPARE_ITEM_FLASH_PLAYER_FIELD = "compareItemFlashPlayer";
	public static final String COMPARE_ITEM_EMAIL_FIELD = "compareItemEmail";
	public static final String COMPARE_ITEM_VIDEO_FIELD = "compareItemVideo";
	public static final String COMPARE_ITEM_MUSIC_PLAYER_FIELD = "compareItemMusicPlayer";
	public static final String COMPARE_ITEM_GPS_FIELD = "compareItemGPS";
	public static final String COMPARE_ITEM_SPEAKERPHONE_FIELD = "compareItemSpeakerphone";
	public static final String COMPARE_ITEM_MEMORY_FIELD = "compareItemMemory";
	public static final String COMPARE_ITEM_PROCESSOR_FIELD = "compareItemProcessor";
	public static final String COMPARE_ITEM_CALENDAR_FIELD = "compareItemCalendar";
	public static final String COMPARE_ITEM_VISUAL_VOICE_MAIL_FIELD = "compareItemVisualVoicemail";
	public static final String COMPARE_ITEM_3G_FIELD = "compareItem3G";
	public static final String COMPARE_ITEM_BLUETOOTH_FIELD = "compareItemBluetooth";
	public static final String EXTRA_FEAT_COUNT_FIELD = "extraFeatureCount";
	public static final String FEATURES_FIELD = "features";
	public static final String SPEC_FEAT_COUNT_LIST_FIELD = "specialFeatureListCount";
	public static final String SPEC_FEATURES_FIELD = "specialFeatures";
	public static final String TECH_SPEC_GROUP_COUNT_FIELD = "techSpecsGroupCount";
	public static final String TECH_SPEC_VARIANT_FIELD = "techSpecs";
	public static final String PICTURES_FIELD = "pictures";
	
	
	//asset default values for mandatory fields
	public static final String ASSET_VALUE = "asset";
	public static final String PHONE_VALUE = "phone";
	public static final String EXTERNAL_ID_VALUE="id";
	public static final String DEPLOYED_VALUE="true";
	public static final String SERVICE_VALUE= ApplicationPropertiesReader.getInstance().getProperty("builder.asset.phone.serviceId");
	public static final String BRAND_ID_VALUE = ApplicationPropertiesReader.getInstance().getProperty("builder.asset.phone.brandId");
	public static final String MSRP_VALUE = ApplicationPropertiesReader.getInstance().getProperty("builder.asset.phone.defaultMsrpValue");
	public static final String EQP_VALUE = ApplicationPropertiesReader.getInstance().getProperty("builder.asset.phone.defaultEqpValue");
	public static final String OOS_THRESHOLD_VALUE = ApplicationPropertiesReader.getInstance().getProperty("builder.asset.phone.defaultOOSTresholdValue");
	public static final String AVAILABLE_VALUE = "true";	
	public static final String PHONE_ID_VALUE = "phoneId";	
	public static final String IS_REDVENTURES_VALUE = "false";
	public static final String SHORT_DESC_VALUE = "description";
	public static final String PHONE_NAME_VALUE = "phoneName";
	public static final String MANUFACT_NAME_VALUE = "manufacturer";
	public static final String MANUFACT_RAW_VALUE = "phoneName";
	public static final String EXTRA_FEAT_COUNT_VALUE = "0";
	public static final String SPEC_FEAT_COUNT = "0";

	
	
	
	
	
	
	
	

}
