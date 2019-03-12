package org.rts.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import org.apache.log4j.Logger;
import org.rts.base.Scrapper;
import org.kafkaparser.utilities.DbUtil;
import org.kafkaparser.utilities.EmailUtility;
import org.rts.utilities.JsonParserForGithub;
import org.sqlite.dataaccess.util.DaoUtil;

public class GithubImpl implements Scrapper {

	private String baseurl;
	private String access_token;
	private String timetoSleep;
	private ArrayList<String> searchTerms=new ArrayList<String>();
	final static Logger logger = Logger.getLogger(GithubImpl.class);
	private String profile ="";
	
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
		this.profile= prop.getProperty("profile");
	}

	public void kickOffActualWork() {
		while(true)
		{
			try {
				for(String searchterm:searchTerms)
				{
					Set<String> alertSet=JsonParserForGithub.githubUrlFetcher(baseurl.replace("{searchTerm}", searchterm.replace(" ", "%20"))+"&access_token="+access_token);
					Set<String> filteredalertSet = new HashSet<String>(); 
					
					for(String url:alertSet)
					{
						if(!DaoUtil.searchDuplicateByUrl(url))
						{
							//System.out.println("Comparing url" + url);
							filteredalertSet.add(url);
						}
					}
					if(filteredalertSet.size()>0)
					{
						//System.out.println("Reuqired terms have been found");
						EmailUtility.sendEmailUsingGmail(profile, filteredalertSet, searchterm);
						for(String url:filteredalertSet)
						{
							if(!DaoUtil.searchDuplicateByUrl(url))
							{
								ArrayList<String> temp=new ArrayList<String>();
								temp.add(searchterm);
								DbUtil.addNewEntry(temp, url,profile);
							
						     }
					    }
				    }
				}
				Thread.sleep(Integer.parseInt(timetoSleep));
			} catch (Exception e) {
				logger.error("kickOffActualWork function in GithubImpl class has thrown exception",e);
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
