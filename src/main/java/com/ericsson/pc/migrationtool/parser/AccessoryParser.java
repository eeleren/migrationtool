package com.ericsson.pc.migrationtool.parser;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ericsson.pc.migrationtool.bean.Model;
import com.ericsson.pc.migrationtool.bean.Phone;
import com.ericsson.pc.migrationtool.interfaces.Parser;
import com.ericsson.pc.migrationtool.util.LogUtil;
import com.ericsson.pc.migrationtool.util.PathUtil;

public class AccessoryParser implements Parser {
	
	final static Logger logger = Logger.getLogger(AccessoryParser.class);

	@Override
	public List<Model> execute(String file) throws FileNotFoundException {
		return null;
	}

	@Override
	public List<Model> execute() {
		logger.info("PARSER EXECUTION STARTED...");
		List<String> phonePathList = PathUtil.getPhonesFilesPath();	
		return parse(phonePathList);
	}
	

	@Override
	public Parser getNewInstance() {
		return new AccessoryParser();
	}	
	
	public List<Model> parse(List<String> phonePathList) {
		List phoneList = new ArrayList<Phone>();
		
		for (String phoneFilePath : phonePathList) {
		//	phoneList.add(getPhone(phoneFilePath));
			if (logger.isDebugEnabled()) {
				logger.debug("Adding file to parsing list: " + phoneFilePath);
			}
		}
		
		logger.info("Found " + phoneList.size() + " phones in folder " + PathUtil.getPhonesPath());
		
		//getCatalogue(phoneList);
	//	getAccessories(phoneList);
	//	cleanPhoneList(phoneList);
		
		LogUtil.logPhones(phoneList);
		
		logger.info("PARSER EXECUTION COMPLETED!");
		
		return phoneList;
	}

}
