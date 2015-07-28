package com.ericsson.pc.migrationtool.builder;

import java.io.File;
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

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.ericsson.pc.migrationtool.bean.Accessory;
import com.ericsson.pc.migrationtool.bean.PhoneAsset;
import com.ericsson.pc.migrationtool.bean.PhoneAssetStructure;
import com.ericsson.pc.migrationtool.bean.PhoneField;
import com.ericsson.pc.migrationtool.bean.Variation;

public class PhoneBuilder {
	
	//msdp asset structure
	private static PhoneAssetStructure phoneStructure = new PhoneAssetStructure();
	
	public static void main (String[] args) {	
		
		//phones from boost
		ArrayList<PhoneAsset> phones = new ArrayList<PhoneAsset>();			
		
		//PhoneVariationBuilder builder = new PhoneVariationBuilder();		
		PhoneAsset phoneAsset = new PhoneAsset();
		phoneAsset.setId("asdasdasd");	
		phoneAsset.setPhoneName("lg-rumor-reflex-preowned_automatic");
		phoneAsset.setManufacturerName("lg");
		phones.add(phoneAsset);
		//output list with boost phones translated in msdp structure
		List<PhoneAssetStructure> phoneStructures = AssetBuilderTest.createAssetStructure(phones);
		AssetBuilderTest builder = new AssetBuilderTest();
		
		builder.createXml(phoneStructures.get(0));

	/*	for (int i = 0; i < phones.size(); i++ ) {
			if (phones.get(i).hasVariations()); {
				builder.createPhoneVariations(phones.get(i));
				//builder.createPhoneVariations(phones.get(i));
			}
			try {
				builder.createPhoneAsset(phones.get(i));
			} catch (SAXException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/
		

}
	
	public void createPhoneAssets(List<PhoneAsset> phones) {
		PhoneVariationBuilder builder = new PhoneVariationBuilder();
		List <PhoneAssetStructure> phoneAssets = new ArrayList<PhoneAssetStructure>();
		
		for (int i = 0; i < phones.size(); i++ ) {
			if (phones.get(i).hasVariations()); {
				builder.createPhoneVariations(phones.get(i));
				
			}
			try {
				
				builder.createPhoneAsset(phones.get(i));
			} catch (SAXException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
		
		
	}
	public void createXml(PhoneAssetStructure assetPhone) {
		String extension = PhoneConstants.FILE_EXTENSION;		
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
				rootElement.setAttribute(PhoneConstants.EXTERNAL_ID, "idTest");
				asset.appendChild(rootElement);
				
				//da qui itera sulla lista di PhoneField
				for (PhoneField f : fieldList) {
					if (f.isMandatory()) {
						Element e = doc.createElement(f.getName());
						if (f.getValue()!= null) {
							e.appendChild(doc.createTextNode(f.getValue()));
						} else {
							e.appendChild(doc.createTextNode(f.getDefaultValue()));
						}						
						rootElement.appendChild(e);
						
					} else {
						if(f.getValue()!=null) {
							Element e = doc.createElement(f.getName());
							e.appendChild(doc.createTextNode(f.getValue()));
							rootElement.appendChild(e);
							
						}
					}
				}				
				
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				DOMSource source = new DOMSource(doc);
				File output = new File("C:\\Users\\eeleren\\"+"test"+extension);
				StreamResult result = new StreamResult(output);
				// DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
				//doc = docBuilder.parse(output);
				//doc = removeNullValues(doc);		 
				// Output to console for testing
				// StreamResult result = new StreamResult(System.out);
		 
				transformer.transform(source, result);
		 
				System.out.println("File saved!");
			//removeNullValues();
		 
			  } catch (ParserConfigurationException pce) {
				pce.printStackTrace();
			  } catch (TransformerException tfe) {
				tfe.printStackTrace();
			  }
				
				
	}
	
	//translates a boost list of phones data into msdp phone asset
	public static List<PhoneAssetStructure> createAssetStructure(ArrayList<PhoneAsset> phones) {
		
		List<PhoneAsset> boostPhonesList = phones;		
		List<PhoneAssetStructure> assetsList = new ArrayList<PhoneAssetStructure>();
		PhoneAssetStructure asset;		
		
		for (PhoneAsset p : boostPhonesList) {		
			
			asset = new PhoneAssetStructure();	
			asset.init();			
			
			//setting asset fields with boost values
		
			asset.setPhoneFieldValueByFieldName(PhoneConstants.MIN_ADV_PRICE_FIELD, p.getMap());		
			asset.setPhoneFieldValueByFieldName(PhoneConstants.OVERRIDE_OOS_FIELD, p.getOosThresholdOverride());			
			asset.setPhoneFieldValueByFieldName(PhoneConstants.PHONE_ID_FIELD, p.getId());			
			asset.setPhoneFieldValueByFieldName(PhoneConstants.DEFAULT_VARIANT_ID_FIELD, p.getDefaultId());				
			asset.setPhoneFieldValueByFieldName(PhoneConstants.IS_REDVENTURES_FIELD, p.getRedVentures());
			asset.setPhoneFieldValueByFieldName(PhoneConstants.GENIE_ORDER_FIELD, p.getGenieOrder());
			asset.setPhoneFieldValueByFieldName(PhoneConstants.IS_NEW_FIELD, p.getIsNew());
			asset.setPhoneFieldValueByFieldName(PhoneConstants.IS_PREOWNED_FIELD, p.getPhoneName().contains("preowned")+"");			
			asset.setPhoneFieldValueByFieldName(PhoneConstants.DATE_LAUNCH_FIELD, p.getDateLaunch());
			asset.setPhoneFieldValueByFieldName(PhoneConstants.IS_EOL_FIELD, p.getEol());
			asset.setPhoneFieldValueByFieldName(PhoneConstants.SHORT_DESC_FIELD, p.getShortDescription());
			asset.setPhoneFieldValueByFieldName(PhoneConstants.EXT_DESC_FIELD, p.getExtendedDescription());
			asset.setPhoneFieldValueByFieldName(PhoneConstants.PHONE_NAME_FIELD, p.getPhoneName());
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
			//asset.setPhoneFieldValueByFieldName(PhoneConstants.FEATURE_MEMORY_FIELD, p.getFeature());
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
			asset.setPhoneFieldValueByFieldName(PhoneConstants.COMPARE_ITEM_FLASH_PLAYER_FIELD, p.getCompareItemFlashPlayer());
			asset.setPhoneFieldValueByFieldName(PhoneConstants.COMPARE_ITEM_EMAIL_FIELD, p.getCompareItemEmail());
			asset.setPhoneFieldValueByFieldName(PhoneConstants.COMPARE_ITEM_VIDEO_FIELD, p.getCompareItemVideo());
			asset.setPhoneFieldValueByFieldName(PhoneConstants.COMPARE_ITEM_MUSIC_PLAYER_FIELD, p.getCompareItemMusicPlayer());
			asset.setPhoneFieldValueByFieldName(PhoneConstants.COMPARE_ITEM_GPS_FIELD, p.getCompareItemGPS());
			asset.setPhoneFieldValueByFieldName(PhoneConstants.COMPARE_ITEM_SPEAKERPHONE_FIELD, p.getCompareItemSpeakerphone());
			asset.setPhoneFieldValueByFieldName(PhoneConstants.COMPARE_ITEM_MEMORY_FIELD, p.getCompareItemMemory());
			asset.setPhoneFieldValueByFieldName(PhoneConstants.COMPARE_ITEM_PROCESSOR_FIELD, p.getCompareItemProcessor());
			asset.setPhoneFieldValueByFieldName(PhoneConstants.COMPARE_ITEM_CALENDAR_FIELD, p.getCompareItemCalendar());
			asset.setPhoneFieldValueByFieldName(PhoneConstants.COMPARE_ITEM_VISUAL_VOICE_MAIL_FIELD, p.getCompareItemVisualVoicemail());
			asset.setPhoneFieldValueByFieldName(PhoneConstants.COMPARE_ITEM_3G_FIELD, p.getCompareItem3G());
			asset.setPhoneFieldValueByFieldName(PhoneConstants.COMPARE_ITEM_BLUETOOTH_FIELD, p.getCompareItemBluetooth());
			asset.setPhoneFieldValueByFieldName(PhoneConstants.EXTRA_FEAT_COUNT_FIELD, p.getFeatureList().size()+"");
			asset.setPhoneFieldValueByFieldName(PhoneConstants.SPEC_FEAT_COUNT_LIST_FIELD, p.getSpecialFeatureList().size()+"");
			asset.setPhoneFieldValueByFieldName(PhoneConstants.TECH_SPEC_GROUP_COUNT_FIELD, p.getGroupList().size()+"");
			assetsList.add(asset);				
		}
		return assetsList;
	}
	
	public static String getAccessories(List<Accessory> accessories) {
		
		String accessoriesValues = "";
		String separator = ";";
		
		for (int i = 0; i< accessories.size(); i++) {
			accessoriesValues=accessoriesValues + accessories.get(i) + separator;						
		}				
		return accessoriesValues;					
	}
	
	
}
