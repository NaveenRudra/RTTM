package com.realtimescrapperpastie.realtimescrapperprofile;

import java.util.Properties;

import com.realtimescrapper.base.Scrapper;
import com.realtimescrapper.base.ScrapperProfile;
import com.realtimescrapperpastie.realtimescrapperpastie.RedditImpl;

public class RedditProfile implements ScrapperProfile {

	public String getName() {
		// TODO Auto-generated method stub
		return "Reddit";
	}

	public Scrapper newInstance(Properties properties) {
		// TODO Auto-generated method stub
		RedditImpl reddit=new RedditImpl();
		reddit.initScrapper(properties);
		return reddit;
	}

}
