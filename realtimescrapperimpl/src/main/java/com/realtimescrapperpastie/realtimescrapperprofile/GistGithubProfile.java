package com.realtimescrapperpastie.realtimescrapperprofile;

import java.util.Properties;

import com.realtimescrapper.base.Scrapper;
import com.realtimescrapper.base.ScrapperProfile;
import com.realtimescrapperpastie.realtimescrapperpastie.PastieImpl;

public class GistGithubProfile implements ScrapperProfile{
	
	public String getName() {
		// TODO Auto-generated method stub
		return "GistGithub";
	}

	public Scrapper newInstance(Properties properties) {
		// TODO Auto-generated method stub
		PastieImpl pastie=new PastieImpl();
		pastie.initScrapper(properties);
		return pastie;
	}

}
