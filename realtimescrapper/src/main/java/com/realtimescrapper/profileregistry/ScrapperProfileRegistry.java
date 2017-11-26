package com.realtimescrapper.profileregistry;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.ServiceLoader;

import com.realtimescrapper.base.Scrapper;
import com.realtimescrapper.base.ScrapperProfile;

public class ScrapperProfileRegistry {

	static Map<String, ScrapperProfile> scrapperProfileMap = new HashMap<String, ScrapperProfile>();
	
	static {
        //This is a service loader used to load all scanning profiless
        ServiceLoader<ScrapperProfile> loadSP = ServiceLoader.load(ScrapperProfile.class, ScrapperProfile.class.getClassLoader());
        for (ScrapperProfile scrapperProfile : loadSP) {
        	scrapperProfileMap.put(scrapperProfile.getName(), scrapperProfile);
        }
    }
	
	public static Scrapper newScrapProfile(String profile,Properties properties)
	{
		ScrapperProfile sp=scrapperProfileMap.get(profile);
		if (sp==null)
		{
			throw new RuntimeException("ScannerProfile "+profile+" is not defined");
		}
		return sp.newInstance(properties);
	}
}
