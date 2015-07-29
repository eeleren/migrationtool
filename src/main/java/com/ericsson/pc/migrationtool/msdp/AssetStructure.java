package com.ericsson.pc.migrationtool.msdp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class AssetStructure {
	
	final static Logger logger = Logger.getLogger(AssetStructure.class);	
	
	
	private List<AssetField> fieldList = new ArrayList<AssetField>();
	
	
	public List<AssetField> getFieldList() {
		return fieldList;
	}

	public void setFieldList(List<AssetField> fieldList) {
		this.fieldList = fieldList;
	}

	//asset mandatory fields list
	private Map<String, String> mandatoryFields = new HashMap<String, String>();
	
	public Map<String, String> getMandatoryFields() {
		return mandatoryFields;
	}

	public void setMandatoryFields(Map<String, String> mandatoryFields) {
		this.mandatoryFields = mandatoryFields;
	}

	private List<String> fieldOrderList = new LinkedList<String>();	

	
	public AssetField geteFieldByName(String fieldName) {
		List<AssetField> fieldList = this.getFieldList();
		
		for (AssetField p : fieldList) {
			if (fieldName.equalsIgnoreCase(p.getName())) {
				return p;
			}
		}		
		return null;
	}
	
	public AssetField checkMandatory(AssetField f) {		
		if (getMandatoryFields().containsKey(f.getName())) {
			f.setMandatory(true);
			f.setDefaultValue(getMandatoryFields().get(f.getName()));
			//logger.debug("value set to: " + mandatoryFields.get(f.getName()));
		} else {
			f.setMandatory(false);
		}
		return f;
	}
	
	public void setFieldValueByFieldName(String fieldName, Object fieldValue) {
		List<AssetField> assetFieldList = this.getFieldList();
		AssetField f;
		for (int i = 0; i < assetFieldList.size(); i++) {
			if (fieldName.equalsIgnoreCase(assetFieldList.get(i).getName())) {
				f = assetFieldList.get(i);
				f.setValue(fieldValue);
				assetFieldList.set(i, f);
			}
				
		}
		
	}
	
	public List<String> getFieldOrderList() {
		return fieldOrderList;
	}

	public void setFieldOrderList(List<String> fieldOrderList) {
		this.fieldOrderList = fieldOrderList;
	}
	
	public void init() {
			List<String> assetFieldsList =  this.getFieldOrderList();
		
		AssetField field; 
		  for (int i = 0; i < assetFieldsList.size(); i++) {
			  field = new AssetField();
			  field.setName(assetFieldsList.get(i));
			  field.setOrder(i); 
			  field = this.checkMandatory(field);
			  fieldList.add(field);
	        }		 
		  //sort the list of fields according to the order value
		  Collections.sort(fieldList);
		  this.setFieldList(fieldList);		
	}

}
