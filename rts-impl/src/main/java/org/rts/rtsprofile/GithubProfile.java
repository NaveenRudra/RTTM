package org.rts.rtsprofile;

import java.util.Properties;
import org.rts.base.Scrapper;
import org.rts.base.ScrapperProfile;
import org.rts.impl.GithubImpl;

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
