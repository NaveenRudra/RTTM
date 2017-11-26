package com.realtimescrapperpastie.realtimescrapperprofile;

import java.util.Properties;

import com.realtimescrapper.base.Scrapper;
import com.realtimescrapper.base.ScrapperProfile;
import com.realtimescrapperpastie.realtimescrapperpastie.GithubImpl;

public class GithubProfile implements ScrapperProfile{

	public String getName() {
		// TODO Auto-generated method stub
		return "Github";
	}

	public Scrapper newInstance(Properties properties) {
		// TODO Auto-generated method stub
		
		GithubImpl gitHub=new GithubImpl();
		gitHub.initScrapper(properties);
		return gitHub;
	}

}
