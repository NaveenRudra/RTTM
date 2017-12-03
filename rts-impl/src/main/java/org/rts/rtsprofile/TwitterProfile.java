package org.rts.rtsprofile;

import java.util.Properties;

import org.rts.base.Scrapper;
import org.rts.base.ScrapperProfile;
import org.rts.impl.TwitterImpl;

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
