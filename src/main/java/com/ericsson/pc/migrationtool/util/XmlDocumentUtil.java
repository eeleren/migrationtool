package com.ericsson.pc.migrationtool.util;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.ericsson.pc.migrationtool.builder.manual.ManualConstants;
import com.ericsson.pc.migrationtool.builder.phone.PhoneConstants;

public class XmlDocumentUtil {
	
	//private Document doc;
	
	
	public void createXmlDocument() throws ParserConfigurationException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		docFactory.setNamespaceAware(true);			
		docBuilder.newDocument();
		
	}
	
	public static Document createDocument() throws ParserConfigurationException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		docFactory.setNamespaceAware(true);			
		return docBuilder.newDocument();
		
	}
	

	/*public void addNodeWithNSAttribute(String nodeName, String attrName, String namespace) {
		Element node = doc.createElement(nodeName);
		Attr attr = doc.createAttribute(attrName);
		attr.setValue(namespace);
		node.setAttributeNodeNS(attr);
		doc.appendChild(node);
	}
*/

	/**
	 * Add the first node with a namespace attribute as below:
	 * <asset xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
	 * @nodeName: name of the element
	 * @
	 * */
	public static Document addNodeWithNSAttribute(Document document, String nodeName, String attrName, String namespace) {
		Element node = document.createElement(nodeName);
		Attr attr = document.createAttribute(attrName);
		attr.setValue(namespace);
		node.setAttributeNodeNS(attr);
		document.appendChild(node);
		return document;
	}
	
	public static Document appendChildWithAttribute(Document doc, String nodeName, String parentNodeName, String attrName, String attrValue) {
		Element node = doc.createElement(nodeName);		
		node.setAttribute(attrName, attrValue);
		doc.getElementsByTagName(parentNodeName).item(0).appendChild(node);		
		return doc;
	}
	
	
	/**
	 * Appends the node identified by @nodeName to the node identified by parentName
	 * 
	public static Document appendChild(Document document, String nodeName, String parentName) {
		
	}*/


	/**
	 * Add a node with a namespace attribute as below:
	 * <asset xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
	 * */
/*	public static Document appendChild(Document document, Node father, Node child) {
		Element node = document.createElement(nodeName);
		Attr attr = document.createAttribute(attrName);
		attr.setValue(namespace);
		node.setAttributeNodeNS(attr);
		document.appendChild(node);
		return document;
	}*/
}
