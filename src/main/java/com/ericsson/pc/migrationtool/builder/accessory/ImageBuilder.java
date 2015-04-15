package com.ericsson.pc.migrationtool.builder.accessory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

import com.ericsson.pc.migrationtool.bean.Item;
import com.ericsson.pc.migrationtool.bean.msdp.ImageItem;
import com.ericsson.pc.migrationtool.util.ApplicationPropertiesReader;


public class ImageBuilder {
	
	public ImageItem imageItem = new ImageItem();

	final static Logger logger = Logger.getLogger(ImageBuilder.class);
	
	private static String IMAGE_FILE_PATH = ApplicationPropertiesReader.getInstance().getProperty("builder.asset.phone.imagePath");
	
	public static String getIMAGE_FILE_PATH() {
		return IMAGE_FILE_PATH;
	}

	public static void setIMAGE_FILE_PATH(String iMAGE_FILE_PATH) {
		IMAGE_FILE_PATH = iMAGE_FILE_PATH;
	}

	public static List<String> getImagesFilesPath() {
		List<String> imagesFilePath = new ArrayList<String>();
		
		for (final File fileEntry : new File(IMAGE_FILE_PATH).listFiles()) {
	        if (fileEntry.isDirectory()) {
	        	imagesFilePath.add(IMAGE_FILE_PATH + fileEntry.getName());
	        } 
	    }
		
		return imagesFilePath;
	}
	
	public static boolean isImageFolder(String phoneName, String brandName, String dirName) {
		dirName = dirName.substring(dirName.lastIndexOf(File.separator), dirName.length()).replaceAll("-", "");		
		phoneName = phoneName.trim().toLowerCase().replaceAll("\\s+","").replaceAll("iii", "3");
		//phoneName = adaptName(phoneName);
		brandName = brandName.trim().toLowerCase().replaceAll("\\s+","");
		return (dirName.contains(phoneName)&&(dirName.contains(brandName)));
	}
	
	public static String getImageFolder(String phoneName, String brandName)  {
		List<String> imagesFilePath = new ArrayList<String>();
		
		String result = "";
		try {
		imagesFilePath = ImageBuilder.getImagesFilesPath();
		for (String s: imagesFilePath) {
			if (isImageFolder(phoneName, brandName, s)) {
				result = s;
				break;
				
			} 
		}
		if (result.equals("")) {
			throw new FileNotFoundException();
		}
			
		}catch (FileNotFoundException e) {
			logger.error(e,e);		
			return null;
			
		}
		return result;
	}
		


	public static String getImageName(String uri) {
		return uri.substring(uri.lastIndexOf("\\")+1, uri.length());
	}
	
	/**
	 * returns true if the item representing an image is contained in the imageDir directory or in any subdirectories
	 * */
	public  boolean containsImage(Item item, String imageDir) {
		
		logger.debug("Scanning image folder: "+ imageDir+ "for item: "+item.getView());
		boolean result = false;
	
		File root = new File(imageDir);
		File[] imagesFile = root.listFiles();
		
		for (File f : imagesFile) {
			if(f.isDirectory() && !(f.getName().equalsIgnoreCase("thumb"))) {
				logger.debug("Directory found: "+f.getName());
				return containsImage(item, f.getPath());
			} else if(FilenameUtils.removeExtension(f.getName()).equalsIgnoreCase(item.getView())) {
				this.getImageItem().setName(f.getName());
				this.getImageItem().setPath(f.getPath());
				logger.debug("Found image with name: "+ getImageItem().getName()+" in the directory: "+f.getPath());
				result = true;
				break;
			}				
		}
		
		return result;
	}
	
	
	public List<ImageItem> getContainedImages(List<Item> items, String imageDir) {
		List<ImageItem> imageItems = new ArrayList<ImageItem>();
		
		for(Item i: items) {
				if(this.containsImage(i, imageDir)) {
				ImageItem image = new ImageItem();
				image.setName(getImageItem().getName());
				image.setPath(getImageItem().getPath());
				imageItems.add(image);
			}
		}
	return imageItems;
		
	}
	
	public void moveImages(List<ImageItem> images, String assetDirectory) {
		File dest = new File(assetDirectory);
		File source;
		for (ImageItem i: images) {
			source = new File(i.getPath());
			try {
				FileUtils.copyFileToDirectory(source, dest);
			} catch (IOException e) {
				logger.error("IO exception while moving images "+ e.getMessage());				
			}
		}
		
	}
	
	public ImageItem getImageItem() {
		return imageItem;
	}

	public void setImageItem(ImageItem imageItem) {
		this.imageItem = imageItem;
	}
	

}
