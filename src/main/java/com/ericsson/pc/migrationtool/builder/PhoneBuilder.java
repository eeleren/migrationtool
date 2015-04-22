package com.ericsson.pc.migrationtool.builder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.ericsson.pc.migrationtool.bean.Accessory;
import com.ericsson.pc.migrationtool.bean.Feature;
import com.ericsson.pc.migrationtool.bean.Group;
import com.ericsson.pc.migrationtool.bean.Model;
import com.ericsson.pc.migrationtool.bean.Phone;
import com.ericsson.pc.migrationtool.bean.SpecialFeature;
import com.ericsson.pc.migrationtool.bean.VariantPhone;
import com.ericsson.pc.migrationtool.bean.Variation;
import com.ericsson.pc.migrationtool.builder.phone.ImageBuilder;
import com.ericsson.pc.migrationtool.builder.phone.PhoneConstants;
import com.ericsson.pc.migrationtool.builder.phone.VariantBuilder;
import com.ericsson.pc.migrationtool.msdp.AssetField;
import com.ericsson.pc.migrationtool.msdp.ImageItem;
import com.ericsson.pc.migrationtool.msdp.PhoneAssetStructure;
import com.ericsson.pc.migrationtool.util.ApplicationPropertiesReader;
import com.ericsson.pc.migrationtool.util.PathUtil;
import com.ericsson.pc.migrationtool.util.XmlDocumentUtil;

public class PhoneBuilder extends Builder {

	final static String buildAccessories = ApplicationPropertiesReader.getInstance().getProperty("builder.asset.buildAccessories");
	final static String brandId = ApplicationPropertiesReader.getInstance().getProperty("builder.asset.phone.brandId");

	final String featureExtension = PhoneConstants.FEATURES_EXTENSION;
	final String specFeatureExtension = PhoneConstants.SPEC_FEATURES_EXTENSION;
	final String techSpecExtension = PhoneConstants.TECHSPEC_EXTENSION;

	private String slug;
	private String colorVariant = "";

	@Override
	public Builder getNewInstance() {
		return new PhoneBuilder();
	}

	public void createAssets(List<Model> models) {

		PhoneBuilder builder = new PhoneBuilder();
		List<Phone> phones = new ArrayList<Phone>();

		for (Model m : models) {
			if (m instanceof Phone)
				phones.add((Phone) m);
		}

		builder.cleanOutputDir();
		buildAccessories(phones);

		logger.info("BUILDING EXECUTION STARTED...");

		for (Phone p : phones) {
			builder.setSlug(p.getSlug());
			if (p.hasVariations(p.getSlug())) {
				logger.debug("[" + p.getSku() + "] Phone Asset: " + p.getManufacturerRaw() + " " + p.getPhoneNameRaw() + " building start");
				logger.debug("Phone has variation..");
				builder.createPhoneVariations(p);

			} else {
				logger.debug("[" + p.getSku() + "] Phone Asset: " + p.getManufacturerRaw() + " " + p.getPhoneNameRaw() + " building start");
				logger.debug("Phone has not variation..");
				builder.setAssetName(p.getManufacturerRaw(), p.getPhoneNameRaw());
				builder.createPhoneAsset(new VariantPhone(p));
			}
		}
		PathUtil.cleanAssetDirectory();
		logger.info("BUILDER EXECUTION COMPLETED!");

	}

	public void createPhoneVariations(Phone phone) {
		VariantPhone variantPhone = new VariantPhone(phone);
		List<Variation> variationList = phone.getVariations();

		for (Variation v : variationList) {
			variantPhone.setSku(v.getId());
			variantPhone.setOosThresholdOverride(v.getOosThresholdOverride());
			variantPhone.setMap(v.getMap());
			variantPhone.setColorVariant(v.getColorVariant());
			variantPhone.setGradientColor(v.getGradientColor());
			variantPhone.setMemoryVariant(v.getMemoryVariant());
			variantPhone.setIsParent("false");

			setAssetName(variantPhone.getManufacturerRaw(), variantPhone.getPhoneNameRaw(), variantPhone.getColorVariant(), variantPhone.getMemoryVariant());
			setVariantColor(variantPhone.getColorVariant());
			setAssetOutputDir(getAssetName());
			createPhoneAsset(variantPhone);
		}
	}

	public void createPhoneAsset(VariantPhone phone) {
		PhoneAssetStructure asset = buildAssetStructure(phone);
		createXml(asset);
	}

	/**
	 * Translates a phone data coming from boost into msdp phone assets
	 * structure
	 * */
	public PhoneAssetStructure buildAssetStructure(VariantPhone p) {
		PhoneAssetStructure asset = new PhoneAssetStructure();
		asset.init();
		// setting asset fields with boost values

		asset.setPhoneFieldValueByFieldName(PhoneConstants.MIN_ADV_PRICE_FIELD,	p.getMap());
		asset.setPhoneFieldValueByFieldName(PhoneConstants.OVERRIDE_OOS_FIELD, p.getOosThresholdOverride());
		asset.setPhoneFieldValueByFieldName(PhoneConstants.GENERAL_AV_DATE_FIELD, p.getDateLaunch());
		asset.setPhoneFieldValueByFieldName(PhoneConstants.EXTERNAL_URL, p.getExternalUrl());
		asset.setPhoneFieldValueByFieldName(PhoneConstants.SKU, p.getSku());
		//asset.setPhoneFieldValueByFieldName(PhoneConstants.GROUP_ID_FIELD, p.getDefault_id());
		asset.setPhoneFieldValueByFieldName(PhoneConstants.VARIANT_LIST_ORDER_FIELD,getVariantlistOrder(p.getVariations()));
		asset.setPhoneFieldValueByFieldName(PhoneConstants.IS_REDVENTURES_FIELD, p.getRedVentures());
		asset.setPhoneFieldValueByFieldName(PhoneConstants.GENIE_ORDER_FIELD,p.getGenieOrder());
		asset.setPhoneFieldValueByFieldName(PhoneConstants.IS_NEW_FIELD, p.getIsNew());
		asset.setPhoneFieldValueByFieldName(PhoneConstants.IS_PREOWNED_FIELD, p.isPreowned());
		asset.setPhoneFieldValueByFieldName(PhoneConstants.IS_EOL_FIELD, p.getEol());
		asset.setPhoneFieldValueByFieldName(PhoneConstants.IS_PARENT, p.getIsParent());
		asset.setPhoneFieldValueByFieldName(PhoneConstants.SHORT_DESC_FIELD, p.getShortDescription());
		asset.setPhoneFieldValueByFieldName(PhoneConstants.EXT_DESC_FIELD, p.getExtendedDescription());
		asset.setPhoneFieldValueByFieldName(PhoneConstants.PHONE_NAME_FIELD, normalized(p.getPhoneName()));
		asset.setPhoneFieldValueByFieldName(PhoneConstants.MANUFACT_NAME_FIELD, p.getManufacturerName());
		asset.setPhoneFieldValueByFieldName(PhoneConstants.MANUFACT_RAW_FIELD, p.getManufacturerRaw());
		asset.setPhoneFieldValueByFieldName(PhoneConstants.PHONE_NAME_RAW_FIELD, p.getPhoneNameRaw());
		asset.setPhoneFieldValueByFieldName(PhoneConstants.DISCLAIMER_MINI_FIELD, p.getDisclaimerMini());
		asset.setPhoneFieldValueByFieldName(PhoneConstants.DISCLAIMER_FULL_FIELD, p.getDisclaimerFull());
		asset.setPhoneFieldValueByFieldName(PhoneConstants.FOOTER_LEGAL_FIELD, p.getFooterLegal());
		asset.setPhoneFieldValueByFieldName(PhoneConstants.COLOR_VARIANT_FIELD, p.getColorVariant());
		asset.setPhoneFieldValueByFieldName(PhoneConstants.COLOR_ID_FIELD, p.getColorId());
		asset.setPhoneFieldValueByFieldName(PhoneConstants.GRADIENT_COLOR_FIELD, p.getGradientColor());
		asset.setPhoneFieldValueByFieldName(PhoneConstants.MEMORY_VARIANT_FIELD, p.getMemoryVariant());
		asset.setPhoneFieldValueByFieldName(PhoneConstants.PHONE_TYPE, getPhoneType(p));
		asset.setPhoneFieldValueByFieldName(PhoneConstants.FEATURE_BAR_FIELD, p.getFeatureBar());
		asset.setPhoneFieldValueByFieldName(PhoneConstants.FEATURE_SLIDER_FIELD, p.getFeatureSlider());
		asset.setPhoneFieldValueByFieldName(PhoneConstants.FEATURE_FLIP_FIELD, p.getFeatureFlip());
		asset.setPhoneFieldValueByFieldName(PhoneConstants.FEATURE_TOUCHSCREEN_FIELD, p.getFeatureTouchScreen());
		asset.setPhoneFieldValueByFieldName(PhoneConstants.FEATURE_QWERTY_FIELD, p.getFeatureQWERTY());
		asset.setPhoneFieldValueByFieldName(PhoneConstants.FEATURE_CAMERA_FIELD, p.getFeatureCamera());
		asset.setPhoneFieldValueByFieldName(PhoneConstants.FEATURE_FRONT_FAC_CAM_FIELD, p.getFeatureFronFacingCamera());
		asset.setPhoneFieldValueByFieldName(PhoneConstants.FEATURE_GPS_FIELD, p.getFeatureGPS());
		asset.setPhoneFieldValueByFieldName(PhoneConstants.FEATURE_4GWIMAX_FIELD, p.getFeature4GWimax());
		asset.setPhoneFieldValueByFieldName(PhoneConstants.FEATURE_WIFI_FIELD, p.getFeatureWiFi());
		asset.setPhoneFieldValueByFieldName(PhoneConstants.FEATURE_BLUETOOTH_FIELD, p.getFeatureBluetooth());
		asset.setPhoneFieldValueByFieldName(PhoneConstants.FEATURE_SPEAKERPHONE_FIELD, p.getFeatureSpeakerphone());
		asset.setPhoneFieldValueByFieldName(PhoneConstants.FEATURE_3G_FIELD, p.getFeature3G());
		asset.setPhoneFieldValueByFieldName(PhoneConstants.FEATURE_4GLTE_FIELD, p.getFeature4GLTE());
		asset.setPhoneFieldValueByFieldName(PhoneConstants.FEATURE_4G_FIELD, p.getFeature4G());
		asset.setPhoneFieldValueByFieldName(PhoneConstants.FEATURE_TEXT_FIELD, p.getFeatureText());
		asset.setPhoneFieldValueByFieldName(PhoneConstants.FEATURE_VIDEO_FIELD, p.getFeatureVideo());
		asset.setPhoneFieldValueByFieldName(PhoneConstants.FEATURE_HOTSPOT_FIELD, p.getFeatureHotspot());
		asset.setPhoneFieldValueByFieldName(PhoneConstants.FEATURE_EMAIL_FIELD, p.getFeatureEmail());
		asset.setPhoneFieldValueByFieldName(PhoneConstants.FEATURE_HTML_BROWSER_FIELD, p.getFeatureHTMLBrowser());
		asset.setPhoneFieldValueByFieldName(PhoneConstants.FEATURE_ULE_CERTIFIED_FIELD, p.getFeatureULECertified());
		asset.setPhoneFieldValueByFieldName(PhoneConstants.FEATURE_MUSIC_FIELD, p.getFeatureMusic());
		asset.setPhoneFieldValueByFieldName(PhoneConstants.FEATURE_VISUAL_VOICE_MAIL_FIELD, p.getFeatureVisualVoicemail());
		asset.setPhoneFieldValueByFieldName(PhoneConstants.FEATURE_IS_PREMIUM_FIELD, p.getIsPremium());
		asset.setPhoneFieldValueByFieldName(PhoneConstants.SYNONYM_FIELD, p.getSynonyms());
		asset.setPhoneFieldValueByFieldName(PhoneConstants.ADDWORDS_LID_FIELD, p.getAdwordsLid());
		asset.setPhoneFieldValueByFieldName(PhoneConstants.ADDWORDS_S_KWGID_FIELD, p.getAdwordsDsSKwgid());
		asset.setPhoneFieldValueByFieldName(PhoneConstants.BING_LID_FIELD, p.getBingLid());
		asset.setPhoneFieldValueByFieldName(PhoneConstants.BING_KWGID_FIELD, p.getBingDsSKwgid());
		asset.setPhoneFieldValueByFieldName(PhoneConstants.ACCESSORY_LIST_FIELD, getAccessories(p.getAccessories()));
		asset.setPhoneFieldValueByFieldName(PhoneConstants.COMPARE_ITEM_OS_FIELD, p.getCompareItemOS());
		asset.setPhoneFieldValueByFieldName(PhoneConstants.COMPARE_ITEM_DISPLAY_FIELD, p.getCompareItemDisplay());
		asset.setPhoneFieldValueByFieldName(PhoneConstants.COMPARE_ITEM_CAMERA_FIELD, p.getCompareItemCamera());
		asset.setPhoneFieldValueByFieldName(PhoneConstants.COMPARE_ITEM_WIFI_FIELD, p.getCompareItemWifi());
		asset.setPhoneFieldValueByFieldName(PhoneConstants.COMPARE_ITEM_4G_FIELD, p.getCompareItem4G());
		asset.setPhoneFieldValueByFieldName(PhoneConstants.COMPARE_ITEM_HOTSPOT_FIELD, p.getCompareItemHotspot());
		asset.setPhoneFieldValueByFieldName(PhoneConstants.COMPARE_ITEM_QWERTY_KEYBOARD_FIELD, p.getCompareItemQWERTYKeyboard());
		asset.setPhoneFieldValueByFieldName(PhoneConstants.COMPARE_ITEM_WEBBROWSER_FIELD, p.getCompareItemWebBrowser());
		asset.setPhoneFieldValueByFieldName(PhoneConstants.COMPARE_ITEM_FLASH_PLAYER_FIELD,	p.getCompareItemFlashPlayer());
		asset.setPhoneFieldValueByFieldName(PhoneConstants.COMPARE_ITEM_EMAIL_FIELD, p.getCompareItemEmail());
		asset.setPhoneFieldValueByFieldName(PhoneConstants.COMPARE_ITEM_VIDEO_FIELD, p.getCompareItemVideo());
		asset.setPhoneFieldValueByFieldName(PhoneConstants.COMPARE_ITEM_MUSIC_PLAYER_FIELD, p.getCompareItemMusicPlayer());
		asset.setPhoneFieldValueByFieldName(PhoneConstants.COMPARE_ITEM_GPS_FIELD, p.getCompareItemGPS());
		asset.setPhoneFieldValueByFieldName(PhoneConstants.COMPARE_ITEM_SPEAKERPHONE_FIELD,	p.getCompareItemSpeakerphone());
		asset.setPhoneFieldValueByFieldName(PhoneConstants.COMPARE_ITEM_MEMORY_FIELD, p.getCompareItemMemory());
		asset.setPhoneFieldValueByFieldName(PhoneConstants.COMPARE_ITEM_PROCESSOR_FIELD, p.getCompareItemProcessor());
		asset.setPhoneFieldValueByFieldName(PhoneConstants.COMPARE_ITEM_CALENDAR_FIELD,	p.getCompareItemCalendar());
		asset.setPhoneFieldValueByFieldName(PhoneConstants.COMPARE_ITEM_VISUAL_VOICE_MAIL_FIELD,p.getCompareItemVisualVoicemail());
		asset.setPhoneFieldValueByFieldName(PhoneConstants.COMPARE_ITEM_3G_FIELD, p.getCompareItem3G());
		asset.setPhoneFieldValueByFieldName(PhoneConstants.COMPARE_ITEM_BLUETOOTH_FIELD,p.getCompareItemBluetooth());
		asset.setPhoneFieldValueByFieldName(PhoneConstants.FEATURES_FIELD,p.getFeatureList());
	//	asset.setPhoneFieldValueByFieldName(PhoneConstants.SPEC_FEAT_COUNT_LIST_FIELD, p.getSpecialFeatureList().size() + "");
		asset.setPhoneFieldValueByFieldName(PhoneConstants.SPEC_FEATURES_FIELD,	p.getSpecialFeatureList());
/*		asset.setPhoneFieldValueByFieldName(PhoneConstants.SPEC_HEIGHT_FIELD,p.getSpecByGroupIdAndSpecType("dimensions", "height"));
		asset.setPhoneFieldValueByFieldName(PhoneConstants.SPEC_WIDTH_FIELD,p.getSpecByGroupIdAndSpecType("dimensions", "width"));
		asset.setPhoneFieldValueByFieldName(PhoneConstants.SPEC_DEPTH_FIELD,p.getSpecByGroupIdAndSpecType("dimensions", "depth"));
		asset.setPhoneFieldValueByFieldName(PhoneConstants.SPEC_WEIGHT_FIELD,p.getSpecByGroupIdAndSpecType("dimensions", "weight"));
		asset.setPhoneFieldValueByFieldName(PhoneConstants.SPEC_SCREENSIZE_FIELD,p.getSpecByGroupIdAndSpecType("dimensions", "screen_size"));
		asset.setPhoneFieldValueByFieldName(PhoneConstants.SPEC_BATTERYTYPE_FIELD,p.getSpecByGroupIdAndSpecType("battery", "battery_type"));
		asset.setPhoneFieldValueByFieldName(PhoneConstants.SPEC_TALKINGTIME_FIELD,p.getSpecByGroupIdAndSpecType("battery", "talking_time"));
		asset.setPhoneFieldValueByFieldName(PhoneConstants.SPEC_REMOVEABLE_FIELD,p.getSpecByGroupIdAndSpecType("battery", "removable"));
		asset.setPhoneFieldValueByFieldName(PhoneConstants.SPEC_MEMORY_FIELD,p.getSpecByGroupIdAndSpecType("battery", "memory"));
		asset.setPhoneFieldValueByFieldName(PhoneConstants.SPEC_PROCESSOR_FIELD, p.getSpecByGroupIdAndSpecType("battery", "processor"));
		asset.setPhoneFieldValueByFieldName(PhoneConstants.SPEC_OS_FIELD,p.getGroupValueById("os"));
		asset.setPhoneFieldValueByFieldName(PhoneConstants.TECH_SPEC_GROUP_COUNT_FIELD, p.getGroupList().size() + "");*/
		asset.setPhoneFieldValueByFieldName(PhoneConstants.TECH_SPEC_VARIANT_FIELD, p.getGroupList());
		//asset.setPhoneFieldValueByFieldName(PhoneConstants.PICTURES_PHONE_DETAILS, p.getGalleryImages());

		if (p.isPreowned().equalsIgnoreCase("true")) {
			setAssetName(getAssetName() + "_preowned");
		}

		return asset;
	}

	private String normalized(String phoneName) {
		if(phoneName.contains("#8482")) {
			phoneName.replace(phoneName.substring(phoneName.indexOf("&"), phoneName.length()), "&#8482;");
		}
		return phoneName;
	}

	/**
	 * Verify that the images linked in the boost phone.xml file have a
	 * reference in the image folder
	 * 
	 * 
	public List<ImageItem> scanAssetImages(PhoneAssetStructure asset,
			List<Item> items, ImageBuilder imageBuilder) {
		logger.debug("Setting phone images...");

		String phoneNameRaw = (String) asset.getPhoneFieldByName(
				PhoneConstants.PHONE_NAME_RAW_FIELD).getValue();
		String brandNameRaw = (String) asset.getPhoneFieldByName(
				PhoneConstants.MANUFACT_RAW_FIELD).getValue();
		List<ImageItem> imageItems = new ArrayList<ImageItem>();
		String imageDir = "";

		imageDir = ImageBuilder.getImageFolder(phoneNameRaw, brandNameRaw);
		if (imageDir == null) {
			logger.error("Image directory not found for " + phoneNameRaw
					+ "...trying with slug");
			if (getImagesFromSlug() != null) {
				imageDir = getImagesFromSlug();
			} else {
				logger.error("Image directory not found for " + phoneNameRaw
						+ " images will not be loaded");
				return null;
			}
		}

		logger.debug("image root directory set to: " + imageDir);

		imageItems = imageBuilder.getContainedImages(items, imageDir);
		return imageItems;
	}*/

	/**
	 * Return the list of images contained in the Phone Images folder, that will
	 * be included in the asset in the category "phoneDetails"
	 * 
	public List<ImageItem> getPhoneDetailsImages(PhoneAssetStructure asset, ImageBuilder imageBuilder) {
		logger.debug("Setting phone details images...");

		String phoneNameRaw = (String) asset.getPhoneFieldByName(PhoneConstants.PHONE_NAME_RAW_FIELD).getValue();
		String brandNameRaw = (String) asset.getPhoneFieldByName(PhoneConstants.MANUFACT_RAW_FIELD).getValue();
		List<ImageItem> imageItems = new ArrayList<ImageItem>();
		String imageDir = "";

		imageDir = ImageBuilder.getImageFolder(phoneNameRaw, brandNameRaw);
		if (imageDir == null) {
			logger.error("Image directory not found for " + phoneNameRaw + "...trying with slug");
			if (getImagesFromSlug() != null) {
				imageDir = getImagesFromSlug();
			} else {
				logger.error("Image directory not found for " + phoneNameRaw + " images will not be loaded");
				return null;
			}
		}

		logger.debug("image root directory set to: " + imageDir);

		imageItems = imageBuilder.getContainedImages(items, imageDir);
		return imageItems;
	}*/
	

	/** Returns the phone image main dir 
	public String getImageDir(PhoneAssetStructure asset) {
		String phoneNameRaw = (String) asset.getPhoneFieldByName(PhoneConstants.PHONE_NAME_RAW_FIELD).getValue();
		String brandNameRaw = (String) asset.getPhoneFieldByName(PhoneConstants.MANUFACT_RAW_FIELD).getValue();
		String imageDir = "";

		imageDir = ImageBuilder.getImageFolder(phoneNameRaw, brandNameRaw);
		if (imageDir == null) {
			logger.error("Image directory not found for " + phoneNameRaw + "...trying with slug");
			if (getImageDirFromSlug() != null) {
				imageDir = getImageDirFromSlug();
			} else {
				logger.error("Image directory not found for " + phoneNameRaw + " images will not be loaded");
				return null;
			}
		}

		logger.debug("image root directory set to: " + imageDir);
		return imageDir;
	}*/

	public String getImageDirFromSlug() {
		String slug = this.getSlug();
		String imageDir = ImageBuilder.getIMAGE_FILE_PATH() + File.separator+ slug;
		return imageDir;
	}

	@SuppressWarnings("unchecked")
	public void createXml(PhoneAssetStructure assetPhone) {

		setAssetOutputDir(getAssetName());
		PathUtil.createAssetOutputDir(getAssetOutputDir());
		String filename = getAssetName();
		List<AssetField> fieldList = assetPhone.getPhoneFieldList();

		try {
			
			Document doc = XmlDocumentUtil.createDocument();
			XmlDocumentUtil.addNodeWithNSAttribute(doc, PhoneConstants.ASSET_FIELD, PhoneConstants.PREFIX, PhoneConstants.NAMESPACE);
			

			Element rootElement = doc.createElement(PhoneConstants.PHONE_FIELD);
			String externalId = brandId+ "_" + (String) assetPhone.getPhoneFieldByName(PhoneConstants.SKU).getValue();
			rootElement.setAttribute(PhoneConstants.EXTERNAL_ID, externalId);
			doc.getElementsByTagName(PhoneConstants.ASSET_FIELD).item(0).appendChild(rootElement);

			// iteration over PhoneField elements
			for (AssetField f : fieldList) {
				if (f.isMandatory()) {
					Element e = doc.createElement(f.getName());
					if (!"".equalsIgnoreCase((String)f.getValue()) && (f.getValue() != null)) {
						e.appendChild(doc.createTextNode((String) f.getValue()));
					} else {
						e.appendChild(doc.createTextNode(f.getDefaultValue()));
					}
					rootElement.appendChild(e);

					// analysis of variants elements: pictures, extrafeatures, specialfeatures, techspec
				} else if (f.getName().equals(PhoneConstants.FEATURES_FIELD) && (f.getValue() != null)) {
					// the phone asset contains features variants
					logger.debug("creating xml structure for features");

					VariantBuilder variantBuilder = new VariantBuilder();
					variantBuilder.setAssetName(getAssetName());
					variantBuilder.setFeaturesName(getAssetName() + featureExtension);
					variantBuilder.setFeatureFile(outputDir + assetName + File.separator + assetName + featureExtension);
					variantBuilder.createPhoneFeatures((List<Feature>) f.getValue());

					Element e = doc.createElement(f.getName());// features element
					Element variant = doc.createElement("variant");
					Element item = doc.createElement("item");
					item.setAttribute("uri", variantBuilder.getFeaturesName());
					variant.appendChild(item);
					e.appendChild(variant);
					rootElement.appendChild(e);

				} else if (f.getName().equals(PhoneConstants.SPEC_FEATURES_FIELD) && (f.getValue() != null)) {
					// the phone asset contains features variants
					logger.debug("creating xml structure for special features");

					VariantBuilder variantBuilder = new VariantBuilder();
					variantBuilder.setAssetName(getAssetName());
					variantBuilder.setSpecFeaturesName(getAssetName() + specFeatureExtension);
					variantBuilder.setSpecFeatureFile(outputDir + assetName + File.separator + assetName + specFeatureExtension);
					variantBuilder.createPhoneSpecialFeatures((List<SpecialFeature>) f.getValue());

					Element e = doc.createElement(f.getName());// elemento
																// features
					Element variant = doc.createElement("variant");
					Element item = doc.createElement("item");
					item.setAttribute("uri", variantBuilder.getSpecFeaturesName());
					variant.appendChild(item);
					e.appendChild(variant);
					rootElement.appendChild(e);

				} else if (f.getName().equals(PhoneConstants.TECH_SPEC_VARIANT_FIELD) && (f.getValue() != null)) {
					// the phone asset contains features variants
					logger.debug("creating xml structure for technical specifications..");

					VariantBuilder variantBuilder = new VariantBuilder();
					variantBuilder.setAssetName(getAssetName());
					variantBuilder.setTechSpecName(getAssetName() + techSpecExtension);
					variantBuilder.setTechSpecFile(outputDir + assetName + File.separator + assetName + techSpecExtension);
					variantBuilder.createPhoneTechSpec((List<Group>) f.getValue());

					Element e = doc.createElement(f.getName());// elemento	// features
					Element variant = doc.createElement("variant");
					Element item = doc.createElement("item");
					item.setAttribute("uri", variantBuilder.getTechSpecName());
					variant.appendChild(item);
					e.appendChild(variant);
					rootElement.appendChild(e);
				}

				else if (f.getName().equals(PhoneConstants.PICTURES_PHONE_DETAILS)) {
					// the phone asset contains images
					logger.debug("creating xml structure for phone details images");
					ImageBuilder imageBuilder = new ImageBuilder();
						
					List<ImageItem> images = imageBuilder.getPhoneDetailsImages(getImageDirFromSlug(), getColorVariant());
					if ((images != null) && (!images.isEmpty())) {
						Element e = doc.createElement(f.getName());
						for (ImageItem i : images) {
							Element variant = doc.createElement("variant");
							Element item = doc.createElement("item");
							item.setAttribute("uri", i.getName());
							variant.appendChild(item);
							e.appendChild(variant);
							rootElement.appendChild(e);

						}
						imageBuilder.moveImages(images, outputDir + getAssetOutputDir());
						imageBuilder.renameThumbsImages(outputDir + getAssetOutputDir());
						
												
					} else {
						logger.error("phone details images not found for asset:"
								+ filename);
					}
				} else if (f.getName().equals(PhoneConstants.PICTURES_SHOP_GRID)) {
					logger.debug("creating xml structure for shop grid images");				
					ImageBuilder imageBuilder = new ImageBuilder();
					ImageItem shopImage = imageBuilder.getShopGridImages(getImageDirFromSlug(), getColorVariant());
					Element e = doc.createElement(f.getName());// elemento
					// pictures
					if (shopImage != null) {
						
						Element variant = doc.createElement("variant");
						Element item = doc.createElement("item");
						item.setAttribute("uri", shopImage.getName());
						variant.appendChild(item);
						e.appendChild(variant);
						rootElement.appendChild(e);
						imageBuilder.moveImage(shopImage, outputDir	+ getAssetOutputDir());

					} else {
						logger.error("images not found for asset:" + filename);
					}
				} else if (f.getName().equals(PhoneConstants.PICTURES_COMPARE)) {
					logger.debug("creating xml structure for compare images");
					// images found in the directory
					ImageBuilder imageBuilder = new ImageBuilder();
					//String imageDir = getImageDir(assetPhone);
					List<ImageItem> compareImages = imageBuilder.getCompareImages(getImageDirFromSlug());
					if ((compareImages != null) && (!compareImages.isEmpty())) {
						Element e = doc.createElement(f.getName());
						for (ImageItem i : compareImages) {						
							Element variant = doc.createElement("variant");
							Element item = doc.createElement("item");
							item.setAttribute("uri", i.getName());
							variant.appendChild(item);
							e.appendChild(variant);
							rootElement.appendChild(e);
						}

						imageBuilder.moveImages(compareImages, outputDir + getAssetOutputDir());

					} else {
						logger.error("images not found for asset:" + filename);
					}
				} else if (f.getName().equals(PhoneConstants.PICTURES_CART)) {
					logger.debug("creating xml structure for cart images");
					// images found in the directory
					ImageBuilder imageBuilder = new ImageBuilder();
				//	String imageDir = getImageDir(assetPhone);
					ImageItem cartImage = imageBuilder.getCartImage(getImageDirFromSlug());
					if (cartImage != null) {
						Element e = doc.createElement(f.getName());
						Element variant = doc.createElement("variant");
						Element item = doc.createElement("item");
						item.setAttribute("uri", cartImage.getName());
						variant.appendChild(item);
						e.appendChild(variant);
						rootElement.appendChild(e);

						imageBuilder.moveImage(cartImage, outputDir	+ getAssetOutputDir());
					} else {
						logger.error("cart image not found for asset:"	+ filename);
					}
				} else if (f.getName().equals(PhoneConstants.PICTURES_FEATURES)) {
					logger.debug("creating xml structure for feature images");
					
					ImageBuilder imageBuilder = new ImageBuilder();
					List<ImageItem> featureImages = imageBuilder.getFeatureImages(getImageDirFromSlug());
					if ((featureImages != null) && (!featureImages.isEmpty())) {
						
						Element e = doc.createElement(f.getName());
						for (ImageItem i : featureImages) {						
							Element variant = doc.createElement("variant");
							Element item = doc.createElement("item");
							item.setAttribute("uri", i.getName());
							variant.appendChild(item);
							e.appendChild(variant);
							rootElement.appendChild(e);
						}
						imageBuilder.moveImages(featureImages, outputDir + getAssetOutputDir());
					} else {
						logger.error("cart image not found for asset:"	+ filename);
					}
				} else {
					if ((f.getValue() != null) && (!f.getValue().equals(""))) {
						Element e = doc.createElement(f.getName());
						e.appendChild(doc.createTextNode((String) f.getValue()));
						rootElement.appendChild(e);

					}
				}
			}

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(doc);
			File output = new File(outputDir + getAssetOutputDir()
					+ File.separator + filename + extension);
			StreamResult result = new StreamResult(output);
			transformer.transform(source, result);

			logger.info("Asset file: " + filename + extension
					+ " created and saved to: " + output);

		} catch (ParserConfigurationException pce) {

			logger.error("ParserConfigurationException :" + pce.getMessage());
		} catch (TransformerException tfe) {

			logger.error("TransoformerException: " + tfe.getMessage());
			tfe.printStackTrace();

		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}

	public void buildAccessories(List<Phone> phones) {

		if (buildAccessories.equalsIgnoreCase("true")) {
			AccessoryBuilder accBuilder = new AccessoryBuilder();
			logger.info("ACCESSORIES ASSETS BUILDING STARTED...");

			for (Phone p : phones) {

				accBuilder.setRelatedPhoneBrand(p.getManufacturerName());
				accBuilder.setRelatedPhoneName(p.getPhoneNameRaw());
				accBuilder.createAccessories(p.getAccessories());

			}

		}
	}

	public static String getAccessories(List<Accessory> accessories) {

		String accessoriesValues = "";
		String separator = ",";

		for (int i = 0; i < accessories.size(); i++) {
			accessoriesValues = accessoriesValues + accessories.get(i).getId()
					+ separator;
		}
		if (!accessoriesValues.equals("")) {
			accessoriesValues = accessoriesValues.substring(0,
					accessoriesValues.length() - 1);
		}
		return accessoriesValues;
	}

	public static String getVariantlistOrder(List<Variation> variations) {

		String variationValues = "";
		String separator = ",";

		for (int i = 0; i < variations.size(); i++) {
			variationValues = variationValues + variations.get(i).getId()+ separator;
		}
		if (!variationValues.equals("")) {
			variationValues = variationValues.substring(0,
					variationValues.length() - 1);
		}
		return variationValues;
	}

	public String getPhoneType(VariantPhone p) {
		String phoneType = "";
		List<Group> groupList = p.getGroupList();
		String os = groupList.get(0).getValue();
		if (StringUtils.containsIgnoreCase(os, "ios")) {
			phoneType = PhoneConstants.PHONE_TYPE_IPHONE;
		} else if (StringUtils.containsIgnoreCase(os, "android")) {
			phoneType = PhoneConstants.PHONE_TYPE_ANDROID;
		} else if (StringUtils.containsIgnoreCase(os, "windows")) {
			phoneType = PhoneConstants.PHONE_TYPE_WINDOWS;
		} else {
			phoneType = PhoneConstants.PHONE_TYPE_BASIC;
		}
		return phoneType;
	}
	
	private void setVariantColor(String colorVariant) {
		this.colorVariant = colorVariant;

	}

	public String getColorVariant() {
		return colorVariant;
	}
	
	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

}
