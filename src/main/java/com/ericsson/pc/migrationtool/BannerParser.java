package com.ericsson.pc.migrationtool;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.ericsson.pc.migrationtool.bean.Banner;
import com.ericsson.pc.migrationtool.bean.Model;
import com.ericsson.pc.migrationtool.interfaces.Parser;
import com.ericsson.pc.migrationtool.util.LogUtil;
import com.ericsson.pc.migrationtool.util.PathUtil;
import com.ericsson.pc.migrationtool.util.XPathUtil;

public class BannerParser implements Parser {
	
	final static Logger logger = Logger.getLogger(BannerParser.class);

	@Override
	public List<Model> execute(String file) throws FileNotFoundException {
		// TODO Auto-generated method stub
		return execute();
	}

	@Override
	public List<Model> execute() {
		// TODO Auto-generated method stub
		return parse();
	}

	@Override
	public Parser getNewInstance() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<Model> parse() {
		List banners = new ArrayList<Banner>();
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = null;
        XPathFactory xpathFactory = XPathFactory.newInstance();
		XPath xpath = xpathFactory.newXPath();
		
		try {
			builder = factory.newDocumentBuilder();
	        Document doc = builder.parse(PathUtil.getPromosPath());
	        logger.debug("Parsing banners objects from file: "+ PathUtil.getPromosPath());
	        int numOfPromos = Integer.parseInt(XPathUtil.getValueAsString(doc, xpath, "count(/payload/promos/promo)"));	      
	        int counter = 1;	   
	        while (counter <= numOfPromos) {
	        	Banner banner = new Banner();
	        	banner.setId(XPathUtil.getValueAsString(doc, xpath, "/payload/promos/promo["+counter+"]/@id"));
	        	banner.setIsActive(XPathUtil.getValueAsString(doc, xpath, "/payload/promos/promo["+counter+"]/@active"));
	        	banner.setImgFolder(XPathUtil.getValueAsString(doc, xpath, "/payload/promos/promo["+counter+"]/@imgFolder"));
	        	banner.setUrl(XPathUtil.getValueAsString(doc, xpath, "/payload/promos/promo["+counter+"]/banner/@url"));
	        	banner.setImageName(XPathUtil.getValueAsString(doc, xpath, "/payload/promos/promo["+counter+"]/banner/@imageName"));
	        	banner.setHome(XPathUtil.getValueAsString(doc, xpath, "/payload/promos/promo["+counter+"]/banner/@home"));
	        	banner.setHasGrid(XPathUtil.getValueAsString(doc, xpath, "/payload/promos/promo["+counter+"]/banner/@grid"));
	        	banner.setHasDetail(XPathUtil.getValueAsString(doc, xpath, "/payload/promos/promo["+counter+"]/banner/@detail"));
	        	banner.setPlans(XPathUtil.getValueAsString(doc, xpath, "/payload/promos/promo["+counter+"]/banner/@plans"));
	        	banner.setAccessories(XPathUtil.getValueAsString(doc, xpath, "/payload/promos/promo["+counter+"]/banner/@accessories"));
	        	banner.setCart(XPathUtil.getValueAsString(doc, xpath, "/payload/promos/promo["+counter+"]/banner/@cart"));
	        	banner.setDeals(XPathUtil.getValueAsString(doc, xpath, "/payload/promos/promo["+counter+"]/banner/@deals"));
	        	
	           	counter++;
	        	banners.add(banner);
	        }
	        LogUtil.logBanners(banners);

		} catch (ParserConfigurationException | SAXException | IOException e) {
			logger.error(e, e);
        }		
		return banners;
	}

}
