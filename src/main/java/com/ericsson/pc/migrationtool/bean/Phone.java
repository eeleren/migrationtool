package com.ericsson.pc.migrationtool.bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.ericsson.pc.migrationtool.bean.Group.Spec;

public class Phone extends Model {
	
	
	//FROM CATALOGUE.XML
	private String path;
	private String map;
	private String redVentures;
	private String defaultId;
	private String oosThresholdOverride;
	private String genieOrder;
	private List<Variation> variations = new ArrayList<Variation>();
	
	//FROM <PHONE>.XML
	private String isNew;
	private String dateLaunch;
	private String eol;
	private String shortDescription;
	private String extendedDescription;
	private String phoneName;
	private String manufacturerName;
	private String manufacturerRaw;
	private String phoneNameRaw;
	private String disclaimerMini;
	private String disclaimerFull;
	private String footerLegal;
	private String featureBar;
	private String featureSlider;
	private String featureFlip;
	private String featureTouchScreen;
	private String featureQWERTY;
	private String featureCamera;
	private String featureFronFacingCamera;
	private String featureGPS;
	private String feature4GWimax;
	private String featureWiFi;
	private String featureBluetooth;
	private String featureSpeakerphone;
	private String feature3G;
	private String feature4GLTE;
	private String feature4G;
	private String featureText;
	private String featureVideo;
	private String featureHotspot;
	private String featureEmail;
	private String featureHTMLBrowser;
	private String featureULECertified;
	private String featureMusic;
	private String featureVisualVoicemail;
	private String isPremium;
	private String synonyms;
	private String adwordsLid;
	private String adwordsDsSKwgid;
	private String bingLid;
	private String bingDsSKwgid;
	private List<Accessory> accessories = new ArrayList<Accessory>();
	private String preowned;	
	private String sku;
	private String externalUrl;
	private String slug;
	private ArrayList<String> colors = new ArrayList<String>(Arrays.asList("titanium", "white"));
	private String originalPrice;
	private List<String> compareList = new ArrayList<String>();
	private List<Feature> featureList = new ArrayList<Feature>();
	private List<SpecialFeature> specialFeatureList = new ArrayList<SpecialFeature>();
	private List<Group> groupList = new ArrayList<Group>();
	private List<Item> galleryImages = new ArrayList<Item>();
	private String mobileId;


	public List<String> getCompareList() {
		return compareList;
	}

	public void setCompareList(List<String> compareList) {
		this.compareList = compareList;
	}

	public String getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(String originalPrice) {
		this.originalPrice = originalPrice;
	}

	public String getSlug() {
		return slug;
	}


	
	
	//FROM VARIANTS
	
	public Phone(Phone p) {	
		setPath(p.getPath());
		setMap(p.getMap());
		setRedVentures(p.getRedVentures());
		setDefaultId(p.getDefaultId());
		setOosThresholdOverride(p.getOosThresholdOverride());
		setGenieOrder(p.getGenieOrder());
		setVariations(p.getVariations());
		setIsNew(p.getIsNew());
		setDateLaunch(p.getDateLaunch());
		setEol(p.getEol());
		setShortDescription(p.getShortDescription());
		setExtendedDescription(p.getExtendedDescription());
		setPhoneName(p.getPhoneName());
		setManufacturerName(p.getManufacturerName());
		setManufacturerRaw(p.getManufacturerRaw());
		setPhoneNameRaw(p.getPhoneNameRaw());
		setDisclaimerMini(p.getDisclaimerMini());
		setDisclaimerFull(p.getDisclaimerFull());
		setFooterLegal(p.getFooterLegal());
		setFeatureBar(p.getFeatureBar());
		setFeatureSlider(p.getFeatureSlider());
		setFeatureFlip(p.getFeatureFlip());
		setFeatureTouchScreen(p.getFeatureTouchScreen());
		setFeatureQWERTY(p.getFeatureQWERTY());
		setFeatureCamera(p.getFeatureCamera());
		setFeatureFronFacingCamera(p.getFeatureFronFacingCamera());
		setFeatureGPS(p.getFeatureGPS());
		setFeature4GWimax(p.getFeature4GWimax());
		setFeatureWiFi(p.getFeatureWiFi());
		setFeatureBluetooth(p.getFeatureBluetooth());
		setFeatureSpeakerphone(p.getFeatureSpeakerphone());
		setFeature3G(p.getFeature3G());
		setFeature4GLTE(p.getFeature4GLTE());
		setFeature4G(p.getFeature4G());
		setFeatureText(p.getFeatureText());
		setFeatureVideo(p.getFeatureVideo());
		setFeatureHotspot(p.getFeatureHotspot());
		setFeatureEmail(p.getFeatureEmail());
		setFeatureHTMLBrowser(p.getFeatureHTMLBrowser());
		setFeatureULECertified(p.getFeatureULECertified());
		setFeatureMusic(p.getFeatureMusic());
		setFeatureVisualVoicemail(p.getFeatureVisualVoicemail());
		setIsPremium(p.getIsPremium());
		setSynonyms(p.getSynonyms());
		setAdwordsLid(p.getAdwordsLid());
		setAdwordsDsSKwgid(p.getAdwordsDsSKwgid());
		setBingLid(p.getBingLid());
		setBingDsSKwgid(p.getBingDsSKwgid());
		setAccessories(p.getAccessories());
		setFeatureList(p.getFeatureList());
		setSpecialFeatureList(p.getSpecialFeatureList());
		setGroupList(p.getGroupList());
		setIsPreowned(p.isPreowned());
		setGalleryImages(p.getGalleryImages());
		setExternalUrl(p.getExternalUrl());
		setSku(p.getSku());
		setSlug(p.getSlug());
		setOriginalPrice(p.getOriginalPrice());
		setCompareList(p.getCompareList());
		
	};
	
	public Phone() {}

	public String getDefault_id() {
		return defaultId;
	}
	public List<Item> getGalleryImages() {
		return galleryImages;
	}
	public void setGalleryImages(List<Item> galleryImages) {
		this.galleryImages = galleryImages;
	}
	public void setDefault_id(String defaultId) {
		this.defaultId = defaultId;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getMap() {
		return map;
	}
	public void setMap(String map) {
		this.map = map;
	}
	public String getRedVentures() {
		return redVentures;
	}
	public void setRed_ventures(String redVentures) {
		this.redVentures = redVentures;
	}
	public String getOosThresholdOverride() {
		return oosThresholdOverride;
	}
	public void setOosThresholdOverride(String oosThresholdOverride) {
		this.oosThresholdOverride = oosThresholdOverride;
	}
	public String getGenieOrder() {
		return genieOrder;
	}
	public void setGenieOrder(String genieOrder) {
		this.genieOrder = genieOrder;
	}
	public List<Variation> getVariations() {
		return variations;
	}
	public void setVariations(List<Variation> variations) {
		this.variations = variations;
	}
	public String getDefaultId() {
		return defaultId;
	}
	public void setDefaultId(String defaultId) {
		this.defaultId = defaultId;
	}
	public String getIsNew() {
		return isNew;
	}
	public void setIsNew(String isNew) {
		this.isNew = isNew;
	}
	public String getDateLaunch() {
		return dateLaunch;
	}
	public void setDateLaunch(String dateLaunch) {
		this.dateLaunch = dateLaunch;
	}
	public String getEol() {
		return eol;
	}
	public void setEol(String eol) {
		this.eol = eol;
	}
	public String getShortDescription() {
		return shortDescription;
	}
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}
	public String getExtendedDescription() {
		return extendedDescription;
	}
	public void setExtendedDescription(String extendedDescription) {
		this.extendedDescription = extendedDescription;
	}
	public String getPhoneName() {
		return phoneName;
	}
	public void setPhoneName(String phoneName) {
		this.phoneName = phoneName;
	}
	public String getManufacturerName() {
		return manufacturerName;
	}
	public void setManufacturerName(String manufacturerName) {
		this.manufacturerName = manufacturerName;
	}
	public String getManufacturerRaw() {
		return manufacturerRaw;
	}
	public void setManufacturerRaw(String manufacturerRaw) {
		this.manufacturerRaw = manufacturerRaw;
	}
	public String getPhoneNameRaw() {
		return phoneNameRaw;
	}
	public void setPhoneNameRaw(String phoneNameRaw) {
		this.phoneNameRaw = phoneNameRaw;
	}
	public String getDisclaimerMini() {
		return disclaimerMini;
	}
	public void setDisclaimerMini(String disclaimerMini) {
		this.disclaimerMini = disclaimerMini;
	}
	public String getDisclaimerFull() {
		return disclaimerFull;
	}
	public void setDisclaimerFull(String disclaimerFull) {
		this.disclaimerFull = disclaimerFull;
	}
	public String getFooterLegal() {
		return footerLegal;
	}
	public void setFooterLegal(String footerLegal) {
		this.footerLegal = footerLegal;
	}
	public String getFeatureBar() {
		return featureBar;
	}
	public void setFeatureBar(String featureBar) {
		this.featureBar = featureBar;
	}
	public String getFeatureSlider() {
		return featureSlider;
	}
	public void setFeatureSlider(String featureSlider) {
		this.featureSlider = featureSlider;
	}
	public String getFeatureFlip() {
		return featureFlip;
	}
	public void setFeatureFlip(String featureFlip) {
		this.featureFlip = featureFlip;
	}
	public String getFeatureTouchScreen() {
		return featureTouchScreen;
	}
	public void setFeatureTouchScreen(String featureTouchScreen) {
		this.featureTouchScreen = featureTouchScreen;
	}
	public String getFeatureQWERTY() {
		return featureQWERTY;
	}
	public void setFeatureQWERTY(String featureQWERTY) {
		this.featureQWERTY = featureQWERTY;
	}
	public String getFeatureCamera() {
		return featureCamera;
	}
	public void setFeatureCamera(String featureCamera) {
		this.featureCamera = featureCamera;
	}
	public String getFeatureFronFacingCamera() {
		return featureFronFacingCamera;
	}
	public void setFeatureFronFacingCamera(String featureFronFacingCamera) {
		this.featureFronFacingCamera = featureFronFacingCamera;
	}
	public String getFeatureGPS() {
		return featureGPS;
	}
	public void setFeatureGPS(String featureGPS) {
		this.featureGPS = featureGPS;
	}
	public String getFeature4GWimax() {
		return feature4GWimax;
	}
	public void setFeature4GWimax(String feature4gWimax) {
		feature4GWimax = feature4gWimax;
	}
	public String getFeatureWiFi() {
		return featureWiFi;
	}
	public void setFeatureWiFi(String featureWiFi) {
		this.featureWiFi = featureWiFi;
	}
	public String getFeatureBluetooth() {
		return featureBluetooth;
	}
	public void setFeatureBluetooth(String featureBluetooth) {
		this.featureBluetooth = featureBluetooth;
	}
	public String getFeatureSpeakerphone() {
		return featureSpeakerphone;
	}
	public void setFeatureSpeakerphone(String featureSpeakerphone) {
		this.featureSpeakerphone = featureSpeakerphone;
	}
	public String getFeature3G() {
		return feature3G;
	}
	public void setFeature3G(String feature3g) {
		feature3G = feature3g;
	}
	public String getFeature4GLTE() {
		return feature4GLTE;
	}
	public void setFeature4GLTE(String feature4glte) {
		feature4GLTE = feature4glte;
	}
	public String getFeature4G() {
		return feature4G;
	}
	public void setFeature4G(String feature4g) {
		feature4G = feature4g;
	}
	public String getFeatureText() {
		return featureText;
	}
	public void setFeatureText(String featureText) {
		this.featureText = featureText;
	}
	public String getFeatureVideo() {
		return featureVideo;
	}
	public void setFeatureVideo(String featureVideo) {
		this.featureVideo = featureVideo;
	}
	public String getFeatureHotspot() {
		return featureHotspot;
	}
	public void setFeatureHotspot(String featureHotspot) {
		this.featureHotspot = featureHotspot;
	}
	public String getFeatureEmail() {
		return featureEmail;
	}
	public void setFeatureEmail(String featureEmail) {
		this.featureEmail = featureEmail;
	}
	public String getFeatureHTMLBrowser() {
		return featureHTMLBrowser;
	}
	public void setFeatureHTMLBrowser(String featureHTMLBrowser) {
		this.featureHTMLBrowser = featureHTMLBrowser;
	}
	public String getFeatureULECertified() {
		return featureULECertified;
	}
	public void setFeatureULECertified(String featureULECertified) {
		this.featureULECertified = featureULECertified;
	}
	public String getFeatureMusic() {
		return featureMusic;
	}
	public void setFeatureMusic(String featureMusic) {
		this.featureMusic = featureMusic;
	}
	public String getFeatureVisualVoicemail() {
		return featureVisualVoicemail;
	}
	public void setFeatureVisualVoicemail(String featureVisualVoicemail) {
		this.featureVisualVoicemail = featureVisualVoicemail;
	}
	public String getIsPremium() {
		return isPremium;
	}
	public void setIsPremium(String isPremium) {
		this.isPremium = isPremium;
	}
	public String getSynonyms() {
		return synonyms;
	}
	public void setSynonyms(String synonyms) {
		this.synonyms = synonyms;
	}
	public String getAdwordsLid() {
		return adwordsLid;
	}
	public void setAdwordsLid(String adwordsLid) {
		this.adwordsLid = adwordsLid;
	}
	public String getAdwordsDsSKwgid() {
		return adwordsDsSKwgid;
	}
	public void setAdwordsDsSKwgid(String adwordsDsSKwgid) {
		this.adwordsDsSKwgid = adwordsDsSKwgid;
	}
	public String getBingLid() {
		return bingLid;
	}
	public void setBingLid(String bingLid) {
		this.bingLid = bingLid;
	}
	public String getBingDsSKwgid() {
		return bingDsSKwgid;
	}
	public void setBingDsSKwgid(String bingDsSKwgid) {
		this.bingDsSKwgid = bingDsSKwgid;
	}
	public List<Accessory> getAccessories() {
		return accessories;
	}
	public void setAccessories(List<Accessory> accessories) {
		this.accessories = accessories;
	}

	public List<Feature> getFeatureList() {
		return featureList;
	}
	public void setFeatureList(List<Feature> featureList) {
		this.featureList = featureList;
	}
	public List<SpecialFeature> getSpecialFeatureList() {
		return specialFeatureList;
	}
	public void setSpecialFeatureList(List<SpecialFeature> specialFeatureList) {
		this.specialFeatureList = specialFeatureList;
	}
	public void setRedVentures(String redVentures) {
		this.redVentures = redVentures;
	}
	public List<Group> getGroupList() {
		return groupList;
	}
	public void setGroupList(List<Group> listGroup) {
		this.groupList = listGroup;
	}
	public Variation getVariationById(String vId) {
		List<Variation> variationList = this.getVariations();
		
		for (Variation v : variationList) {
			if (vId.equalsIgnoreCase(v.getId())) {
				return v;
			}
		}
		
		return null;
	}
	
	public boolean hasVariations(String slug) {
		boolean result = false;
		if (!variations.isEmpty()) {
			result = true;
		} else {
			for (String c: colors) {
				if (slug.contains(c)) {
					List<Variation> variation = new ArrayList<Variation>();
					Variation v = new Variation();
					v.setColorVariant(c);
					v.setId(getSku());
					variation.add(v);
					this.setVariations(variation);
					result = true;
					break;
				}
			}
		}		
		return result;
	}

	public void setIsPreowned(String preowned) {
		this.preowned = preowned;
		
	}
	public String isPreowned() {
		return preowned;
	}
	
	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getExternalUrl() {
		return externalUrl;
	}

	public void setExternalUrl(String externalUrl) {
		this.externalUrl = externalUrl;
	}
	
	
	public String getMobileId() {
		return mobileId;
	}

	public void setMobileId(String mobileId) {
		this.mobileId = mobileId;
	}
	
	public String getGroupValueById(String id) {
		
		List<Group> groups = this.getGroupList();
		for (Group g: groups) {
			if(g.getId().equalsIgnoreCase(id)) {
				return g.getValue();
		}
	}
		return null;
	}
	public String getSpecByGroupIdAndSpecType(String id, String type) {
		
		List<Group> groups = this.getGroupList();
		List<Spec> specs = new ArrayList<Spec>();
		String result = "";
 		for (Group g: groups) {
			if(g.getId().equalsIgnoreCase(id)) {
				specs =  g.getListSpec();
				for (Spec s: specs) {
					if(s.getType().equalsIgnoreCase(type)) {
						result = s.getValue();
					}
				}
		}
	}
 		if ((result==null)||(("").equalsIgnoreCase(result))) {
 			result = "Not Available";
 		} return result;
	}

	public void setSlug(String slug) {
		this.slug = slug;
		
	}
	/**
	 * Check whether compare structure is in the format: 
	 *   
	 *   <feature id="4g" featured="compare_only" url="" order=""></feature>
	 *   
	 *   or
	 *      <compare> 
            <item id="os"><![CDATA[Android&#8482; 2.3.6]]></item> 
            </compare>
            
       and update the Phone accordingly
	 * */
	public void setCompareStructure() {
		List<String> compareList = this.getCompareList();
		List<Feature> featureList = this.getFeatureList();
		for (Feature f : featureList) {
			if((f.getFeatured()!=null)||("".equals(f.getFeatured()))) {
				if(f.getFeatured().equalsIgnoreCase("compare_only")) {
					compareList.add(f.getFeatureId());
				}
			}
		
		}
		
	}
	
	/**
	 * Check whether special features are listed as features using the following structure: 
	 *   
	 *   <feature id="4g" featured="false" url="" order=""></feature>
	 *   
	 *in case the field featured is equals to false, the feature is a special features and the phone is updated accordingly
	 * */
	public void setSpecFeatureStructure() {
		List<SpecialFeature> specFeaturesList = this.getSpecialFeatureList();
		List<Feature> featureList = this.getFeatureList();
		for (Feature f : featureList) {
			if((f.getFeatured()!=null)||("".equals(f.getFeatured()))) {
				if(f.getFeatured().equalsIgnoreCase("false")) {
					SpecialFeature sf = new SpecialFeature();
					sf.setSpecialFeatureId(f.getFeatureId());
					specFeaturesList.add(sf);
				}
			}
		
		}
		
	}
	

}
