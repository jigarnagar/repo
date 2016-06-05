package com.adp.vehicleidentifier.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * This Utility class is used read the property file 
 * @author Jigar Nagar
 *
 */
public class ReadPropertyUtil {
    private static Logger LOGGER = LoggerFactory.getLogger(ReadPropertyUtil.class);
	private static Map<String, String> propertyCache = new HashMap<String, String>();
	private static final String PROPERTY_FILE_LOCATION = "src/main/resources/identifyvehicletype.properties";

	/**
	 * This method is used to read the property file and put it entries in the MAP
	 * @return
	 * @throws IOException
	 */
	public static Map<String, String> getPropValues() throws IOException {
	    LOGGER.trace("Entered ReadPropertyUtil getPropValues()");
		FileInputStream inputStream = null;
		try {
			Properties prop = new Properties();

			inputStream = new FileInputStream(PROPERTY_FILE_LOCATION);

			prop.load(inputStream);
			@SuppressWarnings("unchecked")
			Enumeration<String> enumeration = ((Enumeration<String>) prop
					.propertyNames());
			while (enumeration.hasMoreElements()) {
				String key = enumeration.nextElement();
				propertyCache.put(key, prop.getProperty(key));
			}		
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
		}
		LOGGER.trace("Exiting ReadPropertyUtil getPropValues()");
		return propertyCache;
	}
}
