package com.realtimescrapperpastie.realtimescrapperprofile;

import java.util.Properties;

import com.realtimescrapper.base.Scrapper;
import com.realtimescrapper.base.ScrapperProfile;
import com.realtimescrapperpastie.realtimescrapperpastie.TwitterImpl;

public class TwitterProfile implements ScrapperProfile{

	public String getName() {
		// TODO Auto-generated method stub
		return "Twitter";
	}

	public Scrapper newInstance(Properties properties) {
		// TODO Auto-generated method stub
		TwitterImpl twitter=new TwitterImpl();
		twitter.initScrapper(properties);
		return twitter;
	}

}
