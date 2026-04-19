package com.api.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigManagerOLD {
	
	//Make private constructor so that no one can create object of this class
	private ConfigManagerOLD()
	{
		
	}
	private static Properties prop= new Properties();
	//static block execute once during class load first time
	static {

        File file= new File(System.getProperty("user.dir")+File.separator+"src"+File.separator+"test"+File.separator+"resources"+File.separator+"config"+File.separator+"config.properties");
        FileReader fileReader;
		try {
			fileReader = new FileReader(file);
			prop.load(fileReader);
		} 
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String getProperty(String key) throws IOException {
	
         return prop.getProperty(key);
	}
}
