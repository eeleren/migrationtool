package com.ericsson.pc.migrationtool;

import java.io.FileNotFoundException;
import java.util.List;

import org.apache.log4j.Logger;

import com.ericsson.pc.migrationtool.bean.Phone;
import com.ericsson.pc.migrationtool.builder.Builder;
import com.ericsson.pc.migrationtool.builder.PhoneBuilder;
import com.ericsson.pc.migrationtool.interfaces.Parser;



public class Manager {
	
	final static Logger logger = Logger.getLogger(Manager.class);

	public static void main(String[] args) {
		String assetName = null;
		String fileToBeParsed = null;
				
		if (args.length == 2) {
			assetName = args[0];
			fileToBeParsed = args[1];
			
		} else if (args.length == 1) {
			assetName = args[0];
		}
		else {
			logger.error("Error: No arguments, please provide parser class name and/or directory to be parsed");
			return;
		}
		
		try {
			Class parser = Class.forName("com.ericsson.pc.migrationtool." + assetName + "Parser");
			Class builder = Class.forName("com.ericsson.pc.migrationtool.builder." + assetName + "Builder");
			
			((Builder) builder.newInstance()).createAssets(((Parser)parser.newInstance()).execute(fileToBeParsed));
		
			
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			logger.error(e, e);
		} catch (FileNotFoundException fne) {
			logger.error("Error: the argument provided does not match any available phone file. Please insert a valid name and try again.");
			return;
		}
	}
}
