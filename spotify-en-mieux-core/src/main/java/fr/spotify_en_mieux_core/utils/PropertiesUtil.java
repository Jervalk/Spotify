package fr.spotify_en_mieux_core.utils;

import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {
	
	private static Properties properties;
	
	static {
		try {
			properties = new Properties();	
			properties.load(PropertiesUtil.class.getClassLoader().getResourceAsStream("application.properties"));
		} catch (IOException e) {
			throw new Error("properties loading failed", e);
		}
	}
	
	public static Properties getProperties() {
		return properties; 
	}
	
	public static String getProperty(String key) {
		return properties.getProperty(key);
	}
	

}
