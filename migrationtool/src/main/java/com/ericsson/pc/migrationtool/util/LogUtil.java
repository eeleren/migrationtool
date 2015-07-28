package com.ericsson.pc.migrationtool.util;

import java.util.List;

import org.apache.log4j.Logger;

import com.ericsson.pc.migrationtool.bean.Accessory;
import com.ericsson.pc.migrationtool.bean.Feature;
import com.ericsson.pc.migrationtool.bean.Group;
import com.ericsson.pc.migrationtool.bean.Item;
import com.ericsson.pc.migrationtool.bean.Group.Spec;
import com.ericsson.pc.migrationtool.bean.Phone;
import com.ericsson.pc.migrationtool.bean.SpecialFeature;
import com.ericsson.pc.migrationtool.bean.Variation;

public class LogUtil {
	
	final static Logger logger = Logger.getLogger(LogUtil.class);
	final static String SEPARATOR = "------------------------------------";

	public static void logPhones(List<Phone> phoneList) {
		logger.info("Parsed the following phones (enable debug log level for verbose logging):");
		for (Phone phone : phoneList) {
			String idBracket = "[" + phone.getId() + "] ";
			logger.info(idBracket + "ID: " + phone.getId());
			logger.info(idBracket + "Name: " + phone.getPhoneName());
			logger.info(idBracket + "Brand: " + phone.getManufacturerName());
			
			List<Variation> variationList = phone.getVariations();
			logger.info(idBracket + "Phone Variations");
			if (variationList.size() == 0) {
				logger.info(idBracket + "no variations");
			}
			else {
				for (Variation v : variationList) {
					logger.info(idBracket + "Variation ID: " + v.getId());
					logger.debug(idBracket + " Variation Color ID: " + v.getColorId());
					logger.debug(idBracket + " Variation Color: " + v.getColorVariant());
					logger.debug(idBracket + " Variation Gradient Color: " + v.getGradientColor());
					logger.debug(idBracket + " Variation Map: " + v.getMap());
					logger.debug(idBracket + " Variation Memory: " + v.getMemoryVariant());
					logger.debug(idBracket + " Variation OSS Threshold Override: " + v.getOosThresholdOverride());
				}
			}
			
			logger.debug(idBracket + "New: " + phone.getIsNew());;
			logger.debug(idBracket + "Date Launch: " + phone.getDateLaunch());;
			logger.debug(idBracket + "EOL: " + phone.getEol());;
			logger.debug(idBracket + "Short Description: " + phone.getShortDescription());;
			logger.debug(idBracket + "Extended Description: " + phone.getExtendedDescription());;
			logger.debug(idBracket + "Brand Name Raw: " + phone.getManufacturerRaw());;
			logger.debug(idBracket + "Phone Name Raw: " + phone.getPhoneNameRaw());;
			logger.debug(idBracket + "Disclaimer Mini: " + phone.getDisclaimerMini());;
			logger.debug(idBracket + "Disclaimer Full: " + phone.getDisclaimerFull());;
			logger.debug(idBracket + "Footer Legal: " + phone.getFooterLegal());;
			logger.debug(idBracket + "Feature Bar: " + phone.getFeatureBar());;
			logger.debug(idBracket + "Feature Slider: " + phone.getFeatureSlider());;
			logger.debug(idBracket + "Feature Flip: " + phone.getFeatureFlip());;
			logger.debug(idBracket + "Feature TouchScreen: " + phone.getFeatureTouchScreen());;
			logger.debug(idBracket + "Feature QWERTY: " + phone.getFeatureQWERTY());;
			logger.debug(idBracket + "Feature Camera: " + phone.getFeatureCamera());;
			logger.debug(idBracket + "Feature Front Facing Camera: " + phone.getFeatureFronFacingCamera());;
			logger.debug(idBracket + "Feature GPS: " + phone.getFeatureGPS());;
			logger.debug(idBracket + "Feature 4GWimax: " + phone.getFeature4GWimax());;
			logger.debug(idBracket + "Feature WiFi: " + phone.getFeatureWiFi());;
			logger.debug(idBracket + "Feature Bluetooth: " + phone.getFeatureBluetooth());;
			logger.debug(idBracket + "Feature Speaker: " + phone.getFeatureSpeakerphone());;
			logger.debug(idBracket + "Feature 3G: " + phone.getFeature3G());;
			logger.debug(idBracket + "Feature 4GLTE: " + phone.getFeature4GLTE());;
			logger.debug(idBracket + "Feature 4G: " + phone.getFeature4G());;
			logger.debug(idBracket + "Feature Text: " + phone.getFeatureText());;
			logger.debug(idBracket + "Feature Video: " + phone.getFeatureVideo());;
			logger.debug(idBracket + "Feature Hotspot: " + phone.getFeatureHotspot());;
			logger.debug(idBracket + "Feature Email: " + phone.getFeatureEmail());;
			logger.debug(idBracket + "Feature HTML Browser: " + phone.getFeatureHTMLBrowser());;
			logger.debug(idBracket + "Feature ULE Certified: " + phone.getFeatureULECertified());;
			logger.debug(idBracket + "Feature Music: " + phone.getFeatureMusic());;
			logger.debug(idBracket + "Feature Visual Voice Mail: " + phone.getFeatureVisualVoicemail());;
			logger.debug(idBracket + "Premium: " + phone.getIsPremium());;
			logger.debug(idBracket + "Synonyms: " + phone.getSynonyms());;
			for (Item item : phone.getGalleryImages()) {
				logger.debug(idBracket + "Gallery-Images galleryType: " + item.getGalleryType());
				logger.debug(idBracket + "Gallery-Images view: " + item.getView());
				logger.debug(idBracket + "Gallery-Images order: " + item.getOrder());
				
			}
			logger.debug(idBracket + "Adwords Lid: " + phone.getAdwordsLid());;
			logger.debug(idBracket + "Adwords Ds SKwgid: " + phone.getAdwordsDsSKwgid());;
			logger.debug(idBracket + "Bing Lid: " + phone.getBingLid());;
			logger.debug(idBracket + "Bing DS SKwgid: " + phone.getBingDsSKwgid());;
			for (Accessory a : phone.getAccessories()) {
				logger.debug(idBracket + "Accessory: " + a.getId());
			}
			logger.debug(idBracket + "Compare OS: " + phone.getCompareItemOS());
			logger.debug(idBracket + "Compare Display: " + phone.getCompareItemDisplay());
			logger.debug(idBracket + "Compare Camera: " + phone.getCompareItemCamera());
			logger.debug(idBracket + "Compare WiFi: " + phone.getCompareItemWifi());
			logger.debug(idBracket + "Compare 4G: " + phone.getCompareItem4G());
			logger.debug(idBracket + "Compare Hotspot: " + phone.getCompareItemHotspot());
			logger.debug(idBracket + "Compare QWERTY Keyboard: " + phone.getCompareItemQWERTYKeyboard());
			logger.debug(idBracket + "Compare WebBrowser: " + phone.getCompareItemWebBrowser());
			logger.debug(idBracket + "Compare FlashPlayer: " + phone.getCompareItemFlashPlayer());
			logger.debug(idBracket + "Compare Email: " + phone.getCompareItemEmail());
			logger.debug(idBracket + "Compare Video: " + phone.getCompareItemVideo());
			logger.debug(idBracket + "Compare Music Player: " + phone.getCompareItemMusicPlayer());
			logger.debug(idBracket + "Compare GPS: " + phone.getCompareItemGPS());
			logger.debug(idBracket + "Compare Speakerphone: " + phone.getCompareItemSpeakerphone());
			logger.debug(idBracket + "Compare Memory: " + phone.getCompareItemMemory());
			logger.debug(idBracket + "Compare Processor: " + phone.getCompareItemProcessor());
			logger.debug(idBracket + "Compare Calendar: " + phone.getCompareItemCalendar());
			logger.debug(idBracket + "Compare Voicemail: " + phone.getCompareItemVisualVoicemail());
			logger.debug(idBracket + "Compare 3G: " + phone.getCompareItem3G());
			logger.debug(idBracket + "Compare Bluethoot: " + phone.getCompareItemBluetooth());
			
			for (Feature f : phone.getFeatureList()) {
				logger.debug(idBracket + "Feature ID: " + f.getFeatureId());
				logger.debug(idBracket + "Feature Title: " + f.getExtraFeatureTitle());
				logger.debug(idBracket + "Feature Icon Name: " + f.getExtraFeatureIconName());
				logger.debug(idBracket + "Feature Description: " + f.getExtraFeatureDescription());
			}
			for (SpecialFeature sf : phone.getSpecialFeatureList()) {
				logger.debug(idBracket + "Special Feature ID: " + sf.getSpecialFeatureId());
				logger.debug(idBracket + "Special Feature Name: " + sf.getSpecialFeatureName());
				
			}
			for (Group g : phone.getGroupList()) {
				logger.debug(idBracket + "Group ID: " + g.getId());
				logger.debug(idBracket + "Group Order: " + g.getOrder());
				logger.debug(idBracket + "Group Value: " + g.getValue());
				logger.debug(idBracket + "Group Thumb: " + g.getThumb());
				for (Spec spec : g.getListSpec()) {
					logger.debug(idBracket + "Spec Order: " + spec.getOrder());
					logger.debug(idBracket + "Spec Type: " + spec.getType());
					logger.debug(idBracket + "Spec Value: " + spec.getValue());
				}
				
			}
			logger.info(LogUtil.SEPARATOR);
			
		}
		
	}

}
