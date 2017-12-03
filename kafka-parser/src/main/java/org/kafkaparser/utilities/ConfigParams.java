package org.kafkaparser.utilities;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.google.common.io.Resources;

public class ConfigParams {
	
	public static String proxy_ip;
	public static String proxy_port;
	
	public static void initialzie() throws IOException
	{
		/** try (InputStream props = Resources.getResource("config.props").openStream()) {
	            Properties properties = new Properties();
	            properties.load(props);
	            proxy_ip=properties.getProperty("proxy_ip");
	            proxy_port=properties.getProperty("proxy_port"); 
	            System.setProperty("http.proxySet", "true");
	            System.setProperty("http.proxyHost",proxy_ip) ;
	            System.setProperty("http.proxyPort", proxy_port) ;
	            System.setProperty("https.proxySet", "true");
	            System.setProperty("https.proxyHost",proxy_ip) ;
	            System.setProperty("https.proxyPort", proxy_port) ;
		 }**/
	}
	
	

}
