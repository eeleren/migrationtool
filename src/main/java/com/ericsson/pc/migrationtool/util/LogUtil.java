package com.ericsson.pc.migrationtool.util;

import java.util.List;

import org.apache.log4j.Logger;

import com.ericsson.pc.migrationtool.bean.Accessory;
import com.ericsson.pc.migrationtool.bean.Feature;
import com.ericsson.pc.migrationtool.bean.Group;
import com.ericsson.pc.migrationtool.bean.Group.Spec;
import com.ericsson.pc.migrationtool.bean.Item;
import com.ericsson.pc.migrationtool.bean.Phone;
import com.ericsson.pc.migrationtool.bean.SpecialFeature;
import com.ericsson.pc.migrationtool.bean.Variation;

public class LogUtil {
	
	final static Logger logger = Logger.getLogger(LogUtil.class);
	final static String PARSER = "Parser";
	final static String SEPARATOR = "------------------------------------";

	public static void logPhones(List<Phone> phoneList) {
		logger.info("Parsed the following phones (enable debug log level for verbose logging):");
		for (Phone phone : phoneList) {
			String idBracket = "[" + phone.getId() + "] ";
			logger.info(PARSER + " " + idBracket + "ID: " + phone.getId());
			logger.info(PARSER + " " +idBracket + "Name: " + phone.getPhoneName());
			logger.info(PARSER + " " +idBracket + "Brand: " + phone.getManufacturerName());
			
			List<Variation> variationList = phone.getVariations();
			logger.info(PARSER + " " +idBracket + "Phone Variations");
			if (variationList.size() == 0) {
				logger.info(PARSER + " " +idBracket + "no variations");
			}
			else {
				for (Variation v : variationList) {
					logger.info(PARSER + " " +idBracket + "Variation ID: " + v.getId());
					logger.debug(PARSER + " " +idBracket + " Variation Color ID: " + v.getColorId());
					logger.debug(PARSER + " " +idBracket + " Variation Color: " + v.getColorVariant());
					logger.debug(PARSER + " " +idBracket + " Variation Gradient Color: " + v.getGradientColor());
					logger.debug(PARSER + " " +idBracket + " Variation Map: " + v.getMap());
					logger.debug(PARSER + " " +idBracket + " Variation Memory: " + v.getMemoryVariant());
					logger.debug(PARSER + " " +idBracket + " Variation OSS Threshold Override: " + v.getOosThresholdOverride());
				}
			}
			
			logger.debug(PARSER + " " +idBracket + "New: " + phone.getIsNew());;
			logger.debug(PARSER + " " +idBracket + "Date Launch: " + phone.getDateLaunch());;
			logger.debug(PARSER + " " +idBracket + "EOL: " + phone.getEol());;
			logger.debug(PARSER + " " +idBracket + "Short Description: " + phone.getShortDescription());;
			logger.debug(PARSER + " " +idBracket + "Extended Description: " + phone.getExtendedDescription());;
			logger.debug(PARSER + " " +idBracket + "Brand Name Raw: " + phone.getManufacturerRaw());;
			logger.debug(PARSER + " " +idBracket + "Phone Name Raw: " + phone.getPhoneNameRaw());;
			logger.debug(PARSER + " " +idBracket + "Disclaimer Mini: " + phone.getDisclaimerMini());;
			logger.debug(PARSER + " " +idBracket + "Disclaimer Full: " + phone.getDisclaimerFull());;
			logger.debug(PARSER + " " +idBracket + "Footer Legal: " + phone.getFooterLegal());;
			logger.debug(PARSER + " " +idBracket + "Feature Bar: " + phone.getFeatureBar());;
			logger.debug(PARSER + " " +idBracket + "Feature Slider: " + phone.getFeatureSlider());;
			logger.debug(PARSER + " " +idBracket + "Feature Flip: " + phone.getFeatureFlip());;
			logger.debug(PARSER + " " +idBracket + "Feature TouchScreen: " + phone.getFeatureTouchScreen());;
			logger.debug(PARSER + " " +idBracket + "Feature QWERTY: " + phone.getFeatureQWERTY());;
			logger.debug(PARSER + " " +idBracket + "Feature Camera: " + phone.getFeatureCamera());;
			logger.debug(PARSER + " " +idBracket + "Feature Front Facing Camera: " + phone.getFeatureFronFacingCamera());;
			logger.debug(PARSER + " " +idBracket + "Feature GPS: " + phone.getFeatureGPS());;
			logger.debug(PARSER + " " +idBracket + "Feature 4GWimax: " + phone.getFeature4GWimax());;
			logger.debug(PARSER + " " +idBracket + "Feature WiFi: " + phone.getFeatureWiFi());;
			logger.debug(PARSER + " " +idBracket + "Feature Bluetooth: " + phone.getFeatureBluetooth());;
			logger.debug(PARSER + " " +idBracket + "Feature Speaker: " + phone.getFeatureSpeakerphone());;
			logger.debug(PARSER + " " +idBracket + "Feature 3G: " + phone.getFeature3G());;
			logger.debug(PARSER + " " +idBracket + "Feature 4GLTE: " + phone.getFeature4GLTE());;
			logger.debug(PARSER + " " +idBracket + "Feature 4G: " + phone.getFeature4G());;
			logger.debug(PARSER + " " +idBracket + "Feature Text: " + phone.getFeatureText());;
			logger.debug(PARSER + " " +idBracket + "Feature Video: " + phone.getFeatureVideo());;
			logger.debug(PARSER + " " +idBracket + "Feature Hotspot: " + phone.getFeatureHotspot());;
			logger.debug(PARSER + " " +idBracket + "Feature Email: " + phone.getFeatureEmail());;
			logger.debug(PARSER + " " +idBracket + "Feature HTML Browser: " + phone.getFeatureHTMLBrowser());;
			logger.debug(PARSER + " " +idBracket + "Feature ULE Certified: " + phone.getFeatureULECertified());;
			logger.debug(PARSER + " " +idBracket + "Feature Music: " + phone.getFeatureMusic());;
			logger.debug(PARSER + " " +idBracket + "Feature Visual Voice Mail: " + phone.getFeatureVisualVoicemail());;
			logger.debug(PARSER + " " +idBracket + "Premium: " + phone.getIsPremium());;
			logger.debug(PARSER + " " +idBracket + "Synonyms: " + phone.getSynonyms());;
			for (Item item : phone.getGalleryImages()) {
				logger.debug(PARSER + " " +idBracket + "Gallery-Images galleryType: " + item.getGalleryType());
				logger.debug(PARSER + " " +idBracket + "Gallery-Images view: " + item.getView());
				logger.debug(PARSER + " " +idBracket + "Gallery-Images order: " + item.getOrder());
				
			}
			logger.debug(PARSER + " " +idBracket + "Adwords Lid: " + phone.getAdwordsLid());;
			logger.debug(PARSER + " " +idBracket + "Adwords Ds SKwgid: " + phone.getAdwordsDsSKwgid());;
			logger.debug(PARSER + " " +idBracket + "Bing Lid: " + phone.getBingLid());;
			logger.debug(PARSER + " " +idBracket + "Bing DS SKwgid: " + phone.getBingDsSKwgid());;
			for (Accessory a : phone.getAccessories()) {
				logger.debug(PARSER + " " +idBracket + "Accessory: " + a.getId());
			}
			logger.debug(PARSER + " " +idBracket + "Compare OS: " + phone.getCompareItemOS());
			logger.debug(PARSER + " " +idBracket + "Compare Display: " + phone.getCompareItemDisplay());
			logger.debug(PARSER + " " +idBracket + "Compare Camera: " + phone.getCompareItemCamera());
			logger.debug(PARSER + " " +idBracket + "Compare WiFi: " + phone.getCompareItemWifi());
			logger.debug(PARSER + " " +idBracket + "Compare 4G: " + phone.getCompareItem4G());
			logger.debug(PARSER + " " +idBracket + "Compare Hotspot: " + phone.getCompareItemHotspot());
			logger.debug(PARSER + " " +idBracket + "Compare QWERTY Keyboard: " + phone.getCompareItemQWERTYKeyboard());
			logger.debug(PARSER + " " +idBracket + "Compare WebBrowser: " + phone.getCompareItemWebBrowser());
			logger.debug(PARSER + " " +idBracket + "Compare FlashPlayer: " + phone.getCompareItemFlashPlayer());
			logger.debug(PARSER + " " +idBracket + "Compare Email: " + phone.getCompareItemEmail());
			logger.debug(PARSER + " " +idBracket + "Compare Video: " + phone.getCompareItemVideo());
			logger.debug(PARSER + " " +idBracket + "Compare Music Player: " + phone.getCompareItemMusicPlayer());
			logger.debug(PARSER + " " +idBracket + "Compare GPS: " + phone.getCompareItemGPS());
			logger.debug(PARSER + " " +idBracket + "Compare Speakerphone: " + phone.getCompareItemSpeakerphone());
			logger.debug(PARSER + " " +idBracket + "Compare Memory: " + phone.getCompareItemMemory());
			logger.debug(PARSER + " " +idBracket + "Compare Processor: " + phone.getCompareItemProcessor());
			logger.debug(PARSER + " " +idBracket + "Compare Calendar: " + phone.getCompareItemCalendar());
			logger.debug(PARSER + " " +idBracket + "Compare Voicemail: " + phone.getCompareItemVisualVoicemail());
			logger.debug(PARSER + " " +idBracket + "Compare 3G: " + phone.getCompareItem3G());
			logger.debug(PARSER + " " +idBracket + "Compare Bluethoot: " + phone.getCompareItemBluetooth());
			
			for (Feature f : phone.getFeatureList()) {
				logger.debug(PARSER + " " +idBracket + "Feature ID: " + f.getFeatureId());
				logger.debug(PARSER + " " +idBracket + "Feature Title: " + f.getExtraFeatureTitle());
				logger.debug(PARSER + " " +idBracket + "Feature Icon Name: " + f.getExtraFeatureIconName());
				logger.debug(PARSER + " " +idBracket + "Feature Description: " + f.getExtraFeatureDescription());
			}
			for (SpecialFeature sf : phone.getSpecialFeatureList()) {
				logger.debug(PARSER + " " +idBracket + "Special Feature ID: " + sf.getSpecialFeatureId());
				logger.debug(PARSER + " " +idBracket + "Special Feature Name: " + sf.getSpecialFeatureName());
				
			}
			for (Group g : phone.getGroupList()) {
				logger.debug(PARSER + " " +idBracket + "Group ID: " + g.getId());
				logger.debug(PARSER + " " +idBracket + "Group Order: " + g.getOrder());
				logger.debug(PARSER + " " +idBracket + "Group Value: " + g.getValue());
				logger.debug(PARSER + " " +idBracket + "Group Thumb: " + g.getThumb());
				for (Spec spec : g.getListSpec()) {
					logger.debug(PARSER + " " +idBracket + "Spec Order: " + spec.getOrder());
					logger.debug(PARSER + " " +idBracket + "Spec Type: " + spec.getType());
					logger.debug(PARSER + " " +idBracket + "Spec Value: " + spec.getValue());
				}
				
			}
			logger.info(LogUtil.SEPARATOR);
			
		}
		
	}

}
