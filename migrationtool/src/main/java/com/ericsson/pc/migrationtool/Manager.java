package com.ericsson.pc.migrationtool;

import org.apache.log4j.Logger;

import com.ericsson.pc.migrationtool.interfaces.Parser;


public class Manager {
	
	final static Logger logger = Logger.getLogger(Manager.class);

	public static void main(String[] args) {
		String parserClassName = null;
		
		if (args.length > 0) {
			parserClassName = args[0];
		}
		else {
			logger.error("Error: No arguments, please provide parser class name.");
			return;
		}
		
		try {
			Class parser = Class.forName("com.ericsson.pc.migrationtool." + parserClassName);
			
			((Parser)parser.newInstance()).execute();
			
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			logger.error(e, e);
		}
	}
}
