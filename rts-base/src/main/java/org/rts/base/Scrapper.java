package org.rts.base;

import java.util.Properties;

/**
Scrapper is a entity which is to scrap a given pastie,twitter,github etc..
All scrappers have to implement this interface. it is mandatory to extend for best practices and to allow loading of classes dynamically.
If some one needs to extend this framework then it can be done easily by implementing this interface and Scrapperprofile interface.
**/
public interface Scrapper extends Runnable {
	
	//This function is used to initialize the scrapper 

	public void initScrapper(Properties prop);
	
	
	//This function does the atual work working with kafka
	public void kickOffActualWork();
	
	//This function is used to stop scrapper from running
	public boolean stopScrapper();
	
	//This function is used to stop scrapper from running
		public boolean restart();

}
