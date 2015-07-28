package com.ericsson.pc.migrationtool.builder;


import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.ericsson.pc.migrationtool.bean.Model;
import com.ericsson.pc.migrationtool.builder.phone.PhoneConstants;
import com.ericsson.pc.migrationtool.util.ApplicationPropertiesReader;


public abstract class Builder {
	
	
	protected final static Logger logger = Logger.getLogger(Builder.class);
	protected final String outputDir = ApplicationPropertiesReader.getInstance().getProperty("builder.asset.outputdir");	
	protected final String extension = PhoneConstants.FILE_EXTENSION;			
	
	protected String assetOutputDir;
	protected String assetName;
	
	public abstract Builder getNewInstance();
	
	public abstract void createAssets(List<Model> models);
		
	public void setAssetName(String assetName) {
		this.assetName = assetName;
		
	}

	protected void setAssetName(String assetBrand, String assetName) {		
		this.assetName = assetBrand + "_" + assetName;
		this.normalizeAssetName();		
	}
	
	protected void setAssetName(String assetBrand, String assetName, String variantColor) {		
		this.assetName = assetBrand + "_" + assetName + "_" + variantColor;
		this.normalizeAssetName();		
	}	
	
	public void setAssetName(String assetBrand, String assetName, String variantColor, String variantMemory) {	
		if ((variantMemory == null)||("".equalsIgnoreCase(variantMemory))) {
			setAssetName(assetBrand, assetName, variantColor);
		} else {
			this.assetName = assetBrand + "_" + assetName + "_" + variantColor + "_" + variantMemory ;
			this.normalizeAssetName();	
		}
	
	}
	
	public void normalizeAssetName(){
		this.assetName = assetName.toLowerCase().replaceAll("\\s+","_");		
	}	
	

	protected String getAssetOutputDir() {
		return assetOutputDir;
	}
	
	public String getAssetName() {
		return assetName;
	}

	protected void setAssetOutputDir(String assetOutputDir) {
		this.assetOutputDir = assetOutputDir;
	}
	
	public void cleanOutputDir() {
		String cleanup = ApplicationPropertiesReader.getInstance().getProperty("builder.asset.outputdircleanup");
		
		if (cleanup.equalsIgnoreCase("true")) {
			File assetsOutput = new File (outputDir);
			try {
				FileUtils.cleanDirectory(assetsOutput);
			} catch (IOException e) {
			logger.error(e,e);
			}
	
		}
	}
}
