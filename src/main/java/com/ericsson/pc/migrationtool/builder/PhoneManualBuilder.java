package com.ericsson.pc.migrationtool.builder;

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

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.ericsson.pc.migrationtool.bean.Feature;
import com.ericsson.pc.migrationtool.bean.Group;
import com.ericsson.pc.migrationtool.bean.Item;
import com.ericsson.pc.migrationtool.bean.Model;
import com.ericsson.pc.migrationtool.bean.PhoneManual;
import com.ericsson.pc.migrationtool.bean.SpecialFeature;
import com.ericsson.pc.migrationtool.bean.VariantPhone;
import com.ericsson.pc.migrationtool.builder.manual.ManualConstants;
import com.ericsson.pc.migrationtool.builder.phone.ImageBuilder;
import com.ericsson.pc.migrationtool.builder.phone.PhoneConstants;
import com.ericsson.pc.migrationtool.builder.phone.VariantBuilder;
import com.ericsson.pc.migrationtool.msdp.AssetField;
import com.ericsson.pc.migrationtool.msdp.AssetStructure;
import com.ericsson.pc.migrationtool.msdp.ImageItem;
import com.ericsson.pc.migrationtool.msdp.ManualAssetStructure;
import com.ericsson.pc.migrationtool.msdp.PhoneAssetStructure;
import com.ericsson.pc.migrationtool.util.ApplicationPropertiesReader;
import com.ericsson.pc.migrationtool.util.PathUtil;



public class PhoneManualBuilder extends Builder {
	
	final static String brandId = ApplicationPropertiesReader.getInstance().getProperty("builder.asset.phone.brandId");	
	private String phoneManualId = "";

	public String getPhoneManualId() {
		return phoneManualId;
	}

	public void setPhoneManualId(String phoneManualId) {
		this.phoneManualId = phoneManualId;
	}

	@Override
	public Builder getNewInstance() {
		return new PhoneManualBuilder();
	}

	@Override
	public void createAssets(List<Model> models) {
		PhoneManualBuilder builder = new PhoneManualBuilder();	
		
		List<PhoneManual> manuals = new ArrayList<PhoneManual>();
		
		for (Model m: models) {
			if (m instanceof PhoneManual) 
				manuals.add((PhoneManual) m);
		} 	
		
		builder.cleanOutputDir();
		logger.info("BUILDING EXECUTION STARTED...");
		
		for (PhoneManual pm: manuals) {
			builder.setAssetName(pm.getFullName());
			builder.setPhoneManualId(pm.getId());
			createPhoneManualAsset(pm);
		}
		
		logger.info("BUILDER EXECUTION COMPLETED!");
		
	}
	
	public void createPhoneManualAsset(PhoneManual phoneManual) {
		ManualAssetStructure asset = buildAssetStructure(phoneManual);
		createXml(asset);
	}
	
	@SuppressWarnings("unchecked")
	public void createXml(ManualAssetStructure phoneManual) {
		
		setAssetOutputDir(getAssetName());
		PathUtil.createAssetOutputDir(getAssetOutputDir());		
		
		String filename = getAssetName();
				
		List<AssetField> fieldList = phoneManual.getFieldList();
	
		
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
				
				
				Element rootElement = doc.createElement(ManualConstants.PHONE_MANUAL);
				String externalId = brandId + "_" + getPhoneManualId();
				rootElement.setAttribute(PhoneConstants.EXTERNAL_ID, externalId);
				asset.appendChild(rootElement);
				
				//iteration over PhoneField elements
				for (AssetField f : fieldList) {
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
			  }
	}
	
	
	/**
	 * Translates a phone data coming from boost into msdp phone assets structure
	 * */
	public ManualAssetStructure buildAssetStructure(PhoneManual manual) {
			AssetStructure asset = new ManualAssetStructure();
			asset.init();						
			//setting asset fields with boost values		
			asset.setFieldValueByFieldName(ManualConstants.FILENAME, manual.getFullName());			
			//asset.setFieldValueByFieldName(ManualConstants.ID_FIELD, manual.getId());
		return (ManualAssetStructure) asset;
	}
}
