package com.ericsson.pc.migrationtool.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;



public class ApplicationPropertiesReader {
	
	 private static final String CLASSNAME = "ApplicationPropertiesReader";
	 private static ApplicationPropertiesReader instance = null;
	 private static final String CONFIG_PROPERTIES = "config.properties";
	 private Properties properties;
	 
	 private ApplicationPropertiesReader()
	 {
		 try{
			 String basePath = System.getProperty("parser.file.config");
			 String configurationFile = basePath; // + File.separator + "conf" + File.separator + CONFIG_PROPERTIES;
			 properties = new Properties();
			 properties.load(new FileInputStream(new File(configurationFile)));
		 }catch(Exception e){
	         //ApplicationLogger.error(CLASSNAME, null, "Exception inizializing properties file",e);
			 System.out.println("Exception inizializing properties file "+e.getCause());
	         System.exit(-1);
	     }
	 }
	 
	 public static synchronized ApplicationPropertiesReader getInstance()
	 {
	     if(instance == null)
	         instance = new ApplicationPropertiesReader();
	     return instance;
	 }
	 
	 public String getProperty(String key)
	 {
	     String res = properties.getProperty(key);
	     if(res == null)
	         return "";
	     else
	         return res;
	 }
	 
	 public String getProperty(String key, String defaultValue)
	 {
	     if(getProperty(key) == null || getProperty(key).equals(""))
	         return defaultValue;
	     else
	         return getProperty(key);
	 }
	
/*	public String getPropValues() throws IOException {
		 
		String result = "";
		Properties prop = new Properties();
		String propFileName = "config.properties";	
 
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
 
		if (inputStream != null) {
			prop.load(inputStream);
		} else {
			throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
		}
 
		Date time = new Date(System.currentTimeMillis());
 
		// get the property value and print it out
		String user = prop.getProperty("user");
		String company1 = prop.getProperty("company1");
		String company2 = prop.getProperty("company2");
		String company3 = prop.getProperty("company3");
 
		result = "Company List = " + company1 + ", " + company2 + ", " + company3;
		System.out.println(result + "\nProgram Ran on " + time + " by user=" + user);
		return result;
	}*/
}


