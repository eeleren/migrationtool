package com.ericsson.pc.migrationtool.util;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class XPathUtil {
	
    public static String getValueAsString(Document doc, XPath xpath, String value) {
        String id = null;
        try {
            XPathExpression expr =
                xpath.compile(value);
            id = (String) expr.evaluate(doc, XPathConstants.STRING);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }

        return id;
    }
    
    public static NodeList getValueAsNodes(Document doc, XPath xpath, String value) {
        NodeList nodeList = null;
        try {
            XPathExpression expr =
                xpath.compile(value);
            nodeList = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }

        return nodeList;
    }

}
