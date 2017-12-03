package org.rts.base;

import java.util.Properties;

/**
 * @author rudrapna
 *ScrapperProfile interface is implemented by profilers of Scrappers whicg returns new instance of a Scrapper. All Scrappers are loaded dynamically
 *using class loader.
 */

public interface ScrapperProfile {
	
	// This function is used to get the name of 
	public String getName();
	
    Scrapper newInstance(Properties properties);

}
