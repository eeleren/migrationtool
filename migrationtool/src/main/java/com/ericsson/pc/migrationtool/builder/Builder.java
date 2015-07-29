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
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.ericsson.pc.migrationtool.bean.Accessory;
import com.ericsson.pc.migrationtool.bean.Phone;
import com.ericsson.pc.migrationtool.bean.PhoneAsset;
import com.ericsson.pc.migrationtool.bean.Variation;
import com.ericsson.pc.migrationtool.interfaces.Parser;
import com.ericsson.pc.migrationtool.util.ApplicationPropertiesReader;

public class Builder {
	
	private static PhoneAsset phoneAsset;
	
	public static void main (String[] args) {
		
		PhoneVariationBuilder builder = new PhoneVariationBuilder();
		ArrayList<PhoneAsset> phones = new ArrayList<PhoneAsset>();
		phoneAsset = new PhoneAsset();
		phoneAsset.setId("asdasdasd");	
		phoneAsset.setPhoneName("lg-rumor-reflex-preowned_automatic");
		phoneAsset.setManufacturerName("lg");
		phones.add(phoneAsset);

		for (int i = 0; i < phones.size(); i++ ) {
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
		}
		
	}
	
	public void createPhoneAsset(PhoneAsset phone) throws SAXException, IOException {
		
		//String outputPath = ApplicationPropertiesReader.getInstance().getProperty("builder.phone.outputdir");		
		String extension = PhoneConstants.FILE_EXTENSION;		
				
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
				rootElement.setAttribute(PhoneConstants.EXTERNAL_ID, phone.getId());
				asset.appendChild(rootElement);
				
				//deployed element
				Element deployed = doc.createElement(PhoneConstants.DEPLOYED_FIELD);
				rootElement.appendChild(this.setMandatoryFields(null, PhoneConstants.DEPLOYED_FIELD, doc, "true"));
				
				//service element
				Element service = doc.createElement(PhoneConstants.SERVICE_FIELD);
				//rootElement.appendChild(this.setMandatoryFields(null, "service", doc, applicationproperties));
				rootElement.appendChild(this.setMandatoryFields(null, PhoneConstants.SERVICE_FIELD, doc, "S-pYcwCwRj6zV"));
				
				
				//brandId element
				Element brandId = doc.createElement(PhoneConstants.BRAND_ID_FIELD);
				rootElement.appendChild(this.setMandatoryFields(null, PhoneConstants.BRAND_ID_FIELD, doc, PhoneConstants.BRAND_ID_VALUE));
				
				
				//msrp element
				Element msrp = doc.createElement(PhoneConstants.MSRP_FIELD);
				rootElement.appendChild(this.setMandatoryFields(null, PhoneConstants.MSRP_FIELD, doc, PhoneConstants.MSRP_VALUE));
				
				//eqp element
				Element eqp = doc.createElement(PhoneConstants.EQP_FIELD);
				rootElement.appendChild(this.setMandatoryFields(null, PhoneConstants.EQP_FIELD, doc, PhoneConstants.EQP_VALUE));				
				
				
				//MinimumAdvPrice element				
				Element minimumAdvPrice = doc.createElement(PhoneConstants.MIN_ADV_PRICE_FIELD);
				rootElement.appendChild(this.setMandatoryFields(phone.getMap(), PhoneConstants.MIN_ADV_PRICE_FIELD, doc, ""));
				minimumAdvPrice.appendChild(doc.createTextNode(phone.getMap()));
				rootElement.appendChild(minimumAdvPrice);
				
				
				//oos-treshold element
				Element oosTreshold = doc.createElement("oosThreshold");
				//msrp.appendChild(doc.createTextNode(ApplicationPropertiesReader.getInstance().getProperty("builder.asset.phone.defaultOOSTresholdValue")));	
				oosTreshold.appendChild(doc.createTextNode("100"));
				rootElement.appendChild(oosTreshold);
				
				//available element
				Element available = doc.createElement("available");
				available.appendChild(doc.createTextNode("true"));
				rootElement.appendChild(available);
				
				//Override-oos element
				if(phone.getOosThresholdOverride()!=null) {
					Element oosThresholdOverride = doc.createElement("overrideOOS");
					oosThresholdOverride.appendChild(doc.createTextNode(phone.getOosThresholdOverride()));
					rootElement.appendChild(oosThresholdOverride);
				}
				
				//phoneId element
				Element phoneId = doc.createElement("phoneId");
				phoneId.appendChild(doc.createTextNode(phone.getId()));
				rootElement.appendChild(phoneId);
				
				//defaultVariantId element
				if(phone.getDefaultId()!=null) {
					Element defaultId = doc.createElement("defaultVariantId");
					defaultId.appendChild(doc.createTextNode(phone.getDefaultId()));
					rootElement.appendChild(defaultId);					
				}
				
				//redVentures element
				Element redVentures = doc.createElement("isRedVentures");
				redVentures.appendChild(doc.createTextNode((phone.getRedVentures()!=null)+""));
				rootElement.appendChild(redVentures);
				
				//importPath element
				Element importPath = doc.createElement("importPath");
				//importPath.appendChild(doc.createTextNode(ApplicationPropertiesReader.getInstance().getProperty("builder.asset.phone.importPath")));
				importPath.appendChild(doc.createTextNode("boh"));
				rootElement.appendChild(importPath);
				
				//genieOrder element
				if(phone.getGenieOrder()!=null) {
					Element genieOrder = doc.createElement("genieOrder");
					genieOrder.appendChild(doc.createTextNode(phone.getGenieOrder()));
					rootElement.appendChild(genieOrder);					
				}
				
				//isNew element
				Element isNew = doc.createElement("isNew");
				isNew.appendChild(doc.createTextNode((phone.getIsNew()!=null)+""));
				rootElement.appendChild(isNew);		
				
				//isPreowned element ->if phone name ends with preowned?
				Element isPreowned = doc.createElement("isPreOwned");	
				isPreowned.appendChild(doc.createTextNode(phone.getPhoneName().contains("preowned")+""));
				rootElement.appendChild(isPreowned);
				
				
				//dateLaunch element
				if(phone.getDateLaunch()!=null) {
					Element dateLaunch = doc.createElement("dateLaunch");
					dateLaunch.appendChild(doc.createTextNode(phone.getDateLaunch()));
					rootElement.appendChild(dateLaunch);	
				}
				
				//shortDescription element 
				Element shortDescription = doc.createElement("shortDescription");	
				shortDescription.appendChild(doc.createTextNode(phone.getShortDescription()+""));
				rootElement.appendChild(shortDescription);
				
				//extendedDescription element
				if(phone.getExtendedDescription()!=null) {
					Element extendedDescription = doc.createElement("extendedDescription");
					extendedDescription.appendChild(doc.createTextNode(phone.getExtendedDescription()));
					rootElement.appendChild(extendedDescription);	
				}
			
				//phoneName element 
				Element phoneName = doc.createElement("phoneName");	
				phoneName.appendChild(doc.createTextNode(phone.getPhoneName()));
				rootElement.appendChild(phoneName);
				
				//manufacturerName element 
				Element manufacturerName = doc.createElement("manufacturerName");	
				manufacturerName.appendChild(doc.createTextNode(phone.getManufacturerName()+""));
				rootElement.appendChild(manufacturerName);
				
		
				//manufacturerRaw element
				Element manufacturerRaw = doc.createElement("manufacturerRaw");
				if(phone.getManufacturerRaw()!=null) {					
					manufacturerRaw.appendChild(doc.createTextNode(phone.getManufacturerRaw()));
						
				} else {
					manufacturerRaw.appendChild(doc.createTextNode(phone.getManufacturerName()+""));
				}
				rootElement.appendChild(manufacturerRaw);
				
				//phoneNameRaw element
				if(phone.getPhoneNameRaw()!=null) {
					Element phoneNameRaw = doc.createElement("phoneNameRaw");
					phoneNameRaw.appendChild(doc.createTextNode(phone.getPhoneNameRaw()));
					rootElement.appendChild(phoneNameRaw);	
				}
				
				//disclaimerMini element
				if(phone.getDisclaimerMini()!=null) {
					Element disclaimerMini = doc.createElement("disclaimerMini");
					disclaimerMini.appendChild(doc.createTextNode(phone.getDisclaimerMini()));
					rootElement.appendChild(disclaimerMini);	
				}
				
				//disclaimerFull element
				if(phone.getDisclaimerFull()!=null) {
					Element disclaimerFull = doc.createElement("disclaimerFull");
					disclaimerFull.appendChild(doc.createTextNode(phone.getDisclaimerFull()));
					rootElement.appendChild(disclaimerFull);					
				}
				
				//footerLegal element
				if(phone.getFooterLegal()!=null) {
					Element footerLegal = doc.createElement("footerLegal");
					footerLegal.appendChild(doc.createTextNode(phone.getFooterLegal()));
					rootElement.appendChild(footerLegal);					
				}
				
				//settare campi nella variation	phone.getVariationById(vId)
				
				
				if (phone.getFeatureBar()!=null) {
					Element featureBar = doc.createElement("featureBar");
					featureBar.appendChild(doc.createTextNode(phone.getFeatureBar()));
					rootElement.appendChild(featureBar);					
				}
				
				if (phone.getFeatureSlider()!=null) {
					Element featureSlider = doc.createElement("featureSlider");
					featureSlider.appendChild(doc.createTextNode(phone.getFeatureSlider()));
					rootElement.appendChild(featureSlider);					
				}
				
				if (phone.getFeatureFlip()!=null) {
					Element featureFlip = doc.createElement("featureFlip");
					featureFlip.appendChild(doc.createTextNode(phone.getFeatureFlip()));
					rootElement.appendChild(featureFlip);					
				}
				
				if (phone.getFeatureTouchScreen()!=null) {
					Element featureTouchScreen = doc.createElement("featureTouchScreen");
					featureTouchScreen.appendChild(doc.createTextNode(phone.getFeatureTouchScreen()));
					rootElement.appendChild(featureTouchScreen);					
				}
				
				if (phone.getFeatureQWERTY()!=null) {
					Element featureQwerty = doc.createElement("featureQwerty");
					featureQwerty.appendChild(doc.createTextNode(phone.getFeatureQWERTY()));
					rootElement.appendChild(featureQwerty);							
				}
				
				if (phone.getFeatureCamera()!=null) {
					Element featureCamera = doc.createElement("featureCamera");
					featureCamera.appendChild(doc.createTextNode(phone.getFeatureCamera()));
					rootElement.appendChild(featureCamera);							
				}
				
				if (phone.getFeatureFronFacingCamera()!=null) {
					Element featureFrontFacingCamera = doc.createElement("featureFrontFacingCamera");
					featureFrontFacingCamera.appendChild(doc.createTextNode(phone.getFeatureFronFacingCamera()));
					rootElement.appendChild(featureFrontFacingCamera);							
				}
				
				if (phone.getFeatureGPS()!=null) {
					Element featureGPS = doc.createElement("featureGPS");
					featureGPS.appendChild(doc.createTextNode(phone.getFeatureGPS()));
					rootElement.appendChild(featureGPS);							
				}
				
				if (phone.getFeature4GWimax()!=null) {
					Element feature4GWiMax = doc.createElement("feature4GWiMax");
					feature4GWiMax.appendChild(doc.createTextNode(phone.getFeature4GWimax()));
					rootElement.appendChild(feature4GWiMax);							
				}
				
				if (phone.getFeatureWiFi()!=null) {
					Element featureWifi = doc.createElement("featureWifi");
					featureWifi.appendChild(doc.createTextNode(phone.getFeatureWiFi()));
					rootElement.appendChild(featureWifi);							
				}
				
				if (phone.getFeatureWiFi()!=null) {
					Element featureWifi = doc.createElement("featureWifi");
					featureWifi.appendChild(doc.createTextNode(phone.getFeatureWiFi()));
					rootElement.appendChild(featureWifi);							
				}
				

				if (phone.getFeatureBluetooth()!=null) {
					Element featureBluetooth = doc.createElement("featureBluetooth");
					featureBluetooth.appendChild(doc.createTextNode(phone.getFeatureBluetooth()));
					rootElement.appendChild(featureBluetooth);							
				}
				
				if (phone.getFeatureSpeakerphone()!=null) {
					Element featureSpeakerPhone = doc.createElement("featureSpeakerPhone");
					featureSpeakerPhone.appendChild(doc.createTextNode(phone.getFeatureSpeakerphone()));
					rootElement.appendChild(featureSpeakerPhone);							
				}
				
				if (phone.getFeature3G()!=null) {
					Element feature3G = doc.createElement("feature3G");
					feature3G.appendChild(doc.createTextNode(phone.getFeature3G()));
					rootElement.appendChild(feature3G);							
				}
				
				if (phone.getFeature4GLTE()!=null) {
					Element feature4GLTE = doc.createElement("feature4GLTE");
					feature4GLTE.appendChild(doc.createTextNode(phone.getFeature4GLTE()));
					rootElement.appendChild(feature4GLTE);							
				}
				
				if (phone.getFeature4G()!=null) {
					Element feature4G = doc.createElement("feature4G");
					feature4G.appendChild(doc.createTextNode(phone.getFeature4G()));
					rootElement.appendChild(feature4G);							
				}
				
				if (phone.getFeatureText()!=null) {
					Element featureText = doc.createElement("featureText");
					featureText.appendChild(doc.createTextNode(phone.getFeatureText()));
					rootElement.appendChild(featureText);							
				}
				
				if (phone.getFeatureVideo()!=null) {
					Element featureVideo = doc.createElement("featureVideo");
					featureVideo.appendChild(doc.createTextNode(phone.getFeatureVideo()));
					rootElement.appendChild(featureVideo);							
				}
				

				if (phone.getFeatureHotspot()!=null) {
					Element featureHotSpot = doc.createElement("featureHotSpot");
					featureHotSpot.appendChild(doc.createTextNode(phone.getFeatureHotspot()));
					rootElement.appendChild(featureHotSpot);							
				}
				
				if (phone.getFeatureEmail()!=null) {
					Element featureEmail = doc.createElement("featureEmail");
					featureEmail.appendChild(doc.createTextNode(phone.getFeatureEmail()));
					rootElement.appendChild(featureEmail);							
				}
				
				if (phone.getFeatureHTMLBrowser()!=null) {
					Element featureHtmlBrowser = doc.createElement("featureHtmlBrowser");
					featureHtmlBrowser.appendChild(doc.createTextNode(phone.getFeatureHTMLBrowser()));
					rootElement.appendChild(featureHtmlBrowser);							
				}
				
				if (phone.getFeatureULECertified()!=null) {
					Element featureUleCertified = doc.createElement("featureUleCertified");
					featureUleCertified.appendChild(doc.createTextNode(phone.getFeatureULECertified()));
					rootElement.appendChild(featureUleCertified);							
				}
				

				if (phone.getFeatureMusic()!=null) {
					Element featureMusic = doc.createElement("featureMusic");
					featureMusic.appendChild(doc.createTextNode(phone.getFeatureMusic()));
					rootElement.appendChild(featureMusic);							
				}
				
				if (phone.getFeatureVisualVoicemail()!=null) {
					Element featureVisualVoiceMail = doc.createElement("featureVisualVoiceMail");
					featureVisualVoiceMail.appendChild(doc.createTextNode(phone.getFeatureVisualVoicemail()));
					rootElement.appendChild(featureVisualVoiceMail);							
				}
				
				if (phone.getIsPremium()!=null) {
					Element isPremium = doc.createElement("isPremium");
					isPremium.appendChild(doc.createTextNode(phone.getIsPremium()));
					rootElement.appendChild(isPremium);							
				}
				
				if (phone.getSynonyms()!=null) {
					Element synonym = doc.createElement("synonym");
					synonym.appendChild(doc.createTextNode(phone.getSynonyms()));
					rootElement.appendChild(synonym);							
				}
				if (phone.getAdwordsLid() !=null) {
					Element addwordsLID = doc.createElement("addwordsLID");
					addwordsLID.appendChild(doc.createTextNode(phone.getAdwordsLid()));
					rootElement.appendChild(addwordsLID);							
				}
					
				if (phone.getAdwordsLid() !=null) {
					Element addwordsLID = doc.createElement("addwordsLID");
					addwordsLID.appendChild(doc.createTextNode(phone.getAdwordsLid()));
					rootElement.appendChild(addwordsLID);							
				}
				
				if (phone.getAdwordsDsSKwgid() !=null) {
					Element addwordsDS_S_KWGID = doc.createElement("addwordsDS_S_KWGID");
					addwordsDS_S_KWGID.appendChild(doc.createTextNode(phone.getAdwordsDsSKwgid()));
					rootElement.appendChild(addwordsDS_S_KWGID);							
				}
				
				if (phone.getBingLid() !=null) {
					Element bing_lid = doc.createElement("bing_lid");
					bing_lid.appendChild(doc.createTextNode(phone.getBingLid()));
					rootElement.appendChild(bing_lid);							
				}
				

				if (phone.getBingDsSKwgid() !=null) {
					Element bing_kwgid = doc.createElement("bing_kwgid");
					bing_kwgid.appendChild(doc.createTextNode(phone.getBingDsSKwgid()));
					rootElement.appendChild(bing_kwgid);							
				}
				
				if (!phone.getAccessories().isEmpty()) {
					List<Accessory> accessories = new ArrayList<Accessory>();			
					Element associatedAccessoryId = doc.createElement("associatedAccessoryId");
					String accessoriesValues = "";
					String separator = ";";
					
					for (int i = 0; i< accessories.size(); i++) {
						accessoriesValues=accessoriesValues + accessories.get(i) + separator;						
					}				
					associatedAccessoryId.appendChild(doc.createTextNode(accessoriesValues));
					rootElement.appendChild(associatedAccessoryId);							
				}

				if (phone.getCompareItemOS() !=null) {
					Element compareItemOS = doc.createElement("compareItemOS");
					compareItemOS.appendChild(doc.createTextNode(phone.getCompareItemOS()));
					rootElement.appendChild(compareItemOS);							
				}
				
				if (phone.getCompareItemDisplay() !=null) {
					Element compareItemDisplay = doc.createElement("compareItemDisplay");
					compareItemDisplay.appendChild(doc.createTextNode(phone.getCompareItemDisplay()));
					rootElement.appendChild(compareItemDisplay);							
				}
				if (phone.getCompareItemCamera() !=null) {
					Element compareItemCamera = doc.createElement("compareItemCamera");
					compareItemCamera.appendChild(doc.createTextNode(phone.getCompareItemCamera()));
					rootElement.appendChild(compareItemCamera);							
				}
				if (phone.getCompareItemWifi() !=null) {
					Element compareItemWifi = doc.createElement("compareItemWifi");
					compareItemWifi.appendChild(doc.createTextNode(phone.getCompareItemWifi()));
					rootElement.appendChild(compareItemWifi);							
				}
				if (phone.getCompareItem4G() !=null) {
					Element compareItem4G = doc.createElement("compareItem4G");
					compareItem4G.appendChild(doc.createTextNode(phone.getCompareItem4G()));
					rootElement.appendChild(compareItem4G);							
				}
				if (phone.getCompareItemHotspot() !=null) {
					Element compareItemHotspot = doc.createElement("compareItemHotspot");
					compareItemHotspot.appendChild(doc.createTextNode(phone.getCompareItemHotspot()));
					rootElement.appendChild(compareItemHotspot);							
				}
				
				if (phone.getCompareItemQWERTYKeyboard() !=null) {
					Element compareItemQWERTYKeyboard = doc.createElement("compareItemQWERTYKeyboard");
					compareItemQWERTYKeyboard.appendChild(doc.createTextNode(phone.getCompareItemQWERTYKeyboard()));
					rootElement.appendChild(compareItemQWERTYKeyboard);							
				}
				
				if (phone.getCompareItemWebBrowser() !=null) {
					Element compareItemWebBrowser = doc.createElement("compareItemQWERTYKeyboard");
					compareItemWebBrowser.appendChild(doc.createTextNode(phone.getCompareItemWebBrowser()));
					rootElement.appendChild(compareItemWebBrowser);							
				}
				if (phone.getCompareItemFlashPlayer() !=null) {
					Element compareItemFlashPlayer = doc.createElement("compareItemFlashPlayer");
					compareItemFlashPlayer.appendChild(doc.createTextNode(phone.getCompareItemFlashPlayer()));
					rootElement.appendChild(compareItemFlashPlayer);							
				}
				if (phone.getCompareItemVideo() !=null) {
					Element compareItemVideo = doc.createElement("compareItemVideo");
					compareItemVideo.appendChild(doc.createTextNode(phone.getCompareItemVideo()));
					rootElement.appendChild(compareItemVideo);							
				}
				if (phone.getCompareItemMusicPlayer() !=null) {
					Element compareItemMusicPlayer = doc.createElement("compareItemMusicPlayer");
					compareItemMusicPlayer.appendChild(doc.createTextNode(phone.getCompareItemMusicPlayer()));
					rootElement.appendChild(compareItemMusicPlayer);							
				}
				if (phone.getCompareItemGPS() !=null) {
					Element compareItemGPS = doc.createElement("compareItemGPS");
					compareItemGPS.appendChild(doc.createTextNode(phone.getCompareItemGPS()));
					rootElement.appendChild(compareItemGPS);							
				}
				if (phone.getCompareItemSpeakerphone() !=null) {
					Element compareItemSpeakerphone = doc.createElement("compareItemSpeakerphone");
					compareItemSpeakerphone.appendChild(doc.createTextNode(phone.getCompareItemSpeakerphone()));
					rootElement.appendChild(compareItemSpeakerphone);							
				}
				if (phone.getCompareItemMemory() !=null) {
					Element compareItemMemory = doc.createElement("compareItemMemory");
					compareItemMemory.appendChild(doc.createTextNode(phone.getCompareItemMemory()));
					rootElement.appendChild(compareItemMemory);							
				}
				if (phone.getCompareItemProcessor() !=null) {
					Element compareItemProcessor = doc.createElement("compareItemProcessor");
					compareItemProcessor.appendChild(doc.createTextNode(phone.getCompareItemProcessor()));
					rootElement.appendChild(compareItemProcessor);							
				}
				if (phone.getCompareItemCalendar() !=null) {
					Element compareItemCalendar = doc.createElement("compareItemCalendar");
					compareItemCalendar.appendChild(doc.createTextNode(phone.getCompareItemCalendar()));
					rootElement.appendChild(compareItemCalendar);							
				}
				if (phone.getCompareItemVisualVoicemail() !=null) {
					Element compareItemVisualVoicemail = doc.createElement("compareItemVisualVoicemail");
					compareItemVisualVoicemail.appendChild(doc.createTextNode(phone.getCompareItemVisualVoicemail()));
					rootElement.appendChild(compareItemVisualVoicemail);							
				}
				if (phone.getCompareItem3G() !=null) {
					Element compareItem3G = doc.createElement("compareItem3G");
					compareItem3G.appendChild(doc.createTextNode(phone.getCompareItem3G()));
					rootElement.appendChild(compareItem3G);							
				}
				if (phone.getCompareItemBluetooth() !=null) {
					Element compareItemBluetooth = doc.createElement("compareItemBluetooth");
					compareItemBluetooth.appendChild(doc.createTextNode(phone.getCompareItemBluetooth()));
					rootElement.appendChild(compareItemBluetooth);							
				}
				if (phone.getFeatureList().isEmpty()) {
					Element extraFeatureCount = doc.createElement("extraFeatureCount");
					extraFeatureCount.appendChild(doc.createTextNode("0"));
					rootElement.appendChild(extraFeatureCount);							
				} else //{
					//create text files with features		
				
				//}
				if (phone.getSpecialFeatureList().isEmpty()) {
					Element specialFeatures = doc.createElement("specialFeatures");
					specialFeatures.appendChild(doc.createTextNode("0"));
					rootElement.appendChild(specialFeatures);							
				}// else {
					//create text files with special features		
				
				//}
				
				Element techSpecsGroupCount = doc.createElement("techSpecsGroupCount");
				techSpecsGroupCount.appendChild(doc.createTextNode(phone.getGroupList().size()+""));
				rootElement.appendChild(techSpecsGroupCount);
				
				//if (!phone.getGroupList().isEmpty()){
					//create xml files for tech specs
				//}			
				// write the content into xml file
				
				
				
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				DOMSource source = new DOMSource(doc);
				File output = new File("C:\\Users\\eeleren\\"+phone.getPhoneName()+extension);
				StreamResult result = new StreamResult(output);
				// DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
				//doc = docBuilder.parse(output);
				//doc = removeNullValues(doc);
		 
				// Output to console for testing
				// StreamResult result = new StreamResult(System.out);
		 
				transformer.transform(source, result);
		 
				System.out.println("File saved!");
				removeNullValues();
		 
			  } catch (ParserConfigurationException pce) {
				pce.printStackTrace();
			  } catch (TransformerException tfe) {
				tfe.printStackTrace();
			  }
		  
		  
			
	}
			public Element setMandatoryFields(String phoneValue, String assetField, Document doc, String defaultValue) {					
				Element element = doc.createElement(assetField);
				element.appendChild(doc.createTextNode((phoneValue!=null) ? phoneValue : defaultValue));
				return element;
		
	}
			
			public void removeNullValues() throws ParserConfigurationException, SAXException, IOException, TransformerException {
				
				DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
				        .newInstance();
				    DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
				    Document document = docBuilder.parse(new File("C:\\Users\\eeleren\\"+phoneAsset.getPhoneName()+PhoneConstants.FILE_EXTENSION));			
				
				
				 NodeList nodeList = document.getElementsByTagName("*");
				 String content = "";
				 String nodeName = "";
				 List<String> mandatoryList = phoneAsset.getMandatoryFields();
				 String s = "deployed";
				System.out.println("test: "+mandatoryList.contains(s));
				    for (int i = 0; i < nodeList.getLength(); i++) {
				        Node node = nodeList.item(i);
				        if (node.getNodeType() == Node.ELEMENT_NODE) {
				            // do something with the current element
				        	nodeName = node.getNodeName();
				        	content = node.getTextContent();
				        	if (content.equals("null")) {
				        		System.out.println("nullcontent for: "+nodeName);
				        		if (!mandatoryList.contains(nodeName)) {
				        			System.out.println("removing: "+nodeName);
				        			node.getParentNode().removeChild(node);
				        		} else {
				        			node.setTextContent("aContent");
				        		}
				        	}
				        	System.out.println("testo :"+ " nodeName: "+node.getNodeName());
				            System.out.println(node.getNodeName());
				        }
				    }
				    TransformerFactory transformerFactory = TransformerFactory.newInstance();
				    Transformer transformer;
					
						transformer = transformerFactory.newTransformer();
				
				    DOMSource source = new DOMSource(document);
				    StreamResult result = new StreamResult(new File("C:\\Users\\eeleren\\"+phoneAsset.getPhoneName()+PhoneConstants.FILE_EXTENSION));
				  
						transformer.transform(source, result);
					
					System.out.println("File re-saved!");
				}
				
			

}
