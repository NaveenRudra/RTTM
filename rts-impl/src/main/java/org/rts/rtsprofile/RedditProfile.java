package org.rts.rtsprofile;

import java.util.Properties;

import org.rts.base.Scrapper;
import org.rts.base.ScrapperProfile;
import org.rts.impl.RedditImpl;

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
