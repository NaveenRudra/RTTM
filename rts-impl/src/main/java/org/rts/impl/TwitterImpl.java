package org.rts.impl;

import java.util.Properties;

import org.apache.log4j.Logger;

import org.rts.base.Scrapper;
import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;
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
	
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
    }

	public void initScrapper(Properties prop) {
		
		System.out.println( "Hello World!" );
		ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
        configurationBuilder.setOAuthConsumerKey(prop.getProperty("consumerKey"))
                .setOAuthConsumerSecret(prop.getProperty("consumerSecret"))
                .setOAuthAccessToken(prop.getProperty("accessToken"))
                .setOAuthAccessTokenSecret(prop.getProperty("accessTokenSecret"));
        searchTerms=prop.getProperty("searchterms").split(",");
        twitterStream = new TwitterStreamFactory(configurationBuilder.build()).getInstance();
        twitterStream.addListener(new StatusListener () {
           
			public void onException(Exception arg0) {
				// TODO Auto-generated method stub
				arg0.printStackTrace();
				
				
			}

			public void onDeletionNotice(StatusDeletionNotice arg0) {
				// TODO Auto-generated method stub
				
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
