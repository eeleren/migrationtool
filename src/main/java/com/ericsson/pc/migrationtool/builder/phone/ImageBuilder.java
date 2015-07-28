package com.ericsson.pc.migrationtool.builder.phone;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

import com.ericsson.pc.migrationtool.bean.Item;
import com.ericsson.pc.migrationtool.msdp.ImageItem;
import com.ericsson.pc.migrationtool.util.ApplicationPropertiesReader;


public class ImageBuilder {
	
	public ImageItem imageItem = new ImageItem();

	final static Logger logger = Logger.getLogger(ImageBuilder.class);
	
	private static String IMAGE_FILE_PATH = ApplicationPropertiesReader.getInstance().getProperty("builder.asset.phone.imagePath");
	private static String ACCESSORY_IMAGE_FILE_PATH = ApplicationPropertiesReader.getInstance().getProperty("builder.asset.accessory.imagePath");
	
	public static String getIMAGE_FILE_PATH() {
		return IMAGE_FILE_PATH;
	}
	
	public static String getACCESSORY_IMAGE_FILE_PATH() {
		return ACCESSORY_IMAGE_FILE_PATH;
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
	
	/**Return all the images in the directory in inpuy as imageItem objects
	 * 
	 * */
	/*public List<ImageItem> getContainedImages( String imageDir) {
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
		
	}*/
	
	public void moveImages(List<ImageItem> images, String assetDirectory) {
		File dest = new File(assetDirectory);
		File destThumb = new File(assetDirectory+File.separator+"thumb");
		destThumb.mkdirs();
		File source;
		if ((images!=null)&&(!images.isEmpty())) {
			try {
				for (ImageItem i: images) {
					if(i!=null) {
						source = new File(i.getPath());
						dest = new File(assetDirectory);
						if (i.isThumb()) {							
							//File destThumb = new File(assetDirectory+File.separator+"thumb");
							
							logger.debug("Moving thumb image: " +source+" to: "+destThumb);
							FileUtils.copyFileToDirectory(source, destThumb);
						} else {
							logger.debug("Moving: " +source+" to: "+dest);
							FileUtils.copyFileToDirectory(source, dest);
						}
					}
					
						
				}
			}catch (IOException ioe) {
				logger.error("IO exception while moving images "+ ioe.getMessage());				
			} catch (Exception e) {
				logger.error("Generic error while moving images: "+ e.getMessage());
			}
		} else {
			logger.error("Error: images not found for:" + assetDirectory);
		}

		
		
	}
	
	public void renameThumbsImages(String assetDirectory)  {
		File destThumb = new File(assetDirectory+File.separator+"thumb");
		
		try {
			File[] imagesFile = destThumb.listFiles();		
			for (File f : imagesFile) {
				File destFile = new File(assetDirectory+File.separator+FilenameUtils.removeExtension(f.getName())+"_th."+FilenameUtils.getExtension(f.getName()));
				FileUtils.moveFile(f, destFile);			
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} /*finally {
			try {
				//destThumb.deleteOnExit();
				//FileUtils.deleteDirectory(destThumb);
			} catch (Exception e) {
				logger.error("Cannot delete thumb dir");
				e.printStackTrace();
			}
		}
	*/
	}
	
	public void moveImage(ImageItem image, String assetDirectory) {
		File dest = new File(assetDirectory);
		File source  = new File(image.getPath());
		try {
				FileUtils.copyFileToDirectory(source, dest);
			} catch (IOException e) {
				logger.error("IO exception while moving images "+ e.getMessage());				
			}
	}
		
	public ImageItem getImageItem() {
		return imageItem;
	}

	public void setImageItem(ImageItem imageItem) {
		this.imageItem = imageItem;
	}

	public ImageItem getShopGridImages(String imageDir, String variantColor) {
		ImageItem shopImage = new ImageItem();
		ImageItem shopBasic = new ImageItem();
		
		boolean foundVariant = false;
		boolean foundBasic = false;
		
		File root = new File(imageDir);
		File[] imagesFile = root.listFiles();
		
		for (File f : imagesFile) {
			
			if((f.getName().contains("shop"))&&(f.getName().contains(variantColor))) {
				shopImage.setName(f.getName());
				shopImage.setPath(f.getPath());
				foundVariant=true;
				logger.debug("Found shop image with name: "+ shopImage.getName()+" in the directory: "+shopImage.getPath());
				
			} else if(f.getName().contains("shop")) {
				shopBasic.setName(f.getName());
				shopBasic.setPath(f.getPath());
				foundBasic = true;
			}
		}
		if (foundVariant) {
			return shopImage;
		} else if (foundBasic) {
			return shopBasic;
		} else return null;
	
	}

	public List<ImageItem> getCompareImages(String imageDir) {
		List<ImageItem> compareImages = new ArrayList<ImageItem>();
		
		File root = new File(imageDir);
		File[] imagesFile = root.listFiles();
		
		for (File f : imagesFile) {
			ImageItem i;
			if(f.getName().contains("compare")) {
				i = new ImageItem();
				i.setName(f.getName());
				i.setPath(f.getPath());
				logger.debug("Found compare image with name: "+ i.getName()+" in the directory: "+i.getPath());
				compareImages.add(i);
			}
		}
		
		return compareImages;
	}

	public ImageItem getCartImage(String imageDir) {
		ImageItem cartImage = new ImageItem();
		
		File root = new File(imageDir);
		File[] imagesFile = root.listFiles();
		
		for (File f : imagesFile) {
			
			if(f.getName().contains("cart")) {
				cartImage.setName(f.getName());
				cartImage.setPath(f.getPath());
				logger.debug("Found cart image with name: "+ cartImage.getName()+" in the directory: "+cartImage.getPath());
				
			}
		}
		
		return cartImage;
	}

	public List<ImageItem> getPhoneDetailsImages(String imageDir, String colorVariant) {
		List<ImageItem> phoneDetailsImages = new ArrayList<ImageItem>();
		
		if(getImageByTypeAndVariantColor(imageDir, "hero", colorVariant)!=null) {
			phoneDetailsImages.add(getImageByTypeAndVariantColor(imageDir, "hero", colorVariant));
		}
			
		List<ImageItem> fullImages = getFullViewImages(imageDir);
		for (ImageItem i : fullImages) {
			phoneDetailsImages.add(i);
		}
		List<ImageItem> thumbImages = getThumbViewImages(imageDir);
		for (ImageItem i : thumbImages) {
			phoneDetailsImages.add(i);
		}
		
		return phoneDetailsImages;
	}

	private List<ImageItem> getThumbViewImages(String imageDir) {
		List<ImageItem> thumbViewImages = new ArrayList<ImageItem>();
		
		File root = new File(imageDir+File.separator+"views"+File.separator+"thumb");
		File[] imagesFile = root.listFiles();
		
		for (File f : imagesFile) {
			ImageItem i = new ImageItem();
			i.setThumb(true);
			i.setName(FilenameUtils.removeExtension(f.getName())+"_th."+FilenameUtils.getExtension(f.getName()));
			i.setPath(f.getPath());
			logger.debug("Found thumb image with name: "+ i.getName()+" in the directory: "+i.getPath());
			thumbViewImages.add(i);
			}			
		return thumbViewImages;
	}	

	private List<ImageItem> getFullViewImages(String imageDir) {
		List<ImageItem> fullViewImages = new ArrayList<ImageItem>();
		
		File root = new File(imageDir+File.separator+"views"+File.separator+"full");
		File[] imagesFile = root.listFiles();
		
		for (File f : imagesFile) {
			ImageItem i = new ImageItem();
			i.setName(f.getName());
			i.setPath(f.getPath());
			logger.debug("Found full image with name: "+ i.getName()+" in the directory: "+i.getPath());
			fullViewImages.add(i);
			}	
		
		return fullViewImages;
	}

	
	private ImageItem getImageByType (String imageDir, String imageType) {
		ImageItem anImage = null;		
		File root = new File(imageDir);
		File[] imagesFile = root.listFiles();		
		for (File f : imagesFile) {			
			if(f.getName().contains(imageType)) {
				anImage = new ImageItem();
				anImage.setName(f.getName());
				anImage.setPath(f.getPath());
				logger.debug("Found image with name: "+ anImage.getName()+" in the directory: "+anImage.getPath());				
			}
		}		
		return anImage;
	}
	
	private ImageItem getImageByTypeAndVariantColor (String imageDir, String imageType, String variantColor) {
			
		File root = new File(imageDir);
		File[] imagesFile = root.listFiles();
		
		for (File f : imagesFile) {
			
			if((f.getName().contains(imageType))&&(f.getName().contains(variantColor))) {
				ImageItem anImage = new ImageItem();
				anImage.setName(f.getName());
				anImage.setPath(f.getPath());
				logger.debug("Found image with name: "+ anImage.getName()+" in the directory: "+anImage.getPath());
				return anImage;
			}
		}
		
		
		return null;
	}

	public List<ImageItem> getFeatureImages(String imageDir) {
		List<ImageItem> featureImages = new ArrayList<ImageItem>();
		
		if (getImageByType(imageDir, "specifications")!=null) {
			featureImages.add(getImageByType(imageDir, "specifications"));
		}
		if (getImageByType(imageDir, "included_in_box") != null) {
			featureImages.add(getImageByType(imageDir, "included_in_box"));
		}
		return featureImages;
	}
	
	public ImageItem getAccessoryShopImage(String ImageDir) {
		ImageItem shopImage = new ImageItem();
		
		File root = new File(ImageDir);
		File[] imagesFile = root.listFiles();
		
		for (File f : imagesFile) {
			
			if(f.getName().contains("shop")) {
				shopImage.setName(f.getName());
				shopImage.setPath(f.getPath());
				logger.debug("Found shop image with name: "+ shopImage.getName()+" in the directory: "+shopImage.getPath());
				
			}
		}
		
		return shopImage;
	}
	
	public ImageItem getAccessoryCartImage(String ImageDir) {
		ImageItem shopImage = new ImageItem();
		
		File root = new File(ImageDir);
		File[] imagesFile = root.listFiles();
		
		for (File f : imagesFile) {
			
			if(f.getName().contains("cart")) {
				shopImage.setName(f.getName());
				shopImage.setPath(f.getPath());
				logger.debug("Found cart image with name: "+ shopImage.getName()+" in the directory: "+shopImage.getPath());
				
			}
		}
		
		return shopImage;
	}
	
	

}
