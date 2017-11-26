package com.realtimescrapper.base;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;
import com.relatimescrapper.pojo.Data;
import org.apache.commons.io.FilenameUtils;

public class Run {

	public static void main(String [] args) throws IOException, InterruptedException
	{

		System.out.println(FilenameUtils.getExtension("asdasd...txt"));
		
	
		ArrayList<String> list=new ArrayList<>();
		list.add("asdasd1");
		File config=new File("c:\\scrapper");
		Producer.initialize(config);
		for(int i=0;i<20;i++)
		{
			Data data=new Data("https://google.com"+i,list);
		Producer.send(data, "HelloKafkaTopic70");
		}
		
		NotificationConsumerThread ncThread =
		          new NotificationConsumerThread("HelloKafkaTopic70",config);
		Thread t = new Thread(ncThread);
		t.start();
		
		t.join();

		 System.out.println("done man here**********************************||||||||||||||||||||||||||||||||||||");
		
//		
		java.util.regex.Matcher m = Pattern.compile("\\w+").matcher("foo bar");
//		while (m.find()) {
//		    System.out.println("Found: " + m.group(0));
//		}	
		
	}
}
