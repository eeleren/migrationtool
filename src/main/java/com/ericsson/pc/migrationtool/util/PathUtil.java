package com.ericsson.pc.migrationtool.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import com.ericsson.pc.migrationtool.bean.Phone;

public class PathUtil {

	final static Logger logger = Logger.getLogger(PathUtil.class);

	private static String CATALOGUE_FILE_PATH = ApplicationPropertiesReader.getInstance().getProperty("parser.file.catalogue");
	private static String PHONES_DIR_PATH = ApplicationPropertiesReader.getInstance().getProperty("parser.dir.phones");
	private static String ACCESSORY_FILE_PATH = ApplicationPropertiesReader.getInstance().getProperty("parser.file.accessory");
	private static String OUTPUT_FILE_PATH = ApplicationPropertiesReader.getInstance().getProperty("builder.asset.outputdir");
	private static String PHONE_FILE_EXTENSION = ".xml";

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
				phoneFilesPath.add(PHONES_DIR_PATH + File.separator
						+ fileEntry.getName());
			}
		}

		return phoneFilesPath;
	}

	public static String getSinglePhoneFilePath(String phoneName) {
		String phoneFilePath = "";
		for (final File fileEntry : new File(PHONES_DIR_PATH).listFiles()) {
			if (!fileEntry.isDirectory()) {
				if (fileEntry.getName().equalsIgnoreCase(
						phoneName + PHONE_FILE_EXTENSION)) {
					return PHONES_DIR_PATH + File.separator
							+ fileEntry.getName();
				}
			}
		}
		return phoneFilePath;
	}

	public static String getAccessoryPath() {
		return ACCESSORY_FILE_PATH;
	}

	public static String getPhonePath(Phone phone) {
		File file = new File(CATALOGUE_FILE_PATH);
		String xmlFolderPath = file.getParent();

		String pathTmp = phone.getPath();
		String finalPath = xmlFolderPath + File.separator + pathTmp + ".xml";

		return finalPath;
	}

	/**
	 * Creates a single directory for the generated asset in the output path set
	 * in configuration
	 * */
	public static void createAssetOutputDir(String name) {

		File file = new File(OUTPUT_FILE_PATH + name);
		if (!file.exists() && file.canWrite()) {
			file.mkdirs();
			logger.info("Output directory " + name + "created.");
		} else {
			file.delete();
			file.mkdirs();
			logger.info("Output directory deleted and created again");
		}
	}
}
