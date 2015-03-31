package com.ericsson.pc.migrationtool.builder;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.ericsson.pc.migrationtool.bean.Accessory;
import com.ericsson.pc.migrationtool.bean.Feature;
import com.ericsson.pc.migrationtool.bean.Item;
import com.ericsson.pc.migrationtool.bean.SpecialFeature;
import com.ericsson.pc.migrationtool.bean.Phone;
import com.ericsson.pc.migrationtool.bean.msdp.ImageItem;
import com.ericsson.pc.migrationtool.bean.msdp.PhoneAssetStructure;
import com.ericsson.pc.migrationtool.bean.msdp.PhoneField;
import com.ericsson.pc.migrationtool.util.ApplicationPropertiesReader;
import com.ericsson.pc.migrationtool.util.PathUtil;
import com.ericsson.pc.migrationtool.bean.Group;


public class Builder  {
	
	final static String buildAccessories = ApplicationPropertiesReader.getInstance().getProperty("builder.asset.buildAccessories");
	final static Logger logger = Logger.getLogger(Builder.class);
	final static String separator = File.separator;
	final String outputDir = ApplicationPropertiesReader.getInstance().getProperty("builder.asset.outputdir");
	final String imageViewDir = ApplicationPropertiesReader.getInstance().getProperty("builder.asset.phone.imageViewDir");
	final String extension = PhoneConstants.FILE_EXTENSION;	
	final String featureExtension = PhoneConstants.FEATURES_EXTENSION;
	final String specFeatureExtension = PhoneConstants.SPEC_FEATURES_EXTENSION;
	final String techSpecExtension = PhoneConstants.TECHSPEC_EXTENSION;
	private String assetOutputDir = "";
	protected String assetName = "";
	
	
	public String getAssetName() {
		return assetName;
	}

	public void setAssetName(String assetBrand, String assetName) {		
		this.assetName = assetBrand + "_" + assetName;
		this.normalizeAssetName();		
	}

	public void normalizeAssetName(){
		this.assetName = assetName.toLowerCase().replaceAll("\\s+","_");		
	}


	public String getAssetOutputDir() {
		return assetOutputDir;
	}

	public void setAssetOutputDir(String assetOutputDir) {
		this.assetOutputDir = assetOutputDir;
	}

	public void createPhoneAssets(List<Phone> phones) {
	
		PhoneVariationBuilder builder = new PhoneVariationBuilder();
		
		builder.cleanOutputDir();
		buildAccessories(phones);
		
		logger.info("BUILDING EXECUTION STARTED...");
		
		for (Phone p: phones) {			
			if (p.hasVariations()) {
				logger.debug("["+ p.getId()+ "] Phone Asset: "+p.getManufacturerRaw()+ " "+p.getPhoneNameRaw()+" building start" );
				logger.debug("Phone has variation..");
				builder.createPhoneVariations(p);
				
			} else {		
				logger.debug("["+ p.getId()+ "] Phone Asset: "+p.getManufacturerRaw()+ " "+p.getPhoneNameRaw()+" building start" );
				logger.debug("Phone has not variation..");
				builder.setAssetName(p.getManufacturerRaw(), p.getPhoneNameRaw());
				builder.createPhoneAsset(p);
			} 
		}
		
		logger.info("BUILDER EXECUTION COMPLETED!");
		
	}
	
	public void createPhoneAsset(Phone phone) {
		PhoneAssetStructure asset = buildAssetStructure(phone);
		createXml(asset);
	}
	
	
	/**
	 * Verify that the images linked in the boost phone.xml file have a reference in the image folder
	 * 
	 * */
	public List<ImageItem> scanAssetImages(PhoneAssetStructure asset, List<Item> items, ImageBuilder imageBuilder) {
		logger.debug("Setting phone images...");	
		
		String phoneNameRaw = (String) asset.getPhoneFieldByName(PhoneConstants.PHONE_NAME_RAW_FIELD).getValue();
		String brandNameRaw = (String) asset.getPhoneFieldByName(PhoneConstants.MANUFACT_RAW_FIELD).getValue();		
		List<ImageItem> imageItems= new ArrayList<ImageItem>();
		String imageDir = "";
		
		try {
			
			imageDir = ImageBuilder.getImageFolder(phoneNameRaw, brandNameRaw);
		
		} catch (FileNotFoundException e) {
			logger.error("Image directory not found for "+phoneNameRaw+" images will not be loaded");
			return null;
		}		
		
		logger.debug("image root directory set to: "+imageDir);
				
		imageItems = imageBuilder.getContainedImages(items, imageDir);	
		return imageItems;
		}	
	

	
	@SuppressWarnings("unchecked")
	public void createXml(PhoneAssetStructure assetPhone) {
		
		setAssetOutputDir(getAssetName());
		PathUtil.createAssetOutputDir(getAssetOutputDir());		
		
		String filename = getAssetName();
				
		List<PhoneField> fieldList = assetPhone.getPhoneFieldList();
	
		
		try {
				DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
				docFactory.setNamespaceAware(true);
		 
				// root elements
				Document doc = docBuilder.newDocument();
								
				Element asset = doc.createElement(PhoneConstants.ASSET_FIELD);
				Attr attr = doc.createAttribute(PhoneConstants.PREFIX);
				attr.setValue(PhoneConstants.NAMESPACE);
				
				asset.setAttributeNodeNS(attr);
				doc.appendChild(asset);
				
				
				Element rootElement = doc.createElement(PhoneConstants.PHONE_FIELD);
				String externalId = (String) assetPhone.getPhoneFieldByName(PhoneConstants.PHONE_ID_FIELD).getValue();
				rootElement.setAttribute(PhoneConstants.EXTERNAL_ID, externalId);
				asset.appendChild(rootElement);
				
				//iteration over PhoneField elements
				for (PhoneField f : fieldList) {
					if (f.isMandatory()) {
						Element e = doc.createElement(f.getName());
						if (f.getValue()!= null) {
							e.appendChild(doc.createTextNode((String) f.getValue()));
						} else {
							e.appendChild(doc.createTextNode(f.getDefaultValue()));
						}						
						rootElement.appendChild(e);
						
						//analysis of variants elements: pictures, extrafeatures, specialfeatures, techspec
					} else if (f.getName().equals(PhoneConstants.FEATURES_FIELD)&&(f.getValue()!=null)) {
						//the phone asset contains features variants
						logger.debug("creating xml structure for features");
						
						PhoneVariantBuilder variantBuilder = new PhoneVariantBuilder();
						variantBuilder.setAssetName(getAssetName());
						variantBuilder.setFeaturesName(getAssetName()+featureExtension);
						variantBuilder.setFeatureFile(outputDir+assetName+separator+assetName+featureExtension);
						variantBuilder.createPhoneFeatures((List<Feature>) f.getValue());
						
						Element e = doc.createElement(f.getName());//elemento features
						Element variant = doc.createElement("variant");
						Element item = doc.createElement("item");
						item.setAttribute("uri", variantBuilder.getFeaturesName());
						variant.appendChild(item);
						e.appendChild(variant);
						rootElement.appendChild(e);
					
					} else if (f.getName().equals(PhoneConstants.SPEC_FEATURES_FIELD)&&(f.getValue()!=null)) {
						//the phone asset contains features variants
						logger.debug("creating xml structure for special features");
						
						PhoneVariantBuilder variantBuilder = new PhoneVariantBuilder();
						variantBuilder.setAssetName(getAssetName());
						variantBuilder.setSpecFeaturesName(getAssetName()+specFeatureExtension);
						variantBuilder.setSpecFeatureFile(outputDir+assetName+separator+assetName+specFeatureExtension);
						variantBuilder.createPhoneSpecialFeatures((List<SpecialFeature>) f.getValue());
						
						Element e = doc.createElement(f.getName());//elemento features
						Element variant = doc.createElement("variant");
						Element item = doc.createElement("item");
						item.setAttribute("uri", variantBuilder.getSpecFeaturesName());
						variant.appendChild(item);
						e.appendChild(variant);
						rootElement.appendChild(e);
					
					} else if (f.getName().equals(PhoneConstants.TECH_SPEC_VARIANT_FIELD)&&(f.getValue()!=null)) {
						//the phone asset contains features variants
						logger.debug("creating xml structure for technical specifications..");
						
						PhoneVariantBuilder variantBuilder = new PhoneVariantBuilder();
						variantBuilder.setAssetName(getAssetName());
						variantBuilder.setTechSpecName(getAssetName()+techSpecExtension);
						variantBuilder.setTechSpecFile(outputDir+assetName+separator+assetName+techSpecExtension);
						variantBuilder.createPhoneTechSpec((List<Group>) f.getValue());
						
						Element e = doc.createElement(f.getName());//elemento features
						Element variant = doc.createElement("variant");
						Element item = doc.createElement("item");
						item.setAttribute("uri", variantBuilder.getTechSpecName());
						variant.appendChild(item);
						e.appendChild(variant);
						rootElement.appendChild(e);					
					}			
					
					else if(f.getName().equals(PhoneConstants.PICTURES_FIELD)&&(f.getValue()!=null)) {
						//the phone asset contains images
						logger.debug("creating xml structure for images");
						//images found in the directory
					   ImageBuilder imageBuilder = new ImageBuilder();
						//images set in the phone.xml boost
					    ArrayList<Item> items = (ArrayList<Item>) f.getValue();
					    ArrayList<ImageItem> images = (ArrayList<ImageItem>) scanAssetImages(assetPhone, items, imageBuilder);
					    if (!images.isEmpty()) {
							Element e = doc.createElement(f.getName());//elemento pictures
							for (ImageItem i: images) {
								Element variant = doc.createElement("variant");
								Element item = doc.createElement("item");
								item.setAttribute("uri", i.getName());
								variant.appendChild(item);
								e.appendChild(variant);
								rootElement.appendChild(e);
								imageBuilder.moveImages(images, outputDir+getAssetOutputDir());
					    }
					    } else {
					    	logger.error("images not found for asset:" + filename);
					    }
					} else {
						if ((f.getValue()!=null)&&(!f.getValue().equals(""))) {
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
				File output = new File(outputDir+getAssetOutputDir()+File.separator+filename+extension);
				StreamResult result = new StreamResult(output);
							transformer.transform(source, result);
		 
				logger.info("Asset file: "+filename+extension+" created and saved to: "+output);
				
				//images
				/*PathUtil.createAssetFolder((assetPhone.getPhoneFieldByName(PhoneConstants.PHONE_NAME_RAW_FIELD).getValue()),
										   (assetPhone.getPhoneFieldByName(PhoneConstants.PICTURES_FIELD).getValue()));*/
				
				//zip
				 
			  } catch (ParserConfigurationException pce) {
				
				logger.error("ParserConfigurationException :"+pce.getMessage());
			  } catch (TransformerException tfe) {
				
				logger.error("TransoformerException: "+tfe.getMessage());
				
			  }	catch (Exception e) {
				  logger.error(e.getMessage());
			  }
	}
	
	/**
	 * Translates a phone data coming from boost into msdp phone assets structure
	 * */
	public PhoneAssetStructure buildAssetStructure(Phone phone) {		
					
			PhoneAssetStructure asset = new PhoneAssetStructure();
			asset.init();			
			
			//setting asset fields with boost values
			
			logger.debug("Creating phone asset for Phone Model " + phone.getId() +" with name: "+ phone.getPhoneName());
			asset.setPhoneFieldValueByFieldName(PhoneConstants.EXTERNAL_ID, phone.getId());	
			asset.setPhoneFieldValueByFieldName(PhoneConstants.MIN_ADV_PRICE_FIELD, phone.getMap());					
			
			asset.setPhoneFieldValueByFieldName(PhoneConstants.OVERRIDE_OOS_FIELD, phone.getOosThresholdOverride());			
			asset.setPhoneFieldValueByFieldName(PhoneConstants.PHONE_ID_FIELD, phone.getId());			
			asset.setPhoneFieldValueByFieldName(PhoneConstants.DEFAULT_VARIANT_ID_FIELD, phone.getDefaultId());				
			asset.setPhoneFieldValueByFieldName(PhoneConstants.IS_REDVENTURES_FIELD, phone.getRedVentures());
		//	asset.setPhoneFieldValueByFieldName(PhoneConstants.IMPORT_PATH_FIELD, phone.));
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
		//asset.setPhoneFieldValueByFieldName(PhoneConstants.COLOR_VARIANT_FIELD, phone.getColorVariant());
			//asset.setPhoneFieldValueByFieldName(PhoneConstants.COLOR_ID_FIELD, phone.getColorId());	
			//asset.setPhoneFieldValueByFieldName(PhoneConstants.GRADIENT_COLOR_FIELD, phone.getGradientColor());
			//asset.setPhoneFieldValueByFieldName(PhoneConstants.MEMORY_VARIANT_FIELD, phone.getMemoryVariant());
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
			asset.setPhoneFieldValueByFieldName(PhoneConstants.EXTRA_FEAT_COUNT_FIELD, phone.getFeatureList().size()+"");
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
	

	
	protected void setAssetName(String assetName) {
		this.assetName = assetName;
		
	}

	public static String getAccessories(List<Accessory> accessories) {
		
		String accessoriesValues = "";
		String separator = ";";
		
		for (int i = 0; i< accessories.size(); i++) {
			accessoriesValues=accessoriesValues + accessories.get(i).getId() + separator;						
		}				
		return accessoriesValues;					
	}
	
	
	public void buildAccessories(List<Phone> phones) {
		
		if (buildAccessories.equalsIgnoreCase("true")) {
			AccessoryBuilder accBuilder = new AccessoryBuilder();
			logger.info("ACCESSORIES ASSETS BUILDING STARTED...");
			
			for (Phone p: phones) {				
			
				accBuilder.setRelatedPhoneBrand(p.getManufacturerName());
				accBuilder.setRelatedPhoneName(p.getPhoneNameRaw());			
				accBuilder.createAccessories(p.getAccessories());
			
		}
		
	}
}
	
	public void cleanOutputDir() {
		String cleanup = ApplicationPropertiesReader.getInstance().getProperty("builder.asset.outputdircleanup");
		
		if (cleanup.equalsIgnoreCase("true")) {
			File assetsOutput = new File (outputDir);
			try {
				FileUtils.cleanDirectory(assetsOutput);
			} catch (IOException e) {
			logger.error(e,e);
			}
	
		}
	}
}
