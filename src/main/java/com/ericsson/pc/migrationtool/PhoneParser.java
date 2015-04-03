package com.ericsson.pc.migrationtool;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.ericsson.pc.migrationtool.bean.Accessory;
import com.ericsson.pc.migrationtool.bean.Feature;
import com.ericsson.pc.migrationtool.bean.Group;
import com.ericsson.pc.migrationtool.bean.Group.Spec;
import com.ericsson.pc.migrationtool.bean.Item;
import com.ericsson.pc.migrationtool.bean.Model;
import com.ericsson.pc.migrationtool.bean.Phone;
import com.ericsson.pc.migrationtool.bean.SpecialFeature;
import com.ericsson.pc.migrationtool.bean.Variation;
import com.ericsson.pc.migrationtool.builder.phone.PhoneConstants;
import com.ericsson.pc.migrationtool.interfaces.Parser;
import com.ericsson.pc.migrationtool.util.ApplicationPropertiesReader;
import com.ericsson.pc.migrationtool.util.LogUtil;
import com.ericsson.pc.migrationtool.util.PathUtil;
import com.ericsson.pc.migrationtool.util.XPathUtil;

public class PhoneParser implements Parser {
	
	final static Logger logger = Logger.getLogger(PhoneParser.class);
	final static String brandId = ApplicationPropertiesReader.getInstance().getProperty("builder.asset.phone.brandId");	
	
	public Parser getNewInstance() {
		return new PhoneParser();
	}
	public List<Model> execute(String filePath) throws FileNotFoundException {
		if (filePath == null) {
			return execute();
		} else {
			logger.info("SINGLE PARSER EXECUTION STARTED...");
			String phoneFilePath = PathUtil.getSinglePhoneFilePath(filePath);
			
			if ((phoneFilePath!=null)&&(!phoneFilePath.equals(""))) {
				List<String> phonePathList = new ArrayList<String>();
				phonePathList.add(phoneFilePath);
				return parse(phonePathList);
			} else 
				throw new FileNotFoundException();
			
		}
	}
	
	public List<Model> execute() {
		logger.info("PARSER EXECUTION STARTED...");
		List<String> phonePathList = PathUtil.getPhonesFilesPath();	
		return parse(phonePathList);
	}
	
	public List<Model> parse(List<String> phonePathList) {
		List phoneList = new ArrayList<Phone>();
		
		for (String phoneFilePath : phonePathList) {
			phoneList.add(getPhone(phoneFilePath));
			if (logger.isDebugEnabled()) {
				logger.debug("Adding file to parsing list: " + phoneFilePath);
			}
		}
		
		logger.info("Found " + phoneList.size() + " phones in folder " + PathUtil.getPhonesPath());
		
		getCatalogue(phoneList);
		getAccessories(phoneList);
		cleanPhoneList(phoneList);
		
		LogUtil.logPhones(phoneList);
		
		logger.info("PARSER EXECUTION COMPLETED!");
		
		return phoneList;
	}
	
	private void getAccessories(List<Phone> phoneList) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = null;
        XPathFactory xpathFactory = XPathFactory.newInstance();
		XPath xpath = xpathFactory.newXPath();
		
		try {
			builder = factory.newDocumentBuilder();
	        Document doc = builder.parse(PathUtil.getAccessoryPath());
	        
	        for (Phone phone : phoneList) {
	        	for (Accessory accessory : phone.getAccessories()) {
	        		String accessoryId = accessory.getId();
	        		
	        		accessory.setCategory(XPathUtil.getValueAsString(doc, xpath, "/accessories/accessory[@id='" + accessoryId + "']/@type"));
	        		accessory.setDescription(XPathUtil.getValueAsString(doc, xpath, "/accessories/accessory[@id='" + accessoryId + "']/description"));
	        		accessory.setLabel(XPathUtil.getValueAsString(doc, xpath, "/accessories/accessory[@id='" + accessoryId + "']/label"));
	        	}
	        }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            logger.error(e, e);
        }
	}
	
	private void cleanPhoneList(List<Phone> phoneList) {
		String cleanup = ApplicationPropertiesReader.getInstance().getProperty("parser.phones.cleanup");
		
		if (cleanup.equalsIgnoreCase("true")) {
			List<Phone> temp = new ArrayList<Phone>(phoneList);
			
			for (Phone phone : temp) {
				String id = phone.getSku();
				if (id.equalsIgnoreCase("") || id == null) {
					phoneList.remove(phone);
				}
			}
		}
	}

	private void getCatalogue(List<Phone> phoneList) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = null;
        XPathFactory xpathFactory = XPathFactory.newInstance();
		XPath xpath = xpathFactory.newXPath();
		
		try {
			builder = factory.newDocumentBuilder();
	        Document doc = builder.parse(PathUtil.getCataloguePath());
	        
	        for (Phone phone : phoneList) {
	        	String phoneId = phone.getSku();
	        	
	        	//SMART SOLUTION
        		Node phoneNode = XPathUtil.getValueAsNodes(doc, xpath, "/payload/products/phones/phone[@id='" + phoneId + "']").item(0);
        		if (phoneNode != null) {
        			phone.setPath(XPathUtil.getValueAsString(doc, xpath, "/payload/products/phones/phone[@id='" + phoneId + "']/@path"));
        			phone.setRedVentures(XPathUtil.getValueAsString(doc, xpath, "/payload/products/phones/phone[@id='" + phoneId + "']/@red-ventures"));
        			phone.setOosThresholdOverride(XPathUtil.getValueAsString(doc, xpath, "/payload/products/phones/phone[@id='" + phoneId + "']/@oos-threshold-override"));
        			phone.setDefault_id(XPathUtil.getValueAsString(doc, xpath, "/payload/products/phones/phone[@id='" + phoneId + "']/@default-id"));
        			phone.setMap(XPathUtil.getValueAsString(doc, xpath, "/payload/products/phones/phone[@id='" + phoneId + "']/@map"));
        			
        			if (phoneNode.hasChildNodes()) {
        				NodeList variationNodes = XPathUtil.getValueAsNodes(doc, xpath, "/payload/products/phones/phone[@id='" + phoneId +"']/variation");
                		
                		Variation variation = null;
                		for (int i=0; i < variationNodes.getLength(); i++) {
                			
                			Node variationNode = variationNodes.item(i);
                			
                			String vId = (String)variationNode.getAttributes().getNamedItem("id").getNodeValue();
                        	String vOss_threshold_override = (String)variationNode.getAttributes().getNamedItem("oos-threshold-override").getNodeValue();
                        	String vMap =  (String)variationNode.getAttributes().getNamedItem("map").getNodeValue();
                        	
                        	variation = phone.getVariationById(vId);
                        	if (variation == null) {
                        		variation = new Variation();
	                        	
	                        	
	                        	phone.getVariations().add(variation);
                        	}
                        	else {
                        		variation.setId(vId);
	                        	variation.setMap(vMap);
	                        	variation.setOosThresholdOverride(vOss_threshold_override);
                        	}
                		}
        				
        			}
        		}
	        }
			 
		} catch (ParserConfigurationException | SAXException | IOException e) {
			logger.error(e, e);
        }
	}
	
	private Phone getPhone(String phoneFilePath) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = null;
        XPathFactory xpathFactory = XPathFactory.newInstance();
		XPath xpath = xpathFactory.newXPath();
        
        Phone phone = new Phone();
        
        try {
	        builder = factory.newDocumentBuilder();
	        Document doc = builder.parse(phoneFilePath);
	        logger.debug("Parsing ..."+phoneFilePath);
	        phone.setIsPreowned(phoneFilePath.contains("preowned")+"");
	        phone.setSku(XPathUtil.getValueAsString(doc, xpath, "/page/product/@id"));
	    	phone.setIsNew(XPathUtil.getValueAsString(doc, xpath, "/page/product/options/@new"));
	    	phone.setDateLaunch(XPathUtil.getValueAsString(doc, xpath, "/page/product/attributes/@date-launch"));
	    	phone.setEol(XPathUtil.getValueAsString(doc, xpath, "/page/product/options/@eol"));
	    	phone.setShortDescription(XPathUtil.getValueAsString(doc, xpath, "/page/product/descriptions/list-description"));
	    	phone.setExtendedDescription(XPathUtil.getValueAsString(doc, xpath, "/page/product/descriptions/list-extended"));
	    	phone.setPhoneName(XPathUtil.getValueAsString(doc, xpath, "/page/product/names/name"));
	    	phone.setManufacturerName(XPathUtil.getValueAsString(doc, xpath, "/page/product/names/brand"));
	    	phone.setPhoneNameRaw(XPathUtil.getValueAsString(doc, xpath, "/page/product/names/nameraw"));
	    	phone.setManufacturerRaw(XPathUtil.getValueAsString(doc, xpath, "/page/product/names/brandraw"));
	    	phone.setDisclaimerMini(XPathUtil.getValueAsString(doc, xpath, "/page/product/legal/disclaimer-mini"));
	    	phone.setDisclaimerFull(XPathUtil.getValueAsString(doc, xpath, "/page/product/legal/disclaimer-full"));
	    	phone.setFooterLegal(XPathUtil.getValueAsString(doc, xpath, "/page/product/legal/footer-legal"));
	    	
	    	//VARIATIONS
	    	NodeList varNodeList = XPathUtil.getValueAsNodes(doc, xpath, "/page/product/variations/products/product");
	    	
	    	if (varNodeList != null) {
	    		Variation variation = null;
	    		
		    	for (int i=0; i<varNodeList.getLength(); i++) {
		    		Node n = varNodeList.item(i);
		    		variation = new Variation();
		    		
		    		variation.setId(n.getAttributes().getNamedItem("id").getNodeValue());
		    		String color = n.getAttributes().getNamedItem("color").getNodeValue();
		    		variation.setColorVariant(color);
		    		variation.setMemoryVariant(n.getAttributes().getNamedItem("memory").getNodeValue());
		    		
		    		variation.setGradientColor(XPathUtil.getValueAsString(doc, xpath, "/page/product/variations/options/option[@id='color']/value[@id='" + color + "']/@gradient-color"));
		    		//String gradientColor = variation.getGradientColor().replaceAll("#", "c");
		    		//variation.setGradientColor(gradientColor);
		    		variation.setColorId(XPathUtil.getValueAsString(doc, xpath, "/page/product/variations/options/option[@id='color']/value[@id='" + color + "']/@color"));
		    		
		    		phone.getVariations().add(variation);
		    		
		    	}
	    	}
	    	
	    	//FILTERS
	    	phone.setFeatureBar(XPathUtil.getValueAsString(doc, xpath, "/page/product/filters/@bar"));
	    	phone.setFeatureSlider(XPathUtil.getValueAsString(doc, xpath, "/page/product/filters/@slider"));
	    	phone.setFeatureFlip(XPathUtil.getValueAsString(doc, xpath, "/page/product/filters/@flip"));
	    	phone.setFeatureTouchScreen(XPathUtil.getValueAsString(doc, xpath, "/page/product/filters/@touchscreen"));
	    	phone.setFeatureQWERTY(XPathUtil.getValueAsString(doc, xpath, "/page/product/filters/@qwerty"));
	    	phone.setFeatureCamera(XPathUtil.getValueAsString(doc, xpath, "/page/product/filters/@camera"));
	    	phone.setFeatureFronFacingCamera(XPathUtil.getValueAsString(doc, xpath, "/page/product/filters/@frontfacingcamera"));
	    	phone.setFeatureGPS(XPathUtil.getValueAsString(doc, xpath, "/page/product/filters/@gps"));
	    	phone.setFeature4GWimax(XPathUtil.getValueAsString(doc, xpath, "/page/product/filters/@fourGwimax"));
	    	phone.setFeatureWiFi(XPathUtil.getValueAsString(doc, xpath, "/page/product/filters/@wifi"));
	    	phone.setFeatureBluetooth(XPathUtil.getValueAsString(doc, xpath, "/page/product/filters/@bluetooth"));
	    	phone.setFeatureSpeakerphone(XPathUtil.getValueAsString(doc, xpath, "/page/product/filters/@speakerphone"));
	    	phone.setFeature3G(XPathUtil.getValueAsString(doc, xpath, "/page/product/filters/@threeG"));
	    	phone.setFeature4GLTE(XPathUtil.getValueAsString(doc, xpath, "/page/product/filters/@fourGlte"));
	    	phone.setFeature4G(XPathUtil.getValueAsString(doc, xpath, "/page/product/filters/@fourGwimax"));
	    	phone.setFeatureText(XPathUtil.getValueAsString(doc, xpath, "/page/product/filters/@text"));
	    	phone.setFeatureVideo(XPathUtil.getValueAsString(doc, xpath, "/page/product/filters/@video"));
	    	phone.setFeatureHotspot(XPathUtil.getValueAsString(doc, xpath, "/page/product/filters/@hotspot"));
	    	phone.setFeatureEmail(XPathUtil.getValueAsString(doc, xpath, "/page/product/filters/@email"));
	    	phone.setFeatureHTMLBrowser(XPathUtil.getValueAsString(doc, xpath, "/page/product/filters/@htmlwebbrowser"));
	    	phone.setFeatureULECertified(XPathUtil.getValueAsString(doc, xpath, "/page/product/filters/@ulecertified"));
	    	phone.setFeatureMusic(XPathUtil.getValueAsString(doc, xpath, "/page/product/filters/@music"));
	    	phone.setFeatureVisualVoicemail(XPathUtil.getValueAsString(doc, xpath, "/page/product/filters/@visualvoicemail")); //???
	    	
	    	//GALLERY-IMAGES
	    	List<Item> galleryImages = phone.getGalleryImages();
	    	Item item = null;
	    		
    		NodeList galleryNodeList = XPathUtil.getValueAsNodes(doc, xpath, "/page/product/gallery-images/item");
    		
    		for (int i=0; i<galleryNodeList.getLength(); i++) {
    			item = new Item();
    			item.setGalleryType(galleryNodeList.item(i).getAttributes().getNamedItem("galleryType").getNodeValue());
    			item.setView(galleryNodeList.item(i).getAttributes().getNamedItem("view").getNodeValue());
    			item.setOrder(galleryNodeList.item(i).getAttributes().getNamedItem("order").getNodeValue());
    			galleryImages.add(item);
    		}
	    		
	    	
	    	phone.setSynonyms(XPathUtil.getValueAsString(doc, xpath, "/page/product/filters/synonyms"));
	    	phone.setAdwordsLid(XPathUtil.getValueAsString(doc, xpath, "/page/product/tracking/adwords_lid"));
	    	phone.setAdwordsDsSKwgid(XPathUtil.getValueAsString(doc, xpath, "/page/product/tracking/adwords_ds_s_kwgid"));
	    	phone.setBingLid(XPathUtil.getValueAsString(doc, xpath, "/page/product/tracking/bing_lid"));
	    	phone.setBingDsSKwgid(XPathUtil.getValueAsString(doc, xpath, "/page/product/tracking/bing_ds_s_kwgid"));
	    	
	    	//ACCESSORIES
	    	List<Accessory> accessoryList = phone.getAccessories();
	    	NodeList nodeList = XPathUtil.getValueAsNodes(doc, xpath, "/page/product/accessories/accessory/text()");
	    	for (int i=0; i<nodeList.getLength(); i++) {
	    		accessoryList.add(new Accessory(nodeList.item(i).getNodeValue()));;
	    	}
	    	
	    	//COMPARE
	    	phone.setCompareItemOS(XPathUtil.getValueAsString(doc, xpath, "/page/product/compare/item[@id='os']"));
	    	phone.setCompareItemDisplay(XPathUtil.getValueAsString(doc, xpath, "/page/product/compare/item[@id='display']"));
	    	phone.setCompareItemCamera(XPathUtil.getValueAsString(doc, xpath, "/page/product/compare/item[@id='camera']"));
	    	phone.setCompareItemWifi(XPathUtil.getValueAsString(doc, xpath, "/page/product/compare/item[@id='wifi']"));
	    	phone.setCompareItem4G(XPathUtil.getValueAsString(doc, xpath, "/page/product/compare/item[@id='4g']"));
	    	phone.setCompareItemHotspot(XPathUtil.getValueAsString(doc, xpath, "/page/product/compare/item[@id='hotspot']"));
	    	phone.setCompareItemQWERTYKeyboard(XPathUtil.getValueAsString(doc, xpath, "/page/product/compare/item[@id='qwerty-keyboard']"));
	    	phone.setCompareItemWebBrowser(XPathUtil.getValueAsString(doc, xpath, "/page/product/compare/item[@id='web-browser']"));
	    	phone.setCompareItemFlashPlayer(XPathUtil.getValueAsString(doc, xpath, "/page/product/compare/item[@id='flash-player']"));
	    	phone.setCompareItemEmail(XPathUtil.getValueAsString(doc, xpath, "/page/product/compare/item[@id='email']"));
	    	phone.setCompareItemVideo(XPathUtil.getValueAsString(doc, xpath, "/page/product/compare/item[@id='video']"));
	    	phone.setCompareItemMusicPlayer(XPathUtil.getValueAsString(doc, xpath, "/page/product/compare/item[@id='music-player']"));
	    	phone.setCompareItemGPS(XPathUtil.getValueAsString(doc, xpath, "/page/product/compare/item[@id='gps']"));
	    	phone.setCompareItemSpeakerphone(XPathUtil.getValueAsString(doc, xpath, "/page/product/compare/item[@id='speakerphone']"));
	    	phone.setCompareItemMemory(XPathUtil.getValueAsString(doc, xpath, "/page/product/compare/item[@id='memory']"));
	    	phone.setCompareItemProcessor(XPathUtil.getValueAsString(doc, xpath, "/page/product/compare/item[@id='processor']"));
	    	phone.setCompareItemCalendar(XPathUtil.getValueAsString(doc, xpath, "/page/product/compare/item[@id='calendar']"));
	    	phone.setCompareItemVisualVoicemail(XPathUtil.getValueAsString(doc, xpath, "/page/product/compare/item[@id='visual-voicemail']"));
	    	phone.setCompareItem3G(XPathUtil.getValueAsString(doc, xpath, "/page/product/compare/item[@id='3g']"));
	    	phone.setCompareItemBluetooth(XPathUtil.getValueAsString(doc, xpath, "/page/product/compare/item[@id='bluetooth']"));
	    	
	    	//features
	    	List<Feature> featureList = phone.getFeatureList();
	    	Feature feature = null;
	    	NodeList nodeListFeature = XPathUtil.getValueAsNodes(doc, xpath, "/page/product/features/feature");
	    	
	    	for(int i=0; i<nodeListFeature.getLength(); i++) {
	    		feature = new Feature();
	    		feature.setFeatureId(String.valueOf(i));
	    		Node node = nodeListFeature.item(i).getAttributes().getNamedItem("icon");
	    		if (node != null) {
	    			String featureIconName = node.getNodeValue();
	        		feature.setExtraFeatureIconName(featureIconName);
	        		feature.setExtraFeatureTitle(XPathUtil.getValueAsString(doc, xpath, "/page/product/features/feature[@icon='" + featureIconName + "']/title/text()"));
	        		feature.setExtraFeatureDescription(XPathUtil.getValueAsString(doc, xpath, "/page/product/features/feature[@icon='" + featureIconName + "']/description/text()"));
	    		}
	    		
	    		featureList.add(feature);
	    	}
	    	
	    	//SPEACIAL FEATURES
	    	List<SpecialFeature> specialFeatureList = phone.getSpecialFeatureList();
	    	SpecialFeature specialFeature = null;
	    	NodeList nodeListSpecialFeature = XPathUtil.getValueAsNodes(doc, xpath, "/page/product/features/list/item/text()");
	    	
	    	for (int i=0; i<nodeListSpecialFeature.getLength(); i++) {
	    		specialFeature = new SpecialFeature();
	    		specialFeature.setSpecialFeatureId(String.valueOf(i));
	    		specialFeature.setSpecialFeatureName(nodeListSpecialFeature.item(i).getTextContent());
	    		
	    		specialFeatureList.add(specialFeature);
	    	}
	    	
	    	//GROUPS
	    	List<Group> groupList = phone.getGroupList();
	    	
	    	//generating single group for 'os'
	    	String os = XPathUtil.getValueAsString(doc, xpath, "/page/product/tech-specs/os/text()");
			Group group = new Group();
			group.setValue(os);
			group.setId("os");
			group.setOrder("0");
			groupList.add(group);
			
			NodeList nodeListGroup = XPathUtil.getValueAsNodes(doc, xpath, "/page/product/tech-specs/group");
			
			for (int i=0; i<nodeListGroup.getLength(); i++) {
				Node node = nodeListGroup.item(i);
				String groupId = node.getAttributes().getNamedItem("id").getNodeValue();
				
				group = new Group();
				group.setId(groupId);
				group.setOrder(String.valueOf(i + 1));
				group.setThumb(XPathUtil.getValueAsString(doc, xpath, "/page/product/tech-specs/group[@id='" + groupId + "']/@thumb"));
				
				List<Spec> specList = group.getListSpec();
				
				NodeList nodeListSpec = XPathUtil.getValueAsNodes(doc, xpath, "/page/product/tech-specs/group[@id='" + groupId + "']/specs/spec");
				
				for (int j=0; j<nodeListSpec.getLength(); j++) {
					Spec spec = group.new Spec();
					spec.setOrder(nodeListSpec.item(j).getAttributes().getNamedItem("order").getNodeValue());
					spec.setType(nodeListSpec.item(j).getAttributes().getNamedItem("type").getNodeValue());
					spec.setValue(nodeListSpec.item(j).getTextContent());
					
					specList.add(spec);
				}
				
				groupList.add(group);
			}
			//External Url: set to manufacturerNameRaw-phoneNameRaw for BOOST (i.e.:moto-e-lte), SKU for SPP and VMU
			if (brandId.equalsIgnoreCase(PhoneConstants.BRAND_ID_BOOST)) {
				phone.setExternalUrl(FilenameUtils.removeExtension(new File(phoneFilePath).getName()));
			} else {
				phone.setExternalUrl(phone.getSku());
			}
			
	        
			
        } catch (ParserConfigurationException | SAXException | IOException e) {
            logger.error(e, e);
        }
			
		return phone;
	}
}