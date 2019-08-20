package org.kafkaparser.utilities;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.nio.file.*;

import org.sqlite.dataaccess.entity.Result;
import org.sqlite.dataaccess.util.DaoUtil;
import org.kafkaparser.pojo.Data;

import net.amygdalum.stringsearchalgorithms.io.StringCharProvider;
import net.amygdalum.stringsearchalgorithms.search.Horspool;
import net.amygdalum.stringsearchalgorithms.search.StringFinder;

import java.util.UUID; 



public class SearchThread implements Runnable{

	private Data data;
	private static DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
	private static Date dateobj = new Date();
	private Horspool stringSearch;
	
	public void initialize(Data data)
	{
		this.data=data;
	}
	
	
	public void run() {
		// TODO Auto-generated method stub
	    System.out.println("Analyzing **********-----------  "+  data.getUrl());
		String response=null;
		try {
			response = HttpUtilities.sendGet(data.getUrl());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<String> termsFound = new ArrayList<String>();
		for(String s:data.getSearchTerms())
		{
			/**
			if(response.contains(s))
			{
				termsFound.add(s);
			}**/
			
			String[] terms = s.split("\\s+");
			boolean searchTermFound=true;
			
			for (String each : terms) 
			{
				stringSearch = new Horspool(each);
				StringFinder finder = stringSearch.createFinder(new StringCharProvider(response, 0));
				if(finder.findAll().size()==0)
				{
					searchTermFound=false;
					break;
				}
			}
			/**stringSearch = new Horspool(s);
			StringFinder finder = stringSearch.createFinder(new StringCharProvider(response, 0));
			if(finder.findAll().size()>0)
			{
				termsFound.add(s);
			}**/
			if(searchTermFound)
			{
				termsFound.add(s);
			}
		}
		if(termsFound.size()>0)
		{
			//check if multiple threads are resulting in reading same data again and again over ok
  		   // System.out.println(df.format(dateobj)+"found in **************************************************"+data.getUrl()+" data found is "+termsFound.get(0));				

		    if(data.getTrufflehogentropy().equals("true") || data.getTrufflehogregex().equals("true"))
		    {
		    	if(!DaoUtil.searchDuplicateByUrl(data.getUrl()))
				{
		    		DbUtil.addNewEntry(termsFound, data);
					System.out.println("Analyzing url************" + data.getUrl());
					TruffleHog truffleHogThread = new TruffleHog();
					String tempFielPath=createFile(response,data.getBotName());
					truffleHogThread.initilaize(tempFielPath,data.getUrl(), termsFound.toString(),data.getBotName(),data.getTrufflehogregex(),data.getTrufflehogentropy());
					Thread t = new Thread(truffleHogThread);;							
					t.start();
					while(t.isAlive());
					deleteFile(tempFielPath);
					return;
				}
		    }
			
  		   if(!DaoUtil.searchDuplicateByUrl(data.getUrl()))
			{
				EmailUtility.sendEmailUsingGmail(data.getBotName(), data.getUrl(), termsFound);
				DbUtil.addNewEntry(termsFound, data);
			}
  		   
		}
		
		return;
	}
	
	public String createFile(String data,String profile)
	{
		File file = new File(profile+UUID.randomUUID().toString()+".txt");
		try {
			if (file.createNewFile()){
				FileWriter writer = new FileWriter(file);
				System.out.println(data+"*********************");
				BufferedWriter output = new BufferedWriter(new FileWriter(file));
				output.write(data);
				output.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return file.getAbsolutePath();
		
	}
	
	public void deleteFile(String absoluteFilePath)
	{
		try {
			Files.deleteIfExists(Paths.get(absoluteFilePath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
