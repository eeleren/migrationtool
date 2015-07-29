package com.ericsson.pc.migrationtool.builder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.ericsson.pc.migrationtool.bean.Model;
import com.ericsson.pc.migrationtool.bean.PhoneManual;
import com.ericsson.pc.migrationtool.builder.manual.ManualConstants;
import com.ericsson.pc.migrationtool.builder.phone.ImageBuilder;
import com.ericsson.pc.migrationtool.builder.phone.PhoneConstants;
import com.ericsson.pc.migrationtool.msdp.AssetField;
import com.ericsson.pc.migrationtool.msdp.ImageItem;
import com.ericsson.pc.migrationtool.msdp.ManualAssetStructure;
import com.ericsson.pc.migrationtool.util.ApplicationPropertiesReader;
import com.ericsson.pc.migrationtool.util.PathUtil;
import com.ericsson.pc.migrationtool.util.XmlDocumentUtil;



public class PhoneManualBuilder extends Builder {
	
	final static String brandId = ApplicationPropertiesReader.getInstance().getProperty("builder.asset.phone.brandId");	
	final static String USER_GUIDE_DIR = ApplicationPropertiesReader.getInstance().getProperty("builder.asset.manual.userGuidesPath");
	final static String USER_GUIDE_IMAGES = ApplicationPropertiesReader.getInstance().getProperty("builder.asset.manual.userGuidesImagePath");
	
	private String phoneManualId = "";
	protected final String extension = ManualConstants.FILE_EXTENSION;
	

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
			String assetName = pm.getFullName().replaceAll(" ", "_");
			logger.debug("asset name:"+ assetName);
			builder.setAssetName(assetName);
			builder.setPhoneManualId(pm.getId());
			builder.createPhoneManualAsset(pm);
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
				Document doc = XmlDocumentUtil.createDocument();		 
				doc = XmlDocumentUtil.addNodeWithNSAttribute(doc, PhoneConstants.ASSET_FIELD, PhoneConstants.PREFIX, PhoneConstants.NAMESPACE);	
				
				String externalId = brandId + "_" + getPhoneManualId();
				doc = XmlDocumentUtil.appendChildWithAttribute(doc, ManualConstants.PHONE_MANUAL, PhoneConstants.ASSET_FIELD, PhoneConstants.EXTERNAL_ID, externalId);				
						
				//iteration over AssetField elements
				for (AssetField f : fieldList) {
					if (f.isMandatory()) {
						Element e = doc.createElement(f.getName());
						if (f.getValue()!= null) {
							e.appendChild(doc.createTextNode((String) f.getValue()));
						} else {
							e.appendChild(doc.createTextNode(f.getDefaultValue()));
						}						
						doc.getElementsByTagName(ManualConstants.PHONE_MANUAL).item(0).appendChild(e);
						
						//analysis of variants elements: pictures, pdf files
					}else if (f.getName().equals(ManualConstants.USER_GUIDE_IMAGE)) {
						logger.debug("creating xml structure for user guide images");				
						ImageBuilder imageBuilder = new ImageBuilder();
						ImageItem userGuideImage = imageBuilder.getImageByName((String)f.getValue(), USER_GUIDE_IMAGES);
						Element e = doc.createElement(f.getName());// elemento
						// pictures
						if (userGuideImage != null) {
							
							Element variant = doc.createElement("variant");
							Element item = doc.createElement("item");
							item.setAttribute("uri", userGuideImage.getName());
							variant.appendChild(item);
							e.appendChild(variant);
							doc.getElementsByTagName(ManualConstants.PHONE_MANUAL).item(0).appendChild(e);
							imageBuilder.moveImage(userGuideImage, outputDir	+ getAssetOutputDir());

						} else {
							logger.error("images not found for asset:" + filename);
						}
					} else if (f.getName().equals(ManualConstants.MANUAL_PDF) && (f.getValue() != null)) {
						List<String> pdfFiles = getAssetPdfFiles(USER_GUIDE_DIR, (String)f.getValue());						
						movePdfFiles(USER_GUIDE_DIR, pdfFiles);
						
						if (!pdfFiles.isEmpty()) {
							Element e = doc.createElement(f.getName());
							for (String s: pdfFiles) {
								Element variant = doc.createElement("variant");
								Element item = doc.createElement("item");
								item.setAttribute("uri", s);
								variant.appendChild(item);
								e.appendChild(variant);
								
							}
							doc.getElementsByTagName(ManualConstants.PHONE_MANUAL).item(0).appendChild(e);
						}
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
	
	
	/**
	 * Translates a phone manual coming from boost into msdp phone manual assets structure
	 * */
	public ManualAssetStructure buildAssetStructure(PhoneManual manual) {
			ManualAssetStructure asset = new ManualAssetStructure();
			asset.init();						
			//setting asset fields with boost values		
			logger.debug("Manual full name: "+manual.getFullName());
			asset.setFieldValueByFieldName(ManualConstants.FILENAME, manual.getFullName());		
			asset.setFieldValueByFieldName(ManualConstants.USER_GUIDE_IMAGE, manual.getUserGuideImage());
			asset.setFieldValueByFieldName(ManualConstants.MANUAL_PDF, manual.getPdfFileName());
			//asset.setFieldValueByFieldName(ManualConstants.ID_FIELD, manual.getId());
		return (ManualAssetStructure) asset;
	}
	
	
	public List<String> getAssetPdfFiles(String userGuideDir, String manualTitle) {
		
		List<String> pdfFiles = new ArrayList<String>(); 
		
		String manualFullName = getAssetName();
		
		String[] manualDetails = manualFullName.split("_");
		String model = manualDetails[1].toLowerCase();
	
		File userGuides = new File(userGuideDir);
		File[] manuals = userGuides.listFiles();
		for (File f: manuals) {
			
			if (f.getName().contains(manualTitle)) {						
					pdfFiles.add(f.getName());
					logger.debug(f.getName()+" file added to pdf list");
				
			} else if ((f.getName().contains(model))) {			
					pdfFiles.add(f.getName());
					logger.debug(f.getName()+" file added to pdf list");
				
			}
		}
		return pdfFiles;
	}
	
	
	public void movePdfFiles(String userGuideDir, List<String> pdfManuals) {	
		
		
		File userGuides = new File(userGuideDir);
		File destDir = new File(outputDir+getAssetOutputDir());	
		File[] manuals = userGuides.listFiles();
		
		for (String m: pdfManuals) {
			for (File f: manuals) {
			if(f.getName().equalsIgnoreCase(m)) {
				try {
					FileUtils.copyFileToDirectory(f, destDir, false);
					logger.debug("Pdf file moved");
				} catch (IOException e) {
					logger.error(e,e);
				}
			}
			}
		}
		
	
	}
}

