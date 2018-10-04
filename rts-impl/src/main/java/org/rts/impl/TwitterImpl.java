package org.rts.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.kafkaparser.pojo.Data;
import org.kafkaparser.utilities.DbUtil;
import org.kafkaparser.utilities.EmailUtility;
import org.rts.base.Scrapper;
import org.sqlite.dataaccess.util.DaoUtil;

import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;
import java.util.regex.Pattern;

/**
 * Hello world!
 *
 */

public class TwitterImpl implements Scrapper
{
	private TwitterStream twitterStream = null;
	private FilterQuery tweetFilterQuery = null;
	private String[] searchTerms=null;
	final static Logger logger = Logger.getLogger(TwitterImpl.class);
	private HashMap<String, Pattern> keywordsMap = new HashMap<>();
	
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
    }

	public void initScrapper(Properties prop) {
		
		
		ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
        configurationBuilder.setOAuthConsumerKey(prop.getProperty("consumerKey"))
                .setOAuthConsumerSecret(prop.getProperty("consumerSecret"))
                .setOAuthAccessToken(prop.getProperty("accessToken"))
                .setOAuthAccessTokenSecret(prop.getProperty("accessTokenSecret"));
        searchTerms=prop.getProperty("searchterms").split(",");
        if(searchTerms.length > 0) {
            for(String keyword : searchTerms) {
                keywordsMap.put(keyword, Pattern.compile(keyword, Pattern.CASE_INSENSITIVE));
            }
        }
        twitterStream = new TwitterStreamFactory(configurationBuilder.build()).getInstance();
        
        
        
        twitterStream.addListener(new StatusListener () {
           
			public void onException(Exception arg0) {
				// TODO Auto-generated method stub
				arg0.printStackTrace();
				
				
			}

			public void onDeletionNotice(StatusDeletionNotice arg0) {
				// TODO Auto-generated method stub
				
			}
			
			private ArrayList<String> getKeywordsFromTweet(String tweet) {
				ArrayList<String> result = new ArrayList<>();

			    for (String keyword : keywordsMap.keySet()) {
			        Pattern p = keywordsMap.get(keyword);
			        if (p.matcher(tweet).find()) {
			            result.add(keyword);
			        }
			    }

			    return result;
			}
			

			public void onScrubGeo(long arg0, long arg1) {
				// TODO Auto-generated method stub
				
			}

			public void onStallWarning(StallWarning arg0) {
				// TODO Auto-generated method stub
				
			}

			public void onStatus(Status status) {
				// TODO Auto-generated method stub
				System.out.println(status.getText());
				if(!status.isRetweeted())
				{
				System.out.println(status.getText());
				String url= "https://twitter.com/" + status.getUser().getScreenName() 
					    + "/status/" + status.getId();
				
				ArrayList<String> termsfound=getKeywordsFromTweet(status.getText());
				if(!DaoUtil.searchDuplicateByUrl(url))
				{
					EmailUtility.sendEmailUsingGmail("Twitter", url, termsfound);
					DbUtil.addNewEntry(termsfound, url);
				}
				}
				
			}

			public void onTrackLimitationNotice(int arg0) {
				// TODO Auto-generated method stub
				
			}});
		
        tweetFilterQuery = new FilterQuery(); // See 
        tweetFilterQuery.track(searchTerms); // OR on keywords
	}

	public boolean start() {
		// TODO Auto-generated method stub
		return false;
	}

	public void kickOffActualWork() {
		// TODO Auto-generated method stub
		System.out.println("Twitter has been kicked off");
		logger.info("Kicked of the Twitter");
		twitterStream.filter(tweetFilterQuery);
		
	}

	public boolean stopScrapper() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean restart() {
		// TODO Auto-generated method stub
		return false;
	}

	public void run() {
		// TODO Auto-generated method stub
		kickOffActualWork();
		
	}
}
