package com.ericsson.pc.migrationtool.builder.phone;


import java.io.File;
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

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.ericsson.pc.migrationtool.bean.Feature;
import com.ericsson.pc.migrationtool.bean.Group;
import com.ericsson.pc.migrationtool.bean.Group.Spec;
import com.ericsson.pc.migrationtool.bean.SpecialFeature;
import com.ericsson.pc.migrationtool.util.ApplicationPropertiesReader;



public class VariantBuilder {
	
	final static Logger logger = Logger.getLogger(VariantBuilder.class);
	final static String separator = File.separator;
	final String outputDir = ApplicationPropertiesReader.getInstance().getProperty("builder.asset.outputdir");
	final String extension = PhoneConstants.FILE_EXTENSION;	
	
	private String assetName= "";
	
	private String featuresName = "";
	private String featureFile = "";
	
	private String specFeaturesName = "";
	private String specFeatureFile = "";
	

	private String techSpecName = "";
	private String techSpecFile = "";
	

	private String compareFeaturesName = "";
	private String compareFeaturesFile = "";


	
	public String getCompareFeaturesName() {
		return compareFeaturesName;
	}

	public void setCompareFeaturesName(String compareFeaturesName) {
		this.compareFeaturesName = compareFeaturesName;
	}

	public String getCompareFeaturesFile() {
		return compareFeaturesFile;
	}

	public void setCompareFeaturesFile(String compareFeaturesFile) {
		this.compareFeaturesFile = compareFeaturesFile;
	}
	
	public String getTechSpecName() {
		return techSpecName;
	}

	public void setTechSpecName(String techSpecName) {
		this.techSpecName = techSpecName;
	}

	public String getTechSpecFile() {
		return techSpecFile;
	}

	public void setTechSpecFile(String techSpecFile) {
		this.techSpecFile = techSpecFile;
	}
	
	public String getSpecFeaturesName() {
		return specFeaturesName;
	}

	public void setSpecFeaturesName(String specFeaturesName) {
		this.specFeaturesName = specFeaturesName;
	}

	public String getSpecFeatureFile() {
		return specFeatureFile;
	}

	public void setSpecFeatureFile(String specFeatureFile) {
		this.specFeatureFile = specFeatureFile;
	}
	
	public String getFeatureFile() {
		return featureFile;
	}

	public void setFeatureFile(String featureFile) {
		this.featureFile = featureFile;
	}

	public String getAssetName() {
		return assetName;
	}

	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}

	public String getFeaturesName() {
		return featuresName;
	}

	public void setFeaturesName(String featuresName) {
		this.featuresName = featuresName;
	}
	
	/**
	 * Creates the text file representing the features of the phone, starting from the Features list and the asset name
	 * the file will have this structure:
	 * <?xml version="1.0" encoding="UTF-8"?>
		<references> 
			<reference>lg-mach-android-4point0</reference>
		</references>
	 */
	public void createPhoneFeatures(List<Feature> featuresList) {
		//File featureFile= new File(outputDir+assetName+separator+assetName+featureExtension);
		File featureFile = new File(getFeatureFile());		
				
		try {
			 
			logger.debug("Writing features on a separate .xml file...");
			
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();	
			Document doc = docBuilder.newDocument();
			Element root = doc.createElement("references");
			doc.appendChild(root);
 
			for (Feature f: featuresList) {
			
				if (f.getExtraFeatureIconName()!= null) {
					Element featureId = doc.createElement("reference");
					featureId.appendChild(doc.createTextNode(f.getExtraFeatureIconName()));
					root.appendChild(featureId);
				} else if ((f.getFeatureId()!= null) && (f.getFeatured()!=null) && (f.getFeatured().equalsIgnoreCase("true"))) {
					Element featureId = doc.createElement("reference");
					featureId.appendChild(doc.createTextNode(f.getFeatureId()));
					root.appendChild(featureId);
				}
			}
			 
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				DOMSource source = new DOMSource(doc);
				
				StreamResult result = new StreamResult(featureFile);
							transformer.transform(source, result);
		 
				logger.info("Features file created and saved in: "+featureFile);			
 
		
		} catch (TransformerException | ParserConfigurationException e) {
			logger.error(e.getMessage());
		}

}
	
	
	/**
	 * Creates the text file representing the features of the phone, starting from the Features list and the asset name
	
	public void createPhoneFeatures(List<Feature> featuresList) {
		//File featureFile= new File(outputDir+assetName+separator+assetName+featureExtension);
		File featureFile = new File(getFeatureFile());
		logger.debug(featureFile.getAbsolutePath());
				
		try {
			 
			logger.debug("Writing features on a separate .xml file...");
			
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();	
			Document doc = docBuilder.newDocument();
			Element root = doc.createElement("features");
			doc.appendChild(root);
 
			for (int i =0 ; i < featuresList.size(); i++) {
			
				if (featuresList.get(i).getFeatureId()!= null) {
					Element featureId = doc.createElement("featureId_"+i);
					featureId.appendChild(doc.createTextNode(featuresList.get(i).getFeatureId()));
					root.appendChild(featureId);
				}
				
				if (featuresList.get(i).getExtraFeatureTitle() != null) {
				Element extraFeatureTitle = doc.createElement("extraFeatureTitle_"+i);
				extraFeatureTitle.appendChild(doc.createTextNode(featuresList.get(i).getExtraFeatureTitle()));
				root.appendChild(extraFeatureTitle);
				}
				
				if (featuresList.get(i).getExtraFeatureIconName() != null) {
				Element extraFeatureIconName = doc.createElement("extraFeatureIconName_"+i);
				extraFeatureIconName.appendChild(doc.createTextNode(featuresList.get(i).getExtraFeatureIconName()));
				root.appendChild(extraFeatureIconName);
				}
				
				if (featuresList.get(i).getExtraFeatureDescription() != null) {
				Element extraFeatureDescription = doc.createElement("extraFeatureDescription_"+i);
				extraFeatureDescription.appendChild(doc.createTextNode(featuresList.get(i).getExtraFeatureDescription()));
				root.appendChild(extraFeatureDescription);								
				}
			}
			 
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				DOMSource source = new DOMSource(doc);
				
				StreamResult result = new StreamResult(featureFile);
							transformer.transform(source, result);
		 
				logger.info("Features file: "+featureFile+" created");			
 
		
		} catch (TransformerException | ParserConfigurationException e) {
			logger.error(e.getMessage());
		}

} * */
	/**
	 * Creates the text file representing the special features of the phone, starting from the Features list and the asset name
	 *
	 * <?xml version="1.0" encoding="UTF-8"?>
-<references> <reference>three-way-calling</reference> <reference>audiojack3point5mm</reference> <reference>boost-zone</reference>
	 * </references>
	 * */
	
	public void createPhoneSpecialFeatures(List<SpecialFeature> specialFeaturesList) {
		//File featureFile= new File(outputDir+assetName+separator+assetName+featureExtension);
		File specFeatureFile = new File(getSpecFeatureFile());
				
		
		try {
			 
			logger.debug("Writing special features on a separate .xml file...");
			
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();	
			Document doc = docBuilder.newDocument();
			Element root = doc.createElement("references");
			doc.appendChild(root);
 
			for (SpecialFeature sf: specialFeaturesList) {
				if (sf.getSpecialFeatureName()!=null) {
				    Element specFeatureName = doc.createElement("reference");
					specFeatureName.appendChild(doc.createTextNode(sf.getSpecialFeatureName()));
					root.appendChild(specFeatureName);
				} else if (sf.getSpecialFeatureId()!=null) {
					Element specFeatureName = doc.createElement("reference");
					specFeatureName.appendChild(doc.createTextNode(sf.getSpecialFeatureId()));
					root.appendChild(specFeatureName);
				}				
				}
			 
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				DOMSource source = new DOMSource(doc);
				
				StreamResult result = new StreamResult(specFeatureFile);
							transformer.transform(source, result);
		 
				logger.info("Special Features file created and saved in: "+specFeatureFile);			
 
		
		} catch (TransformerException | ParserConfigurationException e) {
			logger.error(e.getMessage());
		}

	}
	/**
	 * Creates the text file representing the special features of the phone, starting from the Features list and the asset name
	 * */
	/*
	public void createPhoneSpecialFeatures(List<SpecialFeature> specialFeaturesList) {
		//File featureFile= new File(outputDir+assetName+separator+assetName+featureExtension);
		File specFeatureFile = new File(getSpecFeatureFile());
		logger.debug(specFeatureFile.getAbsolutePath());		
		
		try {
			 
			logger.debug("Writing special features on a separate .xml file...");
			
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();	
			Document doc = docBuilder.newDocument();
			Element root = doc.createElement("specialFeatures");
			doc.appendChild(root);
 
			for (int i =0; i < specialFeaturesList.size(); i++) {
				Element specFeatureId = doc.createElement("specialFeatureId_"+i);
				specFeatureId.appendChild(doc.createTextNode(specialFeaturesList.get(i).getSpecialFeatureId()));
				root.appendChild(specFeatureId);
				
				Element specFeatureName = doc.createElement("specialFeatureName_"+i);
				specFeatureName.appendChild(doc.createTextNode(specialFeaturesList.get(i).getSpecialFeatureName()));
				root.appendChild(specFeatureName);
				
				}
			 
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				DOMSource source = new DOMSource(doc);
				
				StreamResult result = new StreamResult(specFeatureFile);
							transformer.transform(source, result);
		 
				logger.info("Features file: "+specFeatureFile+" created");			
 
		
		} catch (TransformerException | ParserConfigurationException e) {
			logger.error(e.getMessage());
		}

	}
	public void createPhoneTechSpec(List<Group> groupList) {
		//File featureFile= new File(outputDir+assetName+separator+assetName+featureExtension);
		File techSpecFile = new File(getTechSpecFile());
		logger.debug(techSpecFile.getAbsolutePath());		
		
		try {
			 
			logger.debug("Writing tech spec on a separate .xml file...");
			
			List<Spec> specList = new ArrayList<Spec>();
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();	
			Document doc = docBuilder.newDocument();
			Element root = doc.createElement("techSpecs");
			doc.appendChild(root);
 
			for (int i = 0; i < groupList.size(); i++) {
				
				specList = groupList.get(i).getListSpec();
				
				if(groupList.get(i).getId()!=null) {
					Element groupId = doc.createElement("techSpecGroupId_"+i);				
					groupId.appendChild(doc.createTextNode(groupList.get(i).getId()));
					root.appendChild(groupId);
				}
				
				if(groupList.get(i).getOrder()!=null){
					Element groupIdOrder = doc.createElement("techSpecGroupId_"+i+"_order");
					groupIdOrder.appendChild(doc.createTextNode(groupList.get(i).getOrder()));
					root.appendChild(groupIdOrder);
					
				}
				
				if(groupList.get(i).getThumb()!=null) {
					Element groupIdThumb = doc.createElement("techSpecGroupId_"+i+"_thumb");
					groupIdThumb.appendChild(doc.createTextNode(groupList.get(i).getThumb()));
					root.appendChild(groupIdThumb);
				}			
				
				Element groupIdSpecCount = doc.createElement("techSpecGroupId_"+i+"_specCount");
				groupIdSpecCount.appendChild(doc.createTextNode(groupList.get(i).getListSpec().size()+""));
				root.appendChild(groupIdSpecCount);
				
				for (int j = 0; j < specList.size(); j ++) {
					
					if(specList.get(i).getType()!=null) {
					Element groupIdSpecType = doc.createElement("techSpecGroupId_"+i+"_specType");
					groupIdSpecType.appendChild(doc.createTextNode(specList.get(j).getType()));
					root.appendChild(groupIdSpecType);
					}
					
					if(specList.get(i).getValue()!=null) {
					Element groupIdSpecValue = doc.createElement("techSpecGroupId_"+i+"_specValue");
					groupIdSpecValue.appendChild(doc.createTextNode(specList.get(j).getValue()));
					root.appendChild(groupIdSpecValue);
					}
					
					if(specList.get(i).getOrder()!=null) {
					Element groupIdSpecOrder = doc.createElement("techSpecGroupId_"+i+"_specOrder");
					groupIdSpecOrder.appendChild(doc.createTextNode(specList.get(j).getOrder()));
					root.appendChild(groupIdSpecOrder);
				}
				}
				
			}
			 
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				DOMSource source = new DOMSource(doc);
				
				StreamResult result = new StreamResult(techSpecFile);
							transformer.transform(source, result);
		 
				logger.info("Tech Spec file: "+this.techSpecFile+" created");			
 
		
		} catch (TransformerException | ParserConfigurationException e) {
			logger.error(e.getMessage());
		}

	}
	*/
	/**
	 * Creates the xml file representing the tech spec of the phone, starting from the Features list and the asset name
	 * 
	 * the new structure is similar to the original file
	 * */
	public void createPhoneTechSpec(List<Group> groupList) {
		//File featureFile= new File(outputDir+assetName+separator+assetName+featureExtension);
		File techSpecFile = new File(getTechSpecFile());
				
		try {
			 
			logger.debug("Writing tech spec on a separate .xml file...");
			
			List<Spec> specList = new ArrayList<Spec>();
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();	
			Document doc = docBuilder.newDocument();
			Element root = doc.createElement("tech-specs");
			doc.appendChild(root);
			
			Element os = doc.createElement("os");
			os.appendChild(doc.createTextNode(groupList.get(0).getValue()));
			root.appendChild(os);
			
			Element processor = doc.createElement("processor");
			processor.appendChild(doc.createTextNode(groupList.get(2).getSpecBySpecType("processor")));
			root.appendChild(processor);
			
			Element memory = doc.createElement("memory");
			memory.appendChild(doc.createTextNode(groupList.get(2).getSpecBySpecType("memory")));
			root.appendChild(memory);
		
 
			for (int i = 1; i < groupList.size(); i++) {
				
				specList = groupList.get(i).getListSpec();
				
				if(groupList.get(i).getId()!=null) {
					Element group = doc.createElement("group");	
					group.setAttribute("id", groupList.get(i).getId());
					root.appendChild(group);		
					
					Element order = doc.createElement("order");
					order.appendChild(doc.createTextNode(groupList.get(i).getOrder()));
					group.appendChild(order);
					
				/*	if ((groupList.get(i).getThumb()!=null)&&(!groupList.get(i).getThumb().equals(""))) {
						Element thumb = doc.createElement("thumb");
						thumb.appendChild(doc.createTextNode(groupList.get(i).getThumb()));
						group.appendChild(thumb);
					}*/
					if (specList!=null) {
						Element specs = doc.createElement("specs");
						group.appendChild(specs);
						
						for (int j = 0; j < specList.size(); j ++) {						
							if((specList.get(j).getType()!=null) && (specList.get(j).getType()!=null) &&(!specList.get(j).getType().equalsIgnoreCase("processor"))&&(!specList.get(j).getType().equalsIgnoreCase("memory"))) {
								Element spec = doc.createElement("spec");
								spec.setAttribute("order", specList.get(j).getOrder());
								spec.setAttribute("type", specList.get(j).getType());
								spec.appendChild(doc.createTextNode(specList.get(j).getValue()));
								specs.appendChild(spec);
								
							}					
						}
						
						
					}
					
				}

				
			}
			 
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				DOMSource source = new DOMSource(doc);
				
				StreamResult result = new StreamResult(techSpecFile);
							transformer.transform(source, result);
		 
				logger.info("Tech Spec file: "+this.techSpecFile+" created");			
 
		
		} catch (TransformerException | ParserConfigurationException e) {
			logger.error(e.getMessage());
		}

	}
	
	/**
	 * Creates the xml file representing the compareFeatures of the phone, starting from the Features list and the asset name
	 * the file will have this structure:
	 * <?xml version="1.0" encoding="UTF-8"?>
		<references> 
			<reference>lg-mach-android-4point0</reference>
		</references>
	 */
	public void createPhoneCompareFeatures(List<String> compareList) {
		
		File compareFeatureFile = new File(getCompareFeaturesFile());		
				
		try {
			 
			logger.debug("Writing compare features on a separate .xml file...");
			
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();	
			Document doc = docBuilder.newDocument();
			Element root = doc.createElement("references");
			doc.appendChild(root);
 
			for (String s: compareList) {
			
				if ((s!= null) && (!("").equals(s))) {
					Element compare = doc.createElement("reference");
					compare.appendChild(doc.createTextNode(s));
					root.appendChild(compare);
				} 
			}
			 
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				DOMSource source = new DOMSource(doc);
				
				StreamResult result = new StreamResult(compareFeatureFile);
							transformer.transform(source, result);
		 
				logger.info("Compare Features file created and saved in: "+ compareFeatureFile);			
 
		
		} catch (TransformerException | ParserConfigurationException e) {
			logger.error(e.getMessage());
		}

}
	
}