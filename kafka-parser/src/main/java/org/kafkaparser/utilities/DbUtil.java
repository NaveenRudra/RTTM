package org.kafkaparser.utilities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.kafkaparser.pojo.Data;
import org.sqlite.dataaccess.entity.Result;
import org.sqlite.dataaccess.util.DaoUtil;

public class DbUtil {

	private static DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
	private static Date dateobj = new Date();
	
	public static void addNewEntry(ArrayList<String> termsFound,Data data)
	{

				Result result = new Result();
				result.setSearchedTerms(termsFound);
	     		result.setSearchedtext("This is for future implementation");
	     		result.setBotName(data.getBotName());
				result.setUrl(data.getUrl());
				result.setTime(df.format(dateobj).toString());
				DaoUtil.insert(result);
		
	}
	
	public static void addNewEntry(ArrayList<String> termsFound,String url)
	{

				Result result = new Result();
				result.setSearchedTerms(termsFound);
	     		result.setSearchedtext("This is for future implementation");
	     		result.setBotName("Future");
				result.setUrl(url);
				result.setTime(df.format(dateobj).toString());
				DaoUtil.insert(result);
		
	}
	
	public static void addNewEntry(ArrayList<String> termsFound,String url,String botName)
	{

				Result result = new Result();
				result.setSearchedTerms(termsFound);
	     		result.setSearchedtext("This is for future implementation");
	     		result.setBotName(botName);
				result.setUrl(url);
				result.setTime(df.format(dateobj).toString());
				DaoUtil.insert(result);
		
	}
	
	public static void addNewEntry(ArrayList<String> termsFound,String url,String botName,Boolean is_Valid)
	{

				Result result = new Result();
				result.setSearchedTerms(termsFound);
	     		result.setSearchedtext("This is for future implementation");
	     		result.setBotName(botName);
				result.setUrl(url);
				result.setTime(df.format(dateobj).toString());
				result.setIs_valid(is_Valid);
				DaoUtil.insert(result);
		
	}
}
