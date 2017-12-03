package org.rts.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;
import java.util.Set;
import org.apache.log4j.Logger;
import org.rts.base.Scrapper;
import org.kafkaparser.utilities.EmailUtility;
import org.rts.utilities.JsonParserForGithub;

public class GithubImpl implements Scrapper {

	private String baseurl;
	private String access_token;
	private String timetoSleep;
	private ArrayList<String> searchTerms=new ArrayList<String>();
	final static Logger logger = Logger.getLogger(GithubImpl.class);
	
	public void run() {
		// TODO Auto-generated method stub
		kickOffActualWork();
		
	}

	public void initScrapper(Properties prop)
	{
		// TODO Auto-generated method stub
		this.baseurl=prop.getProperty("baseurl");
		this.access_token=prop.getProperty("access_token");
		this.timetoSleep=prop.getProperty("timetosleep");	
		this.searchTerms=new ArrayList<String>(Arrays.asList(prop.getProperty("searchterms").split("\\s*,\\s*")));
	}

	public void kickOffActualWork() {
		while(true)
		{
			try {
				for(String searchterm:searchTerms)
				{
					Set<String> alertSet=JsonParserForGithub.githubUrlFetcher(baseurl.replace("{searchTerm}", searchterm)+"&access_token="+access_token);
					if(!(alertSet.size()==0))
					{
						//System.out.println("Reuqired terms have been found");
						EmailUtility.sendEmailUsingGmail("Github", alertSet, searchterm);
					}
				}
				Thread.sleep(Integer.parseInt(timetoSleep));
			} catch (Exception e) {
				logger.error("kickOffActualWork in GithubImpl class has thrown exception",e);
			}
		
		}
		
	}

	public boolean stopScrapper() {
		// TODO Auto-generated method stub
		//enable multiple threads to speed up the process man
		return false;
	}

	public boolean restart() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public static void main(String args[])
	{
		Thread t=new Thread(new GithubImpl());
		t.run();
	}

}
