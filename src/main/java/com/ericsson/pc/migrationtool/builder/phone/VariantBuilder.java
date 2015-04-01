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
	 * */
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

}

	/**
	 * Creates the text file representing the special features of the phone, starting from the Features list and the asset name
	 * */
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
	
	/**
	 * Creates the xml file representing the tech spec of the phone, starting from the Features list and the asset name
	 * */
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
	
}