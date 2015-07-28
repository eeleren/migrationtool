package com.ericsson.pc.migrationtool;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.ericsson.pc.migrationtool.bean.Model;
import com.ericsson.pc.migrationtool.bean.PhoneManual;
import com.ericsson.pc.migrationtool.interfaces.Parser;
import com.ericsson.pc.migrationtool.util.ApplicationPropertiesReader;
import com.ericsson.pc.migrationtool.util.LogUtil;
import com.ericsson.pc.migrationtool.util.PathUtil;
import com.ericsson.pc.migrationtool.util.XPathUtil;

public class PhoneManualParser implements Parser {
	
	final static Logger logger = Logger.getLogger(PhoneManualParser.class);
	final static String brandId = ApplicationPropertiesReader.getInstance().getProperty("builder.asset.phone.brandId");	

	@Override
	public List<Model> execute(String file) throws FileNotFoundException {
		// TODO Auto-generated method stub
		return execute();
	}

	@Override
	public List<Model> execute() {
		logger.info("PHONE MANUAL PARSER EXECUTION STARTED...");
		return parse();
	}
	
	public List<Model> parse() {
		List manuals = new ArrayList<PhoneManual>();
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = null;
        XPathFactory xpathFactory = XPathFactory.newInstance();
		XPath xpath = xpathFactory.newXPath();
		
		try {
			//NOT SMART SOLUTION
			builder = factory.newDocumentBuilder();
	        Document doc = builder.parse(PathUtil.getPhoneManualFilePath());
	        NodeList modelNodes = XPathUtil.getValueAsNodes(doc, xpath, "/boostmobile/phones/model");
	        PhoneManual m;
	        String id;
    		for (int i=0; i < modelNodes.getLength(); i++) {
    			m = new PhoneManual();
    			Node model = modelNodes.item(i);
    			NodeList modelChildNodes = model.getChildNodes();
    			for (int j = 0; j < modelChildNodes.getLength(); j++) {    				
    				Node n= modelChildNodes.item(j);   				
    				if (n.getNodeName().equalsIgnoreCase("brightpointcode")) {
    					m.setId(n.getTextContent());
    				}
    				if (n.getNodeName().equals("fullname")) {
    					logger.debug("Fullname: "+n.getTextContent());
    					m.setFullName(n.getTextContent());
    				} if(n.getNodeName().equalsIgnoreCase("userguide")) {
    					NodeList userGuideNodeChildren = n.getChildNodes();
    					for (int k = 0; k < userGuideNodeChildren.getLength(); k++) {
    						if(userGuideNodeChildren.item(k).getNodeName().equalsIgnoreCase("filename")) {
    							m.setPdfFileName(userGuideNodeChildren.item(k).getTextContent());
    						}
    					}
    				} if (n.getNodeName().equalsIgnoreCase("images")) {
    					Element nImages = (Element)n;
    					String userGuideTitle = nImages.getElementsByTagName("image").item(0).getTextContent();
    					String userGuideTitleImage = nImages.getElementsByTagName("image").item(1).getTextContent();    				
    					logger.debug(userGuideTitle);
    					logger.debug(userGuideTitleImage);    			
    					m.setUserGuideTitleImage(userGuideTitle);
    					m.setUserGuideImage(userGuideTitleImage);
    					}
    				}
    				
    			manuals.add(m);	
    			}	        
		} catch (ParserConfigurationException | SAXException | IOException e) {
			logger.error(e, e);
        }
		LogUtil.logManuals(manuals);
		return manuals;
	}
	
	/*public List<Model> parse() {
		List manuals = new ArrayList<PhoneManual>();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = null;
        XPathFactory xpathFactory = XPathFactory.newInstance();
		XPath xpath = xpathFactory.newXPath();
		
		try {
			builder = factory.newDocumentBuilder();
	        Document doc = builder.parse(PathUtil.getPhoneManualFilePath());
	        NodeList modelNodes = XPathUtil.getValueAsNodes(doc, xpath, "/boostmobile/phones/model");
	        PhoneManual m;
	        String id;
    		for (int i=0; i < modelNodes.getLength(); i++) {
    			m = new PhoneManual();
    			Node model = modelNodes.item(i);
    			//id = modelNodes.item(i).getFirstChild().getNodeValue();
    			//manualsId.add(id);
    			NodeList modelChildNodes = model.getChildNodes();
    			for (int j = 0; j < modelChildNodes.getLength(); j++) {    				
    				Node n= modelChildNodes.item(j);
    				logger.debug("Node name: "+n.getNodeName());
    				logger.debug("Node value: "+n.getTextContent());
    				if (n.getNodeName().equals("fullname")) {
    					m.setFullName(n.getTextContent());
    					logger.debug("Fullname: "+ m.getFullName());
    				}
    				if (n.getNodeName())
    				
    			}
    			manuals.add(m);
    			
    			
    		}
    	
	        
		} catch (ParserConfigurationException | SAXException | IOException e) {
			logger.error(e, e);
        }
		return manuals;
	}
	
	public List<Model> getPhoneManuals(List<String> manualsId) {
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = null;
        XPathFactory xpathFactory = XPathFactory.newInstance();
		XPath xpath = xpathFactory.newXPath();
		
		try {
			builder = factory.newDocumentBuilder();
	        Document doc = builder.parse(PathUtil.getPhoneManualFilePath());
	      //  NodeList modelNodes = XPathUtil.getValueAsNodes(doc, xpath, "/boostmobile/phones/model/");
	        PhoneManual m;
	      
	        for (String id : manualsId) {
	        	m = new PhoneManual();
	        	//SMART SOLUTION
        		Node phoneNode = XPathUtil.getValueAsNodes(doc, xpath, "/boostmobile/phones/model/brightpointcode=[@id='" + id + "']").item(0);
    			Node model = modelNodes.item(i);
    			
    			
    		
    			m.setFullName(modelNodes.item(i).getFirstChild().getNodeValue());
    			logger.debug("Fullname: "+ m.getFullName());
    			manuals.add(m);
    		}
    	
	        
		} catch (ParserConfigurationException | SAXException | IOException e) {
			logger.error(e, e);
        }
		
	}*/
	@Override
	public Parser getNewInstance() {
		// TODO Auto-generated method stub
		return null;
	}

}
