package com.rt.scrapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import com.realtimescrapper.base.NotificationConsumerGroup;
import com.realtimescrapper.base.Producer;
import com.realtimescrapper.base.Scrapper;
import com.realtimescrapper.base.ScrapperImpl;
import com.realtimescrapper.utilities.ConfigData;

import org.apache.log4j.Logger;
import org.apache.commons.cli.*;

public class ScrapperTool {
	
	static ScrapperImpl scrapperimpl;
	static HashMap<String, Thread> allThreads = new HashMap<String, Thread>();
	final static Logger logger = Logger.getLogger(ScrapperTool.class);
	public static String configDirectory;
	public static String topicname;
	static File configDirectoryfile;
	
	public static void initializeScrappers(String configDirectory)
	{
		//proper error handling and null pointer exception is a must and code ahs to be written man
		//logger check for proeprties file befor ekicking off
		//check if internet connection is there,log and pause threads if no internet
		//logger check for inetrnet connnection
		//logger check for any otehr things like rate limiting 200ok issues and all
		//logger for email too
		
		try{
			scrapperimpl= ScrapperImpl.getInstance();
			configDirectoryfile = new File(ConfigData.configDirectory);
			scrapperimpl.initialize(configDirectoryfile);
			Producer.initialize(configDirectoryfile);	
		} 
		catch (IOException e) {
			//logger.error("Iniltizlization of scrappers has failed", e);
			e.printStackTrace();
		}		
	}
	
	public static void checkThreadsStatus() throws InterruptedException
	{
		while (true)
		{
			for (Map.Entry<String, Thread> entry : allThreads.entrySet())
			{
			    if(!entry.getValue().isAlive())
			    {
			    	System.out.println("Restrating the thread: "+entry.getKey()+" The reason being it not alive.");
			    	allThreads.remove(entry.getKey());
			    	startThread(entry.getKey());   	
			    	logger.error(entry.getKey()+"Thread has been restarted succeafully");
			    }
			}
			Thread.sleep(60000);
		}
	}
	
	public static void startThread(String threadname)
	{
		Scrapper profile=scrapperimpl.getScrapperMap().get(threadname);
		Thread profilethread=new Thread(profile);
		profilethread.start();
		allThreads.put(threadname, profilethread);
	}
	
	public static void startScrappers()
	{

		try {
				initializeScrappers(configDirectory);
				for (Entry<String, Scrapper> entry : scrapperimpl.getScrapperMap().entrySet())
				{
					startThread(entry.getKey());
				}
				NotificationConsumerGroup newgroup;
				newgroup = new NotificationConsumerGroup(5, ConfigData.topicName,configDirectoryfile);
				newgroup.execute();
				//checkThreadsStatus();
		} catch (Exception e) {
			logger.error("Something wrong happened in scrappers", e);
			e.printStackTrace();
		}
		

	}

	public static void main(String args[])
	{
		Options options = new Options();
		
		Option input1 = new Option("c", "configDirectory", true, "configDirectory path");
        input1.setRequired(true);
        options.addOption(input1);
        
        Option input2 = new Option("t", "topicname", true, "topicname of kafka");
        input2.setRequired(true);
        options.addOption(input2);
		
        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("Scrapper", options);
            System.exit(1);
            return;
        }

        ConfigData.configDirectory=cmd.getOptionValue("configDirectory");
        ConfigData.topicName=cmd.getOptionValue("topicname");
        //configDirectory = cmd.getOptionValue("configDirectory");
        //topicname = cmd.getOptionValue("topicname");
        
		ScrapperTool.startScrappers();
	}
}
