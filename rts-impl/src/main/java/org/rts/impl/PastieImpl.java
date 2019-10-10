package org.rts.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;
import java.util.Queue;
import java.util.Set;
import org.apache.log4j.Logger;
import com.google.common.collect.EvictingQueue;
import org.kafkaparser.base.Producer;
import org.rts.base.Scrapper;
import org.kafkaparser.utilities.ConfigData;
import org.kafkaparser.utilities.PastieParseAndSearch;
import org.kafkaparser.pojo.Data;

public class PastieImpl implements Scrapper{

	private String homeurl;
	private String regex;
	private String downloadurl;
	private String timetoSleep;
	public  Queue<String> evictingQueue= EvictingQueue.create(600);
	public  Set<String> previousSet = new HashSet<String>();
	public  Set<String> presentSet = new HashSet<String>();
	public Set<String> diffSet = new HashSet<String>();
	private ArrayList<String> searchTerms=new ArrayList<String>();
	final static Logger logger = Logger.getLogger(PastieImpl.class);
	private String profile="";
	private String trufflehogregex="false";
	private String trufflehogentropy="false";
	
	public void initScrapper(Properties prop) {
		// TODO Auto-generated method stub
		//write a common function to check values of all the variables that it is populated in the config proeprties file man
		//maintain previous test ones and compare with present ones before making any requests again man
		this.regex=prop.getProperty("regex");
		this.downloadurl=prop.getProperty("downloadurl");
		this.homeurl=prop.getProperty("homeurl");	
		this.timetoSleep=prop.getProperty("timetosleep");
		this.searchTerms=new ArrayList<String>(Arrays.asList(prop.getProperty("searchterms").split("\\s*,\\s*")));
		this.profile=prop.getProperty("profile");
		this.trufflehogregex=prop.getProperty("trufflehogregex").toLowerCase();
		this.trufflehogentropy=prop.getProperty("trufflehogentropy").toLowerCase();
	}

	 public void run() {
		 	System.out.println("Kicked off "+profile);
		 	try {
		        while (true)
		        {
		        	kickOffActualWork();
		            Thread.sleep(Integer.parseInt(timetoSleep));
		            System.gc();
		        }
		        } catch (InterruptedException e) {
					// TODO Auto-generated catch block
		        	logger.error("Pastie Impl run has failed:",e);
				}
	    }

	public void kickOffActualWork() {
		// TODO Auto-generated method stub
		
		ArrayList<String> ids = null;
		try {
			ids = PastieParseAndSearch.fetchids(homeurl,regex);
			presentSet = new HashSet<String>(ids);
			//diffSet=Difference.getDiff(previousSet, presentSet);
			//previousSet = presentSet;
			Iterator<String> iter = presentSet.iterator();
			while (iter.hasNext()) 
			{   String id=(String)iter.next();
				if(!evictingQueue.contains(id))
					{
					Producer.send(new Data(downloadurl.replace("{id}", id),searchTerms,profile,trufflehogregex,trufflehogentropy), ConfigData.topicName);
					}
		       
				evictingQueue.add(id);
			}
		  }
		 catch (IOException e) {
				// TODO Auto-generated catch block
			 logger.error("Pastie Impl kickoff has failed:",e);
			} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean stopScrapper() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean restart() {
		// TODO Auto-generated method stub
		return false;
	}

}
