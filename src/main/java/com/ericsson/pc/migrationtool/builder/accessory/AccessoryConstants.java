package com.ericsson.pc.migrationtool.builder.accessory;

import com.ericsson.pc.migrationtool.util.ApplicationPropertiesReader;

public class AccessoryConstants {
	
	public static final String NAMESPACE = "http://www.w3.org/2001/XMLSchema-instance";
	public static final String PREFIX = "xmlns:xsi";
	public static final String FILE_EXTENSION = "_asset.xml";

	
	//asset fields
	public static final String ASSET_FIELD = "asset";
	public static final String PHONE_FIELD = "phone";
	public static final String EXTERNAL_ID_FIELD = "externalId";
	public static final String DEPLOYED_FIELD = "deployed";
	public static final String SERVICE_FIELD = "service";
	public static final String SKU_FIELD = "sku";
	public static final String BRAND_ID_FIELD = "brandId";
	public static final String MIN_ADV_PRICE_FIELD = "minimumAdvPrice";
	public static final String THEME_NAME_FIELD = "themeName";
	public static final String THEME_START_DATE_FIELD = "themeStartDate";	
	public static final String THEME_STOP_DATE_FIELD = "themeStopDate";		
	public static final String ASSET_DESCRIPTION_FIELD = "assetDescription";
	public static final String LABEL_FIELD = "label";
	public static final String OOS_THRESHOLD_FIELD = "oosThreshold";	
	public static final String AVAILABLE_FIELD = "available";	
	public static final String AVAILABLE_4_PREORDER_FIELD = "available4preorder";
	public static final String GENERAL_AV_DATE_FIELD = "generalAvailabilityDate";
	public static final String OVERRIDE_OOS_FIELD = "overrideOOS";
	public static final String GROUP_ID_FIELD = "groupId";
	public static final String DEFAULT_VARIANT_ID_FIELD = "defaultVariantId";	
	public static final String BAZAAR_VOICE_VAR_ID_FIELD = "bazaarVoiceVariationId";	
	public static final String VARIANT_PRIORITY_FIELD = "variantPriority";
	public static final String GROUP_PURCH_LIMIT_FIELD = "groupPurchaseLimit";
	public static final String PRICE_START_DATE = "startDate";
	public static final String ORIGINAL_PRICE = "msrp";
	public static final String SALE_PRICE = "eqp";
	public static final String TYPE = "type";
	public static final String PICTURE_SHOP = "shopImage";
	public static final String PICTURE_CHECKOUT = "checkoutImage";
	
	//asset default values for mandatory fields
	public static final String ASSET_VALUE = "asset";
	public static final String ACCESSORY_VALUE = "accessory";
	public static final String EXTERNAL_ID_VALUE="id";
	public static final String DEPLOYED_VALUE="true";
	public static final String SERVICE_VALUE= ApplicationPropertiesReader.getInstance().getProperty("builder.asset.accessory.serviceId");
	public static final String BRAND_ID_VALUE = ApplicationPropertiesReader.getInstance().getProperty("builder.asset.phone.brandId");
	public static final String ORIGINAL_PRICE_VALUE = ApplicationPropertiesReader.getInstance().getProperty("builder.asset.phone.defaultMsrpValue");
	public static final String SALE_PRICE_VALUE = ApplicationPropertiesReader.getInstance().getProperty("builder.asset.phone.defaultEqpValue");
	public static final String OOS_THRESHOLD_VALUE = ApplicationPropertiesReader.getInstance().getProperty("builder.asset.phone.defaultOOSTresholdValue");
	public static final String AVAILABLE_VALUE = "true";	
	public static final String TYPE_VALUE = "accessory";



	
	
	
	
	
	
	
	

}
