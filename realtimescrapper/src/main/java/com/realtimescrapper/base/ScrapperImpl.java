package com.realtimescrapper.base;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.realtimescrapper.profileregistry.ScrapperProfileRegistry;
import com.realtimescrapper.utilities.PropertyUtilities;;

public class ScrapperImpl {
	
	public boolean initlialized;
	private Map<String, Scrapper> scrapperMap = new HashMap<String, Scrapper>();
	private static ScrapperImpl instance;
	private Properties properties = new Properties();
	
	public Map<String, Scrapper> getScrapperMap()
	{
		return scrapperMap;
	}
	
	public static synchronized ScrapperImpl getInstance() {
        if (instance == null) {
            instance = new ScrapperImpl();
        }
        return instance;
    }
	
	public synchronized void initialize (File configDirectory) throws IOException
	{
		Properties prop = new Properties();
		prop.setProperty("configDirectory", configDirectory.getAbsolutePath());
		prop.load(new ByteArrayInputStream(Files.readAllBytes(new File(configDirectory, "scanner-configuration.properties").toPath())));
		initialize(prop);
		
	}

	public synchronized void initialize (Properties prop)throws IOException
	{
		properties = new Properties();
        properties.putAll(prop);
        Map<String, Properties> scannerPropertiesMap = PropertyUtilities.propertiesGroupByFirstDot(PropertyUtilities.filterAndShiftByFirstDot(properties, "scrapper"));
        try{
			for(Map.Entry<String, Properties> entry:scannerPropertiesMap.entrySet())
			{
				String profile = entry.getValue().getProperty("profile");	
				scrapperMap.put(profile, ScrapperProfileRegistry.newScrapProfile(profile, entry.getValue()));
			}
		}
        
        finally
        {
        	// This is yet to be implemented and i will decide on this later man
        }
		initlialized=true;
		
	}
}
