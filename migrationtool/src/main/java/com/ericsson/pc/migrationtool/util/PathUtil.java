package com.ericsson.pc.migrationtool.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.ericsson.pc.migrationtool.bean.Phone;

public class PathUtil {
	
	private static String CATALOGUE_FILE_PATH = ApplicationPropertiesReader.getInstance().getProperty("parser.file.catalogue");
	private static String PHONES_DIR_PATH = ApplicationPropertiesReader.getInstance().getProperty("parser.dir.phones");
	private static String ACCESSORY_FILE_PATH = ApplicationPropertiesReader.getInstance().getProperty("parser.file.accessory");
	
	public static String getCataloguePath() {
		return CATALOGUE_FILE_PATH;
	}
	
	public static String getPhonesPath() {
		return PHONES_DIR_PATH;
	}
	
	public static List<String> getPhonesFilesPath() {
		List<String> phoneFilesPath = new ArrayList<String>();
		
		for (final File fileEntry : new File(PHONES_DIR_PATH).listFiles()) {
	        if (!fileEntry.isDirectory()) {
	            phoneFilesPath.add(PHONES_DIR_PATH + File.separator + fileEntry.getName());
	        } 
	    }
		
		return phoneFilesPath;
	}
	
	public static String getAccessoryPath() {
		return ACCESSORY_FILE_PATH;
	}
	
	/**
    public static List<String> getPhonePaths(List<Phone> phoneListFromCatalogue) {
    	File file = new File(CATALOGUE_FILE_PATH);
        String xmlFolderPath = file.getParent();
    	
    	List<String> phonePaths = new ArrayList<String>();
    	
    	for (Phone phone : phoneListFromCatalogue) {
    		String pathTmp = phone.getPath();
    		String finalPath = xmlFolderPath + File.separator + pathTmp + ".xml";
    		
    		phonePaths.add(finalPath);
    	}
    	
    	return phonePaths;
    }
    */
    
    public static String getPhonePath(Phone phone) {
    	File file = new File(CATALOGUE_FILE_PATH);
        String xmlFolderPath = file.getParent();
    	
		String pathTmp = phone.getPath();
		String finalPath = xmlFolderPath + File.separator + pathTmp + ".xml";
		
    	return finalPath;
    }
}
