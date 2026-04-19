package com.api.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {
	private static Properties prop= new Properties();
	private static String path="config\\config.properties";
	private static String env;
	//Make private constructor so that no one can create object of this class
	private ConfigManager(){
		
	}
	
	static {

		env = System.getProperty("env","dev");
		env= env.toLowerCase().trim();
		System.out.println("Running test :"+env);
		

		switch (env) {

		case "dev" -> path = "config/config.dev.properties";

		case "qa"->path = "config/config.qa.properties";
		
		case "uat"->path = "config/config.uat.properties";
		default-> path = "config/config.qa.properties";
		}

		InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
		if (input == null) {
			throw new RuntimeException("Can not fine the file at the path " + path);
		}
		try {

			prop.load(input);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static String getProperty(String key) throws IOException {
	
         return prop.getProperty(key);
	}
}
