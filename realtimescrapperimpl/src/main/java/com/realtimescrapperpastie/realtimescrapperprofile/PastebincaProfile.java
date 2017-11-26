package com.realtimescrapperpastie.realtimescrapperprofile;

import java.util.Properties;

import com.realtimescrapper.base.Scrapper;
import com.realtimescrapper.base.ScrapperProfile;
import com.realtimescrapperpastie.realtimescrapperpastie.PastieImpl;

public class PastebincaProfile implements ScrapperProfile{
	public String getName() {
		// TODO Auto-generated method stub
		//check with time if pastebin.ca comess up with different results browsers and mine are not matching.. need to identify what is that extra parameter needed man
		return "Pastebinca";
	}

	public Scrapper newInstance(Properties properties) {
		// TODO Auto-generated method stub
		PastieImpl pastie=new PastieImpl();
		pastie.initScrapper(properties);
		return pastie;
	}

}
