package com.ericsson.pc.migrationtool.builder;

import java.io.File;
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

import com.ericsson.pc.migrationtool.bean.Accessory;
import com.ericsson.pc.migrationtool.bean.Model;
import com.ericsson.pc.migrationtool.bean.msdp.AccessoryAssetStructure;
import com.ericsson.pc.migrationtool.bean.msdp.AccessoryField;
import com.ericsson.pc.migrationtool.builder.accessory.AccessoryConstants;
import com.ericsson.pc.migrationtool.builder.phone.PhoneConstants;
import com.ericsson.pc.migrationtool.util.PathUtil;


public class AccessoryBuilder extends Builder {
	
	private String relatedPhoneName;
	private String relatedPhoneBrand;
	
	public String getRelatedPhoneName() {
		return relatedPhoneName;
	}

	public void setRelatedPhoneName(String relatedPhoneName) {
		this.relatedPhoneName = relatedPhoneName;
	}

	public String getRelatedPhoneBrand() {
		return relatedPhoneBrand;
	}

	public void setRelatedPhoneBrand(String relatedPhoneBrand) {
		this.relatedPhoneBrand = relatedPhoneBrand;
	}


	
	public void createAccessories(List<Accessory> accessoriesList) {
		
		for (Accessory a: accessoriesList) {
			setAssetName(getRelatedPhoneName(),a.getId());
			AccessoryAssetStructure asset = buildAssetStructure(a);
			createXml(asset);
		}		
	}
	
	/**
	 * Translates an accessory data coming from boost into msdp phone assets structure
	 * */
	public static AccessoryAssetStructure buildAssetStructure(Accessory accessory) {		
					
			AccessoryAssetStructure asset = new AccessoryAssetStructure();
			asset.init();			
			
			//setting asset fields with boost values
			
			logger.debug("Creating accessory asset");
			asset.setExternalId(accessory.getId());	
			asset.setAccessoryFieldValueByFieldName(AccessoryConstants.ASSET_DESCRIPTION_FIELD, accessory.getDescription());	
			asset.setAccessoryFieldValueByFieldName(AccessoryConstants.LABEL_FIELD, accessory.getLabel());		
			//asset.setCategory?			
					
		
		return asset;
	}
	
	@SuppressWarnings("unchecked")
	public void createXml(AccessoryAssetStructure assetAccessory) {
		
		
		setAssetOutputDir(getAssetName());			
		PathUtil.createAssetOutputDir(getAssetOutputDir());
		
			
		String filename = "";
		List<AccessoryField> fieldList = assetAccessory.getAccessoryFieldList();
	
		
		try {
				if((String) assetAccessory.getExternalId() != null) {
					filename = (String) assetAccessory.getExternalId();
					logger.info("Creating asset for "+ filename);
					
					
				} else {
					logger.error("Accessory id not received");
				}
			  
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
				
				
				Element rootElement = doc.createElement(AccessoryConstants.ACCESSORY_VALUE);
				String externalId = (String) assetAccessory.getAccessoryFieldByName(AccessoryConstants.EXTERNAL_ID).getValue();
				rootElement.setAttribute(AccessoryConstants.EXTERNAL_ID,externalId);
				asset.appendChild(rootElement);
				
				//iteration over AccessoryField elements
				for (AccessoryField f : fieldList) {
					if (f.isMandatory()) {
						Element e = doc.createElement(f.getName());
						if (f.getValue()!= null) {
							e.appendChild(doc.createTextNode((String) f.getValue()));
						} else {
							e.appendChild(doc.createTextNode(f.getDefaultValue()));
						}						
						rootElement.appendChild(e);
						
						//analysis of variants elements: pictures, extrafeatures, specialfeatures, techspec
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
				
							 
			  } catch (ParserConfigurationException pce) {
				
				logger.error("ParserConfigurationException :"+pce.getMessage());
			  } catch (TransformerException tfe) {
				
				logger.error("TransoformerException: "+tfe.getMessage());
				
			  }	catch (Exception e) {
				  logger.error(e.getMessage());
				  e.printStackTrace();
			  }
	}

	@Override
	public Builder getNewInstance() {		
		return new AccessoryBuilder();
	}

	@Override
	public void createAssets(List<Model> models) {
		// TODO Auto-generated method stub
		
	}
}

