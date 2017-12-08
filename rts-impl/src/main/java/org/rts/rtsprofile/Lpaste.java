package org.rts.rtsprofile;

import java.util.Properties;

import org.rts.base.Scrapper;
import org.rts.base.ScrapperProfile;
import org.rts.impl.PastieImpl;

public class Lpaste implements ScrapperProfile{
	
	public String getName() {
		// TODO Auto-generated method stub
		return "Lpaste";
	}

	public Scrapper newInstance(Properties properties) {
		// TODO Auto-generated method stub
		PastieImpl pastie=new PastieImpl();
		pastie.initScrapper(properties);
		return pastie;
	}

}
