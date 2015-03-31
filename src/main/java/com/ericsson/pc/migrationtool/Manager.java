package com.ericsson.pc.migrationtool;

import java.util.List;

import com.ericsson.pc.migrationtool.interfaces.Parser;

import org.apache.log4j.Logger;



import com.ericsson.pc.migrationtool.bean.Phone;
import com.ericsson.pc.migrationtool.builder.Builder;



public class Manager {
	
	final static Logger logger = Logger.getLogger(Manager.class);

	public static void main(String[] args) {
		String parserClassName = null;
		String fileToBeParsed = null;
		Builder builder = new Builder();
		
		if (args.length > 1) {
			parserClassName = args[0];
			fileToBeParsed = args[1];
			
		} else if (args.length > 0) {
			parserClassName = args[0];
		}
		else {
			logger.error("Error: No arguments, please provide parser class name and/or directory to be parsed");
			return;
		}
		
		try {
			Class parser = Class.forName("com.ericsson.pc.migrationtool." + parserClassName);
			
			//((Parser)parser.newInstance()).execute();
			List<Phone> phones = ((Parser)parser.newInstance()).execute(fileToBeParsed);
			builder.createPhoneAssets(phones);
			
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			logger.error(e, e);
		}
	}
}
