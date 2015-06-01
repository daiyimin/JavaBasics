package com.ericsson.tc;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;


public class ConfigTool {
	private static Map<String, String> configs;
	
	private static void loadLog4jConfig() {
		String localDir = ConfigTool.getConfig("localdir");
		PropertyConfigurator.configure(localDir + "log4j.properties");
	}
	
	public static void loadConfig() throws IOException {
		String localDir = System.getProperty("user.dir") + "/";
		Properties prop = new Properties();
		configs = new HashMap<String, String>();
		prop.load(new FileInputStream(localDir + "config.prop"));
		for(Object key: prop.keySet()) {
			configs.put(key.toString(), prop.get(key).toString());
		}
		configs.put("localdir", localDir);
		
		loadLog4jConfig();
	}
	
	public static String getConfig(String key) {
		return configs.get(key);
	}
}
