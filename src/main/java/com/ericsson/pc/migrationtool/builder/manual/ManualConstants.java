package com.ericsson.pc.migrationtool.builder.manual;

import com.ericsson.pc.migrationtool.util.ApplicationPropertiesReader;

public class ManualConstants {
	
	public static final String EXTERNAL_ID = "externalId";
	public static final String DEPLOYED_FIELD = "deployed";
	public static final String SERVICE_FIELD = "service";
	public static final String BRAND_ID_FIELD = "brandId";
	public static final String FILENAME = "fileName";
	public static final String USER_GUIDE_IMAGE = "userGuideImage";	
	public static final String USER_GUIDE_TITLE_IMAGE = "userGuideTitleImage";
	public static final String MANUAL_PDF = "manual";
	
	public static final String SERVICE_VALUE = ApplicationPropertiesReader.getInstance().getProperty("builder.asset.manual.serviceId");
	public static final String FILENAME_VALUE = "filename";
	public static final String PHONE_MANUAL = "phone_manual";
	public static final String ID_FIELD = "id";
	
	public static final String FILE_EXTENSION = "_manual_asset.xml";

}

