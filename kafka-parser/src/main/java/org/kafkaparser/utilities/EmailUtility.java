package org.kafkaparser.utilities;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;
import com.google.common.base.Joiner;
import com.google.common.io.Resources;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;


public class EmailUtility {
	
	static Properties properties;
	static Session session;
	final static Logger logger = Logger.getLogger(EmailUtility.class);
	final String emailTemplate="";
	
	static{
		 try {
	            properties = new Properties();
	            File configDirectory =  new File(ConfigData.configDirectory);
	            //File configDirectory =  new File("./scrapper");
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
	
	
	public static void sendEmailUsingGmail(String botName, Set<String> alertSet, String termfound)
	{
		System.out.println("Search term has been found in "+botName+" terms is "+termfound);
		try {

			//configurng free marjer to send message using email template
			// freemarker stuff.
            Configuration cfg = new Configuration();
            cfg.setDirectoryForTemplateLoading(new File(
            	    ConfigData.configDirectory));
            Template template = cfg.getTemplate("html-mail-template.ftl");
            Map<String, Object> rootMap = new HashMap<String, Object>();
            rootMap.put("botname", botName);
            rootMap.put("termsfound", termfound);
            rootMap.put("urls", alertSet);
            Writer out = new StringWriter();
            template.process(rootMap, out);
            // freemarker stuff ends.
            
            Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(properties.getProperty("from-email")));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(properties.getProperty("to-email")));
			message.setSubject(botName+":Alert");
			
            /* you can add html tags in your text to decorate it. */
            BodyPart body = new MimeBodyPart();
            body.setContent(out.toString(), "text/html");
 
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(body);
 
 
            message.setContent(multipart);
 
            Transport.send(message);
            
			


		} catch (MessagingException | IOException | TemplateException e) {
			logger.error("Some issue with sending email:",e);
		}
	}
	
	public static void sendEmailUsingGmail(String botName,Object url,ArrayList<String> termsfound )
	{
		System.out.println("Search term has been found in "+botName+" terms is "+termsfound.toString());
		try {

			//configurng free marjer to send message using email template
			// freemarker stuff.
            Configuration cfg = new Configuration();
            cfg.setDirectoryForTemplateLoading(new File(
            	    ConfigData.configDirectory));
            Template template = cfg.getTemplate("html-mail-template.ftl");
            Map<String, Object> rootMap = new HashMap<String, Object>();
            rootMap.put("botname", botName);
            rootMap.put("termsfound", Joiner.on(",").join(termsfound));
            rootMap.put("urls", url);
            Writer out = new StringWriter();
            template.process(rootMap, out);
            // freemarker stuff ends.
            
            Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(properties.getProperty("from-email")));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(properties.getProperty("to-email")));
			message.setSubject(botName+":Alert");
			
            /* you can add html tags in your text to decorate it. */
            BodyPart body = new MimeBodyPart();
            body.setContent(out.toString(), "text/html");
 
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(body);
 
 
            message.setContent(multipart);
 
            Transport.send(message);
            
			


		} catch (MessagingException | IOException | TemplateException e) {
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
		String urls="jttp://google.com";
		EmailUtility.sendEmailUsingGmail("Twitter", urls, ids);
	}

}
