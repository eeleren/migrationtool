package com.ericsson.pc.migrationtool.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ericsson.pc.migrationtool.PhoneParser;

public class FileUtil {
	
	final static Logger logger = Logger.getLogger(FileUtil.class);
	
	public static void main(String[] args) {
	
	String image = "C:\\_images\\phones\\apple-iphone4s\\iphone.png";
	System.out.println(FileUtil.getImageName(image));
	
	}
	
	//private static String IMAGE_FILE_PATH = ApplicationPropertiesReader.getInstance().getProperty("builder.asset.phone.imagePath");
	private static String IMAGE_FILE_PATH="C:\\_images\\phones\\";
	public static List<String> getImagesFilesPath() {
		List<String> imagesFilePath = new ArrayList<String>();
		
		for (final File fileEntry : new File(IMAGE_FILE_PATH).listFiles()) {
	        if (fileEntry.isDirectory()) {
	        	imagesFilePath.add(IMAGE_FILE_PATH + File.separator + fileEntry.getName());
	        } 
	    }
		
		return imagesFilePath;
	}
	
	public static boolean isImageFolder(String phoneName, String brandName, String dirName) {
		phoneName = phoneName.trim().toLowerCase().replaceAll("\\s+","");
		brandName = brandName.trim().toLowerCase().replaceAll("\\s+","");
		return (dirName.contains(phoneName)&&(dirName.contains(brandName)));
	}
	
	public static String getImageFolder(String phoneName, String brandName) {
		List<String> imagesFilePath = new ArrayList<String>();
		String result = "";
		try {
		imagesFilePath = FileUtil.getImagesFilesPath();
		for (String s: imagesFilePath) {
			if (isImageFolder(phoneName, brandName, s)) {
				result = s;
				
			} 
		}
		if (result.equals("")) {
			throw new FileNotFoundException();
		}
			
		}catch (FileNotFoundException e){
			logger.error(e);			
			return result;
		}
		return result;
	}
	
	public static String getImageName(String uri) {
		return uri.substring(uri.lastIndexOf("\\")+1, uri.length());
	}
	

}
