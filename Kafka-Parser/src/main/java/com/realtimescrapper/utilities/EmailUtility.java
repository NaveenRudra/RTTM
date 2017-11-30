package com.realtimescrapper.utilities;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Set;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.log4j.Logger;
import com.google.common.base.Joiner;
import com.google.common.io.Resources;


public class EmailUtility {
	
	static Properties properties;
	static Session session;
	final static Logger logger = Logger.getLogger(EmailUtility.class);
	
	static{
		 try {
	            properties = new Properties();
	            File configDirectory =  new File(ConfigData.configDirectory);
	            properties.load(new ByteArrayInputStream(Files.readAllBytes(new File(configDirectory, ConfigData.emailPropertiesFileName).toPath())));
	            
			 } catch (IOException e) {
				// TODO Auto-generated catch block
			    logger.error("Error occured while initializing email properties",e);
				e.printStackTrace();
			}
		 
		 session = Session.getInstance(properties,
				  new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(properties.getProperty("from-email"),properties.getProperty("password"));
					}	
				  });
	    }
	
	
	public static void sendEmailUsingGmail(String botName,Set<String> alertSet,String termfound )
	{
		System.out.println("Search term has been found in "+botName+" terms is "+termfound);
		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(properties.getProperty("from-email")));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(properties.getProperty("to-email")));
			message.setSubject(botName+":Alert");
			message.setText("Dear Team,"
				+ "\n\n Terms:"+termfound
				+"\n\n In the below URLs"
				+"\n\n"+Joiner.on(",").join(alertSet));
			Transport.send(message);

		} catch (MessagingException e) {
			logger.error("Some issue with sending email:",e);
		}
	}
	
	public static void sendEmailUsingGmail(String botName,String url,ArrayList<String> termsfound )
	{
		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(properties.getProperty("from-email")));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(properties.getProperty("to-email")));
			message.setSubject(botName+":Alert");
			message.setText("Dear Team,"
				+ "\n\n Terms:"+Joiner.on(",").join(termsfound)
				+"\n\n In the below URL"
				+"\n\n"+url);
			Transport.send(message);

		} catch (MessagingException e) {
			logger.error("Some issue with sending email:",e);
		}
	}
	
	public static void main(String args [])
	{
		ArrayList<String> ids = new ArrayList<String>();
		ids.add("1");
		ids.add("2");
		ids.add("3");
		ids.add("4");
		EmailUtility.sendEmailUsingGmail("pastebin", "http://google.com", ids);
	}

}
